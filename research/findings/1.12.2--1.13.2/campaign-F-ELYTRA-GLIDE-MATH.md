# F-ELYTRA-GLIDE-MATH — 1.13.2 regroups vertical glide acceleration

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1425-1534.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-ELYTRA-GLIDE-MATH.md); lines 1478-1537.

## Difference

1.13.2 regroups the arithmetic and introduces a shared gravity factor that can be affected by Slow Falling.

Preserve these source expressions and intermediate types/order; don't simplify them to a real-number identity.

## Movement meaning

- Reachability and dependencies: inherited LivingEntity movement dispatch is called by PlayerEntity through the local player's travel tick while fall-flying.

Elytra state/equipment eligibility and camera pitch are separate dependencies.

Slow Falling itself is a modern-only effect for this historical one-way scope.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-ELYTRA-GLIDE-MATH.md).
