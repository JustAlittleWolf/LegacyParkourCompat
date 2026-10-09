# Independent MCPK-aware review: Y=256 boundaries (2026-10-09)

## Verdict

**ACCEPT — bounded conditional source mechanism at exact 1.14 and 1.15.2 endpoints.** The cited producer and consumer gates support the conditional stale water-depth path in each endpoint. The review does not assign a first affected or fixed release, and does not establish MCPK's reported jump trajectory or a universal higher-jump result.

## Immutable review inputs

| Role | Exact identity |
|---|---|
| 1.14 boundary memo | `a3a3dda4cdbc0e2df361542d86c2a6f885eb4ecc:workflows/wiki-audit-2026-10-07/mcpk-y256-1.14-boundary-2026-10-09.md`, blob `3675c55628e64c857ae20969b454aabea5c8ee98` |
| Final 1.14 memo merge | `85c1236c8539f9e07b56e4716951ec5a4ffd3cfc`; author memo is its first parent, second parent `ee38e8b1ed658e4b7ea2d128547c7571e3a5f7a2` |
| 1.15.2 boundary memo | `de623cb55be97374f1d13f621e0b9a47005ec092:workflows/wiki-audit-2026-10-07/mcpk-y256-1.15.2-boundary-2026-10-09.md`, blob `3620dfdca035c9fb7024d3b7bdc79a06b9292913` |
| Original mechanism, navigation only | `681d1d54d69ec693f02e07e5a6b1841c401c4bb6:workflows/wiki-audit-2026-10-07/mcpk-y256-producer-2026-10-09.md`, blob `fddcf603a83611d9d10a0f5f684e270433fcfd30` |
| Bounded prior review, navigation only | `2e47dad5165119de97b940e17eebf0bcad21b3c4:workflows/wiki-audit-2026-10-07/mcpk-y256-producer-review-2026-10-09.md`, blob `835b09a7db975c4a4026ebadc1fdb15c2be651b4` |

The original mechanism and prior review were used only to locate the requested scope and preserve their bounded dispositions. This pass inspected the two boundary memos, their directly cited source files and publication manifests, and the exact 1.14 mapped-JAR methods. It did not consult the Wiki, implementation, or ordinary source lane.

## Publication and source identity checks

Read-only ready roots were `ready/1.14/ornithe-feather` (Feather `net.ornithemc:feather-gen2:1.14+build.2`) and `ready/1.15.2/mojmap`. Each marker reports `ready`; marker manifest digests match the sidecar files. The directly cited source files match both their SHA-256 values in the memos and their source-manifest entries.

| Publication | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Relevant client artifact SHA-256 |
|---|---|---|---|---|
| 1.14 Feather | `e0ecb319a63d5d664b87ad7c00638e1cdb9dc302ee2c05bbc05a88ce96d1e60b` | `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` | `2fdf423e2fbe0d20ec961cc42cd69b3107caeed88edf3e097ee588d29868f5df` | mapped `client-ornithe-feather.jar`: `ae171032ec6ad71505488c9c5578c5c04168c74ee2cd822177c24e79d45834d8`; original client: `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` |
| 1.15.2 Mojmap | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` | mapped `client-mojmap.jar`: `c0fe9cfe2a273c42ff269b58c9aabb93815d6bb7bf457ca1deb8695833c4380b`; original client: `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` |

The cited source files are:

- **1.14:** `Entity.java` `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9`; `LivingEntity.java` `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79`; `PlayerEntity.java` `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7`; `WorldView.java` `1a9d57363b8123e48334065c363c5610e83dd353fe66edc2f08fed3ec09ddcb0`.
- **1.15.2:** `Entity.java` `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `LivingEntity.java` `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; `Player.java` `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`; `LevelReader.java` `d09aa8eaaa7f8ca8703091facb4d7e1a26c9f4bea8b99c5c8956682875241d37`.

For 1.14, the exact mapped JAR itself was rehashed and targeted `javap -c -p` checks confirmed the cited member correspondence and branch order despite the publication's general remapper warnings. This does not prove Feather-to-original-JAR equivalence or validate unrelated methods.

## Independent bounded source findings

### 1.14 endpoint

`Entity.m_69693160(Tag)` contracts the box by `0.001`, floors its minimum Y, and calls `World.isAreaLoaded(...)`. `WorldView.isAreaLoaded(...)` checks `maxY >= 0 && minY < 256` before chunk iteration. Therefore a contracted box with `floor(minY) >= 256` returns false before the scan reaches its sole `f_85121000` assignment. A scoped source-root writer search found that assignment only in the scan, after the loop. `checkWaterState()` sets `inWater = false` when the scan returns false and does not clear that depth.

The targeted bytecode checks on mapped JAR SHA-256 `ae171032ec6ad71505488c9c5578c5c04168c74ee2cd822177c24e79d45834d8` confirmed: the vertical gate precedes X/Z chunk iteration; the scanner's false-gate return precedes the depth `putfield`; `checkWaterState()` clears `inWater` but not the depth; `LivingEntity.mobTick()` reads the field and can call the water liquid-jump method without testing `inWater`; and `PlayerEntity.mobTick()` invokes the inherited living method.

### 1.15.2 endpoint

`Entity.checkAndHandleWater(Tag)` likewise deflates the box by `0.001`, floors minimum Y and calls `LevelReader.hasChunksAt(...)`. That gate requires `maxY >= 0 && minY < 256`, before chunk checks. If `floor(contractedBox.minY) >= 256`, the scan returns before its `waterHeight` assignment. A scoped entity-source search found only the final assignment in `Entity.java`. `updateInWaterState()` clears `wasInWater` when the scan returns false but leaves `waterHeight` untouched. `LivingEntity.aiStep()` can select `jumpInLiquid(WATER)` from `waterHeight > 0.4` while airborne or above the threshold on ground, without an `isInWater()` predicate; `Player` extends `LivingEntity` and delegates to `super.aiStep()`.

### Conditions and limits

The relevant conditional path requires a previous completed water scan to have stored a positive depth above `0.4`, followed by a scan whose contracted box minimum floors to at least `256`, and a true jump flag. The consumer then has to meet its own movement gates. These exact-source observations support a stale-depth route at the two inspected endpoints; they do not show that every player or water exit meets those conditions, quantify a resulting jump, or reproduce MCPK's report.

This review makes **no first-affected or first-fixed release claim**. It makes no claim about uninspected 1.14 patches, an exact cross-version cutover, or runtime trajectory. Feather provenance remains a limitation for 1.14. The 1.15.2 endpoint is Mojmap source; this review did not independently recheck other release endpoints.

## Review procedure and scope

This was an independent read-only source/artifact review plus static bytecode inspection. No builds, tests, runtime clients, TAS, Gym, servers, Docker, decompilation, network fetches, implementation edits, or shared-source changes were performed. Only this review memo is new.
