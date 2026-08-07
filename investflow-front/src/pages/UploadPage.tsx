import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { z } from "zod";
import { ImportPipeline } from "@/components/ImportPipeline";
import { UploadFile } from "@/components/UploadFile";

const uploadSchema = z.object({
  file: z
    .instanceof(FileList)
    .refine((files) => files.length === 1, "Please select a CSV file.")
    .refine(
      (files) => files[0]?.name.toLowerCase().endsWith(".csv"),
      "The selected file must be a CSV file.",
    ),
  baseCurrency: z.enum(["GBP", "USD", "EUR"]),
});

type UploadFormValues = z.infer<typeof uploadSchema>;

export function UploadPage() {
  const navigate = useNavigate();
  const [selectedFileName, setSelectedFileName] = useState<string | null>(null);
  const [uploadError, setUploadError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
    reset,
  } = useForm<UploadFormValues>({
    resolver: zodResolver(uploadSchema),
    defaultValues: {
      baseCurrency: "GBP",
    },
  });

  async function onSubmit(data: UploadFormValues) {
    const file = data.file[0];
    const formData = new FormData();
    formData.append("file", file);

    setUploadError(null);

    try {
      const response = await fetch("/api/v1/imports", {
        method: "POST",
        body: formData,
      });

      if (!response.ok) {
        throw new Error(`Request failed with status ${response.status}`);
      }

      reset();
      setSelectedFileName(null);
      navigate("/imports");
    } catch {
      setUploadError("Could not upload the file. Please try again.");
    }
  }

  return (
    <div className="space-y-8">
      <div>
        <h1 className="text-3xl font-semibold tracking-tight">
          Upload Revolut Export
        </h1>
        <p className="mt-2 max-w-3xl text-slate-600">
          Upload a Revolut stock transaction CSV to start the portfolio
          processing pipeline. The file will be validated, normalised, enriched
          with price and FX data, then used to generate reports.
        </p>
      </div>

      <div className="grid gap-6 lg:grid-cols-[minmax(0,2fr)_minmax(320px,1fr)]">
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm"
        >
          <div className="space-y-6">
            <UploadFile
              errorMessage={errors.file?.message}
              registration={register("file", {
                onChange: (event) => {
                  const file = event.target.files?.[0];
                  setSelectedFileName(file?.name ?? null);
                },
              })}
              selectedFileName={selectedFileName}
            />

            <div>
              <label
                htmlFor="baseCurrency"
                className="block text-sm font-medium text-slate-900"
              >
                Reporting currency
              </label>

              <select
                id="baseCurrency"
                className="mt-2 w-full rounded-lg border border-slate-300 bg-white px-3 py-2 text-sm text-slate-900 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100"
                {...register("baseCurrency")}
              >
                <option value="GBP">GBP</option>
                <option value="USD">USD</option>
                <option value="EUR">EUR</option>
              </select>

              {errors.baseCurrency && (
                <p className="mt-2 text-sm text-red-600">
                  {errors.baseCurrency.message}
                </p>
              )}
            </div>

            <div className="flex items-center gap-3">
              <button
                type="submit"
                disabled={isSubmitting}
                className="inline-flex items-center justify-center rounded-lg bg-slate-950 px-4 py-2 text-sm font-medium text-white transition hover:bg-slate-800 disabled:cursor-not-allowed disabled:opacity-60"
              >
                {isSubmitting ? "Uploading…" : "Start import"}
              </button>

              <button
                type="button"
                onClick={() => {
                  reset();
                  setSelectedFileName(null);
                  setUploadError(null);
                }}
                className="inline-flex items-center justify-center rounded-lg border border-slate-300 bg-white px-4 py-2 text-sm font-medium text-slate-700 transition hover:bg-slate-50"
              >
                Clear
              </button>
            </div>

            {uploadError && (
              <div
                className="rounded-lg border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-800"
                role="alert"
              >
                {uploadError}
              </div>
            )}
          </div>
        </form>

        <ImportPipeline />
      </div>
    </div>
  );
}
