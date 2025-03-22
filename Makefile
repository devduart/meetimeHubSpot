.PHONY: build run test docker-build docker-up clean

build:
	mvn clean package -DskipTests

run:
	java -jar target/hubspot-integration-1.0.0.jar

test:
	mvn test

docker-build:
	docker build -t hubspot-integration:latest .

docker-up:
	docker-compose up -d

clean:
	mvn clean

deploy: build docker-build docker-up