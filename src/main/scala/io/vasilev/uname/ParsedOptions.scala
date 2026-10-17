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
    val parsed = ParsedOptions(
      kernelName = enabled(kernelName),
      nodename = enabled(nodename),
      kernelRelease = enabled(kernelRelease),
      kernelVersion = enabled(kernelVersion),
      machine = enabled(machine)
    )

    if allDisabled(parsed) then Default else parsed
  end fromOptions

  private def allDisabled(opts: ParsedOptions): Boolean =
    opts.productIterator.collect { case true => true }.isEmpty

  private val Default: ParsedOptions = ParsedOptions(
    kernelName = true,
    nodename = false,
    kernelRelease = false,
    kernelVersion = false,
    machine = false
  )
