require agl-ivi-demo-base-flutter.bb

DESCRIPTION = "AGL Flutter Demo Platform image"

AGL_APPS_INSTALL += " \
    flutter-ics-homescreen \
    flutter-ics-homescreen-conf${DEMO_CONF_SUFFIX} \
    camera-gstreamer \
    ondemandnavi \
    ondemandnavi-conf${DEMO_CONF_SUFFIX} \
"
