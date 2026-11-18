# Independent audit — 1.19.4→1.20.0 Player swim-pitch Y adjustment

## Verdict

**ACCEPT — the bounded no-difference result is supported by the exact sources.** The author report reviewed was pinned before reading and its SHA-256 is `8e39737fe6085dfff7924ac6fe6b08798829553ad754d1db44c6317b16497e79`.

## Frozen evidence identity and byte checks

- 1.19.4 Mojmap canonical source root: `decompiled_minecraft/1.19.4/mojmap/`. Ready record SHA-256 `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0`; it identifies 1.19.4/Mojmap, client SHA-256 `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`, and source-manifest SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`. The per-version artifact manifest pins the client mappings file `5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606`, mapped client archive `efbf38c89b396faae60cfe3bdb91671cb8d1ec3ccf2e27d3ffab4d261acd016d`, and exact 1.19.4 version metadata file `31c80f5545445fe8e09aae83cda8983988517b75a4ea9e9f359f8a2a17850c75`.
- 1.20 Mojmap canonical source root: `decompiled_minecraft/1.20/mojmap/`. Its hidden root manifest `.decompile-provenance.json` SHA-256 is `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`. It pins client JAR `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`, official client mappings `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`, and exact 1.20 metadata `24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`. There is no separate 1.20 ready marker in this root; the hidden provenance manifest supplies and verifies the source-hash map.
- Independently recomputed SHA-256 values for Player.java, LivingEntity.java, Entity.java, Mth.java, and BlockPos.java match all five entries in both sides’ source manifests. Mth.java is byte-identical across the pair (`201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797`).

## Independent trace

Both `Player.travel(Vec3)` implementations enter the adjustment under `isSwimming() && !isPassenger()`. They read `getLookAngle().y` into a double, select `0.085` only when it is `< -0.2` (otherwise `0.06`), then short-circuit the write gate in this order: look Y `<= 0.0`, `jumping`, and fluid at `BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())` nonempty. The sample uses the current Y before the `super.travel` call; `+` then `-` are left-associative double operations. If enabled, the write reads current delta movement and sets it to `delta.add(0.0, (lookY - deltaY) * coefficient, 0.0)`. Otherwise this pre-super adjustment makes no write. The statement, gate, sample, values, and order are the same at 1.19.4 `Player.java:1440–1453` and 1.20 `Player.java:1446–1459`; the level accessor spelling is the only relevant textual change.

`Entity.getLookAngle()` passes current, raw `getXRot()` and `getYRot()` to `calculateViewVector`, rather than interpolated rotations. Both versions convert pitch to a float radians value with `pitch * (float)(Math.PI / 180.0)`, and calculate vector Y as unary-negative `Mth.sin(pitchRadians)` in float before widening into `Vec3`’s double component. `Mth.SIN` is a `float[65536]`, initialized through `Util.make` by filling every index with `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)` using double math before narrowing. `sin` multiplies the input float by `10430.378F` in float, casts that product to int (truncation toward zero), masks the index with `65535`, and returns that float. This method does not normalize the look vector. These source bodies match in both releases (`Entity.java:1345–1353, 1867–1869` / `1448–1456, 1970–1972`; `Mth.java:25–29, 42–48`).

`BlockPos.containing(double,double,double)` floors all three coordinates through `Mth.floor(double)` in both sources: it casts to int, then subtracts one when the original double is less than that int (`BlockPos.java:74–76` / `81–83`; `Mth.java:59–62`).

After the Player writer, the normal branch calls `super.travel`. The inherited water path in both `LivingEntity.travel` requires a locally controlled instance, `isInWater()`, fluid effect enabled, and inability to stand on that fluid. It reads the updated delta, calls `moveRelative`, moves by delta, may set Y to `0.2` on horizontal collision while climbable, then multiplies X/Z by slowdown and Y by `0.8F`, applies fluid-falling adjustment, and may set Y to `0.3` if its collision/free-space predicate succeeds. The adjacent formulas and order match at 1.19.4 `LivingEntity.java:1983–2027` and 1.20 `LivingEntity.java:2004–2047`; therefore the same pre-super contribution can enter this move path.

The report’s adjacent Slow Falling note is valid but separate: 1.19.4 resets fall distance under the nonpositive-vertical-velocity Slow Falling gate while 1.20 retains the `0.01` gravity value without that reset. It does not alter the Player swim-pitch writer, so it does not change this bounded no-difference verdict.

## Audit signature

Independent source audit performed against the pinned local Mojmap bytes before opening the author report. No implementation, runtime, build, test, wiki, backlog, or Git checks were performed.
