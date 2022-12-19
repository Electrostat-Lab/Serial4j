#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
#!/bin/sh

canonical_link=`readlink -f ${0}`
project_dir=`dirname $canonical_link`

source "${project_dir}/setup-environment.sh"
source "${$project_dir}/constants.sh"

setupCURL

if [[ $? -gt 0 ]]; then 
    echo -e "${RED_C} --MajorTask@SetupCURL : Failed setting up CURL, check your connection and your storage"
	exit $?
else 
    echo -e "${GREEN_C} --MajorTask@SetupCURL : Curl Successfully settled up and ready to run."
fi

echo -e ${RESET_Cs}

downloadJdk

if [[ $? -gt 0 ]]; then
    echo -e "${RED_C} --MajorTask@Download-JDK-19 : Failed downloading jdk-19, check your storage and your permissions."
	exit $?
else 
    echo -e "${GREEN_C} --MajorTask@Download-JDK-19 : jdk-19 archive is downloaded successfully."
fi

echo -e ${RESET_Cs}

extractCompressedFile $jdk_compressed

if [[ $? -gt 0 ]]; then
    echo -e "${RED_C} --MajorTask@Extract-JDK-19 : Failed extracting jdk-19 archive, check your storage and your permissions."
	exit $?
else 
    echo -e "${GREEN_C} --MajorTask@Extract-JDK-19 : jdk-19 archive toolchains successfully extracted."
fi

echo -e ${RESET_Cs}

deleteJdkArchive

if [[ $? -gt 0 ]]; then
    echo -e "${RED_C} --MajorTask@Release-JDK-19-Archive : Failed deleting jdk-19 archive, archive not found."
	exit $?
else 
    echo -e "${GREEN_C} --MajorTask@Release-JDK-19-Archive : jdk-19 archive has been deleted successfully."
fi

echo -e ${RESET_Cs}

provokeReadWriteExecutePermissions './jdk-19'

if [[ $? -gt 0 ]]; then
    echo -e "${RED_C} --MajorTask@Provoke-Permissions-JDK-19 : Failed to provoke permissions, file not found or you aren't [root]."
	exit $?
else 
    echo -e "${GREEN_C} --MajorTask@Provoke-Permissions-JDK-19 : R/W/E Permssions are provoked successfully."
fi

echo -e ${RESET_Cs}
