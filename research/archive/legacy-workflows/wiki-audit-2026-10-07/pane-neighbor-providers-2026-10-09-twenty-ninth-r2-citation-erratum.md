# Pane neighbor-provider slice 29 — citation erratum r2

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-twenty-ninth-r2-citation-erratum`

Status: citation-only correction to the independent review request. Independent re-review pending. This erratum preserves the original memo, its rejection report, and all predicate evidence unchanged.

## Immutable input and prior disposition

- Frozen candidate tree: `8f97dda72e0775c1e39990b087433b2e552d5564`.
- Original memo: `pane-neighbor-providers-2026-10-09-twenty-ninth.md`; Git blob `370ae332ca25c798c13e3c6d9246df04fb22a6c7`; raw SHA-256 `342d7a41b5940c1a695cfca185b1237e6562dcc6dcc159069b17eecb29e1f482`.
- Preserved REQUEST CHANGES report: `reviews/independent-review-pane-neighbor-providers-twenty-ninth-2026-10-09.md`; Git blob `1a576d038d94b7535eaa171553e78ec79d3efd7b`; review commit `6431e599a9c79f30744cb32760d4f6870f2e0e3e`.

## Corrected registration citations

The original memo classifies oak stairs, chest, and redstone wire (IDs 53–55), but its two `Block.java` citations point to IDs 56–58. In the exact 1.8.9 ready source, `net/minecraft/block/Block.java` lines 941–943 register IDs 53–55; file SHA-256 `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`, matching its source-manifest row. In the exact 1.9.4 ready source, lines 835–837 register the same IDs; file SHA-256 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`, also matching its source-manifest row.

Use 1.8.9 lines 941–943 and 1.9.4 lines 835–837 for those registrations. The original citations, 944–955 and 838–849, instead cover diamond ore, diamond block, and crafting table (IDs 56–58). Only the registration line-range bindings are corrected here. The provider predicate and one-east-neighbor findings are not changed, repeated, or extended by this erratum.

This erratum does not replace or revise the original memo. Its citation correction requires independent review before the slice can be treated as accepted. The original derived-JAR-equivalence limitation remains unchanged.
