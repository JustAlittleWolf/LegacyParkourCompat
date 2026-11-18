# Independent exact draft review

## Bound inputs

- Author artifact: `source/comparison.md`, expected SHA-256 `93f6424003875573ba0053a3d61a937a3172d9ca02dc19e47dcf5f7fb3227df1`; observed SHA-256 matches. The file is 8,135 bytes, UTF-8 without BOM, LF.
- Frozen roster: `blind-audit/frozen.md`, expected SHA-256 `9a7d7b9afd9b8107664a21c2004fc8d60f610692c26ea3f6aa63ccb822dca082`; observed SHA-256 matches. The file is 7,896 bytes, UTF-8 without BOM, LF.
- The draft and frozen roster were left unchanged. Canonical Mojmap source bodies were checked only under the authorized 1.20 and 1.20.1 roots. Relevant paired source payloads remain byte-identical by the digests in the frozen roster.

## Four operation verdicts

1. **Body-fluid admission and stored height — ACCEPT.** The stated order, unloaded-chunk rejection, `0.001` box deflation, floor/ceil scan bounds, fluid-tag and surface admission, maximum overlap depth, water flag write, and `!firstTick` lava query match `Entity.java` and the `FluidState`/`FlowingFluid` height delegates. This verdict is conditional on the same tag membership, fluid states/heights, dimension type and chunk availability, as the draft says.

2. **Current accumulation and normalization — ACCEPT.** The cited flow scan, under-`0.4` depth scaling, sum/count averaging, player-specific non-normalization, supplied push coefficient, minimum-push comparisons, and velocity addition match `Entity.java`, `Player.java`, `FluidState.java`, and `FlowingFluid.java`. It depends on the same effective tagged cells, sampled flow states/neighbors, current velocity and flying state.

3. **Eye-fluid writer and read order — REJECT AS WRITTEN.** `Player.tick` does call `updateIsUnderwater()` before `super.tick()`, and `Player.updateIsUnderwater()` samples `isEyeInFluid(WATER)`. But the draft then describes the reachable local player's `isUnderWater()` as `Entity.isUnderWater()` (`wasEyeInWater && isInWater()`). `LocalPlayer` overrides that method at `LocalPlayer.java:1059-1062` to return `wasUnderwater` directly. Its `updateIsUnderwater()` override at `1064-1082` calls the player writer, then handles underwater sound transitions. Thus, for this client local player, the relevant predicate does not add the generic entity's body-water gate. This is a material reader/applicability error in the eye operation's trace. The exact `Entity`, `Player`, and `LocalPlayer` files are byte-identical across the pair, so the bounded no-difference result remains supported; the paragraph needs correction before acceptance. The applicable membership/tag, sampled eye fluid/height, and tick-order inputs must still match.

4. **First ordinary water/lava movement response — ACCEPT.** The local-control gate, `Player.travel` swimming pre-adjustment, water-before-lava branch priority, fluid-effect and standable-fluid gates, water drag and falling adjustment, lava depth threshold/damping/gravity gate, and collision lift match the cited `Player`/`LivingEntity` bodies. This verdict assumes the same player flags/effects/velocity, effective water/lava membership and heights, fluid at `blockPosition`, dimension/gravity inputs and collision/free-space results. The draft correctly identifies `getFluidFallingAdjustedMovement` as `LivingEntity.java:2184-2197`.

## Source-path correction and residual caveat

The frozen roster's two travel rows mistakenly label `getFluidFallingAdjustedMovement` as `Entity` source; the method is `LivingEntity.java:2184-2197`. The draft cites its correct owner. This path-label error does not affect the equality result because both `LivingEntity.java` files were hash-checked and are byte-identical, but the frozen file remains unchanged as required.

Other exclusions and limitations in the draft are acceptable for this bounded comparison: identical code does not establish runtime parity or a patch introduction boundary, and this review does not independently compare data resources or world inputs.