# 1.16.1 → 1.16.2 stationary fixture collision: Anvil, Bell, Lectern, Grindstone, Stonecutter

Status: COMPLETED — source-only bounded comparison; all five families have no ordinary collision-shape delta.

## Evidence identity and integrity

- Mapping: Mojmap on both sides, established by `decompiled_minecraft/1.16.1/mojmap.ready.json` and `decompiled_minecraft/1.16.2/mojmap.ready.json` (`mapping: mojmap`, exact `versionId`).
- 1.16.1 source manifest SHA-256: `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; ready marker records the same digest.
- 1.16.2 source manifest SHA-256: `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`; ready marker records the same digest.
- The exact source-file SHA-256 values below were recomputed from the published tree and match their entries in the corresponding source manifest. `movement-diagnostics.txt` has no diagnostic entry for the selected block/provider files.

| Source file | 1.16.1 SHA-256 | 1.16.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/level/block/AnvilBlock.java` | `6a36a996ee59d387b1713770acaf9a10728a3c96b7cf074cd4633099c3f2fddd` | `31a3e7f8bf1c1a0400bea7de7f24c14bafb0b63fc11a6e9c8ce0c745814c3119` |
| `net/minecraft/world/level/block/BellBlock.java` | `d38230bf358ecba33334ed818c586ac95ad4765ee65d3d0cf68390656169fcbc` | `6977af9b5ee64408bd57cc570225be2af29a95f313cc63c346c838a44d4370f7` |
| `net/minecraft/world/level/block/LecternBlock.java` | `b0b50393b7eaa1fab8410644fa446b93854b39a4b5476e9c5098d44b334e5e11` | `f114900df6174a6a88fa60c91122aff16d52d2a59dbd0efe59e4c275fd1894a9` |
| `net/minecraft/world/level/block/GrindstoneBlock.java` | `184e80c33b49ea0e7dcf79515f8838e433dfad06be83fa941241adc7522b5b19` | `2427ca4055a07ca81b55964abf14cbb7ad8bc605bbb6f8bd08369583a7f7ac61` |
| `net/minecraft/world/level/block/StonecutterBlock.java` | `11087dbe8616e04fe6bb8b5f5fd1b72f36e86bbc952656fcd2b05615fcd7fb41` | `ea6428ba1efff748ee41f96ada1d7cab65528a0a83183e211ddcf24cce992021` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `1b524b5c45e3be4397dd680b9bd12c937497b9088e623428cf6387406018ef7f` | `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b` |
| `net/minecraft/world/level/block/Blocks.java` | `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9` | `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12` |
| `net/minecraft/world/level/block/Block.java` | `ca9a413bf8198402ec9f2febb65c2e89eef4386cb0f1f3d36650f4047d896655` | `2838aa4fb4052b3bd78d77b3d232a6321d03bd2dfd52275ffacfe6915a89e5b6` |
| `net/minecraft/world/level/block/FallingBlock.java` | `93166b3cedaa38ca3bba1429bc50500a2793516c20347c40065ebffcee522a39` | `93166b3cedaa38ca3bba1429bc50500a2793516c20347c40065ebffcee522a39` |

The complete target class files (which contain the cited method bodies and shape definitions) are hash-bound above. A paired line comparison found no differences in collision or outline shape definitions, state selection, or collision provider bodies; textual differences in these classes are mapping-era import paths and `Component`/`TranslatableComponent` typing.

## Paired source verdicts

Source paths below are relative to `decompiled_minecraft/<version>/mojmap/`.

| Family | 1.16.1 evidence | 1.16.2 evidence | Verdict |
|---|---|---|---|
| Anvil | `net/minecraft/world/level/block/AnvilBlock.java:31-39,72-75`; `Blocks.java:1225-1233` | `AnvilBlock.java:32-40,73-76`; `Blocks.java:1232-1240` | No delta. `FACING` axis X selects X union, otherwise Z union. `getShape` supplies the shape; inherited collision provider gates through `hasCollision` then asks the state shape. `FallingBlock` adds no collision override. |
| Bell | `net/minecraft/world/level/block/BellBlock.java:37-51,137-163`; `Blocks.java:2689-2692` | `BellBlock.java:37-51,137-163`; `Blocks.java:2706-2709` | No delta. Direct `getCollisionShape` and `getShape` both dispatch to the same state shape. `FACING` and `ATTACHMENT` choose floor NS/EW, ceiling, double-wall NS/EW, or single-wall direction box; `POWERED` does not participate. |
| Lectern | `net/minecraft/world/level/block/LecternBlock.java:40-68,107-125`; `Blocks.java:2682` | `LecternBlock.java:40-68,108-125`; `Blocks.java:2699` | No delta. Direct collision always returns `SHAPE_COLLISION = SHAPE_COMMON ∪ SHAPE_TOP_PLATE`, regardless of facing/book state. The source defines the plate with equal Y bounds `15.0, 15.0`; retain that degenerate source geometry as written. Facing selects separate `getShape` outlines only. |
| Grindstone | `net/minecraft/world/level/block/GrindstoneBlock.java:28-91,104-144`; `Blocks.java:2676-2681` | `GrindstoneBlock.java:28-91,105-145`; `Blocks.java:2693-2698` | No delta. Direct collision and outline both dispatch through `getVoxelShape`; `FACE` selects floor/wall/ceiling and `FACING` selects NS/EW for floor/ceiling or one of four wall shapes. |
| Stonecutter | `net/minecraft/world/level/block/StonecutterBlock.java:29-35,64-66`; `Blocks.java:2686-2688` | `StonecutterBlock.java:30-36,65-67`; `Blocks.java:2703-2705` | No delta. Fixed `Block.box(0,0,0,16,9,16)` shape, independent of `FACING`; inherited collision provider uses it. |

## Shared provider and registration closure

`BlockBehaviour.BlockStateBase.getCollisionShape` dispatches to the owning block (`1.16.1` lines 523-529; `1.16.2` lines 524-529). Its cached state path precomputes the provider with `EmptyBlockGetter`, origin position, and empty collision context (`1.16.1` line 744; `1.16.2` line 752). Base `BlockBehaviour.getCollisionShape` returns empty only when `hasCollision` is false; otherwise it calls the state shape with empty context (`1.16.1` lines 275-277; `1.16.2` lines 276-278). `Properties.hasCollision` defaults to `true` (`1.16.1` line 767; `1.16.2` line 787). The five `Blocks` registrations use default-collision properties and no `.noCollission()` call. Anvil inherits through `FallingBlock extends Block`; Stonecutter inherits through `Block`; neither `Block` nor `FallingBlock` overrides the provider in these source snapshots.

## Movement meaning and gaps

For stationary ordinary block collision against any of these blocks, fixed profile 1.16.1 and 1.16.2 select equivalent boxes under the same vanilla state. No player trajectory difference or finding is established for this bounded comparison. Interaction paths, support-type review, callbacks, falling behavior and shape-production edges remain outside this assignment. No whole-pair coverage or independent audit is claimed.