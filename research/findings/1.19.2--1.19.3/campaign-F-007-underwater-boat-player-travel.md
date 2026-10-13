# F-007: Underwater boat contact selects a different player travel branch

- **1.19.2**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 1069-1085.
- **1.19.3**, mojmap: `Entity.java`; lines 1074-1093.

## Difference

While the local player is a Boat passenger, let the Boat be underwater and the player's own bounds intersect water.

With flying and fall-flying disabled and the player unable to stand on that fluid, the water-travel guard is reachable.

In 1.19.2, the boat-passenger special case forces `wasTouchingWater` false.

In 1.19.3, the guard falls through to the normal fluid-contact update and sets the flag from the player's water overlap.

Thus `isInWater()` selects the water `LivingEntity.travel` branch in 1.19.3 and the ordinary land/air branch in 1.19.2 during the mounted local-player tick.

The mounted tick later repositions the player to the Boat, but the different travel result remains in the player's delta-movement field.

On dismount, the paired `stopRiding`/`removeVehicle` methods do not clear that field. 1.19.2 subsequent on-foot player movement tick therefore consumes a delta produced by different travel branches.

This is a direct player-state path; no Boat movement simulation is included.

## Movement meaning

`ClientLevel` passenger tick -> `LocalPlayer.rideTick` -> `Entity.rideTick` -> player `baseTick` water-contact update -> `LivingEntity.aiStep` / `Player.travel` -> `isInWater` branch selection and player delta write -> vehicle reposition -> dismount without delta reset -> later on-foot player travel.

The submerged Boat condition and fluid overlap are external world state.

Server correction timing can alter the local prediction and is not resolved here.

Source-proven: with the listed mounted water-contact preconditions, 1.19.2 and 1.19.3 select different player travel branches and write the corresponding player delta; after dismount that delta is not reset by the paired dismount methods and is available to the next player movement tick.

Predicted: the local player's later movement can diverge.

No actual mounting sequence, server correction outcome, or measured trajectory is claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.2--1.19.3/findings/F-007-underwater-boat-player-travel.md).
