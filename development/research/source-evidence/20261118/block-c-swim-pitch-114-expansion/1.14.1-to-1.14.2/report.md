# 1.14.1 → 1.14.2 swim pitch vertical-velocity writer

**Result:** No source difference in the assigned operation. The `PlayerEntity.travel(Vec3d)` method body and its exact look-vector/LUT helpers are identical in the two locally decompiled Yarn sources. This is a narrow operation result, not a full pair-coverage or freeze claim.

## Exact source comparison

- 1.14.1, Yarn `1.14.1+build.10`: `decompiled_minecraft/1.14.1/yarn/net/minecraft/entity/player/PlayerEntity.java`, `travel(Vec3d)`, lines 1361–1390; `Entity.java`, no-argument and float-argument `getRotationVector`, lines 1635–1637 and 1180–1188; `MathHelper.java`, sine table and `sin`/`cos`, lines 11–30.
- 1.14.2, Yarn `1.14.2+build.7`: corresponding files/members at `PlayerEntity.java` 1361–1390; `Entity.java` 1637–1639 and 1182–1190; `MathHelper.java` 11–30.
- `PlayerEntity.travel(Vec3d)` method text SHA-256 is `eb0a801f6dcf105ad76956fb18307c693545d81cb34e719707312cac4ee87daf` in both sources. The float overload of `Entity.getRotationVector` is `5466707ca701997c0b99683fdc66896800a1ff3ff4e46e927e3a3be629635073` in both; the no-argument overload is `dc2907644db39a9df72145ac6193c225af5c77dcd85740de167a4afe4892fa04` in both. `LivingEntity.travel(Vec3d)` method text also matches exactly (SHA-256 `4e0112ed5d80b913a9008fd6cc12b91acca4f853a7f51df060a5274116fd9730`).

## Operation and reachable path

`PlayerEntity.travel` first snapshots `x`, `y`, and `z`. Its writer is gated by `isSwimming() && !hasVehicle()`. It reads `g` as a `double` from `getRotationVector().y`, then chooses `h` using the strict threshold `g < -0.2 ? 0.085 : 0.06`. The write gate is the left-to-right short-circuit expression `g <= 0.0 || jumping || !world.getBlockState(new BlockPos(x, y + 1.0 - 0.1, z)).getFluidState().isEmpty()`. Thus the above-position sample is reached only when `g > 0.0` and `jumping` is false. When the gate succeeds, it reads the current velocity and sets `lv.add(0.0, (g - lv.y) * h, 0.0)`; this interpolation is double arithmetic after `g` was widened into the vector.

The look-Y value originates in `Entity.getRotationVector()`, which passes the entity's float `pitch` and `yaw` directly to `getRotationVector(float,float)`. That helper converts each angle to radians in `float`, obtains `j/k/l/m` as `float` values from `MathHelper.cos/sin`, and constructs `new Vec3d(k * l, -m, j * l)`. The Y component is therefore the float expression `-m`, widened to the vector's double component; it is not recomputed with double trigonometry. `MathHelper` fills a 65,536-entry float sine table from `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)`. `sin` indexes `(int)(f * 10430.378F) & 65535`; `cos` adds `16384.0F` before the cast/mask. These table inputs, casts, indices and expressions match across the pair.

For an ordinary active swimming player who is not a passenger and is not flying, this writer runs before `PlayerEntity` takes its non-flight `super.travel(arg)` path. The inherited `LivingEntity.travel` is entered through its ordinary voluntary/logical-side movement gate; its water path calls `this.move(MovementType.field_6308, this.getVelocity())` after the velocity update. `PlayerEntity` and `LivingEntity` declare no `move` override in either source, so that virtual call resolves to inherited `Entity.move` (`Entity.java` lines 432 in 1.14.1 and 434 in 1.14.2). The normal living tick reaches travel via virtual `this.travel(new Vec3d(sidewaysSpeed, upwardSpeed, forwardSpeed))` (`LivingEntity.java` lines 2204 and 2210 respectively). No fluid or collision response mechanics are evaluated here.

## Local evidence identity checks

Both hidden `.decompile-provenance.json` files and the corresponding local source/cache bytes were read only. Each targeted source file (`PlayerEntity.java`, `LivingEntity.java`, `Entity.java`, and `MathHelper.java`) matched its manifest `sources` SHA-256. For both exact releases, the local `client.jar`, Yarn mapping JAR, Intermediary mapping JAR, and cached `version.json` matched both their local `.pin.json` SHA-256 and the provenance input hash. The manifest files themselves hash to:

- 1.14.1: `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`
- 1.14.2: `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`

The Yarn/Intermediary/client hashes are, respectively:

- 1.14.1: `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` / `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d` / `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`.
- 1.14.2: `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` / `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` / `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`.
