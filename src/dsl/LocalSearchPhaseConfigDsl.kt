package dsl

import org.optaplanner.core.config.heuristic.selector.move.composite.UnionMoveSelectorConfig
import org.optaplanner.core.config.localsearch.LocalSearchPhaseConfig
import org.optaplanner.core.config.localsearch.decider.acceptor.LocalSearchAcceptorConfig
import org.optaplanner.core.config.localsearch.decider.forager.LocalSearchForagerConfig
import org.optaplanner.core.config.solver.termination.TerminationConfig

internal fun LocalSearchPhaseConfig.acceptor(lambda: LocalSearchAcceptorConfig.() -> Unit) {
    this.acceptorConfig = LocalSearchAcceptorConfig().apply(lambda)
}

internal fun LocalSearchPhaseConfig.forager(lambda: LocalSearchForagerConfig.() -> Unit) {
    this.foragerConfig = LocalSearchForagerConfig().apply(lambda)
}

internal fun LocalSearchPhaseConfig.unionMoveSelector(lambda: UnionMoveSelectorConfig.() -> Unit) {
    val config = UnionMoveSelectorConfig()
    config.moveSelectorList = mutableListOf()
    config.lambda()
    this.moveSelectorConfig = config
}

internal fun LocalSearchPhaseConfig.termination(lambda: TerminationConfig.() -> Unit) {
    this.terminationConfig = TerminationConfig().apply(lambda)
}