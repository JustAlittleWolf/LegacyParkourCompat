# F002: Generic no-gravity state gates fluid travel gravity (candidate)

- Older version A: 1.9.4, Ornithe Feather `net.ornithemc:feather-gen2:1.9.4+build.2`
- Newer version B: 1.10.2, Ornithe Feather `net.ornithemc:feather-gen2:1.10.2+build.2`
- Mechanic / coverage slice IDs: fluid travel gravity; `LIVING-FLUID-NO-GRAVITY`
- Classification: added mechanic
- Confidence: candidate (pair mapped-jar hashes currently differ from ready artifact manifests; awaiting canonical repair and fresh verification)
- Applicability: unresolved
- First changed release: unknown within (1.9.4, 1.10.2]
- Runtime validation: not performed

## Paired evidence

- A artifact: validated 1.9.4 Feather source tree, pending derived-artifact integrity repair. `net/minecraft/entity/living/LivingEntity.java`, `LivingEntity#moveRelative(float,float)`, lines 1302-1470, SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; the lava and water branches subtract `0.02` after vertical drag, and the ordinary fallback branch subtracts `0.08` after its loaded-client/server guard. No generic `Entity.isNoGravity` gate appears in the body. A’s `Entity.java` SHA-256 is `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; the only A no-gravity implementation found in the entity tree is ArmorStand-specific.
- B artifact: validated 1.10.2 Feather source tree, pending derived-artifact integrity repair. `net/minecraft/entity/living/LivingEntity.java`, `LivingEntity#moveRelative(float,float)`, lines 1332-1506, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`; the fluid gravity subtractions are conditional on `!this.isNoGravity()`.
- B state source: `net/minecraft/entity/Entity.java`, generic `NO_GRAVITY` synced data registration/default at lines 139 and 192, accessors at 796-801, NBT save at 1252-1253 and load at 1354; source SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.

## Source-level difference

B introduces a generic entity no-gravity flag with default false and makes the `LivingEntity` fluid travel vertical gravity decrements conditional on that flag. Under the false default, the newly added conditions do not suppress gravity. If the flag is true on a player, the affected water/lava gravity subtractions are skipped in B; A has no corresponding generic flag in the checked entity hierarchy.

## Reachability and dependencies

Player `LivingEntity` travel reaches `moveRelative` through the local-player → player → living-entity call path. The source tree confirms the B synced-data accessors and NBT reader, but the pair review has not established whether and how a live client player receives a true flag from a server or another vanilla player path. This server/external state producer must be closed before assigning player applicability. Pair artifact integrity is also unresolved and blocks accepting the finding.

## Consequence and uncertainty

Source conditionals show that B can omit these vertical gravity decrements when the generic flag is true. The player reachability of that state and resulting trajectory are unverified. The full original Gradle stdout was not persisted, and the cached mapped-jar hashes differ from the ready manifests; wait for canonical repair and fresh verification before treating the source-tree result as accepted evidence.

## Handoff

Investigate B’s synced entity-data update path and direct player writers/consumers, verify the exact mapped artifacts after canonical repair, then decide whether the conditional changes player movement. Implementation and runtime tests remain deferred.
