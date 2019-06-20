package closestRailStation.dataParsing.persistence

import closestRailStation.dataParsing.models.RailStation
import closestRailStation.dataParsing.persistence.RailStationRepositoryExceptions.{DynamoException, RepositoryException}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.gu.scanamo.error.{DynamoReadError, MissingProperty}
import com.gu.scanamo.{Scanamo, Table}
import com.typesafe.scalalogging.LazyLogging

trait RailStationRepositoryComponent {

  val railStationRepository: RailStationRepository

  trait RailStationRepository {
    def save(railStations: Set[RailStation]): Either[RepositoryException, String]

    def get: List[Either[DynamoReadError, RailStation]]
  }

}

trait DynamoRailStationRepositoryComponent extends RailStationRepositoryComponent {
  self: LazyLogging =>

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
          Left(DynamoException(s"[Dynamo] Error save: ${exception.getMessage}", exception))
      }
    }

    def get: List[Either[DynamoReadError, RailStation]] = {
      val operations = for {
        railStations <- railStationTable.scan()
      } yield railStations

      try {
        Scanamo.exec(dynamoDB)(operations)
      } catch {
        case exception: Exception =>
          logger.debug(exception.toString)
          List(Left(MissingProperty))
      }
    }

  }

}
