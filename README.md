# uname-scala

The most basic implementation of the `uname` (`unix name`) Unix utility program in Scala Native.

Equivalent to:
```shell
uname -a
```

Use the following command to assemble the production optimized binary:
```shell
sbt nativeLinkReleaseFull
```

The binary is then available in [out](target/out).

Manually copy the binary to a location available on the PATH (e.g. `~/.local/bin`).
