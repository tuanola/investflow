export type ImportStatus = "UPLOADED" | "PROCESSING" | "COMPLETED" | "FAILED";

export type PortfolioImport = {
  id: number;
  fileName: string;
  status: ImportStatus;
  uploadedAt: string;
  recordCount: number;
};
