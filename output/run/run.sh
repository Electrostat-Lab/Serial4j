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

source "${execution_dir}/script.sh"

echo -e "$ORANGE_C...................................It's Ccoffee time..................................."
echo -e $RESET_Cs
echo -e $WHITE_C
echo "........................................................................................................."


run

echo -e $RESET_Cs

