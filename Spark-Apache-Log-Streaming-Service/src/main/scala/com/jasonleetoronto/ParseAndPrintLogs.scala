package com.jasonleetoronto
import java.util.regex.Matcher
import com.jasonleetoronto.utils.LogUtils.apacheLogPattern
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.ReceiverInputDStream

object ParseAndPrintLogs extends SparkLogStream {

  override def appName(): String = "Parse-Apache-Logs"
  override def sparkMaster(): String = "local[*]"
  override def rddBatchTimeFrame(): Duration = Seconds(1)

  override def run(ssc: StreamingContext): Unit = {
    /* Create a tcp socket text input stream for the Apache Server Logs */
    val lines = createSocketStream(ssc)

    parseApacheLogsAndPrint(lines)

    ssc.checkpoint("/home/Jason/Documents/Scala/Spark-Dev/Checkpoint")
    ssc.start()
    ssc.awaitTermination()
  }

  /* Business Use Case: Transaction Data Tags Parsing */
  /* Extract the request field by parsing the logs using Java Regex Pattern */
  def parseApacheLogsAndPrint(lines: ReceiverInputDStream[String]): Unit = {
    val pattern = apacheLogPattern()
    val requests = lines.map(x => {val matcher: Matcher = pattern.matcher(x); if (matcher.matches()) matcher.group(5)}) // Requests (Command-URL-Protocol) DStream
    val urls = requests.map(x => {val arr = x.toString.split(" "); if (arr.size==3) arr(1) else "[error]"}) // URLs DStream

    /* Create a Count URL Aggregate over 5-minute window sliding over every second */
    val urlCounts = urls.map(x => (x,1)) // Map URL Key Value Pair
      .reduceByKeyAndWindow(_ + _, _ - _, Seconds(300), Seconds(1)) // And Reduce by key

    /* Sort in a descending order and print the top 10 urls */
    val sortedResults = urlCounts.transform(rdd => rdd.sortBy(x => x._2, false))
    sortedResults.print(10)
  }
}


//case class => converted to column names
