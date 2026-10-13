# MC1194-1206-03: Fall-distance reset covers more Slow Falling and Levitation states

- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 1983-1989.
- **1.20.6**, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 2674-2683.

## Difference

1.19.4 resets fall distance for Slow Falling only when the vertical movement value is nonpositive and the local travel method runs. 1.19.4 resets it for Levitation in the normal non-fluid movement branch. 1.20.6 moves the reset before travel and applies it for either effect, without checking vertical direction or which fluid/air travel branch will run.

Thus a player moving upward with Slow Falling, or a player in a travel branch where 1.19.4's Levitation reset is not reached, has fall distance set to zero in 1.20.6 but not by these 1.19.4 code paths.

## Movement meaning

Local player travel is reached through `LocalPlayer.aiStep()` -> `LivingEntity.aiStep()` -> `LivingEntity.travel()`. 1.20.6 writes the movement bookkeeping field before the selected travel branch; 1.19.4 writes it only in the cited branch conditions.

The source proves a fall-distance state difference, not a position or velocity delta.

If the differing fall-distance value persists until a later landing after the effect ends, the amount considered by fall-damage handling may differ.

No direct movement trajectory difference is claimed from this reset alone.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.19.4--1.20.6/findings/MC1194-1206-03.md).
