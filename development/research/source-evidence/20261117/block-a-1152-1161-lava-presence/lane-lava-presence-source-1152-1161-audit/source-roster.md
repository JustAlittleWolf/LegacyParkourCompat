# Blind source roster freeze: lava presence, 1.15.2 to 1.16.1

Pair: Minecraft 1.15.2 to 1.16.1, Mojmap on both sides.
Scope: player-reachable lava-contact producer/cache update timing and cached lava-depth consumers, including LocalPlayer tick reachability, held jump, and lava travel branch. This freezes the candidate evidence roster before author comparison access. It does not claim full-pair inventory or intermediate patch boundaries.

Per version, inspect the exact source files below; source hashes and publication/input identities will be verified and recorded before comparing the author artifact.

- `net/minecraft/client/player/LocalPlayer.java`
- `net/minecraft/world/entity/Entity.java`
- `net/minecraft/world/entity/LivingEntity.java`
- `net/minecraft/world/entity/player/Player.java`
- `net/minecraft/world/level/BlockGetter.java`
- `net/minecraft/world/level/LevelReader.java`
- `net/minecraft/world/level/block/Blocks.java`
- `net/minecraft/world/level/block/LiquidBlock.java`
- `net/minecraft/world/level/block/state/BlockState.java`
- `net/minecraft/world/level/material/Fluid.java`
- `net/minecraft/world/level/material/FluidState.java`
- `net/minecraft/tags/FluidTags.java`
- `net/minecraft/world/level/material/Fluids.java`
- `net/minecraft/world/level/material/FlowingFluid.java`
- `net/minecraft/world/level/material/LavaFluid.java`
- `net/minecraft/world/phys/AABB.java`
- 1.15.2 additional interface implementation: `net/minecraft/world/level/material/FluidStateImpl.java`

Primary source roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.15.2/mojmap/` and `.../1.16.1/mojmap/`. Canonical preparation is determined from the version-root ready/provenance/manifests, not inferred from directory names alone.