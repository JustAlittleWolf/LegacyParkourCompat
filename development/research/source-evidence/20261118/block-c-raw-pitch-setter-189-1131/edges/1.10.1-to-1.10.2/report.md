# 1.10.1 → 1.10.2: ordinary local-turn pitch

**Result:** no source change in the assigned direct-turn operation. This is a bounded source comparison, not a full-pair coverage freeze and not an accepted movement finding.

## Exact evidence

Both sides are Ornithe Feather sources under `decompiled_minecraft/<version>/ornithe-feather/`.

- **1.10.1 inputs:** the nested `1.10.1/ornithe-feather/.decompile-provenance.json` is the older-format pin. It records Mojang 1.10.1 metadata SHA-256 `9f61acf31fe84987954b2bdb9f839b57047d5938a8ed4f673f380a1d74ad041f`, Feather metadata SHA-256 `8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a`, and mapping artifact `feather-gen2-1.10.1+build.2-mergedv2.jar` SHA-256 `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`. Nested provenance file SHA-256: `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`.
- **1.10.2 inputs:** version-root `1.10.2/ornithe-feather.provenance.json` pins version `1.10.2`, namespace `ornithe-feather`, and Feather Gen2 build 2 JAR/Tiny inputs. The version-root `artifacts.sha256` pins `client.jar` at `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`, mapping JAR at `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`, and Tiny file at `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1`. Provenance file SHA-256: `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6`. The source-manifest SHA-256 is `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; the ready marker and verification record identify 1.10.2 and report identical published source files.
- The audited `Entity.java`, `LivingEntity.java`, and `GameRenderer.java` file hashes match on both sides: respectively `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`, `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`, and `35467c3723b9b50bb7e25ee439b8262d091cee12a1b94881f25fe90d35a83c52`. These agree with the older nested source map and newer source manifest.

## Direct local turn

In both releases, `GameRenderer.render` calls `player.updateLocalPlayerCamera(h, i * j)` for the ordinary non-smooth-camera path (`net/minecraft/client/render/GameRenderer.java:832–855`; invocation at 854). Mouse Y is scaled by the sensitivity cubic and optionally inverted before that call; there is no pitch clamp in this caller.

`Entity.updateLocalPlayerCamera(float yaw, float pitch)` is identical in both releases (`net/minecraft/entity/Entity.java:291–302`). It saves the old pitch in float `f`, writes yaw first, then performs the first new-pitch write as `(float)(this.pitch - pitch * 0.15)`. The decimal literal `0.15` is a `double`, so the multiply and subtraction are evaluated at double width before the cast back to float. It clamps that newly written pitch to `[-90.0F, 90.0F]`, then updates `lastPitch` with the clamped-current minus saved-old pitch; `lastYaw` follows. The method does not clamp the old pitch before the arithmetic. A mount look callback runs only after these writes when `mount != null`.

A finite rounding witness for the literal-width distinction (not a release difference): with float inputs `oldPitch = -89.98442077636719F` and `pitchDelta = -29.97209930419922F`, the source's double-literal expression casts to `-85.48860931396484F`; the hypothetical all-float `.15F` expression gives `-85.48860168457031F`. Both are inside the clamp, so the difference survives it. Both releases contain the double-literal form, so this witness establishes why the literal and cast matter, not a 1.10.1→1.10.2 delta.

## Active dry, non-passenger Elytra path

A locally controlled player reaches inherited living travel: `LocalClientPlayerEntity.isLocallyControlled()` returns true (`LocalClientPlayerEntity.java:457–460`), and `LivingEntity` travel calls `flyingTick()` then `moveRelative(...)` (`LivingEntity.java:1742–1748`). `flyingTick()` retains fall-flying only when flag 7 is set, the player is airborne and not riding, and the chest equipment is a usable Elytra (`LivingEntity.java:1754–1772`). This is the non-passenger/active-Elytra gate; client-side flag maintenance is left to synchronized state.

For a dry player in that active state, `moveRelative` passes its water/lava gates and enters the `isFallFlying()` branch (`LivingEntity.java:1332–1342`). It reads the current `this.pitch` both in `getLookVector()` and in radians `i = this.pitch * (float)(Math.PI / 180.0)`. `getLookVector()` resolves to the current rotation vector at tick delta `1.0F` (`LivingEntity.java:1852–1865`; `Entity.java:1138–1153`); pitch affects its vertical and horizontal components. The Elytra branch then uses `cos(i)` for lift/drag, `sin(i)` for the upward-pitch pull-up, adjusts velocity, applies `0.99F/0.98F/0.99F` drag, and moves (`LivingEntity.java:1341–1371`). Therefore the current pitch produced by the unchanged direct-turn arithmetic is read by actual active dry non-passenger Elytra movement in both versions.

## Compared scope and disposition

- Compared: direct mouse-to-player pitch input, write/clamp/history ordering, and the current-pitch path through active dry Elytra travel in `Entity`, `GameRenderer`, and `LivingEntity`.
- Result: all three whole source files are byte-identical across the pinned pair; no candidate delta in this slice.
- Not claimed: complete reachable-tick inventory, dependencies beyond this trace, independent coverage audit, or full-pair freeze.

