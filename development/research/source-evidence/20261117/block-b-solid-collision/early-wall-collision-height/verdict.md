# Independent source verdict: 1.11–1.11.1 wall collision height

**Verdict: ACCEPT the bounded claim.** For ordinary, loaded, lawfully placed walls, the block collision boxes collected by `World.getCollisions(entity, box)` reach Y=1.5 in 1.11 and only Y=1.0 in 1.11.1. The tall `getCollisionShape` value remains present in 1.11.1; the behavior change is the ordinary collision-list dispatch, not deletion of the tall array or a block-state rewrite.

## Exact source identity and hash checks

Both checked trees are the local canonical `decompiled_minecraft/<version>/ornithe-feather` outputs. Each has a provenance JSON and ready marker (not a modern `.decompile-provenance.json`). I checked the exact release IDs, `ornithe-feather` mapping namespace, requested Feather mapping option, resolved IDs, and successful preparation status. Each manifest names Feather Gen2 build.2 artifacts. The preparation provenance records `sourcePairReview: not performed`; this verdict is the independent source review, not an inherited acceptance.

- 1.11: client SHA-256 `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634`; Feather merged mapping SHA-256 `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44`; Tiny mapping SHA-256 `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0`.
- 1.11.1: client SHA-256 `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6`; Feather merged mapping SHA-256 `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928`; Tiny mapping SHA-256 `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`.

Ready-marker hashes checked against their corresponding manifests: 1.11 provenance `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40`, sources manifest `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`; 1.11.1 provenance `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055`, sources manifest `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`. The file SHA-256 values below match each tree's `ornithe-feather.sources.sha256` entries; those manifests in turn match the ready markers.

