#!/usr/bin/env bash

if [ "$#" -ne 1 ]; then
    echo "Pass new BOM version"
    exit
fi

mvn versions:set -f bom/pom.xml -DnewVersion=$1
mvn clean install -f bom/pom.xml
