package closestRailStation.dataParsing.services

import closestRailStation.dataParsing.models.{RailStation, RawRailStation}

// Float is not enough => Double
// Int is not enough => Long
object RailStationConverter {
  def toRailStation(rawRailStation: RawRailStation): RailStation = {
    RailStation(
      id = rawRailStation.atcoCode,
      stationName = rawRailStation.stationName,
//      latitude = (rawRailStation.latitude * 1e10).toLong,
//      longitude = (rawRailStation.longitude * 1e10).toLong
      latitude = rawRailStation.latitude,
      longitude = rawRailStation.longitude
    )
  }
}
