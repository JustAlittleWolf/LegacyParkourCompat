# Source alias review: full-cube collision overlap predicate

## Verdict

**ACCEPT** the old-operation alias for exact 1.15, 1.15.1 and 1.15.2 Mojmap sources. `CollisionGetter.getBlockCollisions` and its relevant `Shapes`/`IndirectMerger` dependencies are byte-identical in all three releases. The old query-shape plus Boolean AND path is therefore unchanged across these sampled releases; there is no 1.15.0 or 1.15.1 full-cube raw-AABB shortcut.

Exact 1.14.4 Mojmap source is an additional lower preservation fence for the geometry operation: its `LevelReader.getBlockCollisions` also constructs the query with `Shapes.create(aABB)` and accepts each translated block shape with `Shapes.joinIsNotEmpty(..., BooleanOp.AND)`. The key `Shapes.create`, `findBits`, `createIndexMerger`, `IndirectMerger`, and `BooleanOp` bodies are identical to 1.15.2. `Shapes.joinIsNotEmpty` has one unrelated decompiler-visible exception-wrapper change; with `BooleanOp.AND`, its `apply(false, false)` is false, so that branch is not reached. This fence establishes exact 1.14.4 behavior only. It does not establish every intervening 1.14 patch or any earlier line.

The compared 1.16.5 `CollisionSpliterator` is the source side containing the canonical-full-block raw `AABB.intersects` fast path. The already accepted finding establishes that the same class is byte-identical at exact 1.16. The review here confirms earlier source aliases and does not reopen the movement witness or claim a finer 1.16 patch boundary.

## Exact source identities

Canonical source root: `D:\\Javastuff\\LegacyParkourCompat\\decompiled_minecraft`. All listed sources are the `mojmap` namespace, exact release directories, decompiled with Vineflower 1.12.0. Direct SHA-256 values below were checked against each release's `mojmap.sources.sha256` entry.

Ready-marker source-manifest SHA-256s:

| Release | Exact source identity | Source-manifest SHA-256 | Client SHA-256 |
|---|---|---|---|
| 1.14.4 | `mojmap.ready.json`, mapping `mojmap`, version metadata `1.14.4` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a` |
| 1.15 | `mojmap.ready.json`, mapping `mojmap`, version metadata `1.15` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b` |
| 1.15.1 | `mojmap.ready.json`, mapping `mojmap`, version metadata `1.15.1` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358` |
| 1.15.2 | `mojmap.ready.json`, mapping `mojmap`, version metadata `1.15.2` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` |

Exact method-file hashes (the 1.15, 1.15.1 and 1.15.2 values are equal in each row):

| Exact source file | SHA-256 |
|---|---|
| 1.14.4 `net/minecraft/world/level/LevelReader.java` | `1f86a029e4404b91aa532ca33ab0dbb63b085334c561ce44e0d0875aa50fdbc9` |
| 1.14.4 `net/minecraft/world/phys/shapes/Shapes.java` | `f1a2e60ae358236de7379e66543f99d474e3d431e42c5afbda688cb99b70985d` |
| 1.14.4 `net/minecraft/world/phys/shapes/IndirectMerger.java` | `c96d6dfe5cc6f2414cf947df77d229e060c6d23c8f5ffbda85657c70d7acd896` |
| 1.14.4 `net/minecraft/world/phys/shapes/BooleanOp.java` | `aa1585e4de46313fad4755e4c75571b865e35d6cc7ca2c091c2f9d905ebbcc58` |
| 1.15 / 1.15.1 / 1.15.2 `net/minecraft/world/level/CollisionGetter.java` | `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd` |
| 1.15 / 1.15.1 / 1.15.2 `net/minecraft/world/phys/shapes/Shapes.java` | `81bf70d2c8a1d1336df72beedb30b0c9504f14decc9a06814a794aabf38203c2` |
| 1.15 / 1.15.1 / 1.15.2 `net/minecraft/world/phys/shapes/IndirectMerger.java` | `c96d6dfe5cc6f2414cf947df77d229e060c6d23c8f5ffbda85657c70d7acd896` |
| 1.15 / 1.15.1 / 1.15.2 `net/minecraft/world/phys/shapes/BooleanOp.java` | `aa1585e4de46313fad4755e4c75571b865e35d6cc7ca2c091c2f9d905ebbcc58` |
| 1.16.5 `net/minecraft/world/level/CollisionSpliterator.java` | `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297` |

The 1.15 source files were checked byte-for-byte against 1.15.1 and 1.15.2, in addition to the equal hashes above. The 1.14.4 `Shapes.create(AABB)`, `findBits`, and `createIndexMerger` method bodies are text-identical to 1.15.2. Its `IndirectMerger.java` and `BooleanOp.java` are also byte-identical. `joinIsNotEmpty` differs only at its first `IllegalArgumentException` throw: 1.14.4 throws directly; 1.15.2 wraps it with `Util.pauseInIde`. The AND operation does not enter that guard.

## Paired operation and movement implication

At 1.15, 1.15.1 and 1.15.2, the collector constructs one `Shapes.create(aABB)` query shape and checks each translated candidate with `Shapes.joinIsNotEmpty(query, translatedShape, BooleanOp.AND)` (`CollisionGetter.java:67–120`). `Shapes.create` calls `findBits` on each endpoint pair. If all three axes fit the accepted 1/2/4/8 subdivision, endpoints are rounded into a discrete voxel shape; otherwise the method keeps exact AABB endpoints in an `ArrayVoxelShape`. `createIndexMerger` selects `IndirectMerger` for unequal overlapping coordinate lists; its ordered merge and duplicate-coordinate handling retain the `1.0E-7` comparisons. `BooleanOp.AND` returns true only when both inputs are true. These operations, including casts, math expressions and gates, are byte-identical over the three 1.15 sources.

At 1.14.4, `LevelReader.getBlockCollisions` uses the same query-shape construction and AND join (`LevelReader.java:134–178`). The chunk access call and receiver names differ from 1.15, but the full-cube candidate is tested through the same shape predicate. The exact method-body comparison shows the query geometry and tolerance helpers are preserved at this sampled lower endpoint.

Consequently, the accepted sub-grid-overlap witness remains the correct contrast: old 1.15.x query construction can round a tiny positive face overlap to tangency before the AND join; the 1.16 full-block branch tests the raw query AABB strictly. This source alias review supports applying the old-side behavior to exact profiles 1.15, 1.15.1 and 1.15.2, and confirms 1.14.4 as an additional lower preservation fence. It does not assert a continuous version interval, provider frequency, or runtime test result.

## Immutable input bindings

The alias conclusion is scoped to the accepted finding SHA-256 `bed148428161689abe86b2069bd04320d376768623b5a8f587ca1f7d43172f03` and its prior independent review SHA-256 `369b34818337972f33a93518e577f8160b913a49f8b6e02f3cb1c21f73fa4024`. The collision-collection residual used as context has SHA-256 `3a67f32fdc97d048cd31a1ee735ff5668f486c15d2c44583dc1605cc6bd3eb7c`. This report is an alias/source identity audit only; it adds no movement claim and does not change the bound finding revision.
