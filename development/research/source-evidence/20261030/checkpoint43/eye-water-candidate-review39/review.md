# Independent product/candidate review: eye-water query 39

Candidate: `.native-candidates/eye-water-query39`, commit `ce5fa48d52d1369060a662840fa9805fb034571d`.

## Verdict

**Bounded source finding accepted; candidate is not integration-ready as submitted.** The historical operation is the direct current-eye water query in 1.15, 1.15.1, and 1.15.2, followed by a cached read starting in 1.16 and continuing through 1.16.5. The candidate's core 1.15.2 sampling order and expression match the inspected source. Its duplicate operation contract and redirect owner must be reconciled with the existing product seam before integration.

## Source comparison

In exact Mojmap sources, `Player.updateIsUnderwater()` is identical in 1.15, 1.15.1, and 1.15.2: it assigns `isUnderLiquid(FluidTags.WATER, true)` and returns `wasUnderwater` (`Player.java:298-300`). Each `Player.tick()` calls it before `super.tick()` (`Player.java:236-237`). The corresponding `Entity.isUnderLiquid(Tag<Fluid>, boolean)` method matches across all three releases (`Entity.java:1040-1052`): reject any `Boat` vehicle; obtain the live double `getEyeY()`; floor the current X/eye-Y/Z into a `BlockPos`; when requested, reject an unloaded chunk using the floored X/Z shifted by four; fetch that point's `FluidState`; then require the tag and compare the eye against `blockPos.getY() + (fluidState.getHeight(level, blockPos) + 0.11111111F)` with a strict `<`.

The candidate's `UnderwaterPointQuery.isUnderwaterAtCurrentEye` follows that order. `BlockPos.containing(x, y, z)` preserves the floor-point operation, `level().hasChunk(blockX >> 4, blockZ >> 4)` preserves the enabled loaded-chunk check, and the float epsilon is added to the float height before block Y and the strict double comparison. The boat and water-tag gates are present. This is source fidelity for the sampled operation, not runtime parity.

The accepted boundary is supported by all inspected releases: `Player.updateIsUnderwater()` reads `isEyeInFluid(FluidTags.WATER)` in 1.16 through 1.16.5 (`Player.java:291-293`), while 1.15.2 still performs the direct query. Tick order keeps the assigned `wasUnderwater` value in the movement decisions before the later entity tick refreshes the eye-fluid cache. This review accepts the producer-query cutover and its bounded local-player movement relevance, not a float-boundary witness, vehicle behavior, overlapping fluid-wrapper behavior, or runtime parity.

## Product integration blockers

1. **Reuse the existing producer hook and mixin.** Product already has `PlayerEyeWaterQueryHook.queryEyeWater(Player): Optional<Boolean>` and `PlayerMixin.legacyparkourcompat$queryCachedEyeWater`, which redirects the 26.2 `Player.updateIsUnderwater` call and dispatches this same cached-water producer operation. Existing v1_13 and v1_14_2 changes implement the contract. Add the 1.15.2 behavior as another implementation of `PlayerEyeWaterQueryHook`; preserve the existing hook granularity and native fallback. Do not add `UnderwaterPointQueryHook` or `PlayerUnderwaterPointQueryMixin`.

2. **The submitted redirect owner does not match the target bytecode.** `javap -p -c -v` on the actual `minecraft-merged-deobf-26.2.jar` shows `Player.updateIsUnderwater()Z` invoking constant-pool method reference `net/minecraft/world/entity/player/Player.isEyeInFluid:(Lnet/minecraft/tags/TagKey;)Z`. The candidate redirect targets `Entity.isEyeInFluid` with the same descriptor. The product's existing redirect correctly targets the `Player` owner. As submitted, the candidate redirect will not match this invocation under the exact owner target. Simply changing the new redirect owner to `Player` would add a competing redirect to the existing mixin and still be the wrong integration.

3. **Drop the fence change.** `UnderwaterPointQueryFence` emulates 1.14.2 by returning the current cached value, while the product's existing 1.14.2 implementation intentionally supplies the historical direct query. Under a separate hook, these independently resolved answers can compete for the exact same operation; under the existing hook there is no need for a second fence. Keep the established v1_14_2 direct behavior intact.

4. **Keep the modern-vehicle fallback.** The existing product implementation returns empty for a modern `AbstractBoat` subtype that has no 1.15 counterpart, allowing native behavior. The candidate checks only `instanceof Boat` and would apply the historical result to such a subtype. Reuse the existing `Optional<Boolean>` contract and retain that fallback.

Integration shape: a minimal `change/v1_15_2` class implementing `PlayerEyeWaterQueryHook`, returning the 1.15.2 direct point result for represented vehicles and empty for unsupported modern vehicle subtypes. Dispatch remains through the existing `PlayerMixin` redirect whose call owner is `Player`.

## Verification boundary

Read-only source and bytecode inspection only. No build, tests, or game launch were run. Candidate commit hash: `ce5fa48d52d1369060a662840fa9805fb034571d`.
