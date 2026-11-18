# Independent source audit: 1.20.2–1.20.4 boat rider tag

## Verdict

The launch finding's unresolved tag-membership question is closed for the exact vanilla 1.20.4 archive. For an ordinary Player in the vanilla default state, `Boat.positionRider` performs the same yaw addition, head-yaw addition, and clamp as 1.20.2. The 1.20.4 guard does not create a direct Player rotation difference. The newly conditional operation applies to riders whose types are in `minecraft:can_turn_in_boats`; it does not support a Player movement change for this source pair under vanilla defaults.

This verdict covers the Player gate and vanilla archive resources only. A custom server/datapack tag override is external to this archive and is a separate state that could affect the guard. It does not alter the vanilla default membership conclusion. No other vehicle behavior was audited.

## Bound identities

- Launch: repository commit `583f8a9b01205b09922ed76d5a0eadfbe819cc85`.
- Launch finding: `development/research/findings/1.20.2--1.20.4/boat-rider-rotation.md`, Git blob `2f02a10b99c59023f978a4e38743f103bd11b311`.
- 1.20.2 exact source set: Mojmap, ready metadata versionId `1.20.2`, client SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`, source-manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`.
- 1.20.4 exact source set: Mojmap, ready metadata versionId `1.20.4`, client SHA-256 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`, source-manifest SHA-256 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`.
- Recovered 1.20.4 archive `source-1.20.4/client.jar`: SHA-256 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`; SHA-1 `fd19469fed4a4b4c15b2d5133985f0e3e7816a8a`. The SHA-256 matches the 1.20.4 ready metadata client identity.
- Companion successor evidence note `C:\Users\Wolfi\.codex\visualizations\2026\11\18\01a1f01a-5064-75c0-a594-b2e7fa3b99e2\c-external-boat-rider-tag\successor-1.20.4-tag-bytes.md`: SHA-256 `6a1426f728f966a9491d66404c8e3a02d39e81f77f94784ff709e919cac07a83`.

## Source comparison and reachable client role

Exact Mojmap `net/minecraft/world/entity/Entity.java` has the same `rideTick` dispatch in both releases: 1.20.2 lines 1827–1833 and 1.20.4 lines 1828–1834 call `this.getVehicle().positionRider(this)` when `isPassenger()` is true. The client passenger path is directly visible in `net/minecraft/client/multiplayer/ClientPacketListener.java`: `handleSetEntityPassengersPacket` starts the player riding the vehicle (1.20.2 lines 923–950; 1.20.4 lines 953–980). This bounds the observation to normal client passenger processing; it makes no claim about server-side historical movement.

- 1.20.2 `net/minecraft/world/entity/vehicle/Boat.java:700–710`: calls `super.positionRider`, adds `deltaRotation` to rider YRot and YHeadRot, then calls `clampRotation`. The full-boat `Animal` adjustment is separate and cannot match Player.
- 1.20.4 same source path `:665–677`: calls the same superclass placement first, then guards the same rotation writes and animal adjustment with `!rider.getType().is(EntityTypeTags.CAN_TURN_IN_BOATS)`.
- `clampRotation` is the same body in both releases (1.20.2 `Boat.java:744–751`; 1.20.4 `Boat.java:711–718`): sets body yaw to boat yaw, wraps the rider yaw difference, clamps it to `[-105.0F, 105.0F]`, corrects old/current yaw, and sets head yaw to the corrected yaw.
- In 1.20.4 `EntityTypeTags.java:27` declares the tag. `EntityType.java:902–904` delegates tag membership to its built-in registry holder; `Holder.java:155–157` tests the holder's loaded tag set. `TagLoader.java:41–84, 100–128` loads resource tag entries into those sets.

## Independent 1.20.4 archive verification

Using read-only ZIP inspection of the exact recovered JAR:

- There is no default `data/minecraft/tags/entity_types/can_turn_in_boats.json` member.
- The only archive member ending in `can_turn_in_boats.json` is `data/minecraft/datapacks/update_1_21/data/minecraft/tags/entity_types/can_turn_in_boats.json`, length 44 bytes, SHA-256 `342edad98ebf6fc00d1189d1fb6bf5840d80658e7fbf9915442e8d1d256b083f`. Its exact UTF-8 JSON values contain only `minecraft:breeze`; there is no Player entry or nested tag.
- The adjacent `update_1_21/pack.mcmeta` enables feature `minecraft:update_1_21` and declares pack format 26. Exact Mojmap `FeatureFlags.java:32–41` sets `DEFAULT_FLAGS` to `VANILLA_SET`, which contains only `VANILLA`; `WorldDataConfiguration.java:17` uses those flags for its default. Therefore the required `update_1_21` feature pack is not selected by the vanilla default state, and the ordinary default tag resource is absent. If that experimental feature pack is selected, its sole member still adds Breeze only. Thus Player is absent in both default and bundled experimental-feature states.

The finding's conditional description of the 1.20.4 source is accurate for boat riders in general. Its former uncertainty about ordinary Player membership is now resolved: with the exact vanilla resource state, Player is not in the tag, so the new negated guard passes and Player receives the same rotation operations as in 1.20.2. This is a source-gate disposition, not an observed trajectory claim.

## Limits and write scope

No decompilation was regenerated; exact shared sources were read only. No game/server, tests, or build were run. No file in the primary project was written. This audit report was written only in the assigned visualization directory.


