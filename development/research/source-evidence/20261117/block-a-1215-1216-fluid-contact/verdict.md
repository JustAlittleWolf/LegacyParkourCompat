# Frozen source inventory — 1.21.5 → 1.21.6 fluid-contact audit

Frozen before opening the two parent-supplied immutable lane proofs. Host: Windows 11, PowerShell. Mappings: Mojmap.

## Version identity

- 1.21.5 version-root `mojmap.ready.json`: versionId 1.21.5, Mojmap, 5,921 sources/27,311,391 bytes; declared source-manifest SHA256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` (verified against `mojmap.sources.sha256` bytes); artifacts-manifest SHA256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`.
- 1.21.6 embedded hidden `mojmap/.decompile-provenance.json`: SHA256 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`; 6,103 source entries, 87 pinned inputs. Embedded inputs include exact 1.21.6 metadata `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd`, Mojmap client jar `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`, official client mappings `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`.
- 1.21.5 provenance sidecars identify Mojmap exact run for 1.21.1/1.21.3/1.21.4/1.21.5, Vineflower 1.12.0, mapping-io 0.9.1, Java 25.0.3+9-LTS; ready marker is the published manifest for the source tree.

## Frozen paired source roster

SHA-256 hashes are raw source file bytes. Relative path is beneath each version mapping root. These 12 files are the source-only movement/resource dependency inventory frozen for adjudication:

| Relative source | 1.21.5 SHA-256 | 1.21.6 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/world/entity/LivingEntity.java` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/world/entity/player/Player.java` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/client/player/LocalPlayer.java` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `971bc296fe1feeb3fc54a2b78c0a0a6d0df2578fb79428c451f25be57c20a648` | `b10c8a80de875262ffc8c95614f8e6eeff187ded7c042261ffde993230479d2e` |
| `net/minecraft/world/level/material/Fluid.java` | `e98bdfd14b3092096c6ba763642c3724f852b7589edbf0052b2c8767a874bf94` | `e98bdfd14b3092096c6ba763642c3724f852b7589edbf0052b2c8767a874bf94` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `fd3f64f3739a9aded48358bd76362abf6a1248b42a636035cc5ca988b3f8238d` | `fd3f64f3739a9aded48358bd76362abf6a1248b42a636035cc5ca988b3f8238d` |
| `net/minecraft/world/level/material/WaterFluid.java` | `9a829ec0d8083d6a3e73a98d40d6c674fd3431aafdb7d05a78cca1daff6e1674` | `9a829ec0d8083d6a3e73a98d40d6c674fd3431aafdb7d05a78cca1daff6e1674` |
| `net/minecraft/world/level/material/LavaFluid.java` | `04134f233b68e7a8856fbf410414eca8ee75f6acb565276d4c4859dc28a6308c` | `04134f233b68e7a8856fbf410414eca8ee75f6acb565276d4c4859dc28a6308c` |
| `net/minecraft/world/level/material/Fluids.java` | `913cb5d937af92a7f90f7dc233a3d02c05f75e70a2a740300b2f26eedcbed931` | `913cb5d937af92a7f90f7dc233a3d02c05f75e70a2a740300b2f26eedcbed931` |
| `net/minecraft/tags/FluidTags.java` | `1960057695560fbedaba1c722fdb6ddc0c97a8f233726a15de0191fde242d08f` | `1960057695560fbedaba1c722fdb6ddc0c97a8f233726a15de0191fde242d08f` |
| `net/minecraft/tags/TagKey.java` | `0e91dfc9262d0ef487d1c528dab79606122ba2c71e535f590e17a555d57a8df3` | `0e91dfc9262d0ef487d1c528dab79606122ba2c71e535f590e17a555d57a8df3` |

The 1.21.5 per-source values match entries in `mojmap.sources.sha256`; the 1.21.6 values match entries in `.decompile-provenance.json.sources`. A raw-source no-difference verdict is limited to the listed files and method/member slices examined; no vanilla resource/tag payload is included by these Java source roots.

