# Final earlier position-writer pin audit

Status: **COMPLETED — ACCEPT, narrowly scoped**

## Verdict

The ordinary post-resolution position writer is applicable in all 11 assigned exact snapshots: 1.8, 1.8.1–1.8.8, 1.9.1, and 1.10.1. Each ordinary non-no-clip `Entity.move` path reaches `setPositionFromShape()` after the collision/step resolution block and profiler transition, with no test of requested, clipped, or resolved displacement guarding that call. Thus a zero or tiny resolved displacement still reaches this writer if ordinary resolution reaches its terminal point.

The coordinate writes preserve this order and double arithmetic:

1. X = `(minX + maxX) / 2.0`
2. Y = `minY`
3. Z = `(minZ + maxZ) / 2.0`

There is no float or integer cast and no intermediate rounding operation. In 1.8–1.8.8, X and Z expressions call `getShape()` separately for each bound; 1.9.1 and 1.10.1 first store one `Box box = this.getShape()` and then use its bounds. The formula consumes the final box only; it does not use the requested delta, resolved delta, or previous position. This confirms applicability and arithmetic only. It does not confirm the historical AABB producer or end-to-end parity.

The ordinary conclusion excludes the separate no-clip branch. No piston-specific early-return gate is present in these sampled `move(double, double, double)` ordinary paths. This does not establish every intermediate patch boundary beyond the listed exact snapshots.

## Exact source pins

Paths are relative to `decompiled_minecraft/<version>/ornithe-feather/`. Mapping artifacts are Feather Gen2 build 2 mergedv2 jars. The `Entity.java` SHA-256 was recomputed and matched the nested `.decompile-provenance.json` `sources["net/minecraft/entity/Entity.java"]` entry for every row. The listed mapping artifact hash came from the same manifest's exact versioned input URL.

| Exact release | Entity.java SHA-256 | Ordinary call | Helper lines | Feather mergedv2 SHA-256 |
|---|---|---:|---:|---|
| 1.8 | `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9` | 543 | 637–641 | `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe` |
| 1.8.1 | `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c` | 548 | 642–646 | `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` |
| 1.8.2 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | 544 | 638–642 | `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b` |
| 1.8.3 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | 544 | 638–642 | `e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2` |
| 1.8.4 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | 544 | 638–642 | `8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c` |
| 1.8.5 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | 544 | 638–642 | `18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d` |
| 1.8.6 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | 544 | 638–642 | `d1507e3253a944d59dd0b924b6495deb7732ecadb5f8bd06f7139c241639ad81` |
| 1.8.7 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | 544 | 638–642 | `e0cd7842acda42ae23d2e9d031358c1faac1ac01a139bb29ac8034fbaa5fa27b` |
| 1.8.8 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | 544 | 638–642 | `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f` |
| 1.9.1 | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | 622 | 719–724 | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` |
| 1.10.1 | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | 630 | 727–732 | `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37` |

The source root contained no root-level ready marker for these snapshots. Each namespace-level nested manifest was present, identified the matching version-specific Feather mapping artifact, and supplied the source hash that matched the recomputed `Entity.java` hash. No missing exact source pin remains within this assignment.

## Scope and exclusions

This audit covers only the assigned ordinary position reconstruction writer operation and the listed pins. It does not inspect or accept historical AABB shape/support/step geometry, collision resolution, no-clip semantics, simulation behavior outside the writer, or any new inventory. Previously covered snapshots (1.8.9; 1.9, 1.9.2–1.9.4; 1.10, 1.10.2; 1.11/1.12; and 1.13.x) were not re-audited here. The independently accepted 1.13.1 binding remains as supplied.

No product sources or history, wiki inventories, backlog, source-generation commands, tests, build, game/TAS/Gym/server launch, or push were used or changed.
