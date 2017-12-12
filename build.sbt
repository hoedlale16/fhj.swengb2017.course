import sbt._
import Dependencies._
import BuildConstants._


lazy val commonSettings: Seq[Def.SettingsDefinition] = Seq(
  organization := org,
  scalaVersion := scalaVer,
  version := buildVer,
  libraryDependencies += scalaTest,
  fork := true
)

// ------------------------------------------------------
// common
lazy val common = (project in file("common/")).
  settings(commonSettings: _*).
  settings(
    name := "common",
  )


// ------------------------------------------------------
// labs

lazy val labs = (project in file("labs/")).
  settings(commonSettings: _*).
  settings(
    name := "labs",
  ).dependsOn(common)

// END labs ----------------------------------------------


// ------------------------------------------------------
// main project
lazy val course = (project in file(".")).
  settings(commonSettings: _*).
  settings(name := "course").aggregate(common,labs)
