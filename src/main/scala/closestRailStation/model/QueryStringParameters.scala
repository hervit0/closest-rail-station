package closestRailStation.model

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class QueryStringParameters(position: String) {

}

object QueryStringParameters {
  implicit val encoder: Encoder[QueryStringParameters] = deriveEncoder[QueryStringParameters]
  implicit val decoder: Decoder[QueryStringParameters] = deriveDecoder[QueryStringParameters]
}
