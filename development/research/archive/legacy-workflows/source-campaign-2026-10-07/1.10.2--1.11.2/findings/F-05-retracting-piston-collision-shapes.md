# F-05: Retracting moving piston uses separate base and head collision shapes

- Older version A: 1.10.2
- Newer version B: 1.11.2
- Mechanic / coverage slice IDs: moving-piston collision geometry; S4-piston-collision-geometry
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player collision input
- First changed release: unknown within (1.10.2, 1.11.2]
- Runtime validation: not performed

## Paired evidence

- A: artifact `A` in `../run.md`; `MovingBlock#getCollisionShape`, lines 132-137, SHA-256 `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6`; inherited `Block#addCollisions`, lines 341-344, SHA-256 `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`; `MovingBlockEntity#getShape`, lines 80-89, SHA-256 `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1`; retracting source creation in `PistonBaseBlock`, lines 177-182, SHA-256 `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36`.
- B: artifact `B` in `../run.md`; ordinary `World#getCollisions` dispatches `forceShape=false`, lines 960-1010, `World.java` SHA-256 `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff849a9a7d0cb82efb58`; new `MovingBlock#addCollisions`, lines 134-140, SHA-256 `f2f99070cad0b64d8ecede16b44e0d0383e11c5b4e0c787edeaa2b8fc5c8af18`; `MovingBlockEntity#getStateForShape`, lines 107-114, and `addCollisions`, lines 318-345, SHA-256 `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e`; `PistonHeadBlock` new short-arm boxes and selector, lines 37-42 and 76-92, SHA-256 `da30307abf1e88698a220ab3b61538be9b2f5f16d6648c8f41a2671dc06b7ed9`; retracting source creation in `PistonBaseBlock`, lines 181-186, SHA-256 `3a055f33949305827c3159bb5fbddf8bf51d0ec942f1b30f1fa59ff1bab9be64`.

### Derived-artifact provenance

This finding uses the unchanged published Java source trees with canonical consumer revision `feather-r1-2026-10-07`, independently ops-verified. A snapshot `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.10.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`; its `revision.json` SHA-256 `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`. Unchanged A source manifest `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather.sources.sha256`, SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; raw-artifact manifest `build/movement-campaign-2026-10-07/ready/1.10.2/artifacts.sha256`, SHA-256 `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`. B snapshot `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`; its `revision.json` SHA-256 `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`. Unchanged B source manifest `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather.sources.sha256`, SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; raw-artifact manifest `build/movement-campaign-2026-10-07/ready/1.11.2/artifacts.sha256`, SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`. Original mapped-JAR equivalence with these revision snapshots is unproven; no metadata-only-change claim is made.

## Source-level difference

A's `MovingBlock` does not override `addCollisions`. Ordinary world collision dispatch therefore uses the base `Block#addCollisions`, which asks `MovingBlock#getCollisionShape`; that returns `MovingBlockEntity#getShape`, a union of the translated `movedState` shape at current and last progress. For a retracting source, `PistonBaseBlock` constructs the moving entity with `extending=false, source=true` and `movedState` set to the piston base state.

B's ordinary world query passes `forceShape=false` to the new `MovingBlock#addCollisions` override. The override delegates to `MovingBlockEntity#addCollisions`, which adds the extended base collision at the source and then constructs collision boxes from a piston-head state translated by progress. The head arm uses new direction-specific short-arm boxes as the animation approaches completion.

## Reachability and dependencies

The retracting source is created by the paired piston-base path in both versions. A local player's ordinary `World.getCollisions` query scans the moving-block cell and receives the version-specific boxes when its query box intersects them. This slice covers that source path and provider dispatch; general block collision providers, neighboring state/resource data and the full player collision graph remain open under D-COLLISION and D-BLOCK-DATA.

## Consequence and uncertainty

The collision-box set supplied to player movement differs for a reachable retracting source during partial progress. Which axis clipping or step candidate changes depends on player position, box, progress and nearby geometry. This finding makes no claim about a specific final position or runtime trajectory.

## Handoff

Keep separate from F-02's PISTON displacement cap and F-03's sneak edge-restraint mover gate. Introduction is unknown within the endpoint pair.
