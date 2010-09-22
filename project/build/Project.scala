import sbt._
import de.element34.sbteclipsify._

trait Defaults {
  this: DefaultProject =>
  
  lazy val junit = "junit" % "junit" % "4.6"
  val junitInterface = "com.novocode" % "junit-interface" % "0.4" % "test"
  val scalaSwing = "org.scala-lang" % "scala-swing" % buildScalaVersions.value
}

class Project(info: ProjectInfo) extends DefaultProject(info) with Eclipsify{

  lazy val common = project("common", "common", new DefaultProject(_) with Defaults)
  
  lazy val intro = projectBasedOnCommon("intro")
  lazy val firstClassFunctions = projectBasedOnCommon("first-class-functions")
  lazy val patternMatching = projectBasedOnCommon("pattern-matching")
  lazy val ooTraits = projectBasedOnCommon("oo-traits")
  lazy val higherOrderFunctions = projectBasedOnCommon("higher-order-functions")
  lazy val quiz = projectBasedOnCommon("quiz")
  lazy val tmorissList = projectBasedOnCommon("tmoriss-list")
  lazy val tmorissOption = projectBasedOnCommon("tmoriss-option")

  def projectBasedOnCommon(nameAndFolder: String) = 
    project(nameAndFolder, nameAndFolder, new DefaultProject(_) with Defaults, common)
}
