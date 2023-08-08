import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory
import kotlin.math.*

class AngleDifficultyFactory : SelectionSorterWeightFactory<Solution, Stop> {
    override fun createSorterWeight(solution: Solution, stop: Stop): AngleDifficulty {
        return AngleDifficulty(stop, solution.vehicles[0].depot)
    }
}

class AngleDifficulty(stop: Stop, depot: Point) : Comparable<AngleDifficulty> {
    private val easiestVector = Vector(1.0, 1.0)
    private val stopVector = depot.vectorTo(stop.location)
    val angle = easiestVector.angleDegrees(stopVector)

    override operator fun compareTo(other: AngleDifficulty): Int {
        val angleDiff = this.angle - other.angle
        if (angleDiff == 0) {
            return -1
        }
        return angleDiff
    }
}

data class Vector(
    val x: Double,
    val y: Double
) {
    fun angleDegrees(other: Vector): Int {
        val radians = atan2(det(other), dot(other))
        val degrees = (180 * radians / PI).roundToInt()
        return if (degrees < 0) degrees + 360 else degrees
    }

    fun dot(other: Vector): Double {
        return this.x*other.x + this.y*other.y
    }

    fun det(other: Vector): Double {
        return this.x * other.y - other.x * this.y
    }
}

fun Point.vectorTo(other: Point): Vector {
    val x = other.x - this.x
    val y = other.y - this.y
    return Vector(x.toDouble(), y.toDouble())
}