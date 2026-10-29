# Mounted and facing block provider source closure (1.8.9 → 26.2)

## Scope and result

This source-only report closes registry identities, state inputs, old per-block movement collision providers, legacy mutable ray-bound writers, and current aliases/providers for IDs 68, 69, 144, 176, and 177. It does not claim DDA visitation, first-hit order, or overall ray-trace parity.

Canonical read-only roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.9\ornithe-feather` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated`. Exact selected Java file hashes and matching `.sources.sha256` entries are in `source-hashes.json`.

## Legacy registry and state projections

`1.8.9/ornithe-feather/net/minecraft/block/Block.java` registers ID 68 `wall_sign` (`WallSignBlock`), 69 `lever` (`LeverBlock`), 144 `skull` (`SkullBlock`), 176 `standing_banner` (`BannerBlock.Standing`), and 177 `wall_banner` (`BannerBlock.Wall`). These are five numeric identities but four provider classes (the two banner IDs share a class hierarchy with distinct state subclasses).

| ID | State decoded by the old class | Shape-relevant inputs |
|---|---|---|
| 68 wall sign | `WallSignBlock.getStateFromMetadata` uses `Direction.byId(metadata)` and coerces vertical directions to NORTH. | Horizontal FACING; metadata 2–5 give the four horizontal directions. |
| 69 lever | `metadata & 7` selects the eight `LeverBlock.Facing` values; bit 3 supplies POWERED. | Attachment/orientation and powered are represented by old FACING plus POWERED. `updateShape` reads FACING; POWERED does not affect the box. |
| 144 skull | `metadata & 7` selects Direction; bit 3 supplies NODROP. | FACING selects the old shape. The block entity separately stores `SkullType` and `Rot`; neither is read by old collision/ray shape methods. |
| 176 standing banner | Metadata becomes ROTATION (0–15). | ROTATION does not change the old bounds. Banner block entity stores `Base` and pattern data, not shape inputs. |
| 177 wall banner | `Direction.byId(metadata)`; vertical directions are coerced to NORTH. | Horizontal FACING selects the old bounds. Banner block entity color/pattern fields do not affect them. |

The exact legacy registrations are at `Block.java:968–969,1140,1209–1210`. Old metadata and shape methods are in `WallSignBlock.java:19–65`, `LeverBlock.java:121–212`, `SkullBlock.java:70–95,224–241`, and `BannerBlock.java:122–211`. `SkullBlockEntity` writes `SkullType` and `Rot`; `BannerBlockEntity` writes `Base` and `Patterns` (`block/entity/SkullBlockEntity.java:19–34`, `BannerBlockEntity.java:31–63`).

## Legacy movement collision and shared-bound behavior

- Wall signs inherit `SignBlock.getCollisionShape`, which returns null (`SignBlock.java:26–28`).
- Levers return null from their own `getCollisionShape` (`LeverBlock.java:28–31`).
- Both banner variants inherit `BannerBlock.getCollisionShape`, which returns null (`BannerBlock.java:39–42`).
- Skull is different: `SkullBlock.getCollisionShape` calls `updateShape(world,pos)` and then `super.getCollisionShape` (`SkullBlock.java:91–94`). The shape is refreshed from the queried FACING before the inherited six shared bounds are used for movement collision. The old shape is a centered half-width/half-height standing box; horizontal states use the corresponding half-width, middle-height, half-depth wall box (`SkullBlock.java:51,70–94`). The skull block entity type does not enter this shape method.

Legacy ray clipping uses `Block.rayTrace`, which calls virtual `updateShape(world,pos)` before clipping (`Block.java:456–459`). Consequently wall signs, levers, skulls, and wall banners update the shared instance bounds for the ray; standing banners use their constructor bounds because the standing subclass does not override `updateShape`. This report identifies provider writes only; it does not assert that sign, lever, or banner bounds are later consumed by movement, because their own movement collision providers return null. Skull’s movement getter explicitly refreshes its own state-dependent shape.

