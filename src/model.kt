import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.lookup.PlanningId
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty
import org.optaplanner.core.api.domain.solution.PlanningScore
import org.optaplanner.core.api.domain.solution.PlanningSolution
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider
import org.optaplanner.core.api.domain.variable.AnchorShadowVariable
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable
import org.optaplanner.core.api.domain.variable.PlanningVariable
import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore

@PlanningSolution
data class Solution(
    @PlanningEntityCollectionProperty
    @ValueRangeProvider(id = "stopRange")
    val stops: List<Stop>,

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "vehicleRange")
    val vehicles: List<Vehicle>
) {
    @PlanningScore
    val score: HardSoftScore? = null
}

@PlanningEntity
interface RouteLink {
    @get:InverseRelationShadowVariable(sourceVariableName = "previous")
    var next: Stop?
}

data class Vehicle(
    @PlanningId
    val id: String,
    val capacity: Int,
    val depot: Point
) : RouteLink {
    override var next: Stop? = null

    val stops: List<Stop>
        get() {
            val items = mutableListOf<Stop>()
            var current: Stop? = this.next
            while (current != null) {
                items.add(current)
                current = current.next
            }
            return items
        }
}

@PlanningEntity
data class Stop(
    @PlanningId
    val id: String,
    val location: Point,
    val demand: Int
) : RouteLink {
    @AnchorShadowVariable(sourceVariableName = "previous")
    var vehicle: Vehicle? = null

    @PlanningVariable(
        graphType = PlanningVariableGraphType.CHAINED,
        valueRangeProviderRefs = ["stopRange", "vehicleRange"]
    )
    var previous: RouteLink? = null

    override var next: Stop? = null
}

data class Point(
    val x: Int,
    val y: Int
)
