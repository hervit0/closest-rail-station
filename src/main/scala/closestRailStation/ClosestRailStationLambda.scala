package closestRailStation

import closestRailStation.model.{ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse}
import com.amazonaws.services.lambda.runtime.Context
import io.github.mkotsur.aws.handler.Lambda
import io.github.mkotsur.aws.handler.Lambda._

class ClosestRailStationLambda
  extends Lambda[ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse]
    with ClosestRailStationLambdaComponent {

  override def handle(request: ClosestRailStationLambdaRequest, context: Context) =
    Right(ClosestRailStationLambdaResponse(request.inputMsg.reverse))

}

