# 1.8.9 → 1.9 player equipment and movement-attribute lifecycle follow-up

## Scope and source identity

This report closes only the equipment/attribute lifecycle residual named in `tick-state/supplement.md`: player equipment install/swap paths, client packet application, movement-speed attribute reads, and the relevant status-effect packet path. It does not claim full-pair source coverage or independently accept a new movement finding.

The compared trees are the exact assigned `decompiled_minecraft/1.8.9/ornithe-feather/` and `decompiled_minecraft/1.9/ornithe-feather/` namespaces. The assigned 1.8.9 publication identity is bound to provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` and source-manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`. The 1.9 mapping-local provenance identity is SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.

## Findings

### Movement-speed consumption on the client

Both `PlayerEntity.initAttributes()` bodies set `EntityAttributes.MOVEMENT_SPEED` base to `0.1F` (1.8.9 `entity/living/player/PlayerEntity.java:131–135`; 1.9 same file `151–157`). Both `PlayerEntity.mobTick()` bodies obtain that attribute and call `setSpeed((float) entityAttributeInstance.get())`; changing the base to `abilities.getWalkSpeed()` is guarded by `!world.isClient` in both versions (1.8.9 lines 446–459; 1.9 lines 416–429). `PlayerEntity.getSpeed()` returns the same float cast of the movement-speed attribute in both versions (1.8.9 lines 1297–1300; 1.9 lines 1386–1389), and the inherited `LivingEntity` travel code consumes `getSpeed()` for player travel. The compared `ModifiableEntityAttributeInstance.computeValue()` bodies retain the same order: base, operation-0 additions, operation-1 base-scaled additions, operation-2 multipliers, then attribute clamp (1.8.9 lines 137–154; 1.9 lines 145–163). No arithmetic or local-client read difference was found in these paths.

### Equipment changes and attribute application

`LivingEntity.tick()` performs equipment-change detection and modifier installation only inside `if (!world.isClient)` in both releases. In 1.8.9 it iterates integer slots `0..4`, compares cached equipment with `getEquipment(j)`, sends an equipment packet, removes old `ItemStack.getAttributeModifiers()`, adds the new stack's modifiers, and updates its cached stack (lines 1274–1287). In 1.9 it iterates `EquipmentSlot.values()`, selects hand or armor storage, sends the enum-slot equipment packet, and removes/adds `getAttributeModifiers(equipmentSlot)` before replacing the relevant cache (lines 1492–1522). This confirms the server-side slot-filtering and extra-slot lifecycle already bounded by the existing equipment candidate; it is not a client movement-loop calculation. The 1.9 offhand slot is additional post-1.8 equipment state and is not treated here as a historical 1.8 item source.

Player inventory selection and swap methods write inventory slots: 1.8.9 `PlayerInventory.selectSlot(Item, int, boolean, boolean)` and `scrollInHotbar()` update `selectedSlot`/hotbar entries (lines 85–129); 1.9 `selectSlot(ItemStack)` and `swapSlotWithHotbar()` update the same inventory state (lines 67–97). S2C `handleSelectSlot` updates the local player's `inventory.selectedSlot` in both versions (1.8.9 `ClientPlayNetworkHandler.java:471–477`; 1.9 lines 497–503). These methods do not call the stack attribute-modifier getter or recompute `MOVEMENT_SPEED` locally.

The S2C `handleEntityEquipment` handlers find the entity and call `entity.setEquipment(packet.getEquipmentSlot(), packet.getItem())` in both releases (1.8.9 `ClientPlayNetworkHandler.java:976–983`; 1.9 lines 1037–1044). The player setter implementation differs with the slot API: 1.8.9 `PlayerEntity.setEquipment(int, ItemStack)` writes `inventory.armor[slot]` (lines 1590–1593); 1.9 `setEquipment(EquipmentSlot, ItemStack)` routes main hand, offhand and armor to their inventory stores (lines 1667–1678). Neither setter computes or applies attribute modifiers. The client obtains movement-affecting values through `handleEntityAttributes`: both handlers validate a live `LivingEntity`, then set each attribute base, clear its modifiers and add the packet modifiers (1.8.9 `ClientPlayNetworkHandler.java:1581–1605`; 1.9 lines 1689–1713). The local speed value therefore comes from the replicated attribute container, while the equipment packet updates the visible/local inventory representation.

