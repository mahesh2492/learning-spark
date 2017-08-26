package rdd

import org.apache.spark.{SparkConf, SparkContext}

object Factorial extends App {
  /*val conf = new SparkConf().setMaster("local").setAppName("Factorial")
  val sc = new SparkContext(conf)
*/
 def factorial(num:BigInt):BigInt = {
   def factImp(num:BigInt,fact:BigInt):BigInt = {
     if (num == 0) fact
     else
       factImp(num - 1,num * fact)
   }
   factImp(num,1)
 }
  println(s"Factorial Without Spark" + factorial(6))
}
