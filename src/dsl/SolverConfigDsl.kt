package dsl

import org.optaplanner.core.api.solver.Solver
import org.optaplanner.core.api.solver.SolverFactory
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicPhaseConfig
import org.optaplanner.core.config.exhaustivesearch.ExhaustiveSearchPhaseConfig
import org.optaplanner.core.config.localsearch.LocalSearchPhaseConfig
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig
import org.optaplanner.core.config.solver.SolverConfig
import org.optaplanner.core.config.solver.termination.TerminationConfig

inline fun <reified T> solver(
    configDslBody: SolverConfig.() -> Unit
): Solver<T> {
    val config = SolverConfig()
    config.phaseConfigList = mutableListOf()
    config.configDslBody()
    config.withSolutionClass(T::class.java)

    return SolverFactory.create<T>(config).buildSolver()
}

fun SolverConfig.scoreDirectorFactory(lambda: ScoreDirectorFactoryConfig.() -> Unit) {
    this.scoreDirectorFactoryConfig = ScoreDirectorFactoryConfig().apply(lambda)
}

fun SolverConfig.exhaustiveSearch(lambda: ExhaustiveSearchPhaseConfig.() -> Unit) {
    this.phaseConfigList.add(ExhaustiveSearchPhaseConfig().apply(lambda))
}

fun SolverConfig.termination(lambda: TerminationConfig.() -> Unit) {
    this.terminationConfig = TerminationConfig().apply(lambda)
}

fun SolverConfig.constructionHeuristic(lambda: ConstructionHeuristicPhaseConfig.() -> Unit) {
    this.phaseConfigList.add(ConstructionHeuristicPhaseConfig().apply(lambda))
}

fun SolverConfig.localSearch(lambda: LocalSearchPhaseConfig.() -> Unit) {
    this.phaseConfigList.add(LocalSearchPhaseConfig().apply(lambda))
}
