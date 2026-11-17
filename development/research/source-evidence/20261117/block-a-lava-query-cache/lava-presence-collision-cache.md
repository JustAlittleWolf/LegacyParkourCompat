# Lava presence changes from a live query to a cached collision result

1.13.2, Ornithe Feather: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `isInLava()`, lines 1053-1054; `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/world/World.java`, `containsMaterial(Box, Material)`, lines 1310-1330.

1.14, Ornithe Feather: `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/entity/Entity.java`, lava flag and `isInLava()`, lines 151, 530, 745-769, 1003-1007; `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/block/LiquidBlock.java`, `onEntityCollision(...)`, lines 168-171; `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, jump and travel lava gates, lines 1789 and 2200-2202.

Difference: 1.13.2 queries `Material.LAVA` against the entity shape contracted by 0.1 on X/Z and 0.4 on Y. 1.14 reads a boolean reset before post-move block collision scanning. That scan visits block coordinates from `floor(box.min + 0.001)` through `floor(box.max - 0.001)`, inclusive, when the area is loaded; the liquid-block callback sets the flag when the block's fluid matches the lava tag.

Movement meaning: an ordinary nonspectator player with a narrow overlap into a lava voxel can be admitted by the 1.14 callback scan even when the 1.13.2 contracted query excludes it. On the next movement tick that changes the lava jump gate from the normal ground-jump path to the lava swim-up response; player travel also consumes the cached predicate. The cache is produced after movement, while position setters do not refresh it.
