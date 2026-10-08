# Source-only movement comparison: Minecraft 1.21.4 to 1.21.5

- Status: active
- Scope: direct client player movement; older A = 1.21.4; newer B = 1.21.5.
- Track: source-only discovery; no wiki comparison, mod implementation inspection, or runtime implementation.
- Repository base: 002137b227676caea77f6832b9f4c8d0b6200bff (main); branch feat/source-discovery-movement-source-1-21-4-1-21-5; start date 2026-10-07.
- Source preparation owner: shared source preparer; published records and artifact identity below were reverified before source comparison.
- Namespace and CLI mode: Mojmap for both releases; exact alignment verified against each release's source marker, official mapping file and mapped jar.
- Discovery author: this task.
- Independent reviewer: pending coordinator assignment.

## Artifact manifest

Both exact releases have ready publications under `build/movement-campaign-2026-10-07/ready/`. Both source roots, cited source/artifact manifests and diagnostic signatures were checked before comparison. The worker reads those trees only.

- A 1.21.4: readiness and metadata IDs both `1.21.4`; mode/namespace `mojmap`; source root `build/movement-campaign-2026-10-07/ready/1.21.4/mojmap/`; client SHA-256 `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e`; official `client_mappings.txt` SHA-256 `48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23`; mapped `client-mojmap.jar` SHA-256 `56995548c9cb8bd7cdb9996b676daeafae02bde9d6ef4029b9eeca6bfd7dcc74`. Marker `ready/1.21.4/mojmap.ready.json`; source-manifest SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` (5,744 entries, verified); artifact-manifest SHA-256 `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` (89 entries, verified); diagnostics SHA-256 `fb5d4cfc7238ea634c47ad148e22539fec30a1b33bb5d3353ff8c34a3e44a421` (22 method rows, signatures verified at cited lines).
- B 1.21.5: readiness and metadata IDs both `1.21.5`; mode/namespace `mojmap`; source root `build/movement-campaign-2026-10-07/ready/1.21.5/mojmap/`; client SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`; official `client_mappings.txt` SHA-256 `3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5`; mapped `client-mojmap.jar` SHA-256 `124561e91a61714ca5a73495784c14c03a7a927d1f715eafbcb56ae115a6712a`. Marker `ready/1.21.5/mojmap.ready.json`; source-manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` (5,921 entries, verified); artifact-manifest SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656` (89 entries, verified); diagnostics SHA-256 `d43c0fdfc627492b043962c0379b744461ebbb6b2ffb24f326fe72a63a039755` (22 method rows, signatures verified at cited lines).
- Shared command: `gradlew decompileMinecraft --versions=1.21.1,1.21.3,1.21.4,1.21.5 --mappings=mojmap --decompiler-heap=4G --output-root=...\staging\mojmap-1.21.1-to-1.21.5-cd5a99cb1024417c9d370097c886a131 --cache-directory=...\artifacts`; full log: `build/movement-campaign-2026-10-07/staging/mojmap-1.21.1-to-1.21.5-cd5a99cb1024417c9d370097c886a131/gradle.full.log`. Toolchain: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; ASM 9.10.1; Gson 2.14.0. Generated source and artifacts are owned by the shared source preparer and remain read-only here.

## Blind-discovery freeze

- Status: pending; source comparison remains in progress.
- Freeze commit/checkpoint and timestamp: pending.
- Evidence inventory and finding IDs included at freeze: pending; F-01 through F-12 are current source-confirmed preliminary findings, and final evidence hashes will be recorded after coverage closes.
- Confirmation old mod implementation/code and isolated wiki-audit results were not opened before freeze: confirmed; source-navigation docs and an earlier source-discovery report were used only as navigation.
- Source/mapping hashes covered by freeze: pending.

## Correspondence and call order

Exact-source correspondence is in progress. The verified Mojmap pair resolves `LocalPlayer`, `ClientInput`, `KeyboardInput`, `Player`, `LivingEntity`, `Entity`, `BlockBehaviour`, `BlockGetter`, `CollisionGetter`, `BlockCollisions`, `BubbleColumnBlock`, `NetherPortalBlock`, `PowderSnowBlock`, `HoneyBlock`, `FluidState`, `WaterFluid` and `LavaFluid` through source members and caller paths. Confirmed call order: client `LocalPlayer.aiStep` samples input and sprint/jump state, calls `super.aiStep`; `LivingEntity.aiStep` performs velocity cutoff and input application, jump, travel dispatch, block effects, then post-travel work. The full tick graph, inherited dispatch, collision/pose writers, resources and external inputs remain open. Similar names and adjacent reports are not proof.

## Required source inventories


- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slices=S1-INPUT-SAMPLE, S1-INPUT-SCALE, S1-LOCAL-TICK, S3-DISPATCH, S3-GROUND, S3-AIR, S3-GRAVITY-DRAG, S3-JUMP-MATH, S3-SPRINT-MATH, S3-CLIMB, S3-WATER, S3-LAVA, S3-SWIM, S3-GLIDE, S3-POST; evidence=F-03, F-05, F-06, F-09, F-11 plus paired LocalPlayer/LivingEntity/Entity excerpts; remaining branch/member closure open.
- `INV-STATE` movement state writers/readers: status=pending; slices=S1-SPRINT-GATE, S1-SPRINT-TIMER, S1-JUMP-GATE, S1-SPRINT-JUMP, S1-FLIGHT, S1-RIDING, S2-POSE, S2-DIMENSIONS, S2-EYE-HEIGHT, S2-STATE-WRITERS; evidence=F-04 plus paired LocalPlayer/LivingEntity/Entity writers; timer, pose, dimensions and external writer closure open.
- `INV-COLLISION` collision/query path, shape providers, registrations, callbacks, neighboring-block dependencies: status=pending; slices=S4-COLLISION-QUERY, S4-AXIS, S4-STEP, S4-EDGE, S4-GROUND-SUPPORT, S4-SHAPES, S4-CALLBACKS, S5-BLOCK-SHAPES, S5-NEIGHBORS; evidence=F-01, F-07, F-08, F-10 plus paired Entity/Player/NetherPortalBlock/PowderSnowBlock/BlockBehaviour paths; full collision and shape-provider inventory open.
- `INV-WORLD-MOVEMENT` block/fluid properties, subclasses, registries, tags/data and resource-backed defaults: status=pending; slices=S5-BLOCK-SHAPES, S5-BLOCK-FACTORS, S5-NEIGHBORS, S5-FLUIDS; evidence=F-01, F-02, F-07, F-08 plus block/fluid callback excerpts; resource/tag/registry closure open.
- `INV-MODIFIERS` movement attributes/effects/enchantments/equipment and application/removal/conditions: status=pending; slices=S6-ATTRIBUTES, S6-EFFECTS, S6-ENCHANTMENTS, S6-EQUIPMENT; evidence=F-03/F-04 cite direct sneaking and sprint movement-attribute consumers; full producer/default and equipment/effect dependency inventory not started.
- `INV-EXTERNAL` player-only external movement inputs and client consumers: status=pending; slices=S7-CORRECTIONS, S7-PUSH, S7-PISTON-LAUNCH, S7-MOUNTS, S7-DEPENDENCIES; partial client correction/pitch and push/knockback formulas inspected (F-12; S7-PUSH), while other correction writers, impulse callers, piston and mount paths remain open.
- `INV-EXCLUSIONS` health/regen/hunger/food/saturation/exhaustion/damage/combat and non-player or vehicle movement: status=pending; evidence=scope contract and F-01 excludes fire/lava damage outcomes; direct sprint predicate reads remain in scope while producer systems are excluded. Full scope audit remains open.

## Coverage ledger

These are initial bounded navigation units. Exact sources are published; slice-by-slice closure and dependency follow-through remain in progress. Split further into exact method/body ranges where one unit exceeds the bounded member scope. Every terminal slice needs both-side evidence, state producers/writers and consumers/readers, parent/dependencies and reachability disposition.

### Slice S1-INPUT-SAMPLE: Input sampling and previous/current input timing

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `KeyboardInput.tick` sampling -> `LocalPlayer.aiStep` input tick -> A `serverAiStep` / B `applyInput` -> inherited `LivingEntity.aiStep`; order is partially verified and broader tick entry/exit closure remains open.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `KeyboardInput.java` lines 20-31; `ClientInput.java` lines 5-13; `LocalPlayer.java` lines 607-617, 653-678.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `KeyboardInput.java` lines 22-31; `ClientInput.java` lines 5-13; `LocalPlayer.java` lines 607-621, 693-703.
- State producers/writers -> consumers/readers: Options keys -> `KeyboardInput.keyPresses` and axes/vector -> `LocalPlayer.input` -> `xxa`/`zza`/jumping -> `LivingEntity.aiStep`/travel. `ClientPacketListener` input construction sites and only subclass checked on both sides.
- Parent slices / dependencies / closure evidence: S1-LOCAL-TICK; S1-INPUT-SCALE; S1-SPRINT-GATE; F-03. Full player input producer/consumer and previous/current coupling remains open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): local keyboard route verified; full tick input inventory remains open.
- Finding IDs or checked absence/replacement path: F-03


### Slice S1-INPUT-SCALE: Directional scaling, normalization, yaw-to-motion

