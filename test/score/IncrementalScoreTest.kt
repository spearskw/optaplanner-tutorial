package score

import IncrementalScore
import Point
import Solution
import Stop
import Vehicle
import org.junit.Test
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import kotlin.test.assertEquals

class IncrementalScoreTest {

    @Test
    fun `can reset working solution correctly`() {
        val scoreCalculator = IncrementalScore()
        val vehicle = Vehicle(
            id = "a",
            capacity = 10,
            depot = Point(0, 0),
        )
        val stops = listOf(
            Stop("1", Point(0, -1), 10),
            Stop("2", Point(0, 1), 20)
        )

        vehicle.next = stops[0]
        stops[0].next = stops[1]

        stops[0].previous = vehicle
        stops[1].previous = stops[0]

        stops[0].vehicle = vehicle
        stops[1].vehicle = vehicle

        val solution = Solution(stops, listOf(vehicle))
        scoreCalculator.resetWorkingSolution(solution)
        val actual = scoreCalculator.calculateScore()

        assertEquals(HardSoftScore.of(-20, -40), actual)
    }
}