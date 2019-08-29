package streaming.spark

import com.datastax.driver.core.{Cluster, Session}
import org.apache.spark.sql.{ForeachWriter, Row}
import streaming.ConfigReader.Keyspace

object CassandraForeachWriter {

  def writeToCassandra: ForeachWriter[Row] = {

    new ForeachWriter[Row] {
      var cluster: Option[Cluster] = None
      var session: Option[Session] = None

      override def open(partitionId: Long, version: Long): Boolean = {

        cluster = Some(Cluster.builder().addContactPoint("localhost").build())
        session = Some(cluster.get.newSession())
        true
      }

      override def process(row: Row): Unit = {
        val (word, count) = (row.getString(0), row.getLong(1))
        session.get.execute(s"insert into $Keyspace.wordcount(word, count) values('$word', $count);")
      }

      override def close(errorOrNull: Throwable): Unit = {
        session.get.getCluster.close()
        session.get.close()
      }

    }
  }

}
