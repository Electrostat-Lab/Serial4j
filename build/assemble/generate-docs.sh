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

function generateDocs() {
    if [[ ! -e "${project_root}/docs" ]]; then
        mkdir -d "${project_root}/docs"
    fi
    $javadoc -sourcepath $project_root'/src/main/java/' -subpackages $root_package -d "${project_root}/${docs_dir}"
}
