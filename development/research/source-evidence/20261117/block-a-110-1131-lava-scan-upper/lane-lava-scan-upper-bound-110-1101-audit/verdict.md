# Independent bounded source audit — 1.10 → 1.10.1 lava scan

## Exact binding

- Frozen blind roster: `roster.md`, SHA-256 `be4b074791f3b1a56fd1af4cf41af4e6d166461e1ca3f24a918197c2558faf8e`.
- Reviewed author comparison: `comparison.corrected-r1.md`, SHA-256 `ec4ac3ff5d0ab706a94b8cfcc7bfe5f40acf465f57041ddacb1e130b7866c0a7`.
- Reviewed author provenance note: `provenance.md`, SHA-256 `9a58bfd522aff0170cf1b02c02be53b3bb84cd31b744212797c848c45509efe3`.
- Primary evidence: exact canonical Ornithe Feather sources for 1.10 and 1.10.1, mapping roots under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather\`.

## Source publication verification

Both hidden per-mapping `.decompile-provenance.json` manifests exist and their SHA-256 values match the comparison and provenance note: 1.10 `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da`; 1.10.1 `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`. Their input records identify exact client releases 1.10 and 1.10.1, matching Feather metadata endpoints, and Feather Gen2 `1.10+build.2` / `1.10.1+build.2` mapping archives. Client, metadata and mapping archive SHA-256 inputs match the values in the frozen roster. Programmatic verification found all 1,845 source paths present and every file SHA-256 equal to its per-file manifest entry in each release.

## Independent trace

- `LocalClientPlayerEntity.tick()` begins inherited client ticking only when `world.isChunkLoaded(new BlockPos(x, 0.0, z))` is true. The method is byte-identical across the pair. The local player tick path reaches inherited living ticking; its `mobTick()` enters the jump decision and travel section, scales movement inputs, calls `flyingTick()`, and then dispatches `moveRelative`.
- `Entity.isInLava()` in both releases passes `getShape().grown(-0.1F, -0.4F, -0.1F)` and `Material.LAVA` to `World.containsMaterial`. `Box.grown(double,double,double)` subtracts each delta from minima and adds it to maxima, so these float literals are widened to double and contract the box by approximately 0.1 on X/Z and 0.4 on Y.
- Both `World.containsMaterial` methods use floor(minX/minY/minZ), ceil(maxX/maxY/maxZ), then nested integer loops in X/Y/Z order with strict `<` upper comparisons. In particular, if contracted `maxY` equals integer N, `ceil(maxY)` is N and loop Y=N is excluded; positions from `floor(minY)` through N-1 are candidates. A nonintegral upper bound is rounded upward, and the highest scanned Y is `ceil(maxY)-1`. The method compares material identity and returns on the first hit. I found no per-cell loaded-chunk test in this scanner.
- `World.getBlockState` returns AIR outside world height and otherwise calls `getChunk(pos).getBlockState(pos)`. The local tick column gate is distinct from the scan and does not prove adjacent columns loaded. The normal movement branch has a separate client gravity gate requiring `isChunkLoaded(pooledMutable) && getChunk(pooledMutable).isLoaded()`; that gate is after the lava branch and is not used by it.
- In `LivingEntity.moveRelative`, movement runs under `isLocallyControlled() || isLogicalSideForUpdatingMovement()`. Water is checked before lava. A non-flying player in lava takes the lava movement branch, which applies `updateVelocity(..., 0.02F)`, moves by velocity, halves X/Y/Z velocity, applies `-0.02` gravity unless no-gravity, and may apply the horizontal collision step. A flying player can bypass the lava branch condition. `PlayerEntity.moveRelative` preserves the flying override/delegates to the superclass. Both the local tick gate and movement/query gates match across the pair.

## Method evidence

SHA-256 values below are computed over the exact extracted method text from each mapped source file; each paired value is identical:

| Method | 1.10 | 1.10.1 |
|---|---|---|
| `World.containsMaterial(Box, Material)` | `32aa474acfd430eed4837d7db5d0b33de1ba58500e1ecaea430521b39674f34c` | `32aa474acfd430eed4837d7db5d0b33de1ba58500e1ecaea430521b39674f34c` |
| `Entity.isInLava()` | `e0682c9491745ff612d6cb4b5b73be2fc9e43ba4ceae57f5d7b042b90500e242` | `e0682c9491745ff612d6cb4b5b73be2fc9e43ba4ceae57f5d7b042b90500e242` |
| `LivingEntity.moveRelative(float,float)` | `18c9cdfa50d85ae46da37fbc72eba21e3832022b5bd879d8a9db4c2dac47c45e` | `18c9cdfa50d85ae46da37fbc72eba21e3832022b5bd879d8a9db4c2dac47c45e` |
| `PlayerEntity.moveRelative(float,float)` | `e477b0d2123caa60eba3661c3273fb36a7f3f99c0b1319c67645e197cae3c4b4` | `e477b0d2123caa60eba3661c3273fb36a7f3f99c0b1319c67645e197cae3c4b4` |
| `LocalClientPlayerEntity.tick()` | `522084d075dd61a0c53b236f3507f230172463bf1fb02bb9e5bc5b7012bc1590` | `522084d075dd61a0c53b236f3507f230172463bf1fb02bb9e5bc5b7012bc1590` |
| `Box.grown(double,double,double)` | `46e36555f52f10989165e7d2340e4f0a97ab00f2696fc4fdd6597a8bef3733b0` | `46e36555f52f10989165e7d2340e4f0a97ab00f2696fc4fdd6597a8bef3733b0` |

The source-file hashes for `Entity.java`, `LivingEntity.java`, and `LocalClientPlayerEntity.java` also match across the pair; `World.java` differs as a whole, while its extracted scanner method matches. `PlayerEntity.java` differs as a whole, while its movement override matches. All cited files pass the manifest source-hash checks.

## Scoped verdict

**BOUNDED AUDIT ACCEPTED — NO DIFFERENCE FOUND.** The corrected author conclusion is independently supported for the assigned 1.10 → 1.10.1 lava-material scan and local-player travel slice, including the integer upper-Y boundary and the inspected geometry/chunk gates. No evidence supports a scan-boundary change in this pair. This verdict binds only to the exact artifacts and source methods above; it does not establish complete pair inventory, pair-wide source acceptance, an intermediate patch boundary, implementation correspondence, or runtime parity.
