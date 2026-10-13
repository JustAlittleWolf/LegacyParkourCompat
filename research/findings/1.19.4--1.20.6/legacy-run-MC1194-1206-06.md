# MC1194-1206-06: Passenger crouch no longer scales local movement input

- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 654-660.
- **1.20.6**, mojmap: `1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 659-668.

## Difference

If the local player is a passenger, presses shift, is not flying or swimming, can fit in the crouching pose, is not visually crawling, and has nonzero movement input, 1.19.4 reports slow movement to `Input.tick`. 1.20.6 forces the crouching flag false and reports no slow movement.

With a sneak-speed factor below `1.0`, 1.19.4 scales horizontal input impulses and 1.20.6 leaves them unscaled.

Both then copy those impulses into the local player movement vector and run the ordinary player travel path.

## Movement meaning

The local call chain is `LocalPlayer.aiStep()` -> `Input.tick()` -> `LocalPlayer.serverAiStep()` -> `LivingEntity.aiStep()` -> `LivingEntity.travel()` -> `moveRelative()`/`Entity.move()`.

The slow-movement factor includes the sneaking-speed enchantment bonus; that effect is a Stage 6 dependency.

This finding concerns the player's local movement path while mounted.

Any additional effect on the vehicle's movement input is outside the finding and the separate non-player movement scope.

Source-proven: 1.19.4 scales the local player's horizontal movement input under the stated passenger/shift conditions, while 1.20.6 does not.

This changes the movement vector passed to player travel when the factor is below one.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.19.4--1.20.6/findings/MC1194-1206-06.md).
