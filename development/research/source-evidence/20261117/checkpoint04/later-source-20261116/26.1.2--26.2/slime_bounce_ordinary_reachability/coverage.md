# Slime bounce below-gravity player path: 26.1.2 → 26.2

## Scope and conclusion

This is a source-only supplement to the accepted SlimeBlock bounce comparison at `build/later-source-20261116/26.1.2--26.2/slime_bounce_transition_revision_r2/coverage-r2.md` (SHA-256 `d6fd005ad2fd60c3d10645036c5286063eb645808bba560e776301004390453f`; independent ACCEPT `build/later-source-20261116/26.1.2--26.2/slime_bounce_transition_r2_audit/verdict-r2.md`, SHA-256 `AA55C0770A4AC1AE80D0F2764028946035D03D8E3D7743E7E9B36B30A88C505F`). It tests whether the newly added below-gravity gate can occur on an ordinary local-player movement path.

**Bounded source witness found.** In a normal level containing a carpet over a full block beside a SlimeBlock at the same block Y, a non-flying, non-passenger LocalPlayer can walk off the 1/16-high carpet onto the adjacent slime top. With ordinary unmodified LivingEntity defaults, the first airborne travel update produces downward velocity `-0.08 * 0.98F`, whose magnitude is strictly below effective gravity `0.08`. If the player's feet are then 1/16 above the slime top, the next move clips downward travel at the slime surface and reaches the new restitution threshold with `effectState` equal to the SlimeBlock. The old callback bounces for this negative Y velocity when the player is not sneaking; the new threshold zeros restitution. This witness does not depend on the `SUPPRESSES_BOUNCE` tag, because the strict below-gravity test alone selects zero restitution.

This establishes an ordinary map-state/input path from source conditions and block geometry; it is not a runtime trajectory test, a claim about natural world generation, or a frequency estimate. It uses default gravity and air-drag attribute values with no Slow Falling/Levitation effects or external attribute modifiers.

## Exact source identity

Both endpoints are canonical `unobfuscated` publications. Identity and ready-to-source-manifest bindings are reused from the accepted r2 Slime source report/audit; the source files used below were individually rehashed and matched their current source-manifest entries. No full-tree rehash was performed.

