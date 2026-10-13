# F-S6-LEVITATION-ELYTRA-GATES: Levitation blocks elytra glide start and continuation in 1.16.5

- **1.15.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S6-LEVITATION-ELYTRA-GATES.md).
- **1.16.5**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S6-LEVITATION-ELYTRA-GATES.md).

## Difference

With an airborne player who is not already fall-flying or in water and has an enabled Elytra, 1.15.2's `tryToStartFallFlying` accepts the start even when Levitation is active. 1.16.5 adds `!hasEffect(MobEffects.LEVITATION)` to that gate, so the same state rejects the start. 1.16.5 also adds the same Levitation guard to `LivingEntity.updateFallFlying`: if the shared fall-flying bit is already set and the existing ground, passenger and Elytra checks pass, 1.15.2 retains the bit while 1.16.5 clears it on the server.

The reachable client jump path calls the start method and sends `START_FALL_FLYING` only when the start gate succeeds.

The server packet handler calls the same gate again and calls `stopFallFlying` on failure. `startFallFlying` sets shared-flag bit 7; `Entity.setSharedFlag` updates that bit in the synchronized flags byte. `SynchedEntityData.set` marks changed values dirty, the tracked server entity packages dirty data, and the client listener assigns it.

These paths match in 1.15.2 and 1.16.5, so the 1.16.5-only effect guards are the source of the eligibility difference.

## Movement meaning

Client jump input -> LocalPlayer Elytra/jump gates -> Player.tryToStartFallFlying -> Player.startFallFlying / bit-7 write -> serverbound START_FALL_FLYING -> server tryToStartFallFlying recheck -> server updateFallFlying before travel -> tracked entity-data update to client.

The active Levitation flag is a supplied effect-state input; effect production is not included. `Player.updatePlayerPose` reads fall-flying state to select the fall-flying pose in both versions, and `LivingEntity.travel` dispatches to the paired fall-flying equations already covered by S2-POSE-EPSILON and S3-FALL-FLYING.

Source proves that 1.16.5 refuses to start or maintain the fall-flying state while Levitation is active and that the shared flag is synchronized through the ordinary entity-data path.

It also proves that the subsequent pose and travel consumers read that state.

No position or velocity trajectory was observed or predicted here.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S6-LEVITATION-ELYTRA-GATES.md).
