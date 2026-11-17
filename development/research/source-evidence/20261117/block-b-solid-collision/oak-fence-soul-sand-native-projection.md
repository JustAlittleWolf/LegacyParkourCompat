# 26.2 native projection for an Oak Fence beside Soul Sand

Exact 26.2 unobfuscated code corresponds to the bounded ordinary dry Oak Fence with one Soul Sand horizontal neighbor and air on each other side. The checked default target derives a connected native fence rail. The historical 1.13 result already corresponds to that target for ordinary placement/neighbor recomputation. A 1.13 restoration would add a rail that the target already supplies.

`SoulSandBlock.getCollisionShape` (lines 15, 26–29) is a 16×14×16 box. Its separate `getBlockSupportShape` (lines 31–34) returns a full cube. `BlockBehaviour.BlockStateBase.isFaceSturdy` (lines 892–897) dispatches through `SupportType.FULL` (lines 12–16); that tests `getBlockSupportShape`. The cache uses the same support predicate (lines 947–950). `FenceBlock.getStateForPlacement` (lines 79–96) and `updateShape` (lines 114–121) pass the adjacent side's full support to `connectsTo` (lines 60–69). The ordinary Soul Sand block is outside `Block.isExceptionForConnection` (lines 252–260) with the pinned built-in tags. Its side is sturdy in 26.2 even though its movement collision shape is shorter.

`FenceBlock` (lines 37–42) supplies a four-unit width and 24-unit collision height to `CrossCollisionBlock`. The latter's collision provider (lines 37–48,53–69,81–84) unions the 4×24 post with just the arm for the stored side. The post is X/Z 6/16 through 10/16 and Y 0 through 24/16. The north arm spans Z 0 through 8/16; the outer rail beyond the post spans Z 0 through 6/16. `Shapes.rotateHorizontal` (lines 404 onward) rotates that same geometry to each of the other sides. Thus removing the outer rail from an exact one-arm shape leaves the unchanged centered post, with no block-state write. A native post with no arm already has the no-arm shape and needs no overlay.

`BlockCollisions.computeNext` (lines 79–103) reads each loaded cell, calls the context-based shape provider with the world collision getter and translates the local shape by the cell position. That getter can verify neighbor availability without loading chunks. `Level.getChunkForCollisions` (lines 768–771) calls `getChunk(..., ChunkStatus.FULL, false)`. Absence of a loaded result cannot prove air. A bounded adaptation can require a dry Oak Fence, exactly one loaded Soul Sand neighbor, a loaded ordinary air cell in the other directions, the single corresponding native connection bit, and the exact matching native local post-plus-arm shape. Other neighbors, unusual state/shape combinations, unloaded cells, custom tags and explicit state history remain outside this projection. Wall shapes and `UP` are separate.

This is a static target projection. Applicability for any historical **no-arm** release range depends on the separately accepted reversion boundary.

## Verified source and resource identities

26.2 unobfuscated source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. The following exact source hashes match that manifest:

- `net/minecraft/world/level/block/SoulSandBlock.java` `6be030c181dd96298c219e6001eb69f05468531c22cbab0df2b7a066df74a9fc`.
- `net/minecraft/world/level/block/FenceBlock.java` `be5e1aa19a07d68457ff21968fb4e264669588fb6e83a86a6f51e76833f17f93`.
- `net/minecraft/world/level/block/SupportType.java` `ea19a02f2559afe4a8e4c0f6e12cd1480abb0f62fe6853e37fa93771bd7dbedc`.
- `net/minecraft/world/level/block/Block.java` `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab`.
- `net/minecraft/world/level/block/CrossCollisionBlock.java` `dcd2dbbcd093a68dee4ee282e5f3101c519d633f3f173dd33383bd4b548c1ba5`.
- `net/minecraft/world/level/block/state/BlockBehaviour.java` `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e`.
- `net/minecraft/world/level/BlockCollisions.java` `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`.
- `net/minecraft/world/level/Level.java` `1ae4f565ad909f4e90786089b5b4f125e8a7d18e2ef7d8c376172a5787fc5639`.
- `net/minecraft/world/phys/shapes/Shapes.java` `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891`.

The 26.2 client JAR SHA-256 is `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`, matched against the release artifact manifest. Default tag entries in that exact JAR:

- `data/minecraft/tags/block/shulker_boxes.json` `9bc43c47d90eb246371c67b7d5b631ceed7acdaf489be766f5f03b32cae9d12a`.
- `data/minecraft/tags/block/fences.json` `214462701d11703b222317fb7d470caa40e017b5036b127db242986b92fb30cd`.
- `data/minecraft/tags/block/wooden_fences.json` `b688d023f03758224cfd1bf8b50d018c287aab3bddd939a00abd699fea7ac11f`.
