# End portal frame collision provider: 1.11 → 1.11.1

## Exact source identity

Compared the exact `ornithe-feather` sources for this edge:

| Input | 1.11 | 1.11.1 |
| --- | --- | --- |
| Source root | `decompiled_minecraft/1.11/ornithe-feather/` | `decompiled_minecraft/1.11.1/ornithe-feather/` |
| `EndPortalFrameBlock.java` SHA-256 | `B9396746F87A619C89DCAD660D434ABB57E1943A6FBEBB63A75D30A05F752965` | `E4E9B5AECA29EFF111A8ECCE69C7267A6BC1D0458A9E528F9522D08F58584AF4` |
| `ornithe-feather.sources.sha256` SHA-256 | `8B3E5DA58494F1DBA77410960753B0BF646425A1ADA491D8242CC71E6C7A59CA` | `0D5F4697D4E4CE53B36D0DBFEC89D44830EB76753A6643B49E42F0857753066D` |
| `ornithe-feather.provenance.json` SHA-256 | `6F775C806FD1401A4D1756E9EF88DA8D73185FE1D13598235F85C424405F4C40` | `0F6C92B8FBF4B1053A26A4CB61DC8F20C695E74A1963B49D911B8C7FBF01A055` |
| `ornithe-feather.ready.json` SHA-256 | `3C4102230DBA0F9D2D07F752BBD2A19D2717375F6E31FB618C0450B7FD82BD45` | `454036A978074D3A34359DFEBF03D6EF3E3335D1318B88D6B17E4F1F5AB566CF` |

Each source-manifest row for `net/minecraft/block/EndPortalFrameBlock.java` matches the corresponding direct file hash above. The roots and release identities are bound by their exact provenance files; the pair coverage correction records 1,918/1,921 source entries and 113 changed shared paths plus three additions.

## State and collision comparison

`EndPortalFrameBlock.createStateDefinition` contains `FACING` and `EYE`. `FACING` aliases `HorizontalFacingBlock.FACING`, a horizontal direction property (north, east, south, west); `EYE` is boolean. Thus the legal state product is four facings × eye absent/present. Default state is north with no eye. Placement chooses the opposite of the placing living entity's horizontal facing and initializes eye=false. Rotation/mirroring only change facing.

For every one of the eight legal combinations, the outline method returns the same lower slab, `Box(0, 0, 0, 1, 0.8125, 1)`, independently of both properties. The collision callback adds that same base box if it intersects the query. If the block state read from the world at the position has eye=true, it also adds the centered eye box `Box(0.3125, 0.8125, 0.3125, 0.6875, 1, 0.6875)` when intersecting the query. Facing never changes either box. The eye box is vertically adjacent to the base top at 0.8125, not overlapping it; the collision list therefore represents both surfaces for eye-bearing frames.

There is no collision-shape behavior change in this provider between the versions. The exact method change is API-only: the 1.9-era `addCollisions(..., Entity)` override in 1.11 becomes `addCollisions(..., Entity, boolean forceShape)` in 1.11.1. Both bodies emit the same `SHAPE`, test the same world state for `EYE`, and emit the same `EYE_SHAPE`; the new `forceShape` argument is unused. `getShape`, property definition/default, placement, metadata conversion, rotation, and mirror logic are otherwise the same in the compared provider.

## Local-player query boundary

In 1.11.1, `World.getCollisions(entity, shape)` enumerates block states and calls the provider with `forceShape=false`; its `Entity` argument is passed through. `Entity.move` obtains collision boxes from this world query in its collision-resolving movement branches. Therefore the unchanged frame boxes remain among the collision candidates for a local client player whose query overlaps them; this is a direct provider/query fact, not a claim about any particular trajectory or movement outcome. The separate no-entity unobstructed query is outside the local-player response claim.

## Exclusions and residual status

Portal activation is separate: eye/facing predicates participate in `getPortalPattern`, and analog signal output depends on eye. Those pattern/redstone results do not alter this collision provider. Item insertion, portal creation, drops, rendering and world-generation occurrence are not assessed as movement claims.

This closes only the EndPortalFrameBlock provider/state row for the exact pair. It does not disposition other changed providers, world collision iteration, client tick/input/response families, or any pair-level residual from coverage r2. Reuse the accepted piston and other bounded slices as originally scoped; no full-pair freeze is inferred.
