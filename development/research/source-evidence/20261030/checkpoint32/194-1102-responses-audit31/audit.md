# Independent audit: 1.9.4 → 1.10.2 client response consumers

## Verdict

The named response methods in the submitted report support its narrow “no independently demonstrated change” result. The assigned response inventory is not terminally complete, however. In-scope receive routes omitted from its examined list include entity-relative movement/teleport, synchronized metadata, player sleep/wake position and box updates, and the consumer side of received movement attributes/effects. Several are retraced below and have no resulting movement delta; the report should record them before this slice is called complete.

The report’s identity is verified: SHA-256 `6c33dcb9fef05bc054204d75fa0696f7454c0ceae44b0ccd91a5c31e04ad0e50`.

## Source identity and files

Canonical read-only roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.4\ornithe-feather\` and `...\1.10.2\ornithe-feather\`. Ready/provenance records identify exact versions 1.9.4 and 1.10.2, Ornithe Feather Gen2 build.2 mappings, and ready source-manifest hashes `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` and `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`.

Additional file SHA-256 values (older / newer):

- `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`: `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c` / `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656`.
- `net/minecraft/entity/Entity.java`: `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` / `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.
- `net/minecraft/entity/living/player/PlayerEntity.java`: `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` / `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`.
- `net/minecraft/entity/living/LivingEntity.java`: `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` / `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` / `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`.
- `net/minecraft/entity/data/SyncedData.java`: `a139e116a5e2c9fc6cff49a60b4d8c82a9fec2b1fc9470e8df3b619610feb2c2` / `a3a8af735e8fe3a43cba943ec192bc0a1f4aea4095a2b5f849710c31c92a74e2`.
- `net/minecraft/client/Minecraft.java`: `6778bd1f0e8aa8a29fdce3bc5285cbd3ea56fa18ca8a43afc4d88439ab12133e` / `0979d770e8742a07bb8c54cf7fd3449a8c43a4f48910600ffabf75f1f67ba2d9`.

## Additional receive and writer edges retraced

- `ClientPlayNetworkHandler.handleEntityTeleport(EntityTeleportS2CPacket)`, 1.9.4 lines 476–496 / 1.10.2 lines 482–502; `handleEntityMove(EntityMoveS2CPacket)`, 507–524 / 513–530. These resolve an entity from the world and are not restricted to remote players. `Entity.getControllingPassenger()` returns null for a player entity here, so on a client `isLogicalSideForUpdatingMovement()` is false; if the packet targets the local player id, both handlers reach `lerpPositionAndAngles` and update the player’s position, rotation and shape (plus `onGround` for movement). Both handler bodies preserve their gate, fixed-point conversion, angle handling and calls. The report covered `PlayerMoveS2CPacket` correction but omitted these direct local-player-capable receive paths.
- `handleEntityData(EntityDataS2CPacket)`, lines 444–450 / 447–453, forwards entries to `SyncedData.update`; that update applies entries and calls `onDataValueChanged` on both versions. The local callback adds a 1.10.2 Elytra sound branch for shared flag 7; its pre-existing item-use flag path is retained and the addition writes no position, velocity or box. This handler/callback path is not in the report’s examined list.
- `handlePlayerSleep`, 743–746 / 749–752, calls `PlayerEntity.trySleep`; `handleEntityAnimation` action 2, 719–739 / 725–745, calls `wakeUp`. These are direct local-player position/velocity/shape writers. Their endpoint positioning and size logic is equivalent. One source-order detail differs in `trySleep`: 1.9.4 chains the three zero assignments (`velocityX = velocityZ = velocityY = 0.0`), while 1.10.2 assigns X, then Y, then Z. There is no intervening read/call and the final velocity state is identical, so this is not a demonstrated movement result difference. Neither receive route is listed by the submitted report.
- `handlePlayerRespawn` calls the corresponding `Minecraft.respawnPlayer`; the response method recreates the local player, transfers synced data, resets position and network id, and reinitializes input in the same relevant order in both versions. The submitted report marks respawn only partial and should close this called body for the response slice.
- Ability and attribute receipt bodies match in the cited report, and the local tick consumers relevant to those values retain their movement operations: `PlayerEntity.mobTick` refreshes movement speed from `MOVEMENT_SPEED`; `PlayerEntity.getSpeed` reads the same attribute; and `LivingEntity.jump` applies the same `JUMP_BOOST` term. `LivingEntity.addStatusEffect` and removal/update dispatch also preserve the existing effect-cache transitions. `LocalClientPlayerEntity.mobTick` changes the sprint comparison from a local `0.8F` variable to the same literal, and the added auto-jump work remains the distinct tick change identified in the earlier tick-boundary slice. New `isNoGravity` gates and the new Elytra callback behavior are 1.10.2 feature paths, not changed values from these ability/effect packet handlers; this audit does not promote them as 1.9.4 response deltas.
- The direct mounted-player correction `handleVehicleMove`, 1492–1499 / 1493–1500, is the same `getVehicle` → local movement-authority predicate → update-position-and-angles → acknowledgement path. This confirms the report’s direct-player-side vehicle conclusion. Vehicle physics and non-player mount movement remain outside this review.

## Writer-order qualification

The report says the correction handler’s predicate, arithmetic, write order and gates are unchanged. At the handler-body level that is accurate, but its transitive `Entity.updatePositionAndAngles` writer changed statement scheduling: 1.9.4 chains each current/last coordinate assignment, while 1.10.2 writes X/Y/Z first and then copies them to lastX/lastY/lastZ. The values passed, clamps, final current/last coordinates, angle normalization, and final shape are unchanged, with no callback between assignments. This is a source-order difference, not a demonstrated distinct movement result; qualify “write order unchanged” as referring to the handler body rather than the callee’s internal statement order.

`Entity.setPosition` still writes X/Y/Z then constructs the same width/height box on both sides. `setShape` remains a plain shape-field write. These are the common shape writers reached by the receive paths above.

## Completeness boundary

This audit closes the omitted receive endpoints identified above as no-difference (while noting the two assignment-order changes with identical final state). It does not establish pair freeze or full source coverage. The report’s own unexamined local tick/travel, `Entity.move` collision-response body, collision providers, callbacks, shapes and world queries remain dependencies outside this response audit. The receive report should add the omitted direct handlers/callees and distinguish their verified no-difference result from those remaining pair-wide dependencies. No canonical finding draft is warranted from this response audit.
