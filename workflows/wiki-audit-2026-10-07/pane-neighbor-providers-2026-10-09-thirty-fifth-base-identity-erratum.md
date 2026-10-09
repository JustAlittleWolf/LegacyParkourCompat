# Pane-provider slice 35 base-block identity erratum

Status: separate source-citation wording correction; the original memo remains byte-for-byte unchanged. This erratum addresses only the identity wording for the base block passed to `StairsBlock`.

## Exact frozen identities

- Original memo commit: `58fc0b6c8a94045d3bf9bd708908573da6fd5acf`.
- Original memo path: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirty-fifth.md`.
- Original memo Git blob: `3bf654a6f4405e4e86df4bfe1f36f555c09498f2`.
- Original memo raw SHA-256: `f663cf9e0050b7251791aabc60b1874bf988ff35ac7425a5d2b93696b1763531`.
- Exact source roots: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`.

## Correction

The original memo says that `block` is the “registered stone-brick base block” at ID 4. Correct wording: each registry initializer assigns `block` to `new Block(Material.STONE)`, sets its key to `stonebrick`, and registers that same object under registry name `cobblestone`, ID 4. `stone_stairs`, ID 67, then constructs `new StairsBlock(block.defaultState())`.

The exact 1.8.9 `Block.java` source SHA-256 is `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`; relevant lines are 830–836 (base object, key, and ID 4 registration) and 967 (ID 67 registration). The exact 1.9.4 `Block.java` source SHA-256 is `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`; relevant lines are 722–728 and 863. Both digests were recomputed from the canonical ready tree and match their source-manifest entries.

## Scope and preserved result

This wording correction does not change the registration, default stair state, predicate results, or mask result. The memo's bounded `false`/`false` pane-predicate result and no-east-arm witness remain as recorded; no predicate or mask claim is withdrawn. The original memo and its prior independent review remain untouched. This erratum is submitted for a narrow independent source-identity review only; it does not expand the memo's review scope or establish full provider/mask closure or original derived-JAR equivalence.
