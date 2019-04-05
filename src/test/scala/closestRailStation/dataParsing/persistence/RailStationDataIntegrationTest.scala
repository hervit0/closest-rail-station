package closestRailStation.dataParsing.persistence

import closestRailStation.dataParsing.models.RailStation
import closestRailStation.fixtures.Fixtures
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType._
import com.amazonaws.services.dynamodbv2.model.{ResourceInUseException, ResourceNotFoundException}
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}
import com.gu.scanamo.syntax._
import com.gu.scanamo.{LocalDynamoDB, Scanamo, Table}
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterEach, WordSpec}

class RailStationDataIntegrationTest extends WordSpec with BeforeAndAfterEach {
//  DYNAMO_ENDPOINT=http://127.0.0.1:8000 AWS_REGION=us-west-2 AWS_ACCESS_KEY_ID=local AWS_SECRET_ACCESS_KEY=local dynamodb-admin
  private val dynamoDBClient: AmazonDynamoDB = {
    val configuration = new EndpointConfiguration("http://127.0.0.1:8000", "us-west-2")
    AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(configuration).build()
  }

  override def beforeEach: Unit = {
    try {
      LocalDynamoDB.createTable(dynamoDBClient)("station")('id -> S)
    } catch {
      case e: ResourceInUseException =>
        println("[RailStationDataIntegrationTest] resource in use while try createTable")
      case e: ResourceNotFoundException =>
        println("[RailStationDataIntegrationTest] resource not found while try createTable")
    }
  }

  override def afterEach: Unit = {
    try {
      LocalDynamoDB.deleteTable(dynamoDBClient)("station")
    } catch {
      case e: ResourceInUseException =>
        println("[RailStationDataIntegrationTest] resource in use while try deleteTable")
      case e: ResourceNotFoundException =>
        println("[RailStationDataIntegrationTest] resource not found while try deleteTable")
    }
  }

  private val railStations = Set(Fixtures.railStation)
  private val railStationTable = Table[RailStation]("station")
  private def stationByName(name: String): Either[String, RailStation] =
    Scanamo.exec(dynamoDBClient)(railStationTable.get('id -> name)) match {
      case Some(Right(station: RailStation)) => Right(station)
      case _                                 => Left("No station")
    }

  trait Subject extends RailStationDataComponentImplementation with DynamoRailStationRepositoryComponent {
    val railStationRepository: RailStationRepository = new DynamoRailStationRepository

    val dynamoDB: AmazonDynamoDB = dynamoDBClient

    val railStationData = new RailStationDataImplementation
  }

  "RailStationDataImplementation" should {
    "saveAll()" should {
      "pass down the response from the repository" in new Subject {
        // When
        val result = railStationData.saveAll(railStations)

        // Then
        result shouldBe Right("saved!")
      }

      "save the station data" in new Subject {
        // When
        val result = railStationData.saveAll(railStations)

        // Then
        val expectedSavedStation = stationByName("9100PENZNCE")
        assert(expectedSavedStation === Right(Fixtures.railStation))
      }

      "not return a station which is not part of the set" in new Subject {
        // When
        val result = railStationData.saveAll(railStations)

        // Then
        val stationNotSaved = stationByName("STATION NOT IN THE SET")
        assert(stationNotSaved === Left("No station"))
      }
    }
  }

}
