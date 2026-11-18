# 1.14.3 → 1.14.4 Entity pitch setter source check

## Scope and result

Compared the direct local-player mouse-look pitch write in `Entity` and the clamp it calls, with only the device-input caller context needed to establish the double-valued input route. Followed the resulting live `pitch` field to the first fall-flying branch in movement. This is not a full movement-pair inventory.

**Result: no source-confirmed behavioral difference in pitch arithmetic, finite-overflow handling, clamp order, or movement's live-pitch read.** The names and mappings differ. For this operation, both releases use the same `double` delta scale, add in double precision to a `float` pitch, cast once to float before the first setter, clamp current pitch, then independently update and clamp the previous/interpolation pitch. A sufficiently large finite double can cast to positive/negative infinity at the first setter; both float clamps map that to +90/-90. Neither clamp adds a finite check; NaN would pass through, but is outside the requested ordinary finite input case.

## Exact source identity and hashes

- **1.14.3 / Yarn `1.14.3+build.13`:** `decompiled_minecraft/1.14.3/yarn/`. Its hidden `.decompile-provenance.json` SHA-256 is `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`. The manifest pins the Mojang client input at `af100b34ec7ef2b8b9cf7775b544d21d690dddec` (SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`) and Yarn `1.14.3+build.13` (SHA-256 `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`).
- **1.14.4 / local Ornithe Feather source tree:** `decompiled_minecraft/1.14.4/ornithe-feather/`. This ready tree has no `.decompile-provenance.json`; the separate `.yarn.staging-*` tree was ignored. The local client pin identifies Mojang object `8c325a0c5bd674dd747d6ebaa4c791fd363ad8a9`, SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; the local version-metadata pin SHA-256 is `615f466a39d2c19ae9b6e2401aa7bd07dbda337f976bcce383326b8d6ba4e532`. The Feather mapping artifact/build identity is not present in the local cache, so exact source-to-mapping provenance for this legacy tree remains unverified. These are the available local pins; no regeneration or network lookup was performed.

Exact SHA-256 for relevant source files:

| Release / mapping | Relative source file | SHA-256 |
|---|---|---|
| 1.14.3 Yarn | `net/minecraft/entity/Entity.java` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| 1.14.3 Yarn | `net/minecraft/util/math/MathHelper.java` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` |
| 1.14.3 Yarn | `net/minecraft/client/Mouse.java` | `b3724c88b74c005d741259f89329b92b4ab3af7a3f44cd0bdf7ac0222733f243` |
| 1.14.3 Yarn | `net/minecraft/entity/LivingEntity.java` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| 1.14.4 Ornithe Feather | `net/minecraft/entity/Entity.java` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| 1.14.4 Ornithe Feather | `net/minecraft/util/math/MathHelper.java` | `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843` |
| 1.14.4 Ornithe Feather | `net/minecraft/client/MouseHandler.java` | `eab8cbbaf087a049f2687637336ac9c305b4c58ae9c3513fc1db87709a18c44c` |
| 1.14.4 Ornithe Feather | `net/minecraft/entity/living/LivingEntity.java` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |

## Paired method evidence

- **1.14.3 Yarn**, `net/minecraft/entity/Entity.java`, `changeLookDirection(double d, double e)`, lines 314–325: `f = e * 0.15` is double; `pitch = (float)(pitch + f)` precedes `MathHelper.clamp(pitch, -90.0F, 90.0F)`; then `prevPitch = (float)(prevPitch + f)` precedes its clamp. Yaw writes occur between the first pitch cast and its clamp, and between the first clamp and previous-pitch write.
- **1.14.4 Feather**, `net/minecraft/entity/Entity.java`, `updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)`, lines 317–326: `d = cursorDeltaY * 0.15` is double; current `pitch` is cast on assignment then clamped; `lastPitch` is separately cast on assignment then clamped. The yaw writes and their placement match 1.14.3. `prevPitch` is named `lastPitch` in this mapping.
- **Clamp helper:** both `MathHelper.clamp(float, float, float)` bodies (1.14.3 Yarn lines 89–94; 1.14.4 Feather lines 89–94) compare `< min`, then `> max`, else return the input. In either source, a float infinity produced by the first cast is therefore clamped to the corresponding finite endpoint.
- **Device caller context only:** 1.14.3 `Mouse.tick` (lines 191–214) and 1.14.4 `MouseHandler.turnPlayer` (lines 195–218) retain the adjusted cursor Y delta as a double and pass it, after the invert-Y integer multiplier, into the respective Entity setter. The Entity setter has no explicit finite/range gate on that double. This establishes the type path; it does not claim that a physical device normally emits a particular extreme value.
- **Movement consumer:** 1.14.3 `LivingEntity.travel(Vec3d)` line 1790 enters the Elytra branch when `isFallFlying()`; line 1797 derives `l` directly from the live `this.pitch`. The ordinary living movement path calls `travel` at line 2220. In 1.14.4 Feather, the mapped equivalent is `LivingEntity.moveRelative(Vec3d)` (lines 1786 onward), with the active fall-flying branch at 1797 and live `this.pitch` read at 1804; the regular movement path calls it at line 2227. No interpolation/previous-pitch field is used in that flight pitch read.

For a direct finite input such as a positive value large enough that `deltaY * 0.15` remains finite as double but exceeds `Float.MAX_VALUE`, the current-pitch expression yields `+Infinity` at the float cast and clamps to `+90.0F` in both releases. The same independent previous-pitch expression/clamp occurs afterward in both. If fall-flying is active when the movement method runs, its first flight branch reads the clamped current `pitch`. This arithmetic establishes source reachability only; scheduling relative to a native input callback was not part of the assigned scope.
