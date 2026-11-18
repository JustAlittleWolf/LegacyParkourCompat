# Late geometry source verdict

Scope: ordinary dry stationary-state collision shape, step response, and support behavior for Scaffolding, Big Dripleaf (including its registered stem), Pointed Dripstone, Turtle Egg, and Chain across 1.20.2→1.20.3 and 1.20.3→1.20.4. This is a bounded source review, not pair-completion evidence.

## Evidence identity

- 1.20.2 Mojmap source manifest SHA-256: `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`.
- 1.20.3 Mojmap provenance SHA-256: `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`. Candidate and consumer source file hashes were checked against its embedded `sources` map.
- 1.20.4 Mojmap source manifest SHA-256: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`.
- The assigned 1.20.3 file set and exact Mojmap tree were present; every examined 1.20.3 source hash matched provenance. 1.20.2 and 1.20.4 hashes matched their pinned source manifests. Key files: `Blocks.java`, `BlockBehaviour.java`, `BlockState.java`, `LocalPlayer.java`, `Entity.java`, `BlockCollisions.java`, `CollisionGetter.java`, `CollisionContext.java`, `EntityCollisionContext.java`, `BlockStateProperties.java`, and each target block class.

## Per-edge verdicts

### 1.20.2→1.20.3

No change found to a legal ordinary block state's collision geometry, step-triggered state transition, or support predicate for these blocks. In the target block classes, 1.20.3 adds `MapCodec` constants and `codec()` methods. The selected `Blocks` registrations still construct the same block types directly with the same properties. This changes block codec/registry representation, not the runtime shape state or provider result.

Two neighboring source changes are outside this movement coverage: `PointedDripstoneBlock.onProjectileHit` adds `Projectile.mayBreak` before destroying a block (projectile-produced block removal), and `BigDripleafStemBlock.getCloneItemStack` changes the world parameter type from `BlockGetter` to `LevelReader` (clone-item interaction). Neither changes ordinary state legality, collision, step response, or support.

### 1.20.3→1.20.4

No relevant source change found. The six target classes, their registry entries, `BlockState`, `LocalPlayer`, collision-context consumer, and collision/support-provider bodies have matching source hashes or matching bodies across these endpoints.

## State outcomes checked

- **Scaffolding:** state schema is distance 0–7, waterlogged boolean, bottom boolean. Its outline provider returns stable/unstable shape unless the context holds scaffolding (then full cube). The player collision provider returns the stable deck plus four corner posts when above the full block and not descending; otherwise it returns the 2/16 bottom plate only when distance is nonzero, bottom is true, and context is above `BELOW_BLOCK`; else empty. Waterlogged does not change the shape. Survival and distance use a sturdy block below or neighboring scaffolding. These predicates and geometry match each version.
- **Big Dripleaf:** waterlogged boolean, four horizontal facings, and Tilt `NONE/UNSTABLE/PARTIAL/FULL`. Its movement collision provider is leaf-only: `NONE` and `UNSTABLE` are y=11..15/16, `PARTIAL` y=11..13/16, `FULL` empty; facing and waterlogging do not alter this leaf collision. Its outline shape additionally unions the facing-dependent stem portion. Server `entityInside` can start tilt only from `NONE` for a grounded entity above y+0.6875 with no neighbor signal; scheduled transitions and reset, and the stem's support/survival path, are unchanged. The separately registered Big Dripleaf Stem has `noCollission`; its directional shaft outline does not create player collision.
- **Pointed Dripstone:** state schema is vertical tip direction, all `DripstoneThickness` values, and waterlogged boolean. Shape selection remains: `TIP_MERGE` 6×6 full height; `TIP` 6×6 with its y=5..16 or y=0..11 segment selected by direction; `FRUSTUM` 8×8; `MIDDLE` 10×10; `BASE` 12×12. Existing XZ state offset is applied. Placement survival/support, thickness updates, and the `isCollisionShapeFullBlock=false` predicate are unchanged. Fall damage is excluded from this review.
- **Turtle Egg:** egg count 1–4 and hatch level 0–2. One egg uses x/z 3..12 and y 0..7; counts 2–4 use x/z 1..15 and y 0..7; hatch level does not affect shape. A non-careful step by a destroy-capable player may decrement the count server-side at 1/100; the non-zombie fall path uses 1/3. Both step/fall responses are unchanged and only count changes can switch the later shape.
- **Chain:** axis X/Y/Z chooses the same centered 3×3 rod spanning the block along that axis; waterlogging does not alter geometry. Its default collision/support provider and registered `forceSolidOn` property are unchanged; full-face support still comes from `SupportType` applied to the support shape, not the render-solid flag.

## Key source anchors

All paths are relative to `decompiled_minecraft/<version>/mojmap/`.

- Registrations in `net/minecraft/world/level/block/Blocks.java`: 1.20.2 `CHAIN` 2614, `TURTLE_EGG` 5438, `SCAFFOLDING` 6085, `POINTED_DRIPSTONE` 7141, `BIG_DRIPLEAF` 7245, `BIG_DRIPLEAF_STEM` 7256; 1.20.3 and 1.20.4 respectively 2606, 5376, 6015, 7309, 7413, 7424. Direct constructor/property expressions match across the three versions.
- `ScaffoldingBlock.java`: state/shape and collision provider 1.20.2 lines 31–53, 112–123, support distance 130–154, static shapes 157–168; 1.20.3/1.20.4 corresponding lines 33–60, 119–130, 137–161, 164–175.
- `BigDripleafBlock.java`: shapes/state and collision provider 1.20.2 lines 57–88, 245–267; 1.20.3/1.20.4 lines 59–95, 252–274; step tilt producer 1.20.2 lines 177–198 and 1.20.3/1.20.4 lines 184–205. `BigDripleafStemBlock.java`: state/shape/support 1.20.2 lines 28–80; 1.20.3/1.20.4 lines 30–88.
- `PointedDripstoneBlock.java`: state, support/update 1.20.2 lines 77–119 and shape 247–267; 1.20.3/1.20.4 lines 86–126 and 256–276. Projectile-only changed gate is 1.20.2 lines 123–128 versus 1.20.3/1.20.4 lines 130 onward.
- `TurtleEggBlock.java`: count/hatch state and step/fall 1.20.2 lines 34–70, shape 150–157; 1.20.3/1.20.4 lines 36–76, shape 157–164.
- `ChainBlock.java`: axes/default state and shape 1.20.2 lines 20–44, 1.20.3/1.20.4 lines 21–51. `BlockStateProperties.java`: EGGS/HATCH 1.20.2 lines 94–95 (1.20.3/1.20.4 95–96), distance 113 (114), and Tilt/vertical direction/thickness 128–130 (129–131).
- Client path: `LocalPlayer.move` calls `super.move` and its step-up probes use `CollisionContext.of(this)` at line 871 and 907–959 in all versions. `Entity.move`/`collide` and `stepOn`/`fallOn` are at 1.20.2 lines 595–660, 872–910, and fallOn dispatch 1110; 1.20.3/1.20.4 lines 596–661, 873–911, and fallOn dispatch 1111. `BlockCollisions` creates entity context at 32–33 and calls state `getCollisionShape` at 79 in all versions.
- Provider/support path: `BlockBehaviour.getBlockSupportShape` delegates to collision shape with empty context at 1.20.2 lines 245–246 and 1.20.3/1.20.4 lines 288–289; `getCollisionShape` default and `BlockState` forwarding remain equivalent (1.20.2 lines 290–292, 611–620; 1.20.3/1.20.4 lines 334–336, 654–663); `isFaceSturdy`/cache derive from `SupportType` at 1.20.2 lines 807–812, 862–887 and 1.20.3/1.20.4 lines 858–863, 913–938. `EntityCollisionContext.isAbove` threshold is unchanged at lines 62–64.

Limits: only the assigned five stationary block families, the directly related registered dripleaf stem, and ordinary LocalPlayer collision/step/support consumer paths were reviewed. This does not close the pair inventory or other block families/lanes.