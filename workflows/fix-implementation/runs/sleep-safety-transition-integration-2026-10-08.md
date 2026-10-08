# Sleep-safety transition integration

**Integration branch:** `fix/integrate-sleep-safety-slime-2026-10-08`

**Base:** local `main` at `7e7b7bb7068ec7b68f3a8f09cbb42bc498a04bf`

**Integration date:** 2026-10-08

## Sleep implementation and review chain

- Accepted source snapshot: `cdc8db327b9d99fb6344c1f98e2f12f8774dc6a1`, finding `F-SLEEP-SAFETY-TRANSITION-r3`, raw finding SHA-256 `d98ec64359ac2cc8857383a9f6ddffdcf3c3b003c8752f9b85bfb290f470b797`. The current source-campaign review records this bounded finding as accepted in `workflows/coverage-review-2026-10-07/early.md`; it keeps the 1.11.2–1.12.2 pair partial.
- Private implementation commits: original `d892a7ad527fa5a52f57fdb4ef48c178f8a21f41`; corrected implementation `fc9962a8aa967101ec0c4d3a1447b933678e1e79`; final worker record `04ba46308c389b2b171525193ad3d15a31a11ee4`. Local commits are `e41889bb` (original), `da10f176` (correction), and `7cf1957d` (updated worker record). The correction removes the exact-version guard from the old monster predicate and adds the separate creative-query gate.
- Earlier independent implementation review: REQUEST CHANGES, private commits `9794f1e6b06c71629a839aceb2e137bd39f899df` and `0e1a3bf8cc64f2ba7a87e9b75ba2a840c9387ab2`; imported as `2674a8d1` and `62b3139b`.
- Corrected independent re-review: ACCEPT for the bounded finding, private commit `c34f9830ad11569272964b93f697eab3a9a584bf`, imported as `2cfe146b`. Both review records are retained at `workflows/implementation-reviews/2026-10-08-sleep-safety-transition-review.md` and `workflows/implementation-reviews/2026-10-08-sleep-safety-transition-correction-rereview.md`.

### Source-reference note

The corrected private implementation review cites `960ce5644ceacd8e27bd3cb9145bd4eaf221379c` as the source-review acceptance commit. In this integration repository, that object ID resolves to an unrelated Elytra acceptance commit. The imported review text remains unchanged, but that private-store commit pointer is not used here as source provenance. The local campaign ledger directly records acceptance for the exact `cdc8db3` sleep snapshot and finding hash above.

## Integrated behavior and limits

`V1_11_2` registers two separate mechanics: `player.rest-safety` restores the old any-monster rejection (including a non-angry zombified piglin), and `player.rest-safety-query` makes the safety query run for creative players. Both dispatch only through `ServerPlayer.startSleepInBed`; when native behavior applies, the original creative expression and predicate remain in place. The existing fall-flying saved-state injection remains intact.

This closes only the bounded finding. Exact 1.11 and 1.11.1 sources remain unavailable; the first anger-predicate release within `(1.11.2, 1.12.2]` is unknown. The later creative-query cutover, including `V1_12`, remains a separate open behavior. No pair-coverage or runtime-parity claim is made.

Current local `main` advanced beyond the sleep workers' private base `a5612be5`. Those later source changes include the shallow-water current patch in `EntityFluidCurrentMixin` and `EntityMixin`, plus source/review documentation. None changes `ServerPlayerMixin`, the new rest-safety hook paths, pose behavior, or saved-state behavior. The shallow-water code remains separate and its retained build artifacts were rehashed after this build.

## Slime no-code disposition

Imported the documentation-only reconciliation `4bb2bd611711a184f474017d90ce8e5ac857026d` and independent ACCEPT review `b36612a06b5f2247320681a90743457f7cfa0948` as local commits `44822f27` and `3342c058`. They accept existing bounded 26.1 slime landing coverage; the separate bed finding, other restitution slices, full-pair status, and modern attribute/producer questions remain open. The Java source tree did not change for this import, so it required no additional build.

## Build and retained artifacts

- Command: `.\gradlew.bat build -x test --init-script build\disable-tests.init.gradle --console=plain`, from the integration worktree. The init script disables every Gradle `Test` task; `-x test` also excludes the standard test task.
- Result: `BUILD SUCCESSFUL`, exit code `0`. No tests or runtime, client, server, TAS, Gym, Docker, or push operation was run.
- Full log: `workflows/fix-implementation/runs/sleep-safety-transition-build-2026-10-08.log`, SHA-256 `ebe4d6e738a771a1318f68648c917587d8865a1c04074b47fd74e86e388c91ce`.
- Unique mod JAR: `build/integration-artifacts/LegacyParkourCompat+26.2-1.0.0-sleep-safety-transition-2026-10-08.jar`, SHA-256 `dba8e3316104ec2de195539472bd767376930c7b05f2fd4999ced0c9a3f9209e`.
- Water artifact unchanged: `D:\Javastuff\LegacyParkourCompat\.task-worktrees\shallow-water-current-cutoff-1-21-11-2026-10-08\build\integration-artifacts\LegacyParkourCompat+26.2-1.0.0-shallow-water-current-cutoff-2026-10-08.jar`, SHA-256 `b0e7b58b91311c34de30ba192cc4b03362ec1f59615800875607a7d7af3da1a4`; build log `D:\Javastuff\LegacyParkourCompat\.task-worktrees\shallow-water-current-cutoff-1-21-11-2026-10-08\workflows\fix-implementation\runs\1.21.11-shallow-water-current-cutoff-build-2026-10-08.log`, SHA-256 `4f78a61dcd98d785fda83f95fc6dbf42d9b447943cddf18887033ac09d94472c`.
- Slipperiness/sampler artifact unchanged: `D:\Javastuff\LegacyParkourCompat\.task-worktrees\integrate-world03-swimming-entry-2026-10-08\build\libs\LegacyParkourCompat+26.2-1.0.0.jar`, SHA-256 `f4035fbb47ac5169de7a3510cd3728c68256fc3c778834839c79c1418d674f9c`; retained build log `D:\Javastuff\LegacyParkourCompat\.task-worktrees\integrate-world03-swimming-entry-2026-10-08\workflows\fix-implementation\runs\1.19.4-slipperiness-build-2026-10-08.log`, SHA-256 `77af6ef6f85f9d77d3453d13551962b52382f2a3f8d3fb04e540209395f2f115`.

The integrated Java source tree is `cbf8ca85e2925b2a51963df09eef36a52e7f8180`. It is unchanged by the slime documentation import and the build.
