# Climbing players are excluded from the push recipient filter

1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public boolean isPushable() {`, lines 1887-1889.
1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/entity/EntityFilter.java`, `public static <T extends Entity> Predicate<T> canBePushedBy(Entity entity) {`, lines 56-94.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public boolean isPushable() {`, lines 1950-1952.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/EntityFilter.java`, `public static <T extends Entity> Predicate<T> canBePushedBy(Entity entity) {`, lines 50-88.

Difference: For a non-removed living player, 1.10.2 reports pushable regardless of climbing. 1.11.2 rejects the player while climbing.

The paired filter excludes rejected entities from the collision candidate list.

On the client, the filter permits a player candidate only when it is local; therefore a remote player can select the local player when its bounding box overlaps the local player's box and collision rules permit the interaction.

Its inherited `pushAway` calls `entity.push(this)` on that local player.

The `Entity.push` path can add horizontal velocity when entities are not in the same vehicle, neither is no-clip, horizontal separation is at least `0.01F`, and the target has no passengers.

Movement meaning: Remote player tick -> inherited `LivingEntity.pushAwayCollidingEntities` -> `EntityFilter.canBePushedBy(remotePlayer)` -> local player `isPushable` -> `pushAway`/`Entity.push` -> local player's velocity.

The direct precondition for the difference is that the local player is climbing and the remote player's collision query otherwise admits the local player.

Team collision rules, overlap geometry and `Entity.push` guards gate the call.

This finding does not model entity movement or dead-player behavior.

Source proves 1.11.2 can omit a climbing local player from this push recipient list where 1.10.2 would include it, preventing the downstream local-player velocity write on this path.

The magnitude and resulting trajectory are not claimed.

The separate `isAlive()` gate can also affect removed/dead entities, but those state producers are excluded and are not part of this finding.
