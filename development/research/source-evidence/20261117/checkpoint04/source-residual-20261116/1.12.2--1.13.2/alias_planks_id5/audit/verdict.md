# Independent source audit: old planks ID 5 alias/state join

Verdict: ACCEPT, narrowly bounded to the exact six-way ID-5 state join and registered routes in the audited author packet. This does not imply general migration behavior, provider geometry, movement-query closure, or pair completion.

Audited author packet: `build/source-residual-20261116/1.12.2--1.13.2/alias_planks_id5/coverage.md` SHA-256 `d0f7bddab38e0ec257d2ab24a4707783b347458b70365bcbf9dcef90f4335c9e` (verified against the bytes audited).

Pinned source identities independently verified against manifest file hashes:

- 1.12.2 manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2 manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- `1.12.2/ornithe-feather/net/minecraft/block/PlanksBlock.java` SHA-256 `f190065f853a6aa9ad64e760607f057752bdeb4fe3ac724116174a23eddae29e`.
- `1.13.2/ornithe-feather/net/minecraft/unmapped/C_80754635.java` SHA-256 `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e`.
- `1.13.2/ornithe-feather/net/minecraft/unmapped/C_29689164.java` SHA-256 `5f327c8aaf217f59975635e382860f92fbc7a40202d8e61200eebc8779c6bf1c`.
- `1.13.2/ornithe-feather/net/minecraft/unmapped/C_15874933.java` SHA-256 `8d62c370f468ca9534a4db65338dacc69a0f416079d4c499cc8d0e9250c3ae71`.
- `1.13.2/ornithe-feather/net/minecraft/unmapped/C_37931705.java` SHA-256 `2717df7465597f98634ff1569968ff247a4ccda472209e2628ae4ab21c7364de`.
- `1.13.2/ornithe-feather/net/minecraft/util/datafix/DataFixes.java` SHA-256 `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8`.

Verified facts: the old enum assigns IDs 0–5 to oak, spruce, birch, jungle, acacia, and dark_oak; metadata decoding selects by ID and metadata encoding returns that ID. The serialized key for the last enum is `dark_oak` even though a compatibility name field is `big_oak`. The 1.13.2 state table registers encoded IDs 80–85 to the six corresponding `<wood>_planks` targets, with old alias states keyed by `minecraft:planks` and `Properties.variant` values in the same order. The table's registered dynamic-key lookup maps the exact old alias state to the same integer slot, and the numeric accessor returns that target state.

`DataFixes` registers `C_15874933` at schema 1450; its rule updates `TypeReferences.BLOCK_STATE` remainder through the table lookup. The same registry adds `C_29689164` at schema 1451.3; its covered entity block-state conversion obtains block ID 5 for `minecraft:planks`, masks data to four bits, combines `(id << 4) | data`, then resolves through the same table. Thus data 0–5 addresses IDs 80–85 on that explicitly covered route. This is not a claim that every persisted block-state context uses these routes.

The separately registered schema 1451.4 `C_37931705` is an item-stack damage/name flattening fixer. Its six literal `minecraft:planks.0`–`.5` entries agree with the target names, but this audit treats that as item-stack-only corroboration, not block-state applicability.

No correction/addendum was required. Reused prior provider-only evidence solely as scoped context; no geometry claim was audited here.