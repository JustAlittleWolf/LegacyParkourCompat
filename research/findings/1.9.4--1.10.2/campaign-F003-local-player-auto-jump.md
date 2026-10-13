# F003: 1.10.2 adds obstacle-triggered local-player jump input

- **1.9.4**, ornithe-feather: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 586-597.
- **1.10.2**, ornithe-feather: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; line 105.

## Difference

1.10.2 detects eligible collision geometry after the local player's movement call and schedules a one-tick auto-jump.

In the next local-player `mobTick`, the timer is consumed after input ticking and sets `input.jumping=true`.

The call to `super.mobTick()` then copies that input into `LivingEntity.jumping` and processes the jump in the same invocation when the ordinary grounded/cooldown guards pass. 1.9.4 has no corresponding local-player timer, movement override, helper, or synthesized jump input. 1.10.2 also suppresses treating this generated input as the creative-flight double-tap.

## Movement meaning

The local player receives `KeyboardInput`; its normal movement branches reach virtual `Entity.move` through `LivingEntity.moveRelative`. 1.10.2's local-player override calls the superclass movement first and runs the helper on the actual horizontal displacement.

The helper requires the option and player-state guards, nonzero movement, and collision/landing checks.

Its timer writer, timer consumer, serverTickAi copy, and LivingEntity jump consumer form a source- and bytecode-confirmed path.

When the helper's guards and geometry checks pass, 1.10.2 can initiate a player jump without a physical jump-key press; 1.9.4 has no corresponding path.

This establishes the paired-version behavior difference.

Exact resulting position and follow-on movement depend on the movement and collision scenario and are not claimed.

Source/revision identity remains limited by the unavailable original derived jars.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.9.4--1.10.2/findings/F003-local-player-auto-jump.md).
