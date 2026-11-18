# COMPLETED — 26.1.2→26.2 stationary solid fixtures

## Scope and verdict

Windows host, PowerShell. Read-only exact unobfuscated sources under the two assigned absolute version directories. Covered legal registry entries, default states, cached/static shape selection, inherited collision providers, collision context and the ordinary entity/player collision lookup for Bell, Grindstone, Stonecutter, Lectern, Anvil/Chipped Anvil/Damaged Anvil, and Enchanting Table. No eligible stationary dry-solid collision difference was found; no finding draft is warranted.

## Exact-source gates

- `26.1.2`: `unobfuscated.ready.json` reports status `ready`, exact version `26.1.2`, mapping `unobfuscated`; `unobfuscated.sources.sha256` SHA256 = `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` (matches assignment).
- `26.2`: `unobfuscated.ready.json` reports status `ready`, exact version `26.2`, mapping `unobfuscated`; `unobfuscated.sources.sha256` SHA256 = `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` (matches assignment).
- Every relevant source file below was SHA256-checked against its line in that release's source manifest. Bodies were intact and inspectable. No source generation or mutation performed.

| Source file | 26.1.2 SHA256 | 26.2 SHA256 |
|---|---|---|
| `world/level/block/BellBlock.java` | `376e9341d9660a2e979ba9352e759a183441f38b982d30c64399323d009deca5` | `89267235fe2f3d9d762b2e05be4b7f41ebb6fc3dfa42b23c11162c3550d78fc1` |
| `world/level/block/GrindstoneBlock.java` | `ffe4dd67c9b3c6ef2ed81b7baab0075aade78a92b9c7676cfa5d9ef79d763d73` | same |
| `world/level/block/StonecutterBlock.java` | `6fbb26aa6b535fe62ca25688c31f55635c3b1dd2c8038c7fdb7ed9b9bca61ab2` | same |
| `world/level/block/LecternBlock.java` | `a59665b6eba0ea0a87b20b05255a67d281102e312d99e63bfb041518b0a18949` | `7f81309060b80f2c7ca17e869c8b1afbe1dc8f7aa5c7d99aa6439f8aa0dd810f` |
| `world/level/block/AnvilBlock.java` | `a15b5ef5067f098a2e6b83d3b934730f376a3420a4ce47c798a6a80d800b9391` | same |
| `world/level/block/EnchantingTableBlock.java` | `97c1f127b812ef20d375f53dcbff9afc09528e3dcb76823ca24ef2437cd5f852` | `e4853a98ce96882cb42c7b078b74782ae673d71dba151ebdba10235695f3918f` |
| `world/level/block/Blocks.java` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |
| `world/level/block/state/BlockBehaviour.java` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |
| `world/level/BlockCollisions.java` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` | same |
| `world/level/CollisionGetter.java` | `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01` | `8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c` |
| `world/phys/shapes/CollisionContext.java` | `8702d23bea0f119e0d63ce15ff59088559a319283186372fd69adebeed4dcd69` | `0af02ec998df9e5b093a1b1887255619dd9b9a08604de40b452c11922fbf3bca` |
| `world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |

## Per-domain evidence

### Registry and defaults

`world/level/block/Blocks.java`: all six fixture families are real ordinary block registrations in both releases. The three Anvil registry entries (`ANVIL`, `CHIPPED_ANVIL`, `DAMAGED_ANVIL`) all instantiate `AnvilBlock`; fixture property chains remain collision-enabled defaults (`Properties.of()` starts `hasCollision = true`) and none calls `noCollision`. In 26.2 registry names move from string arguments to `BlockItemIds` constants while constructors and collision-relevant property chains remain the same. Anvil aliases retain the same class and properties.

Class defaults and shape inputs, checked in each release:

- `BellBlock.java:49-55,63-68,155-173`: default north/floor/unpowered. Same static bell, ceiling, floor, single-wall and double-wall shapes; collision and outline both select through `getVoxelShape` on attachment/facing. 26.2 changes the `isProperHit` switch form elsewhere, not shape selection.
- `GrindstoneBlock.java:40-43,46-66`: default north/wall. `makeShapes()` derives rotated face/facing shapes once into a state-keyed function held in the instance field; collision and outline use that same lookup. File hash is identical.
- `StonecutterBlock.java:31,38-41,67-70`: default north and fixed `Block.column(16,0,9)` shape used as outline; inherited collision derives from this outline. File hash is identical.
- `LecternBlock.java:49-57,65-68,96-104`: default north, unpowered, no book. Collision is fixed union of a full-width 2/16 base and centered 8/16 column from 2/16 to 14/16. Outline additionally rotates its upper-board shapes by facing. These collision methods/constants are unchanged despite unrelated file edits.
- `AnvilBlock.java:32-50,76-79`: default north; same axis-keyed union of four column pieces for outline, with inherited collision. All three registry aliases share this provider. File hash is identical. Falling-block behavior excluded.
- `EnchantingTableBlock.java:36-45,57-60`: no extra state; fixed `Block.column(16,0,12)` outline with inherited collision. Shape body unchanged; the file hash difference is elsewhere.

### Provider, cached shape and context

`BlockBehaviour.java` in both releases: `getCollisionShape(state,level,pos,context)` defaults to `hasCollision ? state.getShape(level,pos) : Shapes.empty()` (26.1.2 lines 333-334; 26.2 lines 327-328). Thus Grindstone/Bell/Lectern provide collision explicitly; Stonecutter, Anvil and Enchanting Table inherit collision from their shape implementation. `Properties.hasCollision` initializes to true (26.1.2 line 979; 26.2 line 985). The state cache stores `block.getCollisionShape(..., CollisionContext.empty())` (26.1.2 line 926; 26.2 line 932), and the no-context `BlockState.getCollisionShape(level,pos)` reads that cached value. The context-taking overload instead dispatches to the block provider (26.1.2 line 685; 26.2 line 691). Grindstone's own state-to-shape function is separately precomputed on instance construction.

### Ordinary player/entity lookup

`Entity.collide` gathers ordinary block colliders with `level.getBlockCollisions(source,boundingBox)` (26.1.2 line 1157; 26.2 line 1228). `CollisionGetter.getBlockCollisions` builds `CollisionContext.of(source)` and the block iterator; 26.2 exposes the context iterator as a default method while preserving this route. `BlockCollisions` passes each state to `context.getCollisionShape(state, collisionGetter, pos)` (line 93 in both); `EntityCollisionContext` delegates to `state.getCollisionShape(collisionGetter,pos,this)`. `CollisionContext.of(player)` yields `EntityCollisionContext` on the ordinary player path. This context-aware query reaches the same fixture shapes in both versions; none has a player-specific collision branch or fluid-only condition.

## Coverage and next action

All assigned named fixtures, their registration/default inputs, shape providers, inherited fallback, cached-shape path, ordinary collision context and entity lookup were examined in both exact versions. No stationary dry-solid collision change and no eligible candidate. This closes only this bounded slice; it does not assert full-pair coverage or independent pair audit.
