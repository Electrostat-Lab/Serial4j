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

function clean() {
    if [[ -d $project_root'/output/'$jar_folder ]]; then
        rm -rf ${project_root}'/output/'$jar_folder
    fi
    return $?
} 
