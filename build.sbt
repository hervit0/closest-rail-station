import sbt.Keys._
import sbt._
import sbtrelease.Version

name := "closestRailStation"

organization in ThisBuild := "com.hervito"
scalaVersion in ThisBuild := "2.12.6"
val scanamoVersion = "1.0.0-M7"

resolvers += Resolver.sonatypeRepo("public")
releaseNextVersion := { ver =>
  Version(ver).map(_.bumpMinor.string).getOrElse("Error")
}
assemblyJarName in assembly := "hello.jar"
artifactName := { (_, _, _) =>
  "hello.jar"
}

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-events" % "2.2.1",
  "com.amazonaws" % "aws-lambda-java-core" % "1.2.0",

  "io.github.mkotsur" %% "aws-lambda-scala" % "0.1.1",

  "com.nrinaudo" %% "kantan.csv" % "0.5.0",
  "com.nrinaudo" %% "kantan.csv-generic" % "0.5.0",

  "com.gu" %% "scanamo" % scanamoVersion,
  "com.gu" %% "scanamo-testkit" % "1.0.0-M8",

  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.apache.logging.log4j" % "log4j-core" % "2.11.1",
  "ch.qos.logback" % "logback-classic" % "1.2.3",

  "org.scalatest" % "scalatest_2.12" % "3.0.5" % Test,
  "org.mockito" % "mockito-all" % "1.10.19" % Test
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")

//http://www.wartremover.org/doc/install-setup.html
//wartremoverErrors ++= Warts.unsafe
wartremoverErrors in (Compile, compile) ++= Warts.allBut(
  Wart.Option2Iterable,
  Wart.DefaultArguments,
  Wart.EitherProjectionPartial,
  Wart.Throw,
  Wart.Overloading,
  Wart.FinalCaseClass,
  Wart.Nothing,
  Wart.Product,
  Wart.Equals,
  Wart.Serializable,
  Wart.NonUnitStatements,
  Wart.JavaSerializable,
  Wart.ImplicitParameter
)

addCompilerPlugin("org.wartremover" %% "wartremover" % "2.4.1")
//scalacOptions += "-P:wartremover:traverser:org.wartremover.warts.Unsafe"
