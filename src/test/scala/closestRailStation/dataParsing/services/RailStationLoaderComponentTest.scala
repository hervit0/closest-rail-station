package closestRailStation.dataParsing.services

import closestRailStation.ConfigProvider
import closestRailStation.dataParsing.models.RailStation
import closestRailStation.dataParsing.persistence.DynamoRailStationRepositoryComponent
import closestRailStation.fixtures.Fixtures
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType._
import com.amazonaws.services.dynamodbv2.model.{ResourceInUseException, ResourceNotFoundException}
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}
import com.gu.scanamo.syntax._
import com.gu.scanamo.{LocalDynamoDB, Scanamo, Table}
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{BeforeAndAfterEach, WordSpec}

class RailStationLoaderComponentTest extends WordSpec with BeforeAndAfterEach {
  private val dynamoDBClient: AmazonDynamoDB = {
    val configuration = new EndpointConfiguration("http://127.0.0.1:8000", "us-west-2")
    AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(configuration).build()
  }

  override def beforeEach: Unit = {
    try {
      LocalDynamoDB.createTable(dynamoDBClient)("stations")('id -> S)
      println("[RailStationExtractorComponentTest] createTable is success")
    } catch {
      case e: ResourceInUseException =>
        println("[RailStationExtractorComponentTest] resource in use while try createTable")
      case e: ResourceNotFoundException =>
        println("[RailStationExtractorComponentTest] resource not found while try createTable")
    }
  }

  override def afterEach: Unit = {
    try {
      LocalDynamoDB.deleteTable(dynamoDBClient)("stations")
      Thread.sleep(1000)
      println("[RailStationExtractorComponentTest] deleteTable is success")
    } catch {
      case e: ResourceInUseException =>
        println("[RailStationExtractorComponentTest] resource in use while try deleteTable")
      case e: ResourceNotFoundException =>
        println("[RailStationExtractorComponentTest] resource not found while try deleteTable")
    }
  }

  private val railStations = Set(Fixtures.railStation)
  private val railStationTable = Table[RailStation]("stations")
  private def stationByName(name: String): Either[String, RailStation] =
    Scanamo.exec(dynamoDBClient)(railStationTable.get('id -> name)) match {
      case Some(Right(station: RailStation)) => Right(station)
      case _                                 => Left("No station")
    }

  private trait Resource
      extends RailStationLoaderImplementationComponent
      with RailStationExtractorImplementationComponent
      with DynamoRailStationRepositoryComponent
      with ConfigProvider {
    val railStationRepository: RailStationRepository = new DynamoRailStationRepository

    val dynamoDB: AmazonDynamoDB = dynamoDBClient

    val railStationExtractor: RailStationExtractor = new RailStationExtractorImplementation

    val railStationLoader: RailStationLoader = new RailStationLoaderImplementation

    val config: Config = ConfigFactory.load()
  }

  "RailStationLoaderComponent" should {
    "load" should {
      "pass down the response from the repository" in new Resource {
        // When
        val result = railStationLoader.load

        // Then
        assert(result === Right("saved!"))
      }

      "save the station data" in new Resource {
        // When
        val result = railStationLoader.load

        // Then
        val expectedSavedStation = stationByName("9100PENZNCE")
        assert(expectedSavedStation === Right(Fixtures.railStation))
      }

      "not return a station which is not part of the set" in new Resource {
        // When
        val result = railStationLoader.load

        // Then
        val stationNotSaved = stationByName("STATION NOT IN THE SET")
        assert(stationNotSaved === Left("No station"))
      }
    }
  }
}
