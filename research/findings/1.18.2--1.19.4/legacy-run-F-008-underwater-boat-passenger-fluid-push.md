# F-008: Water currents can push a passenger of an underwater boat

- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/Entity.java`.
- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`.

## Difference

For a non-flying player on a submerged boat, 1.18.2's boat check unconditionally bypasses water-current sampling. 1.19.4 continues through the same fluid scan as an unmounted entity, and the player's `isPushedByFluid()` result permits the flow vector to be added to the player's delta movement.

This is distinct from boat steering or boat motion: the write is to the passenger player's velocity.

## Movement meaning

In 1.19.4, flowing water can directly change the velocity of a non-flying player riding an underwater boat; 1.18.2 suppresses that passenger current update.

The branch and velocity write are confirmed from paired source.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.18.2--1.19.4/findings/F-008-underwater-boat-passenger-fluid-push.md).