## Current alias and provider evidence

- **Wall sign ID 68:** 26.2 `DataFixers.java` v1802 explicitly renames `minecraft:wall_sign` to `minecraft:oak_wall_sign`; `Blocks.java` registers `OAK_WALL_SIGN` as `WallSignBlock` with `.noCollision()`. The legacy facing maps to the current horizontal FACING property. Current shape uses an orientation-keyed `VoxelShape` (`WallSignBlock.java:31,38–45`); the `.noCollision()` registration means the current movement provider is empty, matching the old null box. Other wood wall-sign variants are modern-only and are not aliases asserted here.
- **Lever ID 69:** `BlockItemIds.LEVER`, `Blocks.LEVER`, and `BlockStateData.java:3627–3659` keep the `lever` key and map legacy attachment/orientation and POWERED into current FACE/FACING/POWERED states. Modern `LeverBlock` builds a state-indexed shape with `Shapes.rotateAttachFace`, while `Blocks.LEVER` sets `.noCollision()` (`LeverBlock.java:46–59`, `Blocks.java:1788–1792`). Thus movement collision is empty on both ends. The visual/ray box is not asserted equal: old wall facing uses y=0.2–0.8, while the modern shape is derived from an 8/16-high centered primitive.
- **Skull ID 144:** `EntityBlockStateFix.java` identifies legacy name `minecraft:skull` with numeric ID 144. The 26.2 `ChunkPalettedStorageFix` maps the block-entity `SkullType` 0–5 to skeleton, wither-skeleton, zombie, player, creeper, and dragon skull/head families. For wall states it uses FACING; for standing states it uses block-entity ROT and produces 16 rotation states (`ChunkPalettedStorageFix.java:221–225,277–285,956–976`). Missing/unsupported keys fall back to the source-defined skeleton-wall-north mapping in that data-fix code; this is a conversion fallback, not a claim about a live missing block entity. Piglin skull is a current-only type. The current skull providers use state-derived standing or wall shapes (`SkullBlock.java:42–44`, `WallSkullBlock.java:23–36`); the old six skull types all share the old geometric formula. The 26.2 skull registrations do not set `.noCollision()`, so their nonempty shape is used for collision, like the old skull’s non-null box. No claim is made for current-only Piglin geometry.
- **Standing/wall banner IDs 176/177:** `ChunkPalettedStorageFix.java:268–274,599–607,1006–1023` converts old `Base` plus the old `rotation` (ID 176) or `facing` (ID 177) into color-specific standing/wall banner keys. Its map explicitly inverts the legacy base-color key (`15 - dyeIndex`) and includes all non-white colors; white states are represented by `BlockStateData.java:7773–7792`. Block patterns are not shape inputs. Modern `Blocks.BANNER` and `Blocks.WALL_BANNER` are color collections and set `.noCollision()` (`Blocks.java:3162–3184`), matching the legacy null movement box. Modern standing banner’s `Block.column(8,0,16)` and wall banner’s orientation-keyed `Block.boxZ(16,0,12.5,14,16)` correspond to the old standing 8/16-wide full-height bounds and wall 2/16-thick, 12.5/16-high bounds. This geometric comparison does not claim ray traversal or hit equivalence.

`BlockBehaviour.Properties.noCollision()` and the base collision-shape gate define the modern empty collision route (`BlockBehaviour.java:327–328,1094–1097`). Skull is intentionally excluded from that empty-provider conclusion: its modern registrations keep collision enabled and select a shape from state/type.

## Limits

This closure covers the listed registrations and source-defined mapping/provider details only. It does not close every historical state conversion path, prove what datafix runs for a particular live world, or establish the full cell visitation and hit behavior of either ray traversal. Old skull and banner variant conversion requires the described block-entity fields; a one-cell block-state lookup alone does not recover those inputs. No product code, tests, build, runtime, or game behavior was inspected or executed.
