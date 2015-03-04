name := """owl-links"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.34",
  "org.postgresql" % "postgresql" % "9.3-1103-jdbc41",
  "commons-validator" % "commons-validator" % "1.4.1",
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)
