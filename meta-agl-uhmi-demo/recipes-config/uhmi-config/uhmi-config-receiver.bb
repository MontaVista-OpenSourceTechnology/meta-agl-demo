SUMMARY     = "Setting files for UHMI receiver"
LICENSE     = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://wired-receiver.network \
"

do_install() {
    install -d ${D}${sysconfdir}/systemd/network/
    install -m 644 ${WORKDIR}/wired-receiver.network ${D}${sysconfdir}/systemd/network/wired.network
}

FILES:${PN} += " \
    ${sysconfdir}/systemd/network/* \
"
