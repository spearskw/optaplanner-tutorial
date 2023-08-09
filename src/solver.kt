import dsl.*
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicType
import org.optaplanner.core.config.heuristic.selector.value.chained.SubChainSelectorConfig
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig
import org.optaplanner.core.config.solver.EnvironmentMode
import score.ScoreCalculator

val vrpSolver = solver<Solution> {
    entityClassList = listOf(
        Stop::class.java,
        RouteLink::class.java
    )

    environmentMode = EnvironmentMode.FULL_ASSERT

    scoreDirectorFactory {
        incrementalScoreCalculatorClass = IncrementalScore::class.java
        assertionScoreDirectorFactory = ScoreDirectorFactoryConfig().apply {
            easyScoreCalculatorClass = ScoreCalculator::class.java
        }
    }

    constructionHeuristic {
        constructionHeuristicType = ConstructionHeuristicType.FIRST_FIT_DECREASING
    }

    localSearch {
        unionMoveSelector {
            tailChainSwapMoveSelector { } //2-opt
            changeMoveSelector {
                nearby("1", 25)
            }
            changeMoveSelector {
                nearby("2", 10)
            }
            swapMoveSelector { }
            subChainChangeMoveSelector {
                selectReversingMoveToo = true
                subChainSelectorConfig = SubChainSelectorConfig().apply {
                    minimumSubChainSize = 2
                }
            }
            subChainSwapMoveSelector {
                selectReversingMoveToo = true
            }
        }
        acceptor {
            entityTabuSize = 5
            simulatedAnnealingStartingTemperature = "10hard/400soft"
        }
        forager {
            acceptedCountLimit = 4
        }
        termination {
            stepCountLimit = 30000
        }
    }
}
