# Recent movement source audit

- Status: preliminary; assigned owners are still active. No pair is accepted complete.
- Assigned pairs: 1.21.1–1.21.3, 1.21.3–1.21.4, 1.21.4–1.21.5, 1.21.5–1.21.8, 1.21.8–1.21.10, 1.21.10–1.21.11, 1.21.11–26.1.2, 26.1.2–26.2.
- Branch/worktree: requested managed worktree was created at `C:\Users\Wolfi\.codex\worktrees\coverage-review-recent\LegacyParkourCompat`; Git operations there failed with `fatal: this operation must be run in a work tree` for the initial `git status --short --branch` and `git switch -c feat/coverage-review-recent` attempts. The same checkout worked with the reviewed elevated operation; branch `feat/coverage-review-recent` is active. No replacement checkout was created.

## Independent disposition

All inspected drafts remain active and incomplete. Their planned/stage ledgers are not source coverage. Exact pair-specific readiness records, body-level evidence, full caller/callee closure, registrations/resources and semantic comparisons are still outstanding. No draft is accepted complete.

| Pair | Owner draft observed | Disposition |
|---|---|---|
| 1.21.1 → 1.21.3 | `run.md`, active; seven required inventory maps pending; no bounded slice blocks; exact sources pending | Not accepted; no comparison evidence. |
| 1.21.3 → 1.21.4 | `run.md`, active; seven required inventory maps are present but pending; no bounded slice blocks; exact pair awaits ready publication | Not accepted; no comparison evidence.  |
| 1.21.4 → 1.21.5 | `run.md`, active; seven inventory maps and 46 pending slice templates; exact sources pending | Not accepted; no comparison evidence. |
| 1.21.5 → 1.21.8 | `run.md`, active; seven inventory maps and 27 pending slice templates; exact sources pending | Not accepted; no comparison evidence. |
| 1.21.8 → 1.21.10 | `run.md`, active; seven inventory maps and 21 pending slice templates; exact sources pending | Not accepted; no comparison evidence. |
| 1.21.10 → 1.21.11 | `run.md`, active; 7 inventory maps, 44 pending slice templates plus 1 explicit out-of-scope row; exact sources unavailable | Not accepted; no comparison evidence. These are plans, not proof of producer/provider closure. |
| 1.21.11 → 26.1.2 | `run.md`, active; 7 inventory maps and 50 pending slice templates; exact sources unavailable | Not accepted; no comparison evidence. The report explicitly says source comparison has not begun. |
| 26.1.2 → 26.2 | `run.md`, active; seven inventory maps and 14 pending slice templates; 26.1.2 source pending | Not accepted; one-sided 26.2 provenance cannot establish a difference or equality. |

## Verified source handoff

For 26.2, independently read `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated.ready.json`. It names exact release `26.2`, native `unobfuscated`, and the source/artifact/diagnostic manifests. Recomputed hashes match the three cited manifest hashes. The client jar hash `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290` was recomputed and matches `artifacts.sha256`; `version.json` SHA-256 is `4d6b3a5a27fed3c0faab2cb4b6dee7318be310bc3c627a78951519ffefaf1d0c` and its metadata ID is `26.2`. The four cited source hashes match their entries in `unobfuscated.sources.sha256`. The diagnostics contain anchors for `Entity.move`, `Entity.moveRelative`, `LivingEntity.travel`, `LivingEntity.jumpFromGround`, `Player.travel`, and `LocalPlayer.aiStep`.

This validates only 26.2 artifact identity and those reported hashes/anchors. The diagnostic list is not an inventory of every movement caller, writer, provider or data dependency. It does not establish intact bodies for every reachable method, exact input-to-collision call order, state-writer closure, block/fluid registration coverage, pose-resize timing, collision/step/support coverage, or any pairwise no-difference claim. The 26.1.2 side remains unavailable in the draft. The latest owner checkpoint correctly plans native `unobfuscated` for 26.1.2 to align official names with 26.2; an earlier Mojmap request was corrected. Verify the actual A ready marker, exact release ID and input artifact before admitting that alignment.


## Hardened schema and static checker

