# F019: Wall neighbor connection predicate

- **1.13.2**, ornithe-feather: `WallBlock.java`; lines 37-38.
- **1.14.4**, ornithe-feather: `WallBlock.java`; lines 39-40.

## Difference

1.13.2 computes each horizontal neighbor flag by passing the neighbor's `FaceShape` to `shouldConnectTo`.

The predicate accepts `MIDDLE_POLE_THICK` unconditionally, accepts `MIDDLE_POLE` for a fence gate, or accepts `SOLID` when the neighbor is not an attachment exception. 1.14.4 passes a boolean face-solidity result and direction to `m_71853799`; it accepts a full collision face when the block is not an attachment exception, a block in `BlockTags.WALLS`, or a direction-compatible fence gate. `BlockState.m_87223014` may use a cached face-solidity value for cached states; otherwise `Block.isFaceSolid` excludes leaves and checks whether the collision shape fills that face.

Both placement and neighbor-update paths recompute the four horizontal flags and `UP`; `WallBlock.getCollisionShape` selects the collision-shape array from those flags.

The predicates have different inputs and exception sets.

For common-era TNT and frosted ice, 1.13.2 gets `SOLID` from the base `Block.getFaceShape` but `WallBlock.isExceptionForConnection` rejects both. 1.14.4 does not list either in `Block.isExceptionForAttachment`; their inherited full-block collision shapes make `Block.isFaceSolid` true.

Therefore normal wall placement/update beside either block sets the directional connection flag false in 1.13.2 and true in 1.14.4, and `getShapeIndex` feeds that flag into the wall collision-shape array.

Other 1.13.2-era `FaceShape` providers and 1.14.4 tag/cache cases still need enumeration.

## Movement meaning

Placement and neighbor updates beside vanilla TNT, frosted ice or a retracted piston are ordinary reachable wall-state transitions.

The changed direction flag selects a different wall collision shape that can be queried during player movement through the paired world block-collision path in S020.

Modern-only block registrations are excluded.

Source confirms different wall connection flags for adjacency to vanilla TNT, frosted ice and retracted pistons, with the corresponding different collision-shape selection.

This report preserves those facts as source-only evidence of an excluded block-state-lifecycle change; it does not accept them for implementation.

The resulting difference in player displacement/clipping has not been measured.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F019-wall-neighbor-connection-predicate.md).
