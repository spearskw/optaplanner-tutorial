import dsl.*

val vrpSolver = solver<Solution> {
    entityClassList = listOf(
        Vehicle::class.java
    )

    scoreDirectorFactory {
        easyScoreCalculatorClass = ScoreCalculator::class.java
    }
}