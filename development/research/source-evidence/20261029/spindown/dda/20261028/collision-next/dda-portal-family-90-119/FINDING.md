# Portal provider closure: legacy IDs 90 and 119 (1.8.9 → 26.2)

## Source identity

The read-only 1.8.9 source is the `ornithe-feather` export (Feather Gen2 1.8.9 build.2 merged-v2, Vineflower). Its ready marker reports version `1.8.9`, mapping `ornithe-feather`, and 1,612 source files. The 26.2 export is `unobfuscated` (Vineflower), version `26.2`, with 7,055 source files. Their ready-marker, provenance, and canonical source-manifest SHA-256 values are recorded in `source-hashes.json`; all selected Java files were rehashed and match the canonical manifest entries.

## Legacy registration and state

The 1.8.9 registry registers ID 90 as `portal` using `PortalBlock` (`net/minecraft/block/Block.java:1026`) and ID 119 as `end_portal` using `EndPortalBlock` (`Block.java:1077`).

ID 90 has an `AXIS` property with valid values X and Z and default X (`PortalBlock.java:22–27`). Metadata is decoded by `(metadata & 3) == 2 ? Z : X` (`PortalBlock.java:200–202`); encoding returns 1 for X and 2 for Z (`PortalBlock.java:72–77,205–207`). This report only treats those decoded states as supported; other metadata values collapse through the shown mask.

ID 119 has no state property or metadata override in `EndPortalBlock`; its provider is state-invariant. The block-entity behavior is not part of this closure because neither the ray-bound writer nor its movement-collision path reads block-entity data.

## Legacy ray and movement providers

Neither class overrides `canRayTrace` or `rayTrace`, so both use `Block` defaults. `Block.canRayTrace(BlockState,boolean)` delegates to the default-true no-argument predicate (`Block.java:356–362`). The inherited default `Block.rayTrace` calls virtual `updateShape(world,pos)` before clipping against the instance bounds (`Block.java:456–459`); a miss still occurs after that writer call. The base `updateShape` is empty (`Block.java:596–598`).

`PortalBlock.getCollisionShape` returns null (`PortalBlock.java:52–54`), so it contributes no movement box. Its `updateShape` reads only the queried cell's AXIS and writes these bounds (`PortalBlock.java:57–72`):

| State | X bounds | Y bounds | Z bounds |
|---|---|---|---|
| X | `[0,1]` | `[0,1]` | `[6/16,10/16]` |
| Z | `[6/16,10/16]` | `[0,1]` | `[0,1]` |

`EndPortalBlock.updateShape` writes `[0,1] × [0,1/16] × [0,1]` (`EndPortalBlock.java:31–34`). It overrides `addCollisions` with an empty body (`EndPortalBlock.java:42–44`), so the mutable ray bounds are not consumed by the legacy movement-collision collection path for this block.

## Current aliases and provider behavior

The 26.2 identifiers are `BlockIds.NETHER_PORTAL` and `BlockIds.END_PORTAL` (`net/minecraft/references/BlockIds.java:45,49`), registered as `Blocks.NETHER_PORTAL` and `Blocks.END_PORTAL` (`world/level/block/Blocks.java:2110–2118,2590–2598`). The block-state data-fixer evidence binds legacy ID 90 to `minecraft:portal` and ID 119 to `minecraft:end_portal` (`util/datafix/fixes/EntityBlockStateFix.java:112,141`; `ItemIdFix.java:95,116`). `EntityTheRenameningFix.RENAMED_BLOCKS` maps `minecraft:portal` to `minecraft:nether_portal` (`:22–23`), while `BlockStateData` records the old portal X/Z state entries and the end-portal entry (`:3887–3888,4943`). These mappings establish the named data-fixer aliases; they do not assert an untraced runtime conversion pipeline.

Both current registrations use `.noCollision()` (`Blocks.java:2114–2118,2593–2598`); base `BlockBehaviour.getCollisionShape` therefore returns `Shapes.empty()` when `hasCollision` is false (`BlockBehaviour.java:327–328`, property setter at `1094–1098`). For ordinary movement collision, this matches the old no-box results: portal's null provider and EndPortal's empty `addCollisions`.

The current outline providers are separate from those empty movement providers. `NetherPortalBlock.getShape` selects a shape by AXIS (`NetherPortalBlock.java:51,63–65`). Its base Z shape is `Block.column(4,16,0,16)`; `Block.column(sizeX,sizeZ,minY,maxY)` centers the given X/Z widths and uses Y bounds in sixteenths (`world/level/block/Block.java:177–182`). `Shapes.rotateHorizontalAxis` maps Z to that shape and X to a 90-degree rotation (`world/phys/shapes/Shapes.java:379–385`). Consequently the current axis-dependent outline bounds match the legacy `PortalBlock.updateShape` bounds listed above. This is a local outline/provider geometry comparison, not a claim that the complete ray traversal or hit result is equivalent.

`EndPortalBlock.getShape` returns `Block.column(16,6,12)` (`EndPortalBlock.java:34,50–52`), which is full-width X/Z with Y bounds `[6/16,12/16]`. This differs from the legacy ray-bound writer's `[0,1/16]` height. Its registration still disables ordinary movement collision. No end-portal entity-inside or teleport response is assessed here.

## Bounded conclusion and open scope

- Closed for these two IDs: exact legacy registrations; ID 90 axis metadata decoding; current registered aliases; legacy ray-provider dispatch and state inputs; old versus current local outline geometry; and old versus current ordinary movement-collision results.
- No legacy provider in this pair reads neighboring block states or block-entity data for its ray-bound or movement-collision result.
- Not closed: complete DDA visited-cell order, cache-history/last-writer behavior across other providers, ray hit selection, a full old-world provider adapter, portal entity responses, or runtime reachability.
- No product files, builds, tests, or runtime launches were used.

## Source files

Canonical absolute roots and every selected file digest are listed in `source-hashes.json`. The selected files were read-only and every per-file digest matched its release's canonical `.sources.sha256` entry.
