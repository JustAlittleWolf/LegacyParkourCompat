# Independent audit: layer-one snow Y clip

Status: COMPLETED

## Scope

Cold audit of `b-source-snow-one-historical-patches/snow-one-historical-patches.md` (input SHA-256 `2ac7d724831cce66920fe053a1cd30ba9b3e5b4a04cd0f124d40ead070166a0e`). Exact sources inspected read-only: 1.8–1.8.8, 1.9–1.9.3, 1.10 and 1.10.1, all `ornithe-feather`. Reviewed provenance pins, five consulted member hashes per release, layer-one collision geometry, legal placement support, strict collision collection, and ordinary negative-Y clipping. No product code, source history, wiki, backlog, other lane, tests or runtime were inspected. Additional exact controls 1.8.9, 1.9.4, 1.10.2 and 1.11.x were outside this audit and are not included in these verdicts.

## Verdicts

| Release | Verdict | Exact witness |
|---|---|---|
| 1.8 | REQUEST CHANGES | The report correctly rejects layer 1 over same-block layer 8: `canBePlaced` requires lower `LAYERS == 7`. It omits two actually legal supports: lower same-block layer 7, and a full solid, movement-blocking block (except the separately rejected ice/packed ice cases). Either permits layer 1 in the cell above. Its zero-height collision box lies at the upper cell's base plane and clips descending Y when the expanded movement query strictly straddles that plane. Thus the scoped layer-eight verdict is correct, but the old degenerate-Y clip has a legal 1.8 witness. Add this qualification. |
| 1.8.1 | ACCEPT | Placement changes to `>= 7`; layer 8 and the layer-7/solid alternatives are legal. Layer 1 collision max-Y is zero; strict query intersection admits it only for a straddling query; ordinary negative-Y `intersectY` clamps to that plane. |
| 1.8.2 | ACCEPT | Same as 1.8.1; consulted snow and collision math remain hash-identical to 1.8.1. |
| 1.8.3 | ACCEPT | Same legal support predicate and zero-height layer-one collision; report's consulted-member hashes match the embedded manifests. |
| 1.8.4 | ACCEPT | Same legal support predicate and zero-height layer-one collision; report's consulted-member hashes match the embedded manifests. |
| 1.8.5 | ACCEPT | Same legal support predicate and zero-height layer-one collision; report's consulted-member hashes match the embedded manifests. |
| 1.8.6 | ACCEPT | Same legal support predicate and zero-height layer-one collision; report's consulted-member hashes match the embedded manifests. |
| 1.8.7 | ACCEPT | Same legal support predicate and zero-height layer-one collision; report's consulted-member hashes match the embedded manifests. |
| 1.8.8 | ACCEPT | Same legal support predicate and zero-height layer-one collision; report's consulted-member hashes match the embedded manifests. |
| 1.9 | ACCEPT | `>= 7` placement and zero-height collision max-Y are present. The positive layer-one outline shape is distinct and does not replace collision geometry. |
| 1.9.1 | ACCEPT | Same relevant placement and collision behavior as 1.9; all five source-member hashes match the report and embedded manifest. |
| 1.9.2 | ACCEPT | Same relevant placement and collision behavior as 1.9; all five source-member hashes match the report and embedded manifest. |
| 1.9.3 | ACCEPT | Source files changed, but the reviewed `>= 7` placement, zero-height collision shape, strict collection and negative-Y clamp remain as reported. |
| 1.10 | ACCEPT | Same relevant behavior; the collision shape uses `(LAYERS - 1) * 0.125F`, giving zero max-Y for layer 1, while outline shape remains separate. |
| 1.10.1 | ACCEPT | Same relevant behavior as 1.10. |

The layer-seven support's own collision top is below the upper cell base; the confirmed clip is the zero-height box in the upper snow cell. This does not require the lower support to touch that collision plane. In the legal solid-block alternative, the upper snow cell likewise starts one block above the support. Rendering and snow contact/effect behavior were not used as evidence.

## Evidence integrity

For all 15 audited releases, the report's provenance-manifest SHA-256, release-metadata URL/hash pin, Feather Gen2 mapping-artifact URL/hash pin, and each of `SnowLayerBlock.java`, `Block.java`, `World.java`, `Entity.java`, and `Box.java` hashes were rechecked against the exact canonical tree, embedded manifest and current file bytes. Result: 15/15 provenance rows and 15/15 five-member rows matched; zero mismatches.

Representative exact source trail (member names and line numbers; mappings may shift between releases):

- `1.8/ornithe-feather/net/minecraft/block/SnowLayerBlock.java`: `getCollisionShape` at 40–45; `canBePlaced` at 74–85. The support fallback is `isSolidRender() && material.blocksMovement()`.
- `1.8.1/ornithe-feather/net/minecraft/block/SnowLayerBlock.java`: `canBePlaced` at 74–84 changes the snow support comparison to `>= 7`.
- `1.8/ornithe-feather/net/minecraft/block/Block.java`: collision append gate calls strict `shape.intersects(var7)` at 293–294. `Box.intersects` at 153–158 uses strict overlap inequalities.
- `1.8/ornithe-feather/net/minecraft/world/World.java`: `getCollisions` enumerates block cells and delegates to block-state collision collection.
- `1.8/ornithe-feather/net/minecraft/entity/Entity.java`: ordinary `move` gathers collisions from the expanded AABB at 438 and clips Y at 442.
- `1.8/ornithe-feather/net/minecraft/util/math/Box.java`: `intersectY` negative branch at 120–128 clamps a downward displacement when the moving box bottom is at/above the obstacle's top. For a degenerate box, strict collector intersection requires query `minY < plane < maxY`.

## Required report edit

Add to the 1.8 row/findings that the exact layer-eight arrangement is illegal, but a lower layer-seven snow state or eligible full solid block makes the upper layer-one cell legal and still supplies the same degenerate-plane clipping witness. Preserve the exact layer-eight distinction. No other audited outcome requires change.
