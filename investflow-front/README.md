# InvestFlow Frontend

InvestFlow is a small full-stack app for importing a synthetic Revolut-style stock export, processing the data through a backend pipeline, and viewing portfolio analytics in a React dashboard.

## What this project does

The frontend provides a simple interface to:

* upload a transaction export
* view processing status
* inspect portfolio analytics and summaries
* review results from the backend

## Tech stack

* React
* TypeScript
* Vite

## Prerequisites

* Node.js 18+ recommended
* npm or yarn
* the InvestFlow backend running locally or deployed

## Setup

Install dependencies:

```bash
npm install
```

Create a local environment file if needed and point the frontend to the backend API.

Example:

```bash
VITE_API_BASE_URL=http://localhost:8000
```

## Run locally

Start the development server:

```bash
npm run dev
```

Open the app in your browser at the local address shown in the terminal.

## Build for production

```bash
npm run build
```

Preview the production build locally:

```bash
npm run preview
```

## Environment variables

The frontend may require:

* `VITE_API_BASE_URL` — backend URL used by the React app

Check the source code for any additional variables required by your local setup.

## Notes

This frontend is designed to work with the InvestFlow backend pipeline. Some features will not function unless the backend API is available.
