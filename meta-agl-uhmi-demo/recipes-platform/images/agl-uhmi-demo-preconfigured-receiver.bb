SUMMARY = "AGL Unified HMI demo preconfigured weston receiver image"
LICENSE = "MIT"

DEPENDS = "uhmi-ivi-wm"

require recipes-platform/images/agl-image-weston.bb

IMAGE_FEATURES += "ssh-server-openssh"

# Add packages for Unified HMI demo platform here
IMAGE_INSTALL += " \
    packagegroup-rvgpu \
    packagegroup-ddfw \
    uhmi-config-receiver \
    agl-compositor \
"

UHMI_HOSTNAME ?= "agl-host1"
require recipes-config/uhmi-config/set-hostname.inc
require recipes-config/uhmi-config/customize-uhmi-ivi-wm.inc
