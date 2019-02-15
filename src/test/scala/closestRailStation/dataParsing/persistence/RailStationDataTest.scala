package closestRailStation.dataParsing.persistence

import org.scalatest.WordSpec

class RailStationDataTest extends WordSpec {
  class Subject extends RailStationData {}

  "RailStationData" should {
    "saveAll()" should {
      "save all railReferences to database" in new Subject {
//        assert(this.saveAll === "")
      }
    }
  }

}
