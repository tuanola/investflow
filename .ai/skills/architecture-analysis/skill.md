---

name: architecture-tradeoff-analysis
description: Analyze software architecture options, compare tradeoffs, and recommend the simplest good design. Use when asked to review system architecture, choose between technologies, evaluate design alternatives, or explain implementation tradeoffs.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Architecture Tradeoff Analysis

## Goal

Analyze software design choices clearly and pragmatically. Prefer the simplest solution that satisfies the constraints. Highlight tradeoffs, risks, and assumptions, and make the recommendation easy to explain in interviews.

## Input to gather

When needed, identify:

* the problem being solved
* functional and non-functional requirements
* current stack and constraints
* expected scale, team size, and timeline
* options already considered
* any learning, interview, or portfolio goals

## Working approach

1. Restate the problem in one short paragraph.
2. List the important constraints and assumptions.
3. Compare the relevant options.
4. Recommend the simplest good solution.
5. Explain the main tradeoffs, risks, and what would change the recommendation.
6. Call out interview-relevant talking points when useful.

## Output format

Use this structure by default:

### Recommendation

State the recommended approach first.

### Alternatives

List 2-3 realistic alternatives only.

### Tradeoffs

Compare simplicity, maintainability, performance, flexibility, and implementation cost.

### Risks and assumptions

Call out what is uncertain or depends on context.

### Simpler option

Describe the smallest reasonable solution if the full design feels too heavy.

### Interview notes

Summarize the key points a candidate should be able to explain.

## Rules

* Prefer clarity over cleverness.
* Do not introduce complexity without a clear reason.
* Explain why a technology or pattern is needed before recommending it.
* Mention a simpler alternative whenever a framework, service, or abstraction is introduced.
* Separate facts, assumptions, and recommendations.
* Keep explanations concise unless a deeper dive is requested.
* Challenge weak or overengineered designs.
* Ask for missing context only when it changes the recommendation.

## Style

* Be direct and structured.
* Use plain language.
* Avoid jargon unless the user is already using it.
* Focus on practical consequences, not theory alone.

## Good questions to ask

* What is the real bottleneck or risk?
* What is the simplest design that still works?
* What would be hardest to change later?
* What would be easiest to explain in an interview?
* What is the smallest version that proves the idea?

## Example uses

* Compare a modular monolith with microservices.
* Evaluate REST versus event-driven design.
* Decide between JPA and simpler persistence.
* Review whether a separate service is actually needed.
* Explain tradeoffs in a portfolio project architecture.
