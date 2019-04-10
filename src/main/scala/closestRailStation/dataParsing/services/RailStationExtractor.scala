package closestRailStation.dataParsing.services
import java.io.File

import closestRailStation.ConfigProvider
import closestRailStation.dataParsing.decoders._
import closestRailStation.dataParsing.models.{RailStation, RawRailStation}
import kantan.csv.ops._
import kantan.csv.rfc

trait RailStationExtractorComponent {

  val railStationExtractor: RailStationExtractor

  trait RailStationExtractor {
    def extract: List[RailStation]
  }

}

trait RailStationExtractorImplementationComponent extends RailStationExtractorComponent {
  self: ConfigProvider =>

  class RailStationExtractorImplementation extends RailStationExtractor {
    def extract: List[RailStation] = {
      val filePath = config.getString("app.stage") match {
        case "dev" => "src/main/resources/RailReferencesTest.csv"
        case _     => "src/main/resources/RailReferences.csv"
      }

      new File(filePath)
        .asCsvReader[RawRailStation](rfc.withHeader)
        .filter(_.isRight)
        .toList
        .map(_.right.get)
        .map(RailStationConverter.toRailStation)
    }
  }

}
