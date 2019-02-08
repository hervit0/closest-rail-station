package closestRailStation.dataParsing.services

import java.io.File

import closestRailStation.dataParsing.decoders._
import closestRailStation.dataParsing.models.RailStation
import kantan.csv.ops._
import kantan.csv.{ReadResult, rfc}

object RailStationExtractor {
  def extract: List[ReadResult[RailStation]] = {
    val decodedRailStations = new File("src/main/resources/RailReferences.csv").asCsvReader[RailStation](rfc.withHeader)
    decodedRailStations.filter(_.isRight).toList
  }
}
