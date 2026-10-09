# Independent MCPK-aware source review: Y=256 protection boundary (2026-10-09)

## Verdict

**ACCEPT — bounded source bracket: affected at exact 1.15.2 and protected at exact 1.16.0.** The producer ordering, range gate and player-reachable consumer support the conditional stale-depth route through the 1.15.2 endpoint and its removal at the 1.16.0 endpoint. The first protected release within `(1.15.2, 1.16.0]` remains unknown. No runtime trajectory or universal higher-jump claim is accepted.

## Immutable memo identity

| Role | Exact identity |
|---|---|
| Reviewed memo | `ec665da6da065d0099dec6ccb469ac523d4f771e:workflows/source-campaign-2026-10-07/source-memos/y256-fluid-depth-1.15-1.16-cutover-2026-10-09.md` |
| Git blob | `1bb2892719213cb625506e0c3dbe38eb956c2bbf` |
| Raw SHA-256 | `536c9dca9dd2b750232d6f729439545e9d296cdb94a9fa7169a346ffb2a0aaf7` |
| Final report-branch merge | `858f471fc2eaef7b03b0d6441f61449e91341daa`; its tree has the same blob and raw SHA-256 |

The original mechanism snapshot `681d1d54d69ec693f02e07e5a6b1841c401c4bb6`, its bounded acceptance `2e47dad5165119de97b940e17eebf0bcad21b3c4`, and the previously accepted 1.14/1.15.2 endpoint comparison (`9da67abd41f2f1f53c886e5c3519ed44f3e2e2f7`, navigation only) were not treated as proof for this new bracket. The exact 1.15–1.16 sources below were checked independently.

This review branch started at current local `main` `6e18e3009f016ff64ac025b5f51b53379150de2c`. The supplied final source-report merge `858f471fc2eaef7b03b0d6441f61449e91341daa` is not an ancestor of that `main`; it is recorded here as the immutable source-memo carrier, and both exact memo blobs are present in its tree.

Before handoff, the branch fast-forwarded from `6e18e300` to local `main` `3467cc39`, then merged the newly advanced `main` `fcd113c3fafcfde91d5cb5c8308b77292c601f9d`. The first incoming paths concerned F005 fall-distance work; the later commit only changes orchestration queue/status documentation. Neither overlaps this review or the cited memo/source artifacts. The source-review scope did not include inspecting implementation code.

## Publication and artifact identity checks

The ready roots are `1.15/mojmap`, `1.15.1/mojmap`, `1.15.2/mojmap` and `1.16/mojmap` (the last marker identifies exact 1.16.0). Each marker's version/mapping and its source/artifact manifest digests match; cited source-file hashes match the source-manifest rows. Mapped client JAR bytes for all versions used in the memo's bytecode/source checks match the artifact manifests.

| Exact release | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Mapped client JAR SHA-256 |
|---|---|---|---|---|
| 1.15.0 (`ready/1.15`) | `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `acc29974470e4f4864c695e1f1d084e746874663c47a365ca1fa41ff9fcc0cae` | `2c975a79f9d7437b723a158bf2c7ebb471bd8c65ad0caf477057b2293fc51f6b` |
| 1.15.1 | `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `d2c40f0770820752fe879670b35bd718b87e4f14491b3ec782e2e74df6b38bbb` | `458dac86a05bc8ac2e58491c6ce95acaed046894555b60fc51add8125e093918` |
| 1.15.2 | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` | `c0fe9cfe2a273c42ff269b58c9aabb93815d6bb7bf457ca1deb8695833c4380b` |
| 1.16.0 (`ready/1.16`) | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` | `3213f0827fa9215f41e74ca1dedaf104738e9c70ea1635769fb90f3996e4bf6c` |

The cited 1.15.0/1.15.1 `Entity`, `LivingEntity`, `Player` and `LevelReader` files match their source hashes; the 1.15.2 producer/gate files match the earlier producer/gate bytes and its consumer files match its own manifest. The 1.16.0 `Entity`, `LivingEntity`, `Player` and `LevelReader` files likewise match their cited hashes. The local-input bridge files match their source-manifest entries too: 1.15.2 `LocalPlayer.java` is `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`, and 1.16.0 is `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`. A scoped writer search finds the 1.15 `waterHeight` assignment in the scan only, and the 1.16 fluid-depth map clear/store in the corresponding entity scan path.

## Independently checked behavior

- **1.15.0–1.15.2 producer and water predicate:** `Entity.baseTick()` reaches `updateWaterState()` before living AI (`Entity.java:348–364`). `isInWater()` reads `wasInWater` (`Entity.java:912–913`); `updateInWaterState()` sets it false when `checkAndHandleWater(WATER)` returns false and does not clear `waterHeight` (`Entity.java:957–972`). The scan deflates the box by `0.001`, floors `minY`, and returns false on a failed `hasChunksAt` preflight before the sole `waterHeight = d` write (`Entity.java:2593–2603, 2650–2651`). `LevelReader.hasChunksAt(...)` requires `maxY >= 0 && minY < 256`; thus `floor(contractedBox.minY) >= 256` preserves the prior field value while the current water predicate clears.
- **1.15.2 consumer and player reachability:** `LivingEntity.aiStep()` chooses the water-liquid jump from `waterHeight` without an `isInWater()` predicate (`LivingEntity.java:2265–2276`). A sufficient state is a prior completed water scan with `waterHeight > 0.4`, then the failed height preflight, followed by `jumping=true` while airborne. `Player` is a `LivingEntity` and delegates through its tick/AI path; the local `LocalPlayer.isEffectiveAi()` is true and `serverAiStep()` copies `input.jumping` before the jump decision (`LocalPlayer.java:489–492, 607–617`; `LivingEntity.java:2258–2266`). This verifies a reachable conditional branch and does not infer an actual trajectory.
- **1.16.0 protection ordering:** `isInWater()` reads `wasTouchingWater` (`Entity.java:910–911`). `updateInWaterStateAndDoFluidPushing()` clears `fluidHeight` before the water scan (`Entity.java:947–955`). The same vertical gate can reject the scan, whose final map store occurs only after scanning (`Entity.java:2633–2643, 2696–2697`); the failed water refresh also sets `wasTouchingWater=false` (`Entity.java:958–972`). The player-reachable `LivingEntity.aiStep()` requires `isInWater() && k > 0.0` before selecting the water-liquid jump (`LivingEntity.java:2424–2446`). The local player routes its jump input through `serverAiStep()` as well (`LocalPlayer.java:518–521, 634–642`). Thus both stale depth and the current-water gate block the 1.15-style path at exact 1.16.0.

## Limits and review scope

This accepts only the exact endpoints and intermediate sources named by the memo: 1.15.0, 1.15.1, 1.15.2 and 1.16.0. It does not identify which release first applies the protection inside the remaining interval, claim an unsampled cutover, or validate MCPK's reported jump trajectory. The previously accepted 1.14/1.15.2 records remain endpoint context; this review does not widen them into a first-change claim. No implementation, ordinary source-lane report, Minecraft Wiki page, build, test, client, TAS, Gym, server, Docker or shared-source change was used.
