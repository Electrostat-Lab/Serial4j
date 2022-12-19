#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
#!/bin/sh

# export all locales as "en_US.UTF-8" for the gcc compiler 
export LC_ALL="en_US.UTF-8"

echo "Compiling the project"
echo -e $RESET_Cs
echo "--------Script start--------"

canonical_link=`readlink -f ${0}`
compile_dir=`dirname $canonical_link`

source "${compile_dir}/build-java.sh"
source "${compile_dir}/build-natives.sh"

if [[ $enable_java_build == true ]]; then
    echo -e "$ORANGE_C---MajorTask@Build Java Sources : Java build started"
    copyJavaSources
    generateHeaders
    if [[ $? -eq 0 ]]; then
       moveHeaders
       echo -e "$GREEN_C Task@Build Compile Java : Succeeded"
    else
        echo -e "$RED_C Task@Build Compile Java : Failed"
    fi
    echo -e "$ORANGE_C---MajorTask@Build Java Sources : Java build finished"
fi

echo -e $RESET_Cs

if [[ $enable_natives_build == true ]]; then
    echo -e "$MAGNETA_C---MajorTask@Build Native Sources : Native build started"
    compile
    echo -e "$MAGNETA_C---MajorTask@Build Native Sources : Native build finished"
fi

echo -e $RESET_Cs
echo "--------Script end--------"
