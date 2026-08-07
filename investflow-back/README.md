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

## CSV processing boundary

The upload controller converts Spring's `MultipartFile` into an application
command containing the filename and bytes. The application layer depends on the
`CsvParser` port rather than on Spring multipart types or a specific transport.

The Python/FastAPI parser adapter has not been implemented yet. Until a
`CsvParser` bean is registered, newly created imports remain `UPLOADED`. Once an
adapter is available, synchronous processing follows this lifecycle:

1. Create the import as `UPLOADED` in a short transaction.
2. Change it to `PROCESSING` in a second transaction.
3. Call the Python parser outside any database transaction.
4. Change it to `COMPLETED` with its record count, or `FAILED` when parsing
   throws an exception.

For larger files or asynchronous processing, store the file durably and pass a
storage reference to the Python service instead of retaining the complete file
in memory.
