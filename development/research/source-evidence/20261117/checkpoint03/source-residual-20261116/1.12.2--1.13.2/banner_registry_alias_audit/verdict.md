# Independent audit: banner registry alias

**Verdict: CLOSED for the scoped state-migration alias claim.** The report's evidence identities match the canonical source tree, and the traced 1.13.2 routine confirms that legacy standing-banner states for rotations 0–15 normalize to `minecraft:white_banner`; legacy wall-banner states for north, south, west, and east normalize to `minecraft:white_wall_banner`.

## Evidence identity

- Audited report SHA-256: `37703c90a3ace2540858e856d79d34e4225ae632f8febdc5af28a507f00d7451` (matches assigned identity).
- 1.12.2 `ornithe-feather.sources.sha256`: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; matches ready marker and the cited `Block.java` manifest entry/hash.
- 1.13.2 `ornithe-feather.sources.sha256`: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; matches ready marker and all cited file entries/hashes (`C_80754635.java`, `C_15874933.java`, `C_37931705.java`, `Block.java`, `Blocks.java`).
- Exact mapping family and versions in the ready markers are `ornithe-feather` for 1.12.2 and 1.13.2.

## Audit finding

In 1.13.2 `C_80754635.m_11856336` assigns the new representation to the indexed canonical slot, then parses each legacy representation and indexes it to the same slot. `m_11299400` looks up the legacy state and returns that canonical slot's representation when recognized. The table entries at lines 7391–7410 therefore establish precisely the two old-key to white-key mappings and preserve their listed rotation/facing properties. `C_15874933.makeRule` invokes this function as a `BLOCK_STATE` remainder update, so this is a block-state data migration/normalization rule.

This differs from `C_37931705`'s item ID fixer, which maps `minecraft:banner.<metadata>` item identifiers to dye-specific banner item IDs. The item conversion does not establish a block registry-key alias. The 1.12.2 registration evidence shows only `standing_banner` and `wall_banner`; the 1.13.2 registrations show color-specific block keys, but the audited state migration entries map those two legacy keys specifically to the white variants. No broader claim about legacy aliases for every dye key is supported or needed here.

## Remaining limits

This verdict audits only the pinned banner migration alias dependency. It does not establish gameplay movement effects, banner-content/item migration parity, aliases outside the inspected entries, or complete source-pair coverage/freeze.
