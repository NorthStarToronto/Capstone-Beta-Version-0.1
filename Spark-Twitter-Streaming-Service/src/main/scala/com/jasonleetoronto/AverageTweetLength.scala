package com.jasonleetoronto
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}

object AverageTweetLength extends SparkTwitterStream {
  import org.slf4j.{Logger, LoggerFactory}

  private val logger: Logger = LoggerFactory.getLogger(AverageTweetLength.getClass)

  override def appName(): String = "average-tweet-length"
  override def sparkMaster(): String = "local[*]"
  override def rddBatchTimeFrame(): Duration = Seconds(1)

  override def run(ssc: StreamingContext): Unit = {
    val tweets = TwitterUtils.createStream(ssc, None)

    averageTweetLength(tweets)

    ssc.checkpoint("/home/Jason/Documents/Scala/Spark-Dev/Checkpoint")
    ssc.start()
    ssc.awaitTermination()
  }
  import org.apache.spark.streaming.dstream.DStream
  import twitter4j.Status
  import java.util.concurrent.atomic.AtomicLong

  def averageTweetLength(tweets: DStream[Status]) = {
    // "Transformation DStream1" Map the Text of Each Status RDD
    val statuses = tweets.map(status => status.getText())

    // "Transformation DStream2" Map the length of Each Status Text RDD
    val lengths = statuses.map(status => status.length())

    // "Java Atomic Long Class" Thread Safety for the Distributed Spark Processing
    var totalTweets = new AtomicLong(0) // Running total of number of tweets
    var totalChars = new AtomicLong(0) // Running total of number of characters

    lengths.foreachRDD((rdd, time) => {

      var count = rdd.count()
      if (count > 0) {
        totalTweets.getAndAdd(count)
        totalChars.getAndAdd(rdd.reduce((x, y) => x + y))
        logger.info("Total tweets: " + totalTweets.get() + "\n" +
                    "Total characters: " + totalChars.get() +"\n" +
                    "Average Tweet Length: " + totalChars.get()/totalTweets.get())
      }
    })
  }
}
