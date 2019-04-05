package closestRailStation.dataParsing

import closestRailStation.ConfigProvider
import closestRailStation.dataParsing.persistence.DynamoRailStationRepositoryComponent
import closestRailStation.dataParsing.services.RailStationExtractorImplementationComponent
import closestRailStation.logging.ClosestRailStationLogging
import com.amazonaws.ClientConfiguration
import com.amazonaws.regions.Regions
import com.amazonaws.retry.PredefinedRetryPolicies
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}
import com.typesafe.config.{Config, ConfigFactory}

object SeedDatabaseTask
    extends App
    with DynamoRailStationRepositoryComponent
    with RailStationExtractorImplementationComponent
    with ConfigProvider {

//  US East (N. Virginia)	us-east-1

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

  val railStationExtractor: RailStationExtractor = new RailStationExtractorImplementation

  val config: Config = ConfigFactory.load()

  private def call(): Unit = {
    val startTime = ClosestRailStationLogging.startSeedDatabase

    railStationExtractor.extract

    ClosestRailStationLogging.finishSeedDatabase(startTime)
  }

  call()
}
