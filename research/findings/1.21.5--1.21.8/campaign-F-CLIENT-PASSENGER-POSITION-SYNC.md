# Candidate: vehicle entity interpolation gate changes client player passenger positioning

1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, positionRider 2019-2023.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/entity/Entity.java`, positionRider 2187-2191.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleEntityPositionSync 567-592.

Difference: - Exact precondition: the client receives an entity-position sync for a non-local-authoritative entity that has the local player as an indirect passenger. For an interpolating entity 1.21.5 applies the passenger-position update and 1.21.8 skips it; for a non-interpolating entity 1.21.8 applies it and 1.21.5 skips it. The report does not audit vehicle movement or physics; it records only the changed player-position call. Candidate remains pending blind review and scope disposition.

Movement meaning: - Analysis: this changes when client-side passenger positioning is applied for the local player. The report does not audit vehicle movement or physics; it records only the changed player-position call. Candidate remains pending blind review and scope disposition.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.5--1.21.8/findings/F-CLIENT-PASSENGER-POSITION-SYNC.md).
