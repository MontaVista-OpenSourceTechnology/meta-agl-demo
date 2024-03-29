FILESEXTRAPATHS:prepend := "${THISDIR}/gpsd:"

SRC_URI += "file://sw-device-hook"

do_install:append() {
    # Install device-hook script for starting NMEA output on Sierra Wireless modems
    install -d ${D}${sysconfdir}/gpsd
    install -m 0755 ${WORKDIR}/sw-device-hook ${D}${sysconfdir}/gpsd/device-hook
}

