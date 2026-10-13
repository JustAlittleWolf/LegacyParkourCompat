# Independent review: pane neighbor-provider slice 17

Verdict: **ACCEPT**

## Exact input and boundary

Reviewed only `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-seventeenth.md` from source snapshot `8f97dda72e0775c1e39990b087433b2e552d5564`. Memo SHA-256: `cc266e2058e0bb5ce02f77ebf514a7152706e88007a1ef3fd4b756260b30a951`.

This verdict covers the memo's three providers: `coal_ore` (16), `log` (17), and `sponge` (19), and its one-east-neighbor pane mask result. It does not accept a complete provider census or complete reachable-mask inventory.

## Review evidence

Both exact ready markers say `ready`; the 1.8.9 and 1.9.4 source-manifest and artifact-manifest hashes match the values recorded in the memo. Each cited source file for this slice was matched to its manifest entry and rehashed from the named Feather ready tree; all matched.

The pane resolver reads north, south, west, and east neighbors. Its connection test is `Block.isOpaque()` in 1.8.9, with the pane/glass allowlist, and `block.defaultState().isCube()` in 1.9.4, with the corresponding allowlist. In 1.8.9, `isOpaque()` returns the constructor-cached `opaqueCube`, initialized from virtual `isSolidRender()`. In 1.9.4, the default state delegates `isCube()` to the block predicate.

The cited registrations and class paths support the reported true/true results: `OreBlock` uses stone material and has no predicate override; `LogBlock` defaults to oak/Y and inherits the base predicates through `AbstractLogBlock` and `AxisBlock`; `SpongeBlock` defaults `WET=false` and inherits the base predicates. Each provider therefore contributes the east arm in the memo's one-east-neighbor example in both versions. No changed mask is supported or claimed.

## Limits and checkpoint state

The memo correctly limits itself to registration-level predicate outcomes and defaults. A one-neighbor example does not establish every combination of pane-neighbor masks. The shared provider census and world-reachable mask set remain open, and no runtime validation was performed.

The verification records show identical source trees and raw input artifacts. Original derived JARs remain unavailable, so equivalence of regenerated derived JARs to the original derived JARs remains unverified; this review does not infer metadata-only differences.

Before report checkpoint: phase `review slice 17`; input `8f97dda72e0775c1e39990b087433b2e552d5564`; HEAD `8f97dda72e0775c1e39990b087433b2e552d5564`; dirty files `none`; owned processes `none`; next action `commit this report, then review slice 18`.

