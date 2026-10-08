# F-S3-02: 1.21.4 changes the movement-through-block shape sweep

- Older version A: 1.21.3
- Newer version B: 1.21.4
- Mechanic / coverage slice IDs: post-travel block callback traversal; S3-07
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.3, 1.21.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: artifact A in `run.md`; `build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/world/entity/Entity.java`, `Entity#applyEffectsFromBlocks(Vec3,Vec3)`, lines 740-784; `checkInsideBlocks()`, lines 1038-1079; `collidedWithShapeMovingFrom()`, lines 1080-1083; `setAsInsidePortal()`, lines 2214-2224; SHA-256 `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`. A traverses cells with the final entity box, then tests a recentered final box using a vector derived from the segment start.
- B manifest: artifact B in `run.md`; `Entity#applyEffectsFromBlocks(Vec3,Vec3)`, lines 751-797; `checkInsideBlocks()`, lines 1050-1091; `collidedWithShapeMovingFrom()`, lines 1092-1095; `setAsInsidePortal()`, lines 2242-2252; SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`. B snapshots and clears the recorded movement list before running block callbacks, computes a box at each segment endpoint, and sweeps a box starting at the segment's from-position along `to - from`.
- Cell traversal: A `build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/world/level/BlockGetter.java#boxTraverseBlocks()`, lines 172-190, SHA-256 `5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39`; B corresponding method, lines 172-189, SHA-256 `8bd936e3ecac5720a36b675f4910801e9d201e899b34c316c6d0be9a12d4da20`. A offsets the traversal start/end by a normalized `1.0E-7` vector; B begins at the supplied box minimum and subtracts the movement vector without that offset.
- Inside-shape default: A/B `BlockBehaviour#getEntityInsideCollisionShape()`, lines 353-355, SHA-256 `4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133` / `a549c5dcf7768f89f18519bdd2f985ef498d1bd4f6b329a30e0088e437819ddf`, returns `Shapes.block()`; EndPortal's explicit partial shape is covered by the following paired block source.
- Player call path: A `LivingEntity#aiStep()`, lines 2775-2777, SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72881cfc52`; B lines 2786-2788, SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`. The local player applies block effects after its movement/travel branch when it is locally controlled.
- Partial-shape callback example: A `EndPortalBlock#getShape()`, `getEntityInsideCollisionShape()` and `entityInside()`, lines 25-63, SHA-256 `725bbf4f36daa1f57f343d3f3123a6c527f13d7bf3c51fba1b27515b65c8c863`; B same bounded methods, same lines, SHA-256 `9f364e00f466e7cb10da6e5e3ab4719f32940529f18f5a5b9b30fcd1543cbfb9`. The block's entity-inside shape is a 6-to-12-pixel-high slab, and a callback hit calls `Entity.setAsInsidePortal()`, which updates the player's portal process. The default `BlockBehaviour#getEntityInsideCollisionShape()` is the full block on both releases; that full-shape fast path is not changed by the sweep helper.

## Source-level difference

B changes which cells are yielded near the movement path's grid boundaries and replaces A's recentered-final-box/reverse-vector test with a segment-start box and forward displacement for non-full inside shapes. The callback set can therefore differ when a recorded player segment passes a partial shape boundary, including the End Portal slab. The callback state changes on that path can affect later player portal processing and position.

## Reachability and dependencies

`LivingEntity.aiStep()` -> `Entity.applyEffectsFromBlocks(oldPosition, position)` records the local player's movement segment after travel. The block scan asks each candidate state for its entity-inside collision shape; full `Shapes.block()` cases call through the existing fast path, while partial shapes use the changed swept-box helper. When the End Portal partial shape is intersected and portal use is allowed, `EndPortalBlock.entityInside()` calls `setAsInsidePortal()`, writing the player portal process. The exact movement segment, endpoint, box height and shape must place the swept boxes on different sides of the partial-shape boundary for A/B to select different callbacks.

## Consequence and uncertainty

Source proves a changed block-cell traversal and changed swept-box geometry used by the reachable local-player callback path. It also proves that a hit on the End Portal shape can update the player's portal process. It does not establish which exact trajectories or portal dwell outcomes occur in runtime. Full collision-shape/resource inventory remains open. The exact release introduction is unknown within the endpoint interval.

## Handoff

This is one post-travel movement-through-block callback delta. Pair-level freeze and the complete collision/world-data audit remain open.
