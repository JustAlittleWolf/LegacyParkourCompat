# Independent fluid-contact source audit: 1.13.2 → 1.14.4

## Audit boundary and evidence identity

Audited only the permitted fluid-contact closure report and exact canonical Feather Gen2 build 2 source roots. No product, wiki, backlog, history, or other lane material was used. No build, test, launch, source generation, decompile, or canonical source write was performed.

The whole-file SHA-256 values reported for the sampled `Entity.java`, `LivingEntity.java`, `FlowingFluid.java`, `LiquidBlock.java`, `WorldView.java`, `World.java`, and `ClientWorld.java` files match the files under both assigned roots. Independent method-hash checks matched the report for `Entity.checkBlockCollisions` (same SHA-256 on both sides) and both `FlowingFluid.getFlow` methods. The `checkWaterState` equal-hash claim is reproduced in the supplied report but was not independently recomputed here.

## Verdicts by central claim

### A. Lava direct query becomes a reset/callback cache — supported

- 1.13.2 `Entity.isInLava()` directly calls `World.containsMaterial(getShape().contract(0.1F, 0.4F, 0.1F), Material.LAVA)` (`Entity.java:1053–1055`). The supplied report’s account of integer cell/material enumeration is consistent with `World.containsMaterial` (`World.java:1310–1332`).
- 1.14.4 `Entity.isInLava()` reads the boolean cache (`Entity.java:1058–1060`). `Entity.move` resets it immediately before collision scanning (`Entity.java:540–548`); `checkBlockCollisions` checks the post-resolution entity box with a 0.001 inset, applies `isAreaLoaded`, and invokes block-state `onEntityCollision` before entity `onBlockCollision` (`Entity.java:797–826`). `LiquidBlock.onEntityCollision` sets the cache for lava-tagged fluid (`LiquidBlock.java:168–172`). The inspected old `LiquidBlock` has no such override.
- The tick and travel consumers cited in the supplied report support its timing description: lava damage is read from `baseTick` after water-state refresh (`Entity.java:394–415` old; `:357–378` new); `moveRelative` and the jump branch also read `isInLava` at the cited `LivingEntity` ranges. Thus the new value is reset/rebuilt on a completed move and is consumed from that cache until a later move; this differs from the old repeated shape/material query.
- Qualification: the report is right to reject an “only the extra 0.001 margin” explanation. The old deliberately contracted shape and new inset callback scan have different extents and selection logic. The callback scan’s unloaded-area early-out can leave the just-reset flag false. An exact ordinary-player entry/exit trajectory is not demonstrated, as the report itself limits.

### B. Body and eye fluid contact; stacked context height — supported, with partial witness closure

- The paired body-fluid methods have the same scan bounds (`getShape().contract(0.001)`; floor/ceil; exclusive x/y/z loops), area check, fluid-tag test, immersion comparison, max-depth tracking, flow accumulation, normalization rules and 0.014 impulse. The inspected methods differ at the contextual height call and loaded-area API (`Entity.java:2509–2571` old; `:2563–2622` new). The reported per-cell `d < 0.4` scaling uses the running maximum depth `d`, not a separately computed depth for the current cell; that is what both source bodies actually do, and their operation order matches.
- The stacked-height claim is supported: old `FlowingFluid.getHeight(state)` returns `state.getLevel()/9.0F` (`FlowingFluid.java:447–450`); new `getHeight(state, world, pos)` returns 1.0 when the same fluid type occupies `pos.up()`, otherwise delegates to raw level height (`:442–454`). Body scan uses that contextual overload only in the new source (`Entity.java:2588`), so lower-face contact can differ in the stated vertical interval.
- Eye contact also uses raw context-free height in the old predicate and contextual height in the new predicate (`Entity.java:1042–1051` old; `:1039–1052` new). The report correctly limits the level-8 unstacked case and identifies lower-level stacked fluid as the meaningful broadened case. Neither this audit nor the supplied closure proves a specific ordinary-player eye-position trajectory.

### C. Flow-vector arithmetic — supported as materially equivalent

