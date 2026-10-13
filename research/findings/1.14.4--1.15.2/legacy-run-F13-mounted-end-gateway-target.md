# F13: 1.15.2 End Gateways target a mounted player's root vehicle

- **1.14.4**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F13-mounted-end-gateway-target.md).
- **1.15.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F13-mounted-end-gateway-target.md).

## Difference

When the queried first entity is a player riding another entity, 1.14.4 targets that player for the gateway teleport. 1.15.2 targets the player's root vehicle. 1.15.2's common entity teleport path then updates the root/passenger chunk and teleport state and repositions its direct passengers, so the mounted player is carried through the passenger chain as part of the root vehicle teleport.

This is a changed player position route; it does not establish any difference in independent vehicle movement.

## Movement meaning

End Gateway server tick -> first intersecting entity -> (1.15.2 only) root-vehicle selection -> ticketed teleport -> `Entity.teleportTo` / `ServerPlayer.teleportTo` -> passenger position propagation.

The target selection and passenger propagation follow from the paired source paths.

The finding is limited to a mounted player selected by the gateway's first-entity query; other tracked-entity and external position/velocity writers remain under audit.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F13-mounted-end-gateway-target.md).
