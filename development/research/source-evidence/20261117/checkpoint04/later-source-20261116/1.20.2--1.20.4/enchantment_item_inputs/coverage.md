# 1.20.2 → 1.20.4 enchantment inventory lookup coverage

## Inputs and scope

- Compared exact Minecraft 1.20.2 Mojmap and 1.20.4 Mojmap source trees under `decompiled_minecraft/<version>/mojmap/`.
- Root-sidecar provenance identities: 1.20.2 `mojmap.provenance.json` SHA-256 `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05`; 1.20.4 `mojmap.provenance.json` SHA-256 `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385`. The paired identity report records ready/source-manifest identity validation and exact Mojmap namespace for both endpoints.
- Bounded open dependency: the paired movement-enchantment report left the inventory-level lookup, footwear eligibility and `BlockTags.SOUL_SPEED_BLOCKS` resource membership open. This supplement addresses only Java-side enchantment inventory and eligibility. It does not revisit the accepted travel formulas or Soul Speed block-factor consumer.

## Paired source trace

All cited Java files were checked against their endpoint `mojmap.sources.sha256` entries. The cited Java hashes are identical across endpoints except `ItemStack.java`:

| Source path relative to Mojmap root | 1.20.2 SHA-256 | 1.20.4 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `C98B9E538AAE7FEA33A125560400F346745BAC993E859FFD3912DA58F3CC1128` | same |
| `net/minecraft/world/item/enchantment/Enchantment.java` | `0D5EE07BC252BF9A409DCFF2F907894CA2460A9F946E05E8BED8DA623B61AB09` | same |
| `net/minecraft/world/item/enchantment/EnchantmentCategory.java` | `DDD440DA3BAA03B33CF4E9B9B9DDEE0BC8033A91F0E4014C12B0ECB31DF4E7ED` | same |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `35FA4D1F9DA93971FA70BF6EC2F9218A8825FF343038B51143AE1404867F38E3` | same |
| `net/minecraft/world/item/enchantment/WaterWalkerEnchantment.java` | `B7B2F93857B699E492446F995A235EF88163F5CC8B2FF359C589EA0A8817FD17` | same |
| `net/minecraft/world/item/enchantment/SoulSpeedEnchantment.java` | `B5731E8F1C8848BB0957588DB8F06F16B2C1114AD745CFDC5BEB53471E6CD108` | same |
| `net/minecraft/world/item/ItemStack.java` | `93C5054D96FAE740977A9C70FC23CBCC4E2BEC50AD1C656A18911C6591CFC70E` | `0E0B2A4973EE41E042A9EE6158FFDD4A366676F90019CB2C7D236DE7BC809710` |
| `net/minecraft/world/item/ArmorItem.java` | `7EAF3E5BAE736F098054173090BF4491FE80100AB0599995CAB565A35527FCC5` | same |
| `net/minecraft/tags/ItemTags.java` | `BFA5767D36FFFFF272F770CE156A1627CFC6EEB22AC0C1C900B2D2A1D9274E03` | same |

For each endpoint, `EnchantmentHelper.getDepthStrider(LivingEntity)` directly returns `getEnchantmentLevel(Enchantments.DEPTH_STRIDER, entity)` (`EnchantmentHelper.java:209–210`). The generic entity-level lookup (`:175–187`) obtains `enchantment.getSlotItems(entity).values()`, examines each returned stack with `getItemEnchantmentLevel`, and retains the greatest level. It does not sum levels. The item-level helper (`:63–78`) returns zero for an empty stack, scans the stack's `Enchantments` list, matches the enchantment registry ID and returns the stored level; it does not call `Enchantment.canEnchant` or consult tags.

`Enchantment.getSlotItems(LivingEntity)` (`Enchantment.java:39–49`) iterates only the enchantment instance's configured equipment slots and retrieves each using `LivingEntity.getItemBySlot`; it includes nonempty stacks. `Enchantments.DEPTH_STRIDER` is registered with `WaterWalkerEnchantment` and `ARMOR_SLOTS` (`Enchantments.java:8,27`, where the array is HEAD, CHEST, LEGS, FEET). `Enchantments.SOUL_SPEED` uses `SoulSpeedEnchantment` with only `EquipmentSlot.FEET` (`:30`). Both subclasses set category `ARMOR_FEET` (`WaterWalkerEnchantment.java:7`; `SoulSpeedEnchantment.java:7`).

The category's *enchanting eligibility* (`EnchantmentCategory.java:23–27`) is a Java predicate requiring an `ArmorItem` whose declared equipment slot is FEET. `Enchantment.canEnchant(ItemStack)` delegates to that category predicate (`Enchantment.java:115–116`). This is distinct from the runtime level lookup: the helper reads a matching stored enchantment from every configured slot without rechecking `canEnchant`. Consequently Depth Strider's getter considers its four configured armor slots, even though ordinary enchantment eligibility is feet-only; Soul Speed's getter reads feet only. This distinction applies equally to stacks with unusual or externally written enchantment data.

`ItemStack.getEnchantmentTags()` (`ItemStack.java:521` in 1.20.2; `:553` in 1.20.4) obtains the `Enchantments` compound list from that stack's NBT tag (or an empty list). Thus the level itself is stored per stack; the source does not resolve it through a packaged item tag. `ItemTags.java` contains no footwear/enchantment eligibility tag used by this path. There is no item-tag resource key to extract for this source-side lookup.

The lookup methods above contain no logical-side guard; they read the passed entity's equipment and stack NBT directly. The movement caller's client/server gates and Soul Speed modifier lifecycle are separately described in the existing bounded report. This supplement makes no new claim about effective client attribute synchronization or server behavior.

## Dependency disposition

- **Java lookup and eligibility computation:** paired and unchanged in these bounded slices; no movement finding or source difference identified.
- **Resource gate still open:** `BlockTags.SOUL_SPEED_BLOCKS` is the exact block-tag key read by the already examined consumer. Its default resource membership is data-driven and was not established by these Java files. Resource resolver should target `data/minecraft/tags/blocks/soul_speed_blocks.json` using the resource pack's actual schema/path for this release, rather than infer membership from the Java item-eligibility code.
- **Not covered here:** endpoint resource payload membership, item stack contents in any particular world, full player tick coverage, independent pair audit/freeze, implementation applicability, and runtime parity.

**Bounded result:** no 1.20.2→1.20.4 code difference found in the examined Depth Strider/Soul Speed level lookup or category eligibility. The only remaining packaged-resource dependency in this branch is the source consumer's `minecraft:soul_speed_blocks` block tag; no movement-enchantment item tag is consulted by the lookup described above.
