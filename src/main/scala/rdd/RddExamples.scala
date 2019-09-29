package rdd

import org.apache.spark.sql.SparkSession

object RddExamples extends App {

  val spark = SparkSession.builder().appName("rdd-example").master("local[*]").getOrCreate()

  //creating an rdd. Directly using rdd method on dataframe/dataset
  val rdd = spark.range(10).toDF().rdd

  println(rdd.map(_.getLong(0)))

  //another way converting collection to rdd using parallelize method
  val myCollection = "Spark The Definitive Guide : Big Data Processing Made Simple".split(" ")
  val words = spark.sparkContext.parallelize(myCollection, 2)

  //creating a key/value pair rdd
  words.map(word => (word.toLowerCase, 1)).foreach(println)

  //another way to create key/value pair using keyBy method
  val pairRdd = words.keyBy(word => word.toLowerCase.toSeq.head.toString)
  pairRdd.foreach(println)

  //mapping over values
  pairRdd.mapValues(word => word.toUpperCase).collect().foreach(println)

  //Extracting keys and values
  pairRdd.keys.foreach(println)
  pairRdd.values.foreach(println)

  //finding particular key using lookup method
  pairRdd.lookup("s").foreach(println)

  //aggregation
  val chars = words.flatMap(word => word.toLowerCase.toSeq)
  val keyValueChars = chars.map(letter => (letter, 1))

  def maxFunc(left: Int, right: Int) = math.max(left, right)
  def addFunc(left: Int, right: Int) = left +  right
  val nums = spark.sparkContext.parallelize(1 to 30, 5)

  nums.foreach(println)

  //finding number of elements in particular key using countByKey
  val timeout = 1000L
  val confidence = 0.95

  println("countByKey result: " + keyValueChars.countByKey())
}