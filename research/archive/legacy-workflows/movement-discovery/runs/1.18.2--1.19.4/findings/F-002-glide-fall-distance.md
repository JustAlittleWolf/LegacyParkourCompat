# F-002: Glide updates slow fall distance only when above one block

- Older version A: Minecraft 1.18.2
- Newer version B: Minecraft 1.19.4
- Mechanic / coverage slice IDs: stage 3 fall-flying travel; stage 4 fall-distance state writer
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior (player wearing Elytra and in fall-flying travel)
- First changed release: unknown within (1.18.2, 1.19.4]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact: `run.md` A / 1.18.2; client SHA-256 `1D09E3639644B6B2254499469D0765CC005A286D19F3FA595B0ED8FB07971EC7`, mappings SHA-256 `A2AA6EE1030BFEF79E9B2E08E79DE1637FDD7ECB5BF8891CF2E9A4B186042543`, Mojmap remapped jar SHA-256 `2D0C4B2EAC022E43DBE4706B7FE18C51547E4FBED86B675E92AB2040A9CF51D4`.
- B manifest artifact: `run.md` B / 1.19.4; client SHA-256 `0E79CF7F07C107E9A1FE22ED703E472372B44149E64114944F0811ABBD25F3EC`, mappings SHA-256 `5EF270B938F89CFC77371E0DEEE9F9044C41D9A373FA11DCB1FD1923272C4606`, Mojmap remapped jar SHA-256 `3E40F8B67DC696F4E08F2F7AFD4D4C2E263B2A86221D13141953C7FDAA80375D`.
- A: `decompiled_minecraft/1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2074–2079; SHA-256 `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782`. In the `isFallFlying()` branch it reads the vertical delta and, when `y > -0.5`, unconditionally assigns `fallDistance = 1.0F` before the glide movement calculations.
- B: `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2048–2051; SHA-256 `C8D91AF61F87AAA1666DE79696D7CD9D4A8212D05F873BDAF28D7BB2926BF165`. The same branch calls `checkSlowFallDistance()` before continuing the glide calculations.
- B: `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, `checkSlowFallDistance()`, lines 2167–2170; SHA-256 `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B`. It assigns `1.0F` only when `getDeltaMovement().y() > -0.5` **and** `fallDistance > 1.0F`.
- A `Entity.java` (SHA-256 `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A`) has no `checkSlowFallDistance()` helper; the A assignment is inline in the player-reachable glide branch. The relevant fall-distance state is later read by entity fall handling; actual server damage remains server-authoritative.

## Source-level difference

Under the shared glide-branch condition `vertical delta > -0.5`, A always overwrites accumulated `fallDistance` with `1.0F`, including when it was `0.0F` or between zero and one. B sets it to `1.0F` only if the accumulated value was already greater than `1.0F`; values at or below one remain unchanged. The delta is the extra `fallDistance > 1.0F` guard in B's helper. Values greater than one are still reduced to one in B.

## Reachability and dependencies

A local player can reach `LivingEntity.travel()` while fall-flying after the player equips/activates Elytra; the glide branch updates this state before its movement math. The helper is inherited from `Entity` in B. `fallDistance` persists as player state and feeds later fall handling, but damage application is server-side. The source establishes different client-side state writes; it does not prove the server's eventual damage result or a trajectory.

## Consequence and uncertainty

For a gliding player whose vertical delta is above `-0.5`, B preserves a smaller fall distance that A would raise to `1.0F`; for an accumulated value above one, both leave one. This can change later fall-distance-dependent handling after leaving glide. No gameplay result was measured. Exact first changed release remains unknown within `(1.18.2, 1.19.4]`.

## Handoff

This is one glide-state delta. Keep fall-distance accumulation, Elytra activation, movement math, and server-side damage as separate dependent slices where code changes require them. Implementation and runtime tests are deferred.
