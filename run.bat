@echo off

echo Stopping containers...
docker-compose down

echo Building auth-service...
cd auth-service || (echo Could not change directory to auth-service && exit /b 1)
gradlew build || (echo Auth-service build failed && exit /b 1)
cd ..

echo Building library-service...
cd library-service || (echo Could not change directory to library-service && exit /b 1)
gradlew build || (echo Library-service build failed && exit /b 1)
cd ..

echo Building book-service...
cd book-service || (echo Could not change directory to book-service && exit /b 1)
gradlew build || (echo Book-service build failed && exit /b 1)
cd ..

echo Rebuilding and starting containers...
docker-compose up --build

echo Containers are up.