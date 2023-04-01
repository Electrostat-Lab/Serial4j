#!/bin/bash

java_home=${1}
arch=${2}

cmake -DJAVA_HOME="${java_home}" -DARCH="${arch}" -v --build -S ./serial4j-native/ -B ./serial4j-native/build/ 
cmake --build ./serial4j-native/build/

