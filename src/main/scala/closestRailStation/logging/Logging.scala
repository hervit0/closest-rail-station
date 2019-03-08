package closestRailStation.logging

import closestRailStation.model.ClosestRailStationLambdaRequest
import com.typesafe.scalalogging.LazyLogging

object Logging extends LazyLogging {
  def request(request: ClosestRailStationLambdaRequest) =
    logger.info("Request received: {}", request)

}
