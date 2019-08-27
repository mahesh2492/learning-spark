package streaming.kafka

import java.util.Properties

import akka.actor.ActorSystem
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import streaming.ConfigReader._
import streaming.LoggerHelper

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._
import scala.util.Random

object DataStreamer extends App with LoggerHelper {

  lazy val system = ActorSystem("data-streamer")

  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  private val properties =  new Properties()

  properties.put("bootstrap.servers", bootstrapServer)
  properties.put("key.serializer", serializer)
  properties.put("value.serializer", serializer)

  val producer = new KafkaProducer[String, String](properties)

  val randomWords = List("confluent", "databricks", "lightbend", "datastax", "akka")

  info("Streaming words to kafka..")
  system.scheduler.schedule(0 seconds, 100 milliseconds) {
    Random.shuffle(randomWords).foreach { word =>
      producer.send(new ProducerRecord[String, String]("topic", word))
    }
  }

}
