# Callback adapter review supplement: composed Bubble Column and Lava Cauldron

This supplement extends `review.md` without changing its bytes. It reviews the composed product at exact revision `67dbf3736a9cd464422a849a8d704ee37620c57f`, including already-present versioned Bubble Column hooks.

**Updated verdict: the prior Bubble Column finding is a false positive and is withdrawn. The Lava Cauldron finding is fixed by this revision.** No actionable callback-body source-fidelity issue remains from the two findings in the initial review.

## Bubble Column correction

The original review considered only the direct 26.2 callback call in `EntityInsideBlocksHistoricalSweep`. That missed the existing `BubbleColumnBlockMixin` injection, which runs during that call.

For an admitted Bubble Column cell, the sweep forces `precise=true`; 26.2 `BubbleColumnBlock.entityInside` then computes and stores `nothingAbove` from the modern empty-collision/empty-fluid predicate. `BubbleColumnBlockMixin.legacyparkourcompat$useHistoricalAboveContact` modifies that stored local via `BubbleColumnAboveContactHook`. Resolver direction selects the closest change at or later than the selected profile, so profile 1.21.2 selects `change/v1_21_4/BubbleColumnAboveContact`. Its `isAboveContact` returns `stateAbove.isAir()` for profile 1.21.2 (the `selected.olderThan(V1_13)` condition is false). This restores the 1.21.2/1.21.3 `BubbleColumnBlock.entityInside` air-only branch before the callback chooses the above-versus-inside entity response.

Thus forcing precision is intentional to preserve the old unconditional callback response, and the composed versioned hook restores the old air-only predicate. The earlier finding that the callback still used the modern predicate does not hold for the full product.

## Lava Cauldron correction

Revision `67dbf3736a9cd464422a849a8d704ee37620c57f` changes `SynchronousBlockResponses` to call `applyLavaCauldron`. It obtains the old state-dependent content height through `AbstractCauldronBlockAccessor`, applies both old inequalities from `AbstractCauldronBlock.isEntityInsideContent`, checks `!entity.fireImmune()`, and invokes `igniteForSeconds(15.0F)` synchronously. It omits only the old `lavaHurt` server damage/sound branch and does not register modern deferred effects. This matches the accepted movement-relevant old callback contract.

## Binding

- Product HEAD: `67dbf3736a9cd464422a849a8d704ee37620c57f`; worktree was clean at review.
- Prior immutable review: `.native-source/20261030/callback-contract-review31/review.md`, SHA-256 `89307AA6F430482BDCBA0704558FDD48760E499E6DD436EC88B1A5ED20D98FE0`.
- Exact source pair evidence remains the cited 1.21.2/1.21.3 Mojmap and 26.2 unobfuscated source under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. The Bubble Column integration evidence is the versioned hook/change and source boundary described above.

This supplement is source/static review only; no product changes, builds, tests or runtime launches were performed.
