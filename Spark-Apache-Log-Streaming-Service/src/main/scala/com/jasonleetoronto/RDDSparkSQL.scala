package com.jasonleetoronto

import java.util.regex.Matcher
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}

object RDDSparkSQL extends SparkLogStream {

  override def appName(): String = "Spark-Streaming-SQL"
  override def sparkMaster(): String = "local[*]"
  override def rddBatchTimeFrame(): Duration = Seconds(1)

  override def run(ssc: StreamingContext): Unit = {
    /* Create a tcp socket text input stream for the Apache Server Logs */
    val lines = createSocketStream(ssc)
    parseApacheLog(lines)


    ssc.checkpoint("/home/Jason/Documents/Scala/Spark-Dev/Checkpoint")
    ssc.start()
    ssc.awaitTermination()
  }

  import com.jasonleetoronto.utils.LogUtils._

  /* Convert Log-Texts DStream to Fields-Tuples DStream */
  // DStream[(String, Int, String)]
  def parseApacheLog(lines: ReceiverInputDStream[String]): Unit = {
    val pattern = apacheLogPattern()

    // Extract and Create the record/tuple (URL, status, user agent for each log line
    val requests = lines.map(x => {
      val matcher:Matcher = pattern.matcher(x)
      if (matcher.matches()) {
        val request = matcher.group(5)
        val requestFields = request.toString().split(" ")
        val url = util.Try(requestFields(1)) getOrElse "[error]"
        (url, matcher.group(6).toInt, matcher.group(9))
      } else {
        ("error", 0, "error")
      }
    })

    requests.foreachRDD((rdd, time) => {
      val spark = SparkSession
        .builder()
        .appName("Hello")
        .getOrCreate()

      import spark.implicits._
      val requestsDataFrame = rdd.map(w => Record(w._1, w._2, w._3)).toDF()
      requestsDataFrame.createOrReplaceTempView("requests")

      val wordCountsDataFrame =
        spark.sqlContext.sql("select agent, count(*) as total from requests group by agent")
      println(s"========= $time =========")
      wordCountsDataFrame.show()

    })
  }

  /* Create a DataFrame from DStream
     => Create a SQL Table "Quasi Cache Database" from the database
     => Execute SQL Query on the SQL Table for mini-OLAP insights
   */
//  def rddToDataFrame(requests: DStream[(String, Int, String)]): Unit = {
//    requests.foreachRDD((rdd, time) => {
//      val spark = SparkSession
//        .builder()
//        .appName(appName())
//        .getOrCreate()
//
//      import spark.implicits._
//      val requestsDataFrame = rdd.map(w => Record(w._1, w._2, w._3)).toDF()
//      requestsDataFrame.createOrReplaceTempView("requests")
//
//      val wordCountDataFrame = spark.sqlContext.sql("select agent, count(*) as total from requests group by agent")
//      println(s"++++++++++ $time ++++++++++")
//      wordCountDataFrame.show()
//    })
//  }
}

case class Record(url: String, status: Int, agent: String)