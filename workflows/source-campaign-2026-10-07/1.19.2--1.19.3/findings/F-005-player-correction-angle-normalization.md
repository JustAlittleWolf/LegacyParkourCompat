# F-005 — Absolute player corrections bypass angle normalization

- Older version A: 1.19.2
- Newer version B: 1.19.3
- Mechanic / coverage slice IDs: server player-position correction; S7-player-rotation-correction, S3-glide, S7-external-velocity
- Classification: changed behavior
- Confidence: source-confirmed client correction difference; movement consequence is conditional on an external out-of-range absolute pitch
- Applicability: client player receives an absolute position correction with pitch outside [-90.0F, 90.0F], then uses fall-flying movement
- First changed release: unknown within (1.19.2, 1.19.3]
- Runtime validation: not performed
- Manifest artifact reference: `../run.md` Artifact manifest A/B sections; aligned `mojmap` source trees and official original client jars

## Paired evidence

- A `net/minecraft/client/multiplayer/ClientPacketListener.java`, SHA-256 `98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3`: `handleMovePlayer` lines 563-628 resolves relative flags, sets position/velocity, then calls `Entity.absMoveTo(x, y, z, yaw, pitch)`.
- B same path, SHA-256 `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`: `handleMovePlayer` lines 592-667 uses direct `setXRot` / `setYRot` calls for absolute rotation arguments and writes the same values to previous-rotation fields.
- A/B `Entity.absMoveTo(double,double,double,float,float)` is byte-identical, body SHA-256 `cb16633ddbd3275e377011492821109427158c27bc96aac755bc0e552d2b2ac5`: it applies `yaw % 360.0F` and `Mth.clamp(pitch, -90.0F, 90.0F) % 360.0F`. A/B `Entity.setXRot(float)` is also identical, body SHA-256 `e5a15c3fb912300f215b58a156a0a8cdd38f76a6bc6ecf557a3ef8c8132a1eb6`; it rejects non-finite values but otherwise assigns the supplied value.
- A/B `net/minecraft/server/network/ServerGamePacketListenerImpl.teleport` is text-identical, body SHA-256 `7401a23626d05ef5dd27424e8f55261f77ee96ae6196cb2ad5d7c47f151c3d4f`. A lines 994-1010 / B lines 988-1004 first call server-side `player.absMoveTo(...)`, then construct the correction from the original target angles minus the previous angles only for relative rotation flags. With no relative flags, an input pitch such as `100.0F` is sent as `100.0F` while the server player is clamped to `90.0F`.
- A/B `Entity.getLookAngle()` body is identical, SHA-256 `fd25fc6cce328efdc57d5427751297be5d183410078b23aae5b338ebca8a99a0`. `LivingEntity.travel` A lines 2107-2128 / B lines 2117-2138 consumes `getLookAngle()` and current pitch in the fall-flying steering path. Full source SHA-256: A `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`; B `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`.

## Source-level difference

For an absolute correction carrying pitch `100.0F`, A calls `absMoveTo` and sets the client player's pitch to `90.0F`. B calls `setXRot(100.0F)`, which accepts that finite value. The paired vanilla server teleport method can produce this input when an absolute teleport caller supplies pitch `100.0F`: it clamps its own player state through `absMoveTo` but sends the unmodified target pitch in the absolute correction packet.

## Reachability and dependencies

`ServerGamePacketListenerImpl.teleport` -> `ClientboundPlayerPositionPacket` -> client `ClientPacketListener.handleMovePlayer` -> player pitch -> `Entity.getLookAngle` -> fall-flying `LivingEntity.travel` steering. The correction packet is an external movement input; this finding relies on the exact vanilla server producer method and an out-of-range teleport argument, not an ordinary-play frequency assumption.

## Consequence and uncertainty

Source-proven: A clamps the client pitch to 90 degrees while B retains 100 degrees for this packet path. Predicted only: if the player is fall-flying after the correction, the changed look vector and pitch input can change glide steering and subsequent movement. No actual teleport or trajectory is claimed. Absolute yaw normalization is also bypassed by B, but the concrete movement path recorded here uses pitch during fall-flying.

## Handoff

Independent absolute correction-angle delta. The same handler also changes previous-position/interpolation fields; that separate producer/consumer route remains open in the run ledger. Implementation and testing decisions are deferred.

**Inventories/slices:** INV-TICK, INV-STATE, INV-EXTERNAL; S7-player-rotation-correction, S3-glide, S7-external-velocity. **Implementation disposition:** deferred until blind freeze.
