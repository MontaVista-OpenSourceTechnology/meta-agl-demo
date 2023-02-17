#!/bin/bash
set -x

SCRIPTPATH="$( cd $(dirname $0) >/dev/null 2>&1 ; pwd -P )"
echo $SCRIPTPATH
AGLROOT="$SCRIPTPATH/../.."
POKYDIR="$AGLROOT/external/poky"
TMPROOT=`mktemp -d`

rm -rf ${TMPROOT}/testbuild-ycl || true
mkdir -p ${TMPROOT}/testbuild-ycl
cd ${TMPROOT}/testbuild-ycl

source $POKYDIR/oe-init-build-env .

cat << EOF >> conf/local.conf
# just define defaults
AGL_FEATURES ?= ""
AGL_EXTRA_IMAGE_FSTYPES ?= ""

# important settings imported from poky-agl.conf
# we do not import 
DISTRO_FEATURES:append = " systemd wayland pam polkit"
DISTRO_FEATURES_BACKFILL_CONSIDERED:append = " sysvinit"
VIRTUAL-RUNTIME_init_manager = "systemd"
VIRTUAL-RUNTIME_net_manager = "connman"
# mask hard dependency on smack
#BBMASK += "meta-oe/recipes-extended/ostree/"
# ERROR: Nothing PROVIDES 'sip3' (but /home/dl9pf/AGL/master/external/meta-qt5/recipes-python/pyqt5/python3-pyqt5_5.15.1.bb, /home/dl9pf/AGL/master/external/meta-qt5/recipes-python/pyqtchart/python3-pyqtchart_5.15.1.bb DEPENDS on or otherwise requires it)
BBMASK += "meta-qt5/recipes-python/pyqt5/"
BBMASK += "meta-qt5/recipes-python/pyqtchart/"
# commercial license flag on ffmpeg
BBMASK += "meta-flutter/recipes-graphics/toyota/"
BBMASK += "meta-flutter/recipes-graphics/flutter-apps/flutter-test-plugins_git.bb"
BBMASK += "meta-agl-demo/recipes-platform/packagegroups/packagegroup-agl-demo-platform-flutter.bb"

EOF


yocto-check-layer \
	--dependency \
	$AGLROOT/external/meta-openembedded/meta-oe \
	$AGLROOT/external/meta-qt5 \
	$AGLROOT/meta-agl/meta-agl-core \
	$AGLROOT/external/meta-openembedded/meta-python \
	$AGLROOT/external/meta-openembedded/meta-multimedia \
	--additional-layers \
	$AGLROOT/external/meta-qt5 \
	$AGLROOT/meta-agl/meta-app-framework \
	$AGLROOT/external/meta-flutter \
	$AGLROOT/external/meta-clang \
	$AGLROOT/external/meta-python2 \
	$AGLROOT/external/meta-openembedded/meta-python \
	$AGLROOT/external/meta-openembedded/meta-multimedia \
	$AGLROOT/external/meta-openembedded/meta-networking \
	-- \
	$AGLROOT/meta-agl-demo


[ $? = 0 ] && rm -rf ${TMPROOT}/testbuild-ycl

exit 0

	--dependency \
	    $AGLROOT/external/meta-openembedded/meta-oe \
	    $AGLROOT/external/meta-openembedded/meta-python \
	    $AGLROOT/external/meta-openembedded/meta-networking \
