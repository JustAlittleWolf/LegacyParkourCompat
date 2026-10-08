# Main netcode and Elytra review evidence verification — 2026-10-08

## Scope and checkout

This bounded audit used the isolated managed worktree `C:/Users/Wolfi/.codex/worktrees/movement-ops-verification/LegacyParkourCompat` on branch `ops/verify-elytra-review-evidence-2026-10-08`. The audited `main` tip is `1ea23480755a2574abd5b5855827ded5d5f50702`; the comparison base is `457b350fa5490b90b08de4f97033718c77efec1d`. The primary `D:` checkout was not switched or edited. No source files, build, tests, runtime, push, or chat messages were made by this audit.

## Current main source delta

`git diff 457b350..1ea234 -- src` contains 19 files, 357 insertions, and 27 deletions. The net code groups are the accepted TICK-01 sprint-timer phase change, corrected direct passenger-yaw refresh, 1.18.2 rising sneak-edge restraint, 1.14 Elytra start request, and 1.13 swimming-pitch lookup. The integrated Elytra gate is in `LocalPlayer.aiStep`; swimming is a separate `Player.travel` hook. The changed source paths match those groups and their providers/mixins. The held pose-fit and WORLD-03 code is not part of this net source delta.

Elytra implementation commit `361c15c687a16bbbaaef2525d27a4d2277b33c3a` is an ancestor of current main. Comparing that commit to current main across `ElytraJumpStart`, `FallFlyingStartBehavior`, its provider, and `EntityInvoker` shows no changes. `LocalPlayerMixin` has only the separate TICK-01 injection-location change relative to the reviewed Elytra code; the Elytra injections are retained. Thus the accepted code tip and source bindings reviewed at `b8dfbc3e4d092141861759cab36272ea223a4d17` still describe the Elytra implementation on main.

## Elytra snapshot identity reconciliation

The static implementation review at `b8dfbc3e4d092141861759cab36272ea223a4d17` names source snapshot commit `4a0c35f2008d785867c00360a7b72726e3506435` and path `workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-ELYTRA-START.md`. That immutable Git blob is `066856290714a826dffb44072609114389cf9d8c`; hashing its 3,868 raw blob bytes gives SHA-256 `b6d5091e95e7cc0d35eb696dea6d06f720aaad91540ad88915ddc61dad576e59`.

The implementation-review report instead gives `b6d5091e95e7cc0d35bed323f73bd4eaf221379c` in its `content SHA` field. That is a 40-hex-character typo and does not match the raw SHA-256 or the Git blob ID. The later review commit's diff only removed the `-256` portion of the label; it did not correct the value. The review report itself is identified by SHA-256 `65d047fb306bdbc7a24c6aa14f91e1a83d4f15c0d9695b8fc8a579ec705e455c` at the review tip.

The accepted blind finding review `960ce5644ceacd8e27bd3cb9145bd4eaf221379c` records the same snapshot commit and path with the correct 64-character SHA-256 and Git blob ID above, and accepts that finding. Therefore the static implementation review and the integrated code bind to the same actual 1.14.4–1.15.2 finding content and version. The mismatch is review-report digest metadata, not evidence of a different source file, changed finding, or version mix-up.

**Disposition and follow-up:** The review owner corrected the provenance field in commit `937a50203344bfc48c4c21b5551c3732a5503bdb` on `fix/review-elytra-start-2026-10-08`. Its parent is the audited main tip `1ea23480755a2574abd5b5855827ded5d5f50702`; the original review tip is its ancestor. The corrected report now lists the exact SHA-256 and Git blob above. The updated report blob is `2fcbfbf96f73967dc9a6cc338a3f93e6d6281919` (6,804 raw bytes), SHA-256 `ee9c731188e73b5cba3c4593e2161b2b74681b713077272199708ea06348f38d`. The correction changes review/integration documentation only; `git diff 937a502^ 937a502 -- src` is empty, and the ACCEPT verdict is unchanged. This closes the evidence correction; no new implementation re-review is required.

## Existing test-disabled build evidence

The integration worktree at `C:/Users/Wolfi/.codex/worktrees/movement-campaign-integration/LegacyParkourCompat` is at current main. Its `build/no-tests.init.gradle` disables every Gradle `Test` task type with `configureEach` both in `gradle.beforeProject` and across all projects in `gradle.projectsEvaluated`. The recorded command is `gradlew.bat build -x test --init-script build/no-tests.init.gradle`.

The captured integration command output for current main reports `BUILD SUCCESSFUL`, 18 actionable tasks (3 executed, 15 up-to-date), and no test task execution. The final JAR at `build/libs/LegacyParkourCompat+26.2-1.0.0.jar` was independently rehashed from the integration worktree as `C717F4F74B51D5F41A7D6B1A1034683377C6744478B41AC863E30DF9602ADA08`. No standalone Gradle console `.log` file was present under `build`; the command output is retained in the integration task execution history and summarized in its checked-in integration review. The build is compile/package evidence only; runtime behavior and Mixin application remain unverified.