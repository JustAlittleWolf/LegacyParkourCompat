# Ordinary-player fluid submersion boundary

## Scope and evidence identity

Bounded predicate comparison: 1.13.2 → 1.14, with 1.14.4 used as a later cross-mapping confirmation. The first changed stable source inspected directly is 1.14; this is not inferred from the 1.14.4 endpoint. Sources were read-only under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

- 1.13.2, Ornithe Feather: source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; mappings `feather-gen2-1.13.2+build.2-mergedv2.jar` and `feather-gen2-1.13.2+build.2.tiny`; source provenance `1.13.2/ornithe-feather.provenance.json`.
- 1.14, Ornithe Feather: source-manifest SHA-256 `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`; mappings `feather-gen2-1.14+build.2-mergedv2.jar` and `feather-gen2-1.14+build.2.tiny`; source provenance `1.14/ornithe-feather.provenance.json`.
- 1.14.4, Mojmap: source-manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; mapping option `feather,mojmap`, with `feather-gen2-1.14.4+build.2-mergedv2.jar` and `feather-gen2-1.14.4+build.2.tiny`; source provenance `1.14.4/mojmap.provenance.json`.

Relevant source-file SHA-256 values:

- 1.13.2 `ornithe-feather/net/minecraft/entity/Entity.java`: `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- 1.13.2 `ornithe-feather/net/minecraft/fluid/FlowingFluid.java`: `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253`.
- 1.13.2 `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`: `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.
- 1.14 `ornithe-feather/net/minecraft/entity/Entity.java`: `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9`.
- 1.14 `ornithe-feather/net/minecraft/fluid/FlowingFluid.java`: `a92395f2e8219aafb7c80444dd14ba5427577a7f1d2027c780e822eb8793a15`.
- 1.14 `ornithe-feather/net/minecraft/fluid/WaterFluid.java`: `ed5a28c7f4370ef8f7217dae482fbb1e28296f08c1d70944a1a690bf5f6bb13f`.
- 1.14 `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`: `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7`.
- 1.14 `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f`.
- 1.14.4 `mojmap/net/minecraft/world/entity/Entity.java`: `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`.
- 1.14.4 `mojmap/net/minecraft/world/level/material/FlowingFluid.java`: `e8a395552e53f33bcb5648325615d31bc1796ce2a8aeb501bdda3127622dccdf`.

## Compared operation and gates

1.13.2 `Entity.isSubmergedIn(Tag<Fluid>)` first rejects an entity mounted on a boat, then samples `d = y + getEyeHeight()`, constructs `BlockPos(x, d, z)`, reads that cell's `FluidState`, and returns `fluidState.is(tag) && d < blockPos.y + (fluidState.getHeight() + 0.11111111F)` (`Entity.java:1042–1051`). The height is context-free: `FluidState.getHeight()` delegates to `Fluid.getHeight(state)`, and `FlowingFluid.getHeight` returns `state.getLevel() / 9.0F` (`FluidState.java:29–30`; `FlowingFluid.java:448–450`).

1.14 directly retains the boat gate, same eye-height expression, same `BlockPos` construction, same fluid-tag test, strict `<`, and `0.11111111F` addition. `isSubmergedIn` delegates to `m_69330161(tag, false)`; that helper optionally checks the eye cell's chunk, reads the same cell, and supplies `(world, blockPos)` to `FluidState.getHeight` (`Entity.java:983–999`). Player tick's cache updater calls it with `true` (`PlayerEntity.java:265–267`). The corresponding exact 1.14 `FlowingFluid.getHeight` returns `1.0F` if the eye-cell fluid accepts the fluid directly above via `Fluid.is`, else `state.getLevel() / 9.0F` (`FlowingFluid.java:442–449`). For water, the override of `Fluid.is(Fluid)` accepts both `Fluids.WATER` and `Fluids.FLOWING_WATER` (`WaterFluid.java:98–101`), so a flowing-water cell recognizes source or flowing water above.

The additional chunk gate does not remove this case for an ordinary local player: `LocalClientPlayerEntity.tick` enters `super.tick()` only after `world.isChunkLoaded(new BlockPos(x, 0, z))` (`LocalClientPlayerEntity.java:178–180`). The predicate's eye query has the same X/Z chunk. The mounted-boat gate excludes ridden players; it does not exclude the ordinary unmounted local player. There is no changed player eye-height or cell-sampling expression in this slice.

## Concrete conditional witness and bounded movement consequence

For an unmounted local player whose eye is in water in a loaded chunk, take the queried flowing-water state at level 7, the same water fluid immediately above, and an eye fraction of `0.9` within the queried block. 1.13.2 computes a threshold of `7/9 + 0.11111111F` (about `0.8889`), so the strict comparison is false. 1.14 obtains height `1.0F` from the same-fluid-above branch, making the threshold about `1.1111`, so the comparison is true. The fluid tag and vanilla block/fluid states are unchanged; only the contextual height query changes the result.

The exact 1.14 flow producer makes this witness transient around a fluid update: when `getSpreadFluid` is next computed for a flowing-fluid cell with same fluid above, it returns level 8 with `FALLING=true` (`FlowingFluid.java:158–190`). Thus the supported source claim is the changed predicate for the stated reachable world snapshot, not a claim that level 7 under same-fluid-above is a steady-state fluid arrangement. The local player samples the cache during its ordinary loaded tick; 1.14 `PlayerEntity.tick` refreshes `isSubmergedInWater` before `super.tick()` (`PlayerEntity.java:215, 265–267`), and `LocalClientPlayerEntity` consumes the cache in water-sprint eligibility and sprint-stop gates (`LocalClientPlayerEntity.java:655–682`). This gives a concrete movement-facing consumer for the changed predicate. No server-side historical movement conclusion follows.

1.14.4 Mojmap independently confirms the same operation: `Entity.isUnderLiquid(tag, bl)` uses the same boat, eye-cell and strict height-threshold structure with a guarded contextual `FluidState.getHeight(level, pos)` (`Entity.java:1053–1069`); `FlowingFluid.getHeight` has the same-fluid-above `1.0F` branch (`FlowingFluid.java:465–472`).

## Finding boundary

The source-visible change is introduced by the directly inspected 1.14 stable source: the submersion predicate starts sampling fluid height with world/cell context, and flowing-fluid height can become full-block height when the same fluid is above. The predicate-level difference is conditional on the queried cell and its above cell; it is not a general change to fluid states. This finding is limited to player-facing submersion sampling and its direct movement consumer. Fluid production, fluid-state mutation, rendering, and server execution are outside the claim.
