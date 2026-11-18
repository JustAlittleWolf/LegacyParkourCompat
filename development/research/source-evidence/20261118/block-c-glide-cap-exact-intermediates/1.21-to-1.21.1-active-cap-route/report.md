# 1.21 → 1.21.1: active Elytra glide at climbable feet

## Result

**No source difference found in the assigned bounded operation.** The paired `LocalPlayer.java`, `Player.java`, `LivingEntity.java`, and `Entity.java` files are byte-identical between the canonical Mojmap source trees. Their hashes match the exact source manifests on both sides. The active default chest-slot Elytra path therefore has identical gates, shared flag writes, first travel branch, fall-distance cap, movement call, and climbable cleanup ordering in these two releases.

This is a bounded operation result, not a full movement-pair coverage or freeze verdict. No wiki, product implementation, Git history, or prior report was consulted.

## Exact source identity

- Older: Minecraft **1.21**, official Mojang mappings (`mojmap`), canonical source root `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21/mojmap/`.
  - Hidden provenance: `.decompile-provenance.json`, SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`.
  - The provenance inputs identify exact metadata `.../1.21.json` (`cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`), client jar (`4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`) and official client mappings text (`127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`). The manifest pins Vineflower 1.12.0 and the 1.21 source hashes.
- Newer: Minecraft **1.21.1**, official Mojang mappings (`mojmap`), canonical source root `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.1/mojmap/`.
  - Hidden prepared-source provenance: sibling `mojmap.provenance.json`, SHA-256 `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9` (schema 1, `versionId: 1.21.1`, namespace `mojmap`, mapping option `mojmap`, batch IDs 1.21.1/1.21.3/1.21.4/1.21.5; Vineflower 1.12.0).
  - Ready marker: sibling `mojmap.ready.json`, SHA-256 `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`. It identifies version 1.21.1, Mojmap, 5,363 source files, and source-manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`.
  - Artifact manifest `artifacts.sha256` pins 1.21.1 client jar `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`, `client_mappings.txt` `140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec`, and `client-mojmap.jar` `6b36d2ccc99e7eeb98e942b6dc093388d66eb6ede06b64238f35a0e26b092abe`.

