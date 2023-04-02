#!/bin/bash

source "./helper-scripts/abstract/abstract-util.sh"
source "./helper-scripts/project-impl/variables.sh"

java_home=${1}
folder=${2}
arch=${3}

function compileX86() {
    local temp=$(pwd)
    cd ${source_dir}
    cmake "${JAVA_HOME}=$java_home" \
          "${BUILD_ANDROID}=false" \
          "${ARCH}=$arch" \
          -v --build -S . -B ./${cmake_build_dir}
    cmake --build ./${cmake_build_dir}
    cd ${temp}
}

function moveBinary() {
    assignBinaryExtension
    lib="${lib}${ext}"
    mkdir -p "${root_relative_build_dir}/${system}/${folder}"
    mv -v "${root_relative_cmake_build_dir}/$lib" "${root_relative_build_dir}/${system}/${folder}/$lib"
}

compileX86
moveBinary
