# 1.7.10–1.8 collision provider registration and dispatch

## Evidence identity

- Canonical read-only sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.7.10\ornithe-feather` (Ornithe Feather 1.7.10+build.2) and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8\ornithe-feather` (Ornithe Feather 1.8+build.2). Mapping family is Feather on both sides.
- Raw client SHA-256: 1.7.10 `a4fc2284657544e0f4bcc964f927c2fda3e3a205178ed1d5d58883aaf9780cce`; 1.8 `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe`.
- `.decompile-provenance.json` SHA-256 re-read: 1.7.10 `11BADA441EBE07661402D1C40E1C490B78B279ECAA084F9A4DC3AE2DD54CAF6F`; 1.8 `52C8312C34A1A491D4A200C84E90F5D8D95C7E5CAA9571F864D8E70F9737DB05`.
- [Complete case-sensitive class/member/per-file hash inventory](provider-inventory.tsv) is generated from both canonical trees and records every public `getCollisionShape` and `addCollisions` declaration under `net/minecraft/block/`.
- Artifact SHA-256: `provider-inventory.tsv` `FEC39E0A4CC5DFFBFEAC001D0A55509A9026BA39C367D3C872DEDF5639029706`.

## Provider inventory and gap check

A case-sensitive whole-tree scan for `public Box getCollisionShape(...)` and `public void addCollisions(...)` found 44 block-package classes on each side, with 43 common classes. The membership change is 1.7.10 `CarpetBlock` vs 1.8 `BannerBlock`; `CarpetBlock` remains registered in both versions but loses its override in 1.8. `Block` declares the common base API. The scan found no additional public block collision provider class omitted from the existing collision slice inventory.

The inventory uses fully qualified relative class paths and exact declaration lines. Important multi-member entries include `Block` (both base methods), `MovingBlock` (ordinary and moved-block overloads), `LilyPadBlock` (both methods), and `PistonBaseBlock` (both methods). The companion TSV includes both per-file SHA-256 values for all entries, including absent-side rows.

The whole-tree scan also finds `Entity`’s entity bounding-box method, vehicle methods, and particle geometry. They are separate role classes, not block shape providers; vehicle/non-player collision geometry is outside this lane.

## Registration and lookup path

- 1.7.10 creates and registers block instances in `net/minecraft/block/Block.java` static setup via `REGISTRY.register(id, key, instance)`. Examples: piston base/head/moving block `:206-213`, stairs `:271`, ladder `:311`, carpet `:662`.
- 1.8 does the same in `net/minecraft/block/Block.java` via `register(id, key, instance)`. Examples: piston base/head/moving block `:827-834`, stairs `:868`, ladder `:885`, carpet `:1089`, standing/wall banners `:1103-1104`.
- `net/minecraft/block/Blocks.java` is a lookup facade, not the provider registration site. In 1.7.10 fields resolve registry keys directly (`MOVING_BLOCK` line 42, `CARPET` line 166); 1.8 resolves them in its static lookup block (`MOVING_BLOCK` line 45, `CARPET` line 187, `WALL_BANNER` line 200). In 1.8 `getOrThrow` also checks `Bootstrap.isInitialized()` before populating these aliases (line 203 onward).
- Abstract provider classes such as `AbstractRailBlock` and `AbstractPressurePlateBlock` are reached through registered concrete descendant types. Several shared provider classes have multiple registry instances (stairs, fences, fence gates, doors, slabs, panes); virtual dispatch remains through their concrete block object.

## Player query and dispatch graph

```text
Entity.move / sneak-edge support
  -> World.getCollisions(entity, sweptBox)
     -> scan candidate block positions and obtain block/state
     -> block.addCollisions(world, pos, state, box, list, entity) [virtual]
        -> Block.addCollisions gets virtual getCollisionShape
           -> add non-null shape only if it intersects the query box
     -> entity collision boxes are appended separately
```

