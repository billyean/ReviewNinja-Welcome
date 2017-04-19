name := "Kafka-Programming"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  // The excludes of jms, jmxtools and jmxri are required as per https://issues.apache.org/jira/browse/KAFKA-974.
  // The exclude of slf4j-simple is because it overlaps with our use of logback with slf4j facade;  without the exclude
  // we get slf4j warnings and logback's configuration is not picked up.
  "org.apache.kafka" %% "kafka" % "0.10.2.0",
  "org.apache.kafka" % "kafka-clients" % "0.10.2.0",
  "org.apache.kafka" % "kafka-streams" % "0.10.2.0"
)
        