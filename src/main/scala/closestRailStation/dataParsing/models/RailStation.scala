package closestRailStation.dataParsing.models

//"AtcoCode","TiplocCode","CrsCode","StationName","StationNameLang","GridType","Easting","Northing","CreationDateTime","ModificationDateTime","RevisionNumber","Modification"
//"9100PENZNCE","PENZNCE","PNZ","Penzance Rail Station","","U",147588,30599,"2003-11-04T00:00:00","2011-09-30T14:47:28",2,"rev"
final case class RailStation(atcoCode: String,
                             tiplocCode: String,
                             crsCode: String,
                             stationName: String,
                             stationNameLang: String,
                             gridType: String,
                             easting: Int,
                             northing: Int,
                             creationDateTime: String,
                             modificationDateTime: String,
                             revisionNumber: Int,
                             modification: String) {}
