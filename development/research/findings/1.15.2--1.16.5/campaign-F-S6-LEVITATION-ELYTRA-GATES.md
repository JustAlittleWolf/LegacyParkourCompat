# F-S6-LEVITATION-ELYTRA-GATES: Levitation blocks elytra glide start and continuation in 1.16.5

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying`, lines 1578–1588.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying`, lines 1520–1530.

Difference: Under the same airborne, non-gliding, outside-water and usable chest-Elytra state, 1.15.2 tryToStartFallFlying permits a player with Levitation; 1.16.5 adds !hasEffect(LEVITATION) and rejects the local start. The separately observed continuation flag writer runs only on the server and remains a central source disposition.

Movement meaning: Client jump input -> LocalPlayer Elytra/jump gates -> Player.tryToStartFallFlying -> Player.startFallFlying / bit-7 write -> serverbound START_FALL_FLYING -> server tryToStartFallFlying recheck -> server updateFallFlying before travel -> tracked entity-data update to client.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S6-LEVITATION-ELYTRA-GATES.md).
