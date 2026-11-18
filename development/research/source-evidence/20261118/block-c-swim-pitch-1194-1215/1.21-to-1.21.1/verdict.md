# Independent source audit: 1.21 → 1.21.1 swim pitch Y correction

## Frozen verdict

**Operation verdict: no difference found.** For an ordinary, swimming, nonpassenger player, the `Player.travel(Vec3)` swim-pitch Y correction, its pitch source and numeric path, its above-fluid gate, and the following inherited `LivingEntity.travel(Vec3)` path are identical in Mojmap 1.21 and 1.21.1. The paired source-file SHA-256 values are equal for all files listed below. This closes only this assigned operation and its named source dependencies; it does not claim full-pair inventory or pair freeze.

## Exact source identities and integrity

Both sides are the exact release-specific Mojmap source trees in the primary checkout. The 1.21 Mojmap tree carries embedded hidden provenance at `decompiled_minecraft/1.21/mojmap/.decompile-provenance.json` (SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`; whitespace omitted in digest display below). Its embedded source map agrees with the actual file hashes. The recorded exact inputs include 1.21 client `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`, official Mojang mapping file `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`, and 1.21 metadata `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`.

The 1.21.1 Mojmap tree is bound by its rooted ready marker and manifests. `mojmap.provenance.json` SHA-256 `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`; `mojmap.ready.json` SHA-256 `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`; `mojmap.sources.sha256` SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; `artifacts.sha256` SHA-256 `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`. The ready record identifies version `1.21.1`, mapping `mojmap`, 5,363 source files, client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`, and the source-manifest digest above. The provenance pins the exact batch `1.21.1,1.21.3,1.21.4,1.21.5`, `--mappings=mojmap`, Gradle 9.7.1, Java 25.0.3+9-LTS, Mapping IO 0.9.1, Tiny Remapper 0.14.1, and Vineflower 1.12.0.

| Paired source | Mojmap locations | SHA-256 on both 1.21 and 1.21.1 |
|---|---|---|
| `Player.java` | `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)` lines 1456–1478; `Player.isSwimming()` lines 1827–1829 | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `Entity.java` | `net/minecraft/world/entity/Entity.java`, `calculateViewVector(float,float)` lines 1530–1538; `getLookAngle()` lines 2099–2101 | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| `LivingEntity.java` | `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)` lines 2091 onward | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `Mth.java` | `net/minecraft/util/Mth.java`, `SIN` initializer lines 32–36, `sin(float)` lines 48–50, `cos(float)` lines 52–54, and `floor(double)` lines 65–68 | `aa40272f0f980438ca7e8c874ceedc664feb77958b162d7aa3ed01f233bd2deb` |
| `BlockPos.java` | `net/minecraft/core/BlockPos.java`, `containing(double,double,double)` lines 93–95 | `8f8b3d36ce0b8015895252a98a4402186e63898fb24a7f19be5b2c0c12d4d564` |

For 1.21.1 each listed digest also matches its entry in `mojmap.sources.sha256`; for 1.21 each matches the corresponding entry in the embedded provenance `sources` map. Independently computed file hashes match those pins.

## Frozen Player-level trace

The compared entry point is `Player.travel(Vec3)`; its input argument is passed through unchanged to the inherited call. First, `this.isSwimming() && !this.isPassenger()` gates the local correction. `Player.isSwimming()` further requires `!this.abilities.flying && !this.isSpectator()` and then delegates to `super.isSwimming()`, which is `Entity.isSwimming()` and reads shared flag 4. No logical-server or `isControlledByLocalInstance()` check gates this Player-level correction.

When that gate passes, `double lookY = this.getLookAngle().y` is evaluated once, followed by the coefficient `lookY < -0.2 ? 0.085 : 0.06`. The correction is applied only if `lookY <= 0`, or `this.jumping`, or the short-circuited above-fluid query succeeds. That query is ordered as `getX()`, then `getY() + 1.0 - 0.1`, then `getZ()`; it calls `BlockPos.containing(x,y,z)`, which floors each double with `Mth.floor(double)`, then `level().getBlockState(pos).getFluidState().isEmpty()`. Thus a nonempty fluid state at that sampled block makes the condition true. If the combined condition passes, the method reads the current `Vec3` delta movement and writes `delta.add(0.0, (lookY - delta.y) * coefficient, 0.0)` back via `setDeltaMovement`. The subexpression is evaluated in double precision; `lookY` was widened from the float vertical component described below. The sampled `y` expression retains the written `+ 1.0 - 0.1` order.

After this correction, `Player.travel` takes its flying branch only for `abilities.flying && !isPassenger()`: it snapshots current Y velocity, calls `super.travel(input)`, restores Y as the snapshot times `0.6`, resets fall distance, and clears shared flag 7. Otherwise it calls `super.travel(input)` directly. In the ordinary swimming nonpassenger case `Player.isSwimming()` excludes flight, so this flying branch is false; inherited `LivingEntity.travel` follows. Its locally controlled water branch reads `FluidState` at `blockPosition()`, tests water/affected-by-fluids/can-stand-on-fluid, then performs its water travel operations (including `moveRelative`, `move`, and post-move velocity adjustment). This inherited work occurs after, and therefore consumes the delta movement already updated by the Player correction. `LivingEntity.travel` is byte-identical across the pair.

## Pitch and lookup-table numeric trace

`Entity.getLookAngle()` calls `calculateViewVector(this.getXRot(), this.getYRot())`, passing pitch and yaw as floats. `calculateViewVector` evaluates, in source order:

1. `float pitchRad = xRot * (float)(Math.PI / 180.0)`;
2. `float negativeYawRad = -yRot * (float)(Math.PI / 180.0)`;
3. yaw `Mth.cos(negativeYawRad)`, yaw `Mth.sin(negativeYawRad)`, pitch `Mth.cos(pitchRad)`, pitch `Mth.sin(pitchRad)`;
4. `new Vec3(yawSin * pitchCos, -pitchSin, yawCos * pitchCos)`.

Each angle conversion and product inside the vector components is float arithmetic. The vertical component is unary-negated as a float and widened to the `Vec3` constructor's double parameter; it is independent of yaw's value, though the method computes the yaw lookups before constructing the vector. `getLookAngle().y` is therefore the double widening of `-Mth.sin(pitchRad)`, not a direct `Math.sin` result and not a double-precision trigonometric calculation.

`Mth.SIN` is a 65,536-element float table initialized once in ascending integer index order. For each integer `i`, initialization stores `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`: the angle and `Math.sin` are double, then the result is narrowed to float. `Mth.sin(a)` indexes `SIN[(int)(a * 10430.378F) & 65535]`; `a * 10430.378F` is float multiplication, cast to `int` truncates toward zero, and the bit mask keeps the low 16 bits. `Mth.cos(a)` uses `SIN[(int)(a * 10430.378F + 16384.0F) & 65535]`, with the offset addition still float before the same cast and mask. Both endpoints have identical initializer, index expressions, casts, table width, masking, and result types.

## Difference check

The Player method, Player swimming override, Entity view-vector methods, Mth table and lookup methods, BlockPos conversion, and inherited LivingEntity travel bytes have equal SHA-256 on the two endpoints. Their relevant control flow and numeric operations consequently have no source delta for this assignment. No independent movement behavior change is supported for 1.21 → 1.21.1 by this operation. The scope is source-only; this is not a runtime-parity result and does not establish any boundary within the release interval.

