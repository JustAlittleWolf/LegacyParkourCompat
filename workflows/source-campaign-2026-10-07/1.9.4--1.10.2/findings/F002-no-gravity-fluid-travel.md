# F002: Generic no-gravity state gates fluid travel gravity

- Older version A: 1.9.4, Ornithe Feather `net.ornithemc:feather-gen2:1.9.4+build.2`
- Newer version B: 1.10.2, Ornithe Feather `net.ornithemc:feather-gen2:1.10.2+build.2`
- Mechanic / coverage slice IDs: fluid travel gravity; `LIVING-FLUID-NO-GRAVITY`
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: historical player behavior when the server-loaded player's saved `NoGravity` flag is true; default false
- First changed release: unknown within (1.9.4, 1.10.2]
- Runtime validation: not performed

## Paired evidence

- A artifact: exact 1.9.4 Feather source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`, original artifact-manifest SHA-256 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`. `net/minecraft/entity/living/LivingEntity.java`, `LivingEntity#moveRelative(float,float)`, lines 1302-1470, SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; water/lava subtract `0.02`, and the ordinary movement branch subtracts `0.08` when the client chunk is loaded or on the server; the unloaded-client fallback instead sets vertical velocity to `-0.1` above Y=0 or zero. No generic `Entity.isNoGravity` gate appears in the body. A `Entity.java` SHA-256 is `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; A's no-gravity state is ArmorStand-specific and is not inherited by PlayerEntity.
- B artifact: exact 1.10.2 Feather source manifest SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`, original artifact-manifest SHA-256 `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`. `net/minecraft/entity/living/LivingEntity.java`, `LivingEntity#moveRelative(float,float)`, lines 1332-1506, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`; the two `0.02` and one `0.08` subtractions are gated by `!this.isNoGravity()`.
- B state source: `net/minecraft/entity/Entity.java`, generic `NO_GRAVITY` synced-data registration/default at lines 139 and 192, accessors at 796-801, NBT save at 1252-1253 and load at 1354; SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.
- Revised mapped-artifact evidence: revision `feather-r1-2026-10-07`; A immutable jar SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`, revision.json SHA-256 `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`; B immutable jar SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`, revision.json SHA-256 `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`. Both jars matched `artifact.sha256`/`revision.json`; all A/B source files and original client/raw mapping inputs were rehashed; independent ops audit passed. Original derived jars are unavailable and equivalence to them is unproven.
- Bytecode confirmation: `javap -c -p` over the cited immutable jars and `net.minecraft.entity.living.LivingEntity#moveRelative(float,float)` shows A has no `isNoGravity()` invocation and executes the `0.02` subtractions at offsets 200/351; B calls `isNoGravity()` at offsets 197/355 before those subtractions and at 1347 before the `0.08` subtraction. Exact revised jar hashes above bind this disassembly.
- B saved-player reachability evidence: `server/PlayerManager.java::onLogin`, lines 94-101, and `#load`, lines 266-277, SHA-256 `bead7d1b104d289752b193b772e704034763dc76231982132be7f1c3906a4437`; `world/storage/AlphaWorldStorage.java::loadPlayerData`, lines 162-178, SHA-256 `96bdadfd51daf326d2550aa2f6c0ea49878bc60476998a81244b217a1ac5b910`, read saved player data into `player.readNbt`. `entity/Entity.java::readNbt`, lines 1347-1355, calls `setNoGravity(nbt.getBoolean("NoGravity"))`; accessors are lines 796-801, same Entity SHA above. `entity/data/SyncedData.java::set`, lines 118-125, SHA-256 `a3a8af735e8fe3a43cba943ec192bc0a1f4aea4095a2b5f849710c31c92a74e2`, marks changes dirty. `server/TrackedEntity.java::sendDataAndAttributes`, lines 269-284, and `#sendPacketToAll`, lines 286-297, SHA-256 `842cf1310c7c0a57e63c1a82d0dddc3bfb96913dac684ea5340a32cf26051090`, sends dirty entries and includes the tracked `ServerPlayerEntity`'s own network handler; `#tick` invokes data publishing at lines 123-238 (same hash). `network/packet/s2c/play/EntityDataS2CPacket.java`, lines 17-39, SHA-256 `e431eca560c9aab47b934991dd546fa80634284373dfcac47158836bc6bf28d8`; `client/network/handler/ClientPlayNetworkHandler.java::handleEntityData`, lines 447-452, SHA-256 `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656`, resolves packet ID in the client world and applies entries. `handleLogin` sets the local player network ID at line 278 and calls `Minecraft.setWorld`; `client/Minecraft.java` adds that local player to the world at lines 1906-1914, SHA-256 `0979d770e8742a07bb8c54cf7fd3449a8c43a4f48910600ffabf75f1f67ba2d9`; `world/World.java::getEntity(int)`, lines 2245-2247, returns the mapped entity, SHA-256 `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`.

## Source-level difference

B introduces a generic entity no-gravity flag with default false and gates the `LivingEntity` water/lava `-0.02` and ordinary-branch `-0.08` vertical gravity decrements on that flag. The unloaded-client fallback remains the separate `y > 0 ? -0.1 : 0.0` assignment. Under the false default, the new gates do not suppress the subtractions. When saved player NBT sets the flag true, the server syncs it back to that player's client entity; B skips the gated fluid/ordinary gravity subtractions, while A has no corresponding generic player flag.

## Reachability and dependencies

Player `LivingEntity` travel reaches `moveRelative` through the local-player → player → living-entity call path. The B saved-player NBT, dirty-data broadcast to the owning `ServerPlayerEntity`, and client packet application close the player reachability path for saved `NoGravity=true` state. Normal default is false. Pair revision hashes and source/raw inputs were independently verified; the original derived jars are unavailable and their equivalence remains unproven.

## Consequence and uncertainty

Source and bytecode show that B omits these vertical gravity decrements when the synced player flag is true. The saved-data and network path is source-confirmed. Exact trajectory consequences depend on fluid state and movement order and are not claimed. Revised artifact identity is explicitly limited because the original derived jars are unavailable.

## Handoff

Compare B's generic no-gravity fluid gates with A's unconditional decrements, only when server-loaded player data carries `NoGravity=true`. Keep the source revision limitation attached. Implementation and runtime tests remain deferred.
