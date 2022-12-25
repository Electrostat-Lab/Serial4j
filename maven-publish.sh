#!/bin/bash

canonical_link=`readlink -f ${0}`
project_dir=`dirname $canonical_link`

source "${project_dir}/constants.sh"

function prepare_maven() {
	
	sudo apt-get install maven 

	return $?
}

function deploy() {
	
	local settings=$1
	local groupId=$2
	local artifactId=$3
	local file=$4
	local version=$5
	local url=$6
	
	mvn deploy:deploy-file --debug -s "${settings}" "-DgroupId=$groupId" \
						   "-DartifactId=$artifactId" \
						   "-Dversion=$version" \
						   "-Dpackaging=jar" \
						   "-Dfile=$file" \
						   "-DrepositoryId=github" \
						   "-Durl=$url"

	return $?
}

function publish() {
	groupId="com.avrsandbox.serial4j"
	version=$GITHUB_REF_NAME
	url="https://maven.pkg.github.com/Software-Hardware-Codesign/Serial4j-v1"

	files=(`cd "${project_dir}/output/serial4j" && ls *.jar && cd "${project_dir}"`)
	artifacts=("serial4j-docs" "serial4j-core")
	

	for ((i=0; i<${#files[@]}; i++)); do	
		deploy "${project_dir}/maven-settings.xml" "$groupId" "${artifacts[i]}" "${project_dir}/output/serial4j/${files[i]}" "$version" "$url"
	done

	return $?
}

echo -e $RESET_Cs

prepare_maven

if (( $? > 0 )); then 
    echo -e "$RED_C Task@PrepareMaven : Failed, check your connection !"
	exit 1
else 
    echo -e "$CYAN_C Task@PrepareMaven : Completed"
fi

echo -e $RESET_Cs

publish

if (( $? > 0 )); then 
    echo -e "$RED_C Task@Publish : Failed"
	exit 1
else 
    echo -e "$CYAN_C Task@Publish : Completed"
fi

echo -e $RESET_Cs
