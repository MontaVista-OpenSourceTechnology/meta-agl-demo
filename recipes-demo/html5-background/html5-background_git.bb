SUMMARY     = "AGL HTML5 Homescreen"
HOMEPAGE    = "https://github.com/AGL-web-applications/background"
SECTION     = "apps"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PV      = "1.0+git${SRCPV}"
S       = "${WORKDIR}/git"
B       = "${WORKDIR}/build"

SRC_URI = " \
  git://gerrit.automotivelinux.org/gerrit/apps/html5-background;protocol=https;branch=master \
  file://webapps-html5-background.desktop \
"
SRCREV = "7b796408f4ee1ca1b92a02445513d939079af46e"

DEPENDS = "nodejs-native"

do_compile() {
  cd ${S}
  rm -rf package node_modules package-lock.json
  npm install
  npm run build
}

WAM_APPLICATIONS_DIR="${libdir}/wam_apps"
APPLICATIONS_DIR="${datadir}/applications"

do_install() {
  install -d ${D}${WAM_APPLICATIONS_DIR}/${PN}
  cp -R --no-dereference --preserve=mode,links ${S}/dist/* ${D}${WAM_APPLICATIONS_DIR}/${PN}
  install -d ${D}${APPLICATIONS_DIR}
  install ${WORKDIR}/webapps-html5-background.desktop ${D}${APPLICATIONS_DIR}
}

FILES_${PN} = " \
  ${WAM_APPLICATIONS_DIR}/${PN} \
  ${APPLICATIONS_DIR} \
"

RCONFLICTS_${PN} = "homescreen"
