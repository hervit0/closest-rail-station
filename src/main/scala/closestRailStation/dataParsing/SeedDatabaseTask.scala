package closestRailStation.dataParsing

import closestRailStation.dataParsing.services.RailStationExtractorImplementationComponent
import com.typesafe.scalalogging.LazyLogging

object SeedDatabaseTask extends App with RailStationExtractorImplementationComponent with LazyLogging {
  val railStationExtractor = new RailStationExtractorImplementation

  private def call: Unit = {
    logger.info("Start: Parsing Rail Station data from CSV")

    railStationExtractor.extract

    logger.info("Done: Parsing Rail Station data from CSV")
    println("Done")
  }

  call
}
