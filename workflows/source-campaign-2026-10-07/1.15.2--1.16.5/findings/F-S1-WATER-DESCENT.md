# F-S1-WATER-DESCENT: creative flight suppresses crouch descent in water

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: local-player water input; S1-LOCAL-AISTEP, S1-WATER-DESCENT
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/client/player/LocalPlayer.java`; `LocalPlayer#aiStep()`, lines 722-725, SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`. The gate is `isInWater() && input.shiftKeyDown`, followed by `goDownInWater()`.
- A effect: `net/minecraft/world/entity/LivingEntity.java`; `LivingEntity#goDownInWater()`, lines 1819-1821, SHA-256 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; adds `(0.0, -0.04F, 0.0)` to delta movement.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; `net/minecraft/client/player/LocalPlayer.java`; same method, lines 732-735, SHA-256 `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`; the gate adds `isAffectedByFluids()`.
- B gate dependency: `net/minecraft/world/entity/player/Player.java`; `Player#isAffectedByFluids()`, lines 1005-1007, SHA-256 `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; returns `!abilities.flying`. B `LivingEntity#goDownInWater()`, lines 1898-1900, SHA-256 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`, retains the same impulse addition.

## Source-level difference

For a player in water with shift held, both versions call the same vertical impulse helper when not flying. In A the call has no flight predicate; in B the player override makes the added `isAffectedByFluids()` predicate false while `abilities.flying` is true. Thus A applies the downward `-0.04F` addition to a flying player in water, while B skips it.

## Reachability and dependencies

The client player reaches this branch in `LocalPlayer.aiStep()` after input sampling. `Player.isAffectedByFluids()` directly reads the existing flying ability flag; no producer-system behavior is required for the source-level conclusion. The helper's vector addition is identical on both versions. This finding is limited to the local water descent input and does not claim other fluid forces or net movement.

## Consequence and uncertainty

The source proves the impulse is added in A and gated off in B under the concrete predicate `isInWater && shiftKeyDown && abilities.flying`. Whether another movement input changes the resulting tick displacement is outside this isolated source delta. No runtime observation is claimed.

## Handoff

Independent source delta: B excludes flying players from the crouch-to-descend water impulse. Related finding IDs: none. Boundary within the endpoint interval remains unknown.
