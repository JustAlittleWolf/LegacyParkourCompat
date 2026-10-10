# F-09: Trapdoor-aware redstone wire signal can change trapdoor state

- Older version A: 1.19.4
- Newer version B: 1.20.1
- Mechanic / coverage slice IDs: D3, S5-02, S4-02
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.19.4, 1.20.1]
- Runtime validation: not performed

## Paired evidence

### A artifact identity

- Evidence artifact record ID: A-1.19.4-MOJMAP-ORIGINAL (run.md, Artifact evidence identities)
- Publication status: original-verified
- Revision ID: none
- Immutable evidence path: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/
- Evidence artifact SHA-256: individual source-file hashes below; source manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3.
- Evidence manifest path / SHA-256: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap.sources.sha256 / 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3.
- Original artifact-manifest path / SHA-256: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/artifacts.sha256 / e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72.
- Original derived-artifact availability and SHA-256: ../../../build/movement-campaign-2026-10-07/artifacts/1.19.4/client-mojmap.jar / efbf38c89b396faae60cfe3bdb91671cb8d1ec3ccf2e27d3ffab4d261acd016d.
- Source/raw-input hash relation and verification reference: the exact published client jar, official Mojang mappings and derived Mojmap tree are recorded in run.md artifact identity A-1.19.4-MOJMAP-ORIGINAL; all cited source files match the immutable source manifest.
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable; original-verified publication.
- Provenance limitations: source evidence is bounded to exact endpoint 1.19.4; no inference about earlier or later releases.
- Relative source paths; members; original line ranges; source hashes:
  - RedStoneWireBlock.java, getConnectingSide(BlockGetter, BlockPos, Direction, boolean): lines 244-261 and getSignal(BlockState, BlockGetter, BlockPos, Direction): lines 391-406; SHA-256 9cb901b000b60746263b50f3ec4c70218ee3546645d8959ddb4eff29aa224e77.
  - Level.java, getSignal(BlockPos, Direction) and hasNeighborSignal(BlockPos): lines 746-764; SHA-256 a6d0feba4b033e36dfa54214e1c9ca08a812f60efc97a5906f5e9cb13cd22dfe.
  - TrapDoorBlock.java, getStateForPlacement(BlockPlaceContext): lines 132-146; neighborChanged(...): lines 114-128; getShape(BlockState, BlockGetter, BlockPos, CollisionContext): lines 59-76; SHA-256 fae6479ab458d35b7c5b7a6588c28f8a6f7a3569b8abab96179ae86ac37904c4.
  - BlockBehaviour.java, getCollisionShape(...): lines 295-296; SHA-256 517aa4bd9875e9f7b3f5fba472a84e82afd1e11e35d3c83dc2677d8bbeb4997d.
  - Entity.java, move(MoverType, Vec3): lines 568-569 and collide(Vec3): lines 801-841; SHA-256 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b.
  - BlockCollisions.java, computeNext(): lines 65-90 calls the state collision-shape provider; SHA-256 11fecadc012d0a544097bf0febfa1a83da5a534e6182ee7df7b2334b6ee80127.

### B artifact identity

- Evidence artifact record ID: B-1.20.1-MOJMAP-ORIGINAL (run.md, Artifact evidence identities)
- Publication status: original-verified
- Revision ID: none
- Immutable evidence path: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/
- Evidence artifact SHA-256: individual source-file hashes below; source manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465.
- Evidence manifest path / SHA-256: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap.sources.sha256 / 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465.
- Original artifact-manifest path / SHA-256: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/artifacts.sha256 / 5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2.
- Original derived-artifact availability and SHA-256: ../../../build/movement-campaign-2026-10-07/artifacts/1.20.1/client-mojmap.jar / 221d543b9f80ca42faaaa8e3f7cebc4fe1879b8f7ba954bb6ca62f8250be8b77.
- Source/raw-input hash relation and verification reference: the exact published client jar, official Mojang mappings and derived Mojmap tree are recorded in run.md artifact identity B-1.20.1-MOJMAP-ORIGINAL; all cited source files match the immutable source manifest.
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable; original-verified publication.
- Provenance limitations: source evidence is bounded to exact endpoint 1.20.1; no inference about earlier or later releases.
- Relative source paths; members; original line ranges; source hashes:
  - RedStoneWireBlock.java, getConnectingSide(BlockGetter, BlockPos, Direction, boolean): lines 244-261 and getSignal(BlockState, BlockGetter, BlockPos, Direction): lines 391-406; SHA-256 db9be63a8fe2a90ef72c6321346224c4b564d9f7c445a008f04b84881c7910d3.
  - SignalGetter.java, default getSignal(BlockPos, Direction) and hasNeighborSignal(BlockPos): lines 65-83; LevelReader extends SignalGetter at line 25 and Level implements LevelAccessor at Level.java line 73; SHA-256 387c52b2812332787fbf16c4cca41f120d1afcd9d3130cffd9736689e0eeb86b. This is B's replacement for A's Level-declared signal methods.
  - TrapDoorBlock.java, getStateForPlacement(BlockPlaceContext): lines 131-145; neighborChanged(...): lines 113-127; getShape(BlockState, BlockGetter, BlockPos, CollisionContext): lines 59-75; SHA-256 2151e4f2c3ea35c3dceab7dcc78e53c0d7e0357f4fbd1197f54c6d772fd6cabb.
  - BlockBehaviour.java, getCollisionShape(...): lines 285-291; SHA-256 5f37987191165379c222c412949a0c80a904bf78a453035b1f9d86c6454510fa.
  - Entity.java, move(MoverType, Vec3): lines 612-613 and collide(Vec3): lines 870-910; SHA-256 94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759.
  - BlockCollisions.java, computeNext(): lines 65-90 calls the state collision-shape provider; SHA-256 3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b.

