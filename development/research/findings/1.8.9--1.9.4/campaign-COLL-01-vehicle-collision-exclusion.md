# COLL-01: Mounted player collision query includes its boat in 1.8.9

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(Entity entity, Box shape) {`, lines 891-945.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`, `public void move(double dx, double dy, double dz) {`, lines 371-636.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(@Nullable Entity entity, Box shape) {`, lines 899-961.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/world/World.java`, `public boolean getCollisions(Box shape) {`, lines 1018-1055.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/Entity.java`, `public void move(double dx, double dy, double dz) {`, lines 441-720.

Difference: 1.8.9's comparisons use the whole collision-query result list where the loop needs to test a candidate entity.

The mapped bytecode confirms the decompiled source expression.

Since the player rider and its boat are not the result-list object, the predicate admits the boat candidate; when its boat shape intersects the player's collision query box, 1.8.9 adds that shape.

The player's base opposing-shape callback returns null, so the boat's own collision shape is the relevant added shape.

1.9.4 checks each candidate's root vehicle. 1.8.9 mounted player and its boat resolve to the same root vehicle, so 1.9.4 skips the boat candidate before reading either collision shape.

Both versions otherwise query the same entity candidates for this bounded comparison.

Movement meaning: The local passenger reaches `LivingEntity#moveRelative(FF)V` through `World#tickEntities()` -> `Entity#rideTick()` -> local player tick -> `LivingEntity#mobTick()`.

The travel method calls `Entity#move(DDD)V`, which calls `World#getCollisions(Entity,Box)`. 1.8.9 boat exposes its current box as a collision shape in both versions.

Original evidence: [COLL-01-vehicle-collision-exclusion.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/COLL-01-vehicle-collision-exclusion.md).
