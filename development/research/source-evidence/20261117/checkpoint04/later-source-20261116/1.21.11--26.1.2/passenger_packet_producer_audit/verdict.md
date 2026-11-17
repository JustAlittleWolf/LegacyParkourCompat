# Independent audit: passenger packet producer slice

## Verdict

**ACCEPT, bounded NO_DIFF.** This verdict binds to `build/later-source-20261116/1.21.11--26.1.2/passenger_packet_producer/coverage.md`, SHA-256 `9B742B1E6FEB36B49B93524791AF801E36943DAA24DE64798D713733859B7E48`.

The report accurately compares the named server-side passenger-list packet producers, filtering, direct mount/dismount delivery, pairing payload, and packet encoding. No behavior difference was found in that bounded producer slice. It does not establish full-pair coverage, vehicle physics parity, mount-policy parity, or runtime movement parity.

## Identity and evidence checks

- The exact source directories and namespaces are `decompiled_minecraft/1.21.11/mojmap/` and `decompiled_minecraft/26.1.2/unobfuscated/`.
- Actual provenance sidecars are the root-level `mojmap.provenance.json` and `unobfuscated.provenance.json` under each version directory. Their current SHA-256 values match the report: 1.21.11 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; 26.1.2 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`.
- The ready-sidecar hashes match the report: 1.21.11 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; 26.1.2 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`. Each ready marker declares the expected release, namespace, and manifest digest; the current source-manifest sidecar hashes match those declarations.
- Independently rehashed the five cited source files in each tree and checked each result against its source-manifest row. All ten checks match the report and manifest: `ServerEntity.java`, `ServerPlayer.java`, `ChunkMap.java`, `Entity.java`, and `ClientboundSetPassengersPacket.java`.
- A recursive `rg` search of each complete published Java source directory found exactly five `new ClientboundSetPassengersPacket(...)` call sites, matching the report's inventory: two in `ServerPlayer` and three in `ServerEntity` (`sendChanges` and the two `sendPairingData` branches). No other constructor call site was found.

## Independent source checks

- `ServerPlayer.startRiding` sends the packet only after `super.startRiding` succeeds; rider positioning, relative-rotation teleport, optional active-effect sync, and packet send are in the same order in both versions. `removeVehicle` captures the prior vehicle, removes the relationship, sends optional effect removals, and sends the passenger packet only when the captured vehicle is non-null, also in the same order.
- `ServerEntity.sendChanges` compares the current passenger list against `lastPassengers`; on change it sends the full current list to tracked players whose own membership boolean is unchanged, then updates `lastPassengers`. The predicate and order match.
- `ChunkMap.TrackedEntity.sendToTrackingPlayersFiltered` sends only to connections whose player satisfies the predicate. New pairing uses the same horizontal-distance, `broadcastToPlayer`, and tracked-chunk conditions before calling `ServerEntity.addPairing` at both endpoints.
- `ServerEntity.sendPairingData` emits the entity's passenger list when nonempty and emits the vehicle's list when the paired entity is a passenger. The checked packet ordering relative to the surrounding pairing records agrees.
- `Entity.passengers` is an `ImmutableList` in both versions, and add/remove replaces it rather than mutating the snapshot. The server-side player-first insertion rule is also present in both versions. `ClientboundSetPassengersPacket` snapshots ordered passenger IDs and encodes vehicle VarInt followed by passenger VarInt array in both sources.

The mapping/decompiler variable names and formatting differ, but the relevant gates, recipients, order, list payload, and encoding are consistent. The report's boundary and unknowns are explicit and accurate.

## Limits

This acceptance is confined to the listed passenger-list packet producer and tracking routes. Mount eligibility, other packet types, client interpolation, vehicle movement, concrete vehicle attachment calculations, intermediate-release boundaries, and the whole pair remain open. No artifacts, product code, backlog/wiki, Git history, build, test, or runtime were inspected.
