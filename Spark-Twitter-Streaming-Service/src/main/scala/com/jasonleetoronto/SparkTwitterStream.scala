package com.jasonleetoronto

import com.jasonleetoronto.utils.TwitterUtils._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}

trait SparkTwitterStream {

  def main(args: Array[String])={
    setupLogging()
    setupTwitterInputSource()

    val sparkConfiguration = new SparkConf()
      .setAppName(appName)
      .setMaster(sys.env.get("spark.master").getOrElse(sparkMaster))

    val sc = new SparkContext(sparkConfiguration)
    val ssc = new StreamingContext(sc, rddBatchTimeFrame)
    run(ssc)
  }

  def run(ssc: StreamingContext)

  def appName(): String
  def sparkMaster(): String = "local[*]"
  def rddBatchTimeFrame(): Duration = Seconds(1)

}
