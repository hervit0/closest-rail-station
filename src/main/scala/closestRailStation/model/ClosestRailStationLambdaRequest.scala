package closestRailStation.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class ClosestRailStationLambdaRequest(queryStringParameters: QueryStringParameters)

object ClosestRailStationLambdaRequest {
  implicit val encoder: Encoder[ClosestRailStationLambdaRequest] = deriveEncoder[ClosestRailStationLambdaRequest]
  implicit val decoder: Decoder[ClosestRailStationLambdaRequest] = deriveDecoder[ClosestRailStationLambdaRequest]
}
