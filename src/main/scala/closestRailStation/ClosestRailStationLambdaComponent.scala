package closestRailStation

import closestRailStation.dataParsing.models.RailStation
import closestRailStation.dataParsing.persistence.RailStationRepositoryComponent
import closestRailStation.dataParsing.services.RailStationExtractorImplementationComponent
import closestRailStation.logging.ClosestRailStationLogging
import closestRailStation.model.{ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse}
import com.amazonaws.services.lambda.runtime.Context
import com.gu.scanamo.error.DynamoReadError
import io.github.mkotsur.aws.handler.Lambda

trait ClosestRailStationLambdaComponent
    extends Lambda[ClosestRailStationLambdaRequest, ClosestRailStationLambdaResponse]
    with RailStationExtractorImplementationComponent
    with RailStationRepositoryComponent
    with ConfigProvider {

  def euclideanDistance(station: RailStation, latitude: Float, longitude: Float): Double =
    Math.sqrt(Math.pow(station.latitude - latitude, 2) + Math.pow(station.longitude - longitude, 2))

  @SuppressWarnings(Array("org.wartremover.warts.TraversableOps"))
  override def handle(request: ClosestRailStationLambdaRequest,
                      context: Context): Either[Throwable, ClosestRailStationLambdaResponse] = {
    ClosestRailStationLogging.request(request)

    val latitude = request.queryStringParameters.latitude.toFloat
    val longitude = request.queryStringParameters.longitude.toFloat

    val stations: List[Either[DynamoReadError, RailStation]] = railStationRepository.get
    ClosestRailStationLogging.stations(stations)

//    val closestRailStation = railStationExtractor.extract.minBy(euclideanDistance(_, latitude, longitude)).stationName
    val closestRailStation = "lol"

    Right(ClosestRailStationLambdaResponse.ok(isBase64Encoded = true, None, closestRailStation))
  }

}
