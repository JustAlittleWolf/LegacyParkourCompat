# COMPLETED — blind ordinary block-collision fixture audit

## Bounded assignment and result

Static, exact-source comparison of 1.14.3 → 1.14.4 and 1.14.4 → 1.15 for dry, stationary ordinary player movement collision against Bell, Grindstone, Stonecutter, dry Campfire, Lectern, and Composter. Includes legal state domains/defaults, registry instantiation, direct/inherited collision-shape selection, state cache/context behavior, and the ordinary entity movement collision consumer. Excludes block contact/fire effects, support-only and placement checks, AutoJump, all other edges, and all unlisted block families.

**Verdict:** No change to the ordinary movement collision shape for any assigned fixture on either edge. No movement finding is warranted in this bounded slice. The 1.15 Bell adds a `POWERED` state property/default (`false`); its collision selector does not read that property, so it does not change collision for any Bell state.

## Exact source identity and integrity

- `1.14.3/yarn`, Yarn `1.14.3+build.13`: the embedded `.decompile-provenance.json` SHA-256 is `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`. Its pinned inputs include the exact Yarn build.13 mapping artifact. SHA-256 values of the six assigned block classes, `Blocks.java`, `Block.java`, `BlockState.java`, `Entity.java`, and `ViewableWorld.java` all match the embedded source list (11/11).
- `1.14.4/mojmap`: version-parent `mojmap.sources.sha256` SHA-256 is `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; provenance identifies version `1.14.4`, namespace `mojmap`. Assigned block classes, `Blocks.java`, `Block.java`, `BlockState.java`, `Entity.java`, and `Shapes.java` match the source list (11/11).
- `1.15/mojmap`: version-parent `mojmap.sources.sha256` SHA-256 is `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`; provenance identifies version `1.15`, namespace `mojmap`. The same 11 movement-relevant source files match the source list.

Compared exact paths (relative to each named source root): 1.14.3 Yarn `net/minecraft/block/{BellBlock,GrindstoneBlock,StonecutterBlock,CampfireBlock,LecternBlock,ComposterBlock,Blocks,Block,BlockState}.java`, `net/minecraft/entity/Entity.java`, `net/minecraft/world/ViewableWorld.java`; 1.14.4 and 1.15 Mojmap `net/minecraft/world/level/block/{BellBlock,GrindstoneBlock,StonecutterBlock,CampfireBlock,LecternBlock,ComposterBlock,Blocks,Block}.java`, `net/minecraft/world/level/block/state/BlockState.java`, `net/minecraft/world/entity/Entity.java`, `net/minecraft/world/phys/shapes/Shapes.java`.


## Legal-state and inherited-selector audit

The defaults are stable across all three releases except Bell's 1.15-only `POWERED=false`: Bell defaults to north/floor; Grindstone to north/wall; Stonecutter to north; dry Campfire to lit, non-signal, non-waterlogged, north; Lectern to north/unpowered/no-book; Composter to level 0. The corresponding state declarations expose the same selector domains across the pair, with that Bell property addition only: horizontal facing has four directions; Bell attachment has floor, ceiling, single-wall, double-wall; Grindstone face has floor, wall, ceiling; Campfire's three flags and Lectern's two flags are booleans; Composter level is 0–8. Selector outputs were traced across each domain, not inferred from only the defaults. Grindstone's 1.14.3 Yarn parent is `WallMountedBlock` with `WallMountLocation`; its Mojmap parent in 1.14.4/1.15 is `FaceAttachedHorizontalDirectionalBlock` with equivalent `AttachFace` values. Both properties are included in Grindstone's own state definition, and Grindstone's direct selector handles all three face values and four horizontal facings.

The property/parent files read for this closure are also intact against their corresponding source lists: 1.14.3 `Attachment.java`, `WallMountLocation.java`, `Properties.java`, `HorizontalFacingBlock.java`, `WallMountedBlock.java` (5/5); 1.14.4 and 1.15 `BellAttachType.java`, `AttachFace.java`, `BlockStateProperties.java`, `FaceAttachedHorizontalDirectionalBlock.java` (4/4 each). Shape-coordinate unit helpers were checked: Yarn `Block.createCuboidShape` (1.14.3 `Block.java` 147–148) and Mojmap `Block.box` (1.14.4 `Block.java` 151–152; 1.15 `Block.java` 154–155) both divide each coordinate by 16.
## Per-fixture comparison

All default states and registry constructors were checked in each `Blocks.java`; state-domain declarations and constructors were checked in each block class. Collision-selector results were checked for every legal selector value represented by these properties. `Block.box` in Mojmap and Yarn `Block.createCuboidShape` carry the same numeric box coordinates in the compared implementations.

| Fixture | 1.14.3 → 1.14.4 | 1.14.4 → 1.15 | Exact shape selection relevant to movement |
|---|---|---|---|
| Bell | No collision delta | No collision delta; 1.15 adds `POWERED` to state definition/defaults, but selector ignores it | Facing plus attachment choose the same floor NS/EW, ceiling, double-wall NS/EW, and single-wall directional unions. Collision and outline both call the selector. |
| Grindstone | No collision delta | No collision delta | Facing plus `FACE` (`FLOOR`, `WALL`, `CEILING`) select the same union of post/pivot/plate cuboids in all 12 orientations. Collision and outline use the same state selector. |
| Stonecutter | No collision delta | No collision delta | Constant box `(0,0,0)-(16,9,16)`; `FACING` is legal/defaulted north but does not affect shape. In 1.14.3 its `getOutlineShape` feeds inherited collision; in Mojmap its `getShape` feeds inherited collision. |
| Campfire (dry) | No collision delta | No collision delta | Constant box `(0,0,0)-(16,7,16)` for every state (`LIT`, `SIGNAL_FIRE`, `WATERLOGGED`, `FACING`); dry state is explicitly legal and defaults to `WATERLOGGED=false`. In 1.14.3 `getOutlineShape` feeds inherited collision; in Mojmap `getShape` feeds inherited collision. |
| Lectern | No collision delta | No collision delta | Collision always returns common base plus the same top-plate shape, independent of `FACING`, `POWERED`, and `HAS_BOOK`. Facing-specific elevated-book outline is separate and not selected for collision. |
| Composter | No collision delta | No collision delta | `LEVEL` ranges over the same levels and defaults to zero. State-dependent outline uses the same `max(2, 1 + 2*level)` hollow-shape construction; movement collision explicitly selects index/level 0 for every state in all three releases. |

Registry names and instantiated classes for all six fixtures persist on both edges (`Blocks.java`: 1.14.3 lines 1993–2021; 1.14.4 lines 1992–2019; 1.15 lines 2086–2115). Relevant per-version declarations and collision selectors are at:

- 1.14.3: Bell `BellBlock.java` 32–49, 107–132; Grindstone `GrindstoneBlock.java` 25–140; Stonecutter `StonecutterBlock.java` 28–55, 90; Campfire `CampfireBlock.java` 42–51, 131–132, 258; Lectern `LecternBlock.java` 35–67, 91–107, 123; Composter `ComposterBlock.java` 36–42, 133, 170–181, 266.
- 1.14.4: Bell `BellBlock.java` 36–51, 111–136, 229–230; Grindstone `GrindstoneBlock.java` 25–140, 170–171; Stonecutter `StonecutterBlock.java` 29–58, 92–93; Campfire `CampfireBlock.java` 43–51, 140–141, 266–267; Lectern `LecternBlock.java` 35–67, 91–107, 122–123; Composter `ComposterBlock.java` 39–42, 131, 168–179, 263–264.
- 1.15: Bell `BellBlock.java` 39–54, 138–163, 256–257; Grindstone `GrindstoneBlock.java` 27–142, 179–180; Stonecutter `StonecutterBlock.java` 29–64, 88–89; Campfire `CampfireBlock.java` 47–56, 148–149, 304–305; Lectern `LecternBlock.java` 38–70, 94–110, 125–126; Composter `ComposterBlock.java` 41–44, 133, 170–181, 267–268.

## Collision-consumer and cache/context closure

- 1.14.3 `Entity.handleCollisions` constructs `EntityContext.of(this)` and feeds block shapes into movement clipping (`Entity.java` 653–699); `ViewableWorld` obtains each block state’s `getCollisionShape(..., entityContext)` (`ViewableWorld.java` 133–176). `BlockState.getCollisionShape` delegates unless its state cache is available (`BlockState.java` 192–197); that cache evaluates the collision shape with absent context (`BlockState.java` 379–398). Every assigned collision selector ignores context/world/position, so cached and direct calls agree.
- 1.14.4 `Entity.collide` builds `CollisionContext.of(this)` and runs entity movement clipping (`Entity.java` 658–715, 753–780). The collision solver calls block-state collision shape with the supplied context (`Shapes.java` 267–290). `BlockState` delegates or uses its cached collision shape (`BlockState.java` 194–199 and cache initialization around 409). All six fixture collision functions ignore context/world/position; no entity-dependent branch is present.
- 1.15 `Entity.collide` and its bounding-box collision branches use the same entity collision-context path (`Entity.java` 641–698). `CollisionGetter.getBlockCollisions` requests each state’s collision shape using the entity context (`CollisionGetter.java` 67–108); `BlockState` delegates or uses the context-empty cache (`BlockState.java` 192–199, cache initialization around 415). All assigned collision selectors remain context-independent.

No source-integrity, selector-closure, or collision-consumer gap remains within the bounded assignment. This is a static source verdict only; no tests, game launch, or runtime parity claim is made.