## Immutable supplied proof rehash

Both parent-supplied source-only reports were rehashed before adjudication. The report payloads decoded as strict UTF-8 with LF line endings and no CR bytes; actual SHA-256 exactly matches the supplied identity:

- `lane-fluid-contact-1215-1216/comparison.md`: `03a914cdb74599b81e28e69b808df17be523e66bcdbf7c615aec560f03282878`.
- `lane-fluid-tags-1215-1216/comparison.md`: `068ca6a4d747d03f25ee7516c915dd525cb70d35c88a5c8010a70e1089c590fe`.

The older resource proof it cites, `development/research/source-evidence/20261103/checkpoint03/1.21.5-resource-manifest.json`, was read and rehashed locally: `22257a196c00ce94b3ee1862f9405d1c2ecb5077703a085491bb3016110351a0`. Its client hash `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` matches the 1.21.5 ready marker and `artifacts.sha256` client.jar entry. The manifest itself records `historicalMetadataMatch: false`, so its metadata hash is not used as the historical artifact identity.

The local 1.21.6 `.minecraft-decompile-cache/1.21.6/client.jar` hashes to `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`, matching the hidden 1.21.6 provenance input pin. Both relevant members were read directly from this ZIP. Raw member SHA-256 and payloads:

| Member | SHA-256 | Parsed default values |
|---|---|---|
| `data/minecraft/tags/fluid/water.json` | `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004` | `minecraft:water`, `minecraft:flowing_water` |
| `data/minecraft/tags/fluid/lava.json` | `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b` | `minecraft:lava`, `minecraft:flowing_lava` |

The local 1.21.5 client ZIP was not present in the bounded build/cache/Gradle-cache locations, and the 1.21.5 source run’s artifact directory did not contain a client ZIP. Therefore the older member hashes remain recorded pinned-resource evidence; I could not independently re-extract those bytes from the older archive. The supplied report states this limitation. The endpoint no-difference disposition is supported by the older pinned manifest digest matching the directly read newer ZIP member digest, with direct old-archive reproduction still unavailable.

## Supplemental source roster and identities

The tag/resource proof names the following exact source witnesses. All existing Java-file hashes below were independently computed and matched each endpoint’s published source hash map (`1.21.5/mojmap.sources.sha256` or `1.21.6/mojmap/.decompile-provenance.json` `sources` map). `DriedGhastBlock.java` is absent from the complete 1.21.5 source manifest/tree and exists in the 1.21.6 manifest.

| Relative source | 1.21.5 SHA-256 | 1.21.6 SHA-256 |
|---|---|---|
| `net/minecraft/world/level/material/FluidState.java` | `1bfc76676cc7cf136220c05f3506ae4d931cfa33ce0833d307b28048a5d28c61` | `1bfc76676cc7cf136220c05f3506ae4d931cfa33ce0833d307b28048a5d28c61` |
| `net/minecraft/data/tags/FluidTagsProvider.java` | `ab9fa0839166ea355bd34ca6c3f789415ee5dddc93779d97c2d6f6d0a931d2b7` | `ab9fa0839166ea355bd34ca6c3f789415ee5dddc93779d97c2d6f6d0a931d2b7` |
| `net/minecraft/world/level/block/SimpleWaterloggedBlock.java` | `5e81d82a6f143201e185642d5c1a5bdb348f8f66db20fba65e4f2224bbab2bcb` | `5e81d82a6f143201e185642d5c1a5bdb348f8f66db20fba65e4f2224bbab2bcb` |
| `net/minecraft/world/level/block/SlabBlock.java` | `d3666004285a0df44a3b20244d2f474e2feaea78e766b7da1317ca7bd70c8ec8` | `d3666004285a0df44a3b20244d2f474e2feaea78e766b7da1317ca7bd70c8ec8` |
| `net/minecraft/world/level/block/TrapDoorBlock.java` | `dc18356849fa509dcaf15fe9d6bc4688cfcf7c6e22f443cd47a43702148b8185` | `dc18356849fa509dcaf15fe9d6bc4688cfcf7c6e22f443cd47a43702148b8185` |
| `net/minecraft/world/level/block/WaterloggedTransparentBlock.java` | `4ebddce2840903ce1749a6b094a5d96dc3484b3347b2631c0c4eefdca156fc83` | `4ebddce2840903ce1749a6b094a5d96dc3484b3347b2631c0c4eefdca156fc83` |
| `net/minecraft/world/level/block/WallBlock.java` | `30f65426c97e2507c13e55edbd078ab86f12d24a1496adb80e5d3cd44b2880f4` | `30f65426c97e2507c13e55edbd078ab86f12d24a1496adb80e5d3cd44b2880f4` |
| `net/minecraft/world/level/block/DriedGhastBlock.java` | absent | `831c0ddfa32477abe6e0f59cce30531259acc9cf2067dbc2e240bea72866125b` |

