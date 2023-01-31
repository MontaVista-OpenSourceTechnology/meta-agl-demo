FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
	file://psplash-colors.h \
	file://psplash-args.conf.in \
	"

SPLASH_IMAGES = "file://psplash-poky-img.h;outsuffix=default"

do_configure:append () {
	cp -f ${WORKDIR}/psplash-colors.h ${S}
}

PSPLASH_ARGS ?= ""

do_install:append () {
	sed -e "s,@PSPLASH_ARGS@,${PSPLASH_ARGS},g" \
		${WORKDIR}/psplash-args.conf.in > ${WORKDIR}/psplash-args.conf

	install -d ${D}${systemd_system_unitdir}/psplash-start.service.d
	install -m644 ${WORKDIR}/psplash-args.conf ${D}/${systemd_system_unitdir}/psplash-start.service.d/
}

FILES:${PN} += "${systemd_system_unitdir}"
