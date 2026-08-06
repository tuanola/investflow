import { render, screen } from "@testing-library/react";
import { delay, http, HttpResponse } from "msw";
import { MemoryRouter } from "react-router-dom";
import { describe, expect, it } from "vitest";
import { ImportsPage } from "./ImportsPage";
import { server } from "@/test/server";

const importsEndpoint = "http://localhost:5173/api/imports";

function renderPage() {
  return render(
    <MemoryRouter>
      <ImportsPage />
    </MemoryRouter>,
  );
}

describe("ImportsPage", () => {
  it("showsLoadingStateWhileImportsAreLoading", () => {
    server.use(
      http.get(importsEndpoint, async () => {
        await delay("infinite");
        return HttpResponse.json([]);
      }),
    );

    renderPage();

    expect(screen.getByText("Loading imports…")).toBeInTheDocument();
  });

  it("rendersImportsReturnedByBackend", async () => {
    let requestedPath: string | undefined;
    server.use(
      http.get(importsEndpoint, ({ request }) => {
        requestedPath = new URL(request.url).pathname;

        return HttpResponse.json([
          {
            id: 42,
            fileName: "portfolio.csv",
            status: "COMPLETED",
            uploadedAt: "2026-08-06T12:00:00Z",
            recordCount: 125,
          },
        ]);
      }),
    );

    renderPage();

    expect(await screen.findByText("portfolio.csv")).toBeInTheDocument();
    expect(screen.getByText("COMPLETED")).toBeInTheDocument();
    expect(screen.getByText("125")).toBeInTheDocument();
    expect(screen.getByRole("link", { name: "View" })).toHaveAttribute(
      "href",
      "/imports/42",
    );
    expect(requestedPath).toBe("/api/imports");
  });

  it("showsEmptyStateWhenBackendReturnsNoImports", async () => {
    server.use(
      http.get(importsEndpoint, () => HttpResponse.json([])),
    );

    renderPage();

    expect(await screen.findByText("No imports found.")).toBeInTheDocument();
  });

  it("showsErrorWhenBackendReturnsNonSuccessfulResponse", async () => {
    server.use(
      http.get(importsEndpoint, () =>
        HttpResponse.json({ message: "Unexpected error" }, { status: 500 }),
      ),
    );

    renderPage();

    expect(await screen.findByRole("alert")).toHaveTextContent(
      "Could not load imports. Please try again later.",
    );
  });

  it("showsErrorWhenBackendCannotBeReached", async () => {
    server.use(
      http.get(importsEndpoint, () => HttpResponse.error()),
    );

    renderPage();

    expect(await screen.findByRole("alert")).toHaveTextContent(
      "Could not load imports. Please try again later.",
    );
  });
});
