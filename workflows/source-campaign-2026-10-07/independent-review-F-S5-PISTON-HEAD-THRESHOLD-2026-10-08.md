# Independent blind review: F-S5-PISTON-HEAD-THRESHOLD

- Review date: 2026-10-08
- Snapshot commit: `58e56ee59d7cc4015d499e3e2a37ec1d45049d56`
- Snapshot path: `workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S5-PISTON-HEAD-THRESHOLD.md`
- Snapshot blob: `54def810126026ecb51f389c346b564f4b7503d3`
- Snapshot raw SHA-256: `58cd05981150b0c162ecd10ea16560e7cfbb50cdf5027cb57577cf32a4c30163`
- Decision: **ACCEPT** as a bounded source snapshot
- Pair status: **PARTIAL** (this finding decision does not establish pair coverage or completion)

## Scope and integrity

Reviewed only the immutable finding snapshot and its exact paired source/readiness material, including the named collision-query path needed to check its player applicability. Did not consult implementation, wiki material, MCPK material, run ledgers, unrelated findings, or runtime output. The supplied commit/path/blob/raw-SHA binding identifies the reviewed snapshot. The ready markers bind Mojmap source manifests for both versions: 1.15.2 source manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`, client artifact SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`; 1.16.5 source manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, client artifact SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`. Each finding-cited piston source hash matches its paired ready source manifest.

## Review result

The threshold difference is correctly parsed and evaluated. The source uses `this.extending != 1.0F - this.progress < threshold`; Java evaluates `<` before `!=`. With the same retracting source-piston state (`extending == false`, `isSourcePiston == true`) and `progress == 0.5F`, `1.0F - progress` is `0.5F`. In 1.15.2, `0.5F < 4.0F` is true, so `false != true` selects `SHORT == true`. In 1.16.5, `0.5F < 0.25F` is false, so `false != false` selects `SHORT == false`. The comparison is strict and the stated witness is away from a boundary-rounding ambiguity.

This is a reachable intermediate block-entity state: in both cited `PistonMovingBlockEntity.tick` implementations, a progress value below `1.0F` advances by `0.5F`, then stores the new progress. A normal non-matching-NOCLIP collision-shape query can observe the resulting `0.5F` state. In the retraction branch the method constructs the extended base shape and then adds the piston-head shape; `MovingPistonBlock.getCollisionShape` dispatches to this method. The paired `PistonHeadBlock` source selects short-arm versus long-arm geometry from `SHORT` for every facing direction. A/B `Entity.move` uses the level's `getBlockCollisions` stream while resolving movement (A lines 693–696, source SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; B lines 720–723, source SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`), so a player's ordinary movement collision query reaches the same block-shape dispatch. The stated exclusion for the matching NOCLIP direction is consistent with the block-entity guard; ordinary player queries outside that piston-push movement scope are included.

The snapshot correctly limits the conclusion to a paired collision-shape selection difference for the stated source-piston state. It does not claim a particular resolved player displacement, conflate this shape selector with dynamic piston push response, or treat the finding as completion of the broader S5 inventory. Its first changed release remains unknown within the endpoint interval, and runtime validation remains unperformed.

## Pair and campaign disposition

Keep the 1.15.2–1.16.5 pair **PARTIAL**. This bounded finding review does not audit the full movement call graph or remaining producer/consumer inventory and is not an implementation approval or runtime result.
