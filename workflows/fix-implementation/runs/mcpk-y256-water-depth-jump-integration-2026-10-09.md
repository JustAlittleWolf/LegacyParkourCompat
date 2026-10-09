# MCPK Y=256 retained water-depth jump integration — 2026-10-09

## Accepted inputs

- Bounded source mechanisms: introduction memo `360b629cdb30450904c00625bee97bfd5390b4fa`, blob `3c40a634db190f60e1ced59e3e97eaa0db851b9b`, raw SHA-256 `7d4682f5dc560c528351107b9ae7151d14289b1c3c93c5e4c777d935596f6009`; protection memo `ec665da6da065d0099dec6ccb469ac523d4f771e`, blob `1bb2892719213cb625506e0c3dbe38eb956c2bbf`, raw SHA-256 `536c9dca9dd2b750232d6f729439545e9d296cdb94a9fa7169a346ffb2a0aaf7`. Their bounded independent source acceptance is recorded in `workflows/orchestration-2026-10-09/integration.md`.
- Final implementation candidate `8f8e381b67ce435c82b805dc6318d4c13e552bdc`, tree `2c1e89414527e622508ccdf148d0f6f6baa3a6a7`.
- Independent technical ACCEPT: commit `c76c205702d92dc03267787e71644d10eb752aff`; report `workflows/implementation-reviews/2026-10-09-y256-water-depth-jump-correction-independent-rereview.md`, blob `e67005bcb6916b93bf0337d8ad87b62b05afc9dc`, raw SHA-256 `03213e87ca0ff7aa9c16aecc5517439aa8513c5869d13ed743d1f4eed119e05`.
- Original independent REQUEST CHANGES: commit `4494e91504811ff0e9deb0e41a14fab3b4873610`, binding report blob `36ff5e851379cf659b96e717e11b0d6d2c85102d`, raw SHA-256 `90e753c229052b11b0e9a10adbe32296954d242736d87649a47358eb4ed71492`. The original report, rejected intermediate correction `6401f7939992941950157c23d75ee5b9d0339218`, and later accepted correction remain separate immutable history.

## Source behavior and implementation

- Exact 1.13.0 `LivingEntity.mobTick()` and 1.15.2 `LivingEntity.aiStep()` select the water jump when jump input is true and retained water depth exceeds `0.4`, including while grounded. Their ground-state condition redirects only the shallow/zero-depth branch. At the Y=256 loaded-area preflight, water state can clear while stored depth remains, and the later player jump branch can consume it.
- Exact 1.16.0 clears depth before scanning and gates liquid jump on current water state and positive depth. The reviewed implementation maps the active mechanism to existing V1_13, V1_14, V1_15 and V1_15_2 groups, V1_12 to a no-op baseline, and V1_16 to protection. The accepted report verifies player-only producer timing, jump-input gate, strict `> 0.4` threshold, vanilla water-jump operation, resolver selection, separate hook registration and native fallback.
- Integration commits: `db833568` cherry-picks the initial candidate; `90f8f805` retains the original rejected intermediate correction; `a7be5f77` applies the accepted grounded-state correction. Catalog-order integration commit `9eb93a3e3c12790bf3b693b1afe2058d52294e42` retains both independent V1_15_2 water-jump and sneak-edge collision-query registrations in the candidate's order. The initial cherry-pick conflict was limited to these independent registrations. After reconciliation, every source file under `src` matched candidate `8f8e381b` exactly (`git diff 8f8e381b..9eb93a3e -- src` empty).

## Integrated build

- Build input commit: `9eb93a3e3c12790bf3b693b1afe2058d52294e42`; tree: `22cd69f5c6accb7a69d3e7d5e7fa866a46346e48`.
- Command: `\.\gradlew.bat build -x test --init-script workflows/fix-implementation/runs/F005-fall-reset-no-tests-2026-10-09.init.gradle --no-daemon --console=plain`.
- Init-script SHA-256: `C2C10707E1A30E0D873442B94DA87EEE147E9DFA00FC89BCE880CBADB8A46941`. It disabled all `Test` task types across projects and logged `:core:test`, `:test`, `:parkourgym-server:test`, and `:testing:test` as disabled. `-x test` was also supplied. Result: `BUILD SUCCESSFUL`, 18 actionable tasks (3 executed, 15 up-to-date); no Test task ran.
- Artifact `build/libs/LegacyParkourCompat+26.2-1.0.0.jar`: 286557 bytes, SHA-256 `21F330CD236F19D67F7C1702DCDD6BC725A56D79A225ED7757BE1B0F49D88853`.
- The first wrapper attempt stopped before Gradle startup because the external Gradle cache lock was denied. The same command then completed with authorized cache access. This was an environment permission issue, not a project failure.

## Remaining scope

- This implementation does not complete its source pair; the pair remains partial. The first protected release inside `(1.15.2, 1.16.0]` is unknown. The 1.13.2 mapped-JAR identity mismatch remains, so no 1.13.2 bytecode or original-JAR equivalence claim is made.
- Compilation does not verify mixin application or movement parity. MCPK trajectory/runtime validation, target mixin application and the exact protection cutover remain unverified. No tests, game, client, TAS/Gym/server or Docker runtime was launched; no push was made.
