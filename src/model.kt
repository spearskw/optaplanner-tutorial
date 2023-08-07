import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.lookup.PlanningId
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty
import org.optaplanner.core.api.domain.solution.PlanningScore
import org.optaplanner.core.api.domain.solution.PlanningSolution
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider
import org.optaplanner.core.api.domain.variable.PlanningListVariable
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore

@PlanningSolution
data class Solution(
    @ProblemFactCollectionProperty
    @ValueRangeProvider
    val stops: List<Stop>,

    @PlanningEntityCollectionProperty
    val vehicles: List<Vehicle>
) {
    @PlanningScore
    val score: HardSoftScore? = null
}

@PlanningEntity
data class Vehicle(
    @PlanningId
    val id: String,
    val capacity: Int,
    val depot: Point,

    @PlanningListVariable
    val stops: List<Stop> = emptyList()
)

data class Stop(
    val id: String,
    val location: Point,
    val demand: Int
)

data class Point(
    val x: Int,
    val y: Int
)
