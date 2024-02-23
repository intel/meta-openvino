SUMMARY = "OpenVINO(TM) Toolkit - Deep Learning Deployment Toolkit"
HOMEPAGE = "https://github.com/opencv/dldt"
DESCRIPTION = "This toolkit allows developers to deploy pre-trained \
deep learning models through a high-level C++ Inference Engine API \
integrated with application logic."

SRC_URI = "git://github.com/openvinotoolkit/openvino.git;protocol=https;name=openvino;branch=releases/2023/3;lfs=0 \
           git://github.com/openvinotoolkit/oneDNN.git;protocol=https;destsuffix=git/src/plugins/intel_cpu/thirdparty/onednn;name=mkl;nobranch=1 \
           git://github.com/oneapi-src/oneDNN.git;protocol=https;destsuffix=git/src/plugins/intel_gpu/thirdparty/onednn_gpu;name=onednn;nobranch=1 \
           git://github.com/herumi/xbyak.git;protocol=https;destsuffix=git/thirdparty/xbyak;name=xbyak;branch=master \
           git://github.com/nlohmann/json.git;protocol=https;destsuffix=git/thirdparty/json/nlohmann_json;name=json;branch=develop \
           git://github.com/opencv/ade.git;protocol=https;destsuffix=git/thirdparty/ade;name=ade;nobranch=1 \
           git://github.com/protocolbuffers/protobuf.git;protocol=https;destsuffix=git/thirdparty/protobuf/protobuf;name=protobuf;branch=3.20.x \
           git://github.com/gflags/gflags.git;protocol=https;destsuffix=git/thirdparty/gflags/gflags;name=gflags;nobranch=1 \
           git://github.com/madler/zlib.git;protocol=https;destsuffix=git/thirdparty/zlib/zlib;name=zlib;nobranch=1 \
           git://github.com/nodejs/node-api-headers.git;protocol=https;destsuffix=git/node-api-headers-src;name=node-api-headers;nobranch=1 \
           git://github.com/nodejs/node-addon-api.git;protocol=https;destsuffix=git/node-addon-api-src;name=node-addon-api;nobranch=1 \
           file://0001-cmake-yocto-specific-tweaks-to-the-build-process.patch \
           file://0002-Change-the-working-directory-to-source-to-workaround.patch \
           file://0003-cmake-Fix-overloaded-virtual-error.patch \
           file://0004-protobuf-allow-target-protoc-to-be-built.patch \
           "

SRCREV_openvino = "ceeafaf64f346c6f14a67c612e131da5c27ef620"
SRCREV_mkl = "cb3060bbf4694e46a1359a3d4dfe70500818f72d"
SRCREV_onednn = "cb77937ffcf5e83b5d1cf2940c94e8b508d8f7b4"
SRCREV_xbyak = "740dff2e866f3ae1a70dd42d6e8836847ed95cc2"
SRCREV_json = "9cca280a4d0ccf0c08f47a99aa71d1b0e52f8d03"
SRCREV_ade = "0e8a2ccdd34f29dba55894f5f3c5179809888b9e"
SRCREV_protobuf = "fe271ab76f2ad2b2b28c10443865d2af21e27e0e"
SRCREV_gflags = "e171aa2d15ed9eb17054558e0b3a6a413bb01067"
SRCREV_zlib = "04f42ceca40f73e2978b50e93806c2a18c1281fc"
SRCREV_node-api-headers = "186e04b5e40e54d7fd1655bc67081cc483f12488"
SRCREV_node-addon-api = "39a25bf27788ff7a7ea5c64978c4dcd1e7b9d80d"
SRCREV_FORMAT = "openvino_mkl_onednn_xbyak_json_ade_protobuf_gflags_zlib_node-api-headers_node-addon-api"

LICENSE = "Apache-2.0 & MIT & BSD-3-Clause & Zlib"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
                    file://thirdparty/xbyak/COPYRIGHT;md5=3c98edfaa50a86eeaef4c6109e803f16 \
                    file://thirdparty/cnpy/LICENSE;md5=689f10b06d1ca2d4b1057e67b16cd580 \
                    file://thirdparty/json/nlohmann_json/LICENSE.MIT;md5=f969127d7b7ed0a8a63c2bbeae002588 \
                    file://thirdparty/ade/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
                    file://thirdparty/gflags/gflags/COPYING.txt;md5=c80d1a3b623f72bb85a4c75b556551df \
                    file://thirdparty/zlib/zlib/LICENSE;md5=b51a40671bc46e961c0498897742c0b8 \
                    file://src/plugins/intel_cpu/thirdparty/onednn/LICENSE;md5=3b64000f6e7d52516017622a37a94ce9 \
                    file://src/plugins/intel_gpu/thirdparty/onednn_gpu/LICENSE;md5=3b64000f6e7d52516017622a37a94ce9 \
                    file://node-api-headers-src/LICENSE;md5=6adb2909701d4605b4b2ae1a9b25d8bd \
                    file://node-addon-api-src/LICENSE.md;md5=0492ef29a9d558a3e9660e7accc9ca6a \
"

inherit cmake python3native pkgconfig qemu

