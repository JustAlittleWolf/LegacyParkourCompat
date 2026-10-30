# Collision query closure: 1.13.2 → 1.14.4

**Input boundary.** This closure uses only the surfaced pair-audit claims in `.native-source/20261030/1132-1144-pool31/pair-audit/review.md` (SHA-256 `9cb7a6b3769e688f61ba1fd61ee61aa601945cf363a0b36e7545de373a009530`) and the exact assigned Ornithe Feather Gen2 build 2 sources. It does not make an implementation or runtime-parity claim.

## Result

The query/provider closure is **possible for explicit full-cube witnesses**, enough to establish both resolver-order and resolved-vector-cutoff preconditions at the `Entity.move(SELF, …)` boundary. It is **not a complete pair-wide collision-query inventory**: stateful/context-sensitive provider classes, neighboring-state producers, arbitrary entity-collision shapes, and all movement-input trajectories are outside these witnesses. The exact requested displacement values below are resolver witnesses; source proves ordinary `LivingEntity` travel forwards its velocity to the self-move call, but this report does not back-solve either exact vector through keyboard/input/attribute history. That distinction keeps the witness static and source-bounded.

## Movement route and resolver gates

- **1.13.2:** the ordinary dry travel branch reaches `LivingEntity.travel`'s `this.move(MoverType.SELF, velocityX, velocityY, velocityZ)` at `LivingEntity.java:1575` (and corresponding ordinary movement calls at `:1523`, `:1605`, `:1640`). `PlayerEntity` initializes standing size to `0.6F × 1.8F` at `PlayerEntity.java:450`; `Entity.setPosition` centers the box on x/z. `Entity.move` enters collision resolution if any requested axis is nonzero (`Entity.java:578–599`), clips Y then X then Z (`:581–598`), and always calls `setPositionFromShape()` after resolution (`:674–680`). Each horizontal clipped nonzero component is applied separately (`:586–597`).
- **1.14.4:** `LivingEntity` ordinary dry travel calls `move(MoverType.SELF, currentVelocity)` at `LivingEntity.java:1846` (`:1826`, `:1871`, `:1905` are other self-move branches). Player standing dimensions are `0.6F × 1.8F` at `PlayerEntity.java:109`. `Entity.move` passes through `m_91740517` and `m_72373519` at `Entity.java:463–464`; it only moves the box and updates position when the resolved vector's squared distance is **strictly greater than** `1.0E-7` (`:465–468`).
- In 1.14.4, the resolver helper `m_64271959` enters the diagonal helper when at least two components are nonzero (`Entity.java:692–705`). `m_79801352` clips Y first and picks Z-before-X only for `abs(dx) < abs(dz)`; otherwise it clips X before Z (`Entity.java:708–738`). 1.13.2 retains Y→X→Z unconditionally (`Entity.java:581–598`).

## Query, candidate, shape, border, and chunk route

**1.13.2 query path.** `Entity.move` calls `World.getCollisions(entity, shape, dx,dy,dz)` (`Entity.java:578–580`). `WorldView.getCollisions(Box, delta)` constructs the swept region with a `1.0E-7` margin and removes the existing box (`WorldView.java:154–163`); the shape overload updates entity world-border state and calls `getBlockCollisions` (`:170–177`), while `World.getCollisions` concatenates the entity-collision stream for non-null entities (`World.java:1754–1758`). Block candidates are enumerated across floor/ceil bounds with a one-cell halo; boundary triple-corner positions are skipped, only loaded positions are read, and the block state's collision shape is queried and translated to world coordinates (`WorldView.java:112–151`).

**1.14.4 query path.** `Entity.m_72373519` creates the entity `CollisionContext`, offers a world-border shape only at a box/border crossing, and queries entity collisions on the requested swept box (`Entity.java:656–665`). In a multi-axis resolution, `m_64271959` concatenates the accumulated candidates with a fresh `getBlockCollisions(entity, expandedBox)` query (`:692–705`). `WorldView.getBlockCollisions` enumerates the expanded integer bounds and asks `getChunk(chunkX,chunkZ,getChunkStatusForCollisions(),false)`; a null chunk is omitted; otherwise it reads the state, applies the large-shape/moving-block scan filters, queries its context-aware collision shape, translates it, and emits it only on positive overlap with the swept shape (`WorldView.java:133–185`). `World.getChunkStatusForCollisions()` supplies `FULL_CHUNK` (`World.java:874–877`), and `World.getChunk(...,load=false)` forwards to the chunk source and may return null without loading (`:157–164`).

**Provider used by these witnesses.** Both witnesses need only the vanilla base full-cube collision provider. In both versions, base `Block.getShape` returns `VoxelShapes.block()` (1.13.2 `Block.java:388–390`; 1.14.4 `:371–373`) and base `getCollisionShape` returns that shape when `hasCollision` is true (`1.13.2 :392–395`; 1.14.4 `:375–378`). `Block.Properties.hasCollision` defaults true (`1.13.2 Block.java:1901`; 1.14.4 `:811`), and the stone registration uses that default (`1.13.2 Blocks.java:626` resolves the stone registry entry; 1.14.4 `Blocks.java:22` constructs a regular `Block` with stone material and does not disable collision). State dispatch reaches the block provider (`BlockState.java:209–210` in 1.13.2; `:197–202` in 1.14.4). Thus the explicit witness cells can be ordinary full-collision base-block states; no neighbor-sensitive shape provider is required.

