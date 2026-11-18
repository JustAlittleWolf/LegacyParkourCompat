# 1.16.1–1.16.2 connected-block collision slice

Status: no movement-shape difference found in this assigned slice; no permanent finding drafted.

## Exact source identity

Canonical read-only sources: `decompiled_minecraft/{1.16.1,1.16.2}/mojmap`.

- 1.16.1 provenance SHA-256 `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`; ready marker says `ready`, version `1.16.1`, mapping `mojmap`, 3,572 source files. Source-manifest SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` (matches ready marker and on-disk file hash).
- 1.16.2 provenance SHA-256 `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`; ready marker says `ready`, version `1.16.2`, mapping `mojmap`, 3,516 source files. Source-manifest SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` (matches ready marker and on-disk file hash).
- The manifest file hashes for both provenance files also match the supplied identities. The inspected class files are present in their corresponding source manifests.

## Examined evidence and comparison

All paths below are relative to `decompiled_minecraft/<version>/mojmap/`.

### Cobblestone wall: no movement difference

`net/minecraft/world/level/block/WallBlock.java`, 1.16.1 and 1.16.2: `makeShapes` (lines 58–113), `getShape` (116–118), `getCollisionShape` (121–123); wall collision parameterization and shape lookup bodies match. The only class diff is the `BlockPlaceContext` import relocation (`world.item` to `world.item.context`), outside the queried shape methods.

The same registered `COBBLESTONE_WALL` is a `WallBlock` constructed with copied cobblestone properties in 1.16.1 `Blocks.java:1085` and 1.16.2 `Blocks.java:1092`. Static legal shape-state inputs are `UP` plus four `WallSide` properties (`NONE`, `LOW`, `TALL`) and waterlogged state; the generated collision map duplicates each geometry for either waterlogged value. The collision dimensions remain: central post x/z 4–12, y 0–24; each side strip x/z 5–11, y 0–24. The separate non-collision shape remains central post to y 16 and side arms at their selected 14/16 heights. Neighbor-derived state generation was not treated as part of the slice.

### Oak fence and nether-brick fence: no movement difference

`net/minecraft/world/level/block/CrossCollisionBlock.java`, both versions: shape construction (`makeShapes`, 44–82), dispatch (`getShape`, 90–92; `getCollisionShape`, 95–97), and the cached horizontal connection-mask lookup (`getAABBIndex`, 103–123) match. SHA-256 of this file is identical across versions: `cebb3dcbbb94ab7513e11430fa4969bc1ff0a96c733298940889fc749b7ec1e4`.

`net/minecraft/world/level/block/FenceBlock.java`: constructor (30–36) and collision-shape behavior inherited from `CrossCollisionBlock` match; only `BlockPlaceContext` import relocation differs. `Blocks.java` registers `OAK_FENCE` using `FenceBlock` with the same properties at 1.16.1:712–714 and 1.16.2:718–720; `NETHER_BRICK_FENCE` uses `FenceBlock` at 1.16.1:970–978 and 1.16.2:977–985. Both retain horizontal boolean mask states and the same shape parameters. Fence collision is the 4×4-pixel central core and 4×4-pixel arms, with core/arms y 0–24; the returned outline shape remains y 0–16. Fixed mask values select the same unions of core and the arms extending to each block edge.

### Iron bars and glass pane: no movement difference

`net/minecraft/world/level/block/CrossCollisionBlock.java` shape construction, mask cache, and collision/outline dispatch match as stated above. `net/minecraft/world/level/block/IronBarsBlock.java` constructor (19–26) and collision behavior match; the only diff is the import relocation. Both use `IronBarsBlock` registrations for `IRON_BARS` and `GLASS_PANE`: 1.16.1 `Blocks.java:899–921`, 1.16.2 `Blocks.java:906–928`. Constructor parameters remain `(1, 1, 16, 16, 16)`, so central and directional arms are 2 pixels thick (coordinate 7–9), extend to the relevant block edge, and occupy y 0–16 for both outline and collision. Their fixed horizontal masks and cached mask calculation are unchanged.

### Ordinary dry client collision consumer checked

`net/minecraft/world/level/CollisionSpliterator.java` was compared in both versions. The ordinary query still constructs a `CollisionContext` from the entity (or empty context), expands the integer scan shell using `floor(min - 1e-7) - 1` through `floor(max + 1e-7) + 1`, applies the existing special scan-type admissions (`hasLargeCollisionShape` and moving-piston checks), calls `BlockState.getCollisionShape`, uses the whole-block fast path when shape is `Shapes.block()`, and otherwise clips via `Shapes.joinIsNotEmpty(..., BooleanOp.AND)`. No source difference was shown for this file.

`CollisionGetter.java` has a 1.16.2 convenience method `noBlockCollision` which tests the existing block-collision stream with `allMatch(VoxelShape::isEmpty)`. The 1.16.2 `LocalPlayer.suffocatesAt` uses it. This wrapper does not alter the collision shapes or scan/admission/clipping code examined for this roster; the corresponding player consumer was read only to verify ordinary use, not as an additional owned candidate.

## Limits

This is only the assigned stationary, ordinary-collision slice for the named registered roster. Excluded/unexamined: connection-state generation or neighbor updates, world-border handling, AutoJump, pose/dimensions, airborne movement backoff, other block families (including doors, gates and fixtures), broad player tick closure, and full-pair independent audit. Evidence establishes source observations for these endpoints only; it does not establish an intermediate patch boundary or runtime parity.
