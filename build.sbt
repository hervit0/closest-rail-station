import sbt.Keys._
import sbt._
import sbtrelease.Version

name := "closestRailStation"

organization in ThisBuild := "com.hervito"
scalaVersion in ThisBuild := "2.12.6"

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
  "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")

//http://www.wartremover.org/doc/install-setup.html
//wartremoverErrors ++= Warts.unsafe
wartremoverErrors in(Compile, compile) ++= Warts.allBut(
  Wart.Option2Iterable,
  Wart.DefaultArguments,
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
