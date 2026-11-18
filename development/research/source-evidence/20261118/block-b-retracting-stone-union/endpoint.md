# Ordinary moving-player collision during sticky Stone retraction

## Scope and result

This is a bounded source-only resolution for the ordinary non-source Stone retraction branch in the `1.8.9--1.9` exact release comparison. The branch is vanilla-reachable: a sticky piston retracting with Stone two cells ahead creates a moving Stone block entity with `source=false` and `extending=false`. A non-sticky piston does not pull Stone. At the first moving phase (`progress=0.5F`, `lastProgress=0.0F`), a local player's ordinary swept collision path can observe and clip against the 1.9 provider shape independently of piston direct push. The witness below starts beside Stone in the real pre-retraction layout, rather than placing the player inside a detached hypothetical shape.

The source evidence proves this path conditional on the block entity and player being at the stated progress/position when `LivingEntity` calls `Entity.move`. I did not infer exact same-tick client scheduling for a redstone event, block-entity tick, and local-player tick; no runtime was launched.

## Exact source pins

Primary comparison: Minecraft `1.8.9` versus exact stable `1.9`, both Ornithe Feather. The 1.9 canonical source is `decompiled_minecraft/1.9/ornithe-feather/`, whose `.decompile-provenance.json` records `versionId=1.9`, Feather mapping metadata URL `https://meta.ornithemc.net/v3/versions/feather/1.9` (SHA-256 `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371`), and mapping artifact `feather-gen2-1.9+build.2-mergedv2.jar` (SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`). The exact client input is `1.9` with SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`. Provenance file SHA-256: `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.

The provenance `sources` map was checked against current bytes for every source used below. Pinned 1.9 file SHA-256 values:

| Source path under `1.9/ornithe-feather/` | SHA-256 |
| --- | --- |
| `net/minecraft/block/MovingBlock.java` | `a5b2584bc575dc888f1d32d9d4b683b91fc8b145ee079e66ff4722718fc8906f` |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `7ad3426bb74c9e69b4f521d20e045b1dc1eafa9b725daf0e2c0b9b718bc2683c` |
| `net/minecraft/block/PistonBaseBlock.java` | `d2b396408dc26daf9fba18ccac8cc1bfa4bc197042c2629e8df062a66d61f639` |
| `net/minecraft/block/piston/PistonMoveStructureResolver.java` | `8794b5bbd07dfb048a968031d780e71b1eabad788b2966bf44eb7c726aa04568` |
| `net/minecraft/block/Block.java` | `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2` |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/entity/living/LivingEntity.java` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` |
| `net/minecraft/util/math/Box.java` | `cb0f30c72b7925fa0c0b133dffd051cf8260f4321f7c4a093c64ef5a5f500e62` |
| `net/minecraft/world/World.java` | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |
| `net/minecraft/world/chunk/WorldChunk.java` | `b98bccf16277a5a62b09ac2e77db7d77e098a62d8029c03a44fbed62e01ff267` |

Supporting endpoint evidence for the already documented `1.8.9--1.9.4` comparison remains separate; it does not substitute for exact 1.9. The canonical 1.8.9 and 1.9.4 sidecars record Feather build.2 merged archives and Tiny mapping files. Their source-manifest SHA-256 values are respectively `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; selected source bytes were checked against their manifests. The 1.8.9/1.9.4 exact member lines and hashes are the bounded supporting evidence reported with this handoff.

## Vanilla event creates the branch

Fixture before retraction, in block coordinates:

```text
y=0, z=0:  x=0 sticky piston (facing EAST, extended) | x=1 piston head | x=2 Stone | x=3 air
y=-1:      solid floor under the player approach
```

When power is removed, `PistonBaseBlock.checkExtended` queues retraction event type 1 with the facing ID (`1.9 PistonBaseBlock.java:112-121`). In `doEvent`, both ordinary and sticky pistons replace the piston cell with a moving block entity for the piston base, created with `extending=false, source=true` (`167-182`). Only the sticky branch examines the block at `pos + 2*facing`; an eligible Stone calls `move(world, pos, direction, false)` (`182-203`). The ordinary-piston branch instead removes the head (`204-206`), so ordinary retraction does not produce a moving Stone.

For `extend=false`, `PistonMoveStructureResolver` sets its start to `pistonPos.offset(facing, 2)` and its move direction to `facing.opposite` (`PistonMoveStructureResolver.java:22-31`); `resolve()` begins with that start block (`34-47`). Thus this fixture resolves Stone at `(2,0,0)` to `(1,0,0)`. `PistonBaseBlock.move` sets the destination to the source block position plus the move direction and creates `MovingBlockEntity(movedState, facing, extend, false)` (`PistonBaseBlock.java:276-315`). The pulled Stone is therefore a non-source retracting moved block at `(1,0,0)`, with facing still EAST.

## First-phase collision and independent ordinary clip

The 1.9 `MovingBlock.getCollisionShape` delegates to the moving entity's `getShape` (`MovingBlock.java:130-134`). `MovingBlockEntity.getShape` unions the shapes at current and previous progress (`79-81`); each uses `getAmountExtended`, which for retraction is `1.0F-progress`, and translates the moved outline by that amount times facing (`75-88`). With Stone at cell `(1,0,0)`, current `.5F` and last `0.0F` produce world X intervals `[1.5,2.5]` and `[2,3]`; their union is `[1.5,3]`, spanning Y `[0,1]` and Z `[0,1]`.

Use a normal player of width `0.6F`, height `1.8F` (Entity constructor defaults, `Entity.java:161-180`; PlayerEntity also restores these dimensions in `PlayerEntity.java:385-388`). Put its center at `(3.31,0,0.5)`, so its box is X `[3.01,3.61]`, Y `[0,1.8]`, Z `[0.2,0.8]`. Before retraction it is separated from the real Stone at `[2,3]` by `0.01` and does not overlap the piston head or pulled block. This position is reachable in the initial layout. At the first-phase shape, it also lies outside the Stone's current union box, so the Stone `moveEntities` query does not select it.

For ordinary movement, a westward call with `dx=-0.02`, `dy=dz=0` expands the player's collision query to X minimum `2.99`. The local player inherits `LivingEntity` movement (`LocalClientPlayerEntity` extends `ClientPlayerEntity`, which extends `PlayerEntity`; `LivingEntity.java:1407` calls `this.move(velocityX, velocityY, velocityZ)`). `Entity.move` requests collisions for the swept box and clips X against each returned box (`Entity.java:506-522`). `World.getCollisions` calls each state’s `addCollisions` (`World.java:893-928`); `Block.addCollision` moves the provider shape to world coordinates and appends it when it intersects the swept query (`Block.java:313-323`). This appends the moving Stone box `[1.5,3]`. `Box.intersectX` handles a negative delta when the player's minX is at or beyond the obstacle's maxX and limits the delta to `obstacle.maxX-player.minX` (`Box.java:139-153`), clipping `-0.02` to `-0.01` at X=3.

Direct push is excluded for this fixture by the exact strict overlap gates. The Stone block entity's `moveEntities` queries `getEntities(null, shape)` from its current/last union and then moves only returned entities (`MovingBlockEntity.java:90-96,112-145`; tick reaches this at the first progress phase, `163-181`). `WorldChunk.getEntities` requires `entity.getShape().intersects(bounds)` (`WorldChunk.java:663-675`), and `Box.intersects` uses strict `min < max && max > min` comparisons (`Box.java:197-203`); player minX `3.01` is beyond Stone maxX `3`. The separate source piston block entity is at x=0, has facing EAST and, because the retraction event data has no extended bit, `getStateFromMetadata` gives the default unextended piston state (`PistonBaseBlock.java:346-348`; event setup `173-182`). That state’s collision shape is a full block at local x `[0,1]` (`PistonBaseBlock.java:47-66`); its current/previous translated union ends at world x=2. It cannot directly select this player either.

At the same held player position/input, the 1.8.9 provider uses `getProgress(0.0F)` and reverses only extension (`MovingBlock.java:118-130`). The retracting state consequently passes progress 0 to its axis-adjusted single-shape helper (`162-193`), yielding Stone box x `[1,2]` at cell `(1,0,0)`. The swept player minimum x=2.99 remains beyond 2, so no collision box is appended and the `-0.02` input is not clipped by the moving Stone. Thus the 1.9 clip is an independently eligible ordinary movement response; the witness does not depend on direct piston push or a player initially embedded inside the legacy provider shape.

## Supporting `1.8.9--1.9.4` endpoint references

The existing finding `development/research/findings/1.8.9--1.9.4/moving-piston-collision-shape.md` establishes the broad provider delta: 1.8.9 `MovingBlock.getCollisionShape` samples progress at tick delta zero and returns one adjusted moved-state collision shape (`118-130,162-197`); 1.9.4 delegates to `MovingBlockEntity.getShape`, which unions current and previous moved outline shapes (`MovingBlock.java:134-137`; `MovingBlockEntity.java:75-88`). For ordinary retracting moved blocks, 1.8.9 `MovingBlockEntity.tick` reaches `moveEntities` during partial progress only under `if (extending)` (`127-147`), so its first retraction phase has no direct push. Both endpoint event bodies have the same sticky-only pull branch pattern (1.8.9 PistonBaseBlock `127-165`; 1.9.4 `168-207`) and create the retracted pulled block with `extend=false, source=false` in their move loops (1.8.9 `287-320`; 1.9.4 `278-316`).

Verified selected endpoint source hashes:

| Release/member | SHA-256 |
| --- | --- |
| 1.8.9 `MovingBlock.java` | `700010b071738738f04e0508050590d8d625f01115fc4886fef6e262f85c6e85` |
| 1.8.9 `MovingBlockEntity.java` | `05a5bfbf2556eaa621996bda365b7bab01f5b9060cf773e7b9949ebe2f39fff6` |
| 1.8.9 `PistonBaseBlock.java` | `3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929` |
| 1.9.4 `MovingBlock.java` | `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6` |
| 1.9.4 `MovingBlockEntity.java` | `50eb847d3ff4ff78441057f397c43e15fb894177f7c5a736a61c22a80f1de467` |
| 1.9.4 `PistonBaseBlock.java` | `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36` |

These endpoints corroborate provider and piston-event mechanisms, but exact 1.9 source above is the immediate newer-release evidence used for the retraction gate and player witness. No intermediate patch boundary beyond either named exact pair is asserted.
