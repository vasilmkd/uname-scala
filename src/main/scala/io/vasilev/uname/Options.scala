package io.vasilev.uname

import flagged.{Parser, Versioned, cmd, opt, version}

@cmd(name = "uname", help = "Print system information")
@version(dynamic = true)
final case class Options(
  @opt(help = "Print all information, in the following order", short = 'a') all: Boolean = false,
  @opt(help = "Print the kernel name", short = 's') kernelName: Boolean = false,
  @opt(help = "Print the network node hostname", short = 'n') nodename: Boolean = false,
  @opt(help = "Print the kernel release", short = 'r') kernelRelease: Boolean = false,
  @opt(help = "Print the kernel version", short = 'v') kernelVersion: Boolean = false,
  @opt(help = "Print the machine hardware name", short = 'm') machine: Boolean = false
) derives Parser.Command

object Options:
  private given Versioned[Options] = Versioned.of(BuildInfo.version)
