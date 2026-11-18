# Blind source audit: mouse turn, 1.20.1 to 1.20.2

Disposition: bounded equivalence for the assigned slices; no movement difference found.

## Evidence identity

Both inputs are the canonical local read-only `decompiled_minecraft/<version>/mojmap` trees. Their version roots contain `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, `artifacts.sha256`, and movement diagnostics. Ready status, exact version IDs, Mojmap mapping selection, and embedded source/artifact manifest SHA-256 values were checked against the files. Provenance identifies Vineflower 1.12.0, Java 25.0.3+9-LTS, TinyRemapper 0.14.1, Mapping IO 0.9.1, batch `1.20.1,1.20.2,1.20.4,1.20.6`, and the exact Gradle 9.7.1 run.

| Identity | 1.20.1 | 1.20.2 |
| --- | --- | --- |
| Ready marker SHA-256 | `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed` | `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` |
| Provenance SHA-256 | `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa` | `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` |
| Source manifest SHA-256 | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbbe62887` |
| Artifact manifest SHA-256 | `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2` | `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54` |
| Client JAR SHA-256 | `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f` | `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda` |
| Mojmap mapping text SHA-256 | `67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d` | `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd` |
| Client Mojmap JAR SHA-256 | `221d543b9f80ca42faaaa8e3f7cebc4fe1879b8f7ba954bb6ca62f8250be8b77` | `3dce01cbbb74e367414c811440ca6e80bc2ec18284386720ce413f9aec0ba901` |

The source-manifest entries match the exact SHA-256 bytes of all consumed Java files:

| Relative source | 1.20.1 SHA-256 | 1.20.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/MouseHandler.java` | `702359936208db4060f390cf9443520389e987d27c2d3c5237130fea408589e4` | `a75d99f45510c6689ddd7b241b28033b07ccbedc59e8a4288fc15d367bcc0b56` |
| `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` |
| `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` |
| `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` |

Line references below are one-based in the consumed files.

## Callback and rotation slices

`MouseHandler.onMove`: 1.20.1 lines 175–210; 1.20.2 lines 187–222. For the matching-window callback, both initialize the first ignored position when `ignoreFirstMove` is set, then—when grabbed and the window is active—add `(newX - xpos)` and `(newY - ypos)` to accumulated deltas. They invoke `turnPlayer()` before updating `xpos`/`ypos`. No delta/order change was found. OS callback registration and device plumbing are outside this slice.

`MouseHandler.turnPlayer`: 1.20.1 lines 212–257; 1.20.2 lines 224–269. Both update `lastMouseEventTime` before branching. The active gate is `isMouseGrabbed() && minecraft.isWindowActive()`. In the requested smooth-camera-off, unscoped path, both reset the smooth-turn filters and calculate sensitivity as `s = sensitivity.get() * 0.6F + 0.2F`, `s3 = s * s * s`, `s8 = s3 * 8.0`; yaw/pitch inputs are accumulated X/Y times `s8`. First-person scoping uses `s3`; smooth camera uses `smoothTurn*.getNewDeltaValue(accumulatedDelta * s8, elapsed * s8)`. Both clear accumulated deltas before applying the invert-Y multiplier, notify the tutorial with un-inverted values, then call `player.turn(yaw, pitch * invertSign)` only when player is non-null. Inactive window/mouse clears both deltas. The requested path has no difference in constants, expression order, multiplication, reset or clear timing.

`Entity.turn`: 1.20.1 lines 395–408; 1.20.2 lines 397–410. Both cast pitch input to `float` and multiply by `0.15F`, then cast yaw input and multiply by `0.15F`; they call the virtual `setXRot(currentPitch + pitchDelta)`, then `setYRot(currentYaw + yawDelta)`, then call virtual `setXRot(clamp(currentPitch, -90.0F, 90.0F))`. They subsequently add the deltas to `xRotO`/`yRotO`, clamp only `xRotO`, and notify the vehicle if present. Operation order and float arithmetic are identical. `Entity.setXRot`: 1.20.1 lines 3273–3279; 1.20.2 lines 3303–3309. For `LocalPlayer` (Player → LivingEntity → Entity), no subclass override intervenes; Entity's setter rejects non-finite pitch with the existing log/pause path and otherwise writes `xRot`. There is no new clamp in the setter itself.

## Reachable movement witness: locally simulated fall flight

A finite ordinary captured mouse delta with a non-null local player reaches `LocalPlayer.turn` through the callback and active `turnPlayer` branch above. Entity's resulting pitch and yaw are read by `getLookAngle()` (`Entity.java`: 1.20.1 line 1970; 1.20.2 line 2004), which passes `getXRot()` and `getYRot()` to `calculateViewVector` in both versions.

For a locally controlled player with the fall-flying state active, `LivingEntity.travel` first passes the `isControlledByLocalInstance()` outer guard (1.20.1 lines 2004–2005; 1.20.2 lines 2038–2039), then reaches the `isFallFlying()` branch when the preceding water and lava branches do not match (1.20.1 lines 2068–2108; 1.20.2 lines 2102–2142). In that branch the look vector and pitch-derived radians are read, then the glide acceleration and horizontal alignment update velocity; `setDeltaMovement` and `move(MoverType.SELF, ...)` make this a real movement response. This entire branch, including expression order, float/double types, constants, sub-gates (`y < 0`, horizontal-look magnitude `> 0`, negative pitch, collision, client-side checks), and writes, is identical across the pair. Player's `travel` routes the non-creative-flight case to `super.travel`; its `abilities.flying && !isPassenger()` wrapper is identical in 1.20.1 lines 1446–1469 and 1.20.2 lines 1450–1473.

Thus the movement witness closes reachability without claiming a trajectory difference: the rotation produced by the callback feeds the same fall-flight movement equations on both sides. The scoped slices are bounded; this is not a full player-tick, collision-provider, device-plumbing, or pair-coverage audit. No claim is made about unexamined input dispatch or other travel modes.

## Frozen result

No difference in the assigned ordinary captured, unscoped, smooth-camera-off callback math/order, `Entity.turn` pitch setter/clamp path, or first locally active fall-flight move branch was found between exact Mojmap 1.20.1 and 1.20.2 sources. No finding is proposed from this bounded audit.