package closestRailStation.dataParsing.services

import closestRailStation.dataParsing.models.RawRailStation
import org.scalatest.WordSpec

class RailStationExtractorComponentTest extends WordSpec {
  private trait Resource extends RailStationExtractorImplementationComponent {
    val railStationExtractor: RailStationExtractor = new RailStationExtractorImplementation()
  }

  "RailStationExtractorComponent" should {
    "extract" should {
      "return a list of [RailStation]" in new Resource {
        val response = railStationExtractor.extract
        val firstStation = response.head

        val expectedFirstStation = RawRailStation("9100PENZNCE",
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

        assert(response.length === 2628)
        assert(firstStation === expectedFirstStation)
      }
    }
  }
}
