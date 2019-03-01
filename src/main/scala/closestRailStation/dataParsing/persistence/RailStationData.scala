package closestRailStation.dataParsing.persistence

import closestRailStation.dataParsing.models.RailStation
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.gu.scanamo.error.DynamoReadError
import com.gu.scanamo.{Scanamo, Table}

// TODO: Cake pattern dude
class RailStationData {
  private val client = {
    val conf = new EndpointConfiguration("http://localhost:8000", "us-east-1")
    AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(conf).build()
  }

  private val railStationTable = Table[RailStation]("station")

  def saveAll(railStations: List[RailStation]): Option[Either[DynamoReadError, RailStation]] = {
    Scanamo.exec(client)(railStationTable.put(railStations(0)))
  }
}
