# Frozen blind source roster
Pair: Minecraft 1.11.2 to 1.12.2
Mappings: Ornithe Feather on both sides
Slice: on-foot LocalPlayer ordinary, non-fall-flying water current path, including tick ordering, applyLiquidDrag bounds/load gate and per-cell current accumulation/normalization/velocity write; exclude deep AI, falling/fall-flying, eye/submerged predicates except for overlap with this path.

The following paired source files are frozen for review, with exact version-root readiness/provenance metadata:

- `decompiled_minecraft/<version>/ornithe-feather.ready.json`
- `decompiled_minecraft/<version>/ornithe-feather.provenance.json`
- `decompiled_minecraft/<version>/ornithe-feather.sources.sha256`
- `decompiled_minecraft/<version>/ornithe-feather.verification.json`
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`
- `net/minecraft/entity/living/LivingEntity.java`
- `net/minecraft/entity/Entity.java`
- `net/minecraft/world/World.java`
- `net/minecraft/block/LiquidBlock.java`
- `net/minecraft/block/FlowingLiquidBlock.java`
- `net/minecraft/block/state/BlockState.java`
- `net/minecraft/block/state/AbstractBlockState.java`
- `net/minecraft/block/Block.java`
- `net/minecraft/block/material/Material.java`
- `net/minecraft/block/material/LiquidMaterial.java`
- `net/minecraft/util/math/Box.java`
- `net/minecraft/util/math/BlockPos.java`
- `net/minecraft/util/math/Vec3d.java`
- `net/minecraft/world/WorldView.java`
- `net/minecraft/world/WorldRegion.java`
- `net/minecraft/world/chunk/WorldChunk.java`

No product implementations, Git history, wiki inventories, online mappings, source generation, build, tests, runtime or network evidence are in this roster.
