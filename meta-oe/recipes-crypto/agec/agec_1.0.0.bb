SUMMARY = "age file encryption in C"
DESCRIPTION = "agec is a simple file encryption tool that implements age \
format in C with minimal dependencies. The tool supports asymmetric \
encryption based on X25519, and a passphrase encryption based on scrypt."
HOMEPAGE = "https://sr.ht/~min/agec/"
SECTION = "console/crypto"

LICENSE = "0BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1b78e29525f0a95762d6db9a34a36e4e"

SRC_URI = "git://git.sr.ht/~min/agec;protocol=https;branch=master;tag=${PV}"
SRCREV = "85ace80cc4137095ca204eba5e099be4e978d889"

# libcrypto is used only for its random source
DEPENDS = "openssl"

EXTRA_OEMAKE = "'PREFIX=${prefix}'"

do_install() {
	oe_runmake 'DESTDIR=${D}' install
}

BBCLASSEXTEND = "native nativesdk"
