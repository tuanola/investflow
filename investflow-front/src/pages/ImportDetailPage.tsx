import { useParams } from "react-router-dom";

export function ImportDetailPage() {
  const { importId } = useParams();

  return (
    <div>
      <h1 className="text-3xl font-semibold tracking-tight">Import detail</h1>
      <p className="mt-2 text-slate-600">
        Pipeline status for import <span className="font-mono">{importId}</span>.
      </p>
    </div>
  );
}