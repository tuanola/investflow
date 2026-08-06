# InvestFlow

InvestFlow is a small full-stack project built with React, Kotlin,
Spring Boot, and PostgreSQL.

## Run with Docker Compose

### Prerequisites

- Docker Desktop, or another Docker installation with Compose support

From the repository root, build and start the frontend, backend, and database:

```bash
docker compose up --build
```

The services are available at:

- Frontend: http://localhost:5173
- Backend API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- Backend health check: http://localhost:8080/actuator/health
- PostgreSQL: `localhost:5432`

Stop the application with:

```bash
docker compose down
```

Database data is kept in the `investflow-postgres-data` Docker volume. To also
delete that data and start with an empty database, run:

```bash
docker compose down --volumes
```

## Run tests

### Backend

The backend integration tests use Testcontainers, so Docker must be running.
The scaffold context test also needs the Compose PostgreSQL service:

```bash
docker compose up --detach postgres

cd investflow-back
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/investflow ./mvnw test
```

On Windows PowerShell, set the datasource URL before running Maven:

```powershell
docker compose up --detach postgres

cd investflow-back
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://localhost:5432/investflow"
./mvnw.cmd test
```

### Frontend

Install the frontend dependencies and run the Vitest component tests:

```bash
cd investflow-front
npm ci
npm test
```

To rerun affected tests automatically while developing:

```bash
npm run test:watch
```

Run the production build and lint checks with:

```bash
npm run build
npm run lint
```