- Inventory ID(s): INV-TICK, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: A raw float axes are item/sneak-scaled then `0.98F` multiplied before `Entity#getInputVector`; B keyboard float `Vec2.normalized`, local item/sneak scaling and square correction precede travel. On the controlled-camera path the `LocalPlayer.applyInput` override skips the base `0.98F` damping (F-09).
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `KeyboardInput.java` lines 20-31; `LocalPlayer.java` lines 668-678; `LivingEntity.java` lines 2766-2784; `Entity.java` lines 1429-1441.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `KeyboardInput.java` lines 22-31; `Vec2.java` lines 48-51; `LocalPlayer.java` lines 623-658; `LivingEntity.java` lines 2712-2717, 2806-2809; `Entity.java` lines 1458-1470.
- State producers/writers -> consumers/readers: keyboard booleans -> impulses (A) or `moveVector` (B) -> `xxa`/`zza` -> `Entity#getInputVector` -> travel acceleration. `Attributes.SNEAKING_SPEED` and item-use state scale the vector.
- Parent slices / dependencies / closure evidence: S1-INPUT-SAMPLE; S3-GROUND; S3-AIR; all fluid travel slices; S6-ATTRIBUTES; F-03.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): input normalization and math order differ for local diagonal keyboard movement; exact downstream rounding and travel dependencies remain open.
- Finding IDs or checked absence/replacement path: F-03, F-09

### Slice S1-LOCAL-TICK: Local tick order, superclass tick and pre-travel work

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local-player `aiStep` input tick and pre-travel state updates -> inherited `LivingEntity.aiStep` velocity cleanup, virtual input application, jump dispatch and travel; post-travel callbacks follow. A transfers local input through `serverAiStep`; B dispatches `applyInput` before jump/travel. Current order is compared; whole LocalPlayer pre/post tick and writer inventory remains open.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LocalPlayer.java` lines 643-809; `LivingEntity.java` lines 2691-2787.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LocalPlayer.java` lines 683-830; `LivingEntity.java` lines 2669-2771.
- State producers/writers -> consumers/readers: `LocalPlayer.input.tick()` updates key state; local pre-travel transitions update sprint, jump/flying and crouch state; `serverAiStep` (A) / `applyInput` (B) supplies axes and jumping; inherited `LivingEntity.aiStep` consumes these before `travel`. B also ticks equipment before velocity cleanup. Equipment and complete state-writer closure remain open.
- Parent slices / dependencies / closure evidence: S1-INPUT-SAMPLE; S1-INPUT-SCALE; S1-SPRINT-GATE; S1-SPRINT-TIMER; S1-FLIGHT; S3-DISPATCH; S3-POST; F-04; F-05; F-09.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): both local-player overrides and inherited tick ordering were inspected in exact paired sources. F-09 is reachable on a controlled-camera LocalPlayer because its B override supplies axes without calling base `applyInput`; complete pre-travel state and post-travel dependencies remain open.
- Finding IDs or checked absence/replacement path: F-04, F-05, F-09

### Slice S1-SPRINT-GATE: Sprint eligibility predicates and vanilla state reads

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): paired `LocalPlayer.aiStep`/start-stop predicates compared; food producer excluded, but full gates, consumer speed path and dependencies remain open.
- Finding IDs or checked absence/replacement path: F-04

### Slice S1-SPRINT-TIMER: Sprint start/stop state, windows and timers

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected

- Parent slices / dependencies / closure evidence: pending
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): timer writes and backward/shift/item-use reset predicates compared; full preceding/current input edge timing remains open.
- Finding IDs or checked absence/replacement path: F-04

### Slice S1-JUMP-GATE: Jump input, eligibility, cooldown and stored state

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S1-SPRINT-JUMP: Sprint-jump impulse and state writes

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S1-AUTOJUMP: Auto-jump probes, timing, normalization and trigger

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S1-FLIGHT: Flight input, toggles and movement speed state

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected

- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S1-RIDING: Player riding gates, jump charge and mount transition

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S2-POSE: Pose selection transitions and movement predicates

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S2-DIMENSIONS: Dimensions, box resize and collision rejection timing

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending


### Slice S2-EYE-HEIGHT: Eye height in fluid and collision checks

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S2-STATE-WRITERS: Movement flags, defaults, resets, timers and consumers

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S3-DISPATCH: Travel dispatch and branch guards

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: player tick selects `travelRidden` or ordinary `travel`; travel applies fluid-first, fall-flight-second, air otherwise. A guards inside `travel` with `isControlledByLocalInstance`; B guards the call in `aiStep` with `canSimulateMovement`. Local client-player authority resolves true on both paths. The climbable fall-flight sub-branch is a source finding.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity.java` lines 2189-2199 and 2767-2784; `LocalPlayer.java` lines 352-353 and 483-485.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity.java` lines 2213-2222 and 2758-2768; `Player.java` lines 1307-1327; `LocalPlayer.java` lines 348-350.
- State producers/writers -> consumers/readers: client/player local-authority state, controlling-passenger state, fluid presence/affectability/standability and fall-flying flag -> branch selection -> travel implementation and movement vector.
- Parent slices / dependencies / closure evidence: S1-LOCAL-TICK; S3-GROUND; S3-AIR; S3-WATER; S3-LAVA; S3-GLIDE; S3-CLIMB; S7-MOUNTS. A/B local-player authority overrides checked; ridden-player caller and all branch dependencies remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): for the local client player, A's effective-AI/local-player predicates and B's `canSimulateMovement`/authority predicates both enable ordinary travel. A and B keep the fluid-first branch order; B adds a climbable exit from active fall flying (F-06). Ridden and environment-specific paths remain open.
- Finding IDs or checked absence/replacement path: F-06

