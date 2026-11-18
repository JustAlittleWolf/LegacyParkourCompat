# Independent source audit: earlier snow layer-one controls

Status: COMPLETED

## Immutable bindings

- Earlier-controls candidate: `b-source-snow-one-earlier-aliases/snow-layer-one-earlier-controls.md`, SHA-256 `f161f8106146d5ec65afe399948cb3b43e557605a4955e4a70bdaa09bcad62db`.
- Original accepted 1.12.2 → 1.13 audit: `b-audit-snow-layer-one-claim/audit.md`, SHA-256 `a1bbe6a03dd93e00e087af63195128fa57bff51b728ef186edb3cc7bb7e97ac0`.
- Source family: exact `ornithe-feather` directories in the primary read-only checkout. The parent `ornithe-feather.sources.sha256` hashes match all six supplied identities. Each ready marker reports `ready`, the exact release and `ornithe-feather`, with Feather Gen2 `build.2` mapping jars and Tiny files. In every version, `SnowLayerBlock.java`, `Entity.java`, `World.java`, `Block.java`, and `Box.java` exist and their current SHA-256 values match their source-manifest entries. No source tree was modified.

| Version | Manifest SHA-256 | SnowLayerBlock SHA-256 | Mapping build |
|---|---|---|---|
| 1.8.9 | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `d922f1f9a8d6f543959835e8b21e800ecf5cd9c7c60816b9152d873ca132a3ed` | Feather Gen2 `1.8.9+build.2` |
| 1.9.4 | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `3f60db09c66fb07439869254db883eed5da679a17e369e019f95ba97d427b2a5` | Feather Gen2 `1.9.4+build.2` |
| 1.10.2 | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `108450eb72f36a431bbbb49e59d47928de81a6d96f80b21774e57557d02f4881` | Feather Gen2 `1.10.2+build.2` |
| 1.11 | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `82c435d75bd45879fbc8c06950c8aa3fa06a2cd43740a0274eda7894d8b9fcff` | Feather Gen2 `1.11+build.2` |
| 1.11.1 | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `74bc424529548cdd37abc67cd7bffb5a1ebc7b82589c08862c5ea553da2ad172` | Feather Gen2 `1.11.1+build.2` |
| 1.11.2 | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `74bc424529548cdd37abc67cd7bffb5a1ebc7b82589c08862c5ea553da2ad172` | Feather Gen2 `1.11.2+build.2` |

## Per-release verdicts

All six requested earlier controls are **CONFIRMED** for the narrow claim: a same-block one-layer snow state has a non-null degenerate collision box at its block-bottom plane; it is collectable when the expanded movement query strictly straddles that plane; and the ordinary negative-Y clip can stop a descending entity there. A same-block layer-eight support is permitted by the placement predicate, so the plane is reachable in the candidate's witness arrangement.

- **1.8.9:** `SnowLayerBlock.getCollisionShape` uses `i = LAYERS - 1` and `i * 0.125F`; for layer one maxY is the block bottom. `canBePlaced` accepts the same block below at `LAYERS >= 7`. `World` calls `Block.addCollisions`; that method adds any non-null box whose `intersects` predicate passes. `Box.intersects` uses strict Y overlap, and `Box.intersectY` for negative movement clamps when the player/entity bottom is at or above the plane.
- **1.9.4:** `SHAPES[1]` is a positive 1/8 outline, but `getCollisionShape` rebuilds the collision box with maxY `(LAYERS - 1) * 0.125F`, zero for layer one. Same-block support accepts `LAYERS >= 7`. The state/block collision collector translates and appends intersecting collision boxes; strict `Box.intersects` retains an interior-straddled zero-height plane, and negative `intersectY` clamps to it.
- **1.10.2:** Same relevant collision calculation and same-block support threshold (`>= 7`) as 1.9.4. The ordinary collector, strict overlap predicate and negative Y clip preserve plane collection and stopping behavior.
- **1.11:** Collision maxY remains `(LAYERS - 1) * 0.125F`; same-block placement requires exactly 8 layers. `Block.addCollision` excludes only the `EMPTY_BLOCK_SHAPE` sentinel, then translates the returned box and appends it on strict intersection. The zero-height snow box is a distinct ordinary `Box`; the negative Y clip clamps at its maxY.
- **1.11.1:** Same snow geometry and exactly-eight support rule as 1.11. The entity movement query expands the current box by requested displacement, collects through the normal world/block collision path, then applies `intersectY`; strict overlap retains an interior-straddled plane.
- **1.11.2:** Same relevant geometry, support predicate, collector, strict overlap and negative Y clipping as 1.11.1.

## Cross-check of operation path

For every release, the movement body forms a collision query from the entity box expanded by requested displacement and processes returned boxes with `intersectY` (the later 1.11.1/1.11.2 method takes `MoverType`, without changing this relevant path). `Block.addCollisions`/`Block.addCollision` receives the snow collision shape; it does not discard the layer-one ordinary box. The strict vertical overlap condition accepts a degenerate box `[plane, plane]` only when query `minY < plane < maxY`, which is the candidate's stated crossing precondition. The clip's negative-limit branch checks `box.minY >= plane` and replaces the displacement with `plane - box.minY` when that is greater than the requested negative limit. Thus the box acts as a landing boundary. In the witness layout, the lower layer-eight top is `7/8` of its block, and the upper layer-one plane is the next block's base, `1/8` higher.

## Limits

This independent audit confirms the earlier exact-version controls and supports the accepted 1.12.2 → 1.13 finding for these releases. It does not prove full tick-trajectory parity or an exact introduction patch. Intermediate patches between the inspected endpoints were not inspected. No product code, wiki lanes, Git history, tests, gameplay/runtime, or source generation was inspected or changed.
