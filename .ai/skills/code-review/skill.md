---

name: code-review
description: Review code changes for correctness, readability, maintainability, tests, edge cases, and architecture tradeoffs. Use when asked to review a diff, PR, patch, refactor, bug fix, or implementation plan, and when the user wants concise feedback, risks, and improvements.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Code Review

## Goal

Review code changes with a practical, critical eye. Focus on correctness, clarity, maintainability, test coverage, and whether the change is the simplest good solution.

## Input to gather

When needed, identify:

* the code change or diff to review
* the feature or bug being addressed
* the relevant stack, module, or subsystem
* any constraints, goals, or known risks
* whether the review should focus on correctness, architecture, tests, performance, or style

## Working approach

1. Restate the purpose of the change in one short paragraph.
2. Check correctness first.
3. Check edge cases, error handling, and regressions.
4. Check readability, naming, structure, and duplication.
5. Check tests and whether behavior is covered adequately.
6. Check whether the solution is unnecessarily complex.
7. Call out any follow-up work only when it is clearly useful.

## Output format

Use this structure by default:

### Summary

State the overall review verdict in one or two sentences.

### What looks good

List the strongest parts of the change.

### Issues to fix

List concrete problems, ordered by importance.

### Suggestions

List improvements that are helpful but not strictly required.

### Test coverage

State what is missing, weak, or worth adding.

### Architecture and tradeoffs

Call out design choices, complexity, and whether a simpler alternative exists.

### Final verdict

Give a short closing judgment, such as approve, approve with changes, or request changes.

## Rules

* Prioritize correctness over style.
* Be specific about what is wrong and why it matters.
* Separate blocking issues from nice-to-have suggestions.
* Prefer the simplest solution that meets the requirements.
* Challenge overengineering or unclear abstractions.
* Mention missing tests when behavior changes.
* Do not nitpick formatting unless it affects readability or consistency.
* If context is missing, make the minimum necessary assumptions and say so.
* Keep the review concise unless a deeper review is requested.

## Style

* Be direct and practical.
* Use plain language.
* Focus on consequences, not abstract theory.
* Keep feedback actionable.
* Avoid unnecessary depth unless the user asks for it.

## Good questions to ask

* Does this change do exactly what it claims to do?
* What breaks if this input or edge case appears?
* Is there a simpler design?
* Are the tests strong enough to catch regressions?
* Is the code easy to understand and modify later?
* Would this be easy to explain in an interview?

## Example review focus

* Validate the main logic and error handling.
* Check whether the API contract changed safely.
* Check whether database changes are consistent and reversible.
* Check whether UI behavior is covered by tests.
* Check whether the implementation is maintainable and worth the added complexity.
