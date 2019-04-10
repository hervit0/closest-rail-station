package closestRailStation

import closestRailStation.dataParsing.services.RailStationExtractorImplementationComponent
import closestRailStation.model.{ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse}
import com.typesafe.config.{Config, ConfigFactory}
import io.github.mkotsur.aws.handler.Lambda
import io.github.mkotsur.aws.handler.Lambda._

class ClosestRailStationLambda
    extends Lambda[ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse]
    with ClosestRailStationLambdaComponent
    with RailStationExtractorImplementationComponent
    with ConfigProvider {

  val config: Config = ConfigFactory.load()

  val railStationExtractor: RailStationExtractor = new RailStationExtractorImplementation

}
