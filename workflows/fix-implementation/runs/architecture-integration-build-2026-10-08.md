# Architecture integration batch — initial build result — 2026-10-08

## Accepted changes on the integration branch

The isolated branch `fix/architecture-integration-2026-10-08` contains the accepted suffocation-probe refactor, SneakEdgeBackoff HEAD dispatch change, corrected static mechanic catalog, and accepted bed/water documentation records. The author, REQUEST-CHANGES, correction, and ACCEPT histories are preserved as merge commits. The candidate and accepted source/code reviews remain separate statuses.

## Initial exclusive build

- Command: `gradlew.bat build --no-daemon -x test --init-script D:\Javastuff\LegacyParkourCompat\.task-worktrees\architecture-integration-2026-10-08\build\no-tests.init.gradle --console=plain`.
- The init script disabled every Gradle `Test` task type; `-x test` was supplied. The retained log contains zero executed Test-task entries.
- Result: **BUILD FAILED** at `:compileJava`, after 11 actionable tasks executed. The catalog cannot instantiate `me.wolfii.legacyparkourcompat.change.v26_1.BlockLanding` because both its class and constructor are package-private. This is an accessibility gap in the catalog migration, not a source-review finding or movement-semantic merge conflict.
- Complete stdout/stderr: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\integration-artifacts\architecture-2026-10-08\build.full.log`, SHA-256 `36d1fb4d9fdf1312a4c8d90b47e7a4e1d858fdbc6e3e0a5785b7de2051171218`.
- No packaged result JAR was created. Pre-batch packaged/source JAR copies remain preserved in `integration-artifacts/architecture-2026-10-08/prior/`; their hashes are recorded in `prior-artifacts.json`.

## Separate review candidate

The two-modifier correction is committed separately on `fix/architecture-blocklanding-visibility-candidate-2026-10-08` at `1310bfbb` (`fix: expose V26.1 landing catalog implementation`), based on integration tip `ae112de6`. It makes only `BlockLanding` and its constructor public. The movement body, per-bed/slime registration behavior, and catalog order are unchanged. Candidate rationale is recorded in `workflows/fix-implementation/candidates/2026-10-08-v26-1-blocklanding-catalog-visibility.md`.

The candidate is unreviewed and remains outside the integration branch and local `main`. No build or tests were run after the candidate change. A separate accepted correction review is required before integrating it or running a repair build. The initial failed build remains a distinct record and its log is retained.

## Main and runtime status

Primary local `main` remains clean at `d8f3956602da94bf0cf67753cc0a9f4665397729`, with the prior successful artifact unchanged. No tests, clients, servers, TAS, Gym, Docker, runtime checks, or pushes were performed.
