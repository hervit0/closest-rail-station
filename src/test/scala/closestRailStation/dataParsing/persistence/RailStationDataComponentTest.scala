package closestRailStation.dataParsing.persistence

import closestRailStation.fixtures.Fixtures
import org.mockito.Matchers.any
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.Matchers._
import org.scalatest.WordSpec
import org.scalatest.mockito.MockitoSugar._

class RailStationDataComponentTest extends WordSpec {
  trait Subject extends RailStationDataComponentImplementation with RailStationRepositoryComponent {
    val railStationRepository: RailStationRepository = mock[RailStationRepository]

    val railStationData = new RailStationDataImplementation
  }

  "RailStationDataImplementation" should {
    "saveAll()" should {
      "pass down the response from the repository" in new Subject {
        // Given
        when(railStationRepository.save(any())).thenReturn(Right("wow"))
        val railStations = Set(Fixtures.railStation)

        // When
        val result = railStationData.saveAll(railStations)

        // Then
        result shouldBe Right("wow")
      }

      "call the repository with the correct parameters" in new Subject {
        // Given
        val railStations = Set(Fixtures.railStation)

        // When
        val result = railStationData.saveAll(railStations)

        // Then
        verify(railStationRepository, times(1)).save(railStations)
      }
    }
  }

}
