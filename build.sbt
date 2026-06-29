lazy val root = project.in(file("."))
  .enablePlugins(ScalaNativePlugin)
  .settings(
    name := "uname-scala",
    scalaVersion := "3.8.4"
  )
