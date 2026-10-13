# Retrospective: lost historical movement behavior

Date: 2026-10-07
Repository baseline: `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`
Scope: explain the omitted 1.8.9 behavior reported after selecting the 1.8 profile. This is a provenance and process retrospective, not new Minecraft movement research.

## Finding

The three reported symptoms line up with known code removed by the 2026-10-01 reset and not restored by the fresh 1.8.9 → 1.9.4 integration:

- Jump apex: the old `change/v1_8/NegligibleSpeed.java` was deleted. The fresh catalog’s narrow ordinary `LivingEntity.moveRelative()` comparison does not cover the earlier velocity cutoff elsewhere in the tick.
- Glass panes: the old `change/v1_8/PaneCollision.java` was deleted. The original comparison left collision and block-shape coverage open.
- Sneak dimensions: the old `change/v1_8/StandingPose.java` was deleted. The later dimensions change only addresses swimming; the fresh 1.8 findings have no crouching-pose/dimension finding.

These are code/history findings, not a new runtime reproduction. The user’s report in the 2026-10-07 “Find why movement patches are unap…” chat (ID `01a116a7-73f3-7562-8aba-0de2936e4c19`) is the observed behavior. No game or TAS run was performed for this retrospective.

## Evidence chain

1. **The first 1.8.9 → 1.9.4 discovery remained partial.**
   Chat “Movement discovery: 1.8.9 → 1.9.4” (`01a0de00-ae4b-7b40-9312-1dc3e25b153f`, created 2026-09-26 15:56:21 CEST) committed its later checkpoint as `50461549f0419987b357daa07bed71dde1b8d22b` at 17:01:31 CEST. Its run record, `workflows/movement-discovery/runs/1.8.9--1.9.4/run.md`, still had four pending coverage rows, including entity movement/collision and block/fluid shape providers. It explicitly said stages 2–7 and other stage-1 dependencies remained open. It separately marked `LivingEntity.moveRelative()` ordinary ground/air acceleration, climbing, gravity, and drag as unchanged *within that bounded branch*. That scoped equality did not establish the entire tick’s jump/movement result or check the pre-travel negligible-speed cutoff. The pose/dimensions path was not a terminal coverage row either.

   The discovery coordinator, “Launch movement discovery runs” (`01a0ddbd-ee97-7470-8d29-4bac4c63666a`, created 2026-09-26 14:43:27 CEST), tracked partial work and later requested more review. The record was useful and explicit about its gaps; the failure was that those gaps did not remain a blocking obligation through the later rebuild.

2. **The rebuild removed a much larger set than the partial catalog represented.**
   “Orchestrate major version discovery” (`01a0f62f-6519-74a0-b5f8-9a7d247eb6fd`, created 2026-10-01 09:50:55 CEST) records the scope shift: implement the existing deltas, then more could come later. In the same campaign the user requested a clean rebuild. Commit `ebe56a21d17d120f11f8ede5b6a3d49bea7c7e41`, “chore: reset historical movement baseline” (2026-10-01 10:00:26 CEST), deleted 116 files and 4,511 lines, including `NegligibleSpeed`, `PaneCollision`, and `StandingPose`, along with the old movement mixins and other versioned changes.

   Resetting old code to avoid implementation bias did not require dropping its behavior from the acceptance scope. The orchestration mistake was to keep the narrow “rebuild cataloged findings” assignment after the reset broadened the removal to all historical implementations. No mandatory inventory compared the removed behavior set against the fresh source-only findings before declaring the rebuild complete.

3. **The fresh 1.8 implementation completed its assigned catalog, not the open comparison.**
   The fresh 1.8.9 → 1.9.4 integration entered the campaign at merge `3476c5c8c37e5d1c31f588f6106f4cde574216c8` (2026-10-01 12:57:14 CEST). The implementation record, `workflows/fix-implementation/runs/1.8.9--1.9.4.md`, lists seven implemented findings, MD-01 through MD-07: sprint timeout, creative-flight sneak input, chunk lookup, boat input, rideable jump charge, flight fall-distance reset, and natural regeneration. It does not claim pane shapes or standing crouch dimensions were implemented.

   Final integration commit `5205356d3558b0c1f965cccfc61241d84c1f60c9` (2026-10-01 13:38:41 CEST) reports a 75-registration inventory and a successful build. It also explicitly says runtime Mixin application and tick-level parity were unverified and discovery slices remained pending. Thus “all worker findings integrated,” resolver selection, registration counts, and compilation were each true but did not prove behavior coverage or execution.

## Why the existing checks missed it

- The source comparison’s scoped “unchanged” result was allowed to stand beside an unfinished full-tick ledger. A method-level no-difference result does not cover callers, earlier gates, collision shapes, or pose state.
- The clean reset had no machine-readable “removed mechanics” ledger that the replacement findings had to disposition as restored, superseded, intentionally excluded, or still open.
- The fresh implementation handoff was finding-oriented. Its success condition was the seven known findings plus a compiling integration; it did not require closing the earlier discovery’s pending slices.
- Static registration checks proved that registered hooks resolve. They did not compare registrations with a complete source-coverage inventory. A successful build established compilation, not correct Mixin application or movement parity.
- Runtime/TAS validation was explicitly not performed. This is why the user’s physical observation, rather than an automated parity signal, exposed the omissions.

The event is therefore not evidence that the selected 1.8 profile failed to resolve. It is evidence that a selected profile can be internally consistent while its historical coverage is incomplete.

## Remediation mapped to the hardened workflow

The current hardened discovery contract is at docs tip `2422192b518ff8fb15324691221c33da38e984e3` in `C:/Users/Wolfi/.codex/worktrees/3e61/LegacyParkourCompat` (not part of this task’s `main` baseline). The following gates address the specific failure:

| Failure in this history | Required control in the hardened workflow |
|---|---|
| Travel branch was treated as a broad proxy for movement | Inventory the complete reachable per-tick call graph: pre-travel decisions/state writes, travel branches, post-travel work, velocity cutoffs, collision, pose/dimensions, providers, and producer/consumer dependencies. Each ledger row names paired method ranges and one bounded behavior. |
| Pending collision/shape/pose work did not block later completion | Pending, in-progress, or blocked rows prevent a complete discovery handoff. Requeue partial work; require exact remaining slices and a resume checkpoint. A checker may validate structure/status, but an independent source reviewer must validate coverage. |
| Reset discarded known implementations with no gap reconciliation | Freeze blind source findings first, then separately reconcile every old implementation against the findings as restored, superseded, excluded, or open. Never reset historical implementations merely because a prior catalog is incomplete. Implementation status cannot close discovery coverage. |
| Worker completion/build/registration was mistaken for overall parity | Keep source discovery, implementation coverage, and runtime validation as separate statuses. Require exact code/provider evidence per finding; retain runtime parity as unverified until the later validation workflow supplies evidence. |

A static completion-checker bug was still being fixed when this retrospective was requested. Treat that checker as a mechanical structure gate, not proof of source completeness; do not accept completion until the checker fix and independent audit are both reviewed.

## Limits

- The records establish why these three omitted mechanics were not restored by this campaign. They do not establish that the remaining historical behavior is complete; the integrated report itself keeps profiles partial.
- This retrospective did not re-run Minecraft/TAS, re-decompile sources, or use either wiki as movement evidence. Old wiki references in other chats are not used here.
- It cannot establish why the user-visible collision behavior occurred for any other pane-adjacent path, or whether any mixin bypass contributes beyond the missing historical implementation.
- The deleted implementation source and current integration record establish the code gap. Exact parity, including the size of the observed jump-apex difference, remains unmeasured.
