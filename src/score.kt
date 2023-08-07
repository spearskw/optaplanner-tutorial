import kotlin.math.max
import kotlin.math.sqrt

fun calculateScore(stops: Set<Stop>, vehicles: List<Vehicle>): Score {
    val missedDeliveries = findMissedDeliveries(stops, vehicles)
    val totalCapacityOverage = vehicles.sumOf { it.capacityOverage() }
    val totalDistance = vehicles.sumOf { it.distanceTravelled() }

    return Score(
        hard = -(missedDeliveries.size * 10 + totalCapacityOverage),
        soft = -(totalDistance * 10).toInt()
    )
}

fun findMissedDeliveries(stops: Set<Stop>, vehicles: List<Vehicle>): Set<Stop> {
    val delivered = vehicles.flatMap { it.stops }.toSet()
    return stops - delivered
}

fun Vehicle.capacityOverage(): Int {
    return max(this.stops.sumOf { it.demand } - this.capacity, 0)
}

fun Vehicle.distanceTravelled(): Double {
    val locations = listOf(this.depot) + this.stops.map { it.location } + this.depot
    return locations.zipWithNext { a, b -> a.distance(b) }.sum()
}

fun Point.distance(other: Point): Double {
    val a = this.x - other.x
    val b = this.y - other.y
    val c = a * a + b * b
    return sqrt(c.toDouble())
}