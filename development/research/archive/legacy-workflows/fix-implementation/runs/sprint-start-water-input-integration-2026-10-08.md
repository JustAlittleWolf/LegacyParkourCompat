# Sprint-start water-input integration

**Integration branch:** `fix/integrate-sprint-start-water-input-2026-10-08`

**Base:** local `main` at `a5c191df557cd152d578150c689227897ac33256`

**Integration date:** 2026-10-08

## Implementation and review chain

- Source finding: `F-WATER-SPRINT` at `workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-SPRINT.md`, SHA-256 `8698af8d4474a11725abdcfb1aabcea8a84e6fb44c31a8254bf6a40810b5f114`. Its pair remains active/partial; this integration makes no full-pair claim.
- Implementation commit `bc1e178f7d05786eea357896ddf90c6a0e5cf25d` added the separate double-tap and held-key sprint-start routes and versioned V1_12/V1_13 changes. Its accepted correction is `4ade0220a6c5cfa299c08acab57594270d444660`. Local code commits are `83b4cde5` and `21358a4a`.
- Worker handoff `bd396a4f122f0253c806515b3991df81df6ea8e3` is imported as `b30fd6f9`. The release-scope paragraph was corrected in local commit `37f8ea7f` to reflect the accepted fix: when no closer registration exists, the resolver selects the V1_12 grounded double-tap behavior for older selected profiles too. Sampled sources support that condition at 1.8.9, 1.9.4, 1.10.2, 1.11.2, and 1.12.2. Exact 1.11/1.11.1 sources and patch-level continuity for every earlier profile remain unverified; no parity claim is made for uninspected releases.
- Original independent implementation review: REQUEST CHANGES at `fd5a4153` (later corrected reference `01d4ee90`), imported locally as `2e25ed86` and `e9a823bf`.
- Fresh independent reviewer accepted the correction at `4a01f7e2f589231ee9abe8787e6327f3f04eea1c`; local review commit `f8aa98b8`. The original REQUEST CHANGES report remains preserved. The correction review accepts the bounded static code change only, not patch-level continuity or runtime behavior.

## Hook interaction and scope

The combined `LocalPlayerMixin` composes the new `SprintInputStartBehavior` after the existing `WaterSprintGateBehavior` in the `canStartSprinting()` shallow-water argument. It separately wraps the first two `setSprinting(true)` calls in `LocalPlayer.aiStep()` for double-tap and held-key starts. Later stop calls and sprint continuation are untouched.

The existing `SwimmingUpdateBehavior` dispatch is in `EntityMixin` at the shared `Entity.updateSwimming()` operation. This patch changes only `LocalPlayerMixin` and the V1_12/V1_13 sprint-input providers; it does not alter `SwimmingUpdate`, the V1_16_2 registration, or the shallow-water current code in `EntityMixin` and `EntityFluidCurrentMixin`. The current main's sleep-safety hooks are also untouched.

The V1_12 grounded rule applies wherever its hook is the closest registration, including older profiles. The known source endpoints support the rule, but missing 1.11/1.11.1 bodies and gaps between release representatives remain open. The 1.13.2 predicates are represented by V1_13; exact 1.13.0/1.13.1 evidence is absent and the first changed patch release in `(1.12.2, 1.13.2]` is unknown. No broader source continuity, sprint parity, or runtime result is claimed. The revised-derived Feather artifacts also remain unproven equivalent to unavailable original derived jars.

## Build and artifacts

- Successful command: `.\gradlew.bat build -x test --init-script build\disable-tests.init.gradle --console=plain`. The init script disables every Gradle `Test` task. Result: `BUILD SUCCESSFUL`, exit code `0`; no test task ran.
- Successful full log: `workflows/fix-implementation/runs/sprint-start-water-input-build-2026-10-08.log`, SHA-256 `5e89fa390e2b020a3ef142fa8942d5a977620f1aa228154e7d5e2c7c0ed9627a`.
- A setup invocation exited before Gradle configured the project because the fresh worktree did not yet have a `build` directory for the init script. It ran no tasks or tests. Its retained log is `workflows/fix-implementation/runs/sprint-start-water-input-build-setup-failure-2026-10-08.log`, SHA-256 `ed76bcec2d00a4e9133983f26c5f6ab1b598913f4c20fb175fa3afface53bfcc`.
- Unique mod JAR: `build/integration-artifacts/LegacyParkourCompat+26.2-1.0.0-sprint-start-water-input-2026-10-08.jar`, SHA-256 `154db8767cc48ae00a3c82c400f97af63cef2fad4ef7981be8152891d5eae73a`.
- Previously retained sleep JAR unchanged: `D:\Javastuff\LegacyParkourCompat\.task-worktrees\integrate-sleep-safety-slime-no-code-2026-10-08\build\integration-artifacts\LegacyParkourCompat+26.2-1.0.0-sleep-safety-transition-2026-10-08.jar`, SHA-256 `dba8e3316104ec2de195539472bd767376930c7b05f2fd4999ced0c9a3f9209e`; build log SHA-256 `ebe4d6e738a771a1318f68648c917587d8865a1c04074b47fd74e86e388c91ce`.
- Previously retained water-current JAR unchanged: `D:\Javastuff\LegacyParkourCompat\.task-worktrees\shallow-water-current-cutoff-1-21-11-2026-10-08\build\integration-artifacts\LegacyParkourCompat+26.2-1.0.0-shallow-water-current-cutoff-2026-10-08.jar`, SHA-256 `b0e7b58b91311c34de30ba192cc4b03362ec1f59615800875607a7d7af3da1a4`; build log SHA-256 `4f78a61dcd98d785fda83f95fc6dbf42d9b447943cddf18887033ac09d94472c`.
- Previously retained slipperiness/sampler JAR unchanged: `D:\Javastuff\LegacyParkourCompat\.task-worktrees\integrate-world03-swimming-entry-2026-10-08\build\libs\LegacyParkourCompat+26.2-1.0.0.jar`, SHA-256 `f4035fbb47ac5169de7a3510cd3728c68256fc3c778834839c79c1418d674f9c`; build log SHA-256 `77af6ef6f85f9d77d3453d13551962b52382f2a3f8d3fb04e540209395f2f115`.

The three prior JAR and build-log hashes above were rechecked after this build. The integrated Java source tree is `45c497108f24b7508b60488a756ef950be4572d1`.

No tests, clients, servers, TAS runs, Gym, Docker, or push were used. The compiled JAR verifies compilation and packaging only; runtime validation remains pending.