### Slice S3-GROUND: Ground acceleration, friction source and order

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: grounded `travelInAir` selects block friction, computes friction-influenced input speed, moves, applies post-collision climb/powder-snow upward clamp, then gravity and drag. B's powder-snow state predicate differs (F-11); formula/input dependencies remain open.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity.java` lines 2202-2221 and 2362-2372; `Entity.java` lines 867-878 and 1424-1441.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity.java` lines 2225-2244 and 2394-2404; `Entity.java` lines 915-926 and 1453-1470.
- State producers/writers -> consumers/readers: grounded flag and below-block friction -> friction-influenced movement speed and input acceleration; collision/jump plus climbable/powder-snow state -> vertical clamp; gravity/effect state -> vertical update; friction -> horizontal and vertical drag.
- Parent slices / dependencies / closure evidence: S3-DISPATCH; S3-AIR; S3-GRAVITY-DRAG; S4-COLLISION-QUERY; S5-BLOCK-FACTORS; S5-BLOCK-SHAPES; S6-ATTRIBUTES; S6-EFFECTS; F-11. Travel helper bodies inspected, but block values, collision, gravity and modifier inventories remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): grounded players reach this branch when ordinary air travel is selected. Formula order was inspected; F-11 identifies a changed powder-snow predicate before gravity/drag. Friction, attribute, effect and collision dependencies are not closed.
- Finding IDs or checked absence/replacement path: F-11

### Slice S3-AIR: Air acceleration, friction and speed attribute use

- Inventory ID(s): INV-TICK, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: airborne `travelInAir` uses friction `1.0F`, input acceleration from `getFrictionInfluencedSpeed`, same post-collision climb/powder-snow clamp, gravity and drag. B's powder-snow state predicate differs (F-11); other input and travel dependencies remain open.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity.java` lines 2202-2221 and 2362-2372; `Entity.java` lines 1424-1441.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity.java` lines 2225-2244 and 2394-2404; `Entity.java` lines 1453-1470.
- State producers/writers -> consumers/readers: on-ground false branch selects unit friction; input, speed/flying-speed attributes and yaw -> relative movement acceleration; collision/jump plus contact state -> vertical clamp; gravity and discard-friction state -> velocity update.
- Parent slices / dependencies / closure evidence: S3-DISPATCH; S3-GROUND; S3-GRAVITY-DRAG; S4-COLLISION-QUERY; S5-BLOCK-SHAPES; S6-ATTRIBUTES; S6-EFFECTS; F-03; F-09; F-11. Core formula bodies inspected; inputs and collision/contact dependencies remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): airborne players reach this branch under ordinary air travel. F-11 identifies the changed powder-snow state predicate; F-03 and F-09 document local input precision and damping deltas. Speed, gravity, and state producer inventories remain open.
- Finding IDs or checked absence/replacement path: F-03, F-09, F-11

### Slice S3-GRAVITY-DRAG: Gravity, drag, clamps and velocity cutoffs

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): player-specific horizontal velocity cutoff compared at `LivingEntity.aiStep`; complete gravity/drag dependencies remain open.
- Finding IDs or checked absence/replacement path: F-05

### Slice S3-JUMP-MATH: Jump power, vertical impulse and modifier terms

- Inventory ID(s): INV-TICK, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: `getJumpPower`/jump-boost addition -> `jumpFromGround` vertical max and sprint impulse; `jumpInLiquid` vertical addition. External attribute, block-factor and effect values remain input dependencies.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity.java` lines 2136-2160 and 2167-2169; `Entity.java` lines 867-871; `MobEffects.java` lines 55-61.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity.java` lines 2160-2184 and 2191-2193; `Entity.java` lines 915-919; `MobEffects.java` lines 55-61.
- State producers/writers -> consumers/readers: `Attributes.JUMP_STRENGTH`, block jump factor and jump-effect amplifier -> float jump power -> `max(jumpPower,currentY)`; sprint state and yaw -> horizontal `0.2` impulse; liquid jump call -> `+0.04F` vertical velocity.
- Parent slices / dependencies / closure evidence: S1-JUMP-GATE; S1-SPRINT-JUMP; S5-BLOCK-FACTORS; S6-ATTRIBUTES; S6-EFFECTS. Paired formulas and built-in jump-effect registration inspected; input state/default inventories remain separate and open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the jump-power multiplication/addition order, `1.0E-5F` guard, vertical `Math.max`, sprint trigonometry and `0.2` impulse, and liquid `0.04F` impulse match. A's `MobEffects.JUMP` and B's `JUMP_BOOST` both register `jump_boost` with the same effect body. `getBlockJumpFactor` also has the same two-block lookup and fallback. This closes the arithmetic formula slice for equal input values; jump eligibility and data/modifier production remain open dependencies.
- Finding IDs or checked absence/replacement path: checked absence of a jump-impulse formula delta for equal jump power, current velocity, sprint state, yaw and liquid-jump call.

