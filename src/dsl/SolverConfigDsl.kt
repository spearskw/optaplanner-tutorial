package dsl

import org.optaplanner.core.api.solver.Solver
import org.optaplanner.core.api.solver.SolverFactory
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig
import org.optaplanner.core.config.solver.SolverConfig

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
