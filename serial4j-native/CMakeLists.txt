cmake_minimum_required(VERSION 3.10)

# define a project with a version
project(serial4j VERSION 1.0)

# print java_home
message(STATUS "JAVA_HOME: ${JAVA_HOME}")
message(STATUS "Target Binary Instruction-set: ${ARCH}")

# Cmake variables
set(library "serial4j")

set(headers "${CMAKE_CURRENT_SOURCE_DIR}/src/include/"
            "${CMAKE_CURRENT_SOURCE_DIR}/src/include/linux/"
            "${CMAKE_CURRENT_SOURCE_DIR}/src/include/jni/"
            "${JAVA_HOME}/include/"
            "${JAVA_HOME}/include/linux/")

set(sources "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/"
            "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/jni/com_serial4j_core_serial_NativeTerminalDevice.cpp"
            "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/jni/com_serial4j_core_errno_NativeErrno.cpp"
            "${CMAKE_CURRENT_SOURCE_DIR}/src/lib/linux/TerminalDevice.cpp")


# add a library target
add_library(${library} SHARED ${sources})

# set both COMPILE_FLAGS and LINK_FLAGS to the specified binary architecture
set_target_properties(${library} PROPERTIES COMPILE_FLAGS "${ARCH}" LINK_FLAGS "${ARCH}")

# include headers for the target
target_include_directories(${library} PUBLIC ${headers})

# Start building the target
target_sources(${library} PUBLIC ${sources})
