# 1.20.6 → 1.21 Mojmap: mouse turn and pitch setter

## Bounded result

No source difference found in the ordinary mouse-to-player rotation path examined here. `MouseHandler.handleAccumulatedMovement` gates the call on an active window, grabbed mouse, and non-null player, invokes `turnPlayer`, then resets accumulated deltas. `turnPlayer` computes sensitivity identically in both releases: `s = sensitivity * 0.6F + 0.2F`, `s³`, and `s³ * 8.0`; smooth-camera, scoped first-person, and normal branches retain the same products and order. It applies inverted-Y sign after those branches, calls tutorial `onMouse`, then calls `player.turn(dx, dy * sign)`.

`Entity.turn(double dx, double dy)` is byte-for-byte equivalent in its compared body: cast `dy` to float then multiply by `0.15F`; cast `dx` then multiply by `0.15F`; call `setXRot(current + pitchDelta)`; call `setYRot(current + yawDelta)`; clamp current pitch to `[-90.0F, 90.0F]`; add the deltas to old pitch and yaw; clamp old pitch; notify the vehicle if mounted. `setXRot` and `setYRot` also match: finite values are assigned as-is; non-finite values log/discard. Setters do not modulo or clamp angles. `setRot` / `absRotateTo` paths are separate from this ordinary mouse turn path and are not evidence of a mouse-turn difference.

For a finite large delta that remains finite after the `(float) delta * 0.15F` operations, current pitch saturates to ±90 degrees, old pitch independently saturates to the same range, and yaw is not wrapped by `turn` or its setters. For an input large enough that float conversion/multiplication becomes non-finite, the setter discards that current-angle write; the later clamp still runs on the stored angle. These operation and gate facts are shared by the two examined versions. No incoming-packet behavior is inferred.

The direct movement consumer of pitch located in both releases is `LivingEntity.travel`'s fall-flying branch: it reads `getLookAngle()` and `getXRot()` for the glide vector/angle terms. That consumer uses the same operation sequence in the checked slice, so this bounded comparison yields no adjacent-version movement-angle change. A normal grounded travel path does not consume pitch in this slice. Ray direction also follows the same `getLookAngle()` calculation, but interaction reach itself is outside this bounded movement finding.

## Exact source references

- 1.20.6, Mojmap: `net/minecraft/client/MouseHandler.java`, `handleAccumulatedMovement()` lines 205–233 and `turnPlayer(double)` lines 235–267; `net/minecraft/world/entity/Entity.java`, `turn(double,double)` lines 400–412 and `setYRot(float)` / `setXRot(float)` lines 3312–3330; `net/minecraft/world/entity/LivingEntity.java`, fall-flying branch of `travel` around lines 2168–2174.
- 1.21.0, Mojmap: `net/minecraft/client/MouseHandler.java`, `handleAccumulatedMovement()` lines 225–253 and `turnPlayer(double)` lines 255–287; `net/minecraft/world/entity/Entity.java`, `turn(double,double)` lines 401–413 and `setYRot(float)` / `setXRot(float)` lines 3351–3369; `net/minecraft/world/entity/LivingEntity.java`, fall-flying branch of `travel` around lines 2151–2157.

## Immutable bytes relied on

SHA-256 of exact canonical source files read:

- 1.20.6 `MouseHandler.java`: `ED1248F1D62A927907E6E10FAE87A0D4AB0A99D91E44A149AEBA40D05FBDA1E1`
- 1.20.6 `Entity.java`: `71CD6B9F6C002684154DCE11D3745E8714D82F13C8E1B3AA56743930131C18F3`
- 1.21 `MouseHandler.java`: `824C52842483EB2899BD5A76C39701C3A616F249DCB64283DA556DFF4BCD50F7`
- 1.21 `Entity.java`: `B81905C7879E2CC5C5063A41D865C4164AD919F156306705BE791D1017B99850`

The 1.21 Mojmap mapping-root has `.decompile-provenance.json`; its `sources` entries match the two source hashes above. The manifest pins the exact 1.21 client jar SHA-256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`, official client mappings SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`, and version metadata SHA-256 `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`; matching cached `.pin.json` sidecars were read.

The 1.20.6 Mojmap source tree is present and its relied Java files are hashed above, but no `.decompile-provenance.json` or `.minecraft-decompile-cache/1.20.6` pin files were present in the canonical checkout. Therefore, this report does not assert verified 1.20.6 input/mapping provenance; the 1.20.6 source identity needs its preparation owner’s pinned manifest/cache record before this comparison can be treated as fully source-accepted.

## Coverage boundary

Compared the complete mouse accumulation/dispatch and `turnPlayer` methods, `Entity.turn`, both rotation setters, and the fall-flying pitch consumer only. This is not a complete player movement inventory, does not establish an implementation cutover, and does not claim runtime parity.
