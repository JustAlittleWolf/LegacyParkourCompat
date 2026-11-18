# Blind source audit: 1.21.8 to 1.21.9 water movement slice

## Evidence identity

- Pair: Minecraft 1.21.8 -> 1.21.9; exact decompiled client sources; Mojmap.
- 1.21.8 readiness: `decompiled_minecraft/1.21.8/mojmap.ready.json` says `status=ready`, `versionId=1.21.8`, `mapping=mojmap`, 6,105 source files. Client SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`; source-manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` (matched actual manifest file). `mojmap.provenance.json` identifies the exact decompile run and toolchain.
- 1.21.9: mapping-root `.decompile-provenance.json` identifies the source pipeline and records the exact client artifact (`ce92fd8d1b2460c41ceda07ae7b3fe863a80d045/client.jar`, SHA-256 `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`), version metadata (`.../1.21.9.json`, SHA-256 `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`) and mapped source hashes. Its mapping input is Mojang's client mapping text (`client.txt`, SHA-256 `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`). The exact version metadata identifies 1.21.9.
- Terminal source-file SHA-256 values, taken after review and before this frozen record was drafted:
  - 1.21.8 `net/minecraft/world/entity/Entity.java`: `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`
  - 1.21.8 `net/minecraft/world/entity/LivingEntity.java`: `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`
  - 1.21.8 `net/minecraft/client/player/LocalPlayer.java`: `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee`
  - 1.21.9 `net/minecraft/world/entity/Entity.java`: `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c`
  - 1.21.9 `net/minecraft/world/entity/LivingEntity.java`: `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`
  - 1.21.9 `net/minecraft/client/player/LocalPlayer.java`: `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`

## Frozen bounded inventory and verdicts

Roster consists of the three assigned independent operations below. Exact line ranges are inclusive; names are Mojmap. For each verdict, the methods and ordered operations were retraced in both releases. Verdict means no source difference in the listed bounded paths under the same inputs; it does not claim full-pair closure or runtime parity.

### 1. Ordinary player water body-height and eye-fluid prediction — no difference

Ordered path in both versions:

1. `Entity.tick()` -> `Entity.baseTick()` invokes `updateInWaterStateAndDoFluidPushing()` and then `updateFluidOnEyes()` (1.21.8 `Entity.java:468-493`; 1.21.9 `Entity.java:475-500`).
2. `updateInWaterStateAndDoFluidPushing()` clears `fluidHeight`, calls `updateInWaterStateAndDoWaterCurrentPushing()`, then samples lava; ordinary water body height is stored by `updateFluidHeightAndDoFluidPushing(FluidTags.WATER, 0.014)` (1.21.8 `Entity.java:1423-1444`; 1.21.9 `Entity.java:1478-1499`).
3. `updateFluidHeightAndDoFluidPushing` shrinks the box by `0.001`, floors minima/ceils maxima, visits x/y/z in ascending nested loops, takes max surface-minus-minY as body height, and writes the tag height; these ordered steps and expressions match (1.21.8 `Entity.java:3414-3488`; 1.21.9 `Entity.java:3480-3554`).
4. `updateFluidOnEyes()` first snapshots `wasEyeInWater` from the previous `fluidOnEyes` set, clears it, samples at `(x, eyeY, z)`, and adds fluid tags iff surface height is strictly greater than eyeY (1.21.8 `Entity.java:1446-1458`; 1.21.9 `Entity.java:1501-1513`). `isInWater()` returns `wasTouchingWater`; `isEyeInFluid(tag)` checks `fluidOnEyes`; `isUnderWater()` is `wasEyeInWater && isInWater()` on both sides (1.21.8 `Entity.java:1377-1395,1522-1524`; 1.21.9 `Entity.java:1428-1446,1577-1579`).

Same-input bound: ordinary local player, no vehicle, same bounding box/position/eyeY and same world fluid states and tags; compare the resulting water-height map, `wasTouchingWater`, `wasEyeInWater`, and eye-tag set. The selected methods preserve loop order, strict comparisons, sampling coordinates, previous-eye snapshot order, and writes.

### 2. Water flow sampling and push — no difference

Ordered path in both versions: `Entity.baseTick()` -> fluid-state update -> `updateInWaterStateAndDoWaterCurrentPushing()` -> `updateFluidHeightAndDoFluidPushing(WATER, 0.014)` -> each intersecting fluid cell's `FluidState.getHeight` and `getFlow` -> accumulate and average flow -> normalize only for non-`Player` -> multiply by push coefficient -> minimum-current adjustment when horizontal velocity is below `0.003` and current length below `0.0045000000000000005` -> add to current delta movement. Exact `Entity.java` ranges: 1.21.8 `3414-3488`; 1.21.9 `3480-3554`. The source bodies are identical, including nested x/y/z sampling order, `$9 < 0.4` scaling, player normalization exception, and constants.

Same-input bound: same ordinary-player classification, box, matching tagged fluid cells and their heights/flow vectors, push coefficient, and initial velocity. The listed method emits the same height result and movement addition in the same order.

### 3. First ordinary water travel move, drag, and vertical response — no difference

Ordered path in both versions: `LocalPlayer.aiStep()` calls `super.aiStep()`; `LivingEntity.aiStep()` reaches `travel(input)`; `LivingEntity.travel(Vec3)` selects fluid travel when touching water, affected by fluids, and not standing on the sampled fluid; `travelInFluid(Vec3)` takes the water branch. Relevant LocalPlayer handoff: 1.21.8 `LocalPlayer.java:683-830`; 1.21.9 `LocalPlayer.java:687-834` (the `super.aiStep()` handoff is at line 830 and 834 respectively). LivingEntity travel entry: 1.21.8 `2245-2253`, fluid body `2301-2353`; 1.21.9 `2275-2283`, fluid body `2331-2383`.

For ordinary water, both bodies calculate sprint/non-sprint drag, water movement efficiency and airborne halving, interpolate drag and acceleration, apply Dolphin's Grace override, call `moveRelative`, perform `move(SELF, deltaMovement)`, optionally replace vertical velocity with `0.2` on horizontal collision while climbable, multiply velocity by `(drag, 0.8F, drag)`, then call `getFluidFallingAdjustedMovement(gravity, wasNonpositiveY, velocity)`. The selected lines, literals, branch gates, operation order and writes match.

Same-input bound: same ordinary LocalPlayer input vector and touching-water state, initial position/velocity, sprint/ground/collision/climbable flags, water slowdown, movement-efficiency/speed attributes, effects, and effective gravity; same collision/move outcomes. This bounds the first water-travel call and its post-move velocity adjustment, not later tick branches.

## Exclusions and closure

Excluded as assigned: abilities-flight/run-sprint shallow-water exemption, other creative or Elytra movement, collision/fluid geometry changes, damage, riding, and later travel branches. This is a bounded independent audit only; it does not assert full-pair inventory closure, implementation acceptance, or runtime parity. No difference was found in the three listed operations.
