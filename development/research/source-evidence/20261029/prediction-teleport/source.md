# Client received state and local-player responses: 1.21.11 → 26.1.2

## Exact source identity

Compared `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.11\mojmap` with `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.2\unobfuscated`, read-only. The sidecar provenance and ready markers identify exact version IDs `1.21.11` / `26.1.2`, namespaces `mojmap` / `unobfuscated`, and status `ready`. Their source-manifest SHA-256 values match the ready markers: `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` and `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`. Exact relevant source hashes are recorded in the manifests; key evidence:

- `ClientPacketListener.java`: 1.21.11 `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e`; 26.1.2 `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82`.
- `ClientLevel.java`: 1.21.11 `e76d09de5acad450062d5acb98e76ff032ecb179fef0f7af434e4148f84183a2`; 26.1.2 `6e46db964229bf53bb88c33140cfa9acb5423dbed11a7c721b5b8bf9a7424de7`.
- `prediction/BlockStatePredictionHandler.java`: 1.21.11 `39914e30a1c4cc8a5a6e53a7032e58153c9bd7000bb7def126229ab4cf854728`; 26.1.2 `3436c1a040b55036fc1b1c35bb99fe811e799d013503d65143f3edb77cd83a21`.

The 26.1.2 names are paired by source content and direct packet `handle` callbacks into `ClientGamePacketListener`, not by online mappings. No unresolved identity ambiguity remained for the operations below; this does not map classes outside this bounded slice.

## Difference found

**Server position correction now marks the current block-prediction sequence as a teleport boundary before later block acknowledgments reconcile.**

- 1.21.11 Mojmap: `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleMovePlayer` (line 776) applies the `ClientboundPlayerPositionPacket` through `setValuesFromPositionPacket`, then sends teleport acceptance and the current movement packet. `net/minecraft/client/multiplayer/prediction/BlockStatePredictionHandler.java`, `endPredictionsUpTo` (line 37), passes every saved `playerPos` into `ClientLevel.syncBlockState` (line 182). If the authoritative block state differs and collides with the current player, `syncBlockState` calls `Player.absSnapTo(savedX, savedY, savedZ)`.
- 26.1.2 unobfuscated: `ClientPacketListener.java`, `handleMovePlayer` (line 796) performs the same position response and sends, then calls `this.minecraft.level.getBlockStatePredictionHandler().onTeleport()` (line 806). The handler records `lastTeleportSequence = currentSequenceNr` (`BlockStatePredictionHandler.java`, line 66). During later `endPredictionsUpTo` (line 38), it passes a saved `playerPos` only when `lastTeleportSequence < acknowledgedSequence`; otherwise it passes `null`. `ClientLevel.syncBlockState` (line 187) now snaps only when `playerPos != null`, while retaining the same state difference, collision and current-level gates.
- Reachable consequence: if an outstanding predicted block update later reconciles after a server position correction, and its corrected block state differs and collides with the local player, 1.21.11 can move the player back to the position saved with that prediction. 26.1.2 suppresses that stale snap while the acknowledgment sequence is at or before the recorded teleport sequence. If the acknowledgment is later than the teleport sequence, the saved position remains eligible. The changed behavior is a local-player position response to prediction reconciliation; the block state itself is still synchronized normally.

## Compared with no movement difference in this slice

- Player position/rotation correction and teleport entity handlers: `ClientPacketListener.handleMovePlayer`, `setValuesFromPositionPacket`, `handleRotatePlayer`, `handleTeleportEntity`, and `handleEntityPositionSync`. Relative-coordinate composition, pitch clamp, rotate-delta math/order, interpolation distance threshold (`4096.0` squared), local-authority gates, passenger updates, on-ground writes, and position/velocity/rotation write order match. The new prediction-boundary action above is the sole movement-relevant delta found here.
- Packet shapes/codecs: `ClientboundPlayerPositionPacket`, `ClientboundPlayerRotationPacket`, `ClientboundEntityPositionSyncPacket`, and `ClientboundTeleportEntityPacket` retain their field order and codec composition. `PositionMoveRotation.STREAM_CODEC` still sends two `Vec3.STREAM_CODEC` values followed by yaw and pitch floats; `calculateAbsolute` retains its arithmetic order and gates.
- Velocity and explosion responses: `handleSetEntityMotion` still checks that the entity exists and calls `lerpMotion`; `handleExplosion` still applies the optional player knockback through `addDeltaMovement`. The low-precision vector codec remains the same `LpVec3` read/write algorithm (`readLpVec3`/`writeLpVec3` in 1.21.11; `Vec3.LP_STREAM_CODEC` in 26.1.2). The 26 accessor change from `level.random` to `level.getRandom()` affects sound pitch only.
- Player-state inputs: `handlePlayerAbilities`, `handleUpdateMobEffect`, `handleRemoveMobEffect`, `handleUpdateAttributes`, `handleSetEquipment`, `handleSetPlayerInventory`, and `handleSetHeldSlot` preserve the same target, validation/slot gates, and writes/order in the compared handlers.
- Respawn and vehicle paths: `handleRespawn` retains the same `shouldKeep((byte)2)` position/velocity/rotation path, `shouldKeep((byte)1)` attributes path, and player recreation/state-copy order. `handleMoveVehicle` retains its local-authoritative-root-vehicle gate and snap/send order. No vehicle simulation or server-side packet producer behavior was examined.

## Coverage and limits

This closes only the assigned direct client packet/local-player state-response partition for the named handlers and dependencies. No server packet producers, other lanes, intermediate releases, product code, runtime behavior, test/build/game launch, or pair-wide movement inventory was examined. No runtime or pair-freeze claim is made.