**World border.** 1.13.2 sets/checks the `outsideWorldBorder` hysteresis flag (`WorldView.java:170–199`) and substitutes a full cube for out-of-border enumerated cells only when border collision is active (`:119–145`). 1.14.4 obtains the border collision shape from `WorldBorder.m_91823398()` (`WorldBorder.java:46–48`), emits it only when the player box expanded by `1.0E-7` overlaps it while the contracted box does not (`Entity.java:659–662`, `WorldView.java:150–156`). Witnesses are placed far inside the border, with the entire swept box separated from it; neither route contributes a border candidate.

**Chunks and entity candidates.** The 1.13.2 block scan calls `isChunkLoaded(pos)` before state access (`WorldView.java:130–135`; position overload at `:274–279`); the witness uses loaded interior chunks. In 1.14.4 the witness's `World.getChunk(..., FULL_CHUNK, false)` returns a non-null loaded chunk. Entity candidates are independently queried by 1.13.2 `EntityView.getEntityCollisions`, which expands bounds by `0.25`, excludes configured entities/same-vehicle entities and filters actual shape intersections (`EntityView.java:21–35`); 1.14.4 uses a `1.0E-7` expansion and equivalent exclude/same-vehicle/intersection filtering (`EntityView.java:53–66`). Witness assumption: no other entity collision shape intersects the swept box. That removes entity candidates without relying on differences in their broad-phase selection.

## Explicit witnesses

### A. Horizontal axis-order difference

Use a standing-dimensions player box `x=[0,0.6]`, `z=[0,0.6]`, `y=[0.25,2.05]`, airborne (`onGround=false`); place one ordinary full-cube collision block at `(1,0,1)` (local box `[1,2]×[0,1]×[1,2]`). Keep the area loaded, isolated from entities, and far from the world border. Request SELF delta `(dx,dy,dz)=(0.5,0.01,0.6)`. The initial x and z gaps to the block are both `0.4`; positive-volume overlap begins only after the orthogonal component has moved. Y clears first and remains overlapping the block vertically. Since the player is airborne and not clipped downward, neither version's grounded/descending step-up branch is entered.

- 1.13.2 X-first: the box initially has no z overlap with the block, so X advances `0.5`; Z then overlaps in x and is limited to `0.4`. Resolved `(0.5,0.01,0.4)`.
- 1.14.4 has `abs(0.5) < abs(0.6)`, so Z advances `0.6` before X; X then overlaps in z and is limited to `0.4`. Resolved `(0.4,0.01,0.6)`.

The sole full-cube cell is in both swept candidate regions, loaded, and inside-border; the base provider above gives the required unit cube on both sides. This is a concrete resolver/query witness and a source-confirmed ordinary SELF-movement route. Exact emission of `(0.5,0.01,0.6)` from a particular ordinary input trajectory is not claimed.

### B. Resolved squared-distance cutoff

Use an airborne standing player box `x=z=[0.3999,0.9999]`, `y=[1.1,2.9]`. Put full-cube blocks along an X-facing wall at cells `(1,1,0)` and `(1,1,-1)`, and along a Z-facing wall at `(0,1,1)` and `(-1,1,1)`; the two walls extend across the player's respective orthogonal footprint, and all blocks use the same base full-cube collision provider. Keep all candidate chunks loaded, no entities in the swept box, and remain far inside the world border. Request SELF delta `(0.001,0.00001,0.001)`. Each horizontal requested component exceeds its `0.0001` wall gap. The X wall clips X to approximately `0.0001`; after that movement the Z wall overlaps the translated x footprint and clips Z to approximately `0.0001`. Y is unobstructed. Thus the resolved vector is approximately `(0.0001,0.00001,0.0001)` and its squared length is about `2.01E-8`, below `1.0E-7`.

1.13.2 applies both nonzero horizontal remainders and then recomputes position. 1.14.4 computes that resolved vector but skips the shape/position update because the strict squared-distance gate at `Entity.java:465` fails. The SELF route is ordinary `LivingEntity` travel as cited above. This witness establishes the cutoff's consequence for a qualifying resolver result; an exact prior-input trajectory to these near-wall gaps/speeds is not closed.

## Coverage and remaining limits

**Closed for these two witnesses:** ordinary player-self movement call site; collision dispatcher and horizontal clipping order; both block candidate routes; the specific generic full-cube collision provider; border exclusion in a comfortably interior position; loaded-chunk routes; entity collision exclusion assumption; step-up suppression; and the cutoff comparison/result math.

**Not closed:** exhaustive historical block collision providers and state/neighbor-context production; exact server/client chunk-state availability through every runtime path; all entity collision source/filter producers; collision differences for arbitrary shapes or border/chunk edges; a complete keyboard-to-velocity-to-position trajectory for the witness vectors; and broader pair-level reachable movement inventory. Thus witness-level query closure is possible and achieved, while the pair must remain unfrozen for the broader inventory cited by the permitted audit.

## Source hashes

Canonical Java-file SHA-256 values for every source cited above (paths relative to each endpoint's `ornithe-feather` root):

| Relative path | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` | `fcdf8dc98f97781f4639c4b8cda8a238edf834f3963e401e6eeef6cb475c4909` |
| `net/minecraft/world/EntityView.java` | `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de` | `3822844ced53882c52f0523a1bd714baa3cd9bb9dab38a5e2c53051c7b1590e3` |
| `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` | `498a72cdd4bd035cd731d2c49fce401bef439d6f92f498501577d9cfc59ab487` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/world/border/WorldBorder.java` | `7593d7a8df153f2a039a5b38cca684e1e15529b80ffde743151419e125590718` | `dc01d59f1d8ad15fdf0cc33dc4280f871ce7d62f68a1822fe7233e28f606345f` |

Report hash is recorded in the adjacent `.sha256` file.
