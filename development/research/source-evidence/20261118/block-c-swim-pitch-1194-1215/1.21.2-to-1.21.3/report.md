# 1.21.2–1.21.3 swim-pitch vertical correction source trace

## Scope and disposition

Bounded slice: ordinary active, nonpassenger local Player travel, specifically Player's swim-pitch vertical-velocity correction, its raw pitch/look-angle inputs and the inherited travel/move reached afterward. Compared Mojmap decompiled sources only. The four relevant source files have identical bytes in the available 1.21.2 and 1.21.3 directories, so this slice shows no source difference. Pair-level source acceptance remains provisional because the 1.21.3 source tree has no provenance manifest and its raw cache is absent.

## Exact path and gates

In `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)` first tests `isPassenger()`. A passenger immediately delegates to `super.travel` and skips the Player swim correction. Otherwise, `isSwimming()` gates the correction. It reads `double lookY = getLookAngle().y`, then chooses a double scale of `0.085` when `lookY < -0.2`, or `0.06` otherwise. It modifies velocity only when `lookY <= 0.0`, `jumping` is true, or the fluid state at `BlockPos.containing(x, y + 1.0 - 0.1, z)` is nonempty. The write is `setDeltaMovement(old.add(0.0, (lookY - old.y) * scale, 0.0))`; this is an additive correction toward the look-vector Y, not a replacement of Y. It occurs before the subsequent flight check and `super.travel`.

For the ordinary nonflying case, Player delegates to `LivingEntity.travel`. That method enters only under `isControlledByLocalInstance()`, reads the current-position fluid state, and dispatches to `travelInFluid` when the entity is in water or lava, affected by fluids, and cannot stand on that fluid. In the water path, `moveRelative` is followed by `move(MoverType.SELF, getDeltaMovement())`; collision adjustment, damping and fluid-falling adjustment then update velocity. The lava path also moves before its later damping/falling adjustments. Thus the Player correction is part of the input velocity to the inherited movement call; it is not itself a position move. The separate flying branch saves pre-super Y, calls `super.travel`, then writes saved Y times `0.6`.

## Raw pitch and sine table

`Entity.getLookAngle()` calls `calculateViewVector(getXRot(), getYRot())`, using the current raw rotation fields, not interpolated rotations. `calculateViewVector` converts pitch with the float expression `pitch * (float)(Math.PI / 180.0)` and yaw with `-yaw * (float)(Math.PI / 180.0)`. It obtains the pitch sine through `Mth.sin(pitchRadians)` and forms the view Y as `-sinPitch` (the other sine/cosine calls form X and Z).

`Mth` initializes `SIN` with `Util.make(new float[65536], ...)`; each entry is `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`. `Mth.sin(float angle)` returns `SIN[(int)(angle * 10430.378F) & 65535]`. The multiplication is float arithmetic, cast to `int` truncates toward zero, and the bit mask wraps the resulting index to 16 bits. Both releases have the same initializer, width, entry formula, scale, cast, mask and lookup.

## Compared source identities and readiness

| Release | Mojmap source tree | Provenance | SHA-256 of cited files |
|---|---|---|---|
| 1.21.2 | `decompiled_minecraft/1.21.2/mojmap/` | `.decompile-provenance.json` is present. The manifest pins the 1.21.2 metadata/client and `client.txt` mappings; the recorded hashes for all four cited source files match their bytes. Runtime/toolchain is recorded as Eclipse Adoptium 25.0.3+9-LTS on Windows 11. | `Player.java` `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de`; `Entity.java` `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`; `Mth.java` `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da`; `LivingEntity.java` `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| 1.21.3 | `decompiled_minecraft/1.21.3/mojmap/` | Required class files are present, but a hidden-file listing confirms no `.decompile-provenance.json`. There is no `.minecraft-decompile-cache/1.21.3/` directory. The cited files happen to be byte-identical to the 1.21.2 files; this does not independently establish their 1.21.3 source/mapping origin. | Same four actual SHA-256 values as 1.21.2, confirming byte equality only. |

The 1.21.2 raw cache contains `version.json`, `client.jar`, and `client_mappings.txt` with `.pin.json` sidecars for the latter source inputs. Per the decompiler guide, a local pin establishes a first-use content baseline, not historical origin. The unmanifested 1.21.3 tree therefore has the documented legacy/unverified-provenance caveat; no source generation or cache regeneration was performed. `decompiled_minecraft` is present and readable, but exact 1.21.3 readiness is not fully verified.

## Source locations

All locations below are in `decompiled_minecraft/<version>/mojmap/` and are the same in both trees:

- `net/minecraft/world/entity/player/Player.java`: `travel(Vec3)` lines 1445–1468; correction and above-player fluid sample at 1449–1457.
- `net/minecraft/world/entity/Entity.java`: `calculateViewVector(float,float)` lines 1619–1627; `getLookAngle()` lines 2192–2194.
- `net/minecraft/util/Mth.java`: sine table declaration/initialization lines 32–36; `sin`/`cos` lookup lines 48–54.
- `net/minecraft/world/entity/LivingEntity.java`: local-control gate and travel dispatch lines 2178–2187; fluid movement order lines 2216–2259.
