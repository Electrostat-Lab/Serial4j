cmake_minimum_required(VERSION 3.18.1)

# define a project with a version
project(serial4j VERSION 1.0)

# print java_home
message(STATUS "JAVA_HOME: ${JAVA_HOME}")

# assign the system dependent jni-headers identifier
if(CMAKE_SYSTEM_NAME STREQUAL "Windows")
    set(system "win32")
elseif(CMAKE_SYSTEM_NAME STREQUAL "Darwin")
    set(system "darwin")
else()
    set(system "linux")
endif()

# Cmake variables
set(library "serial4j")

set(jni_headers "${JAVA_HOME}/include/"
                "${JAVA_HOME}/include/${system}/")

set(headers "${CMAKE_CURRENT_SOURCE_DIR}/src/include/"
            "${CMAKE_CURRENT_SOURCE_DIR}/src/include/${system}/"
            "${CMAKE_CURRENT_SOURCE_DIR}/src/include/jni/"
            "${jni_headers}")

set(jni_sources "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/jni/com_serial4j_core_terminal_NativeTerminalDevice.cpp"
                "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/jni/com_serial4j_core_terminal_NativeFilePermissions.cpp"
                "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/jni/com_serial4j_core_errno_NativeErrno.cpp"
                "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/jni/com_serial4j_core_terminal_control_NativeTerminalFlags_ControlFlags.cpp"
                "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/jni/com_serial4j_core_terminal_control_NativeTerminalFlags_ControlFlags_MaskBits.cpp"
                "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/jni/com_serial4j_core_terminal_control_NativeTerminalFlags_InputFlags.cpp"
                "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/jni/com_serial4j_core_terminal_control_NativeTerminalFlags_LocalFlags.cpp"
                "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/jni/com_serial4j_core_terminal_control_NativeTerminalFlags_OutputFlags.cpp"
                "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/jni/com_serial4j_core_terminal_control_NativeTerminalFlags_OutputFlags_MaskBits.cpp"
                "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/jni/com_serial4j_core_terminal_NativeTerminalDevice_FileSeekCriterion.cpp")
set(sources "${jni_sources}"
            "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/linux/TerminalDevice.cpp"
            "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/AddressesBuffer.cpp")

# add a library target
add_library(${library} SHARED ${sources})

# set android properties if android build is enabled
if(BUILD_ANDROID STREQUAL "true")
    # required to pass the [NDK_HOME] and [ANDROID_ABI] and [ANDROID_PLATFORM] on the command-line
    set(CMAKE_C_COMPILER ${NDK_HOME}/toolchains/llvm/prebuilt/linux-x86_64/bin/clang)
    set(CMAKE_CXX_COMPILER ${NDK_HOME}/toolchains/llvm/prebuilt/linux-x86_64/bin/clang++)
    set(COMPILER_OPTIONS "-target ${ANDROID_ABI}${ANDROID_PLATFORM}") 

    message(STATUS "Building for Android")
    message(STATUS "Build for Android: ${ANDROID_ABI}")
    message(STATUS "Build for API: ${ANDROID_PLATFORM}")
else()
    set(COMPILER_OPTIONS "${ARCH}")
    message(STATUS "Building binary for: ${CMAKE_SYSTEM_NAME}")
    message(STATUS "Target Binary Instruction-set: ${ARCH}")
endif()

# set both COMPILE_FLAGS and LINK_FLAGS to the specified binary architecture
set_target_properties(${library} PROPERTIES COMPILE_FLAGS "${COMPILER_OPTIONS}" LINK_FLAGS "${COMPILER_OPTIONS}")

# include headers for the target
target_include_directories(${library} PUBLIC ${headers})

# Start building the target
target_sources(${library} PUBLIC ${sources})
