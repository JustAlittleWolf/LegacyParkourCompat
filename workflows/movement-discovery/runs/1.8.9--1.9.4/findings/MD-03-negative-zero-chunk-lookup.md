# MD-03: Unloaded-chunk fallback selects a different chunk near zero

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: stage 3 ground/air movement and unloaded-client-chunk fallback; stage 4 block-position conversion and chunk lookup
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A artifact A; `../../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `LivingEntity.moveRelative(float,float)` lines 1170-1184; SHA-256 `082831C6578E3A70FA6CEA5B90BC3EEFC26678259B66334470DE22B90B5B0E4E`. After moving, the client checks chunk state using `new BlockPos((int)this.x, 0, (int)this.z)`; if that chunk is unavailable, it sets `velocityY` to `-0.1` when `y > 0.0`, otherwise `0.0`. If loaded, it subtracts `0.08` instead.
- B artifact B; `../../../../../decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `LivingEntity.moveRelative(float,float)` lines 1396-1412; SHA-256 `BBB7703F18FD5DA05C4E4A43A77EA644B388E63C01D34166D308EA52054BE4E5`. With no Levitation effect, the same client fallback uses `pooledMutable.set(this.x, 0.0, this.z)` before the chunk checks and applies the same velocity assignments once that selected chunk is found unavailable.
- A coordinate and chunk conversion: `../../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/util/math/BlockPos.java`, int constructor lines 18-20 passes the integer coordinates to `Vec3i(int,int,int)`, which stores them at lines 11-13; hashes `5C8E0BAAFEAEA9D941E6A8C730AAA8800A96D0CE84C43CFCE1AC6F979CED9D10` and `8C2005298883F9D04F8C1B4AD53E1C98F1BD37EBF88AA58F37B9C1CE096B3115`. `World.isChunkLoaded(BlockPos,boolean)` lines 175-176 and `World.getChunk(BlockPos)` lines 230-231 map `pos.getX()`/`getZ()` to chunks with arithmetic `>> 4`; `World.java` SHA-256 `3C04F5B874FBB6692039164C882A3C47FE28C7ECA864E91C9AC57798153888EE`.
- B coordinate conversion: `../../../../../decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/util/math/BlockPos.java`, `PooledMutable.set(double,double,double)` lines 356-358 delegates to `Mutable.set(double,double,double)` lines 272-274, which floors each coordinate via `MathHelper.floor`; `Vec3i(double,double,double)` lines 19-20 uses the same floor conversion. Hashes `FEB3784D161A120A6737AE54E51AF4B07EEE51A71191E9BE11962DA7F3B6D70A` and `E5F5476A28EF90B229506257BD4EEBD1010725A32AB98C0DFADA1B546F50DC66`.
- B chunk conversion: `../../../../../decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/world/World.java`, `isChunkLoaded(BlockPos,boolean)` lines 186-187 and `getChunk(BlockPos)` lines 239-240 use the same `>> 4` coordinate-to-chunk conversion; SHA-256 `2FE063E0EC224EED9FC7D01B8F788C32035EED5FEE5A9D98EA5E82296236E05A`.

## Source-level difference

For a negative fractional X or Z coordinate between `-1.0` and `0.0`, A's explicit Java `(int)` conversion truncates toward zero and queries block coordinate `0`; B's pooled mutable position floors the coordinate and queries block coordinate `-1`. The world then shifts that block coordinate by four to select the chunk, so these positions belong to chunks 0 and -1 respectively. The fallback predicates and resulting vertical-velocity assignments are otherwise the same.

## Reachability and dependencies

The local player reaches `LivingEntity.moveRelative()` in the ordinary non-water, non-lava, non-fall-flying branch while locally controlled. For this slice B must not have Levitation active, because its new Levitation branch bypasses the chunk fallback. The chunk lookup occurs after `move`, using the resulting player X/Z. `World.isChunkLoaded` and `World.getChunk` derive their selected chunk from the integer block coordinates using `>> 4`.

## Consequence and uncertainty

Source proves a distinct chunk lookup only at the negative-zero boundary. If one of chunks -1 and 0 is loaded while the other is not, A and B can take opposite fallback branches. For `y > 0.0`, the selected chunk determines whether this code assigns `-0.1` or subtracts `0.08` before the shared `0.98F` vertical drag. The predicted next-tick vertical velocity can therefore differ; no trajectory or chunk-streaming state was tested.

## Handoff

Independent delta: negative-fraction truncation versus floor in the client unloaded-chunk movement fallback. Keep separate from Levitation and from block support/friction position lookups. Applicability is the local player's client movement near coordinate zero when adjacent chunk load states differ.
