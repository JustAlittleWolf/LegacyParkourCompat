# Blind source audit: powder snow / frost movement boundary

## Frozen scope

Exact Mojmap endpoint comparison: Minecraft 1.16.5 → 1.17.0. This freezes only the requested support boundary: powder snow block/state and entity metadata presence, the client-side inherited post-travel frost attribute modifier, and its ordinary following travel speed consumer and guards. No trajectory prediction is made. Damage, rendering, frost-state production, and other powder-snow mechanics are not evaluated.

## Source identity and integrity

- **1.16.5:** decompiled_minecraft/1.16.5/mojmap/. The version-root ready marker identifies version 1.16.5, mapping mojmap, status ready. The source manifest SHA-256 is 9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b, matching mojmap.ready.json. The artifact manifest SHA-256 is f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c, matching the ready marker. Client JAR SHA-256: 00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263. Official client mappings file client_mappings.txt SHA-256: 7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c.
- **1.17.0:** decompiled_minecraft/1.17/mojmap/, mapping family Mojmap. Embedded .decompile-provenance.json SHA-256: afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357. Pinned inputs identify version metadata URL ending /1.17.json (SHA-256 537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6), client JAR object 1cf89c77ed5e72401b869f66410934804f3d6f52 (content SHA-256 14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f), and official client-mappings object 227d16f520848747a59bef6f490ae19dc290a804 (content SHA-256 191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471).
- Relevant source files were rehashed against the 1.16.5 source manifest or the 1.17 embedded source entries; all matched:

| Source file | 1.16.5 SHA-256 | 1.17.0 SHA-256 |
|---|---|---|
| net/minecraft/client/player/LocalPlayer.java | 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b | 08dbb8615cc6c7776f35ce269223699d39f758d15786babfe11e497421c5f4d4 |
| net/minecraft/world/entity/LivingEntity.java | b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 | 8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed |
| net/minecraft/world/entity/Entity.java | absent | 81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef |
| net/minecraft/world/entity/player/Player.java | d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960 | 9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516 |
| net/minecraft/world/level/block/Blocks.java | 3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12 | 87d72a113a3f8937a6a585ef917a4fd29cc5b335c00a858f6800e38f3c1bf7a8 |
| net/minecraft/world/level/block/PowderSnowBlock.java | absent | 4069b3ef70e2d0ce146bbff79be54b2be99b7c7b32818c1eb45f11fd66f99aa3 |

## Frozen source findings

1. **Powder snow is absent in 1.16.5 and present in 1.17.0.** The 1.16.5 tree has no PowderSnowBlock.java, no Blocks.POWDER_SNOW registration, and a whole-tree identifier search for powder-snow, frozen-tick, and powder-snow speed-modifier symbols returned no matches. In 1.17, Blocks.java:3496–3498 registers "powder_snow" as PowderSnowBlock; PowderSnowBlock.java:32 declares the class. That class declares no block-state properties or state-definition override, so this check found a block addition, not a powder-snow block-state-property delta.
2. **1.17 has entity-side powder-snow flags and frozen-tick data.** Entity.java:200, 218–219, 239–240 declares and initializes synchronized integer DATA_TICKS_FROZEN, while isInPowderSnow and wasInPowderSnow are ordinary boolean fields. Entity.java:2059–2078 exposes frozen ticks/percentage and a 140-tick requirement. These identifiers are absent from the 1.16.5 source tree. This records metadata presence only; state production is outside this audit.
3. **The frost movement modifier is inherited by the local client player and runs after that tick’s travel.** LocalPlayer.java:80, 651, 812 gives LocalPlayer → AbstractClientPlayer; AbstractClientPlayer.java:23 gives AbstractClientPlayer → Player; Player.java:113, 489, 506 gives Player → LivingEntity and its aiStep() calls super.aiStep(). In LivingEntity.aiStep() (LivingEntity.java:2541–2556), this.travel(...) precedes this.removeFrost() and this.tryAddFrost().
4. **Exact modifier guards and operation:** LivingEntity.java:500–522. removeFrost() acts only when the movement-speed attribute exists and the powder-snow UUID modifier exists. tryAddFrost() requires !getBlockStateOn().isAir() and getTicksFrozen() > 0; it then returns if the movement-speed attribute is absent. It computes float f = -0.05F * getPercentFrozen() and adds a transient MOVEMENT_SPEED modifier with Operation.ADDITION. The method itself has no client-side, isInPowderSnow, or canFreeze() guard. Entity.getPercentFrozen() clamps frozen ticks to the required count before division (Entity.java:2067–2078).
5. **Following ordinary travel consumer and guards:** LivingEntity.travel(Vec3) (LivingEntity.java:1999–2000) runs only under isEffectiveAi() || isControlledByLocalInstance(). Its non-water, non-lava, non-fall-flying branch calls handleRelativeFrictionAndCalculateMovement(...) (:2151–2153), which passes getFrictionInfluencedSpeed(...) to moveRelative. For a grounded player speed is getSpeed() * (0.21600002F / (friction * friction * friction)); airborne travel uses flyingSpeed instead (LivingEntity.java:2196–2198). Player.getSpeed() reads getAttributeValue(Attributes.MOVEMENT_SPEED) directly (Player.java:1464–1465), so the post-travel modifier is a source-level input to a subsequent grounded ordinary travel. This states a consumer boundary only, not a predicted trajectory.

### Older-side ordinary speed consumer

The 1.16.5 Player extends LivingEntity (Player.java:111); its aiStep updates the movement-speed attribute-backed speed field after super.aiStep() (Player.java:483–506), and Player.getSpeed() directly reads Attributes.MOVEMENT_SPEED (Player.java:1433–1434). LivingEntity.travel(Vec3), getFrictionInfluencedSpeed, and getSpeed are present at LivingEntity.java:1914, 2106–2108, and 2110+. The old source retains an ordinary travel speed consumer, but has no powder-snow block, entity frozen-tick data, or powder-snow frost modifier. These endpoints do not establish an exact intermediate patch boundary.

