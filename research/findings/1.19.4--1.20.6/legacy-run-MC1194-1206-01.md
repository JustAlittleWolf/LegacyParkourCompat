# MC1194-1206-01: Flight activation adds a ground-jump impulse

- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 720-728.
- **1.20.6**, mojmap: `1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 728-739.

## Difference

Under the shared preconditions `mayfly == true`, `isAlwaysFlying() == false`, the jump key rising edge entering the second-press branch (`jumpTriggerTime != 0`), and `isSwimming() == false`, both versions toggle flight on.

If the player is grounded, only 1.20.6 additionally invokes the player jump routine.

When jump power exceeds its helper threshold, that routine writes positive vertical velocity and sets the impulse flag.

The 1.20.6 `Player` override also awards the normal jump statistic after the superclass call.

## Movement meaning

Local input is sampled by `KeyboardInput.tick`; `LocalPlayer.aiStep` reads `input.jumping`, manages the creative-flight double-tap timer and calls the virtual player jump method only in 1.20.6. `LocalPlayer` inherits `Player.jumpFromGround`, which delegates to `LivingEntity.jumpFromGround`.

No movement-relevant resource dependency was found in this call chain.

The source proves the added call and velocity write under the helper threshold; it does not establish an observed trajectory.

Source-proven: on the specified grounded flight-activation path, 1.20.6 runs the normal jump impulse path, while 1.19.4 does not.

The resulting vertical velocity depends on jump power and surrounding tick physics.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.19.4--1.20.6/findings/MC1194-1206-01.md).
