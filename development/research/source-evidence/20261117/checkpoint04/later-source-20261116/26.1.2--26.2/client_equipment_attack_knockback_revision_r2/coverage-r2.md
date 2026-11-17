# Client equipment synchronization and ATTACK_KNOCKBACK modifiers

Pair: Minecraft 26.1.2 → 26.2. This bounded source slice asks whether the client can install an `ATTACK_KNOCKBACK` item modifier locally when an equipment stack is synchronized. It does not establish a concrete item/component value, `Player.stabAttack` invocation, gameplay occurrence, or full-pair coverage.

## Exact source identity

Both inputs are canonical `unobfuscated` publications at `decompiled_minecraft/26.1.2/unobfuscated/` and `decompiled_minecraft/26.2/unobfuscated/`. Actual version-root provenance sidecars are `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` (SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`) and `decompiled_minecraft/26.2/unobfuscated.provenance.json` (SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`); they declare exact version IDs and namespace `unobfuscated`. Ready sidecars are at each version root as `unobfuscated.ready.json`; their SHA-256 values are `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` and `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`. Both say `status=ready`, exact version ID and mapping. Each ready marker's `sourceManifestSha256` matches the version-root `unobfuscated.sources.sha256` file: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` and `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

All cited source-file hashes were recalculated from the canonical files and match their version-root source-manifest entries:

| Relative Java path | 26.1.2 SHA-256 | 26.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82` | `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/world/entity/Avatar.java` | `01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78` | same |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` | same |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `net/minecraft/world/item/ItemStack.java` | `26ba4af70893fae5107105ed46ed28cd3d51c7b4aba22bc8810f01b82177e146` | `c9d29d21e59a75fdeccdc68f115585114e2e489eed3b25148e3bbc61ce0bd0dc` |
| `net/minecraft/world/item/component/ItemAttributeModifiers.java` | `4fbf0507be1a289df08782751b9e647acd1631cbf304f00638d80f7e1337e80b` | same |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `ca262f3d973642a2887b235a549b14f335706413f9551b417059e72e6bcf8cfd` | `c7cbe38bfdc1b4aafcf623bd1b604200cc6190641091369ee9bb9303e8f07ad5` |

## Client-side synchronized-stack path

`ClientPacketListener.handleSetEquipment` first enforces packet-thread execution, resolves the target entity, and iterates packet slots in order, calling `LivingEntity.setItemSlot(slot, stack)` (`ClientPacketListener.java:1512–1515` in 26.1.2; `:1510–1515` in 26.2). If the target is the local player, it is a `LivingEntity` and enters this same writer. `LivingEntity.setItemSlot` replaces the stored equipment stack and calls `onEquipItem` (`LivingEntity.java:2257–2258` / `2294–2295`). The shared `onEquipItem` body gates sound and equip/unequip events on `!level().isClientSide()` and does not enumerate/apply attribute modifiers (`LivingEntity.java:665–682` / `688–707`). Neither `Player` nor `LocalPlayer` declares a `setItemSlot` or `onEquipItem` override in these source trees; `LocalPlayer → AbstractClientPlayer → Player` inherits the `LivingEntity` writer.

`ClientPacketListener.handleSetPlayerInventory` is a distinct route: it calls `minecraft.player.getInventory().setItem(packet.slot(), packet.contents())` after the packet-thread gate (`ClientPacketListener.java:1448–1452` in 26.1.2; `:1446–1450` in 26.2). That writes the player inventory slot, not `LivingEntity`'s equipment slot or its attribute map.

## Where equipment modifiers are applied

`ItemStack.forEachModifier(EquipmentSlot, consumer)` reads the stack's `ATTRIBUTE_MODIFIERS` component, visits the slot-matching entries, then forwards slot-matching enchantment modifiers (`ItemStack.java:1011–1014` in both releases; the method bodies match). This method enumerates values; it does not itself mutate an `AttributeInstance`.

The relevant mutator is `LivingEntity.collectEquipmentChanges`: for a changed, nonempty, unbroken equipped stack it calls `current.forEachModifier(...)`, finds each `AttributeInstance`, removes the same modifier ID and adds the transient modifier (`LivingEntity.java:2892–2925` in 26.1.2; `:2959–2992` in 26.2). `detectEquipmentUpdates` calls that collector, but `LivingEntity.tick` invokes `detectEquipmentUpdates` only inside `if (!this.level().isClientSide())` in both releases (`LivingEntity.java:2703–2728` and `:2769–2794`). Search of both complete source publications found no other call to `collectEquipmentChanges`. The equipment-slot overload `ItemStack.forEachModifier(EquipmentSlot, ...)` is also called by `LivingEntity.stopLocationBasedEffects` (`LivingEntity.java:3795–3800` / `:3861–3866`), including from `onEquippedItemBroken`; its callback passes each matching modifier to `AttributeInstance.removeModifier`, so this is removal from a prior stack, not modifier installation. The client equipment packet path above does not invoke `stopLocationBasedEffects`. Separately, the client entity-break event calls `LivingEntity.breakItem` (`LivingEntity.java:1412–1423` / `:1437–1448`) to play the break sound and spawn item particles; it does not invoke `onEquippedItemBroken` or remove modifiers. The tooltip uses a separate display overload. Therefore the synchronized client equipment-stack path replaces the local equipment object but does not locally install its `ATTRIBUTE_MODIFIERS` or enchantment modifiers into the local player's attributes. The server periodic equipment-update path can install its own modifiers, but that does not by itself establish a local client attribute write.

## Bounded conclusion and open gates

For the specific question asked, **no client-side `ATTACK_KNOCKBACK` modifier installation from a synchronized equipment stack is established by these source routes**: the equipment packet calls the stack writer, while modifier collection/application is in a tick path gated to server-side levels. The inventory packet writes inventory storage only. This does not establish that a local player's effective `ATTACK_KNOCKBACK` value must remain zero; other client attribute initialization/synchronization or direct client-local state routes were not assessed here. The report also does not establish which packaged items carry `ATTACK_KNOCKBACK` components, whether an item config can supply a positive value, whether `Player.stabAttack` is reached by ordinary local gameplay, or what server-authoritative synchronization may later do. These remain separate dependencies. Pair coverage remains partial.

No artifact payload, product implementation, backlog, wiki, Git history, build, test, launch, or runtime was inspected.
