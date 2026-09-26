# F001: Local player automatic obstacle jumping added

- Older version A: 1.9.4
- Newer version B: 1.10.2
- Mechanic / coverage slice IDs: S1b, S2, S4 (local input/tick; player gates; move dispatch)
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: historical player behavior (added on newer endpoint; controlled by the client option)
- First changed release: unknown within (1.9.4, 1.10.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `LocalClientPlayerEntity.mobTick()` lines 604–761; SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`. `Input.java`, full class; SHA-256 `206dea2ff5977599596c907c54f36300c4e72d07d6778d82e8850f57bb0fb009`. Full class search/diff shows no local `move` override, auto-jump method/state/timer, auto-jump option, or input-vector accessor. A `LivingEntity.tick()` invokes `mobTick()` at line 1551; movement paths dispatch virtual `move` at lines 1341, 1391, 1420 and 1448.
- B: `decompiled_minecraft/1.10.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `move(double,double,double)` lines 812–817, `isAutoJumpEnabled()` lines 819–821 and `autoJump(float,float)` lines 823–923; SHA-256 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`. `Input.getMovement()` lines 17–20; SHA-256 `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e`. `GameOptions.autoJump` defaults true at line 108; option persistence/controls appear at lines 398, 831–832, 949 and 1066; SHA-256 `767ed017470a0909148017461668fa95a988f2f15582b4e2c6636b03502b4d43`.
- B copies the option into local `autoJumpEnabled` at line 228. `mobTick()` counts down the one-tick delay and sets `input.jumping = true` at lines 673–676. The scan schedules at line 914. B `LivingEntity.tick()` invokes `mobTick()` at line 1587; movement dispatch to the local override is virtual.

## Source-level difference

B adds a local-player hook after collision resolution: it measures actual X/Z displacement around `super.move`, then considers an automatic jump if the option is enabled, the player is grounded after moving, the timer is clear, the player is neither sneaking nor riding, and the input vector is nonzero. It checks forward-facing direction, overhead clearance and collision boxes along the prospective route. On a qualifying obstacle it schedules a jump when the height is greater than `0.5F` and at most `1.2F` plus the Jump Boost allowance. On the next local `mobTick`, the delay sets the input jump flag. A has no corresponding local hook/state/accessor in the inspected classes.

## Reachability and dependencies

`LivingEntity.tick()` -> virtual `mobTick()` -> local player input processing; movement integration invokes virtual `this.move(velocityX, velocityY, velocityZ)` -> B `LocalClientPlayerEntity.move` -> `autoJump` scan -> next `mobTick` supplies the jump input to the existing jump path. The scan reads collision shapes, player dimensions/yaw/speed and Jump Boost. Its allowance reads the active Jump Boost amplifier (`(amplifier + 1) * 0.75F`); S6 must verify that dependency on both sides. Collision queries use client world block states. No server-supplied collision-data dependency is identified in this source path.

## Consequence and uncertainty

Source proves that B can synthesize a jump input after qualifying movement toward a low obstacle while the default-on option is enabled; A has no corresponding client implementation. It predicts that a subsequent grounded jump can clear some low obstacles without a physical jump-key press. This is a behavior added by the newer endpoint, not 1.9.4 behavior; the exact first changed release is unknown from the endpoints. No trajectory or runtime reproduction is claimed. Jump Boost, collision-call ordering and remapper-warning overlap remain under review.

## Handoff

For 1.9.4 emulation, this newer-only local feature is relevant only when B's option is active. Implementation and default-policy decisions belong to later workflows. Related dependency: S6 Jump Boost. No implementation or runtime validation was performed.

