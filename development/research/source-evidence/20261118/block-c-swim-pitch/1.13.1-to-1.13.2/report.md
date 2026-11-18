# 1.13.1 → 1.13.2: player swimming look-vector vertical adjustment

## Bounded result

**No difference found in the assigned operation.** In both exact Ornithe Feather sources, `PlayerEntity.moveRelative(float forwards, float sideways, float upwards)` has the same complete method body (lines 1441–1468). Its UTF-8/LF body digest, including a final LF and excluding surrounding class text, is `46e90d04517eb0ab8ec115569d3e1b86e548e47a83bed07babcc04db4bd7b0b6` on both sides. The containing `PlayerEntity.java` files do differ elsewhere, so this is a bounded method result, not a claim that the classes or pair are identical.

## Compared behavior and order

Both releases save `x`, `y`, and `z`; then, only when `isSwimming()` and not riding, read `g` as the `double` Y component of `getLookVector()`. They choose `h` as `0.085` when `g < -0.2`, otherwise `0.06`. They update `velocityY` only when `g <= 0.0`, jumping, or the fluid state at `new BlockPos(x, y + 1.0 - 0.1, z)` is nonempty, using the exact expression `velocityY + (g - velocityY) * h`. The `y + 1.0 - 0.1` expression is left-associative double arithmetic; the double `BlockPos` constructor forwards to `Vec3i(double, double, double)`, which floors each coordinate.

That adjustment precedes the override's inherited `super.moveRelative(forwards, sideways, upwards)` call. The existing flying-and-not-riding branch saves vertical velocity and air speed, calls the inherited method, restores air speed, scales the saved vertical velocity by `0.6`, clears fall distance, and clears flag 7; otherwise it directly calls the inherited method. Movement-related stats are updated afterward. No separate fluid-motion or collision operation was reviewed in this assignment.

## Ordinary local-player witness and gates

The ordinary client path reaches this override: `LocalClientPlayerEntity.tick()` calls `super.tick()` when its chunk is loaded (line 181–183); its `mobTick()` calls `super.mobTick()` (line 837); `LivingEntity.tick()` invokes `mobTick()` (line 1752); and `LivingEntity.mobTick()` calls virtual `this.moveRelative(this.sidewaysSpeed, this.verticalSpeed, this.forwardSpeed)` (line 1926). `LocalClientPlayerEntity.isLocallyControlled()` returns `true` (lines 489–491).

The operation's swimming gate is not a direct pose-field read. `PlayerEntity.isSwimming()` returns `!abilities.flying && !isSpectator() && super.isSwimming()` (lines 1819–1821 in 1.13.1; same in 1.13.2). Its inherited flag is ordinarily reachable: `Entity.updateSwimming()` sets swimming from sprinting plus either in-water (already swimming) or submerged-in-water (not already swimming), and not riding (lines 953–958); the player override clears swimming while flying and otherwise delegates (1.13.1 `PlayerEntity` lines 1471–1476; same in 1.13.2). A nonflying, nonspectating local player can therefore satisfy the gate in ordinary play. `getLookVector()` passes current `pitch` and `yaw` to `getRotationVector`; that helper converts degrees using `float` intermediates and returns Y as `-sin(pitchRadians)` (both `Entity.java` files, lines 1251–1260 and 1691–1693). The fluid gate uses the exact double-position expression above, followed by `getBlockState(...).getFluidState().isEmpty()`.

## Exact source identities

Mapping family on both sides: `ornithe-feather` (Feather Gen2 build 2 for each exact patch). Canonical read-only roots: `decompiled_minecraft/1.13.1/ornithe-feather/` and `decompiled_minecraft/1.13.2/ornithe-feather/`.

| Release | Ready marker SHA-256 | Provenance JSON SHA-256 | Source-manifest SHA-256 | Source files / bytes | Client SHA-256 |
|---|---|---|---|---:|---|
| 1.13.1 | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | 2710 / 14,768,136 | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` |
| 1.13.2 | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | 2711 / 14,770,525 | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` |

The relevant `ready.json` files report `status: ready`. The 1.13.2 local verification record reports all 2711 published source files verified with zero source-file differences; its one artifact difference concerns a regenerated derived remapped jar, while raw input differences are zero. The inspected source files agree with their per-release `.sources.sha256` entries. Exact source-file SHA-256 values (identical on both sides unless noted):

- `net/minecraft/entity/living/player/PlayerEntity.java`: 1.13.1 `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272`; 1.13.2 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.
- `net/minecraft/entity/living/LivingEntity.java`: `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- `net/minecraft/entity/Entity.java`: `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- `net/minecraft/util/math/BlockPos.java`: `ee71cfdedcaa27458f48168ab761b435cf9c878b04f21e971ddbafb6254aa3fd`.
- `net/minecraft/util/math/Vec3i.java`: `481827f072a5d36ae3a7f133afed5396988e3ce7c0501b2777a92b60c6a498b8`.

## Coverage boundary

Compared the complete paired `PlayerEntity.moveRelative` override; its `isSwimming` guard; the look-vector source; the double `BlockPos`/floor conversion used by the fluid gate; the player swim-flag update gate; and the ordinary local-player `tick`/`mobTick` call path through inherited `moveRelative`. This report does not cover A fluid physics, B collision, or any other 1.13.1 → 1.13.2 movement slice, and does not establish full-pair coverage or freeze.