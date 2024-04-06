DESCRIPTION = "AGL Gateway Demo Platform image."

LICENSE = "MIT"

require recipes-platform/images/agl-image-minimal.bb
require agl-demo-features.inc

IMAGE_FEATURES += " \
    kuksa-val-databroker \
    ssh-server-openssh \
    ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', 'can-test-tools' , '', d)} \
"

IMAGE_INSTALL += " \
    agl-vss-proxy \
    vss-agl-gw-control-panel \
    vss-agl-gw-hardware \
    ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', 'packagegroup-agl-kuksa-val-databroker-devel' , '', d)} \
    tcpdump \
"
