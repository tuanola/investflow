import { createBrowserRouter } from "react-router-dom";
import { Layout } from "../components/Layout";
import { UploadPage } from "../pages/UploadPage";
import { ImportsPage } from "../pages/ImportsPage";
import { ImportDetailPage } from "../pages/ImportDetailPage";
import { PortfolioOverviewPage } from "../pages/PortfolioOverviewPage.tsx";
import { HoldingsPage } from "../pages/HoldingsPage";
import { TransactionsPage } from "../pages/TransactionsPage";
import { ReportsPage } from "../pages/ReportsPage";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        index: true,
        element: <UploadPage />,
      },
      {
        path: "imports",
        element: <ImportsPage />,
      },
      {
        path: "imports/:importId",
        element: <ImportDetailPage />,
      },
      {
        path: "overview",
        element: <PortfolioOverviewPage />,
      },
      {
        path: "holdings",
        element: <HoldingsPage />,
      },
      {
        path: "transactions",
        element: <TransactionsPage />,
      },
      {
        path: "reports",
        element: <ReportsPage />,
      },
    ],
  },
]);