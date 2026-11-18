# Frozen blind source roster

Scope: 1.13.2 to 1.14, Ornithe Feather exact sources; ordinary riding-player detach/callback while its vehicle occupies a portal-tagged cell, local XYZ writes through detach, vehicle helper contribution, and client passenger-packet/disconnect/reconnect route. Excludes rideTick position updates and separate authoritative correction. Roster frozen before opening the supplied finding.

| Release | Canonical source members/files to inspect |
|---|---|
| 1.13.2 | `net/minecraft/entity/Entity.java` (ride/dismount and position writes), `net/minecraft/entity/living/PlayerEntity.java`, `net/minecraft/client/entity/living/player/ClientPlayerEntity.java`, `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, `net/minecraft/network/packet/s2c/play/EntityPassengersS2CPacket.java`, `net/minecraft/entity/vehicle/BoatEntity.java`, `net/minecraft/entity/living/mob/passive/animal/HorseEntity.java`, `net/minecraft/block/PortalBlock.java` |
| 1.14 | Same semantic files under `net/minecraft/...` |

Inputs: exact releases 1.13.2 and 1.14; mapping family Ornithe Feather. Published source roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.13.2\ornithe-feather` and `...\1.14\ornithe-feather`. These source roots and manifests are read-only. Provenance/ready metadata and manifest digests are recorded in the audit verdict after source verification.
