/**
 * @file com_serial4j_core_serial_NativeTerminalDevice.c
 * @author pavl_g.
 * @brief Instantiates a native interface of Serial4j API for the java programming language.
 * @version 0.1
 * @date 2022-09-06
 * 
 * @copyright 
 * BSD 3-Clause License
 *
 * Copyright (c) 2022, Scrappers Team, The AVR-Sandbox Project, Serial4j API.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
#include<jni/com_serial4j_core_serial_NativeTerminalDevice.h>
#include<errno.h>
#include<TerminalDevice.h>
#include<stdlib.h>
#include<JniUtils.h>

AddressesBuffer serialPorts;

/**
 * @brief Deprecated for removal.
 */
JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setupJniEnvironment0
  (JNIEnv* env, jclass clazz) {
    return JniUtils::setupJavaEnvironment(env, JNI_VERSION_1_8);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setTerminalControlFlag
  (JNIEnv* env, jobject object, jlong flag) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    return TerminalDevice::setTerminalControlFlag(flag, &fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setTerminalLocalFlag
  (JNIEnv* env, jobject object, jlong flag) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    return TerminalDevice::setTerminalLocalFlag(flag, &fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setTerminalInputFlag
  (JNIEnv* env, jobject object, jlong flag) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    return TerminalDevice::setTerminalInputFlag(flag, &fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setTerminalOutputFlag
  (JNIEnv* env, jobject object, jlong flag) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    return TerminalDevice::setTerminalOutputFlag(flag, &fd);
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getTerminalControlFlag
  (JNIEnv* env, jobject object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    return (jlong) TerminalDevice::getTerminalControlFlag(&fd);
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getTerminalLocalFlag
  (JNIEnv* env, jobject object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    return (jlong) TerminalDevice::getTerminalLocalFlag(&fd);
} 

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getTerminalInputFlag
  (JNIEnv* env, jobject object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    return (jlong) TerminalDevice::getTerminalInputFlag(&fd);
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getTerminalOutputFlag
  (JNIEnv* env, jobject object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    return (jlong) TerminalDevice::getTerminalOutputFlag(&fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setReadConfigurationMode0
  (JNIEnv* env, jobject object, jshort timeoutValue, jshort minimumBytes) {
    
    jint fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    /* Narrowing off the [jshort] values to [unsigned char]
       will drop the bits that are not in the range of the unsigned char */
    cc_t readConfig[READ_CONFIG_SIZE] = {(cc_t) timeoutValue, (cc_t) minimumBytes};

    return TerminalDevice::setReadConfigurationMode(readConfig, &fd);
}

JNIEXPORT jshortArray JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getReadConfigurationMode0
  (JNIEnv* env, jobject object) {

    cc_t readConfig[READ_CONFIG_SIZE];
    int fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    TerminalDevice::getReadConfigurationMode(readConfig, &fd);
    /* wrap the incompatible data type into a jint primitive array */
    /* Note: at this point, casting from [unsigned char] to [short] is implicit! */
    jshort s_readConfig[READ_CONFIG_SIZE] = {readConfig[0], readConfig[1]};
    /* create a jintArray from a primitive jint C-array */
    jshortArray array = JniUtils::getShortArrayFromBuffer(env, s_readConfig, READ_CONFIG_SIZE);

    return array;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_fetchSerialPorts0
  (JNIEnv* env, jobject object) {

     int state = TerminalDevice::fetchSerialPorts(&serialPorts);
     int length = *serialPorts.getAddressesCount();
     jobjectArray stringArray = JniUtils::getStringArrayFromBuffer(env, (const char**) serialPorts.getStartAddress(), length);
     JniUtils::setObjectField(env, &object, "serialPorts", "[Ljava/lang/String;", stringArray);
     serialPorts.deallocateAll();

     return state;  
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_writeData0
  (JNIEnv* env, jobject object, jint buffer) {
    int fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    return TerminalDevice::writeData(&buffer, 1, &fd);
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_readData0
  (JNIEnv* env, jobject object) {

    int fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    cc_t readConfig[READ_CONFIG_SIZE];
    TerminalDevice::getReadConfigurationMode(readConfig, &fd);
    int length = (readConfig[0] <= 0 ? readConfig[0] : 1);
    jchar buffer[length];
    int state = TerminalDevice::readData((void*) &buffer, length, &fd);
    jcharArray arrayBuffer = JniUtils::getCharArrayFromBuffer(env, buffer, length);
    JniUtils::setObjectField(env, &object, "readData", "[C", arrayBuffer);

    return state;
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_writeBuffer0
  (JNIEnv* env, jobject object, jstring string, jint length) {

    int fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    const char* buffer = JniUtils::getBufferFromString(env, string);
    int state = TerminalDevice::writeData(buffer, length, &fd);
    env->ReleaseStringUTFChars(string, buffer);

    return state;
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_readBuffer0
  (JNIEnv* env, jobject object) {

    int fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    /* construct a termios and get the control charachter flag from fd */
    struct termios tty;
    TerminalDevice::getTermiosFromFd(&tty, &fd);
    int length = tty.c_cc[VMIN];
    char strBuffer[length];
    long numberOfReadChars = TerminalDevice::readData((void*) &strBuffer, length, &fd);
    /* get the java string buffer and setup its data with the buffer */
    JniUtils::setObjectField(env, &object, "readBuffer", "Ljava/lang/String;", env->NewStringUTF(strBuffer));

    return numberOfReadChars;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setBaudRate0
  (JNIEnv* env, jobject object, jint baudRate) {
    int fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    return TerminalDevice::setBaudRate(baudRate, &fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getBaudRate0
  (JNIEnv* env, jobject object) {
    int fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    return TerminalDevice::getBaudRate(&fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_initTermios0
  (JNIEnv* env, jobject object) {
    int fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    return TerminalDevice::initTermios(&fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_openPort0
  (JNIEnv* env, jobject object, jstring string, jint flag) {

    const char* buffer = JniUtils::getBufferFromString(env, string);
    int fd = TerminalDevice::openPort(buffer, flag);
    jobject serialPortObject = JniUtils::getSerialPortFromTerminalDevice(env, &object);
    JniUtils::setIntField(env, &serialPortObject, "portOpened", "I", 1);
    JniUtils::setIntField(env, &serialPortObject, "fd", "I", fd);
    JniUtils::setIntField(env, &serialPortObject, "ioFlag", "I", flag);
    env->ReleaseStringUTFChars(string, buffer);

    return fd;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_closePort0
  (JNIEnv* env, jobject object) {
    
    jobject serialPortObject = JniUtils::getSerialPortFromTerminalDevice(env, &object);
    int fd = JniUtils::getPortDescriptorFromSerialPort(env, &object);
    JniUtils::setIntField(env, &serialPortObject, "portOpened", "I", 0);
    JniUtils::setIntField(env, &serialPortObject, "fd", "I", 0);
    JniUtils::setObjectField(env, &serialPortObject, "path", "Ljava/lang/String;", env->NewStringUTF(""));

    return TerminalDevice::closePort(&fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getErrno0
  (JNIEnv* env, jobject object) {
    return errno;
}