| Release | Ready SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Identity |
| --- | --- | --- | --- | --- | --- |
| 26.1.2 | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `versionId=26.1.2`, `namespace=unobfuscated`, ready |
| 26.2 | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` | `versionId=26.2`, `namespace=unobfuscated`, ready |

Exact sidecars are at each version root: `unobfuscated.ready.json`, `unobfuscated.provenance.json`, `unobfuscated.sources.sha256`, and `artifacts.sha256`; Java sources are under `unobfuscated/`.

## Normal block state and collision geometry

- `Blocks.CARPET` registers the vanilla wool carpet collection through `WoolCarpetBlock::new`; `Blocks.SLIME_BLOCK` registers `SlimeBlock::new` using `BlockItemIds.CARPET` and `BlockItemIds.SLIME_BLOCK` respectively (`Blocks.java:2976–2980, 3062–3067`). These are built-in block entries, not custom block states. Use a full supporting block at `(0,0,0)`, carpet at `(0,1,0)`, and SlimeBlock at `(1,0,0)`.
- `CarpetBlock.canSurvive` requires a non-empty block below (`CarpetBlock.java:49–52`), so that carpet state is supported by the full block. `getShape` returns `Block.column(16, 0, 1)`, a full X/Z footprint with height 1/16 (`CarpetBlock.java:17, 28–31`). The normal registration does not disable collision; `BlockBehaviour.Properties.hasCollision` defaults true (`BlockBehaviour.java:985`) and `getCollisionShape` returns `state.getShape(...)` when enabled (`327–329`). Thus the carpet surface is 1/16 above the top of the adjacent full SlimeBlock. A loaded map can contain both standard registered states; this report makes no claim that world generation creates this arrangement.

## Ordinary local-player path and threshold calculation

The accepted Slime r2 report already hash-binds the ordinary LocalPlayer tick → `LivingEntity.aiStep` → non-passenger `Player.travel` → `LivingEntity.travelInAir` → `handleRelativeFrictionAndCalculateMovement` → `Entity.move` chain at both endpoints. Its r2 audit ACCEPTed the exact added call path. The same movement helper applies horizontal input before calling `Entity.move` (`LivingEntity.java:2676–2679` in 26.2), so a nonzero ordinary direction input can take a player positioned at the carpet edge onto the neighboring slime cell.

For the bounded witness, use a non-flying local player, not sneaking, outside fluids, without Slow Falling/Levitation, and with no externally modified movement attributes. In 26.2:

1. `Attributes.GRAVITY` has base value `0.08`; `Attributes.AIR_DRAG_MODIFIER` has base value `1.0` (`Attributes.java:10–11, 55–57`). `LivingEntity.createLivingAttributes` includes both (`LivingEntity.java:332–357`). With no Slow Falling, `getEffectiveGravity()` returns `getGravity()` (`LivingEntity.java:2420–2427`); `Entity` has no-gravity false by default and `getGravity()` returns the LivingEntity gravity attribute (`Entity.java:319, 1521–1528`).
2. At the horizontal step that leaves the carpet/base support, requested vertical movement is zero. Local-player authority still updates `verticalCollision` and `onGround` from that movement; with no vertical clip, `verticalCollisionBelow` is false and the player is no longer grounded (`Entity.java:762–767`). The ordinary `travelInAir` branch then subtracts effective gravity after `Entity.move`; its default vertical drag is `computeModifiedFriction(0.98F, 1.0F)=0.98F` because ordinary players use the base `omnidirectionalAirMover=false` and `shouldDiscardFriction=false` (`Entity.java:862–864`; `LivingEntity.java:225, 676–681, 2480–2495`; `Attributes.java:10–11`). The resulting Y delta is `-0.08 * 0.98F`, approximately `-0.0784`; since the float `0.98F` is below 1, its magnitude is strictly below `0.08`.
3. Choose the local player's horizontal position/input so the full footprint has cleared the carpet/base block and is over the adjacent SlimeBlock while the feet are at `1 + 1/16`. On the next ordinary travel call, the downward request is approximately `-0.0784`; the distance to the slime top is `1/16 = 0.0625`, so the vertical collision clips movement at the slime surface. The post-move effect-state lookup reads the block under the player (`Entity.java:775–778`, `1045–1047`), which is the SlimeBlock in this setup. The new predicate compares `-currentMovement.y` with `getEffectiveGravity()` and selects zero restitution when `0.0784 < 0.08` (`Entity.java:815–819` in the accepted paired report). The below-gravity condition itself is sufficient; tag membership is irrelevant to this witness.
4. On 26.1.2, the accepted paired source report establishes that the same non-sneaking LivingEntity SlimeBlock callback reverses any negative Y delta, with no gravity-threshold gate. The constructed negative value therefore reaches the old bounce branch and differs from the 26.2 zero result.

The geometry can be arranged in an ordinary map and reached with the usual on-foot local-player input path. The exact client tick path and old/new callback formulas remain bound by the accepted r2 report/audit; this supplemental report closes the previously unstated below-gravity movement condition only.

## Limits

This is a source-level existence witness using built-in block states and default attribute values. It does not prove natural-world generation, survival inventory acquisition, the frequency of this transition, effective attributes under all server-synchronized modifiers, datapack/resource overrides, or runtime behavior. It does not need or assert `SUPPRESSES_BOUNCE` membership. The pair remains partial and is not frozen.

## Cited Java-file SHA-256

| Relative Java path | 26.1.2 | 26.2 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `net/minecraft/world/level/block/Blocks.java` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |
| `net/minecraft/world/level/block/CarpetBlock.java` | `ce669310bf7ab918e5bbdb07253b9030a516ae143cd4aa9e464bd7f2fdedffdc` | `ce669310bf7ab918e5bbdb07253b9030a516ae143cd4aa9e464bd7f2fdedffdc` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5` | `4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c` |
