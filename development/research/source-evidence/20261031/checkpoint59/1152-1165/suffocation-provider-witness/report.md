# Suffocation provider witness: 1.15.2 → 1.16.5

## Disposition

**Witness found: a shared ShulkerBox block with an opened block entity.** Hold the block state fixed to a ShulkerBox facing EAST and use the corresponding vanilla block entity in `OPENED` status (`progress = 1.0F`) at the same position on both endpoints. The old `LocalPlayer.checkInBlock` path can treat that cell as blocked even though its collision shape is not the unit full-block shape. The 1.16.5 `suffocatesAt` filter rejects that same semantic block-entity state before its collision shape can count. This closes the provider dependency left in `closest-space-geometry/closure-report.md`; it does not address player positioning or escape direction.

## Exact witness and gates

- **1.15.2 predicate:** `world/level/block/ShulkerBoxBlock.java`, `isSuffocating(BlockState, BlockGetter, BlockPos)` (62–64) returns `true` unconditionally. `BlockState.isSuffocating` dispatches to that block method (`world/level/block/state/BlockState.java`, 268–270). Its `Blocks` registration uses `dynamicShape()` and does not replace the predicate (`world/level/block/Blocks.java`, 1563–1643; primary/default and dyed shulker variants all construct `ShulkerBoxBlock`).
- **1.15.2 geometry:** inherited `Block.getCollisionShape` returns `blockState.getShape(...)` when collision is enabled (`world/level/block/Block.java`, 391–393). `ShulkerBoxBlock.getShape` uses the resident `ShulkerBoxBlockEntity.getBoundingBox(blockState)` (`ShulkerBoxBlock.java`, 223–225). The entity box expands the unit box by `0.5F * getProgress(1.0F)` along its facing direction (`world/level/block/entity/ShulkerBoxBlockEntity.java`, 93–100). With facing EAST and `OPENED`/progress 1, the collision bounds extend 0.5 block beyond the block's east face, so the shape is not the unit full-block shape. The old unconditional override still returns true; it bypasses `Block.isSuffocating`'s default motion-material plus full-collision-shape test (`Block.java`, 285–287).
- **1.16.5 corresponding gate:** `Blocks.shulkerBox` assigns one predicate to every color (`world/level/block/Blocks.java`, 3224–3236). It returns true for no block entity, otherwise `ShulkerBoxBlockEntity.isClosed()` (`Blocks.java`, 3228–3232; `world/level/block/entity/ShulkerBoxBlockEntity.java`, 290–292). `OPENED` is not `CLOSED`, so the predicate is false. `LocalPlayer.suffocatesAt` passes `state.isSuffocating` as the predicate to `CollisionGetter.noBlockCollision`; the collision spliterator filters by that predicate before obtaining and testing a collision shape (`client/player/LocalPlayer.java`, 443–447; `world/level/CollisionGetter.java`, 62–68; `world/level/CollisionSpliterator.java`, 72–86). Thus the opened ShulkerBox's shape is excluded even when it intersects the query box.

The compared block-state properties are identical (ShulkerBox facing EAST); block-entity animation status is the additional geometry/predicate input. The source facts establish a provider witness and its old/new predicate result without relying on tags or assumed resource contents.

## Provider census used to narrow the result

The exact-source search for `isSuffocating` in block providers and `.isSuffocating(...)` in the 1.16.5 block registrations yielded this bounded roster:

- **1.15.2 method providers:** `Block` default; `AbstractGlassBlock` false; `LeavesBlock` false; `MovingPistonBlock` false; `PistonBaseBlock` true only when unextended; `ShulkerBoxBlock` unconditionally true. The default provider requires a full collision shape. Glass, leaves, and moving pistons therefore cannot witness true/non-full; extended piston bases return false and unextended bases have full shapes. ShulkerBox is the sole override with a true/non-full case.
- **1.16.5 providers:** `BlockBehaviour.State.isSuffocating` delegates to the configured state predicate, whose default still requires a full collision shape (`BlockBehaviour.java`, 634–636, 806–808). Registrations set `never` for glass, stained glass, moving piston and leaves; `always` for farmland, soul sand and grass path; a closed-or-missing-block-entity predicate for ShulkerBoxes; and an unextended predicate for piston bases (`Blocks.java`, 215–223, 342–350, 494–503, 734–741, 1965–1970, 3196–3248). The `always` partial-shape states do not witness the old predicate: their shared 1.15.2 registrations use the default full-shape rule. The ShulkerBox predicate specifically rejects the opened case that the old override accepts.

This census uses direct provider overrides plus the complete 1.16.5 suffocation-registration sites; it does not enumerate ordinary blocks governed solely by the default predicate, since that predicate itself excludes non-full shapes. No resource-tag membership is part of this result.

## Exact source identity

Canonical roots and pair pins: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` (parent manifest `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`) and `...\1.16.5\mojmap` (parent manifest `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`). `source-hashes.tsv` records SHA-256 for the cited provider, registration, and query sources. These files were read-only.

No implementation, player trajectory, test, build, launch, or decompilation was performed.
