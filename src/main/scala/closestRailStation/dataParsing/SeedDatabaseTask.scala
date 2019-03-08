package closestRailStation.dataParsing

import closestRailStation.dataParsing.services.RailStationExtractorImplementationComponent
import closestRailStation.logging.ClosestRailStationLogging

object SeedDatabaseTask extends App with RailStationExtractorImplementationComponent {
  val railStationExtractor = new RailStationExtractorImplementation

  private def call(): Unit = {
    val startTime = ClosestRailStationLogging.startSeedDatabase

    railStationExtractor.extract

    ClosestRailStationLogging.finishSeedDatabase(startTime)
  }

  call()
}
