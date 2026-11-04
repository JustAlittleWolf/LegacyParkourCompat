# Lily Pad provider coverage proposal

## Immutable evidence inputs

- Accepted bounded provider note: `build/source-drafts/lily-pad-effective-provider.md`, SHA-256 `084FAAB0940C829DE76B99B1D786129ED0BE2E50034451F6677063B560BF3C8E`.
- Accepted PlantBlock dependency checklist: `build/source-drafts/plant-collision-dependency-checklist.md`, SHA-256 `1D5F3142CB9411E6C508D8EB945749ED1934654E85B5ADD4D4735DEDC6711003`.
- Frozen registry crosswalk: `build/source-drafts/registry-collision-crosswalk.md`, SHA-256 `BBB1C099DC384489DBB219DAA90F797649FC0C8094E5931A68A0C2997F32280A`.
- Pair and source identity: exact 1.12.2 → 1.13.2 Ornithe Feather Gen2 build 2; endpoint source manifests are 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

## Proposed additive coverage item

Append one bounded provider-only checked slice:

> LilyPad collision-provider check for ordinary player movement. In 1.12.2, `LilyPadBlock.addCollisions` supplies its static box for non-boat entities, bypassing the inherited `PlantBlock.getCollisionShape` empty provider. A player is not a `BoatEntity`. In 1.13.2, the registered `LilyPadBlock` keeps the base `hasCollision` gate enabled and `LilyPadBlock.getShape` supplies a fixed box. The old and new providers therefore supply the same local bounds: `(1/16, 0, 1/16)` to `(15/16, 3/32, 15/16)` for an ordinary player.

This is a provider-result slice only. The old `waterlily` key and new `lily_pad` key remain as recorded by the frozen registry crosswalk; this item does not infer an alias, migration, or registry equivalence. The old boat exclusion is outside player movement scope.

## Preservation limits

- Keep world/block collision collection, candidate-region and exclusion semantics, query flags, and collision-axis resolution open. Equal provider bounds do not prove equal collected collision results or movement.
- Keep full paired tick/provider coverage, other providers, registry alias semantics, implementation acceptance, and runtime parity open. Do not mark pair coverage complete or add a player movement difference from this provider result.
- Add only this checked slice. Preserve the pair's existing fields, evidence associations, open dependencies, unexamined slices, source-coverage and dependency-closure states, and audit status.
