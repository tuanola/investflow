import type { PortfolioImport } from "./types";

export const mockImports: PortfolioImport[] = [
  {
    id: "imp_001",
    filename: "revolut-stocks-2026-06.csv",
    status: "completed",
    createdAt: "2026-06-18T10:21:00Z",
    completedAt: "2026-06-18T10:21:34Z",
    rowCount: 148,
    transactionCount: 92,
  },
  {
    id: "imp_002",
    filename: "revolut-stocks-2026-05.csv",
    status: "completed",
    createdAt: "2026-05-30T19:42:00Z",
    completedAt: "2026-05-30T19:42:27Z",
    rowCount: 121,
    transactionCount: 76,
  },
  {
    id: "imp_003",
    filename: "revolut-stocks-2026-04.csv",
    status: "failed",
    createdAt: "2026-04-28T08:10:00Z",
    rowCount: 54,
    transactionCount: 0,
  },
  {
    id: "imp_004",
    filename: "revolut-stocks-current.csv",
    status: "processing",
    createdAt: "2026-06-29T11:45:00Z",
    rowCount: 183,
  },
];