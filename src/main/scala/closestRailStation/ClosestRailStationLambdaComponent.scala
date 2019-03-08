package closestRailStation

import closestRailStation.logging.ClosestRailStationLogging
import closestRailStation.model.{ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse}
import com.amazonaws.services.lambda.runtime.Context
import io.github.mkotsur.aws.handler.Lambda

trait ClosestRailStationLambdaComponent
    extends Lambda[ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse] {

  override def handle(request: ClosestRailStationLambdaRequest,
                      context: Context): Either[Throwable, ClosestRailStationLambdaResponse] = {
    ClosestRailStationLogging.request(request)
    val closestRailStation = request.queryStringParameters.position.reverse
    Right(ClosestRailStationLambdaResponse.ok(isBase64Encoded = true, None, closestRailStation))
  }

}
