# F-SLOW-FALLING — 1.13.2 introduces an effect-dependent gravity factor

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1425-1618.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-SLOW-FALLING.md); lines 1478-1688.

## Difference

1.13.2 introduces Slow Falling gravity 0.01 rather than 0.08 for downward/nonpositive Y velocity and clears fallDistance. 1.12.2 lacks the effect.

## Movement meaning

The effect-dependent branch changes gravity for eligible players in 1.13.2. Slow Falling is absent from 1.12.2, so it does not define movement for an equivalent historical effect state.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-SLOW-FALLING.md).
