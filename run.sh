#!/bin/bash

# Остановка всех контейнеров
echo "Stopping containers..."
docker-compose down

# Сборка каждого микросервиса
echo "Building library-service..."
cd library-service || { echo "Could not change directory to library-service"; exit 1; }
./gradlew build || { echo "Library-service build failed"; exit 1; }
cd ..

echo "Building book-service..."
cd book-service || { echo "Could not change directory to book-service"; exit 1; }
./gradlew build || { echo "Book-service build failed"; exit 1; }
cd ..

# Запуск контейнеров
echo "Rebuilding and starting containers..."
docker-compose up --build

echo "Containers are up."
