# Active Elytra glide versus climbable handling: 1.14.1 → 1.14.2

## Scope and result

Compared the exact local Yarn decompilations for an already-active fall-flying flag on an ordinary local player, in dry travel, with a normally recognized ladder or vine under the player's feet. The active flag is an input to this comparison. Elytra activation/start eligibility is a separate predicate and is not assessed here. Geometry/contact, fluids, other entities, damage calculation, and full tick coverage are outside this bounded check.

**Result:** No difference was found in the requested active-glide behavior between 1.14.1 and 1.14.2. The shared source order gives the glide branch precedence over ordinary climbable cleanup. On a local client, both the flag updater and the landing cancellation are server-authoritative; they do not locally clear the active bit. The `fallDistance = 1.0F` write has a strict pre-move Y-velocity gate and is immediately followed by `Entity.move`, whose generic fall bookkeeping can overwrite it based on the actual move result.

## Exact evidence identity

| Release / mappings | Source and manifest | Pinned Yarn inputs | SHA-256 |
|---|---|---|---|
| Minecraft 1.14.1 / Yarn `1.14.1+build.10` | `decompiled_minecraft/1.14.1/yarn/net/minecraft/entity/LivingEntity.java`; `.decompile-provenance.json` | Yarn artifact `yarn-1.14.1+build.10-v2.jar`; metadata `meta.fabricmc.net/v2/versions/yarn/1.14.1` | source `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda`; manifest `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`; artifact `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`; metadata `6d026a20d6165444f67df52ddcc29f575f0267ab4b545c3bb427e5803c434873` |
| Minecraft 1.14.2 / Yarn `1.14.2+build.7` | `decompiled_minecraft/1.14.2/yarn/net/minecraft/entity/LivingEntity.java`; `.decompile-provenance.json` | Yarn artifact `yarn-1.14.2+build.7-v2.jar`; metadata `meta.fabricmc.net/v2/versions/yarn/1.14.2` | source `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78`; manifest `786546f99684423cc2563be45c9c3334dfcc2c7db58dd74ba39ce403bd0869a`; artifact `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`; metadata `a65478802722f65b4dfec15e8a732872f2836e1e9b7ae95d7086c601b07f1d81` |

Both manifests identify the same decompiler pipeline (`lpc-decompile-v1`, Vineflower 1.12.0) and the local Windows 11 / Adoptium Java 25 runtime. The manifest source hashes match the independently computed SHA-256 of each full `LivingEntity.java` file. The mapping artifacts and metadata are pinned by those manifests; no online mapping lookup was used.

## Compared source slices

All paths below are relative to each release's `yarn/` source root.

| Operation | 1.14.1 | 1.14.2 | Comparison |
|---|---|---|---|
| Climbability predicate | `net/minecraft/entity/LivingEntity.java`, `isClimbing()`, lines 1216–1226 | same file, lines 1222–1232 | Same predicate: spectators are not climbing; otherwise it inspects the block at `BlockPos(this)` and applies the same block/trapdoor checks. |
| Travel branch and fall-flying movement | same file, `travel(Vec3d)`, lines 1763–1844 | same file, lines 1769–1846 | Same branch order, strict Y gate, `1.0F` assignment, glide velocity operations, move call, server-only landing clear, and non-glide climb handling. Line offsets reflect surrounding source changes. |
| Active-flag updater | same file, `initAi()`, lines 2216–2235 | same file, lines 2222–2241 | Same predicate and write authority: revalidates an already-set bit against airborne/no-vehicle/usable Elytra state, then calls `setFlag(7, bl)` only when `!world.isClient`. |
| Active-state accessor | same file, `isFallFlying()`, lines 2590–2592 | same file, lines 2596–2598 | Same `return this.getFlag(7)`. |
| Immediate move bookkeeping | `net/minecraft/entity/Entity.java`, `move`, lines 432–479; `fall`, lines 824–834 | same file, `move`, lines 434–481; `fall`, lines 826–836 | Same relevant order: `move` calls `fall` with actual clipped Y displacement and `onGround`; grounded clears distance, otherwise a negative displacement adds to it. |

## Behavior and bounds

1. **Flag authority/cancellation.** `isFallFlying()` reads tracked flag 7. `LivingEntity.initAi()` recomputes that existing flag using `!onGround && !hasVehicle()` and a usable Elytra in the chest slot, but commits the result only on the logical server. In `travel`, landing also clears flag 7 only under `onGround && !world.isClient`. Thus, for an already-active ordinary local client, these two paths do not themselves cancel the active bit. The distinct creative-flight override in `PlayerEntity.travel()` clears fall-flying after its flight branch, but that branch is excluded by the ordinary-player precondition.

2. **Glide precedes climb cleanup.** Within dry travel, after water/lava exclusions, `if (isFallFlying())` selects the glide branch; the `else` contains normal ground/air movement. That ordinary branch invokes `method_18801(getVelocity())` before moving, and `method_18801` zeros `fallDistance` when `isClimbing()` is true. An active glide therefore bypasses that generic climbable velocity/fall-distance cleanup even when the player's feet block makes `isClimbing()` true. Other climb boosts in the non-glide branch are likewise not reached. This does not bypass `Entity.move`'s general fall bookkeeping.

3. **Strict gate and transient write.** At the start of the glide branch, `lv5` snapshots `getVelocity()`. Only if `lv5.y > -0.5` does the code assign `fallDistance = 1.0F`; equality (`-0.5`) does not pass. The comparison occurs before glide velocity adjustments and before `move`, and the assigned value is a float literal.

4. **Immediate overwrite bounds.** The next glide path adjusts velocity and calls `move(...)`. `Entity.move` then calls `fall(actualMovedY, onGround, ...)`. If that move lands, `fall` sets distance to `0.0F`; if actual moved Y is negative without landing, it performs `fallDistance = (float)(fallDistance - actualMovedY)`; if actual moved Y is nonnegative and it did not land, this method does not overwrite the `1.0F`. Therefore the assignment is not an unconditional end-of-tick value. Which case occurs depends on the move result, which is deliberately not modeled in this report.

No separate activation predicate was used as evidence for active glide parity. This is a bounded source comparison, not full 1.14.1–1.14.2 movement-pair coverage, independent audit, or runtime parity evidence.