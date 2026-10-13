# open shulker boxes stop triggering player escape

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `checkInBlock`, lines 386–440.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, `moveTowardsClosestSpace`, lines 414–447.

Difference: For an open shulker box with its block entity present, 1.15.2's `ShulkerBoxBlock.isSuffocating` still returns true. When a sampled player column includes that block, `blocked` therefore treats the column as obstructed, regardless of the shulker's open state or the player's overlap with its current shape. 1.16.5's shulker registration defines suffocation as `blockEntity.isClosed()` (or true when the block entity is absent). An open shulker is filtered out before 1.16.5 checks collision-shape intersection, so that same sample does not count as suffocating.

Movement meaning: Shulker boxes exist in both endpoints.

The first changed stable release is 1.16: `1.16/mojmap/net/minecraft/world/level/block/Blocks.java`, `shulkerBox`, uses the closed-block-entity predicate. `1.15/mojmap` and `1.15.1/mojmap` `ShulkerBoxBlock.isSuffocating` still return true unconditionally. In 1.14 Feather, `LocalClientPlayerEntity.wouldCollideAt` reads `PlayerEntity.shouldSuffocate`, which reaches the unconditional `ShulkerBoxBlock.isViewBlocking`. Earlier 1.13.2 uses `isSolid` and `isCube`, so its shulker escape predicate is a separate operation boundary.
