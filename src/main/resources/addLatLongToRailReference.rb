require 'csv'
# require 'rb-readline'
require 'pry'

# If launch from root
batchTransformations = CSV.read("src/main/resources/batch_transformation_2019_02_15_153246.csv")
railReferencesOriginal = CSV.read("src/main/resources/RailReferencesOriginal.csv")

latLongs =
  batchTransformations
    .map { |transformation| transformation[3] }
    .drop(1)
    .map { |latLong| latLong.split(",").take(2) }

latLongsWithHeaders = [["Latitude", "Longitude"]] + latLongs

railReferences =
  railReferencesOriginal
    .zip(latLongsWithHeaders)
    .map { |(origin, latLong)| origin + latLong }

CSV.open("src/main/resources/RailReferences.csv", "wb") do |csv|
  railReferences.each do |line|
    csv << line
  end
end

binding.pry

