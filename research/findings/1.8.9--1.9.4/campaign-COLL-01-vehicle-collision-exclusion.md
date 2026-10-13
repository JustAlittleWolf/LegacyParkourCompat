# COLL-01: Mounted player collision query includes its boat in 1.8.9

- **1.8.9**, ornithe-feather: `net/minecraft/world/World.java`; lines 927-942.
- **1.9.4**, ornithe-feather: `net/minecraft/world/World.java`; lines 941-957.

## Difference

1.8.9's comparisons use the whole collision-query result list where the loop needs to test a candidate entity.

The mapped bytecode confirms the decompiled source expression.

Since the player rider and its boat are not the result-list object, the predicate admits the boat candidate; when its boat shape intersects the player's collision query box, 1.8.9 adds that shape.

The player's base opposing-shape callback returns null, so the boat's own collision shape is the relevant added shape.

1.9.4 checks each candidate's root vehicle. 1.8.9 mounted player and its boat resolve to the same root vehicle, so 1.9.4 skips the boat candidate before reading either collision shape.

Both versions otherwise query the same entity candidates for this bounded comparison.

## Movement meaning

The local passenger reaches `LivingEntity#moveRelative(FF)V` through `World#tickEntities()` -> `Entity#rideTick()` -> local player tick -> `LivingEntity#mobTick()`.

The travel method calls `Entity#move(DDD)V`, which calls `World#getCollisions(Entity,Box)`. 1.8.9 boat exposes its current box as a collision shape in both versions. 1.9.4's root-vehicle helper follows the passenger-to-boat mount link; 1.8.9's base player collision-against-shape method supplies no second shape.

Source proves that 1.8.9's mounted-player collision query can include the intersecting boat box and 1.9.4 omits that same-vehicle candidate.

The supplied shape can affect later axis clipping when requested movement intersects it, but no particular clipped displacement or trajectory is claimed.

The original derived mapped JARs are unavailable; the immutable revised mapped artifacts corroborate the source expressions, but their equivalence to the originals is unproven under the run's recorded artifact-identity limitation.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/COLL-01-vehicle-collision-exclusion.md).
