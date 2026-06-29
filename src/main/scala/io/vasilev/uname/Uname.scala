package io.vasilev.uname

import scala.scalanative.posix.stdio.printf
import scala.scalanative.posix.sys.utsname.{uname, utsname}
import scala.scalanative.posix.sys.utsnameOps.utsnamePtrOps
import scala.scalanative.unsafe.{CQuote, stackalloc}

object Uname:
  def main(args: Array[String]): Unit =
    val u = stackalloc[utsname]()

    if uname(u) != 0 then
      sys.error("Failed to obtain uname data from the system")

    printf(c"%s %s %s %s %s\n", u.sysname, u.nodename, u.release, u.version, u.machine)
  end main
