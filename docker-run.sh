#!/bin/bash

./gradlew clean build -x test

docker stop sample-users-api

docker rm sample-users-api

docker rmi labcabrera/sample-users-api:latest

echo "Building sample-users-api Docker image..."

docker build -t labcabrera/sample-users-api:latest .

echo "Running sample-users-api container..."

docker run -d --name sample-users-api -p 8083:8083 \
  -e JAVA_OPTS="-Xms512m -Xmx1024m" \
  -e IAM_JWK_URI="http://localhost:8090/realms/archetype-realm/protocol/openid-connect/certs" \
  labcabrera/sample-users-api:latest

docker logs -f sample-users-api
