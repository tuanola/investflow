# InvestFlow Backend

InvestFlow backend is a Kotlin-based Spring Boot API scaffold for a simple portfolio project.  
It provides the base structure for future backend features, starting with a clean, maintainable setup and a health-check endpoint.

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