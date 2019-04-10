package closestRailStation

import java.io.File

import closestRailStation.dataParsing.decoders._
import closestRailStation.dataParsing.models.{RailStation, RawRailStation}
import closestRailStation.dataParsing.services.RailStationConverter
import closestRailStation.logging.ClosestRailStationLogging
import closestRailStation.model.{ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse}
import com.amazonaws.services.lambda.runtime.Context
import io.github.mkotsur.aws.handler.Lambda
import kantan.csv.ops._
import kantan.csv.rfc

trait ClosestRailStationLambdaComponent
    extends Lambda[ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse]
    with ConfigProvider {

  def euclideanDistance(station: RailStation, latitude: Float, longitude: Float): Double =
    Math.sqrt(Math.pow(station.latitude - latitude, 2) + Math.pow(station.longitude - longitude, 2))

  override def handle(request: ClosestRailStationLambdaRequest,
                      context: Context): Either[Throwable, ClosestRailStationLambdaResponse] = {
    ClosestRailStationLogging.request(request)

    val latitude = request.queryStringParameters.latitude.toFloat
    val longitude = request.queryStringParameters.longitude.toFloat

    val filePath = config.getString("app.stage") match {
      case "dev" => "src/main/resources/RailReferencesTest.csv"
      case _     => "src/main/resources/RailReferences.csv"
    }

    val decodedRailStations = new File(filePath).asCsvReader[RawRailStation](rfc.withHeader)

    val closestRailStation = decodedRailStations
      .filter(_.isRight)
      .toList
      .map(_.right.get)
      .map(RailStationConverter.toRailStation)
      .minBy(euclideanDistance(_, latitude, longitude))
      .stationName

    Right(ClosestRailStationLambdaResponse.ok(isBase64Encoded = true, None, closestRailStation))
  }

}
