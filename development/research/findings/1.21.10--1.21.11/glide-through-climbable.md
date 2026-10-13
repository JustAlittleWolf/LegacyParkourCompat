# Fall-flying Players no longer take the climbable fallback on tagged vines

1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java`, onClimbable 1662-1678; travelFallFlying 2383-2397.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, onClimbable 1669-1687.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/world/entity/player/Player.java`, onClimbable 1955-1957; travel 1295-1318.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/player/Player.java`, travel 1360-1383.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/data/tags/VanillaBlockTagsProvider.java`, tag 320-324.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/tags/BlockTags.java`, class declaration / field initialization 125.

Difference: For a Player with `abilities.flying == false`, `isSpectator() == false`, `isFallFlying() == true`, and a current block state at `blockPosition()` that is one of the vanilla-default tag intersection states (vine, twisting vine or plant, weeping vine or plant, cave vine or plant), outside the earlier fluid-travel branch, 1.21.10 reports the Player climbable and 1.21.11 reports it not climbable. The `Player.onClimbable()` wrapper delegates to `LivingEntity.onClimbable()` under this ability guard. 1.21.10 then calls `travelInAir()` and `stopFallFlying()`; 1.21.11 follows the fall-flying movement branch. This is a branch and state-write difference, not a measured trajectory.

Movement meaning: The intersection blocks are present in 1.21.10's vanilla `climbable` tag and are existing blocks in the 1.21.10 endpoint artifact. This is a direct player movement-path difference; it does not depend on custom tags for the stated vanilla-default case. Server/resource-pack tag overrides are not assessed. The glide formula itself is otherwise unchanged for an equal branch decision.
