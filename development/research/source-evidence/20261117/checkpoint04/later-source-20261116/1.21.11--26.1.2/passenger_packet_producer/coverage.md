# Passenger-list packet producer and tracking slice — 1.21.11 to 26.1.2

Disposition: **bounded NO_DIFF** for the server-side creation, filtering, direct self-delivery, and new-tracker initialization of `ClientboundSetPassengersPacket`. This is not a pair freeze or a claim about vehicle physics, server passenger policy as a whole, or runtime movement parity. The paired client consumer/attachment behavior is a separate assigned slice.

## Exact source identities

- Older: Minecraft 1.21.11, published Mojmap, canonical `decompiled_minecraft/1.21.11/mojmap/`. Actual provenance sidecar `decompiled_minecraft/1.21.11/mojmap.provenance.json`, SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`, declares `versionId=1.21.11`, `namespace=mojmap`, `mappingOption=mojmap`. Ready sidecar `decompiled_minecraft/1.21.11/mojmap.ready.json`, SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`, reports `status=ready`, source manifest `mojmap.sources.sha256`, SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.
- Newer: Minecraft 26.1.2, published unobfuscated, canonical `decompiled_minecraft/26.1.2/unobfuscated/`. Actual provenance sidecar `decompiled_minecraft/26.1.2/unobfuscated.provenance.json`, SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`, declares `versionId=26.1.2`, `namespace=unobfuscated`, `mappingOption=unobfuscated`. Ready sidecar `decompiled_minecraft/26.1.2/unobfuscated.ready.json`, SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`, reports `status=ready`, source manifest `unobfuscated.sources.sha256`, SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`.

The source-manifest sidecar digests match those declared by the ready sidecars. Each cited source file's current SHA-256 matches its corresponding manifest row. No artifact bytes, complete source tree, or server/runtime behavior was tested or claimed.

## Ordered producer and delivery comparison

### 1. Player mount and dismount send to that player's connection

In 1.21.11 `ServerPlayer.startRiding` calls `super.startRiding` first and sends nothing if it fails. On success it positions the rider, sends a relative-rotation teleport, sends active effects when the vehicle is a `LivingEntity`, then directly sends `ClientboundSetPassengersPacket(vehicle)` to this player's connection and returns true (`ServerPlayer.java:2082–2095`). `ServerPlayer.removeVehicle` captures the old vehicle, runs `super.removeVehicle`, sends removal of the vehicle's active effects when applicable, and sends `ClientboundSetPassengersPacket(oldVehicle)` only when the old vehicle is non-null (`2098–2110`).

The 26.1.2 overrides preserve those gates and order: successful `super.startRiding`, rider positioning, relative-rotation teleport, optional active-effect sync, then direct passenger packet (`ServerPlayer.java:2124–2137`); for dismount, old-vehicle capture, superclass removal, optional effect removal, then packet only for non-null old vehicle (`2140–2151`). Thus mount success and old-vehicle presence determine the same self-delivery packet paths. This compares packet production only; it does not classify the server's decision to permit mounting.

`ServerPlayer.loadAndSpawnParentVehicle` also reattaches persisted players via `startRiding(..., true, false)`, either to the root vehicle or to the matching indirect passenger (`1.21.11:433–449; 26.1.2:439–455`); the paired calls retain argument/order structure and flow through the same override. This is server-side restore state, not a client physics step.

### 2. Tracking clients receive changed passenger lists, with the transitioning player filtered out

`ServerEntity.sendChanges` reads the entity's current passenger list and compares it with `lastPassengers`. When unequal, it creates one passenger packet for the tracked entity and calls `sendToTrackingPlayersFiltered` with the same predicate on both sides: `currentPassengers.contains(player) == lastPassengers.contains(player)`. It then replaces `lastPassengers` with the current list (`1.21.11 ServerEntity.java:88–95; 26.1.2:90–99`).

The corresponding `ChunkMap.TrackedEntity.sendToTrackingPlayersFiltered` loops over `seenBy` connections and sends exactly when that predicate returns true (`1.21.11 ChunkMap.java:1331–1337; 26.1.2:1356–1362`). Therefore tracking viewers whose own passenger membership did not change receive the updated full list; a player whose membership changed is omitted from this filtered broadcast. The direct `ServerPlayer.startRiding` / `removeVehicle` path above sends that transitioning player's own list directly. The order, list comparison, predicate, and update of the saved list match across the pair.

The list comparison is a content snapshot, not a moving mutable view: `Entity.passengers` is an `ImmutableList`, `getPassengers()` returns that current immutable value, and `addPassenger`/`removePassenger` replace it with a new immutable list. The server-side player ordering rule is also unchanged: when adding a player on a logical server ahead of a non-player first passenger, insert at index zero; otherwise append (`1.21.11 Entity.java:197, 3353–3355, 2368–2400; 26.1.2:212, 3445–3447, 2428–2459`). This preserves the `lastPassengers` comparison and packet order semantics.

### 3. New trackers receive the current list

On a newly established pairing, `ChunkMap.TrackedEntity` calls `ServerEntity.addPairing` after the same horizontal-range, entity-visibility, and tracked-chunk gates; `addPairing` gathers `sendPairingData` then sends a bundle to that player (`1.21.11 ChunkMap.java:1359–1376, ServerEntity.java:258–263; 26.1.2 ChunkMap.java:1383–1401, ServerEntity.java:262–267`). `sendPairingData` emits `ClientboundSetPassengersPacket(entity)` if that entity has passengers and emits a second packet for `entity.getVehicle()` when the entity being paired is itself a passenger (`1.21.11 ServerEntity.java:265–310; 26.1.2:269–314`). Packet append order relative to spawn data, synced entity data, attributes, equipment, and leash data is the same. This is initial entity-tracking synchronization, not a changed movement rule.

### 4. Packet payload and call-site inventory

The packet constructor snapshots the vehicle ID and ordered passenger IDs into an integer array, and the codec writes/reads vehicle VarInt followed by passenger VarInt array in both releases (`ClientboundSetPassengersPacket.java:10–48` in both). A source search for constructor calls in both complete published source trees found the same five production sites: `ServerEntity.sendChanges`, `ServerEntity.sendPairingData` for current passengers, `ServerEntity.sendPairingData` for the entity's vehicle when it is a passenger, and the two direct `ServerPlayer` mount/dismount sends. No other constructor site was found in either tree. This call-site census is limited to this packet and does not inventory other passenger-related packet types.

## Bounded result and limits

No source difference was found in the conditions, order, recipients, packet list contents, or packet encoding for this passenger-list producer slice. On the server producer side, local-player inclusion/removal is delivered directly by the ServerPlayer mount/dismount override, while other tracking players receive the changed list when their own membership did not change; newly tracking viewers receive the current list through pairing data. No client trajectory effect is inferred.

Unexamined: the server's broader mount/dismount eligibility policy and all entity-specific callers; every other packet/teleport provider; vehicle movement and vehicle-specific attachment calculations; client-side interpolation and the complete movement tick. Those remain outside this bounded source result. The pair is not frozen and runtime parity is unverified.

No finding is drafted because the checked bounded producer routes have no behavioral delta. No backlog, wiki, product implementation or Git history was consulted; no canonical file was written; no build, test, artifact inspection, or runtime launch was performed.

## Cited exact source files and SHA-256

| Relative source path | 1.21.11 Mojmap SHA-256 | 26.1.2 unobfuscated SHA-256 |
|---|---|---|
| `net/minecraft/server/level/ServerEntity.java` | `268073683299a1860b5032d184add16f757a4c8fc000b5be7cc1c00291ac930b` | `b2ba4e6331f6b1e44aee186436b141e903d528553c5ff78516edd6a1b4ad7ac3` |
| `net/minecraft/server/level/ServerPlayer.java` | `5cd23824185202d9367674c9c208415f0f5f9922a247f0749c28849e295694b0` | `fe7146799ebe95ba92220ad21a38a80f5352ddbb21ccefb93063b8f9a89631dd` |
| `net/minecraft/server/level/ChunkMap.java` | `b7309e9d6b6b3d0ebbda146231392b679a78dc0c5ce54f54549a986fda2adec7` | `07110b3a9c6b688ea1ed7afcbffa72019ce6143b2e7a2db4235a57cba5dbda49` |
| `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/network/protocol/game/ClientboundSetPassengersPacket.java` | `6b194a346ae005be3d1c490d015546392c17ae63a3e56ed370795c48ddf8b371` | `77602265cfc1407dbEADBFEA19DED2B3242BC946B55548D0C505D3B164D4C637` |