- Main path: 1.7.10 `entity/Entity.java:379,457,503` -> `world/World.java:972`; block virtual dispatch at `World.java:992`, entity-box append at `:1002`. 1.8 `entity/Entity.java:367,438,468` -> `world/World.java:873`; block dispatch at `World.java:902`, entity-box append at `:913`. Sneak-edge support queries are 1.7.10 `Entity.java:413,425,435` and 1.8 `:396,408,418`.
- Base API/intersection gate: 1.7.10 `block/Block.java:836-847`; 1.8 `block/Block.java:292-303`. Both versions route block collision collection through `addCollisions`; providers override either it or `getCollisionShape`.
- Secondary block-only query: `World.getBlockCollisions`, 1.7.10 `world/World.java:1016-1048` (dispatch `:1036`), 1.8 `:948-980` (dispatch `:969`). Entity push-away calls it at 1.7.10 `entity/Entity.java:1420` and 1.8 `:1523`.
- Direct support classification: `World.isFullCube` calls `getCollisionShape` at 1.7.10 `world/World.java:1835` and 1.8 `:1694`.
- Moving piston route: `block/entity/MovingBlockEntity.java:87` invokes `Blocks.MOVING_BLOCK.getCollisionShape(...)` in both versions. `MovingBlock` reads the moving block entity and forwards its moved block/state, progress, and direction to the overload (1.7.10 `block/MovingBlock.java:102-143`; 1.8 `:118-164`). That overload asks the moved block’s provider for a shape.

## Structural moves and semantic changes

- Structural API change: provider calls move from `(World, int x, int y, int z)` in 1.7.10 to `(World, BlockPos, BlockState)` in 1.8. The caller still dispatches on the block instance via `addCollisions`; the base hook and virtual provider routing remain.
- Carpet semantic change: 1.7.10 `CarpetBlock.java:29` overrides its shape to a zero-height box; 1.8 has no override and inherits `Block.getCollisionShape`. Because the base collision gate requires intersection, the 1.7.10 degenerate box is rejected while the inherited 1/16-high 1.8 shape can be added. Carpet remains registered under the same key in both versions.
- Cake semantic change: `CakeBlock.getCollisionShape` is present at 1.7.10 line 41 and 1.8 line 43. The old top is 7/16; the new top is 1/2. The exact per-file hashes are in the inventory.
- Banner membership is a feature addition in 1.8 (`BannerBlock.java:33`; registrations at `Block.java:1103-1104`); it has no 1.7.10 counterpart and is not an old-era block candidate.
- Stairs remain an open shape detail: declaration and dispatch coverage is complete, but this lane does not establish branch-by-branch orientation equivalence.

## Registration and query file hashes

| Relative source file | 1.7.10 SHA-256 | 1.8 SHA-256 |
|---|---|---|
| `net/minecraft/block/Block.java` | `462EA8943470DE83F2D923D1D267BEBC6B9AF05EECF3C60C5E90EAE870394D0B` | `4A4E6DFDE24A135ECF87C359FD98F0380664E4BACAF679815C9016CB1BA0D814` |
| `net/minecraft/block/Blocks.java` | `B912E4C682F9E1FFECD916F3EE5CC6FF1BBA3196E0634E20442B618A0A062E11` | `AF42DC7D1FE95A3CC070427E32CD76213D6EAB4DF49118FEE99AA3C7A3A004B0` |
| `net/minecraft/world/World.java` | `2D648EDFE7FDC953825A1DF94709E889EA21354494D85762BCA49B4A109D01F9` | `AFBC701EF8B66020BDBB2831FD80683705D20237C68FBF23A4001C020876FC6D` |
| `net/minecraft/entity/Entity.java` | `AF4EF7B11BC709D96BD3EEA1260A61D38A1A04996BDF87F2A77C459DDF070A69` | `2BE081ABE1720F89D7AB90F782B8411E71BC5B8A6B53966B49A2380E3C47FFD9` |
| `net/minecraft/block/MovingBlock.java` | `6528C9C12CDAE609A023326F2AE764309F7E9CEE2D70E9A5C4D04E1638F553D7` | `614CC52B357152F6F515B33EA59440DFE338227EA0CE4E7DAC7E43A13A95BA7D` |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `2674E0EBF3075C357EB15D9865D7D374DE988ED7DC43D801547FE67132D49D58` | `37B472840FE4E9AC2427B293BF21EB0A3979D7C73137612DB1D048D40C891E4B` |

No runtime parity claim is made. This lane closes provider class/member enumeration and registration/query role tracing, not every provider state branch or downstream movement consumer.
