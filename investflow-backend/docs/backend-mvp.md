Good FastAPI scope for InvestFlow

Build a Portfolio Import and Processing API that manages Revolut export ingestion, pipeline execution, portfolio calculations, and report retrieval.

A strong CV-friendly summary would be:

Built a FastAPI backend for an event-driven portfolio analytics platform, supporting CSV ingestion, asynchronous pipeline orchestration, transaction normalisation, enrichment with market/FX data, portfolio calculation, reporting APIs, PostgreSQL persistence and structured pipeline status tracking.

Core FastAPI features to build
1. Import management API

Endpoints:

POST   /imports
GET    /imports
GET    /imports/{import_id}
DELETE /imports/{import_id}

Purpose:

upload Revolut CSV
create import record
store original file path
start async pipeline
track import status

This is your entry point into the event-driven system.

2. Pipeline status API

Endpoints:

GET /imports/{import_id}/stages
GET /imports/{import_id}/events

Purpose:

show pipeline progress in the React UI
expose stage states: pending, running, completed, failed
store errors and timestamps
make backend processing visible

This is useful for demonstrating async workflows and operational thinking.

3. Transaction API

Endpoints:

GET /imports/{import_id}/transactions
GET /imports/{import_id}/transactions/{transaction_id}

Purpose:

return normalised Revolut transactions
support filtering by ticker, date, transaction type
support pagination

Example query:

GET /imports/imp_123/transactions?ticker=AAPL&type=BUY&page=1&page_size=50

This demonstrates API design beyond simple CRUD.

4. Holdings API

Endpoints:

GET /imports/{import_id}/holdings
GET /imports/{import_id}/overview

Purpose:

expose calculated holdings
show total invested, market value, realised/unrealised P&L
provide data for dashboard cards
5. Reports API

Endpoints:

GET /imports/{import_id}/reports/allocation
GET /imports/{import_id}/reports/performance
GET /imports/{import_id}/reports/fees
GET /imports/{import_id}/reports/pnl

Purpose:

feed charts in React
return report-ready JSON
separate calculation from presentation
Backend architecture

Use a layered structure:

backend/
  app/
    main.py
    api/
      routes/
        imports.py
        pipeline.py
        transactions.py
        holdings.py
        reports.py
    core/
      config.py
      logging.py
      errors.py
    db/
      session.py
      base.py
    models/
      import_model.py
      transaction_model.py
      pipeline_model.py
      portfolio_model.py
      report_model.py
    schemas/
      imports.py
      transactions.py
      holdings.py
      reports.py
    services/
      import_service.py
      pipeline_service.py
      transaction_service.py
      portfolio_service.py
      report_service.py
      storage_service.py
    workers/
      tasks.py
    tests/

This looks much stronger than putting everything in main.py.

Technology choices

Use:

Area	Tool
API	FastAPI
Validation	Pydantic
Database	PostgreSQL
ORM	SQLAlchemy 2.0
Migrations	Alembic
Background jobs	RQ + Redis first
Testing	pytest
HTTP tests	httpx
Lint/format	ruff
Settings	pydantic-settings
Logging	structlog or standard structured logging
File storage	local filesystem first

For MVP, I’d use RQ + Redis rather than Celery. It is simpler and easier to explain.

Event-driven backend design

When the frontend uploads a file:

POST /imports
  -> save file
  -> create imports row
  -> create initial pipeline stages
  -> enqueue validate_import job
  -> return import_id

Then workers process stages:

validate_import
  -> normalise_transactions
  -> enrich_market_data
  -> calculate_portfolio
  -> generate_reports

Each stage updates:

pipeline_stages
pipeline_events
imports.status

This gives you a backend that feels production-like.

MVP endpoints to implement first

Start with just these:

POST /imports
GET  /imports
GET  /imports/{import_id}
GET  /imports/{import_id}/stages
GET  /imports/{import_id}/transactions
GET  /imports/{import_id}/holdings
GET  /imports/{import_id}/overview

That is enough to connect the React frontend.

What to show on your CV

A good CV bullet:

Developed a FastAPI backend for InvestFlow, an event-driven portfolio analytics platform, implementing CSV import APIs, PostgreSQL data models, asynchronous Redis/RQ pipeline workers, transaction normalisation, market/FX enrichment, portfolio calculations, report endpoints, structured error handling and automated tests.

A shorter version:

Built a FastAPI data-processing backend with PostgreSQL, Redis/RQ workers and event-style pipeline tracking for portfolio import, enrichment, calculation and reporting workflows.

Best build order
Create FastAPI app skeleton.
Add /health.
Add PostgreSQL connection.
Add SQLAlchemy models and Alembic migrations.
Add POST /imports file upload.
Store file locally.
Create import and pipeline-stage records.
Add mock worker pipeline.
Add real CSV validation.
Add transaction normalisation.
Add holdings calculation.
Add report endpoints.
Connect React frontend.
My recommendation

For CV value, prioritise these backend features:

Clean FastAPI architecture
PostgreSQL schema + Alembic
Async/background pipeline with Redis/RQ
Typed Pydantic schemas
Good tests
Clear OpenAPI docs
Structured logging and error handling

That will make InvestFlow useful as both a fullstack project and a strong backend/Python portfolio piece.