| Release | Source file | SHA-256 |
|---|---|---|
| 1.11 | `net/minecraft/block/WallBlock.java` | `c44989cfcdeb01f8842aaca6173596dff3e9a661e6ad75fbe43c54cf2e270cf9` |
| 1.11 | `net/minecraft/block/Block.java` | `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` |
| 1.11 | `net/minecraft/block/state/StateDefinition.java` | `a8d5fcb3d6a5582f8130600bbfb01a751a09b527d71a7652682487d80a2f717a` |
| 1.11 | `net/minecraft/world/World.java` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` |
| 1.11 | `net/minecraft/entity/Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |
| 1.11 | `net/minecraft/entity/living/player/PlayerEntity.java` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` |
| 1.11.1 | `net/minecraft/block/WallBlock.java` | `336d4fe9a541bb6a371b81236893d7e80f004c9a1fd4181aa3345acd47b47a41` |
| 1.11.1 | `net/minecraft/block/Block.java` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` |
| 1.11.1 | `net/minecraft/block/state/StateDefinition.java` | `95704c944742efb2ec9cba7eb73399a330195556e669451131ba542e6a9fac65` |
| 1.11.1 | `net/minecraft/world/World.java` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` |
| 1.11.1 | `net/minecraft/entity/Entity.java` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |

 

## Paired call trace

**1.11.** `World.getCollisions(Entity, Box)` scans candidate block positions and, for each selected state, calls `BlockState.addCollisions(world, pos, shape, list, entity)` (`World.java:960–1015`, call at 994). `StateDefinition.java:374–375` dispatches to the block's five-argument `addCollisions`. `WallBlock` does not override that method. The inherited `Block.addCollisions` (`Block.java:353–355`) obtains `state.getCollisionShape(world, pos)` and passes it to `addCollision`. `WallBlock.getCollisionShape` resolves virtual neighbor properties and returns `COLLISION_SHAPES[getShapeIndex(state)]` (`WallBlock.java:88–93`).

The 1.11 `COLLISION_SHAPES` initializer explicitly fills entries 0 through 15 with `SHAPES[i].withMaxY(1.5)` (`WallBlock.java:45–62`). Thus every north/east/south/west mask receives a tall collision shape, including the isolated pattern index 0. `getShape` separately returns the short `SHAPES` entry (`WallBlock.java:82–86`).

**1.11.1.** `World.getCollisions(Entity, Box)` calls a private overload with `enforceWorldBorder=false` and collects ordinary block results (`World.java:960–1015`, especially 997 and 1013–15). It dispatches `BlockState.addCollisions(..., entity, false)` (`StateDefinition.java:373–375`). `WallBlock` now overrides the seven-argument collision collector: when `forceShape` is false it resolves virtual neighbor properties, then unconditionally calls `addCollision(..., SHAPES[getShapeIndex(state)])` (`WallBlock.java:92–98`). It does not choose `COLLISION_SHAPES` on this route. The 1.11.1 `SHAPES` entries remain the same short shapes: indices 5 and 10 have maxY 0.875; the other 14 have maxY 1.0. The tall `COLLISION_SHAPES` array also still exists, and `getCollisionShape` still returns it (`WallBlock.java:30–64, 85–104`).

In both releases `addCollision` offsets the selected local shape by the block position and appends it only if the query box intersects (`Block.java:357–363`). The world query includes a one-block vertical scan margin (1.11 `World.java:962–967`; 1.11.1 `World.java:961–966`), so a query confined to the block's upper band still visits the wall's block position. With a loaded chunk, ordinary in-world coordinates, and no other colliding entities, no border or entity result is needed for the difference.

## State-pattern and player-geometry check

`getShapeIndex` sets bits from N, E, S, W and returns the resulting 0–15 integer (`WallBlock.java:95–113` in 1.11; `107–125` in 1.11.1); UP is not part of the index. `resolveVirtualProperties` derives those four booleans from the neighbor connections (`WallBlock.java:175–182` in 1.11; `187–194` in 1.11.1). For an isolated wall with air on all horizontal sides, each boolean is false, index is 0; whether the UP property is set does not change the index. Repeating the source-defined array lookup for each of the 16 masks confirms every 1.11 collision entry has maxY 1.5. In 1.11.1 the ordinary collector selects SHAPES with maxY values by index `[1, 1, 1, 1, 1, 0.875, 1, 1, 1, 1, 0.875, 1, 1, 1, 1, 1]`. Thus the ordinary route loses 0.5 height for 14 masks and 0.625 height for masks 5 and 10.

Concrete collision geometry: for a wall at block position `(0,0,0)`, the isolated state is index 0 with local bounds x/z `[0.25,0.75]`. A normally sized player (0.6 wide × 1.8 tall; `PlayerEntity.java:403`) centered at x=z=0.5 with feet at y=1.1 has box x/z `[0.2,0.8]`, y `[1.1,2.9]`. It strictly overlaps the 1.11 box `[0.25,0.75] × [0,1.5] × [0.25,0.75]`; it does not intersect the 1.11.1 ordinary box `[0.25,0.75] × [0,1.0] × [0.25,0.75]`. This is a witness inside the differing upper band (1.0 < y < 1.5), not a claim about the full trajectory. Entity movement obtains collision lists from `World.getCollisions(this, expandedBox)` before axis clipping (`Entity.java:523/569` in 1.11; `564/610` in 1.11.1), so such an upper-band overlap can change player clipping when the movement query crosses the wall there.

## Forced/direct routes and bounds

The reviewed claim is specifically the ordinary world collision-list route. In 1.11.1, `forceShape=false` explicitly takes the virtual-property resolution step and then returns a short SHAPES box. For `forceShape=true`, the WallBlock override skips that resolution but still selects SHAPES, so the flag does not restore height 1.5. The source tree's `forceShape=true` call sites are moving-block-entity routes (`MovingBlockEntity.java:120, 340`), not ordinary stationary-wall player queries. A direct `getCollisionShape` call still returns the tall array in both versions; other APIs that inspect that getter must be assessed as separate routes. The 1.11 `World.getCollisions(Box)` also has a separate no-entity/world-border overload; this verdict is about the entity overload used by player movement.

After independently completing this trace and 16-pattern check, I opened the supplied author scratch `source-111-1111-wall-chorus-portal-shapes/source-proof.md` (SHA-256 `466c4eaebfd6a518456fe7e578183cbd42bd4093d1c15b9b32dd8094b0402c0e`) solely to bind the review. Its wall matrix and ordinary-route verdict agree with this independent result; its Chorus/End Portal material is outside this review. No code, tests, decompilation or runtime launches were performed. This is an independent source verdict for this bounded operation; it does not claim pair-wide discovery completion or an exact intermediate introduction patch beyond the 1.11 to 1.11.1 interval.




