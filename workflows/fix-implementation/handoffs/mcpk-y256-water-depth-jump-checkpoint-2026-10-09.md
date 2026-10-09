# MCPK Y=256 water-depth jump implementation checkpoint

State: resumed; implementation is wired but uncommitted, pending independent technical review and current-main merge. No build, test, runtime, decompilation or push was performed. No process remains running.

## Checkout

- Branch: `fix/water-depth-jump-initiation-2026-10-09`
- Worktree: `.task-worktrees/water-depth-jump-initiation-2026-10-09`
- HEAD: `a2af029ee9d051f5b44ceaf3bf1d6087690935d2` (`docs: record F3 integration build`)
- Git status and diff are readable with per-command LFS filter overrides. Git is configured with required `filter.lfs.process=git-lfs filter-process`; launching it fails before LFS code with the Git for Windows MSYS `NtCreateDirectoryObject ... 0xC0000022` error. Direct `git-lfs.exe` is present. This is an environment/launcher failure, not a project-code failure.
- An unrelated modified `parkourgym-server/worlds/physics_test.polar` is present in this worktree. It was not edited or staged and must remain untouched/out of the candidate.

## Immutable accepted inputs

- Introduction memo: `360b629cdb30450904c00625bee97bfd5390b4fa:workflows/source-campaign-2026-10-07/source-memos/y256-fluid-depth-1.12.2-1.13.2-introduction-2026-10-09.md`; accepted in `30e3caac80bb776403f034ee8884666865c696d2` at `workflows/wiki-audit-2026-10-07/reviews/mcpk-y256-introduction-source-review-2026-10-09.md`.
- Protection memo: `ec665da6da065d0099dec6ccb469ac523d4f771e:workflows/source-campaign-2026-10-07/source-memos/y256-fluid-depth-1.15-1.16-cutover-2026-10-09.md`; accepted in the same review commit at `workflows/wiki-audit-2026-10-07/reviews/mcpk-y256-protection-source-review-2026-10-09.md`.
- Original bounded mechanism/review: `681d1d54d69ec693f02e07e5a6b1841c401c4bb6` and `2e47dad5165119de97b940e17eebf0bcad21b3c4`.
- Endpoint review: `9da67abd41f2f1f53c886e5c3519ed44f3e2e2f7:workflows/wiki-audit-2026-10-07/mcpk-y256-boundaries-review-2026-10-09.md`.
- Limits retained: exact first checked introduction is 1.13.0, route is source-confirmed through 1.13.2; exact 1.15.2 is affected and 1.16.0 protected, but first protected release inside the interval is unknown. The 1.13.2 mapped-JAR identity mismatch remains; no bytecode claim. Runtime trajectory remains unverified.

## Partial edits applied

- Added `StaleWaterDepthJumpBehavior`, `WaterJumpDepthAccess`, shared gate helper, active version wrappers for V1_13/V1_14/V1_15/V1_15_2, V1_16 protection, and a V1_12 no-op baseline. Catalog registrations cover V1_12 through V1_16, preserving closest-later resolver semantics.
- `EntityMixin` caches the player's water depth after the fluid refresh while leaving it unchanged when the contracted-box `minY` floors to `>= 256`. `LivingEntityMixin` dispatches the retained-depth water jump before the existing fluid-jump gate, suppresses the competing ground jump, and restores the delay that branch can set.
- The implementation record is at `workflows/fix-implementation/handoffs/y256-water-depth-jump-initiation-2026-10-09.md`. Static `git diff --check` passed. Build target is 26.2; exact 26.2 cached source is absent, so current-target compile/descriptor verification remains unperformed. Accepted source/JAR provenance caveats remain explicit.
- Current local `main` observed during resume: `b742f9cb1b90dcd09c94d752800cc39af93d393f`; it was still advancing and has not yet been merged.

## Resume action

Next: complete static inspection of the wired code and implementation record, stage only the candidate/report/checkpoint paths, commit a coherent candidate, merge the then-current local `main` and inspect incoming commits for semantic conflicts, then obtain independent technical review of the merged candidate. Do not stage the `.polar` file. Do not run tests, builds, game/TAS/Gym/server/Docker, or push.
