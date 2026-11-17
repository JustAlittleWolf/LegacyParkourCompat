# S10 source coverage: sorted indices 1–29

## Scope and evidence

Pair: Minecraft 1.20.1 → 1.20.2, Mojmap exact decompilations. This is the assigned first 29 paths (1-based) of the frozen 116-file unexamined roster, sorted ordinally by complete relative path. Scope is player solid collision and direct support shape/directional selection, with inherited state/property/neighbor/context/block-entity and loaded ordinary-route dependencies where relevant.

Canonical roots: `decompiled_minecraft/1.20.1/mojmap/` and `decompiled_minecraft/1.20.2/mojmap/`.

- 1.20.1 ready marker: `mojmap.ready.json`, source manifest SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`; exact provenance identifies version `1.20.1`, Mojmap, decompiler run, and `clientSha256` `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`.
- 1.20.2 ready marker: `mojmap.ready.json`, source manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; exact provenance identifies version `1.20.2`, Mojmap, the same declared batch run, and `clientSha256` `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`.
- Verified each of the 29 paired block source files against its corresponding `mojmap.sources.sha256` entry. The SHA-256 prefix shown below is the 1.20.1 source digest; `same` means both full file digests match, `diff` means the source files differ.

| # | Complete relative path | 1.20.1 SHA-256 prefix | Files | Scoped disposition |
|---:|---|---|---|---|
| 1 | `net/minecraft/world/level/block/AbstractCauldronBlock.java` | `abb98a9f` | same | Identity short circuit. |
| 2 | `net/minecraft/world/level/block/AirBlock.java` | `8fd90eb4` | same | Identity short circuit. |
| 3 | `net/minecraft/world/level/block/AmethystClusterBlock.java` | `4acddebc` | same | Identity short circuit. |
| 4 | `net/minecraft/world/level/block/AnvilBlock.java` | `0b0f5a53` | same | Identity short circuit. |
| 5 | `net/minecraft/world/level/block/AttachedStemBlock.java` | `956d5de2` | same | Identity short circuit. |
| 6 | `net/minecraft/world/level/block/AzaleaBlock.java` | `d4cc0d23` | diff | `getShape` returns the same static two-box shape; state/context selectors and `Blocks` registration properties match. Source change is outside the scoped provider path. |
| 7 | `net/minecraft/world/level/block/BambooSaplingBlock.java` | `8e120fea` | diff | `getShape` still offsets the same `SAPLING_SHAPE` using `BlockState.getOffset`; same `XZ` offset registration. `.noCollission()` remains set, so its outline cannot enter solid player collision/support. Other diff is outside scoped provider path. |
| 8 | `net/minecraft/world/level/block/BambooStalkBlock.java` | `fbd9af0f` | diff | `getShape` still selects `LARGE_SHAPE` iff `LEAVES == LARGE`, otherwise `SMALL_SHAPE`, then applies the same state offset; `getCollisionShape` still returns the same narrow `COLLISION_SHAPE` with that offset. Relevant fields, properties, and registration match. Other diff is outside these shape operations. |
| 9 | `net/minecraft/world/level/block/BannerBlock.java` | `afc1c856` | same | Identity short circuit. |
| 10 | `net/minecraft/world/level/block/BaseCoralFanBlock.java` | `4d96b4d8` | same | Identity short circuit. |
| 11 | `net/minecraft/world/level/block/BaseCoralPlantBlock.java` | `e24fa5cf` | same | Identity short circuit. |
| 12 | `net/minecraft/world/level/block/BaseCoralPlantTypeBlock.java` | `4faecffd` | same | Identity short circuit. |
| 13 | `net/minecraft/world/level/block/BaseCoralWallFanBlock.java` | `6eb6fd6f` | same | Identity short circuit. |
| 14 | `net/minecraft/world/level/block/BaseFireBlock.java` | `3692f450` | same | Identity short circuit. |
| 15 | `net/minecraft/world/level/block/BaseRailBlock.java` | `9ec3cd93` | same | Identity short circuit. |
| 16 | `net/minecraft/world/level/block/BedBlock.java` | `aa5fad8b` | same | Identity short circuit. |
| 17 | `net/minecraft/world/level/block/BeehiveBlock.java` | `37bed84c` | diff | No `getShape`/`getCollisionShape` provider override. Changed drip-particle path reads collision shapes only for particle placement; ordinary inherited full-block collision, support, and registration properties are unchanged. |
| 18 | `net/minecraft/world/level/block/BeetrootBlock.java` | `2231e49e` | same | Identity short circuit. |
| 19 | `net/minecraft/world/level/block/BellBlock.java` | `b0722505` | same | Identity short circuit. |
| 20 | `net/minecraft/world/level/block/BigDripleafBlock.java` | `910a0c0a` | diff | `getCollisionShape` remains the same `LEAF_SHAPES[TILT]` selector; `getShape` remains the same cached combined directional stem/leaf shape. `TILT`/`FACING` state inputs and registration match. Other diff is outside these shape operations. |
| 21 | `net/minecraft/world/level/block/BigDripleafStemBlock.java` | `dfe804a4` | diff | Directional `getShape` switch and four directional boxes are unchanged. Same properties and registration; `.noCollission()` remains set, excluding the outline from solid collision/support. Other diff is outside the selector. |
| 22 | `net/minecraft/world/level/block/BrewingStandBlock.java` | `528a06fb` | same | Identity short circuit. |
| 23 | `net/minecraft/world/level/block/BubbleColumnBlock.java` | `34f7c540` | diff | `getShape` remains empty; `.noCollission()` registration remains. Changed code is outside player solid collision/support. |
| 24 | `net/minecraft/world/level/block/CactusBlock.java` | `8220bf29` | same | Identity short circuit. |
| 25 | `net/minecraft/world/level/block/CakeBlock.java` | `850ab1af` | same | Identity short circuit. |
| 26 | `net/minecraft/world/level/block/CampfireBlock.java` | `cfe63fec` | diff | `getShape` remains the same fixed 7/16-high `SHAPE`; same registration properties. The changed nearby virtual-fence check uses a collision shape to route smoke behavior, not a player collision/support provider. |
| 27 | `net/minecraft/world/level/block/CandleBlock.java` | `eab94010` | same | Identity short circuit. |
| 28 | `net/minecraft/world/level/block/CandleCakeBlock.java` | `28c829e6` | same | Identity short circuit. |
| 29 | `net/minecraft/world/level/block/CarpetBlock.java` | `996be528` | same | Identity short circuit. |

## Dependency closure and verdict

- Verified `Block.java`, `state/BlockState.java`, `state/StateHolder.java`, `SupportType.java`, and the relevant `BushBlock` / `HorizontalDirectionalBlock` inheritance sources are byte-identical between the pair. `Block.isFaceFull`, state shape dispatch, offset dispatch, and direct `SupportType` support selection therefore have no changed implementation dependency here.
- `state/BlockBehaviour.java` differs elsewhere (the changed cache/occlusion-field area is unrelated). The compared `getShape`, `getCollisionShape`, `getBlockSupportShape`, `entityCanStandOn[Face]`, collision-cache construction, and cached `isFaceSturdy` / `SupportType.isSupporting` path are unchanged. The 1.20.2 cache change gates occlusion-face caching on `canOcclude`; it does not change collision or support selection.
- `Blocks.java` differs in the full source, so checked exact registrations for all scoped changed-provider blocks in both releases. Construction arguments and shape-relevant properties match: Azalea; Bamboo Sapling; Bamboo; Beehive; Big Dripleaf; Big Dripleaf Stem; Bubble Column; Campfire. In particular, `noCollission` remains on Bamboo Sapling, Big Dripleaf Stem, and Bubble Column.
- No changed reachable player solid-collision or direct support shape/directional selector was found in these 29 paths. No player-movement finding authored.

## Limits

This closes only ordinal indices 1–29 of the frozen 116-path remainder. Indices 30–116 remain unexamined by this worker. This does not establish complete pair coverage, independent audit, acceptance, implementation, or runtime parity. Non-player collision, fluid/reaction behavior, resources, rendering, and non-shape behavior remain outside this bounded review.
