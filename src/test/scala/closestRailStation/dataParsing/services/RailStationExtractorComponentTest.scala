package closestRailStation.dataParsing.services

import closestRailStation.ConfigProvider
import closestRailStation.dataParsing.models.RailStation
import closestRailStation.dataParsing.persistence.DynamoRailStationRepositoryComponent
import closestRailStation.fixtures.Fixtures
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType._
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}
import com.gu.scanamo.syntax._
import com.gu.scanamo.{LocalDynamoDB, Scanamo, Table}
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{BeforeAndAfterEach, WordSpec}

class RailStationExtractorComponentTest extends WordSpec with BeforeAndAfterEach {
  private val dynamoDBClient: AmazonDynamoDB = {
    val configuration = new EndpointConfiguration("http://127.0.0.1:8000", "us-west-2")
    AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(configuration).build()
  }

  override def beforeEach: Unit = {
    LocalDynamoDB.createTable(dynamoDBClient)("station")('id -> S)
  }

  override def afterEach: Unit = {
    LocalDynamoDB.deleteTable(dynamoDBClient)("station")
  }

  private val railStations = Set(Fixtures.railStation)
  private val railStationTable = Table[RailStation]("station")
  private def stationByName(name: String): Either[String, RailStation] =
    Scanamo.exec(dynamoDBClient)(railStationTable.get('id -> name)) match {
      case Some(Right(station: RailStation)) => Right(station)
      case _                                 => Left("No station")
    }

  private trait Resource
      extends RailStationExtractorImplementationComponent
      with DynamoRailStationRepositoryComponent
      with ConfigProvider {
    val railStationRepository: RailStationRepository = new DynamoRailStationRepository

    val dynamoDB: AmazonDynamoDB = dynamoDBClient

    val railStationExtractor: RailStationExtractor = new RailStationExtractorImplementation()

    val config: Config = ConfigFactory.load()
  }

  "RailStationExtractorComponent" should {
    "extract" should {
      "pass down the response from the repository" in new Resource {
        // When
        val result = railStationExtractor.extract

        // Then
        assert(result === Right("saved!"))
      }

      "save the station data" in new Resource {
        // When
        val result = railStationExtractor.extract

        // Then
        val expectedSavedStation = stationByName("9100PENZNCE")
        assert(expectedSavedStation === Right(Fixtures.railStation))
      }

      "not return a station which is not part of the set" in new Resource {
        // When
        val result = railStationExtractor.extract

        // Then
        val stationNotSaved = stationByName("STATION NOT IN THE SET")
        assert(stationNotSaved === Left("No station"))
      }
    }
  }
}