## Source-level difference

A RedStoneWireBlock#getConnectingSide enters its upper-connection branch only when the adjacent block passes canSurviveOn (its FULL upward face is sturdy or it is a hopper). B adds an adjacent TrapDoorBlock exception before that check. When the side wire is adjacent to a bottom-half closed trapdoor whose UP face is not FULL, a connectable redstone source (for example, redstone wire) is above the trapdoor, the side wire has POWER > 0, the block above the side wire is not a redstone conductor, and no other source powers the trapdoor, B returns an UP or SIDE connection while A leaves the side disconnected. The trapdoor has a non-connectable solid block below it so A's fallback also returns NONE.

Both versions' RedStoneWireBlock#getSignal use POWER and recompute getConnectionState for side queries, returning zero when the opposite directional state is not connected. Thus B can emit its positive side signal into the trapdoor in that topology and A does not. The signal-method consumer is unchanged; the difference comes from its changed state-producing helper.

TrapDoorBlock#getStateForPlacement checks hasNeighborSignal and starts OPEN/POWERED when signal is present. Its server neighborChanged path also checks the same predicate and updates OPEN/POWERED. A declares Level#getSignal and Level#hasNeighborSignal directly; B moves the same direction-by-direction check to SignalGetter, inherited through LevelReader. TrapDoorBlock's corresponding placement, neighbor update, and shape methods are otherwise the same at these endpoints.

## Reachability and dependencies

The bounded route is a wire with positive POWER next to a bottom-half closed trapdoor, with a connectable signal source above the trapdoor and a non-conductor above the wire. On placement, TrapDoorBlock#getStateForPlacement reads the changed side signal through hasNeighborSignal and sets OPEN/POWERED true in B; A leaves them false when no other neighboring source powers it. The unchanged neighborChanged route supplies the same result when the wire topology or signal updates after placement.

In the reachable client player path already mapped in S3-02/S4-02, LocalPlayer.aiStep -> LivingEntity.travel -> Entity.move/collide -> Level.getBlockCollisions queries block collision shapes. TrapDoorBlock#getShape selects a horizontal plate when closed and a vertical 3/16 side plate when open; BlockBehaviour#getCollisionShape supplies that shape to block-collision iteration. A player moving across the cell or the panel's occupied plane can therefore meet a different collision shape after the source-derived state difference. This is a concrete possible movement consequence from source, not a runtime trajectory claim.

Finding-specific dependencies are closed by D3 row 54's paired wire connection/state writers, paired wire signal output, A Level versus B SignalGetter correspondence, paired trapdoor signal consumer/state writer/shape, and S4-02's paired entity/block-collision path. Broader D3 and S5-02 inventories remain open at pair level.

## Consequence and uncertainty

Source proves a changed emitted-side-signal precondition and the downstream OPEN-state writer. The trapdoor shape selected for a fixed OPEN/FACING/HALF state is unchanged; the possible player-collision difference comes from changed vanilla block-state production. Project scope keeps vanilla block states and excludes redstone/block-state lifecycle emulation, so this is recorded as an explicit out-of-scope source finding rather than an eligible shape delta. Runtime validation was not performed. The endpoints show only that this behavior differs between 1.19.4 and 1.20.1; its first changed release is unknown within the interval.

## Handoff

Independent source description: trapdoor-aware redstone-wire output can make a powered trapdoor open in B under the stated preconditions, with a possible collision consequence for a player crossing the changed trapdoor shape. Related finding IDs: none. Applicability is limited to historical redstone wire and trapdoor states. Scope disposition: out of scope as changed block-state production; no implementation or runtime work is authorized by this source finding.