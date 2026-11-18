# 1.21 to 1.21.1 raw pitch update and movement consumption

## Result

No source difference was found in the ordinary mouse-to-player pitch path or the checked immediate movement consumer. The exact decompiled `MouseHandler.java`, `Entity.java`, `LivingEntity.java`, `Player.java`, `Minecraft.java`, and `LocalPlayer.java` files have identical bytes between 1.21 Mojmap and 1.21.1 Mojmap (each listed source hash below matches both sides). This closes only this assigned slice; it does not establish whole-pair coverage.

## Exact source evidence

1.21, Mojmap:
- `decompiled_minecraft/1.21/mojmap/net/minecraft/client/MouseHandler.java`: `onMove` lines 207–223; `handleAccumulatedMovement` lines 225–253; `turnPlayer` lines 255–287.
- `decompiled_minecraft/1.21/mojmap/net/minecraft/world/entity/Entity.java`: `turn` lines 401–412; `setXRot` lines 3359–3369; `setYRot` lines 3351–3357; view-vector methods lines 1514–1537.
- `decompiled_minecraft/1.21/mojmap/net/minecraft/world/entity/LivingEntity.java`: `travel` lines 2091–2177, especially fall-flying branch lines 2151–2177.

1.21.1, Mojmap: corresponding files/members and line ranges are identical; the entire cited Java files match 1.21 by SHA-256.

### Mouse accumulation and call order

`onMove` accumulates differences in window coordinates only while the window is active, then updates the previous coordinates. `handleAccumulatedMovement` computes elapsed time, optionally sends UI events, and calls `turnPlayer` only when the mouse is grabbed and a player exists; both accumulated deltas are reset afterward. In ordinary non-smooth, non-scoping first-person movement, `turnPlayer` computes `s = sensitivity * 0.6F + 0.2F`, then `s^3`, then multiplies by `8.0`; it multiplies accumulated X/Y by that scale, applies the invert-Y sign, calls the tutorial mouse callback, then invokes `player.turn(dx, dy * sign)`.

### Entity turn/setter behavior

`Entity.turn(double dx, double dy)` casts `dy` to `float` before multiplying by `0.15F`; similarly it casts `dx` before multiplying. It first sets pitch to current pitch plus the computed pitch delta, then yaw to current yaw plus computed yaw delta, then clamps current pitch to `[-90.0F, 90.0F]`. It next adds the same deltas to old pitch/yaw, clamps old pitch to the same range, and finally notifies a vehicle if present. `setXRot` and `setYRot` each reject non-finite float values through `Util.logAndPauseIfInIde` and otherwise store the value. Ordinary `turn` has no modulo operation. The separate `setRot` helper applies `% 360.0F` to both axes before storing; it is not called by this mouse path.

## Bounded finite witness and movement meaning

With sensitivity 1.0, smooth camera off, not scoped, invert-Y off, active/grabbed mouse, and finite accumulated `dy = 1000.0` window-coordinate units, the ordinary scale is `((1.0 * 0.6F + 0.2F)^3) * 8.0` (approximately 4.096). `turnPlayer` passes approximately `4096.0` to `Entity.turn`; the float cast and `* 0.15F` produce approximately `614.4F`. From pitch 0, the finite setter stores that intermediate pitch and the subsequent clamp stores +90 degrees. The old pitch is advanced by the same delta and clamped to +90. This is a large but finite input: it exercises the actual high-delta clamp without relying on float overflow or a rejected setter write.

Pitch reaches real player movement when fall-flying: `LivingEntity.travel` reads `getLookAngle()` and `getXRot()` in its fall-flying branch and uses them to update vertical and horizontal velocity before moving the player. Therefore a turn reaching +90 can alter a gliding tick's trajectory. That effect is the same in both compared releases; this witness demonstrates the shared path and is not evidence of a 1.21→1.21.1 movement change. Ordinary ground travel in the inspected `LivingEntity.travel` branches does not use pitch.

## Immutable source identity

Canonical inputs are read-only decompiler outputs. 1.21 provenance: `decompiled_minecraft/1.21/mojmap/.decompile-provenance.json`, SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`; its pinned client JAR SHA-256 is `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`, and pinned Mojang `client.txt` SHA-256 is `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`.

1.21.1 readiness/provenance: `decompiled_minecraft/1.21.1/mojmap.ready.json` SHA-256 `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`; `mojmap.provenance.json` SHA-256 `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`; source manifest `mojmap.sources.sha256` SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; artifact manifest `artifacts.sha256` SHA-256 `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`. The ready manifest pins the 1.21.1 client JAR SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`.

Matching SHA-256 for each cited source file on both releases:
- `MouseHandler.java`: `824c52842483eb2899bd5a76c39701c3a616f249dcb64283da556dff4bcd50f7`
- `Entity.java`: `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`
- `LivingEntity.java`: `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`
- `Player.java`: `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`
- `Minecraft.java`: `67735674739c1602f6c852c8bfd6d4c07e6ba8e3228efc95abefb3bea6124fda`
- `LocalPlayer.java`: `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`
