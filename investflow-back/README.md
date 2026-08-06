# InvestFlow Backend

InvestFlow backend is the API and orchestration layer for the InvestFlow project. It is built with Kotlin and Spring Boot, and it coordinates the core backend flow for importing, processing, and validating investment data.

The service is designed to provide a clean, maintainable foundation for the application, including request handling, business logic, persistence integration, and health checks. Data processing and validation are handled by a separate FastAPI service.

## Running with Docker

Build the image:

```bash
docker build -t investflow-backend .
```

Run the container:
```bash
docker run --rm -p 8080:8080 investflow-backend
```

The API will be available on http://localhost:8080.