# Residual source coverage: movement attribute and equipment modifiers

Pair: Minecraft 1.8.1 → 1.8.2; Feather `feather-gen2` `1.8.1+build.2` → `1.8.2+build.2`; source roots `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.8.1,1.8.2}\ornithe-feather`. Read-only source comparison; no product implementation/history, wiki, backlog, tests, builds, launches, or decompilation. Pair provenance and mapping identity are inherited from the permitted `registry-packet-gates/report.md` and `core-scan.md`: `lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess`, Eclipse Adoptium 25.0.3+9 / Windows 11 amd64. Their source-tree audit reports 2,500/2,500 and 1,612/1,612 source files matching provenance. Every specific file hashed below also matched its exact side's `.decompile-provenance.json` `sources` entry.

## Player movement consumers

- `PlayerEntity.initAttributes` sets `MOVEMENT_SPEED` base to `0.1F` (old 133–136; new 131–134). In `mobTick`, both versions get that attribute; on server only set base to `abilities.getWalkSpeed()`; set air speed to flying speed plus 30% if sprinting; then call `setSpeed(attribute.get())` (old 443–453; new 449–459). `PlayerEntity.getSpeed()` returns the same attribute (old 1295–1298; new 1297–1300). No gate/order/value change.
- `LivingEntity.setSprinting` sets the superclass sprint flag, retrieves `MOVEMENT_SPEED`, removes any matching sprint UUID modifier, then adds it only when sprinting. UUID `662A6B8D-DA3E-4C1C-8813-96EA6097278D`, amount `0.3F`, operation 2 remains unchanged (old 59–60, 1028–1037; new 59–60, 1026–1035).
- Player jump uses `getJumpStrength() == 0.42F`, then if `JUMP_BOOST` applies adds `(amplifier + 1) * 0.1F`, before sprint horizontal impulse (old `LivingEntity.java` 1094–1105; new 1092–1103). The paired `EntityAttributes` registry has only max health, follow range, knockback resistance, movement speed and attack damage. There is no player jump-strength attribute. Horse-specific routes are non-player and excluded.
- The local player travel consumer uses `MOVEMENT_SPEED`; `ATTACK_DAMAGE` is used by combat and outside movement. This aligns with the already permitted core scan's player `getSpeed`/travel findings.

## Item, armor, equipment modifiers

Exact source search over both complete `net/minecraft` roots for `getDefaultAttributeModifiers(` found only `Item.java`, `SwordItem.java`, `ToolItem.java` definitions. Base `Item` returns an empty multimap. `SwordItem` and `ToolItem` add only `generic.attackDamage`, operation 0 (old Sword 98–102 / Tool 72–76; new Sword 102–106 / Tool 72–76). `ArmorItem` extends `Item` and has no override. No vanilla default item or armor modifier directly changes player travel or jump.

There is a separate custom-NBT route: `ItemStack.getAttributeModifiers()` reads an `AttributeModifiers` NBT list instead of defaults, decodes each modifier, rejects UUIDs with a zero most- or least-significant half, then keys each by its `AttributeName` (old 669–686; new 667–686). `EntityAttributes.fromNbt` decodes UUID/name/amount/operation. `AbstractEntityAttributeContainer.addModifiers` resolves the string against registered attributes, removes a same-UUID modifier and adds the new one. Since a player registers `generic.movementSpeed`, a custom-NBT item can name that attribute. This possibility is not an intrinsic default armor/item modifier and is present in both versions.

`LivingEntity.tick` performs equipment modifier changes only under `!world.isClient`, iterates equipment slots 0–4, and on an `ItemStack.matches` change removes old modifiers, adds current modifiers, then stores a copy (old 1261–1289; new 1259–1287). Player slot 0 resolves selected hand and slots 1–4 resolve armor; `getEquipment()` for serialized armor state returns the armor array. Same order/gate in both versions. The permitted packet-gates report checks the paired client attribute/equipment sinks; this slice additionally checked `TrackedEntity.sendDataAndAttributes` and initial tracker serialization, which send tracked attributes in `EntityAttributesS2CPacket`. The exact server-to-owning-local-player delivery for an equipment-derived custom movement modifier was not independently closed; no claim is made that this custom case necessarily changes local prediction.

## Effects, potions, beacon, enchantment