S = "${WORKDIR}/git"
EXTRA_OECMAKE += " \
                  -DCMAKE_CROSSCOMPILING_EMULATOR=${WORKDIR}/qemuwrapper \
                  -DENABLE_OPENCV=OFF \
                  -DENABLE_INTEL_GNA=OFF \
                  -DENABLE_SYSTEM_TBB=ON \
                  -DPYTHON_EXECUTABLE=${PYTHON} \
                  -DCMAKE_BUILD_TYPE=RelWithDebInfo \
                  -DTHREADING=TBB -DTBB_DIR="${STAGING_LIBDIR}/cmake/TBB" \
                  -DTREAT_WARNING_AS_ERROR=FALSE \
                  -DENABLE_DATA=FALSE \
                  -DENABLE_SYSTEM_PUGIXML=TRUE \
                  -DENABLE_OV_ONNX_FRONTEND=FALSE \
                  -DUSE_BUILD_TYPE_SUBFOLDER=OFF \
                  -DENABLE_FUZZING=OFF \
                  -DENABLE_TBBBIND_2_5=OFF \
                  -DCPACK_GENERATOR=RPM \
                  -DENABLE_SYSTEM_FLATBUFFERS=ON \
                  -DENABLE_SYSTEM_SNAPPY=ON \
                  -DENABLE_MLAS_FOR_CPU=OFF \
                  -DFETCHCONTENT_BASE_DIR="${S}" \
                  "
EXTRA_OECMAKE:append:aarch64 = " -DARM_COMPUTE_LIB_DIR=${STAGING_LIBDIR} "

DEPENDS += "\
            flatbuffers-native \
            pugixml \
            python3-pybind11 \
            python3-pybind11-native \
            python3-scons-native \
            qemu-native \
            snappy \
            tbb \
            "
DEPENDS:append:aarch64 = " arm-compute-library"


#COMPATIBLE_HOST = '(x86_64).*-linux'
COMPATIBLE_HOST:libc-musl = "null"

PACKAGECONFIG ?= "samples"
PACKAGECONFIG[opencl] = "-DENABLE_INTEL_GPU=TRUE, -DENABLE_INTEL_GPU=FALSE, virtual/opencl-icd opencl-headers opencl-clhpp,"
PACKAGECONFIG[python3] = "-DENABLE_PYTHON=ON -DPYTHON_LIBRARY=${PYTHON_LIBRARY} -DPYTHON_INCLUDE_DIR=${PYTHON_INCLUDE_DIR} -DENABLE_PYTHON_PACKAGING=ON, -DENABLE_PYTHON=OFF, python3-cython-native patchelf-native, python3 python3-numpy python3-progress python3-cython"
PACKAGECONFIG[samples] = "-DENABLE_SAMPLES=ON -DENABLE_COMPILE_TOOL=ON, -DENABLE_SAMPLES=OFF -DENABLE_COMPILE_TOOL=OFF, opencv"
PACKAGECONFIG[verbose] = "-DVERBOSE_BUILD=1,-DVERBOSE_BUILD=0"

do_configure:prepend() {
    # Dont set PROJECT_ROOT_DIR
    sed -i -e 's:\${OpenVINO_SOURCE_DIR}::;' ${S}/src/CMakeLists.txt

    # qemu wrapper that can be used by cmake to run target binaries.
    qemu_binary="${@qemu_wrapper_cmdline(d, d.getVar('STAGING_DIR_HOST'), [d.expand('${STAGING_DIR_HOST}${libdir}'),d.expand('${STAGING_DIR_HOST}${base_libdir}')])}"
    cat > ${WORKDIR}/qemuwrapper << EOF
#!/bin/sh
$qemu_binary "\$@"
EOF
    chmod +x ${WORKDIR}/qemuwrapper
}

do_install:append() {
    rm -rf ${D}${prefix}/install_dependencies
    rm -rf ${D}${prefix}/setupvars.sh

    find ${B}/src/plugins/intel_cpu/cross-compiled/ -type f -name *_disp.cpp -exec sed -i -e 's%'"${S}"'%'"${TARGET_DBGSRC_DIR}"'%g' {} +
}

# Otherwise e.g. ros-openvino-toolkit-dynamic-vino-sample when using dldt-inference-engine uses dldt-inference-engine WORKDIR
# instead of RSS
SSTATE_SCAN_FILES:append = " *.cmake"

FILES:${PN} += "\
                ${libdir}/openvino-${PV}/lib*${SOLIBSDEV} \
                ${libdir}/openvino-${PV}/plugins.xml \
                ${libdir}/openvino-${PV}/cache.json \
                "

# Move inference engine samples into a separate package
PACKAGES =+ "${PN}-samples"

FILES:${PN}-samples = "${datadir}/openvino \
                       ${bindir} \
                       ${libdir}/libformat_reader.a \
                       ${libdir}/libopencv_c_wrapper.a \
                       "
RDEPENDS:${PN}-samples += "python3-core"

# Package for inference engine python API
PACKAGES =+ "${PN}-python3"

FILES:${PN}-python3 = "${PYTHON_SITEPACKAGES_DIR}"

UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>(\d+\.\d+\.\d+))$"
