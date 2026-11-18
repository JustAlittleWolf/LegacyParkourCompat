# Independent audit: earlier position-writer pins

Status: **COMPLETED — ACCEPT for the bounded writer claim**

## Binding

Audited immutable input: `c-external-position-final-aliases/verdict.md`, SHA-256 `6689964194F08B98E32A6941A1334983DB7B7AA1C3FACC2FD8FEEB8375CE0887`.

I independently checked only the 11 exact releases listed below: 1.8–1.8.8, 1.9.1, and 1.10.1. Source paths are under `decompiled_minecraft/<version>/ornithe-feather/`. For every row, the nested `.decompile-provenance.json` source entry for `net/minecraft/entity/Entity.java` equals the recomputed source-file SHA-256. The Feather Gen2 `mergedv2.jar` input URL and SHA-256 also match the bound verdict.

| Release | Entity.java SHA-256 | Feather mergedv2 SHA-256 | Ordinary call | Helper |
|---|---|---|---:|---:|
| 1.8 | `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9` | `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe` | 543 | 637–641 |
| 1.8.1 | `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c` | `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` | 548 | 642–646 |
| 1.8.2 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b` | 544 | 638–642 |
| 1.8.3 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2` | 544 | 638–642 |
| 1.8.4 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c` | 544 | 638–642 |
| 1.8.5 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d` | 544 | 638–642 |
| 1.8.6 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `d1507e3253a944d59dd0b924b6495deb7732ecadb5f8bd06f7139c241639ad81` | 544 | 638–642 |
| 1.8.7 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `e0cd7842acda42ae23d2e9d031358c1faac1ac01a139bb29ac8034fbaa5fa27b` | 544 | 638–642 |
| 1.8.8 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f` | 544 | 638–642 |
| 1.9.1 | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | 622 | 719–724 |
| 1.10.1 | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37` | 630 | 727–732 |

## Source audit

For every listed release, `Entity.move(double, double, double)` has a separate no-clip branch that also calls `setPositionFromShape()`. The audited ordinary path is the other branch. It resolves/mutates the shape, closes the collision/step-selection block, pops the `move` profiler section, pushes `rest`, and then reaches the listed ordinary call. The call is not guarded by a comparison of requested, clipped or resolved displacement. A zero or tiny resolved displacement therefore does not by itself skip this terminal writer, assuming the ordinary path reaches its terminal resolution point. There is no `return` or piston-specific gate before this call in the sampled `move` methods.

In 1.8–1.8.8, helper lines listed above assign, in order:

- `x = (this.getShape().minX + this.getShape().maxX) / 2.0`
- `y = this.getShape().minY`
- `z = (this.getShape().minZ + this.getShape().maxZ) / 2.0`

Each bound read is a separate `getShape()` call. In 1.9.1 and 1.10.1, the helper first assigns `Box box = this.getShape()` and then uses the same X/Y/Z formulas with `box` bounds. All are double expressions; no float/integer cast or intermediate rounding is present. The helper consumes the resulting shape bounds, not the requested/resolved movement deltas or previous position.

## Limits

This accepts only source applicability and arithmetic of the ordinary position reconstruction writer in these exact snapshots. It does not establish AABB production, collision resolution, step or piston response math, no-clip semantics, runtime parity, or a patch-introduction boundary between sampled versions. Other releases, including the separately accepted 1.13.1 binding, were not re-audited.

This audit read local sources and nested provenance only. No product code/history, wiki inventory, backlog, generated source, test/build, game/TAS/Gym/server launch or push was inspected or changed.
