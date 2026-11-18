# 1.14.2–1.14.3 swim pitch vertical velocity writer

## Scope and verdict

Bounded slice: the player swimming pitch-derived vertical-velocity adjustment in `PlayerEntity.travel`, including the inherited `LivingEntity.travel` movement that consumes the adjusted velocity. **No behavioral difference found** in this operation across 1.14.2 and 1.14.3. This is operation-level source coverage, not full-pair coverage or an acceptance verdict.

## Exact sources and immutable identities

The canonical read-only sources are `decompiled_minecraft/<version>/yarn/`; each manifest's actual relevant source hashes equal its recorded `sources` entries.

| Version / mapping | Source artifact identities |
|---|---|
| 1.14.2 / Yarn `1.14.2+build.7` | manifest SHA-256 `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`; Mojang client SHA-256 `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`; Yarn v2 SHA-256 `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`; Intermediary v2 SHA-256 `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` |
| 1.14.3 / Yarn `1.14.3+build.13` | manifest SHA-256 `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`; Mojang client SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`; Yarn v2 SHA-256 `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`; Intermediary v2 SHA-256 `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9` |

Relevant decompiled source SHA-256 values (also verified against each manifest):

| Source | 1.14.2 | 1.14.3 |
|---|---|---|
| `net/minecraft/entity/player/PlayerEntity.java` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` |
| `net/minecraft/entity/Entity.java` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| `net/minecraft/entity/LivingEntity.java` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| `net/minecraft/entity/MovementType.java` | `87b49d851735f7645f23b1d530ef1e976922054fe4e75e1f83493deaa4ab8231` | `20e65c4fe0927818a97fdf259d6304d6eec1a17aaa5f8c0e9a8a5986afd0047d` |
| `net/minecraft/util/math/MathHelper.java` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` |

## Compared operation

`PlayerEntity.java`, `PlayerEntity.travel(Vec3d)`: 1.14.2 lines 1361–1390; 1.14.3 lines 1363–1392. The swimming branch is textually the same in both:

1. It runs only when `isSwimming() && !hasVehicle()`.
2. It reads `g` as the `double` Y component of `getRotationVector()`.
3. The coefficient is `0.085` iff `g < -0.2`; otherwise it is `0.06`.
4. It writes velocity only when `g <= 0.0 || jumping || !world.getBlockState(new BlockPos(x, y + 1.0 - 0.1, z)).getFluidState().isEmpty()`. Thus the above-position fluid sample is at the floored block position constructed from `(x, y + 0.9, z)`, and a nonempty sample enables the write for positive look-Y.
5. The write is `setVelocity(currentVelocity.add(0.0, (g - currentVelocity.y) * coefficient, 0.0))`. The adjustment is computed before the inherited travel call.

`Entity.java`, `getRotationVector()` delegates with the entity's `float pitch` and `float yaw`; `getRotationVector(float,float)` uses `float` intermediates. It computes `h = pitch * (float)(Math.PI / 180.0)`, `i = -yaw * (float)(Math.PI / 180.0)`, then `m = MathHelper.sin(h)`, returning vector Y as `-m`. This formula and operation order match. The relevant overload is at 1.14.2 lines 1182–1190 and 1.14.3 lines 1226–1234; the no-argument delegation is at lines 1637–1639 and 1677–1679, respectively.

`MathHelper.java` has identical trig lookup code in both releases: `sin(float f)` returns `SINE_TABLE[(int)(f * 10430.378F) & 65535]` (lines 22–23); the float product is cast to `int` before the 16-bit mask. The table has 65,536 entries and is populated from `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)` (lines 11–14). Its source SHA-256 is identical across the pair.

For an active, nonpassenger, non-flying swimming player, `PlayerEntity.travel` then calls `super.travel(arg)`. `LivingEntity.travel` enters its normal movement gate (`canMoveVoluntarily() || isLogicalSideForUpdatingMovement()`). With a player in water and not flying, the inherited water branch applies input acceleration and calls `move(MovementType.field_6308, getVelocity())` in 1.14.2 (lines 1769–1770, 1865–1889), corresponding to `move(MovementType.SELF, getVelocity())` in 1.14.3 (lines 1779–1780, 1875–1898). In the compared enum sources both are the first (ordinal-zero) movement type; Yarn spells that constant `field_6308` in 1.14.2 and `SELF` in 1.14.3. The move therefore consumes the pitch-adjusted velocity; subsequent damping and the existing gravity correction follow it.

## Coverage boundary

Compared: the player writer, its inherited rotation-vector calculation, the trig lookup used for look-Y, and the actual inherited water movement call. Environmental swimming state and fluid/collision outcomes are treated as inputs; their separate implementation is outside this assignment. No other swim, fluid or collision operation was audited.


