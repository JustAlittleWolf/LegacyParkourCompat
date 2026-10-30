# Independent applicability audit: piston and Shulker aliases

## Scope and inputs

This is a bounded exact-source applicability audit, not pair coverage or runtime parity. Canonical inputs were read-only under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<release>\ornithe-feather` for the named Feather Gen2 builds, plus the 26.2 `unobfuscated` sources for the stair identifier check. The source reports supplied for the two accepted witnesses cite the exact release/mapping provenance and complete source-pin checks; this audit directly rehashed its inspected Java files and compared the relevant method bodies. It did not read product implementations, wiki lanes, backlog or Git history, and performed no build, tests, launch, decompilation, download, or canonical source write.

The three supplied alias inputs currently hash to:

| Input | SHA-256 |
| --- | --- |
| `piston-110-family-applicability.md` | `b88aceaff59a7ef266615f7585cc0961e4409bd80f3ad6a5249ae394c9e53d3b` |
| `shulker-pre111-contract-applicability.md` | `b5bab98870cf9131afa14fed65b317021ca272331da81f1665139c16498aace4` |
| Current `hash-matrix.tsv` | `aee79cb1c3dff53936e4adcd2b3754574c4b3941a72066bce90147dd445e654e` |

I rehashed the matrix bytes as they exist now. This binds the appended current matrix only; it does not retain or validate the earlier matrix bytes identified elsewhere as `77be…`.

The corrected Shulker witness supplied for context is `shulker-opening-sneak-edge-corrected.md` (SHA-256 `078650b8e08933ed95a40f760416cb7d52f1b99a2b42f8e754f559c523df0086`), reviewed by its audit (`audit.md`, SHA-256 `8a4c8f60fc580a4ff722b394c3c907f28ce162e205a494930fa0fbb9501520f5`). Those accepted witness claims are used as bounded inputs; I do not re-audit their original 1.10.2→1.11.2 geometry.

## A. 1.10-family applicability of the piston witness

**Verdict: source-applicable to exact releases 1.10, 1.10.1, and 1.10.2.** The directly rehashed `MovingBlockEntity.java`, `StairsBlock.java`, `Block.java`, `Entity.java`, and `Box.java` files are byte-identical across all three releases. The relevant old response therefore keeps progress-increment timing, the union of shapes at current/previous progress, full-cube inherited `Block.getShape` input for the piston query, strict box overlap, positive-axis clipping, and the untyped player move call. These hashes match those recorded in the supplied 1.10-family report and current matrix.

`World.java` differs as a whole, so I checked the used `getCollisions` and `getEntities` method bodies; they are whitespace-normalized identical across the family. No relevant family-local source change was found. This closes applicability of the accepted piston response witness within these three exact releases only; it does not extend the paired witness to another release or close the 1.10→1.11 pair.

### Stair identifier caveat

The source names must not be equated by spelling alone. In 1.9 `Blocks.STONE_STAIRS` resolves through `getOrThrow("stone_stairs")`, and `CraftingManager` produces that block from `Blocks.COBBLESTONE`. In 26.2 `BlockItemIds.COBBLESTONE_STAIRS` is `"cobblestone_stairs"` and `Blocks.COBBLESTONE_STAIRS` is registered with `COBBLESTONE`; separately, `BlockItemIds.STONE_STAIRS` is `"stone_stairs"` and `Blocks.STONE_STAIRS` is registered with `STONE`. For a semantic modern stair analogue to the old craftable block, use `COBBLESTONE_STAIRS`; the literal `stone_stairs` key in 26.2 names the distinct STONE-based block. The inspected source does not itself provide a complete historical registry migration rule from the old key to the modern key, so I do not claim a direct data-fixer mapping.

## B. Pre-1.11 Shulker contract and concrete geometry applicability

**Verdict: the accepted call/predicate/math contract is present in all eight inspected releases (1.9 through 1.10.2). The collision and shape dependencies of the concrete one-move witness also match for that isolated player layout.** This supports bounded source applicability for the witness's local geometry and movement call, not general Shulker trajectories or pair coverage.

Across 1.9–1.10.2, `ShulkerEntity.tick()` retains the positive opening-delta gate, WEST-directed `h`, expanded shape query, Shulker/noClip filters, and untyped `entity.move(k,l,m)` call. The method has one body hash in 1.9–1.9.4 and another in 1.10–1.10.2. Its differences include an AIR test (`getBlock() != Blocks.AIR` versus `getMaterial() != Material.AIR`), equivalent sequential assignments of the same position fields, and removal of a redundant `default` switch label. The AIR test is in the server-only attachment maintenance branch; the accepted witness concerns the client path. The opening float expression and table source remain the same. `MathHelper.java` and Shulker `getCollisionShape()` are byte-identical across all eight.

I extended the accepted witness's stair/box/world collision-provider checks across all eight releases. The whitespace-normalized bodies are identical for `StairsBlock.addCollisions`, `getCollisionShapes`, `getCollisionShapeForInnerStep`, `Block.addCollision`, inherited `Block.getCollisionShape`, `Box.intersects`, `Box.intersectX`, `World.getCollisions`, and both `World.getEntities` overloads. The east upper-step box, strict intersection, positive-X clipping gate, world block-shape collection and query are therefore the same for this layout.

There is one exact provider-body variation to preserve: `WorldChunk.getEntities` changes between the 1.9–1.9.4 and 1.10–1.10.2 groups from an indexed loop over `Entity[]` parts to an enhanced-for loop over the same array; 1.10+ also marks the excluded entity nullable. The regular player inherits `Entity.getParts()` returning `null`; no player override was found. The witness's isolated query contains the player and the Shulker (filtered by the Shulker caller) and no multipart entity, so the loop rewrite cannot change candidate admission for that witness. I do not claim the provider bodies are identical for arbitrary multipart-entity queries.

`Entity.move(double,double,double)` has two body spellings: 1.9–1.9.4 store the exact `0.05` double literal in local `j`; 1.10–1.10.2 repeat the same literal directly in the thresholds and additions/subtractions. The grounded+sneaking+`PlayerEntity` gate, probe offsets, comparisons and step order match. This is an exact source spelling difference with the same Java `double` value, not a behavior boundary for the candidate. `Entity.getCollisionAgainstShape()` is byte-equivalent across the eight, retaining no extra custom Shulker collision response for the ordinary player.

The call/predicate/math contract extends to each inspected pre-1.11 release; the supplied accepted paired audit identifies typed `MoverType.SHULKER` and the SELF/PLAYER-only edge gate at inspected 1.11 sources. This audit does not identify an earliest patch boundary between releases, prove other entity-query cases, or establish runtime movement parity.

## Source identity and evidence artifacts

The direct per-release Java file hashes are in `source-hashes.tsv`; exact selected-body fingerprints are in `method-hashes.tsv`. The fingerprints use SHA-256 over Java method bodies after whitespace collapse; they are comparison aids, with the raw canonical source remaining the primary evidence. The 26.2 identifier rows are under the `stair-id-check` lane.

Static exact-source inspection and hashing only.
