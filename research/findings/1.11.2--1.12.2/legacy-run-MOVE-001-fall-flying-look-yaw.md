# MOVE-001: Fall-flying direction uses body yaw instead of head yaw

- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1915-1928.
- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 1690-1692.

## Difference

For 1.11.2, `LivingEntity.getLookVector()` calls the virtual `getRotationVector(1.0F)`, so the `LivingEntity` override supplies pitch and head yaw at the tick endpoint.

In 1.12.2 the `LivingEntity.getLookVector()` override is absent; the inherited `Entity` implementation calls the final two-float rotation-vector helper directly with pitch and body yaw.

Although the 1.12.2 `LivingEntity.getRotationVector(float)` still uses head yaw for its own callers, `Entity.getLookVector()` bypasses that override.

## Movement meaning

The client player's inherited `LivingEntity.moveRelative` selects its fall-flying branch while `isFallFlying()` is true and uses `getLookVector()` in the glide acceleration calculations. 1.11.2 concrete differing precondition is active player fall-flying while `headYaw != yaw`.

The exact 1.11.2/1.12.2 player path reaches this shared living-entity implementation; other aspects of glide selection/equipment were not needed to establish this direction change.

Source proves the vector input changes; trajectory consequences are inferred from the unchanged downstream glide expressions consuming its X/Y/Z components.

Source-proven: the glide algorithm receives a look vector built from head yaw in 1.11.2 and body yaw in 1.12.2.

Predicted consequence: when those yaw values differ, subsequent fall-flying velocity updates can differ.

The adjacent owner is checking 1.11.2 remapper invalid-access warning targets; the raw log is unavailable, so the exact warning-to-class relation remains an audit caveat.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.11.2--1.12.2/findings/MOVE-001-fall-flying-look-yaw.md).
