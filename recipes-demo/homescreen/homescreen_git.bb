SUMMARY     = "Home Screen application"
DESCRIPTION = "AGL demonstration Home Screen application"
HOMEPAGE    = "http://docs.automotivelinux.org"
LICENSE     = "Apache-2.0"
SECTION     = "apps"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    qtbase \
    qtdeclarative \
    libqtappfw \
    wayland-native \
    wayland \
    qtwayland \
    qtwayland-native \
    protobuf \
    grpc grpc-native \
    agl-compositor \
    applaunchd \
"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/homescreen;protocol=https;branch=${AGL_BRANCH} \
           file://homescreen.service \
           file://kuksa.toml \
           file://homescreen.token \
           "
SRCREV = "0253013fb268f820a965bbebd8388f78686ccef6"

inherit meson pkgconfig systemd meson_qt6_path

PATH:prepend = "${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}:"

OE_QMAKE_CXXFLAGS:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', '' , '-DQT_NO_DEBUG_OUTPUT', d)}"

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_install:append() {
    install -D -m0644 ${UNPACKDIR}/homescreen.service ${D}${systemd_system_unitdir}/homescreen.service

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/homescreen
    install -m 0644 ${UNPACKDIR}/kuksa.toml ${D}${sysconfdir}/xdg/AGL/homescreen/
    install -m 0644 ${UNPACKDIR}/homescreen.token ${D}${sysconfdir}/xdg/AGL/homescreen/
}

RDEPENDS:${PN} += " \
    libqtappfw \
    applaunchd \
    qtwayland \
    qtbase-qmlplugins \
    qt5compat \
    qtshadertools \
"
