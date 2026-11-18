# 1.20.4 → 1.20.5: Player swim-pitch vertical writer

## Evidence identity

Compared canonical decompiled sources for exact Minecraft releases **1.20.4** and **1.20.5**, both official Mojang mappings (`mojmap`), Vineflower 1.12.0, Java 25.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 |
| --- | --- | --- | --- |
| 1.20.4 | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` |
| 1.20.5 | `4a587caa86e4ddd68ba63a5431417933dc4df4822e33aaf29ce77ebda53947c2` | `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` |

Both ready markers say `status: ready`, identify the exact version and Mojmap namespace, and bind the source-manifest digest above. The 1.20.4 provenance records the Mojang client SHA-256 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`; 1.20.5 records client SHA-256 `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` and official client-mapping artifact SHA-256 `9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac`. Local source files checked against their listed source-manifest entries; hashes are provided below.

## Paired source slices

- `decompiled_minecraft/1.20.4/mojmap/net/minecraft/world/entity/player/Player.java`, `travel(Vec3)`, lines 1451–1472; source SHA-256 `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`.
- `decompiled_minecraft/1.20.5/mojmap/net/minecraft/world/entity/player/Player.java`, `travel(Vec3)`, lines 1473–1494; source SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`.
- `decompiled_minecraft/1.20.4/mojmap/net/minecraft/world/entity/Entity.java`, `getLookAngle()` lines 2005–2006 and `calculateViewVector(float,float)` lines 1459–1467; SHA-256 `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`.
- `decompiled_minecraft/1.20.5/mojmap/net/minecraft/world/entity/Entity.java`, `getLookAngle()` lines 2027–2028 and `calculateViewVector(float,float)` lines 1479–1487; SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`.
- `decompiled_minecraft/1.20.4/mojmap/net/minecraft/util/Mth.java`, SIN initialization and `sin`/`cos` lines 27–53, `floor(double)` lines 64–67; SHA-256 `f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075`.
- `decompiled_minecraft/1.20.5/mojmap/net/minecraft/util/Mth.java`, SIN initialization and `sin`/`cos` lines 28–54, `floor(double)` lines 65–68; SHA-256 `f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d`.
- `decompiled_minecraft/1.20.4/mojmap/net/minecraft/core/BlockPos.java`, `containing(double,double,double)` lines 81–83; SHA-256 `a91e96745a49cdb2ec18be2ac7986071d25ed2de2a04c8f779ca41cfcccc29fc`.
- `decompiled_minecraft/1.20.5/mojmap/net/minecraft/core/BlockPos.java`, `containing(double,double,double)` lines 93–95; SHA-256 `43f7cdbed56f35856782eb023727486ffc6b3947209f49d64bfce4147fb7cbe2`.
- Inherited dispatch/order checked in `LivingEntity.travel(Vec3)`: 1.20.4 lines 2035–2068 and 1.20.5 lines 2104–2136. The first water branch reaches `this.move(MoverType.SELF, this.getDeltaMovement())` after `moveRelative`; the inherited movement method is `Entity.move(MoverType,Vec3)` (1.20.4 line 596; 1.20.5 line 597). The source SHA-256 values are `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` (1.20.4) and `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` (1.20.5).

## Direct writer behavior and exact evaluation

In both `Player.travel` bodies the direct block is the same, in the same order:

1. Gate on `this.isSwimming() && !this.isPassenger()`.
2. Read `double lookY = this.getLookAngle().y`.
3. Choose a `double` coefficient: `0.085` when `lookY < -0.2`, otherwise `0.06`.
4. Enter the writer only when `lookY <= 0.0 || this.jumping || !upperBlockFluidIsEmpty`. Java `||` short-circuits: a nonpositive look Y skips both later predicates; if it is positive, `jumping` skips the block lookup when true.
5. The final predicate evaluates `BlockPos.containing(this.getX(), this.getY() + 1.0 - 0.1, this.getZ())`, then reads that block's fluid state and tests `isEmpty()`. The source keeps the left-associated double expression `getY() + 1.0 - 0.1`; it does not spell it as `getY() + 0.9`. `BlockPos.containing` floors each double using `Mth.floor(double)`, whose implementation casts to `int` and subtracts one when the original value is below that cast result.
6. On entry, read the current `Vec3` velocity and write `velocity.add(0.0, (lookY - velocity.y) * coefficient, 0.0)` back with `setDeltaMovement`. The expression is double arithmetic: `lookY`, `velocity.y`, and the coefficient are doubles. Thus the writer adjusts vertical velocity toward the look-vector Y by the selected factor; it does not assign the pitch value directly.
7. Only after this block does Player call `super.travel($$0)`. The normal inherited water travel branch does its relative input update and then calls the actual `move(MoverType.SELF, ...)`, so this Player-owned vertical-velocity writer precedes the inherited real move in both versions. The Player flying branch also calls `super.travel` after this block and has its own later vertical-velocity replacement; that post-call handling is unchanged in the paired Player bodies.

`Entity.getLookAngle()` returns `calculateViewVector(getXRot(), getYRot())`. The authoritative pitch is `Entity.xRot`, a `float`; `getXRot()` returns that float in both releases. `calculateViewVector` first computes `pitchRadians` as `float pitch * (float)(Math.PI / 180.0)`, then its vertical component is unary-minus of `Mth.sin(pitchRadians)`. `Vec3` construction widens that float Y component to the constructor's double parameter, which is exposed as the `double .y` used by the writer. The yaw sine/cosine calculations affect the horizontal components, not this Y component.

The relevant `Mth` table is initialized identically: `Util.make(new float[65536], ...)`, filling each slot with `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`. `Mth.sin(float)` computes the float product `angle * 10430.378F`, casts to `int` (Java truncation toward zero), masks with `65535`, and indexes that float array. `Mth.cos(float)` uses the same cast/mask/index path after adding `16384.0F`; both source bodies are unchanged. `Mth.sin` supplies the vertical look component. The `Mth` field declaration includes the float constant `SIN_SCALE = 10430.378F`, although the method body uses the literal `10430.378F` in both releases.

## Difference verdict

**No behavioral difference found in the bounded Player-owned swim-pitch vertical writer or its direct pitch, lookup, and call-order dependencies.** The paired `Player.travel` writer block, its gates, coefficient choice, coordinate expression, arithmetic, and placement before `super.travel` are textually identical. `Entity.getLookAngle` and `calculateViewVector`, Mth table initialization and indexing/casts, `BlockPos.containing`, and `Mth.floor(double)` also preserve the relevant behavior. `calculateViewVector` changes visibility from `protected final` in 1.20.4 to `public final` in 1.20.5; this access change does not alter the movement calculation.

For an upward look (`lookY > 0`), not-jumping player swimming states enter this writer only if the specified upper-position block has a nonempty fluid state; otherwise this particular writer does not modify Y velocity. Nonpositive look Y always enters it without evaluating that block predicate. These are source-level consequences of the exact gate, not a trajectory/runtime claim. The separately excluded creative-flight activation logic and deeper fluid behavior were not inventoried or compared.

## Bounded coverage and open work

Examined: paired Player direct writer and its full local travel gate; pitch source and look-vector calculation; Mth sine-table init/index/casts and double floor helper; BlockPos coordinate conversion; Player-to-LivingEntity travel dispatch and ordering of the inherited `move` call. Not examined: other Player travel deltas, fluid-state implementation or fluid tags/blocks, creative-flight activation, full inherited travel parity, all other tick inputs/consumers, and complete 1.20.4→1.20.5 movement coverage. This operation-specific no-difference result does not freeze the release pair.
