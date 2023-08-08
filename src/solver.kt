import dsl.*
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicType

val vrpSolver = solver<Solution> {
    entityClassList = listOf(
        Stop::class.java,
        RouteLink::class.java
    )

    scoreDirectorFactory {
        easyScoreCalculatorClass = ScoreCalculator::class.java
    }

    constructionHeuristic {
        constructionHeuristicType = ConstructionHeuristicType.FIRST_FIT_DECREASING
    }

    localSearch {
        termination {
            stepCountLimit = 1000
        }
    }
}