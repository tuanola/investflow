import { ImportsTable } from "@/components/ImportsTable";
import { mockImports } from "@/features/imports/mockImports";

export function ImportsPage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-semibold tracking-tight">Imports</h1>
        <p className="mt-2 text-slate-600">
          View uploaded Revolut exports and their processing status.
        </p>
      </div>

      <ImportsTable portfolioImports={mockImports} />
    </div>
  );
}
