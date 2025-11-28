#!/bin/bash

./gradlew clean build -x test

docker build -t labcabrera/sample-users-api:latest .

docker tag labcabrera/sample-users-api:latest labcabrera/sample-users-api:latest

docker push labcabrera/sample-users-api:latest
