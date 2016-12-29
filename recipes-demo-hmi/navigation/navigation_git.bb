LICENSE="GPLv2"
LIC_FILES_CHKSUM="file://LICENSE;md5=3595e9c703a847d990664d2b396a9df0 \
                  file://COPYING;md5=947b2d60ca3872e172034438e9801200"

DEPENDS = " \
          glib-2.0 freetype sqlite3 wayland zlib expat openssl virtual/libgles2 virtual/libgl virtual/egl \
          wayland libdbus-c++ \
          "

RDEPENDS_${PN} = " flite openjtalk "

SRCREV="1b7218335b5b7a5312e3611138c70c49a27a3b9f"
SRC_URI="git://github.com/AGLExport/gpsnavi.git \
         file://flite.in \
         file://jtalk.in \
         file://config.xml \
"

# To avoid C++ library link failure
SECURITY_CFLAGS = ""

inherit autotools pkgconfig

S = "${WORKDIR}/git"

do_compile_prepend() {
   cp ${WORKDIR}/flite.in ${S}/
   cp ${WORKDIR}/jtalk.in ${S}/
}

do_install_append() {
   mkdir -p ${WORKDIR}/widget
   install -m 0644 ${WORKDIR}/config.xml ${WORKDIR}/widget
   install -m 0755 ${D}/usr/bin/navi ${WORKDIR}/widget
   zip -ju ${WORKDIR}/widget/navigation.wgt ${WORKDIR}/widget/navi ${WORKDIR}/widget/config.xml

   install -d ${D}/usr/AGL/apps
   install -m 0644 ${WORKDIR}/widget/navigation.wgt ${D}/usr/AGL/apps/

   install -d ${D}/usr/share/mapdata
}

FILES_${PN} += " /usr/AGL/apps/navigation.wgt /usr/share/mapdata "