# F-WALL-SINGLE-ARM-GAPS — 1.13.2 wall collision unions leave corner gaps

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: WALL-SINGLE-ARM-COLLISION, WORLD-COLLISIONS, ENTITY-MOVE-AXES.
- Classification: changed behavior
- Confidence: source-confirmed; independent finding review pending
- Applicability: historical player behavior
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired evidence

### A artifact identity

- Evidence artifact record ID: `EA-FEATHER-R1-A` in `run.md`
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`
- Evidence manifest path / SHA-256: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/artifact.sha256` / `162170b94be0fa6393a515ea3beed10210b2c86a006366cf9d351eba136bae2d`
- Original artifact-manifest path / SHA-256: `ready/1.12.2/artifacts.sha256` / `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`
- Original derived-artifact availability and expected hash: unavailable; expected `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b`
- Source/raw-input hash relation and verification reference: source tree and raw inputs match the original publication per revision record; this worker rehashed the 2,050 source files and all 37 available original raw inputs. See `run.md`, artifact verification and `EA-FEATHER-R1-A`.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified because the original mapped JAR is unavailable; see `run.md`, `EA-FEATHER-R1-A`.
- Provenance limitations: source comparison uses the exact ready source tree, independently matched to its manifest. The revised derived JAR does not establish original mapped-bytecode identity.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `ready/1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `WallBlock#addCollisions`, lines 90-96, and shape table/index `WallBlock`, lines 28-62 and 105-124; SHA-256 `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50`. Neighbor derivation `WallBlock#resolveVirtualProperties`, lines 190-197, same hash. `Blocks.java` maps `COBBLESTONE_WALL` to `cobblestone_wall` at line 437, SHA-256 `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e`.
- Additional A evidence: `FenceGateBlock#getCollisionShape`, lines 81-84, and `getFaceShape`, lines 175-181, SHA-256 `38ae9e6aa1057a8e9b65ff78414d0d6a9a70cf2645f1ea42141ff2815ad6a477`; `Block#addCollision`, lines 373-382, SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`; `PlayerEntity` constructor `setSize(0.6F, 1.8F)`, line 399, SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; `Entity#setPosition` and `Entity#move`, lines 298-304 and 462-796, SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; `World` collision collector, lines 964-1015, SHA-256 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`.

### B artifact identity

- Evidence artifact record ID: `EA-FEATHER-R1-B` in `run.md`
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`
- Evidence manifest path / SHA-256: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/artifact.sha256` / `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`
- Original artifact-manifest path / SHA-256: `ready/1.13.2/artifacts.sha256` / `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`
- Original derived-artifact availability and expected hash: unavailable; expected `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5`
- Source/raw-input hash relation and verification reference: source tree and raw inputs match the original publication per revision record; this worker rehashed the 2,711 source files and all 41 available original raw inputs. See `run.md`, artifact verification and `EA-FEATHER-R1-B`.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified because the original mapped JAR is unavailable; see `run.md`, `EA-FEATHER-R1-B`.
- Provenance limitations: source comparison uses the exact ready source tree, independently matched to its manifest. The revised derived JAR does not establish original mapped-bytecode identity.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `ready/1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, constructor and `getCollisionShape`, lines 22-39, and `getPlacementState`/`updateShape`, lines 69-122; SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`. Inherited `PaneBlock#makeShapes`, lines 32-73, SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`; `Block#block`, lines 136-138, scales each coordinate by `1/16`, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; `VoxelShapes#or`/`join`, lines 96-104, and `overlap`, line 140 onward, SHA-256 `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`. `Blocks.java` maps `COBBLESTONE_WALL` to `cobblestone_wall` at line 906, SHA-256 `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303`.
- Additional B evidence: `FenceGateBlock#getCollisionShape`, lines 67-73, and `getFaceShape`, lines 159-163, SHA-256 `e251cbd1b5e06c42e856a1d537e4ba043f6760b8f54ea1388039428aa7c05164`; `PlayerEntity` constructor `setSize(0.6F, 1.8F)`, line 450, SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`; `Entity#setPosition` and `Entity#move`, lines 309-315 and 473-782, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; `World.getCollisions` forwarding, lines 1755-1756, SHA-256 `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`; `WorldView#getBlockCollisions` and swept-query overload, lines 112-163, SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; `WorldAccess` extends `WorldView`, line 18, SHA-256 `fc75177d629ef8d0d491821fe557e21606e4cb381164f1ca41b2763073f854be`.

## Source-level difference

For a wall with only NORTH connected, both versions derive `UP=true`. A selects `COLLISION_SHAPES[4]`, the single Box X[4,12]×Z[0,12]×Y[0,24] in sixteenth-block coordinates. B selects `VoxelShapes.or(center,northArm)`: center X[4,12]×Z[4,12]×Y[0,24], north arm X[5,11]×Z[0,11]×Y[0,24]. The OR shape leaves two empty corner columns: X[4,5]×Z[0,4] and X[11,12]×Z[0,4]. A's single Box fills both. `VoxelShapes.or` performs a Boolean OR over occupied voxels, so matching outer bounds do not make these shapes equal.

The state is reachable with a north-adjacent open east-west-facing fence gate and air on the other sides. The gate's south face reports `MIDDLE_POLE`, which the wall connection helper accepts for fence gates; its open collision shape is empty in both versions. For one NORTH connection, A's and B's placement/neighbor formulas both set `UP=true`, selecting the shapes above.

## Reachability and dependencies

The local player reaches the shared `Entity.move` resolver through the recorded player tick/travel path in `run.md` and `WORLD-COLLISIONS`. A gathers translated Box collisions from its swept player Box. B builds a swept VoxelShape query, filters block shapes by Boolean overlap, and passes the returned shape stream to the same player's Y/X/Z movement resolution.

Concrete query relative to the wall at (0,0,0): the player has width `0.6F`, height `1.8F`, center approximately x=-0.06,z=-0.31,y=0, giving a Box approximately X[-0.36,0.24], Z[-0.61,-0.01]. Request `dx=0.07`, `dy=0`, `dz=0.25`. A's expanded Box reaches X≈0.31,Z≈0.24 and intersects its wall Box. X resolves fully because the current player Box is still north of the wall's z=0 face; after X, the player overlaps the wall in X, so A clips Z at approximately 0.01. B's swept query, including its `1.0E-7` expansion, still ends before the north arm's X=0.3125 boundary and the center's Z=0.25 boundary; no B wall collision shape overlaps that query. The open gate supplies no other collision. B therefore resolves the requested Z displacement without the wall candidate.

## Consequence and uncertainty

The source proves a different returned collision set and a reachable player movement query whose ordered axis clipping differs. The concrete displacement values above are a source-derived consequence, not a runtime observation. The remaining wall arm combinations, multi-arm states, `UP=false` inherited PaneBlock path, and full stored-neighbor state lifecycle are still open. The first changed release is unknown within (1.12.2, 1.13.2]. The broader collector/representation candidate remains `F-COLLISION-REPRESENTATION`.

## Handoff

Independent delta: one-arm cobblestone-wall collision arms contain corner gaps in 1.13.2 that are filled by the 1.12.2 Box. Related candidate: `F-COLLISION-REPRESENTATION`. Applicability is constrained to shared wall states and reachable player queries that intersect the gap. Independent source review is pending; do not treat this finding as an accepted snapshot or a pair freeze. Runtime validation and implementation decisions are deferred.
