# Brewing stand collision provider: 1.12.2 → 1.13.2

## Evidence identity and exact key

This bounded source-only comparison uses Ornithe Feather Gen2 build 2 sources. The SHA-256 of `decompiled_minecraft/1.12.2/ornithe-feather.sources.sha256` is `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the 1.13.2 manifest SHA-256 is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Both match the supplied identities and their ready markers. Effective-owner/key join reused from the source-only roster inputs: the exact common key `minecraft:brewing_stand` maps to legacy numeric ID 117 and `BrewingStandBlock` on both sides; old effective `addCollisions` owner is `BrewingStandBlock` (old `getCollisionShape` owner is base `Block`), and the new route is base `Block.getCollisionShape` → `BrewingStandBlock.getShape`. This is exact-key/class evidence only, not a geometry inference.

Source files checked against the exact manifests:

| Release | Relative source | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/BrewingStandBlock.java` | `e875022b9a47ca6336878bcec205459462687bb35d567ee183f7ec2f1924a4fd` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/state/BlockState.java` | `3fb6f06df86c3b89716b5efaed5d97eee670d007d3c4535e47eb0fdd993e92eb` |
| 1.12.2 | `net/minecraft/block/Blocks.java` | `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e` |
| 1.13.2 | `net/minecraft/block/BrewingStandBlock.java` | `0ff465b18dc0377c1a1a123ad083f04d07f77cea8a0bc88a218a67d750f886d3` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |

## Provider and ordinary player-collision route

In 1.12.2, `BrewingStandBlock` defines a base plate `Box(0,0,0, 1,0.125,1)` and a stem `Box(0.4375,0,0.4375, 0.5625,0.875,0.5625)` (lines 36–37). Its `addCollisions` override (70–73) independently calls `addCollision` for the stem and plate; base `Block.addCollision` offsets each box by the block position and appends it only when it intersects the query box (`Block.java:376–383`). The ordinary world collision enumeration calls each block state's `addCollisions` in its candidate-block loop (`World.java:964–1015`); the roster/crosswalk confirms this block-specific effective owner. Thus player block queries receive up to two separately tested world-space obstacle boxes. The overridden `getShape` separately returns only the plate (`BrewingStandBlock.java:76–78`); it is not the full legacy player collision provider. `getFaceShape` returns `FaceShape.UNDEFINED` (`180–182`).

In 1.13.2, `BrewingStandBlock.SHAPE` is the union of `Block.block(1,0,1,15,2,15)` and `Block.block(7,0,7,9,14,9)` (`BrewingStandBlock.java:33`), and its `getShape` returns that static shape (`56–58`). `Block.block` expresses coordinates in sixteenths, so these components occupy respectively the same 1×0.125×1 plate and 0.125×0.875×0.125 stem as the legacy boxes. The stem overlaps the plate over their shared lower 0.125 height. Therefore the occupied union is geometrically equal for all brewing-stand states; neither provider reads the three `HAS_BOTTLE` properties or world/neighbor context.

The new exact-key registration constructs the block with `Properties.of(Material.IRON).strength(0.5F).lightLevel(1)` and does not call `.noCollision()` (`Block.java:1215`); `Properties.hasCollision` defaults to `true` (`1901`), copied to the block by the constructor (`231`). The new state route delegates `getCollisionShape` to the block (`BlockState.java:209–210`), and base `Block.getCollisionShape` then returns `state.getShape(world,pos)` while `hasCollision` is true (`Block.java:393–395`). The block does not override `getCollisionShape`, so the normal player collision shape is the full brewing-stand union, unlike legacy's pair of separately appended boxes. Its `getFaceShape` also returns `FaceShape.UNDEFINED` (`BrewingStandBlock.java:128–130`): no change in that predicate was found.

## Difference and limits

Difference: the provider interface and collision representation changed from a state-agnostic, intersection-filtered pair of legacy `Box` obstacles emitted by `addCollisions` to one static `VoxelShape` returned through the new `hasCollision`-gated collision-shape route. The union dimensions and all state/context-dependent geometry match; all three bottle-state bits are irrelevant to collision shape. The ordinary route remains collidable because the registered block retains `hasCollision=true`.

The provider comparison establishes occupancy equivalence, not tick-level collision-resolution parity: the legacy route emits overlapping component boxes separately, while the later voxel-shape consumer decides how its union is decomposed and traversed. Any resulting player response requires a separate exact collision-consumer comparison. No registry-key or block-class migration uncertainty remains for this exact common key; this report does not infer an intermediate patch boundary, close that downstream dependency, or claim pair coverage/freeze.

Terminal result: bounded brewing-stand provider comparison completed; downstream collision-consumer parity and pair-wide closure remain open.
