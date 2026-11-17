# Source proof: 1.11 → 1.11.1 wall, chorus plant, and end portal collision geometry

## Scope and verdicts

Exact pair: Minecraft 1.11 and 1.11.1; published Ornithe Feather mappings (build.2 per release). This proof covers ordinary block collision-box collection with `forceShape == false` in 1.11.1, the corresponding 1.11 collector, and the player movement caller route. It covers only `WallBlock`, `ChorusPlantBlock`, and `EndPortalBlock`. End-portal contact/transfer is excluded. No other block providers, collision gates, or prior conclusions are assessed.

- **WallBlock: difference.** In the ordinary path, the selected shape changes from the 1.11 `COLLISION_SHAPES[index]` (all maxY 1.5) to the 1.11.1 `SHAPES[index]` (maxY 1.0 for 14 patterns and 0.875 for patterns 5 and 10). X/Z bounds and state-to-index mapping are unchanged.
- **ChorusPlantBlock: no difference.** Neighbor-derived state and the ordered list of up to seven boxes are identical in the ordinary path.
- **EndPortalBlock: no difference.** Both versions have the same outline `SHAPE` `(0,0,0)–(1,0.75,1)` and append no collision boxes.

## Exact input identities and integrity checks

Both `.ready.json` markers say `status: ready`, identify the requested/resolved version, namespace `ornithe-feather`, Feather build.2 mappings, and the exact client SHA-256. In each version, the actual SHA-256 of the entire source manifest, provenance JSON, and artifacts manifest matches the digest in the ready marker. The actual SHA-256 of every reviewed source file below also matches its entry in the source manifest.

| Version | Client SHA-256 | Source-manifest SHA-256 | Provenance SHA-256 | Artifacts-manifest SHA-256 |
|---|---|---|---|---|
| 1.11 | `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` | `ae874615f961d67e402c978ff01cd3f26f61145e6b3c21ef318c1ab71f7794fd` |
| 1.11.1 | `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` | `ebbf181f60d7bc922df56826f528d1ea8a064783ac0f0f2b576e73db1fc2a958` |

The provenance records mapping input hashes:

| Version | Feather merged mapping JAR SHA-256 | Feather Tiny SHA-256 |
|---|---|---|
| 1.11 | `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44` | `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0` |
| 1.11.1 | `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928` | `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` |

The published source, provenance, ready-marker, and artifact-manifest files are present and their recorded digests match. The mapping artifacts named in provenance are in the historical campaign cache paths; those raw paths are absent in this checkout, so their bytes were not rehashed here. Their provenance SHA-256 values above and the full artifact-manifest digest are verified as recorded claims, not independently revalidated raw mapping bytes.

Reviewed source hashes (each checked against the corresponding source-manifest entry):

| Source | 1.11 | 1.11.1 |
|---|---|---|
| `net/minecraft/block/WallBlock.java` | `c44989cfcdeb01f8842aaca6173596dff3e9a661e6ad75fbe43c54cf2e270cf9` | `336d4fe9a541bb6a371b81236893d7e80f004c9a1fd4181aa3345acd47b47a41` |
| `net/minecraft/block/ChorusPlantBlock.java` | `606308371e35761b4bdff5abc8863389ba91c3fa277a628df204b72635371f13` | `ecd52ae5749b6112ccd58100f6e043713cf111592455b7988f5479a8fa87a8ba` |
| `net/minecraft/block/EndPortalBlock.java` | `62f0df85f00562e37559e816cb76329f359dbbf013981daf93b12c059358465c` | `3e246cd437d5ca61758cd03e3dd258a14f84de9121d043016274d1b6c03d2` |
| `net/minecraft/block/Block.java` | `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` |
| `net/minecraft/world/World.java` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` |
| `net/minecraft/util/math/Direction.java` | `116a61f67e93f895d29c71128b392fbde5f1ca85f1b51d9f7fce55a0dd5960bf` | `116a61f67e93f895d29c71128b392fbde5f1ca85f1b51d9f7fce55a0dd5960bf` |
| `net/minecraft/util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| `net/minecraft/entity/Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` | `8b37fd286b2dac1b343bd708b80284b43bb31c61bafc63bdcf6620ba3a59065c` |

