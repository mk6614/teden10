name := "TP Scala - teden 10."

version := "1.0"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
    "org.specs2" %% "specs2" % "2.4" % "test",
    "org.scala-lang" % "scala-swing" % "2.11.0-M7"
  )

org.scalastyle.sbt.ScalastylePlugin.Settings
