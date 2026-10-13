# Candidate: cross-dimension teleports move players spectating the entity

- **1.21.5**, mojmap: `Entity#teleportCrossDimension(ServerLevel, TeleportTransition)`; lines 2681-2719.
- **1.21.8**, mojmap: `Entity#teleportCrossDimension(ServerLevel, ServerLevel, TeleportTransition)`; lines 2852-2885.

## Difference

- Exact precondition: a server-level entity successfully completes a cross-dimension `TeleportTransition` while one or more source-level `ServerPlayer` instances have `getCamera() ==` the entity being moved.

In 1.21.8, those spectator players receive the same transition through `ServerPlayer#teleport`; 1.21.5 leaves them in their original dimension.

ServerPlayer-initiated cross-dimension teleport has the corresponding direct helper call in 1.21.8.
- Analysis: this is a direct player position/dimension writer triggered by a native dimension-transfer event.

It does not change the target entity's ordinary movement or client correction formulas.

The movement consequence is the newly added player relocation and camera reset for active spectators.

The finding remains a candidate pending blind review and final audit scope disposition.

## Movement meaning

- Analysis: this is a direct player position/dimension writer triggered by a native dimension-transfer event.

It does not change the target entity's ordinary movement or client correction formulas.

The movement consequence is the newly added player relocation and camera reset for active spectators.

The finding remains a candidate pending blind review and final audit scope disposition.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.5--1.21.8/findings/F-SPECTATOR-DIMENSION-FOLLOW.md).
