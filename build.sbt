import scala.scalanative.build.{GC, LTO}

lazy val `uname-scala` = project.in(file("."))
  .enablePlugins(ScalaNativePlugin, BuildInfoPlugin)
  .settings(
    name := "uname-scala",
    version := "0.1.0",
    scalaVersion := "3.9.0",
    libraryDependencies ++= Seq(
      "io.github.bishabosha" %% "flagged" % "1.0.0",
      "org.scalameta" %% "munit-scalacheck" % "1.3.1" % Test
    ),
    Compile / nativeConfig ~= { _.withGC(GC.none).withLTO(LTO.full) },
    Test / nativeConfig ~= { _.withGC(GC.immix).withLTO(LTO.none) },
    Test / logBuffered := false,
    buildInfoKeys := Seq[BuildInfoKey](version),
    buildInfoPackage := "io.vasilev.uname",
    testOptions += Tests.Argument(TestFrameworks.MUnit, "+l")
  )
