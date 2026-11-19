# Sweet berry bush direct player slowdown, 26.1.2 to 26.2

No difference in the berry multiplier, callback applicability to the local player, or the direct consumption of the multiplier. No movement finding for this slice.

## Paired source trace

All paths are relative to `decompiled_minecraft/<version>/unobfuscated/net/minecraft/`. The pairs of line ranges are 26.1.2 and 26.2 respectively.

| Operation | File and member | 26.1.2 | 26.2 |
| --- | --- | ---: | ---: |
| Block registration | `world/level/block/Blocks.java`, `SWEET_BERRY_BUSH` | 5582–5593 | 4612–4623 |
| Registered block ID | `references/BlockItemIds.java`, `SWEET_BERRY_CROP`, and `world/level/block/Blocks.java`, `register` | String at 5583 | 746 and 5980–5982 |
| Travel then inside-effect processing | `world/entity/LivingEntity.java`, `aiStep` | 3070–3078 | 3137–3145 |
| Local authority | `world/entity/player/Player.java`, `isLocalClientAuthoritative`, and `client/player/LocalPlayer.java`, `isLocalPlayer` | 1260–1275 and 378–380 | 1281–1296 and 378–380 |
| Movement list, effects and inside sweep | `world/entity/Entity.java`, `applyEffectsFromBlocks`, `isAffectedByBlocks`, `checkInsideBlocks` | 839–850, 885–915, 1195–1295 | 903–914, 949–979, 1278–1378 |
| Swept candidate enumerator | `world/level/BlockGetter.java`, `forEachBlockIntersectedBetween` | 175 onward | 175 onward |
| Inside shape and block dispatch | `world/level/block/state/BlockBehaviour.java`, `getEntityInsideCollisionShape`, state forwarding | 337–339, 688–690, 774–778 | 331–333, 694–696, 780–784 |
| Direct callback | `world/level/block/SweetBerryBushBlock.java`, `entityInside` | 82–103 | 82–103 |
| Flight gate and impulse grace | `world/entity/player/Player.java`, `makeStuckInBlock`, and `world/entity/LivingEntity.java`, `tryResetCurrentImpulseContext`, `resetCurrentImpulseContext` | 1494–1500 and 1802–1816 | 1515–1521 and 1835–1848 |
| Fall-distance reset and pending multiplier write | `world/entity/Entity.java`, `resetFallDistance`, `makeStuckInBlock` | 2841–2843, 2876–2879 | 2908–2910, 2943–2946 |
| Local move dispatch and consumption | `client/player/LocalPlayer.java`, `move`, and `world/entity/Entity.java`, `move` | 986–994 and 704–731 | 986–994 and 711–738 |
| Vector component arithmetic | `world/phys/Vec3.java`, constructor and `multiply` | 65–69, 160–166 | 65–69, 160–166 |

The bush uses `.noCollision()` and inherits the full-block inside shape, including at age zero. The 26.2 `BlockItemId` still produces the `sweet_berry_bush` block ID. The local player's block-effect sweep follows the travel branch and runs for the authoritative local player. The same removed/no-physics and alive gates, swept-candidate enumeration, per-position deduplication, and inside-shape test govern dispatch. The full-block inside shape passes the direct shape check for a visited candidate. The bush ignores the precision flag. Its callback immediately calls `makeStuckInBlock` for living entities other than foxes and bees, before checking the server and age for damage.

For an ordinary local player entering the bush, flight is off, so `Player.makeStuckInBlock` delegates to `Entity`, which resets fall distance to double zero and overwrites the pending vector with `new Vec3(0.8F, 0.75, 0.8F)`. Each horizontal float widens to double (`0.800000011920929`); the Y component is the double literal `0.75`. The player attempts to reset impulse context regardless of flight, with the same zero-grace condition and impact-position clearing. The old extra field clear affected only the server's fall-after-explosion advancement path (26.1.2 `server/level/ServerPlayer.java` 846 and 1295).

On a subsequent ordinary self-move the non-no-physics branch compares multiplier length squared with double `1.0E-7`, multiplies requested delta by its matching components in double precision, clears the stored multiplier, and sets delta movement to `Vec3.ZERO` before edge backoff and collision. A piston move first limits its requested delta and returns early if zero; an accepted piston move consumes the pending multiplier and zeros velocity without scaling the piston delta. These operations are identical on both sides. While flying, the callback does not write the multiplier or reset fall distance through this path.

The downstream collision response changes in 26.2 `Entity.move` after the shared scaling and before final velocity settles. In the newer version the response calls `restituteMovementAfterCollisions` (starting at 802) under its own movement/collision gates. This shared collision operation can affect the trajectory after bush scaling, and belongs to the collision slice. Generic travel, other inside-block responses, health/damage, and collision restitution were outside the bounded comparison.

## Exact source identities

Both publications identify the concrete client release, published `unobfuscated` names (no remapped namespace), Vineflower 1.12.0 and Java 25.0.3+9-LTS. Ready-marker manifest hashes were recomputed. All source digests below were recomputed and matched their corresponding source manifests.

| SHA-256 | 26.1.2 | 26.2 |
| --- | --- | --- |
| Client JAR, from artifact identity | b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0 | 40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290 |
| Source manifest | 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 | a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894 |
| Artifact manifest | 89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92 | ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba |
| SweetBerryBushBlock.java | ca930f1c40d72a03e55e9972a58b4cc3df645ea918625125b4255343bf893a1d | 39883c99abc12e6f6b654f025a2340396f86fea4e9bea6d9f5283172ef61699b |
| Entity.java | 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf | 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5 |
| Player.java | 44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d | 8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531 |
| LivingEntity.java | c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd | 7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a |
| LocalPlayer.java | 433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe | 8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6 |
| Blocks.java | ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f | f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435 |
| BlockBehaviour.java | 9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13 | 9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e |
| Vec3.java | 57b08ae818868a4fffdc9b5dadba2b127138c8945c2194f6f4b01e6150cd3bcb | 026c8461113b32116f0183ec42fa1cb4c7414cec38b685484d2309f9a0cb9a33 |
| BlockGetter.java | 623a44206db93d8fb2eefc9353345689cc35e0c1742b5d61789eab2b098b687d | 623a44206db93d8fb2eefc9353345689cc35e0c1742b5d61789eab2b098b687d |
