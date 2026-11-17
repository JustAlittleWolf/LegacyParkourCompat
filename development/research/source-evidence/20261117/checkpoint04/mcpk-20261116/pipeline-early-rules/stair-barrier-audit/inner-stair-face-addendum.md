# Supplemental audit: inner stair face table

Verdict: **ACCEPT** for the bounded supplemental predicates.

Bound input: `build/mcpk-20261116/pipeline-early-rules/stair-barrier-source/inner-stair-face-facts.md`, SHA-256 `4a228bfc789ad8d15dd09494d73dd3e0ace8602ef1f3f526690d05334ae1dc7c` (independently recomputed; match).

Verified against the previously authenticated 1.12 `StairsBlock.java` bytes: SHA-256 `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611`; the same hash is present in 1.12 mapping-local provenance JSON SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.

The table is accurate. `getFaceShape` resolves virtual properties first, then `INNER_RIGHT` returns horizontal `SOLID` for `face == FACING` or `face == FACING.counterClockwiseY()`; `INNER_LEFT` uses `face == FACING` or `face == FACING.clockwiseY()`. The other horizontal queries return `UNDEFINED`. For vertical faces, the boolean equality at source line 143 yields `SOLID` exactly for UP with `HALF=TOP` or DOWN with `HALF=BOTTOM`; this branch precedes and is independent of stair shape. These are source-predicate facts only; no movement or collision inference is added.
