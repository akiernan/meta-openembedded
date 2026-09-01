SUMMARY = "Run a command with altered stdio buffering"
DESCRIPTION = "BSD-licensed stdbuf from FreeBSD, for images which exclude \
GPL-3.0 and so cannot use the coreutils version."
HOMEPAGE = "https://git.freebsd.org/src.git"
SECTION = "console/utils"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://usr-stdbuf.c;beginline=1;endline=26;md5=4afefc1eaad5cbfec47a4a2f0a8cfda1"

# Fetch just the two files from the GitHub mirror rather than cloning the
# whole FreeBSD tree. FBSD_SRCREV is the last commit to touch either file.
FBSD_SRCREV = "b3e7694832e81d7a904a10f525f8797b753bf0d3"
FBSD_RAW = "https://raw.githubusercontent.com/freebsd/freebsd-src/${FBSD_SRCREV}"

SRC_URI = "${FBSD_RAW}/usr.bin/stdbuf/stdbuf.c;name=stdbuf;downloadfilename=usr-stdbuf.c \
           ${FBSD_RAW}/lib/libstdbuf/stdbuf.c;name=libstdbuf;downloadfilename=lib-stdbuf.c"
SRC_URI[stdbuf.sha256sum] = "d217bb2c3ea074ea49b844b5f7a9494da5834bd49d91f02dc0c648bb15f8decc"
SRC_URI[libstdbuf.sha256sum] = "e8245611a10e8db646c7d0f73fdcbbf63e6cb1f9b9cb48376f24ef7f0cc9ec83"

S = "${UNPACKDIR}"

inherit update-alternatives

# stdbuf hardcodes /usr/lib/libstdbuf.so as its preload path
do_configure() {
	sed -i 's|"/usr/lib/libstdbuf.so"|"${libdir}/libstdbuf.so"|' ${S}/usr-stdbuf.c
	grep -q '"${libdir}/libstdbuf.so"' ${S}/usr-stdbuf.c
}

# _GNU_SOURCE for asprintf(3)
do_compile() {
	${CC} ${CFLAGS} ${CPPFLAGS} ${LDFLAGS} -D_GNU_SOURCE \
		-fPIC -shared -o libstdbuf.so lib-stdbuf.c
	${CC} ${CFLAGS} ${CPPFLAGS} ${LDFLAGS} -D_GNU_SOURCE \
		-o stdbuf usr-stdbuf.c
}

do_install() {
	install -d ${D}${bindir} ${D}${libdir}
	install -m 0755 ${B}/stdbuf ${D}${bindir}/stdbuf
	install -m 0755 ${B}/libstdbuf.so ${D}${libdir}/libstdbuf.so
}

# libstdbuf.so is a preload object and belongs in ${PN}, not ${PN}-dev
FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}/libstdbuf.so"

# Lower than coreutils and uutils-coreutils, which also provide stdbuf
ALTERNATIVE:${PN} = "stdbuf"
ALTERNATIVE_LINK_NAME[stdbuf] = "${bindir}/stdbuf"
ALTERNATIVE_PRIORITY = "90"
