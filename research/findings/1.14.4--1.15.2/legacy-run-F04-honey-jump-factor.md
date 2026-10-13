# F04: Honey Block halves the base ground-jump impulse

- **1.14.4**, mojmap: `LivingEntity.java`; lines 1748–1750.
- **1.15.2**, mojmap: `LivingEntity.java`; lines 1799–1801.

## Difference

With no Jump Boost and a ground jump whose block lookup resolves to Honey Block, 1.15.2 calculates `0.42F * 0.5F` for the initial vertical impulse; 1.14.4 has no Honey Block and uses `0.42F`.

If Jump Boost is active, its additive term is applied after this base-power calculation in both source paths. 1.15.2 also rejects the automatic-jump trigger when this block lookup returns less than `1.0F`, so Honey blocks do not trigger the local obstacle-jump path.

## Movement meaning

Local player input -> living movement integration -> `jumpFromGround()` -> `getJumpPower()` -> current block's `jumpFactor`; the same factor feeds 1.15.2 `LocalPlayer.canAutoJump()`.

The 1.15.2 factor comes from the Honey Block registration and is read through the shared entity helper.

The player reaches the ground-jump path through the `LivingEntity` superclass and the automatic-jump path through `LocalPlayer.move()` after collision movement.

Source proves the changed vertical impulse formula and its operation order.

This is a new block mechanic in 1.15.2, so it must remain classified as modern-only and does not establish historical behavior for 1.14.4 maps.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F04-honey-jump-factor.md).
