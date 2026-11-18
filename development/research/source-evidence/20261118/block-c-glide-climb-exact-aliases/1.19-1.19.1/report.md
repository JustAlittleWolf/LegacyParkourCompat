# 1.19 → 1.19.1 Elytra/climbable alias gate

## Verdict

For the bounded `LivingEntity.travel` glide path, the 1.19 and 1.19.1 Mojmap sources have the same strict vertical-velocity gate and the same pre-move `fallDistance` write. An already-active, ordinary dry Elytra player enters `else if (this.isFallFlying())`; a ladder or vine at the feet is a valid climbable witness, but climbability does not gate or replace that glide-path write. This is a no-difference result for this operation and pair only. It does not establish full-pair coverage, profile selection/upstream behavior, an implementation cutover, or the final tick value after `move` and later tick writers.

## Exact source evidence

Mapping family: Mojmap. The paired `LivingEntity.java` excerpts are byte-addressed by the hashes below.

| Release | Source member and lines | SHA-256 of source bytes |
|---|---|---|
| 1.19 | `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2042–2045 and 2107–2136 | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` |
| 1.19.1 | `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2042–2045 and 2107–2136 | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |

On both sides, `travel` first requires `isEffectiveAi() || isControlledByLocalInstance()` (2042–2045). In the dry, non-lava case, the `else if (this.isFallFlying())` branch begins at line 2107. It reads delta movement into `$$13`, then uses the strict condition `$$13.y > -0.5` (2108–2109); equality does not pass. The branch directly assigns the exact float literal `this.fallDistance = 1.0F` at line 2110, before look-angle and glide calculations. It later damps the glide vector with `multiply(0.99F, 0.98F, 0.99F)`, writes it with `setDeltaMovement` (2135), and immediately calls the bound `move(MoverType.SELF, this.getDeltaMovement())` (2136). The literal, condition, and statement order match in 1.19.1.

The paired climbable predicate is `LivingEntity.onClimbable()`, lines 1462–1477 in both files. It rejects spectators, obtains `blockPosition()` and `getFeetBlockState()`, and accepts a feet state tagged `BlockTags.CLIMBABLE` (1466–1470). The same release's `BlockTagsProvider.java`, lines 607–619, places `Blocks.LADDER` and `Blocks.VINE` in that tag. Its SHA-256 is `7386e263290f48276ad9ed1a099cb4cb630529ebac22e9dfd94d748007411ea0` in both releases. `onClimbable()` also recognizes a specific open trapdoor over a same-facing ladder (1471–1473), which is not needed for the ladder/vine witness.

In both releases, `handleRelativeFrictionAndCalculateMovement` calls `handleOnClimbable` before its own ordinary `move` (2194–2197); `handleOnClimbable` resets fall distance only when `onClimbable()` is true (2222–2234). The active Elytra branch in `travel` reaches its glide `move` at 2136 without entering that ordinary friction/climbable path. Thus the climbable feet witness is not a pre-move fall-distance reset on this branch. This statement is limited to explicit direct writes and branch ordering in `travel`; it does not claim what collision handling inside `move` or subsequent tick operations leave as the final tick value.

## Readiness and immutable source identity

Both adjacent campaign readiness markers say `status: ready`, identify the requested exact version and `mojmap`, and bind source, artifact, and diagnostic manifests. Their per-version `mojmap.provenance.json` records agree with those identities. The source-manifest entries were checked against the published files: 1.19 has 4,430 files / 21,697,172 bytes / zero mismatches; 1.19.1 has 4,480 files / 21,921,512 bytes / zero mismatches. The hidden `mojmap/.decompile-provenance.json` is absent on both sides; no hidden-provenance claim is made.

| Release | `mojmap.ready.json` SHA-256 | `mojmap.provenance.json` SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Diagnostics SHA-256 | Client SHA-256 (recorded) |
|---|---|---|---|---|---|---|
| 1.19 | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | `eee4a52f029446e075ff10ef761cea9a621b40df6e66240b6323d1596ca60ebf` | `e666eb5dd7bdbea46599f85b5ae720077338255df0b9d0cc5b63fe6f292faa4c` | `ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764` |
| 1.19.1 | `c7b5373078eb777895a4f4b10d8c99eb289cb14fa1835004c1eb4563b6a8a690` | `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | `aae6327967e07f5cdd92ac9bfa6d9c606e1be9a96e24dd30acad07fd2425df41` | `afd25c98efb5dbf3ae434f5c7be063a176f28c2ae42d37ec60db9979d5301163` | `146d4018cfed50c5e77483940653091f186d2eea9ad42fc40939f5c72d6b7f52` |

The provenance records note a package-private access correction in Mojmap remapping and Vineflower processed-twice notices, with no decompiler errors logged. These are retained as provenance caveats; the readiness/source hashes above bind the exact reviewed bytes.
