# 1.20 → 1.20.1: Player swim pitch vertical adjustment

## Scope and verdict

Blind, source-only comparison of the ordinary nonpassenger `Player.travel(Vec3)` swim pitch adjustment, its look-angle numeric path, and the immediate inherited movement consumer. Verdict: **no source-level change** in this operation across 1.20 and 1.20.1. The paired `Player`, `Entity`, `LivingEntity`, and `Mth` source files have identical SHA-256 hashes, and their relevant bodies match. This is a bounded no-difference coverage result, not a movement finding or a claim about intermediate releases.

## Exact sources and pinned identities

Both sides are Mojmap, at `decompiled_minecraft/1.20/mojmap/` and `decompiled_minecraft/1.20.1/mojmap/`.

- **1.20:** hidden `.decompile-provenance.json` SHA-256 `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`. Its pinned inputs include release metadata `1.20.json` (`24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`), Mojmap `client.txt` (`fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`), and client JAR (`5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`). The manifest identifies Eclipse Adoptium Java `25.0.3+9-LTS` on Windows 11 and Vineflower `1.12.0` among the pinned tools.
- **1.20.1:** `mojmap.ready.json` SHA-256 `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed`; it identifies exact version `1.20.1`, Mojmap, ready status, 4,786 source files, and the paired source/artifact manifests. `mojmap.sources.sha256` SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`; `artifacts.sha256` SHA-256 `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2`; `mojmap.provenance.json` SHA-256 `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa`. The ready marker binds those manifest hashes and the exact client hash `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`. The artifact manifest pins the 1.20.1 Mojmap archive `221d543b9f80ca42faaaa8e3f7cebc4fe1879b8f7ba954bb6ca62f8250be8b77` and client mappings `67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d`.

For both releases, the exact source hashes are identical:

| Mojmap source | SHA-256 on both sides |
|---|---|
| `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |
| `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| `net/minecraft/util/Mth.java` | `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797` |

The 1.20 hashes come from its hidden provenance source map and were checked against the files; the 1.20.1 hashes were checked against both the files and the pinned source manifest.

## Compared operation and numeric path

- `Player.java`, `Player.travel(Vec3)`, lines 1446–1473 in both releases. At 1450, the adjustment is gated by `isSwimming() && !isPassenger()`. It samples `getLookAngle().y` into a `double`; chooses `0.085` when that value is strictly below `-0.2`, otherwise `0.06`; and applies the adjustment only if look-Y is `<= 0.0`, the player is jumping, or the fluid state at `BlockPos.containing(x, y + 1.0 - 0.1, z)` is nonempty. It reads current delta movement and writes `delta + (lookY - deltaY) * factor` to Y, with the other components unchanged. All gates and order are unchanged.
- `Entity.java`, `getLookAngle()` line 1970 and `calculateViewVector(float,float)` lines 1448–1455 in both releases. `getLookAngle()` supplies raw `getXRot()` and `getYRot()`; this is not partial-tick interpolation. `calculateViewVector` first computes the pitch angle as a `float`: `pitch * (float)(Math.PI / 180.0)`. It passes that float to `Mth.sin`; the returned table value is a float, negated as a float for look-Y and widened to `double` when passed to `Vec3(double,double,double)`. Player then reads that double Y, compares it with double literals, and performs the correction using double arithmetic against `Vec3.y`.
- `Mth.java`, `sin(float)` and `cos(float)`, lines 42–48 in both releases. `sin` indexes `SIN[(int)(angle * 10430.378F) & 65535]`; the multiplication is float before Java's truncating float-to-int cast and masking. `cos` uses the same float expression plus `16384.0F` before cast/mask. The vertical view component specifically comes from the sine lookup, not `Math.sin`.
- `Player.travel` performs the adjustment before its flying/nonflying branch and before `super.travel($$0)`. `Player` extends `LivingEntity`, so its direct `super.travel($$0)` enters `LivingEntity.travel(Vec3)`; that water branch (lines 2004–2047) uses the already-adjusted `getDeltaMovement()` at line 2036 in `move(MoverType.SELF, ...)`, then applies water drag and fluid-fall adjustment. `Entity.move` begins the player displacement with its argument at lines 593 onward in both releases. Those directly consuming inherited bodies are hash-identical too.

For a swimming nonpassenger looking downward, look-Y is negative and the correction steers vertical velocity toward that sampled look component before inherited travel. Jumping or fluid immediately above the player also enables that correction for nonpositive look-Y as specified by the same gates. This describes the source path only; no trajectory was simulated.

