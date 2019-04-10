package closestRailStation

import closestRailStation.fixtures.ClosestRailStationFixtures
import closestRailStation.model.{
  ClosestRailStationLambdaRequest,
  ClosestRailStationLambdaResponse,
  QueryStringParameters
}
import com.typesafe.config.{Config, ConfigFactory}
import io.github.mkotsur.aws.handler.Lambda
import io.github.mkotsur.aws.handler.Lambda._
import org.scalatest.{EitherValues, WordSpec}

class ClosestRailStationLambdaComponentTest extends WordSpec with EitherValues {

  class Subject
      extends Lambda[ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse]
      with ClosestRailStationLambdaComponent {
    val config: Config = ConfigFactory.load()

    val railStationExtractor: RailStationExtractor = new RailStationExtractorImplementation
  }

  private val context = ClosestRailStationFixtures.amazonContext

  "ClosestRailStationLambdaComponent" should {
    "handle(ClosestRailStationLambdaRequest, Context)" should {
      "returns a station" in new Subject {
        val request = ClosestRailStationLambdaRequest(QueryStringParameters(latitude = "51.2", longitude = "32.4"))

        val response = handle(request, context)

        assert(response.right.value.body === "Penzance Rail Station")
        assert(response.right.value.statusCode === 200)
      }
    }

  }

}
