#!/usr/bin/env bash

if [[ -f ./build.sbt ]] && [[ -d ./src/main/g8 ]]; then

    export TEMPLATE=`pwd | xargs basename`
    echo ${TEMPLATE}
    mkdir -p target/sandbox
    cd target/sandbox
    sudo rm -r new-shiny-service
    g8 file://../../../${TEMPLATE} --servicename="New Shiny Service" --serviceTargetPort="9999" --serviceUrlPrefix="shiny-url-prefix" --package="uk.gov.hmrc.newshinyservice" -o new-shiny-service "$@"
    cd new-shiny-service
    git init
	git add .
	git commit -m start
    sbt test it:test

else

    echo "WARNING: run the script ./test.sh in the template root folder"
    exit -1

fi