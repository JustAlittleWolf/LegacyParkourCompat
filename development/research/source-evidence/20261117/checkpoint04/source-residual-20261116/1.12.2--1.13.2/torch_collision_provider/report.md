# Torch collision provider: `minecraft:torch` and common redstone torch key

## Exact evidence identities

The exact Ornithe Feather Gen2 build.2 source sets are the assigned 1.12.2 manifest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and 1.13.2 manifest `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Each local `ornithe-feather.ready.json` names the corresponding manifest digest; recomputing SHA-256 over `ornithe-feather.sources.sha256` matches both ready records. Mapping family is Ornithe Feather on both sides.

Source-only registry inputs: `build/source-drafts/registry-collision-1122.md` SHA-256 `29278a0c2b0899866f444296bae8575bc6e039c3bbe419d0c51051077c1624a2`; `build/source-drafts/registry-collision-1132.md` SHA-256 `d9bae34a1b4f02a5ab00f7ce7909f420663625c145c328fc5f332a9957a2b139`; exact-key crosswalk `development/research/source-evidence/20261104/registry-collision-crosswalk.md` SHA-256 `bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a`. The crosswalk's exact common-key rows assign old `minecraft:torch` to `TorchBlock` with effective `Block.addCollisions` and `TorchBlock.getCollisionShape`, and new `TorchBlock` via `Block.getCollisionShape → TorchBlock.getShape` (row at line 104). Its common `minecraft:redstone_torch` row has the same old and new provider family (line 130). The key join is exact equality; it does not establish aliases.

The dispatched `provider_override_roster/provider-override-roster.md` was not present in this checkout, so no independent roster file hash or standalone owner census is asserted here. Direct ownership is established for this bounded slice from the exact class bodies and the source-only crosswalk above.

SHA-256 of consulted Java files (all match the respective source manifests):

| Release | Relative source | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/TorchBlock.java` | `78aeee1e8c03aa0544f6d7e9fbba2555608ca9c721d1541d0d01ebf2aa9ea009` |
| 1.12.2 | `net/minecraft/block/RedstoneTorchBlock.java` | `059236531a4036fc5ab952a0d14891a432fdf50e14a3c056ce5fdffcb19a4e33` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/state/BlockState.java` | `3fb6f06df86c3b89716b5efaed5d97eee670d007d3c4535e47eb0fdd993e92eb` |
| 1.12.2 | `net/minecraft/block/state/StateDefinition.java` | `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.13.2 | `net/minecraft/block/TorchBlock.java` | `da11308e51f57ce95764f2ee645dde8d15607914a3a68bac9c0635ab3d1531e0` |
| 1.13.2 | `net/minecraft/block/RedstoneTorchBlock.java` | `d6be1c4a35b5112012e4b583310836bac3cac5e735e2671bb33c0cf9a8a7710f` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |

## Bounded comparison

### Common `minecraft:torch` key

- **State domain and outline provider.** In 1.12.2 `TorchBlock` defines `FACING` with every direction except `DOWN` (lines 21–25), defaults it to `UP` (line 34), and declares it as its state definition (lines 250–253). `getShape` switches over facing to choose the floor or one of four wall-oriented boxes (lines 26–53). In 1.13.2 plain `TorchBlock` has no state-definition override and returns only `FLOOR_SHAPE` from `getShape` without inspecting state (lines 16–25). Thus the declared state domains and outline shapes differ. The common key's registered 1.13.2 class is `TorchBlock`; wall torch is a distinct key and class and is not included.
- **Effective player collision shape.** In 1.12.2 `TorchBlock.getCollisionShape` returns `EMPTY_BLOCK_SHAPE` for every state (lines 56–59). The vanilla player route collects world collisions through `World.getCollisions` and the state adapter calls the block's `addCollisions` (world lines 964–1002; state delegation `StateDefinition.java:375–376`). Base `Block.addCollisions` passes the effective collision shape to `addCollision` (Block lines 372–382), so the torch contributes no obstacle regardless of its nonempty outline shape.
- In 1.13.2, registered `minecraft:torch` uses `.noCollision()` (Block registration line 986). `Block.Properties.hasCollision` defaults true and `.noCollision()` sets it false (lines 1901, 1943–1945); the `Block` constructor copies that registration property (line 231). Inherited `Block.getCollisionShape` returns empty when `hasCollision` is false (lines 393–394). `BlockState.getCollisionShape` dispatches there (BlockState lines 209–210), and the player collision collector obtains that shape (WorldView lines 133–136). Therefore the changed `getShape` outline does not become player collision: the registered shape is empty in both releases.

### Common `minecraft:redstone_torch` key, separately scoped

The old registry crosswalk assigns this exact common key to `RedstoneTorchBlock` in both releases' provider family. In 1.12.2 it subclasses `TorchBlock`, so it inherits the same five legal `FACING` values, orientation-selected outline boxes, empty `getCollisionShape`, and `FaceShape.UNDEFINED`; the class constructor's `lit` field belongs to this registered old block instance. The separate old key `minecraft:unlit_redstone_torch` is old-only in the crosswalk and is not treated as a common-key alias.

In 1.13.2 the common key registers `RedstoneTorchBlock` with `.noCollision()` (Block lines 1058–1061). That class inherits the floor-only `TorchBlock.getShape`; its state definition adds `LIT` (RedstoneTorchBlock lines 133–134), rather than using the old facing property. As with plain torch, inherited `Block.getCollisionShape` is empty for every registered redstone torch state. The state domain and outline provider therefore differ, while effective player collision stays empty.

### FaceShape and route summary

Both `TorchBlock` implementations override `getFaceShape` to return `FaceShape.UNDEFINED` for every direction (1.12.2 lines 255–258; 1.13.2 lines 66–69). The torch provider's face shape is unchanged. Old ordinary collision collection routes through `Block.addCollisions → BlockState.getCollisionShape → TorchBlock.getCollisionShape`; new ordinary collection routes through `BlockState.getCollisionShape → Block.getCollisionShape → hasCollision gate → state.getShape → TorchBlock.getShape`. The registration-level gate and old direct override yield the same empty player collision result for both joined keys.

## Disposition

No source-confirmed player movement collision delta is established for the exact common torch key or the separately scoped common redstone torch key: both yield empty collision shapes for every legal state. Their outline shapes and state domains change, but the registered blocks do not feed those shapes to ordinary player collision. The old-only `minecraft:unlit_redstone_torch` key is recorded as unmatched, not inferred to alias the common new key. No wall-torch key/class is compared; no provider for it is inferred from the common-key join. This report closes only the assigned provider slice and makes no full-pair coverage or freeze claim.



