package dataframe

import dataframe.Constants._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object AggregationOperation extends App {

  //let's minimize the partition
  val modifiedDf: DataFrame = df.coalesce(5).toDF()
  modifiedDf.cache()

  modifiedDf.show(false)

  //counting the number of rows
  println(s"number of rows in dataframe: ${modifiedDf.count()}")

  //counting number of rows for particular column
  modifiedDf.select(count(col("StockCode"))).show(1)

  //number of unique rows. It als ignores null rows.
  modifiedDf.select(countDistinct(col("StockCode"))).show(1)

  //Sometimes we want approximate distinct count
  modifiedDf.select(approx_count_distinct(col("StockCode"), 0.1)).show(1)

  //To extract first and last value fom a DataFrame
  modifiedDf.select(first(col("StockCode")), last(col("StockCode"))).show(1)

  //To extract the min and max value fom a DataFrame
  modifiedDf.select(max(col("Quantity")), min(col("Quantity"))).show(1)

  //To sum all values in a row
  modifiedDf.select(sum(col("Quantity"))).show(1)

  //To sum only distinct values
  modifiedDf.select(sumDistinct(col("Quantity"))).show(1)

  //To find the average
  modifiedDf.select(avg(col("Quantity"))).show(1)

  // To find average and mean
  modifiedDf.select(
    count("Quantity").alias("total_transactions"),
    sum("Quantity").alias("total_purchase"),
    avg("Quantity").alias("avg_purchase"),
    expr("mean(Quantity)").alias("mean_purchase"))
    .selectExpr(
      "total_purchase/total_transactions",
      "avg_purchase",
      "mean_purchase"
    ).show(1)

  //Finding standard deviation and variance
  modifiedDf.select(stddev_pop(col("Quantity")),
    stddev_samp(col("Quantity")),
    var_pop(col("Quantity")),
    var_samp(col("Quantity"))
  ).show(1)

  //Finding asymmetry around mean. Skewness and kurtosis

  modifiedDf.select(skewness(col("Quantity")), kurtosis(col("Quantity"))).show(1)

  // finding correlation and covariance between two columns
  modifiedDf.select(corr("InvoiceNo", "Quantity"),
    covar_pop("InvoiceNo", "Quantity"),
    covar_samp("InvoiceNo", "Quantity")
  ).show(1)

  //Aggregating to complex type
  modifiedDf.select(collect_list("InvoiceNo"), collect_set("Quantity")).show(1)
}
