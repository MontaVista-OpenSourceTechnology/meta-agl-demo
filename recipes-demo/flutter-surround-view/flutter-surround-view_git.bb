SUMMARY = "Surround View: GPU camera view and calibration for AGL"
HOMEPAGE = "https://github.com/Abdulrahman-Yasser/flutter-surround-view"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/flutter-surround-view;protocol=https;branch=${AGL_BRANCH}"
SRCREV = "7d89e95c2a46ccc49df79caccfb8a83afba360ce"

inherit flutter-app-native agl-app

DEPENDS += " \
    libcamera \
    libdrm \
    virtual/libgbm \
    virtual/egl \
    virtual/libgles2 \
    libpng \
    ivi-homescreen-shared \
    vulkan-headers \
    opencv \
"

# libcamera is built with libstdc++, so libbev_view.so must not use libc++
CFLAGS:remove = "-rtlib=compiler-rt -unwindlib=libunwind"
CXXFLAGS:remove = "-rtlib=compiler-rt -unwindlib=libunwind -stdlib=libc++"

FLUTTER_APPLICATION_PATH = "flutter-surround-view"
PUBSPEC_APPNAME = "surround_view"

AGL_APP_TEMPLATE = "agl-app-flutter"
AGL_APP_ID = "surround_view"
AGL_APP_NAME = "Surround View"
