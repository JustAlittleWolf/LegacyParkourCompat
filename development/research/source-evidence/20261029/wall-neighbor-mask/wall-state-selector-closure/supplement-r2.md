# Supplemental r2: source-closed live wall selector boundary

This is an immutable clarification of `supplement.md` (SHA-256 `ffcfd9626bb56acaa3ec572194318e8b471f2c706389930488410d24d44adc5c`) in response to the independent REVISE audit at `.native-source/20261029/wall-state-selector-closure-audit/supplement-audit.md` (SHA-256 `ad6512fa9262f93fec4e9759e97d9e36b2601ff57cf636bb8b4c94e2f9be3f34`). It states source-closed selector requirements. It makes no claim that a product implementation currently performs them; product sources were not inspected.

## Source-level result

The exact legacy target identity mapping is closed for old wall ID 139 metadata 0/1:

- `VARIANT=NORMAL` maps to current key `minecraft:cobblestone_wall`.
- `VARIANT=MOSSY` maps to current key `minecraft:mossy_cobblestone_wall`.

The 1.11.2 historical collision operation can be selected from those current keys without converting a live 26.2 wall state into a legacy `BlockState`: compute the four old neighbor booleans from the current neighbor states under the exact admitted domain below, assemble the historical four-bit mask in the old source direction order, and select the corresponding 1.11.2 collision table entry directly. This equivalence follows from the exact source methods: `resolveVirtualProperties` recomputes all four old booleans, and `getShapeIndex` reads only those booleans; neither `VARIANT` nor `UP` participates in index selection. The arrays contain the 16 entries addressed by that index.

This is a source-level operation contract, not a claim that the running product uses key dispatch, recomputes all four booleans, or discards live current wall-side properties. If an implementation instead converts or reads current `WallSide` properties as legacy booleans, its equivalence remains unproved here. Any target or neighbor outside the admitted selector must use whole-shape native fallback.

## Admitted source-proven operation domain

**Target selector:** one current block-state whose registered block key is exactly `minecraft:cobblestone_wall` or `minecraft:mossy_cobblestone_wall`, and whose `waterlogged` property is `false`. This is the exact identity/variant pair proved by the old decoder and 26.2 migration rows. Current target `UP` and side properties are not read by this operation contract. Any `UP` boolean and any combination of side enum values below can be ignored because the historical four-neighbor mask is recomputed and historical shape indexing excludes `UP` and the target's stored directional fields. `waterlogged=true` is excluded: it is absent from the old wall model and absent from the legacy migration target properties; current wall state defaults to `false`.

**Each of the four cardinal neighbor selectors:** exactly current `minecraft:air`, or any state under one of the 14 current stair keys listed in `supplement.md`. Stair property values are `FACING={north,east,south,west}`, `HALF={top,bottom}`, and `SHAPE={straight,inner_left,inner_right,outer_left,outer_right}`. For the 1.11.2 result the stair selector is block-identity-only: old `StairsBlock.isCube(BlockState)` is literal `false` for every state, so no modern property is inverted or read. The full old mask is false on all four sides for every admitted tuple. Fence gates, walls as neighbors, cubes, other keys, modern-only stairs, and every other state remain outside this selector and require whole-shape native fallback.

## Exact current wall property values and migration boundary

26.2 `WallBlock.java:35–43, 266–268` declares `UP` as boolean, `NORTH/EAST/SOUTH/WEST` as `EnumProperty<WallSide>`, and `WATERLOGGED` as boolean. The current wall-side enum is exactly `NONE`, `LOW`, `TALL` (`WallSide.java:5–19`). The state definition includes all six properties. Its default is `UP=true`, all four sides `NONE`, `WATERLOGGED=false` (`WallBlock.java:54–67`). The current wall update path can set a side to `NONE`, `LOW`, or `TALL` (`WallBlock.java:233–253`), so `TALL` is a real live value even though it is not emitted by the old-state property fix.

The datafix chain establishes identities and the legacy-generated defaults:

1. 1.11.2 and 1.12.2 register ID 139 as one `WallBlock`; metadata decodes to the two variants (old `Block.java` registrations and `WallBlock.getStateFromMetadata`).
2. 26.2 `BlockStateData.java:5941–6066` stores `minecraft:cobblestone_wall` as target for old aliases with `variant=cobblestone`; `BlockStateData.java:6067–6198` stores `minecraft:mossy_cobblestone_wall` for aliases with `variant=mossy_cobblestone`. The aliases enumerate the old boolean wall fields; the mapping function at lines 686–697 binds each supplied old tag to its target index. The two keys are independently registered as `WallBlock` in `Blocks.java:2699–2703`.
3. 26.2 `DataFixers.java:847–848` installs `WallPropertyFix` at schema 2503. `WallPropertyFix.java` applies to both current keys (the `WALL_BLOCKS` set), maps old side string `true` to `low` and every other old side string to `none`, and updates east, west, north, south. It does not change `UP` or `WATERLOGGED` and does not produce `tall`.
4. The `BlockStateData` wall targets omit `waterlogged`; current `WallBlock`'s registered default is `false`. Therefore the source-closed legacy-origin target subset requires `waterlogged=false`. The legacy migration emits side values within `{NONE, LOW}` after `WallPropertyFix`, but this operation contract does **not** reverse-convert those values or restrict live current side values to that subset. It ignores all four values `{NONE, LOW, TALL}` and computes the historical mask anew from the neighbors. A live state with `TALL` is admitted only under this key-only, property-insensitive operation contract; this report does not claim it is a direct legacy-migration output.

