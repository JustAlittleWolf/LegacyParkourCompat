# Independent audit: cobblestone wall mixed TNT/stair tuple

**Verdict: ACCEPT — COMPLETED**, bounded to the frozen claim and the exact 1.11.2 → 1.12 evidence below. This accepts the source claim for its stated tuple conditions. It does not close pair coverage, runtime parity, or an intermediate release boundary.

## Immutable identity and source integrity

- Frozen claim SHA-256: `749bf5f2bb6ba52f35a3936f4938d42a3d1efe69c253aa7ba3a97d6efa8c6145` (matches the assigned digest).
- 1.11.2 Feather build.2 source identity: `ornithe-feather.ready.json` SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; it identifies version 1.11.2 and `feather-gen2-1.11.2+build.2-mergedv2.jar` plus `.tiny`. Its source-manifest digest is `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`.
- 1.12 Feather build.2 source identity: `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`; its inputs identify `feather-gen2-1.12+build.2-mergedv2.jar` (SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`) and exact 1.12 client input.
- Independently recomputed SHA-256 for every cited source file/member on both sides matched its 1.11.2 source manifest or 1.12 provenance `sources` map. No cited method body was damaged or mismatched.

## Findings retraced

The old-side dependency chain is `WallBlock.resolveVirtualProperties` → four independent `shouldConnectTo` calls → N/E/S/W properties → `getShapeIndex`. In 1.11.2 the predicate accepts walls/fence gates, rejects barriers, and otherwise requires `material.isSolidBlocking()` and `BlockState.isCube()`, with pumpkin excluded. TNT uses translucent `Material.TNT`, so `isSolidBlocking()` is false. Stairs return `isCube=false`, dispatched by the state implementation. Air fails the same gate. Thus both specified non-air neighbors are rejected: mask 0 and `UP=true`, independent of side arrangement and the above block. The tuple depends on these old-side material/cube checks, not the newer face-shape check.

In 1.12 the wall still calls the predicate separately for each direction, but passes the face toward the wall and tests the neighbor state’s face shape, subject to connection exceptions. TNT inherits `Block.getFaceShape`, which returns `SOLID`, and TNT is not in the attachment/connection exception set. Air returns `UNDEFINED`. A stair connects only when its *resolved* shape is `STRAIGHT` and `FACING` equals the queried wall-facing direction; the resolved stair shape is itself computed from adjacent stairs. This is a sufficient narrow gate for the stated Oak-stair witness, not an all-orientations/all-stair-shapes result. The two remaining neighbor sides must be air. For an opposite pair, the table’s `UP=false` additionally requires air above the wall; adjacent pairs have `UP=true` regardless.

Independent bit derivation confirms N/E/S/W IDs 2/3/0/1 and mask bits 4/8/1/2. The six pair masks index the 1.12 `SHAPES` array as stated: N+E 12, N+W 6, S+E 9, S+W 3, N+S 5, E+W 10. Reading those entries reproduces every listed outline box. `COLLISION_SHAPES` is the corresponding outline box with maxY changed to 1.5, on both versions; index 0 on 1.11.2 is the cited central outline with collision maxY 1.5.

The ordinary movement connection is also closed: wall `getCollisionShape` resolves the directional properties, and `addCollisions` does so when `forceShape=false`; `World.getCollisions` calls block `addCollisions(..., false)`, and `Entity.move` obtains that list before axis clipping. A player box intersecting an added arm’s volume can therefore clip differently. No concrete trajectory outcome follows without the player box and displacement.

## Bounds and state that does not affect this witness

The comparison is supported only for the two pinned exact releases, 1.11.2 and 1.12; it does not establish which intervening patch introduced the change or support a wider range. Once the stated predicates are evaluated, the wall’s `UP` value and `VARIANT` do not alter the horizontal mask/index. For this 1.12 witness, TNT’s `EXPLODE` property is not observed by the inherited face-shape path; material identity is also not the connection test. The stair’s half is not consulted by the horizontal `STRAIGHT` face-shape branch, though surrounding state can affect whether virtual resolution yields `STRAIGHT`. Wall-above air is observed only for the opposite-pair `UP` branch.

This is a source-only acceptance of the bounded claim. It makes no assertion about unsupplied side layouts, additional stair arrangements, other releases, or measured gameplay parity.
