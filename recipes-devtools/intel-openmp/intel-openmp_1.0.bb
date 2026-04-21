SUMMARY = "Intel OpenMP runtime library"
HOMEPAGE = "https://www.openmp.org/"
DESCRIPTION = "Prebuilt Intel OpenMP runtime library used by OpenVINO when iomp threading is enabled."

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.txt;md5=db4eb8e27bbcaefcb3ad611c750d476d"

SRC_URI = "https://storage.openvinotoolkit.org/dependencies/thirdparty/linux/iomp.tgz"
SRC_URI[sha256sum] = "7832b16d82513ee880d97c27c7626f9525ebd678decf6a8fe6c38550f73227d9"

S = "${UNPACKDIR}/omp"

COMPATIBLE_HOST = "(x86_64).*-linux"
COMPATIBLE_HOST:libc-musl = "null"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${libdir}
    install -m 0755 ${S}/lib/libiomp5.so ${D}${libdir}/libiomp5.so
}

FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}/libiomp5.so"

INSANE_SKIP:${PN} += "already-stripped"
