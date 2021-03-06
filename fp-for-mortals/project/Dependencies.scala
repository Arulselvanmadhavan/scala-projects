import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val scalaz = "org.scalaz" %% "scalaz-core" % "7.2.26"
  lazy val scalazIO = "org.scalaz" %% "scalaz-ioeffect" % "2.10.1"
  lazy val simulacrum = "com.github.mpilquist" %% "simulacrum" % "0.13.0"
  lazy val refined = "eu.timepit" %% "refined-scalaz" % "0.9.2"
}
