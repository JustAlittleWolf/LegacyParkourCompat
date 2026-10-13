# Dismount candidates wholly beyond the world border are rejected in 1.18.2

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/vehicle/DismountHelper.java`, `canDismountTo, findSafeDismountLocation`, lines 41–43, 74–89.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/vehicle/DismountHelper.java`, `canDismountTo, findSafeDismountLocation`, lines 41–49, 80–104.

Difference: For equal block-collision inputs, 1.17.1 accepts a dismount AABB when no block collision shape intersects it. 1.18.2 additionally rejects it when the AABB lies wholly outside the world-border interior along either horizontal axis. An AABB that still overlaps the interior passes 1.18.2's `isWithinBounds` predicate.

Movement meaning: The player wake path is `LivingEntity#stopSleeping` -> `BedBlock#findStandUpPosition` -> ordered safe-candidate search -> `DismountHelper#findSafeDismountLocation` -> `LivingEntity#setPos`.

The first changed stable release is 1.18: exact Mojmap `DismountHelper.canDismountTo` and `findSafeDismountLocation` add the border check absent in 1.17.1. `1.18` and `1.18.2` `DismountHelper` and `WorldBorder` sources match. This is candidate admission for supplied equal floor, block-collision and AABB inputs; it does not establish the final pose or permit changing non-player/server consumers.
