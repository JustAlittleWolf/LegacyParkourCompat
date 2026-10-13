# 1.21.4 defers the local player movement tick until level readiness or timeout

1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, tick 2457-2459; aiStep 2680-2808.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 189-212.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 191-217.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, class declaration / field initialization 167-168.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, tick 2435-2454.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, tick 2441-2464.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/multiplayer/LevelLoadStatusManager.java`, LevelLoadStatusManager 17-44.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/multiplayer/LevelLoadStatusManager.java`, LevelLoadStatusManager 17-44.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/Minecraft.java`, runTick 1155-1290; tick 1724-1732.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/Minecraft.java`, runTick 1147-1285.

Difference: For a non-removed, non-passenger, non-frozen local player in an unpaused level, with `clientLoaded == false` and timeout greater than zero, 1.21.3 runs the ordinary inherited local-player tick. 1.21.4 runs only `tickClientLoadTimeout()` and returns without `super.tick()`. 1.21.4's timer starts at 60 and is decremented before the readiness check, so if readiness never arrives, the inherited tick is permitted when the timer first reaches zero. If readiness becomes true, the packet-listener tick sets the flag after that frame's entity-tick phase; local movement resumes on the next entity tick. The readiness status manager is present on both sides and uses the same player-chunk/render-section readiness conditions. The change is 1.21.4's use of that status to gate the local player and its 60-tick fallback. The receiving-level screen itself does not pause the world tick, so the gate changes a reachable tick path.

Movement meaning: `Minecraft.runTick()` -> `ClientLevel.tickEntities()` -> `tickNonPassenger(LocalPlayer)` -> `LocalPlayer.tick()` -> inherited `LivingEntity.tick()` -> `aiStep()` -> jump/travel. 1.21.4 gates this path on a local client readiness flag set by `ClientPacketListener.tick()` or by the timeout. The loaded packet is an external protocol notification; no server movement behavior is inferred here. Source proves 1.21.4 suppresses the ordinary local player/AI/movement tick while the load flag is false and the timeout remains positive. This defers input sampling and local travel during that interval.
