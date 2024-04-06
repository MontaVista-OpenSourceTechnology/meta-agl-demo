DESCRIPTION = "A virtio-sound device using the vhost-user protocol."
HOMEPAGE = "https://github.com/rust-vmm/vhost-device"
LICENSE = "Apache-2.0 | BSD-3-Clause"
LIC_FILES_CHKSUM = " \
    file://LICENSE-APACHE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://LICENSE-BSD-3-Clause;md5=2489db1359f496fff34bd393df63947e \
"

DEPENDS += "alsa-lib pipewire clang-cross-${TARGET_ARCH}"

SRC_URI += "crate://crates.io/vhost-device-sound/0.1.0"
 
inherit cargo cargo-update-recipe-crates pkgconfig

include vhost-device-sound-crates.inc

SKIP_RECIPE[vhost-device-sound] ?= "${@bb.utils.contains('BBFILE_COLLECTIONS', 'clang-layer', '', 'Depends on clang-native from meta-clang which is not included', d)}"

