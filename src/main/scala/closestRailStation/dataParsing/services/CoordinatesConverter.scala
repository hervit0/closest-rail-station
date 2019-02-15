package closestRailStation.dataParsing.services

//https://github.com/alphagov/location-data-importer/blob/master/src/main/scala/uk/gov/gds/location/importer/conversions/EastingNorthingToLatLongConvertor.scala
//https://geotrellis.readthedocs.io/en/latest/tutorials/quickstart.html
//https://github.com/locationtech/geotrellis/blob/master/README.md

//50.122045, -5.532459
//147588	30599

//https://www.bgs.ac.uk/data/webservices/convertForm.cfm#decimalLatLng
//British National Grid (BNG)
//Easting: 147598
//Northing: 30640

class CoordinatesConverter {
//  private val crs = ReferencingFactoryFinder.getCRSAuthorityFactory("EPSG", null)
//  private val egs84crs = crs.createCoordinateReferenceSystem("4326")
//  private val osbgr = crs.createCoordinateReferenceSystem("27700")
//  private val op = new DefaultCoordinateOperationFactory().createOperation(osbgr, egs84crs)
//
//  def gridReferenceToLatLong(easting: Double, northing: Double) = {
//    val eastNorth = new GeneralDirectPosition(easting, northing)
//    LatLong(op.getMathTransform().transform(eastNorth, eastNorth))
//  }
}
