package com.jasonleetoronto

import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}
import twitter4j.Status

object PopularHashtagsRanking extends SparkTwitterStream {

  override def appName(): String = "popular-hashtags-ranking"
  override def sparkMaster(): String = "local[*]"
  override def rddBatchTimeFrame(): Duration = Seconds(1)

  import org.apache.spark.streaming.twitter.TwitterUtils

  override def run(ssc: StreamingContext): Unit = {
    /* Input Stream */
    val tweets = TwitterUtils.createStream(ssc, None)
    popularHashtRanking(tweets)

    ssc.checkpoint("/home/Jason/Documents/Scala/Spark-Dev/Checkpoint")
    ssc.start()
    ssc.awaitTermination()
  }

  /* Real-Time Transaction Data Management, min/max/avg and rankings by Tags
     => Business Use Case => Tuple(Key, Frequency Count, $ Amount) => Reduce By Key => Sort => Ranking
   */
  def popularHashtRanking(tweets: DStream[Status]): Unit = {
    /* Transformation Streams of Map, FlatMap, Filter, Reduce By Key, and Sorting */
    // Status Text DStream - Map
    val statuses = tweets.map(status => status.getText)

    // Word DStream - FlatMap
    val tweetwords = statuses.flatMap(tweetText => tweetText.split(" "))

    // Hashtag DStream - Filter
    val hastags = tweetwords.filter(word => word.startsWith("#"))

    // Key-Value Pair Tuple DStream - Map
    val hashtagKeyValues = hastags.map(hashtag => (hashtag, 1))

    // Aggregate on 5 Minute Window - Reduce by Key
    // 1 Second Batch Interval, 5 Second Sliding Interval, and 4 Seconds Window Interval
    val hashtagCounts = hashtagKeyValues.reduceByKeyAndWindow((x, y) => x + y, (x, y) => x - y, Seconds(300), Seconds(1))

    // Sort the Results by the Counts
    val sortedResults = hashtagCounts.transform(rdd => rdd.sortBy(x => x._2, false))

    // Print the top 10
    sortedResults.print(10)
  }
}
