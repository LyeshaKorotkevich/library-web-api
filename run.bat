@echo off

REM Остановка всех контейнеров
echo Stopping containers...
docker-compose down

REM Сборка каждого микросервиса
echo Building auth-service...
cd auth-service || (echo Could not change directory to auth-service & exit /b 1)
call gradlew build || (echo Auth-service build failed & exit /b 1)
cd ..

echo Building library-service...
cd library-service || (echo Could not change directory to library-service & exit /b 1)
call gradlew build || (echo Library-service build failed & exit /b 1)
cd ..

echo Building book-service...
cd book-service || (echo Could not change directory to book-service & exit /b 1)
call gradlew build || (echo Book-service build failed & exit /b 1)
cd ..

REM Запуск контейнеров
echo Rebuilding and starting containers...
docker-compose up --build

echo Containers are up.
