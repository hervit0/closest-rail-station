package closestRailStation

import closestRailStation.model.{
  ClosestRailStationLambdaRequest,
  ClosestRailStationLambdaResponse,
  QueryStringParameters
}
import io.github.mkotsur.aws.handler.Lambda
import io.github.mkotsur.aws.handler.Lambda._
import org.scalatest.{EitherValues, WordSpec}

class ClosestRawRailStationLambdaComponentTest extends WordSpec with EitherValues {

  class Subject
      extends Lambda[ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse]
      with ClosestRailStationLambdaComponent {}

  private val context = ClosestRailStationFixtures.amazonContext

  "ClosestRailStationLambdaComponent" should {
    "handle(ClosestRailStationLambdaRequest, Context)" should {
      "returns a station" in new Subject {
        val requestedPosition = "154150:35730"
        val request = ClosestRailStationLambdaRequest(QueryStringParameters(position = requestedPosition))

        val response = handle(request, context)

        assert(response.right.value.body === requestedPosition.reverse)
        assert(response.right.value.statusCode === 200)
      }
    }

  }

}
