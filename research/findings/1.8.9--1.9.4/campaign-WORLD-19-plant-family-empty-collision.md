# WORLD-19: plant-family empty collision inheritance

- **1.8.9**, ornithe-feather: `PlantBlock.java`.
- **1.9.4**, ornithe-feather: `PlantBlock.java`.

## Difference

1.8.9 null collision shape and 1.9.4 EMPTY_BLOCK_SHAPE produce no collision entries for the inventoried PlantBlock descendants.

## Movement meaning

The compared plant providers append no collision box in either release; representation changes from null to an empty shape do not establish a player collision delta.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-19-plant-family-empty-collision.md).
