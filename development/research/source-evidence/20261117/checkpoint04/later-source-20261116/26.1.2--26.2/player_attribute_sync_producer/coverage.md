# Player attribute snapshot producer: 26.1.2 to 26.2

Disposition: **bounded NO_DIFF for `ATTACK_KNOCKBACK` synchronization**. The vanilla server-side attribute snapshot route does not include this attribute in either release, including after a server equipment modifier changes it. This does not establish client attack invocation or close the pair.

## Exact source identities

Both canonical publications are `unobfuscated`: `decompiled_minecraft/26.1.2/unobfuscated/` and `decompiled_minecraft/26.2/unobfuscated/`. The actual provenance sidecars are `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` (SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`) and `decompiled_minecraft/26.2/unobfuscated.provenance.json` (SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`); each declares its corresponding version ID and namespace. The ready sidecars are `decompiled_minecraft/26.1.2/unobfuscated.ready.json` (SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`) and `decompiled_minecraft/26.2/unobfuscated.ready.json` (SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`). They bind source manifests `unobfuscated.sources.sha256` with SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` and `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`, respectively. No hidden provenance filename is used. The source-tree identities are reused from the supplied accepted source reports; no whole-tree rehash was performed for this bounded follow-up.

Each cited Java file below was rehashed from the canonical source and its digest was found in that version's ready-bound source manifest.

| Relative Java path | 26.1.2 SHA-256 | 26.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/server/level/ServerEntity.java` | `b2ba4e6331f6b1e44aee186436b141e903d528553c5ff78516edd6a1b4ad7ac3` | `3c55dbb107c3659313b64ab1ecfc2bb9549d33989cf9fc9fbc20b22366478dc7` |
| `net/minecraft/world/entity/ai/attributes/AttributeMap.java` | `675b87b1757a73c6354db2dcfe52ec65c903bd9b3f5fcb97a648be252ea3f7f6` | same |
| `net/minecraft/world/entity/ai/attributes/Attribute.java` | `854d1c12ab0b49a2136d5807db70676fe041671d40d44140cc2fda7c4ab4db59` | same |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5` | `4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c` |
| `net/minecraft/network/protocol/game/ClientboundUpdateAttributesPacket.java` | `5f0016c7156db12e6a05edf24e0b803c60dca39dded6cb0ef54a2c3dec0cd656` | `b551db9b91beb6bf29b8d01df912f872f1b332c3eb1b936a9e6c0666c56a96a9` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |

## Bounded server-to-client route

A source-wide search for `new ClientboundUpdateAttributesPacket` found two producer sites in each exact publication, both in `ServerEntity`:

1. **Entity pairing / initial snapshot.** `ServerEntity.sendPairingData` first appends the entity spawn packet, then, for a `LivingEntity`, obtains `getAttributes().getSyncableAttributes()` and appends an update packet only when that collection is nonempty (26.1.2 lines 269–285; 26.2 lines 274–290). `addPairing` sends the accumulated bundle to the paired player's connection (26.1.2 lines 262–266; 26.2 lines 267–271). The paired method gates and ordering are the same.
2. **Dirty attribute update.** `sendDirtyEntityData` obtains `getAttributesToSync()`, sends a packet to tracking players and self only when the set is nonempty, then clears the set (26.1.2 lines 336–350; 26.2 lines 341–355). The gates and order are the same; 26.2 uses a pattern variable where 26.1.2 casts the entity. `ServerEntity`'s movement-tracking call sites invoke this helper in both trees.

`AttributeMap.getSyncableAttributes()` filters existing instances by `attribute.isClientSyncable()`. Its `onAttributeModified` callback always adds the instance to the server's update set, but adds it to `attributesToSync` only if `isClientSyncable()` is true (26.1.2 `AttributeMap.java:26–45`; 26.2 the same body/lines). Thus the initial snapshot and later dirty snapshots share the same client-syncability filter.

The packet constructor copies each supplied instance's attribute holder, base value, and modifiers into an `AttributeSnapshot` (26.1.2 and 26.2 `ClientboundUpdateAttributesPacket.java:30–36`). The packet only serializes values supplied by the server's filtered snapshot collection; it does not independently add attributes.

## `ATTACK_KNOCKBACK` and equipment modifiers

`Attribute.syncable` is a boolean field with no initializer, so Java's default is false; `setSyncable(true)` is the opt-in (both versions `Attribute.java:14–35`). `Attributes.ATTACK_KNOCKBACK` is registered in both versions with default value `0.0` and bounds `0.0..5.0`, without calling `setSyncable(true)` (26.1.2 `Attributes.java:15`; 26.2 `:18`). Accordingly, the attribute is not client-syncable at either endpoint.

That registration means `ATTACK_KNOCKBACK` is filtered out of `getSyncableAttributes()` during entity pairing. If it is modified server-side, `onAttributeModified` does not place it in `attributesToSync`, so `sendDirtyEntityData` cannot include it in a `ClientboundUpdateAttributesPacket`. The packet's base/modifier snapshot fields do not override this upstream filter. No source difference in the two server producers changes this result.

The server's normal equipment modifier collection occurs in `LivingEntity.collectEquipmentChanges`, which applies current, unbroken item modifiers to matching attribute instances (26.1.2 lines 2892–2920; 26.2 lines 2959–2983). Its periodic caller `detectEquipmentUpdates` is inside the `!level().isClientSide()` block in `LivingEntity.tick` in both versions (26.1.2 lines 2703–2728; 26.2 lines 2769–2794). Therefore equipment may modify the server-side `ATTACK_KNOCKBACK` instance, but that change still does not pass the attribute's false client-syncability gate. This report makes no claim about concrete packaged item modifiers or a different client-local/mod implementation.

## Packet representation difference and limits

The packet class's top-level list codec differs: 26.1.2 uses `ByteBufCodecs.list()` and 26.2 uses `ByteBufCodecs.list(128)` (`ClientboundUpdateAttributesPacket.java:20–26`). This is a packet list-bound change. It does not add `ATTACK_KNOCKBACK` to the server's filtered collection, so it does not change the bounded conclusion above. No claim is made about other movement attributes or malformed/oversized attribute lists.

**Bounded conclusion:** vanilla server attribute snapshots do not transmit the default or equipment-modified `ATTACK_KNOCKBACK` attribute through `ClientboundUpdateAttributesPacket` in either release. The local attribute definition has a zero default, but this report does not claim the effective client value under non-vanilla modifications, does not resolve whether ordinary LocalPlayer execution reaches `Player.stabAttack`, and does not assess any resulting attack/trajectory. Packaged component values, attack conditions, other client attribute sources, server/client transport beyond this packet, and pair coverage remain open.

No build, test, game launch, runtime, source generation, wiki/backlog/product/Git-history review, artifact payload inspection, canonical finding or backlog write was performed.
