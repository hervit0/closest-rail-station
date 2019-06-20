package closestRailStation

import closestRailStation.dataParsing.persistence.DynamoRailStationRepositoryComponent
import closestRailStation.dataParsing.services.RailStationExtractorImplementationComponent
import closestRailStation.model.{ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse}
import com.amazonaws.ClientConfiguration
import com.amazonaws.regions.Regions
import com.amazonaws.retry.PredefinedRetryPolicies
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import io.github.mkotsur.aws.handler.Lambda
import io.github.mkotsur.aws.handler.Lambda._

class ClosestRailStationLambda
    extends Lambda[ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse]
    with ClosestRailStationLambdaComponent
    with DynamoRailStationRepositoryComponent
    with LazyLogging
    with RailStationExtractorImplementationComponent
    with ConfigProvider {

  val config: Config = ConfigFactory.load()

  val railStationExtractor: RailStationExtractor = new RailStationExtractorImplementation

  val dynamoDB: AmazonDynamoDB =
    AmazonDynamoDBClientBuilder
      .standard()
      .withRegion(Regions.US_EAST_1)
      .withClientConfiguration(
        new ClientConfiguration()
          .withRetryPolicy(PredefinedRetryPolicies.NO_RETRY_POLICY)
          .withMaxErrorRetry(0)
      )
      .build()

  val railStationRepository: RailStationRepository = new DynamoRailStationRepository

}