## Independent adjudication

### Fluid-contact/current queue: no player movement delta found

The comparison report’s relevant claims are accepted for the assigned LocalPlayer route. The source methods governing the operation are identical by body comparison where noted below; the operation-specific blocks within larger methods were directly compared in both decompilations:

- `Entity.baseTick` runs `updateInWaterStateAndDoFluidPushing`, then `updateFluidOnEyes`, then `updateSwimming` on both sides (`1.21.5 Entity.java:442–463`; `1.21.6 Entity.java:472–493`). The full method differs in an unrelated fire-tick block, not in this fluid sequence.
- `Entity.updateInWaterStateAndDoFluidPushing` and `Entity.updateFluidHeightAndDoFluidPushing` are exact same-body comparisons (`1.21.5 Entity.java:1347–1353, 3241–3305`; `1.21.6 Entity.java:1424–1430, 3414–3478`). `updateInWaterStateAndDoWaterCurrentPushing` is textually identical as well. The unloaded-chunk gate, deflated AABB, floor/ceil and exclusive iteration bounds, FluidState tag predicate, maximum admitted surface depth, current collection and depth scaling, per-player normalization exception, minimum impulse, and per-tag cache write all match.
- `isInWater` returns cached `wasTouchingWater`; `isInLava` remains gated by `!firstTick` and cached lava height. The movement path reads those cached observations; both implementations and `updateFluidOnEyes` match. Water is refreshed before travel during base tick. `Entity.move` calls `checkFallDamage` only on the local-authority path after collision resolution (`1.21.5 Entity.java:700–704`; `1.21.6 Entity.java:717–721`). The unchanged `LivingEntity.checkFallDamage` refreshes water/current after movement only when cached water contact is false. It does not refresh lava there. Thus an exit with a prior true cache can be observed at the next base tick; a false water cache can trigger a post-move sample/current push in the same operation order in both releases.
- `LivingEntity.travel` and `travelInFluid` compare equal by method body (`1.21.5 LivingEntity.java:2213–2222, 2249–2293`; `1.21.6 LivingEntity.java:2245–2254, 2301–2345`). The water/lava gate, current-block FluidState lookup, cached contact decisions, water drag/acceleration and attribute casts, lava depth threshold, gravity branch, movement, and post-move horizontal-clearance response that sets Y to `0.3F` are unchanged. `getFluidFallingAdjustedMovement` also matches exactly (`1.21.5:2406–2419`; `1.21.6:2458–2471`).
- Vertical fluid input/exit response is unchanged: `goDownInWater` adds `-0.04F` Y, `jumpInLiquid` adds `0.04F` Y, and the `LivingEntity.aiStep` water/lava jump selection is unchanged (`1.21.5 LivingEntity.java:2187–2193, 2727–2745`; `1.21.6:2219–2225, 2779–2797`). The locally controlled `LocalPlayer.aiStep` body is equal and its shift-key water-descent gate is unchanged (`LocalPlayer.java:775–776`). `Player.travel`, `isAffectedByFluids`, and `isPushedByFluid` are equal; both player gates return `!abilities.flying`. `Player.canSimulateMovement` and `Player.isEffectiveAi` are identical in both sources and return the same player-specific expression; `LocalPlayer.isLocalPlayer()` is true in each endpoint. The 1.21.6 `LivingEntity.aiStep` adds `&& this.isEffectiveAi()` to the travel call condition, but this does not change the Player route because the two Player overrides are identical. The newly added `LivingEntity.travelFlying` helper is called by Allay, HappyGhast, Ghast, and Phantom, not Player/LocalPlayer; `Player.travel` delegates through `super.travel` to the same water/lava selector.
- `LocalPlayer.tick` has an unrelated removal of `sendShiftKeyState()` (a network-state packet); it does not alter local fluid movement. The one full `LivingEntity.aiStep` predicate change described above likewise does not alter player travel due to Player’s matching overrides. The raw file SHA differences in Entity, LivingEntity, Player, LocalPlayer and AbstractClientPlayer do not represent a fluid-contact delta in the examined slices.

