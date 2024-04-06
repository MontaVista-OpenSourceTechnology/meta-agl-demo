require agl-demo-features.inc

FEATURE_PACKAGES_agl-demo-preload = "packagegroup-agl-demo-preload"

FEATURE_PACKAGES_agl-demo-cluster-support = " \
    tbtnavi \
    tbtnavi-conf${DEMO_CONF_SUFFIX} \
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-kuksa", "", "kuksa-databroker-agl-demo-cluster", d)} \
"

IMAGE_FEATURES += " \
    ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", "can-test-tools" , "", d)} \
    ${@bb.utils.filter("AGL_FEATURES", "agl-demo-preload agl-demo-cluster-support", d)} \
"

IMAGE_FEATURES += " \
    kuksa-val-databroker-client \
    ${@bb.utils.contains_any("AGL_FEATURES", "agl-kvm-host-kuksa agl-demo-gateway-preload", "", "kuksa-val-databroker", d)} \
"
