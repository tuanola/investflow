import { FileText, UploadCloud } from "lucide-react";
import type { UseFormRegisterReturn } from "react-hook-form";

type UploadFileProps = {
  errorMessage?: string;
  registration: UseFormRegisterReturn;
  selectedFileName: string | null;
};

export function UploadFile({
  errorMessage,
  registration,
  selectedFileName,
}: UploadFileProps) {
  return (
    <div>
      <label htmlFor="file" className="block text-sm font-medium text-slate-900">
        Revolut CSV export
      </label>

      <label
        htmlFor="file"
        className="mt-3 flex cursor-pointer flex-col items-center justify-center rounded-xl border border-dashed border-slate-300 bg-slate-50 px-6 py-10 text-center transition hover:border-blue-400 hover:bg-blue-50"
      >
        <UploadCloud className="h-10 w-10 text-slate-400" />
        <span className="mt-3 text-sm font-medium text-slate-900">
          Choose a CSV file
        </span>
        <span className="mt-1 text-sm text-slate-500">
          Revolut stock export in .csv format
        </span>

        {selectedFileName && (
          <span className="mt-4 inline-flex items-center gap-2 rounded-full bg-blue-100 px-3 py-1 text-sm font-medium text-blue-800">
            <FileText className="h-4 w-4" />
            {selectedFileName}
          </span>
        )}
      </label>

      <input
        id="file"
        type="file"
        accept=".csv,text/csv"
        className="sr-only"
        {...registration}
      />

      {errorMessage && (
        <p className="mt-2 text-sm text-red-600">{errorMessage}</p>
      )}
    </div>
  );
}
