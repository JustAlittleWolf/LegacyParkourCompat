# Recent movement source audit

- Status: preliminary; assigned owners are still active. No pair is accepted complete.
- Assigned pairs: 1.21.1–1.21.3, 1.21.3–1.21.4, 1.21.4–1.21.5, 1.21.5–1.21.8, 1.21.8–1.21.10, 1.21.10–1.21.11, 1.21.11–26.1.2, 26.1.2–26.2.
- Branch/worktree: requested managed worktree was created at `C:\Users\Wolfi\.codex\worktrees\coverage-review-recent\LegacyParkourCompat`; Git operations there failed with `fatal: this operation must be run in a work tree` for both `git status --short --branch` and `git switch -c feat/coverage-review-recent`. No replacement checkout was created.

## Independent disposition

All inspected drafts remain active and incomplete. Their planned/stage ledgers are not source coverage. Exact pair-specific readiness records, body-level evidence, full caller/callee closure, registrations/resources and semantic comparisons are still outstanding. No draft is accepted complete.

| Pair | Owner draft observed | Disposition |
|---|---|---|
| 1.21.1 → 1.21.3 | `run.md`, active; exact sources pending; seven coarse stage roots pending | Not accepted; no comparison evidence. |
| 1.21.3 → 1.21.4 | `run.md`, active; seven required inventory maps are present but pending; no bounded slice blocks; exact pair awaits ready publication | Not accepted; no comparison evidence. Checker also has a false top-status error (see below). |
| 1.21.4 → 1.21.5 | `run.md`, active; 17 navigation slices pending before method-level expansion; exact sources pending | Not accepted; no comparison evidence. |
| 1.21.5 → 1.21.8 | `run.md`, active; neither endpoint had a validated ready publication; seven provisional stages pending | Not accepted; no comparison evidence. |
| 1.21.8 → 1.21.10 | `run.md`, active; seven provisional stage envelopes pending; exact sources pending | Not accepted; no comparison evidence. |
| 1.21.10 → 1.21.11 | `run.md`, active; 50 planned slices, all pending; exact sources unavailable | Not accepted; no comparison evidence. The 50-row plan is a queue, not proof that every reachable movement method/provider was inventoried. |
| 1.21.11 → 26.1.2 | `run.md`, active; 50 planned slices, all pending; exact sources unavailable | Not accepted; no comparison evidence. The report explicitly says source comparison has not begun. |
| 26.1.2 → 26.2 | `run.md`, active; seven coarse stages pending; 26.1.2 source pending | Not accepted; one-sided 26.2 provenance cannot establish a difference or equality. |

## Verified source handoff

For 26.2, independently read `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated.ready.json`. It names exact release `26.2`, native `unobfuscated`, and the source/artifact/diagnostic manifests. Recomputed hashes match the three cited manifest hashes. The client jar hash `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290` was recomputed and matches `artifacts.sha256`; `version.json` SHA-256 is `4d6b3a5a27fed3c0faab2cb4b6dee7318be310bc3c627a78951519ffefaf1d0c` and its metadata ID is `26.2`. The four cited source hashes match their entries in `unobfuscated.sources.sha256`. The diagnostics contain anchors for `Entity.move`, `Entity.moveRelative`, `LivingEntity.travel`, `LivingEntity.jumpFromGround`, `Player.travel`, and `LocalPlayer.aiStep`.

This validates only 26.2 artifact identity and those reported hashes/anchors. The diagnostic list is not an inventory of every movement caller, writer, provider or data dependency. It does not establish intact bodies for every reachable method, exact input-to-collision call order, state-writer closure, block/fluid registration coverage, pose-resize timing, collision/step/support coverage, or any pairwise no-difference claim. The 26.1.2 side remains unavailable in the draft. The latest owner checkpoint correctly plans native `unobfuscated` for 26.1.2 to align official names with 26.2; an earlier Mojmap request was corrected. Verify the actual A ready marker, exact release ID and input artifact before admitting that alignment.


## Hardened schema and static checker

The two workflow-only commits were reviewed by file list and cherry-picked: `40c34f5` (coverage workflow/checker/template updates) and `2422192` (campaign roster). The checker was run against each owner worktree report; all eight returned exit 1. This is a schema/status signal, not source evidence:

- All eight reports currently have no bounded `### Slice` entries. Their tables, 7-stage envelopes and 17/50-row navigation plans are not the required per-slice method/body evidence.
- Seven reports omit the seven required `INV-*` maps. The 1.21.3→1.21.4 report has all seven maps but each is pending and it has no bounded slices.
- The 1.21.3→1.21.4 checker error `expected exactly one top-level status, found 3` is a false positive in the checker: it counts the intended `- Status: pending` lines under `## Blind-discovery freeze` and `## Independent source audit` along with the single top-level status. The checker implementation's unscoped regex `^- Status:` causes it. Its other error (`no bounded coverage slice entries found`) is accurate.
- Reports with pending sources/slices are expected not to pass completion. No one should rewrite them as complete to satisfy the checker.
## Required requeue / acceptance gaps

