# Smart Retails Backend

Production-ready Spring Boot 3 (Java 17) backend for Smart Retails Inventory & POS.

## Features

- **Global exception handling** via `GlobalExceptionHandler` returning consistent `ApiResponse` payloads
- **Validation errors** and common web/security/database exceptions mapped to proper HTTP codes
- **Config via environment variables** (no hardcoded secrets in `application.yml`)
- **Dockerized** with multi-stage build for small runtime image

## Tech Stack

- Spring Boot 3.2.x, Java 17, Maven
- Spring Web, Data JPA (Hibernate), Security, Validation
- MySQL Connector/J
- Lombok

## Project Structure

- `src/main/java/com/smartretails/backend/`
  - `controller/` REST controllers (e.g. `ProductController`)
  - `exception/GlobalExceptionHandler.java` centralized error handling
  - `config/ApiResponse.java` response wrapper
  - `entity/`, `service/`, `repository/` domain layers (as applicable)
- `src/main/resources/application.yml` environment-driven config
- `Dockerfile`, `.dockerignore`

## Configuration

Set the following environment variables (examples shown):

- `SPRING_DATASOURCE_URL=jdbc:mysql://<host>:3306/products_db`
- `SPRING_DATASOURCE_USERNAME=<db_user>`
- `SPRING_DATASOURCE_PASSWORD=<db_password>`
- `SERVER_PORT=8080`
- `JWT_SECRET=your_really_long_random_secret_at_least_32_chars`
- `JWT_EXPIRATION=86400000` (ms)
- `SPRING_JPA_SHOW_SQL=false`

You can export them in your shell or pass them via Docker `-e` flags.

## Build & Run (Local)

- Build: `mvn clean package`
- Run: `java -jar target/backend-0.0.1-SNAPSHOT.jar`

Or run via Maven: `mvn spring-boot:run`

## Docker

Build the image from the `backend/` directory:

```bash
# Build
docker build -t smart-retails-backend:latest .

# Run (example)
docker run --name smart-retails-backend \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/products_db" \
  -e SPRING_DATASOURCE_USERNAME="root" \
  -e SPRING_DATASOURCE_PASSWORD="password" \
  -e JWT_SECRET="change_me_to_a_long_secret" \
  smart-retails-backend:latest
```

Notes:
- On Windows/macOS using Docker Desktop, `host.docker.internal` lets the container reach a DB running on your host.
- Use a managed DB in production and inject credentials via your orchestrator/secret manager.

## API Conventions

All responses follow `ApiResponse<T>`:

```json
{
  "success": true,
  "message": "optional message",
  "error": null,
  "data": {},
  "timestamp": "2025-01-01T12:00:00"
}
```

On errors:

```json
{
  "success": false,
  "message": "Validation error",
  "error": "fieldName: must not be blank",
  "timestamp": "2025-01-01T12:00:00"
}
```

Common statuses returned by the global handler:
- 400 Bad Request (validation, type mismatch, malformed JSON)
- 401 Unauthorized (bad credentials)
- 403 Forbidden (access denied)
- 404 Not Found (resource/username not found)
- 405 Method Not Allowed
- 409 Conflict (DB constraint violations)
- 500 Internal Server Error (with reference ID in payload and server logs)

## Development Tips

- Keep `ddl-auto: update` for dev only. Use Flyway/Liquibase for production schema management.
- Add `spring-boot-starter-actuator` for health/metrics in production (optional, not included by default).
- Configure centralized logging (e.g., JSON logs) if deploying to container platforms.

## License

Proprietary. All rights reserved.
