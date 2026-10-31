# Respawn retains the prior jump-strength base

1.20.6, Mojmap: `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleRespawn`, lines 1109–1133; `net/minecraft/world/entity/ai/attributes/Attributes.java`, `JUMP_STRENGTH`, lines 45–47; `net/minecraft/world/entity/LivingEntity.java`, `createLivingAttributes`, `getJumpPower` and `jumpFromGround`.

1.21 and 1.21.1, Mojmap: `ClientPacketListener.handleRespawn`, lines 1110–1138; `net/minecraft/world/entity/ai/attributes/AttributeMap.java`, `assignBaseValues`, lines 111–117; `Attributes.JUMP_STRENGTH`, lines 53–55; `LivingEntity.getJumpPower` and `jumpFromGround`, lines 2047–2060.

When the respawn packet does not keep attributes (`shouldKeep((byte)1)` is false), 1.20.6 leaves the replacement player's fresh attribute map unchanged. The jump-strength base therefore has its registered default, `0.42F` widened to double. In 1.21 the false branch instead calls `assignBaseValues` and retains the old instantiated base without copying its modifiers. The relevant 1.21.1 sources are unchanged.

A prior valid attribute snapshot can instantiate the syncable jump-strength attribute with base `0.6`, within its registered range. Given that state and the same no-keep respawn packet, the next ordinary grounded jump, with no jump boost and block jump factor one, writes the fresh default Y velocity in 1.20.6 and the retained base's float conversion in 1.21. This is a direct client respawn response and subsequent jump arithmetic; it does not require a claim about server packet production or a later trajectory.
