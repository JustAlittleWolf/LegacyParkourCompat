# Addendum: swim pitch block-position predicate

Bound to original source-review report `report.md`, SHA-256 `8003852493e5879fa67270b8b60202f06d189ef88af12442ffee556cdf4e7a38`. This addendum narrows the prior report's unadjudicated `BlockPos` expression in `Player.travel`; it preserves the original report.

## Verdict

**Unchanged predicate coordinates and block-position conversion.** The 1.19.3 constructor call and 1.19.4 `BlockPos.containing` call perform the same ordered double inputs and the same three `Mth.floor(double)` conversions. I found no valid coordinate witness that distinguishes the resulting `BlockPos` values.

## Paired source trace

- 1.19.3 `net/minecraft/world/entity/player/Player.java:1457` calls `new BlockPos(this.getX(), this.getY() + 1.0 - 0.1, this.getZ())`.
- 1.19.4 `net/minecraft/world/entity/player/Player.java:1449` calls `BlockPos.containing(this.getX(), this.getY() + 1.0 - 0.1, this.getZ())`.
- Each coordinate expression is passed in Java's left-to-right argument order: X getter, Y getter and arithmetic, Z getter. `getX()` and `getZ()` return doubles; `getY()` returns a double. The Y expression parses and evaluates left-associatively as `(getY() + 1.0) - 0.1`, using the double literals `1.0` and `0.1`. Both releases preserve this exact expression and its two separately rounded double operations; neither rewrites it as `getY() + 0.9`.
- In 1.19.3, `BlockPos(double,double,double)` (`core/BlockPos.java:46`) delegates to `Vec3i(double,double,double)`; `Vec3i` (`core/Vec3i.java:39–40`) delegates to its integer constructor with `Mth.floor(x)`, `Mth.floor(y)`, and `Mth.floor(z)`, in that order.
- In 1.19.4, `BlockPos.containing(double,double,double)` (`core/BlockPos.java:74–76`) constructs `new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z))`, in the same order.
- `Mth.floor(double)` is the same in both sources: cast the input to `int`, then return the cast value minus one iff the original double is less than that cast value. Thus negative nonintegers floor downward in both; integer values and the Java cast edge cases are handled by the same operation in both. The result is then passed to the same `BlockPos(int,int,int)` constructor.

The conditional remains short-circuited identically: after the sampled pitch-Y test and `jumping`, only a still-false condition evaluates `level.getBlockState(position).getFluidState().isEmpty()`. The version-specific position-expression spelling therefore does not alter the predicate's sample coordinates or conversion.

## Reachability of inherited owner gate

The `LivingEntity.travel` owner gate is after the `Player.travel` swim-pitch block, because `Player.travel` evaluates that block before calling `super.travel`. So it cannot suppress evaluation of this block-position predicate. For the ordinary locally controlled `LocalPlayer`, inherited travel is also reachable in both versions: 1.19.3's gate is `isEffectiveAi() || isControlledByLocalInstance()`, and `LocalPlayer.isEffectiveAi()` returns true; 1.19.4's gate is `isControlledByLocalInstance()`, whose `Entity` implementation falls back to `isEffectiveAi()` when there is no controlling passenger, and `LocalPlayer.isEffectiveAi()` still returns true. The gate rewrite therefore does not make this ordinary owner-path sample unreachable.

## Exact source hashes

All paths are under `decompiled_minecraft/<version>/mojmap/`; digests below are SHA-256 of full source-file bytes.

| Source | 1.19.3 digest | 1.19.4 digest |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` |
| `net/minecraft/core/BlockPos.java` | `19fd76058d98a5bc8f0dfd6aab03524c4941c0407660812d46a22f3cc05fc54a` | `6f7d806a3a3ca3204db248a7d0dba4908685224dc5a3350e2eb51f69f97d9715` |
| `net/minecraft/core/Vec3i.java` | `e48be042611e39e69be4d6858644209cd00dbaa8e389f7a3f92809b51c642d32` | `36db6e4b3001c2ea4e5c218ba601d47b76a91e60455f9088b1db4893cef623c3` |
| `net/minecraft/util/Mth.java` | `f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f` | `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797` |
| `net/minecraft/world/entity/LivingEntity.java` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` |
| `net/minecraft/client/player/LocalPlayer.java` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` |
| `net/minecraft/world/entity/Entity.java` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |
