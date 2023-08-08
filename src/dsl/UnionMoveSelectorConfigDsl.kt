package dsl

import org.optaplanner.core.config.heuristic.selector.move.composite.UnionMoveSelectorConfig
import org.optaplanner.core.config.heuristic.selector.move.generic.ChangeMoveSelectorConfig
import org.optaplanner.core.config.heuristic.selector.move.generic.SwapMoveSelectorConfig
import org.optaplanner.core.config.heuristic.selector.move.generic.chained.SubChainChangeMoveSelectorConfig
import org.optaplanner.core.config.heuristic.selector.move.generic.chained.SubChainSwapMoveSelectorConfig
import org.optaplanner.core.config.heuristic.selector.move.generic.chained.TailChainSwapMoveSelectorConfig

fun UnionMoveSelectorConfig.tailChainSwapMoveSelector(lambda: TailChainSwapMoveSelectorConfig.() -> Unit) {
    this.moveSelectorList.add(TailChainSwapMoveSelectorConfig().apply(lambda))
}

fun UnionMoveSelectorConfig.subChainChangeMoveSelector(lambda: SubChainChangeMoveSelectorConfig.() -> Unit) {
    this.moveSelectorList.add(SubChainChangeMoveSelectorConfig().apply(lambda))
}

fun UnionMoveSelectorConfig.subChainSwapMoveSelector(lambda: SubChainSwapMoveSelectorConfig.() -> Unit) {
    this.moveSelectorList.add(SubChainSwapMoveSelectorConfig().apply(lambda))
}

fun UnionMoveSelectorConfig.swapMoveSelector(lambda: SwapMoveSelectorConfig.() -> Unit) {
    this.moveSelectorList.add(SwapMoveSelectorConfig().apply(lambda))
}

fun UnionMoveSelectorConfig.changeMoveSelector(lambda: ChangeMoveSelectorConfig.() -> Unit) {
    this.moveSelectorList.add(ChangeMoveSelectorConfig().apply(lambda))
}
