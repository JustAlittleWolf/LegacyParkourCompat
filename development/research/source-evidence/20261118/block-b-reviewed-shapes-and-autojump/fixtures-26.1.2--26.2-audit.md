# Independent source audit: stationary fixture collision, 26.1.2 → 26.2

Status: **COMPLETED**

## Assignment and limits

Blind source-only audit of dry, stationary solid geometry and the ordinary local-player block-collision lookup for Bell, Grindstone, Stonecutter, Lectern, Anvil (ANVIL / CHIPPED_ANVIL / DAMAGED_ANVIL), and Enchanting Table. The result is an endpoint comparison only; it does not establish an intermediate patch boundary, full-pair movement coverage, implementation acceptance, or runtime parity.

Excluded as assigned: transferred A slab/stair/door/trapdoor/fence-gate families; Hopper and Composter; walls and panes; falling blocks; fluids and contact effects; restitution. No implementation, tests, gameplay launch, or build was performed.

## Exact source identity and integrity

Read only the canonical published trees:

- `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.1.2/unobfuscated/`
- `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/`

Both ready markers report the matching exact `versionId`, `mapping: unobfuscated`, and `status: ready`. Their `sourceManifestSha256` values match the supplied parent-manifest identities and the computed SHA-256 of each `unobfuscated.sources.sha256`. Counts agree across ready marker, manifest lines, and actual Java source files (26.1.2: 6,882; 26.2: 7,055). All 18 reviewed source members below match their respective source-manifest entries. Reviewed methods are present with concrete bodies; neither movement-diagnostics file has an entry naming the target block or collision-path classes.

| Version | Ready marker SHA-256 | Provenance marker SHA-256 | Source-manifest SHA-256 |
|---|---|---|---|
| 26.1.2 | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` |
| 26.2 | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |

The 26.2 provenance marker records its success log as a summary excerpt and `mappingArtifacts: null`; the ready marker, exact version ID, namespace, and complete source-manifest identity still agree. This audit treats the supplied ready/source manifests as the publication identity.

### Reviewed source-member SHA-256

| Relative source member | 26.1.2 | 26.2 |
|---|---|---|
| `net/minecraft/world/level/block/BellBlock.java` | `376e9341d9660a2e979ba9352e759a183441f38b982d30c64399323d009deca5` | `89267235fe2f3d9d762b2e05be4b7f41ebb6fc3dfa42b23c11162c3550d78fc1` |
| `net/minecraft/world/level/block/GrindstoneBlock.java` | `ffe4dd67c9b3c6ef2ed81b7baab0075aade78a92b9c7676cfa5d9ef79d763d73` | same |
| `net/minecraft/world/level/block/StonecutterBlock.java` | `6fbb26aa6b535fe62ca25688c31f55635c3b1dd2c8038c7fdb7ed9b9bca61ab2` | same |
| `net/minecraft/world/level/block/LecternBlock.java` | `a59665b6eba0ea0a87b20b05255a67d281102e312d99e63bfb041518b0a18949` | `7f81309060b80f2c7ca17e869c8b1afbe1dc8f7aa5c7d99aa6439f8aa0dd810f` |
| `net/minecraft/world/level/block/AnvilBlock.java` | `a15b5ef5067f098a2e6b83d3b934730f376a3420a4ce47c798a6a80d800b9391` | same |
| `net/minecraft/world/level/block/EnchantingTableBlock.java` | `97c1f127b812ef20d375f53dcbff9afc09528e3dcb76823ca24ef2437cd5f852` | `e4853a98ce96882cb42c7b078b74782ae673d71dba151ebdba10235695f3918f` |
| `net/minecraft/world/level/block/Blocks.java` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `net/minecraft/world/level/CollisionGetter.java` | `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01` | `8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c` |
| `net/minecraft/world/level/BlockCollisions.java` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` | same |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |
| `net/minecraft/world/phys/shapes/CollisionContext.java` | `8702d23bea0f119e0d63ce15ff59088559a319283186372fd69adebeed4dcd69` | `0af02ec998df9e5b093a1b1887255619dd9b9a08604de40b452c11922fbf3bca` |
| `net/minecraft/world/phys/shapes/EntityCollisionContext.java` | `2f0a2bfd72a3d35121e3ef4ab80755f04cefaa7dd933988e2246b39ff5c39291` | same |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` | same |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/world/entity/Avatar.java` | `01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78` | same |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |

## Ordinary local-player lookup checked

In both releases `LocalPlayer.move` saves the previous X/Z, calls `super.move`, then updates auto-jump/walk distance (`LocalPlayer.java:986-994`). `AbstractClientPlayer` does not override `move`; `Player` → `Avatar` → `LivingEntity` → `Entity` supplies the general implementation. `Entity.move` calls `collide`; `Entity.collide` calls the `collideBoundingBox(Entity, ...)` overload for ordinary movement (`Entity.java:704-731, 1077-1080` in 26.1.2; `738` / `1141-1144` in 26.2; see the exact bodies around these members). The Entity overload gathers `level.getBlockCollisions(source, box)` on both versions (26.1.2 `Entity.java:1157`; 26.2 `Entity.java:1228`).