The canonical follow-up `fba28fa154d29572263ea3f2c44cf1dc23134329` was reviewed and cherry-picked as `8cde578`. It fixes top-level status parsing and the required `->` field parsing, and accepts structurally valid non-complete reports without claiming closure. `DEP-CHECKER` is resolved by this commit.

- I reran the canonical checker against all eight current owner reports. Six returned exit 0 with `non-complete report status active is structurally valid; completion is not claimed`.
- The 1.21.1→1.21.3 and 1.21.3→1.21.4 reports returned exit 1: `no bounded coverage slice entries found`. That is a real ledger gap; those owners must add slice blocks.
- Structural acceptance is not source proof. All reports remain active, sources for the assigned pair endpoints remain unavailable, and no pair is accepted complete.
## Required requeue / acceptance gaps

- The three previously absent reports have now appeared as active drafts. Keep all reports open. The 1.21.1→1.21.3 and 1.21.3→1.21.4 owners must add bounded slices; all other owners must replace pending templates with exact pair evidence and close producer/dependency inventories.
- Every owner must keep the report partial/active until the exact aligned pair is ready and verified; source-queued stages cannot be closed as compared-no-difference or not-applicable.
- For each pair, independently verify exact IDs/namespace and cited ready/source/artifact/diagnostic hashes, then inspect complete relevant method bodies and decompiler diagnostics. Hash validity alone is not semantic evidence.
- For 26.1.2→26.2, add bounded water, lava, fall-flying and fluid-travel slices under `LivingEntity.travel` and add `LocalPlayer.move`/`updateAutoJump` after collision resolution; split combined input/crouch/unstuck/sprint and flight/fall-flying/riding slices. Resolve `S-STATE-01` placeholder ranges.
- Require all collision, stepping, support/edge probes, axis selection, callbacks, shape/AABB and fluid paths. Enumerate shape/property/callback providers, registrations, historical block availability and neighbor dependencies; inspect movement-relevant jar resources/tags.
- For 1.21.5→1.21.8, remove the overbroad exclusion of “any indirect sprint-gate finding”; compare direct movement eligibility predicates and their vanilla-state reads/order, while leaving food/hunger producer systems out of emulation and findings. Require direct movement effects/attributes/equipment and external state-writer/packet-consumer closure.
- Treat the reported 1.8 jump-apex cutoff and panes/bars/sneaking-box concerns as hypotheses for their exact source pairs, not evidence or a complete checklist. They are outside the assigned recent pairs except any independently demonstrated 1.21+ pose/collision delta.

## Current unresolved state

- Pairwise findings verified: none.
- Accepted pairs: none.
- Three reports initially absent are now present as active drafts; all eight remain active and no pair is accepted complete.
- Exact source pairs validated by this reviewer: none. The current ready root has exact ready JSON only for 26.2 among the nine assigned endpoints; the other seven endpoints have no ready marker. Only 26.2's one-sided ready record and cited artifact/source hashes were independently checked.
- Runtime validation, tests, builds, game/Gym/server/Docker launches: not performed.
- Reviewer worktree: Git access required narrowly scoped escalation because of sandbox path access; the managed checkout, branch and commits are working. No checkout was recreated.


## Additional 26.2 B-side inventory seeds

Read-only inspection of the published 26.2 source confirms that the seven diagnostic anchors leave substantial reachable paths to inventory before comparison. These are navigation seeds, not paired findings or closure:

