# Independent source audit verdict

Verdict: **accepted with a bounded correction**. The new cobweb falling witness is source-supported as a one-move ordinary LocalPlayer contact consequence. The original short fire witness in `comparison.md` is not supported for 1.21.3: its squared span is below `BlockGetter.boxTraverseBlocks`' `square(0.99999F)` sweep gate, so only the target-box block positions are examined. The witness document explicitly withdraws that example and replaces it with a valid falling span. Do not carry the fire example forward as movement evidence.

This is exact endpoint source acceptance only. It does not freeze all source coverage for the pair, prove an intermediate release boundary, or establish runtime parity.

## Identity and integrity

Reviewed only the three assigned author artifacts. Their SHA-256 values matched the assignment: `ordinary-contact-movement-witness.md` `d2810938c1a0c9473f3c6849223bb10e21d0f5c144299e4f60ac9ab847485738`; `comparison.md` `32302c9d2e4f82c9d4dc4a2ca0971d911e71eeea76996d81ec7f8869f733bebd`; `source-roster.tsv` `9e2c4c90430f782d75c258be7320dd2018313a18f0b19f20173eb2c7666e3900`.

Published roots are `decompiled_minecraft/1.21.3/mojmap` and `decompiled_minecraft/26.2/unobfuscated`. Ready markers report those exact pairs. Their source manifests contain respectively 5,655 and 7,055 entries; every listed file exists and matches its SHA-256 (zero missing, zero mismatches). The manifest SHA-256 matches each ready marker, as do artifact-manifest hashes. Provenance version/namespace agree. Relevant files in the independent roster and supplemental dependency roster also match their manifest entries.

The independent roster was frozen before opening the assigned author scratch. The small supplemental roster records direct dependency files subsequently checked to validate the specific claims.

## Claim findings

1. **Ordinary LocalPlayer callback timing and origin — verified.** `LocalPlayer.tick` reaches `super.tick`; `LivingEntity.tick` performs travel then calls block effects under the client/local-authority gate. In 1.21.3 `Entity.move` does not append ordinary movement; `applyEffectsFromBlocks()` constructs one record from `oldPosition()` to current position and traverses that span. In 26.2 accepted non-`noPhysics` moves append the pre-move position, resolved endpoint, and edge-adjusted request. At the callback, an empty queue falls back to old/current position; a nonempty queue gets an unannotated bridge only when the final endpoint differs by squared distance greater than `9.9999994E-11F`. Axis metadata is present only on the 3-argument `Movement` constructor. The ordinary unmounted air-travel route makes one SELF move, so the witness needs no artificial repeated moves.

2. **No-solid cobweb and movement-state write — verified.** Cobweb is registered with `.noCollission()` / `.noCollision()` in both versions. Its `entityInside` directly calls `makeStuckInBlock`; absent WEAVING, the multiplier is `(0.25, 0.05, 0.25)`. `makeStuckInBlock` resets fall distance and writes `stuckSpeedMultiplier`. On the following non-piston ordinary move, a multiplier with squared length above `1.0E-7` scales the request, clears the multiplier, and zeroes delta movement. The block does not clip the witness movement.

3. **Old swept contact gate and geometry — verified.** The 1.21.3 falling span has squared length `1.440032`, exceeding `square(0.99999F)`, so `boxTraverseBlocks` enters its swept branch. With a 0.6-wide, 1.8-high player at `(1.296,65,0.699)` moving by `(0.004,-1.2,0.004)`, the starting deflated box has no Z overlap with cobweb `(0,64,1)`, while the diagonal interior of the path overlaps its full-block entity-inside shape. The entity callback therefore reaches WebBlock and writes the multiplier.

4. **26.2 axis-split miss at the same endpoints — verified.** The accepted move stores endpoint delta and original movement metadata. Equal absolute X/Z chooses Y-X-Z. The Y and X legs remain outside the cobweb in Z; the Z leg occurs at X `1.300`, with the deflated player box beginning east of the block. The cobweb callback is skipped. A two-field record with the same endpoints would use the direct span branch in the 26.2 consumer, but this is only a fixed-endpoint consumer counterfactual, not ordinary vanilla provenance.

5. **Attribution — verified as carefully scoped.** Across 1.21.3 and 26.2 both movement provenance and traversal implementation change (`boxTraverseBlocks` versus `forEachBlockIntersectedBetween`). Therefore the cross-version difference belongs to the combined old-span/new-axis-record path; provenance alone is not established as the only source change. The fixed-endpoint 26.2 counterfactual isolates that metadata changes the native consumer's traversal branch.

6. **Ordinary falling reachability — source-supported, with velocity history unspecified.** Air travel consumes the existing downward velocity for its single SELF move and applies gravity afterward; the inspected route has no terminal-speed clamp. A downward movement of this size is reachable during an ordinary fall. This audit does not claim a particular prior input produced the exact components.

## Evidence references

1.21.3 Mojmap: `LocalPlayer.tick` 189-192; `LivingEntity.tick` 2768-2776; air move `LivingEntity` 2178-2214 / 2676-2685; `Entity.move` 619-701; `Entity.applyEffectsFromBlocks` 736-752 and 1038-1084; `BlockGetter.boxTraverseBlocks` 172-189; `Blocks.COBWEB` 701-712; `WebBlock.entityInside` 25-33; `Entity.makeStuckInBlock` 2525-2528; `EntityType` default dimensions 1053-1062.

26.2 unobfuscated: `LocalPlayer.tick` 227-245; `LivingEntity.tick` 3137-3145; air travel 2468-2497 / move 2676-2685; `Entity.move` 711-755; `Entity.applyEffectsFromBlocks` 903-929; `Movement` record 4120-4127; `Entity.checkInsideBlocks` 1278-1352; `BlockGetter.forEachBlockIntersectedBetween` 175-209; `Direction.axisStepOrder` 378-380; `Blocks.COBWEB` 716-727; `WebBlock.entityInside` 27-41; `Entity.makeStuckInBlock` 2943-2946; `EntityTypes` dimensions 1163-1173.
