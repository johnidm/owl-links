name := """owl-links"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.5"


libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.34",
  "org.postgresql" % "postgresql" % "9.3-1103-jdbc41",
  "commons-validator" % "commons-validator" % "1.4.1",  
  "org.apache.commons" % "commons-email" % "1.3.3",   
  "org.jongo" % "jongo" % "1.1",
  "org.mongodb" % "mongo-java-driver" % "2.13.0",  
  "org.freemarker" % "freemarker" % "2.3.22",
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)



javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}