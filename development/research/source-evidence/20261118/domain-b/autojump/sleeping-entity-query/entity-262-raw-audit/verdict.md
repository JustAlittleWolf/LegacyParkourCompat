# Independent source audit verdict: 26.2 sleeping AutoJump input

Verdict: **ACCEPTED within the bounded vanilla producer claim.** The corrected representability conclusion is supported: ordinary vanilla 26.2 keyboard/mouse input cannot present a nonzero `ClientInput` movement vector to the local post-move AutoJump callback while the player remains in the sleeping pose.

## Immutable inputs and source identity

- Corrected claim/report: `domain-b-autojump/entity-262-raw-representability/result.md`
- Corrected report SHA-256: `E29036DED93BEA5D568FDAAA0DBB4256FB87AE8F5AE1F1CF9CAA5606EEAF804D`
- Canonical source: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/`
- Exact release / namespace: `26.2` / `unobfuscated`; provenance identifies Vineflower 1.12.0 and Java 25.0.3+9-LTS.
- Ready marker status: `ready`; source count: 7,055.
- Verified source-manifest SHA-256: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; all 7,055 listed file hashes recomputed with zero mismatches. The report's 19 individually named source hashes also match.

## Verified source chain

`LivingEntity.getDimensions(Pose.SLEEPING)` returns fixed `SLEEPING_DIMENSIONS` `(0.2F, 0.2F)` (`LivingEntity.java:191, 3738-39`). `LocalPlayer.canAutoJump()` has no sleeping guard and `isMoving()` checks `input.getMoveVector()` (`LocalPlayer.java:1121-33`), so the question depends on whether vanilla can supply that nonzero vector.

Bed use is not predicted client-side: `BedBlock.useWithoutItem` returns `SUCCESS_SERVER` on the client (`BedBlock.java:79-82`). The server's `startSleeping` sets sleeping pose and position and zeroes velocity (`LivingEntity.java:3791-95`); these dirty entity-data values are packed together (`SynchedEntityData.java:56-89`, `ServerEntity.java:341-46`). The client marshals the entity-data handler to its thread (`ClientPacketListener.java:632-37`, `PacketUtils.java:21-25`). Queued packet work runs before client ticks (`Minecraft.java:1164-84`).

During the client tick, `Gui.tick` opens `InBedChatScreen` for a sleeping local player before `level.tickEntities` (`Gui.java:94-108`, `Minecraft.java:1789-1809`). `Gui.setScreen(non-null)` releases mouse capture and all key mappings (`Gui.java:219-268`). Keyboard presses set gameplay mappings only when no screen is active (`KeyboardHandler.java:508-11, 550-59`); mouse gameplay mappings also require no screen or overlay (`MouseHandler.java:131-45`). Existing screens use the same `setScreen` path and continue blocking gameplay input.

`LocalPlayer.aiStep` calls `input.tick()` before its movement work (`LocalPlayer.java:766-95`); `KeyboardInput.tick` rebuilds the movement vector from currently held mappings (`KeyboardInput.java:23-36`). The local `move` override calls `updateAutoJump` after `super.move` (`LocalPlayer.java:985-93`). Thus no stale pre-sleep keyboard/mouse vector reaches the supported ordinary post-local-move callback.

## Bounded residual and exclusions

Residual only for a non-vanilla writer/injection that changes `ClientInput` or gameplay mappings while a screen is active, a mixin that alters the vanilla screen/input gates, or an external move piggyback that invokes the callback before the normal local input tick. These are outside ordinary vanilla input representability. NBT or externally injected input is not evidence of a vanilla producer route. No modern Scale shortcut, product implementation, runtime parity, or UI preservation claim is included. This verdict establishes source representability only; it does not claim implementation or runtime parity.