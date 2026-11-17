# End Rod collision provider: 1.12.2 → 1.13.2

## Bounded result

**No source difference in End Rod state-dependent collision geometry or its direct collision-provider gate is found.** For all six `facing` values, both releases select the same axis-aligned 4/16-thick rod box. Old `Block.addCollisions` dispatch reaches `EndRodBlock.getShape` through `Block.getCollisionShape`; new `BlockState.getCollisionShape` reaches base `Block.getCollisionShape`, which gates `state.getShape` on the registered block's `hasCollision` flag, then dispatches to `EndRodBlock.getShape`. The 1.13.2 `end_rod` registration does not call `.noCollision()`, and `Properties.hasCollision` defaults to `true`, so the added gate preserves this provider's shape.

Both `EndRodBlock.getFaceShape` overrides unconditionally return `FaceShape.UNDEFINED` for every queried face. This is also unchanged. Results describe the provider and state only; they do not close the separate 1.12.2/1.13.2 movement collision-consumer math.

## Registry and state domain

The permitted accepted exact-key crosswalk row is `minecraft:end_rod` (`EXACT_KEY_ONLY`, legacy numeric ID 198), with `EndRodBlock` on both sides and new route `Block.getCollisionShape → EndRodBlock.getShape`. Crosswalk: `development/research/source-evidence/20261104/registry-collision-crosswalk.md`, SHA-256 `bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a`.

- **1.12.2:** `EndRodBlock` extends `FacingBlock`; `FACING` is `DirectionProperty.of("facing")`, whose no-filter overload includes all `Direction.values()`. Its state definition has only `FACING`, and the default is `Direction.UP`. Legacy metadata decoding uses `Direction.byId(metadata)` and encoding uses `Direction.getId()` (`EndRodBlock.java:25,108–118`; `FacingBlock.java:8`; `DirectionProperty.java:15–20`).
- **1.13.2:** `EndRodBlock` extends `FacingBlock`; its state definition adds only `FACING`, and the default is `Direction.UP`. `FacingBlock.FACING` aliases `Properties.FACING`, whose explicit values are NORTH, EAST, SOUTH, WEST, UP and DOWN (`EndRodBlock.java:23,89–91`; `FacingBlock.java:9`; `Properties.java:53–56`).
- Thus both domains contain the same six enum directions and no other End Rod state property. The registry key is the same exact `end_rod` key. No unresolved End Rod key/state alias is needed for this provider comparison; this bounded review does not audit flattened-world datafix behavior.

## Shape and dispatch comparison

- **Shape:** In 1.12.2 `EndRodBlock.java:17–19,40–51`, the static `Box` constants are `SHAPE_Y=(0.375,0,0.375)–(0.625,1,0.625)`, `SHAPE_Z=(0.375,0.375,0)–(0.625,0.625,1)`, and `SHAPE_X=(0,0.375,0.375)–(1,0.625,0.625)`. `getShape` switches only on `FACING.getAxis()`. In 1.13.2 `EndRodBlock.java:18–20,37–48`, the respective `VoxelShape` bounds are `(6,0,6)–(10,16,10)`, `(6,6,0)–(10,10,16)`, and `(0,6,6)–(16,10,10)` in 0–16 block units; the switch is the same X/default, Z, Y mapping. The normalized geometry is identical for each facing. World and position are unused in both provider bodies.
- **Old collision path:** 1.12.2 `Block.addCollisions` calls `state.getCollisionShape(world,pos)` and passes that box to `addCollision`; the latter translates it and appends it only when the query box intersects (`Block.java:372–389`). The registered old state forwards `addCollisions`, `getCollisionShape`, `getShape`, and `getFaceShape` to its block (`StateDefinition.java:370–381,415–416`). The exact-key crosswalk records the effective End Rod route as `Block.addCollisions → Block.getCollisionShape → EndRodBlock.getShape`; `EndRodBlock` supplies no `addCollisions` or `getCollisionShape` override.
- **New collision path:** 1.13.2 `BlockState.getCollisionShape` delegates to the registered block (`BlockState.java:209–210`). Base `Block.getCollisionShape` returns `state.getShape(world,pos)` if `hasCollision`, otherwise an empty shape (`Block.java:393–395`); state `getShape` delegates to the block, so End Rod's override is reached. `Properties.hasCollision` initializes to `true` (`Block.java:1901`) and the exact `end_rod` registration at `Block.java:1584` supplies no `.noCollision()`. Construction copies the flag into the block (`Block.java:231`).
- **Face shape:** Both release methods are unconditional `return FaceShape.UNDEFINED` (`EndRodBlock.java:127` in 1.12.2; `EndRodBlock.java:99` in 1.13.2), so every face has the same classification. State-level face-shape dispatch delegates to the block (`BlockState.java:285–286` in 1.13.2); in 1.12.2 the block state route likewise resolves this override. This report makes no broader claim about neighboring-block predicates.

## Evidence identity and consulted source hashes

Both exact source manifests and ready markers identify Ornithe Feather Gen2 build 2; their digests were recomputed and match the ready-marker values: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

| Release | Relative source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/EndRodBlock.java` | `ad3f9d6abf8d990c4e2d7dc86389a066c10683da814b2f9562ceee6944c0cbd0` |
| 1.12.2 | `net/minecraft/block/FacingBlock.java` | `32f747c0ca9145efa9f36e99a7673f62f4c67a2c308ac33cc0e3ca5f9666cf22` |
| 1.12.2 | `net/minecraft/block/state/property/DirectionProperty.java` | `6ce2f536c3ec4792105b77ddaff2b81584eb74231d4721dbfdf0cbc7fe77e943` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/Blocks.java` | `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e` |
| 1.12.2 | `net/minecraft/block/state/BlockState.java` | `3fb6f06df86c3b89716b5efaed5d97eee670d007d3c4535e47eb0fdd993e92eb` |
| 1.12.2 | `net/minecraft/block/state/StateDefinition.java` | `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37` |
| 1.13.2 | `net/minecraft/block/EndRodBlock.java` | `e7ba3191e3303d94987648d57384bab787226c5a8914cc5f72e92fc2ffb8545d` |
| 1.13.2 | `net/minecraft/block/FacingBlock.java` | `2e90aa010630ae9786a631f56cdee4b454a5d01359f15ac29f52a15ad3c9eb1e` |
| 1.13.2 | `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` |
| 1.13.2 | `net/minecraft/state/property/DirectionProperty.java` | `3d36632d97141b65d1c45fe11d632d7cf6c5ef0c7c08cf7b57635fb87e8ac188` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |


