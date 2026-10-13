# End Portal Frame solidity changes local player block-ejection gate

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/Block.java`, `public boolean isCube() {`, lines 245-247.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `protected boolean pushAwayFrom(double x, double y, double z) {`, lines 292-342.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/EndPortalFrameBlock.java`, `public boolean isCube(BlockState state) {`, lines 109-111.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/Block.java`, `public boolean isCube(BlockState state) {`, lines 223-225.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `protected boolean pushAwayFrom(double x, double y, double z) {`, lines 349-399.

Difference: 1.8.9's End Portal Frame inherits `Block#isCube()==true`.

It has `Material.STONE`, whose `isSolidBlocking()` is true, and its analog output does not change the separate `isSignalSource()` predicate; 1.8.9's `Block#isSolid()` therefore returns true.

In 1.9.4, `EndPortalFrameBlock#isCube(BlockState)` returns false for its states, so `Block#isSolid(BlockState)` returns false for the same block despite unchanged material and signal-source inputs.

The local-player helper differs only in which solidity API it reads: 1.8.9 checks the block instance; 1.9.4 checks the sampled states.

The paired `pushAwayFrom` methods keep the same guards, coordinate conversion, west/east/north/south ordering, strict distance comparisons, and `±0.1F` horizontal velocity writes.

If the sampled position is the frame and the above position is non-solid, 1.8.9's `canSurvive` result is false and the escape search runs; 1.9.4's result is true and skips that write.

When an escape neighbor is survivable, this creates a reachable 1.8.9-only velocity overwrite for a locally controlled player before superclass movement.

Movement meaning: Both registries contain the historical `end_portal_frame` block and both constructors use `Material.STONE`.

The path is the local player's four pre-travel body probes in `LocalClientPlayerEntity#mobTick()V`, then `pushAwayFrom(DDD)Z` and `canSurvive(BlockPos)Z`.
