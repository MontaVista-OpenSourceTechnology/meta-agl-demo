# Based on meta-flatpak/recipes-core/images/core-image-flatpak-runtime.bb

# We avoid agl-demo-platform.bb as it pulls in all apps.
# Instead, start from minimal image.
require recipes-platform/images/agl-image-minimal.inc

# Disable disk image creation
IMAGE_FSTYPES = ""

inherit flatpak-image

SUMMARY = "Flatpak runtime for AGL demo platform"

# Add depenendencies from packagegroup-agl-demo-platform.bb
IMAGE_INSTALL_append = " \
    ca-certificates ncurses-terminfo \
    packagegroup-agl-profile-graphical-qt5 \
    libqtappfw qtwebsockets qtwebsockets-qmlplugins \
    qtquickcontrols2-agl qtquickcontrols2-agl-style \
"
