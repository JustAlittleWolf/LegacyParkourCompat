# COMPLETED — source lane: 1.14.3 → 1.14.4 → 1.15 block collision shapes

## Outcome

**Status: COMPLETED for the assigned bounded source slice.** I found no eligible movement-collision shape difference, and therefore no finding draft, for slabs, stairs, iron doors, iron trapdoors, or fence gates on either edge. The reported comparisons are exact-version source observations, not runtime parity claims.

## Inputs and source integrity

Read-only source roots:

- `decompiled_minecraft/1.14.3/yarn` — exact 1.14.3; Yarn `1.14.3+build.13` (from source provenance).
- `decompiled_minecraft/1.14.4/mojmap` — exact 1.14.4; Mojang mappings.
- `decompiled_minecraft/1.15/mojmap` — exact 1.15; Mojang mappings.

The 1.14.3 provenance SHA-256 is `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`. The 1.14.4 source-manifest SHA-256 is `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; the 1.15 source-manifest SHA-256 is `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`. Both version-parent source-manifest identities and each consulted source file's SHA-256 were checked against disk; all matched. The version provenance/ready records identify the requested exact release and namespace. No source was regenerated.

The tables below give the direct source SHA-256 for the shape classes on every side of each edge. Shared selector/registry/shape dispatch evidence is listed separately.

| Family | 1.14.3 Yarn source — SHA-256 | 1.14.4 Mojmap source — SHA-256 | 1.15 Mojmap source — SHA-256 |
|---|---|---|---|
| Slab | `net/minecraft/block/SlabBlock.java` — `641a1d8e2268c5a622321f9b00f5e1b5d3b5fb7815a63656fa661184f395bea6` | `net/minecraft/world/level/block/SlabBlock.java` — `5a57ae37e77fc93074100d7e3eff1c5e23b43d8a23e0289b68ad8c9914df0d86` | `net/minecraft/world/level/block/SlabBlock.java` — `5a57ae37e77fc93074100d7e3eff1c5e23b43d8a23e0289b68ad8c9914df0d86` |
| Stair | `net/minecraft/block/StairsBlock.java` — `00b8d1a9a13b4441e10f9ad1f71b0d0de848a3e4f70e6841dca2a76272bc284d` | `net/minecraft/world/level/block/StairBlock.java` — `b302c4e8e6abe8541051f09f4b502ce51927b5ad614fd214da6d30338331dac7` | `net/minecraft/world/level/block/StairBlock.java` — `b273bfe6a1cd6a3fbb9e04a1ce13c28d697aa96105a6ffa1a7c511d264499788` |
| Door (including iron) | `net/minecraft/block/DoorBlock.java` — `0477cb19eaf5c9a2981bb2d35704134a74c2bd45768a2a13725b99817157342c` | `net/minecraft/world/level/block/DoorBlock.java` — `1280cbdebbfec5e14f4efa5b812616dab66293ccc1e4d7404c8866851ae25409` | `net/minecraft/world/level/block/DoorBlock.java` — `cce2e855a0876155913a3cbc564441b3c90b56d476cb190774db0f86a8f0b241` |
| Trapdoor (including iron) | `net/minecraft/block/TrapdoorBlock.java` — `215b4f51b1d2ce880f2373dcbba53dd4868475dc8317682efb11e7c303b41654` | `net/minecraft/world/level/block/TrapDoorBlock.java` — `286139ae1df614393a6debd68e9deba535cb67cb87659ce35a121f0b2883eb3f` | `net/minecraft/world/level/block/TrapDoorBlock.java` — `0de8aeb2981cefcbf8fb296e08401668e5fcaf3e003419bf1d45d56a06d81b86` |
| Fence gate | `net/minecraft/block/FenceGateBlock.java` — `28a55fd56a1292de15545fe25b2ae802286646843cbb011769f2413213af17c6` | `net/minecraft/world/level/block/FenceGateBlock.java` — `51c17e70f96d37767d9cd077301d7f08b75d5cb3600754b1745e10b78b5c68e5` | `net/minecraft/world/level/block/FenceGateBlock.java` — `a141dc6aaff85797fbec5552bd2b8499b418f05da00207b2c68e9be1bccdfd29` |

Additional directly checked source files and hashes:

| Version | Source path — SHA-256 |
|---|---|
| 1.14.3 | `net/minecraft/block/Block.java` — `d8728c19555875aee8f47ea138bf836d02acf9076d69fce289baad93c60e715f` |
| 1.14.3 | `net/minecraft/block/BlockState.java` — `c93fd032a3f9bd83e852eb7ce95e7afe8acd895556ed942fabc596f04be06e04` |
| 1.14.3 | `net/minecraft/block/Blocks.java` — `d003f43e26e26190ed29b17cd3a0f16b0473b5b4b58d4dbd4a247974af8b0f71` |
| 1.14.3 | `net/minecraft/entity/Entity.java` — `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| 1.14.3 | `net/minecraft/world/ViewableWorld.java` — `3e6f183c66c98206cef362551c53dbf8ea7ac11e44123060c640da09c944d29d` |
| 1.14.3 | `net/minecraft/util/math/Direction.java` — `319758bd2eb12d3862c8c9b967dae40321c3149dee8a63b4d61391799ce96335` |
| 1.14.3 | `net/minecraft/block/enums/StairShape.java` — `1c559ec5375e7959a476dd3f9274bc3c0480a3540477fcf68ac640a1b2275117` |
| 1.14.3 | `net/minecraft/util/shape/VoxelShapes.java` — `e485fa88440027dcd73d6bcd15a69438be6ee3f70ac1ba9465cb69720a0bc47c` |
| 1.14.3 | `net/minecraft/util/shape/VoxelShape.java` — `70a870fdeea376e9936ea62287f96c3377ac319c9877c0e19cb857dbd1ac96d2` |
| 1.14.4 | `net/minecraft/world/level/block/Block.java` — `276022cfc5bc00437fe65a23bbcdc9c078026e68d88930eb3244581f6dbfcf5f` |
| 1.14.4 | `net/minecraft/world/level/block/state/BlockState.java` — `2bcc0167cb566df8745abbe967f0a8750038ad5a9b7843f5fb25bab714d73188` |
| 1.14.4 | `net/minecraft/world/level/block/Blocks.java` — `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9` |
| 1.14.4 | `net/minecraft/world/entity/Entity.java` — `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 | `net/minecraft/world/level/LevelReader.java` — `1f86a029e4404b91aa532ca33ab0dbb63b085334c561ce44e0d0875aa50fdbc9` |
| 1.14.4 | `net/minecraft/core/Direction.java` — `c601c81922d1a0924cc3873c4357f006f3efb053407aeb2139a32859f60e4ada` |
| 1.14.4 | `net/minecraft/world/level/block/state/properties/StairsShape.java` — `6d8afba8e4fe6a85b11c9409b9b1d21a0774365f6ab1281407857eaa9df77f31` |
| 1.14.4 | `net/minecraft/world/phys/shapes/Shapes.java` — `f1a2e60ae358236de7379e66543f99d474e3d431e42c5afbda688cb99b70985d` |
| 1.14.4 | `net/minecraft/world/phys/shapes/VoxelShape.java` — `6a49550faa944196e16d55a8d28ca8b6cd49f1b75bb491ddc345b935ea0651b4` |
| 1.15 | `net/minecraft/world/level/block/Block.java` — `069ffc485f94c84eb63e528e608f91104229477b6ac266aa3d4103f836cac2fe` |
| 1.15 | `net/minecraft/world/level/block/state/BlockState.java` — `77320fd1e50e58d7a3ed593ccbcf21853c97d125fccebde2e0e305dc47524f21` |
| 1.15 | `net/minecraft/world/level/block/Blocks.java` — `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9` |
| 1.15 | `net/minecraft/world/entity/Entity.java` — `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.15 | `net/minecraft/world/level/CollisionGetter.java` — `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd` |
| 1.15 | `net/minecraft/core/Direction.java` — `1c87ce454b4236a469a257773a932014f4dd82afc680e70b11f54a5cb3c33b0e` |
| 1.15 | `net/minecraft/world/level/block/state/properties/StairsShape.java` — `6d8afba8e4fe6a85b11c9409b9b1d21a0774365f6ab1281407857eaa9df77f31` |
| 1.15 | `net/minecraft/world/phys/shapes/Shapes.java` — `81bf70d2c8a1d1336df72beedb30b0c9504f14decc9a06814a794aabf38203c2` |
| 1.15 | `net/minecraft/world/phys/shapes/VoxelShape.java` — `5faceea0e4ce9ad0f2a1a2c72a89d9a768ab877c8728ae2968fe34aa1e03d3ea` |

