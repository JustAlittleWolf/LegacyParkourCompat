# 1.20.3–1.20.4 default boat tag membership

## Scope and inputs

Windows PowerShell host. Read-only exact Mojmap source trees in `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.20.3\mojmap` and `...\1.20.4\mojmap`; no source generation or downloads. The frozen independent boat-rider verdict was read only after checking its requested SHA-256: `boat-rider-rotation-audit/verdict.md`, `b8dcb68e54d716cdbcd1bee777b4157fb721da54009c7bb768cd39a7be4e17b5`.

The 1.20.3 tree has `.decompile-provenance.json`; the 1.20.4 tree has `mojmap.provenance.json` and `mojmap.sources.sha256`. The relevant 1.20.3 source entries match their manifest hashes. The 1.20.4 source list matches recomputed hashes. Each compared tag-provider and `Main.java` file is byte-identical between the two source trees.

## Default tag source

In both releases, `net/minecraft/data/tags/EntityTypeTagsProvider.java`, `addTags` has no `CAN_TURN_IN_BOATS` entry and no `EntityType.PLAYER` membership. Its SHA-256 is `d66c8e81c8bed4778dce31eed91b0492f5348011bd9e4b5960638371009c1dbf` in both trees.

`net/minecraft/data/tags/UpdateOneTwentyOneEntityTypeTagsProvider.java` does add `EntityType.BREEZE` to `CAN_TURN_IN_BOATS`, but `Main.java` registers it in the separate 1.21 update feature-pack provider group (around lines 171–179, with `FeatureFlags.UPDATE_1_21` metadata), while the ordinary entity-tag provider is registered in the base pack group around line 134. This is update-pack data, not default base-pack membership. The update provider SHA is `f21562603085f25ddae0ba125f5b21b8b1d13076ed0d2013e70275c3562ead9c` and `Main.java` SHA is `0aebf4ab316bb3f148c0fc2e783801f52b718cafa7af30b69de807ccbe3ac3ca` in each tree. `EntityTypeTags.java` declares the tag key; searches of both exact source trees found no other Java membership reference.

The exact 1.20.3 `client.jar` already present under the primary `.minecraft-decompile-cache` has no `data/minecraft/tags/entity_type/can_turn_in_boats.json` entry. This is consistent with an empty vanilla base tag: vanilla `PLAYER` is untagged by default in 1.20.3, so Boat's `!passenger.getType().is(CAN_TURN_IN_BOATS)` condition is true and the player enters the untagged/clamp branch. It does not enter the excluded tagged branch. A datapack that adds PLAYER can change the predicate. There is no exact 1.20.4 client archive under the allowed primary cache, so packaged 1.20.4 tag data could not be checked independently; source Java still shows no PLAYER in the base provider.

## Separate mouse-turn clamp

The frozen boat-rider verdict's separate clamp result remains supported by the exact local sources: `Entity.turn` calls `vehicle.onPassengerTurned(this)` after mounted player turning, and `Boat.onPassengerTurned` calls `clampRotation` in both 1.20.3 and 1.20.4. The `Entity.java` and `Boat.java` SHA-256 values are respectively `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` and `f0296d2198d1d0d1f65162126197249789c5d22f081841af454af3a074024109` in both trees. The new positionRider tag test does not gate this callback path. No boat physics or early boat-placement behavior was reassessed.

## Residual evidence gate

The exact 1.20.4 packaged tag resource was unavailable in the permitted local cache. Close that independent resource check only if an exact preexisting 1.20.4 client/package artifact becomes available; otherwise retain this package-level limitation. Datapack overrides and runtime parity were not examined.

TERMINAL: COMPLETED; report-body SHA-256: f30d6ef1463f0eb0cf42f08c7449bf6bebe609bfe95a7bf1fa342c911a8cfaae; residual evidence gate: exact 1.20.4 packaged tag resource unavailable in permitted local cache; default vanilla PLAYER is untagged in the source-defined base provider and checked 1.20.3 package.
