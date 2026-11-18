# B16 source review: ordinary walls, bars, and panes (26.1.2 → 26.2)

Status: COMPLETED — bounded source review; no eligible movement difference found, so no finding draft was created.

## Inputs and evidence identity

- Host: Windows PowerShell; canonical read-only sources at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{26.1.2,26.2}/unobfuscated`.
- `26.1.2`: `unobfuscated.sources.sha256` SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; ready marker names exact version 26.1.2, unobfuscated, 6,882 files.
- `26.2`: `unobfuscated.sources.sha256` SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; ready marker names exact version 26.2, unobfuscated, 7,055 files.
- Relevant source hashes from each source manifest are recorded below. Matching hashes mean byte-identical source bodies; differing hashes were inspected at the movement-relevant members, not treated alone as behavior changes.

## Checked coverage

- Ordinary `WallBlock`: compared declaration, default state, `makeShapes`, `getShape`, `getCollisionShape`, connection predicates, placement and neighbor update paths, fixed-state above-shape lookup, wall-side/post update logic, and rotation/mirror. Geometric constants and provider construction are unchanged: visual provider `makeShapes(16.0F, 14.0F)`, collision provider `makeShapes(24.0F, 24.0F)`; post/arm dimensions and shape-state cache construction match. The rotation implementation is rendered as switch-expression vs switch-statement source syntax; the assignments are equivalent. Fixed-state vertical support remains driven by the above neighbor collision face and the unchanged `shouldRaisePost` / `makeWallState` predicates.
- Ordinary `IronBarsBlock` and `StainedGlassPaneBlock`: exact source files are byte-identical. Both continue to use `CrossCollisionBlock`; its provider formulas and mask iteration are unchanged in operation. Placement and horizontal neighbor updates still derive each directional mask from neighbor face-sturdiness plus the unchanged exception / iron-bars / wall-tag checks.
- Glass and stained glass pane registrations: ordinary glass pane remains an `IronBarsBlock` with identical pane properties. 26.1.2 registers 16 individually named `StainedGlassPaneBlock(DyeColor, properties)` instances; 26.2 uses `ColorCollection.registerBlocks`. The 26.2 `ColorCollection` order and `pick(DyeColor)` map preserve the same 16 DyeColor-to-ID/factory pairing; pane properties are unchanged. This is registration organization and aliasing, with no state or movement change.
- Shape/cache/provider and clipping dependencies: `BlockState.java`, `BlockCollisions.java`, and `VoxelShape.java` are byte-identical. `BlockBehaviour.BlockStateBase` cache still captures each fixed collision shape via `getCollisionShape(state, EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty())`, computes the same large-shape bounds predicate, and caches face-support results through `SupportType.isSupporting`; relevant behavior is unchanged. `BlockCollisions` still gates its extra cursor-face traversal using `hasLargeCollisionShape()` and obtains the player-context collision shape before translating/intersecting it. `CrossCollisionBlock` source hashes differ due source-level representation changes, but the inherited arm/post dimensions, direction mask, shape OR behavior, and providers were checked and found equivalent.
- Legal neighbor domain considered: air, stone, the matching wall, and same-kind pane/bar neighbor cases only. Fluid/waterlogging production, unrelated world mutation and non-legal neighbor states were not treated as findings.
- Excluded by assignment: fences and fence-gates, slabs/stairs/doors/trapdoors, restitution, fluid behavior, unrelated world-state production, and generic solver changes.

## Relevant exact-source references and hashes

Paths are relative to each version's `unobfuscated/` root. Line numbers are from the decompiled files.

| Source | 26.1.2 SHA-256 | 26.2 SHA-256 | Reviewed members / lines |
|---|---|---|---|
| `net/minecraft/world/level/block/WallBlock.java` | `4dc61223dce4023deb3a7986983db677ccf4c89a53778692bacb301927b9b345` | `b2bcd69d81e5a19a21eb50e7e5ac259269aca3871cec5899caef03ff4507a9c7` | fields/constructor/provider, 35–108; placement/update/side and fixed-state vertical support, 113–253; collision getters 90–98 |
| `net/minecraft/world/level/block/IronBarsBlock.java` | `b8d8467db13454fc5cd24c95827172b8550d5cba3d51930ba8b240b960606e54` | same | placement and horizontal masks, 27–79; clipping/render predicate and attach predicate, 81–107 |
| `net/minecraft/world/level/block/StainedGlassPaneBlock.java` | `dfaee99dba568ab4b0c791db1e4733f3da0205ab53a82e1e039481bf8696492de` | same | inheritance/default directional state/color constructor, 15–37 |
| `net/minecraft/world/level/block/CrossCollisionBlock.java` | `e6cea1c67ebf69f726e3dca7781cf83249a20990c339e4d053f12ec21f1fa05c` | `dcd2dbbcd093a68dee4ee282e5f3101c519d633f3f173dd33383bd4b548c1ba5` | constructor/provider, 34–84; horizontal masks, shape map and collision getter |
| `net/minecraft/world/level/block/Blocks.java` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` | glass pane registration 2454–2458 / 2360–2364; stained pane registrations 3134–3213 / collection 2963–2968 |
| `net/minecraft/world/level/block/ColorCollection.java` | absent | verified as 26.2 source | Dye color ordering and block registration/pick, 15–53, 94–113 |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` | `hasLargeCollisionShape`, 575–576 / 577–578; cached state shape/support data, 916–956 / 922–962 |
| `net/minecraft/world/level/block/state/BlockState.java` | `df5b24701ee69145a78901ee0e370e063b63b3e74a0aae32c89b2ae45bc6d205` | same | state delegation surface, byte-identical |
| `net/minecraft/world/level/BlockCollisions.java` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` | same | cursor face-type / large collision gate and context-provider clipping, byte-identical; relevant lines 80–100 |
| `net/minecraft/world/phys/shapes/VoxelShape.java` | `419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854` | same | shape face/bounds/clipping primitives, byte-identical |

## Disposition

No changed operation in this bounded family changes player collision shape, mask state, fixed-state support, shape caching/provider selection, or horizontal clipping for the assigned dry ordinary states and legal neighbors. No eligible finding draft.

Static evidence checks only; no implementation, test, gameplay, TAS, Gym, server, or decompiler task was run.
