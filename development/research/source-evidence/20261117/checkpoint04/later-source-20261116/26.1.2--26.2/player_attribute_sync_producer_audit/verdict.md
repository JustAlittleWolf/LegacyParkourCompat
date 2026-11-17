# Independent source audit: player attribute snapshot producer

## Verdict

**ACCEPT** for the bounded `ATTACK_KNOCKBACK` synchronization claim, bound to `build/later-source-20261116/26.1.2--26.2/player_attribute_sync_producer/coverage.md`, SHA-256 `172316AF22D84120176C33DEF62713299D45AFD70F1177B7AC2F10438E41EE48`.

## Evidence integrity

The actual provenance sidecars are the version-root `unobfuscated.provenance.json` files; their hashes match the report and declare versions 26.1.2 / 26.2, namespace `unobfuscated`. The version-root ready markers and source manifests match the report's hashes and ready/version/mapping identities. All seven cited Java files for both versions match their source-manifest rows and current bytes (14/14, zero mismatches). The current report bytes recompute to the assigned SHA. No whole-tree rehash or artifact payload inspection was performed.

## Independent source trace

- A source-wide search found two actual producer constructions per release, both in `ServerEntity`: `sendPairingData` filters initial `LivingEntity` attributes using `getSyncableAttributes()` before adding a packet to the pairing list; `sendDirtyEntityData` obtains `getAttributesToSync()`, sends only when nonempty, then clears the set. The pairing packet bundle and helper call/order support the report's initial and dirty snapshot route. No other packet construction site was found.
- `AttributeMap.getSyncableAttributes()` filters instances by `Attribute.isClientSyncable()`. `onAttributeModified` adds all modified instances to the general update set, but adds to the sync set only when that predicate is true. The packet constructor snapshots only the supplied attribute holder/base/modifier values; it adds no attributes itself.
- `Attribute.syncable` has no initializer, so its Java default is false. `setSyncable` is the opt-in; repository source search finds its declarations in `Attribute` and uses in `Attributes` only. `Attributes.ATTACK_KNOCKBACK` is registered with default 0.0 and bounds 0.0–5.0 and has no `.setSyncable(true)`. This holds in both source releases. Therefore it fails both the initial snapshot filter and the dirty update filter.
- `LivingEntity.tick` calls `detectEquipmentUpdates()` inside `if (!level().isClientSide())` in both releases. `collectEquipmentChanges` may install equipment-provided attribute modifiers server-side, but those modifications still cannot put the non-syncable instance into `attributesToSync`.
- The packet codec changes from `ByteBufCodecs.list()` to `ByteBufCodecs.list(128)` in 26.2. This affects the list bound only; the server-side attribute filter remains upstream and unchanged. The constructor copies only the provided collection.

## Limits

Accepted only: vanilla `ClientboundUpdateAttributesPacket` snapshots in these source trees do not include the default or equipment-modified `ATTACK_KNOCKBACK` instance. This does not establish the LocalPlayer's effective value through other local or transport paths, packaged item/component values, whether ordinary client attack reaches `Player.stabAttack`, attack outcomes, runtime behavior, or full-pair coverage. Pair remains partial.

No backlog/wiki/product/Git history, build, test, runtime, source generation, or canonical-file action was performed.