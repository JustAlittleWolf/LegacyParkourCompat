# Sea Pickle movement-collision provider (1.12.2 → 1.13.2)

## Bounded accepted inputs and exact sources

Accepted registration/dispatch inputs:

- Plant-family checklist: `build/source-drafts/plant-collision-dependency-checklist.md`, SHA-256 `1D5F3142CB9411E6C508D8EB945749ED1934654E85B5ADD4D4735DEDC6711003`.
- Registry crosswalk: `build/source-drafts/registry-collision-crosswalk.md`, SHA-256 `BBB1C099DC384489DBB219DAA90F797649FC0C8094E5931A68A0C2997F32280A`; its `minecraft:sea_pickle` row is new-only (`UNMATCHED_ALIAS_UNRESOLVED`, line 648).

Exact local inputs are Ornithe Feather Gen2 build 2 for 1.12.2 and 1.13.2. Ready-marker source-manifest hashes: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Current relevant source hashes match those manifests:

| Source | SHA-256 |
|---|---|
| 1.12.2 `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 `net/minecraft/block/Blocks.java` | `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e` |
| 1.13.2 `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 `net/minecraft/block/SeaPickleBlock.java` | `1b232549de646d46b2f986d78802b0f7208978e103f00bd98fe64e4fe47d76a4` |
| 1.13.2 `net/minecraft/block/PlantBlock.java` | `a1234ccec309bd8805fbe3bad48bce076e17c131e048d2a57d18cdb75f0f286e` |
| 1.13.2 `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` |
| 1.13.2 `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |

## Effective 1.13.2 movement-collision route

`WorldView.getBlockCollisions` gets each candidate cell's state and calls `state.getCollisionShape(this, pos)` (`WorldView.java:135`). `BlockState.getCollisionShape` dispatches to the registered block's collision getter (`BlockState.java:209-210`). The `sea_pickle` registration constructs `SeaPickleBlock` with `Block.Properties.of(Material.WATER_PLANT, MapColor.GREEN).lightLevel(3).sounds(BlockSounds.SLIME)` (`Block.java:1869`; `Blocks.SEA_PICKLE` resolves `getOrThrow("sea_pickle")` at `Blocks.java:1210`). No `.noCollision()` is supplied. `Properties.hasCollision` defaults to true (`Block.java:1901`); `Block` copies that field from properties (`:224-232`).

`SeaPickleBlock` and its `PlantBlock` base do not override `getCollisionShape`; the accepted dispatch is `Block.getCollisionShape → SeaPickleBlock.getShape`. The base getter returns empty only when `hasCollision` is false, otherwise it delegates through `state.getShape` (`Block.java:393-394`); `BlockState.getShape` then dispatches to the registered block (`BlockState.java:205-206`). Thus the base collision gate is enabled and the Sea Pickle shape getter supplies the movement-collision provider.

## Getter inputs and emitted shape

`SeaPickleBlock.getShape` (`SeaPickleBlock.java:87-100`) selects solely on `state.get(PICKLES)`: 1/default → `ONE_SHAPE`; 2 → `TWO_SHAPE`; 3 → `THREE_SHAPE`; 4 → `FOUR_SHAPE`. The legal property range is `PICKLES = IntegerProperty.of("pickles", 1, 4)` (`Properties.java:88`); the block's default state sets `PICKLES=1` and `WATERLOGGED=true` (`SeaPickleBlock.java:31-34`). The getter body does not read `WATERLOGGED`, world, position, neighbor states, fluids, entities, or a block entity. The state definition contains both `PICKLES` and `WATERLOGGED` (`:136-139`), but only the pickle count affects this getter.

The selected shape constants are constructed as follows (`SeaPickleBlock.java:26-29`):

| State | `Block.block` arguments `(minX,minY,minZ,maxX,maxY,maxZ)` |
|---|---|
| `PICKLES=1` | `(6,0,6,10,6,10)` |
| `PICKLES=2` | `(3,0,3,13,6,13)` |
| `PICKLES=3` | `(2,0,2,14,6,14)` |
| `PICKLES=4` | `(2,0,2,14,7,14)` |

`Block.block` divides these source coordinates by 16 before constructing the voxel shape (`Block.java:136-138`). The provider itself is therefore closed over the four legal state values; shape selection is state-only and does not have a hidden world/neighbor/BE branch.

## 1.12.2 endpoint and remaining gates

No `SeaPickleBlock.java` exists in the exact 1.12.2 `net/minecraft/block` tree, and no `sea_pickle`/`SeaPickleBlock` registration token is present in its block registration/constants sources. The exhaustive accepted registry crosswalk independently records `minecraft:sea_pickle` as new-only. Consequently there is no 1.12.2 Sea Pickle provider body or registered key to pair with the 1.13.2 getter; the crosswalk's unresolved-alias status is retained and no historical shape is inferred.

This packet closes only the registered 1.13.2 Sea Pickle collision-eligibility and getter-input/shape route. It does not claim old/new shape parity, a movement trajectory, a cross-release feature alias, source-pair completion, or generic plant-family closure. Placement/support checks (`canBePlacedOn`, `canBePlaced`, and `updateShape`) and all other PlantBlock descendants remain with their bounded owners. Any broader paired query/collector behavior remains outside this slice.