The compared state-enum files were also read directly. Across all three exact versions, the relevant serialized/member orders match: slab `TOP, BOTTOM, DOUBLE`; stair `STRAIGHT, INNER_LEFT, INNER_RIGHT, OUTER_LEFT, OUTER_RIGHT`; hinge `LEFT, RIGHT`; and top/bottom halves in the corresponding enum. `Direction` uses horizontal id/data values `NORTH=2, SOUTH=0, WEST=1, EAST=3` in both naming families, so Yarn `getHorizontal()` aliases Mojmap `get2DDataValue()` for the stair lookup.

## Edge and family coverage

The comparison interprets mapped names semantically: Yarn `StairsBlock` → Mojmap `StairBlock`, Yarn `TrapdoorBlock` → Mojmap `TrapDoorBlock`, `getOutlineShape` → the later `getShape` entry point, Yarn `BlockHalf`/`StairShape`/`DoorHinge` → Mojmap `Half`/`StairsShape`/`DoorHingeSide`, and Yarn `Block.createCuboidShape` → Mojmap `Block.box`.

| Edge | Family | Paired source slice | Result for ordinary fixed-state player collision |
|---|---|---|---|
| 1.14.3 → 1.14.4 | Slab | 1.14.3 `SlabBlock.java:26-55`; 1.14.4 `SlabBlock.java:28-57`; superclass selector in each `Block.java:364-371` / `Block.java:370-377` | **No difference found.** Bottom/top are the same 16×8×16 half-cubes; double is the full unit cube. `TYPE` selects the same branch. The API changes from outline shape plus default collision delegation to `getShape` plus default collision delegation.
| 1.14.4 → 1.15 | Slab | Same Mojmap `SlabBlock.java:28-57` on both sides (identical source SHA-256) | **No difference found.** Same source body, dimensions and selector.
| 1.14.3 → 1.14.4 | Stair | 1.14.3 `StairsBlock.java:38-108`; 1.14.4 `StairBlock.java:38-112` | **No difference found.** The base half-slab and four complementary 8×8×8 octets per half feed the same 20-entry state-index table; enum ordinal order and horizontal direction index match. `composeShape`/`union` becomes `makeStairShape`/`or`, which applies the same OR to the same cuboids; its shape simplification is representation work, not a changed occupied region.
| 1.14.4 → 1.15 | Stair | `StairBlock.java:38-112` on both sides | **No difference found.** Coordinates, octet order, index table, state-property selection and `getShape` expression are unchanged. Other class-body changes lie outside shape selection.
| 1.14.3 → 1.14.4 | Iron door | 1.14.3 `DoorBlock.java:38-71`, `Blocks.java:511-513`; 1.14.4 `DoorBlock.java:40-73`, `Blocks.java:495-497` | **No difference found.** Iron door is the same generic `DoorBlock` registered under `iron_door`. Constant names differ, but all four facing branches resolve to the same 3/16-thick coordinate plane when closed and the same hinge-dependent plane when open. E.g. EAST closed is x=0..3 on both sides despite the corresponding constant names changing.
| 1.14.4 → 1.15 | Iron door | 1.14.4 `DoorBlock.java:40-73`; 1.15 `DoorBlock.java:40-73`; `Blocks.java:519-521` | **No difference found.** Same facing/open/hinge state selector and physical cuboids. The 1.15 registration adds `.noOcclusion()`, a light/occlusion property; collision remains inherited and collidable and the shape method is unchanged in geometry.
| 1.14.3 → 1.14.4 | Iron trapdoor | 1.14.3 `TrapdoorBlock.java:29-65`, `Blocks.java:1081-1083`; 1.14.4 `TrapDoorBlock.java:33-70`, `Blocks.java:1071-1073` | **No difference found.** Iron trapdoor is the same generic class under registry id `iron_trapdoor`. Closed top/bottom are horizontal 3/16 plates; open facing branches select the same 3/16 vertical plane. Only aliases and constant identifiers change.
| 1.14.4 → 1.15 | Iron trapdoor | 1.14.4 `TrapDoorBlock.java:33-70`; 1.15 `TrapDoorBlock.java:33-70`; `Blocks.java:1139-1141` | **No difference found.** Same state-based physical geometry. As for the door, 1.15 `.noOcclusion()` does not select or alter collision shape.
| 1.14.3 → 1.14.4 | Fence gate | 1.14.3 `FenceGateBlock.java:24-29, 49-75`; 1.14.4 `FenceGateBlock.java:26-31, 43-71`; `Blocks.java:748-750`, `723-725` | **No difference found.** Gate outline remains 4/16 wide by axis and drops from 16 to 13 pixels in-wall. Movement collision remains a separate override: OPEN gives empty; closed gives the same 4/16-wide, 24/16-high axis-aligned shape. `IN_WALL` does not change that collision selector.
| 1.14.4 → 1.15 | Fence gate | 1.14.4 `FenceGateBlock.java:26-31, 43-71`; 1.15 `FenceGateBlock.java:27-32, 44-72` | **No difference found.** Same outline and collision constants and same `IN_WALL`, `FACING`, and `OPEN` selector branches.

