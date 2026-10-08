# TICK-10: unloaded-client-chunk sampling uses different block coordinates

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: ordinary land travel's client unloaded-chunk gravity gate; `TICK-10`, `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`
- Classification: source-confirmed coordinate-conversion delta in a movement velocity gate
- Confidence: source-confirmed for the paired local-player ordinary land branch and chunk-coordinate consumers
- Applicability: local-player ordinary non-fall-flying land travel, when the player is client-side and fractional X or Z is immediately below a negative multiple of 16, and loadedness differs between the two adjacent chunks
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A `LivingEntity.moveRelative(FF)V`, lines 1119-1186, applies `move()`, the climbing response, then checks client chunk loadedness using `new BlockPos((int)this.x, 0, (int)this.z)` in both calls. The `BlockPos(int,int,int)` constructor stores those integer coordinates. When either query reports unloaded, it writes `velocityY` to `-0.1` if `y > 0`, otherwise `0`; otherwise it subtracts `0.08`. It then applies `velocityY *= 0.98F` and X/Z slipperiness. `LivingEntity.java` SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; A `World.isChunkLoaded(BlockPos)` lines 171-176 and `World.getChunk(BlockPos)` lines 230-232 both use `pos.getX() >> 4` and `pos.getZ() >> 4`. `World.java` SHA-256 `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`.
- B `LivingEntity.moveRelative(FF)V`, lines 1302-1485, has the corresponding ordinary land branch at 1391-1414. Except for B's separate Levitation branch (modern-only and outside this historical slice), it sets a `BlockPos.PooledMutable` from `(this.x, 0.0, this.z)` before the same two client loadedness checks and the same gravity/fallback and drag writes. `BlockPos.Mutable.set(double,double,double)` floors each coordinate at lines 272-274, and `PooledMutable.set(double,double,double)` delegates to it at lines 356-358. `BlockPos.java` SHA-256 `feb3784d161a120a6737ae54e51af4b07eee51a71191e9be11962da7f3b6d70a`. B `World.isChunkLoaded(BlockPos)` lines 182-187 and `World.getChunk(BlockPos)` lines 239-241 both shift the stored coordinates right by four. `World.java` SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`.
- Reachability: A/B `PlayerEntity.moveRelative(FF)V` delegates to `super.moveRelative()` whenever the creative-flight wrapper does not apply (A lines 1279-1294, B 1372-1390). Its paired sources have SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` / `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`. `TICK-09` establishes the normal player travel callers. B's separate fall-flying branch is outside this historical slice.

## Source-level difference

A truncates the floating coordinate toward zero before choosing a chunk; B floors it. The resulting chunk differs only at negative chunk boundaries for coordinates just below a multiple of 16, such as `-0.2` (A stores 0, B stores -1). If this maps the two versions to chunks with different loadedness on a client, their Y-velocity update takes different branches: ordinary gravity versus the unloaded fallback (`-0.1` above Y=0, otherwise `0`), followed by the shared `0.98F` multiplier. No chunk-loading state or subsequent trajectory is inferred.

## Handoff

Source finding only. The input-to-travel ordering, source lineage, chunk availability and subsequent movement consumers remain in their parent inventories; no implementation or runtime claim is made.
