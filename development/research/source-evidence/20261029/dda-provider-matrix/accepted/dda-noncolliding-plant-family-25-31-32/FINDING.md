# 1.8.9 IDs 30, 31, and 32: web and short vegetation collision providers

## Scope correction

The assigned numeric list said ID 25 for cobweb. Exact 1.8.9 `net/minecraft/block/Block.java:878` registers ID 25 as `noteblock`; `:883-885` register ID 30 as `web`, ID 31 as `tallgrass`, and ID 32 as `deadbush`. This report therefore covers the intended web/vegetation family at IDs 30/31/32. ID 25 is excluded because it is a note block, not a member of this provider family.

## Exact 1.8.9 providers and states

- ID 30 is `web`/`CobwebBlock` (`Block.java:883`; `Blocks.java:247`). `CobwebBlock.getCollisionShape` returns `null` (`CobwebBlock.java:32-34`). It does not override the constructor's default full-block outline bounds (`Block.java:201`); the effective old ray outline is the unit cube.
- ID 31 is one `TallPlantBlock` with `TYPE`: metadata 0 `DEAD_BUSH`, 1 `GRASS` (`tall_grass`), and 2 `FERN` (`TallPlantBlock.java:25-29,118-120,137-160`). Invalid metadata indexes normalize to ID 0 (`TallPlantBlock.java:159-161`). Its constructor sets the shared block-instance bounds to x/z `[0.5F - 0.4F, 0.5F + 0.4F]`, y `[0.0F,0.8F]` (`TallPlantBlock.java:25-29`).
- ID 32 is `deadbush`/`DeadBushBlock` (`Block.java:885`; `Blocks.java:249`). It uses the same x/z `[0.5F - 0.4F, 0.5F + 0.4F]`, y `[0.0F,0.8F]` bounds (`DeadBushBlock.java:16-21`).
- `PlantBlock.getCollisionShape` returns `null` (`PlantBlock.java:61-64`), so IDs 31 and 32 have no old movement collision. `CobwebBlock` independently returns null as above.
- The base `Block.rayTrace` calls `updateShape` before using the instance min/max bounds (`Block.java:456-462`). `Block.updateShape` is empty (`Block.java:596-597`); these classes set their bounds in construction and do not rewrite them per ray query. Thus the old outline/ray box for ID 31 (all three metadata types) and ID 32 is the same 0.8-by-0.8-by-0.8 box; ID 30 retains the default unit box. This is only a per-block provider fact, not a traversal or visitation claim.

## Current 26.2 identities and providers

The block registrations and `BlockItemIds` establish current `cobweb`, `short_grass`, `fern`, and `dead_bush` keys (`Blocks.java:716-760`; `references/BlockItemIds.java:197-203`). The exact legacy-state conversion table maps old `web` to `cobweb`, `tallgrass{type=dead_bush}` to `dead_bush`, `tallgrass{type=tall_grass}` to `grass`, `tallgrass{type=fern}` to `fern`, and `deadbush` to `dead_bush` (`util/datafix/fixes/BlockStateData.java:1104-1111`). DataFixers later renames `grass` to `short_grass` (`util/datafix/DataFixers.java:1372-1374`). The item flattening table independently maps old `tallgrass.0/.1/.2` to `dead_bush`/`grass`/`fern` and `deadbush.0` to `dead_bush` (`util/datafix/fixes/ItemStackTheFlatteningFix.java:217-220`). Consequently old ID 31 metadata 0 and old ID 32 converge on current `dead_bush`; ID 31 metadata 1 maps to `short_grass`, and metadata 2 maps to `fern`.

All four current registrations explicitly use `.noCollision()` (`Blocks.java:716-760`). `BlockBehaviour.Properties.noCollision` clears the `hasCollision` flag (`BlockBehaviour.java:1094-1097`) and base collision-shape lookup returns `Shapes.empty()` when that flag is false (`BlockBehaviour.java:327-329`). This matches the old providers' lack of movement collision at the provider level.

Current outline shapes differ for the plant-like states: `TallGrassBlock` (used by `SHORT_GRASS` and `FERN`) and `DryVegetationBlock` (used by `DEAD_BUSH`) return `Block.column(12.0, 0.0, 13.0)` (`TallGrassBlock.java:17,29-30`; `DryVegetationBlock.java:17,29-30`). `Block.column` centers the 12/16 X/Z width and uses Y bounds 0..13/16 (`Block.java:177-185`). Old tallgrass/deadbush ray bounds use x/z `0.5F ± 0.4F` and y 0..0.8, so the old and current outline providers are not identical. Modern cobweb has no `getShape` override (`WebBlock.java`); base `BlockBehaviour.getShape` returns `Shapes.block()` (`BlockBehaviour.java:323-324`), preserving the old unit outline even though its collision shape is empty.

## Bounded conclusion

For these exact aliases, old and current movement collision providers both yield no colliding shape. Their old ray/outline boxes are construction-time instance bounds and are not updated by these blocks during ray queries. The outline provider changes for short grass, fern, and dead bush; no claim is made here about traversal, visitation order, first-hit behavior, or complete DDA parity.
