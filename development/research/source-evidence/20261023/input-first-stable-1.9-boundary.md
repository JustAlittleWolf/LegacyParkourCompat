# First stable 1.9 boundary supplement

This supplement adds the exact `1.9` source tree to the two immutable candidates already in this lane. It does not modify their revisions. The assigned `1.9` source is the first stable 1.9 release; compared with the preceding assigned stable source, `1.8.9`, both the yaw-conversion change and the flying-sneak caller change are already present in `1.9`. Thus both first appear at the 1.9 boundary, rather than first appearing in 1.9.4.

## Yaw conversion at the first stable boundary

1.8.9, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`, `updateVelocity(float sideways, float forwards, float scale)`, lines 844-860. The expression is `yaw * (float) Math.PI / 180.0F` for both `sin` and `cos`.
1.9, ornithe-feather: `1.9/ornithe-feather/net/minecraft/entity/Entity.java`, same member, lines 943-959. The expression is `yaw * (float) (Math.PI / 180.0)` for both calls.

The input threshold, square-root/minimum-length gates, `scale / length`, vector scaling order, trig calls, and X/Z additions remain the same. `MathHelper.sin` and `cos` use the same table-index expressions and 65,536-entry table in both versions (`1.8.9` lines 14-20 and 392-395; `1.9` lines 15-21 and 423-425).

A concrete source-derived state crosses the sine-table index boundary in the first stable 1.9 release. With `yaw = 0.0823974609375F`, `sideways = 0.0F`, `forwards = 0.98F`, `scale = 0.02F`, and zero initial X/Z velocity, the old expression yields `0.0014381071086972952F`; the 1.9 expression yields `0.0014381069922819734F`. `MathHelper.sin` indexes entry 15 in 1.8.9 and entry 14 in 1.9; the cosine index is 16399 in both. This input corresponds to one forward key after the shared `0.98F` travel multiplier, with the shared airborne `speedInAir = 0.02F` scale (`LivingEntity` 1.8.9 lines 87 and 1447-1450; 1.9 lines 112 and 1694-1698). The same representable player state therefore reads a different sine entry at the 1.9 boundary. This arithmetic example makes no later-position claim.

## Flying-sneak caller at the first stable boundary

1.8.9, ornithe-feather: `KeyboardInput.tick()` in `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, lines 13-37; `LocalClientPlayerEntity.mobTick()` in `.../LocalClientPlayerEntity.java`, lines 488-604.
1.9, ornithe-feather: `KeyboardInput.tick()` in `1.9/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, lines 13-49; `LocalClientPlayerEntity.mobTick()` in `.../LocalClientPlayerEntity.java`, lines 600-750.

Both keyboard implementations reduce horizontal input by `(float)(input * 0.3)` while sneaking. In 1.8.9, the local camera player's flying/sneaking branch only adjusts vertical velocity (lines 596-604); the reduced horizontal values continue into travel. In 1.9, the equivalent branch additionally assigns both inputs from `(float)(input / 0.3)` before its vertical descent (lines 710-715). The 1.9 override then calls `super.mobTick()` at line 750. In inherited `LivingEntity.mobTick`, `serverTickAi()` copies the adjusted local input to player movement fields before the shared `0.98F` travel multiplier and `moveRelative` call (1.8.9 lines 1424-1450; 1.9 lines 1671-1698). With an airborne local camera player, flight enabled, sneak held, and no item-use slowdown, a held horizontal key is restored from the sneak-reduced value before the same tick's travel. The double division and float cast are part of that operation.

## Exact evidence identity

The 1.8.9 source identity is provenance `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json` (SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`) with source-manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`. Its Feather inputs are `feather-gen2-1.8.9+build.2-mergedv2.jar` and `.tiny` (SHA-256 `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da` and `de2023ea2cca9921402fbfcfe6e475f41da4932ea6dbc609e35c505b76a32c63`).

The exact 1.9 source identity is `decompiled_minecraft/1.9/ornithe-feather/.decompile-provenance.json` (SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`). Its embedded source hashes were checked against all five relevant files on disk and match. It records the exact 1.9 Minecraft metadata input at `https://piston-meta.mojang.com/v1/packages/9178828493e41a24452841039710f05728c466d6/1.9.json` (SHA-256 `8d6f1e7f3a99f1236914b61e64eeade955ba21e8150563457142d3981aeee688`), Feather 1.9 metadata (SHA-256 `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371`), and `feather-gen2-1.9+build.2-mergedv2.jar` (SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`). The manifest records the pinned single-thread pipeline and Java 25.0.3 runtime.

Relevant 1.8.9 source SHA-256: `Entity.java` `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`; `LivingEntity.java` `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; `KeyboardInput.java` `a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd`; `LocalClientPlayerEntity.java` `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`; `MathHelper.java` `de0aa811e3ef80cfce9ec3cf8752627b8d2de5261c7e567b34ac1d611d167366`.

Relevant 1.9 source SHA-256: `Entity.java` `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`; `LivingEntity.java` `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb`; `KeyboardInput.java` `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; `LocalClientPlayerEntity.java` `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06`; `MathHelper.java` `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151`.

The first-boundary check is closed for these two source differences at exact stable release `1.9`. The 1.9 fall-flying/Elytra branch is a separate new-item feature and was not used to establish either result.
