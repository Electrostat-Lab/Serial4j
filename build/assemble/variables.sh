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

jar_folder='serial4j'
version_number=$GITHUB_REF_NAME
jar=${jar_folder}'.jar'

java_docs_folder=$jar_folder'-'$version_number'-javadoc'
java_docs_jar=$java_docs_folder'.jar'

# Manifest attributes
manifest='Manifest-Version: 1.0'
mainclass='Main-Class: com.serial4j.example.Launcher'
classpath='Class-Path: dependencies'
api_name='Name: '$jar_folder
version='Version: '$version_number

# cut the working directory from its end by a one '/' delimiter
build_dir="${assemble_dir%/*}"
# cut the working directory from its end by a one '/' delimiter again
project_root="${build_dir%/*}"
root_package='com.serial4j'
docs_dir=$project_root'/docs/'$java_docs_folder

source ${project_root}'/java-home.sh'
source ${build_dir}'/compile/variables.sh'
source ${project_root}'/constants.sh'

java_jar=${java_home}'/jar'
javadoc=${java_home}'/javadoc'
jar_tmp=$javabuild_directory"/$jar_folder"

manifest_file=$javabuild_directory'/'$jar_folder'/Manifest.mf'

