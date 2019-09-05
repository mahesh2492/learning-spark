package datasets

import org.apache.spark.sql.{Encoder, Encoders, SparkSession}

case class Flight(DEST_COUNTRY_NAME: String, ORIGIN_COUNTRY_NAME: String, count: BigInt)

object Examples extends App {

  val spark = SparkSession.builder().master("local[*]").appName("dataset").getOrCreate()

  val df = spark.read.format("json").load("src/main/resources/2015-summary.json")

  df.show(5, false)

  implicit val flightEncoder: Encoder[Flight] = Encoders.product[Flight]

  val flight = df.as[Flight]

  flight.show(5)
}
