#!/bin/bash

echo "===> Limpando e empacotando o projeto via Maven..."
mvn clean package -DskipTests

echo "===> Construindo a imagem Docker via docker-compose..."
docker-compose build

echo "===> Subindo o contêiner em segundo plano..."
docker-compose up -d

echo "===> Aplicação deve estar rodando em http://localhost:8080"