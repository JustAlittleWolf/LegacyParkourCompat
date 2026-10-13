# Blind source-snapshot review — 2026-10-08

Scope: independent source-only review of the two immutable findings below. Exact vanilla sources were inspected from ready Feather trees for 1.9.4, 1.10.2, and 1.11.2; cited file hashes match the per-version source manifests. No mod implementation, wiki material, build, runtime, or tests were used. Both version-pair discoveries remain partial.

## F-07 — REQUEST CHANGES

Snapshot identity:
- Source commit: `bd95d35bd9a9a0ae468b9cf035f99f08639e91c0`
- Path: `workflows/source-campaign-2026-10-07/1.10.2--1.11.2/findings/F-07-player-pose-fit-stale-outer-stair-shape.md`
- Finding SHA-256: `e2e44bc72fd166294d6fdae72e145d2a483b286f7700b5eedf82d41e1afef3d1`

The claimed 1.10.2→1.11.2 collision-query delta is contradicted by the exact call chain. In 1.10.2, `PlayerEntity.updatePlayerPose` asks `World.getCollisions`, which dispatches block collision shapes. `StairsBlock.addCollisions` resolves virtual stair properties before selecting boxes. In 1.11.2, the pose-fit path also asks `World.getCollisions`; its public wrapper passes `true` for world-border enforcement, while the shared block loop passes literal `false` as `forceShape` to `BlockState.addCollisions`. The stair provider resolves virtual properties when `forceShape` is false. The finding conflates these distinct booleans. Both pose-fit queries therefore use neighbor-derived stair shapes, so the asserted stale straight-shape collision and resulting occupancy difference do not follow.

Primary ranges: 1.10.2 `PlayerEntity.java:296–322`, `World.java:1022–1055`, `StairsBlock.java:66–89`; 1.11.2 `PlayerEntity.java:295–321`, `World.java:960–997,1058–1059`, `StairsBlock.java:65–88`.

Cited source SHA-256 values:
- 1.10.2: `World.java` `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`; `PlayerEntity.java` `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`; `StairsBlock.java` `ee143aced2a6902563cddbae77e78d1d1a512244f278987684ebe5bcacba37cb`; `Block.java` `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`.
- 1.11.2: `World.java` `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58`; `PlayerEntity.java` `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`; `StairsBlock.java` `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627`; `Block.java` `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`.

Artifact limitation: derived JAR hashes and revision metadata in the finding were reproducible, but the metadata says the original mapped JAR was unavailable. Equivalence to that original artifact remains unverified. This does not change the source-based decision.

## F005 — ACCEPT

Snapshot identity:
- Source commit: `4bded1c9cda9fd3dae61675d6f3083956806b8af` (branch `feat/source-discovery-movement-source-1-9-4-1-10-2-resume3`)
- Path: `workflows/source-campaign-2026-10-07/1.9.4--1.10.2/findings/F005-fall-flying-state-persists-across-player-load.md`
- Finding SHA-256: `3e55452cc24c5684544afc3c6f17af2fe06ad2712b2512c8399d6cdff89b5a4b`

The source supports the finding. 1.10.2 `LivingEntity.writeCustomNbt` stores `FallFlying`; `readCustomNbt` reads it and restores shared flag 7. These key reads/writes are absent from the corresponding 1.9.4 source. `ServerPlayerEntity` delegates its custom NBT read/write to `LivingEntity`. Login loads saved player NBT through `PlayerManager.load` before adding/tracking that player. A producer also exists: the server's accepted START_FALL_FLYING path calls `ServerPlayerEntity.setFlying`, setting flag 7, which the save method records.

The flag reaches local movement: dirty synced data is sent in an entity-data packet; `TrackedEntity.sendPacketToAll` explicitly sends a tracked server player's packets to that player's own network handler as well as other trackers. The client handler applies received entries to the entity identified by packet ID. `LocalClientPlayerEntity.isLocallyControlled()` returns true; `LivingEntity.mobTick` calls `flyingTick` then `moveRelative`, whose fall-flying branch changes velocity. This establishes the persistence-to-local-movement path without claiming that persistence bypasses ordinary Elytra/ground validity checks.

Primary ranges: 1.10.2 `LivingEntity.java:460,463–497`; corresponding 1.9.4 `LivingEntity.java` custom-NBT methods (no `FallFlying` key); 1.10.2 `ServerPlayerEntity.java:169–183,1042–1044`; `PlayerManager.java:94–102,266–277`; `ServerPlayNetworkHandler.java:846–850`; `TrackedEntity.java:269–273,292–296`; `ClientPlayNetworkHandler.java:447–452`; `LocalClientPlayerEntity.java:458–459`; `LivingEntity.java:1332–1365,1743–1748`.

Cited source SHA-256 values:
- 1.9.4: `LivingEntity.java` `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; `PlayerEntity.java` `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`; `SyncedData.java` `a139e116a5e2c9fc6cff49a60b4d8c82a9fec2b1fc9470e8df3b619610feb2c2`; `PlayerManager.java` `4b77ddc263a0d9e17ffa359370ef56f9d1b59834ec53a21f29aeb8cc02a571c3`; `TrackedEntity.java` `82e4772fb6da96cbc92548211f7e0f5161b68e1b71726f26a6e2b7bb08ef09f0`; `ClientPlayNetworkHandler.java` `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c`; `LocalClientPlayerEntity.java` `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- 1.10.2: `LivingEntity.java` `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`; `PlayerEntity.java` `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`; `SyncedData.java` `a3a8af735e8fe3a43cba943ec192bc0a1f4aea4095a2b5f849710c31c92a74e2`; `PlayerManager.java` `bead7d1b104d289752b193b772e704034763dc76231982132be7f1c3906a4437`; `ServerPlayerEntity.java` `de500c8bf8e82a3f89d3a7f7208f3eed3d47f616344fb1e72aaacfd9e331a03f`; `ServerPlayNetworkHandler.java` `14b537a3f11e57d8364e90508f45b996b7e0593b741064167d85256166a287c6`; `TrackedEntity.java` `842cf1310c7c0a57e63c1a82d0dddc3bfb96913dac684ea5340a32cf26051090`; `EntityMap.java` `d3818806da6850e4543ae71432b179bd68feaed002cf515a95ced4c207056d83`; `ClientPlayNetworkHandler.java` `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656`; `LocalClientPlayerEntity.java` `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`.

These decisions disposition only the two cited findings; they do not complete either version-pair discovery.