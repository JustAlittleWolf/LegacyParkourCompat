# F-01: Block contact callbacks follow accepted axis movement segments

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S4-CALLBACKS, S5-BLOCK-FACTORS, S5-FLUIDS
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: manifest `ready/1.21.4/mojmap.sources.sha256`; `net/minecraft/world/entity/Entity.java`; `net.minecraft.world.entity.Entity#move(MoverType, Vec3)` lines 627-712 (SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`) sets the final collided position directly. `Entity#applyEffectsFromBlocks()` / `(Vec3, Vec3)` lines 747-778 form the block-effect movement from the caller's endpoints and any externally recorded movements. `Entity#checkInsideBlocks(List<Movement>, Set<BlockState>)` lines 1050-1090 runs `entityInside` during traversal.
- B: manifest `ready/1.21.5/mojmap.sources.sha256`; `net/minecraft/world/entity/Entity.java`; `Entity#move(MoverType, Vec3)` lines 636-733 records one `Movement` for each nonzero axis in `axisStepOrder` before setting the final position. `Entity#applyEffectsFromBlocks()` / `(List<Movement>)` lines 768-815 replays those recorded segments and applies a `StepBasedCollector` after scanning. `Entity#checkInsideBlocks(List<Movement>, StepBasedCollector)` lines 1079-1122 advances the collector at each step, invokes block callbacks, and checks intersected fluid AABBs.
- B: manifest `ready/1.21.5/mojmap.sources.sha256`; `net/minecraft/world/entity/InsideBlockEffectApplier.java` (SHA-256 `ab7e6b651a2a97f8cdbb8b4b52084f9d44fd3d4ed3a1a202c41c2b0c4a7d2f11`) and `InsideBlockEffectType.java` (SHA-256 `2b3d7b27bc5242bc1394843a8c9ae227deb550c730d2a4c24dd3fb2b59df2d68`) define step-batched ordered effects. Relevant direct block movement callbacks remain in `HoneyBlock#entityInside` lines 63-71 (SHA-256 `c636983d5451a90164833f5b77e3c0933d7198f0f54f3d8b5fc46821fdf17170`) and `PowderSnowBlock#entityInside` lines 62-96 (SHA-256 `de72fb6e70616d7727bf324b0da4a98250cabd97ad39ecbe1062732d47f39770`).

## Source-level difference

For movement that changes more than one coordinate, 1.21.5 records the accepted displacement as sequential axis-aligned segments, using the collision result's axis order. The end-of-travel block scan checks each segment, so the traversed contact path can differ from 1.21.4's caller endpoint movement. Block callbacks still run during the scan in both versions, but 1.21.5 queues enumerated effects in movement-step order and applies them after scanning; 1.21.4 callback effects are immediate. The new fluid callback path schedules water extinguishing and lava ignition, while lava hurt is also scheduled after ignition; damage outcomes are outside this comparison's movement scope.

The same player travel reaches `LivingEntity.aiStep`, which calls `applyEffectsFromBlocks` after travel on the authoritative client or server path (A lines 2785-2787; B lines 2769-2771). `Entity.move` is called by player travel and supplies the B movement segments.

## Reachability and dependencies

Local player tick -> `LivingEntity.aiStep` -> `travel` / `travelRidden` -> `Entity.move` -> recorded movement -> `applyEffectsFromBlocks` -> `checkInsideBlocks` -> block callback. Directly movement-affecting callbacks include honey slide velocity adjustment and powder snow stuck movement multiplier. B additionally uses each block's entity-aware inside collision shape and the `BlockGetter.forEachBlockIntersectedBetween` ordering. Relevant block callbacks and collector types are identified above; source/resource inspection of all callback registrations and shape overrides remains open in the run ledger.

## Consequence and uncertainty

Source confirms the movement path representation, enumerated-effect ordering and added fluid-contact route. It therefore changes which player path segments are tested and when queued callback effects are committed. Direct callback code can still change movement state during the scan. Which individual contacts differ depends on the collision result, axis order, block shapes and callback. No specific changed trajectory has been measured. Health, fire damage and other damage outcomes are excluded. Server player corrections have separate movement recording callers and are not evidence for the local client path.

## Handoff

Independent delta: movement-path block/fluid contact traversal and ordered effect application changed in 1.21.5. Related finding IDs: F-02. Applicability is player movement through blocks with movement callbacks or intersected fluids. Exact first release within the pair is unknown.
