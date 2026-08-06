import { Link } from "react-router-dom";
import type {
  ImportStatus,
  PortfolioImport,
} from "@/features/imports/types";
import { formatDateTime } from "@/lib/formatters";

type ImportsTableProps = {
  portfolioImports: PortfolioImport[];
};

function getStatusClasses(status: ImportStatus): string {
  switch (status) {
    case "COMPLETED":
      return "bg-emerald-100 text-emerald-800";
    case "PROCESSING":
      return "bg-blue-100 text-blue-800";
    case "FAILED":
      return "bg-red-100 text-red-800";
    case "UPLOADED":
      return "bg-slate-100 text-slate-800";
  }
}

export function ImportsTable({ portfolioImports }: ImportsTableProps) {
  return (
    <div className="overflow-hidden rounded-xl border border-slate-200 bg-white shadow-sm">
      <table className="w-full border-collapse text-left text-sm">
        <thead className="border-b border-slate-200 bg-slate-50 text-slate-600">
          <tr>
            <th className="px-4 py-3 font-medium">File</th>
            <th className="px-4 py-3 font-medium">Status</th>
            <th className="px-4 py-3 font-medium">Records</th>
            <th className="px-4 py-3 font-medium">Uploaded</th>
            <th className="px-4 py-3 font-medium"></th>
          </tr>
        </thead>

        <tbody className="divide-y divide-slate-100">
          {portfolioImports.map((portfolioImport) => (
            <tr key={portfolioImport.id} className="hover:bg-slate-50">
              <td className="px-4 py-3 font-medium text-slate-900">
                {portfolioImport.fileName}
              </td>

              <td className="px-4 py-3">
                <span
                  className={[
                    "inline-flex rounded-full px-2.5 py-1 text-xs font-medium capitalize",
                    getStatusClasses(portfolioImport.status),
                  ].join(" ")}
                >
                  {portfolioImport.status}
                </span>
              </td>

              <td className="px-4 py-3 text-slate-700">
                {portfolioImport.recordCount}
              </td>

              <td className="px-4 py-3 text-slate-700">
                {formatDateTime(portfolioImport.uploadedAt)}
              </td>

              <td className="px-4 py-3 text-right">
                <Link
                  to={`/imports/${portfolioImport.id}`}
                  className="text-sm font-medium text-blue-600 hover:text-blue-700"
                >
                  View
                </Link>
              </td>
            </tr>
          ))}
          {portfolioImports.length === 0 && (
            <tr>
              <td className="px-4 py-8 text-center text-slate-500" colSpan={5}>
                No imports found.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}
