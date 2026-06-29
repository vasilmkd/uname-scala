import scala.scalanative.build.{GC, LTO}

lazy val root = project.in(file("."))
  .enablePlugins(ScalaNativePlugin)
  .settings(
    name := "uname-scala",
    scalaVersion := "3.8.4",
    nativeConfig ~= { c =>
      c.withGC(GC.none)
        .withLTO(LTO.full)
    }
  )
