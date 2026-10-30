import scala.scalanative.build.{GC, LTO}

lazy val `uname-scala` = project.in(file("."))
  .enablePlugins(ScalaNativePlugin, BuildInfoPlugin)
  .settings(
    name := "uname-scala",
    version := "1.0.0",
    scalaVersion := "3.10.0",
    libraryDependencies ++= Seq(
      "io.github.bishabosha" %% "flagged" % "1.0.0",
      "org.scalameta" %% "munit-scalacheck" % "1.3.1" % Test
    ),
    Compile / nativeConfig ~= { c =>
      val base = c.withGC(GC.none).withLTO(LTO.full)
      val isLinux = System.getProperty("os.name").toLowerCase.contains("linux")
      if (isLinux) {
        base
          .withCompileOptions(base.compileOptions :+ "-fPIE")
          .withLinkingOptions(base.linkingOptions :+ "-pie")
      } else {
        base
      }
    },
    Test / nativeConfig ~= { _.withGC(GC.immix).withLTO(LTO.none) },
    Test / logBuffered := false,
    buildInfoKeys := Seq[BuildInfoKey](version),
    buildInfoPackage := "io.vasilev.uname",
    testOptions += Tests.Argument(TestFrameworks.MUnit, "+l")
  )
