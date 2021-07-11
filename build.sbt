
name := "Festival"

version := "0.1"

scalaVersion := "2.12.8"

scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies += "joda-time" % "joda-time" % "2.10.10"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"
libraryDependencies += "junit" % "junit" % "4.13.2" % "test"


