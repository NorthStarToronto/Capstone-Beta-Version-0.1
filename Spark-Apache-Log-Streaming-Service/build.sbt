name := "Spark-Apache-Log-Streaming-Service"

version := "0.1"

scalaVersion := "2.11.6"

scalaVersion := "2.11.6"
val sparkVersion = "2.0.1"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core"      % sparkVersion,
  "org.apache.spark" %% "spark-sql"       % sparkVersion,
  "org.apache.spark" %% "spark-hive"      % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % "2.0.1",
  "com.typesafe" % "config" % "1.4.0"
)