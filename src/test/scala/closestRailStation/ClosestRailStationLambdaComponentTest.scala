package closestRailStation

import closestRailStation.dataParsing.models.RailStation
import closestRailStation.dataParsing.persistence.{DynamoRailStationRepositoryComponent, RailStationRepositoryComponent}
import closestRailStation.fixtures.{ClosestRailStationFixtures, Fixtures}
import closestRailStation.model.{ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse, QueryStringParameters}
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType._
import com.amazonaws.services.dynamodbv2.model.{ResourceInUseException, ResourceNotFoundException}
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}
import com.gu.scanamo.{LocalDynamoDB, Table}
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import io.github.mkotsur.aws.handler.Lambda
import io.github.mkotsur.aws.handler.Lambda._
import org.scalatest.{BeforeAndAfterEach, EitherValues, WordSpec}

class ClosestRailStationLambdaComponentTest extends WordSpec with EitherValues with BeforeAndAfterEach {
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

  class Subject
      extends Lambda[ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse]
      with ClosestRailStationLambdaComponent
      with DynamoRailStationRepositoryComponent
      with LazyLogging
      with RailStationRepositoryComponent {
    val config: Config = ConfigFactory.load()

    val railStationExtractor: RailStationExtractor = new RailStationExtractorImplementation

    val railStationRepository: RailStationRepository = new DynamoRailStationRepository

    val dynamoDB: AmazonDynamoDB = dynamoDBClient
  }

  private val context = ClosestRailStationFixtures.amazonContext

  "ClosestRailStationLambdaComponent" should {
    "handle(ClosestRailStationLambdaRequest, Context)" should {
      "returns a station" in new Subject {
//        Coordinates of South Bank Waterloo
        val request =
          ClosestRailStationLambdaRequest(QueryStringParameters(latitude = "51.500841", longitude = "-0.107606"))

        val response = handle(request, context)

        assert(response.right.value.body === "London Waterloo East Rail Station")
        assert(response.right.value.statusCode === 200)
      }
    }

  }

}
