package score

import Point
import Solution
import Vehicle
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator
import kotlin.math.max
import kotlin.math.sqrt


class ScoreCalculator : EasyScoreCalculator<Solution, HardSoftScore> {
    override fun calculateScore(solution: Solution): HardSoftScore {
        val totalCapacityOverage = solution.vehicles.sumOf { it.capacityOverage() }
        val totalDistance = solution.vehicles.sumOf { it.distanceTravelled() }

        return HardSoftScore.of(
            -totalCapacityOverage,
            -(totalDistance * 10).toInt()
        )
    }
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