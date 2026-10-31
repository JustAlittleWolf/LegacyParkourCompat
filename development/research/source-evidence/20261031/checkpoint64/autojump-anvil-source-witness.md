# Auto-jump consumer witness for anvil collision geometry (1.12.2 → 1.13.2)

## Frozen roster and exact sources

The independent roster was frozen before reading any finish58 author report. `independent-roster.md` SHA-256: `D0F227C59252B78C9A26884F7FE639ED442949C683F604EDD3041C075E747671`; `independent-source-roster.tsv` SHA-256: `B4C5E1073B5BC5E2B6825BAB81FD51B493431BF67818C70557BE30C7923CE5BF`. The roster records the exact four source manifests, every block-package declaration of `getShape`, `getOutlineShape`, `getCollisionShape`, or `addCollisions`, and local-player/world/query/mapping sources. All selected source-file hashes match their exact Feather `.sources.sha256` entries.

Primary hash identities relevant to this witness:

| Release | Primary source | SHA-256 |
|---|---|---|
| 1.12.2 | `client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| 1.13 | `client/entity/living/player/LocalClientPlayerEntity.java` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` |
| 1.13.1 / 1.13.2 | same path | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.12.2 | `util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| 1.13 / 1.13.1 / 1.13.2 | `util/math/Box.java` | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` |
| 1.12.2 | `block/AnvilBlock.java` | `80729f34a74ff8b1b4b2d59e55c5d8dd9d61fff94c47f862c61c12d9947eb8bb` |
| 1.13 / 1.13.1 / 1.13.2 | `block/AnvilBlock.java` | `156505373606a0cf2c0df329e170465c207b72b2ab59798272cbec389373092e` |

Auto-jump method-body SHA-256 values: 1.12.2 `5b124378adc6f3e0116153987d752d813a48d5c4916d07731993d10e537b4aa3`; 1.13 `df3fc053248f2496974a08a7e0724ca248a982f368a5125fcbe8f1c5a6fb4186`; 1.13.1 and 1.13.2 `7aa01a65bc8433517c0265fbf58b766defbc9615884a8f5f86d57f09c516dfb3`. `Box.intersects(Vec3d,Vec3d)` has the same method-body SHA-256 `3c8dc0d0b474f905717fcc9b5ab794d66866c6e30b3e5440ca5cc5491b43b68b` in all four releases. Full file and source-manifest hashes are in the frozen TSV.

## Caller and provider gates

`LocalClientPlayerEntity.move` calls `super.move`, then calls `autoJump` with the actual horizontal position delta (1.12.2 `LocalClientPlayerEntity.java:850`; 1.13 `:871`; 1.13.1/.2 `:875`). The caller is the local-player implementation. The witness sets auto-jump enabled, cooldown `0`, grounded, not sneaking, not riding, forward input `(sideways=0, forward=1)`, yaw `-80°`, no Jump Boost, and ordinary standing dimensions (`0.6 × 1.8`). It uses a small unobstructed move `dx=0.01`, `dz≈0.00176`; the player remains supported by stone and the Anvil is outside the immediate player AABB.

The paired old state is Anvil metadata/damage `0`, facing north. Exact 1.13+ mappings retain it: block remap `minecraft:anvil.0 → minecraft:anvil` and state fixer old facing north → modern `anvil` with `damage:'0',facing:'north'` (`C_37931705.java:63`; `C_80754635.java:2322` in 1.13, 1.13.1 and 1.13.2). The provider does not read damage. Modern `Block.block` divides its shape coordinates by 16 (`Block.java:136`), and the three modern Anvil provider files are byte-identical.

Old Anvil `getShape` chooses `SHAPE_Z` for north-facing (axis Z): world-local x `[0.125, 0.875]`, y `[0,1]`, z `[0,1]` (`1.12.2 AnvilBlock.java:27-28,90-95`). Modern north-facing chooses `SHAPE_X`; its components are base x `[0.125,0.875]`, y `[0,0.25]`, z `[0.125,0.875]`; two legs at y `[0.25,0.3125]` and `[0.3125,0.625]`; and top x `[0.1875,0.8125]`, y `[0.625,1]`, z `[0,1]` (`1.13.2 AnvilBlock.java:28-35,61-64`). Intermediate provider hashes match 1.13.2.

## Same-input collision-query witness

