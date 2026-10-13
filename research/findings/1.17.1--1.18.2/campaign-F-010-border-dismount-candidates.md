# F-010: Dismount candidates wholly beyond the world border are rejected in 1.18.2

- **1.17.1**, mojmap: `net/minecraft/world/entity/vehicle/DismountHelper.java`; lines 41-43.
- **1.18.2**, mojmap: `net/minecraft/world/entity/vehicle/DismountHelper.java`; lines 41-49.

## Difference

For equal block-collision inputs, 1.17.1 accepts a dismount AABB when no block collision shape intersects it. 1.18.2 additionally rejects it when the AABB lies wholly outside the world-border interior along either horizontal axis.

An AABB that still overlaps the interior passes 1.18.2's `isWithinBounds` predicate.

## Movement meaning

The player wake path is `LivingEntity#stopSleeping` -> `BedBlock#findStandUpPosition` -> ordered safe-candidate search -> `DismountHelper#findSafeDismountLocation` -> `LivingEntity#setPos`.

Vehicle exits follow `LivingEntity#stopRiding` -> `dismountVehicle` -> the active boat/minecart/pig/horse/strider provider -> `DismountHelper#canDismountTo` -> `dismountTo`.

Both paths directly select a player position from collision-tested candidates; no vehicle motion simulation is needed for this finding.

The respawn-position helper also uses `findSafeDismountLocation` for player bed/anchor candidates, but respawn trajectory and gameplay are not claimed here.

Source-proven: if an ordered candidate is free of block collisions but wholly outside the border, 1.17.1 accepts it and 1.18.2 rejects it.

Where another candidate is also valid, the selected wake/dismount position can differ; if no candidate remains, the caller's fallback may differ from 1.17.1's previously accepted candidate.

Border overlap, block collision and candidate order remain the determining inputs.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-010-border-dismount-candidates.md).
