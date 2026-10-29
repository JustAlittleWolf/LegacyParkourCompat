# Supplemental r3: legacy stair waterlogging state boundary

This immutable follow-up narrows the neighbor selector in `supplement-r2.md` (SHA-256 `3fc036aafd62b848e4f4cc3e60d85c26ad1142fff5b7296a5706eafefcdb9406`). The source-level R2 contract was accepted by `.native-source/20261029/wall-state-selector-closure-audit/supplement-r2-audit.md` (SHA-256 `d4e47725337f694672deff4baa57d85778580255175046027a1b3c1766092d0b`). R3 adds a required `waterlogged=false` condition to every admitted stair neighbor. It does not revise the target-wall selector, other neighbor families, or the explicit statement that product implementation was not inspected.

## Result

For each of the 14 mapped old stair block identities, the complete admitted 26.2 neighbor state domain is:

- exact current registry key listed below;
- `FACING ∈ {north, east, south, west}`;
- `HALF ∈ {top, bottom}`;
- `SHAPE ∈ {straight, inner_left, inner_right, outer_left, outer_right}`;
- `WATERLOGGED=false`.

The current `StairBlock` includes `WATERLOGGED` as an actual state property. Each legacy state mapping output omits that property, and the exact 26.2 `BlockState` decode path supplies the current block's default value. `StairBlock` sets that default to false. Thus every state produced by the bound legacy stair mapping is non-waterlogged. `WATERLOGGED=true` is a modern-only stair state outside the mapped source-era domain and must use whole-shape native fallback.

The old 1.11.2 stair predicate remains false across every admitted facing, half, and shape because old `StairsBlock.isCube(BlockState)` returns literal false. This property result does not authorize admitting the current waterlogged states: their current property state has no old stair mapping.

## Exact 26.2 state path

`BlockStateData.create(name, properties)` writes exactly the supplied target property map (`BlockStateData.java:678–684`). For each of the 14 stair blocks, the source has eight target `register` entries at indices `oldNumericId * 16` through `oldNumericId * 16 + 7`. Those eight targets cover four horizontal facings × two halves. Each target has the mapped current key and `FACING`, `HALF`, `SHAPE=straight`, with no `waterlogged` key. Each `register` call includes five source-era shape aliases (`straight`, `inner_left`, `inner_right`, `outer_left`, `outer_right`) for that facing/half. The `register` implementation places its target tag in `MAP[id]` and associates every supplied legacy alias with that target index (`BlockStateData.java:686–697`); no alias adds a waterlogging property to the target.

The output tag is decoded through the current state codec as follows:

1. 26.2 `BlockState.CODEC` calls the `StateHolder.codec` with the block registry, `Block::defaultBlockState`, and `Block::getStateDefinition` (`BlockState.java:9`).
2. `StateHolder.codec` uses `StateDefinition.propertiesCodec()` for an optional `Properties` field and supplies the block's default state when that field is absent (`StateHolder.java:145–158`).
3. `StateDefinition.createCodec` starts from the default state and each property codec uses that default property's value when the property key is absent (`StateDefinition.java:56–66, 133–140`).
4. 26.2 `StairBlock` registers `WATERLOGGED` in the state definition and sets its default state to `WATERLOGGED=false` (`StairBlock.java:33–36, 54–63, 215–218`).

This proves the final decoded state value is false; it is not an inference from the current default alone. Conversely, a state with `WATERLOGGED=true` cannot result from any of these legacy aliases through the stated codec path.

## Exhaustive mapped stair keys and source rows

The old IDs are the 1.11.2 registrations that construct `StairsBlock`; the 26.2 target keys are the exact first target states of each eight-entry `BlockStateData` group. For every row below, all eight registrations have six `create` calls each: one current target and five old `SHAPE` aliases. None of the target maps includes `waterlogged`; decoding supplies `false` by the path above.

