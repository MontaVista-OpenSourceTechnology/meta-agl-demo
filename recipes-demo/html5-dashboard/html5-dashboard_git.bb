SUMMARY     = "AGL HTML5 dashboard Application"
HOMEPAGE    = "https://git.automotivelinux.org/apps/html5-dashboard/"
SECTION     = "apps"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PV      = "1.0+git${SRCPV}"
S       = "${WORKDIR}/git"
B       = "${WORKDIR}/build"

SRC_URI = " \
  git://gerrit.automotivelinux.org/gerrit/apps/html5-dashboard;protocol=https;branch=master \
  file://webapps-dashboard.desktop \
"
SRCREV = "4680db99615757df9d9cc561c9a9fc5b9b1c108a"

inherit pythonnative

DEPENDS = "nodejs-native"

do_compile[network] = "1"
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
  install ${WORKDIR}/webapps-dashboard.desktop ${D}${APPLICATIONS_DIR}
}

FILES:${PN} = " \
  ${WAM_APPLICATIONS_DIR}/${PN} \
  ${APPLICATIONS_DIR} \
"