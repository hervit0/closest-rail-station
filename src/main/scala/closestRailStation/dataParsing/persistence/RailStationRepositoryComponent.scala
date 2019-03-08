package closestRailStation.dataParsing.persistence

import closestRailStation.dataParsing.models.RailStation
import closestRailStation.dataParsing.persistence.RailStationRepositoryExceptions.{DynamoException, RepositoryException}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.gu.scanamo.{Scanamo, Table}

trait RailStationRepositoryComponent {

  val railStationRepository: RailStationRepository

  trait RailStationRepository {
    def save(railStations: Set[RailStation]): Either[RepositoryException, String]
  }

}

trait DynamoRailStationRepositoryComponent extends RailStationRepositoryComponent {

  val dynamoDB: AmazonDynamoDB

  class DynamoRailStationRepository extends RailStationRepository {
    private val railStationTable = Table[RailStation]("station")

    override def save(railStations: Set[RailStation]): Either[RepositoryException, String] = {
      try {
        Scanamo.exec(dynamoDB) {
          railStationTable.putAll(railStations)
        }
        Right("saved!")
      } catch {
        case exception: Exception =>
          Left(DynamoException(s"There was an error while saving the request: ${exception.getMessage}", exception))
      }

    }
  }

}
