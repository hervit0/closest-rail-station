package closestRailStation.dataParsing.persistence

import closestRailStation.dataParsing.models.RailStation
import closestRailStation.dataParsing.persistence.RailStationRepositoryExceptions.{DynamoException, RepositoryException}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.gu.scanamo.error.DynamoReadError
import com.gu.scanamo.{Scanamo, Table}

trait RailStationRepositoryComponent {

  val railStationRepository: RailStationRepository

  trait RailStationRepository {
    def save(railStations: Set[RailStation]): Either[RepositoryException, String]

    def get: List[Either[DynamoReadError, RailStation]]
  }

}

trait DynamoRailStationRepositoryComponent extends RailStationRepositoryComponent {

  val dynamoDB: AmazonDynamoDB

  class DynamoRailStationRepository extends RailStationRepository {
    private val railStationTable = Table[RailStation]("stations")

//    @SuppressWarnings(Array("org.wartremover.warts.TraversableOps"))
    def save(railStations: Set[RailStation]): Either[RepositoryException, String] = {
      try {
        println(s"About to save ${railStations.size} stations")
        Scanamo.exec(dynamoDB) {
          railStationTable.putAll(railStations)
        }
        Right("saved!")
      } catch {
        case exception: Exception =>
          Left(DynamoException(s"There was an error while saving the request: ${exception.getMessage}", exception))
      }
    }

    def get: List[Either[DynamoReadError, RailStation]] = {
      val operations = for {
        railStations <- railStationTable.scan()
      } yield railStations

      Scanamo.exec(dynamoDB)(operations)
    }

  }

}
