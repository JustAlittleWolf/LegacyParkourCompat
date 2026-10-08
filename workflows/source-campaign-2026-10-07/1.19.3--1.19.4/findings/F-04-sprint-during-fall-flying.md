# F-04: sprint initiation is blocked while fall-flying

- Older version A: 1.19.3
- Newer version B: 1.19.4
- Mechanic / coverage slice IDs: INV-TICK / I-TICK-SPRINT-ELIGIBILITY; INV-STATE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.19.3, 1.19.4]
- Runtime validation: not performed

## Paired evidence

All paths are repository-relative; hashes match the verified per-version source manifests.

- A `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep()V`, lines 709–717, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`: the key-sprint branch requires the player not already sprinting, suitable water state, enough forward impulse, food/flight ability, no item use and no blindness, then calls `setSprinting(true)`. It does not test fall-flying state.
- B `build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep()V`, lines 696–698 and `#canStartSprinting()Z`, lines 1022–1030, SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`: the same direct key route delegates to `canStartSprinting()`, which additionally requires `!isFallFlying()`.

## Source-level difference

Under the stated non-passenger key-sprint preconditions, A can write the sprint state during fall flight and B rejects initiation through the added fall-flying guard. Passenger eligibility is a separate condition and finding in F-03.

## Reachability and dependencies

The player can reach `LocalPlayer#aiStep` while fall-flying. The direct key branch does not require being grounded; A's listed state predicates can permit its sprint write during flight. B's helper includes `!isFallFlying()`. Sprint state is a direct input to later ordinary player movement-speed selection; F-01 separately covers the airborne sprint-speed consumer. The paired fall-flying travel equations themselves do not consume sprint state, so this finding does not assert an immediate change to gliding speed or trajectory.

## Consequence and uncertainty

The source pair proves a reachable difference in sprint-state eligibility. A later non-fall-flying movement tick can observe different sprint state if it has not been cleared by then. No specific subsequent trajectory is claimed.

## Handoff

Separate from the passenger/vehicle eligibility and carry-over path in F-03. Release introduction is unknown within the compared interval. No runtime validation was performed.