`EntityAttributes.MOVEMENT_SPEED` is registered with the same default ranged attribute (`0.7F`, minimum `0.0`, maximum `1024.0`) in both trees; player initialization then sets the base to `0.1F`. The incoming attribute handler applies the server-provided base/modifier set without a release-specific client-side inventory recomputation. This closes the path from equipment-modifier production to the client movement-speed read for the scoped lifecycle; it does not characterize packet scheduling or server policy.

### Status-effect updates relevant to movement attributes

The status-effect S2C handlers add/remove an effect on the addressed `LivingEntity` in both versions (1.8.9 `ClientPlayNetworkHandler.java:1133–1142, 1223–1229`; 1.9 lines 1193–1205, 1271–1277). The 1.9 addition resolves the effect ID and returns if it is unknown, while 1.8.9 constructs from its packet-provided effect. Both call the corresponding `LivingEntity` lifecycle. `onStatusEffectApplied`, `onStatusEffectUpgraded`, and `onStatusEffectRemoved` set `effectsChanged` on either side, but add/remove attribute modifiers only when `!world.isClient` (1.8.9 `LivingEntity.java:578–598`; 1.9 lines 631–652). Thus the local client does not locally recalculate the movement attribute from the status effect packet; its attribute update arrives through the same S2C attribute handler described above. The prior tick-state supplement's paired speed/slowness and jump-boost formulas remain the bounded numeric evidence; this report found no additional effect-derived local-client movement delta.

## Bounded outcome and remaining uncertainty

No new movement finding is added by this follow-up. The existing slot-filtering candidate remains the only equipment-related movement candidate in this slice; this trace closes its local-client attribute-consumption path without extending its claim. I did not infer a client movement consequence from server-only equipment synchronization, inventory representation changes, status-effect packet decoding, or the 1.9-only offhand slot.

The client source shows which attribute state is read and how packets replace it, but does not establish the remote server's packet production policy or ordering under arbitrary custom items. Those are outside this source-only client lifecycle disposition. Other full-pair work listed in the tick-state supplement—pose collision-shape closure, piston movement response, and independent full-pair coverage audit—remains open.

## Exact source evidence

| Release | Relative source file | SHA-256 |
| --- | --- | --- |
| 1.8.9 | `entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| 1.9 | `entity/living/player/PlayerEntity.java` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` |
| 1.8.9 | `entity/living/LivingEntity.java` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |
| 1.9 | `entity/living/LivingEntity.java` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` |
| 1.8.9 | `client/network/handler/ClientPlayNetworkHandler.java` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` |
| 1.9 | `client/network/handler/ClientPlayNetworkHandler.java` | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` |
| 1.8.9 | `entity/living/player/PlayerInventory.java` | `b933355a513d08450064be3b4861670d30f7930c018cdce8c0c745e7af6e0c68` |
| 1.9 | `entity/living/player/PlayerInventory.java` | `d84ccfc8fe036966eeb075978ae51546f02a09a4c4974854c7b54c61a8adbfda` |
| 1.8.9 | `item/ItemStack.java` | `32917e0c486e8ba2a2dc9f91545c4f2a95c78e3a341f3ba1340993efac99c360` |
| 1.9 | `item/ItemStack.java` | `f97e08adfa7eb246e4d9bd16fe8dd9aa20d320296b95f5470dba09e3a6d93c01` |
| 1.8.9 | `entity/living/attribute/ModifiableEntityAttributeInstance.java` | `3a8ddba11c80ee90640a1b0b85fd498d5e557459ed5b8bb64ea6a30ab7a0157d` |
| 1.9 | `entity/living/attribute/ModifiableEntityAttributeInstance.java` | `906b280f48994a808806609af57aaa95ccfbee838cf12dd4ad51266b6daa2173` |
| 1.8.9 | `entity/living/attribute/EntityAttributes.java` | `573689391528a81df3a1e221cc5055c4fbc30b3ad3a55adc0acf14647b5456c0` |
| 1.9 | `entity/living/attribute/EntityAttributes.java` | `e8705a96b6a4dadb97eaabd401184332edd27fb7f0c07cb620dc724309898dff` |
