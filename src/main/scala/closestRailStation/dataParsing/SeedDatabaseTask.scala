package closestRailStation.dataParsing

import closestRailStation.dataParsing.services.RailStationExtractor
import com.typesafe.scalalogging.LazyLogging

object SeedDatabaseTask extends App with LazyLogging {
  private def call: Unit = {
    logger.info("Start: Parsing Rail Station data from CSV")

    RailStationExtractor.extract

    logger.info("Done: Parsing Rail Station data from CSV")
    println("Done")
  }

  call
}
