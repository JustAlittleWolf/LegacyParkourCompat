# F-ENDER-PEARL-OWNER-DIMENSION: 1.12.2 clears a pearl's player thrower before dimension transfer

1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/projectile/EnderPearlEntity.java`, `protected void onCollision(HitResult result) {`, lines 37-98.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/projectile/EnderPearlEntity.java`, `public Entity changeDimension(int dimension) {`, lines 119-125.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/projectile/EnderPearlEntity.java`, `protected void onCollision(HitResult result) {`, lines 40-105.

Difference: The generic server portal tick reaches the virtual `changeDimension` method for a pearl that has entered a Nether portal and completed its portal timer.

On 1.11.2, the inherited transfer serializes the live player thrower into `ownerName` before recreating the pearl, so a matching player in the destination world can be resolved as its thrower afterward.

On 1.12.2, the pearl override first clears the live thrower when the target dimension differs; for a newly thrown pearl whose `throwerName` has not already been populated, the inherited serializer therefore writes an empty owner name and the replacement pearl cannot resolve that player.

When the transferred pearl later collides, both versions use the same player response guard: connected server player, player world equal to pearl world and player not sleeping.

If that guard holds, 1.11.2 can teleport the player to the pearl and 1.12.2 has no thrower to enter the branch.

If it does not hold, neither version teleports that player through the ordinary pearl collision branch. 1.11.2 previously populated owner name may remain serialized in 1.12.2 because the override clears `thrower`, not `throwerName`; the finding therefore states the fresh-pearl precondition rather than claiming all dimension transfers lose ownership.

Movement meaning: Source establishes a difference in player thrower retention across a pearl dimension transfer, and a reachable conditional difference in the later player position write.

It does not establish how often players encounter that sequence or a measured trajectory.

The 1.12.2-only End Gateway advancement callback does not itself write movement state.

Entity dimension transfer, packet authority and other external movement writers remain in S7.3/S7.4; no damage behavior is in scope.

Original evidence: [F-ENDER-PEARL-OWNER-DIMENSION.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.11.2--1.12.2/findings/F-ENDER-PEARL-OWNER-DIMENSION.md).
