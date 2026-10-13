# Discarded candidate: fresh-login spawn-angle change is corrected before player movement starts

- **1.21.5**, mojmap: `ServerPlayer.java`; lines 344-363.
- **1.21.8**, mojmap: `PlayerList.java`; lines 161-166.

## Difference

- 1.21.5 evidence: 1.21.5 `ServerPlayer` constructor calls `super(level, sharedSpawnPos, sharedSpawnAngle, profile)`, then snaps to `adjustSpawnLocation(...).getBottomCenter()` with yaw `0.0F` and pitch `0.0F` (1.21.5 `ServerPlayer.java` constructor lines 344-363). `PlayerList#placeNewPlayer()` does not apply another yaw for a player with no saved data before its connection teleport (1.21.5 `PlayerList.java`
- 1.21.8 evidence: `Player` and `ServerPlayer` constructors no longer set the initial spawn pose.

In `PlayerList#placeNewPlayer()`, when player data is absent, 1.21.8 snaps the new player to `adjustSpawnLocation(...).getBottomCenter()` using `getSharedSpawnAngle()` and pitch `0.0F` before sending the connection teleport (1.21.8 `PlayerList.java` branch at lines 161-166). 1.21.8 `ServerPlayer.java` `Level#getSharedSpawnAngle()` returns `levelData.getSpawnAngle()` in both versions (1.21.5/1.21.8 `Level.java` hashes  / , lines 634-636).
- Artifact provenance: exact 1.21.5/1.21.8 Mojmap source manifests and readiness records are recorded in `run.md`; cited source hashes in this finding were checked against them.

No external documentation or derived jar was used.

## Movement meaning

- Movement reachability check: `LocalPlayer#tick()` gates movement and outbound position packets on `hasClientLoaded()` (1.21.5 line 193, 1.21.8 line 199); `ClientPacketListener#tick()` sends `ServerboundPlayerLoadedPacket` only after level readiness (1.21.5 line 2484, 1.21.8 line 2591).

The server marks the player loaded on that packet (1.21.5/1.21.8 `ServerGamePacketListenerImpl#handleAcceptPlayerLoad`, 1.21.5 line 511, 1.21.8 line 522) and the paired server `handleMovePlayer()` methods process movement only when the loaded flag is true (1.21.5 line 969, 1.21.8 line 988).

Thus the initial absolute yaw correction arrives before movement can start, while movement arriving before loaded is ignored.

The configured angle and changed server-side construction pose do not result in a direct Player movement difference.

Whole-source hashes for the gating paths are recorded in `run.md`.
- Disposition: the source difference is an initial server-side pose change for a fresh profile with a nonzero shared spawn angle, but the client is corrected before movement readiness and the server rejects movement until readiness.

The candidate movement route is therefore unreachable under the paired vanilla login flow.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.5--1.21.8/findings/F-PLAYER-FRESH-LOGIN-SPAWN-ANGLE.md).
