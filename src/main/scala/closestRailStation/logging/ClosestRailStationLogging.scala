package closestRailStation.logging

import closestRailStation.model.ClosestRailStationLambdaRequest
import com.typesafe.scalalogging.LazyLogging

object ClosestRailStationLogging extends LazyLogging {
  def request(request: ClosestRailStationLambdaRequest): Unit =
    logger.info("Request received: {}", request)

}
