# Relative pitch clamp before rotating received player velocity

1.21.4, Mojmap: `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleMovePlayer` and `setValuesFromPositionPacket`, lines 707–735; `net/minecraft/world/entity/PositionMoveRotation.java`, `calculateAbsolute`, lines 35–57.
1.21.5, Mojmap: `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleMovePlayer` and `setValuesFromPositionPacket`, lines 720–748; `net/minecraft/world/entity/PositionMoveRotation.java`, `calculateAbsolute`, lines 34–56.

Difference: Both listeners apply a received `ClientboundPlayerPositionPacket` to the local player when the player is not a passenger, then acknowledge the teleport and report the resulting player position and rotation. They also route the removed-vehicle `ClientboundTeleportEntityPacket` fallback through the same helper when its entity is no longer present. In both versions `setValuesFromPositionPacket` calls `PositionMoveRotation.calculateAbsolute` before writing the returned delta movement and rotation to the target player. In 1.21.4, `calculateAbsolute` forms pitch as `relativePitch + packetPitch` at line 43 and uses that value to calculate the X rotation delta for `Relative.ROTATE_DELTA` at lines 45–49. In 1.21.5, line 42 clamps the calculated pitch to `[-90.0F, 90.0F]` before the same delta rotation. `Entity.setXRot` clamps the final stored pitch in both versions, so the new difference is specifically the pitch used to rotate velocity before the setter, not merely final camera pitch.

Movement meaning: If the local player receives a position packet whose calculated pitch exceeds either pitch bound and whose relative flags include `ROTATE_DELTA`, the packet rotates the player's current velocity by a different pitch delta in 1.21.5. For example, at pitch 80 degrees with a +20 degree relative pitch and `ROTATE_DELTA`, 1.21.4 uses a 100 degree target for the velocity rotation, while 1.21.5 uses 90 degrees; both ultimately store a clamped 90 degree pitch. The changed delta velocity feeds subsequent local movement. This is a direct client packet consumer finding; no claim is made about which server actions produce that packet/flag combination.

Source hashes from the verified release source manifests:

- 1.21.4 `ClientPacketListener.java`: `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4`
- 1.21.5 `ClientPacketListener.java`: `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617`
- 1.21.4 `PositionMoveRotation.java`: `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436`
- 1.21.5 `PositionMoveRotation.java`: `75baa0b6f99d1b9d18f157cbbf2c6d209b13af6719e960c7da1c451587433cc3`
- `net/minecraft/world/entity/Entity.java` was checked to confirm `setXRot` clamps identically in both versions (method line 3513 in each).
