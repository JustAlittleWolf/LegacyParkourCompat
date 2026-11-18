# Feather 1.9 → 1.9.1: direct local pitch and Elytra read

## Scope and result

Blind source-only comparison of the ordinary local camera-turn pitch update and the first stored-pitch read in a dry, active local Elytra movement branch. **No difference was found in these bounded operations.** This is no-difference coverage for these slices, not pair-complete coverage or an exact patch-introduction claim; no permanent difference finding is warranted from this work item.

## Pinned source identity and byte checks

Both canonical mapping roots are under `decompiled_minecraft/<release>/ornithe-feather/`. Each `.decompile-provenance.json` has the legacy accepted fields `pipeline`, `runtime`, `tools`, `inputs`, and `sources`. Both identify the `lpc-decompile-v1` pipeline and contain exact release/client and source-hash records.

- **1.9:** Feather Gen2 `1.9+build.2`, artifact SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`; Feather metadata `https://meta.ornithemc.net/v3/versions/feather/1.9`, SHA-256 `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371`; exact client URL ending in `2f67dfe8953299440d1902f9124f0f2c3a2c940f/client.jar`, SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`; release metadata URL `https://piston-meta.mojang.com/v1/packages/9178828493e41a24452841039710f05728c466d6/1.9.json`, SHA-256 `8d6f1e7f3a99f1236914b61e64eeade955ba21e8150563457142d3981aeee688`.
- **1.9.1:** Feather Gen2 `1.9.1+build.2`, artifact SHA-256 `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`; Feather metadata `https://meta.ornithemc.net/v3/versions/feather/1.9.1`, SHA-256 `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4`; exact client URL ending in `9bc7f02323d90b9385c1a5dbd47fb144a3fb8835/client.jar`, SHA-256 `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9`; release metadata URL `https://piston-meta.mojang.com/v1/packages/fdca0cf4bdab9959fce30413202a00a1bce35be5/1.9.1.json`, SHA-256 `b423c2ee9dc43f9204fea8bf8cbad1780c52fbb62b107dc948cc72b56491fd56`.

For every cited Java file, the on-disk SHA-256 matched the corresponding `sources` entry in that release's mapping-root provenance manifest:

| Source file | 1.9 SHA-256 | 1.9.1 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/entity/living/LivingEntity.java` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
| `net/minecraft/client/render/GameRenderer.java` | `d03fb41524c7b0ecf92696ae6aa8144fdce7a449f5e670a59b210d8f6b06a709` | `d03fb41524c7b0ecf92696ae6aa8144fdce7a449f5e670a59b210d8f6b06a709` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` |

## Ordinary direct local turn

In both releases, `GameRenderer.render` calls `player.updateLocalPlayerCamera(h, i * j)` in both smooth and ordinary mouse branches (1.9 lines 831–854; 1.9.1 same lines; the whole `GameRenderer.java` source hash is identical). `h` and `i` are `float`; `j` is `int`, so the supplied pitch delta `i * j` is `float`.

`Entity` declares `pitch` and `lastPitch` as `float` (1.9 lines 97–100; 1.9.1 identical source). `Entity.updateLocalPlayerCamera(float yaw, float pitch)` is byte-identical in both releases (lines 283–294): it snapshots old current pitch into float `f`, computes `(float)(this.pitch - pitch * 0.15)`, clamps that current pitch to `[-90.0F, 90.0F]`, then updates `lastPitch` by `this.pitch - f`; `lastYaw` is updated afterward. The unsuffixed `0.15` makes the product and subtraction double-width before the explicit cast back to float. The current-minus-snapshot difference and previous-pitch addition use float operands. The mount callback is after these writes and only runs when mounted.

Finite witness exercising clamp and previous-update order, identical in both releases: old current pitch `89.0F`, old previous pitch `50.0F`, input pitch delta `-20.0F` and yaw delta `0.0F`. The double product is `-3.0`; the cast current pitch is `92.0F`, then clamp stores `90.0F`; only then `lastPitch = 50.0F + (90.0F - 89.0F) = 51.0F`. Both source versions yield current `90.0F` and previous `51.0F`.

## First stored-pitch read in dry, active local Elytra movement

`LocalClientPlayerEntity.isLocallyControlled()` returns `true` in both releases (1.9 lines 443–445; 1.9.1 lines 444–446). In `LivingEntity.moveRelative`, after the local/server movement gate, the ordinary dry path passes the water and lava guards, then the active Elytra path is selected by `isFallFlying()` (1.9 lines 1290–1328; 1.9.1 lines 1294–1332). In this slice, both bounded method blocks compare exactly equal after the four-line upstream offset.

The first pitch-dependent read in that branch is `this.getLookVector()`. `LivingEntity.getLookVector()` calls `getRotationVector(1.0F)`; the exact-1.0 branch passes current `this.pitch` and `this.headYaw` to `Entity.getRotationVector(float,float)`, bypassing interpolation from `lastPitch` (1.9 `LivingEntity` lines 1803–1816 and `Entity` lines 1120–1125; 1.9.1 `LivingEntity` lines 1807–1820 with the same `Entity` source). Immediately after the look-vector read, the movement code separately computes `float i = this.pitch * (float)(Math.PI / 180.0)`. `isFallFlying()` reads flag 7 in both releases (1.9 lines 2038–2040; 1.9.1 lines 2042–2044). The look-vector and active Elytra pitch slices compare exactly equal across the pair.

**Movement meaning:** direct mouse turning preserves the same current/previous pitch update and numeric widths; in active local Elytra flight on a dry path, the first look-direction pitch comes from current stored player pitch, followed by a second current-pitch radians read. Neither operation changes between 1.9 and 1.9.1 in the cited sources.