# Architecture integration batch — initial build result — 2026-10-08

## Accepted changes on the integration branch

The isolated branch `fix/architecture-integration-2026-10-08` contains the accepted suffocation-probe refactor, SneakEdgeBackoff HEAD dispatch change, corrected static mechanic catalog, and accepted bed/water documentation records. The author, REQUEST-CHANGES, correction, and ACCEPT histories are preserved as merge commits. The candidate and accepted source/code reviews remain separate statuses.

## Initial exclusive build

- Command: `gradlew.bat build --no-daemon -x test --init-script D:\Javastuff\LegacyParkourCompat\.task-worktrees\architecture-integration-2026-10-08\build\no-tests.init.gradle --console=plain`.
- The init script disabled every Gradle `Test` task type; `-x test` was supplied. The retained log contains zero executed Test-task entries.
- Result: **BUILD FAILED** at `:compileJava`, after 11 actionable tasks executed. The catalog cannot instantiate `me.wolfii.legacyparkourcompat.change.v26_1.BlockLanding` because both its class and constructor are package-private. This is an accessibility gap in the catalog migration, not a source-review finding or movement-semantic merge conflict.
- Complete stdout/stderr: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\integration-artifacts\architecture-2026-10-08\build.full.log`, SHA-256 `36d1fb4d9fdf1312a4c8d90b47e7a4e1d858fdbc6e3e0a5785b7de2051171218`.
- No packaged result JAR was created. Pre-batch packaged/source JAR copies remain preserved in `integration-artifacts/architecture-2026-10-08/prior/`; their hashes are recorded in `prior-artifacts.json`.

## Accepted visibility repair

The two-modifier correction is committed separately on `fix/architecture-blocklanding-visibility-candidate-2026-10-08` at `1310bfbb` (`fix: expose V26.1 landing catalog implementation`), based on integration tip `ae112de6`. It makes only `BlockLanding` and its constructor public. The movement body, per-bed/slime registration behavior, and catalog order are unchanged. Candidate rationale is recorded in `workflows/fix-implementation/candidates/2026-10-08-v26-1-blocklanding-catalog-visibility.md`. Independent ACCEPT review `aa756668` confirmed the narrow visibility-only change and verified catalog constructor accessibility.

The candidate and ACCEPT review were merged into the integration branch as `e5becd04` and `b5df9614`.

## Separate repair build

- The repair build used the same no-tests init script and exclusive decompile lock as the initial run. The initial failure log remains unchanged.
- Result: **BUILD SUCCESSFUL in 19s**, with 18 actionable tasks (8 executed, 10 up-to-date). The retained repair log contains zero Gradle Test-task entries; no tests were run.
- Complete stdout/stderr: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\integration-artifacts\architecture-2026-10-08\repair-build.full.log`, SHA-256 `ef0980eb181be9ea83a423a032f11f72045317ff369f9b81f47ddb795725511f`.
- Packaged JAR: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\integration-artifacts\architecture-2026-10-08\repair-result\LegacyParkourCompat+26.2-1.0.0.jar`, 267,837 bytes, SHA-256 `da749785311afe8eafb772f61d463b4a10accc30ed97865ee70146eeba3da372`.
- Sources JAR: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\integration-artifacts\architecture-2026-10-08\repair-result\LegacyParkourCompat+26.2-1.0.0-sources.jar`, SHA-256 `7a86508e3ff983a4d64cb89c90ebc610603122ce587caf2a10f202e2736497c9`.
- The prior artifacts remain preserved and unchanged in `integration-artifacts/architecture-2026-10-08/prior/`; the repaired outputs are in a separate `repair-result/` directory.

## Main and runtime status

Primary local `main` was clean at `d8f3956602da94bf0cf67753cc0a9f4665397729` before integration. After the successful repair build, the integration branch is ready for local merge. No tests, clients, servers, TAS, Gym, Docker, runtime checks, or pushes were performed.
