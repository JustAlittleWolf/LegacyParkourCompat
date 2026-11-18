# Independent source audit: ordinary local pitch turn, Feather 1.9 → 1.9.1

**Verdict: no difference in the audited operation.** The direct local camera-turn pitch update, its current/previous pitch ordering, finite overrange handling, and the current-pitch read reached by a dry active local Elytra movement path are source-identical across this pair. This closes only the assigned pitch slice, not the complete pair inventory or pair freeze.

## Exact source identity

The exact nested roots are `decompiled_minecraft/1.9/ornithe-feather/` and `decompiled_minecraft/1.9.1/ornithe-feather/`. Each contains `.decompile-provenance.json`; the version-root marker is absent, which is permitted by the documented legacy schema and does not invalidate these nested manifests.

| Release | Nested manifest SHA-256 | Feather mapping input (pinned URL; SHA-256) |
| --- | --- | --- |
| 1.9 | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | `https://maven.ornithemc.net/releases/net/ornithemc/feather-gen2/1.9+build.2/feather-gen2-1.9+build.2-mergedv2.jar`; `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` |
| 1.9.1 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `https://maven.ornithemc.net/releases/net/ornithemc/feather-gen2/1.9.1+build.2/feather-gen2-1.9.1+build.2-mergedv2.jar`; `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` |

Both manifests declare the same pinned decompiler pipeline and runtime. The listed source hashes were checked against the bytes currently at each manifest's source paths before authoring this verdict; every comparison matched.

| Source relative to each mapping root | 1.9 SHA-256 | 1.9.1 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/entity/living/LivingEntity.java` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
| `net/minecraft/client/render/GameRenderer.java` | `d03fb41524c7b0ecf92696ae6aa8144fdce7a449f5e670a59b210d8f6b06a709` | `d03fb41524c7b0ecf92696ae6aa8144fdce7a449f5e670a59b210d8f6b06a709` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |

## Compared behavior

- **Reachable direct turn:** `GameRenderer.java:831–854` passes mouse deltas to `player.updateLocalPlayerCamera` under the focused/input-active gate, with the same normal and smooth-camera call order in both releases. `Entity.java:283–294` is byte-identical. It snapshots `pitch` into float `f` and `yaw` into float `g`; updates yaw as `(float)(yaw + yawDelta * 0.15)` and pitch as `(float)(pitch - pitchDelta * 0.15)`; clamps current pitch; then advances `lastPitch` by `(clamped pitch - saved old pitch)`, advances `lastYaw` by `(new yaw - saved old yaw)`, and finally calls the mount look callback when mounted.
- **Arithmetic and overrange:** The literal `0.15` is double, so each delta multiplication and enclosing addition/subtraction is evaluated as double before the explicit float cast. The differences used to advance `lastPitch` and `lastYaw` are float operations. A finite float pitch delta scaled by `0.15` remains representable as finite float, while a resulting pitch outside the legal range is saturated by `MathHelper.clamp` (`MathHelper.java:80–85`). The identical branch implementation returns NaN unchanged because both comparisons are false; ordinary finite overrange input clamps to `-90.0F` or `90.0F`. No pitch wrapping or rejection is added.
- **First active dry local Elytra current-pitch read:** `LivingEntity.moveRelative` enters the Elytra branch only after the locally controlled or logical-side movement gate, the water and lava gates, and `isFallFlying()`. On the dry local path, the first pitch-dependent operation is `getLookVector()` (`LivingEntity.java:1804–1806` in 1.9; `1808–1810` in 1.9.1), which calls `getRotationVector(1.0F)`. That exact-1.0 branch reads current `this.pitch` (`1809–1816` / `1813–1820`) rather than interpolating from `lastPitch`. The Elytra branch then explicitly reads current `this.pitch` again to form radians (`moveRelative`: 1.9 `1298–1305`; 1.9.1 `1302–1309`). `moveRelative`, `getLookVector`, and `getRotationVector(float)` have the same audited operation order; the line offset comes from earlier source changes in `LivingEntity`, not this path.

No accepted difference is supported for this assigned operation and boundary. No implementation, source generation, build, test, or runtime check was performed.

## Phase-two independent audit of the frozen source report

The assigned source input `c-raw-pitch-setter-190-191-source/report.md` matched its frozen SHA-256 exactly: `27c8622d839e37aab97ea0ba1628d637b2e7d728410fd3f3cb1c187fc3349b3a`. Its bounded no-difference claim is supported by the exact Feather sources and agrees with the verdict above; it does not claim pair-complete coverage or an exact intermediate introduction boundary.

I rechecked the report's additional `LocalClientPlayerEntity.java` provenance entries against the current file bytes in both mapping roots; both matched their nested manifests. The reported `isLocallyControlled()` overrides return `true` in both releases. The report's source-hash table also matches the manifest entries for `Entity.java`, `LivingEntity.java`, and `GameRenderer.java`. In the Elytra call chain, `getLookVector()` supplies `1.0F`; `getRotationVector(1.0F)` passes current `this.pitch` to `Entity.getRotationVector(float,float)`, while non-1.0 interpolation reads `lastPitch`. The immediately following `moveRelative` statement independently reads current `this.pitch` for radians. The cited local gate, dry water/lava path, and `isFallFlying()` branch remain consistent with the source.

The finite witness is arithmetically correct: from current pitch `89.0F`, previous pitch `50.0F`, pitch delta `-20.0F`, and yaw delta `0.0F`, the unsuffixed `0.15` multiplication gives `-3.0` in double precision; current pitch becomes `92.0F`, clamps to `90.0F`, then previous pitch advances to `50.0F + (90.0F - 89.0F) = 51.0F`. No substantive correction to the frozen source report is needed. This phase reviewed the report without modifying it.