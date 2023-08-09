package score

import Stop
import Vehicle
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import org.optaplanner.core.api.score.stream.Constraint
import org.optaplanner.core.api.score.stream.ConstraintCollectors.sum
import org.optaplanner.core.api.score.stream.ConstraintFactory
import org.optaplanner.core.api.score.stream.ConstraintProvider

class StreamScore : ConstraintProvider {
    override fun defineConstraints(factory: ConstraintFactory): Array<Constraint> {
        return arrayOf(distanceFromPrevious(factory), demand(factory))
    }
}

fun distanceFromPrevious(factory: ConstraintFactory): Constraint {
    return factory.forEach(Stop::class.java)
        .filter { it.vehicle != null }
        .penalize(HardSoftScore.ONE_SOFT) {
            (it.distanceFromPrevious() * 10).toInt()
        }.asConstraint("distance")
}

private fun Stop.distanceFromPrevious(): Double {
    val distance = when (val prev = previous) {
        is Stop -> prev.location.distance(location)
        is Vehicle -> prev.depot.distance(location)
        else -> 0.0
    }

    val returnDistance = if (next == null) {
        location.distance(vehicle!!.depot)
    } else {
        0.0
    }

    return distance + returnDistance
}

fun demand(factory: ConstraintFactory): Constraint {
    return factory.forEach(Stop::class.java)
        .filter { it.vehicle != null }
        .groupBy(Stop::vehicle, sum(Stop::demand))
        .filter { vehicle, demand -> demand > vehicle!!.capacity }
        .penalize(HardSoftScore.ONE_HARD) { vehicle, demand ->
            demand - vehicle!!.capacity
        }.asConstraint("demand")
}