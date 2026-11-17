# 26.1.2–26.2 item input dependencies

## Result

No movement-input delta was found for this slice. In both exact source sets, `LocalPlayer.applyInput` takes `input.getMoveVector()` on the controlled-camera path and calls `modifyInput`; the local-only transformations and order are identical: return a zero vector unchanged, multiply by `0.98F`, multiply by the used stack's `USE_EFFECTS.speedMultiplier()` when using an item and not riding, multiply by the effective `SNEAKING_SPEED` when crouching or visually crawling, then normalize/clamp for the unit square. `ClientInput.getMoveVector()` returns its stored move vector unchanged in both versions. Compare `client/player/LocalPlayer.java:692–729` and `client/player/ClientInput.java:13` in both releases.

## Defaults and ordinary scenario

`UseEffects.DEFAULT` is exactly `(canSprint=false, interactVibrations=true, speedMultiplier=0.2F)` in both releases (`world/item/component/UseEffects.java:9–10`). `DataComponents.USE_EFFECTS` uses the same persistent codec and `UseEffects.STREAM_CODEC` network synchronization; `COMMON_ITEM_COMPONENTS` installs `UseEffects.DEFAULT` in both (`core/component/DataComponents.java:119–120, 415–421` in 26.1.2; `:120–121, 419–425` in 26.2). `LocalPlayer.itemUseSpeedMultiplier()` reads the used stack with `getOrDefault(..., UseEffects.DEFAULT)` in both (`client/player/LocalPlayer.java:563–568`). Thus, for an ordinary supported item with no per-item override, active item use gives the same `0.2F` multiplier. Vanilla's sword-property builder supplies `(true,false,1.0F)` in both (`world/item/Item.java:542` / `:543`), so this explicit override also reconstructs the same factor.

## Effective sneaking factor and synchronization

`SNEAKING_SPEED` remains a syncable ranged attribute with default `0.3` and bounds `[0.0,1.0]` (`world/entity/ai/attributes/Attributes.java:79–80` / `:92–93`), and `Player.createAttributes()` still adds it (`world/entity/player/Player.java:205,214` / `:206,215`). The common item component map defaults `ATTRIBUTE_MODIFIERS` to empty; `ItemStack.forEachModifier` reads that component and enchantment modifiers. The vanilla Swift Sneak enchantment is a direct source: `Attributes.SNEAKING_SPEED` receives `LevelBasedValue.perLevel(0.15F)` with `ADD_VALUE` in both (`world/item/enchantment/Enchantments.java:583–585` / `:584–586`). These values and source types are unchanged. In the ordinary unmodified scenario the local input factor is `0.3`; equipped modifiers can change the effective value in both versions.

The sync path is also equivalent: `AttributeMap` marks a changed attribute for synchronization only when `isClientSyncable()` is true (`world/entity/ai/attributes/AttributeMap.java:24–42`); `ServerEntity` sends syncable attributes initially and changed attributes to tracking players and self (`server/level/ServerEntity.java:282–284,345–347` / `:287–289,350–352`); `ClientPacketListener.handleUpdateAttributes` applies the packet's base and modifiers (`client/multiplayer/ClientPacketListener.java:2355–2372` / `:2357–2373`). This makes the same server-effective `SNEAKING_SPEED` available to the local `getAttributeValue` consumer.

## Coverage and dependencies

Compared: exact `LocalPlayer.applyInput`/`modifyInput` chain and guards; `ClientInput.getMoveVector`; `UseEffects` value/codec; data-component registration and common defaults; vanilla item override; `SNEAKING_SPEED` declaration and player attribute supplier; effective item/enchantment modifier sources; server attribute sync and client application. No paired difference or open dependency remains for the assigned slice. No unrelated effects or environmental fluid were examined.

## Evidence identity

Canonical exact decompiled source: `decompiled_minecraft/26.1.2/unobfuscated` and `decompiled_minecraft/26.2/unobfuscated`, both `unobfuscated` mapping. Ready metadata confirms version/mapping identities. Source-manifest SHA-256: 26.1.2 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; 26.2 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Relevant source-file hashes were checked against each source manifest. Provenance/ready records: `decompiled_minecraft/26.1.2/unobfuscated.provenance.json`, `unobfuscated.ready.json`; `decompiled_minecraft/26.2/unobfuscated.provenance.json`, `unobfuscated.ready.json`.