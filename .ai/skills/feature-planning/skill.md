---

name: feature-planning
description: Turn a rough feature idea into a simple, actionable implementation plan with scope, assumptions, acceptance criteria, tests, risks, and the simplest good solution. Use when asked to plan a new feature, break down work, define an MVP, or prepare an implementation sequence.
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Feature Planning

## Goal

Turn a feature idea into a practical plan that is small, clear, and implementable. Prefer the simplest approach that delivers value and supports learning, testing, and maintainability.

## Input to gather

When needed, identify:

* the feature goal
* the user problem being solved
* the current stack and repo context
* constraints such as time, scope, team size, and technical limits
* dependencies or related features
* whether the goal is MVP, learning, or interview preparation

## Working approach

1. Restate the feature in one short paragraph.
2. Define the smallest useful scope.
3. List assumptions and open questions.
4. Break the work into small implementation steps.
5. Identify tests, data changes, API changes, and UI changes.
6. Call out risks, dependencies, and the simplest alternative.
7. Keep the plan easy to hand to another developer or to implement directly.

## Output format

Use this structure by default:

### Summary

State the feature in one or two sentences.

### MVP scope

List only the minimum behavior needed to deliver value.

### Implementation steps

Provide a short ordered sequence of steps.

### Acceptance criteria

State clear checks that define done.

### Tests

List the most important tests to add or update.

### Risks and dependencies

Call out blockers, edge cases, and anything that could change the plan.

### Simpler alternative

Describe the smallest possible version if the full feature feels too large.

### Interview notes

Summarize the design choices and tradeoffs worth explaining.

## Rules

* Keep plans simple and concrete.
* Prefer one feature at a time.
* Avoid overengineering.
* Explain why a technology or abstraction is needed before introducing it.
* Mention what a simpler solution would be.
* Separate scope, assumptions, and implementation.
* Challenge unnecessary complexity.
* Ask for missing context only when it changes the plan.
* Keep explanations concise unless a deeper dive is requested.

## Style

* Use plain language.
* Be direct and structured.
* Make the plan easy to execute.
* Focus on practical next steps, not broad theory.

## Good questions to ask

* What is the smallest version that is still useful?
* What is the user trying to do?
* What can be deferred safely?
* What will be hardest to test or maintain?
* What would be easiest to explain in an interview?

## Example uses

* Break a file-import feature into backend, validation, persistence, and UI steps.
* Plan a portfolio analytics page with a minimal first version.
* Define acceptance criteria for a new API endpoint.
* Turn a rough idea into a small, testable implementation sequence.
