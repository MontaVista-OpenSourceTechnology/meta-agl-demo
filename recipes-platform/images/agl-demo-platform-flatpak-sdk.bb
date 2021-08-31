require agl-demo-platform-flatpak-runtime.bb

SUMMARY = "Flatpak SDK image for AGL demo platform."

# Pull in development tools.
IMAGE_FEATURES_append = " dev-pkgs tools-sdk "
IMAGE_INSTALL_append = " cmake cmake-apps-module git qtlocation ninja "

