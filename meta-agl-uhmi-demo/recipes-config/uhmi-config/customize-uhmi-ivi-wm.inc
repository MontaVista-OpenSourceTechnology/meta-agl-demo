customize_uhmi_ivi_wm() {
    sed -i "s/XDG_RUNTIME_DIR=\/run\/user\/1001/XDG_RUNTIME_DIR=\/run\/user\/200/" ${IMAGE_ROOTFS}/${systemd_system_unitdir}/uhmi-ivi-wm.service
    sed -i "s/WAYLAND_DISPLAY=wayland-0/WAYLAND_DISPLAY=wayland-1/" ${IMAGE_ROOTFS}/${systemd_system_unitdir}/uhmi-ivi-wm.service
}
ROOTFS_POSTPROCESS_COMMAND:append = " customize_uhmi_ivi_wm;"
