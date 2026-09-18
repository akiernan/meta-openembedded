SUMMARY = "Report file access events, with the process that caused them"
DESCRIPTION = "fatrace reports file access events from all running processes, \
using fanotify."
HOMEPAGE = "https://github.com/martinpitt/fatrace"

LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/martinpitt/fatrace;protocol=https;branch=main;tag=${PV}"
SRCREV = "62bd8e6d7ad7f112b37a8d5fe3dbb88c13b34844"

do_install() {
	oe_runmake 'PREFIX=${prefix}' 'DESTDIR=${D}' install
}

PACKAGE_BEFORE_PN = "power-usage-report"
RDEPENDS:power-usage-report += "${PN} powertop python3-core"
FILES:power-usage-report = "${sbindir}/power-usage-report"
