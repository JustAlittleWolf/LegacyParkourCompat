# F-05: Player horizontal velocity is cut off by vector length in 1.21.5

- **1.21.4**, mojmap: `net/minecraft/world/entity/LivingEntity.java`; lines 2708-2724.
- **1.21.5**, mojmap: `LivingEntity.java`; lines 2686-2709.

## Difference

For a player with horizontal velocity `(0.0025, 0.0025)`, 1.21.4 zeros both components because each absolute value is below `0.003`. 1.21.5 preserves both because their squared horizontal length, `1.25E-5`, is greater than the `9.0E-6` cutoff.

The reverse shape of difference is not possible: if a player's squared horizontal length is below `9.0E-6`, each component is necessarily below `0.003`, so both versions zero both.

Thus 1.21.5 retains some diagonal low-speed vectors that 1.21.4 removes.

Vertical cutoff and non-player cutoff are unchanged.

## Movement meaning

`LivingEntity.aiStep` executes this velocity cleanup before player input, jumping and travel.

LocalPlayer reaches the inherited method via `super.aiStep()`.

The changed components are written through `setDeltaMovement` and then consumed by travel.

The branch is selected by the vanilla `EntityType.PLAYER` identity; server/client movement authority controls later travel but not this cutoff.

The source proves the differing threshold geometry and a concrete velocity precondition. 1.21.5 can carry a retained diagonal low-speed horizontal velocity into subsequent travel where 1.21.4 starts that stage at zero horizontal velocity.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-05-player-horizontal-velocity-cutoff.md).
