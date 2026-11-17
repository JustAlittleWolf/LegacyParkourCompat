# 1.20.2 to 1.20.4 piston collision shape providers

Status: **NO_DIFF for this bounded source slice.** This compares Mojmap 1.20.2 and Mojmap 1.20.4 only. It does not close the pair or establish reachability, trajectories, or runtime parity.

## Source identity

Both canonical source roots are read-only `decompiled_minecraft/<version>/mojmap`. Their root sidecars identify the exact release and namespace:

| Release | `mojmap.ready.json` SHA-256 | `mojmap.provenance.json` SHA-256 | ready source-manifest SHA-256 | artifact-manifest SHA-256 | pinned client SHA-256 |
|---|---|---|---|---|---|
| 1.20.2 / mojmap | `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` | `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54` | `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda` |
| 1.20.4 / mojmap | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` | `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` |

The ready markers declare `status=ready`, matching `versionId` and `mapping=mojmap`; provenance declares the same version/namespace and a Vineflower run. Each cited Java file's SHA-256 was recomputed and matched its corresponding row in that release's `mojmap.sources.sha256`. The identical hash for `PistonMovingBlockEntity.java` across endpoints means its full source bytes are identical.

## Compared shape providers and evidence

Paths below are relative to each `decompiled_minecraft/<version>/mojmap` root. For each paired path, the listed 1.20.2 and 1.20.4 digest is the SHA-256 of that endpoint's source file and was checked against its source manifest.

| Source path | 1.20.2 SHA-256 | 1.20.4 SHA-256 | Checked surface |
|---|---|---|---|
| `net/minecraft/world/level/block/piston/PistonBaseBlock.java` | `adb5c06fa4e8337bd099f21a7ff40de46c8d784b06931d840eacbd4036e050b3` | `90f35c735e5ad359c2ad2f12bfa85e169b6b8605ea911b3e2777f9088fe6d410` | state defaults, six base-shape constants, `getShape` |
| `net/minecraft/world/level/block/piston/PistonHeadBlock.java` | `85a667cf8fb199115ef844325ad14cf80d615649984c790efc60008cc9e49e54` | `e5c67e3fd5bf39ebee90e9a52aa2f311517071deae585e2ea48449813981778b` | shape constants, `makeShapes`, `calculateShape`, defaults, `getShape` |
| `net/minecraft/world/level/block/piston/MovingPistonBlock.java` | `82ce74dec42c51e6fa8652de8a30f965fa4a11c16ae5cf869ecc4dfa62f91f21` | `9a74576454e3a77d4bba8227df7c5fec27937e6eb25c0e68370ca199b6397b22` | defaults, `getShape`, `getCollisionShape`, typed block-entity lookup |
| `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` | `ecbe61885f9d5ecb3229427fd4082e8324b50894da83d65e2d2a4b5f0db2d4d0` | `ecbe61885f9d5ecb3229427fd4082e8324b50894da83d65e2d2a4b5f0db2d4d0` | `getExtendedProgress`, `getCollisionShape` |
| `net/minecraft/world/level/block/Blocks.java` | `75e8e77adab4a07b02ef95619be83a004f5f79810cf3f07157a713d714881253` | `5392c4349317e55c2989865be38c17b94e6e0543e7017e6a2a64d79217e83643` | PISTON/STICKY_PISTON, PISTON_HEAD, MOVING_PISTON registrations and `pistonBase` properties |
| `net/minecraft/world/level/block/Block.java` | `0c2df3d958078929c4cc2985e0d6a6a766cbbbb02066e6c9ac471981aa858656` | `9a777d44c9c6b55677132fb5a56d46c9aa322e42faf956749ef80443817416c4` | `Block.box` coordinate scaling |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `a6d88726ce4642fc0a61f46a01347bb0b74887b61ef55af7e7c76723789a0b57` | `6284a7d08a6d0d475cd4fe8aea62b35153ae8fbc0bce5f51788389f679233b54` | default `getShape` and `getCollisionShape`, `hasCollision` default |

The compared source anchors are `PistonBaseBlock.getShape` (1.20.2 lines 61–81; 1.20.4 lines 72–92), `PistonHeadBlock.makeShapes/calculateShape/getShape` (1.20.2 lines 57–92; 1.20.4 lines 64–99), `MovingPistonBlock.getShape/getCollisionShape/getBlockEntity` (1.20.2 lines 97–110; 1.20.4 lines 105–118), and `PistonMovingBlockEntity.getExtendedProgress/getCollisionShape` (both files lines 94–96 and 333–361). `BlockBehaviour`'s default collision dispatch is at 1.20.2 lines 285–292 and 1.20.4 lines 329–336; `Block.box` scaling is 1.20.2 lines 139–141 and 1.20.4 lines 146–148.

### Static piston base

`PistonBaseBlock` defaults to `FACING=NORTH`, `EXTENDED=false`. For unextended state, `getShape` returns the full unit-block shape. For extended state, it chooses by `FACING`: EAST box `(0,0,0)-(12,16,16)`, WEST `(4,0,0)-(16,16,16)`, SOUTH `(0,0,0)-(16,16,12)`, NORTH `(0,0,4)-(16,16,16)`, UP `(0,0,0)-(16,12,16)`, DOWN `(0,4,0)-(16,16,16)`. `Block.box` divides each coordinate by 16 before passing it to `Shapes.box`. `getShape` ignores world, position and collision context. Neither class overrides `getCollisionShape`, so inherited `BlockBehaviour.getCollisionShape` returns this state shape when `hasCollision` is true. Its property default is true; the piston registration does not call `noCollission()`.

### Piston head

`PistonHeadBlock` defaults to NORTH, DEFAULT type, `SHORT=false`. The shape array is built in `Direction.values()` order. Each direction's shape is `Shapes.or(direction-facing base plate, short-or-long arm)`, selected by `SHORT` and then `FACING.ordinal()`; `TYPE` and collision context do not select geometry. Base plates are four-sixteenths thick: SOUTH z=12..16, NORTH z=0..4, UP y=12..16, DOWN y=0..4, WEST x=0..4, EAST x=12..16. Long arm boxes are: UP `(6,-4,6)-(10,12,10)`, DOWN `(6,4,6)-(10,20,10)`, SOUTH `(6,6,-4)-(10,10,12)`, NORTH `(6,6,4)-(10,10,20)`, EAST `(-4,6,6)-(12,10,10)`, WEST `(4,6,6)-(20,10,10)`. The corresponding short arms are clipped to the block boundary: UP y=0..12, DOWN y=4..16, SOUTH z=0..12, NORTH z=4..16, EAST x=0..12, WEST x=4..16, with cross-section 6..10. The head registration does not disable collision; inherited collision dispatch uses the selected `getShape` result.

### Moving piston provider

`MovingPistonBlock` defaults to NORTH/DEFAULT, has empty ordinary `getShape`, and overrides `getCollisionShape`: it looks up the block entity at the queried position, accepts only `PistonMovingBlockEntity`, delegates to that entity's `getCollisionShape(world,pos)`, and returns empty if no matching entity exists. This direct override does not consult `hasCollision` or the supplied collision context.

The delegated method is byte-identical across the two endpoint sources. In order it:

1. Sets an initial `base` shape to empty, except for a retracting source piston whose moved block is a `PistonBaseBlock`, where it sets the moved piston state's `EXTENDED=true` and gets that state's collision shape.
2. Reads the thread-local `NOCLIP` direction. If `progress < 1.0` and it equals `getMovementDirection()`, returns only `base` immediately.
3. Uses a synthetic `PISTON_HEAD` state for source pistons, setting `FACING=direction` and `SHORT` to the exact predicate `extending != 1.0F - progress < 0.25F`; otherwise it uses `movedState`. The synthetic head keeps its default `TYPE`, which does not affect the head shape.
4. Computes `extendedProgress = extending ? progress - 1.0F : 1.0F - progress`, multiplies each integer direction step component by that float to form x/y/z doubles, translates the selected state's collision shape by that vector, and returns `Shapes.or(base, translatedShape)`.

The method uses the BE's stored `progress`, not a partial-tick parameter. This report records the provider's formula only; it makes no claim about piston actuation, displacement, or reachable world states.

## Registrations and bounded comparison

Both releases register `PISTON` and `STICKY_PISTON` through `pistonBase(false/true)`, with the same strength, shape-collision defaults and predicate properties. Both register `PISTON_HEAD` with the same stone color, strength, no-loot-table and block push reaction properties. Both register `MOVING_PISTON` with `dynamicShape`, strength -1, no-occlusion and the same predicate/push-reaction properties; neither registration calls `noCollission()`. Registry declaration locations shifted, but these relevant constructor/property chains are unchanged. `BlockBehaviour`'s default `getCollisionShape` remains `hasCollision ? state.getShape(world,pos) : empty`, and `hasCollision` defaults true. `Block.box` retains identical sixteenth-to-unit scaling.

**Verdict:** No change was found in the bounded piston base, head, moving-piston provider, or directly used piston geometry path. The differing source-file hashes for `PistonBaseBlock`, `PistonHeadBlock`, `MovingPistonBlock`, `Blocks`, `Block`, and `BlockBehaviour` reflect other source content in those classes; the inspected shape-provider methods, constants, registration properties and inherited collision dispatch are unchanged. `PistonMovingBlockEntity.java` is byte-identical. The slice is limited to those provider paths and supporting registrations/dispatch; it does not establish full collision-provider inventory, pair coverage, ordinary-world reachability, historical trajectory effects, or runtime parity.
