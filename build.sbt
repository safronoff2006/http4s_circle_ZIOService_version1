
// The simplest possible sbt build file is just one line:

scalaVersion := "2.13.12"
// That is, to create a valid sbt build, all you've got to do is define the
// version of Scala you'd like your project to use.

// ============================================================================

// Lines like the above defining `scalaVersion` are called "settings". Settings
// are key/value pairs. In the case of `scalaVersion`, the key is "scalaVersion"
// and the value is "2.13.12"

// It's possible to define many kinds of settings, such as:

name := "phonebook"
organization := "safronoff2006"
version := "1.0"



libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0"

val http4sVersion = "0.23.26"

libraryDependencies ++= Seq(
 "dev.zio" %% "zio" % "2.0.21",
 "dev.zio" %% "zio-macros" % "2.0.21",

)

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-ember-client" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-dsl"          % http4sVersion,
)

libraryDependencies ++= Seq(

  "org.typelevel" %% "cats-effect" % "3.5.4",
  "org.http4s" %% "http4s-circe" % http4sVersion,
  // Optional for auto-derivation of JSON codecs
  "io.circe" %% "circe-generic" % "0.14.6",
  // Optional for string interpolation to JSON model
  "io.circe" %% "circe-literal" % "0.14.6",
  "io.circe" %% "circe-parser" % "0.14.6",
  "dev.zio" %% "zio-interop-cats" % "23.1.0.0",
)

scalacOptions += "-Ymacro-annotations"



