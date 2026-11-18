# 1.20.5–1.20.6 Player.travel swim-pitch Y correction

## Result

No source difference was found for the bounded operation. The complete `Player.java`, `Entity.java`, `LivingEntity.java`, and `Mth.java` files are byte-identical across the canonical 1.20.5 and 1.20.6 Mojmap source trees, and their source-manifest SHA-256 entries match their on-disk bytes. This establishes no difference for this operation only, not complete player-tick pair coverage.

## Exact operation and reachable behavior

In both releases, `net/minecraft/world/entity/player/Player.java:1473–1494`, `Player.travel(Vec3)`, first gates the correction on `isSwimming() && !isPassenger()` (line 1474). For that gate it samples `this.getLookAngle().y` once into a `double` (1475), then selects a `double` coefficient: `0.085` only if sampled Y `< -0.2`, otherwise `0.06` (1476). The correction write is gated by the left-to-right short-circuit expression: sampled Y `<= 0.0`, or `jumping`, or nonempty fluid at `BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())` (1477–1479). Only after that gate succeeds does it sample current delta movement (1480), add `(sampled look Y - current delta Y) * coefficient` to the Y component (1481), and set delta movement. Thus the look sample precedes coefficient selection and all correction gates; current velocity is sampled only on the correction path. The condition's first two true operands avoid the block/fluid query.

For the assigned ordinary, non-flying, non-passenger case, `Player.travel` then calls `super.travel(input)` (1492–1494). The inherited `LivingEntity.travel` is identical in both releases. On the local-controlled, water-affected path (`LivingEntity.java:2104–2147`), it computes water slowdown/depth-strider inputs, calls `moveRelative`, then `move(MoverType.SELF, getDeltaMovement())` (2135–2136), followed by velocity damping and fluid-fall adjustment. The swim-pitch correction therefore changes the velocity supplied to this inherited move when its own gate is reached. This path additionally requires the inherited water branch (`isInWater() && isAffectedByFluids() && !canStandOnFluid(fluidState)`); otherwise inherited travel chooses another branch.

`Entity.java:164–165` declares `yRot` and `xRot` as private `float` fields without explicit initializers (default zero on fresh construction); getters return those raw fields (`3304–3329`). `getLookAngle()` uses `calculateViewVector(getXRot(), getYRot())` (`2027–2028`), with no interpolation. `calculateViewVector` (`1479–1486`) first computes two `float` radians values using `angle * (float)(Math.PI / 180.0)`, then evaluates yaw cosine/sine and pitch cosine/sine as floats, returning `(sin(yaw)*cos(pitch), -sin(pitch), cos(yaw)*cos(pitch))` in a `Vec3`. The swim correction reads its Y coordinate, which is the negated pitch sine.

`Mth.java:28,32–36,48–54` initializes a 65,536-element float sine table in index order using `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`. `sin(float)` returns `SIN[(int)(angle * 10430.378F) & 65535]`; `cos(float)` returns `SIN[(int)(angle * 10430.378F + 16384.0F) & 65535]`. Multiplication and offset occur in float arithmetic; the cast to `int` truncates toward zero before the 16-bit mask. Both versions have identical table width, initialization expression/order, scale constant, casts, offsets and indices.

The player-only swimming predicate is `Player.java:1832–1833` in both versions: `!abilities.flying && !isSpectator() && super.isSwimming()`. The shared Entity predicate reads shared flag 4 (`Entity.java:2170–2172`). For the stated ordinary non-flying player, the travel branch still independently excludes passengers.

## Compared exact sources and byte identity

All source references below are Mojmap and line numbers are identical on both sides.

- `decompiled_minecraft/1.20.5/mojmap/net/minecraft/world/entity/player/Player.java` and `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/player/Player.java`: SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` on each side. Manifest entries: 1.20.5 `mojmap.sources.sha256:3700`; 1.20.6 `mojmap.sources.sha256:3668`.
- `net/minecraft/world/entity/Entity.java`: SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` on each side. Manifest entries: 1.20.5 line 3177; 1.20.6 line 3541.
- `net/minecraft/world/entity/LivingEntity.java`: SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` on each side. Manifest entries: 1.20.5 line 3198; 1.20.6 line 3566.
- `net/minecraft/util/Mth.java`: SHA-256 `f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d` on each side. Manifest entries: 1.20.5 line 2653; 1.20.6 line 2967.

Both ready markers say `status: ready`, with `versionId` 1.20.5/1.20.6 and `mapping: mojmap`. Their source-manifest file hashes agree with marker values: 1.20.5 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; 1.20.6 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`. Provenance hashes on disk: 1.20.5 `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242`; 1.20.6 `ee53e67e7ab51069a0eaa5e0ce13d49a9f972bdc13e3c31e041afa77f4d26e8`. The 1.20.5 ready marker names the provenance and its matching hash; the 1.20.6 marker does not declare a provenance hash, so the latter is reported as the locally computed file identity only. No source generation was performed.
