# F-03: mounted sprint state can carry into player movement after dismount

- Older version A: 1.19.3
- Newer version B: 1.19.4
- Mechanic / coverage slice IDs: INV-TICK / I-TICK-MOUNT-START; INV-STATE / I-TICK-MOUNT-START; INV-EXTERNAL / I-TICK-MOUNT-START
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player interaction with another entity; historical player behavior after dismount
- First changed release: unknown within (1.19.3, 1.19.4]
- Runtime validation: not performed

## Paired evidence

All paths are repository-relative; hashes match the verified per-version source manifests.

- A `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep()V`, lines 693–719, and `hasEnoughFoodToStartSprinting()Z`, lines 1047–1050, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`: the sprint start ground gate permits `isPassenger() && vehicle.isOnGround()`, and the helper treats passengers as having enough food to start sprinting. The key sprint branch also calls `setSprinting(true)` while passenger when its other conditions pass.
- B `.../1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep()V`, lines 685–701, `canStartSprinting()Z` / `vehicleCanSprint(Entity)Z`, lines 1022–1034, and `hasEnoughFoodToStartSprinting()Z`, lines 1041–1044, same `LocalPlayer.java` hash `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`: sprint start requires `!isPassenger() || vehicleCanSprint(vehicle)`; `vehicleCanSprint` requires both `vehicle.canSprint()` and local control.
- B `.../1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, `Entity#canSprint()Z`, lines 3163–3165, SHA-256 `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`: default returns false. Full-tree override search found only `Player` and the newly added `Camel` as other overrides; the camel is absent in A and does not establish historical vehicle behavior. Existing A-era vanilla rideables inheriting the default therefore fail B's gate.
- A/B `Entity#removeVehicle()V`, respectively `.../1.19.3/mojmap/net/minecraft/world/entity/Entity.java` lines 1784–1790 and `.../1.19.4/mojmap/net/minecraft/world/entity/Entity.java` lines 1798–1804; SHA-256 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` and `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`. The method clears the vehicle link and removes the passenger; it does not call `setSprinting(false)`. A/B `LocalPlayer#removeVehicle()V`, lines 172–176 in each version, only clears `handsBusy` after the superclass method.
- A/B `LivingEntity#setSprinting(Z)V`, respectively A lines 1964–1974 and B lines 1895–1905, hashes `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` and `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`: sprint state installs the movement-speed attribute modifier. The grounded movement-speed consumer is `LivingEntity#getFrictionInfluencedSpeed()F`; its A/B branches read `getSpeed()` when on ground (same respective LivingEntity files cited in F-01).

## Source-level difference

A permits a local player to enter sprinting while a passenger, subject to its input, vehicle-ground, item-use, blindness and other existing guards; its food helper explicitly allows passengers. B routes both sprint-start paths through `canStartSprinting`, which denies passengers when the vehicle does not report `canSprint()` and local control. Existing vanilla rideable entities inherit the B `Entity` default of false. The added Camel override is a new-version vehicle and is outside this historical comparison.

Both versions retain sprint state through `Entity#removeVehicle` and the LocalPlayer override. If A entered sprint while riding an existing vehicle and a dismount occurs before the next local tick, the sprint flag and speed modifier remain. With food above the normal sprint threshold, continued forward input, and a grounded post-dismount player tick whose other stop conditions are false, A's grounded movement consumes the sprint-modified movement speed. B cannot enter that mounted sprint state under the same vehicle precondition, so that carry-over path is absent.

## Reachability and dependencies

LocalPlayer passenger/input gate -> `setSprinting(true)` -> `LivingEntity#setSprinting` sprint flag and movement-speed modifier -> entity/player dismount clears vehicle link without clearing sprint -> next LocalPlayer tick retains sprint while forward input and food satisfy the existing stop check -> grounded `LivingEntity` movement-speed consumer. Vehicle physics are excluded; only the player's sprint state and post-dismount movement are in scope.

## Consequence and uncertainty

Source proves the eligibility and persistence differences and a reachable post-dismount grounded movement path under the stated conditions. The report does not claim a specific position delta, vehicle trajectory, or server-side dismount schedule. The direct food value is a movement predicate input; food-system production remains excluded.

## Handoff

Separate from F-01's airborne speed calculation. Applies to vehicles that inherit B's default `Entity#canSprint() == false` and a post-dismount grounded movement tick. Release introduction is unknown within the compared interval.
