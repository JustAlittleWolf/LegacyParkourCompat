# Blind source audit: 1.20.2 to 1.20.3

## Evidence identity

Read-only canonical sources: `decompiled_minecraft/1.20.2/mojmap/` and `decompiled_minecraft/1.20.3/mojmap/`.

- 1.20.2: `mojmap.ready.json` reports exact `versionId=1.20.2`, mapping `mojmap`, ready status, client SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`, source-manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`, and artifact-manifest SHA-256 `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54`. Both manifest hashes match the files. `artifacts.sha256` pins `1.20.2/client_mappings.txt` to `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd` and `1.20.2/client-mojmap.jar` to `3dce01cbbb74e367414c811440ca6e80bc2ec18284386720ce413f9aec0ba901`; the client artifact matches the ready marker.
- 1.20.3: source tree includes `.decompile-provenance.json` (SHA-256 `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`). It pins client `client.jar` SHA-256 `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`, official client mappings `client.txt` SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`, and exact version metadata `1.20.3.json` SHA-256 `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`. Provenance identifies the Mojmap pipeline and its tool/runtime identity.
- The four consumed Java files below match their respective 1.20.2 source-manifest entries and 1.20.3 embedded source hashes (and match computed SHA-256). `MouseHandler.java` and `LocalPlayer.java` have byte-identical bodies across these releases.

| Relative source | 1.20.2 SHA-256 | 1.20.3 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/MouseHandler.java` | `a75d99f45510c6689ddd7b241b28033b07ccbedc59e8a4288fc15d367bcc0b56` | `a75d99f45510c6689ddd7b241b28033b07ccbedc59e8a4288fc15d367bcc0b56` |
| `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |

## Bounded comparison

### Ordinary captured mouse callback and turn math

`MouseHandler.onMove` (both versions, lines 187–221) gates on the current window, then when captured and active accumulates `x - xpos` and `y - ypos` in that order. It calls `turnPlayer()` before replacing `xpos/ypos`. The full method bytes are identical.

`MouseHandler.turnPlayer` (both versions, lines 224–266) first samples time, computes elapsed time and updates `lastMouseEventTime`; only then checks captured and active. For smooth-camera-off and unscoped input, the scoping exception is false, so the ordinary `else` path resets both smoothers and computes `dx = accumulatedDX * factor`, `dy = accumulatedDY * factor`, with `sensitivity = options.sensitivity().get() * 0.6F + 0.2F`, `factor = sensitivity * sensitivity * sensitivity * 8.0`. The float constants/first expression retain float arithmetic before assignment to double; subsequent multiplication is double. It clears both accumulators before applying the integer Y inversion, calls the tutorial callback, then only when player is non-null invokes `player.turn(dx, dy * sign)`. The elapsed time is unused on this path but its state update precedes the captured/active gate. These bodies are byte-identical; no arithmetic, cast, gate or ordering delta is present.

Reachable witness: a captured, active local window; finite callback movement `(dx,dy)=(1,1)`; smooth camera off; not scoping; non-null local player; normal sensitivity `0.5`. The ordinary branch produces a finite factor of `1.0` and invokes `turn(1.0, 1.0)` when Y is not inverted. This is a concrete callback-to-player path, not a claim that all device configurations were tested.

### `Entity.turn` and pitch setter

`Entity.turn` is 1.20.2 lines 397–409 and 1.20.3 lines 398–410. Operation sequence is identical: cast `dy` to float and multiply by `0.15F`; cast `dx` and multiply by `0.15F`; invoke virtual `setXRot(getXRot()+pitchDelta)`; invoke virtual `setYRot(getYRot()+yawDelta)`; invoke virtual `setXRot(clamp(getXRot(), -90F, 90F))`; add the deltas to previous rotations; clamp previous pitch; then notify a non-null vehicle. The method body is unchanged despite other `Entity.java` changes.

The inherited `Entity.setXRot(float)` (1.20.2 lines 3303–3309; 1.20.3 lines 3309–3315) discards non-finite values after the existing log/pause call and otherwise assigns `xRot`. `LocalPlayer` declares no override; its entire source file is byte-identical. Calls remain virtual, and clamping still follows the first setter call. No pitch setter, clamp, cast, or previous-rotation delta is source-confirmed here.

### First active elytra movement branch

The relevant first real flight movement is `LivingEntity.travel(Vec3)`'s `isFallFlying()` branch: 1.20.2 lines 2038–2141; 1.20.3 lines 2035–2138. The outer `isControlledByLocalInstance()` gate and ordered fluid branches precede it; water and lava branches take precedence, and the branch requires `isFallFlying()`. `LivingEntity.isFallFlying()` returns shared flag 7 on both versions (1.20.2 lines 3084–3086; 1.20.3 lines 3086–3088). A controlled local player with that flag set and neither preceding fluid branch active reaches it.

The active flight math and order are equivalent line-for-line: check slow-fall distance; read velocity and current look vector; convert current pitch to float radians; compute horizontal/look lengths and cosine; update vertical velocity; conditionally add dive lift when new vertical velocity is negative and look-horizontal length is positive; conditionally add pitch-up lift when pitch is negative and horizontal look is positive; conditionally steer toward look direction when horizontal look is positive; multiply velocity by `(0.99F,0.98F,0.99F)`, store it, then move with `MoverType.SELF`. Expressions, constants, float/double boundaries, conditions and writes are unchanged. `Entity.getLookAngle()` remains `calculateViewVector(getXRot(),getYRot())` (1.20.2 lines 2004–2006; 1.20.3 lines 2005–2007); the movement therefore consumes the rotation set by the preceding virtual `turn` calls. `Entity.isControlledByLocalInstance()` has the same local-player/effective-AI predicate in both sources (1.20.2 lines 2939–2945; 1.20.3 lines 2945–2951).

The post-move collision-damage and landing flag writes are guarded by `!level().isClientSide`; they are not part of client movement parity. The no-gravity, fluid travel alternatives, grounded movement, other `LivingEntity.travel` tails, input/device plumbing, mouse timing/smoothing path, scoping exception, callback registration and packet/server behavior are residual to this bounded audit.

### Direct flight math dependencies

Two called helpers are also checked because their classes differ elsewhere in the pair. `Entity.checkSlowFallDistance()` is unchanged (1.20.2 lines 2312–2316; 1.20.3 lines 2313–2317): when current vertical velocity is above `-0.5` and fall distance exceeds `1.0F`, it writes `1.0F`. `Entity.calculateViewVector(float,float)` is unchanged (1.20.2 lines 1458–1466; 1.20.3 lines 1459–1467), including float degree conversions and `Mth.cos/sin` order. `Mth.sin/cos` (1.20.2 lines 42–48; 1.20.3 lines 47–53) and the `SIN` table initialization (1.20.2 lines 25–30; 1.20.3 lines 27–35) are identical. The used `Vec3` methods—componentwise `add`, `multiply`, `length`, and `horizontalDistance`—have identical bodies (1.20.2 lines 88–94, 129–151; 1.20.3 lines 88–94, 136–158). Their containing files have other changes, so equivalence is limited to the cited bodies.

| Relative source | 1.20.2 SHA-256 | 1.20.3 SHA-256 |
| --- | --- | --- |
| `net/minecraft/util/Mth.java` | `8a16560d3dd84853a2caaa658d6a106ea2e7f4e04d5cd6ed4d8f76839cda9dce` | `f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075` |
| `net/minecraft/world/phys/Vec3.java` | `2ed588cbbb5bf85f847ba21242fafa2d627bc25213db7b983eaab58297d67fab` | `dc05337c94e64d5c137138ef98b7f10768a420af23bffb0812b68a8320c7c813` |

## Frozen verdict

For the assigned ordinary finite local callback path, `Entity.turn` including virtual pitch setter/clamping, and the active controlled elytra travel branch with its direct movement gates and look-angle dependency, exact Mojmap source shows bounded equivalence from 1.20.2 to 1.20.3. No movement difference is frozen for these slices. This does not close the pair's full movement inventory or assert equivalence outside the stated paths and residuals.