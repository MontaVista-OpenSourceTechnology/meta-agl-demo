FEATURE_PACKAGES_can-test-tools = " \
    can-utils \
    cannelloni \
"

FEATURE_PACKAGES_kuksa-val-databroker-client = " \
    kuksa-certificates-agl-ca \
    ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', 'packagegroup-agl-kuksa-val-databroker-devel' , '', d)} \
"
FEATURE_PACKAGES_kuksa-val-databroker = "packagegroup-agl-kuksa-val-databroker"

DEMO_CONF_SUFFIX_GW = "${@bb.utils.contains("AGL_FEATURES", "agl-demo-gateway-preload", "-gateway-demo", "", d)}"
DEMO_CONF_SUFFIX = "${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-kuksa", "-kvm-demo", "${DEMO_CONF_SUFFIX_GW}", d)}"
