#!/bin/bash

mvn clean package -DskipTests

docker build -t hubspot-integration:latest .

docker-compose up -d