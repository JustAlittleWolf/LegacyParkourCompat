# Mounted sprint gate: dismount applicability addendum

This addendum resolves the ordinary-player applicability question recorded in `inventory.md` for 1.19.2→1.19.3. Exact sources are the supplied Mojmap trees; source-manifest SHA-256 values are 1.19.2 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` and 1.19.3 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`.

## Client-side persistence and consequence

In 1.19.2 `LocalPlayer.aiStep` only treats food above 6 or `mayfly` as enough for sprint (`:771–795`); in 1.19.3 `hasEnoughFoodToStartSprinting()` additionally returns true while `isPassenger()` (`LocalPlayer.java:1047–1049`). With food at or below 6, no flight ability, forward impulse, sprint key held, no item use/blindness, and an underwater passenger, the 1.19.3 held-key branch sets sprint at `LocalPlayer.java:709–716`; 1.19.2 does not (`:788–795`). This gate does not require a grounded vehicle for the held-key branch. The new state is local: `setSprinting` writes the shared sprint flag (`Entity.java:1994–1999`) and the movement-speed modifier (`LivingEntity.java:1964–1973`).

A normal dismount arrives through `ClientPacketListener.handleSetEntityPassengersPacket` (1.19.2 `:869–892`, 1.19.3 `:922–945`): the handler ejects existing passengers, then re-adds only IDs in the new list. When the local player is omitted, `Entity.ejectPassengers` calls `stopRiding`; `Entity.stopRiding`/`removeVehicle` clear the vehicle relationship and do not change sprint state (`1.19.3 Entity.java:1780–1800`). The same handler behavior exists in both releases.

On the next ordinary client tick, `Entity.baseTick` updates fluid/eye state and calls virtual `updateSwimming` before `LivingEntity.tick` reaches `LocalPlayer.aiStep` (`Entity.java:403–430`). With the player now unmounted, underwater, sprinting, and standing in water, `Entity.updateSwimming` sets swimming true (`1.19.3 Entity.java:1056–1064`; `Player.updateSwimming` delegates to it unless flying, `Player.java:1481–1487`). The 1.19.3 local sprint-maintenance branch then sees insufficient food but does not clear sprint for a swimming player while shift remains down and the player remains in water (`LocalPlayer.java:719–727`). Shift-down is the normal dismount input and is sent in the mounted input packet (`LocalPlayer.java:188–198`). In 1.19.2, sprint was never set under the same low-food precondition; `updateSwimming` therefore leaves swimming false and the on-foot maintenance branch cannot start sprint.

The difference reaches ordinary player movement. 1.19.3 `Player.travel` applies the swim pitch adjustment when swimming and not a passenger (`Player.java:1448–1461`) and then invokes inherited water travel; `LivingEntity.travel` selects sprint damping `0.9F` instead of `getWaterSlowDown()` when sprinting (`:2064–2090`). The old player takes the non-sprint water path. This is a source-closed local movement consequence for the stated underwater dismount/input precondition; vehicle physics are not part of the claim.

## Packet boundary and limits

The client also sends the sprint transition while mounted in 1.19.3 (`LocalPlayer.tick():194–198`, `sendIsSprintingIfNeeded():271–279`); the 1.19.2 mounted branch does not call that helper. No server processing or reply behavior is inferred. `ClientPacketListener.handleSetEntityData` can assign an incoming shared-flags value to the player (1.19.2 `:466–471`, 1.19.3 `:495–500`); therefore the stated divergence is conditional on an equal incoming packet trace with no intervening packet that overwrites the sprint/swimming bits before the on-foot tick. Exact server packet production and whether a live server sends such an update after the changed outbound command remain unexamined. No trajectory/runtime test was performed.

SHA-256 for cited source files:

- 1.19.2 `LocalPlayer.java`: `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef`
- 1.19.2 `Entity.java`: `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`
- 1.19.2 `LivingEntity.java`: `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`
- 1.19.2 `Player.java`: `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1`
- 1.19.2 `ClientPacketListener.java`: `98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3`
- 1.19.3 `LocalPlayer.java`: `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`
- 1.19.3 `Entity.java`: `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`
- 1.19.3 `LivingEntity.java`: `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`
- 1.19.3 `Player.java`: `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`
- 1.19.3 `ClientPacketListener.java`: `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`
