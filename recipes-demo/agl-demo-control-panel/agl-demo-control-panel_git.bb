SUMMARY     = "AGL demo control panel"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=685e0faaaec2c2334cf8159ca6bd2975"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/src/agl-demo-control-panel;protocol=https;branch=${AGL_BRANCH} \
           file://agl-demo-control-panel.service \
"
SRCREV = "262fd5529462617cbb65747585498c8977cdd9db"

S = "${WORKDIR}/git"

inherit systemd allarch update-alternatives

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    # There's no provision for a Pythonic install into /usr/lib, so dump
    # into a directory /usr/libexec.
    install -d ${D}${libexecdir}/${BPN}
    cp -drv ${S}/* ${D}${libexecdir}/${BPN}

    # Remove stray shell script from Docker container build support to
    # avoid QA complaints
    rm -rf ${D}${libexecdir}/${BPN}/docker

    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service
    
    # Install conf file
    install -d ${D}${sysconfdir}/agl-demo-control-panel
    install -m 0644 ${S}/extras/config.ini ${D}${sysconfdir}/agl-demo-control-panel/config.ini.default
    sed 's/ip = localhost/ip = 192.168.10.2/' ${S}/extras/config.ini > \
        ${D}${sysconfdir}/agl-demo-control-panel/config.ini.demo
    sed -e 's/hvac-enabled = true/hvac-enabled = false/' \
        -e 's/steering-wheel-enabled = true/steering-wheel-enabled = false/' \
        ${S}/extras/config.ini > \
        ${D}${sysconfdir}/agl-demo-control-panel/config.ini.gateway-demo
}

# For now generate resource wrapper on first boot, as it looks non-trivial
# to get python3-pyqt5-native working to run pyrcc5 during build.
pkg_postinst_ontarget:${PN} () {
    /usr/bin/pyrcc5 -o ${libexecdir}/${BPN}/res_rc.py ${libexecdir}/${BPN}/assets/res.qrc
    true
}

ALTERNATIVE_LINK_NAME[agl-demo-control-panel.ini] = "${sysconfdir}/agl-demo-control-panel/config.ini"

RDEPENDS:${PN} += " \
    ${PN}-conf \
    python3 \
    python3-modules \
    python3-packaging \
    python3-qtwidgets \
    python3-can \
    agl-users \
    weston \
"

PACKAGE_BEFORE_PN += "${PN}-conf"
FILES:${PN}-conf += "${sysconfdir}/agl-demo-control-panel/config.ini.default"
RDEPENDS:${PN}-conf = "${PN}"
RPROVIDES:${PN}-conf = "agl-demo-control-panel.ini"
ALTERNATIVE:${PN}-conf = "agl-demo-control-panel.ini"
ALTERNATIVE_TARGET_${PN}-conf = "${sysconfdir}/agl-demo-control-panel/config.ini.default"

PACKAGE_BEFORE_PN += "${PN}-conf-demo"
FILES:${PN}-conf-demo += "${sysconfdir}/agl-demo-control-panel/config.ini.demo"
RDEPENDS:${PN}-conf-demo = "${PN}"
RPROVIDES:${PN}-conf-demo = "agl-demo-control-panel.ini"
ALTERNATIVE:${PN}-conf-demo = "agl-demo-control-panel.ini"
ALTERNATIVE_TARGET_${PN}-conf-demo = "${sysconfdir}/agl-demo-control-panel/config.ini.demo"
ALTERNATIVE_PRIORITY_${PN}-conf-demo = "20"

PACKAGE_BEFORE_PN += "${PN}-conf-gateway-demo"
FILES:${PN}-conf-gateway-demo += "${sysconfdir}/agl-demo-control-panel/config.ini.gateway-demo"
RDEPENDS:${PN}-conf-gateway-demo = "${PN}"
RPROVIDES:${PN}-conf-gateway-demo = "agl-demo-control-panel.ini"
ALTERNATIVE:${PN}-conf-gateway-demo = "agl-demo-control-panel.ini"
ALTERNATIVE_TARGET_${PN}-conf-gateway-demo = "${sysconfdir}/agl-demo-control-panel/config.ini.gateway-demo"
ALTERNATIVE_PRIORITY_${PN}-conf-gateway-demo = "30"
