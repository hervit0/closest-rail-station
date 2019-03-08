package closestRailStation.dataParsing.persistence

import closestRailStation.dataParsing.models.RailStation
import closestRailStation.dataParsing.persistence.RailStationRepositoryExceptions.RepositoryException

trait RailStationDataComponent {

  val railStationData: RailStationData

  trait RailStationData {
    def saveAll(railStations: Set[RailStation]): Either[RepositoryException, String]
  }
}

trait RailStationDataComponentImplementation extends RailStationDataComponent {
  self: RailStationRepositoryComponent =>

  class RailStationDataImplementation extends RailStationData {
    def saveAll(railStations: Set[RailStation]): Either[RepositoryException, String] =
      railStationRepository.save(railStations)
  }
}