### Slice S3-SPRINT-MATH: Sprint velocity inputs and arithmetic

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source

- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S3-CLIMB: Climb contact, velocity clamps and wall state

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: travel branch selection for fall flying on climbables plus shared air-travel post-collision vertical clamp for climbable/powder-snow contact. The glide exit and powder-snow state read are compared; full climbable predicates and block/resource inventory remain open.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity.java` lines 2189-2221 and 2279-2288, 2362-2372, 2390-2403; `Entity.java` lines 451-452, 3360-3365.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity.java` lines 2213-2245 and 2301-2320, 2394-2404, 2421-2434; `Entity.java` lines 459-460.
- State producers/writers -> consumers/readers: fall-flying flag + climbable predicate -> glide or ordinary air travel; horizontal-collision/jump state + current/prior powder-snow contact -> `0.2` vertical velocity clamp; PowderSnowBlock callback/inside-effect type writes current contact flag -> next tick `wasInPowderSnow` snapshot.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): F-06 records the new climbable fall-flight exit. F-11 records the powder-snow state representation change in the wall-jump clamp. Both are reachable through the local player's ordinary travel path; powder-snow walkability/equipment and complete block contact dependencies remain open.
- Finding IDs or checked absence/replacement path: F-06, F-11

### Slice S3-WATER: Water travel, drag, gravity and current

- Inventory ID(s): INV-TICK, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: water branch of `travelInFluid`: sprint/water slowdown and efficiency scalars -> relative acceleration -> movement -> climbable collision rise -> water drag/falling adjustment. External fluid contact/current production remains open.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity.java` lines 2227-2258, 2375-2392, 2184-2187; `Entity.java` lines 3323-3325.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity.java` lines 2249-2280, 2406-2423, 2208-2211; `Entity.java` lines 3321-3323.
- State producers/writers -> consumers/readers: water contact/height/current and affected-by-fluid state -> water branch; sprint, `WATER_MOVEMENT_EFFICIENCY`, ground state, speed, gravity and Dolphin's Grace -> acceleration/drag/falling adjustment -> `move` and velocity update.
- Parent slices / dependencies / closure evidence: S3-DISPATCH; S3-GRAVITY-DRAG; S3-SWIM; S5-FLUIDS; S6-ATTRIBUTES; S6-EFFECTS. Travel formula helpers inspected; fluid contact/current and modifier producers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): water branch arithmetic and helper order match in the paired sources for equal input values. Water-flow/current production and the complete attribute/effect state inventory remain open, so this branch slice is not terminal.
- Finding IDs or checked absence/replacement path: checked absence of a `travelInFluid` water-formula delta; dependencies remain open.

### Slice S3-LAVA: Lava travel, drag, gravity and current

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: lava branch of `travelInFluid`: fixed relative acceleration -> movement -> fluid-height/threshold choice of drag and falling adjustment -> quarter-gravity term -> collision/free-space upward move.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity.java` lines 2258-2277, 2375-2392, 2184-2187; `Entity.java` lines 3323-3325.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity.java` lines 2280-2299, 2406-2423, 2208-2211; `Entity.java` lines 3321-3323.
- State producers/writers -> consumers/readers: lava height/current and threshold plus gravity -> branch-specific drag and falling adjustment -> quarter-gravity and collision rise -> delta movement; the fluid contact/current producer remains open.
- Parent slices / dependencies / closure evidence: S3-DISPATCH; S3-GRAVITY-DRAG; S5-FLUIDS. Paired travel and falling-adjustment formulas inspected; fluid height/current producers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): lava branch arithmetic and helper order match in the paired sources for equal input values. Fluid contact/current and the full gravity path remain open, so this branch slice is not terminal.
- Finding IDs or checked absence/replacement path: checked absence of a `travelInFluid` lava-formula delta; dependencies remain open.

### Slice S3-SWIM: Swimming input/look vector and pose interaction


- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S3-GLIDE: Fall-flying travel and equipment gates

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): glide branch and climbable precondition compared; Elytra/equipment and all glide inputs remain open.
- Finding IDs or checked absence/replacement path: F-06

### Slice S3-POST: Post-travel fall-distance, collision and state writes

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S4-COLLISION-QUERY: Player collision query and candidate collection

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending

- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S4-AXIS: Axis order, collision tie-breaks and velocity cancellation

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `Entity.collideWithShapes` Y-first axis resolution, horizontal X/Z tie-break and AABB offset supplied to the next axis; exact ordered axis lists and `Shapes.collide` dependency checked.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `Entity.java` lines 993-1020; `Shapes.java` lines 204-215 (SHA-256 `ab6c69c27c07c3cfa82e85906888036c34a46188e1bde5663e980dea3f6c2a75`).
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `Entity.java` lines 1041-1060 and 159-160; `Shapes.java` lines 211-222 (SHA-256 `03cd390636f0253b881ddd36c70e61cca2956b02281d45df26c8038ebbdbf63a`).
- State producers/writers -> consumers/readers: requested `Vec3` and collected collision `VoxelShape` list -> per-axis `Shapes.collide` result -> offset AABB for subsequent horizontal axis -> resolved movement vector -> `Entity.move` position and collision flags.
- Parent slices / dependencies / closure evidence: S4-COLLISION-QUERY (shape list production remains open); S4-SHAPES (shape context remains open); A/B `Shapes.collide` method bodies inspected and equal.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for every input vector and ordered shape list, A applies Y then X/Z, choosing X first when `abs(x) >= abs(z)` and Z first otherwise; B selects the same YXZ/YZX sequences using the same strict comparison and offsets the AABB by prior resolved axes. Both use the same `Shapes.collide` implementation and `1.0E-7` cutoff. This bounded solver slice has no source behavior delta; collision-list providers and step alternatives are separate open slices.
- Finding IDs or checked absence/replacement path: checked absence of an axis-order/collision-tie-break delta in the paired method bodies; F-01 remains the separate movement-path callback delta.

### Slice S4-STEP: Step conditions, alternatives and tie-breaking

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `Entity.collide` step eligibility, downward-step base box, expanded candidate box, candidate-height enumeration/sort, horizontal-distance winner and returned vertical offset.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `Entity.java` lines 920-968; `Vec3.java` lines 104-105 (SHA-256 `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d`).
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `Entity.java` lines 968-1017; `Vec3.java` lines 104-105 (same SHA-256 `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d`).
- State producers/writers -> consumers/readers: requested movement and current on-ground/collision flags plus max step height -> candidate step-up heights from collision-shape Y coordinates -> collision solver horizontal-distance comparison -> returned movement vector consumed by `Entity.move`.
- Parent slices / dependencies / closure evidence: S4-COLLISION-QUERY and S4-SHAPES (collision shape providers remain open); S4-AXIS (axis solver checked separately); paired candidate-height helper bodies and `Vec3.subtract(double,double,double)` checked.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): step eligibility, candidate collection, float casts, strict/equality guards, unstable sort and horizontal-distance winner match. The sole expression rewrite returns `add(0,-offset,0)` in A and `subtract(0,offset,0)` in B; both versions of `Vec3.subtract` return `add(-x,-y,-z)`, so the selected vertical value and addition order match. This bounded step-choice slice has no source behavior delta; shape providers and movement callbacks remain separate open slices.
- Finding IDs or checked absence/replacement path: checked absence of a step eligibility/candidate/tie-break delta in the paired methods; F-01 remains the separate movement-path callback delta.

