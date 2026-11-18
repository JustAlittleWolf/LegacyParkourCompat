# Independent blind audit: SnowLayerBlock, 1.12.2 → 1.13.2

Status: COMPLETED — bounded snow collision-input audit; this is not a full-pair freeze.

## Inputs verified

- Exact sources: `decompiled_minecraft/1.12.2/ornithe-feather` and `decompiled_minecraft/1.13.2/ornithe-feather`, both Feather gen2 build.2 per ready records.
- 1.12.2 ready record: version/mapping match; source manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` verified.
- 1.13.2 ready record: version/mapping match; source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` verified.
- SnowLayerBlock source hashes match each manifest entry: 1.12.2 `6ed1d83d9230cd3c1434b4ef5055963048763eea4dbdeaa6a58dd3b8591b915b`; 1.13.2 `12b2d50064fd306c09007bc9a3629cc14a540123ce37e3b53a7f6615c9915df2`. Hashes for the compared Entity/World-side sources were also checked against their manifests.

## Finding and verdict

**No snow-specific collision geometry/provider difference for fixed vanilla layers 2–8.** The local collision shape is the same full-footprint axis-aligned prism, from block-local y=0 through `(layers−1)/8`, inclusive as a geometric boundary. For layer values 2 through 8 those heights are 1/8, 2/8, …, 7/8. These are exactly representable binary fractions: 1.12.2 calculates `(layers−1) * 0.125F` and widens that exact float to the Box coordinate; 1.13.2 selects the VoxelShape entries made from pixel heights 2, 4, …, 14 divided by 16. The horizontal bounds are 0..1 in both.

The providers therefore agree on both geometry and ordinary collision input. In 1.12.2, `SnowLayerBlock.getCollisionShape` (lines 64–71) starts from `getShape`'s full-width/full-depth box and replaces only maxY. In 1.13.2, `getCollisionShape` (lines 78–80) selects `SHAPES[layers−1]`, whose entries (lines 31–40) use the same full footprint and the corresponding 2..14 pixel heights. The legacy state decoder masks metadata with `& 7`, adds 1, and has representable layer values 1..8 (`1.12.2 SnowLayerBlock.java:24, 146–148`); 1.13.2's shared `Properties.LAYERS` is `IntegerProperty.of("layers", 1, 8)` (`Properties.java:82`). Thus every requested old metadata value 1..7 maps to the same layer number 2..8 as the new state property. Metadata 0/default is layer 1 in 1.12.2 (`SnowLayerBlock.java:37–40, 146–148`); the 1.13.2 property minimum is 1 and its shape slot 0 is empty. The default layer-1 collision is degenerate/empty in both representations, outside the requested 2..8 proof.

## Reachable player path and shared engine boundary

`LocalClientPlayerEntity.move` delegates to `super.move` in both releases (1.12.2 lines 845–850; 1.13.2 lines 870–875). The shared Entity movement path gathers collision input and resolves normal movement, grounded sneaking edge reduction, and step-up attempts: 1.12.2 `Entity.move` lines 462–462, 522–560, 567–605, 613–682; 1.13.2 lines 473–473, 533–570, 578–605, 612–662. The snow shape is supplied through the block state collision-shape provider: old `Block.addCollision` offsets the shape and filters with `Box.intersects` (`1.12.2 Block.java:368–380`); new `WorldView.getBlockCollisions` gets each state collision shape, offsets it, and filters overlaps (`1.13.2 WorldView.java:112–150`; `BlockState.getCollisionShape` delegates to the block).

The collision *engine* changed around this unchanged snow input. 1.12.2 axis resolution uses Box intersection and the explicit strict cross-axis tests (`Box.java:185–233`); 1.13.2 uses `VoxelShape.calculateMaxDistance` (`VoxelShape.java:184–237`) and collision-query shapes with 1e-7 offsets (`WorldView.java:154–177`). These are shared engine changes, not SnowLayerBlock-specific provider changes. At exact touching boundaries, both geometry/filtering treat zero-volume contact as non-overlap for collision gathering; old Box intersection uses strict `<`/`>` and new voxel overlap filters nonempty AND overlap. The 1.13.2 offset solver's epsilon and its subsequent behavior for arbitrarily thin positive overlaps are not proven equivalent here. Grounded sneak and step-up branches reach the same snow collision provider, but parity of their full collision-engine outcomes is outside this bounded snow-input verdict.

## Limits

This result proves the fixed-state snow collision geometry and provider mapping for layers 2–8, plus the reachable local-player movement call path. It does not establish full 1.12.2→1.13.2 player parity, equality of the shared collision engine, intermediate patch introduction, layer-1 behavior beyond the stated default-shape observation, navigation/pathing predicates, placement/survival, or runtime parity.

## Exact source references

- 1.12.2 Feather: `net/minecraft/block/SnowLayerBlock.java` (`LAYERS`, `SHAPES`, `getShape`, `getCollisionShape`, metadata decoder/encoder); `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` (`move`); `net/minecraft/entity/Entity.java` (`move`); `net/minecraft/block/Block.java` (`addCollision`); `net/minecraft/util/math/Box.java` (`intersectX/Y/Z`, `intersects`).
- 1.13.2 Feather: `net/minecraft/block/SnowLayerBlock.java` (`LAYERS`, `SHAPES`, `getCollisionShape`); `net/minecraft/state/property/Properties.java` (`LAYERS`); `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` (`move`); `net/minecraft/entity/Entity.java` (`move`); `net/minecraft/world/WorldView.java` (`getBlockCollisions`, `getCollisions`); `net/minecraft/block/shape/VoxelShape.java` (`calculateMaxDistance`).