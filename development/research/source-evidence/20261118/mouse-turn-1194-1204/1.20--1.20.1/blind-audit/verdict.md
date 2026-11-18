# Independent claim review: Mojmap 1.20 → 1.20.1

## Bound revisions

- Author draft: `source/comparison.md`, raw SHA-256 `c7636e0a6fd75718d25eff372326fe9f8a77cb37ed3fe819e4122041d78fba2e`.
- Blind source freeze: `blind-audit/frozen.md`, SHA-256 `364e11f7d7501e6b6e64b0350b075489b5b90a9ed3eca1344dcfd4547b893857`.
- Exact source pair and mapping family: official Minecraft IDs `1.20` and `1.20.1`, Mojmap.

The claimed raw artifact identities for both releases agree with the embedded/version-root provenance, ready data and pinned manifests. I recomputed the provenance hashes stated in the draft (`1.20` embedded provenance `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`; `1.20.1` version-root provenance `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa`). The draft's five movement source files and the additional scheduler sources below match their on-disk SHA-256, the 1.20 embedded source map, and the 1.20.1 source manifest; every listed old/new pair is byte-identical.

| Consumed source | SHA-256 in both releases |
| --- | --- |
| `net/minecraft/client/MouseHandler.java` | `702359936208db4060f390cf9443520389e987d27c2d3c5237130fea408589e4` |
| `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `net/minecraft/client/player/LocalPlayer.java` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |
| `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |
| `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| `net/minecraft/client/Minecraft.java` | `fd55374b9a1d51e4b09063a2803a999823668a6bbd49575ed7cb7b61aeb9b7a4` |
| `net/minecraft/util/thread/BlockableEventLoop.java` | `823f4da3c37d7e69cc7d90441081a30614ea9e0b722f67e482d50fb374293489` |
| `com/mojang/blaze3d/systems/RenderSystem.java` | `40c215a6a7c3cca0ceeb54693c6821ec7ee7c119f7ce80e10a5ee29209d17f94` |
| `com/mojang/blaze3d/platform/Window.java` | `9a5ff35935cb68ba68318c421b8d71e6aa40c0dcbea7c6bf1c7eef6faa7f4a0d` |

## Separate verdicts

### 1. Callback arithmetic and gates — ACCEPT

The draft accurately bounds the path to a matching-window cursor callback with captured mouse, active window, smooth camera off, and the scoping exception false. `MouseHandler.onMove` accumulates cursor differences only under the capture/active gate, calls `turnPlayer()` before updating prior cursor coordinates, and `turnPlayer()` clears accumulated values on its inactive branch. The active path computes the staged sensitivity factor (`sensitivity * 0.6F + 0.2F`, cube, then `* 8.0`), resets smoothing on the selected ordinary branch, consumes the accumulators, applies invert-Y only to the pitch argument, and calls `player.turn` only when a player exists. The scoped first-person branch is properly excluded; other camera modes with scoping false use the same ordinary branch.

The draft left callback scheduling unevaluated. I checked that scope: `MouseHandler.setup` wraps `onMove` in `minecraft.execute`; `Minecraft.run` establishes the game thread, `runTick` invokes `Window.updateDisplay`, `Window.updateDisplay` calls `RenderSystem.flipFrame`, and `flipFrame` polls GLFW events. On this same thread `BlockableEventLoop.scheduleExecutables()` is false and `execute` runs the callback inline. Thus `onMove`'s direct `turnPlayer()` is synchronous during event polling, after the current runTick's simulation and before the following client tick. The scheduler does not add a release difference; a cursor delta processed there can update pitch consumed by the next reachable player movement tick. This closes the draft's scheduler limitation for the stated ordinary local path; device plumbing remains excluded.

### 2. Virtual pitch setter and clamp — ACCEPT

`Entity.turn` has the exact reported sequence on both sides: cast incoming doubles to floats, multiply by `0.15F`, call virtual `setXRot(current + pitchDelta)`, set yaw, then call virtual `setXRot(clamp(current pitch, -90F, 90F))`; update old rotations afterward and clamp old pitch before vehicle handling. `Player`, `LivingEntity`, and `LocalPlayer` do not override `setXRot`. The inherited `Entity.setXRot` validates finiteness, logs/discards non-finite values, and stores finite values unchanged. The source file body is identical, and the draft preserves the operation order and float conversions.

### 3. First active flight move — ACCEPT, ELYTRA PATH ONLY

The draft's title and bounded witness are specifically the first fall-flying/Elytra move, not every meaning of flight. Its witness is reachable: a local, airborne, non-passenger player can produce a rising jump edge with enabled Elytra equipped and without climbable, water/lava, or Levitation blockers; `LocalPlayer.aiStep` invokes `tryToStartFallFlying`, the enabled shared flag permits `LivingEntity.updateFallFlying` to set active fall-flying state, and the subsequent delegated travel reaches the fall-flying branch in `LivingEntity.travel`. That branch reads the current pitch in its glide math and reaches `move(MoverType.SELF, ...)`. With the callback above occurring during the prior frame's event poll, this active move consumes the callback-updated pitch. The local tick, update gates, travel dispatch and movement body are all in byte-identical paired source files. No 1.20 → 1.20.1 delta appears in this witness.

The author's explicit residual for abilities-based flight is appropriate. My blind freeze's directly traced active-flight witness was the separate `abilities.flying` branch in `LocalPlayer.aiStep`/`Player.travel`; that branch is also no-delta, but it does not substitute for the draft's Elytra witness. The draft does not establish abilities-flight parity as part of its claim, nor passenger flight, all fluid/glide alternatives, other mouse modes, intermediate patch boundaries, or runtime parity.

## Review outcome

The exact draft is accepted for the three bounded claims above. The scheduler gap stated in the draft is resolved by this review and should be treated as reviewer evidence, not as a modification to either bound revision. No claim here closes whole-pair discovery coverage or runtime parity.