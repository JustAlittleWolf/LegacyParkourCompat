# F-07: Server movement collision validation samples a different player Y

- **1.19.4**, mojmap: `ServerGamePacketListenerImpl.java`.
- **1.20.1**, mojmap: `ServerGamePacketListenerImpl.java`.

## Difference

In 1.19.4, `handleMovePlayer` applies the packet position with `player.absMoveTo(packetX, packetY, packetZ, ...)` before checking newly intersected collisions.

The helper accepts only the moved box and constructs its collision context from the player, whose Y is now the requested Y.

In 1.20.1, the receiver leaves the player at the server-clipped current position during the collision check and passes packet XYZ to the helper.

The helper translates the current box to make the requested target box, while its collision context is still constructed from the player at the clipped current Y.

The shared `EntityCollisionContext` constructor captures `entity.getY()` as `entityBottom`.

Its `isAbove` predicate compares that value to the relevant shape top. `ScaffoldingBlock#getCollisionShape` uses this context: when the player is above the full block and is not descending, it returns the stable upper shape.

Its distance-zero alternate branch returns empty.

## Movement meaning

1.19.4 target box that newly overlaps distance-zero scaffolding can be treated differently when the server-clipped current player Y is at the scaffolding top but packet-requested Y is below that top, with the player not descending. 1.19.4 can build a context using the below-top requested Y and receive the empty shape; 1.20.1 can build the target box from packet coordinates while its context uses the clipped at-top Y and receive the stable shape.

That can change the newly-intersected-collision boolean and therefore the movement receiver's accept/correct decision.

This is a source-derived reachability condition.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.4--1.20.1/findings/F-07-server-movement-collision-context-y.md).
