SUMMARY = "OpenVINO(TM) Toolkit - Deep Learning Deployment Toolkit"
HOMEPAGE = "https://github.com/opencv/dldt"
DESCRIPTION = "This toolkit allows developers to deploy pre-trained \
deep learning models through a high-level C++ Inference Engine API \
integrated with application logic."

SRC_URI = "git://github.com/openvinotoolkit/openvino.git;protocol=https;name=openvino;branch=releases/2025/4;lfs=0 \
           git://github.com/openvinotoolkit/oneDNN.git;protocol=https;destsuffix=${BB_GIT_DEFAULT_DESTSUFFIX}/src/plugins/intel_cpu/thirdparty/onednn;name=mkl;nobranch=1 \
           git://github.com/oneapi-src/oneDNN.git;protocol=https;destsuffix=${BB_GIT_DEFAULT_DESTSUFFIX}/src/plugins/intel_gpu/thirdparty/onednn_gpu;name=onednn;nobranch=1 \
           git://github.com/herumi/xbyak.git;protocol=https;destsuffix=${BB_GIT_DEFAULT_DESTSUFFIX}/thirdparty/xbyak;name=xbyak;branch=master \
           git://github.com/openvinotoolkit/mlas.git;protocol=https;destsuffix=${BB_GIT_DEFAULT_DESTSUFFIX}/src/plugins/intel_cpu/thirdparty/mlas;name=mlas;nobranch=1 \
           git://github.com/nodejs/node-addon-api.git;protocol=https;destsuffix=${BB_GIT_DEFAULT_DESTSUFFIX}/node-addon-api-src;name=node-addon-api;nobranch=1 \
           git://github.com/openvinotoolkit/telemetry.git;protocol=https;destsuffix=${BB_GIT_DEFAULT_DESTSUFFIX}/thirdparty/telemetry;name=telemetry;nobranch=1;lfs=0 \
           git://github.com/openvinotoolkit/googletest.git;protocol=https;destsuffix=${BB_GIT_DEFAULT_DESTSUFFIX}/thirdparty/gtest/gtest;name=gtest;nobranch=1;lfs=0 \
           file://0001-node-addon-use-system-node-api-headers.patch \
           file://0001-cmake-yocto-specific-tweaks-to-the-build-process.patch \
           file://0002-cmake-Fix-overloaded-virtual-error.patch \
           file://0001-Fix-dependencies-to-use-system.patch \
           file://0004-fix-python-detection.patch \
           file://0004-Don-t-detect-arm-compute-library-version.patch \
           file://0001-intel_cpu-remove-executable-stack-flag-from-libopenv.patch \
           file://0001-RecordProperty-serializes-ints-and-64-bit-ints-inclu.patch;patchdir=thirdparty/gtest/gtest \
           file://0001-Don-t-error-out-on-CI_BUILD_NUMBER-not-defined.patch \
           file://0005-Use-system-zlib.patch \
           file://0005-ittapi-prefer-system-ittnotify-over-bundled-source.patch \
           "

SRCREV_openvino = "85e49f27be1b1647a7ec331069b053596d1112f8"
SRCREV_mkl = "a4ed4a789b6e0869e4f651bbfeff6878e91d388e"
SRCREV_onednn = "29d64fe0ec0f1f20d7f80aa76630d58a6011a869"
SRCREV_xbyak = "0d67fd1530016b7c56f3cd74b3fca920f4c3e2b4"
SRCREV_gtest = "99760ac1776430f3df65947992bf4e8ebc0d7660"
SRCREV_mlas = "d1bc25ec4660cddd87804fcf03b2411b5dfb2e94"
SRCREV_node-addon-api = "6babc960154752f686a7dca8e712991a976a754b"
SRCREV_telemetry = "8abddc3dbc8beb04a39b5ea40cbba5020317102f"
SRCREV_FORMAT = "openvino_mkl_onednn_xbyak_ade_node-addon-api_mlas_telemetry_gtest"

LICENSE = "Apache-2.0 & MIT & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
                    file://thirdparty/xbyak/COPYRIGHT;md5=3c98edfaa50a86eeaef4c6109e803f16 \
                    file://thirdparty/cnpy/LICENSE;md5=689f10b06d1ca2d4b1057e67b16cd580 \
                    file://src/plugins/intel_cpu/thirdparty/mlas/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
                    file://src/plugins/intel_cpu/thirdparty/onednn/LICENSE;md5=3b64000f6e7d52516017622a37a94ce9 \
                    file://src/plugins/intel_gpu/thirdparty/onednn_gpu/LICENSE;md5=05fda7e0b3a0fe6749e8443316fc9a3f \
                    file://node-addon-api-src/LICENSE.md;md5=fc3ff1120869be6b3cce17f9a06bfe2e \
                    file://thirdparty/telemetry/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
                    file://thirdparty/gtest/gtest/LICENSE;md5=cbbd27594afd089daa160d3a16dd515a \
