# F-07: Server movement collision validation samples a different player Y

## Source identity

- Pair: exact vanilla 1.19.4 -> 1.20.1, Mojmap sources from the verified campaign artifacts.
- A `ServerGamePacketListenerImpl.java` SHA-256: `1e1b8dc23031bd5dfc4a3dd5453ec1e6ad3211a534a810927c8fa725fc271dc9`.
- B `ServerGamePacketListenerImpl.java` SHA-256: `5d790f4ade510baa163cd17e622851261a3eaebbbe8ad82ac31a9bca3c251364`.
- Shared `EntityCollisionContext.java` SHA-256: `fa505d477deaeed144816a3ec08512357ee178dc8769534b3a7f59e9a3299587`.
- Shared `ScaffoldingBlock.java` SHA-256: `fe0d38c1f1f06453fb42aad778d0ae8a8a5e13db335be9b465ca8c7653339eec`.

## Difference

In A, `handleMovePlayer` applies the packet position with `player.absMoveTo(packetX, packetY, packetZ, ...)` before checking newly intersected collisions. The helper accepts only the moved box and constructs its collision context from the player, whose Y is now the requested Y.

In B, the receiver leaves the player at the server-clipped current position during the collision check and passes packet XYZ to the helper. The helper translates the current box to make the requested target box, while its collision context is still constructed from the player at the clipped current Y.

The shared `EntityCollisionContext` constructor captures `entity.getY()` as `entityBottom`. Its `isAbove` predicate compares that value to the relevant shape top. `ScaffoldingBlock#getCollisionShape` uses this context: when the player is above the full block and is not descending, it returns the stable upper shape. Its distance-zero alternate branch returns empty.

## Reachable consequence

A target box that newly overlaps distance-zero scaffolding can be treated differently when the server-clipped current player Y is at the scaffolding top but packet-requested Y is below that top, with the player not descending. A can build a context using the below-top requested Y and receive the empty shape; B can build the target box from packet coordinates while its context uses the clipped at-top Y and receive the stable shape. That can change the newly-intersected-collision boolean and therefore the movement receiver's accept/correct decision.

This is a source-derived reachability condition. The slice does not claim a runtime reproduction or assert that every collision provider behaves this way.

## Scope and status

- In scope: direct player movement packet validation and collision-provider inputs.
- Status: source-confirmed input-path finding; underlying non-scaffolding block/tag/resource inputs remain open under S5-02 and D2.
- Follow-up: compare the paired fall and ground-state writer/consumer chain separately in S7-08B.
