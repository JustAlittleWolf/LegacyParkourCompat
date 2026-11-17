# Independent bounded source audit: local-player sprint gates r2

## Verdict binding

**ACCEPT — bounded finding only** for `build/later-source-20261116/1.21.3--1.21.4/modifiers/local-player-sprint-gates-r2.md`, exact draft SHA-256 `393D9463D5B50A646BD7ECAD3571D9715078BFA9EC44742B6F4557ABF684FBA5`.

This verdict supersedes neither the separate r1 `REQUEST CHANGES` report nor the pair-level coverage gate. It accepts this corrected finding as a bounded source difference; it does not establish full pair coverage, implementation acceptance beyond the finding-level source gate, or runtime parity.

## Evidence identity

The compared source trees are exact Mojmap decompilations. Provenance file hashes match the dispatch identities, and their ready markers identify matching source manifests:

- 1.21.3 provenance SHA-256: `24093437C0CD41D807AB0829068EF97828B65BBB95B38DFDC4F706883A8483D7`; source-manifest SHA-256: `D673BB5464853E3A2A92ED9B1FFE1789D4E62C097BB884F67C336FCBB3B178CE`.
- 1.21.4 provenance SHA-256: `AB0439864C89F4E0DAB09DAAE7B57AB973368EEB0320626822E06F5A9BCE9FB9`; source-manifest SHA-256: `F90B61197928632E061EA877955A19055C92AE6F357C2DAF1BC646172C6F51F0`.

The draft's cited LocalPlayer and KeyboardInput file SHA-256 values match the current files and their source-manifest entries. I also checked the source-manifest entries for LivingEntity and Player against their current files. The r2 draft digest was recomputed from its current exact bytes and matches the binding above.

## Independent retrace

Compared paired Mojmap sources:

- `LocalPlayer.aiStep`: 1.21.3 lines 638-718; 1.21.4 lines 643-725.
- `LocalPlayer.shouldStopSprinting`: 1.21.4 lines 816-822.
- `LocalPlayer.canStartSprinting`: 1.21.3 lines 1023-1031; 1.21.4 lines 1053-1062.
- `LocalPlayer.isMovingSlowly`: 1.21.3 lines 597-599; 1.21.4 lines 602-604.
- `KeyboardInput.tick(boolean, float)`: 1.21.3 lines 22-37; `KeyboardInput.tick()`: 1.21.4 lines 22-33.
- Sprint modifier and consumer: `LivingEntity` modifier declaration and `setSprinting` (1.21.3 lines 144-146, 2066-2073; 1.21.4 lines 142-146, 2077-2084); `Player.getSpeed` (1.21.3 lines 1488-1491; 1.21.4 lines 1491-1494); `LivingEntity.getFrictionInfluencedSpeed` and `handleRelativeFrictionAndCalculateMovement` (1.21.3 lines 2351-2358, 2396-2398; 1.21.4 lines 2362-2369, 2407-2409).

In 1.21.3, `LocalPlayer.aiStep` passes `isMovingSlowly()` and `Attributes.SNEAKING_SPEED` to `input.tick` before the tutorial observer and item-use scaling. `KeyboardInput.tick` applies the sneak factor to both impulses. There is no slow-posture term in `canStartSprinting`. For an already-sprinting player, the later stop logic is based on forward impulse/food, collision and water/swimming conditions; with positive forward input and the stated land/no-collision/sufficient-food preconditions, the scaled forward impulse remains positive and does not trigger the impulse-based stop.

In 1.21.4, `KeyboardInput.tick()` first creates unscaled impulses. `LocalPlayer.aiStep` calls the tutorial observer, then `shouldStopSprinting`, then applies item-use scaling when `isUsingItem() && !isPassenger()`, and afterward applies the sneaking-speed factor. `shouldStopSprinting` checks fall-flying, blindness, slow posture, a non-camel passenger state, and the item-use expression `isUsingItem() && !isPassenger() && !isUnderWater()`. The r2 draft states this full item-use qualifier and correctly notes that the item-use term alone is false for passengers or underwater players. It no longer has the overstatement identified in the r1 verdict. The added `canStartSprinting` condition rejects slow movement except underwater; the existing later sprint stop logic remains present.

The example of an already-sprinting crouched/crawling player on land is reachable through LocalPlayer's ordinary `aiStep` path. For vanilla key input, the nonzero forward impulse is `1.0F`; sneak scaling leaves it positive (and above `ClientInput.hasForwardImpulse`'s `1.0E-5F` threshold). In 1.21.4 the earlier slow-posture stop gate clears sprint before scaled input reaches the later stop check, while the added start predicate blocks restarting on land as long as slow posture remains. This establishes the bounded sprint-state change without predicting a complete tick trajectory.

`LivingEntity.setSprinting(false)` removes the sprinting modifier; `true` removes then re-adds the same transient modifier. In both versions it is `0.3F` with `ADD_MULTIPLIED_TOTAL`. `Player.getSpeed()` returns the `MOVEMENT_SPEED` attribute value, and ordinary grounded travel uses `getSpeed()` in `getFrictionInfluencedSpeed()` before `moveRelative`. Thus the state-gate difference reaches the player movement-speed input consumer described in the finding.

The additional blindness, fall-flying and non-camel passenger stop terms are present in 1.21.4's stop predicate and absent from the 1.21.3 existing-sprint stop block; the r2 phrasing restricts its comparison to cases where the older stop conditions would not already clear sprint. The one bounded qualifier still applicable is that these conclusions review the stated vanilla LocalPlayer slice; external/modded gates and resource membership remain outside its closure.

## Scope limit

This accepts only the independently reviewed LocalPlayer sprint-gates difference for the named endpoints. It does not freeze the pair or close the draft's stated open resource/data, external/modded, full tick-graph, or intermediate-boundary questions. No tests, game launch, implementation, or runtime verification were performed.