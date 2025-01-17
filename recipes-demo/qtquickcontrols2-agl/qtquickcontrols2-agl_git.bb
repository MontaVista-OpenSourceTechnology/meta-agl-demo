SUMMARY     = "AGL demo custom QtQuickControls2 widgets"
HOMEPAGE    = "https://git.automotivelinux.org/src/qtquickcontrols2-agl"
LICENSE     = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=815ca599c9df247a0c7f619bab123dad"

DEPENDS = "qtdeclarative"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/src/qtquickcontrols2-agl;protocol=https;branch=${AGL_BRANCH}"
SRCREV = "22b538aae1f69de5273f1e42a854cfab23a32b60"

S = "${WORKDIR}/git"

inherit qt6-qmake

FILES:${PN} += "${OE_QMAKE_PATH_QML}/AGL/Demo/Controls/*"

RDEPENDS:${PN} += " \
    qtsvg-plugins \
"
