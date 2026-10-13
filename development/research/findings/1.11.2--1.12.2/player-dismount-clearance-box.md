# non-boat/non-horse dismount uses different clearance geometry

1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void stopRiding() {`, lines 1864-1870.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void stopRiding() {`, lines 1908-1914.

Difference: For non-Boat/non-Horse mounts, both versions derive candidate exit coordinates from the rider's position and movement direction, inspect candidate boxes with `World.getCollisions`, and teleport the rider to the first eligible support or the retained fallback coordinates.

The clearance box differs. 1.11.2 derives its vertical interval from the rider's own box after subtracting the mount's height, then tests each horizontal candidate after moving that box up by `1.0`. 1.12.2 derives the vertical interval from the mount's bounding-box minimum and the rider height, then tests the candidate at vertical offset `0.0`.

Those two boxes cover different world-space volumes for the same mounted player, mount and candidate. 1.11.2 block collision in the exclusive part of one box changes whether the candidate is accepted and can therefore change which dismount coordinates are sent through `teleport`.

The BoatEntity/HorseBaseEntity special branch is unchanged and does not use this changed general clearance box.

Movement meaning: Source proves a changed collision-clearance query on a reachable player dismount path and a conditional change to its selected player exit position.

It does not establish a measured trajectory or identify a particular world arrangement where the first accepted candidate differs.
