# Stable cutover correction — farmland collision shape

This correction supersedes the stable-cutover status in `audit.md` and `stable-cutover-supplement.md`. The prior conclusion that the intermediate source trees lacked provenance was incorrect: I looked for provenance at the version parent, while these trees embed `.decompile-provenance.json` inside the `ornithe-feather` source root. Keep the earlier files as immutable historical revisions; use this supplement as the renewed verdict.

## Exact intermediate evidence and byte verification

Canonical read-only roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.10\ornithe-feather`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.10.1\ornithe-feather`

Each root is the version-specific `ornithe-feather` mapping source root. Its embedded `.decompile-provenance.json` identifies the `lpc-decompile-v1` pipeline, Java 25.0.3 on Windows 11 amd64, and pinned Vineflower 1.12.0, Mapping IO 0.9.1, Tiny Remapper 0.14.1 and other tool artifacts. The `sources` object is the complete per-file source hash manifest.

- 1.10 provenance SHA-256: `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da`; 1,845 embedded source entries, all 1,845 source bytes present and matching their embedded SHA-256.
- 1.10.1 provenance SHA-256: `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`; 1,845 embedded source entries, all 1,845 source bytes present and matching their embedded SHA-256.

Relevant embedded hashes, independently recomputed from the files:

| Source | 1.10 | 1.10.1 |
| --- | --- | --- |
| `net/minecraft/block/FarmlandBlock.java` | `9df445485ba7e1603c20b5dede847fe412558148acefe1eeddc9bb3b62b14c25` | `63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f` |
| `net/minecraft/block/Block.java` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| `net/minecraft/util/math/Box.java` | `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155` | `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155` |

The 1.10.1 farmland hash also matches the already pinned 1.10.2 endpoint. `Block.java` is byte-identical across 1.10, 1.10.1 and 1.10.2, closing the inherited collision-shape dependency. In all three later trees, `Block.getCollisionShape` returns `state.getShape(world,pos)` and `addCollisions` obtains that state collision shape, translates it by block position, checks swept-box intersection, then appends it. `Entity.java` and `Box.java` movement dependencies are byte-identical across 1.10 and 1.10.1; no changed collector prerequisite affects this shape result.

## Renewed source verdict

**Accept the first stable release cutover as 1.10.1.** In 1.10, `FarmlandBlock.getShape` returns the 15/16-high `SHAPE`, but its explicit `getCollisionShape` override still returns `FULL_BLOCK_SHAPE` (lines 32–40). In 1.10.1, `getShape` still returns `SHAPE`, while the collision override has been removed (no override after `getShape`, lines 32 onward); the unchanged superclass default therefore supplies the 15/16-high shape to collision collection. In 1.10.2 the same no-override state remains. The accepted first stable cutover is consequently between 1.10 and 1.10.1, first present in 1.10.1. This does not identify a patch commit or establish an earlier prerelease boundary.

The farmland finding itself remains accepted and unchanged: 1.9.4 has the explicit full-block collision, 1.10 retains it, and 1.10.1 is the first verified stable release with the inherited 15/16 collision shape. This verdict is bound to the endpoint finding SHA in the audit and the intermediate provenance/source hashes recorded here. Other pair-coverage gates in the audit remain open.
