#!/bin/bash

lib="libserial4j"

ANDROID_NDK_LATEST_HOME="/home/twisted/Android/Sdk/ndk/22.0.7026061"
JAVA_HOME="-DJAVA_HOME"
NDK_HOME="-DNDK_HOME=$ANDROID_NDK_LATEST_HOME"
ARCH="-DARCH"
BUILD_ANDROID="-DBUILD_ANDROID"
ANDROID_ABI="-DANDROID_ABI"
ANDROID_PLATFORM="-DANDROID_PLATFORM=21"


arm64="aarch64-linux-android"
arm64_lib_directory="arm64-v8a"

arm32="armv7a-linux-androideabi"
arm32_lib_directory="armeabi-v7a"

intel32="i686-linux-android"
intel32_lib_directory="x86"

intel64="x86_64-linux-android"
intel64_lib_directory="x86_64"

# ----------------------

source_dir="./serial4j-native"
build_dir="build"
cmake_build_dir="${build_dir}/cmake-build/"

output_binary="${cmake_build_dir}${lib}"

root_relative_build_dir="./${source_dir}/${build_dir}/lib"
root_relative_cmake_build_dir="./${source_dir}/${cmake_build_dir}"

