# F-FENCE-FROSTED-ICE-CONNECTION — 1.13.2 fences omit the Frosted Ice arm

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: FENCE-CONNECTION-EXCEPTION, FENCE-COLLISION-GEOMETRY, WORLD-COLLISIONS, ENTITY-MOVE-AXES.
- Classification: changed behavior
- Confidence: source-confirmed; independent finding review pending
- Applicability: historical player behavior
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Artifact provenance

- A source artifact: `EA-FEATHER-R1-A` in `run.md`; revised-derived revision `feather-r1-2026-10-07`; source tree `ready/1.12.2/ornithe-feather`; source manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`. Original mapped JAR unavailable; original derived-bytecode identity unverified.
- B source artifact: `EA-FEATHER-R1-B` in `run.md`; revised-derived revision `feather-r1-2026-10-07`; source tree `ready/1.13.2/ornithe-feather`; source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Original mapped JAR unavailable; original derived-bytecode identity unverified.
- The exact ready source trees match those manifests; see `run.md` for immutable artifact and raw-input verification records.

## Paired source evidence

- A `FenceBlock.java#shouldConnectTo/resolveVirtualProperties/addCollisions`, lines 59-78, 124-136 and 163-168; SHA-256 `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e`. A `Block.java#isExceptionForAttachment/getFaceShape/getShape/getCollisionShape`, lines 232-234, 307-309, 362-364 and 387-389; registration at line 1296; SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`.
- B `FenceBlock.java#shouldConnectTo/getPlacementState/updateShape`, lines 46-61, 76-94 and 97-108; SHA-256 `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2`. B `Block.java#isExceptionForAttachment/getFaceShape/getShape/getCollisionShape`, lines 231, 255-257, 383-385, 388-395, registration line 1607 and default `Properties.hasCollision=true` line 1901; SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- A/B `Blocks.java` maps `FROSTED_ICE` to `frosted_ice` at lines 494 / 1102; source hashes `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e` / `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303`. Block constructors register the same key at A `Block.java` line 1296 and B line 1607. `FrostedIceBlock` A/B hashes are `27bbe9206e78f1b737883403169ab3a7f8783b45062478fed6329aae3a578b9b` / `096940541255ab371d6f197fbf22f30b99da48ff1bde8be5c3be15ba00a299be`; neither class overrides the inherited face, outline or collision shape.
- A/B `Entity.java#move` and world collision-collector routes are recorded with exact paired file hashes under `WALL-SINGLE-ARM-COLLISION` in `run.md`. All source digests cited here match the exact ready manifests.

## Source-level difference

For a solid-face neighbor, A's fence predicate rejects the neighbor only when it appears in `Block.isExceptionForAttachment` or the fence's additional exception list. Neither list contains `Blocks.FROSTED_ICE`, so an east-adjacent Frosted Ice block with its default `FaceShape.SOLID` sets EAST=true. B adds `Blocks.FROSTED_ICE` to `FenceBlock.shouldConnectTo(Block)`, so the same solid face sets EAST=false. B's placement and horizontal `updateShape` both use that predicate; A resolves the connection from neighbors during collision collection.

Frosted Ice existed in both versions under the same `frosted_ice` registry key. It inherits the full-block face and collision shapes. In B, `Block.Properties.hasCollision` defaults true and the Frosted Ice registration does not disable it. This finding therefore concerns an old block present in both eras.

## Reachability and player consequence

Place a fence at (0,0,0) and Frosted Ice at (1,0,0), leaving the remaining fence neighbors open. On the paired movement path, A resolves EAST=true and appends its east collision arm X[10,16] × Z[6,10] × Y[0,24] in sixteenth-block coordinates. B resolves EAST=false and returns only the center post; the B fence geometry and inherited pane-provider route are bounded in `FENCE-COLLISION-GEOMETRY`.

For an isolated source-derived player query, set a 0.6-wide player box to X[1.0,1.6], Z[0.2,0.8], Y[1.0001,2.8001], with `onGround=false`, not sneaking, and request `dx=-0.1`, `dy=dz=0`. The player is just above the Frosted Ice top, so the Frosted Ice full cube does not overlap vertically. A's east arm extends to x=1.0 and up to y=1.5, so the negative X movement is clipped at zero. B's center post ends at x=0.625 and does not overlap this query; with no east arm, B applies the requested -0.1 X displacement. The airborne precondition prevents the step candidate path from replacing this axis result.

This is a source-derived ordered player movement consequence, not a measured trajectory. It is bounded to the stated historical block arrangement and movement query; no frequency claim is made.

## Handoff

- Independent delta description: for a solid face, A connects a fence arm to Frosted Ice while B explicitly suppresses that connection; the added A arm can block a player above the full-cube neighbor.
- Related finding IDs: `F-COLLISION-REPRESENTATION`, `FENCE-COLLISION-GEOMETRY`.
- Applicability constraints: shared `frosted_ice` block, EAST fence neighbor, steady state with B's connection state updated, player above the neighbor's top and within the east-arm lane.
- Independent blind finding review: pending. No implementation handoff until a reviewer accepts this exact source snapshot.
