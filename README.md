# User Registration API

A Spring Boot application with a RESTful API for user registration, built with Java 17, Gradle, Spring Web, Lombok, H2 database, and JPA (Hibernate). Includes optional JWT support, unit tests, and Swagger UI.

## Setup
1. Clone the repository.
2. Ensure Java 17 and Gradle are installed.
3. Run the application:
```bash
   ./gradlew clean build
   ```
   ```bash
   ./gradlew clean bootRun --args='--spring.profiles.active=dev'
   ```
4. Access the H2 console at http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:testdb`, username: `sa`, password: empty).
5. Access Swagger UI at http://localhost:8080/swagger-ui.html.

## Testing the API and solution diagram
Send a POST request to http://localhost:8080/api/auth/register with the following JSON body:
```json
{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "Hunter2A1",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}
```
Client -> POST /api/auth/register -> AuthController -> UserService -> UserRepository -> H2 DB
- Email uniqueness and format validated.
- Password validated with configurable regex.
- JWT token generated and persisted.

```bash 
curl --location 'localhost:8080/api/auth/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "asd",
    "email": "asd@asd.com",
    "password": "asdasdasd",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}'
```
Response:
```json
{
    "id": "f563e804-50fd-429e-bc2b-79bde1324182",
    "name": "asd",
    "email": "asd@asd.com",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2RAYXNkLmNvbSIsImlhdCI6MTc1NzA0MTY4MywiZXhwIjoxNzU3MTI4MDgzfQ.IwqQp1QFJXa_YyjIA42Jdgldcsd_1A3NVUyNkhYYiGI",
    "created": "2025-09-04T22:08:03.176445",
    "modified": "2025-09-04T22:08:03.176478",
    "lastLogin": "2025-09-04T22:08:02.912028",
    "isActive": true
}
```

Client -> POST /api/auth/login -> AuthController -> UserService -> UserRepository -> H2 DB
```bash 
curl --location 'localhost:8080/api/auth/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "asd@asd.com",
    "password": "asdasdasd",
}'
```
Response:
```json
{
    "id": "f563e804-50fd-429e-bc2b-79bde1324182",
    "name": "asd",
    "email": "asd@asd.com",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2RAYXNkLmNvbSIsImlhdCI6MTc1NzA0MTY4MywiZXhwIjoxNzU3MTI4MDgzfQ.IwqQp1QFJXa_YyjIA42Jdgldcsd_1A3NVUyNkhYYiGI",
    "created": "2025-09-04T22:08:03.176445",
    "modified": "2025-09-04T22:08:03.176478",
    "lastLogin": "2025-09-04T22:08:02.912028",
    "isActive": true
}
```

Client -> GET /api/users/{id} -> UserQueryController -> UserService -> UserRepository -> H2 DB
```bash
curl --location 'localhost:8080/api/users/50e73fd8-a6ce-43e4-87dd-fa5a2a49e858' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2RAYXNkLmNvbSIsImlhdCI6MTc1NzAzNDA2MywiZXhwIjoxNzU3MTIwNDYzfQ.ACGOou7kd5EX8YoXcIOmTohwMbHWBlGJxTWsLxR6T1E' \
--header 'Cookie: JSESSIONID=DA6A8BB7DDBA5F96C480BFF734D409B7' \
--data ''
```
Response:
```json
{
  "id": "f563e804-50fd-429e-bc2b-79bde1324182",
  "name": "asd",
  "email": "asd@asd.com",
  "password": "$2a$10$caNiZqw5D0DNEOxjXZNtA.wG4ZMrrxqYf/h9YdU0cT.CUA/3uaUm.",
  "phones": [],
  "created": "2025-09-04T22:08:03.176445",
  "modified": "2025-09-04T22:08:03.176478",
  "lastLogin": "2025-09-04T22:08:02.912028",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2RAYXNkLmNvbSIsImlhdCI6MTc1NzA0MTY4MywiZXhwIjoxNzU3MTI4MDgzfQ.IwqQp1QFJXa_YyjIA42Jdgldcsd_1A3NVUyNkhYYiGI",
  "active": true
}
```

Client -> PUT /api/users/{id} -> UserCommandController -> UserService -> UserRepository -> H2 DB
```bash
curl --location --request PUT 'localhost:8080/api/users/bfd5a89c-925f-42da-a1eb-f2408c0cc0e9' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2RAYXNkLmNvbSIsImlhdCI6MTc1NzA0NzUzNCwiZXhwIjoxNzU3MTMzOTM0fQ.nXF1ALrTllNYjiNvrjIpOMaHbWQbL5Z8MpQ7opwbp-E' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=DA6A8BB7DDBA5F96C480BFF734D409B7' \
--data-raw '
{
    "name": "asd-updated",
    "email": "asd@asd.com",
    "password": "12345",
    "phones": [],
    "active": false
}'
```
Response:
```json
{
  "id": "bfd5a89c-925f-42da-a1eb-f2408c0cc0e9",
  "name": "asd-updated",
  "email": "asd@asd.com",
  "password": "$2a$10$xTLKjFRUo8OuQnJGE5UtU.C/6MzrZhShwz0pzJkhZB7FCa4KoxGF6",
  "phones": [],
  "created": "2025-09-04T23:45:24.870618",
  "modified": "2025-09-04T23:46:21.062146",
  "lastLogin": "2025-09-04T23:46:20.954804",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2RAYXNkLmNvbSIsImlhdCI6MTc1NzA0NzUzNCwiZXhwIjoxNzU3MTMzOTM0fQ.nXF1ALrTllNYjiNvrjIpOMaHbWQbL5Z8MpQ7opwbp-E",
  "active": false
}
```

## Running Unit Tests
Run unit tests using the custom Gradle task:
```bash
./gradlew clean test
```
- Tests are located in `src/test/java/com/example/demo/service`.
- Results are printed in the console, and HTML reports are generated in `build/reports/unit-tests`.
