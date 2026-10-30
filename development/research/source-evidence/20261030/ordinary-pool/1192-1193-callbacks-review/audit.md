# Bounded callback source audit: 1.19.2 → 1.19.3

## Verdict

Supported within the assigned callback slice. The six `Entity.checkInsideBlocks` inset constants are the only body change in that method; after substitution the ordered scan and gates match. The callback override file set is identical (23 files per version), and the report's directly movement-relevant callback families and stated gates agree with the exact sources. The ordinary-movement cobweb witness is arithmetically consistent and source-reachable under its stated conditions.

## Evidence checked

- Canonical 1.19.2 / 1.19.3 Mojmap source manifests hash to `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` / `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; version IDs and mappings in the ready metadata match. All 18 paired source file hashes listed in the inventory match both the files and manifest rows (manifest rows carry the expected leading `*`).
- `checkInsideBlocks` changes exactly six min/max inset literals from `0.001` to `1.0E-7`; `hasChunksAt`, inclusive x/y/z iteration, state callback, `onInsideBlock`, and crash wrapping retain order. `tryCheckInsideBlocks` calls it without a side check; `Entity.move` calls the wrapper only on the normal (`noPhysics == false`) branch after movement and before later movement bookkeeping.
- For the min-x example, `10.2995 - (float)0.6 / 2` is about `9.999499988`; adding the old inset floors to 10 and the new inset floors to 9. The `-0.002` collision-free SELF displacement from `10.3015` reaches that endpoint. Cobweb is registered non-colliding, and `WebBlock.entityInside` calls `makeStuckInBlock`; its multiplier is consumed and cleared on the next normal `move`.
- Source callback response checks confirm the web, berry, powder-snow, bubble-column, honey, dripleaf, and portal gates/consequences summarized in the report. The exact `onInsideBlock` override scan found only `ServerPlayer` in both versions; its server-only criterion effect is outside local-player movement.
- Output-integrity hashes match the report (`c6020140661c6d919e952fc4bb9c5582d758dde2c9b65a73987c206188aaa05d`) and inventory (`129011e7cc213e4b6e13666025386d9cb46c751a77113fa7d4f28d14a096a330`); recorded byte sizes also match.

## Limits and correction

The pair-level no-freeze decision remains open: this bounded audit does not close the full tick graph or its independent coverage audit. Exact keyboard-input/velocity provenance is also unproven, as the report already states; the endpoint witness establishes a collision-free SELF displacement, not a recorded input sequence. The ready markers and manifests are in each version directory beside `mojmap/`, not inside the stated Mojmap source root, so “co-located” should be read as adjacent at the version-directory level. No other unsupported callback claim was found in this bounded scope.
