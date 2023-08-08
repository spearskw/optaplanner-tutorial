import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import kotlin.test.Test
import kotlin.test.assertEquals

class ScoreTest {

    @Test
    fun `capacity overage is calculated correctly`() {
        val vehicle = Vehicle(
            id = "a",
            capacity = 10,
            depot = Point(0, 0)
        )
        val stop = Stop("1", Point(0, 0), 20)
        vehicle.next = stop
        assertEquals(10, vehicle.capacityOverage())
    }

    @Test
    fun `distance is calculated correctly`() {
        val vehicle = Vehicle(
            id = "a",
            capacity = 10,
            depot = Point(0, 0),
        )
        val stops = listOf(
            Stop("1", Point(0, 1), 0),
            Stop("2", Point(1, 1), 0),
            Stop("3", Point(1, 0), 0),
        )
        vehicle.next = stops[0]
        stops[0].next = stops[1]
        stops[1].next = stops[2]
        assertEquals(4.0, vehicle.distanceTravelled(), 1e-6)
    }

    @Test
    fun `score is calculated correctly`() {
        val scoreCalculator = ScoreCalculator()
        val stops = listOf(
            Stop("2", Point(0, -1), 10),
            Stop("3", Point(0, 1), 20)
        )
        val vehicle = Vehicle(
            id = "a",
            capacity = 10,
            depot = Point(0, 0),
        )
        vehicle.next = stops[0]
        stops[0].next = stops[1]
        val solution = Solution(stops, listOf(vehicle))
        val actual = scoreCalculator.calculateScore(solution)
        assertEquals(HardSoftScore.of(-20, -40), actual)
    }
}