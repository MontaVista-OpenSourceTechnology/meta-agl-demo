SUMMARY = "Flutter Instrument Cluster "
DESCRIPTION = "An instrument cluster app written in dart for the flutter runtime"
AUTHOR = "Aakash Solanki"
HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/apps/flutter-instrument-cluster"

SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=0c52b0e4b5f0dbf57ea7d44bebb2e29d"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/flutter-instrument-cluster;protocol=https;branch=${AGL_BRANCH} \
    file://flutter-cluster-dashboard.service \
    file://flutter_cluster_dashboard_on_bg.toml \
    file://kvm.conf \
    file://kuksa.toml \
    file://flutter-cluster-dashboard.token \
"

PV = "1.0+git${SRCPV}"
SRCREV = "2cdb923c76286d20d25ca18599e8f992f053df3f"

PUBSPEC_APPNAME = "flutter_cluster_dashboard"

inherit flutter-app systemd

APP_CONFIG = "flutter_cluster_dashboard_on_bg.toml"

PUBSPEC_IGNORE_LOCKFILE = "1"

SYSTEMD_SERVICE:${PN} = "flutter-cluster-dashboard.service"

do_install:append() {
    install -D -m 0644 ${UNPACKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service

    install -D -m 0644 ${UNPACKDIR}/kvm.conf ${D}${systemd_system_unitdir}/${BPN}.service.d/kvm.conf

    install -D -m 0644 ${UNPACKDIR}/${APP_CONFIG} ${D}${datadir}/flutter/${BPN}.json

    install -d ${D}${sysconfdir}/xdg/AGL/flutter-cluster-dashboard
    install -m 0644 ${UNPACKDIR}/kuksa.toml ${D}${sysconfdir}/xdg/AGL/flutter-cluster-dashboard/
    install -m 0644 ${UNPACKDIR}/flutter-cluster-dashboard.token ${D}${sysconfdir}/xdg/AGL/flutter-cluster-dashboard/
}

FILES:${PN} += "${datadir} ${sysconfdir}/xdg/AGL"

RDEPENDS:${PN} += "flutter-auto agl-flutter-env liberation-fonts"

# systemd override to add network-online.target dependency for KVM setups
PACKAGE_BEFORE_PN += "${PN}-conf-kvm"
FILES:${PN}-conf-kvm += "${systemd_system_unitdir}/flutter-cluster-dashboard.service.d/kvm.conf"
RDEPENDS:${PN}-conf-kvm = "${PN}"
