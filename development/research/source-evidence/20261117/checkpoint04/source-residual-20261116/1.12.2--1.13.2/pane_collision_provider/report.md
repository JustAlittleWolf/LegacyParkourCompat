# Bounded glass-pane and iron-bars collision provider comparison: 1.12.2 → 1.13.2

## Exact source identities

Compared read-only Ornithe Feather Gen2 build 2 sources. Ready markers identify 1.12.2 source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and 1.13.2 source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

Each listed file was rehashed from canonical source and its path/hash entry was checked against that release's `ornithe-feather.sources.sha256` manifest:

| Release | Source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/PaneBlock.java` | `83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/util/math/Direction.java` | `116a61f67e93f895d29c71128b392fbde5f1ca85f1b51d9f7fce55a0dd5960bf` |
| 1.13.2 | `net/minecraft/block/PaneBlock.java` | `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c` |
| 1.13.2 | `net/minecraft/block/IronBarsBlock.java` | `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0` |
| 1.13.2 | `net/minecraft/block/GlassPaneBlock.java` | `7abf507b21b920c76e63e34293e6f40bbdefc95df396eb4847406a6d41df96a0` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/util/math/Direction.java` | `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082` |

## Registered keys and provider routes

The exact common keys are `minecraft:iron_bars` and `minecraft:glass_pane`. The 1.12.2 registrations construct both as `PaneBlock` (`Block.java:979–981`). The 1.13.2 registrations keep both keys and instantiate `IronBarsBlock` and `GlassPaneBlock` respectively (`Block.java:1183–1184`); `GlassPaneBlock` extends `IronBarsBlock` and adds no collision override (`GlassPaneBlock.java:9–17`). Both target constructors use `IronBarsBlock`'s `super(1,1,16,16,16,properties)` shape parameters (`IronBarsBlock.java:18–21`).

Old `PaneBlock` defines four Boolean connection properties, NORTH/EAST/SOUTH/WEST, and a 16-entry box table. In `addCollisions` (`PaneBlock.java:54–75`) it appends the central box, then one center-plus-arm box per enabled direction. Ordinary block collection calls the state collision method with `forceShape=false` (`World.java:1001`), so this route first invokes `resolveVirtualProperties` (`PaneBlock.java:109–114`) and computes the four bits from current adjacent states and their directional `FaceShape` values. `forceShape=true` would use the supplied stored bits, but it is not the ordinary `World.getCollisions` call shown here.

In 1.13.2, `IronBarsBlock` defines the same four directional booleans plus `WATERLOGGED` (`IronBarsBlock.java:110–113`). `PaneBlock.getCollisionShape` selects `shapes[getShapeIndex(state)]` using only the state's four connection bits (`PaneBlock.java:82–84,90–110`); it does not read world, position, neighboring states or collision context. The corresponding `BlockState.getCollisionShape` dispatches to this block method (`state/BlockState.java:209–210`), and the world collision iteration uses that state route (`WorldView.java:112–137`). The extra WATERLOGGED bit is not used by the shape index or provider. The target `IronBarsBlock.getPlacementState` and `updateShape` set connection bits from adjacent states' face shapes (`IronBarsBlock.java:24–53`); they also derive waterlogged state from local fluid state.

## Bounded geometry result

For a fixed N/E/S/W connection-bit vector, old and target provider geometry have the same occupied union for both registered keys:

- The old central box spans x/z `[7/16,9/16]` and y `[0,1]`. Each old enabled-direction box is that center joined to the corresponding full-height 2/16-wide arm, and `addCollisions` emits the center plus the selected single-arm boxes.
- The target constructor's `makeShapes` creates the same center and directional arms, unions the selected arms according to the same `1 << Direction.getIdHorizontal()` mask, then `getCollisionShape` selects that union. The old and target `Direction` declarations use the same horizontal IDs: SOUTH=0, WEST=1, NORTH=2, EAST=3.
- The union of the old appended center/arm boxes equals the target union for each of the 16 bit masks. The target's collision heights/widths equal its shape parameters here: `h=i=j=16`; glass and iron pane use the same shape arguments. Waterlogged state does not select a different collision box.

This establishes **conditional geometry equivalence when the effective connection bits match**. It does not establish that the old live-neighbor recomputation and target stored-state bits match for every world snapshot. The old ordinary collision query recomputes the bits from adjacent blocks during collision collection; the target shape getter uses the stored bits, whose update/placement routes call a neighbor predicate. Full adjacent block FaceShape/exception evolution, stale or externally inserted states, and migration applicability are not closed by this provider slice. Therefore this report makes no overall player-movement no-difference claim and does not freeze the pair.

No source generation, build, test, runtime, wiki, product, backlog, history, Git, or shared-source edits were performed.
