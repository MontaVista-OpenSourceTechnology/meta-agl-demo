LICENSE="GPLv2"
LIC_FILES_CHKSUM="file://LICENSE;md5=53633740548e7211116fc17bbe20aaf8"

inherit cmake_qt5 pkgconfig

DEPENDS += " qtbase libdbus-c++ json-c \
           "

SRCREV="4e09ab88d0d97c59896ba66bf08a8e006e2af81a"
SRC_URI="git://github.com/AGLExport/genivi-navi-yelp-client.git;branch=new-layout \
         file://config.xml \
"

RDEPENDS_${PN} = " navigation "

S = "${WORKDIR}/git"

do_install_append() {
   mkdir -p ${WORKDIR}/widget
   install -m 0644 ${WORKDIR}/config.xml ${WORKDIR}/widget
   install -m 0755 ${D}/usr/bin/yelp-client ${WORKDIR}/widget/poi
   zip -ju ${WORKDIR}/widget/poi.wgt ${WORKDIR}/widget/poi ${WORKDIR}/widget/config.xml

   install -d ${D}/usr/AGL/apps
   install -m 0644 ${WORKDIR}/widget/poi.wgt ${D}/usr/AGL/apps/
}

FILES_${PN} += " /usr/AGL/apps/poi.wgt "
