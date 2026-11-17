# Player equipment to movement attributes — bounded source slice

Pair: Minecraft 1.21.11 Mojmap → 26.1.2 published unobfuscated. This is a bounded inventory/equipment/attribute synchronization comparison, not a pair freeze or runtime-parity claim.

## Exact source identities

- 1.21.11 source root: `decompiled_minecraft/1.21.11/mojmap/`; namespace `mojmap`. Provenance sidecar `decompiled_minecraft/1.21.11/mojmap.provenance.json`, SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`.
- 26.1.2 source root: `decompiled_minecraft/26.1.2/unobfuscated/`; namespace `unobfuscated`. Provenance sidecar `decompiled_minecraft/26.1.2/unobfuscated.provenance.json`, SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`.
- Ready sidecars are `decompiled_minecraft/1.21.11/mojmap.ready.json` and `decompiled_minecraft/26.1.2/unobfuscated.ready.json`; each reports the exact release/namespace and `status=ready`. Their source manifests are `mojmap.sources.sha256` and `unobfuscated.sources.sha256`, with SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` and `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`, respectively; both match their ready declarations.
- Every Java file listed below was hashed from current bytes and matched its exact source-manifest row. No whole-tree rehash, artifact inspection, source generation, build, test, or runtime activity was performed.

## Ordered path and comparison

### 1. Client inventory/equipment writes

`ClientPacketListener.handleSetEquipment` first hands off to the client packet thread, looks up the packet entity, and for a `LivingEntity` applies each `(EquipmentSlot, ItemStack)` pair through `setItemSlot` (1.21.11 `ClientPacketListener.java:1462–1466`; 26.1.2 `:1512–1516`). If the ID resolves to the local player, this directly updates that player's equipment map. `handleSetPlayerInventory` instead calls `minecraft.player.getInventory().setItem(slot, contents)` (1.21.11 `:1398–1401`; 26.1.2 `:1448–1451`). The `Inventory` slot map is the same at both endpoints: indices 36–39 map to feet/legs/chest/head, 40 to offhand, 41 to body, and 42 to saddle (`Inventory.java:29–54` both releases). `Inventory.setItem` writes ordinary slots below 36 into the item list and writes mapped indices into the `EntityEquipment` object (`1.21.11 Inventory.java:371–378`; `26.1.2 :377–384`). `Player` constructs its `Inventory` with its equipment object (`Player.java:183–185` old; `:177–179` new; constructor field setup in `LivingEntity`).

The default `InventoryMenu` uses `ArmorSlot` over those inventory indices for armor and a `Slot` over index 40 for offhand (`InventoryMenu.java:48–67` both releases; `ArmorSlot.java:13–30` old, `:13–38` new). `ArmorSlot.setByPlayer` invokes `onEquipItem` and then writes its container slot; the offhand slot does likewise. Client container slot/content handlers route through `AbstractContainerMenu.setItem` / `initializeContents` (1.21.11 `ClientPacketListener.java:1356–1381, 1405–1411`; 26.1.2 `:1406–1431, 1455–1461`), which calls `Slot.set` and writes the underlying container (`AbstractContainerMenu.java:614–626` old, `:619–631` new; `Slot.java:56–66` both). Thus the player inventory menu's mapped armor/offhand slots can update the same local equipment storage; generic open-menu slots remain their own backing containers. `handleSetCursorItem` changes the carried stack only (1.21.11 `ClientPacketListener.java:1389–1396`; 26.1.2 `:1439–1446`). These branches and the slot map are semantically equivalent across the pair.

### 2. Server-side equipped modifier collection

In both releases, `LivingEntity.tick` runs `detectEquipmentUpdates` only inside `if (!level().isClientSide())` (`1.21.11 LivingEntity.java:2630–2640`; `26.1.2 :2719–2729`). The collector compares each slot against its last equipment snapshot (`1.21.11 :2793–2828`; `26.1.2 :2892–2929`). For changed slots it removes location-based effects from a nonempty previous stack; for each new nonempty, nonbroken stack it calls `ItemStack.forEachModifier(slot, ...)`, removes any same-ID modifier from a present attribute instance, then adds the modifier as transient. The item-stack method reads the `ATTRIBUTE_MODIFIERS` component (defaulting to empty), filters its entries by equipment slot, and invokes `EnchantmentHelper.forEachModifier` for slot-applicable enchantment modifiers (`ItemStack.java:1017–1020` both; `ItemAttributeModifiers.java:79–88` old / corresponding `:79–88` new). Enchantment location-changed effects also run only on a `ServerLevel`.

The paired inventory/slot mapping, equipment-change ordering, removal/addition ordering, and component/enchantment modifier enumeration show no movement-relevant source difference. Crucially, this collector is server-tick gated; client equipment item writes do not by themselves run this server modifier collection path.

### 3. Attribute synchronization to the local player

`ServerEntity.sendDirtyEntityData` checks a living entity's dirty attributes and, when present, sends `ClientboundUpdateAttributesPacket` to tracking players and self, then clears the collected dirty set (`1.21.11 ServerEntity.java:332–345`; `26.1.2 :336–349`). In both versions, `ClientPacketListener.handleUpdateAttributes` resolves the entity ID and rejects a non-living target; for each snapshot with a known attribute it sets the base value, removes current modifiers, and adds the snapshot's modifiers as transient (`1.21.11 ClientPacketListener.java:2231–2251`; `26.1.2 :2355–2377`). This is the observed client-side attribute state writer. The same-thread packet gate, attribute lookup, snapshot iteration, and write order match across the pair. The separate equipment packet changes the equipment stack snapshot; this report does not claim an order between that packet and any attribute snapshot sent during synchronization.

### 4. Movement-relevant attribute readers

The inspected movement consumers and corresponding attribute keys are present with the same default definitions/ranges in both exact source trees:

- `Player`'s movement speed is read into `setSpeed` and by `getSpeed`; its player attribute builder supplies `MOVEMENT_SPEED` base `0.1F` and includes `SNEAKING_SPEED` (`Player.java:211–224, 466, 1405` old; `:205–218, 456, 1426` new).
- `LocalPlayer` scales slow-movement input by `SNEAKING_SPEED` before square-movement normalization (`LocalPlayer.java:674–681` old; `:713–720` new).
- `LivingEntity` reads `MOVEMENT_EFFICIENCY` in `getBlockSpeedFactor` (`:495–496` old / `:491–492` new), `JUMP_STRENGTH` in jump-power computation (`:2260–2263` / `:2343–2346`), `GRAVITY` in gravity lookup (`:2300–2302` / `:2383–2385`), `WATER_MOVEMENT_EFFICIENCY` in the water movement branch (`:2381–2390` / `:2468–2477`), and `STEP_HEIGHT` in `maxUpStep` (`:3808–3811` / `:3919–3922`). Their corresponding `Attributes.java` definitions/defaults/ranges are identical at the inspected declarations (`:45–96` both).

No difference was found in the inspected item-to-equipment mapping, modifier installation/removal order, dirty-attribute packet route, client snapshot writes, or the listed movement-attribute defaults/readers. `Player.createAttributes` also contains interaction-range attributes; those are outside this movement-only comparison. The item component and enchantment methods are generic source mechanisms. This source-only slice does not inspect packaged item/component values or resource membership, so it cannot identify which particular items carry a movement attribute modifier or establish a concrete item-data difference.

## Boundary

Checked: local player equipment packet and inventory/menu slot writes; the slot-to-equipment map; server-side equipment modifier collection; component/enchantment slot filtering; server dirty-attribute publication; client attribute snapshot application; and named movement-attribute defaults/readers.

Unexamined: packaged item component values and enchantment/resource data; exact server transaction and packet ordering between equipment changes and attribute snapshots; all other inventory/menu effects; and full pair tick/provider closure. This report establishes no movement difference in the compared Java routes and does not close those gates or the pair.

## Cited Java SHA-256 values

| Relative Java path | 1.21.11 Mojmap | 26.1.2 unobfuscated |
| --- | --- | --- |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e` | `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82` |
| `net/minecraft/world/entity/player/Inventory.java` | `0b1258e7ff2f092e223130c6ed93691c057fe150a52842ed70c54c2549f52a79` | `db2dd240221743c7523d17de5cce71827fb4f3aa3c959b201547b295785d6839` |
| `net/minecraft/world/inventory/InventoryMenu.java` | `05557040c4ff55280d1ff139342d3e3e77e4c01b34cf7ea2a1da2914f5590092` | `32929c42e96b150f38473106958853bcb21403ec0002b437466d297e41037e9c` |
| `net/minecraft/world/inventory/ArmorSlot.java` | `b1575b164353c3ee7509e38bc525498b9d83c92f41d20f04c22fce1bd151e7b2` | `3d30b9790d541dfd964ef9fec220bb30d32cafc9a3ad6ede14c947b9b5aa9c05` |
| `net/minecraft/world/inventory/AbstractContainerMenu.java` | `abed9046242460bff516039868d4a118d30f627cafdf25d30ca5d4fe4c1733ce` | `4f944b69f2be2069ffee9efd9c9f97adac42e7568acf20e18aa874a2f49d71d6` |
| `net/minecraft/world/inventory/Slot.java` | `a1a01bed1d0a9a631e28f412331f91c2b3ab972e97de77e53545fa16146a24e0` | `7c8efbae0cb600fb93ea56da508d5e5a663eaa0a8df2c4f289a26e9d32d5f85c` |
| `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/item/ItemStack.java` | `a40daaf8a0e31dbf1f0f5c5413c14265d52fa0117132e0aeb1bb8fa10487e01a` | `26ba4af70893fae5107105ed46ed28cd3d51c7b4aba22bc8810f01b82177e146` |
| `net/minecraft/world/item/component/ItemAttributeModifiers.java` | `992814789d3fe99db0d5acd2eed50fa69430da4e0f8f7b09b3c2a603a33afd61` | `4fbf0507be1a289df08782751b9e647acd1631cbf304f00638d80f7e1337e80b` |
| `net/minecraft/world/entity/ai/attributes/AttributeMap.java` | `c8dfebeefa367556ff706951cacea308c18da53fa28108c18f59789e6f1b60e2` | `675b87b1757a73c6354db2dcfe52ec65c903bd9b3f5fcb97a648be252ea3f7f6` |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `4b89cf81cf75b16ed743b6bd0a683154c481247230b56dfa37c11ccfe17004c1` | `ca262f3d973642a2887b235a549b14f335706413f9551b417059e72e6bcf8cfd` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `62129028c2f7beb6d2f87bcd3345e63a7be8fc3950a7ecdbc6ee21f8fb89f843` | `8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5` |
| `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/server/level/ServerEntity.java` | `268073683299a1860b5032d184add16f757a4c8fc000b5be7cc1c00291ac930b` | `b2ba4e6331f6b1e44aee186436b141e903d528553c5ff78516edd6a1b4ad7ac3` |
