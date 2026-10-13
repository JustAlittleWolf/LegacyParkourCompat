# Remaining-ref proof — 2026-10-09

The pre-publication census below is retained as historical context. The post-retirement verification at the end supersedes its pending status.

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

## Limits recorded at pre-publication

The coordinator's older `queue.json` blob `9835dcb56e2534e3eaf1d3cf17693307f918d3d1` is historical and contains stale branch pointers. Use the final captured resume plan for this keeper snapshot; do not interpret queued work as active unless its owner state says so. The source ownership and non-source audit reports are metadata snapshots, not source acceptance or movement evidence.

`git worktree list --porcelain` reported 150 registered worktrees at capture. At that point, the three metadata refs remained pending.

## Post-retirement verification — complete

- Imported the exact coordinator result file `metadata-retirement-final-results.json`, blob `34592f2c295b0a9badfe60be0ac9e848f79c58d7`, raw SHA-256 `CDDDCC71F5EAB82358BB16A2ED5983728509331B8994155ECFBA563DE2B58FB9`. Its three entries bind `fix/branch-retirement-audit-2026-10-09` -> `e33afd65bb1db9b5002c1b8de23b5feed3dc3033`, `fix/source-ownership-final-audit-2026-10-09` -> `0356e9085f1492797368b7f8ca1eaabc5321f89c`, and `fix/campaign-coordinator-2026-10-09` -> `9e47e6589403b8f40fa25c6aa47a5bf5617ecbcc` to their exact `refs/archive/exhaustive-closure-2026-10-09/<branch>` destinations. Each result records `retired-files-preserved`.
- Verified each archived ref resolves to the reported head. Each associated worktree is detached, clean, and at the same original head; the worktree paths remain present. No remote refs changed.
- The current local branch set is exactly 22 heads: `main` plus the 21 keeper branches (19 unfinished canonical source refs and two active pane refs). All 21 keeper heads match the final coordinator plan and worktree HEADs, with zero unexpected heads and no temporary metadata refs remaining.
- The archive count is 52 earlier cleanup refs + five task-closure refs + 48 exhaustive-closure refs = 105 retired refs. The pane-review checkpoint `3d14abed0aba18c06eaa39209069948877fd925b` is contained by `refs/archive/task-closure-2026-10-09/fix/pane-reviews-25-32-2026-10-09`. The 150 registered worktrees remain present.
- After the resume plan was published, the 1.13.2–1.14.4 keeper was assigned to same-base worker `/root/resume_source_1132_1144` at `c7fda155df85b581df2adb18d7e738ca421ac603`, with a first unresolved dependency capped at three method pairs. The ref and worktree HEAD match and the worktree is clean. This live owner update supplements the immutable captured plan; its queued phase was not edited in that snapshot.
- The main publication at `aee27d3f` was clean before this documentation update. This update changes documentation only; the resulting main worktree is verified clean after commit.

## Subsequent local history pointer cleanup — 2026-10-09

- The post-retirement 105-ref count above is a historical pre-cleanup snapshot. The exact later manifest is `local-history-ref-cleanup.json`, blob `88618925bdeab8540ffebcb0ce60ac856f4f0a12`, raw SHA-256 `C4106D470BF21D6692358AA9616B8BB7C5468E763D86043211436A7895045693`. It records 138 local pointer removals: 100 completed archive pointers and 38 remote-tracking caches.
- Bundle `D:/Javastuff/LegacyParkourCompat/.task-worktrees/local-history-backup-2026-10-09/retired-local-refs.bundle` has SHA-256 `646164E8C14DDF28ED373299EAAA70A8B30358F546DE7E7EEB0420EDFE2B234B`. `git bundle verify` confirms complete history, and exact manifest-to-bundle comparison found 138 entries on each side with zero missing, different or extra heads.
- The 22 live heads and six unfinished canonical checkpoint archive refs remain unchanged. The two unmerged cached remote tips `refs/remotes/origin/cursor/client-movement-authority-2d96` and `refs/remotes/origin/cursor/physics-test-server` remain pending historical reconciliation; their history is bundle-backed and still exists on the original remote. No remote repository or worktree/file/GC changes occurred.
- The orchestration workflow now gives the selected-tip recovery procedure. This record supersedes the earlier local archive-pointer count while preserving that earlier verification as a dated snapshot.
