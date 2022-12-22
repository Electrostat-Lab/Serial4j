#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
#!/bin/sh

# export all locales as "en_US.UTF-8" for the gcc compiler 
export LC_ALL="en_US.UTF-8"

canonical_link=`readlink -f ${0}`
assemble_dir=`dirname $canonical_link`

source "${assemble_dir}/variables.sh"

if [[ ! -d $javabuild_directory ]]; then 
    chmod +x $project_root'/build/compile/compile.sh'
    cd $project_root'/build/compile'
    ./'compile.sh'
fi

echo "Assemble JAR"
echo -e $RESET_Cs
echo "--------Script start--------"

source "${assemble_dir}/script.sh"
source "${assemble_dir}/clean.sh"

clean
if (( $? > 0 )); then 
    echo -e "$RED_C Task@Clean : Failed"
else 
    echo -e "$CYAN_C Task@Clean : Completed"
fi

echo -e $RESET_Cs

makeOutputDir
if ((  $? > 0 ));  then
    echo -e "$RED_C Task@MakeOutputDirectory : Failed"
else
    echo -e "$WHITE_C Task@MakeOutputDirectory : Completed"
fi

echo -e $RESET_Cs

createManifest
if (( $? > 0 )); then
    echo -e "$RED_C Task@CreateJarManifest : Failed"
else
    echo -e "$ORANGE_C Task@CreateJarManifest : Completed"
fi

echo -e $RESET_Cs

generateDocs
if (( $? > 0 )); then
    echo -e "$RED_C Task@GenerateJavaDocs : Failed"
else
    echo -e "$ORANGE_C Task@GenerateJavaDocs : Completed"
fi

echo -e $RESET_Cs

addDesktopNativeDependencies
if (( $? > 0 )); then 
    echo -e "$RED_C Task@AddDesktopNativeDependencies : Failed"
else
    echo -e "$MAGNETA_C Task@AddDesktopNativeDependencies : Completed"
fi

echo -e $RESET_Cs

# addAndroidNativeDependencies
# if (( $? > 0 )); then 
#     echo -e "$RED_C Task@AddNativeDependencies : Failed"
# else
#     echo -e "$MAGNETA_C Task@AddNativeDependencies : Completed"
# fi

# echo -e $RESET_Cs

add_assets=`addAssets`
if (( add_assets > 0 )); then 
    echo -e "$RED_C Task@AddAssets : Failed -- AssetsNotFound"
else
    echo -e "$ORANGE_C Task@AddAssets : Completed"
fi

echo -e $RESET_Cs

createJar
if (( $? > 0 )); then 
    echo -e "$RED_C Task@CreateJar : Failed"
else
    echo -e "$ORANGE_C Task@CreateJar : Completed"
fi

echo -e $RESET_Cs

createDocsJar
if (( $? > 0 )); then 
    echo -e "$RED_C Task@CreateDocsJar : Failed"
else
    echo -e "$ORANGE_C Task@CreateDocsJar : Completed"
fi

echo -e $RESET_Cs

createOutput
if (( $? > 0 )); then 
    echo -e "$RED_C Task@CreateOutput : Failed"
else
    echo -e "$WHITE_C Task@CreateOutput : Completed"
fi

echo -e $RESET_Cs

removeManifest
if (( $? > 0 )); then 
    echo -e "$RED_C Task@RemoveManifest : Failed"
else
    echo -e "$WHITE_C Task@RemoveManifest : Completed"
fi

echo -e $RESET_Cs

echo "--------Script end--------"
