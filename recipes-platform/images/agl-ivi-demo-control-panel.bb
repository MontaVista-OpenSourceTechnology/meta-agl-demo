require recipes-platform/images/agl-image-weston.bb
require agl-demo-features.inc

SUMMARY = "AGL demo control panel image"

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

# KUKSA.val databroker is only installed with
# "agl-demo-gateway-preload" feature, as it used as
# a bridge to CAN output via kuksa-dbc-feeder in that
# configuration.
IMAGE_FEATURES += " \
    kuksa-val-databroker-client \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-gateway-preload", "kuksa-val-databroker", "", d)} \
"

# NOTE: The client key and certificate in kuksa-certificates-agl-client
#       seem required by kuksa-client at the moment even though client
#       certification has been deprecated upstream, this needs further
#       investigation so it can be dropped from the image.
#   
IMAGE_KUKSA_PACKAGES = " \
    kuksa-client \
    kuksa-certificates-agl-ca \
    kuksa-certificates-agl-client \
"

IMAGE_INSTALL += "\
    packagegroup-agl-networking \
    weston-ini-conf-landscape \
    weston-agl-driver-conf \
    ${IMAGE_KUKSA_PACKAGES} \
    agl-demo-control-panel \
    qtwayland \
    qtwayland-plugins \
    qtwayland-qmlplugins \
    ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", "can-test-tools" , "", d)} \
"
