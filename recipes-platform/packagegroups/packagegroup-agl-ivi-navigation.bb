SUMMARY = "The software for application framework of AGL IVI profile"
DESCRIPTION = "A set of packages belong to AGL application framework which required by \
Navigation and Location-Based Services Subsystem"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-ivi-navigation \
    "

RDEPENDS:${PN} += "\
    gpsd \
    gpsd-machine-conf \
    geoclue \
    "
