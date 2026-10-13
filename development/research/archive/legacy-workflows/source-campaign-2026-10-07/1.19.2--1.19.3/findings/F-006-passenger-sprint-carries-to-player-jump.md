# F-006: Passenger sprint state can change a later on-foot jump impulse

- Older version A: 1.19.2
- Newer version B: 1.19.3
- Mechanic / coverage slice IDs: `S3-mounted-sprint`, `S3-dismounted-sprint-jump`, `S1-input-sample`, `S7-mount-transition`
- Classification: changed behavior
- Confidence: source-confirmed player sprint-state and conditional player-velocity path; exact state combination is an external input
- Applicability: player movement after dismount, when the B-only passenger sprint gate was taken and the sprint flag survives to a grounded jump
- First changed release: unknown within (1.19.2, 1.19.3]
- Runtime validation: not performed
- Manifest artifact reference: `../run.md` Artifact manifest A/B sections; exact Mojmap source trees and official original client jars

## Paired evidence

- A `net/minecraft/client/player/LocalPlayer.java`, full SHA-256 `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef`: `aiStep()` lines 772-785 starts sprint only when `onGround || isUnderWater()`, then applies the shared impulse, food/ability, sprint-state, item-use and blindness guards. The food predicate is `foodLevel > 6.0F || mayfly`.
- B same class, full SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`: `aiStep()` lines 693-705 adds `isPassenger() && getVehicle().isOnGround()` to the start predicate and uses `hasEnoughFoodToStartSprinting()` at lines 1047-1049. With food above 6, the existing food guard is met on both sides, isolating the passenger/vehicle-ground addition. Both sides' `LocalPlayer.setSprinting(boolean)` delegate to the same shared flag writer (A lines 537-540; B lines 449-452).
- A/B `net/minecraft/world/entity/Entity.java`, full SHA-256 `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` / `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6548983667adc32`: `setSprinting(boolean)` sets shared flag 3 (A lines 1984-1990; B lines 1993-1999). `stopRiding()`/`removeVehicle()` do not clear it (A lines 1779-1789; B lines 1784-1798). `Player.removeVehicle()` only delegates and resets `boardingCooldown` (A lines 1045-1048; B lines 1034-1037); `LivingEntity.stopRiding()` only delegates and performs the server dismount callback (A lines 2715-2720; B lines 2730-2735).
- B's post-dismount continuation is in `LocalPlayer.aiStep()` lines 719-729: after dismount, with forward impulse, food above 6, no horizontal collision and dry non-swimming movement, the stop predicate is false and releasing the sprint key does not clear an already-set sprint flag. With that same on-foot state, A's `aiStep()` lines 772-785 does not start sprint immediately when the key is released and its sprint trigger timer is zero; it sets the 7-tick trigger instead.
- A/B `net/minecraft/world/entity/LivingEntity.java`, full SHA-256 `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` / `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`: `aiStep()` invokes `jumpFromGround()` on a grounded jump when the jump delay is zero (A lines 2566-2571; B lines 2575-2582). The paired `jumpFromGround()` methods (A lines 2014-2023; B lines 2024-2034) add `(-sin(yaw) * 0.2F, 0, cos(yaw) * 0.2F)` to player velocity when `isSprinting()` is true.
- The sprint command is a synchronization boundary: B's local controlled-passenger tick calls `sendIsSprintingIfNeeded()`; both paired `ServerGamePacketListenerImpl.handlePlayerCommand` methods accept `START_SPRINTING` by setting the player sprint flag (A lines 1483-1498, full file SHA-256 `976992cd0cf4ce387e12543861aab66cc7cf8a58f078f00e0fe8f91df47b4cd2`; B lines 1379-1393, full file SHA-256 `81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b`). No riding guard rejects that command in this handler.

## Source-level difference

Choose the same input guards on both sides: the player is a passenger of a grounded vehicle, the player itself is neither on ground nor underwater, forward impulse is at least `0.8`, food is above 6, sprint is pressed, and the player is not already sprinting, shifting, using an item or blinded. B's added passenger/vehicle-ground clause starts the player sprint flag; A's `onGround || isUnderWater()` predicate does not.

After dismount, keep sufficient forward input and food, release sprint, and avoid collision/water stop conditions. B retains the previously set sprint flag through `stopRiding`; A has not set it and only arms its sprint trigger. On a subsequent grounded jump, the shared player jump code therefore adds the sprint-only horizontal `0.2F` velocity vector in B and not A. This finding uses food above the threshold on both sides; the separate low-food passenger shortcut is not claimed as an in-scope movement effect.

## Reachability and dependencies

`LocalPlayer.aiStep` -> passenger/vehicle-ground sprint guard -> `LocalPlayer.setSprinting` -> `Entity` shared sprint flag -> dismount without a sprint reset -> on-foot sprint-retention guard -> `LivingEntity.aiStep` grounded jump -> `LivingEntity.jumpFromGround` player velocity write. B also sends the changed state through the existing sprint command path, and the paired vanilla server command handler accepts it without a passenger restriction. Vehicle position/velocity simulation is not part of this finding.

## Consequence and uncertainty

Source-proven: under the listed state and input conditions, B can carry a sprint flag from a grounded passenger state into a later on-foot jump while A remains non-sprinting; the shared jump method then takes its sprint branch only in B. Predicted: the resulting player velocity and later movement can diverge. No actual mounting sequence, server replication timing or trajectory is claimed. The first changed release remains unknown within the exact pair.

## Handoff

Independent player-motion delta from passenger sprint-state retention; related to checked mounted-trajectory exclusion E-001, whose earlier broad scope disposition is partitioned by this direct post-dismount player consumer. The E-001 exclusion remains only for movement while the player is mounted and vehicle physics. Snapshot review and implementation/testing decisions are deferred.
