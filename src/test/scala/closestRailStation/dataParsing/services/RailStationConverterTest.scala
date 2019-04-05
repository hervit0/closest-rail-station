package closestRailStation.dataParsing.services

import closestRailStation.dataParsing.models.{RailStation, RawRailStation}
import org.scalatest.WordSpec

class RailStationConverterTest extends WordSpec {

  class Subject  {
    val railStationConverter: RailStationConverter.type = RailStationConverter
  }

  "RailStationConverter" should {
    "toRailStation(RawRailStation)" should {
      "returns a [RailStation] from a [RawRailStation]" in new Subject {
        val rawRailStation = RawRailStation(
          "9100PENZNCE",
          "PENZNCE",
          "PNZ",
          "Penzance Rail Station",
          "",
          "U",
          147588,
          30599,
          "2003-11-04T00:00:00",
          "2011-09-30T14:47:28",
          2,
          "rev",
          50.1216622978D,
          -5.5326194274D
        )

        val expectedRailStation = RailStation(
          id = "9100PENZNCE",
          stationName = "Penzance Rail Station",
          latitude = 501216622978L,
          longitude = -55326194274L
        )

        assert(railStationConverter.toRailStation(rawRailStation) === expectedRailStation)
      }
    }
  }

}
