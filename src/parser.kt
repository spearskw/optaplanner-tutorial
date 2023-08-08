fun loadProblem(path: String): Solution {
    val lines = {}.javaClass.classLoader.getResource(path).readText().lines()
    val vehicles = parseVehicles(lines[4], lines[9])
    val stops = lines.drop(10).filter { it.isNotBlank() }.map { parseStop(it) }
    return Solution(stops, vehicles)
}

fun parseVehicles(vehicleLine: String, depotLine: String): List<Vehicle> {
    val parts = vehicleLine.trim().split("\\s+".toRegex())
    val numVehicles = parts[0].toInt()
    val capacity = parts[1].toInt()
    val depot = parseStop(depotLine).location
    return List(numVehicles) { Vehicle("${it}", capacity, depot)}
}

fun parseStop(line: String): Stop {
    val numbers = line.trim().split("\\s+".toRegex()).map { it.toInt() }
    return Stop(
        id = "${numbers[0]}",
        location = Point(numbers[1], numbers[2]),
        demand = numbers[3]
    )
}