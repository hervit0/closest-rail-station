package closestRailStation.dataParsing.services

import java.io.File

import closestRailStation.dataParsing.decoders._
import closestRailStation.dataParsing.models.RawRailStation
import kantan.csv.ops._
import kantan.csv.rfc

trait RailStationExtractorComponent {

  val railStationExtractor: RailStationExtractor

  trait RailStationExtractor {
    def extract: List[RawRailStation]
  }
}

trait RailStationExtractorImplementationComponent extends RailStationExtractorComponent {

  class RailStationExtractorImplementation extends RailStationExtractor {
    def extract: List[RawRailStation] = {
      val decodedRailStations =
        new File("src/main/resources/RailReferences.csv").asCsvReader[RawRailStation](rfc.withHeader)
      decodedRailStations.filter(_.isRight).toList.map(_.right.get)
    }
  }

}
