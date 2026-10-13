# F-013: Llama lowers a Player passenger's vertical position in 1.18.2

- **1.17.1**, mojmap: `net/minecraft/world/entity/animal/horse/Llama.java`; lines 164-166.
- **1.18.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-013-llama-passenger-rider-height-source-hash-correction-2026-10-08.md); lines 166-168.

## Difference

For an equal Llama bounding-box height and the same passenger, 1.17.1 positions the player at the Llama's Y plus `height * 0.67` plus the passenger riding offset; 1.18.2 uses `height * 0.6` plus that same passenger offset. 1.18.2 therefore writes a player position lower by `height * 0.07` on each rider-position update.

This is a direct player position result, not a claim about Llama acceleration, route or vehicle physics.

## Movement meaning

For an equal Llama bounding-box height and the same passenger, 1.17.1 positions the player at the Llama's Y plus `height * 0.67` plus the passenger riding offset; 1.18.2 uses `height * 0.6` plus that same passenger offset. 1.18.2 therefore writes a player position lower by `height * 0.07` on each rider-position update.

This is a direct player position result, not a claim about Llama acceleration, route or vehicle physics.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-013-llama-passenger-rider-height-source-hash-correction-2026-10-08.md).
