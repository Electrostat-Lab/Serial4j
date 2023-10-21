<p>  <a href="https://software-hardware-codesign.github.io/Serial4j-v1"> <img src="https://github.com/Software-Hardware-Codesign/Serial4j-v1/blob/master/.repo-resources/assets/serial-4j-rounded-corners.png" height=80 width=100/> </a> </p> 

# Serial4j-API:

A Java terminal IO library designed to communicate and control the serial (RS232) drivers.

## The Serial4j Architectural HAL:

![image](https://user-images.githubusercontent.com/60224159/189999625-fd667e7c-b219-4aa8-a91f-c9809dcef225.png)

Serial4j is a terminal I/O library primarily built on top of POSIX base file API `<fcntl>` for file control operations, `<unistd>` for Unix std R/W operations, `<dirent>` for directory entry dir operations, and `<sys/ioctl>` for controlling modem bits.

## Quick Overview:
### Software Specification:

| What's the problem? | How does Serial4j approach the problem? | Does Serial4j provide a threading/processing model? | Can I use `libserial4j` dynamic library only? | Is it easy to build my own serial monitor on top of Serial4j? | From where should I start, should I learn the Unix/POSIX interface first? | What about other operating systems and machines? | What's jMonkeyEngine? | Can I use Serial4j with other game engines (C++/Python)? |
|---------------------|-----------------------------------------|-----------------------------------------------------|------------------------------------------------|------------------|----------------------------------|--------------------------------------|------------------------------|--------------------------|
| Controlling the serial ports with the most possible minimalistic approach without irrelevant multithreading/processing bloatware. | Serial4j ports the Unix/POSIX standard terminal IO library to the Java platform without adding extra threading stuff, leaving them to the user application, the framework is sub-divided into layered APIs, of which the classes starting with `Native-` prefix acts as the literal native library. | No, it doesn't; but as it evolves, it may provide a threading model, however mostly on a different module. | Yes, you can do this and there are ongoing optimizations to remove the JNI source binaries for C++ applications cross-compatibility. | Yes, yes, and yes, the `serial/monitor` package has the right tools! | Currently, you can start testing the examples provided in the `serial4j-examples` modules, but knowing how the Unix terminal works will help you to understand the bit manipulations taking place at the terminal flags part. | The library hasn't been tested on Windows, Mac, and Android, yet, it should work on Unix/POSIX standard-based systems, that include Mac and Android! | jMonkeyEngine is a complete code-first approach modern 3D game engine written primarily in Java, Serial4j has an example operating a serial monitor inside a game, COOL! | Of course, you CAN, as Serial4j is built into a dynamic library `libserial4j` that's independent of Java! |

### Java Implementation of the binding library:
_**Preface**_: The binding library consists of the main package `serial4j.core.terminal` which houses the terminal control facilities for opening, reading and writing to 
a terminal device in addition to setting the terminal line speed (baud rate), controlling the input and output queues and controlling the UI of the terminal driver.  
- The API `serial4j.core.terminal` houses the terminal IO control and the native IO streams, it is considered the backbone of the Serial4j framework, the monitor API widely utilizes it.
- The API `serial4j.core.serial` houses the serial monitor and the serial port that is internally manipulated using the terminal IO API.
- The API `serial4j.core.flag` houses the interface for the appendable bit masks.
- The API `serial4j.core.modem` houses the modem bits (pins) controller based on the standard DB-25 RS232 port driver, with the help of the Terminal API, a developer acquires full control on the serial ports.
- The API `serial4j.core.errno` houses an interpreter pattern that parses native runtime caught errno codes, and convert them into Java exception thrown against the JRE, a developer is capable of catching them and recovering based on their preferences.

> An average daily code should directly interact with `serial4j.core.serial` API, particularly the `SerialMonitor` API.
> Custom setups will usually require the use of the terminal API, too. Of note that each serial monitor instance is associated with a terminal device instance 
> which is available with a single indirection operation via `SerialMonitor#getTerminalDevice()`, and available from the serial monitor entities (read/write entities),
> as well.

### Java Binding library for the native API:
The Java Native Interface API is a private API, and subjected to breaking changes in the contemporary future releases. Developers are 
discouraged to utilize it via the Java Reflection API, you are very welcome to open an issue and explain the library defects that forced 
you to utilize the native API.

### Compiling, building and testing examples:
```bash
# building and assembling
┌─[pavl-machine@pavl-machine]─[/home/twisted/GradleProjects/Serial4j-v1]
└──╼ $./gradlew :serial4j:build && 
       ./gradlew :serial4j-native:compileX86_64 && 
       ./gradlew :serial4j-native:copyBinaries && 
       ./gradlew :serial4j-native:assemble && 
       ./gradlew :serial4j-native:copyToExample
 
 # selectively running an example
 ┌─[pavl-machine@pavl-machine]─[/home/twisted/GradleProjects/Serial4j-v1]
 └──╼ $./gradlew :serial4j-examples:run --args="com.serial4j.example.jme.RollingTheMonkey  /dev/ttyUSB0"
 ```

### The framework implementation is based on these resources and technologies: 

| The Linux Programming Interface & The Man Page                                                                                           | Advanced Programming in the Unix Environment by Richard Stevens                                                                                                                              | Java & JNI |
|------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------|
| <a href="https://man7.org/index.html"> <img width=160 height=200 src="https://man7.org/tlpi/cover/TLPI-front-cover-small-256.png"/> </a> | <a href="https://www.amazon.com/Advanced-Programming-UNIX-Environment-3rd/dp/0321637739"> <img width=160 height=200 src="https://m.media-amazon.com/images/I/41RBNJ1IfZL._SY466_.jpg"/> </a> | <a href="https://docs.oracle.com/en/java/javase/21/"> <img width=100 height=80 src="https://docs.oracle.com/en/java/sp_common/shared-images/duke-jetsurf-no-cloud64.png"/> </a>  |

> For C/C++ references, find a language reference, either the [IBM's](https://www.ibm.com/docs/en/i/7.5?topic=c-ile-cc-language-reference) or the [GNU's](https://www.gnu.org/software/gnu-c-manual/gnu-c-manual.html), choose whichever suits you.
