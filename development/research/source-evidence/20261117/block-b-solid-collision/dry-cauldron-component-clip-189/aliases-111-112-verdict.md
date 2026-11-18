# Independent source-only verdict: 1.11–1.12 Cauldron clip alias

## Verdict

**Confirm the bounded source-side alias claim across exact releases 1.11, 1.11.1, 1.11.2, 1.12, 1.12.1, and 1.12.2**, anchored to the approved fixture report at SHA-256 `7f3a4a81caca8f200a65396237e3a2cb4aeac6d3649413065f71080c3d67694d`. I independently traced the Cauldron hook, the ordinary `World.getCollisions` dispatch, the strict box tests, the player clip and step route, and the command/setup gates. The old side retains the fixture's +Z clip and canopy-limited step fallback.

One precision for the accepted report: Cauldron's hook offers the five components in the listed order, but the fixture's swept AABB does **not** append all five to the returned collision list. Under the stated position and +0.2Z query, strict intersection appends EAST then SOUTH only; BASE, WEST, and NORTH are filtered out. This does not change the clip verdict, but “five components in the normal list” should mean the normal collector dispatches the five-component hook, not that all five boxes survive its intersection filter.

## Evidence identity

Canonical read-only trees: `decompiled_minecraft/<version>/ornithe-feather/`, Feather Gen2 build.2 for each exact release. Current operation-file SHA-256 values matched the available source maps for all reviewed files.

| Release | Root provenance SHA-256 | Source-map identity |
|---|---|---|
| 1.11 | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` | `ornithe-feather.sources.sha256` `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |
| 1.11.1 | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` | `ornithe-feather.sources.sha256` `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` |
| 1.11.2 | `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6` | `ornithe-feather.sources.sha256` `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |
| 1.12 | `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` | hidden mapping-root `.decompile-provenance.json` contains exact client, Feather build.2 inputs, and per-source hashes |
| 1.12.1 | `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` | hidden mapping-root `.decompile-provenance.json` contains exact client, Feather build.2 inputs, and per-source hashes |
| 1.12.2 | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` | `ornithe-feather.sources.sha256` `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |

The two hidden 1.12 mapping-root manifests are respectively SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` and `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`; their pinned artifacts identify Feather Gen2 `1.12+build.2` and `1.12.1+build.2`. The visible root provenance for other releases identifies exact version IDs and Feather mapping inputs.

## Independently traced behavior

- `net/minecraft/block/CauldronBlock.java:35–52` in all six releases defines BASE `[0,0,0]–[1,0.3125,1]`, then WEST `[0,0,0]–[0.125,1,1]`, NORTH `[0,0,0]–[1,1,0.125]`, EAST `[0.875,0,0]–[1,1,1]`, SOUTH `[0,0,0.875]–[1,1,1]`. `addCollisions` offers them in that order and does not read the cauldron level. Its `:55–57` `getShape` returns `FULL_BLOCK_SHAPE`, but that outline/shape method is not the ordinary collision dispatch.
- In 1.11, `World.java:994` dispatches through `BlockState.addCollisions`, which delegates to the block hook; 1.11.1 onward adds the `forceShape` argument and the ordinary collector passes `false` (`World.java:997` in 1.11.1/1.11.2, `:1001` in 1.12.x). Cauldron ignores that flag and still offers the same five components. `Block.addCollision` translates each component and appends it only when the query AABB strictly intersects it (`Block.java:357–362` in 1.11, `:376–381` in 1.12).
- `Box.intersects` uses strict inequalities on all three axes (`Box.java:245–251`, identical SHA-256 `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` for the six source trees). `Box.intersectZ` first requires strict X and Y overlap, then for positive motion clamps when player `maxZ <= obstacle.minZ` (`:225–239`).
- `Entity.setPosition` uses `float f = width / 2.0F`; the entity width is `0.6F` and height `1.8F`. With center X `1.299999961920929`, center Z `0.500000011920929`, and feet Y `0.5`, the body has xMin `0.99999995`, zMax `0.8000000238418579`, and top Y `2.299999952316284`. For a +0.2Z move, the swept query reaches zMax `1.000000023841858`. Its X overlap with the cauldron east/south edges is strict-positive: `0.99999995 < 1`. SOUTH starts at z `0.875`, so `0.875 - 0.8000000238418579 = 0.0749999761581421`; the ordinary +Z clip is therefore +0.074999976 (approximately). EAST spans the player's existing Z and does not itself clamp +Z. BASE is below feet, WEST is outside X, and NORTH is behind the query, leaving only EAST then SOUTH appended by the cauldron hook for this query.
- `Entity.move` obtains the list from `World.getCollisions(this, this.getShape().expanded(x,y,z))`, then clips Y, X, Z in list order (1.11 around `:523–555`; 1.11.1/1.11.2 around `:564–596`; 1.12.x around `:567–599`). Living entities set `stepHeight = 0.6F` (1.11 `LivingEntity.java:172`, 1.12.x `:175`). The step trial is gated on positive step height, grounded/downward movement, and horizontal clipping. With the top slab at `(1,2,0)`, its TOP shape is `[0,0.5,0]–[1,1,1]` translated to minY `2.5`; it caps trial rise at `2.5 - 2.299999952316284 = 0.200000047683716`. That is insufficient to clear the Cauldron wall top at Y=1, so the step candidates retain the SOUTH Z clip. The step candidate horizontal-distance comparison `av > aw` picks its second route on equality (1.11.2 `Entity.java:662–674`; 1.12.2 `:665–677`). The later `aa * aa + ac * ac >= x * x + z * z` comparison restores the direct clipped route on a tie (1.11.2 `:683–690`; 1.12.2 `:686–692`). Net Y is zero for this canopy fixture.
- A bottom slab at `(1,0,0)` has top Y `0.5` (`SlabBlock.java:20–21,39–44`); strict half-open intersection excludes it from the initial query at feet Y `0.5`. In the step query it still does not extend below the query's minY. The top canopy slab does enter the upward-expanded step query.
- `SetBlockCommand.getRequiredPermissionLevel()` is 2, and its run path validates argument count and target chunk, parses a requested block state (or uses the block default state), then calls `World.setBlockState`; it performs no entity-overlap validation (`SetBlockCommand.java:29–57, 89–95` in 1.11; `:29–57, 89–91` in 1.12.x). Cauldron's default state is dry and its collision hook ignores the level. `World.getCollisions(this, …)` excludes the queried entity when adding nearby entity boxes; an empty fixture world therefore adds no entity collision. The source supports command-created blocks and a command teleport route, but this verdict does not assert that natural player input emits exactly +0.2Z or that the setup was runtime-tested.

## Scope

This confirms only the historical source-side conditions for the cited Cauldron component-clip fixture in these six named exact releases. It does not establish full-pair coverage, an intervening patch boundary, another profile's behavior, or runtime parity. No source generation, product edit, build, test, or game/runtime launch was performed.

