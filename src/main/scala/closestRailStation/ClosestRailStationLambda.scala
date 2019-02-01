package closestRailStation

import closestRailStation.model.{ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse}
import io.github.mkotsur.aws.handler.Lambda
import io.github.mkotsur.aws.handler.Lambda._

class ClosestRailStationLambda
    extends Lambda[ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse]
    with ClosestRailStationLambdaComponent {}
