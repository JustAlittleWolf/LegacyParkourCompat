# F-WATER-GRAVITY — ordinary 1.13.2 water travel uses a smaller downward adjustment

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1555-1584.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-GRAVITY.md); lines 1617-1655.

## Difference

under those conditions 1.12.2's adjustment is `-0.02`; 1.13.2's ordinary adjustment is `-0.005`, subject to the explicit clamp branch.

## Movement meaning

under those conditions 1.12.2's adjustment is `-0.02`; 1.13.2's ordinary adjustment is `-0.005`, subject to the explicit clamp branch.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-GRAVITY.md).
