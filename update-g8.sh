#!/usr/bin/env bash

if [[ -f ./build.sbt ]] && [[ -d ./src/main/g8 ]]; then

   mkdir -p target
   cd target
   if [[ -d .makeitg8 ]] && [[ -d .makeitg8/.git ]] ; then
        cd .makeitg8
        git pull origin master
   else
        rm -r .makeitg8
        git clone https://github.com/arturopala/make-it-g8.git .makeitg8
        cd .makeitg8
   fi

   sbt "run --noclear --source ../../target/sandbox/new-shiny-service-26-fsm --target ../.. --name template-play-26-frontend-fsm.g8 --package uk.gov.hmrc.newshinyservice26fsmfrontend --description HMRC+Digital+Scala+Play+2.6+Stateful+Frontend -K servicename=New+Shiny+Service+26+FSM serviceTargetPort=9999" 

else

    echo "WARNING: run the script ./update-g8.sh in the template root folder"
    exit -1

fi
