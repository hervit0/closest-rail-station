package closestRailStation.dataParsing.decoders

import closestRailStation.dataParsing.models.RawRailStation
import kantan.csv.HeaderDecoder

trait RailStationDecoder {
  implicit val railStationDecoder: HeaderDecoder[RawRailStation] =
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
      "Modification",
      "Latitude",
      "Longitude"
    )(RawRailStation.apply)
}
