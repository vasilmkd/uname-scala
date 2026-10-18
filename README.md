# uname-scala

The most basic implementation of the `uname` (`unix name`) Unix utility program in Scala Native.

## Installation

Pre-compiled standalone binaries for each supported platform (Linux on x86_64 and aarch64, and macOS on x86_64 and arm64) along with SHA-256 checksums are available on the [Releases](https://github.com/vasilmkd/uname-scala/releases) page.

Download the appropriate binary and its `.sha256` file, verify its integrity, make it executable, and move it into your `PATH`:
```shell
# Verify checksum
sha256sum -c uname-scala-<os>-<arch>.sha256   # On Linux
# or: shasum -a 256 -c uname-scala-<os>-<arch>.sha256  # On macOS

chmod +x uname-scala-<os>-<arch>
mv uname-scala-<os>-<arch> ~/.local/bin/uname
```

## Building from sources

Check the environment setup [guide](https://scala-native.org/en/latest/user/setup.html) from Scala Native.

Use the following command to assemble the production optimized binary:
```shell
sbt nativeLinkReleaseFull
```

The binary is then available in [out](target/out).

Manually copy the binary to a location available on the PATH (e.g. `~/.local/bin`).
