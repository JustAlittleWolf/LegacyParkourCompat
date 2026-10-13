# 1.13.2 adds normal jumping in shallow water

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1827-1839.
- **1.13.2**, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1903-1917.

## Difference

For water contact with measured overlap height greater than zero and no more than `0.4`, a jump input reaches the normal jump in 1.13.2 when the jump cooldown is zero. 1.12.2 instead applies its water jump's `+0.04F` vertical increment regardless of ground state or jump cooldown.

The newer normal jump assigns its configured jump strength and may also apply jump-boost and sprint-jump effects.

In deeper water 1.13.2 retains the `+0.04F` fluid impulse branch.

Both endpoint classes and members were inspected directly; this is an endpoint difference only.

## Movement meaning

The client input is sampled in `LocalClientPlayerEntity.mobTick()`, copied into `jumping` by the local `serverTickAi()` override, then read by `LivingEntity.mobTick()`. 1.13.2's water-height field is refreshed in the entity water-state path before living movement processing.

The fluid height is computed from version-specific fluid states and the contracted entity box.

The finding describes the client-side movement code and does not establish server handling of synchronized motion.

Source proves the branch selection and that 1.13.2 enters the normal-jump body under the stated precondition, while 1.12.2 adds `0.04F`.

It predicts different vertical velocity and, when sprinting, potentially different horizontal velocity.

Exact source hashes are recorded above.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/water-jump-height.md).
