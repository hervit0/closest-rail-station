package closestRailStation.dataParsing.persistence
import com.gu.scanamo.error.DynamoReadError

object RailStationRepositoryExceptions {
  type RepositoryException = RuntimeException

  case class DynamoReadException(dynamoReadError: DynamoReadError) extends RepositoryException(dynamoReadError.toString)

  case class DynamoException(msg: String, cause: Throwable) extends RepositoryException(msg, cause)
}
