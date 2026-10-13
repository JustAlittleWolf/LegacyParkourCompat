# Independent review: pane neighbor-provider slice 21

Verdict: **ACCEPT**

## Exact input and boundary

Reviewed only `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-twenty-first.md` from source snapshot `8f97dda72e0775c1e39990b087433b2e552d5564`. Memo SHA-256: `fe66b5af41e6d49ec141223fe6ac43b549de21c7b12e71597fc8d42f58e298c6`.

This verdict covers `sticky_piston` (29), `web` (30), and `tallgrass` (31), and the cited opacity/predicate behavior for these registrations only.

## Review evidence

The ready markers and source/artifact manifest identities agree with the memo. Every source-file hash it cites matches the corresponding ready-manifest entry and a fresh file hash.

The sticky piston uses `PistonBaseBlock(true)` and has the same north-facing, unextended default as the base piston; its class returns false from both pane-relevant predicates. `CobwebBlock` returns false from its solid-render and cube predicates. Its registry's `setOpacity(1)` changes only `opacity`; it does not recompute the `opaqueCube` value cached during construction in 1.8.9, and the 1.9.4 default-state cube result remains false. `TallPlantBlock` defaults to `DEAD_BUSH` and inherits the explicit false predicates from `PlantBlock`. Consequently these providers do not add an east arm through the pane predicate in either version.

## Limits and checkpoint state

The distinction between `opacity` and cached pane-relevant opacity is supported by the cited `Block` constructor and setter. The memo keeps this result limited to registration predicates/defaults; the rest of the provider census and pane masks remain open. No runtime validation was performed.

The published source trees and raw input artifacts are verified identical. Original derived JAR equivalence remains unverified because the originals are unavailable. This review preserves that caveat.

Before report checkpoint: phase `review slice 21`; input snapshot `8f97dda72e0775c1e39990b087433b2e552d5564`; HEAD `02a4756e80b2ef0edd0a04caf3c63aa744142f4b`; dirty files `none`; owned processes `none`; next action `commit this report, then review slice 22`.

