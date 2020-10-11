package com.jasonleetoronto

import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}
import twitter4j.Status

object PrintTweets extends SparkTwitterStream {

  /* Customize Spark Session & Streaming Context Configurations */
  override def appName(): String = "print-tweets"
  override def sparkMaster(): String = "local[*]"
  override def rddBatchTimeFrame(): Duration = Seconds(1)

  /* Spark Streaming I/O*/
  override def run(ssc: StreamingContext): Unit = {
    val tweets = TwitterUtils.createStream(ssc, None)
    printTweets(tweets)

    ssc.start()
    ssc.awaitTermination()
  }

  /* Spark Streaming Transformations */
  def printTweets(tweets: DStream[Status]) ={
    val statuses = tweets.map(status => status.getText) // A new DStream
    statuses.print()
  }
}
