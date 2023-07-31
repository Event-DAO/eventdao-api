#!/usr/bin/env bash
#init day : 2022-04-29 - 23:10:00
echo "Environment: $1"
buildName="eventdao-api-0.0.1-SNAPSHOT"
targetDir="/var/eventdao"
pemFile="eventdao-api";
hostIp="20.166.216.253"

#mvn clean install
ssh -o ""IdentitiesOnly=yes"" -i ~/.ssh/$pemFile.pem azureuser@$hostIp 'sudo systemctl stop eventdao.service'
scp -r -i ~/.ssh/$pemFile.pem target/$buildName.jar azureuser@$hostIp:$targetDir
ssh -o ""IdentitiesOnly=yes"" -i ~/.ssh/$pemFile.pem azureuser@$hostIp 'sudo systemctl start eventdao.service'