- All three previously absent reports (1.21.3→1.21.4, 1.21.4→1.21.5, 1.21.8→1.21.10) have now appeared as active drafts. Keep them open; the latter two still lack required inventory maps, and all three lack bounded `### Slice` evidence blocks.
- Every owner must keep the report partial/active until the exact aligned pair is ready and verified; source-queued stages cannot be closed as compared-no-difference or not-applicable.
- For each pair, independently verify exact IDs/namespace and cited ready/source/artifact/diagnostic hashes, then inspect complete relevant method bodies and decompiler diagnostics. Hash validity alone is not semantic evidence.
- Require an explicit per-tick chain through local input, tick/super-tick and travel dispatch, including velocity changes before travel and cancellation/restitution after `move`; compare jump dispatch/order and precise apex/threshold behavior; trace all pose/dimension writers and resize query timing.
- Require all collision, stepping, support/edge probes, axis selection, callbacks, shape/AABB and fluid paths. Enumerate shape/property/callback providers, registrations, historical block availability and neighbor dependencies; inspect movement-relevant jar resources/tags.
- Require direct movement effects/attributes/equipment paths and external state writers/packet consumers, with producer→consumer/dependency closure. Keep health, food/hunger, regen, saturation, exhaustion, damage and combat emulation excluded; keep modern-only blocks and independent non-player physics out of historical scope.
- Treat the reported 1.8 jump-apex cutoff and panes/bars/sneaking-box concerns as hypotheses for their exact source pairs, not evidence or a complete checklist. They are outside the assigned recent pairs except any independently demonstrated 1.21+ pose/collision delta.

## Current unresolved state

- Pairwise findings verified: none.
- Accepted pairs: none.
- Three reports initially absent are now present as active drafts; no pair is accepted complete.
- Exact source pairs validated by this reviewer: none. The current ready root has exact ready JSON only for 26.2 among the nine assigned endpoints; the other seven endpoints have no ready marker. Only 26.2's one-sided ready record and cited artifact/source hashes were independently checked.
- Runtime validation, tests, builds, game/Gym/server/Docker launches: not performed.
- Reviewer worktree Git failure: `fatal: this operation must be run in a work tree`; branch creation and commits are blocked pending the repository worktree fix. Do not treat this preliminary note as a final audit or as owner completion.


## Additional 26.2 B-side inventory seeds

Read-only inspection of the published 26.2 source confirms that the seven diagnostic anchors leave substantial reachable paths to inventory before comparison. These are navigation seeds, not paired findings or closure:

- `net/minecraft/client/player/LocalPlayer.java` (source SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`): `tick` lines 227–251 calls `super.tick`, then sends input and movement packets; `aiStep` lines 767–919 reads prior/current jump and shift input, performs crouch-fit checks, ticks input, applies auto-jump, calls four `moveTowardsClosestSpace` probes, toggles sprint/flight, dispatches player jump/fall-flight/riding-jump behavior, and then calls `super.aiStep`; `move` begins at line 986 and needs its complete body/callees included for post-collision velocity handling.
- `net/minecraft/world/entity/player/Player.java`: `tick` begins at line 232; pose-fit checks appear at lines 355–374; `aiStep` begins at 442; player edge-backoff override begins at 880 (the base `Entity.maybeBackOffFromEdge` at lines 1097–1099 is identity); `travel` begins at 1402. These entry/override paths are not in the seven-item diagnostics list.
- `net/minecraft/world/entity/LivingEntity.java` (source SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`): `jumpFromGround` begins at 2389; diagnostics anchor selected travel helpers, but jump dispatch, conditions, stored velocity and call order still need a complete chain.
- `net/minecraft/world/entity/Entity.java` (source SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`): `collide` begins at 1141; step candidate collection/sorting at 1172–1193; collision collection and axis resolution at 1195–1260; synced pose updates call `refreshDimensions` at 3379–3409. `move` starts at line 711; shape, support, post-move velocity cancellation and callbacks must be traced through full callers/callees. These collision/pose methods are absent from the short diagnostic anchors.

The short diagnostics are not claimed to be comprehensive by the 26.1.2→26.2 owner, so these are required next inventory rows, not an owner misstatement of completed coverage. The source owner must still verify full method bodies and dependencies on both releases; the A source is not yet available. This side-only inventory does not establish 26.2 behavior equivalence or a historical delta.

Reviewer task branch is `feat/coverage-review-recent`, created from main `002137b227676caea77f6832b9f4c8d0b6200bff`; preliminary checkpoint commit: `653b745335211dcddd867923876f0ebb15b42eac`.
