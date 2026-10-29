# Independent review: legacy climbable collision providers

## Verdict: REVISE

The source identities, supplied digests, old registry IDs, and the detailed provider analysis are supported by the canonical 1.8.9 and 26.2 files. One summary sentence makes a material false statement and contradicts the report's later detailed section, so this revision must be corrected before the finding is accepted as written.

## Required correction

In `finding.md` under **Scope and result**, the sentence “In 26.2, both registered blocks set `noCollision()`, so their collision-shape result is empty independent of their separately supplied visible shape” is false for Ladder. `Blocks.LADDER` registers `LadderBlock` with `forceSolidOff()`, strength, sound, `noOcclusion()`, and push reaction, but does not call `noCollision()` (`Blocks.java:1442–1446`). `BlockBehaviour.Properties` initializes `hasCollision` to `true` (`BlockBehaviour.java:982–986`), and the base collision-shape method uses the state shape when `hasCollision` is true (`BlockBehaviour.java:327–329`). `LadderBlock` supplies a facing-indexed shape (`LadderBlock.java:41–44`).

Only `Blocks.VINE` explicitly calls `noCollision()` (`Blocks.java:2429–2441`), which makes its collision provider empty even though `VineBlock.getShape` separately supplies a visible shape (`VineBlock.java:54–72`). Replace the summary sentence with a per-block statement: current Ladder retains collision and uses its facing shape; current Vine is collision-disabled while retaining a separate visible-shape calculation.

## Independently verified claims

- Old `Block.java` registers ID 65 as `ladder` with `LadderBlock` (`Block.java:965`) and ID 106 as `vine` with `VineBlock` (`Block.java:1046`). Current `BlockItemIds` creates the same `ladder` and `vine` keys (`BlockItemIds.java:316, 373`); current `Blocks` registers those keys with their respective classes (`Blocks.java:1442–1446, 2429–2441`). This supports the two exact key aliases, not broader historical equivalence.
- Old Ladder's state has horizontal `FACING`; its collision method updates bounds and returns the superclass AABB (`LadderBlock.java:17–28, 38–56`). The selected bounds have `0.125F` thickness and cover the remaining axes. Old `Block.addCollisions` ignores a null result (`Block.java:339–344`), while the base collision provider constructs a world-positioned box (`Block.java:346–349`).
- Old Vine's state properties and defaults are as described (`VineBlock.java:24–35`). Its `getCollisionShape` returns `null` (`VineBlock.java:126–129`) despite the separate bounds update (`VineBlock.java:65–124`), so generic collision-list insertion excludes it. Its `UP` state is derived from the block above (`VineBlock.java:39–42`).
- Old `Block.rayTrace` independently calls `updateShape` before intersecting mutable bounds (`Block.java:456–465`). The report properly avoids inferring which shape API DDA uses or claiming visitation equivalence.
- The current Vine visible-shape fallback to a full block when no horizontal property contributes is present (`VineBlock.java:54–66`); its collision gate remains independently disabled by the registration property.

## Hash verification

Recomputed SHA-256 for each of the eight canonical source paths in `sources.sha256.json`; all eight match exactly. `sources.sha256` also lists the same eight paths and digests. Review target identities:

- `finding.md` SHA-256: `830e6df6e5356992e639a062382700a136e29ba4ccec7c290efd7538a05de43b`
- `sources.sha256.json` SHA-256: `1e69d4f9f252a1f2756d992b4eba2e30583321d3b9a71043b8fec213e5e0ed65`

This review covers only the bounded block identity, collision-provider, and shape-path claims above. It makes no DDA traversal or runtime claim.
