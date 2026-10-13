# F-WATER-SPRINT-TRAVEL — 1.13.2 sprinting changes water drag and skips gravity

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1555-1584.
- **1.13.2**, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative(FFF)V`; lines 1617-1655.

## Difference

with the stated inputs, 1.12.2 multiplies X/Z velocity by 0.8F and applies water gravity; 1.13.2 multiplies X/Z by 0.9F and skips the water gravity update.

## Movement meaning

For a player already sprinting in water with gravity enabled and neither Depth Strider nor Dolphin's Grace, 1.13.2 retains more horizontal momentum and omits the water gravity update applied in 1.12.2.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-SPRINT-TRAVEL.md).
