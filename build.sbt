name := "scjs"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "com.chuusai" % "shapeless_2.11" % "2.3.0"
libraryDependencies += "org.scala-js" % "scalajs-library_2.11" % "0.6.8"

//Comment this out and execute "sbt run" command to check that scala code works as expected
enablePlugins(ScalaJSPlugin)

scalaJSUseRhino in Global := false