import type { PortfolioImport } from "./types";

export const mockImports: PortfolioImport[] = [
  {
    id: 1,
    fileName: "revolut-stocks-2026-06.csv",
    status: "COMPLETED",
    uploadedAt: "2026-06-18T10:21:00Z",
    recordCount: 148,
  },
  {
    id: 2,
    fileName: "revolut-stocks-2026-05.csv",
    status: "COMPLETED",
    uploadedAt: "2026-05-30T19:42:00Z",
    recordCount: 121,
  },
  {
    id: 3,
    fileName: "revolut-stocks-2026-04.csv",
    status: "FAILED",
    uploadedAt: "2026-04-28T08:10:00Z",
    recordCount: 54,
  },
  {
    id: 4,
    fileName: "revolut-stocks-current.csv",
    status: "PROCESSING",
    uploadedAt: "2026-06-29T11:45:00Z",
    recordCount: 183,
  },
];