## Ordinary collector and append rule

1.11 `World.getCollisions(Entity, Box)` visits the bounded block region and calls `BlockState.addCollisions(world, pos, shape, list, entity)` (1.11 `World.java` lines 960–995). The base 1.11 `Block.addCollisions` passes `state.getCollisionShape(world,pos)` to `addCollision` (`Block.java` lines 353–355). 1.11.1 `World.getCollisions(Entity, Box)` delegates to its shared collector; that collector passes `forceShape=false` at `World.java` line 997. Thus the 1.11.1 Wall and Chorus overrides resolve virtual neighbor properties in this caller path.

The shared 1.11 and 1.11.1 `Block.addCollision` rule (`Block.java` lines 357–364) is exact: if the candidate reference is not `EMPTY_BLOCK_SHAPE`, translate it by the block position, then append only if `shape.intersects(translatedBox)`. `Box.intersects(Box)` delegates to six strict inequalities in both releases (`Box.java` lines 245–251): min < other max and max > other min on X, Y, and Z. Merely touching a face/edge does not append. Callback order is the world's nested block iteration order; each provider's append order follows its own code. Wall submits one candidate; Chorus submits center then west, east, up, down, north, south conditionally.

## WallBlock finite state-to-box output

`getShapeIndex` starts at zero and sets a bit for each connected horizontal property: `NORTH` uses horizontal ID 2 / bit 4; `EAST` ID 3 / bit 8; `SOUTH` ID 0 / bit 1; `WEST` ID 1 / bit 2 (`Direction.java` enum constants, lines 17–22; `WallBlock.java` `getShapeIndex`). The `UP` and `VARIANT` properties do not enter the index. Both versions' `SHAPES[0..15]` arrays are identical. The 1.11 `COLLISION_SHAPES` explicitly sets every index maxY to 1.5. In 1.11.1's ordinary callback the candidate is `SHAPES[index]`, with the source maxY shown below.

All candidates have minY 0.0. Bounds in the table are local to the block; the callback translates by block position before applying strict intersection.

| Index | Connected sides | X min..max | Z min..max | 1.11 collision maxY | 1.11.1 ordinary maxY |
|---:|---|---|---|---:|---:|
| 0 | none | .25.. .75 | .25.. .75 | 1.5 | 1.0 |
| 1 | S | .25.. .75 | .25..1 | 1.5 | 1.0 |
| 2 | W | 0.. .75 | .25.. .75 | 1.5 | 1.0 |
| 3 | W,S | 0.. .75 | .25..1 | 1.5 | 1.0 |
| 4 | N | .25.. .75 | 0.. .75 | 1.5 | 1.0 |
| 5 | N,S | .3125.. .6875 | 0..1 | 1.5 | .875 |
| 6 | N,W | 0.. .75 | 0.. .75 | 1.5 | 1.0 |
| 7 | N,W,S | 0.. .75 | 0..1 | 1.5 | 1.0 |
| 8 | E | .25..1 | .25.. .75 | 1.5 | 1.0 |
| 9 | E,S | .25..1 | .25..1 | 1.5 | 1.0 |
| 10 | E,W | 0..1 | .3125.. .6875 | 1.5 | .875 |
| 11 | E,W,S | 0..1 | .25..1 | 1.5 | 1.0 |
| 12 | E,N | .25..1 | 0.. .75 | 1.5 | 1.0 |
| 13 | E,N,S | .25..1 | 0..1 | 1.5 | 1.0 |
| 14 | E,N,W | 0..1 | 0.. .75 | 1.5 | 1.0 |
| 15 | E,N,W,S | 0..1 | 0..1 | 1.5 | 1.0 |