`FlowingFluid.getFlow` preserves horizontal direction iteration, neighboring-height gradients, falling-fluid wall response, constants, and final normalization (`FlowingFluid.java:54–99` old; `:54–100` new). The renamed new accessor `m_10879879()` is the raw level-derived height method (`FlowingFluid.java:452–454`), distinct from the contextual stacked-height overload. The extra local `BlockPos` in the new body does not alter the inspected arithmetic/order. The entity-side accumulation/impulse bodies match in the inspected non-contextual work except for the body-contact height and loaded-area call already noted.

### D. Loaded-area and query behavior — supported within stated client scope

The old body scan calls the `allowEmpty` area overload with `true`; the old `WorldView` traverses covered X/Z chunks and the client `isChunkLoaded` immediately returns true when `allowEmpty` is true (`Entity.java:2517`; `WorldView.java:308–327`; `ClientWorld.java:112–114`). The new client `isChunkLoaded` returns true (`ClientWorld.java:221–224`), so the body scan’s new area check also passes on the client. The report appropriately excludes server chunk implementation effects from this movement witness.

The report’s separation of the lava direct query from the area-loaded body-fluid check is sound: `World.containsMaterial` has no corresponding area-loaded precheck, while `Entity.checkBlockCollisions` has its own post-move loaded-area guard. The callback loop hash matches at both endpoints, so the lava change is in flag reset/writer and predicate storage, not a changed collision-dispatch loop.

### E. Water gravity sign snapshot — source-order difference supported; behavioral claim needs qualification

The new `moveRelative` captures `bl = velocityY <= 0.0` before travel/update (`LivingEntity.java:1786–1792`), while old water movement checks `velocityY <= 0.0` after `move` and 0.8 vertical damping (`:1639–1649`); the new captured boolean is later used after movement/damping (`:1911–1921`). This is a real order change.

However, a sign change alone does not establish a differing gravity-snap outcome. The snap conjunction also tests `abs(vY - 0.005) >= 0.003` and `abs(vY - d/16) < 0.003`. With the default `d = 0.08`, `d/16` equals the 0.005 literal, making those two tests mutually exclusive. The snap branch is reachable only under another `d` value, notably the `d = 0.01` slow-falling case set in the same method. Any behavioral divergence therefore also needs the water branch, slow-falling value, `!isNoGravity`, `!isSprinting`, and a movement/collision outcome that changes the sign snapshot as relevant. The supplied report calls this a possible threshold path and explicitly says no trajectory is closed; keep it partial and state the slow-falling gate if using it as a candidate movement difference.

## Overall result

The core lava, stacked body/eye height, flow-vector, and client loaded-area claims are source-supported within their stated limits. The water sign/order observation is source-supported, but its movement consequence is narrower than an unconditional sign-crossing summary: the relevant snap condition is impossible with the ordinary 0.08 gravity parameter and depends on the slow-falling branch. No complete player witness, pair coverage audit, freeze, or implementation decision is supported by this focused audit.

## Exact source references

All paths are relative to `decompiled_minecraft/<version>/ornithe-feather/`:

- `net/minecraft/entity/Entity.java`: old lava query 1053–1055, body-fluid scan 2509–2571, eye predicate 1042–1051, `baseTick` lava consumer 394–415; new reset 540–548, collision scan 797–826, body-fluid scan 2563–2622, eye predicate 1039–1052, lava setter/predicate 1054–1060, `baseTick` consumer 357–378.
- `net/minecraft/entity/living/LivingEntity.java`: old movement/lava consumers 1478–1488, water gravity branch 1639–1649, jump lava consumer 1903–1914; new sign capture 1786–1792, water gravity branch 1911–1921, jump lava consumer 2205–2215.
- `net/minecraft/fluid/FlowingFluid.java`: flow 54–99 / 54–100; old height 447–450; new contextual/raw height 442–454.
- `net/minecraft/block/LiquidBlock.java`: new lava collision callback 168–172; old file has no matching callback.
- `net/minecraft/world/World.java`: old/new `containsMaterial` 1310–1332 / 711–720.
- `net/minecraft/world/WorldView.java`, `net/minecraft/client/world/ClientWorld.java`, `net/minecraft/world/WorldAccess.java`, `net/minecraft/world/chunk/ChunkSource.java`: loaded-area chain at the ranges listed above.
