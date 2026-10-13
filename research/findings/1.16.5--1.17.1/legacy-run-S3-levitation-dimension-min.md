# Levitation fallback now uses the dimension minimum for unloaded client chunks

- **1.16.5**, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 2028–2037.
- **1.17.1**, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 2117–2126.

## Difference

Both sides enter this fallback only when the level is client-side and the block position used for movement friction is in an unloaded chunk, after the levitation-effect branch has been excluded. 1.16.5 sets the vertical value to `-0.1` when `getY() > 0.0`, otherwise `0.0`. 1.17.1 uses `getY() > level.getMinBuildHeight()` for the same choice. 1.17.1's level accessor reads the active dimension type's `minY`; its codec accepts `min_y` values below zero. 1.16.5's movement branch uses zero directly and 1.16.5's dimension type has no `minY` field.

## Movement meaning

`LivingEntity.travel(Vec3)` is used by the local player through the living movement path; the branch reads the player's current levitation effect, client-side flag, unloaded-chunk state and active dimension minimum.

For a dimension type with `minY = -64`, a player at `Y = -1` in an unloaded client chunk yields `0.0` from 1.16.5's threshold and `-0.1` from 1.17.1's threshold.

The dimension type is a server/datapack input.

Its exact synchronization/registration source and the actual active dimension definition must be resolved before claiming a deployment-level consequence.

The code path differs under the stated source preconditions.

Standard dimension data with `minY == 0` makes the two predicates identical.

The feature permitting a nonzero lower bound is absent from 1.16.5's dimension-type schema, so this is outside the dimension configurations an 1.16.5-era map could specify.

Exact dimension data is missing from available client-jar resources and no server/datapack artifact was supplied; this slice remains blocked for environment-specific disposition.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S3-levitation-dimension-min.md).
