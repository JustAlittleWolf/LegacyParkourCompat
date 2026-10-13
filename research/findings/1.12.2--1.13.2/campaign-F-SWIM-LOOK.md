# F-SWIM-LOOK — 1.13.2 swimming adds pitch-directed vertical velocity control

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; lines 1386-1410.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-SWIM-LOOK.md); lines 1442-1487.

## Difference

1.13.2 applies a look-directed vertical adjustment absent from 1.12.2's player override.

It is separate from the local sneak-water descent impulse and water-height jump selection.

## Movement meaning

Exact fluid query, pose, and collision effects remain separate dependencies.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-SWIM-LOOK.md).
