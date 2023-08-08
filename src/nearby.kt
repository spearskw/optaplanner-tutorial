import org.optaplanner.core.impl.heuristic.selector.common.nearby.NearbyDistanceMeter

class StopNearby : NearbyDistanceMeter<RouteLink, RouteLink> {
    override fun getNearbyDistance(origin: RouteLink, destination: RouteLink): Double {
        return if (origin is Stop && destination is Stop) {
            origin.location.distance(destination.location)
        } else {
            0.0
        }
    }
}
