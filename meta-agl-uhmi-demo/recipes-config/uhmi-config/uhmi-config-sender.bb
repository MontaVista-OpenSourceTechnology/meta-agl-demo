SUMMARY     = "Setting files for UHMI sender"
LICENSE     = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://app.json \
    file://initial_vscreen.json \
    file://wired-sender.network \
"

do_install() {
    install -d ${D}${sysconfdir}/systemd/network/
    install -m 644 ${WORKDIR}/wired-sender.network ${D}${sysconfdir}/systemd/network/wired.network

    install -d ${D}/var/local/uhmi-app/glmark2
    install -m 644 ${WORKDIR}/app.json ${D}/var/local/uhmi-app/glmark2/
    install -m 644 ${WORKDIR}/initial_vscreen.json ${D}/var/local/uhmi-app/glmark2/
}

FILES:${PN} += " \
    ${sysconfdir}/systemd/network/* \
    /var/local/uhmi-app/glmark2 \
"