### Slice S4-EDGE: Edge support/sneak probes and bounded queries

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Entity.move` -> `Player.maybeBackOffFromEdge` gating -> `isAboveGround` -> `canFallAtLeast` AABB passed to `Level.noCollision` -> repeated 0.05 horizontal back-off.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `Player.java` lines 1069-1126; `Entity.java` line 647.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `Player.java` lines 1026-1085; `Entity.java` line 655.
- State producers/writers -> consumers/readers: movement vector and mover type plus flying/on-ground/fall-distance/max-step/surface state -> per-axis and combined support probes -> possibly reduced horizontal movement vector -> `Entity.collide`.
- Parent slices / dependencies / closure evidence: S4-COLLISION-QUERY; S4-SHAPES; S4-GROUND-SUPPORT; S5-BLOCK-SHAPES. Probe caller and AABB construction checked; collision shape/noCollision provider closure remains open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): exact paired player override and query inspected. B changes the query bounds and lower Y margin; source finding F-10 records the reachable guard and geometry. Underlying collision provider and all callers remain open.
- Finding IDs or checked absence/replacement path: F-10

### Slice S4-GROUND-SUPPORT: Ground/support lookup and collision flag updates

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source

- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S4-SHAPES: Shape context, box operations and repeated movement

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S4-CALLBACKS: Block/entity callbacks and invocation order

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Entity movement recording/contact scan and ordered effect collection compared; all callback families and direct movement callback closures remain open.
- Finding IDs or checked absence/replacement path: F-01, F-02, F-07, F-08

### Slice S5-BLOCK-SHAPES: Historical block shapes, providers and overrides

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Nether portal and powder snow inside-contact shape changes confirmed; full historical block shape provider set is not closed.
- Finding IDs or checked absence/replacement path: F-07, F-08


### Slice S5-BLOCK-FACTORS: Friction, speed/jump factors and contact behavior

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): bubble-column above-contact precondition and direct velocity update compared; other block contact/factor providers remain open.
- Finding IDs or checked absence/replacement path: F-02

### Slice S5-NEIGHBORS: Neighbor rules, properties and block registrations

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): bubble-column above-block predicate compared; all registered block and neighbor dependencies remain open.
- Finding IDs or checked absence/replacement path: F-02

### Slice S5-FLUIDS: Fluid data, height/flow, currents and pushes

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): new entity-fluid callback path observed; movement-specific fluid math/data and all water/lava branch closure remain open.
- Finding IDs or checked absence/replacement path: F-01

### Slice S6-ATTRIBUTES: Movement attributes, aggregation and travel consumers

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending

- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S6-EFFECTS: Direct movement effects, formulas, conditions and timing

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S6-ENCHANTMENTS: Movement enchantments, conditions and resource data

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S6-EQUIPMENT: Equipment/components, applicability and use slowdown

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S7-CORRECTIONS: Player corrections and position/velocity state writers

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: non-passenger local-player `ClientPacketListener.handleMovePlayer` -> relative position/rotation resolution -> direct `PositionMoveRotation.calculateAbsolute` result -> local-player position, pitch and velocity state; other correction callers and writers remain uninspected.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `ClientPacketListener.java` lines 707-715; `PositionMoveRotation.java` lines 35-63; `Entity.java` default `lerpTargetX/Y/Z` and `LocalPlayer.java` absence of overrides (finding F-12 includes member hashes).
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `ClientPacketListener.java` lines 720-728; `PositionMoveRotation.java` lines 34-62; `Entity.java` default `getInterpolation` and `LocalPlayer.java` absence of overrides (finding F-12 includes member hashes).
- State producers/writers -> consumers/readers: clientbound correction packet relative flags and current local-player position/rotation/velocity -> absolute correction state; corrected pitch is later read by fall-flying travel. Other correction routes and direct movement state writers remain open.
- Parent slices / dependencies / closure evidence: S3-GLIDE; S7-PUSH; S7-DEPENDENCIES; F-12. The handler's non-passenger guard and `false` direct-set path were checked; full packet/correction and external-writer inventory remains open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): for a non-passenger local player, the paired handler uses the direct-set path; B clamps the resolved absolute pitch to `[-90°, 90°]`, whereas A stores it unchanged. This is a confirmed source delta, but this slice is not terminal because other correction and velocity writers/callers remain open.
- Finding IDs or checked absence/replacement path: F-12

### Slice S7-PUSH: Player push/knockback inputs; exclude damage outcomes

- Inventory ID(s): INV-EXTERNAL, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: LivingEntity push override -> Entity collision push vector; direct LivingEntity knockback formula -> delta movement writer. Source formulas are compared; caller and condition inventory remains open.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity.java` lines 1502-1518 and 2101-2104; `Entity.java` lines 1554-1598.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity.java` lines 1531-1547 and 2125-2128; `Entity.java` lines 1583-1624.
- State producers/writers -> consumers/readers: push target/position/no-physics/pushable state -> `0.05` horizontal collision impulse; knockback amount/direction and `KNOCKBACK_RESISTANCE` -> normalized horizontal vector and grounded vertical term -> `setDeltaMovement`, then later player travel.
- Parent slices / dependencies / closure evidence: S4-COLLISION-QUERY; S6-ATTRIBUTES; S7-CORRECTIONS; S7-DEPENDENCIES. Both paired push and knockback bodies match; trigger/caller inventory and external state changes remain open. Damage outcomes excluded.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): push and knockback arithmetic is unchanged in the paired source methods. The player receives collision impulses through its non-sleeping LivingEntity push path; the `KNOCKBACK_RESISTANCE` attribute gates direct knockback magnitude. The exact caller paths, resistance producers, and other external velocity writers remain open.
- Finding IDs or checked absence/replacement path: checked absence of a direct push/knockback formula delta in the paired methods; trigger and caller closure pending.

### Slice S7-PISTON-LAUNCH: Piston displacement and launch items

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

### Slice S7-MOUNTS: Mount/dismount transitions and externally supplied values

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending


### Slice S7-DEPENDENCIES: Revisit unchanged callers after dependency closure

- Inventory ID(s): slice mapping not yet inventoried
- Exact behavior boundary and enclosing guards/order checked: not yet inspected
- A evidence: not yet inspected in exact 1.21.4 source
- B evidence: not yet inspected in exact 1.21.5 source
- State producers/writers -> consumers/readers: not yet inspected
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources are published; this slice has not been inspected, so no behavior conclusion is recorded
- Finding IDs or checked absence/replacement path: pending

## Dependency queue and blockers

- D0: RESOLVED: both exact Mojmap readiness records, IDs, provenance, all source/artifact manifest entries, diagnostics hashes and 22 cited method bodies per side were verified.
- D1: IN PROGRESS. Inventory both player/input/travel/collision hierarchies, callers, state producers and readers; follow changed/influential helpers into bounded slices. Owner: discovery.
- D2: OPEN. Inspect matching client-jar resources and referenced defaults/tags/components omitted by source output; hash entries. Owner: discovery.
- D3: OPEN. Close all block/fluid callbacks, contact shapes, registration and neighbor data; parent slices S4-CALLBACKS/S5-BLOCK-SHAPES/S5-NEIGHBORS/S5-FLUIDS. Owner: discovery.
- D4: OPEN. Close movement attributes, effects, enchantments, equipment tick/applicability and defaults; parent slices S6-ATTRIBUTES through S6-EQUIPMENT. Owner: discovery.
- D5: OPEN. Trace client corrections, direct velocity/impulse writers, piston launches and mount input consumers on both sides; parent slices S7-CORRECTIONS through S7-MOUNTS. Owner: discovery.
- Current dependency: full exact-pair call graph, state writer/consumer chains, resource data and external movement inputs remain open; source readiness is resolved and is not a blocker.

## Finding index

Source-confirmed findings: [F-01 movement callback path](findings/F-01-movement-callback-path.md), [F-02 bubble-column upper contact](findings/F-02-bubble-column-above-contact.md), [F-03 keyboard vector rounding](findings/F-03-keyboard-vector-rounding.md), [F-04 sprint stop gates](findings/F-04-sprint-stop-gates.md), [F-05 player horizontal velocity cutoff](findings/F-05-player-horizontal-velocity-cutoff.md), [F-06 fall-flying climbable exit](findings/F-06-fall-flying-climbable-exit.md), [F-07 Nether portal inside shape](findings/F-07-nether-portal-inside-shape.md), [F-08 powder-snow inside shape](findings/F-08-powder-snow-inside-shape.md), [F-09 local-player input damping](findings/F-09-local-player-input-damping.md), [F-10 player edge-support query](findings/F-10-player-edge-support-query.md), [F-11 powder-snow wall-jump state](findings/F-11-powder-snow-wall-jump-state.md), and [F-12 client correction pitch clamp](findings/F-12-player-correction-clamps-pitch.md). Findings identify confirmed source deltas; they do not close the remaining pair-wide inventory/dependency slices.

## Incremental finding snapshot log

- Candidate S-01: F-09 controlled local-player input damping; immutable finding snapshot commit `95061345541a2b5a8c56e57a7fe71a6007e9d684`; finding path `findings/F-09-local-player-input-damping.md`; finding-file SHA-256 `3a1680c5e724a935cbc576705088f03e3a9d4c6a8e29e5375d4ddb8e93b1e328`.
- Exact publication identity: A 1.21.4 Mojmap source manifest SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`, artifact manifest SHA-256 `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`; B 1.21.5 Mojmap source manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`, artifact manifest SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`. Finding lists the cited LocalPlayer/LivingEntity/Entity file hashes and jar/mapping identities.
- Review status: pending coordinator-routed independent blind source review; no acceptance or implementation handoff is recorded. This candidate does not freeze the pair or close its coverage slices.

