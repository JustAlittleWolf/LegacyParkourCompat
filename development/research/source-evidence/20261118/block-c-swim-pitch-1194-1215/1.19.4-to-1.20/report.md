# 1.19.4→1.20.0 player swim pitch Y adjustment

## Evidence identity

Exact Mojmap decompiled sources only. The 1.19.4 ready record identifies version `1.19.4`, mapping `mojmap`, and client SHA-256 `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`; its source-manifest SHA-256 is `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`. The 1.20 embedded `.decompile-provenance.json` pins client JAR `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`, client mappings `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`, and exact 1.20 metadata `24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`. All five examined source files (Player, LivingEntity, Entity, BlockPos, Mth) match their respective per-source SHA-256 entries in both manifests.

## Bounded comparison

- `1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`, `travel(Vec3)`, lines 1440–1453; `1.20/mojmap/.../Player.java`, lines 1446–1459.
- `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, `calculateViewVector(float,float)` lines 1345–1353 and `getLookAngle()` lines 1867–1869; 1.20 equivalents at lines 1448–1456 and 1970–1972.
- `Mth.java`, both releases, lines 25–29 and 42–48; `BlockPos.java`, both releases, `containing(double,double,double)` at 74–76 (1.19.4) and 81–83 (1.20).
- Inherited water path: `LivingEntity.travel(Vec3)`, 1.19.4 lines 1983–2027 and 1.20 lines 2004–2047.

## Result: no difference in the bounded Player swim pitch Y adjustment

In both versions, the write gate begins with `isSwimming() && !isPassenger()`. It reads `getLookAngle().y` into a double, chooses `0.085` iff that value is `< -0.2` (otherwise `0.06`), and writes only when the look Y is `<= 0.0`, `jumping` is true, or the fluid state at `BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())` is nonempty. These are short-circuit `||` checks in that order. On a write it reads current delta movement, adds `(lookY - deltaY) * coefficient` to Y, then calls `setDeltaMovement`; otherwise this pre-super Y adjustment is skipped. The coordinate expression remains left-associated as `(getY() + 1.0) - 0.1` in double arithmetic.

The looked-at Y component uses the current raw `getXRot()` float, not an interpolated pitch: `calculateViewVector` computes `pitch * (float)(Math.PI / 180.0)`, then `-Mth.sin(radians)`. The identical Mth lookup in both versions is a `float[65536]`, initialized with `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. Lookup multiplies the radian float by `10430.378F`, casts the float product to `int` (truncation toward zero), masks with `65535`, and returns that float entry. Negation occurs as float before widening to the `Vec3` double Y. No normalization is applied. `BlockPos.containing` floors each double coordinate through `Mth.floor(double)` in both versions.

After the adjustment, `Player.travel` calls `super.travel` (except its same flying/nonpassenger wrapper). `LivingEntity.travel` takes its fluid branch only for a locally controlled instance that is in water, affected by fluids, and unable to stand on the current fluid. That branch consumes current delta movement: it calls `moveRelative`, moves by delta, optionally changes Y to `0.2` on a horizontal collision while climbable, applies water slowdown to X/Z and `0.8F` to Y, applies fluid-falling adjustment, then can set Y to `0.3` when the collision/free-space gate passes. The adjustment therefore contributes to inherited water movement when this branch is active; its formulas and order match across these endpoints.

Adjacent inherited difference, outside the bounded Player adjustment: 1.19.4 `LivingEntity.travel` resets fall distance when the nonpositive-Y slow-falling gate sets gravity to `0.01` (lines 1985–1990); 1.20 retains the `0.01` assignment but no longer resets fall distance (lines 2006–2010). This does not change the swim-pitch Y write itself; it is a separate inherited state change.