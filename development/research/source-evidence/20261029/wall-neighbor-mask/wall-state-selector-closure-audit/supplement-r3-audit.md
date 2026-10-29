# Independent R3 audit: stair waterlogging boundary

## Verdict: ACCEPT (as a source-level supplement to accepted R2)

I accept `.native-source/20261029/wall-state-selector-closure/supplement-r3.md`, SHA-256 `7bad1170df6e31864064e7f5e3b3d60d001cee31a973b48e19feee5302dd418d`, as a bounded clarification to the accepted R2 selector contract. The added `waterlogged=false` boundary is supported by the exact 26.2 stair definitions, all fourteen legacy-to-current stair mapping groups, and the current state codec's missing-property defaults. The exclusion requires whole-shape native fallback under R2's already-accepted fallback rule.

This verdict accepts only the source-level domain. It does not certify a product implementation. I did not inspect product files or history. The prior R2 audit remains the binding review for the unchanged wall selector, historical predicate, and legacy stair identity claims.

## Waterlogging evidence

The canonical 26.2 source publication is `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated`. Its ready marker identifies version `26.2`, mapping `unobfuscated`, and source manifest `unobfuscated.sources.sha256`; the ready marker's manifest hash and recomputed manifest hash both equal `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

`world/level/block/StairBlock.java` has SHA-256 `2a8ad6eedc5a151af7a739ccd476f18cab4fe78e9df7c1ec601cb9687a77630b`, matching the manifest. Lines 33–36 declare `FACING`, `HALF`, `SHAPE`, and `WATERLOGGED`; lines 54–63 set the registered default to north, bottom, straight, and `WATERLOGGED=false`; lines 215–218 add exactly those four properties to its state definition. The property is real in current stair states, and its default is false.

`util/datafix/fixes/BlockStateData.java` has SHA-256 `6196f9039890d5f67b59cb372ec06da987e8a5ea80b49fd8bee9eeca0a1784a4`, matching the manifest. Its exact mapping rows identify these fourteen old IDs and current target keys:

| Old ID | Current target key | First target line |
| ---: | --- | ---: |
| 53 | `minecraft:oak_stairs` | 1940 |
| 67 | `minecraft:cobblestone_stairs` | 3552 |
| 108 | `minecraft:brick_stairs` | 4652 |
| 109 | `minecraft:stone_brick_stairs` | 4724 |
| 114 | `minecraft:nether_brick_stairs` | 4824 |
| 128 | `minecraft:sandstone_stairs` | 5023 |
| 134 | `minecraft:spruce_stairs` | 5714 |
| 135 | `minecraft:birch_stairs` | 5786 |
| 136 | `minecraft:jungle_stairs` | 5858 |
| 156 | `minecraft:quartz_stairs` | 6965 |
| 163 | `minecraft:acacia_stairs` | 7476 |
| 164 | `minecraft:dark_oak_stairs` | 7548 |
| 180 | `minecraft:red_sandstone_stairs` | 7826 |
| 203 | `minecraft:purpur_stairs` | 9135 |

For each listed ID, the target registrations occupy `id * 16` through `id * 16 + 7`; each target is a `create` call with a map containing the stair's facing, half, and shape. The five legacy shape aliases are supplied to each registration separately. `create(name, properties)` at lines 682–684 stores exactly the supplied map as `Properties`; `register` at lines 686–697 stores the target tag in `MAP[id]` and binds the old aliases to that index. None of the 26.2 `BlockStateData.java` source contains a `waterlogged` property key. Thus the mapped target tag omits `waterlogged` for all fourteen families. The report accurately distinguishes the mapped target tag (which uses `SHAPE=straight`) from the five legacy shape aliases.

The decoding claim is supported by exact sources whose hashes also match the same manifest: `world/level/block/state/BlockState.java` `df5b24701ee69145a78901ee0e370e063b63b3e74a0aae32c89b2ae45bc6d205`; `StateHolder.java` `c0dab7c413c2c8c0ca1b76afa1a77607117b66f71886674fe89d1059b7a9746b`; and `StateDefinition.java` `195bfe795c1c908f332afd90e9f59036fe3bf51997fe156c240c3c7c7eb3d4d0`. `BlockState.CODEC` invokes `StateHolder.codec` with the block registry, default-state accessor, and state-definition accessor (`BlockState.java:9`). The optional `Properties` field falls back to the block default (`StateHolder.java:145–158`), while missing individual property values are supplied from that default state (`StateDefinition.java:56–66, 133–140`). With the mapped target's properties present but `waterlogged` absent, the resulting stair state's value is false, as the `StairBlock` default establishes. No mapped alias can emit `waterlogged=true`.

## Fallback boundary

R3's fallback is narrow and consistent with R2: a current stair state with `WATERLOGGED=true` is outside the source-mapped state domain and therefore must take whole-shape native fallback under R2's all-or-nothing rule. The same applies to other states outside the accepted target and four-neighbor selectors. This is a domain exclusion based on the absence of a legacy mapping for the modern property value; it does not say that the old `isCube=false` predicate would change for an old stair state. I found no claim that a native side may be mixed into a partial historical mask.

## Bound inputs and source identity

- R3 supplement: SHA-256 `7bad1170df6e31864064e7f5e3b3d60d001cee31a973b48e19feee5302dd418d`.
- Accepted R2 supplement: SHA-256 `3fc036aafd62b848e4f4cc3e60d85c26ad1142fff5b7296a5706eafefcdb9406`.
- Accepted R2 audit: SHA-256 `d4e47725337f694672deff4baa57d85778580255175046027a1b3c1766092d0b`.
- 26.2 unobfuscated source manifest: SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; ready-marker SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`.

The old 1.11.2 predicate and unchanged R2 selector claims are accepted by the bound R2 audit and were not reopened here. No product sources, product history, builds, tests, gameplay, or runtime were inspected.
