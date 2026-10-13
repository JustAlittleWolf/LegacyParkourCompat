# F-S1-WATER-DESCENT: creative flight suppresses crouch descent in water

- **1.15.2**, mojmap: `net/minecraft/client/player/LocalPlayer.java`; lines 722-725.
- **1.16.5**, mojmap: `net/minecraft/client/player/LocalPlayer.java`; lines 732-735.

## Difference

For a player in water with shift held, both versions call the same vertical impulse helper when not flying.

In 1.15.2 the call has no flight predicate; in 1.16.5 the player override makes the added `isAffectedByFluids()` predicate false while `abilities.flying` is true.

Thus 1.15.2 applies the downward `-0.04F` addition to a flying player in water, while 1.16.5 skips it.

## Movement meaning

The client player reaches this branch in `LocalPlayer.aiStep()` after input sampling. `Player.isAffectedByFluids()` directly reads the existing flying ability flag; no producer-system behavior is required for the source-level conclusion.

The helper's vector addition is identical on both versions.

The source proves the impulse is added in 1.15.2 and gated off in 1.16.5 under the concrete predicate `isInWater && shiftKeyDown && abilities.flying`.

Whether another movement input changes the resulting tick displacement is outside this isolated source delta.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-WATER-DESCENT.md).
