# 1.11.2 → 1.12.2 modifier/effect source audit

## Verdict

I found no changed equipment, attribute-modifier, potion-effect, effect-registration, or effect-update behavior that changes a player's movement in these endpoints. The player movement-speed, sprint modifier, jump/Jump Boost, levitation, Depth Strider, Frost Walker, elytra, and ability flight-speed rules are unchanged in the examined call paths.

One adjacent input-plumbing detail should be reconciled with the general movement-input slice: 1.12.2 expands `moveRelative`/`updateVelocity` to three inputs. For a player, the new `LivingEntity.mobTick` passes `(sidewaysSpeed, verticalSpeed, forwardSpeed)`, and `PlayerEntity.moveRelative` forwards those same values. The inherited `verticalSpeed` field is initialized to zero and the only write found in the 1.12.2 source tree is in `MobEntity`; `PlayerEntity` is not that subclass. The expanded `Entity.updateVelocity` therefore receives player horizontal side/forward inputs in the same effective axes and a zero Y input. It also executes `velocityY += 0.0` before movement. That can normalize a prior `-0.0` velocity to `+0.0`; I did not establish a resulting trajectory change or accept this as a separate movement finding. This is a source-level edge for the movement-input reviewer to decide against the parity target.

Exact changed movement call sites (1.11.2 line → 1.12.2 line): `Entity.updateVelocity` 1028–1043 → 1049–1067; `LivingEntity.moveRelative` 1388 onward → 1425 onward; `LivingEntity.mobTick` input dispatch line 1803 → 1847; `PlayerEntity.moveRelative` 1387–1405 → 1386–1403. In the 1.12.2 body, `updateVelocity(forwards, sideways, upwards, u)` receives the zero `verticalSpeed` as its `forwards` parameter, and adds that parameter to `velocityY` at `Entity.java:1064`.

This is a bounded slice audit only; it does not freeze or close the full pair.

## Exact inputs and integrity

Read-only sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.11.2\ornithe-feather` and `...\1.12.2\ornithe-feather`, mapped with Ornithe Feather Gen2 build 2 (`feather-gen2-1.11.2+build.2` and `feather-gen2-1.12.2+build.2`). Provenance records the exact releases, mapping artifacts, Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0, ASM 9.10.1, Mapping IO 0.9.1, Gson 2.14.0, and TinyRemapper 0.14.1.

The ready markers bind source-manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` (1.11.2, 1,921 files) and `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` (1.12.2, 2,050 files). The verification markers report all source files identical to a separate regeneration and zero source-file differences. I also verified the audited files against each source manifest. The verification marker notes one regenerated intermediate mapped-JAR hash mismatch per endpoint; it reports no raw-input artifact difference and confirms the published decompiled source trees match.

## Ordered movement-path coverage

1. **Player controls and speed values:** `PlayerAbilities.getFlySpeed`/`setFlySpeed`, client ability-packet application, `LocalClientPlayerEntity.mobTick` sprint/fly-key gates and elytra-start gate; `PlayerEntity.getSpeed`, air speed, and the `MOVEMENT_SPEED` base attribute. Ability class and S2C ability packet hashes are identical. Local-player file changes are recipe-book/UI/network-visibility additions; the control statements and fly-speed arithmetic are unchanged.
2. **Sprint, jump, and travel:** `LivingEntity.setSprinting`, `jump`, `moveRelative`, `mobTick`; `PlayerEntity.jump`, `moveRelative`, `getSpeed`; `Entity.updateVelocity`. Jump and sprint bodies are unchanged. The only relevant structural changes are the 3-axis input extension described in the verdict; player input remains horizontal with zero vertical input.
3. **Attributes, modifiers, and equipment:** `LivingEntity.initAttributes`, equipment replacement/tick synchronization, NBT load/save modifier application; `PlayerEntity.getEquipment`/`setEquipment`; `Item.getDefaultAttributeModifiers`; `ItemStack.getAttributeModifiers`; `AttributeModifier`, `EntityAttributeInstance`, `ModifiableEntityAttributeInstance`, `AbstractEntityAttributeContainer`, `EquipmentSlot`, `ArmorItem`, and `ElytraItem`. The modifier accumulation and equipment add/remove paths are unchanged. `EntityAttributes` adds the `generic.flyingSpeed` attribute in 1.12.2, but the only registrations of it found in the source tree are in `ParrotEntity`; player code does not register/read it. Player flight continues to use `PlayerAbilities.flySpeed`.
4. **Effect registration and application:** all seven classes under `entity/living/effect/` are present at both endpoints. `StatusEffect`, `StatusEffectInstance`, `StatusEffects`, and effect subclasses are byte-identical by source hash. `LivingEntity` status add/replace/remove/clear/tick and effect modifier application bodies have no source diff. Speed, Slowness, Jump Boost, Levitation, and other effect IDs/registry bindings are unchanged.
5. **Effect acquisition/update:** `PotionItem`, `SplashPotionItem`, `LingeringPotionItem`, `PotionEntity`, `AreaEffectCloudEntity`, `Potion`, `Potions`, `PotionUtils`, milk removal, `EntityStatusEffectS2CPacket`, `EntityRemoveStatusEffectS2CPacket`, `ClientPlayNetworkHandler.handleEntityStatusEffect`, and `handleEntityAttributes`. The changed potion-item lines add consume criteria; the thrown-potion change only routes fire extinguishing through a face-aware helper; `PotionUtils` replaces `Objects.firstNonNull` with equivalent `MoreObjects.firstNonNull`. Splash/cloud effect delivery and packet construction/application are unchanged. Client handler method bodies for status effects and attributes match at both endpoints.
6. **Enchantment equipment reads:** `EnchantmentHelper.getLevel`/`getDepthStriderLevel`, `DepthStriderEnchantment`, `FrostWalkerEnchantment`, `Enchantments`, plus the water travel branch and block-position Frost Walker callback in `LivingEntity`. The Depth Strider/Frost Walker hooks and formulas are unchanged. `ItemStack.getEnchantments` changes absent enchant NBT from `null` to an empty list; `EnchantmentHelper` removes null guards accordingly. The queried level remains zero for an empty list, so unenchanted player equipment retains the same movement result.

