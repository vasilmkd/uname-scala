import scala.scalanative.build.{GC, LTO}

lazy val root = project.in(file("."))
  .enablePlugins(ScalaNativePlugin)
  .settings(
    name := "uname-scala",
    scalaVersion := "3.9.0",
    libraryDependencies += "io.github.bishabosha" %% "flagged" % "1.0.0",
    nativeConfig ~= { _.withGC(GC.none).withLTO(LTO.full) }
  )
