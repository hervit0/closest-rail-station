package closestRailStation.dataParsing.models

//AtcoCode,TiplocCode,CrsCode,StationName,StationNameLang,GridType,Easting,Northing,CreationDateTime,ModificationDateTime,RevisionNumber,Modification,Latitude,Longitude
//9100PENZNCE,PENZNCE,PNZ,Penzance Rail Station,"",U,147588,30599,2003-11-04T00:00:00,2011-09-30T14:47:28,2,rev,50.1216622978, -5.5326194274
final case class RawRailStation(atcoCode: String,
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
                                modification: String,
                                latitude: Double,
                                longitude: Double) {}
