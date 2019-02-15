package closestRailStation.dataParsing.persistence

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType._
import com.gu.scanamo.error.DynamoReadError
import com.gu.scanamo.syntax._
import com.gu.scanamo.{LocalDynamoDB, Scanamo, Table}

case class Farm(animals: List[String])
case class Farmer(name: String, age: Long, farm: Farm)

class RailStationData {
//  private val client = LocalDynamoDB.client()
//  private val client = {
//    val conf = new EndpointConfiguration("http://localhost:8000", "us-east-1")
//    AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(conf).build()
//  }
//
//  def saveAll: Option[Either[DynamoReadError, Farmer]] = {
//    val farmersTableResult = LocalDynamoDB.createTable(client)("farmer")('name -> S)
//    val table = Table[Farmer]("farmer")
//    // table: org.scanamo.Table[Farmer] = Table(farmer)
//
//    Scanamo.exec(client)(table.put(Farmer("McDonald", 156L, Farm(List("sheep", "cow")))))
//    // res0: Option[Either[org.scanamo.error.DynamoReadError,Farmer]] = None
//
//    Scanamo.exec(client)( table.get('name -> "McDonald"))
//  }
}