## Resume checkpoint

- Last completed slice: S3-JUMP-MATH (compared-no-difference); exact-source comparisons have identified F-01 through F-12. S3-WATER, S3-LAVA, S7-PUSH and S7-CORRECTIONS remain in-progress because dependency/caller closure is open. No finding snapshot or pair freeze has been accepted.
- Next: close input/sample and scale dependencies, then continue ordered local tick and sprint/jump slices; continue the full travel branch comparison afterward.
- Outstanding dependencies and owners: D1 call-graph and producer/consumer inventory (discovery); D2 resource/tag/default inspection (discovery); D3 collision/block/fluid callback and shape inventory (discovery); D4 attributes/effects/enchantments/equipment (discovery); D5 client correction/mount/push external paths (discovery).
- Assumptions requiring verification: complete input consumers, player-only reachability through all travel branches, shape and resource dependencies, and direct movement state writers.

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: none; blind freeze not reached.
- Finding -> implementation disposition/evidence: deferred until freeze.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: pending.

## Independent source audit

- Reviewer: pending coordinator assignment; must differ from author.
- Status: pending
- Inventories and call-chain ranges re-walked: pending
- Concrete missed-slice routes (or `none found`): pending
- Misses routed to slice/finding IDs and owners: pending

- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts: 3 compared-no-difference; 0 terminal finding slices; 0 not-applicable; 0 blocked; 22 pending; 21 in-progress. Twelve source-confirmed findings have been recorded; slices remain open until their full inventories and dependencies close.
- Required inventories: all remain open; bounded source work is underway across tick order, input math, travel formulas, collision callbacks, block/fluid contact and partial external correction/push paths. Inventory-level closure is not claimed.
- Open dependencies: D1-D5; D0 resolved.
- Gaps: full movement call graph, branch dependencies, state writers/consumers, collision/shape provider inventory, registry/tag/resource data, modifiers/equipment and external player movement inputs.
- Evidence/hash/correspondence audit: exact source/artifact readiness verified; finding hashes and all bounded slice evidence still require final audit at source freeze.
- Blind freeze: pending.
- Implementation reconciliation: pending.
- Independent audit: pending.
- Completion checker: the owner-published checker at workflow commit fba28fa154d29572263ea3f2c44cf1dc23134329 accepts this active run structure and explicitly does not claim completion; this is schema status only, not source proof.
- Runtime validation: not performed; separate workflow and not authorized.







