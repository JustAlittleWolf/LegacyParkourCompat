# Earlier body-fluid admission at an unloaded chunk edge

## Verdict

**Positive prerequisite.** The historical 1.13 client body-fluid scan admits a still-loaded WATER cell even when the inclusive area guard reaches an adjacent missing chunk. This is a concrete case for the historical body-state query. It says nothing about the separate fluid-current impulse path.

## Exact sources and provenance

All source paths below are relative to `D:/Javastuff/LegacyParkourCompat/`. SHA-256 values are hashes of the canonical file bytes read-only from that checkout.

| Release / mapping | Source or provenance file | SHA-256 |
|---|---|---|
| 1.13 / Ornithe Feather | `decompiled_minecraft/1.13/ornithe-feather/net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| 1.13 / Ornithe Feather | `decompiled_minecraft/1.13/ornithe-feather/net/minecraft/world/WorldView.java` | `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543` |
| 1.13 / Ornithe Feather | `decompiled_minecraft/1.13/ornithe-feather/net/minecraft/client/world/ClientWorld.java` | `07e09cc5636ff7e12f2ca35aef528fca6305e6017d499a65926617cef82f64dc` |
| 1.13 / Ornithe Feather | `decompiled_minecraft/1.13/ornithe-feather.provenance.json` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` |
| 1.13.1 / Ornithe Feather | `decompiled_minecraft/1.13.1/ornithe-feather/net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.1 / Ornithe Feather | `decompiled_minecraft/1.13.1/ornithe-feather.provenance.json` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` |
| 1.13.2 / Ornithe Feather | `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 / Ornithe Feather | `decompiled_minecraft/1.13.2/ornithe-feather.provenance.json` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` |
| 1.14 / Ornithe Feather | `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/entity/Entity.java` | `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9` |
| 1.14 / Ornithe Feather | `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/world/WorldView.java` | `1a9d57363b8123e48334065c363c5610e83dd353fe66edc2f08fed3ec09ddcb0` |
| 1.14 / Ornithe Feather | `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/client/world/ClientWorld.java` | `eb4985a468055bb206add2f5ae8d94864c7ab3a771fd40844c4fa6bc4e35b1b8` |
| 1.14 / Ornithe Feather | `decompiled_minecraft/1.14/ornithe-feather.provenance.json` | `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa` |
| 1.15.2 / Mojmap | `decompiled_minecraft/1.15.2/mojmap/net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.15.2 / Mojmap | `decompiled_minecraft/1.15.2/mojmap/net/minecraft/world/level/LevelReader.java` | `d09aa8eaaa7f8ca8703091facb4d7e1a26c9f4bea8b99c5c8956682875241d37` |
| 1.15.2 / Mojmap | `decompiled_minecraft/1.15.2/mojmap.provenance.json` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` |
| 1.16.2 / Mojmap | `decompiled_minecraft/1.16.2/mojmap/net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| 1.16.2 / Mojmap | `decompiled_minecraft/1.16.2/mojmap/net/minecraft/world/level/LevelReader.java` | `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` |
| 1.16.2 / Mojmap | `decompiled_minecraft/1.16.2/mojmap.provenance.json` | `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c` |
| 26.2 / unobfuscated | `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 / unobfuscated | `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/level/LevelReader.java` | `8fbfb7cf6d6ff8a19e784198d6bf75a1eae346cd8371d3d23eb3c8af546621a1` |
| 26.2 / unobfuscated | `decompiled_minecraft/26.2/unobfuscated.provenance.json` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` |

The 1.13 sidecar identifies `versionId=1.13`, `namespace=ornithe-feather`, exact coordinate `net.ornithemc:feather-gen2:1.13+build.2`, client jar SHA-256 `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e`, and Feather-mapped client jar SHA-256 `4b91c330aaf6179f7384f70f91ebcdf72af2713cc3788fea1482c3529905a761`. The 1.14 sidecar identifies `net.ornithemc:feather-gen2:1.14+build.2` and client jar SHA-256 `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`. Mojmap 1.15.2 and 1.16.2 sidecars record exact requested release IDs and mapping namespace; 26.2 records `versionId=26.2`, `namespace=unobfuscated`.

The 1.13.1 and 1.13.2 trees both contain the same Entity.java bytes as each other in this canonical source publication. Their tree-local provenance sidecars record distinct requested release IDs and exact preparation metadata; this is not a claim that separate releases share the same raw client artifact.

## Call, predicate, and scan

- 1.13 `Entity.java:946-950` calls `checkWaterState()` from the entity's water-state updater; `:960-974` checks boats, calls `m_69693160(FluidTags.WATER)`, then records `inWater` and resets fall distance on success. The corresponding call/helper remains at 1.13.1 `:947-976` / `:2509+`, 1.13.2 `:947-976` / `:2509+`, and 1.14 `:890+` / `:2502+` under the same Feather alias `m_69693160`.
- The 1.13/1.13.1/1.13.2 helper computes `box = getShape().contract(0.001)`, bounds `floor(min)`/`ceil(max)`, and first calls virtual `world.isAreaLoaded(i,k,m,j,l,n,true)`. Its rejection occurs only when this predicate returns false. It then scans `p < j`, `q < l`, `r < n`, reads `world.getFluidState(pos)` and sets the body flag only for matching fluid whose surface reaches the box bottom. The upper bounds in the guard are inclusive at the chunk-check layer, while the fluid loops are exclusive at their upper block bounds.
- 1.13 `WorldView.java:377-394` implements the seven-argument overload by checking `maxY >= 0 && minY < 256`, shifting each X/Z endpoint by 4, and calling `this.isChunkLoaded(chunkX, chunkZ, allowEmpty)` over both endpoints inclusive. That call is virtual. `ClientWorld.java:112-115` implements `isChunkLoaded(x,z,allowEmpty)` as `allowEmpty || !getChunkSource().getChunk(x,z).isEmpty()`. Since the entity passes `allowEmpty=true`, this client predicate admits even the absent adjacent chunk. The guard therefore returns true and the body scan reads the still-loaded candidate cell. 1.14 `WorldView` / `ClientWorld.java:221-223` aliases the same outcome: the client override of `isChunkLoaded(x,z)` returns true.
- Concrete 1.13.2 local-client input: ordinary player bbox width 0.6 centered at x=15.7, with feet at y=64 and center z=8.0, hence x bounds [15.4,16.0]. After contraction by 0.001, `i=15`, `j=16`. The scan reads x=15 in loaded chunk 0 only; place still-loaded WATER at (15,64,8), whose fluid surface reaches the player's box bottom. The area predicate checks chunk X 0 and 1 because it shifts inclusive `minX=15` and `maxX=16`; chunk 1 is absent but `allowEmpty=true` makes `ClientWorld.isChunkLoaded` return true. The body helper admits the WATER sample and returns true. The sample is read from the loaded chunk; no out-of-range `getFluidState` is required.

This proves an actual historical admission at the requested missing-chunk margin. The odd inclusive guard margin is real, but in 1.13.x it does not reject this input because the client override allows empty chunks.

## Release aliases and later control

The behavior-bearing operation alias evolves as follows: Feather 1.13–1.14 `m_69693160(Tag<Fluid>)` under the public state updater (`checkWaterState` in 1.13; `updateInWaterState` appears by 1.15.2); Mojmap 1.15.2 `checkAndHandleWater(Tag<Fluid>)`; Mojmap 1.16.2 `updateFluidHeightAndDoFluidPushing(Tag<Fluid>, double)`; unobfuscated 26.2 retains `updateFluidHeightAndDoFluidPushing(Tag<Fluid>, double)`.

1.15.2 and 1.16.2 use the same shape contraction, floor/ceil bounds, exclusive nested block loops and fluid-state reads, but their body helpers guard with `level.hasChunksAt(i,k,m,j,l,n)` (1.15.2 `Entity.java` `checkAndHandleWater`; 1.16.2 `Entity.java:2641+`). Mojmap `LevelReader.hasChunksAt` checks both chunk endpoints inclusively and returns false if any `hasChunk` is false. This can reject the x=15.7 geometry when neighboring chunk 1 is actually absent. 26.2 `Entity.java:1645-1656` delegates body discovery to `fluidInteraction.update(...)` and then reads `isInFluid(WATER)`; 26.2 `LevelReader.java:188-210` keeps inclusive endpoint chunk checks. Those later controls are consistent with the guard-margin mechanism, but do not establish a patch-level introduction boundary.

## Limits

Source-only static result. No product checkout, Git history/index, backlog, findings, wiki, source generation, launch, build, or test was accessed or changed. No exact 1.14-to-1.15.2 boundary is claimed. No conclusion is made about eye-fluid state, fluid impulses, arbitrary world queries, or the excluded non-player/vehicle paths.