"

inherit cmake python3targetconfig pkgconfig qemu

EXTRA_OECMAKE += " \
                  -DCMAKE_CROSSCOMPILING_EMULATOR=${WORKDIR}/qemuwrapper \
                  -DENABLE_OPENCV=OFF \
                  -DENABLE_INTEL_GNA=OFF \
                  -DENABLE_SYSTEM_TBB=ON \
                  -DCMAKE_BUILD_TYPE=RelWithDebInfo \
                  -DTHREADING=TBB -DTBB_DIR="${STAGING_LIBDIR}/cmake/TBB" \
                  -DTREAT_WARNING_AS_ERROR=FALSE \
                  -DENABLE_DATA=FALSE \
                  -DENABLE_SYSTEM_GFLAGS=ON \
                  -DENABLE_SYSTEM_PROTOBUF=ON \
                  -DProtobuf_USE_STATIC_LIBS=OFF \
                  -DCMAKE_CXX_STANDARD=17 \
                  -DProtobuf_PROTOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/protoc \
                  -DENABLE_SYSTEM_PUGIXML=TRUE \
                  -DENABLE_OV_ONNX_FRONTEND=FALSE \
                  -DUSE_BUILD_TYPE_SUBFOLDER=OFF \
                  -DENABLE_FUZZING=OFF \
                  -DENABLE_TBBBIND_2_5=OFF \
                  -DCPACK_GENERATOR=RPM \
                  -DENABLE_SYSTEM_FLATBUFFERS=ON \
                  -DENABLE_SYSTEM_SNAPPY=ON \
                  -DFETCHCONTENT_BASE_DIR="${S}" \
                  -DENABLE_INTEL_NPU=OFF \
                  -DPYTHON3_CONFIG="python3-config" \
                  -DENABLE_OV_JAX_FRONTEND=OFF \
                  -DCMAKE_POLICY_VERSION_MINIMUM=3.5 \
                  -DENABLE_SYSTEM_ZLIB=ON \
                  "
EXTRA_OECMAKE:append:aarch64 = " -DARM_COMPUTE_LIB_DIR=${STAGING_LIBDIR} "

DEPENDS += "\
            flatbuffers-native \
            nlohmann-json \
            gflags \
            protobuf \
            protobuf-native \
            pugixml \
            python3-pybind11 \
            python3-scons-native \
            qemu-native \
            snappy \
            tbb \
            zlib \
            "
DEPENDS:append:aarch64 = " arm-compute-library"


#COMPATIBLE_HOST = '(x86_64).*-linux'
COMPATIBLE_HOST:libc-musl = "null"

PACKAGECONFIG ?= "samples"
PACKAGECONFIG[itt] = "-DENABLE_PROFILING_ITT=BASE, -DENABLE_PROFILING_ITT=OFF, itt,"
PACKAGECONFIG[opencl] = "-DENABLE_INTEL_GPU=TRUE, -DENABLE_INTEL_GPU=FALSE, virtual/libopencl1 opencl-headers opencl-clhpp,"
PACKAGECONFIG[python3] = "-DENABLE_PYTHON=ON -DENABLE_PYTHON_PACKAGING=ON, -DENABLE_PYTHON=OFF, patchelf-native, python3 python3-numpy python3-progress"
PACKAGECONFIG[node] = "-DENABLE_JS=ON -DENABLE_SYSTEM_NODE=ON,-DENABLE_JS=OFF, nodejs"
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

    # Install the Node.js addon (excluded from cmake install by CPACK RPM packaging)
    if [ -f ${S}/bin/intel64/ov_node_addon.node ]; then
        install -d ${D}${libdir}
        install -m 0755 ${S}/bin/intel64/ov_node_addon.node ${D}${libdir}/ov_node_addon.node
    fi
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

# Package for Node.js bindings
PACKAGES =+ "${PN}-node"

FILES:${PN}-node = "${libdir}/ov_node_addon.node"

RDEPENDS:${PN}-node += "nodejs ${PN}"

UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>(\d+\.\d+\.\d+))$"
