package io.vasilev.uname

import munit.ScalaCheckSuite
import org.scalacheck.Gen
import org.scalacheck.Prop.forAll

import scala.scalanative.meta.LinktimeInfo
import scala.sys.process.stringSeqToProcess

class UnameIntegrationSuite extends ScalaCheckSuite:
  private def flagsGen: Gen[List[String]] =
    val opts =
      if LinktimeInfo.isLinux then
        Gen.oneOf(
          "-s", "--kernel-name",
          "-n", "--nodename",
          "-r", "--kernel-release",
          "-v", "--kernel-version",
          "-m", "--machine"
        )
      else
        Gen.oneOf("-s", "-n", "-r", "-v", "-m")
    Gen.listOf(opts)

  property("any combination of valid flags matches system uname") = forAll(flagsGen): flags =>
    val expected = runUname(flags)
    val obtained = Uname.command(flags).trim
    assertEquals(obtained, expected, clues(flags))

  test("running the -a flag produces a prefix of the platform's output"):
    assertAllIsPrefix("-a")

  test("running the --all flag produces a prefix of the platform's output"):
    assume(LinktimeInfo.isLinux, "Test runs only on Linux")
    assertAllIsPrefix("--all")

  private def assertAllIsPrefix(flag: String): Unit =
    val flags = List(flag)
    val expected = runUname(flags)
    val obtained = Uname.command(flags)
    assertMatches(obtained):
      case o => expected.startsWith(o)

  private def runUname(flags: List[String]): String = ("uname" :: flags).!!.trim
