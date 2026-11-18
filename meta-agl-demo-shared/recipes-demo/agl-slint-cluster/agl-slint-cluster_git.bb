SUMMARY = "A digital instrument cluster written in Rust and Slint, built for Automotive Grade Linux."
HOMEPAGE = "https://github.com/signal-slot/agl-slint-cluster"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1cd9f3535d038f9d1bb320918e6474b1"

PV = "0.1"

SRC_URI = " \
    git://github.com/signal-slot/agl-slint-cluster.git;protocol=https;branch=main \
    file://slint-cluster \
    file://agl-slint-cluster.service \
"
SRC_URI:append:sparrow-hawk = " \
    file://glutin-egl-dlsym-fallback.patch;patchdir=${CARGO_VENDORING_DIRECTORY}/glutin-0.32.3 \
"
SRCREV = "ef7f27f27b515f4eab8d84078304486697bc78f2"

inherit cargo cargo-update-recipe-crates systemd agl-slint

require ${@bb.utils.contains('AGL_FEATURES', 'agl-drm-lease', 'agl-slint-cluster-drm-lease.inc', '', d)}

require ${BPN}-crates.inc
CARGO_MANIFEST_PATH = "${S}/rust/Cargo.toml"

SYSTEMD_SERVICE:${PN} = "agl-slint-cluster.service"

do_install:append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${UNPACKDIR}/agl-slint-cluster.service ${D}${systemd_unitdir}/system/

    install -m 0755 -d ${D}${sysconfdir}/default/
    install -m 0755 ${UNPACKDIR}/slint-cluster ${D}${sysconfdir}/default/
}
