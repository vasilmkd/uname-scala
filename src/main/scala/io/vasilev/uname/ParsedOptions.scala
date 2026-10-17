package io.vasilev.uname

final case class ParsedOptions(
  kernelName: Boolean,
  nodename: Boolean,
  kernelRelease: Boolean,
  kernelVersion: Boolean,
  machine: Boolean
)

object ParsedOptions:
  def fromOptions(opts: Options): ParsedOptions =
    val Options(all, kernelName, nodename, kernelRelease, kernelVersion, machine) = opts
    inline def enabled(o: Boolean) = all || o
    ParsedOptions(
      kernelName = enabled(kernelName),
      nodename = enabled(nodename),
      kernelRelease = enabled(kernelRelease),
      kernelVersion = enabled(kernelVersion),
      machine = enabled(machine)
    )
