# Remaining-ref proof — pre-publication census — 2026-10-09

This is a read-only local-head census after the coordinator's three-entry pane retirement batch and the separate completed eye-state review retirement. It is not the post-retirement proof: the three temporary metadata refs remain present pending this report publication.

## Keeper set

- Final coordinator resume-plan snapshot: `unfinished-branch-resume-plan.json`, captured `2026-10-09T20:53:23Z`, blob `af1cceef759c5268e0566c81d4a8288571377abb`, published in merge commit `9e47e6589403b8f40fa25c6aa47a5bf5617ecbcc` (root commit `94e0a77e58df223991107e6716e359081d2c8f09`).
- The final plan contains 21 exact keeper branch/head pairs: 19 canonical partial source branches and two active pane branches. Its 1.12.2–1.13.2 entry is `b407a2d95884f751117857234d1b7f6edb24c0cf`, checkpoint-complete/resume-same-worker `/root/resume_source_1122_1132`; its 1.15.2–1.16.5 entry is `d0f0a863dffbd954851624da491ccad43d832a7d`, checkpoint-complete/resume-same-worker `/root/resume_source_1152_1165`. A read-only comparison against local refs and attached worktree HEADs found all 21 keeper names and object IDs matching, with zero keeper mismatches and zero unexpected heads.
- The remaining local heads are the plan's 21 keepers, `main`, and three temporary metadata refs. There are 25 local heads in total. The expected count is 22 after the three metadata refs are separately retired.

## Temporary metadata refs

| Ref | Exact head | Worktree state at capture |
|---|---|---|
| `fix/branch-retirement-audit-2026-10-09` | `e33afd65bb1db9b5002c1b8de23b5feed3dc3033` | Published audit worktree clean at this head. |
| `fix/source-ownership-final-audit-2026-10-09` | `0356e9085f1492797368b7f8ca1eaabc5321f89c` | Published audit worktree clean at this head. |
| `fix/campaign-coordinator-2026-10-09` | `9e47e6589403b8f40fa25c6aa47a5bf5617ecbcc` | Coordinator worktree clean at the merged root publication tip. |

Expected archive object IDs after the coordinator's later metadata retirement are `9e47e6589403b8f40fa25c6aa47a5bf5617ecbcc` for the coordinator root, `e33afd65bb1db9b5002c1b8de23b5feed3dc3033` for the non-source auditor, and `0356e9085f1492797368b7f8ca1eaabc5321f89c` for the source auditor. The coordinator will provide the actual archive refs and post-retirement 22-head verification separately.

`main` was `4f33e57d347c8e5101dd9dfe22531ea47af9c4de` before this documentation publication. The publication advances only `main`; this report does not modify any other local branch ref.

## Archive checks

- All 39 entries in `exhaustive-batch-1-results.json` and all three entries in `exhaustive-batch-3-results.json` match their named `refs/archive/exhaustive-closure-2026-10-09/<branch>` object IDs. Every result records `retired-files-preserved`.
- The two separately retired completed refs are archived at `feat/source-discovery-movement-source-1-21-4-1-21-5-resume` -> `1d027c706dd22fd6df2fe14deaf3a02695d6094e` and `feat/movement-discovery-1-11` -> `766ec5006a227d95983bfa91a9513a3ed2681baf`.
- The eye-state review ref is absent from local heads and archived at `fix/review-eye-state-fluid-2026-10-09` -> `86a47f1784d57c6374a8ca28c401e29a4b9a4bec`. Its bounded review is complete; the canonical 1.21.11–26.1.2 source assignment remains partial and retained.
- Together with the 57 earlier recorded retirements, these records reconcile to 102 completed retired refs. The rejected dormant-source proposal was not executed.

## Limits and next state

The coordinator's older `queue.json` blob `9835dcb56e2534e3eaf1d3cf17693307f918d3d1` is historical and contains stale branch pointers. Use the final captured resume plan for this keeper snapshot; do not interpret queued work as active unless its owner state says so. The source ownership and non-source audit reports are metadata snapshots, not source acceptance or movement evidence.

`git worktree list --porcelain` reported 150 registered worktrees at capture. This report does not retire the three temporary metadata refs or claim the post-retirement count. After their authorized retirement, rerun the same read-only head/archive checks and record 22 local heads: `main` plus the 21 keepers. Preserve all source/pane refs, worktrees, files and caches.
