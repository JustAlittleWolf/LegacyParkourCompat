# Boat rider-turn eligibility: 1.20.2 to 1.20.4

## Scope and result

This is a bounded trace of the local player’s direct boat-rider path and the passenger-rotation eligibility gate. It makes no claims about boat movement trajectories or parity. The identity-bound 1.20.2 and 1.20.4 sources show that the boat changes from always applying its turn delta to passenger yaw to gating all passenger-rotation writes on `CAN_TURN_IN_BOATS`. The staged 1.20.3 files match 1.20.4 byte-for-byte but lack release identity, so the exact introduction point within 1.20.2→1.20.4 is unresolved. The local player’s type is `EntityType.PLAYER`, so whether it is excluded depends on the loaded tag contents.

The canonical packaged tag membership for Player is **unverified**: the canonical client JAR payloads/resources are absent from the local evidence roots checked. Do not infer packaged membership from the data-generation provider.

## Direct player path

- `ClientLevel.tickPassenger(parent, passenger)` first drops a removed passenger or one whose vehicle is no longer the parent; otherwise it ticks the passenger when `passenger instanceof Player` or it is in `tickingEntities`. It invokes `passenger.rideTick()` (1.20.2 lines 273–284; 1.20.4 lines 279–290). A local player therefore qualifies through the explicit `instanceof Player` branch. ClientLevel source hashes: 1.20.2 `6204df69a17b375e46f4405fe67a106def336c819e589ec0794c04920a31c5d7`; 1.20.4 `2bc6147b758ba25fea89e1c7c8e28000cf323f1616b01b62af461cbce230e308` (same staged file hash for 1.20.3).
- `net.minecraft.client.player.LocalPlayer.rideTick()` calls `super.rideTick()`; if `getControlledVehicle()` is a `Boat`, it passes the local input booleans to `Boat.setInput(...)`. The method body is unchanged across 1.20.2, 1.20.3 and 1.20.4 in the staged source files (SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`). Source lines 846–853.
- The inherited ride path is `LocalPlayer.rideTick()` → `LivingEntity.rideTick()` → `Entity.rideTick()`. `LivingEntity.rideTick()` delegates with `super.rideTick()`, then updates run animation and fall distance (1.20.2 lines 2739–2744; 1.20.4 lines 2741–2746). `Entity.rideTick()` zeroes passenger delta movement, calls `tick()`, and then, if `isPassenger()`, dispatches to `getVehicle().positionRider(this)` (1.20.2 lines 1827–1844; 1.20.4 lines 1828–1845). The 1.20.3 staged bodies match 1.20.4, but that staged release has no source identity sidecars (see below).
- `Player` constructs itself with `super(EntityType.PLAYER, level)` (1.20.2 `Player.java:182`; 1.20.4 `Player.java:177`). Thus the tag lookup performed on the passenger entity type is a lookup for `EntityType.PLAYER` on the local-player path.
- In 1.20.2 `Boat.positionRider(Entity, MoveFunction)` first calls `super.positionRider`, then unconditionally adds `deltaRotation` to passenger `YRot` and `YHeadRot`, clamps passenger rotation, and applies the full-boat Animal body/head adjustment. Source lines 700–709.
- In the staged 1.20.3/1.20.4 `Boat.positionRider`, after `super.positionRider`, the same writes and Animal adjustment are all inside `if (!passenger.getType().is(EntityTypeTags.CAN_TURN_IN_BOATS))`. Source lines 665–675. A member of the tag skips those writes; nonmembers take the same body as 1.20.2. The Boat tick/input/vehicle simulation itself is outside this bounded direct-player trace.

## Exact source identities and hashes

All source paths below are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`. Namespace and symbol aliases used here are Mojmap: `LocalPlayer.rideTick`, `Entity.rideTick`, `Player`, `Boat.positionRider`, `EntityType.PLAYER`, and `EntityTypeTags.CAN_TURN_IN_BOATS`.

- **1.20.2, Mojmap:** provenance records exact run `1.20.1,1.20.2,1.20.4,1.20.6`, mapping option `mojmap`, Vineflower 1.12.0, Java 25, Gradle 9.7.1. `mojmap.ready.json` source-manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; ready `client.jar` SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`; mapping artifact `1.20.2/client_mappings.txt` SHA-256 `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd`; mapped artifact `1.20.2/client-mojmap.jar` SHA-256 `3dce01cbbb74e367414c811440ca6e80bc2ec18284386720ce413f9aec0ba901`. Ready JSON SHA-256 `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f`; provenance JSON SHA-256 `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`.
  - `Boat.java` SHA-256 `8d01cdf2502e17ffd03f85c4add4b87316baaeea609f40a3d6f66d0a3c110686`.
  - `LocalPlayer.java` SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`.
  - `LivingEntity.java` SHA-256 `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828`.
  - `Entity.java` SHA-256 `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b`.
  - `Player.java` SHA-256 `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c`.
  - `EntityTypeTags.java` SHA-256 `85f060bacc890f2c63c54bb96ff404feac781c66ca01a268fc2891217fb6b002`; it does not declare `CAN_TURN_IN_BOATS`.
- **1.20.3, staged Mojmap tree:** the assigned source directory contains files, but has no `mojmap.provenance.json`, `mojmap.ready.json`, `mojmap.sources.sha256`, or `artifacts.sha256`. Its `ClientLevel.java`, `Boat.java`, `LocalPlayer.java`, `LivingEntity.java`, `Entity.java`, `Player.java`, and `EntityTypeTags.java` hashes are byte-identical to 1.20.4 files. This is only a byte-identity alias to the staged 1.20.4 source, not an independently bound 1.20.3 source identity; no release-specific source conclusion is made from it.
- **1.20.4, Mojmap:** provenance records the same pinned decompiler batch and tools as 1.20.2. `mojmap.ready.json` source-manifest SHA-256 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; ready `client.jar` SHA-256 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`; mapping artifact `1.20.4/client_mappings.txt` SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`; mapped artifact `1.20.4/client-mojmap.jar` SHA-256 `d20c183016505cceecd33d508973e7ca6f2ee8cbd860f316592f4a09a96bc339`. Ready JSON SHA-256 `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1`; provenance JSON SHA-256 `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385`.
  - `Boat.java` SHA-256 `f0296d2198d1d0d1f65162126197249789c5d22f081841af454af3a074024109`.
  - `LocalPlayer.java` SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`.
  - `LivingEntity.java` SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`.
  - `Entity.java` SHA-256 `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`.
  - `Player.java` SHA-256 `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`.
  - `EntityTypeTags.java` SHA-256 `3805f34f55ae616145b2c10b4f6e6c78223096248f4a45779f698cc9f8482d97`; it declares the tag key.

## Resource identity and membership gate

The source declares the tag identifier as `minecraft:can_turn_in_boats` (`EntityTypeTags.java:27` in the staged 1.20.3/1.20.4 files). The likely packaged resource path to inspect is `data/minecraft/tags/entity_type/can_turn_in_boats.json`. No such unpacked resource exists in the decompiled Mojmap source trees. The verified provenance lists client-jar hashes, but the referenced build artifact payload directory (`D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07`) is absent; the local `.minecraft/versions` cache contains only 26.3 and 26.4-snapshot-3, and the Loom cache has only a `26.2/minecraft-client.jar` among client jars. No 1.20.2/1.20.3/1.20.4 client jar payload was found in those locations. Therefore there are no canonical packaged resource bytes here to hash or inspect, so exact default membership for Player cannot be asserted.

`UpdateOneTwentyOneEntityTypeTagsProvider.addTags` in the staged 1.20.3/1.20.4 source adds `EntityType.BREEZE` to this tag; that is data-generation source for the 1.21 update tag provider and is not evidence of the packaged default tag JSON in any of these three releases. The 1.20.2 tag-key source has no such key and the 1.20.2 boat has no tag check.
