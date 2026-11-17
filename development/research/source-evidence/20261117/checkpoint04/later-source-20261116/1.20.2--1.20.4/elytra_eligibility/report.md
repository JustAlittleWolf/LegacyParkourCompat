# Elytra equipment eligibility: 1.20.2 to 1.20.4

## Exact source identity and integrity

- Endpoints: Minecraft `1.20.2` Mojmap → Minecraft `1.20.4` Mojmap.
- Canonical source roots: `decompiled_minecraft/1.20.2/mojmap/` and `decompiled_minecraft/1.20.4/mojmap/`.
- Both source sets have `mojmap.ready.json` status `ready`, exact `versionId`, `mapping: mojmap`, Vineflower, Java runtime 25, and source counts 4,904 / 5,048. Ready-marker source manifest SHA-256 values: 1.20.2 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; 1.20.4 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`.
- Ready-marker client input SHA-256: 1.20.2 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`; 1.20.4 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`.
- Provenance markers identify Mojmap and the exact decompile run; both list batch IDs `1.20.1, 1.20.2, 1.20.4, 1.20.6`, Gradle 9.7.1, Java 25.0.3+9-LTS, Vineflower 1.12.0, and matching ready-marker names.
- The following SHA-256 values were recomputed from the canonical source files and matched their corresponding entries in each `mojmap.sources.sha256` manifest:

| Relative source file | 1.20.2 SHA-256 | 1.20.4 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/item/ElytraItem.java` | `fa4d677a5b109ebf323458f88f05eacb9ffdeca66d952c1a5eb8a7114a911889` | `fa4d677a5b109ebf323458f88f05eacb9ffdeca66d952c1a5eb8a7114a911889` |
| `net/minecraft/world/item/Items.java` | `5db5ac81f8fd29e88a8cb1a1c5c1683b2ee3c23aa0ae17e10aa600dbac8e4fa7` | `5eeaf7ddccb5a9a5c1d744a8e41daf9be1ca423a065078b33f02b3f932e7d8f7` |
| `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/world/entity/player/Inventory.java` | `c44979168a8cfd9e45e3ba69e9c83aa0a92a87a56c252f23df041f1aeda6d01b` | `c44979168a8cfd9e45e3ba69e9c83aa0a92a87a56c252f23df041f1aeda6d01b` |
| `net/minecraft/world/entity/EquipmentSlot.java` | `c9b777e1ab13f87130c1928d7f26b96a6248f9a432f4bab398802a006084b22b` | `c9b777e1ab13f87130c1928d7f26b96a6248f9a432f4bab398802a006084b22b` |

## Ordered scope and evidence

1. **Examined — item registration and slot provider.** `Items.java`: 1.20.2 line 790 and 1.20.4 line 828 register `ELYTRA` as `new ElytraItem(new Item.Properties().durability(432).rarity(Rarity.UNCOMMON))`. `ElytraItem.java` has an identical SHA-256 at both endpoints; its constructor (12–16) registers dispenser behavior, `isFlyEnabled(ItemStack)` (18–20) is the same predicate, and `getEquipmentSlot()` (37–40) returns `EquipmentSlot.CHEST` at both endpoints. No `canElytraFly` method exists in these endpoint sources; the eligibility helper is named `isFlyEnabled`.
2. **Examined — player start gate and flag transition.** `Player.tryToStartFallFlying()` is 1.20.2 lines 1580–1590 and 1.20.4 lines 1506–1516. The bodies match: it requires not grounded, not already fall-flying, not in water, and no levitation; then reads `getItemBySlot(CHEST)`, requires `Items.ELYTRA` and `ElytraItem.isFlyEnabled`, sets the fall-flying flag through `startFallFlying()` and returns true; otherwise false. `startFallFlying()` and `stopFallFlying()` bodies also match (1.20.2 lines 1592–1599; 1.20.4 lines 1518–1525).
3. **Examined — state revalidation and movement-state predicate.** `LivingEntity.updateFallFlying()` is 1.20.2 lines 2639–2664 and 1.20.4 lines 2641–2666. It has the same gates and reads the chest-slot Elytra and `isFlyEnabled`; on invalid state it clears the local flag. Shared-flag mutation remains server-gated; the client-side trajectory body is outside this assignment. `isFallFlying()` remains `getSharedFlag(7)` (1.20.2 lines 3084–3085; 1.20.4 lines 3086–3087).
4. **Examined — equipment state route.** `Player.getItemBySlot` maps armor slots to `inventory.armor.get(slot.getIndex())`; `setItemSlot` writes the same list/index. Bodies match (1.20.2 lines 1783–1807; 1.20.4 lines 1709–1733). `getArmorSlots()` returns `inventory.armor` at both endpoints (1.20.2 lines 1820–1822; 1.20.4 lines 1746–1748). `Inventory.armor` is a four-entry `NonNullList` in both endpoints (Inventory.java lines 32–38). `EquipmentSlot.CHEST` is `Type.ARMOR`, index 2, in both (EquipmentSlot.java lines 6–11). Thus the direct player eligibility read resolves to chest armor index 2 on both sides.

## Verdict and boundaries

**No source difference found in this assigned slice between these endpoints.** The registration, Elytra item slot, player eligibility gates, chest equipment lookup, enabled predicate call, and LivingEntity state revalidation have matching behavior. `Items.java`, `Player.java`, and `LivingEntity.java` differ in whole-file hashes due to other contents; the scoped code above was compared directly. This is an endpoint no-difference only and does not establish an intermediate patch boundary or full-pair movement coverage.

- **Partial / separately bounded:** Elytra `use()` and downstream `Equipable.swapWithEquipmentSlot` state propagation; they are not needed to establish the chest-slot provider but remain part of the previously partial active item-use state slice. Player input/tick caller path is outside this assignment.
- **Unexamined / separately owned:** gliding travel math; block callbacks; friction; attributes; `Entity.move`; player impulse paths. They were not repeated here.
- **Dependencies and next gate:** No changed dependency was exposed by this source slice. Equipment inventory state acquisition is locally traced through `getItemBySlot` to armor index 2. Resource and synchronization provenance, including how a newly equipped stack becomes visible to client movement and the exact caller that invokes `tryToStartFallFlying`, remain open for their assigned coverage. Next gate is the independent source coverage/audit of those caller and state-update paths; this report does not claim their closure.