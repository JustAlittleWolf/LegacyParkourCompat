# F-S1-WATER-DESCENT: creative flight suppresses crouch descent in water

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 722–725.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 732–735.

Difference: For a player in water with shift held, both versions call the same vertical impulse helper when not flying. In 1.15.2 the call has no flight predicate; in 1.16.5 the player override makes the added `isAffectedByFluids()` predicate false while `abilities.flying` is true. Thus 1.15.2 applies the downward `-0.04F` addition to a flying player in water, while 1.16.5 skips it.

Movement meaning: The client player reaches this branch in `LocalPlayer.aiStep()` after input sampling. `Player.isAffectedByFluids()` directly reads the existing flying ability flag; no producer-system behavior is required for the source-level conclusion.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-WATER-DESCENT.md).
