# Independent MCPK-aware source review: Y=256 introduction (2026-10-09)

## Verdict

**ACCEPT — bounded source introduction between exact 1.12.2 and 1.13.0, persisting in the checked 1.13.1 and 1.13.2 sources.** The review verifies the fluid-depth producer, the `<256` preflight, the jump consumer and the player-input route in exact ready sources. The 1.13.2 mapped-client-JAR discrepancy is real and remains a provenance incident: this verdict accepts only the individually verified source files, not bytecode or equivalence to the original client JAR. No runtime trajectory, exact snapshot cutover or broader release-line claim is made.

## Immutable memo identity

| Role | Exact identity |
|---|---|
| Reviewed memo | `360b629cdb30450904c00625bee97bfd5390b4fa:workflows/source-campaign-2026-10-07/source-memos/y256-fluid-depth-1.12.2-1.13.2-introduction-2026-10-09.md` |
| Git blob | `3c40a634db190f60e1ced59e3e97eaa0db851b9b` |
| Raw SHA-256 | `7d4682f5dc560c528351107b9ae7151d14289b1c3c93c5e4c777d935596f6009` |
| Final report-branch merge | `858f471fc2eaef7b03b0d6441f61449e91341daa`; its tree has the same blob and raw SHA-256 |

The immutable memo path is under `workflows/source-campaign-2026-10-07/source-memos/`. The related MCPK context and prior bounded mechanism/review at `681d1d54d69ec693f02e07e5a6b1841c401c4bb6` and `2e47dad5165119de97b940e17eebf0bcad21b3c4` were used only to orient the requested question; they do not substitute for this source check.

This review branch started at current local `main` `6e18e3009f016ff64ac025b5f51b53379150de2c`. The supplied final source-report merge `858f471fc2eaef7b03b0d6441f61449e91341daa` is not an ancestor of that `main`; it is recorded here as the immutable source-memo carrier, and both exact memo blobs are present in its tree.

Before handoff, the branch fast-forwarded from `6e18e300` to local `main` `3467cc39`, then merged the newly advanced `main` `fcd113c3fafcfde91d5cb5c8308b77292c601f9d`. The first incoming paths concerned F005 fall-distance work; the later commit only changes orchestration queue/status documentation. Neither overlaps this review or the cited memo/source artifacts. The source-review scope did not include inspecting implementation code.

## Publication and provenance checks

Read-only roots are `build/movement-campaign-2026-10-07/ready/<version>/<profile>`. The exact roots are `1.12.2/ornithe-feather`, `1.13/ornithe-feather`, `1.13.1/ornithe-feather` and `1.13.2/ornithe-feather`. Each ready marker reports the version and `ornithe-feather` mapping; the ready marker, source-manifest and artifact-manifest file hashes below match the memo and the bytes on disk. The cited `Entity`, `LivingEntity`, `PlayerEntity` and `WorldView` source hashes also match their source-manifest rows.

| Exact release | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Mapped client JAR, actual vs manifest |
|---|---|---|---|---|
| 1.12.2 | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` | Not used for bytecode claims |
| 1.13.0 (`ready/1.13`) | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` | `4b91c330aaf6179f7384f70f91ebcdf72af2713cc3788fea1482c3529905a761` = manifest |
| 1.13.1 | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c` | `5e4305c91a3a46a929322180624ab2cf73f33e11f7ffac86ce77c882a524fcbf` = manifest |
| 1.13.2 | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` | Actual `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; manifest `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` |

The 1.13.2 ready record identifies `versionId=1.13.2`, `mapping=ornithe-feather`, and artifact manifest `artifacts.sha256`. Its source manifest is internally consistent for the cited source files. The player-input bridge files also match their source-manifest entries: 1.13.0 `LocalClientPlayerEntity.java` is `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08`; 1.13.2 is `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`. The byte mismatch is confined to the mapped client JAR identity check above; no 1.13.2 bytecode is relied on here. Feather-derived source is not shown equivalent to an unavailable original derived JAR.

## Independently checked behavior

- **1.12.2 baseline:** `Entity.checkWaterCollisions()` refreshes `inWater` and writes it false on the no-water branch (`Entity.java:948–963`); `isInWater()` reads that boolean (`Entity.java:940`). The inspected `Entity` and `LivingEntity` sources contain no persistent water-depth field used by this jump path. `LivingEntity.mobTick()` calls `jumpInWater()` only under `isInWater()` (`LivingEntity.java:1828–1835`). `PlayerEntity.mobTick()` delegates to `super.mobTick()` (`PlayerEntity.java:413–430`).
- **1.13.0 producer:** `Entity.checkWaterState()` sets `inWater=false` when the scan returns false and does not clear `f_85121000` (`Entity.java:960–975`). The scan contracts the box by `0.001`, floors its minimum Y, and calls `WorldView.isAreaLoaded(...)`; a false result returns before the sole `f_85121000 = d` write (`Entity.java:2507–2517, 2567–2568`). The exact gate is `maxY >= 0 && minY < 256` (`WorldView.java:377–392`), so `floor(contractedBox.minY) >= 256` takes the early return before the scan can replace the old depth.
- **1.13.0 consumer and player reachability:** `isInWater()` reads the `inWater` boolean (`Entity.java:916–918`). `LivingEntity.mobTick()` selects the water-liquid jump from the stored depth without an `isInWater()` check (`LivingEntity.java:1885–1895`). A sufficient state is a prior scan that stored depth `> 0.4`, a later failed height preflight that clears `inWater` while retaining that field, and `jumping=true` with the player airborne. `PlayerEntity.mobTick()` calls `super.mobTick()` (`PlayerEntity.java:478–481`); the local client player reports locally controlled and copies `input.jumping` in `serverTickAi()` (`LocalClientPlayerEntity.java:488–491, 635–645`), which `LivingEntity.mobTick()` invokes before the jump branch (`LivingEntity.java:1896–1904`). This establishes a reachable conditional source path, not a resulting jump height.
- **Persistence through 1.13.2:** the 1.13.1 and 1.13.2 `Entity.java`, `LivingEntity.java` and `WorldView.java` bytes match each other and their memo hashes. The 1.13.2 `PlayerEntity.mobTick()` delegates to `super.mobTick()`; its changed player file has its own matching source-manifest entry. This persistence check is source-only because the 1.13.2 mapped JAR is mismatched.

## Limits and review scope

The source proves a bounded 1.12.2 baseline versus the first checked exact source carrying the route at 1.13.0, with the route present in checked 1.13.1 and 1.13.2 sources. It does not date a snapshot boundary, claim anything about an unsampled release, establish bytecode behavior for the bad 1.13.2 JAR, prove Feather-to-original-JAR equivalence, or validate a jump trajectory. No implementation, ordinary source-lane report, Minecraft Wiki page, build, test, client, TAS, Gym, server, Docker or shared-source change was used.
