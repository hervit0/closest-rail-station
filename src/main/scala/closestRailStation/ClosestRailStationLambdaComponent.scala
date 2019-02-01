package closestRailStation

import closestRailStation.model.{ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse}
import com.amazonaws.services.lambda.runtime.Context
import io.github.mkotsur.aws.handler.Lambda

trait ClosestRailStationLambdaComponent
  extends Lambda[ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse] {

  override def handle(request: ClosestRailStationLambdaRequest, context: Context) =
    Right(ClosestRailStationLambdaResponse(request.inputMsg.reverse))

}
