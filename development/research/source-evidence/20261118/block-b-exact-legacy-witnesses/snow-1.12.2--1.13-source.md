# Independent source audit: snow layer one

## Scope and identity

Bounded slice: snow layer collision shape and its collision-query/movement consumer, exact sequence 1.11.2 → 1.12 → 1.12.1 → 1.12.2 → 1.13, all `ornithe-feather`. Sources read from the assigned canonical roots only. The 1.11.2 published source manifest identity is `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1.12.2 is `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13 is `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`. For legacy 1.12 and 1.12.1 roots the embedded `.decompile-provenance.json` SHA-256 values are `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` and `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`, respectively. Provenance identifies exact requested releases and Feather mappings; 1.12.2 and 1.13 mapping coordinates are Feather Gen2 build.2. Source bodies and names checked in every edge; no online mapping lookup used.

## Per-edge verdicts

- **1.11.2 → 1.12 — no layer-one collision delta.** `SnowLayerBlock.java` in both sides has the same nine-entry `Box[]` table and `getCollisionShape` body (`int i = LAYERS - 1`; return a new box copying X/Z and setting maxY to `i * 0.125F`). Layer 1 therefore yields `(0,0,0)-(1,0,1)` on both.
- **1.12 → 1.12.1 — no layer-one collision delta.** Same table and method body; both mapping roots verified with their embedded provenance hashes above.
- **1.12.1 → 1.12.2 — no layer-one collision delta.** Same table and method body.
- **1.12.2 → 1.13 — confirmed layer-one collision change.** 1.12.2 `SnowLayerBlock.java` lines 25–35 and 66–71 indexes the shape by `LAYERS`, then constructs an AABB whose top is `(LAYERS - 1) * 0.125F`; at layer 1 this is a zero-height box. 1.13 lines 25–35 and 68–77 indexes `SHAPES[LAYERS - 1]`; the first element is `VoxelShapes.empty()`, so layer 1 supplies no collision voxel.

## Collision and reachable local witness

Old `Block.addCollisions` (`1.11.2/.../block/Block.java`, lines 353–364) moves a non-sentinel collision AABB to block position and adds it when the broad-phase query intersects it. `Box.intersects` (`1.11.2/.../util/math/Box.java`, lines 245–251) uses strict interval tests, so a degenerate Y interval is included when the query spans across its plane. `Box.intersectY` (lines 205–221) still treats the plane as a vertical stop: for downward movement, if player `minY >= planeY`, it clips to `planeY - player.minY`. Old `Entity.move` gathers collisions from the expanded movement box and resolves Y via `intersectY` (lines 554–570); its player sneak-edge loops also query collisions (lines 522–548). This establishes narrow-phase behavior in addition to broad-phase collection.

Reachable fixed-state setup: lower snow at LAYERS=8 and upper snow at LAYERS=1 in the cell above. The old 1.12.2 and new 1.13 `canBePlaced` bodies explicitly allow snow supported by same-block LAYERS=8 (1.12.2 lines 84–92; 1.13 lines 83–91), and neither neighbor-survival rule removes this arrangement. Old collision shape for lower LAYERS=8 ends at 7/8 block (`SHAPES[8]` has visual top 1 but the collision constructor sets maxY to `(8-1)*0.125`); the upper LAYERS=1 degenerate plane sits at the upper block's bottom, 1/8 higher. A player falling across that cell is clipped to the upper plane in the old path. In 1.13 the upper shape is empty, so the voxel collision set has no upper-layer obstacle and the player reaches the lower layer's 7/8-height collision top. This is a reachable local vertical displacement difference of 1/8 block for a fixed state. It is a local source-derived witness, not a full trajectory or runtime parity claim.

In 1.13 `Entity.move` passes movement and the entity box into `WorldView.getCollisions` and resolves Y through voxel-shape axis clipping (`1.13/.../entity/Entity.java`, lines 580–584). `SnowLayerBlock.getCollisionShape` returns empty directly for layer 1. The source-level contrast closes the broad phase and axis consumer for this local case. Horizontal and sneak/step paths were inspected for the old shared collision query; no independent layer-one horizontal or sneak/step claim is made. Fluids, contact effects, state production and unrelated snow behavior are excluded.

## Draft

Independent candidate at `snow-layer-one-empty-collision.md`; full text is the immutable draft revision identified by the SHA-256 in `COMPLETED.md`.

## Limits

No implementation, tests, gameplay launch, Git/product/wiki inspection, or trajectory parity claim. Other snow-layer edges, provider shapes and layers 2–8 are outside this owned slice except the lower eight-layer support shape used to close the witness dependency.
