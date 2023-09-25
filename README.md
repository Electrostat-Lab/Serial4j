<p>  <a href="https://software-hardware-codesign.github.io/Serial4j-v1"> <img src="https://github.com/Software-Hardware-Codesign/Serial4j-v1/blob/master/.repo-resources/assets/serial-4j-rounded-corners.png" height=80 width=100/> </a> </p> 

# Serial4j-API:

A Java terminal io library designed to communicate and control the serial (RS232) drivers.

## The Serial4j Architectural HAL:

![image](https://user-images.githubusercontent.com/60224159/189999625-fd667e7c-b219-4aa8-a91f-c9809dcef225.png)

Serial4j is a terminal I/O library primarily built on top of POSIX base file API `<fcntl>` for file control operations, `<unistd>` for Unix std R/W operations and `<dirent>` for directory entry dir operations.

## Quick Overview:
- Software Specification:

| What's the problem? | How does Serial4j approach the problem? | Does Serial4j provide a threading/processing model? | Can I use `libserial4j` dynamic library only? | Is it easy to build my own serial monitor on top of Serial4j? | From where should I start, should I learn the Unix/POSIX interface first? | What about other operating systems and machines? | What's jMonkeyEngine? | Can I use Serial4j with other game engines (C++/Python)? |
|---------------------|-----------------------------------------|-----------------------------------------------------|------------------------------------------------|------------------|----------------------------------|--------------------------------------|------------------------------|--------------------------|
| Controlling the serial ports with the most possible minimalistic approach without irrelevant multithreading/processing bloatware. | Serial4j ports the Unix/POSIX standard terminal IO library to the Java platform without adding extra threading stuff, leaving them to the user application, the framework is sub-divided into layered APIs, of which the classes starting with `Native-` prefix acts as the literal native library. | No, it doesn't; but as it evolves, it may provide a threading model, however mostly on a different module. | Yes, you can do this and there are ongoing optimizations to remove the JNI source binaries for C++ applications cross-compatibility. | Yes, yes, and yes, the `serial/monitor` package has the right tools! | Currently, you can start testing the examples provided in the `serial4j-examples` modules, but knowing how the Unix terminal works will help you to understand the bit manipulations taking place at the terminal flags part. | The library hasn't been tested on Windows, Mac, and Android, yet, it should work on Unix/POSIX standard-based systems, that include Mac and Android! | jMonkeyEngine is a complete code-first approach modern 3D game engine written primarily in Java, Serial4j has an example operating a serial monitor inside a game, COOL! | Of course, you CAN, as Serial4j is built into a dynamic library `libserial4j` that's independent of Java! |

- Java Implementation of the binding library: 

| Entity/Class | Usages | 
|---------------|----------------|
| `com.serial4j.core.serial` | A java package that provides the base implementation code for the serial4j API to control a terminal device. |
| `TerminalDevice.java` | A java class that provides a final implementation code for the `NativeTerminalDevice.java`, the api opens, initializes and R/W from/to a terminal device. |
| `Permissions.java` | A java class that provides the permissions for the terminal io operations. |
| `BaudRate.java` | A java enum class that provides the constants for the available POSIX termios bit speed. |
| `SerialPort.java` | A java wrapper class that wraps a serial port with a name and a port descriptor. |
| `ReadConfiguration.java` | A java enum class that provides an implementation for the various read configurations defined by the POSIX `termios-c_cc.h`. |
| `NativeTerminalDevice.java` | A java class that provides a base implementation for the Serial4j base native code. |
|                           |                          |
| `com.serial4j.core.control` | A java package that provides the terminal flags for the terminal device including control, local, input and output flags. |
| `TerminalFlag.java` | A java abstract class that provides the base implementation for a terminal flag. | 
| `TerminalControlFlag.java` | A java class that provides an implementation for the POSIX terminal control mode flags, e.g: CREAD for receive enable, CS8 for charachter size 8-bits,...etc. |
| `TerminalLocalFlag.java` | A java class that provides an implementation for the POSIX terminal local mode flags, e.g: ECHO for enabling echoing charachters, ECHOCTL for enabling echoing control charachters visually on the terminal. |
| `TerminalInputFlag.java` | A java class that provides an implementation for the POSIX terminal input mode flags, e.g: IUTF8 for enabling utf-8 input, ICRNL for mapping (translating) CR into NL on input. | 
| `TerminalOutputFlag.java` | A java class that provides an implementation for the POSIX terminal output mode flags, e.g: OCRNL for mapping (translating) CR to NL on output. |
|                           |                          |
| `com.serial4j.core.throwable` | A java package that provides java exceptions as translations to C native error codes. |

- Java Binding library for the native API: 
> WIP.

- Direct Implementation of the POSIX base file/directory and terminal IO APIs:
> WIP.

- Unix/POSIX Base APIs:
> WIP.

- Quick usage:
> WIP.

- Compiling, building and testing examples:
> ```bash
> # build Java classes and generate native headers
> └──╼ $./gradlew :serial4j:build
> 
> BUILD SUCCESSFUL in 1s
> 4 actionable tasks: 4 up-to-date
> 
> # assemble Java class files into a Jar for production
> └──╼ $./gradlew :serial4j:assemble
> 
> BUILD SUCCESSFUL in 1s
> 4 actionable tasks: 4 up-to-date
> 
> # compile and link native binaries using cmake-gradle tasks
>  └──╼ $./gradlew :serial4j-native:compileX86_64 :serial4j-native:compileX86
>
> Task :serial4j-native:compileX86_64
> -- JAVA_HOME: /usr/lib/jvm/jdk-20
> -- Building binary for: Linux
> -- Target Binary Instruction-set: -m64
> -- Configuring done
> -- Generating done
> -- Build files have been written to: /home/twisted/GradleProjects/Serial4j-v1/serial4j-native/build/cmake-build
> [ 25%] Building CXX object CMakeFiles/serial4j.dir/src/lib/jni/com_serial4j_core_serial_NativeTerminalDevice.cpp.o
> [ 50%] Building CXX object CMakeFiles/serial4j.dir/src/lib/jni/com_serial4j_core_errno_NativeErrno.cpp.o
> [ 75%] Building CXX object CMakeFiles/serial4j.dir/src/lib/linux/TerminalDevice.cpp.o
> [100%] Linking CXX shared library libserial4j.so
> [100%] Built target serial4j
> renamed '././serial4j-native/build/cmake-build//libserial4j.so' -> '././serial4j-native/build/lib/linux/x86-64/libserial4j.so'
>
> Task :serial4j-native:compileX86
> -- JAVA_HOME: /usr/lib/jvm/jdk-20
> -- Building binary for: Linux
> -- Target Binary Instruction-set: -m32
> -- Configuring done
> -- Generating done
> -- Build files have been written to: /home/twisted/GradleProjects/Serial4j-v1/serial4j-native/build/cmake-build
> [ 25%] Building CXX object CMakeFiles/serial4j.dir/src/lib/jni/com_serial4j_core_serial_NativeTerminalDevice.cpp.o
> [ 50%] Building CXX object CMakeFiles/serial4j.dir/src/lib/jni/com_serial4j_core_errno_NativeErrno.cpp.o
> [ 75%] Building CXX object CMakeFiles/serial4j.dir/src/lib/linux/TerminalDevice.cpp.o
> [100%] Linking CXX shared library libserial4j.so
> [100%] Built target serial4j
> renamed '././serial4j-native/build/cmake-build//libserial4j.so' -> '././serial4j-native/build/lib/linux/x86/libserial4j.so'
> 
> BUILD SUCCESSFUL in 4s
> 4 actionable tasks: 2 executed, 2 up-to-date
>
> # assemble pre-compiled native binaries for the runtime loader (jSnapLoader)
> └──╼ $./gradlew :serial4j-native:copyBinaries &&
>       ./gradlew :serial4j-native:assemble &&
>       ./gradlew :serial4j-native:copyToExamples
>
> BUILD SUCCESSFUL in 1s
> 3 actionable tasks: 3 up-to-date
>
> BUILD SUCCESSFUL in 1s
> 3 actionable tasks: 3 up-to-date
> 
> BUILD SUCCESSFUL in 1s
> 3 actionable tasks: 1 executed, 2 up-to-date
> 
> # run an example selectively
> └──╼ $./gradlew :serial4j-examples:run
> ```
