# Independent audit — sneaking pose dimensions r2

**Verdict: ACCEPT for the bounded conditional source finding.** This verdict binds the immutable candidate `tick-state/sneak-pose-dimensions-r2.md` (SHA-256 `1C35EFF755217612950467848925D2A8FA1726A94AE45715DBEBFA4E87320369`) and its predecessor `tick-state/sneak-pose-dimensions.md` (SHA-256 `F10C1EE423EE913665B394E84DD340A41B8E7091A7C5E5314602733B345A09C1`). It accepts the stated unobstructed local-player witness only. It does not accept equivalence for arbitrary collision-provider states, freeze the pair, or establish implementation/runtime parity.

## Identity and source verification

The 1.8.9 source-manifest sidecar hash is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; its provenance sidecar hash is `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`. The 1.9 mapping-local provenance hash is `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`. These exact sidecar identities were recomputed. All ten paired Java-file hashes listed by the candidate were recomputed and match both source files and their sidecar rows:

| Paired source | 1.8.9 SHA-256 | 1.9 SHA-256 |
| --- | --- | --- |
| `LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` |
| `ClientPlayerEntity.java` | `b322144fcc965c9bf5f6ff18882d17f6f0e1cdaf0e545b1c7663c662aceb029b` | `36cbc6f0e7e2d47f4207d15130976860eac60b4ae6fac2ef2e8f5a9f719370d7` |
| `PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` |
| `Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |

## Audited behavior

- In both releases `LocalClientPlayerEntity` extends `ClientPlayerEntity`, which extends `PlayerEntity`. Its `tick()` requires the current player chunk to be loaded before it calls `super.tick()` (1.8.9 lines 105–107; 1.9 lines 141–143). `ClientPlayerEntity` does not override `tick()`.
- In 1.9, `PlayerEntity.tick()` calls its superclass movement before calling `updatePlayerPose()` at lines 244–245. The pose method selects 0.6F × 1.65F when sneaking, after the fall-flying and sleeping branches; standing is 0.6F × 1.8F. No `onGround` gate appears in this method; grounded is correctly described as the witness condition, not a code gate. In 1.8.9 there is no pose update in the player tick, and the ordinary player size remains 0.6F × 1.8F while sneaking; the other player `setSize` calls found are reset, sleeping/death, and wake-up paths.
- When selected dimensions differ, 1.9 forms the candidate box from the current box minimum corner and the selected dimensions (PlayerEntity lines 301–303). `World.getCollisions(Box)` is a block-only boolean query (World lines 1012–1044); if it finds no collision, `setSize()` rebuilds the box from the same minimum corner (Entity lines 256–266). Width is unchanged in the sneaking case, so the operation changes height without directly moving the player.
- Therefore, for a loaded-chunk local player who is grounded, awake, not fall-flying, sneaking, and has no colliding block shape in the proposed query volume, 1.9 lowers the collision-box maximum Y from 1.8 to 1.65 after that tick's superclass movement. Under the same block/input state, 1.8.9 retains the 1.8-high box. The changed box can affect later movement collision queries. The grounded condition is an illustrative movement witness; the source pose branch itself is not grounded-gated.
- The candidate correctly distinguishes this pose query from the entity-aware `Entity.move` collision query. Fall-flying is a post-1.8 pose and remains excluded.

Collision-provider equivalence for obstructed queries and arbitrary states remains open, as does full 1.8.9→1.9 coverage. This acceptance is limited to the conditional pose-size operation above.
