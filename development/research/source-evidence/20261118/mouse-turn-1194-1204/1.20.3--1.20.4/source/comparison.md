# Mouse turn to local Elytra movement: 1.20.3 → 1.20.4

**Verdict: no source difference in the bounded callback-to-pitch-to-first-active-flight-move path.** No separate difference finding is warranted. The compared Java source bodies below have matching SHA-256 values in both Mojmap snapshots.

## Source identity and integrity

- **1.20.3, Mojmap:** `decompiled_minecraft/1.20.3/mojmap`. Its `.decompile-provenance.json` SHA-256 is `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f` (the assigned value). Pinned client SHA-256: `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`; Mojang client mapping input (`client.txt`) SHA-256: `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`.
- **1.20.4, Mojmap:** version root reports `status=ready`, `versionId=1.20.4`, `mapping=mojmap`. `mojmap.provenance.json` SHA-256 is `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` (the assigned value). `mojmap.sources.sha256` SHA-256 is `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`, matching the ready marker. Pinned client SHA-256: `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`; `client_mappings.txt` SHA-256: `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`.
- Each listed 1.20.3 Java-file digest matches the 1.20.3 provenance `sources` entry and the actual file; each 1.20.4 digest matches its source manifest entry and actual file. The complete file digests match across releases:

| Mojmap source | SHA-256 in both releases |
|---|---|
| `net/minecraft/client/MouseHandler.java` | `a75d99f45510c6689ddd7b241b28033b07ccbedc59e8a4288fc15d367bcc0b56` |
| `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| `net/minecraft/util/Mth.java` | `f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075` |

## Callback and turn math

Compared in both releases: `MouseHandler.java:187–266`, especially `onMove:187–222` and `turnPlayer:224–266`; `Entity.java:398–410` (`turn`) and `3305–3315` (`getXRot`/`setXRot`). The entire enclosing files match by hash.

For an ordinary callback from the active window, with `ignoreFirstMove` already false, captured mouse and active window, finite ordinary coordinate deltas, smooth camera off, and player unscoped, `onMove` adds `newX - xpos` and `newY - ypos` to the accumulated deltas, calls `turnPlayer`, then stores the new cursor coordinates. `turnPlayer` updates the event timestamp; the elapsed time does not enter the selected non-smooth calculation. With `s = sensitivity * 0.6F + 0.2F`, `c = s * s * s`, and `q = c * 8.0`, yaw input is `accumulatedDX * q`, pitch input is `accumulatedDY * q`. Both accumulators are zeroed before the tutorial callback and player turn. Invert-Y multiplies pitch by `-1` when enabled and `+1` otherwise.

`Entity.turn(yaw, pitch)` first casts pitch to `float` and multiplies by `0.15F`, then does the same for yaw. It calls virtual `setXRot(currentPitch + pitchDelta)`, virtual `setYRot(currentYaw + yawDelta)`, and then calls `setXRot(Mth.clamp(currentPitch, -90.0F, 90.0F))`. It separately adds the un-clamped pitch delta to `xRotO` and clamps `xRotO` to the same bounds; yaw history receives the yaw delta without this clamp. A riding entity additionally receives `vehicle.onPassengerTurned`; the reachable witness below is on foot.

`LocalPlayer`, `Player`, and `AbstractClientPlayer` declare no `setXRot` override in these snapshots. Dispatch therefore reaches the inherited `Entity.setXRot(float)`, which logs and discards non-finite values and otherwise assigns `xRot`. The chosen finite callback witness avoids the non-finite path. An ignored first cursor callback contributes zero because `onMove` first sets `xpos/ypos` to the callback coordinates; it is excluded from the nonzero witness.

## Reachable first active flight move

Compared in both releases: `LocalPlayer.java:188–205, 646–805` (tick/AI and Elytra start); `Player.java:1451–1473, 1506–1520` (travel dispatch and start gate); `Entity.java:2945–2951` (local control); `LivingEntity.java:2035–2130, 2511–2610, 2641–2666, 3086–3088` (travel, AI dispatch, flight gate/update and flag); and `Entity.java:3305–3315` (pitch read/write). These complete enclosing files have the equal hashes above.

A concrete witness is an awake, on-foot local player with an enabled Elytra, airborne and outside water, holding jump on a tick where `LocalPlayer.aiStep` passes its Elytra-start gates (`!abilities.flying`, not passenger, not climbable, and the jump edge is accepted). `Player.tryToStartFallFlying` additionally requires not grounded, not already fall-flying, not in water, and no levitation; it sets shared flag 7. `LocalPlayer.isEffectiveAi()` returns true, making the player's `LivingEntity.travel` local-control gate pass. `LocalPlayer.aiStep` then calls `super.aiStep`; `LivingEntity.aiStep` reaches `updateFallFlying` and `travel`. With abilities flight off and no swimming/fluid branch taking precedence, `Player.travel` delegates to `LivingEntity.travel`, whose `isFallFlying()` flag branch is active. The awake precondition avoids the sleeping pitch reset.

The first real move in that branch obtains `getLookAngle()` and `getXRot()`, converts pitch to radians as `pitch * (float)(PI / 180.0)`, derives horizontal look magnitude, and calculates the pitch-dependent lift/dive terms in source order. It updates velocity, applies `(0.99F, 0.98F, 0.99F)` damping, then calls `move(MoverType.SELF, deltaMovement)`. Thus this path can consume the callback-updated, clamped pitch. The active branch, its gates, intermediate operations, and move call are byte-identical between 1.20.3 and 1.20.4.

## Closure and residuals

Closed for this assignment: the selected ordinary captured/unscoped/non-smooth finite callback path; `Entity.turn` pitch/yaw operation order; virtual pitch setter resolution and clamp; and one reachable active Elytra move with relevant local-control, flight, fluid, swimming, passenger, creative-flight, and sleeping gates bounded above. The scoped and smooth-camera alternatives were inspected as callback branches and not treated as the ordinary witness. GLFW/device setup is outside scope.

This is a bounded endpoint comparison, not a full player-tick inventory, runtime-parity claim, or proof of intermediate patch behavior. Other movement paths and other flight modes are unexamined.