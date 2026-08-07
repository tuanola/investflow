import { expect, test } from "@playwright/test";

test("uploads a CSV and displays the created import", async ({ page }) => {
  const fileName = `e2e-import-${Date.now()}.csv`;

  await page.goto("/");
  await page.getByLabel("Revolut CSV export").setInputFiles({
    name: fileName,
    mimeType: "text/csv",
    buffer: Buffer.from("Date,Ticker\n2026-08-07,AAPL\n"),
  });

  await expect(page.getByText(fileName)).toBeVisible();

  const uploadResponsePromise = page.waitForResponse(
    (response) =>
      response.url().endsWith("/api/v1/imports") &&
      response.request().method() === "POST",
  );

  await page.getByRole("button", { name: "Start import" }).click();

  const uploadResponse = await uploadResponsePromise;
  expect(uploadResponse.status()).toBe(201);

  const { importId } = (await uploadResponse.json()) as { importId: number };

  await expect(page).toHaveURL(/\/imports$/);

  const importRow = page.getByRole("row").filter({ hasText: fileName });
  await expect(importRow).toBeVisible();
  await expect(importRow.getByText("UPLOADED", { exact: true })).toBeVisible();
  await expect(importRow.getByRole("cell").nth(2)).toHaveText("0");
  await expect(importRow.getByRole("link", { name: "View" })).toHaveAttribute(
    "href",
    `/imports/${importId}`,
  );
});