Paired source SHA-256 (each value was checked against the source file and that side's canonical source manifest):

| Relative Mojmap source | 1.21 and 1.21.1 SHA-256 |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |

The older source hashes above occur in `1.21/mojmap/.decompile-provenance.json`. The newer values occur in `1.21.1/mojmap.sources.sha256`; that file's actual SHA matches the newer ready marker. Thus the byte-equality comparison is against the exact listed release source artifacts, not inferred from matching names.

## Bounded tick path and operation order

All ranges below are identical in both releases; paths are relative to each release's `mojmap/` root.

1. **Client input / start edge:** `net/minecraft/client/player/LocalPlayer.java:644–748`. `aiStep` saves the old jump input, processes ordinary/ability jumps, then tests Elytra start at `741–745`: jumping, no ability-flight action, not already jumping, not flying, not a passenger, and **not `onClimbable()`**; chest slot must be Elytra with `ElytraItem.isFlyEnabled`, and `tryToStartFallFlying()` must succeed. On success `Player.java:1529–1543` further requires off ground, not already fall-flying, not in water, no levitation, and enabled chest Elytra; it writes shared flag 7 true. LocalPlayer sends `START_FALL_FLYING` after that write, then snapshots `wasFallFlying` at `LocalPlayer.java:748`. For this assignment's already-active case, the start edge is bypassed; the climbable check prevents a new start there but does not itself clear an existing flag.
2. **Flag validation / before travel:** `net/minecraft/client/player/LocalPlayer.java:804` calls `super.aiStep()`. `net/minecraft/world/entity/LivingEntity.java:2665–2678` damps input, calls `updateFallFlying()` at `2668`, then proceeds to travel. In `updateFallFlying` (`2716–2740`), the current flag is retained only if set, airborne, not a passenger, no levitation, and the chest Elytra remains enabled. Failure clears the local boolean. The data flag is written at `2738–2739` only on a non-client level; for this local client branch there is no server-side flag write. No climbable predicate occurs in this validation.
3. **First glide branch and move:** `net/minecraft/world/entity/LivingEntity.java:2151–2177`. The glide branch is selected by `isFallFlying()`. Its first operation is `checkSlowFallDistance()` and only then does it read velocity/look and calculate glide deltas. At `2176` it applies the `.99F, .98F, .99F` multipliers; at `2177` it calls `move(MoverType.SELF, deltaMovement)`. This branch does not call the normal-ground branch's climbable cleanup.
At the terminal edge, `LivingEntity.java:2178–2190` checks for ground only after the glide move; on a non-client level it then clears shared flag 7. That landing tick has already used the glide branch and completed its move. On the following tick, a cleared flag selects ordinary travel, where climbable cleanup occurs before movement. The client branch does not write the flag in either `updateFallFlying` or this landing check; it consumes the synchronized flag state. `LocalPlayer.java:528–530` only uses the false-to-true synchronized transition to play the Elytra sound and does not alter movement.

4. **Climb cleanup in the ordinary branch:** `LivingEntity.java:2191–2215, 2252–2294`. If the glide branch is not selected, normal friction movement runs through `handleRelativeFrictionAndCalculateMovement`: after `moveRelative`, it invokes `handleOnClimbable` before `move`. For a climbable state that helper first resets fall distance to `0.0F`, then clamps horizontal velocity to ±`0.15F`, limits downward velocity to at least `-0.15F`, and conditionally cancels downward sliding for a sneaking player. The glide path precedes this normal branch, so already-active glide at a climbable feet position takes the glide move without this cleanup.
5. **Fall-distance cap and timing:** `net/minecraft/world/entity/Entity.java:2386–2394`. `checkSlowFallDistance` sets `fallDistance = 1.0F` exactly when `deltaMovement.y() > -0.5` and existing `fallDistance > 1.0F`; otherwise it makes no change. It is called at the **start of the selected glide travel branch**, before glide velocity calculation and before the first `move`, not after movement. `Entity.java:645–648, 1163–1178` then handles the move result: grounded movement forwards positive fall distance to the supporting block and resets it; a negative resolved Y displacement while airborne adds `-(float) displacementY`. On a normal climbable move, the helper's reset precedes movement and this later move accounting.

For the fixed standard local-player, dry, on-foot, active-flag and enabled default chest Elytra precondition, changing the feet state's climbable classifier among ordinary non-climbable and ladder/vine climbable feet states changes only whether the *new-start* gate passes; it does not add a climbable rejection to already-active `updateFallFlying`. If still airborne, update retains the active flag and the same first glide branch is reached. The player-visible difference is specifically the different ordinary-branch climb cleanup being bypassed while active gliding; this behavior is identical in 1.21 and 1.21.1. If the player is grounded, becomes a passenger, gains levitation, or the equipped Elytra becomes disabled/absent, the server-side `updateFallFlying` clears its candidate and writes shared flag 7 false before travel; those states are outside the stated fixed active precondition. The client does not perform that write and leaves the glide branch when it consumes the synchronized false flag.

## A/B environmental and collision boundaries

- **A, environmental/block-contact boundary:** `LivingEntity.onClimbable()` (`LivingEntity.java:1517–1530`) reads the feet/current in-block state, rejects spectators, and accepts `BlockTags.CLIMBABLE` (or usable trapdoors) while recording `lastClimbablePos`. The climbing-state query is therefore an input to both the local start gate and normal climb cleanup. The direct ground landing callback is `Entity.checkFallDamage` (`Entity.java:1163–1178`, call at `645–648`); an airborne first glide move has no `fallOn` call unless that move lands. No block-specific external player response is written in the glide branch before that move.
- **B, collision/shape boundary:** `LivingEntity.travel` calls `Entity.move` at the first glide move. The movement resolution determines the actual displacement and `onGround` state, which feed the fall-distance update and later glide validity check. Ladder/vine collision shape behavior and collision-provider implementation are external dependencies of this bounded state/locomotion trace; they are not modified by the four compared methods. No source difference is found in this operation's player-side call boundary.

These A/B notes bound the cross-domain dependencies only; they do not claim complete environmental-block or collision inventory coverage.


