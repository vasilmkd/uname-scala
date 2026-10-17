package io.vasilev.uname

import munit.ScalaCheckSuite
import org.scalacheck.Gen
import org.scalacheck.Prop.forAll

import scala.sys.process.stringSeqToProcess

class UnameIntegrationSuite extends ScalaCheckSuite:
  private val flagsGen: Gen[List[String]] = Gen.listOf(
    Gen.oneOf(
      "-s", "--kernel-name",
      "-n", "--nodename",
      "-r", "--kernel-release",
      "-v", "--kernel-version",
      "-m", "--machine"
    )
  )

  property("any combination of valid flags matches system uname") = forAll(flagsGen): flags =>
    val expected = runUname(flags)
    val obtained = Uname.command(flags).trim
    assertEquals(obtained, expected, clues(flags))

  Seq("-a", "--all").foreach: flag =>
    test(s"running the $flag flag produces a prefix of the platform's output"):
      val flags = List(flag)
      val expected = runUname(flags)
      val obtained = Uname.command(flags)
      assertMatches(obtained):
        case o => expected.startsWith(o)

  private def runUname(flags: List[String]): String = ("uname" :: flags).!!.trim
