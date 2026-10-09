# F-005 exact boundary and dependency evidence — 2026-10-09

## Scope and immutable inputs

This memo closes only the first changed release and the bounded source/resource dependencies for F-005's accepted conditional `fallDistance` reset. It supplements, but does not amend or replace, the accepted 1.17.1–1.18.2 pair freeze. It does not claim a realized trajectory, emulate fall damage, or perform runtime validation.

- Finding: `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-005-fall-distance-reset-edge-gate.md`, author commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`, Git blob `8a9ff41d1c685bda185180863fe5069b5ec90783`, raw SHA-256 `6a0803d36f7d7f0f51ebdf9753e5f7adf3863cf8fe7418415ad55b12e6128d73`.
- Existing independent finding review: commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`; accepts the conditional reset only and leaves first release unknown.
- Intermediate source publications: source-preparation author commit `ecd8731a88920fe64ff1cf3a1995bf6a1a6f10f7`. Report paths/blobs: `workflows/source-campaign-2026-10-07/preparations/1.18-mojmap-2026-10-09.md` / `55c90f755e1b94c30713a9d6d7d603036432f15c`; `workflows/source-campaign-2026-10-07/preparations/1.18.1-mojmap-2026-10-09.md` / `ba2f806c8bdf2ea025c211ab0c1296571ea2acca`. Current preparation branch tip including main merge: `62346b3c8cf6eb8f2315b994aeaadf88f980842e`.
- Canonical read-only source root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\<version>\mojmap`; original client JARs are under `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts\<version>\client.jar`.

## Publication identities and exact source bindings

The ready marker for each release declares the source and artifact manifest identities below; direct file hashes for every cited source row and client resource were checked against those exact artifacts.

| Release | Source manifest SHA-256 | Artifact manifest SHA-256 | `Entity.java` SHA-256 | `Player.java` SHA-256 | `ClipContext.java` SHA-256 |
|---|---|---|---|---|---|
| 1.17.1 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` | `ff14d98c5193dd2792270e36631aace4a034b5da0a2c48e13f260454fc85b210` |
| 1.18 | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `7e41ac5791588d6fd84e84728996e8d7d8d2e995e54891c9646329e3f5dc87cd` |
| 1.18.1 | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `7e41ac5791588d6fd84e84728996e8d7d8d2e995e54891c9646329e3f5dc87cd` |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` | `c26cca8b2a2f185463b3785218518174831ea5a06268b04d9dd5c3a5287ac4ff` |

The 1.18 and 1.18.1 `Entity.java`, `Player.java`, and `ClipContext.java` files are byte-identical, as their manifest hashes show. The complete source and artifact manifests match their ready-marker declarations. The two intermediate preparation reports record the marker, provenance, diagnostics and input identities; they are publication evidence only, not behavior review.

## Exact release boundary

The operation is `Entity.move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V`.

| Release | Paired source range | Behavior after `maybeBackOffFromEdge` and `collide` |
|---|---|---|
| 1.17.1 | `Entity.java` lines 553–557; SHA-256 above | If resolved movement length squared exceeds `1.0E-7`, directly writes the resolved position. No F-005 clip or reset. |
| 1.18 | `Entity.java` lines 560–563; SHA-256 above | Same direct position write under the nonzero-movement guard. No F-005 clip or reset. |
| 1.18.1 | `Entity.java` lines 560–563; SHA-256 above | Byte-identical to 1.18 for this source file; no F-005 clip or reset. |
| 1.18.2 | `Entity.java` lines 562–574; SHA-256 above | Computes resolved length squared. Within the `> 1.0E-7` branch, requires `fallDistance != 0.0F` and length squared `>= 1.0`; clips from current position to current position plus resolved movement; calls `resetFallDistance()` only for a non-MISS hit; then writes position. |

`ClipContext.java` supplies the matching boundary: 1.17.1, 1.18 and 1.18.1 lack `FALLDAMAGE_RESETTING`; 1.18.2 line 51 adds `FALLDAMAGE_RESETTING(($0, ...) -> $0.is(BlockTags.FALL_DAMAGE_RESETTING) ? Shapes.block() : Shapes.empty())`. Thus the first release is exactly **1.18.2**, resolving to `ParkourVersion.V1_18_2`; `ParkourVersion.V1_18` covers 1.18 and 1.18.1.

The unchanged later player consumer is `Player.isAboveGround()` / `maybeBackOffFromEdge()`: in all four sources it first returns true when on ground, otherwise evaluates `fallDistance < maxUpStep` before the downward `noCollision` probe. `Player.java` hashes are in the table. In 1.17.1 the relevant predicate is lines 1017–1072; in 1.18 and 1.18.1 it is lines 1019–1072; in 1.18.2 it is lines 1032–1085. The only material ordering point for F-005 is that the current move's edge predicate precedes the new writer, while a later edge predicate reads the written field.

## Closed dependencies for a concrete old-block witness

The bounded witness uses cobweb, which is registered in every release as `Blocks.COBWEB` with `Properties...noCollission()` (A `Blocks.java` SHA-256 `87d72a113a3f8937a6a585ef917a4fd29cc5b335c00a858f6800e38f3c1bf7a8`; 1.18 through 1.18.2 `cc6b87d2c5897e71e5244b889444ac040e3fa0a139e392523e87fec99805a0f2`). It therefore existed in old maps and remains a vanilla block state.

- The `BlockBehaviour.java` files confirm `noCollission()` sets `hasCollision = false` and `getCollisionShape(...)` returns `Shapes.empty()` when false. Hashes: A `920896e6bc9d7f8794aba3c5f325d9dffd9c2c9422a0be2e5dd0b7474e980515`; 1.18/1.18.1 `80b28a6645ae9f25a59ee666fcfc813761d56a1af255741289a69f944e3058b6`; 1.18.2 `3d82b89f13ed3108e09b64226d98fd673e5fedd9a933afe888ae580db486dd89`. Each hash matches its source manifest. This closes the ordinary movement-collision shape dependency for the target block.
- `fall_damage_resetting` is absent from the 1.17.1, 1.18 and 1.18.1 client JARs. The 1.18.2 resource `data/minecraft/tags/blocks/fall_damage_resetting.json` hashes to `bda5807a4d0edf5e0641bc63312f7ca83e5bb58f9c34c09ac01b7829b0d1a850` and contains `#minecraft:climbable`, `minecraft:sweet_berry_bush`, and `minecraft:cobweb`. The witness uses the explicit cobweb member.
- In 1.18.2 `ClipContext.Block.FALLDAMAGE_RESETTING` returns `Shapes.block()` for any tagged block. `BlockGetter.clip(ClipContext)` lines 65–77 passes that selected shape to `clipWithInteractionOverride` and compares the resulting block hit with the fluid hit. `BlockGetter.java` SHA-256 `724ea5b8a585a8e088d50d09c9d649276c030c8b5520660272996879c76304ec` matches the 1.18, 1.18.1 and 1.18.2 manifest rows. The ray therefore has a full unit-cube target at cobweb even though cobweb's ordinary movement collision shape is empty. This removes a dependency on cobweb's outline/collision provider.
- `data/minecraft/tags/fluids/water.json` hashes to `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` in all four client JARs. It contains `minecraft:water` and `minecraft:flowing_water`. Water is the alternative fluid filter in the gate; it is not needed for the cobweb witness.
- A source-level qualifying state is a non-grounded Player with nonzero fall distance whose resolved horizontal move crosses a cobweb cube and has length squared at least `1.0`; it does not hold sneak during this move, so the pre-collision edge guard does not shorten it. The empty collision shape allows the movement resolver to return that segment; the special clip shape then produces a non-MISS hit and clears fall distance before position write.
- The following `Entity.checkFallDamage` in 1.18.2 (lines 979–992) only resets for grounded movement or accumulates when movement Y is negative. With the witness's zero Y and `onGround == false`, it leaves the newly zero field unchanged. This separates eligibility for the movement-state writer from the grounded callback and excluded fall-damage production.
- For a later predicate witness, `Blocks.STONE` is the ordinary registered `new Block(...)` without a no-collision property; the default `BlockBehaviour.getShape(...)` is `Shapes.block()`, and collision shape delegates to that shape when collision is enabled. Placing that old full-block support inside the short `maxUpStep` probe makes the reset path's `fallDistance < maxUpStep && !noCollision(...)` gate true, while a preserved pre-move fall distance at or above `maxUpStep` short-circuits false. This closes the bounded later-provider dependency without claiming a final pose or a runtime trajectory.

## Disposition and remaining gates

- **Finding-specific boundary:** closed; first changed release 1.18.2 (`V1_18_2`).
- **Finding-specific source/resource dependency closure:** closed for the conditional cobweb witness above, including the writer guards, reset resource, clip shape selection, ordinary cobweb collision shape, water-tag identity and unchanged later Player predicate.
- **Source pair:** complete and frozen for the bounded endpoints and inventories in the accepted run. The corrected independent full-pair audit is bound by commit `e0e36eff36a92be82327bf2a8140f18141d7cc21`; its source reconciliation is in the current ledger. This memo does not change that pair status.
- **Implementation:** not started. The existing blind reviewer accepted the original bounded claim before these intermediate sources were available. An independent source reviewer must review this memo's four-release boundary and cobweb/stone dependency closure and record acceptance before coding begins.
- **Runtime:** unverified. No build, tests, decompile, game/TAS/Gym/server launch, wiki/MCPK lookup or source-owner contact was performed for this memo.
