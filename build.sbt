import sbt.Keys._
import sbt._
import sbtrelease.Version

name := "closestRailStation"

organization in ThisBuild := "com.hervito"
scalaVersion in ThisBuild := "2.12.6"

resolvers += Resolver.sonatypeRepo("public")
releaseNextVersion := { ver => Version(ver).map(_.bumpMinor.string).getOrElse("Error") }
assemblyJarName in assembly := "hello.jar"
artifactName := {(_, _, _) => "hello.jar"}

val awsLambdaScalaVersion = "0.1.1"

libraryDependencies ++= Seq(
  "io.github.mkotsur" %% "aws-lambda-scala" % awsLambdaScalaVersion
)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-Xfatal-warnings")
