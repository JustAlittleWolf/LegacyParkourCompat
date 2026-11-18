# Blind source audit roster freeze

Pair: Minecraft 1.8.5 -> 1.8.8; canonical source: exact client jars decompiled in the primary checkout with Ornithe Feather Gen2 build.2 (mapping `ornithe-feather`). These are separate exact versions. No inference is made about 1.8.6 or 1.8.7.

Source provenance: both generated `.decompile-provenance.json` files report `lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess` on Eclipse Adoptium 25.0.3+9, Windows 11 amd64. 1.8.5 Minecraft metadata package SHA-256 `4fe7e25babf0c16244675812332c1403f0a30f8dac03b9e7c9cfd40ba39c7597`; client jar SHA-256 `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d`; Feather `1.8.5+build.2` mergedv2 artifact SHA-256 `18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d`. 1.8.8 metadata package SHA-256 `7f002c1dca74cc51ce995e7fdb465ab4984c940e77a1b1c4f2b09bed8702e558`; client jar SHA-256 `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8`; Feather `1.8.8+build.2` mergedv2 artifact SHA-256 `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f`.

## Frozen owned slices

1. Dry ordinary oak fence horizontal connection/collision cue: `FenceBlock.addCollisions`, `updateShape`, `shouldConnectTo`, `resolveVirtualProperties`; trace neighbor block-state reads, exact neighbor offsets, material/cube/barrier/gate/fence predicates, collision vertical extents, and the block collision dispatch to ensure queried source is player-reachable. Fence registration/default material context is read only if needed to prove ordinary oak case. Exclude rendering-only predicate and render shape.
2. Local-player water current and gates: `LocalClientPlayerEntity.tick` loaded-position gate and superclass tick route; `ClientPlayerEntity`/`PlayerEntity`/`LivingEntity` player tick/travel movement call path; `Entity.tick`, `baseTick`, `checkWaterCollisions`, movement/velocity writes; `World.applyLiquidDrag` traversal and block dispatch; `LiquidBlock.getFlow`, flow-height/face-solid dependencies, and `applyMaterialDrag`; loaded chunk/block-state accessors reached from this path. Compare order, bounding box construction and ranges, liquid/material predicates, velocity accumulation/normalization, face cue and operation gates. Exclude liquid tick simulation, non-player movement, rendering, and production/health effects.

## Frozen file roster (relative to each `decompiled_minecraft/<version>/ornithe-feather/`)

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`
- `net/minecraft/client/entity/living/player/ClientPlayerEntity.java`
- `net/minecraft/entity/living/player/PlayerEntity.java`
- `net/minecraft/entity/living/LivingEntity.java`
- `net/minecraft/entity/Entity.java`
- `net/minecraft/block/FenceBlock.java`
- `net/minecraft/block/Block.java`
- `net/minecraft/block/LiquidBlock.java`
- `net/minecraft/world/World.java`
- `net/minecraft/world/WorldView.java`

For both exact sides, provenance source-map file hashes are recorded with the audit result. Audit is bounded to the two slices above. Evidence outside these routes is not being represented as full-pair movement inventory or freeze.
## Post-freeze dependency closure

Source inspection required two concrete fence dependencies to verify ordinary oak material semantics and registration: `net/minecraft/block/material/Material.java` and `net/minecraft/block/Blocks.java` for both exact versions. These are appended as source-only dependencies; this addendum does not expand the owned behavior slices. Both files' hashes match their provenance source maps and are identical across the endpoints.