- Status registry definitions are unchanged: SPEED adds `MOVEMENT_SPEED +0.2F`, operation 2; SLOWNESS adds `-0.15F`, operation 2; JUMP_BOOST remains effect ID 8. Effect modifier scaling and add/remove/re-add order are unchanged; paired effect-instance state was also checked in the permitted core scan. `LivingEntity` status application/removal applies attribute modifiers under the same server-only `!world.isClient` gate. Client status-packet sink is covered by the permitted packet-gates report.
- Drinking a potion applies copies of its effect list only under `!world.isClient` in both versions (old/new `PotionItem.finishUsing` 72–84). Splash potion collision application matches: server gate, 4×2×4 grown region, distance `<16`, hit target receives full potency, instant-effect branch, duration scaled and added only when `>20` (old/new `PotionEntity.onCollision` 72–105). Beacon effects include SPEED and JUMP_BOOST; the same 80-tick update, server gate, primary/secondary selection and player application order hold (old/new `BeaconBlockEntity` 31–80). Relevant potion recipe duration/amplifier entries in `PotionHelper` match.
- DEPTH_STRIDER remains enchantment ID 8. `EnchantmentHelper` obtains the highest level from equipment (old 183–184; new 180–181); the unchanged water travel branch reads that level and directly changes water friction. It is a player-reachable movement input, not an attribute modifier. No enchantment declares an attribute modifier. `DamageEnchantment` adds SLOWNESS to arthropod targets in its combat wildcard hook in both (old/new 65–73); attack/damage calculation is excluded. The resulting effect state uses the status-effect route above.

## Verdict and limits

No 1.8.1 → 1.8.2 movement-semantic delta was identified in player speed/jump consumers, sprint modifier, default item/armor providers, NBT modifier parsing, equipment modifier ordering, movement-effect registry/application, potion drink/splash, beacon, or Depth Strider. Differences in these slices are generic annotations, local names and line offsets. No permanent finding proposed.

**Examined:** player speed base/getter and sprint modifier; player jump baseline/effect arithmetic; all `getDefaultAttributeModifiers` declarations; armor default provider absence; NBT item modifier parser and attribute container; equipment update/slot mapping; movement effect registration and status apply/remove/upgrade; drink and splash potions; beacon; Depth Strider lookup/consumer; relevant tracked attribute packet references.

**Partial / unexamined:** this is not a pair freeze. Owning-local-player delivery of server equipment-derived custom-NBT movement modifiers remains open. No complete audit of item registry constructors, every non-movement status-effect producer, or unrelated entity/item attributes is claimed. Server-side movement is excluded.

## Source SHA-256 (actual matched manifest; old | new)

