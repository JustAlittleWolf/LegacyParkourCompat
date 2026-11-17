# Independent source audit: ID 17 log alias/state join

Verdict: **ACCEPT, bounded to the stated source claim and DataFixer routes.**

Audited input: `build/source-residual-20261116/1.12.2--1.13.2/alias_log_id17/coverage.md`, SHA-256 `28136f863941759fd1584a6311c2e28fe62c38491b4485459f643dcc9827b091`.

## Identity and source integrity

The exact 1.12.2 and 1.13.2 Feather Gen2 b2 ready markers identify releases `1.12.2` and `1.13.2`. Their `sourceManifestSha256` values are `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; each equals the hash of its on-disk `.sources.sha256` sidecar. The relevant source files match those sidecars and the report's hashes:

- 1.12.2 `net/minecraft/block/LogBlock.java`: `858f2d75883cb1d1b13ffd2b0f88d2d4b6ac9e244a69c8065b2ee9f2041344ea`.
- 1.13.2 `net/minecraft/unmapped/C_80754635.java`: `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e`.
- 1.13.2 `C_29689164.java`: `5f327c8aaf217f59975635e382860f92fbc7a40202d8e61200eebc8779c6bf1c`.
- 1.13.2 `C_15874933.java`: `8d62c370f468ca9534a4db65338dacc69a0f416079d4c499cc8d0e9250c3ae71`.
- 1.13.2 `net/minecraft/util/datafix/DataFixes.java`: `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8`.
- 1.13.2 `C_37931705.java`: `2717df7465597f98634ff1569968ff247a4ccda472209e2628ae4ab21c7364de`.

## Audited claim

In `LogBlock.getStateFromMetadata` (1.12.2 lines 62–76), the species is `metadata & 3`; `metadata & 12` selects Y for 0, X for 4, Z for 8, and `LogAxis.NONE` otherwise. The corresponding metadata writer emits axis bits 0/4/8/12. `VARIANT` accepts only IDs below four (lines 17–23). This verifies the four species and all 16 metadata values, including the four NONE-axis values.

In 1.13.2 `C_80754635`, `m_11856336` (lines 22–30) parses each row into a canonical state and registers each supplied legacy Dynamic key at that row's integer index. `m_46884624` (lines 71–78) retrieves the indexed state. Static table entries 272–287 (lines 204–219) are the full `17 * 16 + metadata` range. Rows 272–283 map oak/spruce/birch/jungle to `_log[axis=y|x|z]` in the claimed order; rows 284–287 map `axis:none` keys to matching `_bark` states without an `axis` property. No state in this range is omitted, and NONE does not map to `_wood`.

`C_29689164`'s legacy block-name map contains `minecraft:log` → 17 (line 39; lookup helper `m_91167426`, lines 282–285). Its entity block-state fix is registered in `DataFixes` on schema 1451 version 3 (lines 235–236); the falling-block conversion forms `(id << 4) | (Data & 15)` in `m_35696925` (lines 321–336), and selected entity fields use the same low-four-bit index in `m_42094563` (lines 339–349). These establish the described numeric ID/data route for those entity fixes.

Separately, `C_15874933` applies `C_80754635.m_11299400` to `TypeReferences.BLOCK_STATE` (lines 14–20) and is registered at schema 1450 (`DataFixes.java` lines 227–228). `C_37931705` is registered at schema 1451 version 4 (`DataFixes.java` lines 238–240); its `minecraft:log.0`–`.3` rows (source lines 40–43) flatten species names only and do not carry axis state. Those registrations support the bounded route descriptions, not universal third-party importer behavior.

## Citation correction to retain

The source report's `C_29689164` citation “lines 20–24 and 311–314” does not point to the claimed `minecraft:log` ID or the conversion methods. Correct anchors are: map entry line 39; helper lines 282–285; falling-block path lines 321–336; entity fields lines 339–349; registration lines 235–236 of `DataFixes.java`. The underlying claim is confirmed from the exact hashed source; this is a citation correction, not a technical reversal. No separate correction addendum was present during this audit.

## Limits

Acceptance covers only the ID-17 metadata alias/state join and the stated registered DataFixer/entity conversion routes. It does not establish arbitrary importer behavior, full migration coverage, movement behavior, provider geometry, or pair completion. The cited planks/log collision-provider report was not re-audited; its geometry remains outside this assignment.