# S1-03: Gate water descent input on fluid-affectability

- Older version A: 1.15.2
- Newer version B: 1.16.5
- Mechanic / coverage slice IDs: Stage 1, local input and tick ordering; Stage 2, player-specific movement gates; `S1-WATER-DESCENT`.
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A: artifact manifest A; `../../../../decompiled_minecraft/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; `LocalPlayer.aiStep()V` lines 722–725 calls `goDownInWater()` whenever the player is in water and shift is held. `LivingEntity.goDownInWater()V` line 1819 adds `(0.0, -0.04F, 0.0)` to delta movement. SHA-256s: LocalPlayer `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD`; LivingEntity `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`.
- B: artifact manifest B; `../../../../decompiled_minecraft/1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`; `LocalPlayer.aiStep()V` lines 732–735 adds `isAffectedByFluids()` to that guard. `Player.isAffectedByFluids()Z` lines 1004–1007 returns `!abilities.flying`; `LivingEntity.goDownInWater()V` line 1898 retains the same `-0.04F` addition. SHA-256s: LocalPlayer `6011569E766BB1568609147BE9AA14E9C08C51948E3D3A60FD066E848F6A8C2B`; Player `D2E26589BDB6A20DC914266DB06AA48F50811EFC792D6E63B3008C9199914960`; LivingEntity `B5D8A1A3C80F85D5D545B5A777E9E2A915DC002A7E31B5F5AD12BF7E285D7A88`.

## Source-level difference

The water-shift path still subtracts `0.04F` from vertical velocity when it runs, but B suppresses the call when the player's abilities are flying. A has no fluid-affectability guard at this call site. In B, the predicate is exactly false while `abilities.flying` is true.

## Reachability and dependencies

`LocalPlayer.aiStep()` consumes shift state and updates vertical velocity before the parent living movement step calls `travel`. For a player in water with flight enabled and shift held, A applies the downward velocity increment; B skips it. B's player-specific gate is in `Player.isAffectedByFluids`; this does not establish the separate server's fluid rules.

## Consequence and uncertainty

The source proves a `-0.04F` vertical-velocity addition in A and no such addition from this path in B for a flying player. It predicts a different subsequent vertical movement input in the same tick; actual velocity after later travel/collision depends on other gates. No trajectory was run.

## Handoff

Independent delta: shift-to-descend input no longer changes local player velocity while flight makes the player unaffected by fluids. Related coverage: Stage 3 water travel and Stage 7 client/external state boundaries. First changed release is unknown.
