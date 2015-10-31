// Project name (artifact name in Maven)
name := "(tune!)"

// orgnization name (e.g., the package name of the project)
organization := "com.felixfeatures.gui"

version := "2.0"

// project description
description := "project"

// Enables publishing to maven repo
publishMavenStyle := true

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

javaSource in Compile := baseDirectory.value / "src"

mainClass in Compile := Some("com.felixfeatures.gui.AppGUI")

resourceDirectory in Compile := baseDirectory.value / "src/res"

// library dependencies. (orginization name) % (project name) % (version)
libraryDependencies ++= Seq(
"com.googlecode.json-simple" % "json-simple" % "1.1.1",
"javazoom" % "jlayer" % "1.0.1"
)
