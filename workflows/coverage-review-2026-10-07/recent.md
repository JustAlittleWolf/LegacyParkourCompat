# Recent movement source audit

- Status: preliminary; assigned owners are still active. No pair is accepted complete.
- Assigned pairs: 1.21.1–1.21.3, 1.21.3–1.21.4, 1.21.4–1.21.5, 1.21.5–1.21.8, 1.21.8–1.21.10, 1.21.10–1.21.11, 1.21.11–26.1.2, 26.1.2–26.2.
- Branch/worktree: requested managed worktree was created at `C:\Users\Wolfi\.codex\worktrees\coverage-review-recent\LegacyParkourCompat`; Git operations there failed with `fatal: this operation must be run in a work tree` for both `git status --short --branch` and `git switch -c feat/coverage-review-recent`. No replacement checkout was created.

## Independent disposition

All inspected drafts remain active and incomplete. Their planned/stage ledgers are not source coverage. Exact pair-specific readiness records, body-level evidence, full caller/callee closure, registrations/resources and semantic comparisons are still outstanding. No draft is accepted complete.

| Pair | Owner draft observed | Disposition |
|---|---|---|
| 1.21.1 → 1.21.3 | `run.md`, active; exact sources pending; seven coarse stage roots pending | Not accepted; no comparison evidence. |
| 1.21.3 → 1.21.4 | No run file in expected owner worktree at audit time | Missing report; must publish a partial/active source-only ledger and continue. |
| 1.21.4 → 1.21.5 | No run file in expected owner worktree at audit time | Missing report; must publish a partial/active source-only ledger and continue. |
| 1.21.5 → 1.21.8 | `run.md`, active; neither endpoint had a validated ready publication; seven provisional stages pending | Not accepted; no comparison evidence. |
| 1.21.8 → 1.21.10 | No run file in expected owner worktree at audit time | Missing report; must publish a partial/active source-only ledger and continue. |
| 1.21.10 → 1.21.11 | `run.md`, active; 50 planned slices, all pending; exact sources unavailable | Not accepted; no comparison evidence. The 50-row plan is a queue, not proof that every reachable movement method/provider was inventoried. |
| 1.21.11 → 26.1.2 | `run.md`, active; 50 planned slices, all pending; exact sources unavailable | Not accepted; no comparison evidence. The report explicitly says source comparison has not begun. |
| 26.1.2 → 26.2 | `run.md`, active; seven coarse stages pending; 26.1.2 source pending | Not accepted; one-sided 26.2 provenance cannot establish a difference or equality. |

## Verified source handoff

For 26.2, independently read `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated.ready.json`. It names exact release `26.2`, native `unobfuscated`, and the source/artifact/diagnostic manifests. Recomputed hashes match the three cited manifest hashes. The reported client jar hash and four cited source hashes match the listed source manifest entries; source files are present. The diagnostics contain anchors for `Entity.move`, `Entity.moveRelative`, `LivingEntity.travel`, `LivingEntity.jumpFromGround`, `Player.travel`, and `LocalPlayer.aiStep`.

This validates only 26.2 artifact identity and those reported hashes/anchors. The diagnostic list is not an inventory of every movement caller, writer, provider or data dependency. It does not establish intact bodies for every reachable method, exact input-to-collision call order, state-writer closure, block/fluid registration coverage, pose-resize timing, collision/step/support coverage, or any pairwise no-difference claim. The 26.1.2 side remains unavailable in the draft.

## Required requeue / acceptance gaps

- The three missing owner reports (1.21.3→1.21.4, 1.21.4→1.21.5, 1.21.8→1.21.10) need tracked source-only reports, regardless of source publication status.
- Every owner must keep the report partial/active until the exact aligned pair is ready and verified; source-queued stages cannot be closed as compared-no-difference or not-applicable.
- For each pair, independently verify exact IDs/namespace and cited ready/source/artifact/diagnostic hashes, then inspect complete relevant method bodies and decompiler diagnostics. Hash validity alone is not semantic evidence.
- Require an explicit per-tick chain through local input, tick/super-tick and travel dispatch, including velocity changes before travel and cancellation/restitution after `move`; compare jump dispatch/order and precise apex/threshold behavior; trace all pose/dimension writers and resize query timing.
- Require all collision, stepping, support/edge probes, axis selection, callbacks, shape/AABB and fluid paths. Enumerate shape/property/callback providers, registrations, historical block availability and neighbor dependencies; inspect movement-relevant jar resources/tags.
- Require direct movement effects/attributes/equipment paths and external state writers/packet consumers, with producer→consumer/dependency closure. Keep health, food/hunger, regen, saturation, exhaustion, damage and combat emulation excluded; keep modern-only blocks and independent non-player physics out of historical scope.
- Treat the reported 1.8 jump-apex cutoff and panes/bars/sneaking-box concerns as hypotheses for their exact source pairs, not evidence or a complete checklist. They are outside the assigned recent pairs except any independently demonstrated 1.21+ pose/collision delta.

## Current unresolved state

- Pairwise findings verified: none.
- Accepted pairs: none.
- Missing owner report folders observed: three.
- Exact source pairs validated by this reviewer: none. Only 26.2's one-sided ready record and cited artifact/source hashes were independently checked.
- Runtime validation, tests, builds, game/Gym/server/Docker launches: not performed.
- Reviewer worktree Git failure: `fatal: this operation must be run in a work tree`; branch creation and commits are blocked pending the repository worktree fix. Do not treat this preliminary note as a final audit or as owner completion.
