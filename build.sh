#!/bin/sh
docker run -it --rm -v $PWD:$PWD -w $PWD -v /var/run/docker.sock:/var/run/docker.sock gradle:6.9-jdk11 $PWD/gradlew clean test build --stacktrace
docker build -t flixbus/cost-calculator:latest -f "${PWD}"/Dockerfile ${PWD}