package com.jasonleetoronto

import java.util.regex.Matcher

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}

trait SparkLogStream {

  def main(args: Array[String]): Unit = {

    val sparkConfiguration = new SparkConf()
      .setMaster(sparkMaster)
      .setAppName(appName())

    val sc = new SparkContext(sparkConfiguration)
    val ssc = new StreamingContext(sc, rddBatchTimeFrame)

    run(ssc)
  }

  def run(ssc: StreamingContext)

  def appName(): String
  def sparkMaster(): String = "local[*]"
  def rddBatchTimeFrame(): Duration = Seconds(1)

  import com.jasonleetoronto.utils.LogUtils._
  def createSocketStream(ssc: StreamingContext, hostUrl: String = "127.0.0.1", port: Int = 9999): ReceiverInputDStream[String] = {
    /* Create a socket stream for Apache Web Server Logs */
    ssc.socketTextStream(hostUrl, port, StorageLevel.MEMORY_AND_DISK_SER)
  }
}
