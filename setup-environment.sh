#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
#!/bin/sh

canonical_link=`readlink -f ${0}`
project_dir=`dirname $canonical_link`

jdk_compressed='jdk.tar.gz'
jdk_folder='jdk-19'

# setup functions to download and extract toolchains/compilers

##
# Sets up the client url utility.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function setupCURL() {
    sudo apt-get install curl
    return $?
}

##
# Downloads jdk-19 from oracle domains.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function downloadJdk() {
    # download a machine specific jdk
    local machine=`uname -m`
    if [[ $machine -eq "x86_64" ]]; then 
        machine="x64"
    else
        machine="x86"
    fi
    
    curl https://download.oracle.com/java/19/archive/jdk-19_linux-${machine}_bin.tar.gz --output "${project_dir}/${jdk_compressed}"
    
    return $?
}

##
# Provokes R/W/E permissions to the specified files in the given folder.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function provokeReadWriteExecutePermissions() {
    local directory=$1
    # rwx = read-write-execute for the owner, -R for recursive search
    chmod +rwx -R $directory
    return $?
}

##
# Extracts a compressed `.tar.gz` file to an explicit target directory.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function extractCompressedFile() {
    local compressedFile=$1
    local targetDirectory=$2
    tar --overwrite -xf $compressedFile -C $targetDirectory
    return $?
}

##
# Deletes the jdk archive if exists.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function deleteArchive() {
    local archive=$1
    if [[ ! -e "$archive" ]]; then
        return 1    
    fi
    rm $archive
    return $?
}
