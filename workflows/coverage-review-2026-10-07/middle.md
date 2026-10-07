# Independent source-only coverage review — middle campaign

- Review status: active; no assigned pair accepted complete.
- Review base: `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`.
- Requested review branch: `feat/coverage-review-middle`.
- Scope: source-discovery reports for the twelve assigned pairs only. No runtime implementation, wiki pages, prior wiki audits, tests, game, Gym, server, Docker, Gradle build, or decompilation was used.
- Review limitation: the managed checkout was created at `C:\Users\Wolfi\.codex\worktrees\coverage-review-middle\LegacyParkourCompat`, detached at the main commit. `git -C '<that path>' status --short --branch` returned `fatal: cannot change to '<that path>': Permission denied`. Per handoff instructions, no checkout was recreated. This checkpoint is therefore in the writable main checkout and is not committed on the requested review branch.

## Evidence inspected

- Read the global `C:/Users/Wolfi/.codex/AGENTS.md`, project `README.md`, `workflows/README.md`, and `workflows/movement-discovery/README.md`.
- Read the committed source-only `run.md` reports on the assigned owner branches where present. These are active run manifests, not frozen discovery catalogs.
- Read the exact readiness JSON at `build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather.ready.json`. It reports exact version `1.14.4`, namespace `ornithe-feather`, ready status, and source/artifact/diagnostics manifests.
- Independently recalculated the SHA-256 values of the three cited manifests/diagnostics and matched the JSON values. The source manifest entries for `net/minecraft/entity/Entity.java`, `net/minecraft/entity/living/LivingEntity.java`, and `net/minecraft/entity/living/player/PlayerEntity.java` match the actual published source bytes.
- The diagnostics identify `Entity.move`, `LivingEntity.jump`/`moveRelative` and `PlayerEntity.jump`/`moveRelative`, but this verifies source presence only. It does not establish semantic coverage or body-by-body parity. The second endpoint for 1.14.4 → 1.15.2 is not published, so no paired comparison has begun.
- At the latest ready-directory inventory, 1.14.4 was the only newly published assigned endpoint. No readiness marker was present for 1.15.2, 1.16.5, 1.17.1, 1.18.2, 1.19.2, 1.19.3, 1.19.4, 1.20.1, 1.20.2, 1.20.4, 1.20.6, or 1.21.1. Directory presence alone is not treated as readiness.

## Pair disposition

Every pair remains **unaccepted / pending source comparison**. Current owner manifests and their coverage readiness:

| Pair | Owner report state observed | Independent disposition |
|---|---|---|
| 1.14.4 → 1.15.2 | Active; seven stage-level rows pending. | No paired source evidence; 1.14.4 provenance partially verified, 1.15.2 absent. Seven coarse rows are an inventory, not closure. |
| 1.15.2 → 1.16.5 | Active; 34 named bounded slices pending. | No pair artifacts. Keep all rows open; source/body checks and dependency closure not started. |
| 1.16.5 → 1.17.1 | Active; 34 bounded slices explicitly pending. | No pair artifacts. Ledger is appropriately explicit that it is a work plan, not findings or equivalence. |
| 1.17.1 → 1.18.2 | Active; 49 bounded slices pending. | No pair artifacts. No findings or terminal rows accepted. |
| 1.20.6 → 1.21.1 | Active; 35 named slices pending. | No pair artifacts. No findings or terminal rows accepted. |
| 1.18.2 → 1.19.2 | No committed report found on the named owner branch at this checkpoint. | Cannot review owner coverage until a report is committed or otherwise made available through the authorized worktree. |
| 1.19.2 → 1.19.3 | Active; 20 broad initial families pending. | No pair artifacts. Rows must be refined to method/resource slices before acceptance. |
| 1.19.3 → 1.19.4 | Active; seven coarse stage inventories pending. | No pair artifacts. These rows cannot establish whole-stage coverage. |
| 1.19.4 → 1.20.1 | Active; seven stage-level rows pending. | No pair artifacts. No paired evidence or terminal coverage. |
| 1.20.1 → 1.20.2 | No committed report found on the named owner branch at this checkpoint. | Cannot review owner coverage until a report is committed or otherwise made available through the authorized worktree. |
| 1.20.2 → 1.20.4 | Active; seven coarse stage registrations pending, with resource dependency open. | No pair artifacts. Owner correctly labels rows coarse; expand them and close cited resources before any completion claim. |
| 1.20.4 → 1.20.6 | No committed report found on the named owner branch at this checkpoint. | Cannot review owner coverage until a report is committed or otherwise made available through the authorized worktree. |

No behavioral omission is marked confirmed from these drafts: no paired source comparisons have been presented. A report missing from the branch is an availability gap, not evidence that the owner omitted a mechanic.

## Review checklist to apply as reports freeze

For each pair, I will require source-backed rows and dependency closure for:

1. The whole reachable client player tick chain, including input capture, repeated or pre/post travel movement, and the timing of velocity cancellation before and after travel.
2. Jump producer and dispatch order, guards, cooldowns, arithmetic precision, casts, literal types, operation order, and relevant cutoff behavior.
3. Every player movement-state writer and reader: pose and dimensions, resize timing, eye height, ground/fluid/collision flags, input history, abilities, sprint/jump timers, and the collision queries affected by those writes.
4. Every collision and step path: axis order, candidate selection and ties, support/edge probes, grounding, shape contexts, and callback/query timing.
5. Enumerated collision-shape providers and registrations for blocks present in the older release, including state, neighbor and context dependencies. The user hypotheses about panes/bar shapes and sneaking collision boxes remain unverified.
6. Direct movement effects, enchantments, attributes, equipment and item-use inputs, traced from consumer to producer/registration/application/removal/default/resource. Health, food, hunger, saturation, exhaustion, damage and combat emulation remain excluded.
7. Direct player position/velocity inputs from packet corrections, pushes, explosions, pistons, mounts and launch items, with external or server-supplied values identified as such.

No broad stage label, matching method name, source hash, or unchanged travel branch is accepted as a substitute for exact member ranges, both-side evidence, callers/writers and dependency closure. For relevant damaged decompiler bodies, the owner must obtain exact bytecode/source assistance before that row can close.

## Requeue requests for the parent to route

- Publish and announce exact 1.15.2 readiness JSON and cited manifests/diagnostics to unblock the first assigned pair. 1.14.4 has a ready marker whose declared manifest hashes and three core movement source entries were verified here.
- Publish exact readiness records for 1.16.5, 1.17.1, 1.18.2, 1.19.2, 1.19.3, 1.19.4, 1.20.1, 1.20.2, 1.20.4, 1.20.6 and 1.21.1. Continue to serialize source writes under the source-owner lock.
- Commit or expose the missing source-only run reports for 1.18.2 → 1.19.2, 1.20.1 → 1.20.2 and 1.20.4 → 1.20.6 so their coverage can be reviewed independently.
- Resolve the review checkout’s exact `Permission denied` access issue or provide the permitted workspace path. Do not create another checkout; the task explicitly says to report Git worktree-operation failures instead of recreating worktrees.

## Resume point

Continue from the newest committed owner reports and readiness records. First verify each pair’s exact IDs, namespace, cited hashes, source method diagnostics and actual relevant bodies. Then inspect the frozen discovery report against the checklist above and record pair-specific accepted rows, confirmed omissions, overstated claims, missing evidence and unresolved dependencies. Do not mark a pair accepted while its owner report remains active or any scoped row remains open.
