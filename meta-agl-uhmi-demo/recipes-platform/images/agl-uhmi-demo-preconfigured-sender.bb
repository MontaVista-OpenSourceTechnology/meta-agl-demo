SUMMARY = "AGL Unified HMI demo preconfigured weston sender image"
LICENSE = "MIT"

DEPENDS = "uhmi-ivi-wm"

require recipes-platform/images/agl-image-compositor.bb
require recipes-platform/images/agl-demo-features.inc

IMAGE_FEATURES += "ssh-server-openssh package-management"

# Add packages for Unified HMI demo platform here
IMAGE_INSTALL += " \
    packagegroup-rvgpu \
    packagegroup-ddfw \
    uhmi-config-sender \
    agl-compositor \
    native-shell-client \
    glmark2 \
"

UHMI_HOSTNAME ?= "agl-host0"
require recipes-config/uhmi-config/set-hostname.inc
require recipes-config/uhmi-config/customize-uhmi-ivi-wm.inc
