# Independent verdict: dry wooden fence siblings

**Verdict: ACCEPT** the exact sibling applicability claim for spruce, birch, jungle, dark-oak and acacia fences at 1.11.2 → 1.12.0. Each named dry fence is admitted by the reused 1.11.2 oak material cue and rejected by the shared 1.12 horizontal face-shape gate. This is source-level applicability for the already accepted falling-water cue; it adds no separate flow-scan or trajectory claim.

## Immutable input and source identity

Audited the exact assigned Git input `3b00867c7b99fb800e8b681ad5cf9239f77dbc8d:sibling-source-check.md`; binary `git cat-file blob` bytes hash to SHA-256 `269d7105eb9a901f0f7de3b5231a23a1b128455428c63f1d43aeebaceeccb82f`. The input names exact Feather gen2 build.2 source sets: 1.11.2 readiness marker reports version `1.11.2`, mapping `ornithe-feather`, ready, with source-manifest digest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1.12 provenance pins `feather-gen2-1.12+build.2`. The marker and provenance hashes, and every listed source-file hash, match the assigned input and current read-only trees. Accepted oak boundary and verdict inputs also match their supplied raw SHA-256 values (`f7a69e…daa7e1` and `4f6417…2035ae`).

## Independent retrace

`Block.java` registers the five ordinary fences at IDs 188–192 in both releases as `FenceBlock(Material.WOOD, PlanksBlock.Variant.<wood>.getColor())`; `Blocks.java` resolves the corresponding five named entries in both releases. The wood-specific constructor argument is only the planks map color. All five therefore share the oak registration's `Material.WOOD` path. The exact `Material.java` is hash-identical across the pair; `WOOD` is solid (`isSolid()` defaults true). The 1.11.2 accepted `LiquidBlock.isFaceSolid`/`Block.isFaceSolid` trace thus applies equally to each horizontal dry sibling: after the shared exclusions it consults material solidity, independently of fence state, map color, or partial shape.

In 1.12, shared `FenceBlock.getFaceShape` returns `MIDDLE_POLE` for every horizontal face without reading state, material or map color. `LiquidBlock.isFaceSolid` keeps the prior same-fluid, UP and ICE branches, then rejects attachment exceptions/stairs and requires `FaceShape.SOLID`. The five siblings are not any listed attachment exception or stairs; their horizontal `MIDDLE_POLE` is still unequal to `SOLID`, so each is rejected. In the reused accepted `getFlow` caller, the same `LEVEL >= 8` branch tests each horizontal neighbor and the above-neighbor using that horizontal face. This closes the named-sibling dependency only for those side/above queries.

## Per-name outcomes

- **SPRUCE_FENCE:** accepted; ID 188, `Material.WOOD`, spruce map color; same cue results.
- **BIRCH_FENCE:** accepted; ID 189, `Material.WOOD`, birch map color; same cue results.
- **JUNGLE_FENCE:** accepted; ID 190, `Material.WOOD`, jungle map color; same cue results.
- **DARK_OAK_FENCE:** accepted; ID 191, `Material.WOOD`, dark-oak map color; same cue results.
- **ACACIA_FENCE:** accepted; ID 192, `Material.WOOD`, acacia map color; same cue results.

Scope excludes oak re-audit, all other fence families, gates, uninspected versions, flow-scan equivalence, and runtime/world/trajectory claims. No tests, build, launch, source generation or canonical-source writes were performed.
