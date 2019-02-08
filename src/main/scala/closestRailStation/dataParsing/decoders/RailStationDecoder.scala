package closestRailStation.dataParsing.decoders

import closestRailStation.dataParsing.models.RailStation
import kantan.csv.HeaderDecoder

trait RailStationDecoder {
  implicit val railStationDecoder: HeaderDecoder[RailStation] =
    HeaderDecoder.decoder(
      "AtcoCode",
      "TiplocCode",
      "CrsCode",
      "StationName",
      "StationNameLang",
      "GridType",
      "Easting",
      "Northing",
      "CreationDateTime",
      "ModificationDateTime",
      "RevisionNumber",
      "Modification"
    )(RailStation.apply)
}
