# Blind source audit: local pitch setter and dry Elytra read

**Pair / lane:** 1.11 → 1.11.1, exact-source blind audit, Feather (`ornithe-feather`).  
**Verdict:** No difference in the assigned pitch-setter math or the dry Elytra movement path's first current-pitch read. This bounded slice yields no finding.

## Exact source identity

Both ready markers identify the requested release and `ornithe-feather` source set. The 1.11 source manifest SHA-256 is `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`; its client pin is `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634`. Its Feather inputs are `feather-gen2-1.11+build.2-mergedv2.jar` (SHA-256 `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44`) and `feather-gen2-1.11+build.2.tiny` (SHA-256 `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0`).

The 1.11.1 source manifest SHA-256 is `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`; its client pin is `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6`. Its Feather inputs are `feather-gen2-1.11.1+build.2-mergedv2.jar` (SHA-256 `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928`) and `feather-gen2-1.11.1+build.2.tiny` (SHA-256 `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`).

Hashes below are SHA-256 of the complete source files, matching each release's `ornithe-feather.sources.sha256` entry:

| Source | 1.11 | 1.11.1 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |
| `net/minecraft/entity/living/LivingEntity.java` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` | `8b37fd286b2dac1b343bd708b80284b43bb31c61bafc63bdcf6620ba3a59065c` |
| `net/minecraft/client/render/GameRenderer.java` | `7b46d9bb3a0e78f3921dfbf75c0563befb2cfb521745b3235274f73cdc817d09` | `d8f54f58020e52b117c74cf248a1bac4fc541c4244340f6a0b43f5dbce245db5` |

## Setter behavior and finite witness

In `Entity.updateLocalPlayerCamera(float yaw, float pitch)`, both releases save the old current pitch in `f`, compute the new current pitch as `(float)(this.pitch - pitch * 0.15)`, clamp that current float with `MathHelper.clamp(..., -90.0F, 90.0F)`, then update `lastPitch` as `this.lastPitch + (this.pitch - f)`. The literal `0.15` is a double, so multiplication and subtraction are double precision until the explicit float cast; clamp bounds and state fields are floats. `lastPitch` has no separate clamp in this camera setter. The ordinary `updatePositionAndAngles` path instead clamps its float `pitch` parameter and copies that clamped value to both current and previous fields. `setRotation` then applies `% 360.0F`; after that path's ±90 clamp it does not change the value. These bodies and operation orders match across the pair.

A finite first-write witness for the camera setter is initial `pitch = lastPitch = 0.0F` and input pitch delta `-1000.0F`: the cast result is `150.0F`, the current clamp writes `90.0F`, and the previous-field delta update writes `90.0F`. This is a method-level finite witness, not a claim that a physical mouse event supplies that magnitude.

## Dry Elytra movement's first current-pitch read

`GameRenderer` calls `player.updateLocalPlayerCamera(h, i * j)` in both releases. In a local player's ordinary `PlayerEntity.moveRelative` path, the non-creative-flying case delegates to `LivingEntity.moveRelative`; `LivingEntity.tickMovement` invokes `this.moveRelative(this.sidewaysSpeed, this.forwardSpeed)`. In that method, the locally controlled/logical-update gate is followed by the water and lava gates and `isFallFlying()`. On a dry, locally controlled Elytra flight, the first pitch-dependent expression is `this.getLookVector()`; `LivingEntity.getLookVector()` calls `getRotationVector(1.0F)`, whose `tickDelta == 1.0F` branch passes **current** `this.pitch` to the rotation-vector calculation, without interpolating `lastPitch`. The next read is the explicit `float i = this.pitch * (float)(Math.PI / 180.0)`. Those gates, reads, and expressions are unchanged in 1.11.1.

Source locations (1.11; 1.11.1): `Entity.updateLocalPlayerCamera` 300–311; 304–315; `Entity.updatePositionAndAngles` 1024–1047; 1083–1106; `Entity.getRotationVector(float)` 1160–1175; 1219–1234; `GameRenderer` caller 862/866 in each; `LivingEntity.moveRelative` 1386–1439; 1388–1441; `LivingEntity.getLookVector` / override `getRotationVector` 1914–1926; 1916–1928; `LivingEntity.tickMovement` call to `moveRelative` 1801; 1803; `PlayerEntity.moveRelative` delegation 1385–1400; 1387–1402.

The audited claim is source-confirmed as no-difference coverage for these exact endpoints only; this does not establish intermediate patch boundaries or runtime parity.

## Phase-two report comparison

The supplied source report was rehashed before comparison and matches its authorized SHA-256, `fc5c1a24a9cb137620cc9b8cb9732a5baff0ae8bf5972b6d3f7788e083f4a453`. Its exact client/source-manifest pins and the overlapping `Entity.java` and `LivingEntity.java` hashes agree with the frozen verdict above. Its no-difference conclusion for the assigned operation and dry Elytra consumer is consistent with my independent source review.

The report adds a finite witness that clarifies the previous-field behavior: with current pitch `30.0F`, previous pitch `22.0F`, and finite input `Float.MAX_VALUE`, the double product remains finite; the cast value is clamped to `-90.0F`, then the previous update adds `-90.0F - 30.0F`, producing `-98.0F`. This is consistent with the reviewed setter expression and confirms that `lastPitch` is delta-adjusted after the current clamp, without its own clamp. My earlier equal-state witness remains valid but did not show this edge case.

The report also states the shared `MathHelper.clamp(float, float, float)` implementation and provides its hash. That helper-body assertion was not part of the frozen phase-one evidence I independently inspected; this comparison neither contradicts nor upgrades that separate claim. The phase-two comparison adds no new movement finding and does not expand the bounded coverage or runtime-parity claim.