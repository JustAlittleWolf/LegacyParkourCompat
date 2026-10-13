# F-002 — Water source conversion gains a gamerule input

- **1.19.2**, mojmap: `net/minecraft/world/level/material/FlowingFluid.java`; lines 154-178.
- **1.19.3**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.2--1.19.3/findings/F-002-water-source-conversion.md); lines 154-178.

## Difference

Default behavior preserves 1.19.2's true result. 1.19.2 non-default false rule prevents the source conversion branch after the existing horizontal-neighbor/support conditions.

## Movement meaning

`FlowingFluid.getNewLiquid` -> water-specific `canConvertToSource(level)` -> resulting world fluid state -> client player's `LivingEntity.travel` fluid-state/contact reads (1.19.2 lines 2042-2256; 1.19.3 2052-2263).

The gamerule/world fluid state is a server/world input.

Source-proven: 1.19.3 can prevent source conversion when the gamerule is false and the existing neighbor/support conditions pass; the default true value matches 1.19.2.

Predicted only: a resulting water-state topology difference can change the fluid height/contact read by player travel.

No specific trajectory or non-default server configuration is claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.2--1.19.3/findings/F-002-water-source-conversion.md).
