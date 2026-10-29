# Ordinary pressure plates: IDs 70 and 72

## Scope and conclusion

This closes the bounded source matrix row for the ordinary legacy pressure plates: numeric ID 70 (`stone_pressure_plate`) and ID 72 (`wooden_pressure_plate`) in 1.8.9, compared with 26.2 providers. The state projection and their local ray shape are source-supported. These blocks have no legacy movement collision box; their shared mutable ray bounds are written only when the ray provider is called. This is not a DDA visitation or whole-operation parity claim.

## Exact legacy behavior (1.8.9)

`Block.java:970-986` registers ID 70 as `PressurePlateBlock(Material.STONE, ActivationRule.MOBS)` and ID 72 as `PressurePlateBlock(Material.WOOD, ActivationRule.EVERYTHING)`. They are distinct from weighted plates. `PressurePlateBlock.java:20-32,61-68` defines one boolean `POWERED` property: metadata exactly 1 maps to true, all other metadata maps false; serialization is true→1, false→0. Its output signal is 15 when powered and 0 otherwise.

`AbstractPressurePlateBlock.java:26-38` implements `updateShape(WorldView,pos)` by reading the block state at that position and writing the inherited mutable bounds. Powered bounds are `(0.0625,0,0.0625)..(0.9375,0.03125,0.9375)`; unpowered bounds are `(0.0625,0,0.0625)..(0.9375,0.0625,0.9375)`. The class does not override `rayTrace`; `Block.java:456-465` calls virtual `updateShape` once at the start of each rayTrace invocation, then clips against the resulting bounds. `Block.canRayTrace(state,allowLiquids)` delegates to the default true (`Block.java:356-361`). In the ordinary `World.rayTrace` path, after the configured collision-filter and `canRayTrace` checks, the visitor invokes `block.rayTrace` (`World.java:633-653`). Thus an eligible visit to either pressure plate updates its shared bounds before clipping; no custom plate-specific ray loop or early-hit behavior is established here.

The same old class explicitly returns `null` from `getCollisionShape` (`AbstractPressurePlateBlock.java:46-49`). `Block.addCollisions` adds a box only for a non-null provider result (`Block.java:339-344`). Therefore neither ordinary pressure plate contributes a legacy movement collision box; the ray bounds are not consumed as its movement collision shape.

## Current identity, state, and providers (26.2)

The current registry keys are `minecraft:stone_pressure_plate` and `minecraft:oak_pressure_plate` (`BlockItemIds.java:589,593`). `Blocks.java:1793-1802` registers the former using `PressurePlateBlock(BlockSetType.STONE,...)` and `.noCollision()`; lines 1809-1819 register the latter with `BlockSetType.OAK` and `.noCollision()`.

The data-fixer state table preserves the old ID/property mapping: IDs 1120/1121 map stone-pressure-plate powered false/true to the same current block/state (`BlockStateData.java:3664-3665`); IDs 1152/1153 map current oak-pressure-plate powered false/true from old `wooden_pressure_plate` powered false/true (`BlockStateData.java:3782-3783`). These are metadata-derived legacy state IDs (70*16+0/1 and 72*16+0/1). The concrete current block defines `POWERED` and outputs 15/0 from it (`PressurePlateBlock.java:20,29,34`).

`BasePressurePlateBlock.java:26-27,39-42` supplies an outline shape with X/Z width 14/16 and height 0.5/16 when pressed or 1/16 when unpressed. `Block.column` centers that width (`Block.java:177-185`), giving the same local boxes as the old mutable bounds for both states. The current registrations disable collision; `BlockBehaviour.Properties.noCollision` sets `hasCollision=false` (`BlockBehaviour.java:1094-1097`) and the collision provider returns `Shapes.empty()` when that flag is false (`BlockBehaviour.java:327-328`). Thus the current outline and collision providers respectively agree with the old local ray bounds and null movement box for these two IDs/states.

## Applicability limits

This closes only the exact ordinary plate rows and the state/local-provider facts above. It does not infer DDA traversal equivalence, state availability beyond the stated mapping, cache mutation relevance, or behavior for weighted plates by analogy. Source-file hashes, canonical manifest identities, and readiness/provenance digests are recorded in `source-hashes.json`; every selected source hash matches its canonical manifest.
