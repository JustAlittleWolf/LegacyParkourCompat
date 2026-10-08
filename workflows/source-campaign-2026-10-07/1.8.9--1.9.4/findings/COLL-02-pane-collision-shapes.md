# COLL-02: pane collision boxes differ by connection mask

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Source snapshot revision: `feather-r1-2026-10-07`; A immutable mapped JAR SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`; B immutable mapped JAR SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. The original derived JARs are unavailable; equivalence is unproven.
- Mechanic / coverage slice IDs: PaneBlock collision geometry; four-neighbor connection mask; `COLL-02`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player movement through iron bars, clear glass panes, or stained glass panes when the pane collision query intersects one of the differing box unions below
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A: source manifest A; `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/block/PaneBlock.java`; `PaneBlock#addCollisions(World, BlockPos, BlockState, Box, List, Entity)`, lines 62-92; `#shouldConnectTo(Block)`, lines 134-141; SHA-256 `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61`. `Block.java` registers `iron_bars`, `glass_pane`, and `stained_glass_pane` at lines 1040-1041 and 1180; SHA-256 `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`. `StainedGlassPaneBlock` inherits `PaneBlock`; SHA-256 `08f8a8d122b7d4cfc2825565e13f47eb36a66b7bdefa7a664a8b769b25b9cf56`.
- B: source manifest B; `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/block/PaneBlock.java`; `PaneBlock#addCollisions(BlockState, World, BlockPos, Box, List, Entity)`, lines 53-70; `#resolveVirtualProperties(BlockState, WorldView, BlockPos)`, lines 104-110; `#shouldConnectTo(Block)`, lines 133-140; shape table lines 25-40 and mask mapping lines 73-101; SHA-256 `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8`. `Block.java` registers `iron_bars`, `glass_pane`, and `stained_glass_pane` at lines 950-952 and 1099; SHA-256 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`. `StainedGlassPaneBlock` inherits `PaneBlock`; SHA-256 `f2c547b87cb112ec2663140e3d46d328ba8f02c252ec54aacbf82136ccf045ea`.
- Normal player movement collision path: A `World#getCollisions(Entity, Box)` enumerates block cells and dispatches each block state's `addCollisions` at lines 891-923 (dispatch line 921); `Entity#move(DDD)V` calls this query for movement and step candidates at lines 439 and 469. B `World#getCollisions(Entity, Box)` dispatches the state collision method at lines 899-935 (line 933); `Entity#move(DDD)V` calls it at lines 509 and 542 before axis clipping. This is the player-reachable movement path; `World#getBlockCollisions(Box)` is not cited as its caller. `World.java` SHA-256 A/B `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` / `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`; `Entity.java` SHA-256 A/B `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` / `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`.
- Reachable mask witnesses: on both sides, `air` is registered at ID 0 and `stone` at ID 1 in `Block.java` (same side-specific hashes above). A `Block#isOpaque()` reads `opaqueCube`; `AirBlock#isSolidRender()` is false; stone keeps the solid default. B's default `Block#isCube(BlockState)` is true; `AirBlock#isCube(BlockState)` is false. A `AirBlock.java` SHA-256 `19626b89ecc4a776b74dec89c3743d61abe3ca813c3e178dbfeac33513824ca7`; B `AirBlock.java` SHA-256 `e72664bc5d2a7b1e647e136e12ccd78f74dd9a3ac698e00be18a9e2e0029d2cf`.

## Source-level difference

For each pane collision query, A samples north, south, west, and east neighbors. It conditionally emits an east-west strip and a north-south strip, selecting full-length or half-length pieces based on opposing connection booleans. With no connected neighbors it emits both full strips. B resolves the same four virtual connection properties, always submits the center post, then submits one arm for each true direction. Boxes are clipped to the supplied collision-query box on both sides.

The neighbor predicate also differs: A accepts an opaque neighbor, this pane, glass, stained glass, stained glass pane, or any `PaneBlock`; B accepts a neighbor whose default state is a cube, this pane, the same explicit glass cases, or any `PaneBlock`. Each direction is sampled independently. Stone supplies a true connection and air a false connection under both predicates, so all sixteen N/E/S/W masks are reachable on both sides. The table compares collision assembly for the same mask; it does not infer mask equality for every other neighboring block class.

Notation: `X` is an east-west strip with z in `[0.4375, 0.5625]`; `X-W` and `X-E` restrict x to `[0, 0.5]` and `[0.5, 1]`. `Z` is a north-south strip with x in `[0.4375, 0.5625]`; `Z-N` and `Z-S` restrict z to `[0, 0.5]` and `[0.5, 1]`. `P` is B's center post (`x/z` each `[0.4375, 0.5625]`). B adds the named direction arms, each joined to `P` and reaching the corresponding block edge. The A column lists emitted pieces; the B column lists `P` plus all arms selected by the mask.

| Reachable mask | A collision pieces | B collision pieces | Union comparison |
| --- | --- | --- | --- |
| none | `X + Z` | `P` | different |
| N | `Z-N` | `P + N` | different |
| E | `X-E` | `P + E` | different |
| N+E | `X-E + Z-N` | `P + N + E` | different |
| S | `Z-S` | `P + S` | different |
| N+S | `Z` | `P + N + S` | same |
| E+S | `X-E + Z-S` | `P + E + S` | different |
| N+E+S | `X-E + Z` | `P + N + E + S` | different |
| W | `X-W` | `P + W` | different |
| N+W | `X-W + Z-N` | `P + N + W` | different |
| E+W | `X` | `P + E + W` | same |
| N+E+W | `X + Z-N` | `P + N + E + W` | different |
| S+W | `X-W + Z-S` | `P + S + W` | different |
| N+S+W | `X-W + Z` | `P + N + S + W` | different |
| E+S+W | `X + Z-S` | `P + E + S + W` | different |
| N+E+S+W | `X + Z` | `P + N + E + S + W` | same |

Thus the collision union differs for thirteen masks and is equal for the north-south pair, east-west pair, and all-four-connected mask. The zero-connection case is one changed case, not the full scope.

## Reachability and dependencies

Both releases register iron bars and clear glass panes directly as `PaneBlock`; stained glass panes use `StainedGlassPaneBlock`, which inherits the same collision implementation. These blocks existed on both sides. No B-only block or block-state behavior is required for this finding.

The normal world collision collection calls the registered block state's collision method for blocks in the query region. Player movement reaches `Entity#move(DDD)V`, which calls `World#getCollisions(Entity,Box)` before axis clipping and for step candidates. The local-player collision path is therefore source reachable. The mask producer is closed through all four neighbor reads and the connection predicate. The table explicitly accounts for every reachable mask using independent stone/air choices; the changed neighbor predicate may additionally change which mask a particular non-stone/non-air neighbor produces, which is not needed for the conditional geometry result above.

## Consequence and uncertainty

For a player collision query intersecting a pane-derived shape in any of the thirteen differing masks, A and B can supply different collision-box unions to the shared movement clipping code. The code proves the box inputs differ; it does not claim a particular resulting displacement or trajectory. The exact first release of the change is unknown within this interval. Rendering and non-player or vehicle outcomes are outside this finding.

## Handoff

Independent delta: PaneBlock collision-box assembly changed from conditional crossing strips to a center post plus mask-selected arms. Runtime validation is deferred.
