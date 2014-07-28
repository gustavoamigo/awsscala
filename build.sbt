name := """awsscala"""
organization := "com.taxis99"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

enablePlugins(GitVersioning)

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

scalaVersion := "2.11.6"

crossScalaVersions := Seq("2.10.5", "2.11.6")

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk-sqs" % "1.9.40",
  "com.amazonaws" % "aws-java-sdk-s3" % "1.9.40",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.mockito" % "mockito-all" % "1.9.5" % "test"
)

bintraySettings
