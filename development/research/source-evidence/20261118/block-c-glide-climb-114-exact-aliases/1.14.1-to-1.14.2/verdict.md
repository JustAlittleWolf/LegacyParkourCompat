# Independent source audit: active Elytra glide and climbable path, 1.14.1 → 1.14.2

## Verdict

**ACCEPT the bounded no-difference claim, with one scope clarification.** For an ordinary local player whose synchronized fall-flying flag is already true, dry travel enters the same glide branch in both releases before the ordinary climbable path. The exact strict gate and `1.0F` write, velocity-adjustment order, call to `move`, server-side landing clear, and subsequent `Entity.fall` bookkeeping match. The checked predicate recognizes the ladder and vine block IDs identically. This supports no difference for the assigned path; it does not establish full pair coverage or runtime parity.

Clarification: before the water/lava and glide branches, `travel` can set `fallDistance = 0.0F` when vertical velocity is `<= 0.0` and Slow Falling is present. This preceding write is also identical. The glide branch then writes `1.0F` only when its snapshot satisfies `lv5.y > -0.5`; equality fails. Thus the reviewed `1.0F` write is conditional and can replace the earlier zero, not an unconditional tick value.

## Independent trace

- `LivingEntity.isFallFlying()` reads tracked flag 7. `initAi()` retains/revalidates an already-set flag against airborne, no-vehicle, chest-slot Elytra and usability conditions, but its `setFlag(7, bl)` is inside `!world.isClient`; it does not locally clear or revalidate the client's active flag. Glide landing likewise calls `setFlag(7, false)` only under `onGround && !world.isClient`. These findings establish authority for the two inspected writers only; an external tracked-data update is outside this claim.
- In the dry branch, `if (isFallFlying())` precedes the `else` containing ordinary movement. That `else` calls `method_18801(getVelocity())` before `move`; `method_18801` tests `isClimbing()` and clears `fallDistance` before its ladder/vine velocity clamp. The climbing launch adjustment is also in the non-glide `else`. An active glide bypasses both ordinary climb operations.
- `isClimbing()` reads `world.getBlockState(new BlockPos(this))`; the matching `Blocks.field_9983` and `Blocks.field_10597` are registered as `ladder` and `vine`. For the assigned normally recognized local ladder/vine state, the predicate is true in both versions.
- In the glide path, `Vec3d lv5 = getVelocity()` snapshots velocity; the next statement is `if (lv5.y > -0.5) fallDistance = 1.0F`. Rotation and glide math follow, then velocity is set and `move(SELF, getVelocity())` runs. Landing flag clearing follows that move and remains server-only.
- For the ordinary non-noclip move path, `Entity.move` passes the actual clipped Y displacement and `onGround` to `fall`. `fall` clears positive fall distance on landing; otherwise, only negative moved Y writes `(float)(fallDistance - d)`. Nonnegative moved Y without landing leaves the value alone in this method. The same source order and expressions appear in both versions.
- `PlayerEntity.travel` delegates to `super.travel` for the ordinary non-ability-flight branch; the creative ability-flight branch is outside the assignment. Water/lava, geometry outcomes, status effects beyond the noted preceding Slow Falling write, activation/start eligibility, and other lifecycle writers are not being claimed.

## Exact evidence and hashes

Frozen input report SHA-256: `3aa1987ec03db2c0acfe4f1b1a275e959d4d15074dbe73e8ac0c4c082bd6bf84c`.

Both canonical manifests identify `lpc-decompile-v1`, Vineflower 1.12.0, Windows 11 / Adoptium Java 25.0.3. The actual full source hashes below match each manifest's `sources` entries. Each manifest's Yarn artifact and mapping-metadata pins also match the actual cache bytes and sidecar URL records.

| Evidence | 1.14.1 / Yarn 1.14.1+build.10 | 1.14.2 / Yarn 1.14.2+build.7 |
|---|---|---|
| Manifest `decompiled_minecraft/<version>/yarn/.decompile-provenance.json` SHA-256 | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` | `786546f99684423cc2563be45c9c3334dfcc2c7db58dd74ba39ce403bd0869a` |
| `net/minecraft/entity/LivingEntity.java` SHA-256 | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` |
| `net/minecraft/entity/Entity.java` SHA-256 | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` |
| `net/minecraft/entity/player/PlayerEntity.java` SHA-256 | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` |
| Yarn artifact SHA-256 (actual bytes = manifest input = pin sidecar) | `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` | `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` |
| Yarn metadata SHA-256 (actual bytes = manifest input = pin sidecar) | `6d026a20d6165444f67df52ddcc29f575f0267ab4b545c3bb427e5803c434873` | `a65478802722f65b4dfec15e8a732872f2836e1e9b7ae95d7086c601b07f1d81` |

Relevant exact source references (all paths relative to each version's `yarn/` root):

- `net/minecraft/entity/LivingEntity.java`: `isClimbing()` 1216–1226 / 1222–1232; `travel(Vec3d)` 1763–1844 / 1769–1850; `method_18801(Vec3d)` 1921–1935 / 1927–1941; `initAi()` 2216–2235 / 2222–2241; `isFallFlying()` 2590–2592 / 2596–2598.
- `net/minecraft/entity/Entity.java`: `move(MovementType, Vec3d)` 432–479 / 434–481; `fall(double, boolean, BlockState, BlockPos)` 824–834 / 826–836.
- `net/minecraft/entity/player/PlayerEntity.java`: `travel(Vec3d)` 1362–1388 in both releases; ordinary branch calls `super.travel(arg)`.
- `net/minecraft/block/Blocks.java`: ladder registration at line 474 and vine registration at line 749 in both releases.

The published source, mapping inputs and manifests were read-only. No generation, product/history/wiki inspection, build, test or launch was performed.