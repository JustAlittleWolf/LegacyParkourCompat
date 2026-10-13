# F-07: The 1.11.2 player pose-fit query uses a stale straight-stair shape

- Older version A: 1.10.2
- Newer version B: 1.11.2
- Mechanic / coverage slice IDs: boolean world collision query; player pose sizing; `S4-world-query-boolean-player-pose`, `S4-world-query`, `S5-stairs-collision-shapes`, `S2-resize`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player movement collision input and player dimensions
- First changed release: unknown within (1.10.2, 1.11.2]
- Runtime validation: not performed

## Paired evidence

### A artifact identity

- Evidence artifact record ID: A in `../run.md`
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.10.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`
- Evidence manifest path / SHA-256: sibling `revision.json` / `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.10.2/artifacts.sha256` / `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`
- Original derived-artifact availability and SHA-256 or expected hash: original mapped JAR unavailable; original manifest expected `ab7aa536f52e94c4c099999bc731020c11a1b29e2979a566cedcc2ffb00e701d`
- Source/raw-input hash relation and verification reference: unchanged source manifest `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather.sources.sha256` / `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; raw input manifest is the original artifact manifest above. Source owner and worker verified these manifests and cited source hashes; independent ops verification covered the six-bundle revision set.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified; the original mapped JAR is unavailable and the snapshot hash differs.
- Provenance limitations: source-tree provenance is established by the unchanged publication records; identity with the unavailable original mapped JAR is not established.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/net/minecraft/world/World.java::World#getCollisions(Box)`, lines 1022-1055, `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`; `.../net/minecraft/entity/living/player/PlayerEntity.java::PlayerEntity#updatePlayerPose`, lines 250 and 296-322, `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`; `.../net/minecraft/block/StairsBlock.java::StairsBlock#addCollisions/getCollisionShapes/getPlacementState/resolveVirtualProperties/getStairShape`, lines 67-89, 261-273 and 311-354, `ee143aced2a6902563cdbae77e78d1d1a512244f278987684ebe5bcacba37cb`; `.../net/minecraft/block/Block.java::Block#getStateFromMetadata/getPlacementState/neighborChanged` and oak-stairs registration, lines 152-153, 388-390, 520-522 and 840, `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`.

### B artifact identity

- Evidence artifact record ID: B in `../run.md`
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`
- Evidence manifest path / SHA-256: sibling `revision.json` / `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.11.2/artifacts.sha256` / `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`
- Original derived-artifact availability and SHA-256 or expected hash: original mapped JAR unavailable; original manifest expected `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`
- Source/raw-input hash relation and verification reference: unchanged source manifest `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather.sources.sha256` / `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; raw input manifest is the original artifact manifest above. Source owner and worker verified these manifests and cited source hashes; independent ops verification covered the six-bundle revision set.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified; the original mapped JAR is unavailable and the snapshot hash differs.
- Provenance limitations: source-tree provenance is established by the unchanged publication records; identity with the unavailable original mapped JAR is not established.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather/net/minecraft/world/World.java::World#getCollisions(Box)`, lines 1058-1059 and shared helper 960-1011, `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff849a9a7d0cb82efb58`; `.../net/minecraft/entity/living/player/PlayerEntity.java::PlayerEntity#updatePlayerPose`, lines 249 and 295-321, `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`; `.../net/minecraft/block/StairsBlock.java::StairsBlock#addCollisions/getCollisionShapes/getPlacementState/resolveVirtualProperties/getStairShape`, lines 66-90, 253-266 and 303-346, `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627`; `.../net/minecraft/block/Block.java::Block#getStateFromMetadata/getPlacementState/neighborChanged` and oak-stairs registration, lines 157-158, 399-401, 517-519 and 845, `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`.

For A and B, `Block.java` registers the existing oak stairs as numeric ID 53. The paired registrations and provider equivalence for the ordinary entity/list query are recorded in `S5-stairs-collision-shapes` in `../run.md`.

## Source-level difference

Use a registered bottom oak stair at `(0,0,0)`, facing NORTH, with same-half neighboring stairs that cause `getStairShape` to resolve this stair as `OUTER_LEFT`. Its stored `SHAPE` remains `STRAIGHT`: `getPlacementState` sets that value, metadata decoding starts from the same default shape, and `StairsBlock` inherits the empty `Block.neighborChanged` implementation. `resolveVirtualProperties` calculates the neighbor-derived value for a query without writing it back to the world.

For a sneaking player's pose-fit candidate box `[0.70,1.30] × [0.50,2.15] × [0.20,0.80]`, the A collision query resolves `OUTER_LEFT`. The outer shape contributes only `BOTTOM_NORTH_WEST_SHAPE`, whose x range is `[0.0,0.5]` and y range is `[0.5,1.0]`; the full-width base ends at the query's minimum y, so the candidate does not intersect this stair's collision boxes.

A's `World.getCollisions(Box)` dispatches the stored state to `StairsBlock.addCollisions`, which always resolves virtual properties before choosing boxes. B's boolean wrapper calls the shared helper with `forceShape=true`; B's stair provider resolves virtual properties only when `!forceShape`. It therefore reads stored `STRAIGHT` and adds the bottom-north upper-half box, `[0.0,1.0] × [0.5,1.0] × [0.0,0.5]`, which intersects the candidate. The result is A `false` and B `true` for this box.

## Reachability and dependencies

The local player inherits `PlayerEntity.updatePlayerPose` through `LocalClientPlayerEntity -> ClientPlayerEntity -> PlayerEntity`. The per-tick player update calls this method. When the player begins sneaking, the target height changes from 1.8 to 1.65 while width stays 0.6. The method builds a candidate box from the current shape minimum coordinates and target width/height; if `World.getCollisions(box)` is false, it calls `setSize`. A player with current bounds `[0.70,1.30] × [0.50,2.30] × [0.20,0.80]` produces the stated candidate on that pose update. The neighboring stair arrangement supplies the `OUTER_LEFT` virtual shape, and the registered oak stair's collision provider supplies the query result.

The candidate bounds are chosen so their x interval starts beyond the outer-left quarter-step while still overlapping the stored straight north-half box. Ordinary movement's B list query passes `forceShape=false` and computes the outer shape, so the player can occupy this air region beside the stair. On the sneak pose update, B's boolean query reads the stored straight shape and reports a collision; A recomputes the outer shape and does not. This isolates the query-mode difference from ordinary movement collision resolution.

## Consequence and uncertainty

The source proves that the same sneak pose-fit candidate is accepted by A's boolean collision gate and rejected by B's. For the stated current dimensions, A can shrink the player's height while B leaves the existing size unchanged. No gameplay trajectory was observed, and runtime validation was not performed. Original mapped-JAR equivalence remains unverified; blind source review is pending.

## Handoff

Keep separate from F-05's retracting piston collision-shape construction and F-06's lower-row world-scan candidate. Applicability is limited to a player sneak pose-size check intersecting the stale straight-shape-only portion of a neighbor-derived outer stair shape. Introduction is unknown within the endpoint pair.
