SUMMARY = "Flutter HVAC"
DESCRIPTION = "A Flutter based IVI Dashboard Application for automotive grade Linux."

HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/apps/flutter-hvac"

BUGTRACKER = "https://github.com/hritik-chouhan/HVAC_dashboard/issues"

SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://License.md;md5=f712ede8d4f845976061925d1416fc40"


SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/flutter-hvac;protocol=https;branch=${AGL_BRANCH} \
    file://hvac.yaml \
    file://hvac.token \
    "

SRCREV = "6a853805d2479bf7b111511b1f94907e425c607a"
S = "${WORKDIR}/git"

inherit agl-app flutter-app

# flutter-app
#############
PUBSPEC_APPNAME = "flutter_hvac"
FLUTTER_APPLICATION_INSTALL_PREFIX = "/flutter"
FLUTTER_BUILD_ARGS = "bundle -v"

# agl-app
#########
AGL_APP_TEMPLATE = "agl-app-flutter"
AGL_APP_ID = "flutter_hvac"
AGL_APP_NAME = "HVAC"

do_install:append() {
    install -d ${D}${sysconfdir}/xdg/AGL/hvac
    install -m 0644 ${WORKDIR}/hvac.yaml ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/hvac.token ${D}${sysconfdir}/xdg/AGL/hvac/
}

FILES:${PN} += "${sysconfdir}/xdg/AGL"
