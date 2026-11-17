# Old block ID 5 (`minecraft:planks`) alias/state join

## Scope and source identities

This packet covers only the six old `minecraft:planks` variants and their registered 1.13.2 flattening/state-fix routes. It does not compare collision geometry or claim generic legacy-importer behavior.

- 1.12.2 Feather source manifest: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2 Feather source manifest: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Existing provider-only report: `planks_logs_collision_keys/report.md`, SHA-256 `61511ba8ac01a9e3afad8a7e61fea8195825b3065f0f8234a91de24c1eb0d2c6`; its separate ACCEPT verdict is `2448ef91f2ba28c995d432caceea8641ce7930fbaae684a2eb7dbdadb9896fdc`. Reused as prior bounded provider evidence only.

Relevant exact source files, each checked against its endpoint `ornithe-feather.sources.sha256` entry:

- 1.12.2 `net/minecraft/block/PlanksBlock.java` — `f190065f853a6aa9ad64e760607f057752bdeb4fe3ac724116174a23eddae29e`.
- 1.13.2 `net/minecraft/unmapped/C_80754635.java` — `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e`.
- 1.13.2 `net/minecraft/unmapped/C_29689164.java` — `5f327c8aaf217f59975635e382860f92fbc7a40202d8e61200eebc8779c6bf1c`.
- 1.13.2 `net/minecraft/unmapped/C_15874933.java` — `8d62c370f468ca9534a4db65338dacc69a0f416079d4c499cc8d0e9250c3ae71`.
- 1.13.2 `net/minecraft/unmapped/C_37931705.java` — `2717df7465597f98634ff1569968ff247a4ccda472209e2628ae4ab21c7364de`.
- 1.13.2 `net/minecraft/util/datafix/DataFixes.java` — `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8`.

## Findings

1.12.2 `PlanksBlock.Variant` declares IDs and serialized names in order: `0 oak`, `1 spruce`, `2 birch`, `3 jungle`, `4 acacia`, `5 dark_oak`; `getStateFromMetadata` selects `byId(metadata)`, and `getMetadataFromState` returns that same variant ID (`PlanksBlock.java`, enum and metadata methods).

The 1.13.2 legacy state table in `C_80754635` registers encoded legacy state IDs 80–85 with target states that have only `Name` and no `Properties`:

| Old metadata | Encoded table ID | Target state |
| ---: | ---: | --- |
| 0 | 80 | `minecraft:oak_planks` |
| 1 | 81 | `minecraft:spruce_planks` |
| 2 | 82 | `minecraft:birch_planks` |
| 3 | 83 | `minecraft:jungle_planks` |
| 4 | 84 | `minecraft:acacia_planks` |
| 5 | 85 | `minecraft:dark_oak_planks` |

Each table registration also gives the old alias state `{Name:'minecraft:planks',Properties:{variant:'<name>'}}`. `m_11856336` indexes that old dynamic by its full dynamic value and stores the target at the same integer slot; `m_11299400` returns the target when an input dynamic exactly matches a registered old state. The names and property values therefore match the six 1.12.2 enum variants. The target flattened plank states have no surviving `variant` property; the variant is represented by the specific target block key.

`DataFixes` registers `C_15874933` at schema 1450. Its `BlockStateStructureTemplateFix` updates `BLOCK_STATE` remainder through `C_80754635.m_11299400`, so this registered named-state route normalizes matching old plank alias states as above. At schemas 1451.3 and 1451.4, `C_29689164` is registered as `EntityBlockStateFix`; in its explicit entity block-state conversions it converts the block identifier and 4-bit data to `(oldBlockId << 4) | (data & 15)`, then resolves through the same table. Its numeric block-name table assigns `minecraft:planks` ID 5, so metadata 0–5 reaches slots 80–85 on the covered paths. These are source-established registered data-fix routes, not a claim that every persisted block-state context is covered by these particular fixes.

`C_37931705` is separately registered at schema 1451.4 and is an item-stack flattening fix. Its literal `minecraft:planks.<damage>` entries map the same six indices to the same target plank keys. This confirms item damage-name mapping only; it is not evidence for block-state route applicability.

`dark_oak` spelling is explicitly present in both the old 1.12.2 variant serializer and 1.13.2 target/name mapping. No `big_oak` spelling is inferred for this block; the 1.12.2 enum’s compatibility `name` field is not used as its serialized key (`getSerializedName` returns `key`, set to `dark_oak`).

## Disposition and limits

The exact six-way ID-5 state join is source-confirmed for the registered table and the registered routes described above. This does not close other block IDs, other block/state migrations, complete movement query intersections, provider geometry, or either version-pair’s overall source coverage. No additional audit correction/addendum is known for this packet; independent acceptance should bind this report’s exact bytes.
