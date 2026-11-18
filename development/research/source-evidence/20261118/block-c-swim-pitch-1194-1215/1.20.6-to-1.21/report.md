# 1.20.6 → 1.21.0 player swim-pitch vertical adjustment

## Scope and verdict

Compared the ordinary nonpassenger `Player.travel(Vec3)` pre-super swimming block, its raw look-vector calculation, the `Mth` trig lookup, and the inherited `LivingEntity.travel(Vec3)` path for a locally controlled player. The pre-super operation is unchanged between 1.20.6 and 1.21.0; this comparison supports **no independent source delta** for this operation. The inherited water-movement path does have a separate 1.21 change, noted below because it affects the movement that follows the adjustment.

## Exact-source evidence

**1.20.6, Mojmap:** `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/player/Player.java`, `travel(Vec3)`, lines 1472–1494. `Entity.java`, `getLookAngle()` lines 2027–2028 and `calculateViewVector(float,float)` lines 1479–1486. `Mth.java`, trig table initialization lines 31–35 and `sin(float)`/`cos(float)` lines 48–53. Inherited `LivingEntity.travel(Vec3)`, lines 2104 onward.

**1.21.0, Mojmap:** `decompiled_minecraft/1.21/mojmap/net/minecraft/world/entity/player/Player.java`, `travel(Vec3)`, lines 1455–1477. `Entity.java`, `getLookAngle()` lines 2099–2100 and `calculateViewVector(float,float)` lines 1530–1537. `Mth.java`, trig table initialization lines 31–35 and `sin(float)`/`cos(float)` lines 48–53. Inherited `LivingEntity.travel(Vec3)`, lines 2091 onward.

The source text of the paired `Player.travel` pre-super blocks, `Entity.getLookAngle`, `Entity.calculateViewVector`, and `Mth` table/trig methods matches. Whole-file hashes differ between releases because the surrounding classes changed; the per-file source hashes below bind the reviewed canonical files.

## Operation and exact math

Both `Player.travel` methods first gate the adjustment on `isSwimming() && !isPassenger()`. They then read `double lookY = getLookAngle().y` and choose a double coefficient: `0.085` only when `lookY < -0.2`, otherwise `0.06`. The update gate is the short-circuit expression `lookY <= 0.0 || jumping || !aboveBlockFluidIsEmpty`. Thus the above-block query is skipped whenever `lookY <= 0.0` or jumping is true. When reached, it reads `getBlockState(BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())).getFluidState().isEmpty()`; the sampled Y expression is double arithmetic at `y + 0.9`, then `BlockPos.containing` floors to the block position. If the gate passes, it reads current delta movement and adds `(lookY - velocityY) * coefficient` to Y, leaving X/Z unchanged.

`getLookAngle()` uses current `getXRot()` and `getYRot()` directly (not interpolated view rotations). `calculateViewVector` computes float radians in this order: `pitch * (float)(Math.PI / 180.0)` and `-yaw * (float)(Math.PI / 180.0)`; it obtains table-backed float cos/sin for those values and returns `(sinYaw * cosPitch, -sinPitch, cosYaw * cosPitch)` as a `Vec3`. `Mth.sin` indexes `SIN[(int)(radians * 10430.378F) & 65535]`; `cos` uses `SIN[(int)(radians * 10430.378F + 16384.0F) & 65535]`. The 65,536-entry float table is initialized from `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`. These formulas, widths, casts, index masking, and operation order are unchanged.

For ordinary pitch values, positive raw X rotation (looking down) produces negative look-vector Y; the `-0.2` threshold therefore selects the stronger coefficient for sufficiently downward-looking swimmers. This describes the direct source math; no trajectory claim is made.

## Inherited movement context

The call after the adjustment remains `super.travel(input)` for the ordinary nonflying player path in both versions. On a locally controlled player, `LivingEntity.travel` reaches its water branch when `isInWater() && isAffectedByFluids() && !canStandOnFluid(fluidAtBlockPosition)`. That inherited branch changes independently: 1.20.6 derives its water-efficiency scalar from `EnchantmentHelper.getDepthStrider(this)`, caps it at 3, and divides the water-slowdown and input-speed interpolation by 3; 1.21 reads the `WATER_MOVEMENT_EFFICIENCY` attribute and uses that scalar directly in both interpolations (with the same off-ground half multiplier). This downstream behavior must not be mistaken for a change to the unchanged swim-pitch adjustment.

## Readiness, provenance, and relied-byte checks

The 1.20.6 tree has `mojmap.ready.json` (`status: ready`, `versionId: 1.20.6`, mapping `mojmap`), a `mojmap.sources.sha256` source manifest, `artifacts.sha256`, and `mojmap.provenance.json`. The ready marker's declared source-manifest digest is `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; its declared artifact-manifest digest is `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`. Both match the actual manifest bytes. The 1.21 tree has no sibling ready marker; its canonical source directory contains `.decompile-provenance.json`, which pins the exact client, official mapping input, toolchain, and source hashes. Do not treat the missing marker as a ready marker.

SHA-256 checks of every relied Java source matched its canonical manifest entry on both sides:

| Release | Source file | SHA-256 |
| --- | --- | --- |
| 1.20.6 | `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| 1.20.6 | `net/minecraft/world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| 1.20.6 | `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| 1.20.6 | `net/minecraft/util/Mth.java` | `f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d` |
| 1.21.0 | `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| 1.21.0 | `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| 1.21.0 | `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| 1.21.0 | `net/minecraft/util/Mth.java` | `aa40272f0f980438ca7e8c874ceedc664feb77958b162d7aa3ed01f233bd2deb` |

Metadata identities: 1.20.6 `mojmap.ready.json` SHA-256 `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41`; `mojmap.provenance.json` SHA-256 `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8`. 1.21.0 `.decompile-provenance.json` SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`.
