#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
#!/bin/sh

# export all locales as "en_US.UTF-8" for the gcc compiler 
export LC_ALL="en_US.UTF-8"

canonical_link=`readlink -f ${0}`
compile_dir=`dirname $canonical_link`

source "${compile_dir}/variables.sh"

##
# Compile and build native sources.
#
# @echo Script Succeeded if all the commands have passed successfully, exit with error code otherwise.
##
function compile() {
    native_sources=`find $nativessrc_directory'/lib' -name '*.c' -o -name '*.cxx' -o -name '*.cpp' -o -name '*.c++'-o -name '*.ino'`
    # tests if the sources exist, then give the current user full permissions on them and compile them
    if [[ $native_sources ]]; then  
        chmod +x $native_sources
        # compile and build a shared lib for linux systems
        if [[ `linux_x86_x64 "${native_sources}"` -eq 0 ]]; then
            echo -e "$GREEN_C Task@Build Linux-x86-x64 : Succeeded"
        else
            echo -e "$RED_C Task@Build Linux-x86-x64 : Failed"
            echo -e "$RED_C Exiting Script with error 150"
            exit 150
        fi
        # compile and build a shared lib for android systems
        if [[ $enable_android_build == true ]]; then
             if [[ `linux_android "${arm64}" "${arm64_lib}" "${native_sources}" "${min_android_sdk}"` -eq 0 ]]; then
                echo -e "$GREEN_C Task@Build Android-Arm-64 : Succeeded"
             else
                echo -e "$RED_C Task@Build Android-Arm-64 : Failed"
                echo -e "$RED_C Exiting Script with error 250"
                exit 250
             fi
             
             if [[ `linux_android "${arm32}" "${arm32_lib}" "${native_sources}" "${min_android_sdk}"` -eq 0 ]]; then 
                echo -e "$GREEN_C Task@Build Android-Arm-32 : Succeeded"
             else
                echo -e "$RED_C Task@Build Android-Arm-32 : Failed"
                echo -e "$RED_C Exiting Script with error 350"
                exit 350
             fi
             
             if [[ `linux_android "${intel64}" "${intel64_lib}" "${native_sources}" "${min_android_sdk}"` -eq 0 ]]; then
                echo -e "$GREEN_C Task@Build Android-Intel-64 : Succeeded"
             else 
                echo -e "$RED_C Task@Build Android-Intel-64 : Failed"
                echo -e "$RED_C Exiting Script with error 450"
                exit 450
             fi
             
             if [[ `linux_android "${intel32}" "${intel32_lib}" "${native_sources}" "${min_android_sdk}"` -eq 0 ]]; then 
                echo -e "$GREEN_C Task@Build Android-Intel-32 : Succeeded"
             else
                echo -e "$RED_C Task@Build Android-Intel-32 : Failed"
                echo -e "$RED_C Exiting Script with error 550"
                exit 550
             fi
        fi
    fi
    echo -e "$GREEN_C---MajorTask@Build Native Sources : Succeeded---"
} 

##
# Build for desktop linux systems
#
# @param nativeSources sources to be compiled for linux desktop.
# @return 0 if command passes, non zero number otherwise with exit code 150 (search the code on repo's wiki).
##
function linux_x86_x64() {
    local native_sources=$1
    if [[ ! -d $linux_natives_dir'/linux-x86-x64' ]]; then
        mkdir -p $linux_natives_dir'/linux-x86-x64'
    fi
    $gcc -fPIC $native_sources -shared -o $linux_natives_dir'/linux-x86-x64/'${clibName} \
        -I${java_home%/*}'/include' \
        -I${java_home%/*}'/include/linux' \
        -I${nativessrc_directory}'/include/linux/' \
        -I${nativessrc_directory}'/include/' \

    return $?
}

##
# Building native code for arm and intel android.
#
# @param triple the ABI triple name, also used for -target flag of the clang++.
# @param folder the created folder name.
# @param sources the sources to compile and build an object file for them.
# @param min_android_sdk the minimum android sdk to compile against.
# @return 0 if command passes, non zero number otherwise.
##
function linux_android() {
    # parameters attributes
    local triple=$1
    local folder=$2
    local sources=$3
    local min_android_sdk=$4
    
    if [[ ! -d $android_natives_dir ]]; then
        mkdir $android_natives_dir
    fi
    
    if [[ ! -d $android_natives_dir"/$folder" ]]; then
        mkdir $android_natives_dir"/$folder"
    fi
    $ndk_home'/toolchains/llvm/prebuilt/linux-x86_64/bin/clang++' -target ${triple}${min_android_sdk} \
        -fPIC $sources -shared \
        -stdlib=libstdc++ \
        -o $android_natives_dir"/$folder/"${clibName} \
        -I$nativessrc_directory'/includes' \
        -I$ndk_home'/sources/cxx-stl/llvm-libc++/include' \
        -lc++_shared
    result=$?
    cp $ndk_home"/sources/cxx-stl/llvm-libc++/libs/${folder}/libc++_shared.so" $android_natives_dir"/$folder"
    return $result
}
