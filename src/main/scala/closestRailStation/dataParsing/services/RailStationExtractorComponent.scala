package closestRailStation.dataParsing.services

import java.io.File

import closestRailStation.dataParsing.decoders._
import closestRailStation.dataParsing.models.RawRailStation
import closestRailStation.dataParsing.persistence.RailStationRepositoryComponent
import closestRailStation.dataParsing.persistence.RailStationRepositoryExceptions.RepositoryException
import kantan.csv.ops._
import kantan.csv.rfc

trait RailStationExtractorComponent {

  val railStationExtractor: RailStationExtractor

  trait RailStationExtractor {
    def extract: Either[RepositoryException, String]
  }
}

trait RailStationExtractorImplementationComponent extends RailStationExtractorComponent {
  self: RailStationRepositoryComponent =>

  class RailStationExtractorImplementation extends RailStationExtractor {
    def extract: Either[RepositoryException, String] = {
      val decodedRailStations =
        new File("src/main/resources/RailReferences.csv").asCsvReader[RawRailStation](rfc.withHeader)

      val railStationsSet = decodedRailStations
        .filter(_.isRight)
        .toList
        .map(_.right.get)
        .map(RailStationConverter.toRailStation)
        .toSet

      railStationRepository.save(railStationsSet)
    }

  }
}
