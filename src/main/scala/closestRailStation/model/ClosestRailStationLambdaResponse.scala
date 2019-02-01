package closestRailStation.model

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

// https://aws.amazon.com/premiumsupport/knowledge-center/malformed-502-api-gateway/
case class ClosestRailStationLambdaResponse(isBase64Encoded: Boolean,
                                            statusCode: Int,
                                            headers: Option[Map[String, String]],
                                            body: String)

object ClosestRailStationLambdaResponse {
  implicit val encoder: Encoder[ClosestRailStationLambdaResponse] = deriveEncoder[ClosestRailStationLambdaResponse]
  implicit val decoder: Decoder[ClosestRailStationLambdaResponse] = deriveDecoder[ClosestRailStationLambdaResponse]

  def ok(isBase64Encoded: Boolean = false,
         headers: Option[Map[String, String]] = None,
         body: String): ClosestRailStationLambdaResponse =
    ClosestRailStationLambdaResponse(isBase64Encoded, 200, headers, body)
}
