# Independent source audit: 26.1.2 to 26.2 packet responses

Verdict: **ACCEPT the bounded report.** This is an independent acceptance of the exact report bytes below, for the selected client packet-response routes and direct inherited writers it inventories. It is not whole-pair or whole-packet-lane acceptance. Pair coverage remains partial; codecs, server packet production/order, other client packet writers, and the broader tick/provider/dependency graph remain outside this report's checked boundary.

## Immutable input and exact identities

- Report: `build/later-source-20261116/26.1.2--26.2/packet_responses/coverage.md`
- Report SHA-256: `8a46e462d045ef60f5f0877d67bfe0d9d5a5f74418b92109ba0e61b2761dc484`
- Exact canonical sources: `decompiled_minecraft/26.1.2/unobfuscated/` and `decompiled_minecraft/26.2/unobfuscated/`.
- Provenance sidecars: `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; `decompiled_minecraft/26.2/unobfuscated.provenance.json` SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`. Each declares its matching exact version ID and `unobfuscated` namespace.
- Ready markers declare `ready`, version IDs 26.1.2 and 26.2, and `unobfuscated`. Their declared source-manifest, artifact-manifest and diagnostics digests match the sidecars on disk. Source-manifest digests are `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` and `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; artifact-manifest digests are `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` and `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`; diagnostics digests are `b4bd3875263a7155ac32cffd64cfacabc7bd54c82c5fe4082f1ef8a809024c02` and `ba6fd6c5b1c77b3ee2988f0c54968cc5b915680670fdb66699dbcbf8b9fe21d3`.
- All 13 tabled Java paths on both sides were checked against their source-manifest rows and current file bytes; all 26 reported digests match.

## Audit findings

The exact handler/helper method bodies for `handleSetEntityMotion`, `handleSetEntityData`, `handleEntityPositionSync`, `handleTeleportEntity`, `handleMoveEntity`, `handleMovePlayer`, `setValuesFromPositionPacket`, `handleRotatePlayer`, `handleExplosion`, `handleSetHeldSlot`, `handleSetPlayerInventory`, `handleSetEquipment`, `handlePlayerAbilities`, `handleRemoveMobEffect`, and `handleContainerContent` match byte-for-byte across the pair. The thread-handoff helper `PacketUtils.ensureRunningOnSameThread` also matches. The listed position, velocity, relative/interpolation, local-authority, passenger, ground-state, direct explosion knockback, hotbar/inventory, equipment, and ability routes are supported by the cited code and retain their operation order and gates.

The other inventoried paths have source-level syntax/API edits without a movement-response change in the bounded statements: `handleGameEvent` changes screen/chat accessors while retaining the `CHANGE_GAME_MODE` route; `handleRespawn` changes level renderer/extractor and screen accessors while retaining delta/rotation and attribute transfer gates/order; `handleUpdateAttributes` changes the Java pattern-binding form but retains null/nonliving gates, per-snapshot order, base-value write, modifier clearing and transient-modifier application; `handleUpdateMobEffect` changes pattern binding/casts while retaining the same LivingEntity effect writer; and `handleContainerSetSlot` changes GUI accessor patterns while retaining the same menu writes/branches.

The inherited movement-relevant writers and consumers checked in the report match: `SynchedEntityData.assignValues`; `Entity.setPose`, `reapplyPosition`, synced-data pose callback and `refreshDimensions`; `LivingEntity.onSyncedDataUpdated`, `setItemSlot`, effect add/removal and callback paths; `LocalPlayer` item-use start/stop/sync callbacks and the can-sprint/item-use-speed consumers; `Inventory` selected-slot/item writes; the attribute map transfer helpers and attribute instance mutation/replace methods; effect callbacks; `MultiPlayerGameMode.setLocalMode`; and `GameType.updatePlayerAbilities`. This supports the report's bounded no-difference conclusions for those exact routes.

One non-material counting typo is present: the report says “all six handler/helper bodies” after listing six packet handlers plus `setValuesFromPositionPacket`; seven named bodies are listed. I independently checked all seven, and all match byte-for-byte. This does not change the reviewed scope or technical conclusion.

## Limits

No inference is made about untraced codecs, server-side packet production/order, unlisted packet handlers, complete equipment/menu systems, ordinary player tick coverage, or full pair closure. No source was generated or modified; no tests, builds, product review, runtime/game launch or Git-history inspection was performed. No canonical research finding or backlog record was written.
