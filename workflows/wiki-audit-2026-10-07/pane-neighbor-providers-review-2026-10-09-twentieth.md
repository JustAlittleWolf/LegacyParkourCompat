# Independent review: pane neighbor-provider slice 20

Verdict: **ACCEPT**

## Exact input and boundary

Reviewed only `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-twentieth.md` from source snapshot `8f97dda72e0775c1e39990b087433b2e552d5564`. Memo SHA-256: `91e4e82784cb6862cb8f40b71130b4e9302600b6600d899471fb1e2877080520`.

This verdict covers `bed` (26), `golden_rail` (27), and `detector_rail` (28), and the one-east-neighbor mask result only.

## Review evidence

Both ready markers say `ready`, the manifest identities agree with the memo, and all source files cited in the memo match their manifest entries and rehashed bytes.

`BedBlock` defaults to `PART=FOOT`, `OCCUPIED=false` and explicitly returns false from both the old cube and solid-render predicates and the new state-taking counterparts. `PoweredRailBlock` and `DetectorRailBlock` default to the stated north-south, unpowered states. Their `AbstractRailBlock` parent returns false from both relevant predicates in both versions. The pane resolver checks each cardinal neighbor using cached old opacity in 1.8.9 and the neighbor block's default-state cube predicate in 1.9.4. Thus none of these providers connects through the predicate for an east-only neighbor in either version.

## Limits and checkpoint state

The source supports no changed mask for these three registrations. The memo explicitly leaves other masks and the remaining provider census open, and reports no runtime validation.

The ready verification confirms identical source trees and raw inputs. Original derived JARs are unavailable, leaving equivalence to regenerated derived JARs unverified; this review preserves that limitation.

Before report checkpoint: phase `review slice 20`; input snapshot `8f97dda72e0775c1e39990b087433b2e552d5564`; HEAD `a7c0082fe0841e9baf294d4334027e7f194e7400`; dirty files `none`; owned processes `none`; next action `commit this report, then review slice 21`.

