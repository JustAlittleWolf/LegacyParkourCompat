# F-6: Powder-snow climb boost reads current state in 1.20.6 and prior-tick state in 1.21.11

- **1.20.6**, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 2268–2282.
- **1.21.11**, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 2533–2544.

## Difference

The post-move vertical boost gate changed from the current block-state query in 1.20.6 to the prior-tick occupancy flag in 1.21.11.

The shared tick-start rollover and unchanged walkability helper show these are distinct temporal inputs.

When the outer collision/jump gate and walkability gate pass but the current-state and prior-tick predicates disagree, 1.20.6 may return vertical velocity `0.2` while 1.21.11 returns the preexisting vertical component (unless the climbable branch applies).

## Movement meaning

LocalPlayer inherits this shared LivingEntity movement method. 1.20.6 player can meet the walkability predicate by wearing leather boots; the source comparison does not establish a concrete ordinary world/tick sequence where current and prior-tick state diverge at this call.

Whether this produces a trajectory difference depends on powder-snow occupancy updates, movement timing, and the resulting collision/jump state.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.20.6--1.21.11/findings/F-6-powder-snow-climb-boost.md).
