# F-005 — Absolute player corrections bypass angle normalization

1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/Entity.java`, absMoveTo 1194-1200; setXRot 3059-3065.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/Entity.java`, absMoveTo 1199-1205; setXRot 3094-3100.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleMovePlayer 563-628.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/util/Mth.java`, clamp 97-103.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/util/Mth.java`, clamp 102-108.

Difference: For an absolute correction carrying pitch `100.0F`, 1.19.2 calls `absMoveTo` and sets the client player's pitch to `90.0F`. 1.19.3 calls `setXRot(100.0F)`, which accepts that finite value. The paired vanilla server teleport method can produce this input when an absolute teleport caller supplies pitch `100.0F`: it clamps its own player state through `absMoveTo` but sends the unmodified target pitch in the absolute correction packet.

Movement meaning: `ServerGamePacketListenerImpl.teleport` -> `ClientboundPlayerPositionPacket` -> client `ClientPacketListener.handleMovePlayer` -> player pitch -> `Entity.getLookAngle` -> fall-flying `LivingEntity.travel` steering. The correction packet is an external movement input; this finding relies on the exact vanilla server producer method and an out-of-range teleport argument, not an ordinary-play frequency assumption. Source-proven: 1.19.2 clamps the client pitch to 90 degrees while 1.19.3 retains 100 degrees for this packet path. Predicted only: if the player is fall-flying after the correction, the changed look vector and pitch input can change glide steering and subsequent movement. No actual teleport or trajectory is claimed. Absolute yaw normalization is also bypassed by 1.19.3, but the concrete movement path recorded here uses pitch during fall-flying.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.2--1.19.3/findings/F-005-player-correction-angle-normalization.md).
