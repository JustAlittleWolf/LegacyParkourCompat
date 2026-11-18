# 1.20.4 → 1.20.5 mouse-turn batching audit

## Frozen conclusion

Freeze scope is this assigned callback-to-player-turn operation only; this verdict does not claim full-pair discovery coverage.

**Accepted source difference: mouse input is applied to the player rotation on a different schedule.** In 1.20.4, every accepted cursor-move callback accumulates that callback's displacement and immediately calls `turnPlayer()`, which consumes the accumulator and calls `player.turn(...)`. In 1.20.5, callbacks only accumulate displacement and update the cursor position. `Minecraft.runTick` calls `handleAccumulatedMovement()` after its client-tick loop, so mouse callbacks polled at the end of one frame remain queued while the next frame's client tick(s) run. The rotation is applied only afterward. This changes the authoritative live pitch used by fall-flight travel.

This is specifically an input-to-player-turn scheduling change. `Entity.turn` itself is identical between these releases. This finding does not depend on `setRot`/`setXRot` modulo behavior, render interpolation, camera telemetry, or vehicle movement.

## Exact comparison

All paths below are relative to `decompiled_minecraft/<version>/mojmap/`.

### Callback accumulation and consumption

- **1.20.4 `net/minecraft/client/MouseHandler.java:187–221,224–266`.** `onMove` adds `x - xpos` and `y - ypos` when the mouse is grabbed and the window active, calls `turnPlayer()` immediately, then writes the new cursor coordinates. `turnPlayer` samples `Blaze3D.getTime()`, updates `lastMouseEventTime`, computes the turn while grabbed and active, clears both accumulators before the tutorial and player callback, and otherwise clears them in the inactive branch. The `Minecraft.runTick` call at `net/minecraft/client/Minecraft.java:1175` is an additional post-client-tick call; it does not defer the callback's already-applied player turn.
- **1.20.5 `net/minecraft/client/MouseHandler.java:187–203`.** `onMove` skips accumulation on `ignoreFirstMove`; otherwise, while the window is active, it adds each position delta without requiring the mouse to be grabbed, then updates `xpos`/`ypos`. It does not turn the player.
- **1.20.5 `net/minecraft/client/MouseHandler.java:205–267`; `net/minecraft/client/Minecraft.java:1152–1165,1189–1191`.** `runTick` executes its client-tick loop first, then calls `handleAccumulatedMovement` in the render section. That method samples time against `lastHandleMovementTime`; screen callbacks are gated on active window, no overlay, and nonzero accumulated delta. Player turning is gated on grabbed mouse and non-null player. The accumulator is cleared after the active-window block, including when the mouse is not grabbed or there is no player. `turnPlayer(deltaTime)` then applies the accumulated turn.
- **Polling boundary: `com/mojang/blaze3d/platform/Window.java:268–270`; `com/mojang/blaze3d/systems/RenderSystem.java:178–184` in 1.20.5.** `updateDisplay()` invokes `flipFrame()`, whose `pollEvents()` calls surround the buffer swap. Since `updateDisplay()` follows the render-stage `handleAccumulatedMovement()`, callbacks delivered there occur after the 1.20.5 accumulator flush; the next frame's client ticks precede its next flush. In 1.20.4 the callback itself calls `turnPlayer()` during that poll, immediately changing live player rotation before the next frame's client ticks.

### Sensitivity, order, and rotation writes

The two versions use the same scale calculation and branch math, but 1.20.4 evaluates it per cursor callback and 1.20.5 evaluates it once for the batch. With smooth camera off, both compute `sensitivity * 0.6F + 0.2F`, cube the result, and multiply by `8.0`; first-person scoping resets both `SmoothDouble`s and uses the cube without the factor of eight. Otherwise both reset the smoothers and use the factor of eight. With smooth camera on, both feed the accumulated displacement times that factor and elapsed time times that factor to `SmoothDouble.getNewDeltaValue`. Both read invert-Y after selecting the two deltas, report the unsigned turn to the tutorial, and call `player.turn(yaw, pitch * invertSign)`.

`net/minecraft/world/entity/Entity.java:398–410` (1.20.4) and `:400–412` (1.20.5) are byte-for-byte equivalent in behavior: cast pitch and yaw doubles to `float`, multiply each by `0.15F`, add to live X/Y rotation through setters, clamp **live** pitch to `[-90.0F, 90.0F]`, add those same deltas to `xRotO`/`yRotO`, clamp `xRotO`, then notify a vehicle if present. Thus the live pitch write uses per-call float rounding; interpolation fields are separate subsequent writes.

### Reachable next-tick movement consequence

In both versions, `LivingEntity.travel` has a fall-flight branch (`LivingEntity.java:2099–2125` in 1.20.4; `:2168–2194` in 1.20.5). It reads `getLookAngle()` and the authoritative `getXRot()`, converts pitch to radians, uses pitch-derived cosine and look direction in its velocity update, then sets velocity and moves. Therefore stale versus newly turned live pitch changes the actual next fall-flight travel calculation, not only the rendered view.

