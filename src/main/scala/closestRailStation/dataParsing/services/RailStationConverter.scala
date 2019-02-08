package closestRailStation.dataParsing.services

import closestRailStation.dataParsing.models.{RailStation, RawRailStation}

class RailStationConverter {
  def toRailStation(rawRailStation: RawRailStation): RailStation = {
    RailStation(
      id = rawRailStation.atcoCode,
      stationName = rawRailStation.stationName,
      latitude = rawRailStation.northing.toFloat,
      longitude = rawRailStation.easting.toFloat
    )
  }
}