No family-edge row produced an eligible draft.

## Collision lookup, cache and context dependency

- In 1.14.3 `Entity.move` enters collision through `World`/`ViewableWorld`; `ViewableWorld.java:121-185` selects `EntityContext.absent()` only for a null entity, otherwise `EntityContext.of(entity)`, fetches the local `BlockState`, calls `getCollisionShape(..., context)`, offsets by the local block coordinates, and tests the swept query. `BlockState.java:192-197` makes the no-context overload use the state cache when present, but its explicit-context overload calls the block directly. Its `initShapeCache()` caches only when `!hasDynamicBounds()` (`:60-63`); the cached collision shape is computed using `EmptyBlockView` and `EntityContext.absent()` (`:386-398`).
- In 1.14.4, `Entity.collide` uses `CollisionContext.of(this)` and gets block shapes through `LevelReader.getBlockCollisions`; `LevelReader.java:134-185` makes the same null→empty / entity→of(entity) choice and calls the explicit-context `BlockState.getCollisionShape` at `:141, :171-175`. `BlockState.java:186-199` exposes cached no-context collision vs direct explicit-context dispatch; cache initialization is limited to non-dynamic-shape states (`:62-65, :409-418`) and uses `CollisionContext.empty()`.
- In 1.15, the corresponding block lookup lives in `CollisionGetter.java:67-116`; it retains the same context selection and explicit-context state call. `Entity.collide` creates `CollisionContext.of(this)` (`Entity.java:643`) and supplies the entity to the block-collision stream. The cache gate and default empty-context cached collision remain the same in `BlockState.java:64-67, :192-199, :415-424`.
- The five selected shape providers read only fixed block-state properties; they do not branch on the getter, block position, or collision context. Selected registrations are not no-collision or dynamic-shape registrations. Thus the absent/empty-context cache and actual-player-context route return the same per-state collision geometry for these families. Fence gate's collision override is also context-independent.
- `Block.getCollisionShape` retains the ordinary superclass gate (`collidable`/`hasCollision`): if enabled it delegates to the state's outline/shape, otherwise returns empty. Fence gate overrides this with its separate OPEN-dependent collision method in all three releases.

The local movement source review stops at acquisition/selection of block collision shapes. Entity collision solvers, step-up, edge backoff, auto-jump, state production/neighbor updates, contact/fluid behavior, other blocks, and every unrelated released edge were not reviewed.

## Drafts and limits

- **Eligible finding drafts:** none; the inspected states select unchanged collision geometry on both concrete edges.
- **Limits:** This is not full-pair source inventory closure or an independent full-pair audit. The edge 1.14.3→1.14.4 and 1.14.4→1.15 conclusions apply only to this named family and local shape-lookup slice. The exact intermediate patch where a difference might have been introduced is not established by these endpoints. No implementation, tests, launches, or runtime parity check were performed.
