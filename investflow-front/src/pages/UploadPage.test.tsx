import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { afterEach, describe, expect, it, vi } from "vitest";
import { UploadPage } from "./UploadPage";

function asFileList(file: File): FileList {
  const files = {
    0: file,
    length: 1,
    item: (index: number) => (index === 0 ? file : null),
  };

  return Object.setPrototypeOf(files, FileList.prototype) as FileList;
}

function renderPage() {
  return render(
    <MemoryRouter initialEntries={["/"]}>
      <Routes>
        <Route path="/" element={<UploadPage />} />
        <Route path="/imports" element={<div>Imports page</div>} />
      </Routes>
    </MemoryRouter>,
  );
}

describe("UploadPage", () => {
  afterEach(() => vi.restoreAllMocks());

  it("uploadsTheSelectedCsvAndNavigatesToImports", async () => {
    const fetchMock = vi
      .spyOn(globalThis, "fetch")
      .mockResolvedValue(
        new Response(JSON.stringify({ importId: 42 }), { status: 201 }),
      );

    renderPage();

    const file = new File(["Date,Ticker\n"], "portfolio.csv", {
      type: "text/csv",
    });
    fireEvent.change(screen.getByLabelText("Revolut CSV export"), {
      target: { files: asFileList(file) },
    });
    fireEvent.click(screen.getByRole("button", { name: "Start import" }));

    expect(await screen.findByText("Imports page")).toBeInTheDocument();
    expect(fetchMock).toHaveBeenCalledWith("/api/v1/imports", {
      method: "POST",
      body: expect.any(FormData),
    });
    const request = fetchMock.mock.calls[0]?.[1];
    const uploadedFile = (request?.body as FormData).get("file") as File;
    expect(uploadedFile.name).toBe("portfolio.csv");
  });

  it("showsAnErrorAndKeepsTheFileWhenUploadFails", async () => {
    vi.spyOn(globalThis, "fetch").mockResolvedValue(
      new Response(JSON.stringify({ message: "Unexpected error" }), {
        status: 500,
      }),
    );

    renderPage();

    const file = new File(["Date,Ticker\n"], "portfolio.csv", {
      type: "text/csv",
    });
    fireEvent.change(screen.getByLabelText("Revolut CSV export"), {
      target: { files: asFileList(file) },
    });
    fireEvent.click(screen.getByRole("button", { name: "Start import" }));

    expect(await screen.findByRole("alert")).toHaveTextContent(
      "Could not upload the file. Please try again.",
    );
    expect(screen.getByText("portfolio.csv")).toBeInTheDocument();
    await waitFor(() =>
      expect(screen.getByRole("button", { name: "Start import" })).toBeEnabled(),
    );
  });
});