Neighbor resolution is also unchanged: inspect north, east, south, west; connect when neighbor block is this wall or a `FenceGateBlock`, otherwise only when material is solid-blocking and state is cube, with barrier excluded and pumpkin material rejected. `UP = !bl5 || !world.isAir(pos.up())`, where `bl5` is true for exactly the two opposite-pair patterns N+S and E+W. `UP` does not change collision index or append behavior.

**Bounded player-movement consequence:** the inherited `Entity` movement method gathers collision boxes through `World.getCollisions(this, box)` (`Entity.java` 1.11 lines 481–523 and 1.11.1 lines 522–564). Players inherit this route. For a wall at any index, a queried player box with strict overlap against the world-space slice above the 1.11.1 candidate top but below Y=1.5 can receive a wall collision in 1.11 and none from that wall in 1.11.1. This is a shape-level source witness only; no trajectory or tick outcome is asserted. Separately, `PlayerEntity` calls the entity-less boolean `World.getCollisions(Box)` while resizing (both `PlayerEntity.java` line 315); 1.11.1's same private collector still calls providers with `forceShape=false`.

## ChorusPlantBlock and EndPortalBlock no-diff details

`ChorusPlantBlock.resolveVirtualProperties` in both versions reads the block below, above, north, east, south, and west. `DOWN` is true for chorus plant, chorus flower, or end stone; `UP` and four horizontal flags are true for chorus plant or chorus flower. The ordinary append method resolves this state and tests arms in the exact order below; coordinates are identical in both versions.

| Append order | Condition | Local box |
|---:|---|---|
| 1 | always | `(.1875,.1875,.1875)–(.8125,.8125,.8125)` |
| 2 | WEST | `(0,.1875,.1875)–(.1875,.8125,.8125)` |
| 3 | EAST | `(.8125,.1875,.1875)–(1,.8125,.8125)` |
| 4 | UP | `(.1875,.8125,.1875)–(.8125,1,.8125)` |
| 5 | DOWN | `(.1875,0,.1875)–(.8125,.1875,.8125)` |
| 6 | NORTH | `(.1875,.1875,0)–(.8125,.8125,.1875)` |
| 7 | SOUTH | `(.1875,.1875,.8125)–(.8125,.8125,1)` |

`EndPortalBlock` in each version defines `SHAPE = new Box(0,0,0,1,.75,1)`, returns it from `getShape`, and has an empty `addCollisions` callback. The nonempty shape is therefore not a player collision box through this collector. The separate contact callback is outside this review.

## Relevant exact source locations

Paths are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

- 1.11 `block/Block.java`: base `addCollisions` and strict append predicate, lines 353–370.
- 1.11 `block/WallBlock.java`: `SHAPES`/`COLLISION_SHAPES`, lines 24–62; getters, 83–94; bit index, 96–113; neighbor resolution, 176–184.
- 1.11.1 `block/WallBlock.java`: arrays, lines 30–68; ordinary callback/getter, 86–104; index, 106–123; neighbor resolution, 188–196.
- `block/ChorusPlantBlock.java` both releases: neighbor derivation and ordered boxes, lines 37–96.
- `block/EndPortalBlock.java` both releases: shape and empty collision callback, lines 22–45.
- 1.11 `world/World.java`: entity collision collector, lines 960–995; entity-less boolean collector, lines 1044–1070.
- 1.11.1 `world/World.java`: shared collector and `forceShape=false`, lines 960–1015; entity-less boolean overload, lines 1058–1059.
- 1.11 `entity/Entity.java`: movement collision calls, lines 481–523.
- 1.11.1 `entity/Entity.java`: movement collision calls, lines 522–564.
- Both `entity/living/player/PlayerEntity.java`: entity-less resize collision query, line 315.

The raw 1.11/1.11.1 `Block.java`, `World.java`, `Box.java`, `Direction.java`, `Entity.java`, and player source files were checked against their sidecar hashes; values are listed above for reviewed providers and callers. No implementation, tests, gameplay, server launch, decompilation, Git operation, wiki lookup, product source, or backlog was used. No path changes or verdicts beyond this bounded provider batch are implied.

