package closestRailStation.dataParsing.persistence

import closestRailStation.dataParsing.models.RailStation
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType._
import com.gu.scanamo.syntax._
import com.gu.scanamo.{LocalDynamoDB, Scanamo, Table}
import org.scalatest.WordSpec

class RailStationDataTest extends WordSpec {
  private val railStation = RailStation(
    id = "9100PENZNCE",
    stationName = "Penzance Rail Station",
    latitude = 501216622978L,
    longitude = -55326194274L
  )

  private val client = {
    val conf = new EndpointConfiguration("http://127.0.0.1:8000", "us-west-2")
    AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(conf).build()
  }

  private val railStationTable = Table[RailStation]("station")

  private def stationByName(name: String): Either[String, RailStation] =
    Scanamo.exec(client)(railStationTable.get('id -> name)) match {
      case Some(Right(station: RailStation)) => Right(station)
      case _                                 => Left("No station")
    }

  class Subject extends RailStationData {
    LocalDynamoDB.deleteTable(client)("station")
    LocalDynamoDB.createTable(client)("station")('id -> S)
  }

  "RailStationData" should {
    "saveAll()" should {
      "save all railReferences to database" in new Subject {
        // When
        this.saveAll(List(railStation))

        // Then
        val expectedSavedStation = stationByName("9100PENZNCE")
        assert(expectedSavedStation === Right(railStation))
      }
    }
  }

}
