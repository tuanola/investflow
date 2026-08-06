import { useEffect, useState } from "react";
import { ImportsTable } from "@/components/ImportsTable";
import type { PortfolioImport } from "@/features/imports/types";

export function ImportsPage() {
  const [portfolioImports, setPortfolioImports] = useState<PortfolioImport[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  useEffect(() => {
    const controller = new AbortController();

    async function loadImports() {
      try {
        const response = await fetch("/api/imports", {
          signal: controller.signal,
        });

        if (!response.ok) {
          throw new Error(`Request failed with status ${response.status}`);
        }

        const imports = (await response.json()) as PortfolioImport[];
        setPortfolioImports(imports);
      } catch (error) {
        if (error instanceof DOMException && error.name === "AbortError") {
          return;
        }

        setErrorMessage("Could not load imports. Please try again later.");
      } finally {
        if (!controller.signal.aborted) {
          setIsLoading(false);
        }
      }
    }

    void loadImports();

    return () => controller.abort();
  }, []);

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-semibold tracking-tight">Imports</h1>
        <p className="mt-2 text-slate-600">
          View uploaded Revolut exports and their processing status.
        </p>
      </div>

      {isLoading && (
        <div className="rounded-xl border border-slate-200 bg-white px-4 py-8 text-center text-slate-500">
          Loading imports…
        </div>
      )}

      {errorMessage && (
        <div
          className="rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-800"
          role="alert"
        >
          {errorMessage}
        </div>
      )}

      {!isLoading && !errorMessage && (
        <ImportsTable portfolioImports={portfolioImports} />
      )}
    </div>
  );
}