Place the old Anvil at `(0,65,0)` on stone at `(0,64,0)`. Place the supported standing player so that after the small clear-space move its center is `(-0.48,65,-0.35)`; support it on stone at `(-1,64,-1)`. All other cells within the query are air; keep the relevant chunks loaded and the world border remote. These are ordinary paired states and do not require entity or non-vanilla providers.

With player speed `0.1`, the displacement squared is below the auto-jump `0.001` fallback threshold, so the method uses the equal forward input/yaw to derive its forward direction. It chooses `p=max(f×7,1/l)=0.7` (`LocalClientPlayerEntity.java:865-896` old; `:886-917` at 1.13; `:890-921` in .1/.2). The two lateral probe segments then include a segment whose componentwise bounds are approximately x `[-0.532,0.167]`, z `[-0.055,0.069]`, y exactly `65.51` (old `:901-909`; 1.13 `:922-930`; .1/.2 `:926-934`). The query envelope is expanded by the player width and includes the Anvil block; neither immediate movement nor the support blocks intersect the Anvil.

In 1.12.2, the Anvil's single collision box overlaps that probe: x intervals overlap `[0.125,0.167]`, z intervals overlap `[0,0.069]`, and the probe height `65.51` lies strictly inside `[65,66]`. Old `World.getCollisions(entity,box)` adds the box when it intersects the query envelope (`World.java:1017-1038`; `Block.addCollisions`, `Block.java:372-388`). The first/only hit has center `(0.5,65.5,0.5)`, selecting block `(0,65,0)`. The block above at `(0,66,0)` is air, so `s=66`; `u=s-playerMinY=1`, which is above `0.5` and no greater than `1.2`, setting `ticksToNextAutojump=1` (`LocalClientPlayerEntity.java:917-948`).

For north-facing modern Anvil, no component overlaps that probe: the base is below y `65.25`; the only component crossing y `65.51` is the second leg, whose x starts at `0.375` and z starts at `0.25`, both beyond the probe maxima; the top starts at y `65.625` and x `0.1875`. Thus 1.13's combined VoxelShape decomposition and 1.13.1/.2's flattened per-provider iterator both produce no intersecting Anvil box. `s` remains `Float.MIN_VALUE`, so no cooldown write occurs. The 1.13 combined-union versus .1/.2 stream ordering distinction is immaterial for this witness; all modern provider components miss the tested probe.

The Anvil is held fixed by its own stone support. The player's separate floor support only touches the auto-jump search envelope at its lower y boundary; the probe line is at y `65.51`. The current-player support/headroom cells (`x,z` flooring to `-1`, y around `66–67`) are air on both sides. No neighbor-derived Anvil property, entity collision, fluid, custom tag, or modern-only block is involved.

## Disposition and limits

This is a concrete conditional source-level local-player auto-jump response candidate. It binds the state mapping, provider geometry, caller gates, collision query, first box choice and cooldown write. It is not a runtime trajectory claim or independent acceptance of an Anvil finding.

A prior equal-Y no-op hypothesis was incorrect: `Box.intersects(Vec3d,Vec3d)` uses strict overlap between the receiver box and the componentwise argument bounds, so a zero-height probe intersects whenever its y lies strictly within the receiver's y interval. The original Anvil witness survives with the coordinates above.

This report closes only this AutoJump/Anvil witness. The separate AutoJump `getCollisions` call may synchronize entity world-border state; it does not change this shape-intersection calculation, and the world-border side effect is not closed here. The remaining provider census, all other local tick/writer/direct-response terminals, and independent pair coverage verdict remain outside this report. No tests, build, game launch, decompilation, network, online mappings, product source/history, backlog/wiki, or Git writes were used.

## Manifest verification update

After writing the claim, I recomputed each selected AutoJump, Box, AnvilBlock, Block, World/WorldView, C_37931705 and C_80754635 source-file SHA-256 and matched it to that release's `ornithe-feather.sources.sha256` row. The exact file digests are in `independent-source-roster.tsv`; all matching selected rows passed (no mismatches).

The `Block.block` coordinate conversion was reopened at 1.13, 1.13.1, and 1.13.2 and is identical at each: divide each argument by `16.0` before creating the VoxelShape (`Block.java:152-154` in 1.13; `:136-138` in .1/.2). This confirms the modern shape bounds used above at the exact intermediate boundary.