Useful source locations for that unchanged path: `LivingEntity.setSprinting` 1256/1295, `jump` 1361/1398, `addStatusEffect` 620/627 and `tickStatusEffects` 510/513; `PlayerEntity.getSpeed` 1408/1407; `ItemStack.getEnchantments` 407–409/413–415 and `getAttributeModifiers` 711–731/720–740; `EnchantmentHelper.getDepthStriderLevel` 186–188/183–185; `ClientPlayNetworkHandler.handleEntityStatusEffect` 1233–1246/1281–1294 and `handleEntityAttributes` 1741–1767/1789–1815. These identify the matching endpoint methods; offsets differ because unrelated code was added between releases.

## Relevant changed or added source classes

Changed classes in the audited call graph were inspected at the affected hunks: `Entity.java`, `LivingEntity.java`, `PlayerEntity.java`, `EntityAttributes.java`, `Item.java`, `ItemStack.java`, `Enchantment.java`, `EnchantmentHelper.java`, `PotionItem.java`, `PotionEntity.java`, `PotionUtils.java`, `MilkBucketItem.java`, `LingeringPotionItem.java`, and `LocalClientPlayerEntity.java`. The changes relevant enough to trace are described above; the remaining hunks are criteria, tooltip/creative-menu, source-formatting, or unrelated gameplay behavior. No equipment, attribute, potion/effect, enchantment, or client player-ability class was added or removed between the two source manifests. `RemoteClientPlayerEntity` changes `stepHeight` from `0.0F` to `1.0F`; that is outside this equipment/modifier/effect slice and needs separate ownership if remote-player collision behavior is audited.

## Hash anchors

SHA-256 is for the exact source files listed relative to each `ornithe-feather` root. Changed-file hashes are shown old → new:

| File | 1.11.2 → 1.12.2 SHA-256 |
|---|---|
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` → `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` → `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` → `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| `net/minecraft/entity/living/attribute/EntityAttributes.java` | `2246084b0b226db4dc22c0271b576194537c6993d583d25c494a144113306c5e` → `495f2845c167f9429611c847296cdc543cd37a6e55649ab07bbbd21ef92741da` |
| `net/minecraft/item/ItemStack.java` | `dc929fcc42e94dacb1f2d2a32572c00612c4f9b39d4cb551634fd3dd290467d8` → `61964c5fd859eb16cdaa548575a1f348ce73748f45d8671afc8dd317ba5eac26` |
| `net/minecraft/enchantment/EnchantmentHelper.java` | `be67a6c43e624fc5eebfe2f57b0680cf2b537c23f76df3ca2beb691071f7f8c4` → `226243b031824fbea660520a891f461272482808318787f6c04679e9a241d9c9` |
| `net/minecraft/entity/living/effect/StatusEffect.java` | `b7953929609d29d36fb4714013870f9d3b6362b71682f3c06b597536de3eb2db` → same |
| `net/minecraft/entity/living/effect/StatusEffectInstance.java` | `d3cd002f44ded4cd4f6045af6849d73470d56d05c59e62dee4dd99c2fc47f102` → same |
| `net/minecraft/entity/living/effect/StatusEffects.java` | `65a621ba5a80957f742aa14f109224088388340d0b28b94023440ad0f8f74dd2` → same |
| `net/minecraft/item/ArmorItem.java` | `9100b92291feb6848e12f710582d02f3265e02bcdaca9ee4b224f635451d1e02` → same |
| `net/minecraft/item/ElytraItem.java` | `16e02a70181c063c0643b7dfdbe17516ad46cd38053a2d88cc3fb97400652c7c` → same |
| `net/minecraft/network/packet/s2c/play/EntityStatusEffectS2CPacket.java` | `bb7a367f1f981e23203737519d9bda2e99b38256c22992cfe470eb775e727cfe` → same |

## Coverage limits

This report covers only the assigned player equipment/attribute/modifier/effect and adjacent input paths for these two exact endpoints. It is not an independent audit of every player tick dependency, block-shape/input source, every server-side producer, or any intermediate release boundary. No implementation, source generation, build, test, or game launch was performed.
