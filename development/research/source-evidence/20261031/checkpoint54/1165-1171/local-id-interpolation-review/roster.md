# Frozen review roster: local-ID movement-packet interpolation

Frozen before opening the proposing packet-residual report. Exact sources: the supplied Mojmap trees `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.16.5,1.17,1.17.1}\mojmap`.

Comparison route and dependencies:
- `net/minecraft/client/multiplayer/ClientPacketListener.java`: `handleMoveEntity`, `handleTeleportEntity`, local entity lookup and `isControlledByLocalInstance` gate.
- `net/minecraft/world/entity/Entity.java`: `isControlledByLocalInstance`, base `lerpTo`, `getInputVector`, `moveRelative`, position/rotation setter behavior.
- `net/minecraft/world/entity/LivingEntity.java`: overriding `lerpTo`, `aiStep` interpolation and ordering before virtual travel.
- `net/minecraft/client/player/LocalPlayer.java`, `net/minecraft/world/entity/player/Player.java`: inheritance / actual local-player `aiStep` and ordinary local travel dispatch.
- `net/minecraft/network/protocol/game/ClientboundMoveEntityPacket.java`, `ClientboundTeleportEntityPacket.java`: packet rotation byte and coordinate forms.
- `net/minecraft/util/Mth.java`: wrap and sine/cosine lookup expression/table.
- `net/minecraft/server/level/ChunkMap.java`: bounded vanilla self-tracking exclusion check, to distinguish conditional receiver behavior from ordinary vanilla self-packet production.

The comparison asks whether an identical accepted packet and identical current LocalPlayer state can create a source-level movement-state difference in the actual client receiver, and whether the packet is emitted to a tracked local server player by ordinary vanilla entity tracking. It does not audit other packet handlers, general server packet production, runtime trajectories, or implementation.