A finite ordinary witness is six one-pixel vertical cursor callbacks after cursor initialization, with mouse grabbed, active window, smooth camera off, no scoping, invert-Y off, sensitivity `0.5`, and the player fall-flying. At sensitivity `0.5`, the normal multiplier is exactly `1.0`. If the callbacks arrive in the end-of-frame poll, 1.20.4 applies six `Entity.turn` calls immediately; 1.20.5 has accumulated six pixels but runs the next client tick before applying them. With a starting live pitch of zero, 1.20.4's next fall-flight travel uses the turned pitch while 1.20.5's uses zero. Even after the batch is eventually applied, repeated float additions and one aggregated multiplication are not necessarily bit-identical: six `0.15F` increments yield float bits `1063675494`, while `6 * 0.15F` yields `1063675495` (one ULP apart). This numeric observation is a consequence of the preserved `Entity.turn` casts and multiplication order, not a separate global rotation-setter finding.

## Source and artifact identity

Canonical source roots were read-only. Both ready markers declare the exact version, `mojmap`, and `ready`; the ready marker's declared source-manifest and artifact-manifest hashes match the SHA-256 values of those manifest files. Relevant source hashes below also match their lines in the respective source manifests.

| Identity | 1.20.4 Mojmap | 1.20.5 Mojmap |
|---|---|---|
| `mojmap.ready.json` SHA-256 | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `4a587caa86e4ddd68ba63a5431417933dc4df4822e33aaf29ce77ebda53947c2` |
| `mojmap.provenance.json` SHA-256 | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` | `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242` |
| `mojmap.sources.sha256` SHA-256 | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` |
| `artifacts.sha256` SHA-256 | `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` | `f2dbd7a2e6c3fb452b0c28c93c9815e43ca24c179815f3d4fca34e484d465bb6` |
| Client artifact SHA-256 | `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` | `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` |
| Mojang client-mappings artifact SHA-256 | `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f` | `9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac` |
| Mapped client artifact SHA-256 | `d20c183016505cceecd33d508973e7ca6f2ee8cbd860f316592f4a09a96bc339` | `39494c7ed61b0ddda63b79ff2477c1a1c33d3770fa83321c2500db116d07efad` |

The ready-marker and provenance SHA values were independently computed from the files. Each source-file digest was matched to its entry in `mojmap.sources.sha256`.

| Compared source | 1.20.4 SHA-256 | 1.20.5 SHA-256 |
|---|---|---|
| `net/minecraft/client/MouseHandler.java` | `a75d99f45510c6689ddd7b241b28033b07ccbedc59e8a4288fc15d367bcc0b56` | `ed1248f1d62a927907e6e10fae87a0d4ab0a99d91e44a149aeba40d05fbda1e1` |
| `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| `net/minecraft/client/Minecraft.java` | `4c672a48906a76408bfbf77fda303d6cead7b22c6e4632aed7a5d8182e057b0a` | `755dc0ae2b95f1f32b876e249b88875fd3d04c6892b8412cfb8a7ad830ed901c` |
| `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `com/mojang/blaze3d/systems/RenderSystem.java` | `e6c0430820f306bffc8cb960cfb58139432aaa00713f5ae288a0445912122a56` | `5fc28f06bfee1bd19d3083e4d337fce6d10988b00af70c1ce995ede6b1e44eb1` |
| `com/mojang/blaze3d/platform/Window.java` | `69f52decff6770b48f044821df81a109d4103c47dfd349c48d9f98c6157fd372` | `69f52decff6770b48f044821df81a109d4103c47dfd349c48d9f98c6157fd372` |

The provenance manifests identify Mojmap runs with Gradle `9.7.1`, Java `25.0.3+9-LTS`, TinyRemapper `0.14.1`, and Vineflower `1.12.0`. Exact per-version batch/run inputs are recorded in those manifests. No source generation or network access was used.

## Coverage and limits

Examined both exact-release `MouseHandler.onMove`, the complete `turnPlayer`/`handleAccumulatedMovement` implementations, the `Minecraft.runTick` tick-to-render call order, the end-of-frame GLFW polling route, `Entity.turn`, and the fall-flight travel branch that consumes live pitch. Entity turn math and the flight formula are unchanged in the compared slices; the accepted movement-relevant difference is when turn is applied, with callback-versus-batch float grouping a secondary effect of that schedule. No gameplay or runtime test was performed.

## Phase Two: bound author-report review

**Reviewer:** `/root/c_mouse_turn_batching_1204_1205_audit`  
**Reviewed report:** `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-raw-pitch-setter-1204-1205-source/report.md`  
**Report SHA-256 verified before reading:** `45436c6764dc4abcf0ba333d80dc2110de686400944757fb73ca7681605120de`  
**Verdict binding:** accepted for the shared bounded claim that 1.20.4 applies player turns per mouse callback and 1.20.5 applies combined callback deltas during the later render-stage handler; it is consistent with the source evidence and identities above.

The author report separately compared `Entity.setXRot`: it found no setter or clamp-order change. It describes the setter as a finite-value guard followed by raw float assignment, with no modulo/wrap. This audit's claim remains exclusively the mouse scheduling and turn batching boundary; the unchanged raw setter is not an implementation target or competing finding. The report's source-derived witness (+200 then −200 vertical pixels, sensitivity 1.0, starting pitch 0, smooth camera off) is accepted as a second finite discriminator: per-callback turns in 1.20.4 clamp the intermediate positive pitch to +90 before the negative turn, while the 1.20.5 batch sums to zero. It reinforces the schedule finding independently of the small float-grouping witness above. The report also ties the resulting live pitch to the fall-flight branch's `getXRot()` and `getLookAngle()` reads; no render-interpolation inference is needed.

**Signed:** `/root/c_mouse_turn_batching_1204_1205_audit` — bounded report binding accepted; no changes to the frozen source verdict or its scope.
