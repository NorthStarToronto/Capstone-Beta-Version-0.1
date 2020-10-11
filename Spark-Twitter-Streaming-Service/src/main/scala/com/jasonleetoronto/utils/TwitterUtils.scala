package com.jasonleetoronto.utils

object TwitterUtils {
  /* Set the Root Logging Level to ERROR only */
  def setupLogging(): Unit = {
    import org.apache.log4j.{Level, Logger}
    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)
  }

  /* Authenticate Twitter Streaming API credentials */
  def setupTwitterInputSource(): Unit = {
    import java.io.File
    import com.typesafe.config.ConfigFactory

    /* Approach 1: Use java.io.File */
    val twitterConfig = ConfigFactory.parseFile(new File("./src/main/resources/twitter.conf"))
    val consumerKey = twitterConfig.getString("twitter-api.consumerKey")
    val consumerSecret = twitterConfig.getString("twitter-api.consumerSecret")
    val accessToken = twitterConfig.getString("twitter-api.accessToken")
    val accessTokenSecret = twitterConfig.getString("twitter-api.accessTokenSecret")

    System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
    System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
    System.setProperty("twitter4j.oauth.accessToken", accessToken)
    System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)

    /* Approach 2: Use Scala.io.Source */
//    for (line <- Source.fromFile("/home/Jason/Documents/Scala/Spark-Streaming-Demo/src/main/resources/twitter.txt").getLines()) {
//      val fields = line.split(" ")
//      System.setProperty("twitter4j.oauth." + fields(0), fields(1))
//    }
  }
}
