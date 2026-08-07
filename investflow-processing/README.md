# InvestFlow Processing Service

FastAPI service for CSV validation and portfolio data processing. The CSV
parser endpoint is not implemented yet; the current application exposes example
endpoints that can be used to verify the service starts correctly.

## Prerequisites

- Python 3.11
- [uv](https://docs.astral.sh/uv/)

The Python version is defined in `.python-version`, and dependencies are locked
in `uv.lock`.

## Run locally

From the repository root:

```bash
cd investflow-processing
uv sync
uv run uvicorn app.main:app --reload --host 0.0.0.0 --port 8000
```

The service will be available at:

- API: http://localhost:8000
- Swagger UI: http://localhost:8000/docs
- OpenAPI document: http://localhost:8000/openapi.json

Verify it is running:

```bash
curl http://localhost:8000/
```

Expected response:

```json
{"Hello":"World"}
```

Press `Ctrl+C` to stop the service.

## Dependency changes

Add a dependency with:

```bash
uv add <package-name>
```

After changing dependencies, commit both `pyproject.toml` and `uv.lock`.
