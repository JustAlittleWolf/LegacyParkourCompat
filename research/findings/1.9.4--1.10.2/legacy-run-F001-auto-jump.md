# F001: Local player automatic obstacle jumping added

- **1.9.4**, ornithe-feather: `1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 604–761.
- **1.10.2**, ornithe-feather: `1.10.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 812–817.

## Difference

1.10.2 adds a local-player hook after collision resolution: it measures actual X/Z displacement around `super.move`, then considers an automatic jump if the option is enabled, the player is grounded after moving, the timer is clear, the player is neither sneaking nor riding, and the input vector is nonzero.

It checks forward-facing direction, overhead clearance and collision boxes along the prospective route.

On a qualifying obstacle it schedules a jump when the height is greater than `0.5F` and at most `1.2F` plus the Jump Boost allowance.

On the next local `mobTick`, the delay sets the input jump flag. 1.9.4 has no corresponding local hook/state/accessor in the inspected classes.

## Movement meaning

`LivingEntity.tick()` -> virtual `mobTick()` -> local player input processing; movement integration invokes virtual `this.move(velocityX, velocityY, velocityZ)` -> 1.10.2 `LocalClientPlayerEntity.move` -> `autoJump` scan -> next `mobTick` supplies the jump input to the existing jump path.

The scan reads collision shapes, player dimensions/yaw/speed and Jump Boost.

Its allowance reads the active Jump Boost amplifier (`(amplifier + 1) * 0.75F`); S6 must verify that dependency on both sides.

Collision queries use client world block states.

No server-supplied collision-data dependency is identified in this source path.

Source proves that 1.10.2 can synthesize a jump input after qualifying movement toward a low obstacle while the default-on option is enabled; 1.9.4 has no corresponding client implementation.

It predicts that a subsequent grounded jump can clear some low obstacles without a physical jump-key press.

Jump Boost, collision-call ordering and remapper-warning overlap remain under review.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.9.4--1.10.2/findings/F001-auto-jump.md).
