#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
#!/bin/sh

# export all locales as "en_US.UTF-8"
export LC_ALL="en_US.UTF-8"

canonical_link=`readlink -f ${0}`
execution_dir=`dirname $canonical_link`

source "${execution_dir}/variables.sh"

function run() {
    cd $project_root'/output/'$jar_folder
    $java --enable-preview -jar 'serial4j.jar'
}
