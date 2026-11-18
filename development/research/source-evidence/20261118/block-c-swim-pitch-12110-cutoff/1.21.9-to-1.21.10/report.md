# 1.21.9 → 1.21.10 Mojmap: player swim-pitch Y correction

## Bounded result

No difference was found in the assigned operation between 1.21.9 and 1.21.10. `Player.java` is byte-identical across the pair. The direct swim-pitch correction, its inputs and gates, and the call into inherited travel therefore have no pair boundary here. This is a bounded no-difference result, not a proposed movement change.

## Provenance and source integrity

- Older source: `decompiled_minecraft/1.21.9/mojmap/`, Mojmap. Its embedded `.decompile-provenance.json` declares the 1.21.9 metadata input URL `https://piston-meta.mojang.com/v1/packages/91f383c21d8cd06abc9a569f2976b58da192a642/1.21.9.json` and records source hashes. The file SHA-256 is `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`. No sibling `mojmap.ready.json`, `mojmap.provenance.json`, or `mojmap.sources.sha256` was present for this release at inspection; the older side therefore lacks the canonical ready-marker identity.
- Newer source: `decompiled_minecraft/1.21.10/mojmap/`, Mojmap; ready marker declares exact `versionId` 1.21.10 and status `ready`. Ready-marker SHA-256: `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0`. Provenance SHA-256: `9a1db4c050c3caa28a1313f89da9eda3b28feaa5f8afba42cab798b47d7ad77c`. Source-manifest SHA-256: `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` (the ready marker records this identity).
- Relied source SHA-256 values (each actual file hash matched the source hash recorded in its side's manifest): `Player.java` both sides `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`; `LivingEntity.java` both sides `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`; `Mth.java` both sides `d2a84fa876d78eb9693b2625ff5dc2eb8568ea33a974b4368effb03b4390e7f1`. `Entity.java` differs as a whole (`1.21.9`: `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c`; `1.21.10`: `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18`), but the view-angle/get-look methods inspected below have identical bodies.

## Exact path and arithmetic

In both versions, `Player.travel(Vec3)` first delegates immediately to `super.travel` for passengers. Otherwise, if `isSwimming()` is true, before the flying branch and before the ordinary `super.travel` call, it obtains `double lookY = getLookAngle().y`; chooses `double c = lookY < -0.2 ? 0.085 : 0.06`; and applies the correction only when `lookY <= 0.0 || jumping || !fluidState(BlockPos.containing(x, y + 1.0 - 0.1, z)).isEmpty()`. The sample expression is evaluated as `y + 1.0 - 0.1`, then passed with x and z to `BlockPos.containing`; it checks `FluidState.isEmpty()`, not block state. On the passing path the write is exactly `deltaMovement.add(0.0, (lookY - deltaY) * c, 0.0)`, leaving X/Z unchanged. The correction runs before either the flying Y save/super/`* 0.6` restoration branch or non-flying `super.travel`.

`Entity.getLookAngle()` uses `calculateViewVector(getXRot(), getYRot())` directly; it does not obtain interpolated pitch from the partial-tick `getViewXRot(alpha)` path. `Entity.getXRot()` returns the stored `xRot` float in both releases. `calculateViewVector` computes float radians in order: `pitch * (float)(Math.PI / 180.0)` and `-yaw * (float)(Math.PI / 180.0)`; gets cosine/sine through `Mth`; and constructs `Vec3(sinYaw * cosPitch, -sinPitch, cosYaw * cosPitch)`. Those products are float operations before widening to the `Vec3` double components. Consequently `lookY` is the widened negation of the float pitch sine-table value, rather than a fresh double-precision `Math.sin` result.

Both `Mth` files initialize `SIN` with `Util.make(new float[65536], ...)`, filling each slot as `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`. `sin(float)` indexes `SIN[(int)(angle * 10430.378F) & 65535]`; `cos(float)` indexes `SIN[(int)(angle * 10430.378F + 16384.0F) & 65535]`. The multiply and offset are float expressions, conversion truncates toward zero to `int`, then the 16-bit mask selects the table entry. Initialization and both lookup expressions are byte-identical across these versions.

For the requested ordinary local active, nonpassenger player, after this pre-super correction `LivingEntity.travel` selects `travelInFluid` when in water (or lava), affected by fluids, and not standing on the sampled fluid. In the water branch it captures whether old Y velocity is nonpositive, gets effective gravity and water slowdown, adjusts efficiency if airborne, applies Dolphin's Grace override where present, then calls `moveRelative(input, ...)` followed by `move(SELF, deltaMovement)`. It handles horizontal collision on a climbable surface by setting temporary Y to `0.2`, multiplies velocity by `(slowdown, 0.8F, slowdown)`, and calls `getFluidFallingAdjustedMovement`; a final horizontal-collision/free-space check may set Y to `0.3F`. `LivingEntity.java` is byte-identical in this pair, so this inherited water movement does not change between the releases. The local/active restriction is the assignment's reachable-player scope; the `Player.travel` code itself adds no local-player or alive predicate.

## Compared source locations

- 1.21.9 `Player.java` lines 1294–1318; 1.21.10 same lines.
- 1.21.9 `Entity.java` `getViewXRot` 1785–1787, `calculateViewVector` 1801–1809, `getLookAngle` 2424–2426, `getXRot` 3772–3774; 1.21.10 corresponding methods at 1790–1792, 1806–1814, 2429–2431, 3777–3779.
- Both `Mth.java` lines 28–36 and 48–54.
- Both `LivingEntity.java` lines 2275–2284 and 2331–2380.

## Gate

The method/source comparison and recorded per-file hashes establish the bounded no-difference result. Because the 1.21.9 canonical directory has no ready marker or standalone source-manifest identity, source-readiness acceptance remains unverified for that side. No source generation or mutation was performed.