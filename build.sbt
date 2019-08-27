name := "LearningSpark"

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.2.1",
  "com.typesafe.akka" %% "akka-actor" % "2.5.14",
  "org.apache.kafka" % "kafka-clients" % "0.10.2.1",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.5.1",
  "org.apache.spark" %% "spark-sql" % "2.3.1",
  "org.apache.spark" %% "spark-sql" % "2.3.1" excludeAll(
    ExclusionRule("io.netty", "netty"),
    ExclusionRule("commons-net", "commons-net"),
    ExclusionRule("com.google.guava", "guava")
  ),
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.3.1" exclude("org.apache.kafka", "kafka-clients")
)


conflictManager := ConflictManager.latestRevision