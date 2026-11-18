# Blind source trace freeze — swim pitch vertical writer

**Auditor:** independent source-only boundary audit, signed: Codex worker `/root/c_swim_pitch_vertical_12111_261_boundary_audit`.
**Pair:** 1.21.11 Mojmap → 26.1 published unobfuscated source.
**Freeze:** trace recorded before reading the author report. No wiki inventory, product implementation, product Git history, or report contents informed this trace.

## Evidence binding

- `decompiled_minecraft/1.21.11/mojmap.ready.json`: SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; says ready, exact version `1.21.11`, Mojmap, source-manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`, client SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`.
- `decompiled_minecraft/1.21.11/mojmap.provenance.json`: SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; pins exact version, namespace, run, tooling and cache locations.
- `decompiled_minecraft/1.21.11/mojmap.sources.sha256`: SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.
- `decompiled_minecraft/26.1/unobfuscated.ready.json`: SHA-256 `954126940593c1e1e4b987bfd7079452e0d6283c3d1d97a02693f0319f66f0f5`; says ready, exact version `26.1`, published unobfuscated, source-manifest SHA-256 `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`, client SHA-256 `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840`.
- `decompiled_minecraft/26.1/unobfuscated.provenance.json`: SHA-256 `6567d3aaf2940e0350860adf0f36b52ee65bd2b23e2ac5a6696ccd70739d3e5b`; pins exact version, namespace, run, tooling and cache locations.
- `decompiled_minecraft/26.1/unobfuscated.sources.sha256`: SHA-256 `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`.
- Both declared raw cache roots were absent at audit time: the 1.21.11 staging cache path in provenance and `build/movement-campaign-2026-10-07/artifacts` for 26.1. This is separate from the intact ready source trees; the audit relies on the published source byte hashes below, not raw client/cache bytes.

### Relied source bytes (each current file hash matched its corresponding `.sources.sha256` entry)

| Version | Relative source | SHA-256 |
|---|---|---|
| 1.21.11 | `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| 1.21.11 | `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 1.21.11 | `net/minecraft/world/entity/LivingEntity.java` | (source file read; manifest-listed; not used to assert a difference) |
| 1.21.11 | `net/minecraft/util/Mth.java` | `1b547c6b896c9b149d4bc03e3ed106ece485d7ac481a792ae30f600d0239726c` |
| 26.1 | `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| 26.1 | `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| 26.1 | `net/minecraft/world/entity/LivingEntity.java` | (source file read; manifest-listed; not used to assert a difference) |
| 26.1 | `net/minecraft/util/Mth.java` | `19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33` |

## Frozen technical trace

`Player.travel(Vec3)` in both releases first branches on `isPassenger()`: passenger travel delegates directly to `super.travel` and skips this player swim-pitch writer. In the nonpassenger branch, only `isSwimming()` enables the writer. It reads `getLookAngle().y`; `Entity.getLookAngle()` uses the current pitch and yaw (not interpolated rotations) with `calculateViewVector(getXRot(), getYRot())`. The view Y component is `-Mth.sin(xRot * (float)(Math.PI / 180.0))`; yaw computes the other two components but does not affect Y.

The writer chooses multiplier `0.085` exactly when look Y `< -0.2`, otherwise `0.06`. It writes only when look Y `<= 0.0`, or `jumping`, or the fluid state is nonempty at `BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())`. Its direct vertical delta write is `movement.add(0.0, (lookY - movement.y) * multiplier, 0.0)` and `setDeltaMovement`; it does not directly write position.

`Mth.sin(double)` in both builds indexes `SIN[(int)((long)(angle * 10430.378350470453) & 65535L)]`. The cast order is double-to-long truncation, long mask, then int. The 65,536-entry table initializes each element as `(float)Math.sin(i / 10430.378350470453)` using double division. `cos` uses the same expression plus `16384.0`. Both versions retain the same scale, mask, table length, initialization formula, casts and lookup expression; 1.21.11 merely also spells these values as private constants, while these methods use the same literals in both.

After the swim writer, nonpassenger travel delegates to `LivingEntity.travel`. For in-water ordinary travel, both versions perform `moveRelative`, then `move(MoverType.SELF, getDeltaMovement())`, then retain its water multiplier/climbable handling and fluid-falling adjustment. The inherited `Entity.move` consumes that movement vector and updates position through collision resolution / `setPos`; its ordinary collision/position writer order inspected here shows no change material to this vertical-writer boundary. The flying branch remains separate: it saves the post-pitch Y, delegates to `super.travel`, then restores Y scaled by `0.6`.

## Frozen result

No source difference found in the bounded 1.21.11 → 26.1 player swim-pitch vertical writer, look-Y derivation/table indexing, gate, or its relevant inherited water movement/position route. The exact endpoint comparison supports no cutover for this operation across these two endpoints; it does not establish uninspected intermediate patch boundaries.

### Exact member anchors and complete relied-byte pin

Source anchors (1-based lines in the paired source files): `Player.travel`: 1.21.11 `Player.java:1360`, 26.1 `Player.java:1381`; `Entity.getLookAngle`: `Entity.java:2448` / `2512`; `Entity.calculateViewVector`: `Entity.java:1828` / `1879`; `Mth` sine-table initializer: `Mth.java:34`, sine lookup: `Mth.java:50` / `49`, cosine lookup: `Mth.java:54` / `53`; `LivingEntity.travel`: `LivingEntity.java:2309` / `2392`; water movement writer: `LivingEntity.java:2380-2398` / `2467-2485`; `Entity.move`: `Entity.java:685` / `704`.

Complete `LivingEntity.java` file SHA-256: 1.21.11 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` (hex without whitespace: `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`); 26.1 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`.

## Post-freeze independent audit of author report

The author report was opened only after the trace above was frozen. Its supplied identity was verified: `c-swim-pitch-vertical-12111-261-source-boundary/report.md` SHA-256 `95ec4058e714012309d19ce807436923d5abdacaab5b47f360f9aef875d9793d`. I independently agree with its narrow no-change verdict. I also verified its local-client route claim: neither paired `LocalPlayer.java` declares `travel`, so the active local player inherits `Player.travel`; the exact file hashes are 1.21.11 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` and 26.1 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`, matching the respective source-manifest entries. I also confirmed the paired `setXRot` clamp to `Math.clamp(xRot % 360.0F, -90.0F, 90.0F)`; the no-difference derivation itself holds for any finite accepted pitch.

The exact paired sine target index expressions are:

- 1.21.11: `(int)((long)($$0 * 10430.378350470453) & 65535L)`
- 26.1: `(int)((long)(i * 10430.378350470453) & 65535L)`

These differ only in the source parameter name. Final signed result: **independently confirmed, bounded no-change finding** for the assigned operation and endpoint pair; no claim of full pair coverage or runtime parity.

