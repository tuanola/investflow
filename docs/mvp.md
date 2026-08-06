# Recommended MVP

## InvestFlow MVP: Revolut CSV Portfolio Importer + Reporting Dashboard

InvestFlow should let a user upload a synthetic Revolut-style stock export, validate and process it through a FastAPI service, orchestrate the flow from a Kotlin/Spring Boot backend, and view portfolio reports in a React dashboard.

## What the MVP must include

### 1. React + TypeScript frontend

Build only the pages needed to show the end-to-end flow:

| Page | What it shows |
| --- | --- |
| Upload | CSV upload form and import trigger |
| Imports | List of previous imports |
| Import detail | Pipeline stages, validation status, and processing outcome |
| Portfolio overview | Total invested, current value, P&L, and fees |
| Holdings | Current holdings table |
| Reports | Allocation and P&L charts |

That is enough UI for a credible full-stack project.

### 2. Kotlin + Spring Boot backend

Use Spring Boot as the main application layer and API surface for the frontend.

It should:
- accept the upload request from the frontend,
- create and track import records,
- coordinate validation and processing with FastAPI,
- persist the final data in PostgreSQL,
- expose read endpoints for the dashboard,
- provide `/health` and Swagger/OpenAPI documentation.

Suggested endpoints:

- `GET /health`
- `POST /imports`
- `GET /imports`
- `GET /imports/{import_id}`
- `GET /imports/{import_id}/stages`
- `GET /imports/{import_id}/overview`
- `GET /imports/{import_id}/holdings`
- `GET /imports/{import_id}/transactions`
- `GET /imports/{import_id}/reports/allocation`
- `GET /imports/{import_id}/reports/pnl`

### 3. FastAPI processing and validation service

Use FastAPI for the data processing and validation work.

It should:
- validate the uploaded CSV structure,
- check required columns and row-level data quality,
- normalise transactions into a consistent model,
- calculate holdings and portfolio summaries,
- generate report-ready output for the dashboard,
- return clear validation errors when the input is invalid.

A simple first version can run synchronously. The main goal is to show a clean separation between orchestration in Kotlin and data processing in Python.

### 4. PostgreSQL schema

Use PostgreSQL to store the import lifecycle and the processed portfolio data.

Suggested tables:
- `imports`
- `pipeline_stages`
- `transactions`
- `holdings`
- `portfolio_summary`
- `reports`
- `processing_errors`

For the MVP, skip separate `market_prices`, `fx_rates`, and event-queue tables. Add them later if the project grows.

### 5. Simple pipeline

When a file is uploaded:

1. store the raw file metadata,
2. send the file to FastAPI for validation,
3. normalise the transactions,
4. calculate holdings and summary metrics,
5. generate report data,
6. persist everything in PostgreSQL,
7. show the result in the dashboard.

Store stage states so the import lifecycle is visible in the UI:

- uploaded
- validated
- normalised
- calculated
- reported

That gives you the pipeline concept without overbuilding.

### 6. Synthetic Revolut-style data

Use fake data committed to the repo:

- `data/sample/synthetic_revolut_export.csv`

Avoid real data in GitHub.

Example columns:

```csv
Date,Ticker,Type,Quantity,Price,Currency,Fee,Total
2026-01-12,AAPL,BUY,2,180.00,USD,1.00,361.00
2026-02-03,AAPL,SELL,1,195.00,USD,1.00,194.00
2026-03-10,VUAA,BUY,3,85.00,GBP,0.00,255.00
```

### 7. Mock price and FX enrichment

For the MVP, do not call external APIs yet.

Use hardcoded or mock reference data:
- AAPL latest price: 210 USD
- MSFT latest price: 450 USD
- VUAA latest price: 92 GBP
- USD -> GBP: 0.79
- EUR -> GBP: 0.86

This keeps the MVP stable and testable.

Later this can be replaced with live price and FX lookups.

## What this MVP demonstrates

| Skill | How the MVP shows it |
| --- | --- |
| React + TypeScript | Dashboard, forms, tables, charts |
| Spring Boot | Main API layer and orchestration |
| FastAPI | Validation and data processing service |
| Python data processing | CSV parsing, validation, calculations |
| PostgreSQL | Proper persistence and schema design |
| Pipeline thinking | Import lifecycle and stage tracking |
| Financial data modelling | Transactions, holdings, P&L |
| Testing | Unit tests for calculations and API tests |
| Product thinking | Useful dashboard and reports |
| Platform thinking | Docker Compose, clear docs, future async pipeline |

## What to avoid in the MVP

Do not include these yet:

- real AWS infrastructure
- Kubernetes
- Terraform
- live stock API calls
- authentication
- multi-user support
- tax calculations
- stock predictions
- complex event bus
- ML model training

Those are good later, but they will slow you down now.
