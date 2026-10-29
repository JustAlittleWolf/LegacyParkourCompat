# Immutable correction supplement: current Ladder collision gate

This supplement corrects the summary sentence in the original finding. It does not edit or replace the original finding or independent review.

## Corrected statement

In 26.2, Ladder retains collision: `Blocks.LADDER` registers `LadderBlock` without `noCollision()` (`Blocks.java:1442–1446`), `BlockBehaviour.Properties` defaults `hasCollision` to `true` (`BlockBehaviour.java:982–986`), and the inherited collision method returns the state's shape when that flag is true (`BlockBehaviour.java:327–329`). `LadderBlock.getShape` supplies a facing-indexed voxel shape (`LadderBlock.java:41–44`).

Vine is collision-disabled: `Blocks.VINE` explicitly sets `noCollision()` (`Blocks.java:2429–2441`), which clears `hasCollision` (`BlockBehaviour.java:1094–1097`); its inherited collision-shape method consequently returns `Shapes.empty()` (`BlockBehaviour.java:327–329`). `VineBlock.getShape` separately computes its visible shape (`VineBlock.java:54–72`).

The old-provider findings remain as described in the original report: old Ladder returns a facing-dependent non-null AABB, and old Vine returns null from its collision provider. This correction concerns the current Ladder/Vine distinction only and makes no DDA traversal or runtime claim.

## Immutable target identities

- Original finding: `finding.md`, SHA-256 `830e6df6e5356992e639a062382700a136e29ba4ccec7c290efd7538a05de43b`.
- Original independent review: sibling `dda-climbable-family-65-106-review/review.md`, SHA-256 `a60b1c850afb9221f2e277e48c506bc48ab66e9d0a4b6b1a262dd5f5eadabad1` (verdict `REVISE` for this summary sentence).

The review's source line references above are the evidentiary basis for this bounded correction. Preserve the original `REVISE` verdict as the prior revision's disposition; this supplement does not assert that the corrected bundle has been independently reviewed.
