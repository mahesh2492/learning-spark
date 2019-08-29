package streaming.spark

import com.datastax.driver.core.{Cluster, Session}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.types.StringType
import streaming.ConfigReader._
import streaming.LoggerHelper
import streaming.spark.CassandraForeachWriter.writeToCassandra

object StructuredStreamingWordCount extends App with LoggerHelper {

  info("connection to cassandra ")
  val cluster = Cluster.builder().addContactPoint("localhost").build()
  val session: Session = cluster.connect()

  info("creating a keyspace..")
  session.execute(s"create keyspace if not exists $Keyspace WITH replication = " +
    "{'class':'SimpleStrategy', 'replication_factor':1};")

  info("creating a table words..")
  session.execute(s"create table if not exists $Keyspace.wordcount ( word text primary key, count int);")

  info("closing db connection...")
  session.getCluster.close()
  session.close()

  val spark = SparkSession.builder().master("local").appName("streamingwordcount").getOrCreate()
  spark.sparkContext.setLogLevel("WARN")

  val streamingDf =
    spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServer)
      .option("subscribe", "topic")
      .load()


  streamingDf.printSchema()

  info("Writing data to cassandra..")
  val query =
    streamingDf
      .select(col("value").cast(StringType).as("word"), lit(1).as("count"))
      .groupBy("word")
      .agg(sum("count").as("count"))
      .writeStream
      .option("checkpointLocation", "src/main/checkpointing")
      .outputMode(OutputMode.Update())
      .foreach(writeToCassandra)
      .start()

  query.awaitTermination()
  query.stop()
}
