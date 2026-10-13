# F-009: Historical mounts stop allowing the passenger to start sprinting

- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 687–716.
- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 685–708.

## Difference

For a local player riding a shared historical mount, holding sprint with sufficient forward input and food (or mayfly ability), while not using an item or blinded and outside water, 1.18.2 can call `setSprinting(true)`. 1.19.4's `canStartSprinting()` reaches `vehicleCanSprint()` because the player is a passenger, then rejects the start because that historical vehicle inherits `Entity.canSprint() == false`. 1.18.2's manual sprint-key branch has no mount eligibility check.

This finding is limited to this passenger sprint-start path; 1.19.4's modern Camel-specific opt-in is outside the historical map feature set.

## Movement meaning

Local-player tick/input -> `LocalPlayer.aiStep()` sprint-key branch -> `setSprinting(true)` in 1.18.2, or 1.19.4 `canStartSprinting()` -> vehicle capability/control checks -> sprint flag. 1.18.2's food condition is `foodLevel > 6 || mayfly`; 1.19.4's helper additionally treats a passenger as having enough food, but the common historical vehicle fails the earlier `canSprint()` check.

The setter updates the player's sprint state and the shared living-entity sprint movement-speed modifier path documented in slice 3.11.

Source proves that under the stated conditions the passenger sprint flag can become true in 1.18.2 and is refused in 1.19.4 for the shared historical mount set.

The flag is a player movement input and the setter applies the sprint movement-speed modifier; this comparison does not establish a changed mounted vehicle trajectory or measure movement after dismount.

No position/velocity trace was run.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.18.2--1.19.4/findings/F-009-mounted-sprint-eligibility.md).
