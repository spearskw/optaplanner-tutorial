data class Solution(
    val stops: List<Stop>,
    val vehicles: List<Vehicle>
)

data class Vehicle(
    val id: String,
    val capacity: Int,
    val depot: Point,
    val stops: List<Stop>
)

data class Stop(
    val id: String,
    val location: Point,
    val demand: Int
)

data class Point(
    val x: Int,
    val y: Int
)
