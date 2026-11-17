# Player sneaking collision dimensions (r2)

This immutable revision supplements `sneak-pose-dimensions.md` (SHA-256 `F10C1EE423EE913665B394E84DD340A41B8E7091A7C5E5314602733B345A09C1`). It binds the source evidence and narrows the claim to a grounded local player who is sneaking, is not sleeping or fall-flying, and has no colliding block shape in the proposed taller/shorter query volume.

## Exact source identity

- Pair: Minecraft 1.8.9 → 1.9, `ornithe-feather`.
- 1.8.9 source-manifest SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance SHA-256: `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 mapping-local `.decompile-provenance.json` SHA-256: `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.

The paired source-file hashes below match the 1.8.9 `ornithe-feather.sources.sha256` rows and the 1.9 provenance `sources` rows:

| Source | 1.8.9 SHA-256 | 1.9 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `b322144fcc965c9bf5f6ff18882d17f6f0e1cdaf0e545b1c7663c662aceb029b` | `36cbc6f0e7e2d47f4207d15130976860eac60b4ae6fac2ef2e8f5a9f719370d7` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` |
| `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |

## Local-player call path and timing

In both versions, `LocalClientPlayerEntity.tick()` first requires the current player chunk to be loaded, then calls `super.tick()` (1.8.9 lines 105–107; 1.9 lines 141–143). `LocalClientPlayerEntity` extends `ClientPlayerEntity`, which extends `PlayerEntity` on each side. In 1.9, `PlayerEntity.tick()` runs `super.tick()` at line 199 and later calls `updatePlayerPose()` at lines 244–245. Thus the pose query and any dimension change happen after that tick's superclass movement, and the changed box is available to later movement ticks. In 1.8.9, `PlayerEntity.tick()` calls `super.tick()` at line 228 and has no pose update; the ordinary awake player remains 0.6 × 1.8 while sneaking. The old `resetPos()` and `wakeUp()` paths also set 0.6 × 1.8, while the old 0.2 × 0.2 sleeping/death size transitions are unrelated to sneaking.

## Changed operation and gate

In 1.9 `PlayerEntity.updatePlayerPose()` (lines 284–308) selects 0.6F × 1.65F for sneaking after the fall-flying and sleeping branches, and 0.6F × 1.8F for standing. If the selected dimensions differ, it forms a proposed `Box` from the current box minimum corner and the selected width/height (lines 301–303). It calls the boolean `World.getCollisions(Box)` overload and changes dimensions only if that query is false (lines 304–305). The query scans block collision providers in its box; this report does not claim equivalence for every provider or every surrounding state.

`Entity.setSize()` in 1.9 (lines 256–266) updates the dimensions and rebuilds the shape from its existing minimum corner. Because both standing and sneaking use width 0.6F, this candidate changes height only; it does not itself move the player. For a grounded sneaking local player with an unobstructed proposed box, 1.9 lowers the collision-box maximum Y from 1.8 to 1.65 after movement for that tick. With the same inputs and block state, 1.8.9 keeps the 1.8-high box. This can change clearance and subsequent movement collision queries from the next movement tick onward.

## Bounded status and limits

The candidate is limited to the stated grounded, sneaking, awake, non-fall-flying player and an unobstructed pose-query volume. The code's sneaking branch itself is not gated by `onGround`; grounded is the bounded movement witness used here. Sleeping and standing dimensions match the listed 1.9 sizes; fall-flying is post-1.8 and excluded.

The pose boolean query (`World.getCollisions(Box)`, with no entity argument) is distinct from `Entity.move`'s entity-aware collision query. Block-shape providers in the proposed volume can veto the size change, so this r2 does not establish equivalence for obstructed boxes, arbitrary provider/state combinations, or full pair coverage. The accepted provider/query source slices remain scoped to their own claims; a pair-wide dependency audit is still required.
