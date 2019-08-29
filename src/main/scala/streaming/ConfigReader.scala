package streaming

import com.typesafe.config.ConfigFactory

object ConfigReader {
  private val config = ConfigFactory.load()
  val bootstrapServer: String = config.getString("BOOTSTRAP_SERVER")
  val serializer: String = config.getString("STRING_SERIALIZER")
  val deserializer: String = config.getString("STRING_DESERIALIZER")
  val Keyspace: String = config.getString("KEY_SPACE")
}
