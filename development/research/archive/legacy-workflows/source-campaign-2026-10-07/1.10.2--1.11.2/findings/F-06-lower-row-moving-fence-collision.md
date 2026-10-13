# F-06: The 1.11.2 query includes a lower-row moving-fence collision

- Older version A: 1.10.2
- Newer version B: 1.11.2
- Mechanic / coverage slice IDs: world collision candidate scan; `S4-world-query-lower-row-moving-fence`, `S4-world-query`, `S4-piston-collision-geometry`, `S5-fence-collision-shapes`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player movement collision input
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
- Source/raw-input hash relation and verification reference: unchanged source manifest `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather.sources.sha256` / `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; raw input manifest is the original artifact manifest above. The source owner and worker verified these manifests and cited source hashes; independent ops verification covered the six-bundle revision set.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified; the original mapped JAR is unavailable and the snapshot hash differs.
- Provenance limitations: source-tree provenance is established by the unchanged publication records; identity with the unavailable original mapped JAR is not established.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/net/minecraft/world/World.java::World#getCollisions(Entity,Box)`, lines 903-943, `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`; `.../net/minecraft/block/PistonBaseBlock.java::PistonBaseBlock#canMoveBlock/move`, lines 241-279 and 302-315, `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36`; `.../net/minecraft/block/Block.java` fence/piston-extension registrations lines 922-925 and 786, `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`; `.../net/minecraft/block/material/Material.java::Material#WOOD/pistonMoveBehavior`, lines 9 and 52-53, `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d`; `.../net/minecraft/block/MovingBlock.java::MovingBlock#getCollisionShape`, lines 132-137, `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6`; `.../net/minecraft/block/entity/MovingBlockEntity.java::MovingBlockEntity#getAmountExtended/getShape`, lines 76-89, `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1`; `.../net/minecraft/block/FenceBlock.java::FenceBlock#addCollisions`, lines 45-75, `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909`; `.../net/minecraft/util/math/Box.java::Box#intersects`, lines 203-209, `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155`; `.../net/minecraft/entity/Entity.java::Entity#move(double,double,double)`, lines 514-548, `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.

### B artifact identity

- Evidence artifact record ID: B in `../run.md`
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`
- Evidence manifest path / SHA-256: sibling `revision.json` / `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.11.2/artifacts.sha256` / `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`
- Original derived-artifact availability and SHA-256 or expected hash: original mapped JAR unavailable; original manifest expected `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`
- Source/raw-input hash relation and verification reference: unchanged source manifest `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather.sources.sha256` / `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; raw input manifest is the original artifact manifest above. The source owner and worker verified these manifests and cited source hashes; independent ops verification covered the six-bundle revision set.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified; the original mapped JAR is unavailable and the snapshot hash differs.
- Provenance limitations: source-tree provenance is established by the unchanged publication records; identity with the unavailable original mapped JAR is not established.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather/net/minecraft/world/World.java::World#getCollisions(Entity,Box,boolean,List)`, lines 960-1000, `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff849a9a7d0cb82efb58`; `.../net/minecraft/block/PistonBaseBlock.java::PistonBaseBlock#canMoveBlock/move`, lines 230-268 and 291-304, `3a055f33949305827c3159bb5fbddf8bf51d0ec942f1b30f1fa59ff1bab9be64`; `.../net/minecraft/block/Block.java` fence/piston-extension registrations lines 927-930, 789 and 791, and default `hasBlockEntity/getPistonMoveBehavior` lines 64, 284-285 and 611-612, `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`; `.../net/minecraft/block/material/Material.java::Material#WOOD/pistonMoveBehavior`, lines 9 and 52-53, `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d`; `.../net/minecraft/block/MovingBlock.java::MovingBlock#addCollisions`, lines 135-140, `f2f99070cad0b64d8ecede16b44e0d0383e11c5b4e0c787edeaa2b8fc5c8af18`; `.../net/minecraft/block/entity/MovingBlockEntity.java::MovingBlockEntity#getAmountExtended/addCollisions`, lines 92-93 and 318-346, `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e`; `.../net/minecraft/block/FenceBlock.java::FenceBlock#addCollisions`, lines 45-81, `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7`; `.../net/minecraft/util/math/Box.java::Box#intersects`, lines 245-251, `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a`; `.../net/minecraft/entity/Entity.java::Entity#move(MoverType,double,double,double)`, lines 564-610, `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`.

## Source-level difference

Both piston paths can place a movable fence state in a `MOVING_BLOCK` cell: `PistonBaseBlock.canMoveBlock` permits the registered oak fence when its ordinary border/height guards pass, and the push path stores the moved state in a `MovingBlockEntity`. The oak fence uses `Material.WOOD` (whose default piston behavior is `NORMAL`), has no block entity, and has a collision post from local x/z `0.375` to `0.625` and local y `0.0` to `1.5`.

Use a moving-block cell at `(x,y,z)` with an extending piston facing WEST at progress `0.5`, and a player query box `[x+1.0,x+1.6] × [y+1.3,y+3.1] × [z+0.2,z+0.8]`. The moved fence's progress offset is `WEST.offsetX * (0.5F - 1.0F) = +0.5`. Its center collision post therefore occupies `[x+0.875,x+1.125] × [y,y+1.5] × [z+0.375,z+0.625]`, which strictly intersects the query box.

For that query, the moving-block cell is on the x perimeter (`o == i`) but its z coordinate is interior. A's scan has `q == 1` and rejects the lower padded y row `r == k` with `q <= 0 || r != k && r != l - 1`. B's shared helper reaches the same cell and admits that row because it rejects only the upper row, `q != l - 1`. B then calls the moving entity's collision provider, which translates the fence collision boxes by progress and appends the intersecting box. In A the cell is not visited, so that candidate contributes no block collision. This is independent of the retracting piston shape change in F-05.

## Reachability and dependencies

The source path is piston movement eligibility and moved-state storage -> `World.getCollisions` block-state dispatch -> `MovingBlock`/`MovingBlockEntity` collision boxes -> the list consumed by `Entity.move`. A locally controlled player reaches inherited `Entity.move` through the living-player travel path; the collision list is therefore a direct player movement input. The pair's S4-piston and S5-fence slices cover the paired piston state path and fence post geometry. `Material.WOOD` has the default `NORMAL` move behavior, the base `hasBlockEntity` flag defaults false, and `canMoveBlock` admits the fence under valid height/world-border conditions.

## Consequence and uncertainty

Source proves that B supplies this collision box to the player query while A skips the candidate cell. If the player is moving into the box, it can constrain the movement clipping result; no final position or observed trajectory is claimed. Runtime validation was not performed. The report's original mapped-JAR equivalence limitation applies, and the finding remains provisional until its blind source snapshot review.

## Handoff

Keep separate from F-02's piston displacement cap, F-03's sneak-restraint mover gate, and F-05's retracting-source shape construction. Applicability is limited to a player query intersecting the lower-row, horizontally translated fence box during partial piston progress. Introduction is unknown within the endpoint pair.
