package closestRailStation.dataParsing.services

import closestRailStation.dataParsing.persistence.RailStationRepositoryComponent
import closestRailStation.dataParsing.persistence.RailStationRepositoryExceptions.RepositoryException

trait RailStationLoaderComponent {

  val railStationLoader: RailStationLoader

  trait RailStationLoader {
    def load: Either[RepositoryException, String]
  }
}

trait RailStationLoaderImplementationComponent extends RailStationLoaderComponent {
  self: RailStationRepositoryComponent with RailStationExtractorComponent =>

  class RailStationLoaderImplementation extends RailStationLoader {
    def load: Either[RepositoryException, String] = {
      val railStationsSet = railStationExtractor.extract.toSet
//      railStationsSet.toList.foreach(x => println(s"RailStation(${'"'}${x.id}${'"'}, ${'"'}${x.stationName}${'"'}, ${x.latitude}D, ${x.longitude}D),"))

      val savingOperation = railStationRepository.save(railStationsSet)
      println(savingOperation)
      savingOperation
    }

  }
}
