# Sea Pickle provider coverage proposal

## Evidence binding

- Pair: exact 1.12.2 → 1.13.2, Ornithe Feather Gen2 build 2.
- Frozen registry crosswalk: `build/source-drafts/registry-collision-crosswalk.md`, SHA-256 `BBB1C099DC384489DBB219DAA90F797649FC0C8094E5931A68A0C2997F32280A`.
- Accepted PlantBlock dependency checklist: `build/source-drafts/plant-collision-dependency-checklist.md`, SHA-256 `1D5F3142CB9411E6C508D8EB945749ED1934654E85B5ADD4D4735DEDC6711003`.
- Sea Pickle source note: `build/source-drafts/sea-pickle-source-note.md`, SHA-256 `B69DFB4476989DBC9CE47D222A7ED6715EF34F013007C38467F800C5C3707824`; independent **PASS** by `/root/source_1710_189_providers`, bound to this exact source-note hash. The review checked the property gate, state-only getter behavior, and new-only crosswalk status against exact 1.13.2 source and accepted checklist/crosswalk. It did not close broader collector/trajectory or alias/parity scope.

The coverage proposal itself remains a scratch proposal and requires its own independent review before any coverage-record merge.

## Proposed additive source-coverage item

Append this bounded slice to the existing pair coverage record only after independent review of this proposal:

> 1.13.2 `minecraft:sea_pickle` registration, collision eligibility, and effective movement-collision getter route: registration uses `SeaPickleBlock` without `.noCollision()`, so the base `hasCollision` gate remains enabled; `Block.getCollisionShape` delegates through `BlockState.getShape` to `SeaPickleBlock.getShape`. The shape getter selects among four declared shapes from the legal `PICKLES=1..4` state and does not read world, position, WATERLOGGED, neighbors, fluids, entity, or block-entity context. Source evidence: exact refs and hashes in `sea-pickle-source-note.md`.

Keep separate subclaims if the coverage record stores them separately:

1. Registration/property fact: `hasCollision` is enabled for the registered Sea Pickle block.
2. Dispatch/provider fact: the effective collision path reaches `SeaPickleBlock.getShape` and is state-selected by `PICKLES`.
3. Endpoint inventory fact: the exact registry key is new-only in 1.13.2; the 1.12.2 source has no Sea Pickle registration/class to pair. Preserve `UNMATCHED_ALIAS_UNRESOLVED`; do not infer a cross-release alias or old-side behavior.

## Merge constraints

- The source-note review PASS is bound to exact hash `B69DFB4476989DBC9CE47D222A7ED6715EF34F013007C38467F800C5C3707824`; do not transfer that verdict to this proposal's revision.
- Merge only after independent review passes on the exact proposal hash and against the current pair coverage revision.
- Append the item to the current pair coverage revision; do not replace or narrow existing `openDependencies`, `unexaminedSlices`, evidence associations, or coverage/audit status fields.
- Keep old/new shape parity, gameplay applicability, legacy-state migration/aliasing, other PlantBlock descendants, collector behavior, complete provider inventory, and full pair closure open. The one-sided provider audit does not create a paired no-difference result or a pair-completion claim.
- Preserve the frozen crosswalk and its review binding unchanged; this is supplemental evidence for its new-only Sea Pickle row.
