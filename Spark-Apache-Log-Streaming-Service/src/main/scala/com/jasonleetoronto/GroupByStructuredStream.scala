package com.jasonleetoronto

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._
import org.apache.spark.sql.functions._
import java.util.regex.Pattern
import java.util.regex.Matcher
import java.text.SimpleDateFormat
import java.util.Locale
import org.apache.spark.sql.streaming.StreamingQuery

object GroupByStructuredStream extends StructuredLogStream {

  override def appName: String = "Group-By-Structured-Stream"
  override def sparkMaster: String = "local[*]"
  override def sparkWarehouse: String = "/home/Jason/Documents/Scala/Spark-Dev/Spark-Warehouse"
  override def sparkCheckpoint: String = "/home/Jason/Documents/Scala/Spark-Dev/Checkpoint"
  override def logs: String = "src/main/resources/logs"

  override def run(spark: SparkSession, rawData: DataFrame): Unit = {
    val query = groupByDataSet(spark, rawData)
    query.awaitTermination()
    spark.stop()
  }

  import com.jasonleetoronto.utils.LogUtils._

  def groupByDataSet(spark: SparkSession, rawData: DataFrame): StreamingQuery = {
    import spark.implicits._
    val structuredData = rawData.flatMap(parseLog).select("status", "dateTime")
    val windowed = structuredData.groupBy($"status", window($"dateTime", "1 hour")).count().orderBy("window")
    val query = windowed.writeStream.outputMode("complete").format("console").start()
    query
  }
}

// Case class defining structured data for a line of Apache access log data
case class LogEntry(ip: String, client: String, user: String, dateTime: String, request: String, status: String, bytes: String, referer: String, agent: String)