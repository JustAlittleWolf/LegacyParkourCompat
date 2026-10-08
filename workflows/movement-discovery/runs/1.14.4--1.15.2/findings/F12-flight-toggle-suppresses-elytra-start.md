# F12: 1.15.2 suppresses Elytra start on the same tick as a flight-ability toggle

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stages 1–3; local flight-ability and Elytra input ordering
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: a mayfly player double-tapping jump to turn creative flight off while falling and wearing a usable Elytra
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `LocalPlayer.aiStep` processes the mayfly flight toggle at lines 690–705, then independently checks the jump edge and `getDeltaMovement().y < 0.0` before sending `START_FALL_FLYING` at lines 707–711. The flight toggle does not set a guard consumed by this Elytra check. A `LocalPlayer.java` SHA-256: `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F`.
- B `LocalPlayer.aiStep` initializes `bl8 = false`; setting or toggling `abilities.flying` also sets `bl8 = true` at lines 695–713. The following Elytra path requires `!bl8` at line 715 before checking the chest item and calling `tryToStartFallFlying` (lines 715–719). B `LocalPlayer.java` SHA-256: `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD`.
- The shared double-tap timer remains 7 ticks in both local input paths; A sets it on the first press and toggles `abilities.flying` on a later jump edge when not swimming. B retains that toggle and adds `bl8` solely to suppress same-tick follow-on processing.
- A's negative-Y Elytra gate and B's `tryToStartFallFlying` helper differences are detailed in F10. This finding holds the player in the falling cohort so it isolates the additional B same-tick ability-toggle guard.

## Source-level difference

When a mayfly player is already falling, has a usable Elytra and turns creative flight off on the second jump press of a double-tap, A first clears `abilities.flying` and then evaluates the Elytra check independently. If its other gates hold, A sends `START_FALL_FLYING` in that same `aiStep`. B marks the flight-state change with `bl8` and rejects the Elytra path for that tick. Thus the two versions differ in whether the same input edge can both disable creative flight and start Elytra flight.

## Reachability and dependencies

Jump-key edge -> mayfly double-tap timer -> flight ability toggle -> local Elytra-start gate -> shared fall-flying flag / server command -> LivingEntity fall-flying travel. Both clients have the same 7-tick double-tap interval; only B carries the toggle result into the subsequent Elytra gate.

## Consequence and uncertainty

The differing gate order and its player movement-state transition follow from paired source. No gameplay result was observed, and the exact first release is unknown within the endpoint interval.

## Handoff

This same-tick sequencing difference is independent of F10's removal of the negative-Y condition and does not close the remaining ability-transition inventory.
