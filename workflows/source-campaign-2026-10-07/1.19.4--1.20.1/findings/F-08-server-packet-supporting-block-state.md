# F-08: Accepted 1.20.1 movement packets refresh supporting-block state

## Source identity

- Pair: exact vanilla 1.19.4 -> 1.20.1, Mojmap sources from the verified campaign artifacts.
- A `ServerGamePacketListenerImpl.java` SHA-256: `1e1b8dc23031bd5dfc4a3dd5453ec1e6ad3211a534a810927c8fa725fc271dc9`.
- B `ServerGamePacketListenerImpl.java` SHA-256: `5d790f4ade510baa163cd17e622851261a3eaebbbe8ad82ac31a9bca3c251364`.
- A `ServerPlayer.java` SHA-256: `5eeea9be89db11000daa95cada0a4120c5eda1014aae9e868fba0bee8de05606`.
- B `ServerPlayer.java` SHA-256: `4168d5d45d043f5edc0d9cafeeff2540cf544bae92b53f1b018fd1ee8887dd61`.
- A `Entity.java` SHA-256: `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`.
- B `Entity.java` SHA-256: `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759`.

## Difference

After an accepted movement packet, A calls `ServerPlayer#doCheckFallDamage(dy, onGround)` and `Entity#setOnGround(onGround)`. A's ground setter assigns the boolean.

B passes packet XYZ displacement to `ServerPlayer#doCheckFallDamage(dx,dy,dz,onGround)` and calls `Entity#setOnGroundWithKnownMovement(onGround, movement)`. Both B ground setters call `checkSupportingBlock`. When grounded, that method looks for a supporting block in a thin AABB below the player, stores the result, and can retry using the prior horizontal box when movement is known. When not grounded, it clears the no-block state and tracked support position.

The B `ServerPlayer#doCheckFallDamage` also calls `checkSupportingBlock` before forwarding the vertical displacement and ground boolean to `Entity#checkFallDamage`. The A counterpart only forwards vertical displacement and ground boolean. The downstream fall callback and world data are kept as open S7-08B work.

## Reachability and scope

The receiver reaches these calls on accepted player movement packets. This is a source-confirmed server-side state writer difference. It is related to the supporting-block state and movement-factor reader route recorded in F-02, but this finding does not claim the full set of server consumers or any specific block-dependent runtime consequence.

- Status: source-confirmed state-writer difference; full consumer/data inventory remains open under S7-08B and D2.
