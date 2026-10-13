# 1.13.2 adds a swim-height player box

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; lines 291-315.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/swim-dimensions.md); lines 334-361.

## Difference

When 1.13.2's local player is swimming and the collision check accepts the `0.6 x 0.6` box, 1.13.2 selects that size; 1.12.2 has no swimming size branch and retains the size selected by its fall-flying/sleeping/sneaking/standing priority.

The body explicitly includes `isDoingSpinAttack()` as another 1.13.2 trigger; this finding is limited to the swimming trigger, and the spin-attack path remains separately scoped.

The collision-clearance call differs across the versions and its exact equivalence is not assumed here.

## Movement meaning

1.13.2 `Entity.updateSwimming()` derives the swimming flag from sprint and water state; PlayerEntity filters swimming while flying or spectating. `PlayerEntity.tick()` applies pose size after the living tick.

The resulting box feeds later movement and collision queries.

Whether the box shrink succeeds depends on `world.hasNoCollisions` versus 1.12.2 `world.getCollisions`; resolve those helpers and shape behavior in stage 4 before claiming exact clearance equivalence.

Source proves the new 1.13.2 size selection and the different collision-clearance APIs.

It predicts a shorter player collision box when the 1.13.2 swim pose is accepted, changing later support, contact and collision calculations.

It does not establish the exact first-tick movement consequence or clearance equivalence.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/swim-dimensions.md).
