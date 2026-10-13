# F12: 1.15.2 suppresses Elytra start on the same tick as a flight-ability toggle

- **1.14.4**, mojmap: `LocalPlayer.java`; lines 690–705.
- **1.15.2**, mojmap: `LocalPlayer.java`; lines 695–713.

## Difference

When a mayfly player is already falling, has a usable Elytra and turns creative flight off on the second jump press of a double-tap, 1.14.4 first clears `abilities.flying` and then evaluates the Elytra check independently.

If its other gates hold, 1.14.4 sends `START_FALL_FLYING` in that same `aiStep`. 1.15.2 marks the flight-state change with `bl8` and rejects the Elytra path for that tick.

Thus the two versions differ in whether the same input edge can both disable creative flight and start Elytra flight.

## Movement meaning

Jump-key edge -> mayfly double-tap timer -> flight ability toggle -> local Elytra-start gate -> shared fall-flying flag / server command -> LivingEntity fall-flying travel.

Both clients have the same 7-tick double-tap interval; only 1.15.2 carries the toggle result into the subsequent Elytra gate.

The differing gate order and its player movement-state transition follow from paired source.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F12-flight-toggle-suppresses-elytra-start.md).
