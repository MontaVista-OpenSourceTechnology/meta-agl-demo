FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
	file://psplash-colors.h \
	file://psplash-args.conf.in \
	"

# drm-backend backported from:
# https://patchwork.yoctoproject.org/project/yocto/cover/20220425075954.10427-1-vasyl.vavrychuk@opensynergy.com/

FILESEXTRAPATHS:prepend := "${THISDIR}/psplash-drm:"

SRC_URI:append = " \
	file://0000-Fix-duplicated-definition-of-bool.patch \
	file://0001-Trim-trailing-spaces.patch \
	file://0002-Fix-unused-result-warnings.patch \
	file://0003-Remove-unused-save_termios.patch \
	file://0004-Remove-psplash-fb.h-from-psplash.h.patch \
	file://0005-Extract-plot-pixel-from-psplash-fb.patch \
	file://0006-Extract-draw-rect-image-from-psplash-fb.patch \
	file://0007-Extract-draw-font-from-psplash-fb.patch \
	file://0008-psplash.c-Make-psplash_draw_-msg-progress-independen.patch \
	file://0009-Rework-flip-as-function-pointer.patch \
	file://0010-Import-drm-howto-modeset.c-as-psplash-drm.c.patch \
	file://0011-Implement-drm-backend.patch \
	file://0012-Reverse-modeset_list.patch \
	file://0013-psplash-drm.c-Allocate-resources-only-for-the-first-.patch \
	file://0014-psplash-drm.c-Implement-double-buffering.patch \
	file://0015-drm-Fix-incorrect-drawing-with-portrait-orientation.patch \
	file://psplash-wait.conf \
	"

# Update licesnse checksum as it's changed with above patches
LIC_FILES_CHKSUM = "file://psplash.h;beginline=1;endline=8;md5=db1ed16abf4be6de3d79201093ac4f07"

PACKAGECONFIG[drm] = "--enable-drm,,libdrm"

SPLASH_IMAGES = "file://psplash-poky-img.h;outsuffix=default"

do_configure:append () {
	cp -f ${WORKDIR}/psplash-colors.h ${S}
}

PSPLASH_ARGS ?= ""
PSPLASH_ARGS += "${@bb.utils.contains('PACKAGECONFIG', 'drm', '--drm', '', d)}"

# drm device should be released by psplash before start of agl-compositor.
# This is basically satisfied without any care, but sometimes psplash lives
# longer than usual because of its long interval of polling boot progress
# from systemd. So we'd add a tiny script for waiting psplash's exit before
# start of agl-compositor here.
# This potentially not be useful for drm-lease environment since there is a
# transaction support of lease device user in drm-lease-manager.
NEED_PSPLASH_WAIT ?= "${@bb.utils.contains('PACKAGECONFIG', 'drm', '1', '0', d)}"

do_install:append () {
	sed -e "s,@PSPLASH_ARGS@,${PSPLASH_ARGS},g" \
		${WORKDIR}/psplash-args.conf.in > ${WORKDIR}/psplash-args.conf

	install -d ${D}${systemd_system_unitdir}/psplash-start.service.d
	install -m644 ${WORKDIR}/psplash-args.conf ${D}/${systemd_system_unitdir}/psplash-start.service.d/

	if ${@bb.utils.contains('NEED_PSPLASH_WAIT', '1', 'true', 'false', d)}; then
		install -d ${D}${systemd_user_unitdir}/agl-compositor.service.d
		install -m644 ${WORKDIR}/psplash-wait.conf ${D}${systemd_user_unitdir}/agl-compositor.service.d
	fi
}

FILES:${PN} += " \
	${systemd_system_unitdir} \
	${systemd_user_unitdir} \
	"
