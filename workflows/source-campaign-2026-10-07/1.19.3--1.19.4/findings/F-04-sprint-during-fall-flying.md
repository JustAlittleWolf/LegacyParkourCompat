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

- A `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep()V`, lines 709–717, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`: the key-sprint branch requires the player not already sprinting, suitable water state, enough forward impulse, food/flight ability, no item use and no blindness, then calls `setSprinting(true)`. It does not test fall-flying state. A `LivingEntity#setSprinting(boolean)V`, lines 1964–1973, SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`, writes the sprint flag and adds/removes the sprint movement-speed modifier.
- B `build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep()V`, lines 696–698 and `#canStartSprinting()Z`, lines 1022–1030, SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`: the same direct key route delegates to `canStartSprinting()`, which additionally requires `!isFallFlying()`. B `LivingEntity#setSprinting(boolean)V`, lines 1895–1904, SHA-256 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`, has identical method-body SHA-256 `cc439e4e4045ee4ff5e73c212d93867ce2af2798297b91f811a6e893cbe76b87`; when called, it applies the same sprint movement-speed modifier as A.

## Source-level difference

Under the stated non-passenger key-sprint preconditions, A can write the sprint state during fall flight and B rejects initiation through the added fall-flying guard. Passenger eligibility is a separate condition and finding in F-03.

## Reachability and dependencies

The player can reach `LocalPlayer#aiStep` while fall-flying. The direct key branch does not require being grounded; A's listed state predicates can permit its sprint write during flight. B's helper includes `!isFallFlying()`. `setSprinting(true)` installs the movement-speed modifier in both versions; the state can feed later non-fall-flying player movement. F-01 separately covers the airborne sprint-speed consumer. The paired fall-flying travel equations themselves do not consume sprint state, so this finding does not assert an immediate change to gliding speed or trajectory.

## Consequence and uncertainty

The source pair proves a reachable difference in sprint-state eligibility and ties its state writer to the same movement-speed modifier in both versions. A later non-fall-flying movement tick can observe different sprint state if it has not been cleared by then. No specific subsequent trajectory is claimed.

## Handoff

Separate from the passenger/vehicle eligibility and carry-over path in F-03. Release introduction is unknown within the compared interval. No runtime validation was performed.