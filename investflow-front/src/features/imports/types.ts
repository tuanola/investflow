export type ImportStatus = "uploaded" | "processing" | "completed" | "failed";

export type PortfolioImport = {
  id: string;
  filename: string;
  status: ImportStatus;
  createdAt: string;
  completedAt?: string;
  rowCount?: number;
  transactionCount?: number;
};