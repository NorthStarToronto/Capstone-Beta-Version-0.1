package com.jasonleetoronto

import org.apache.spark.sql.{DataFrame, SparkSession}
import com.jasonleetoronto.utils.LogUtils._

trait StructuredLogStream {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName(appName)
      .master(sparkMaster)
      .config("spark.sql.warehouse.dir", sparkWarehouse)
      .config("spark.sql.streaming.checkpointLocation", sparkCheckpoint)
      .getOrCreate()

    setupLogging()

    /* Create a Text File Stream into a DataFrame of Row Objects */
    val rawData = spark.readStream.text(logs) // Unbounded DataFrame

    import spark.implicits._
    run(spark, rawData)
  }

  def run(spark: SparkSession, rawData: DataFrame)

  def appName: String

  def sparkMaster: String = "local[*]"

  def sparkWarehouse: String = "/home/Jason/Documents/Scala/Spark-Dev/Spark-Warehouse"

  def sparkCheckpoint: String = "/home/Jason/Documents/Scala/Spark-Dev/Checkpoint"

  def logs: String = "src/main/resources/logs"
}
