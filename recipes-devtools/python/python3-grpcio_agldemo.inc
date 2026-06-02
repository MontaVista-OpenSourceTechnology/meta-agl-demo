# For some reason the atomics presence test in the grpc build
# passes on RISC-V even though linking with libatomic is actually
# needed. There are some mentions on the web of some atomics
# support incorrectly being in the libc stub on RISC-V, so a
# fix could be tracking down if that is the case and if it has
# been fixed in newer libc. Otherwise, the compile test in grpc
# will need to be changed to some other function that is known to
# only be in libatomic on RISC-V.
#
# The next problem is that setting GRPC_PYTHON_LDFLAGS to any value
# ends up not adding linking with pthread due to the way the logic
# is set up, so we need to actually add it explicitly. The upstream
# recipe seems slightly broken in this respect.

GRPC_LDFLAGS ?= " -lpthread"
GRPC_LDFLAGS:append:riscv64 = " -latomic"
export GRPC_PYTHON_LDFLAGS = "${GRPC_LDFLAGS}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# GCC 13+ no longer transitively provides uint8_t/uint64_t; add missing
# <cstdint> include to the bundled abseil str_format header.
# GCC 14 makes -Wimplicit-function-declaration an error in C99+ mode; add
# missing <unistd.h> to the bundled zlib gzguts.h.
SRC_URI += "file://0001-absl-str-format-extension-add-missing-cstdint.patch \
            file://0002-zlib-gzguts-add-missing-unistd-include.patch \
            "
