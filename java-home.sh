#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
#!/bin/sh

# export all locales as "en_US.UTF-8" for the gcc compiler 
export LC_ALL="en_US.UTF-8"

# DONOT DELETE OR RENAME
## EDIT YOUR CUSTOM JAVAHOME FROM HERE, Ccoffee uses this custom java home to create new aliases for your script !

canonical_link=`readlink -f ${0}`
compile_dir=`dirname $canonical_link`

build_dir="${compile_dir%/*}"
project_dir="${build_dir%/*}"
java_home=$project_dir'/jdk-19/bin'

