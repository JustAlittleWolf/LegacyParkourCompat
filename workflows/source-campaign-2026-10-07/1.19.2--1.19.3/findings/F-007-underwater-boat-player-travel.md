# F-007: Underwater boat contact selects a different player travel branch

- Older version A: 1.19.2
- Newer version B: 1.19.3
- Mechanic / coverage slice IDs: `S7-boat-passenger-water-state`, `S3-water-travel-branch`, `S3-ground-travel-branch`, `S7-mount-transition`
- Classification: changed behavior
- Confidence: source-confirmed local-player water-state and retained player-velocity path; exact fluid overlap and dismount/correction timing are external conditions
- Applicability: a local player riding a submerged Boat while the player's own bounding box contacts water, the player is not flying or fall-flying, the water-travel `isAffectedByFluids && !canStandOnFluid` guard passes, and the player dismounts before a later on-foot movement tick
- First changed release: unknown within (1.19.2, 1.19.3]
- Runtime validation: not performed
- Manifest artifact reference: `../run.md` Artifact manifest A/B sections; exact Mojmap source trees and official original client jars

## Paired evidence

- A `net/minecraft/world/entity/Entity.java`, full SHA-256 `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`: `updateInWaterStateAndDoWaterCurrentPushing()` lines 1069-1085 clears `wasTouchingWater` whenever `getVehicle() instanceof Boat`; `isInWater()` lines 1018-1020 returns that field. `rideTick()` lines 1699-1705 zeros delta movement, calls `tick()`, then positions a passenger that is still mounted.
- B `Entity.java`, full SHA-256 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6548983667adc32`: the same method at lines 1074-1093 clears `wasTouchingWater` only when the passenger's Boat is not underwater. For an underwater Boat it continues through the water-fluid contact update; `isInWater()` lines 1027-1029 reads the resulting flag. Generic `rideTick()` has the same zero/tick/reposition order.
- A/B `net/minecraft/client/multiplayer/ClientLevel.java` call `rideTick()` for a passenger (A line 274, B line 276), full-file SHA-256 `7c16e6aced9e1faad377be20f3aa15ce68c213ebfae5f5011af2287f814676ab` / `8c8a6134a4409c899c098012e1a11e7eee736edac3403b89211f8ae8fa06df85`. The paired `LocalPlayer.rideTick()` overrides call `super.rideTick()` before forwarding the input buttons to Boat; full `LocalPlayer.java` hashes are `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` / `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`.
- A/B `net/minecraft/world/entity/LivingEntity.java`, full SHA-256 `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` / `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`: the local player enters the same `aiStep()`/`Player.travel()` path; `LocalPlayer.isEffectiveAi()` returns true and `Player.travel()` calls `super.travel()` when the player is a passenger and is not in the separately guarded swimming/flying shortcuts. `LivingEntity.travel()` selects its water branch from `isInWater()` and writes the resulting player delta movement; the paired water and ordinary land/air formulas are recorded in `S3-water-travel-branch` and `S3-ground-travel-branch`.
- A/B `net/minecraft/world/entity/Entity.java` and `net/minecraft/world/entity/player/Player.java`: `stopRiding()` only delegates to `removeVehicle()`, while `Player.removeVehicle()` delegates and clears `boardingCooldown`; neither resets player delta movement. Full source hashes are the Entity hashes above and Player hashes `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` / `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`.

## Source-level difference

While the local player is a Boat passenger, let the Boat be underwater and the player's own bounds intersect water. With flying and fall-flying disabled and the player unable to stand on that fluid, the water-travel guard is reachable. In A, the boat-passenger special case forces `wasTouchingWater` false. In B, the guard falls through to the normal fluid-contact update and sets the flag from the player's water overlap. Thus `isInWater()` selects the water `LivingEntity.travel` branch in B and the ordinary land/air branch in A during the mounted local-player tick.

The mounted tick later repositions the player to the Boat, but the different travel result remains in the player's delta-movement field. On dismount, the paired `stopRiding`/`removeVehicle` methods do not clear that field. A subsequent on-foot player movement tick therefore consumes a delta produced by different travel branches. This is a direct player-state path; no Boat movement simulation is included.

## Reachability and dependencies

`ClientLevel` passenger tick -> `LocalPlayer.rideTick` -> `Entity.rideTick` -> player `baseTick` water-contact update -> `LivingEntity.aiStep` / `Player.travel` -> `isInWater` branch selection and player delta write -> vehicle reposition -> dismount without delta reset -> later on-foot player travel. The submerged Boat condition and fluid overlap are external world state. Server correction timing can alter the local prediction and is not resolved here.

## Consequence and uncertainty

Source-proven: with the listed mounted water-contact preconditions, A and B select different player travel branches and write the corresponding player delta; after dismount that delta is not reset by the paired dismount methods and is available to the next player movement tick. Predicted: the local player's later movement can diverge. No actual mounting sequence, server correction outcome, or measured trajectory is claimed. The first changed release remains unknown within the exact pair.

## Handoff

Independent client player-motion delta from underwater-Boat water-contact state. It remains distinct from the vehicle-only exclusion E-001; player position during the mounted tick is still supplied by the Boat.
