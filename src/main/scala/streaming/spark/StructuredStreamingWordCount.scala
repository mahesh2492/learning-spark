package streaming.spark

import com.datastax.driver.core.Cluster
import org.apache.spark.sql.SparkSession
import streaming.LoggerHelper
import streaming.ConfigReader._

object StructuredStreamingWordCount extends App with LoggerHelper {

  info("connection to cassandra ")
  val cluster = Cluster.builder().addContactPoint("localhost").build()
  val session = cluster.connect()

  info("creating a keyspace..")
  session.execute("create keyspace if not exists test WITH replication = " +
    "{'class':'SimpleStrategy', 'replication_factor':1};")

  info("creating a table words..")
  session.execute("create table if not exists test.words ( word text primary key, count int);")

  info("closing db connection...")
  session.close()
  session.getCluster.close()

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

 val query =
   streamingDf
       .selectExpr( "CAST(value AS STRING)")
      .writeStream
      .format("console")
      .start()

     query.awaitTermination()
}