### Built-in tags and WATERLOGGED dependency: digest-level no difference, with old-archive limitation

The supplied tag report’s separate WATER and LAVA dispositions are accepted as no endpoint difference at the pinned-manifest digest level. The source provider is byte-identical and explicitly declares WATER = `Fluids.WATER` plus `Fluids.FLOWING_WATER`, LAVA = `Fluids.LAVA` plus `Fluids.FLOWING_LAVA` (`FluidTagsProvider.addTags`, lines 17–20 in both versions). `FluidState.is(TagKey)` is byte-identical and checks the built-in fluid type holder against the tag (lines 108–110). The directly read 1.21.6 JSON members match the exact SHA-256 recorded for each 1.21.5 member by the local source-evidence resource manifest. Because the 1.21.5 ZIP is absent, this review cannot claim direct byte extraction on both endpoints; retain that precise provenance/reproduction limitation.

The named vanilla waterlogged producers are byte-identical: `WaterloggedTransparentBlock.getFluidState` returns a water source when wet (`getSource(true)`); `SlabBlock`, `TrapDoorBlock`, and `WallBlock` return a water source when wet (`getSource(false)`) and otherwise delegate to their superclass. These producers therefore yield fluid states of the existing WATER-tagged fluid type, while the player’s sampler consumes the resulting `FluidState` and does not inspect the block’s `WATERLOGGED` property directly. No shared producer, tag key, tag predicate or default WATER/LAVA member difference was found.

The 1.21.6 `DriedGhastBlock` is a newly present source (`.6` source hash above; absent from the complete `.5` source roster). Its wet state returns `Fluids.WATER.getSource(false)` (`DriedGhastBlock.java:162–171`). This introduces a new block producer in 1.21.6; it is not a modification to historical shared waterlogged behavior and does not warrant earlier-version feature emulation under project scope. It is relevant only as a modern state that the unchanged fluid sampler can observe when the modern block exists.

## Scope and terminal status

This is an independent, bounded source/resource audit of ordinary locally controlled player fluid contact/current sampling, sample/cache timing and authority, water/lava travel, vertical liquid response, exit clearance, and exact built-in WATER/LAVA dependency for 1.21.5 Mojmap → 1.21.6 Mojmap. No canonical source difference/finding is warranted for these operations. The independent acceptance is limited to the compared endpoints and sources/methods above; it is not full-pair tick-graph coverage, intermediate-patch boundary evidence, runtime parity, or a claim about arbitrary datapacks. The old client archive is unavailable locally, so the old default-tag JSON bytes remain backed by the cited pinned manifest rather than a second direct ZIP read. No product, wiki, backlog, Git-history, runtime, build or test checks were performed.