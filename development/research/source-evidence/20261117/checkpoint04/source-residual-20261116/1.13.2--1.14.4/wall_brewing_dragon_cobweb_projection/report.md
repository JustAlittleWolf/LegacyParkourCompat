# Wall brewing stand, dragon egg, and cobweb face projection

## Bounded scope and immutable inputs

This source-only slice closes only the common keys `minecraft:brewing_stand`, `minecraft:dragon_egg`, and `minecraft:cobweb` from `wall_remaining_face_shape_census/report.md` (SHA-256 `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`). Endpoints are Minecraft 1.13.2 and 1.14.4, Ornithe Feather Gen2 build 2. The read-only endpoint source-manifest SHA-256 identities are:

- 1.13.2 `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256`: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4 `decompiled_minecraft/1.14.4/ornithe-feather.sources.sha256`: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.

The findings below compare the old wall's `FaceShape` connection input with the target wall's collision-face projection for every legal state and each horizontal face. They do not establish a global registration census or freeze the pair.

## Registration and domains

| Common key | 1.13.2 registration | 1.14.4 registration | Complete state domain at both endpoints |
|---|---|---|---|
| `minecraft:brewing_stand` | `Block.java:1215`, `BrewingStandBlock`; `Blocks.BREWING_STAND` resolves the registered key (`Blocks.java:887`). | `Blocks.java:755`, `BrewingStandBlock`. | `HAS_BOTTLE_0 × HAS_BOTTLE_1 × HAS_BOTTLE_2`, each Boolean: 8 states. The shape providers do not read these properties. |
| `minecraft:dragon_egg` | `Block.java:1225`, `DragonEggBlock`; `Blocks.DRAGON_EGG` resolves the key (`Blocks.java:892`). | `Blocks.java:768–770`, `DragonEggBlock`. | No block-state properties: one state. The provider does not read state, world, position, or collision context. |
| `minecraft:cobweb` | `Block.java:920`, `CobwebBlock`; `Blocks.WEB` resolves the key (`Blocks.java:719`). | `Blocks.java:267`, `CobwebBlock`. | No block-state properties: one state. No target collision-shape override or state-dependent shape selection. |

## Projection and wall gates

In 1.13.2, `WallBlock.shouldConnectTo` accepts the generic arm only when the neighbor face is `FaceShape.SOLID` and the block is not in `isExceptionForConnection`; its separate pole arm is limited to pole-shaped faces and fence gates (`WallBlock.java:51–58`). Each assigned old provider returns `FaceShape.UNDEFINED` unconditionally (`BrewingStandBlock.java:127–129`, `DragonEggBlock.java:80–82`, `CobwebBlock.java:60–62`), so the generic arm and pole arm are false on north, east, south, and west for every assigned state.

In 1.14.4, `WallBlock` supplies `BlockState.m_87223014(world, pos, face)` to its neighbor predicate; the predicate is `!isExceptionForAttachment(block) && faceSolid || wallTagOrFenceGate` (`WallBlock.java:48–51`, placement calls at `:67–70`, updates at `:93–102`). `BlockState.m_87223014` returns the state's cached directional result when available and otherwise calls `Block.isFaceSolid`; state-cache construction also computes that result through `Block.isFaceSolid` (`BlockState.java:326–327, 416`). `Block.isFaceSolid` rejects leaf-tagged blocks and checks whether `state.getCollisionShape(world, pos)` projects to a full block face (`Block.java:402–408`; collision-shape state dispatch at `BlockState.java:197–202`). None of the three keys is an attachment exception, wall-tag member, fence gate, or leaf. The target `walls` and `leaves` payloads are the exact 1.14.4 resource entries recorded in `wall_resource_membership/extraction-record.md` (record SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`; payload hashes `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` and `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`). The bounded raw-resource audit accepted those exact entries and hashes (`wall_resource_membership_audit/verdict-r2.md`, SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`).

Target `Block.getCollisionShape` gates provider use on the registered block's `hasCollision` flag; `Block.Properties` defaults that flag to true, while `.noCollision()` sets it false (`Block.java:376–377, 811, 853–854`). The per-key provider and projection result is:

