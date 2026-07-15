Recommended MVP

InvestFlow MVP: Revolut CSV Portfolio Importer + Reporting Dashboard

The app should let a user upload a synthetic Revolut stock export, process it through a backend pipeline, and view portfolio reports in React.

What it must include
1. React + TypeScript frontend

Build only these pages:

Page	What it shows
Upload	CSV upload form
Imports	List of previous imports
Import detail	Pipeline stages and processing status
Portfolio overview	Total invested, current value, P&L, fees
Holdings	Current holdings table
Reports	Allocation and P&L charts

That is enough UI for a credible fullstack project.

2. FastAPI backend

Build these endpoints:

GET  /health
POST /imports
GET  /imports
GET  /imports/{import_id}
GET  /imports/{import_id}/stages
GET  /imports/{import_id}/overview
GET  /imports/{import_id}/holdings
GET  /imports/{import_id}/transactions
GET  /imports/{import_id}/reports/allocation

This shows API design, file upload, typed responses, and frontend integration.

3. PostgreSQL schema

Use these tables:

imports
pipeline_stages
transactions
holdings
portfolio_summary
reports

For MVP, skip separate market_prices, fx_rates, and pipeline_events tables at first. Add them later.

4. Simple pipeline

When a file is uploaded:

upload CSV
  -> validate file
  -> normalise transactions
  -> calculate holdings
  -> generate reports

For MVP, this can run synchronously first, then move to Redis/RQ later.

But still store stages:

uploaded
validated
normalised
calculated
reported

That gives you the event/pipeline concept without overbuilding.

5. Synthetic Revolut-style data

Use fake data committed to the repo:

data/sample/synthetic_revolut_export.csv

Avoid real data in GitHub.

Example columns:

Date,Ticker,Type,Quantity,Price,Currency,Fee,Total
2026-01-12,AAPL,BUY,2,180.00,USD,1.00,361.00
2026-02-03,AAPL,SELL,1,195.00,USD,1.00,194.00
2026-03-10,VUAA,BUY,3,85.00,GBP,0.00,255.00
6. Mock price and FX enrichment

For MVP, do not call external APIs yet.

Use hardcoded/mock reference data:

AAPL latest price: 210 USD
MSFT latest price: 450 USD
VUAA latest price: 92 GBP
USD -> GBP: 0.79
EUR -> GBP: 0.86

This keeps the MVP stable and testable.

Later you can replace this with yfinance + Frankfurter.

What this MVP demonstrates
Skill	How MVP shows it
React + TypeScript	Dashboard, forms, tables, charts
FastAPI	Upload and reporting APIs
Python data processing	CSV parsing, validation, calculations
PostgreSQL	Proper persistence and schema design
Data pipeline thinking	Pipeline stages and import lifecycle
Financial data modelling	Transactions, holdings, P&L
Testing	Unit tests for calculations and API tests
Product thinking	Useful dashboard and reports
Platform thinking	Docker Compose, clear docs, future async pipeline
What to avoid in MVP

Do not include these yet:

real AWS
Kubernetes
Terraform
live stock API calls
authentication
multi-user support
tax calculations
stock predictions
complex event bus
ML model training

Those are good later, but they will slow you down now.

Best build sequence
Frontend layout and mock pages.
FastAPI /health.
PostgreSQL + SQLAlchemy + Alembic.
POST /imports upload endpoint.
CSV validation.
Transaction normalisation.
Holdings calculation.
Portfolio overview endpoint.
React integration with real API.
Allocation chart.
Docker Compose.
Tests and README.