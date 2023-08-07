import kotlin.test.Test
import kotlin.test.assertEquals

class ScoreTest {

    @Test
    fun `missed deliveries is calculated correctly`() {
        val stops = setOf(
            Stop("1", Point(0, 0), 0),
            Stop("2", Point(0, 0), 0),
            Stop("3", Point(0, 0), 0)
        )
        val vehicle = Vehicle(
            id = "a",
            capacity = 10,
            depot = Point(0, 0),
            stops = listOf(
                Stop("2", Point(0, 0), 0)
            )
        )
        val actual = findMissedDeliveries(stops, listOf(vehicle))
        assertEquals(2, actual.size)
    }

    @Test
    fun `capacity overage is calculated correctly`() {
        val vehicle = Vehicle(
            id = "a",
            capacity = 10,
            depot = Point(0, 0),
            stops = listOf(
                Stop("1", Point(0, 0), 20)
            )
        )
        assertEquals(10, vehicle.capacityOverage())
    }

    @Test
    fun `distance is calculated correctly`() {
        val vehicle = Vehicle(
            id = "a",
            capacity = 10,
            depot = Point(0, 0),
            stops = listOf(
                Stop("1", Point(0, 1), 0),
                Stop("2", Point(1, 1), 0),
                Stop("3", Point(1, 0), 0),
            )
        )
        assertEquals(4.0, vehicle.distanceTravelled(), 1e-6)
    }

    @Test
    fun `score is calculated correctly`() {
        val stops = setOf(
            Stop("1", Point(0, 0), 0),
            Stop("2", Point(0, -1), 10),
            Stop("3", Point(0, 1), 20)
        )
        val vehicle = Vehicle(
            id = "a",
            capacity = 10,
            depot = Point(0, 0),
            stops = listOf(
                Stop("2", Point(0, -1), 10),
                Stop("3", Point(0, 1), 20)
            )
        )
        val actual = calculateScore(stops, listOf(vehicle))
        assertEquals(Score(-30, -40), actual)
    }
}