import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import org.optaplanner.core.api.score.calculator.IncrementalScoreCalculator
import score.distance

class IncrementalScore : IncrementalScoreCalculator<Solution, HardSoftScore> {

    private var demandMap = mutableMapOf<Vehicle, Int>()
    private var distance = 0.0

    override fun resetWorkingSolution(workingSolution: Solution) {
        println("Resetting working solution")
        demandMap = mutableMapOf()
        distance = 0.0

        workingSolution.stops
            .filter { it.vehicle != null }
            .forEach {
                val vehicle = it.vehicle!!
                val oldDemand = demandMap.getOrDefault(vehicle, vehicle.capacity)
                demandMap[vehicle] = oldDemand + it.demand

                distance += it.distanceFromPrevious()
                if (it.next == null) {
                    distance += it.distanceToNext()
                }
            }

        println("Working solution reset. Distance is ${distance}")
    }

    override fun beforeEntityAdded(entity: Any?) {
        println("about to add entity: ${entity}")
    }

    override fun afterEntityAdded(entity: Any?) {
        println("added entity ${entity}")
    }

    override fun beforeVariableChanged(entity: Any?, variableName: String?) {
        println("about to change variable $variableName for entity $entity")
        when(variableName) {
            "vehicle" -> {
                if (entity is Stop) {
                    val vehicle = entity.vehicle?:return
                    val oldDemand = demandMap.getOrDefault(vehicle, vehicle.capacity)
                    demandMap[vehicle] = oldDemand + entity.demand
                }
            }
            "next" -> {
                distance -= (entity as RouteLink).distanceToNext()
            }
            "previous" -> {
                distance -= (entity as Stop).distanceFromPrevious()
            }
        }
        println("  new distance: $distance")
        println("  new demand: $demandMap")
    }

    override fun afterVariableChanged(entity: Any?, variableName: String?) {
        println("changed variable $variableName for entity $entity")
        when(variableName) {
            "vehicle" -> {
                if (entity is Stop) {
                    val vehicle = entity.vehicle?:return
                    val oldDemand = demandMap.getOrDefault(vehicle, vehicle.capacity)
                    demandMap[vehicle] = oldDemand - entity.demand
                }
            }
            "next" -> {
                distance += (entity as RouteLink).distanceToNext()
            }
            "previous" -> {
                distance += (entity as Stop).distanceFromPrevious()
            }
        }
        println("  new distance: $distance")
        println("  new demand: $demandMap")
    }

    override fun beforeEntityRemoved(entity: Any?) {
        println("about to remove entity: ${entity}")
    }

    override fun afterEntityRemoved(entity: Any?) {
        println("removed entity: ${entity}")
    }

    override fun calculateScore(): HardSoftScore {
        return HardSoftScore.of(
            demandMap.values.filter { it < 0 }.sum(),
            -(distance * 10).toInt()
        )
    }
}

fun RouteLink?.location(): Point? {
    return when (this) {
        is Vehicle -> this.depot
        is Stop -> this.location
        else -> null
    }
}

fun Stop.distanceFromPrevious(): Double {
    return this.previous.location()?.distance(this.location)?:0.0
}

fun RouteLink.distanceToNext(): Double {
    if (this is Stop && this.vehicle != null && this.next == null) {
        return this.location.distance(this.vehicle!!.depot)
    }
    if (this.next == null) {
        return 0.0
    }
    return this.location()!!.distance(this.next!!.location)
}
