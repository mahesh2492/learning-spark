package streaming

import org.apache.log4j.{Level, LogManager}

trait LoggerHelper {

  private val logger = LogManager.getLogger(this.getClass)

  logger.setLevel(Level.INFO)

  def debug(message: String): Unit = logger.debug(message)

  def info(message: String): Unit = logger.info(message)

  def warn(message: String): Unit = logger.warn(message)

  def error(message: String): Unit = logger.error(message)
}
