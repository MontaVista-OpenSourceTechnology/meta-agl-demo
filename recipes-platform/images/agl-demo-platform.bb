require agl-ivi-demo-base.bb

DESCRIPTION = "AGL Qt Demo Platform image"

AGL_APPS_INSTALL += " \
    dashboard \
    hvac \
    ondemandnavi \
    ondemandnavi-conf${DEMO_CONF_SUFFIX} \
    settings \
    mediaplayer \
    messaging \
    phone \
    radio \
    window-management-client-grpc \
    camera-gstreamer \
"

IMAGE_INSTALL += " \
    packagegroup-agl-demo-platform \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "", "weston-terminal-conf", d)} \
"

