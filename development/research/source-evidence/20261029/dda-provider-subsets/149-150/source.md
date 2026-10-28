# 1.8.9 comparator IDs 149–150: current alias and collision provider

## Finding scope

This source-only report closes the block-key/state and collision-provider dependency for legacy IDs 149 and 150. These IDs are **unpowered and powered comparator**, not repeater: the 1.8.9 registry assigns repeaters to IDs 93 and 94, and comparators to 149 and 150 (`Block.java:1029–1030,1153–1155`). Redstone signal and timing behavior is outside this bounded collision-provider finding. No DDA traversal, camera reachability, or runtime-parity claim is made.

## Exact source identities

The older source is Minecraft 1.8.9, `ornithe-feather` (Feather Gen2 1.8.9 build 2); the newer source is Minecraft 26.2, `unobfuscated`. Ready markers bind those exact identities and their complete canonical source manifests. The manifest at `sources.sha256` includes both ready markers, provenance files, source manifests, and the selected Java files. Every selected Java hash was checked against its exact entry in the corresponding canonical source manifest; each complete source-manifest digest matches its ready marker.

## Legacy registrations and states

In 1.8.9, ID 149 registers `unpowered_comparator` as `new ComparatorBlock(false)` and ID 150 registers `powered_comparator` as `new ComparatorBlock(true)` (`Block.java:1153–1155`). The two registrations create distinct block instances. `ComparatorBlock` gives each instance the same default block state: north-facing, `POWERED=false`, `MODE=COMPARE` (`ComparatorBlock.java:34`). Its state definition contains horizontal `FACING`, Boolean `POWERED`, and `MODE` (`ComparatorBlock.java:230–254`).

All metadata values 0–15 decode to valid states. `getStateFromMetadata` uses `Direction.byIdHorizontal(metadata)`, `metadata & 8` for the `POWERED` property, and `metadata & 4` for `SUBTRACT` versus `COMPARE` (`ComparatorBlock.java:230–236`). The direction helper reduces every input modulo the four horizontal directions (`Direction.java:172–173`); the old direction enum's horizontal IDs give `metadata & 3`: 0=South, 1=West, 2=North, 3=East (`Direction.java:12–15`). The 26.2 legacy-state records use that same order for both old keys (`BlockStateData.java:2384–2415`). Thus high metadata bits do not cause an invalid state. The inverse encoder writes those same direction/mode/powered fields (`ComparatorBlock.java:238–250`). The constructor's `powered` instance flag is an additional behavior of the two old registrations; it does not alter their collision bounds.

## Current alias and state projection

The 26.2 schema maps both old block names, `minecraft:unpowered_comparator` and `minecraft:powered_comparator`, to one current key, `minecraft:comparator` (`V704.java:87–88`). Its block-state table contains records for both old names and the single current comparator, retaining the four horizontal facing values, `MODE` compare/subtract, and `POWERED` true/false (`BlockStateData.java:2384–2415`). Current `ComparatorBlock` inherits `FACING` and `POWERED` from `HorizontalDirectionalBlock`/`DiodeBlock`, declares `MODE`, and defaults to north, false, compare (`ComparatorBlock.java:29–38`, `DiodeBlock.java:24–30`). This is a collision-state mapping only; the old two-key registration distinction and constructor flag do not persist as separate current registry keys.

## Collision provider and bounds lifecycle

The old `DiodeBlock` constructor calls `setShape(0,0,0,1,0.125F,1)` once for each comparator instance (`DiodeBlock.java:17–21`). Neither `ComparatorBlock` nor `DiodeBlock` overrides the shape update or collision provider. The base collision accessor translates the instance's stored bounds into a world-space box (`Block.java:333–352`); no state, facing, mode, block entity, or power value changes this shape. The only comparator/diode bound write is constructor-time. The base `Block.rayTrace` calls `updateShape`, whose default implementation is empty (`Block.java:456–459,596–597`); these two classes add no ray-time writer. Thus this family has no movement-query mutable-bounds cache transition to emulate.

The 26.2 `DiodeBlock` returns a static `Block.column(16,0,2)` shape (`DiodeBlock.java:29,39`). `Block.column(sizeXZ,minY,maxY)` creates a centered shape from `8-sizeXZ/2` to `8+sizeXZ/2` on X/Z and delegates to `Block.box`, which divides by 16 (`Block.java:160–162,177–184`). The result is exactly `[0,0,0]–[1,1/8,1]`, matching the legacy constructor bounds. The returned shape is independent of all comparator states and context. Current `Blocks.COMPARATOR` uses default `BlockBehaviour.Properties.of()` settings and does not opt out of collision (`Blocks.java:2898–2900`); `Properties.hasCollision` defaults true, and the base provider returns the state's shape when this flag is true (`BlockBehaviour.java:985,1029,327–328`).

## Bounded conclusion

For collision-provider purposes, both legacy comparator IDs and all 16 metadata values map to the current comparator key with the same 1/8-height, full-footprint collision geometry. The former separate unpowered/powered registration names map to the single current key; facing/mode/powered properties are retained by the state projection but do not affect this collision shape. The current comparator collision provider is immutable and state-independent, while the old bounds are written once during construction and never refreshed by these classes.

This report does not establish redstone output, block-entity behavior, comparator/repeater timing, arbitrary legacy-state conversion, ray-hit parity in a full scan, or DDA visitor/last-writer equivalence. It does not claim movement or camera runtime reachability for any witness.

## Source references

- 1.8.9 `Block.java`: registry IDs 93–94 and 149–150 (repeater/comparator distinction); collision bounds and `rayTrace`/default `updateShape` (`:292–352,456–459,596–597`).
- 1.8.9 `ComparatorBlock.java`, `HorizontalFacingBlock.java`: constructor/default state, metadata decode/encode, property set, and horizontal `FACING` constraint (`ComparatorBlock.java:28–35,230–254`; `HorizontalFacingBlock.java:8–10`).
- 1.8.9 `DiodeBlock.java`: constructor-time bounds (`:17–21`).
- 1.8.9 `Direction.java`: horizontal direction metadata modulo (`:172`).
- 26.2 `V704.java`: old comparator key aliases (`:87–88`).
- 26.2 `BlockStateData.java`: legacy/current comparator state records (`:2384–2415`).
- 26.2 `Blocks.java`: current comparator registration (`:2898–2900`).
- 26.2 `ComparatorBlock.java`, `DiodeBlock.java`, `HorizontalDirectionalBlock.java`: state properties, defaults and shape provider (`ComparatorBlock.java:29–38`; `DiodeBlock.java:24–39`; `HorizontalDirectionalBlock.java:11–14`).
- 26.2 `Block.java`, `BlockBehaviour.java`: `column`/`box` coordinates, `hasCollision` default and base collision-provider gate (`Block.java:160–184`; `BlockBehaviour.java:327–328,985,1029`).
