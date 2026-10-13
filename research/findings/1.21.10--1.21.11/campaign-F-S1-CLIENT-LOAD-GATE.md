# F-S1-CLIENT-LOAD-GATE: Local movement ticks wait for load readiness in 1.21.11

- **1.21.10**, mojmap: `1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 195-220.
- **1.21.11**, mojmap: `1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 211-35.

## Difference

1.21.10 local player decrements a 60-tick fallback timer at the beginning of every entity tick.

When that timer expires, `hasClientLoaded()` returns true even if the listener has not sent/received the level-ready notification, so the rest of `LocalPlayer.tick()` runs. 1.21.11 moved the flag to `ClientPacketListener` and `LocalPlayer.tick()` returns early until the listener's load tracker calls `notifyPlayerLoaded()` and sets the flag.

The two load paths are reachable while a client level exists and the client is unpaused: `Minecraft.tick()` ticks entities under those conditions without requiring the loading screen to be absent.

During a delayed player-chunk readiness interval, after 60 calls to 1.21.10's local tick gate but before 1.21.11 marks the client loaded, 1.21.10 runs `super.tick()` and its movement path while 1.21.11 skips those operations.

The 1.21.11 load tracker can remain pending until the player section is visible or its 30-second timeout; the 1.21.10 fallback is 60 local-player ticks.

## Movement meaning

`Minecraft.tick()` first advances multiplayer connection processing through `MultiPlayerGameMode.tick()`, then calls `ClientLevel.tickEntities()` when `level != null` and `!pause`. `ClientLevel` ticks a local player through `tickNonPassenger` if it is not a passenger or through `tickPassenger`/`rideTick` if it is.

Both paths invoke `LocalPlayer.tick()`, whose 1.21.10/1.21.11 loaded gates control the local superclass tick, input packet update and subsequent player movement work. 1.21.11's readiness flag is set by `ClientPacketListener.notifyPlayerLoaded()` when `LevelLoadTracker.isLevelReady()` returns true.

Source proves that the two versions may run or skip local player movement-tick work under the same delayed-load precondition.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.10--1.21.11/findings/F-S1-CLIENT-LOAD-GATE.md).
