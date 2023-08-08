package dsl

import StopNearby
import org.optaplanner.core.config.heuristic.selector.common.nearby.NearbySelectionConfig
import org.optaplanner.core.config.heuristic.selector.common.nearby.NearbySelectionDistributionType
import org.optaplanner.core.config.heuristic.selector.entity.EntitySelectorConfig
import org.optaplanner.core.config.heuristic.selector.move.generic.ChangeMoveSelectorConfig
import org.optaplanner.core.config.heuristic.selector.value.ValueSelectorConfig

fun ChangeMoveSelectorConfig.nearby(id: String, max: Int) {
    entitySelectorConfig = EntitySelectorConfig().apply {
        this.id = id
    }
    valueSelectorConfig = ValueSelectorConfig().apply {
        nearbySelectionConfig = NearbySelectionConfig().apply {
            originEntitySelectorConfig = EntitySelectorConfig().apply {
                mimicSelectorRef = id
            }
            nearbyDistanceMeterClass = StopNearby::class.java
            nearbySelectionDistributionType = NearbySelectionDistributionType.PARABOLIC_DISTRIBUTION
            parabolicDistributionSizeMaximum = max
        }
    }
}