| Key / class | Target collision gate and shape | Old wall bit N/E/S/W | Target wall bit N/E/S/W | Result for every legal state |
|---|---|---|---|---|
| `brewing_stand` / `BrewingStandBlock` | `hasCollision=true` by default. Target `getShape` returns the static union of a plate `(1,0,1)..(15,2,15)` and stem `(7,0,7)..(9,14,9)` in sixteenths (`BrewingStandBlock.java:34, 52–53`). Every component is inset from all four horizontal block-boundary planes, so each horizontal face slice is empty and not full. | `false / false / false / false` | `false / false / false / false` | No delta across all 8 bottle-flag states (32 directional projections). |
| `dragon_egg` / `DragonEggBlock` | `hasCollision=true` by default. Target `getShape` returns `Block.block(1,0,1,15,16,15)` (`DragonEggBlock.java:18, 25–27`), i.e. `[1/16,15/16] × [0,1] × [1/16,15/16]`. It is inset on both horizontal axes, so every horizontal face slice is empty and not full. | `false / false / false / false` | `false / false / false / false` | No delta for its single state (4 directional projections). |
| `cobweb` / `CobwebBlock` | Registration uses `.noCollision()` at both endpoints (`Block.java:920`; `Blocks.java:267`); target `hasCollision=false`, so `getCollisionShape` returns empty without consulting the inherited full-block outline shape. | `false / false / false / false` | `false / false / false / false` | No delta for its single state (4 directional projections). |

The target face-slice operation selects the requested outer boundary slice of the voxel shape (`shape/VoxelShape.java:189–200`) before the full-block test; the inset brewing-stand and egg shapes therefore cannot pass merely because their outline occupies most or all of the block height. Wall placement stores the four resulting directional booleans, and neighbor updates recompute the affected one (`WallBlock.java:67–78, 88–103`). Thus this bounded comparison finds **no wall-connection-bit change** for these keys.

## Source byte identities

All listed source-file SHA-256 values were recomputed from local canonical source bytes and match the corresponding endpoint manifest entry.

| Endpoint | Source file | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.13.2 | `net/minecraft/block/BrewingStandBlock.java` | `0ff465b18dc0377c1a1a123ad083f04d07f77cea8a0bc88a218a67d750f886d3` |
| 1.13.2 | `net/minecraft/block/DragonEggBlock.java` | `4d11fb7ca6e092ced4b69ffbf896d4a1a68bde0f113a075364aca8f821731253` |
| 1.13.2 | `net/minecraft/block/CobwebBlock.java` | `a0848fddd8d8c526e99241f6f6fab74df108d0f8861a4f04cc331fd6fd058875` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 | `net/minecraft/block/BrewingStandBlock.java` | `ccda744516bdf232db4ed600bd468833640ac56a4b559725f220cd838c283761` |
| 1.14.4 | `net/minecraft/block/DragonEggBlock.java` | `445931bd984bb95f7602321b39e32850b0302f07049f75966d0ff7a85378c007` |
| 1.14.4 | `net/minecraft/block/CobwebBlock.java` | `ec068b2ead79f4986dad8afdbbf442dafb0725c39f1bbde286f670631324d704` |
| 1.14.4 | `net/minecraft/block/shape/VoxelShape.java` | `8efb68bf9d5efdb0ec48e401d1852e90458c7094f8fa0c8e9ae418f268c40fdd` |
| 1.14.4 | `net/minecraft/block/state/BlockState.java` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |

The brewing-stand provider claim is also present in the exact-pair `brewing_stand_shape_provider/report.md` (SHA-256 `e85a9fb60674a27b9f183308401b1ae3a9a7f44587f503e999499c2985d67f1f`) and its accepted bounded audit `brewing_stand_shape_provider_audit/verdict.md`. No 1.12.2→1.13.2 provider result is extrapolated to establish the 1.14.4 target claims for dragon egg or cobweb; those are checked against the exact endpoint source above.

## Bounds and unknowns

No unknown remains within the three named keys, their legal state domains, the old horizontal `FaceShape` input, or the target horizontal collision-face projection. This endpoint comparison does not establish when any behavior changed in intermediate patches, does not close other common keys or aliases in the census, and does not claim full registration coverage, independent pair audit, pair freeze, implementation, or runtime parity.
