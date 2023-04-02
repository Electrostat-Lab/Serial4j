#!/bin/bash

source "./helper-scripts/abstract/abstract-util.sh"
source "./helper-scripts/project-impl/variables.sh"

java_home=${1}

function compileAndroid() {
    local target=$1

    local temp=$(pwd)
    cd ${source_dir}
    cmake "${JAVA_HOME}=$java_home" \
          "${BUILD_ANDROID}=true" \
          "${NDK_HOME}" \
          "${ANDROID_ABI}=$target" \
          "${ANDROID_PLATFORM}" \
          -v --build -S . -B ./${cmake_build_dir}

    cmake --build ./${cmake_build_dir}
    cd ${temp}
}

function moveBinary() {
    local folder=$1    
    mkdir -p "${root_relative_build_dir}/${folder}"
    mv -v "${root_relative_cmake_build_dir}/$lib.so" "${root_relative_build_dir}/${folder}/$lib.so"
}

function compileAndroidArm32() {
    compileAndroid ${arm32}
    moveBinary ${arm32_lib_directory}
}

function compileAndroidArm64() {
    compileAndroid ${arm64} 
    moveBinary ${arm64_lib_directory}
}

function compileAndroidIntel32() {
    compileAndroid ${intel32} 
    moveBinary ${intel32_lib_directory}
}

function compileAndroidIntel64() {
    compileAndroid ${intel64} 
    moveBinary ${intel64_lib_directory}
}

compileAndroidArm32
compileAndroidArm64
compileAndroidIntel32
compileAndroidIntel64
