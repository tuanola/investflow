import { expect, test } from "@playwright/test";

test("opens the upload page", async ({ page }) => {
  await page.goto("/");

  await expect(
    page.getByRole("heading", { name: "Upload Revolut Export" }),
  ).toBeVisible();
});