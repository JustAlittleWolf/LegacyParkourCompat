# Coordinate authorized movement work

Coordinate only the phases and checks authorized for the assignment. Keep current scope and progress in [research](../../research/README.md); this directory holds reusable instructions only.

## Authority, roles and capacity

The master creates a few separate suborchestrator chats for non-overlapping workstreams. Workers do not delegate by default. Grant each suborchestrator explicit delegation rights with a maximum number of additional agents and permitted hierarchy levels; its own slot also counts toward capacity. Do not make one chat per tiny slice. State each role's writable paths, allowed inputs, integration/report ownership, global resource budget and absolute end time. Child budgets and concurrent work count against the same global limit.

Reserve a closing window before the deadline to stop dispatch, finish bounded current steps and collect commits/checkpoints. Forward pause/stop instructions to every active descendant; do not launch replacement work while paused. Verify that active workers have stopped or finished before the closing response, and report exact open work. Do not silently extend the global budget or end time.

Use an economical reasoning model for bounded work and a stronger model for unresolved complexity; do not hard-code model names. Determine actual concurrency with bounded capacity probes. Desktop chat capacity and per-chat subagent slots are distinct; neither proves unlimited global parallelism. Reserve resources for independent reviews and serial integration. Maintain one exclusive source-preparation owner and one product integration/build owner.

Source preparation writes only canonical `decompiled_minecraft/` in the main repository; all consumers are read-only. Source workers/reviewers receive source-only inputs and do not inspect implementation or either wiki lane before full-pair freeze. Wiki lanes remain separate and provide hints plus located source evidence for reconciliation after freeze. Independent implementation uses the clean infrastructure export described in [implementation](../implementation/README.md), with no old implementation/history. The product integrator alone reconciles that output with existing implementations.

## Release planning

Start with the newest bugfix release in each minor line from 1.8 through 1.20, multiple movement-relevant boundaries within 1.21, then the newest bugfix release in each 26.1/26.2/… line through the exact modern target configured in `gradle.properties`. Resolve the exact release list and source readiness before dispatch. Long-term coverage targets all stable releases, refining broad comparisons with intermediate exact versions. Existing coarse endpoint pairs neither prove patch boundaries nor complete a release line.

## Dispatch and handoff

1. Inspect the small progress index, accepted immutable evidence and unresolved queues. Reuse valid work; do not repeat discovery just because a chat is new. Assign exclusive report/code writers and independent reviewers.
2. Dispatch ready bounded work with complete inputs. When discovery and implementation are jointly authorized, ready accepted implementation takes priority for the next free capacity; do not interrupt running assignments. Close dependencies and reviews before opening more slices. A pending gate or real capacity limit is a recorded blocker, not a reason to duplicate assignments.
3. Track pair coverage/dependency/audit separately from per-finding source acceptance, product integration and runtime parity. Never equate a worker handoff, source-confirmed declaration, catalog entry or build with complete discovery or runtime validation.
4. Before source-pair freeze, require full tick/inventory coverage, closed dependencies and independent blind coverage audit. Early implementation can consume only an independently accepted immutable finding with closed dependencies and established applicability; its pair remains open and blind.
5. Require coherent commits and checkpoints from writing workers. The product integrator merges the default branch before handoff, inspects semantic conflicts, compiles with all Gradle `Test` tasks disabled plus `-x test`, and records corrections. Source-only lanes must not merge product changes that reveal excluded material.
6. Return completed work, immutable evidence, exact open blockers and next actions within the global budget/end time. Runtime work follows [testing](../testing/README.md) only when explicitly authorized.

## Assignment template

```text
Role and delegation rights: <master / suborchestrator / worker / reviewer; max additional agents and permitted levels; own slot counts>.
Action and exact inputs: <pair/finding/slices; immutable evidence/source identity>.
Environment and ownership: <OS, exact worktree/branch/base, writable paths>.
Isolation: <allowed reads and prohibited lanes/history>.
Budget and deadline: <shared global cap, this allocation, absolute time/timezone, closing window>.
Authorized checks: <static/compile/runtime permission scope>.
Output and acceptance: <report/patch/verdict, dependencies, independent gates>.
Handoff: <commit/path, unresolved work, next exact action>.
```

Messages contain the assignment's actions, inputs, constraints and output. Omit chat history, user-message attribution and unrelated campaign details.
