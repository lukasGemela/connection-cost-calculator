#!/bin/sh
project_dir="${0%/*}"

"${project_dir}"/gradlew clean test bootJar -p ${project_dir}
docker build -t flixbus/cost-calculator:latest -f "${project_dir}"/Dockerfile ${project_dir}