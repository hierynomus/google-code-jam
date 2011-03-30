import sbt.{DefaultProject, ProjectInfo}

class GoogleCodeJam(info: ProjectInfo) extends DefaultProject(info) with IdeaProject {
  val junit = "junit" % "junit" % "4.8.2" % "test->default"
}
