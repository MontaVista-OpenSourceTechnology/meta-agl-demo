FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-player-Fix-Play-without-browsing-scope.patch \
    file://0002-player-Answer-pending-request-on-destroy.patch \
    file://0003-player-Update-NumberOfItems-on-SetBrowsedPlayer.patch \
    file://0004-player-Report-EBUSY-from-busy-Search.patch \
    file://0005-unit-Add-media-player-tests.patch \
"
