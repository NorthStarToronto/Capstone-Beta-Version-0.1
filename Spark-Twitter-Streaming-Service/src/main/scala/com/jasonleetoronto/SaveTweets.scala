package com.jasonleetoronto

import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}

object SaveTweets extends SparkTwitterStream {

  override def appName(): String = "save-tweets"
  override def sparkMaster(): String = "local[*]"
  override def rddBatchTimeFrame(): Duration = Seconds(1)

  override def run(ssc: StreamingContext) {
    import org.apache.spark.streaming.twitter.TwitterUtils

    val tweets = TwitterUtils.createStream(ssc, None) // Create an input stream
    saveToLocal(tweets) // Create an transformation stream

    ssc.checkpoint("/home/Jason/Documents/Scala/Spark-Dev/Checkpoint")
    ssc.start()
    ssc.awaitTermination()
  }

  import org.apache.spark.streaming.dstream.DStream
  import twitter4j.Status

  /* Save First 1000 Tweets */
  def saveToLocal(tweets: DStream[Status]): Unit = {
    val statuses = tweets.map(status => status.getText()) // A new DStream
    statuses.print()
    var totalTweets: Long = 0
    statuses.foreachRDD((rdd, time) => {
      // Combine each partition's results into a single RDD
      val repartitionedRDD = rdd.repartition(1).cache() // Optimization for multiple actions
      repartitionedRDD.saveAsTextFile("./src/main/resources/tweets/" + "Tweets_" + time.milliseconds.toString)
      totalTweets +=1
      if (totalTweets > 1000) {
        System.exit(0)
      }
    })
  }
//
//  workflow sequential x
//  restore job on master node failure
//
//  disributed system

}