`CollisionGetter.getBlockCollisions(Entity, AABB)` uses `CollisionContext.of(source)` then creates `BlockCollisions` in both versions (`CollisionGetter.java:96-105`). `EntityCollisionContext.getCollisionShape` delegates to `state.getCollisionShape(collisionGetter, pos, this)` (`EntityCollisionContext.java:68-69`), and the identical-hash `BlockCollisions.java` asks that context for the shape before moving it to block position and intersecting it with the player box (`BlockCollisions.java:89-101`). The 26.2 API adds a `CollisionContext` overload/helper and `CollisionContext.positionContext`; the ordinary `Entity` overload remains the route selected by `Entity.collide`, and `CollisionContext.of(Entity)` still constructs the same standard entity context. This refactor does not change the ordinary target-block lookup.

For state types whose blocks inherit the base collision method, `BlockBehaviour` still returns `hasCollision ? state.getShape(level,pos) : Shapes.empty()` (`BlockBehaviour.java:333-335` / `327-329`). The one-argument `BlockState.getCollisionShape(level,pos)` may return its `Cache.collisionShape` (`680-682` / `686-688`), initialized from the block with `EmptyBlockGetter`, `BlockPos.ZERO`, and an empty context (`Cache` constructor at `926` / `932`). The ordinary entity path does not call that one-argument accessor: `EntityCollisionContext.getCollisionShape` calls the contextual overload (`68-69`), and `BlockState.getCollisionShape(level,pos,context)` dispatches to the block (`684-686` / `690-692`). Bell, Grindstone and Lectern override this contextual method directly; Stonecutter, Anvil and Enchanting Table use the same base fallback. Their providers are state-only (or constant), so the empty context used by that fallback has no geometry effect. No collision result relies on a fluid path here.

## Per-family verdicts

All verdicts: **NO DELTA** in registered target-block collision geometry, state-to-shape selection, or ordinary local-player lookup for the assigned dry/static scenario.

| Family | Exact source evidence and state/registration closure | Verdict |
|---|---|---|
| Bell | `Blocks.java:5515-5518` / `4545-4548` registers `BellBlock`. `BellBlock.java:49-55` / `50-56` defines the body, ceiling, floor and wall shapes; default state is north/floor/unpowered (`65-67` / `66-68`), and legal properties are FACING/ATTACHMENT/POWERED (`276-278` / `269-271`). `getVoxelShape` selects the same attachment/facing map and both `getCollisionShape` and `getShape` delegate to it (`155-173` / `151-169`). The class diff only changes interaction/event/ticker code outside the shape provider. | No delta for any legal attachment/facing/power state. |
| Grindstone | Registered as `GrindstoneBlock` (`Blocks.java:5490-5493` / `4520-4523`). Default state is north/wall; FACING and FACE are its state properties (`GrindstoneBlock.java:42-43, 103-105`, both releases). `makeShapes` builds the same rotated north shape and indexes it by FACE and FACING (`46-56`); both outline and collision return that state-selected shape (`59-65`). Entire source file hashes are identical. | No delta for legal face/facing states. |
| Stonecutter | Registered as `StonecutterBlock` (`Blocks.java:5510-5513` / `4540-4543`). FACING remains the sole state property with north default (`StonecutterBlock.java:40-41, 88-90`); shape remains the 16×16 footprint column from y=0 to 9 (`31`, `68-70` in both). Entire source file hashes are identical. | No delta across facing states. |
| Lectern | Registered as `LecternBlock` (`Blocks.java:5500-5503` / `4530-4533`); default is north, unpowered, no book, with FACING/POWERED/HAS_BOOK properties (`LecternBlock.java:67-68, 117-119`, both). Collision remains `or(column(16,0,2), column(8,2,14))` independent of those state properties (`49`, `97-99` in both); facing affects only `getShape` outline (`102-104`). File diff contains redstone/screen changes, not collision geometry. | No delta for legal state combinations. |
| Anvil aliases | `ANVIL`, `CHIPPED_ANVIL`, and `DAMAGED_ANVIL` each register `AnvilBlock::new` in both versions (`Blocks.java:2906-2929` / `2853-2876`). `AnvilBlock` keeps north default and sole FACING property (`49-50, 121-123`); its axis-rotated shape map and `getShape` selector are byte-identical (`35-47, 77-79`). It inherits the same base collision path in both versions. | No delta for all three aliases and facing states. |
| Enchanting Table | Registered as `EnchantingTableBlock` (`Blocks.java:2634-2637` / `2557-2560`). Its state-independent shape remains `Block.column(16,0,12)` and `getShape` returns it (`36, 58-60` in 26.1.2; `37, 59-61` in 26.2); base collision uses that outline. The class diff changes only a block-entity ticker type reference. | No delta. |

The target registration suppliers and collision-relevant properties remain the same. 26.2 changes some `Blocks` identifier arguments to `BlockItemIds` constants, but preserves each block's registration factory and properties. Each class's state declaration/default relevant to its shape is unchanged.

## Coverage and unresolved boundary

Compared target block registration, state properties/defaults, shape constants/tables/selectors, collision overrides and inherited fallback; traced the ordinary `LocalPlayer.move` → `Entity.move` → block-collision iterator → entity collision context → `BlockState.getCollisionShape` lookup. Both endpoint bodies provide closed evidence for these assigned slices. No historical change is supported for the assigned families. This does not audit other collision consumers, the excluded candidates, every movement subsystem, intermediate patches, or runtime behavior.

