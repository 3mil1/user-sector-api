#!/bin/bash

echo "Starting required services (PostgreSQL)..."
docker compose up -d db 

sleep 5 

echo "Loading environment variables..."
source .env

echo "Running the Spring Boot application..."
./gradlew bootRun
