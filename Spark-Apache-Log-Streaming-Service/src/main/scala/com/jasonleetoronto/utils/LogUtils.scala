package com.jasonleetoronto.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.{Matcher, Pattern}
import org.apache.spark.sql.Row

object LogUtils {

  def setupLogging(): Unit = {
    import org.apache.log4j.{Level, Logger}
    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)
  }

  /* Retrieve a regex pattern for parsing Apache Acess Logs */
  def apacheLogPattern(): Pattern = {
    val ddd = "\\d{1,3}"
    val ip = s"($ddd\\.$ddd\\.$ddd\\.$ddd)?"
    val client = "(\\S+)"
    val user = "(\\S+)"
    val dateTime = "(\\[.+?\\])"
    val request = "\"(.*?)\""
    val status = "(\\d{3})"
    val bytes = "(\\S+)"
    val referer = "\"(.*?)\""
    val agent = "\"(.*?)\""
    val regex = s"$ip $client $user $dateTime $request $status $bytes $referer $agent"
    Pattern.compile(regex)
  }

  // Function to convert Apache log times to what Spark/SQL expects
  def parseDateField(field: String): Option[String] = {

    val dateMatcher = datePattern.matcher(field)
    if (dateMatcher.find) {
      val dateString = dateMatcher.group(1)
      val dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH)
      val date = (dateFormat.parse(dateString))
      val timestamp = new java.sql.Timestamp(date.getTime());
      return Option(timestamp.toString())
    } else {
      None
    }
  }

  val logPattern = apacheLogPattern()
  val datePattern = Pattern.compile("\\[(.*?) .+]")

  // Convert a raw line of Apache access log data to a structured LogEntry object (or None if line is corrupt)
  def parseLog(x:Row) : Option[LogEntry] = {

    val matcher:Matcher = logPattern.matcher(x.getString(0));
    if (matcher.matches()) {
      val timeString = matcher.group(4)
      return Some(LogEntry(
        matcher.group(1),
        matcher.group(2),
        matcher.group(3),
        parseDateField(matcher.group(4)).getOrElse(""),
        matcher.group(5),
        matcher.group(6),
        matcher.group(7),
        matcher.group(8),
        matcher.group(9)
      ))
    } else {
      return None
    }
  }
}


case class LogEntry(ip: String, client: String, user: String, dateTime: String, request: String, status: String, bytes: String, referer: String, agent: String)