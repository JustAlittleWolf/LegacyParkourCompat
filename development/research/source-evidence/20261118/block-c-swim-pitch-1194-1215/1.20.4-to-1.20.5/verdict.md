# Independent audit verdict: 1.20.4 → 1.20.5 swim-pitch vertical writer

**Verdict: ACCEPT — operation-specific no-difference claim.** This verdict is bound to the source report SHA-256 `ba774fcf28e5f8bc50b88e72124dade3eabdb5165190dd31f13c9b8ea7447758` and the exact source identities below. It accepts only the bounded Player-owned swimming writer and the direct pitch, coordinate, and call-order dependencies described here. It does not freeze the release pair or certify total swimming travel parity.

## Evidence identity checked

Both official Mojang-mapped releases have ready markers naming the exact version and `mojmap`, and their source-manifest digests match the marker values. I independently checked all manifest entries against the corresponding `decompiled_minecraft/<version>/mojmap/` bytes: 5,048/5,048 files (24,323,941 bytes) for 1.20.4 and 5,328/5,328 files (25,448,860 bytes) for 1.20.5, with no missing or mismatched entries. The 1.20.4 provenance predates the added ready-marker provenance digest field; its marker, provenance and source-manifest hashes were checked directly. The report's ready-marker, provenance and source-manifest hashes match the checked files.

The report's cited SHA-256 values also match the cited Player, Entity, Mth, BlockPos and LivingEntity source files. Exact evidence is official Mojang mappings, Vineflower 1.12.0, Java 25:

- 1.20.4 ready `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1`; provenance `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385`; source manifest `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`.
- 1.20.5 ready `4a587caa86e4ddd68ba63a5431417933dc4df4822e33aaf29ce77ebda53947c2`; provenance `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242`; source manifest `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`.

## Independent retrace

In `Player.travel(Vec3)`, both versions use the same ordered short-circuit path: `isSwimming() && !isPassenger()`; read `getLookAngle().y`; select `0.085` for look Y below `-0.2`, otherwise `0.06`; enter the writer if look Y is nonpositive, or `jumping`, or the upper-position block fluid is nonempty. Java `||` order means nonpositive look Y skips the later predicates and positive look Y with `jumping` skips the block lookup. The lookup coordinate remains the left-associated double expression `getY() + 1.0 - 0.1` passed through `BlockPos.containing`, which floors each component with `Mth.floor(double)`. Both releases then add `(lookY - velocity.y) * coefficient` to the current Y velocity and write the resulting vector before `super.travel`.

The pitch dependency is also unchanged: `getLookAngle()` passes the float `getXRot()` and `getYRot()` to `calculateViewVector`; pitch radians are computed in float, `Mth.sin(float)` returns a float table value, unary minus remains float, then the Y component widens when passed to `Vec3(double,double,double)`. The 65,536-entry `float[] SIN` initialization expression is unchanged. Sine indexing remains `(int)(angle * 10430.378F) & 65535`; cosine adds `16384.0F` before the same truncating cast and mask. `BlockPos.containing` and the cast-then-correct `Mth.floor(double)` bodies match. `calculateViewVector` changes from `protected final` to `public final` in 1.20.5; this visibility-only change has no effect on the calculation.

The ordinary inherited water branch follows the Player writer via `super.travel`. `LivingEntity.travel` performs `moveRelative` and calls `this.move(MoverType.SELF, this.getDeltaMovement())`. The method resolves to inherited `Entity.move(MoverType, Vec3)`; neither `LivingEntity` nor `Player` declares an override in these source trees. The paired line shifts do not change this ordering.

## Scope and limits

The report's no-difference claim is supported for the assigned Player-owned writer and direct dependencies. This is a source-level operation verdict, not a runtime or trajectory result. Creative-flight activation is outside this audit. Effects and deeper fluid behavior are separate; the inherited water-travel body has other changes between these releases, so this verdict does not accept or reject those changes and must not be generalized to complete swimming movement parity. The source report's exclusions and partial coverage are appropriate; complete pair coverage remains open.
