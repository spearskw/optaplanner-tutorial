import dsl.*

val vrpSolver = solver<Solution> {
    entityClassList = listOf(
        Stop::class.java,
        RouteLink::class.java
    )

    scoreDirectorFactory {
        easyScoreCalculatorClass = ScoreCalculator::class.java
    }

    constructionHeuristic { }

    localSearch {
        termination {
            stepCountLimit = 30000
        }
    }
}