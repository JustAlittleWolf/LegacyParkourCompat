# Independent source/resource audit: boat turn tag

## Verdict

The paired 1.20.2/1.20.4 source claim and local-player caller trace are supported. The report correctly leaves Player's canonical `minecraft:can_turn_in_boats` membership unresolved and makes no boat trajectory or parity claim.

One report conclusion needs correction: 1.20.3 has release-specific input and source provenance in `mojmap/.decompile-provenance.json`. It records the 1.20.3 metadata, client JAR and mapping inputs by URL and SHA-256, plus hashes for individual source files. All seven relevant source hashes match both the provenance entries and the 1.20.4 tree. This supports locating the gate change in 1.20.3. The input payloads are absent, so their hashes could not be rechecked from archive bytes.

The supplied author-report revision digest also does not match the current file: expected `b170f86b83a14b07ae7be268ba439dcd1f22bbc6526b2c48d3da10078cd33897`; observed `4a7ba4b23a8e08aa9d530497ccb8582d37ec2dee9081b910ae91484a63cc2851`. This audit applies to the current on-disk report bytes.

## Bound 1.20.2 and 1.20.4 identities

Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft`, Mojmap.

- 1.20.2 ready metadata says `versionId=1.20.2`, mapping `mojmap`. Ready JSON SHA-256: `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f`. Source manifest SHA-256: `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`. Client JAR SHA-256: `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`. Provenance JSON SHA-256: `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`.
- 1.20.4 ready metadata says `versionId=1.20.4`, mapping `mojmap`. Ready JSON SHA-256: `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1`. Source manifest SHA-256: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`. Client JAR SHA-256: `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`. Provenance JSON SHA-256: `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385`.

I independently recomputed the report's relevant file hashes for Boat, LocalPlayer, LivingEntity, Entity, Player, EntityTypeTags and ClientLevel; the listed hashes match. Player is at `net/minecraft/world/entity/player/Player.java`; its constructor sets `EntityType.PLAYER` at 1.20.2 line 182 and 1.20.4 line 177. 1.20.2 EntityTypeTags does not declare `CAN_TURN_IN_BOATS`; 1.20.4 declares it at line 27.

## Direct local-player path and changed gate

- `ClientLevel.tickPassenger` drops a removed passenger or one whose vehicle changed; otherwise it ticks any `Player` via `rideTick()` (1.20.2 lines 273–284; 1.20.4 lines 279–290). A local player qualifies.
- `LocalPlayer.rideTick()` calls `super.rideTick()`, then passes local input to its controlled Boat if applicable (lines 846–853). Its source hash matches across all three staged trees.
- `LivingEntity.rideTick()` delegates to `Entity.rideTick()` before updating run state and fall distance (1.20.2 lines 2739–2744; 1.20.4 lines 2741–2746). `Entity.rideTick()` zeros passenger velocity, ticks the entity, and then calls its vehicle's `positionRider(this)` if it remains a passenger (1.20.2 lines 1827–1832; 1.20.4 lines 1828–1833).
- 1.20.2 `Boat.positionRider` calls `super`, unconditionally adds `deltaRotation` to passenger yaw and head yaw, clamps rotation, then applies the full-boat Animal body/head adjustment (lines 700–709).
- 1.20.3 and 1.20.4 wrap those rotation writes and the Animal adjustment in `if (!passenger.getType().is(EntityTypeTags.CAN_TURN_IN_BOATS))` after `super.positionRider` (lines 665–675). Tag members skip those writes; nonmembers retain the 1.20.2 body. This establishes the predicate behavior, not Player's membership.

## 1.20.3 provenance and boundary

The seven relevant 1.20.3 files (ClientLevel, Boat, LocalPlayer, LivingEntity, Entity, Player, EntityTypeTags) are byte-identical to their 1.20.4 counterparts. The 1.20.3 tree lacks `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, and `artifacts.sha256`, but includes `mojmap/.decompile-provenance.json` (SHA-256 `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`). It records:

- the `1.20.3.json` metadata URL with SHA-256 `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`;
- Mojang `client.jar` URL with SHA-256 `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`;
- Mojang `client.txt` mappings URL with SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`;
- each of the seven relevant source file hashes, matching both the actual 1.20.3 files and the 1.20.4 files.

Thus the report's statement that 1.20.3 has no release identity evidence is incorrect. The staged provenance records a source publication identity, though the original inputs cannot be independently rehashed because the payloads are absent. On the available provenance, the gate is present in 1.20.3; this is the first checked version with the conditional body.

## Packaged tag resource search

The campaign payload directory `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07` is absent. The local `.minecraft/versions` cache contains only `26.3` and `26.4-snapshot-3`. The searched Fabric Loom cache has only 26.2 client JARs, with no 1.20.2, 1.20.3 or 1.20.4 client archive. No unpacked `can_turn_in_boats.json` exists in the relevant decompiled source trees. These scoped checks support that canonical packaged tag bytes are unavailable in the checked roots; they do not prove no copy exists elsewhere.

The resource to inspect is `data/minecraft/tags/entity_type/can_turn_in_boats.json` in the identity-bound client archive. Until its bytes are recovered and inspected, Player membership remains unknown. `UpdateOneTwentyOneEntityTypeTagsProvider.addTags` is data-generation source and does not establish default packaged JSON membership for these releases.

## Conclusion

Paired source facts, the local-player call chain and the 1.20.3 gate are supported. The report should correct its 1.20.3 identity/boundary conclusion. The remaining practical evidence gap is the packaged tag resource needed to determine whether Player skips boat passenger rotation. The supplied report hash mismatch also means this audit cannot be bound to the requested report revision.
