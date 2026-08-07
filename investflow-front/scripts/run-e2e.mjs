import { spawn } from "node:child_process";
import { fileURLToPath } from "node:url";
import path from "node:path";

const frontendDirectory = path.resolve(
  path.dirname(fileURLToPath(import.meta.url)),
  "..",
);
const repositoryDirectory = path.resolve(frontendDirectory, "..");
const composeFile = path.join(repositoryDirectory, "docker-compose.e2e.yml");
const composeArguments = ["compose", "-f", composeFile];
const playwrightExecutable =
  process.platform === "win32" ? "playwright.cmd" : "playwright";
const playwrightArguments = ["test", ...process.argv.slice(2)];

function run(command, args, cwd = repositoryDirectory) {
  return new Promise((resolve, reject) => {
    const child = spawn(command, args, {
      cwd,
      env: process.env,
      stdio: "inherit",
    });

    child.once("error", reject);
    child.once("exit", (code, signal) => {
      resolve(code ?? (signal ? 1 : 0));
    });
  });
}

async function runSuccessfully(command, args, cwd) {
  const exitCode = await run(command, args, cwd);

  if (exitCode !== 0) {
    throw new Error(`${command} exited with code ${exitCode}`);
  }
}

async function waitForBackend() {
  const deadline = Date.now() + 180_000;

  while (Date.now() < deadline) {
    try {
      const response = await fetch("http://127.0.0.1:18080/actuator/health", {
        signal: AbortSignal.timeout(5_000),
      });

      if (response.ok) {
        return;
      }
    } catch {
      // The backend is still starting.
    }

    await new Promise((resolve) => setTimeout(resolve, 1_000));
  }

  throw new Error("The E2E backend did not become healthy within 180 seconds");
}

async function removeE2eStack() {
  return run("docker", [
    ...composeArguments,
    "down",
    "--volumes",
    "--remove-orphans",
  ]);
}

async function main() {
  if (playwrightArguments.includes("--list")) {
    process.exitCode = await run(
      playwrightExecutable,
      playwrightArguments,
      frontendDirectory,
    );
    return;
  }

  let exitCode = 1;

  try {
    await runSuccessfully("docker", [
      ...composeArguments,
      "down",
      "--volumes",
      "--remove-orphans",
    ]);
    await runSuccessfully("docker", [
      ...composeArguments,
      "up",
      "--build",
      "--detach",
    ]);
    await waitForBackend();

    exitCode = await run(
      playwrightExecutable,
      playwrightArguments,
      frontendDirectory,
    );
  } catch (error) {
    console.error(error instanceof Error ? error.message : error);
  } finally {
    const cleanupExitCode = await removeE2eStack();

    if (cleanupExitCode !== 0) {
      console.error("Could not remove the disposable E2E stack");
      exitCode = 1;
    }
  }

  process.exitCode = exitCode;
}

await main();
