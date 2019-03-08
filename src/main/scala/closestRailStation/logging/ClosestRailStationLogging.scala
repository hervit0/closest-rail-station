package closestRailStation.logging

import closestRailStation.model.ClosestRailStationLambdaRequest
import com.typesafe.scalalogging.LazyLogging

object ClosestRailStationLogging extends LazyLogging {
  def request(request: ClosestRailStationLambdaRequest): Unit =
    logger.info("Request received: {}", request)

  def startSeedDatabase: Long = {
    logger.info("Start: Parsing Rail Station data from CSV")
    System.currentTimeMillis
  }

  def finishSeedDatabase(startTime: Long): Unit = {
    val endTime = System.currentTimeMillis
    logger.info("Done: Parsing Rail Station data from CSV - Duration (ms): {}", (endTime - startTime))
  }
}