| Relative source path | 1.8.1 | 1.8.2 |
|---|---|---|
| `net/minecraft/entity/living/LivingEntity.java` | `fbfb0f0821a2d6b9d1d665387b2c5423fc0c1d8f5f36f7b5e1258b26d90c4c4f` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `6c24523140f31d588214da9c9ce15ac7999446e1b647fc64194e695ebbd30d8e` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| `net/minecraft/item/Item.java` | `0f32dc43358d8728d9701d2de53e2c3ffe088f38317a65342d72e19e178dd29b` | `0b79c6167dd2093713c651f615cb2324213553c343fcf918f2cd90b2c81bc922` |
| `net/minecraft/item/ItemStack.java` | `a301ed6276eb21d85acb85bfc001ebbeb92e3afaec46dca4ec5be18f8ef33d0a` | `32917e0c486e8ba2a2dc9f91545c4f2a95c78e3a341f3ba1340993efac99c360` |
| `net/minecraft/item/ArmorItem.java` | `cf3894f41c43b85fdf3354829049ad3f5e08b4f3244f756f3c85202a4adc11ba` | `8f091739108f9edfaa5aa073d3cb6c8aa47dc80843060e9ce18c3b7dbd1b13c5` |
| `net/minecraft/item/SwordItem.java` | `03a8133d456a8547375a1952b161d4f8445f0528f91311573756fa851f45b214` | `f1415702666621b53848045bc45a90140226f1149577687efaaacaf2ed55db19` |
| `net/minecraft/item/ToolItem.java` | `9e468ca001c2ab1689676ed94db1c8b03e665ce0aef83239cda0a9795b5625da` | `4aa4f8cf5027a81ef75e22055a16815b69dda632a771784b5f5c74d7e581dd0f` |
| `net/minecraft/entity/living/attribute/EntityAttributes.java` | `b8926d7c46d56d643d8c2a2ff318bc40bd7bb2b2e7dfe8d8faaae5611cb13c09` | `945f39931934b378359d428a2f0fb0b57c97bb2e162dcec7e5d8562efaf663aa` |
| `net/minecraft/entity/living/attribute/ModifiableEntityAttributeInstance.java` | `e65eade8584e90d24a2cf4ff28fe0b3e1d6360cc03e1ffe41d42fa54805a0f18` | `3a8ddba11c80ee90640a1b0b85fd498d5e557459ed5b8bb64ea6a30ab7a0157d` |
| `net/minecraft/entity/living/attribute/AbstractEntityAttributeContainer.java` | `c88d27a60306fd3abba329bdd86d9f49a32ecc711ffcfd63ed0153f10c66376e` | `b1576cdc11ee7715080bfdd205601bf40f884af9efa8f5dc0eae07233c43786f` |
| `net/minecraft/entity/living/attribute/EntityAttributeContainer.java` | `3cc48ee81aeaf79ebb197864a60729f495f6404046c3ecdd8f4d25a144b9b094` | `63eac4b5c49e216a7bef6d03da42eba9737c6e2d215acbb6ee27c11dde89a506` |
| `net/minecraft/entity/living/effect/StatusEffect.java` | `6a555b196758c5906ffa854dec5a050757f6223785c9990718ed67148ba7d4f4` | `f9bb4d1839337229cb6f6d8dc9ae17a49cfad757a42388e00abbaa68be2b21a3` |
| `net/minecraft/entity/living/effect/StatusEffectInstance.java` | `1a36f6c852b036bb4cab7f8d114fb391a4b76eb2fc2b977c07d57309b1723666` | `6b69d6232532247f004c0f493ab619e02651d383c3c766bbc6e9c9ea8913e34e` |
| `net/minecraft/entity/living/effect/PotionHelper.java` | `adc9b423ddc07ba3f83217aeb9de6a1b775083e68bc5b488790255ee8666777d` | `d919ed977183acfe9f5a57871a6e7298177daa623426e2cf1bfe3a99b4596d21` |
| `net/minecraft/item/PotionItem.java` | `13cb857fe6e86bd33fe20b7bda1dba7138782758a8e44128862920eea88877c6` | `6c6303daa1176aaa9365073b5c01adfe8e5bd46b55666de34511c76f9457aa2a` |
| `net/minecraft/entity/projectile/PotionEntity.java` | `60373a9a998d58ca7e1f190fde88c34a10a3a73aaf8aef2ebad4d3e536a40933` | `ffb0306f3bba420d062c849f1f0f0c48488f0391dbe25c437da3ba048e2e66f0` |
| `net/minecraft/block/entity/BeaconBlockEntity.java` | `e41a1f4173c4f39b61276dfc90e65e2a39102f908fb760918b81cd5a741dee37` | `dc4a5b60ec087b2f4715769dfb242a20b319eab76309130b711cf767327ac361` |
| `net/minecraft/enchantment/Enchantment.java` | `4c7d8fc2d9bd47aa4f963b5267299d9599cd6b6af737bd85a523eb598e406481` | `f42e778392eddd8a4499b3ae074b059ad0984dcf4a1d775e5933914ae854dd74` |
| `net/minecraft/enchantment/EnchantmentHelper.java` | `5a1b9c99d508c0175256b90c66fcf60b01aa7b607c574bcb6c453de00d8507a7` | `16f46386c3e8fe19d7720d8e631111e82eb3f7260449bb867b401a24d73c244e` |
| `net/minecraft/enchantment/DepthStriderEnchantment.java` | `d0225daa535309d85a83284a69bb6499617dee89d3592542af25427e46496b5d` | `d0225daa535309d85a83284a69bb6499617dee89d3592542af25427e46496b5d` |
| `net/minecraft/enchantment/DamageEnchantment.java` | `c645e9ec8d0dcf31891b153b8cfe4a1e0030e8f4ed53dd988855cd250a87dbae` | `a4930c2b8e04898483b65ac45258adea5b5e0b0c89e73c72ce60f051ebde0f32` |
| `net/minecraft/server/TrackedEntity.java` | `f56e0f1bd8a25bb22008600ac79b6b5979c33c00138d8b06748d39d339f91025` | `c8f4c2115e2bd348c85be8cc4a35f18eece8edcce5560989dfa45cacb39f9b15` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `65fe3f74154996a5b94a9d5534a4805b40e13be245d50178b838ce0e79802118` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` |
| `net/minecraft/network/packet/s2c/play/EntityAttributesS2CPacket.java` | `18255edea43aff7c63d7b8f831a1cb8037a78ae5007f0972cc3f63f3443bff0d` | `b24f98567111cb698abb89770b24607000a6b8c5b2d2a970db076ee69e53ce41` |
| `net/minecraft/server/command/EffectCommand.java` | `31fab04948d3d323e183f8675b4f433879cebebef44c0ec762cc439d2d283c01` | `f5c4a5a3901c942c55acd7dd692648580aea0208d1310bbf6c94510b52d42136` |

The complete-root `getDefaultAttributeModifiers` search found only the three provider definitions listed above. A full-root search for relevant speed/slowness/jump IDs and `addStatusEffect` found potion, splash-potion, beacon, effect-command, network-sink, damage-enchantment, and non-player effect callers; non-player or non-movement callers were not audited as separate slices.
