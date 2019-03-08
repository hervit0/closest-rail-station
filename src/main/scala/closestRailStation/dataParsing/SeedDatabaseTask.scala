package closestRailStation.dataParsing

import closestRailStation.dataParsing.persistence.{
  DynamoRailStationRepositoryComponent,
  RailStationDataComponentImplementation,
  RailStationRepositoryComponent
}
import closestRailStation.dataParsing.services.RailStationExtractorImplementationComponent
import closestRailStation.logging.ClosestRailStationLogging
import com.amazonaws.ClientConfiguration
import com.amazonaws.retry.PredefinedRetryPolicies
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}

object SeedDatabaseTask
    extends App
    with RailStationExtractorImplementationComponent
    with RailStationRepositoryComponent
    with DynamoRailStationRepositoryComponent {

  val dynamoDB: AmazonDynamoDB =
    AmazonDynamoDBClientBuilder
      .standard()
      .withClientConfiguration(
        new ClientConfiguration()
          .withRetryPolicy(PredefinedRetryPolicies.NO_RETRY_POLICY)
          .withMaxErrorRetry(0)
      )
      .build()

  val railStationRepository: RailStationRepository = new DynamoRailStationRepository

  val railStationExtractor: RailStationExtractor = new RailStationExtractorImplementation

  private def call(): Unit = {
    val startTime = ClosestRailStationLogging.startSeedDatabase

    railStationExtractor.extract

    ClosestRailStationLogging.finishSeedDatabase(startTime)
  }

  call()
}
