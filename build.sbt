import play.sbt.PlayImport.caffeine

name := """Work Accident Database"""

organization := "il.org.kavlaoved"

maintainer := "michael@codeworth.io"

version := "1.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.7"

// Targeting JDK11, which is the current LTS
javacOptions ++= Seq("-source", "11", "-target", "11")

libraryDependencies ++= Seq(
  caffeine,
  ws,
  guice,
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
  "com.typesafe.play" %% "play-mailer" % "8.0.0",
  "com.typesafe.play" %% "play-mailer-guice" % "8.0.0",
  "be.objectify" %% "deadbolt-scala" % "2.7.1",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.postgresql" % "postgresql" % "42.2.11",
  "com.github.jferard"% "fastods"%"0.7.3",
  "org.webjars" % "jquery" % "3.2.1",
  "org.webjars" % "jquery-ui" % "1.12.1",
  "org.webjars" % "tether" % "1.4.0",
  "org.webjars" % "sweetalert" % "2.1.0",
  "org.webjars" % "bootstrap" % "5.1.3",
  "org.webjars.bower" % "fontawesome" % "4.7.0",
  "org.webjars" % "d3js" % "5.16.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test,
  "org.seleniumhq.selenium" % "selenium-java" % "2.35.0" % Test,
//  "org.scalamock" %% "scalamock" % "4.0.0" % Test,
)


import org.irundaia.sbt.sass._

SassKeys.cssStyle := Minified

SassKeys.generateSourceMaps := true

// TODO add sections and table helpers
// TwirlKeys.templateImports ++= Seq( "views.Sections", "views.TableHelper")
TwirlKeys.templateImports ++= Seq("views.Helpers")

pipelineStages := Seq(digest, gzip)

// Disable documentation creation
sources in (Compile, doc) := Seq.empty
publishArtifact in (Compile, packageDoc) := false

