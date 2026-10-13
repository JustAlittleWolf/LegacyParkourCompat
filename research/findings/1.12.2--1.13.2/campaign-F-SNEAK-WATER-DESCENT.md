# F-SNEAK-WATER-DESCENT — 1.13.2 adds a local downward water input

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 771-782.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-SNEAK-WATER-DESCENT.md); lines 784-807.

## Difference

sneaking in water has a new direct velocity-writing call in 1.13.2. `LivingEntity.knockDownwards()` 1.13.2 line 1466 subtracts `0.04F` from velocityY; 1.12.2 has no paired helper and its local-player mobTick has no corresponding branch.

## Movement meaning

- Reachability and dependencies: local keyboard sneaking and sampled water-contact state feed this condition; the method runs before superclass LivingEntity.mobTick travel.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-SNEAK-WATER-DESCENT.md).
