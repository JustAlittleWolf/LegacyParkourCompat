# Candidate: server player movement validation uses a placement collision context

- **1.21.5**, mojmap: `ServerGamePacketListenerImpl.java`; lines 40-48.
- **1.21.8**, mojmap: `ServerGamePacketListenerImpl.java`; `CollisionGetter.java`.

## Difference

- Exact precondition: a non-passenger player movement packet reaches the validator's new-collision branch with a target candidate box that overlaps a contextual block shape, while the 1.21.5 ordinary context and 1.21.8 placement/old-position context select different shapes; the resulting shape intersection with the pre-move AABB differs.

For scaffolding/powder snow, `isPlacement` alone can change the shape; vertical motion across their `isAbove` threshold can additionally make the chosen shape differ.
- Analysis: the shape set used to decide whether to accept the requested player position or issue a server correction has changed.

In 1.21.5 it is based on ordinary entity collision context/current post-move entity Y; in 1.21.8 contextual block queries set placement mode and use the old AABB bottom Y.

The following validator branch either snaps to the requested position (1.21.8 lines 1067-1086; 1.21.5 lines 1048-1066) or teleports to the pre-move position on rejection (1.21.8 lines 1104-1108; 1.21.5 lines 1084-1088), so it can accept or correct movement differently for the exact precondition above.

Candidate remains pending full route and blind review.

## Movement meaning

- Analysis: the shape set used to decide whether to accept the requested player position or issue a server correction has changed.

In 1.21.5 it is based on ordinary entity collision context/current post-move entity Y; in 1.21.8 contextual block queries set placement mode and use the old AABB bottom Y.

The following validator branch either snaps to the requested position (1.21.8 lines 1067-1086; 1.21.5 lines 1048-1066) or teleports to the pre-move position on rejection (1.21.8 lines 1104-1108; 1.21.5 lines 1084-1088), so it can accept or correct movement differently for the exact precondition above.

Candidate remains pending full route and blind review.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.5--1.21.8/findings/F-PLAYER-MOVE-VALIDATION-CONTEXT.md).