| Old numeric ID / legacy key | 26.2 target key | Target indices (`id × 16` through `+7`) | First target source line |
| --- | --- | --- | ---: |
| 53 `oak_stairs` | `minecraft:oak_stairs` | 848–855 | 1940 |
| 67 `stone_stairs` | `minecraft:cobblestone_stairs` | 1072–1079 | 3552 |
| 108 `brick_stairs` | `minecraft:brick_stairs` | 1728–1735 | 4652 |
| 109 `stone_brick_stairs` | `minecraft:stone_brick_stairs` | 1744–1751 | 4724 |
| 114 `nether_brick_stairs` | `minecraft:nether_brick_stairs` | 1824–1831 | 4824 |
| 128 `sandstone_stairs` | `minecraft:sandstone_stairs` | 2048–2055 | 5023 |
| 134 `spruce_stairs` | `minecraft:spruce_stairs` | 2144–2151 | 5714 |
| 135 `birch_stairs` | `minecraft:birch_stairs` | 2160–2167 | 5786 |
| 136 `jungle_stairs` | `minecraft:jungle_stairs` | 2176–2183 | 5858 |
| 156 `quartz_stairs` | `minecraft:quartz_stairs` | 2496–2503 | 6965 |
| 163 `acacia_stairs` | `minecraft:acacia_stairs` | 2608–2615 | 7476 |
| 164 `dark_oak_stairs` | `minecraft:dark_oak_stairs` | 2624–2631 | 7548 |
| 180 `red_sandstone_stairs` | `minecraft:red_sandstone_stairs` | 2880–2887 | 7826 |
| 203 `purpur_stairs` | `minecraft:purpur_stairs` | 3248–3255 | 9135 |

Current `StairBlock` properties are exactly `FACING`, `HALF`, `SHAPE`, and `WATERLOGGED` (`StairBlock.java:33–36, 215–218`). The first three values listed above cover the complete old state/property domain. `WATERLOGGED=true` is excluded for all 14 keys, including oak; there is no stair family exception in the mapping table.

## Whole-shape fallback boundary

The admitted four-neighbor tuple domain from R2 remains current air plus the 14 listed stair keys with `WATERLOGGED=false`. Air has no state properties. For any stair neighbor with `WATERLOGGED=true`, any unmapped key, any unclassified cardinal neighbor, or an unmapped target wall, the historical four-bit mask is incomplete and the whole operation must use the native wall shape. Do not reuse a current side value to fill a rejected neighbor's bit.

The exact historical mask remains all false for every admitted tuple. The mapping completeness and the boolean predicate are separate gates: all 14 stair state mappings prove the admitted keys/properties, while the old constant `isCube=false` proves their historical false result.

## Bound audit, source manifests, and exact sources

Accepted R2 audit: `.native-source/20261029/wall-state-selector-closure-audit/supplement-r2-audit.md`, SHA-256 `d4e47725337f694672deff4baa57d85778580255175046027a1b3c1766092d0b`.

Source manifests: 1.11.2 Ornithe Feather `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1.12.2 Ornithe Feather `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 26.2 unobfuscated `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

Exact source SHA-256 values directly relevant to this stair-state check:

| Source | SHA-256 |
| --- | --- |
| 1.11.2 `block/Block.java` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` |
| 1.11.2 `block/StairsBlock.java` | `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627` |
| 26.2 `world/level/block/StairBlock.java` | `2a8ad6eedc5a151af7a739ccd476f18cab4fe78e9df7c1ec601cb9687a77630b` |
| 26.2 `util/datafix/fixes/BlockStateData.java` | `6196f9039890d5f67b59cb372ec06da987e8a5ea80b49fd8bee9eeca0a1784a4` |
| 26.2 `world/level/block/state/BlockState.java` | `df5b24701ee69145a78901ee0e370e063b63b3e74a0aae32c89b2ae45bc6d205` |
| 26.2 `world/level/block/state/StateHolder.java` | `c0dab7c413c2c8c0ca1b76afa1a77607117b66f71886674fe89d1059b7a9746b` |
| 26.2 `world/level/block/state/StateDefinition.java` | `195bfe795c1c908f332afd90e9f59036fe3bf51997fe156c240c3c7c7eb3d4d0` |
| 26.2 `world/level/block/Blocks.java` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |

No product files, product history, backlog, builds, tests, games, or runtime state were inspected.
