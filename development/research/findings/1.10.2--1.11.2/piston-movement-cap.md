# Piston displacement is capped per world-time axis

1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public void move(double dx, double dy, double dz) {`, lines 446-725.
1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `private void moveEntities() {`, lines 91-150.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public void move(MoverType moverType, double x, double y, double z) {`, lines 459-790.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `private void moveEntities(float progress) {`, lines 116-169.

Difference: 1.11.2 adds a PISTON-specific cumulative requested-movement limiter keyed by world time and entity. 1.10.2 has no mover-type branch or corresponding accumulator in `Entity.move`.

For a non-noClip entity, 1.11.2 resets the accumulator on a changed world time, then clamps each nonzero axis's accumulated request to `[-0.51,0.51]`, forwards only the difference from the previous accumulator, and returns before ordinary collision resolution when the forwarded magnitude is `<= 1.0E-5F`.

The accumulator is updated before the early return. 1.10.2 forwards each piston caller request to ordinary collision resolution without this limiter.

Because the cap runs before collision handling, this finding is about requested movement input, not a cap on realized position change.

Movement meaning: The paired moving-piston block entity selects entities intersecting its movement area, skips only `PistonMoveBehavior.IGNORE`, and calls `Entity.move` with the piston direction delta. `PlayerEntity` inherits `Entity#getPistonMoveBehavior() == NORMAL` on both sides; its tick sets `noClip` to the spectator flag.

The bounded precondition is a local non-spectator player present in that entity list and receiving same-axis piston requests during one `World.getTime()` value; 1.11.2 routes each request through the PISTON branch.

The cap itself precedes collision resolution, so the exact collision shape does not determine whether the request is capped once the caller has supplied it.

Source proves a new per-world-time cumulative cap on piston-driven requested movement for the bounded player path.
