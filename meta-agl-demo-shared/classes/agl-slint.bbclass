# Set Slint dependency
DEPENDS = " \
    fontconfig libxkbcommon libdrm virtual/egl virtual/libgbm virtual/libgles2 \
    ca-certificates-native curl-native ninja-native \
    seatd udev libinput \
    ${@bb.utils.contains('AGL_FEATURES', 'agl-drm-lease', 'drm-lease-manager', '', d)} \
    "

inherit pkgconfig features_check

REQUIRED_DISTRO_FEATURES:append =  " opengl"

# Set Slint options
SLINT_CARGO_BUILD_FLAGS ??= "--features slint/backend-linuxkms,slint/renderer-skia"
SLINT_CARGO_BUILD_FLAGS_DRMLEASE ??= "--features slint/backend-linuxkms,slint/backend-linuxkms-libdlmclient,slint/renderer-skia"

CARGO_BUILD_FLAGS += " \
    ${@bb.utils.contains('AGL_FEATURES', 'agl-drm-lease', '${SLINT_CARGO_BUILD_FLAGS_DRMLEASE}', '${SLINT_CARGO_BUILD_FLAGS}', d)} \
    "

# Avoid skia build issue.
TOOLCHAIN = "clang"

export TARGET_CLANGCC_ARCH = "${TARGET_CC_ARCH}"
export CLANGCC = "${TARGET_PREFIX}clang --target=${TARGET_SYS} ${TARGET_CLANGCC_ARCH} --sysroot=${STAGING_DIR_TARGET}"
export CLANGCXX = "${TARGET_PREFIX}clang++ --target=${TARGET_SYS} ${TARGET_CLANGCC_ARCH} --sysroot=${STAGING_DIR_TARGET}"
export CLANGCPP = "${TARGET_PREFIX}clang -E --target=${TARGET_SYS} ${TARGET_CLANGCC_ARCH} --sysroot=${STAGING_DIR_TARGET}"
export SDKTARGETSYSROOT = "${PKG_CONFIG_SYSROOT_DIR}"
