DESCRIPTION = "slLIN driver module"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://sllin.c;beginline=7;endline=37;md5=6408e14dba951f8cbe3c2a003a0d89d2"

inherit module systemd

DEPENDS = "virtual/kernel"

SRC_URI = "git://github.com/trainman419/linux-lin.git;protocol=https;branch=master"
SRCREV = "155d885e8ccc907a56f6c86c4b159fac27ef6fec"
S = "${WORKDIR}/git/sllin"

PV = "0.1+git${SRCPV}"

SRC_URI:append = " \
	file://0001_update_makefile.patch;pnum=2 \
	file://0002_fix_null_operation_check.patch;pnum=2 \
	file://0003-Allow-recent-kernels-newer-4.11.x-to-build.patch;pnum=2 \
	file://0001-Disable-sllin-driver-debug-log.patch;pnum=2 \
	file://0004-Fix-build-with-5.9-kernel.patch;pnum=2 \
	file://0005-Fix-build-with-5.13-kernel.patch;pnum=2 \
	file://0006-Fix-build-with-5.14-kernel.patch;pnum=2 \
	file://sllin-demo.service \
	file://start_lin_demo.sh \
	file://lin_config.conf \
"

KERNEL_MODULE_AUTOLOAD:append = " sllin"
KERNEL_MODULE_PROBECONF:append = " sllin"

SLLINBAUDRATE ??= "9600"
module_conf_sllin = "options sllin baudrate=${SLLINBAUDRATE}"

SYSTEMD_SERVICE:${PN} = "sllin-demo.service"

do_install:append () {
	install -d 644 ${D}/${bindir}
	install -m 755 ${WORKDIR}/start_lin_demo.sh ${D}/${bindir}/start_lin_demo.sh
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/sllin-demo.service ${D}${systemd_system_unitdir}/
	install -d ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/lin_config.conf ${D}${sysconfdir}/
}

FILES:${PN} += "${bindir}/start_lin_demo.sh ${sysconfdir}/lin_config.conf"

RDEPENDS:${PN} += "lin-config"
