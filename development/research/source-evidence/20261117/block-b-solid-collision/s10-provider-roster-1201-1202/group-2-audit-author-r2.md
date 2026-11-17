# 1.20.1–1.20.2 S10 source slice: ordered roster 30–58

> Author recovery receipt r2. Reconstructed from the original authoring turn in this session after `audit.md` was replaced by an independent reviewer. The current reviewer report remains untouched at `audit.md`; `file-manifest.csv` remains untouched. This recovered report carries a new file identity and is not a byte-for-byte copy of the original file.

## Inputs and method

Windows host, PowerShell. Exact source pair: `decompiled_minecraft/1.20.1/mojmap` and `decompiled_minecraft/1.20.2/mojmap`; Mojmap on both sides. The immutable roster supplied for this assignment contains 116 unexamined relative paths. I sorted those complete relative paths with `StringComparer.Ordinal` and examined the inclusive 1-based slice 30–58. `file-manifest.csv` records each source path's SHA-256 and whether the two files are byte-identical.

Publication checks: 1.20.1 client SHA-256 `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`; source-manifest SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`. 1.20.2 client SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`; source-manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`. In both ready markers, the recorded source-manifest digest matched the actual `.sources.sha256` file digest. Source files were verified against those manifests.

## Ordered coverage

| Roster index | Relative path | Result |
|---:|---|---|
| 30 | `CarrotBlock.java` | Byte-identical. |
| 31 | `CeilingHangingSignBlock.java` | Byte-identical. |
| 32 | `ChainBlock.java` | Byte-identical. |
| 33 | `CherryLeavesBlock.java` | Byte-identical. |
| 34 | `ChestBlock.java` | Byte-identical. |
| 35 | `CocoaBlock.java` | Bonemeal target method drops the `boolean` parameter; no shape/support/collision change. |
| 36 | `ComposterBlock.java` | Byte-identical. |
| 37 | `ConduitBlock.java` | Byte-identical. |
| 38 | `CoralPlantBlock.java` | Byte-identical. |
| 39 | `CropBlock.java` | Crop survival predicate changes; crop registrations retain `.noCollission()` on both sides and `getShape`/age geometry is unchanged. Out of solid player collision. |
| 40 | `CrossCollisionBlock.java` | Byte-identical. |
| 41 | `DaylightDetectorBlock.java` | Inversion `setBlock` update flag changes 4→2; no shape/support/collision change. |
| 42 | `DeadBushBlock.java` | Byte-identical. |
| 43 | `DecoratedPotBlock.java` | Adds client placement BE initialization and clone-item retrieval; no shape/support/collision change. |
| 44 | `DiodeBlock.java` | Support predicate is refactored equivalently; new DOWN-neighbor shape-update checks in its ordinary `RepeaterBlock` and `ComparatorBlock` subclasses remove the diode when its support is no longer rigid. See candidate below. |
| 45 | `DirtPathBlock.java` | Byte-identical. |
| 46 | `DragonEggBlock.java` | Byte-identical. |
| 47 | `EnchantmentTableBlock.java` | Byte-identical. |
| 48 | `EndPortalBlock.java` | Byte-identical. |
| 49 | `EndPortalFrameBlock.java` | Byte-identical. |
| 50 | `EnderChestBlock.java` | Byte-identical. |
| 51 | `FarmBlock.java` | Byte-identical. |
| 52 | `FireBlock.java` | Byte-identical. |
| 53 | `FlowerBlock.java` | Suspicious-effect storage/accessor API changes; no shape/support/collision change. |
| 54 | `FlowerPotBlock.java` | Byte-identical. |
| 55 | `FrogspawnBlock.java` | Byte-identical. |
| 56 | `FungusBlock.java` | Bonemeal target method drops the `boolean` parameter; no shape/support/collision change. |
| 57 | `GrindstoneBlock.java` | Byte-identical. |
| 58 | `GrowingPlantBlock.java` | Byte-identical. |

## Source difference to retain for review

Potential independent support/collision operation: 1.20.2 adds a `Direction.DOWN` check to `RepeaterBlock.updateShape` and `ComparatorBlock.updateShape`. When the block below is updated, they call the new inherited `DiodeBlock.canSurviveOn(LevelReader, BlockPos, BlockState)` and return `Blocks.AIR.defaultBlockState()` if the support is not rigid. In 1.20.1 these updateShape methods lacked that check. `DiodeBlock.canSurvive` itself has equivalent support meaning: old `Block.canSupportRigidBlock(level, pos.below())` performs `getBlockState(pos).isFaceSturdy(level, pos, UP, RIGID)`; new code fetches that same position/state and calls the same `isFaceSturdy` predicate. `Block.java` is byte-identical; the relevant collision/support dispatch and `isFaceSturdy` members in `BlockBehaviour.java` are unchanged. The new hook has no subclass override in the exact 1.20.2 tree. The two new subclass gates therefore are the concrete difference, not a changed rigid-support result.

Player movement consequence: on a DOWN-neighbor update with non-rigid support, 1.20.2 removes the repeater/comparator state via `updateShape`, changing its solid collision contribution; 1.20.1 retains it along this updateShape path. The blocks use the inherited Diode `getShape` plate shape. Exact members: `1.20.1` Mojmap `DiodeBlock.canSurvive` lines 37–39; `1.20.2` `DiodeBlock.canSurvive` lines 37–44; `RepeaterBlock.updateShape` lines 55–61 (new DOWN gate); `ComparatorBlock.updateShape` lines 44–46 (new DOWN gate). The endpoint interval does not identify the introducing patch.

## Coverage limits

This closes only sorted roster indices 30–58, 29/116 paths. Indices 1–29 and 59–116 remain unexamined by this worker. This is not the roster's independent full-pair audit or freeze. The review is limited to the named solid-collision and direct support/shape selector operation graph plus the dependencies needed to establish the Diode finding. It does not close unrelated movement areas, all block behavior, world-update scheduling/tick parity, or other slices of the 1.20.1–1.20.2 pair. No wiki, product implementation, product Git history, or runtime evidence was consulted.
