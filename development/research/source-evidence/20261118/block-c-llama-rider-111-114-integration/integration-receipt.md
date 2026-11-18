# Early Llama rider-height integration receipt

## Product integration

- Product branch: `codex/c-llama-rider-early-range`
- Product base: clean accepted Llama ancestry at `84aa0f51c3ab71088d97120cd73be5eafadef17c`, including current-main ancestor `aae9c86464638cb5ed7faa535c92569b89484906`.
- Integrated code commit: `7368d1087197aa703fc9fa5184bb35ced1870541`
- Compiled Git tree: `ce5cba1b5965b093e36247179f982a81ded054f1`
- Existing operation: `PlayerPassengerPlacementHook`, dispatched through `EntityPassengerPlacementMixin`; no additional hook or mixin.

The integration reuses the existing V1_17 Llama provider for selected groups V1_11 through V1_17, covering adult default Llama direct local-player Y placement with the exact source factor `0.67`. The empty native provider moves to V1_10_1, immediately before the accepted V1_11 lower bound. TraderLlama uses the historical provider only from V1_14, the first exact source era containing that entity; earlier selection returns to the native callback. Existing V1_17_1 and later providers remain unchanged. Other carriers, custom dimensions/scales, and rotation are outside this extension.

## Bound source evidence

- Exact 1.11–1.14 comparisons and independent verdicts: source commit `8d26079565b2cfd43d1bef4518ed12f2b76ae432`; manifest `block-c-llama-rider-111-114-exact-aliases/README.md`, SHA-256 `4bab257bfdd5129a7731664e3fe70b82c449646265e7d935578a4c1ec0a1acf7`.
- Exact 1.14–1.15 comparisons and independent verdicts: source commit `154d46f19cbeea4f27e6c7c35917f6069b5287f0`; manifest `block-c-llama-rider-114-exact-aliases/README.md`, SHA-256 `89cc881d1dc8b5e993e887e00c1f43cd42106b258b4116552b4fafc2b0ebd136`.
- Existing exact 1.15–1.17.1 alias archive: source commit `742eaf6020f1f4659e5e366782227dfc611b53a2`; manifest SHA-256 `767961e6a6e8c3a98a918934d381c04112c23a80aefa39732a38e0554c8a27d9`.

The V1_17 Change comment cites exact Feather `LlamaEntity.updateRiderPositon(Entity)`, Yarn `LlamaEntity.updatePassengerPosition(Entity)`, and Mojmap `Llama.positionRider(Entity)` paths and their height members. The source acceptance is limited to the direct ordinary adult/default Llama rider Y operation; it does not establish full release-pair coverage or runtime parity.

## Static review and build disposition

- Independent product review: `c_extended_llama_candidate_independent_review` signed PASS for exact code commit `7368d1087197aa703fc9fa5184bb35ced1870541` and tree `ce5cba1b5965b093e36247179f982a81ded054f1`; the reviewer verified the exact code tree, source bounds, resolver behavior and ancestry.
- Exact command: `gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`
- Result: exit code `0`, `BUILD SUCCESSFUL`; 19 actionable tasks, 3 executed and 16 up-to-date. `compileClientJava` ran. The init script disabled Gradle `Test` tasks and `-x test` was supplied.
- The build ran on the exact code tree `ce5cba1b5965b093e36247179f982a81ded054f1`.
- Compiled class SHA-256 values: V1_17 `LlamaRiderHeight.class` `f364e63f37aaebf029dc3ad467654868b66ae0f335f54e1d7e76056a7c1e3378`; V1_10_1 `NativeLlamaRiderHeight.class` `4bdde03c3fc3b67622f4bd521e2a025dcb0f46e943c9b2d68fbe31a5857a95a5`.
- Intended central finding association: `development/research/findings/1.17.1--1.18.2/llama-passenger-rider-height.md`.

Compilation does not establish movement parity. No tests or runtime launches were performed.
