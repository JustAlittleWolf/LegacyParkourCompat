# F-08: Accepted 1.20.1 movement packets refresh supporting-block state

- **1.19.4**, mojmap: `ServerGamePacketListenerImpl.java`.
- **1.20.1**, mojmap: `ServerGamePacketListenerImpl.java`.

## Difference

After an accepted movement packet, 1.19.4 calls `ServerPlayer#doCheckFallDamage(dy, onGround)` and `Entity#setOnGround(onGround)`. 1.19.4's ground setter assigns the boolean.

1.20.1 passes packet XYZ displacement to `ServerPlayer#doCheckFallDamage(dx,dy,dz,onGround)` and calls `Entity#setOnGroundWithKnownMovement(onGround, movement)`.

Both 1.20.1 ground setters call `checkSupportingBlock`.

When grounded, that method looks for a supporting block in a thin AABB below the player, stores the result, and can retry using the prior horizontal box when movement is known.

When not grounded, it clears the no-block state and tracked support position.

The 1.20.1 `ServerPlayer#doCheckFallDamage` also calls `checkSupportingBlock` before forwarding the vertical displacement and ground boolean to `Entity#checkFallDamage`.

The 1.19.4 counterpart only forwards vertical displacement and ground boolean.

The downstream fall callback and world data are kept as open S7-08B work.

## Movement meaning

The receiver reaches these calls on accepted player movement packets.

This is a source-confirmed server-side state writer difference.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.4--1.20.1/findings/F-08-server-packet-supporting-block-state.md).
