# Bed collision-shape provider: 1.13.2 to 1.14.4

## Scope and verdict

Bounded source-only comparison of `BedBlock`'s shape provider, the state fields and placement path that determine its geometry, and ordinary entity/player block-collision reachability. **Confirmed source difference; ordinary player movement reaches the provider through block collision gathering.** This report does not establish the remainder of pair coverage, pair freeze, runtime parity, or the exact release where the difference was introduced between the endpoints.

## Immutable source identities

Both sides are Feather Gen2 build.2 (`ornithe-feather`) decompilations. The ready markers' source-manifest hashes match the supplied identities:

- 1.13.2: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`
- 1.14.4: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`

Every consulted source file below was SHA-256 checked against its entry in that release's source manifest; all matched.

| Relative source path | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/BedBlock.java` | `a274ed3d3dd8e4f4895fe1f3d1444425a9248e9cd41bc6e1f2677af7dacbec18` | `aab431c7d85bc654b74215d2b2be5fbe6de81643f5e2be466658d918f272abe1` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` | `498a72cdd4bd035cd731d2c49fce401bef439d6f92f498501577d9cfc59ab487` |
| `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` | `fcdf8dc98f97781f4639c4b8cda8a238edf834f3963e401e6eeef6cb475c4909` |

## Provider and state geometry

- **1.13.2** — `BedBlock.java:38-42` defines `SHAPE = Block.block(0, 0, 0, 16, 9, 16)`. `getShape(state, world, pos)` at `:209-212` always returns it. `Block.getCollisionShape` at `Block.java:392-395` returns that state shape when `hasCollision` is true; `Block.Properties` defaults `hasCollision` to true (`Block.java:1898-1902`). `Block.block` divides all six coordinates by 16.0 (`Block.java:136-138`). The provider reads neither state nor world/position. Both bed halves therefore have the same local collision volume: full 16×16 footprint and y=0..9/16.
- **1.14.4** — `BedBlock.java:48-56` defines a full-footprint top from y=3..9/16 and four 3×3×3 corner-foot shapes, then unions the top with two feet for each direction. `Block.block` applies the same coordinate-to-sixteenth conversion (`Block.java:154-156`). `getShape(state, world, pos, context)` at `:179-193` computes `direction2 = FACING` for `PART=HEAD`, otherwise `FACING.getOpposite()`, then returns the matching directional union. `OCCUPIED`, `world`, `pos`, and `context` do not affect that provider's result. Collision is enabled by default (`Block.Properties`, `Block.java:808-812`); collision dispatch is at `Block.java:376-378`.
- The change is geometric, not just a signature change: the top's height and footprint match, while below y=3/16 the old full base is replaced by two oriented corner feet. In local coordinates the returned feet occupy one edge: north `x=0..3,z=0..3` plus `x=13..16,z=0..3`; south uses `z=13..16`; west uses `x=0..3`; east uses `x=13..16`.
- Bed state includes `FACING`, `PART`, and `OCCUPIED` in both versions (`BedBlock.java:276-278` / `:274-277`). Both default to `PART=FOOT` and `OCCUPIED=false` (`:44-48` / `:59-63`); placement carries the clicked-facing value (`:197-201` / `:172-176`); `onPlaced` creates the neighboring `PART=HEAD` at `pos.offset(FACING)` (`:285-293` in each). This verifies that the provider's orientation/half inputs occur on the ordinary two-block bed state path. The provider itself has no neighboring-block read or dependency; neighbor consistency/lifecycle beyond this placement write is outside this bounded check.

## Ordinary player collision reachability

- **1.13.2** — `Entity.move` asks `world.getCollisions(this, box, dx,dy,dz)` and clips Y, X, and Z movement against the resulting shapes (`Entity.java:578-598`). `WorldView.getCollisions` builds the swept region (`WorldView.java:154-178`); `getBlockCollisions` enumerates touched block positions and calls `blockState.getCollisionShape(this,pos)` before overlap testing and world-offsetting the shape (`WorldView.java:112-151`). `BlockState.java:205-210` dispatches that to the bed's block provider through `Block.getCollisionShape`. `World.getCollisions` adds entity collision shapes to the block stream (`World.java:1753-1758`).
- **1.14.4** — ordinary `Entity.move` reaches the collision clipper with `CollisionContext.of(this)` (`Entity.java:656-685`); its block-collision path reaches `WorldView.getBlockCollisions(entity, box)` (`Entity.java:692-705`), which creates an entity context and requests each candidate state's collision shape with it (`WorldView.java:133-174`). `BlockState.getCollisionShape(world,pos,context)` dispatch is at `BlockState.java:197-203`; the block collision default is at `Block.java:376-378`. That base implementation resolves its shape through `getSHape`, which supplies empty context (`BlockState.java:189-203`). This does not obscure the bed result: `BedBlock.getShape` ignores context. The resulting world-offset bed shape is overlap-filtered in `WorldView.java:172-176`, and `Entity` clips the requested movement against those collision shapes.

Thus ordinary player movement consumes these bed collision shapes through the actual block-collision route; reachability is established from `Entity.move` through the per-block collision query, not inferred from shape coordinates alone. The collision input differs below y=3/16 at all but the selected corner feet. This source-only check does not establish a concrete standard-pose player trajectory that responds differently; that depends on the current bounding box, requested movement, and surrounding support geometry.

## Limits and gates

Examined: bed shape construction/provider; relevant bed state fields, default, placement and second-half write; collision-shape dispatch; ordinary entity movement to per-block collision gathering and clipping. Not examined: the remainder of either player's tick graph, other block providers, callbacks, unrelated direct responses, independent audit, or every intermediate 1.13.x/1.14.x release. No product/wiki/history material, tests, build, or game launch was used. This bounded result is not a pair-coverage or pair-freeze verdict.


