SRC_URI:append = " \
    file://slint-drm-lease \
    "

do_install:append() {
    install -m 0755 -d ${D}${sysconfdir}/default/
    install -m 0755 ${UNPACKDIR}/slint-drm-lease ${D}${sysconfdir}/default/
}
