# Snow layer, ice, and snow-block provider facts

## Source identity

- Older: Minecraft 1.8.9, `ornithe-feather`; canonical source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; ready marker SHA-256 `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`.
- Current: Minecraft 26.2, `unobfuscated`; canonical source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; ready marker SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`.
- Selected Java file hashes and exact paths are in `source-hashes.json`; each selected file matched its canonical manifest entry.

## ID and state mapping

1.8.9 `Block.java:1009–1011` registers ID 78 as `snow_layer` (`SnowLayerBlock`, with legacy state key `snow`), ID 79 as `ice` (`IceBlock`), and ID 80 as `snow` (`SnowBlock`). `SnowLayerBlock.getStateFromMetadata` masks the low three bits and adds one, so metadata 0–7 maps to layers 1–8; `getMetadataFromState` returns layers minus one (`SnowLayerBlock.java:134–145`).

The 26.2 `BlockStateData` rows 1248–1255 map the old `minecraft:snow` states with `layers` 1–8 to `minecraft:snow_layer` with the same layer values. Row 1264 maps `ice` to `ice`; row 1280 maps `snow_block` to current `snow` (`BlockStateData.java:3812–3824`). These mappings distinguish old ID 78 snow layers from old ID 80 snow block despite their old registry-key spellings.

## Historical and current providers

- **ID 78, snow layer.** Old movement collision returns an AABB whose Y maximum is `(layers - 1) * 0.125`; its X/Z extents are the block's full width (`SnowLayerBlock.java:40–45`). This is a zero-height box at layer 1. Old ray tracing calls `updateShape(world,pos)` before using the mutable bounds; the snow-layer override reads the cell's layer state and sets the bound maximum Y to `layers / 8.0` (`Block.java:456–461`; `SnowLayerBlock.java:64–71`). Thus movement collision and the ray shape intentionally differ by one eighth of a block.
- The collision query does not itself cause the state-specific bounds write: `Block.addCollisions` calls `getCollisionShape`, while old `World` collision scans call `addCollisions` (`Block.java:339–350`; `World.java:921,989`). The snow-layer `getCollisionShape` computes its Y maximum from the requested state and does not call `updateShape`; its only state-specific mutable-bound writer is the ray-time `updateShape`. Its X/Z bounds and `minY` remain fixed at 0/1 and 0, and the collision provider computes `maxY` rather than reading the mutable `maxY` field. Therefore a prior layer's ray call does not change this block's movement-collision box for another layer.
- **ID 79, ice; ID 80, snow block.** Both old classes inherit the full-cube collision box from `Block`; neither overrides `getCollisionShape` or `updateShape`. `IceBlock` is a `TransparentBlock` subclass, whose transparency override affects rendering only. The inherited ray trace's `updateShape` is the base no-op, so these providers have no state-specific bounds write (`Block.java:339–350,456–461,596`; `IceBlock.java`; `SnowBlock.java`; `TransparentBlock.java`).
- **26.2 snow layer.** `SnowLayerBlock.getShape` selects `SHAPES[layers]` and `getCollisionShape` selects `SHAPES[layers - 1]` (`SnowLayerBlock.java:47–54`). The array generator uses heights `index * 2` sixteenths (`Block.java:164,177–184`), so the current outline height is `layers/8` and current collision height `(layers-1)/8`, matching the two historical bounds formulas for layers 1–8. The `BlockStateData` mappings above preserve the layer property.
- **26.2 ice and snow block.** `Blocks.ICE` uses `IceBlock` and `noOcclusion`, but does not disable collision; `Blocks.SNOW_BLOCK` uses default block properties (`Blocks.java:1970–1985`). `HalfTransparentBlock` has no shape override, `BlockBehaviour.getShape` defaults to a full block, and `getCollisionShape` returns the state's shape when `hasCollision` is true (`HalfTransparentBlock.java`; `BlockBehaviour.java:323–328`). The property default is `hasCollision = true`; `.noCollision()` alone clears it (`BlockBehaviour.java:985,1094–1100`). These current providers therefore retain full-cube collision for ice and snow block.

## Bounded conclusion

For these three IDs, the source-defined historical block-local providers can be reconstructed from the old block identity and (for ID 78) its layer state. Snow-layer movement collision does not depend on stale state-specific `maxY` bounds, while ray tracing writes and uses the layer-specific outline immediately. Ice and snow block use the default full-cube providers. This closes only these local provider inputs and shapes; it does not establish DDA visitation order, first-hit parity, complete old-state reconstruction, or end-to-end ray-trace equivalence.
