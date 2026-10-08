# Movement campaign integration queue — 2026-10-08

Integration branch: `feat/movement-campaign-integration-2026-10-08`.
Baseline: `457b350fa5490b90b08de4f97033718c77efec1d` (`feat/movement-completeness-final`; final 2026-10-07 checkpoint). This queue records incremental handoffs from that baseline. Existing code coverage, new implementation, static review, integration/build, and runtime validation are separate statuses.

| Finding/work item | Source evidence / disposition | Baseline or incoming code | Static review | Integration / build | Runtime |
|---|---|---|---|---|---|
| 1.15 sprint reset + water descent | Owner verified both are already present in baseline; bytecode ordinal and `isAffectedByFluids` call/profile fallback traced. No new commit needed. | Existing coverage at baseline; no duplicate patch. | Static binding verified by owner; not runtime proof. | No integration needed; new campaign build pending. | Not performed. |
| F041, 1.19 fall-flying sprint gate | Owner verified existing baseline coverage in `v1_19SprintFallFlyingGate` / `SprintStart`, `v1_19_4SprintStart`, and `LocalPlayerMixin.canStartSprinting`. | Existing coverage at baseline; not a new fix. | Static binding verified by owner. | No integration needed; new campaign build pending. | Not performed. |
| TICK01 sprint duration | Source snapshot `1dbafbb`; blind decision `304b6a5` accepts the timer delta. | Timeout-ordering patch `3c53118`, integrated as `3699009`. | ACCEPT; corrected static review `bdb6443`. | Test-disabled build succeeded; JAR hash is recorded below. | Not performed. |
| F002 fixed-profile pose selection / collision-fit | Accepted source snapshot `448934e826fb41266dc79a313ef1187899d76382`; blind acceptance `10aa24e1114200397ed2d042a33f269113faf179`. | Patch reviewed and held for float dimension comparison correction. | REQUEST CHANGES; corrected review `bdb6443`. | Not integrated; no new build. | Not performed. |
| 1.14 Soul Sand / friction sample / Elytra start | Accepted source snapshot `4a0c35f`; Elytra blind review `960ce564`. | Soul Sand/friction implementation review pending; Elytra patch held. | Elytra REQUEST CHANGES; corrected review `bdb6443`. | Elytra not integrated; no new build. | Not performed. |
| Passenger yaw | Patch reviewed and held for direct passenger scope correction. | Not integrated. | REQUEST CHANGES; corrected review `bdb6443`. | No new build. | Not performed. |

The baseline contains yesterday's integrated fixes and its successful test-disabled build. Later TICK-01 integration and build status is recorded below. When a patch is accepted, record its exact code and review commits, integrate it, then use the exclusive compile slot with every Gradle Test task explicitly disabled and `-x test`. No tests, client/TAS, Gym/server, Docker, or runtime simulations are authorized. This queue does not close any source pair or claim runtime parity. Cross-version switching remains outside the correctness guarantee.

## Integration review update — 2026-10-08

Initial exact-patch review is recorded at `fddb263`; corrected evidence and scope are recorded at `bdb6443` and supersede conflicting review statements.

- TICK-01 is accepted and integrated as `3699009`; its test-disabled build succeeded as recorded below.
- Passenger yaw and 1.13 pose fit are held for the requested corrections in `integration-review-2026-10-08.md`.
- Elytra comparator commit `a5d07341d941ce2c0b9df1e392af9a40b1fbb9e9` was verified, but the full patch is held because the newer callsite/helper gates remain. The correction and original code are preserved in branch history and reverted from the active diff.
- No build runs until accepted review scope is recorded. Runtime validation remains unperformed.

## Build result — 2026-10-08

- TICK-01-only batch: `BUILD SUCCESSFUL` with `gradlew.bat build -x test --init-script build/no-tests.init.gradle`; every Gradle `Test` task type was disabled and no tests ran.
- Packaged JAR SHA-256: `1C3198B1E05AC5FD4CE4D84683060576CA34ED636CA81E88728FF9F6A07BCBD1`.
- Runtime parity remains unverified. Passenger yaw, pose fit, and Elytra remain held for correction and renewed review.
