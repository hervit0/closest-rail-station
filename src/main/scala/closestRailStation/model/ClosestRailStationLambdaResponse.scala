package closestRailStation.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class ClosestRailStationLambdaResponse(outputMsg: String)


object ClosestRailStationLambdaResponse {
  implicit val encoder: Encoder[ClosestRailStationLambdaResponse] = deriveEncoder[ClosestRailStationLambdaResponse]
  implicit val decoder: Decoder[ClosestRailStationLambdaResponse] = deriveDecoder[ClosestRailStationLambdaResponse]
}
