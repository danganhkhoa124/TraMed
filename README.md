# TraMed Backend

## Overview
TraMed is a modular Spring Boot backend composed of shared core libraries, an application layer, and a web API for exposing REST endpoints. The build is managed with Gradle and targets Java 21.

### Module layout
- `00-tramed-core` – shared base abstractions and utilities that are reused by the rest of the codebase.
- `01-tramed-presentation/web-api` – the Spring Boot application that wires the layers together and exposes HTTP endpoints.
- `02-tramed-application-core` – application services and business logic.
- `03-tramed-infrastructure` – infrastructure adapters such as MyBatis integrations.

## Technologies
- **Java 21** with the Gradle 8.13 toolchain.
- **Spring Boot 3.4.4** with starters for Web, Validation, Security, AOP, Data JPA, and Redis.
- **JSON Web Token (jjwt)** for authentication tokens.
- **PostgreSQL 16** as the relational database.
- **Redis 7** as the token store for login sessions.
- **Docker Compose** for local orchestration of PostgreSQL and Redis services.

## Docker Compose setup
The [`docker-compose.yaml`](./docker-compose.yaml) file defines two services for local development:

| Service   | Description |
|-----------|-------------|
| `postgres` | Runs PostgreSQL 16 with a custom [`entrypoint.sh`](./entrypoint.sh) that keeps the standard `docker-entrypoint.sh` behavior while also executing the SQL initialization scripts described below. Data is persisted via the `postgres_data` named volume. |
| `redis`    | Runs Redis 7 with append-only persistence enabled, secured by a password supplied through the `REDIS_PASSWORD` environment variable (defaults to `tramed`). Data is stored in the `redis_data` named volume. |

Bring both services up with:

```bash
docker compose up -d
```

## Database initialization
Two SQL scripts in the [`database`](./database) directory are automatically executed by the PostgreSQL container via the mounted entrypoint script:

- [`init.sql`](./database/init.sql) – creates the schema, including the `app_user`, `notification`, and `notification_content` tables, along with their relationships.
- [`seed.sql`](./database/seed.sql) – inserts sample records for an administrator account and several localized notification entries to support local testing.

## Inspecting Redis data locally
While the Docker Compose stack is running you can connect to the Redis container and inspect the in-memory data (e.g., active JWTs) with the Redis CLI:

```bash
docker exec -it redis_cache redis-cli -a tramed
```

Inside the shell you can run typical commands such as `KEYS *`, `GET <key>`, or `HGETALL <key>` to review the stored session tokens and other cached values.

