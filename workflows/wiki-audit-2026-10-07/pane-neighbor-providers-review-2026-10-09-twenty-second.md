# Independent review: pane neighbor-provider slice 22

Verdict: **ACCEPT**

## Exact input and boundary

Reviewed only `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-twenty-second.md` from source snapshot `8f97dda72e0775c1e39990b087433b2e552d5564`. Memo SHA-256: `c09441a3c8c54c472c7062db32aec8b19f26a95962a8c208137c71dba7839a28`.

This verdict covers `deadbush` (32), `piston` (33), and `piston_head` (34), and their one-east-neighbor pane predicate results only.

## Review evidence

Both ready markers and the source/artifact manifest hashes match the memo. All cited source-file hashes for this memo match their exact manifest entries and freshly rehashed bytes.

The registrations resolve to `DeadBushBlock`, `PistonBaseBlock(false)`, and `PistonHeadBlock`. The dead bush inherits explicit false solid-render and cube predicates from `PlantBlock`. Piston defaults are north-facing and unextended; piston-head defaults are north-facing, default type, and not short. The cited piston and piston-head implementations return false from both pane-relevant predicates in each version. The 1.8.9 pane reads cached opacity; 1.9.4 tests the default state's cube result. No provider adds the east arm in either version.

## Limits and checkpoint state

This accepts only the three provider dispositions and the stated directional example. The memo leaves full registry and reachable-mask coverage open and reports no runtime validation.

The ready verification reports identical source trees and raw inputs. Original derived JARs remain unavailable, so equivalence of regenerated derived JARs remains unverified and is not treated as metadata-only.

Before report checkpoint: phase `review slice 22`; input snapshot `8f97dda72e0775c1e39990b087433b2e552d5564`; HEAD `6fbd43558fe0ffdf24cb4198fa04c2bff5e0b948`; dirty files `none`; owned processes `none`; next action `commit this report, then review slice 23`.

