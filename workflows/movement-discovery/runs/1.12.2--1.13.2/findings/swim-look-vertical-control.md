# 1.13.2 adds look-directed vertical control while swimming

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: 3.2 (player move-relative swim input)
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: player movement while swimming in water present in both versions
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(FFF)V`, lines 1385-1404; SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`. The method applies ordinary flying input when applicable, otherwise delegates directly to `LivingEntity.moveRelative`, then records movement stats. It contains no swimming pitch-to-velocity adjustment.
- B: corresponding member, lines 1441-1468; SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`. Before delegating to the superclass, when `isSwimming() && !isRiding()`, it reads `getLookVector().y`; chooses `0.085` if that value is below `-0.2`, else `0.06`; and, when looking non-upward, jumping, or fluid is present at `(x, y + 1.0 - 0.1, z)`, applies `velocityY += (lookY - velocityY) * factor`.
- Reachability: `LivingEntity.mobTick()` calls virtual `this.moveRelative(sidewaysSpeed, verticalSpeed, forwardSpeed)` after jump processing (A lines 1842-1848; B lines 1920-1926). The local player inherits PlayerEntity’s override. B `PlayerEntity.isSwimming()Z` filters the inherited swimming flag by flying/spectator state (PlayerEntity lines 1819-1821, same B hash above); A has no swimming state path.

## Source-level difference

B changes vertical velocity toward the look vector’s vertical component during swimming, using a pitch-dependent factor and an additional branch gate when looking upward. A has no corresponding player override behavior. The change is independent of the separate `input.sneaking` water impulse in `sneak-water-descent.md` and the water-height jump dispatch in `water-jump-height.md`.

## Reachability and dependencies

This code is reached during the living movement tick after jump handling and before base relative movement. Its state inputs are B’s swimming flag, riding state, look vector, jump state, and fluid occupancy just above the player. Those flags are produced by the water/sprint path; fluid state and collision effects remain in stages 4-5. Player movement attributes affect the delegated horizontal movement but are not consumed by this vertical adjustment itself.

## Consequence and uncertainty

Source proves a conditional vertical-velocity update in B and no corresponding update in A’s method. It predicts pitch-dependent vertical movement while swimming, subject to the branch gate and later travel/collision processing. No trajectory was measured.

## Handoff

Look-directed swimming vertical input. Related to water sprint/swim state, but it has a distinct velocity expression and sits in the PlayerEntity movement delegate.
