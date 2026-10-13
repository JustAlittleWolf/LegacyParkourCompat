# F-FARMLAND-PUSH-DISTANCE: block conversion moves intersecting players by different distances

- **1.12.2**, ornithe-feather: `FarmlandBlock.java`; lines 20-21.
- **1.13.2**, ornithe-feather: `FarmlandBlock.java`; lines 25.

## Difference

- Behavior boundary: server-side Farmland `onFallenOn` conversion from farmland to dirt and the resulting direct `Entity.teleport` vertical position write for entities intersecting the newly occupied upper block volume.

Damage calculation after the callback is excluded.
- Preconditions and concrete conditional witness: an authoritative server, farmland at block position `(0,64,0)`, player P1 lands on it with positive fall distance and `nextFloat() < fallDistance - 0.5F`, and P1 satisfies `width*width*height > 0.512F`; the PlayerEntity branch bypasses `mobGriefing`.

Player P2's Box intersects the top 1/16 layer, e.g.

X/Z `[0.2,0.8]`, minY `64.95`, and a positive height.

Conditional on 1.12.2 and 1.13.2 returning that same P2 from their corresponding `world.getEntities` query, the formulas differ:
  - 1.12.2: `min(0.0625, 65.0 - 64.95) + 0.001 = 0.051` blocks upward.
  - 1.13.2: for the new-volume voxel ending at Y=65, `calculateMaxDistance` returns `65.0 - (64.95 + 1.0) = -0.95`; the helper teleports by `1.0 - 0.95 = 0.05` blocks upward.
  - Result: under this paired-query and shape precondition, P2's direct vertical position write differs by `0.001` blocks.

The candidate query bounds on 1.12.2 and 1.13.2 are the same top layer; entity candidate enumeration remains conditional on the already recorded CHUNK-ENTITY-ENUMERATION slice.

## Movement meaning

- State producer -> consumer: P1's landing/fallDistance -> Farmland `onFallenOn` guard and block-state replacement -> newly occupied collision layer -> world entity query -> direct P2 teleport -> later player position/collision movement.
- Scope: this is player movement response to a block-state transition, not the damage outcome or non-player physics.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-FARMLAND-PUSH-DISTANCE.md).
