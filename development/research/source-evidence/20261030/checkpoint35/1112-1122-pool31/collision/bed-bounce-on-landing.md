# Bed bounce on vertical collision

1.11.2, Ornithe Feather Gen2: `net/minecraft/block/Block.java`, `beforeCollision`, lines 624–626; `net/minecraft/entity/Entity.java`, `move`, lines 723–725. `BedBlock` has no `beforeCollision` override in this release. The base hook resets `entity.velocityY` to zero.

1.12.2, Ornithe Feather Gen2: `net/minecraft/block/BedBlock.java`, `beforeCollision`, lines 141–150; `net/minecraft/entity/Entity.java`, `move`, lines 726–728. The base hook remains at `net/minecraft/block/Block.java`, lines 643–645.

Difference: the bed override delegates to the zeroing base hook while sneaking. Otherwise, when `velocityY < 0.0`, it writes `-velocityY * 0.66F`; the additional `* 0.8` applies only to entities that are not `LivingEntity`. `Entity.move` calls the hook when resolved vertical movement differs from requested vertical movement, after choosing the supporting block from the block at `floor(y - 0.2F)`.

Movement meaning: a non-sneaking player falling onto an existing bed leaves the collision with upward velocity in 1.12.2 instead of zero vertical velocity in 1.11.2. The introduction boundary within this endpoint interval is unknown.

Client reachability: in both releases `LocalClientPlayerEntity` extends `ClientPlayerEntity`, which extends `PlayerEntity`, which extends `LivingEntity`, which extends `Entity`. The local player's `tick()` calls `super.tick()` (`LocalClientPlayerEntity.java:159–162` in 1.11.2; `:165–168` in 1.12.2). The living travel path calls `moveRelative`, and player movement ultimately dispatches to `LocalClientPlayerEntity.move`, whose `super.move(...)` reaches `Entity.move` (`LocalClientPlayerEntity.java:823–829` and `:845–851`). Neither the `Entity.move` call to `beforeCollision` nor the bed override is guarded by `world.isClient`, so this velocity response runs in the logical client world.
