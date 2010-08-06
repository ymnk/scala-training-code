import sbt._

trait Defaults {
  this: DefaultProject =>
  
  lazy val junit = "junit" % "junit" % "4.6"  
  val scalaSwing = "org.scala-lang" % "scala-swing" % buildScalaVersions.value
}

class Project(info: ProjectInfo) extends DefaultProject(info) {

  lazy val common = project("common", "common", new DefaultProject(_) with Defaults)
  
  lazy val intro = projectBasedOnCommon("intro")
  lazy val firstClassFunctions = projectBasedOnCommon("first-class-functions")
  lazy val patternMatching = projectBasedOnCommon("pattern-matching")
  lazy val ooTraits = projectBasedOnCommon("oo-traits")
  lazy val higherOrderFunctions = projectBasedOnCommon("higher-order-functions")
  lazy val quiz = projectBasedOnCommon("quiz")

  def projectBasedOnCommon(nameAndFolder: String) = 
    project(nameAndFolder, nameAndFolder, new DefaultProject(_) with Defaults, common)
}
