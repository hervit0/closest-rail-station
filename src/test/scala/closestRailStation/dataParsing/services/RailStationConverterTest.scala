package closestRailStation.dataParsing.services
import closestRailStation.dataParsing.models.{RailStation, RawRailStation}
import org.scalatest.WordSpec

class RailStationConverterTest extends WordSpec {

  class Subject extends RailStationConverter {}

  "RailStationConverter" should {
    "toRailStation(RawRailStation)" should {
      "returns a [RailStation] from a [RawRailStation]" in new Subject {
        val rawRailStation = RawRailStation("9100PENZNCE",
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
                                            "rev")

        val expectedRailStation = RailStation(id = "9100PENZNCE",
                                              stationName = "Penzance Rail Station",
                                              latitude = 30599.0F,
                                              longitude = 147588.0F)

        assert(this.toRailStation(rawRailStation) === expectedRailStation)
      }
    }
  }

}