- `net/minecraft/client/player/LocalPlayer.java` (source SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`): `tick` lines 227–251 calls `super.tick`, then sends input and movement packets; `aiStep` lines 767–919 reads prior/current jump and shift input, performs crouch-fit checks, ticks input, applies auto-jump, calls four `moveTowardsClosestSpace` probes, toggles sprint/flight, dispatches player jump/fall-flight/riding-jump behavior, and then calls `super.aiStep`; `move` lines 986–994 calls `super.move`, then updates auto-jump and walked distance; its `updateAutoJump` dependency starts at line 1005 and is not represented by the current B-side slices.
- `net/minecraft/world/entity/player/Player.java`: `tick` begins at line 232; pose-fit checks appear at lines 355–374; `aiStep` begins at 442; player edge-backoff override begins at 880 (the base `Entity.maybeBackOffFromEdge` at lines 1097–1099 is identity); `travel` begins at 1402. These entry/override paths are not in the seven-item diagnostics list.
- `net/minecraft/world/entity/LivingEntity.java` (source SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`): `jumpFromGround` begins at 2389; diagnostics anchor selected travel helpers, but jump dispatch, conditions, stored velocity and call order still need a complete chain.
- `net/minecraft/world/entity/Entity.java` (source SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`): `collide` begins at 1141; step candidate collection/sorting at 1172–1193; collision collection and axis resolution at 1195–1260; synced pose updates call `refreshDimensions` at 3379–3409. `move` starts at line 711; shape, support, post-move velocity cancellation and callbacks must be traced through full callers/callees. These collision/pose methods are absent from the short diagnostic anchors.

The short diagnostics are not claimed to be comprehensive by the 26.1.2→26.2 owner, so these are required next inventory rows, not an owner misstatement of completed coverage. The source owner must still verify full method bodies and dependencies on both releases; the A source is not yet available. This side-only inventory does not establish 26.2 behavior equivalence or a historical delta.


## New 26.2 B-side closure findings

The current 26.1.2→26.2 report has seven inventory maps and 14 slice templates, all pending. I independently re-walked relevant B-side members against the validated 26.2 source manifest:

- `net/minecraft/world/entity/LivingEntity.java` SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`: `travel(Vec3)` lines 2430–2437 dispatches to fluid travel, fall-flying, or air travel; `shouldTravelInFluid` lines 2444–2446 gates that selection. The B-side report’s `S-TRAVEL-01` includes the dispatcher and air helper only. Required reachable branches are absent as bounded rows: fluid dispatch/helper lines 2504–2515, water travel 2516–2548, lava travel 2549–2580, and fall-flying 2581 onward. Add separate branch slices with caller/condition and dependencies. This is a confirmed coverage omission in the pending plan, not a pairwise behavior finding.
- `net/minecraft/client/player/LocalPlayer.java` SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`: the reachable override `move(MoverType,Vec3)` lines 986–994 calls `super.move`, then `updateAutoJump` and walked-distance updates. `updateAutoJump` starts at 1005. The current inventory covers Entity.move but omits this local-player post-move path. Add bounded slices for the override and auto-jump branches, preserving their ordering after collision resolution.
- Current `S-LOCAL-01` groups crouch/pose-fit, input ticking, auto-jump timing, four unstuck moves and sprint gating (one body range 767–829). `S-LOCAL-02` groups flight toggles, fall-flying and rideable-jump input (830–919). These contain several independently changing behaviors and should be split into bounded slices. `S-COLLISION-02` combines collision resolution, candidate collection and clipping, while the owner marks helper closure unread; split candidate/tie selection and axis/query helpers as source dependencies are inventoried.
- `S-STATE-01` cites placeholder ranges `Entity.setPose(Pose), lines 439-?` and `refreshDimensions(), lines 3393-?`. These are not acceptable final evidence ranges. Resolve exact bodies, callers/writers, and the dimension-resize collision-query timing; keep pending until then.
- `S-WORLD-01`, `S-MOD-01` and `S-EXT-01` are single broad umbrella rows with only seed categories/partial B-side notes. They need member- and resource-level decomposition for shape providers/registrations/neighbors, fluid properties/data, modifier/enchantment producers and consumers, and player-facing packet/correction callers. Their pending status is correct; they are not closed inventories.

## Scope boundary to correct

The 1.21.5→1.21.8 draft says it excludes food/hunger producer systems “including consequences on sprint eligibility” and its S1.3 says to exclude “any indirect sprint-gate finding.” That is broader than the campaign rule. Keep health/food/hunger producers out of emulation and do not turn producer-system differences into movement findings, but still inventory and compare direct player sprint-eligibility predicates and their reads/order as movement behavior. Reword this boundary before any closure; it is not evidence for or against a source difference.
Reviewer task branch is `feat/coverage-review-recent`, created from main `002137b227676caea77f6832b9f4c8d0b6200bff`; final audit checkpoint hash is listed in the handoff.
