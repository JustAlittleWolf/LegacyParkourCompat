# Levitation fallback now uses the dimension minimum for unloaded client chunks

- Older version A: `1.16.5`
- Newer version B: `1.17.1`
- Mechanic / coverage slice IDs: `S3-LEVITATION-UNLOADED-CHUNK`
- Classification: changed behavior
- Confidence: candidate (the active dimension type is external data not present in either client jar)
- Applicability: modern-only mechanic/configuration
- First changed release: unknown within (1.16.5, 1.17.1]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel branch lines 2028–2037; SHA-256 `B5D8A1A3C80F85D5D545B5A777E9E2A915DC002A7E31B5F5AD12BF7E285D7A88`.
- B: `decompiled_minecraft/1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, same branch lines 2117–2126; SHA-256 `33FD081AADB2B6FDC9EBF487DB6DA5B38C54F4B8676572790EE2203690D15E6F`.
- B `world/level/LevelReader.java`, `getMinBuildHeight()` lines 68–70 returns `dimensionType().minY()`; SHA-256 `4D041CDBC702D532C5B9DA800C34BD1C23F8F3D3B554ECBDD89DECBB765A6B451A`.
- B `world/level/dimension/DimensionType.java`, codec `min_y` field around line 63 and `minY()` around line 412; SHA-256 `2DC118F16B671D3B1B9736C10E960E199790F7930CFA18983F379BEB8D00F27B`.
- A `DimensionType` has no `minY` field/codec, and A `LevelReader` has no `getMinBuildHeight()` method; A uses the explicit `0.0` comparison in the cited movement branch.
- Neither exact client jar contains a `data/minecraft/dimension_type/` resource entry. The active version-matched server/datapack dimension type definition was not available for this run.

## Source-level difference

Both sides enter this fallback only when the level is client-side and the block position used for movement friction is in an unloaded chunk, after the levitation-effect branch has been excluded. A sets the vertical value to `-0.1` when `getY() > 0.0`, otherwise `0.0`. B uses `getY() > level.getMinBuildHeight()` for the same choice. B's level accessor reads the active dimension type's `minY`; its codec accepts `min_y` values below zero. A's movement branch uses zero directly and A's dimension type has no `minY` field.

## Reachability and dependencies

`LivingEntity.travel(Vec3)` is used by the local player through the living movement path; the branch reads the player's current levitation effect, client-side flag, unloaded-chunk state and active dimension minimum. For a dimension type with `minY = -64`, a player at `Y = -1` in an unloaded client chunk yields `0.0` from A's threshold and `-0.1` from B's threshold. The dimension type is a server/datapack input. Its exact synchronization/registration source and the actual active dimension definition must be resolved before claiming a deployment-level consequence.

## Consequence and uncertainty

The code path differs under the stated source preconditions. Standard dimension data with `minY == 0` makes the two predicates identical. The feature permitting a nonzero lower bound is absent from A's dimension-type schema, so this is outside the dimension configurations an A-era map could specify. Exact dimension data is missing from available client-jar resources and no server/datapack artifact was supplied; this slice remains blocked for environment-specific disposition.

## Handoff

Candidate, conditional delta: the unloaded-chunk levitation fallback follows the B dimension minimum instead of the fixed zero floor. Keep it separate from historical movement for A maps unless additional evidence establishes an A-reachable dimension configuration with a nonzero lower bound.
