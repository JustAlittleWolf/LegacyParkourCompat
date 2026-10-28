# Lava presence moves from a movement latch to a tick cache

1.16.1, Mojmap: `1.16.1/mojmap/net/minecraft/world/entity/Entity.java`, `move`, `checkInsideBlocks`, `setInLava` and `isInLava`; `world/level/block/LiquidBlock.java`, `entityInside`; `world/level/LevelReader.java`, `hasChunksAt`; `world/level/Level.java`, `getBlockState`; `client/multiplayer/ClientLevel.java`, `hasChunk`.
1.16.2, Mojmap: corresponding `Entity.baseTick`, `updateInWaterStateAndDoFluidPushing`, `isInLava` and `LivingEntity` travel/jump consumers.

Difference: 1.16.1 clears a boolean after movement collision/fall/step handling, then scans block callbacks inside the bounding box inset by 0.001. A lava LiquidBlock sets it when the original box's minimum Y is below the float lava surface (or the source second disjunct passes). The value persists until another movement reset. 1.16.2 reads first-tick-gated numeric lava depth refreshed during baseTick, before local AI/travel. Exact 1.16/1.16.1 sources share the former operation; 1.16.2–1.16.5 share the latter.

The old scan admits a box only when its floored maximum Y is at least zero and minimum Y is below 256. Partly overlapping boxes retain their raw bounds, with outside-height cells reading VOID_AIR. ClientLevel.hasChunk always returns true, so its horizontal column loop is not a client loaded-chunk barrier; a missing client chunk exposes EmptyLevelChunk/VOID_AIR. Modern dimension-height admission is a different helper contract.

Movement meaning: local player water/lava travel selection and jump behavior can read the last movement's latch in the older client, while the newer client reads the current tick's fluid scan. This changes a movement predicate independently of lava current, depth, eye-fluid state or damage handling.
