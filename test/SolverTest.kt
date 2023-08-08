import dsl.exhaustiveSearch
import dsl.scoreDirectorFactory
import dsl.solver
import org.junit.Test
import kotlin.test.assertEquals

class SolverTest {

    @Test
    fun `a problem with requiring 2 vehicles uses 2 vehicles efficiently`() {
        val stops = listOf(
            Stop("1", Point(2, 1), 1),
            Stop("2", Point(2, -1), 1),

            Stop("3", Point(-2, 1), 1),
            Stop("4", Point(-2, -1), 1)
        )
        val vehicles = listOf(
            Vehicle("a", 2, Point(0, 0)),
            Vehicle("b", 2, Point(0, 0))
        )
        val problem = Solution(stops, vehicles)
        val solution = testSolver.solve(problem)

        assertEquals(0, solution.score?.hardScore())
        assertEquals(-129, solution.score?.softScore())
    }
}

val testSolver = solver<Solution> {
    entityClassList = listOf(
        Stop::class.java,
        RouteLink::class.java
    )

    scoreDirectorFactory {
        easyScoreCalculatorClass = ScoreCalculator::class.java
    }

    exhaustiveSearch { }
}