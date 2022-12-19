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

# cut the working directory from its end by a one '/' delimiter
output_dir="${execution_dir%/*}"
# cut the working directory from its end by a one '/' delimiter again
project_root="${output_dir%/*}"

# include the JAVAHOME
source $project_root'/java-home.sh'
source $project_root'/constants.sh'
source $project_root'/build/assemble/variables.sh'

java=$java_home'/java'
