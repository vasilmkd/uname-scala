package io.vasilev.uname

import flagged.Flagged

import java.nio.charset.StandardCharsets
import scala.scalanative.posix.sys.utsname
import scala.scalanative.posix.sys.utsname.{uname, utsname}
import scala.scalanative.posix.sys.utsnameOps.utsnamePtrOps
import scala.scalanative.unsafe.{CArray, Ptr, fromCString, stackalloc}

object Uname:
  private def assembleData(opts: ParsedOptions, u: Ptr[utsname]): (format: String, vargs: IArray[String]) =
    def str(arr: CArray[Byte, utsname._256]): String = fromCString(arr.at(0), StandardCharsets.UTF_8)

    val vargs = IArray(
      (opts.kernelName, u.sysname),
      (opts.nodename, u.nodename),
      (opts.kernelRelease, u.release),
      (opts.kernelVersion, u.version),
      (opts.machine, u.machine)
    ).collect { case (true, carr) => str(carr) }

    val count = vargs.size

    val format = IArray.fill(count)("%s").mkString(start = "", sep = " ", end = "\n")
    (format, vargs)
  end assembleData

  def main(args: Array[String]): Unit =
    val opts = Flagged.parseOrExit[Options](args.toIndexedSeq)
    val parsed = ParsedOptions.fromOptions(opts)

    val u = stackalloc[utsname]()

    if uname(u) != 0 then
      sys.error("Failed to obtain uname data from the system")

    val (format, vargs) = assembleData(parsed, u)
    printf(format, vargs*)
  end main
