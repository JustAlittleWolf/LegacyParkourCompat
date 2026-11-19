# Horizontal wall collision writes a different zero sign

Independent blind endpoint verdict: confirmed for ordinary local-player wall collision at default attributes. Mapping namespace is published unobfuscated on both sides (Vineflower 1.12.0, Java 25). The paired source manifests were hashed and compared to the ready markers. Each listed file was hashed and compared to its source manifest. The artifact manifests identify client JAR SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` (26.1.2) and `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290` (26.2).

## Compared source

Paths below start at `decompiled_minecraft/<release>/unobfuscated/net/minecraft/`.

| File / operation | 26.1.2 | 26.2 |
| --- | --- | --- |
| `client/player/LocalPlayer.java`: loaded local tick, local player identity, `aiStep` tail, and `move` | 227–250, 378–380, 914–919, 986–994 | same |
| `client/player/AbstractClientPlayer.java`: `tick` and `aiStep` dispatch | 44–50, 75–90 | same |
| `world/entity/player/Player.java`: `tick`, `aiStep`, simulation/AI, `travel`, speed factor | 231–284, 442–482, 1260–1276, 1381–1404, 1833–1835 | 232–285, 442–482, 1281–1297, 1402–1425, 1854–1856 |
| `world/entity/LivingEntity.java`: default ground speed factor, travel selection/drag and collision-returned velocity | 491–493, 2392–2400, 2430–2453, 2623–2633 | 510–516, 2430–2438, 2468–2497, 2676–2686 |
| `world/entity/LivingEntity.java`: default attributes and restitution lookup | 322–340 | 336–358, 2201–2204 |
| `world/entity/LivingEntity.java`: near-zero clamp before travel, post-travel effects/push and enclosing tick tail | 2993–3016, 3058–3111, 2738–2800 | 3060–3083, 3125–3178, 2804–2866 |
| `world/entity/Entity.java`: collide call/order, flags, response, after-move block speed factor | 704–800 | 711–843 |
| `world/entity/Entity.java`: speed factor and collision clipping with step gate | 1021–1030, 1077–1106, 1161–1176 | 1085–1094, 1141–1170, 1244–1259 |
| `world/entity/Entity.java`: block effect invocation and deflated target, local authority, velocity getters/setters | 839–913, 1232–1282, 3516–3535, 3664–3682 | 903–977, 1315–1365, 3589–3608, 3737–3755 |
| `world/phys/Vec3.java`: direct component assignment, multiplication, axis replacement | 65–69, 164–166, 285–290 | 65–69, 164–166, 289–294 |
| `world/phys/shapes/Shapes.java`, `world/phys/shapes/VoxelShape.java`: collider dispatch and positive/negative axis clipping | 233 onward, 252–309 | 234 onward, 252–309 |
| `util/Mth.java`: double collision comparison | 161–163 | 162–164 |
| `world/level/block/Blocks.java`: stone registration | 56 onward | same |
| `world/level/block/state/BlockBehaviour.java`: collision provider, inside callback, speed/friction property defaults | 333 onward, 375 onward, 986–987 | 327 onward, 377 onward, 992–993 |
| `world/level/block/Block.java`: ordinary step response and speed factor | 463 onward, 502–508 | 459 onward, 498–504 |
| `world/entity/ai/attributes/Attributes.java`: registered base values | 64–66 | 10–12, 31, 52–54, 74–76 |

## Witness and consequences

Take a loaded, alive, ordinary standing local player with no input, external effects, nearby entities, attachments, vehicle, auto-jump, fluids or special blocks. In empty air, start at (1.6875, 80.25, 0.5) with current movement (+0.0625, +0.015625, +0.0). Install a tall full-stone wall whose west face is x=2 with ample overlap in Y and Z. The standing box has approximately 0.0125 clearance in X. The current movement survives the pre-travel near-zero cutoff, and zero input leaves the positive X. The airborne full wall clips X by approximately 0.05, beyond the double comparison epsilon, with no step-up or vertical collision. The two releases apply the same clipped displacement. For a westward control, begin at (0.3125, 80.25, 0.5) with X velocity -0.0625 against the east face at x=0.

For eastward impact, 26.1.2 explicitly stores the blocked X as `0.0` (raw bits `0000000000000000`). 26.2 computes `-currentMovement.x * restitution` and uses `Vec3.with` to store it. The living-entity bounciness attribute defaults to positive zero, making the blocked X negative zero (raw bits `8000000000000000`). Despite the signed result, the bounce event/sync gate is false with zero restitution and there is no vertical collision. Westward impact stores positive zero on both sides. Vec3 construction and the finite velocity setter preserve the bit. Positive block speed factor (1.0F for air/stone) and in-air horizontal friction (0.91F with default modifiers) preserve the sign. Auto-jump is disabled and the remaining position-based, block, pose and pushing work leaves this velocity untouched under the witness conditions. The sign therefore remains in `getDeltaMovement().x` on return from this local player's tick. On the next ordinary tick the player horizontal near-zero clamp writes positive zero to both X and Z, provided intervening effects have not changed the witness state. The tick-level divergence is a raw velocity state difference; this witness establishes no continuing position divergence.

The local-player source path and the default-attribute witness are closed for these two endpoints. Patch introduction within the interval, profile dispatch on product versions, wider callback/movement paths, non-default modern bounciness, and runtime parity were not assessed. Scope here is the horizontal collision axis write, separate from vertical landing response.

## Verified file identities

```text
26.1.2 source manifest 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0
8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf *net/minecraft/world/entity/Entity.java
c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd *net/minecraft/world/entity/LivingEntity.java
44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d *net/minecraft/world/entity/player/Player.java
433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe *net/minecraft/client/player/LocalPlayer.java
7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc *net/minecraft/client/player/AbstractClientPlayer.java
8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5 *net/minecraft/world/entity/ai/attributes/Attributes.java
57b08ae818868a4fffdc9b5dadba2b127138c8945c2194f6f4b01e6150cd3bcb *net/minecraft/world/phys/Vec3.java
cfa63cbed77c922a68e7e8a6a3b78b09a22c7de51e0146e481e9c9d26db66f58 *net/minecraft/world/phys/shapes/Shapes.java
419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854 *net/minecraft/world/phys/shapes/VoxelShape.java
19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33 *net/minecraft/util/Mth.java
1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977 *net/minecraft/world/level/block/Block.java
ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f *net/minecraft/world/level/block/Blocks.java
9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13 *net/minecraft/world/level/block/state/BlockBehaviour.java
26.2 source manifest a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894
7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5 *net/minecraft/world/entity/Entity.java
7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a *net/minecraft/world/entity/LivingEntity.java
8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531 *net/minecraft/world/entity/player/Player.java
8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6 *net/minecraft/client/player/LocalPlayer.java
7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc *net/minecraft/client/player/AbstractClientPlayer.java
4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c *net/minecraft/world/entity/ai/attributes/Attributes.java
026c8461113b32116f0183ec42fa1cb4c7414cec38b685484d2309f9a0cb9a33 *net/minecraft/world/phys/Vec3.java
6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891 *net/minecraft/world/phys/shapes/Shapes.java
419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854 *net/minecraft/world/phys/shapes/VoxelShape.java
30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17 *net/minecraft/util/Mth.java
cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab *net/minecraft/world/level/block/Block.java
f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435 *net/minecraft/world/level/block/Blocks.java
9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e *net/minecraft/world/level/block/state/BlockBehaviour.java
```
