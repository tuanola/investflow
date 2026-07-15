const importPipelineSteps = [
  "Validate file format",
  "Normalise transactions",
  "Enrich with market and FX data",
  "Calculate holdings and P&L",
  "Generate reports",
] as const;

export function ImportPipeline() {
  return (
    <aside className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
      <h2 className="text-lg font-semibold text-slate-900">
        Import pipeline
      </h2>

      <p className="mt-2 text-sm text-slate-600">
        After upload, the event-driven pipeline will process the file in several
        stages.
      </p>

      <ol className="mt-5 space-y-3 text-sm">
        {importPipelineSteps.map((step, index) => (
          <li key={step} className="flex gap-3">
            <span className="flex h-6 w-6 shrink-0 items-center justify-center rounded-full bg-slate-100 text-xs font-semibold text-slate-700">
              {index + 1}
            </span>
            <span className="pt-0.5 text-slate-700">{step}</span>
          </li>
        ))}
      </ol>

      <div className="mt-6 rounded-lg bg-amber-50 p-4 text-sm text-amber-900">
        Use synthetic data for demos. Do not commit real Revolut exports to the
        repository.
      </div>
    </aside>
  );
}
