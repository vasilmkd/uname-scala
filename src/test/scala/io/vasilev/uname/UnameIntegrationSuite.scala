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
          "-a", "--all",
          "-s", "--kernel-name",
          "-n", "--nodename",
          "-r", "--kernel-release",
          "-v", "--kernel-version",
          "-m", "--machine"
        )
      else
        Gen.oneOf("-a", "-s", "-n", "-r", "-v", "-m")
    Gen.listOf(opts)

  property("any combination of valid flags matches system uname") = forAll(flagsGen): flags =>
    val expected = runUname(flags)
    val obtained = Uname.command(flags).trim
    assertMatches(obtained):
      case o if allFlagActive(flags) => expected.startsWith(o)
      case o => o == expected

  private def allFlagActive(flags: List[String]): Boolean =
    Seq("-a", "--all").exists(flags.contains)

  private def runUname(flags: List[String]): String = ("uname" :: flags).!!.trim
