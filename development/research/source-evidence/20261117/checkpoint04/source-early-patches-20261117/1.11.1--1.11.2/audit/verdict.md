# Independent bounded source audit — 1.11.1 → 1.11.2

Verdict: **ACCEPT** for the bounded WallBlock collision-height finding only. This verdict does not freeze the full pair and does not authorize implementation or runtime claims.

## Bound inputs

- Coverage report: `build/source-early-patches-20261117/1.11.1--1.11.2/coverage.md`, SHA-256 `262A9753D5CEB9C983F158FF0C9239A3F7854DA20157204769BAE41EC3301EFE`.
- Candidate: `build/source-early-patches-20261117/1.11.1--1.11.2/findings/wall-collision-height.md`, SHA-256 `0485037E506B5681FE0D92D9006738DD1ADD36AB89154FC6E1C9B1BDEA992718`.
- 1.11.1 ready marker `ornithe-feather.ready.json`, SHA-256 `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf`; source manifest `ornithe-feather.sources.sha256`, SHA-256 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`.
- 1.11.2 ready marker `ornithe-feather.ready.json`, SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; source manifest `ornithe-feather.sources.sha256`, SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`.
- Each source manifest has 1,921 entries; all 3,842 listed files exist and match their row hashes. The path sets are identical (zero additions/removals), with 21 changed shared paths. The `WallBlock`, `World`, `Entity`, and `PlayerEntity` hashes match those listed in the coverage report.

## Verified claim

In 1.11.1, `WallBlock.addCollisions` (92–97) resolves virtual connection properties unless `forceShape` is true, then appends `SHAPES[getShapeIndex(state)]`. In 1.11.2 the same method and gates append `COLLISION_SHAPES[getShapeIndex(state)]`. `COLLISION_SHAPES` is built from each corresponding `SHAPES` entry with `withMaxY(1.5)`. The connection resolver, state-derived index, and `getCollisionShape` selection are otherwise unchanged. Both exact `WallBlock.java` hashes match the report: older `336d4fe9a541bb6a371b81236893d7e80f004c9a1fd4181aa3345acd47b47a41`; newer `17da6d881365e4dce5134f88cb9a9e5273d1fa1734812d13410dc6924a3acbfd`.

Both versions' `World.getCollisions(Entity, Box)` pass the queried world state through `BlockState.addCollisions` with `forceShape=false`; `BlockState` delegates to the block callback. `Entity.move` calls that query and applies returned boxes in its ordered clipping path. The local living-player movement path invokes `move(MoverType.SELF, ...)`. The compared `World.java` files are byte-identical, and the `Entity.java` movement-path diff is identifier-only; its other hunks affect sound/particle handling. `PlayerEntity.java` only changes the written DataVersion value. Thus the report's bounded, reachable collision consequence is supported: a wall whose old outline collision ends at or below Y=1 can block horizontal movement in the 1.11.2 query when the player body overlaps the newly included region above Y=1.

Precision note for future pair-coverage reconciliation: the 1.11.1 `SHAPES` array has two entries with `maxY=0.875` (indices 5 and 10); the array's maximum is 1.0, and the 1.11.2 collision array raises every entry to 1.5. The candidate's aggregate-height statement and movement conclusion remain true, but the coverage report's wording that all outline boxes have `maxY=1.0` is too broad. This does not invalidate the bounded finding, which applies to the supported state shapes and has an explicit upper-band witness, but the coverage wording should be corrected before full-pair freeze.

## Limits

This audit accepts only the WallBlock operation and its stated player collision path. It does not independently accept the report's dispositions for every other changed path, complete the entire tick/provider audit, establish a full-pair freeze, measure trajectories, or assess implementation/runtime parity.
