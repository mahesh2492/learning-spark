name := "LearningSpark"

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies ++= Seq(

  "org.apache.spark" %% "spark-core" % "2.2.0"
)


conflictManager := ConflictManager.latestRevision