Exact legacy wall target properties before `WallPropertyFix` are the boolean side fields `east/west/north/south` and boolean `up` in the `BlockStateData` target record. The two target records start with `up=false` and all four sides `false`; after `WallPropertyFix`, those sides become `none`. Later current-world shape updates may produce low/tall values, but the historical operation must not use those current derived values.

## Why direct mask selection does not require a legacy state object

1.11.2 `WallBlock.resolveVirtualProperties` at lines 188–195 obtains north/east/south/west independently by `shouldConnectTo`; it also writes `UP` from the straight-through pattern and whether the block above is air. `getShapeIndex` at lines 107–125 reads only north/east/south/west and assigns their horizontal direction bits. `addCollisions` uses `COLLISION_SHAPES[getShapeIndex(state)]` at lines 91–98. The collision array is the 16-entry table at lines 48–65, derived from the same `SHAPES` entries; the target `VARIANT` is absent from the index and table selection. The operation can therefore select the exact historical shape-table slot from four freshly computed booleans without constructing a legacy state or attempting to convert `WallSide` back to boolean.

The above block affects only the recomputed `UP` value. Neither `UP` nor the current target wall's UP state enters the four-bit collision index. The neighbor booleans still require complete classification for all four sides; a missing side result cannot be filled from the live current wall-side enum.

## Precise limit

This closes a nonempty **source-level operation selector**: two mapped target keys with `waterlogged=false`, plus four neighbor states each drawn from air or the exact 14 mapped old stair keys. Every admitted four-bit historical mask is all-false. It also covers the oak-stair 1.12 false-positive witness from `supplement.md` while proving that the 1.11.2 operation selects the no-arm table entry.

It does not certify an existing product path. The missing product fact is whether that path selects by the two current block keys, checks the waterlogged boundary, classifies all four neighbors with the old source predicate, ignores the live `WallSide`/`UP` fields, and directly selects the historical table entry. This assignment forbids product inspection, so that implementation question remains open. If the product path cannot meet those selector requirements, retain whole-shape native fallback.

## Bound audit, inputs, and source hashes

Independent REVISE audit: `.native-source/20261029/wall-state-selector-closure-audit/supplement-audit.md`, SHA-256 `ad6512fa9262f93fec4e9759e97d9e36b2601ff57cf636bb8b4c94e2f9be3f34`.

Source manifests (and ready marker identities checked by the independent audit):

| Version / namespace | Source manifest SHA-256 | Ready marker SHA-256 |
| --- | --- | --- |
| 1.11.2 / Ornithe Feather | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` |
| 1.12.2 / Ornithe Feather | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` |
| 26.2 / unobfuscated | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` |

Additional exact 26.2 source SHA-256 values:

| Source | SHA-256 |
| --- | --- |
| `util/datafix/fixes/WallPropertyFix.java` | `fe07247694564755b89d260cd272d696066ae73e952b9e3b65a9594048484b4e` |
| `util/datafix/DataFixers.java` | `4a7de861c83f395255d6045d5f9fb98d932abd42724e54316adc59116ee6fb50` |
| `world/level/block/state/properties/WallSide.java` | `1a93c26666f20c844aa6b68006043542a732627ce23e59bc8e81b638e665adc3` |
| `world/level/block/WallBlock.java` | `b2bcd69d81e5a19a21eb50e7e5ac259269aca3871cec5899caef03ff4507a9c7` |
| `util/datafix/fixes/BlockStateData.java` | `6196f9039890d5f67b59cb372ec06da987e8a5ea80b49fd8bee9eeca0a1784a4` |
| `world/level/block/Blocks.java` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |

Other exact source SHA-256 values:

| Source | SHA-256 |
| --- | --- |
| 1.11.2 `block/Block.java` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` |
| 1.11.2 `block/WallBlock.java` | `17da6d881365e4dce5134f88cb9a9e5273d1fa1734812d13410dc6924a3acbfd` |
| 1.11.2 `block/StairsBlock.java` | `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627` |
| 1.11.2 `block/state/StateDefinition.java` | `95704c944742efb2ec9cba7eb73399a330195556e669451131ba542e6a9fac65` |
| 1.12.2 `block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 `block/WallBlock.java` | `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50` |
| 1.12.2 `block/StairsBlock.java` | `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611` |
| 1.12.2 `block/AirBlock.java` | `ac009eaac5edc3693f5827dfe330ec4f36749d51814e8901efdef95480043202` |

Bound source-evidence and prior artifact hashes:

| Input | SHA-256 |
| --- | --- |
| 20261023 classifier JSON | `feb9b3850bfeae923ed2b7dbb3d455e53d0ed06398de599e4fa9b543d99103fa` |
| 20261023 flattening mappings JSON | `af7c95fdab33b37fd8eefa8814fb26aa6030880979be241982a4187a929a69fa` |
| Prior wall domain contract | `44be2ea9aad699051e9e40e915910e8cdf973b7052125d585b3883adbdc8269f` |
| First source closure report | `0d82feadde804ac8035114421520723169095527852e1e26a34013a45a6c988a` |
| First source closure supplement | `ffcfd9626bb56acaa3ec572194318e8b471f2c706389930488410d24d44adc5c` |

These findings use the exact source publications above; accepted evidence files remain leads and audit bindings. No product files, history, backlog, build, test, or runtime were inspected.

