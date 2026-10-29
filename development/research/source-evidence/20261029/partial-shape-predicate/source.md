# 1.21.2–1.21.4 partial-shape sweep alias closure

## Scope and conclusion

Source-only comparison of the accepted lead `development/research/findings/1.21.3--1.21.4/movement-partial-shape-sweep.md`. This closes only the partial entity-inside-shape sweep operation, its candidate enumeration and inputs, and the ordinary local-player and client-piston entry paths in exact 1.21.2, 1.21.3 and 1.21.4 Mojmap sources. It is not a full-pair freeze, runtime parity assessment, trajectory analysis, implementation decision, or claim about all movement coverage.

**Alias result:** every complete source file used by this bounded operation and its callers is byte-identical between 1.21.2 and 1.21.3. Thus the 1.21.2 outcome for this slice is exactly the 1.21.3 outcome; it does not create a new historical geometry variant. The checked 1.21.4 sources change the partial-shape test geometry, swept-cell endpoint construction, candidate bounds per movement, and movement-list snapshot timing. The End Portal shape/provider and callback body remain behaviorally the same across the checked releases.

## Source identity and integrity

All source was read from the assigned canonical roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.2\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.3\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.4\mojmap`

The 1.21.2 legacy `.decompile-provenance.json` has SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`; its embedded source map contains 5,654 paths. It binds the source inputs to version metadata URL `.../1.21.2.json` (SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`), client jar URL object `c7ac2d0d86f4ca416cab9064ff8a281852ad0c7b` (SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`), and `client.txt` mapping input (SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`). Its provenance records Vineflower 1.12.0 and mapping-io 0.9.1. This older tree has no `mojmap.ready.json`; the embedded per-source hashes were checked directly.

The 1.21.3 and 1.21.4 ready markers identify their exact version IDs and `mapping=mojmap`, report Vineflower, and match their actual source-manifest hashes: 1.21.3 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`; 1.21.4 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`. Their provenance records mapping-io 0.9.1 and Vineflower 1.12.0. Each file hash below was recomputed and matched the corresponding 1.21.2 embedded source record or 1.21.3/1.21.4 source-manifest entry.

| Source file | 1.21.2 SHA-256 | 1.21.3 SHA-256 | 1.21.4 SHA-256 |
| --- | --- | --- | --- |
| `world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | same | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` |
| `world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | same | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` |
| `client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | same | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` |
| `world/level/BlockGetter.java` | `5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39` | same | `8bd936e3ecac5720a36b675f4910801e9d201e899b34c316c6d0be9a12d4da20` |
| `world/level/block/state/BlockBehaviour.java` | `4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133` | same | `a549c5dcf7768f89f18519bdd2f985ef498d1bd4f6b329a30e0088e437819ddf` |
| `world/level/block/EndPortalBlock.java` | `725bbf4f36daa1f57f343d3f3123a6c527f13d7bf3c51fba1b27515b65c8c863` | same | `9f364e00f466e7cb10da6e5e3ab4719f32940529f18f5a5b9b30fcd1543cbfb9` |
| `world/level/block/TripWireBlock.java` | `8fc4647ed98d48434dc3ac10590507156b62fce924d2d7fd28ea40cf9f466012` | same | same |
| `world/level/block/piston/PistonMovingBlockEntity.java` | `426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16` | same | same |
| `world/phys/AABB.java` | `1990e0e1058e9ac5201c32e51c6a5f9b671e7ed00ba863aacf2c5b7b0adff217` | same | same |
| `world/phys/shapes/VoxelShape.java` | `440519ae483df99d05f1d2be8c3e245055b30bb5b676230b70b7be01dc9dea96` | same | same |
| `world/entity/EntityDimensions.java` | `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4` | same | same |

## Compared operation and boundary

`Entity.applyEffectsFromBlocks()` calls its vector overload with `(oldPosition(), position)` in all three versions (`Entity.java:736–767` in 1.21.2/1.21.3; `:747–779` in 1.21.4). The overload first requires `isAffectedByBlocks()` (`!isRemoved() && !noPhysics`), performs `stepOn` when grounded, appends the movement, then checks recorded movements. In 1.21.2/1.21.3 it checks the live list and clears it after the check; 1.21.4 copies the list and clears the live list before checking the copy. Therefore the geometry receives each recorded `from`/`to` pair in list order, and the 1.21.4 entry snapshot is a separate input-lifecycle change.

`Entity.checkInsideBlocks` admits non-air candidate states once per packed block position per normal check. It obtains `state.getEntityInsideCollisionShape(level,pos)`. The default `BlockBehaviour.getEntityInsideCollisionShape` returns the `Shapes.block()` singleton (all versions, `BlockBehaviour.java:353–355`), which takes the direct callback path without calling the partial-shape sweep. For any other returned shape, the callback continues only when `collidedWithShapeMovingFrom(from,to,pos,shape)` returns true; the shape is translated by the block position and expanded through `VoxelShape.toAabbs()`.

The complete 1.21.2 and 1.21.3 `Entity.java` files are identical. Their `collidedWithShapeMovingFrom` body (`Entity.java:1080–1084`) uses the current final entity box, recenters it by subtracting its center, moves that recentered box to `to`, then forms a reverse vector from `from` to that box's bottom center. It tests the current final `getBoundingBox()` along that vector against translated shape AABBs. This is the accepted lead's recentered-final-box/reverse-displacement form.

In 1.21.4 (`Entity.java:1092–1096`), the helper instead uses `makeBoundingBox(from)` and `to.subtract(from)`, then tests that start-position box along the forward segment against the same translated-shape AABBs. `makeBoundingBox(Vec3)` delegates to `EntityDimensions.makeBoundingBox(Vec3)`. The `AABB.collidedAlongVector` implementation is byte-identical in all three files (`AABB.java:390–406`): it checks the start and end centers within each shape AABB inflated by half the entity-box extents, then checks `clip(startCenter,endCenter)`.

The candidate filter is a separate but coupled boundary. `Entity.checkInsideBlocks` computes one `getBoundingBox().deflate(1.0E-5F)` before the movement loop in 1.21.2/1.21.3 (`Entity.java:1038–1078`); 1.21.4 computes `makeBoundingBox(to).deflate(1.0E-5F)` for each movement (`:1050–1090`). `BlockGetter.boxTraverseBlocks` keeps the same short-displacement test (`delta.lengthSqr() < Mth.square(0.99999F)`) and ordered linked-set/AABB-cell union in all versions. For longer segments, 1.21.2/1.21.3 use normalized `delta * 1.0E-7` to offset both traversal endpoints; 1.21.4 uses `AABB.min` and `AABB.min - delta` without epsilon offsets (`BlockGetter.java:172–190` in 1.21.2/1.21.3; `:172–189` in 1.21.4; its unchanged `addCollisionsAlongTravel` helper is at `:192–248` in 1.21.2/1.21.3 and `:191–247` in 1.21.4). `addCollisionsAlongTravel` itself is unchanged. Candidate-order or admission outcomes remain coordinate-dependent; no trajectory is inferred.

## Applicable shape and caller gates

A whole-source search of the three block trees found two block overrides of `getEntityInsideCollisionShape`: `EndPortalBlock` and `TripWireBlock`; the other occurrence is the base provider and state delegate. `EndPortalBlock.SHAPE` is `Block.box(0,6,0,16,12,16)` (`EndPortalBlock.java:31`), and its provider returns the block state's shape (`:53–55`). The block's `entityInside` calls `setAsInsidePortal` when `canUsePortal(false)` is true, except for the server-End credits branch (`:58–64`); the same relevant shape and callback lines are present in all three versions. This is a reachable partial-shape callback for a locally controlled player. `TripWireBlock` also returns its state shape (`TripWireBlock.java:137–139`), but its callback only acts when `!level.isClientSide` (`:142–147`), so this does not add a local-client player response in this closure. The full-block sentinel shortcut and these provider overrides are unchanged across the alias boundary.

Ordinary local-player entry: `LocalPlayer.tick()` invokes `super.tick()` in all three versions (1.21.2/1.21.3 `LocalPlayer.java:189–192`; 1.21.4 `:191–196`). `LivingEntity.tick()` calls `aiStep`; `LocalPlayer.aiStep()` delegates to `super.aiStep`; the post-travel `LivingEntity.aiStep` call to `applyEffectsFromBlocks()` is guarded by `!level.isClientSide() || isControlledByLocalInstance()` (1.21.2/1.21.3 `LivingEntity.java:2774–2776`; 1.21.4 `:2785–2787`). `LocalPlayer.isLocalPlayer()` and `isEffectiveAi()` return true on each side, so the local client passes that gate. The gate and these predicates are byte-identical between 1.21.2 and 1.21.3; the cited behavior remains in 1.21.4.

Piston entry: while piston progress is below 1, `PistonMovingBlockEntity.tick` calls `moveCollidedEntities`; this branch has no logical-side condition (`PistonMovingBlockEntity.java:286–319`, identical files). The actual push helper sets piston noclip, calls `entity.move(MoverType.PISTON, delta)`, then `entity.applyEffectsFromBlocks()`, and clears noclip (`:182–187`). Thus a local player selected for a piston push can enter the same callback scan on the client. The no-argument scan inputs remain `oldPosition()` to current `position`, rather than an explicit piston pre-push point. This closure makes no claim about concrete repeated callbacks or movement outcomes.

## Applicability limits

The operation is used by many entity paths; only the ordinary locally controlled player and piston entry paths above establish player reachability here. The separate server packet `recordMovementThroughBlocks` callers are server-side and are not a local-player input source. The result establishes exact source aliasing for 1.21.2 vs 1.21.3 and an exact 1.21.4 source boundary for the checked slices. It does not establish patch history beyond those releases, complete movement coverage, candidate outcomes for all coordinates, runtime parity, or a product selector decision.

