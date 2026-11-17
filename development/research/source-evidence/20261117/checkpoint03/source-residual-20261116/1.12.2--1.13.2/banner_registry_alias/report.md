# Banner registry alias follow-up

**Verdict: CLOSED for the old registry-key alias.** In the 1.13.2 source migration table, `minecraft:standing_banner` block states normalize to `minecraft:white_banner`, and `minecraft:wall_banner` states normalize to `minecraft:white_wall_banner`. This is a unique state-level join to the white standing/wall variants, not a source-supported one-to-many alias from either old block key to all sixteen dye keys. Do not infer color-specific old block aliases from the matching provider geometry.

## Pinned evidence

The 1.12.2 `ornithe-feather.sources.sha256` manifest hashes to `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, matching `ornithe-feather.ready.json`. The 1.13.2 manifest hashes to `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, also matching its ready marker. Both are exact Feather mapped source trees. The examined file hashes below match their manifest entries.

## Source trace

- 1.12.2 `net/minecraft/block/Block.java` (SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`), lines 1159–1160, registers `standing_banner` and `wall_banner` as the two `BannerBlock` subclasses.
- 1.13.2 `net/minecraft/unmapped/C_80754635.java` (SHA-256 `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e`), lines 22–31, records each new block-state representation as the canonical indexed value and indexes the following legacy representation(s) to that same value; `m_11299400` (lines 33–41) returns that canonical representation for a recognized legacy state.
- Its state table maps all rotations 0–15 of `minecraft:standing_banner` to `minecraft:white_banner` (lines 7391–7406), and all four horizontal `minecraft:wall_banner` facings to `minecraft:white_wall_banner` (lines 7407–7410).
- 1.13.2 `net/minecraft/unmapped/C_15874933.java` (SHA-256 `8d62c370f468ca9534a4db65338dacc69a0f416079d4c499cc8d0e9250c3ae71`), line 19, applies `C_80754635.m_11299400` as a remainder update in the data-fixer pipeline, confirming these entries are normalization/migration rules.
- 1.13.2 `net/minecraft/block/Block.java` (SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`), lines 1444–1490 and 1493–1543, registers the sixteen `DyeColor` standing and wall variants. `Blocks.java` (SHA-256 `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303`), lines 1054–1085, binds their named constants to those keys.
- A distinct 1.13.2 item-ID fixer, `net/minecraft/unmapped/C_37931705.java` (SHA-256 `2717df7465597f98634ff1569968ff247a4ccda472209e2628ae4ab21c7364de`), maps legacy item IDs such as `minecraft:banner.15` through `.0` to dye-specific banner item IDs (lines 303–318). This is an item metadata conversion; it does not replace the explicit block-state alias above.

## Limit

The closed claim is the migration join for block registry/state identity: standing → white standing, wall → white wall. No claim is made that every dye-specific block key has a distinct old registry-key predecessor; the inspected old block registry has only the two undyed keys, and the 1.13.2 state migration table sends those legacy states to the white keys. Banner content/item color migration is a separate data path.
