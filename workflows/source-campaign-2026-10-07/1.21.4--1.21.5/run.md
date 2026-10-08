# Source-only movement comparison: Minecraft 1.21.4 to 1.21.5

- Status: partial
- Scope: direct client player movement; older A = 1.21.4; newer B = 1.21.5.
- Track: source-only discovery; no wiki comparison, mod implementation inspection, or runtime implementation.
- Repository base: 1f4f0d0e753d8b6999a37e4e81f8fc2b32fbaf18 (resume source-discovery checkpoint); branch feat/source-discovery-movement-source-1-21-4-1-21-5-resume; start date 2026-10-08.
- Source preparation owner: shared source preparer; published records and artifact identity below were reverified before source comparison.
- Namespace and CLI mode: Mojmap for both releases; exact alignment verified against each release's source marker, official mapping file and mapped jar.
- Discovery author: this task.
- Independent reviewer: pending coordinator assignment.

## Artifact manifest

Both exact releases have ready publications under `build/movement-campaign-2026-10-07/ready/`. Both source roots, cited source/artifact manifests and diagnostic signatures were checked before comparison. The worker reads those trees only.

- A 1.21.4: readiness and metadata IDs both `1.21.4`; mode/namespace `mojmap`; source root `build/movement-campaign-2026-10-07/ready/1.21.4/mojmap/`; client SHA-256 `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e`; official `client_mappings.txt` SHA-256 `48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23`; mapped `client-mojmap.jar` SHA-256 `56995548c9cb8bd7cdb9996b676daeafae02bde9d6ef4029b9eeca6bfd7dcc74`. Marker `ready/1.21.4/mojmap.ready.json`; source-manifest SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` (5,744 entries, verified); artifact-manifest SHA-256 `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` (89 entries, verified); diagnostics SHA-256 `fb5d4cfc7238ea634c47ad148e22539fec30a1b33bb5d3353ff8c34a3e44a421` (22 method rows, signatures verified at cited lines).
- B 1.21.5: readiness and metadata IDs both `1.21.5`; mode/namespace `mojmap`; source root `build/movement-campaign-2026-10-07/ready/1.21.5/mojmap/`; client SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`; official `client_mappings.txt` SHA-256 `3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5`; mapped `client-mojmap.jar` SHA-256 `124561e91a61714ca5a73495784c14c03a7a927d1f715eafbcb56ae115a6712a`. Marker `ready/1.21.5/mojmap.ready.json`; source-manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` (5,921 entries, verified); artifact-manifest SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656` (89 entries, verified); diagnostics SHA-256 `d43c0fdfc627492b043962c0379b744461ebbb6b2ffb24f326fe72a63a039755` (22 method rows, signatures verified at cited lines).
- Shared command: `gradlew decompileMinecraft --versions=1.21.1,1.21.3,1.21.4,1.21.5 --mappings=mojmap --decompiler-heap=4G --output-root=...\staging\mojmap-1.21.1-to-1.21.5-cd5a99cb1024417c9d370097c886a131 --cache-directory=...\artifacts`; full log: `build/movement-campaign-2026-10-07/staging/mojmap-1.21.1-to-1.21.5-cd5a99cb1024417c9d370097c886a131/gradle.full.log`. Toolchain: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; ASM 9.10.1; Gson 2.14.0. Generated source and artifacts are owned by the shared source preparer and remain read-only here.

### Evidence artifact identities

- Evidence artifact A1: original-verified publication for exact release 1.21.4, Mojmap. Immutable evidence path `build/movement-campaign-2026-10-07/ready/1.21.4/mojmap/`; readiness marker `build/movement-campaign-2026-10-07/ready/1.21.4/mojmap.ready.json` SHA-256 `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be`; source manifest `build/movement-campaign-2026-10-07/ready/1.21.4/mojmap.sources.sha256` SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; artifact manifest `build/movement-campaign-2026-10-07/ready/1.21.4/artifacts.sha256` SHA-256 `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`; diagnostics `build/movement-campaign-2026-10-07/ready/1.21.4/movement-diagnostics.txt` SHA-256 `fb5d4cfc7238ea634c47ad148e22539fec30a1b33bb5d3353ff8c34a3e44a421`. Client SHA-256 `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e`; official mappings SHA-256 `48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23`; mapped client jar SHA-256 `56995548c9cb8bd7cdb9996b676daeafae02bde9d6ef4029b9eeca6bfd7dcc74`. At resume, marker, source/artifact manifest, diagnostics hashes and the cited source-file hashes for this finding were rechecked against the canonical publication. No revised-derived artifact is used.
- Evidence artifact B1: original-verified publication for exact release 1.21.5, Mojmap. Immutable evidence path `build/movement-campaign-2026-10-07/ready/1.21.5/mojmap/`; readiness marker `build/movement-campaign-2026-10-07/ready/1.21.5/mojmap.ready.json` SHA-256 `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e`; source manifest `build/movement-campaign-2026-10-07/ready/1.21.5/mojmap.sources.sha256` SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; artifact manifest `build/movement-campaign-2026-10-07/ready/1.21.5/artifacts.sha256` SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`; diagnostics `build/movement-campaign-2026-10-07/ready/1.21.5/movement-diagnostics.txt` SHA-256 `d43c0fdfc627492b043962c0379b744461ebbb6b2ffb24f326fe72a63a039755`. Client SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`; official mappings SHA-256 `3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5`; mapped client jar SHA-256 `124561e91a61714ca5a73495784c14c03a7a927d1f715eafbcb56ae115a6712a`. At resume, marker, source/artifact manifest, diagnostics hashes and the cited source-file hashes for this finding were rechecked against the canonical publication. No revised-derived artifact is used.

## Blind-discovery freeze

- Status: pending; source comparison remains in progress.
- Freeze commit/checkpoint and timestamp: pending.
- Evidence inventory and finding IDs included at freeze: pending; F-01 through F-17 are current source-confirmed findings, and final evidence hashes will be recorded after coverage closes.
- Confirmation old mod implementation/code and isolated wiki-audit results were not opened before freeze: confirmed; source-navigation docs and an earlier source-discovery report were used only as navigation.
- Source/mapping hashes covered by freeze: pending.

## Correspondence and call order

Exact-source correspondence is in progress. The verified Mojmap pair resolves `LocalPlayer`, `ClientInput`, `KeyboardInput`, `Player`, `LivingEntity`, `Entity`, `BlockBehaviour`, `BlockGetter`, `CollisionGetter`, `BlockCollisions`, `BubbleColumnBlock`, `NetherPortalBlock`, `PowderSnowBlock`, `HoneyBlock`, `FluidState`, `WaterFluid` and `LavaFluid` through source members and caller paths. Confirmed call order: client `LocalPlayer.aiStep` samples input and sprint/jump state, calls `super.aiStep`; `LivingEntity.aiStep` performs velocity cutoff and input application, jump, travel dispatch, block effects, then post-travel work. For bed start, server `startSleeping` leaves the existing fall-flying flag untouched; `Player.tick` reaches `updatePlayerPose` after `super.tick`, with the paired pose priority in S2-POSE. The full tick graph, inherited dispatch, collision/pose writers, resources and external inputs remain open. Similar names and adjacent reports are not proof.

## Required source inventories


- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slices=S1-INPUT-SAMPLE, S1-INPUT-SCALE, S1-LOCAL-TICK, S3-DISPATCH, S3-GROUND, S3-AIR, S3-GRAVITY-DRAG, S3-JUMP-MATH, S3-SPRINT-MATH, S3-CLIMB, S3-WATER, S3-LAVA, S3-SWIM, S3-GLIDE, S3-POST; evidence=F-03, F-05, F-06, F-09, F-11, F-13 plus paired LocalPlayer/LivingEntity/Entity excerpts; remaining branch/member closure open.
- `INV-STATE` movement state writers/readers: status=pending; slices=S1-SPRINT-GATE, S1-SPRINT-TIMER, S1-JUMP-GATE, S1-SPRINT-JUMP, S1-FLIGHT, S1-RIDING, S2-POSE, S2-DIMENSIONS, S2-EYE-HEIGHT, S2-STATE-WRITERS; evidence=F-04, F-13 plus paired LocalPlayer/LivingEntity/Entity writers; timer, pose, dimensions and external writer closure open.
- `INV-COLLISION` collision/query path, shape providers, registrations, callbacks, neighboring-block dependencies: status=pending; slices=S4-COLLISION-QUERY, S4-AXIS, S4-STEP, S4-EDGE, S4-GROUND-SUPPORT, S4-SHAPES, S4-CALLBACKS, S5-BLOCK-SHAPES, S5-NEIGHBORS; evidence=F-01, F-07, F-08, F-10 plus paired Entity/Player/NetherPortalBlock/PowderSnowBlock/BlockBehaviour paths; full collision and shape-provider inventory open.
- `INV-WORLD-MOVEMENT` block/fluid properties, subclasses, registries, tags/data and resource-backed defaults: status=pending; slices=S5-BLOCK-SHAPES, S5-BLOCK-FACTORS, S5-NEIGHBORS, S5-FLUIDS; evidence=F-01, F-02, F-07, F-08 plus block/fluid callback excerpts; resource/tag/registry closure open.
- `INV-MODIFIERS` movement attributes/effects/enchantments/equipment and application/removal/conditions: status=in-progress; slices=S6-ATTRIBUTES, S6-EFFECTS, S6-ENCHANTMENTS, S6-EQUIPMENT; evidence=F-03/F-04 cite direct sneaking and sprint movement-attribute consumers; selected effect formulas and registrations plus the specific Elytra/glide applicability path are paired; remaining modifier producers/defaults, effect lifecycle, enchantments and broader equipment inventory remain open.
- `INV-EXTERNAL` player-only external movement inputs and client consumers: status=pending; slices=S7-CORRECTIONS, S7-PUSH, S7-PISTON-LAUNCH, S7-MOUNTS, S7-DEPENDENCIES; client correction/pitch, wind-charge impulse, and passenger lifecycle subsets inspected (F-12; S7-PUSH; S7-PISTON-LAUNCH; S7-MOUNTS), while other correction writers, piston paths and external inputs remain open.
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

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.aiStep` snapshots previous forward input -> `canStartSprinting` checks current input and eligibility -> distinct double-tap and sprint-key branches -> `setSprinting`; active sprint state is later checked by run/swim stop predicates. Follow-up inspection found a control-flow refactor in B: A's shared `shouldStopSprinting` plus separate swim cancellation becomes B's run/swim stop helpers after sprint-start checks. A non-underwater start uses `forwardImpulse >= 0.8`; B uses `ClientInput.hasForwardImpulse` (> `1.0E-5F`) and adds `(!isInWater() || isUnderWater())`, while both retain a low-threshold forward check for underwater start. These inputs and water/pose conditions require full ordering/reachability reconciliation before concluding a new delta.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LocalPlayer.java` lines 653-656, 698-724, 816-830, 1053-1077; `ClientInput.java` lines 18-20 (`hasForwardImpulse`); `LivingEntity.java` sprint-state writer lines 2077-2084. ClientInput SHA-256 `0f2e4d03749ab7495e386af05aa77a0414b5d1c3ef76256db2b514f42cc6e3c4`.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LocalPlayer.java` lines 693-695, 722-745, 837-852, 1065-1084; `ClientInput.java` lines 17-19 (`hasForwardImpulse`); `LivingEntity.java` sprint-state writer lines 2101-2108. ClientInput SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`.
- State producers/writers -> consumers/readers: key/input sampling and current/previous forward state -> sprint-start eligibility and timer; water/underwater, flight, passenger, use-item, food and blindness state gate start/stop; sprint flag -> `LivingEntity#setSprinting` movement-speed modifier -> travel. Food/Blindness are direct vanilla predicate reads; their producers remain excluded/open as applicable. F-09's changed input representation/scale is a direct dependency of the changed threshold expression.
- Parent slices / dependencies / closure evidence: S1-INPUT-SAMPLE; S1-INPUT-SCALE; S1-SPRINT-TIMER; S1-LOCAL-TICK; S6-ATTRIBUTES; F-04; F-13. Gate/timer and downstream speed-attribute producer/default closure remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): F-13 records a double-tap start-path difference for non-passenger players; F-04 records active-sprint stop differences. The newly checked start-threshold and water/fall-flying gates are not assigned a separate finding because their complete interaction with the earlier/later stop gates, current swimming flag, vanilla input producer and F-09 input transformation has not been closed. Food production is excluded, while input-timing and speed-attribute dependencies remain open.
- Finding IDs or checked absence/replacement path: F-04, F-13

### Slice S1-SPRINT-TIMER: Sprint start/stop state, windows and timers

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: timer decrement at `LocalPlayer.aiStep` entry -> shift/item-use reset -> double-tap arm at seven ticks -> transition to sprint while eligibility holds; the B reset additionally includes backward input.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LocalPlayer.java` lines 644-645, 668-671, 694-708; previous/current impulse capture lines 653-656.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LocalPlayer.java` lines 684-685, 718-733; previous/current impulse capture lines 693-695; `ClientInput.java` lines 17-19.
- State producers/writers -> consumers/readers: timer field is decremented, reset or assigned in the paired `LocalPlayer.aiStep` bodies; its positive value gates the second forward transition to `setSprinting`. Current/previous input timing and sprint eligibility are parent dependencies.
- Parent slices / dependencies / closure evidence: S1-INPUT-SAMPLE; S1-SPRINT-GATE; S1-LOCAL-TICK; F-04; F-13. All direct timer writes in the paired `LocalPlayer.java` bodies were enumerated; full key-state producer/timing closure remains open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): timer decrement, reset, arm and consume paths were compared. B adds backward-input reset (F-04); the paired double-tap transition also differs under airborne conditions (F-13). The key-state edge history remains open.
- Finding IDs or checked absence/replacement path: F-04, F-13

### Slice S1-JUMP-GATE: Jump input, eligibility, cooldown and stored state

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local-player jump input snapshot and `jumping` writer -> `LivingEntity.aiStep` immobile/AI branch -> fluid/ground jump dispatch; `noJumpDelay` decrement, eligibility read, ten-tick write and reset path checked. Local-player flight-toggle and rideable-jump paths are tracked separately.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LocalPlayer.java` lines 607-617, 643-656, 733-749; `LivingEntity.java` lines 2692-2693, 2728-2762, 2149-2169.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LocalPlayer.java` lines 608-621, 683-695, 754-770; `LivingEntity.java` lines 2670-2671, 2712-2748, 2173-2193.
- State producers/writers -> consumers/readers: local jump key state -> `LocalPlayer` controlled-camera input write to `jumping`; inherited `LivingEntity.aiStep` clears it if immobile, otherwise dispatches ground or fluid jump and updates `noJumpDelay`; jump response writes velocity through the separately inventoried `jumpFromGround`/`jumpInLiquid` paths.
- Parent slices / dependencies / closure evidence: S1-INPUT-SAMPLE; S1-LOCAL-TICK; S1-FLIGHT; S1-RIDING; S3-JUMP-MATH; S3-SWIM; S3-WATER. Paired ground/fluid gate and cooldown bodies match in the inspected ranges; overlapping flight/riding/swimming paths and jump response dependencies remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): no source delta was seen in the paired `LivingEntity.aiStep` jump-dispatch conditions or `noJumpDelay` lifecycle. This is a bounded comparison only: the full player jump-input, ability-toggle, swimming, riding and response inventory is not closed.
- Finding IDs or checked absence/replacement path: checked absence of a delta in the cited ground/fluid dispatch and `noJumpDelay` ranges; no conclusion on the open overlapping paths.

### Slice S1-SPRINT-JUMP: Sprint-jump impulse and state writes

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: fluid/ground jump gate reaches `LivingEntity.jumpFromGround()`; method raises vertical delta to jump power, adds the sprint yaw impulse when `isSprinting()`, and sets `hasImpulse`.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity.java` lines 2149-2164; dispatcher `aiStep()` lines 2751-2754.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity.java` lines 2173-2188; dispatcher `aiStep()` lines 2737-2740.
- State producers/writers -> consumers/readers: `jumping`, on-ground/fluid and `noJumpDelay` state -> grounded/fluid jump dispatch -> `jumpFromGround`; the sprint flag gates the horizontal `0.2` yaw impulse. Resulting delta movement and impulse flag feed subsequent entity movement.
- Parent slices / dependencies / closure evidence: S1-SPRINT-GATE; S1-JUMP-GATE; S3-JUMP-MATH; S6-ATTRIBUTES. The paired jump velocity writer and sprint impulse formula match; upstream sprint/jump predicates and modifier dependencies remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): no formula delta was found in the paired `jumpFromGround` body. The sprint gate findings F-04 and F-13 can alter whether the unchanged sprint impulse branch is reached; input gate and movement-attribute dependencies are not closed.
- Finding IDs or checked absence/replacement path: checked absence of a delta in the cited `jumpFromGround`/dispatch bodies; F-04 and F-13 affect upstream sprint-state reachability.

### Slice S1-AUTOJUMP: Auto-jump probes, timing, normalization and trigger

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: option-backed enable flag and one-tick timer -> post-move `updateAutoJump` callback -> input/direction fallback, forward-projection test, collision-shape sweeps and height checks -> `autoJumpTime=1` -> next local tick's `input.makeJump()`.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LocalPlayer.java` lines 136, 269, 681-683, 900-910, 922-1036, 1038-1051.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LocalPlayer.java` lines 136, 265, 705-707, 913-923, 935-1049, 1051-1064.
- State producers/writers -> consumers/readers: options auto-jump value -> `autoJumpEnabled`; movement callback delta and player bounds -> contextual block shapes/collision iterator -> one-tick timer -> next-tick jump input. Jump Boost adjusts maximum probe height.
- Parent slices / dependencies / closure evidence: S1-INPUT-SAMPLE; S1-INPUT-SCALE; S1-JUMP-GATE; S2-DIMENSIONS; S4-COLLISION-QUERY; S4-SHAPES; S5-BLOCK-SHAPES; S6-EFFECTS. Paired probe/order bodies inspected; full shape, dimension, input and effect producer closure remains open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): timer, option guard, probe geometry, thresholds and resulting timer write match in the inspected bodies. `isMoving()` changes from component-nonzero checks in A to `lengthSquared() > 0.0F` in B; the paired vanilla keyboard input sources produce discrete or normalized nonzero vectors for which both predicates agree. The complete collision-shape provider inventory remains open.
- Finding IDs or checked absence/replacement path: checked absence of a reachable auto-jump behavior delta for vanilla keyboard input in the compared predicate; shape/provider and wider input dependencies remain open.

### Slice S1-FLIGHT: Flight input, toggles and movement speed state

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Player `jumpTriggerTime` decrement -> LocalPlayer `mayfly`/always-flying and jump-edge toggle gates -> ability flag write -> controlled-camera vertical jump/shift delta; grounded forced-flight reset; Player flying-speed consumer.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `Player.java` lines 546-555, 2087-2095; `LocalPlayer.java` lines 726-781, 809-813.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `Player.java` lines 554-563, 2030-2038; `LocalPlayer.java` lines 743-798, 830-834.
- State producers/writers -> consumers/readers: server/game-mode ability inputs (`mayfly`, always-flying) and current/previous jump plus auto-jump state -> `abilities.flying` toggle/reset -> Player flying-speed selection and controlled-camera vertical delta; abilities/equipment producers and full travel consumers remain open.
- Parent slices / dependencies / closure evidence: S1-INPUT-SAMPLE; S1-JUMP-GATE; S1-LOCAL-TICK; S3-AIR; S6-ATTRIBUTES; S7-DEPENDENCIES. The paired seven-tick toggle window, gates, vertical-input operation and `getFlyingSpeed` formula match in cited ranges; ability synchronization and all flight consumers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): no delta was found in the inspected local flight toggle, cooldown decrement, vertical input or player flying-speed formula. Flight state writers outside the local key path and complete travel/attribute dependencies have not been closed.
- Finding IDs or checked absence/replacement path: checked absence of a delta in the cited flight-toggle/cooldown/vertical-input/speed bodies; remaining ability and consumer paths open.

### Slice S1-RIDING: Player riding gates, jump charge and mount transition

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: local-player `jumpableVehicle()` requires a controlled vehicle implementing `PlayerRideableJumping` and `canJump`; `aiStep` requires zero jump cooldown, tracks hold/release charge and sends the player-jump callback/packet. `rideTick` forwards input to an `AbstractBoat` and is bounded at the excluded vehicle-control boundary.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LocalPlayer.java` lines 366-371, 532-537, 781-806, 877-884.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LocalPlayer.java` lines 362-367, 523-528, 802-827, 901-908.
- State producers/writers -> consumers/readers: local jump edge and cooldown -> `jumpRidingTicks`/`jumpRidingScale` -> `PlayerRideableJumping.onPlayerJump` and the clientbound action packet. The boat input forwarder affects vehicle-side control only, which is outside the player movement inventory.
- Parent slices / dependencies / closure evidence: S1-INPUT-SAMPLE; S1-JUMP-GATE; S1-LOCAL-TICK; S7-MOUNTS; S7-DEPENDENCIES. Paired player charge/gate/packet bodies match; mount/dismount state writers and external vehicle boundary remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): no delta was found in the cited LocalPlayer jump-charge/controlled-vehicle dispatch paths. The subsequent `onPlayerJump` implementation is vehicle physics and excluded; player mount/dismount lifecycle and external state writers are still open.
- Finding IDs or checked absence/replacement path: checked absence of a delta in the cited LocalPlayer charge/gate/packet bodies; mount lifecycle remains open.

### Slice S2-POSE: Pose selection transitions and movement predicates

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: ordinary jump input -> `Player.tryToStartFallFlying`/`startFallFlying` writes shared flag 7 through `LocalPlayer.aiStep` and the server command handler; vanilla Elytra supplies `GLIDER` and chest-slot `EQUIPPABLE`; `ServerPlayer.startSleepInBed` -> `LivingEntity.startSleeping` writes sleeping pose/position/state and zero velocity without clearing flag 7; next `Player.tick` calls `super.tick()` before `updatePlayerPose`; under a retained flag, A prioritizes `FALL_FLYING`, while B's `getDesiredPose` prioritizes `SLEEPING`. Both selectors first require the 0.6 x 0.6 swimming-pose fit check and then selected-pose fit/fallback logic.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `BedBlock#useWithoutItem` lines 83-119; `ServerPlayer#startSleepInBed` lines 1113-1157; `LivingEntity#startSleeping` lines 3395-3410; `Player#tick` line 329 and `Player#updatePlayerPose` lines 434-461; `LivingEntity#aiStep` lines 2769-2771 and `#canGlide` lines 2847-2859; `LocalPlayer#tick` lines 191-195 calls `super.tick`, `LocalPlayer` extends `AbstractClientPlayer` line 93, `AbstractClientPlayer` extends `Player` line 17, and `RemotePlayer#updatePlayerPose` is empty at lines 87-89. Source hashes: BedBlock `6fa509ef173bb0d670294c66495ee5aa388a0fb2bbed07e21757ac44a0e56d20`; ServerPlayer `d4fd29d9bed9698797e14cf9883e53f04ff4735afb2fee2972085cd82bf0d8f2`; Player `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`; LivingEntity `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`; LocalPlayer `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`; AbstractClientPlayer `fa15cd69238b46b410bc709c8ae0a629d5f3f817a6bc454e70a1d60bf7aa785c`; RemotePlayer `2bac53020610475374bfbcee937be72baba11fceeb1c64f88a09989f21fce0fe`.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `BedBlock#useWithoutItem` lines 81-117; `ServerPlayer#startSleepInBed` lines 1067-1111; `LivingEntity#startSleeping` lines 3386-3401; `Player#tick` line 344 and `Player#updatePlayerPose` lines 449-461 plus `#getDesiredPose` lines 465-475; `LivingEntity#aiStep` lines 2753-2755 and `#canGlide` lines 2837-2849; `LocalPlayer#tick` lines 191-195 calls `super.tick`, `LocalPlayer` extends `AbstractClientPlayer` line 93, `AbstractClientPlayer` extends `Player` line 17, and `RemotePlayer#updatePlayerPose` is empty at lines 86-88. Source hashes: BedBlock `93231daf73d11a6b8d72e6f42f4dcf8b928e443d34708e0a4c2ee50c1c88d4dd`; ServerPlayer `81ec90de6bf1521f45f1552c60af1ebb1a83fbcfa2eefb41b99e25b99d2f0d1b`; Player `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`; LivingEntity `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`; LocalPlayer `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`; AbstractClientPlayer `971bc296fe1feeb3fc54a2b78c0a0a6d0df2578fb79428c451f25be57c20a648`; RemotePlayer `db4508961b75904ebcefc64baae2a3f613be4c4136333152972d320e7b533e08`.
- A fall-flying writer/equipment evidence (additional exact hashes and lines in F-14): `Player#tryToStartFallFlying`/`startFallFlying` lines 1528-1539; `LocalPlayer#aiStep` lines 749-750; `ServerGamePacketListenerImpl#handlePlayerCommand` lines 1512-1515, SHA-256 `c5467cc282f361b5ab5540066385240f847d101c68afdef8655908f3fb3b2c8b`; `Items.ELYTRA` lines 971-982, SHA-256 `e872f8251d0b4f4a8b8ed1609dda56b57d7bc8ce35fc4477095334cbd6d3df0b`; `LivingEntity#canGlideUsing` lines 3626-3633.
- B fall-flying writer/equipment evidence (additional exact hashes and lines in F-14): `Player#tryToStartFallFlying`/`startFallFlying` lines 1507-1518; `LocalPlayer#aiStep` lines 770-771; `ServerGamePacketListenerImpl#handlePlayerCommand` lines 1596-1599, SHA-256 `fdb8726f77618ebcfd4fe3973e1740d999c2fd3d220ca4a3ddba837f160e6d69`; `Items.ELYTRA` lines 989-1001, SHA-256 `d258c31e88d6e7208b7c196a7b7408172f7c54050e11442e54894a1d31f4f5d5`; `LivingEntity#canGlideUsing` lines 3622-3629.
- State producers/writers -> consumers/readers: local jump input or the server's `START_FALL_FLYING` handler -> `Player.tryToStartFallFlying` -> `startFallFlying` writes shared flag 7; Elytra components/equipment slot -> `canGlideUsing`/`canGlide` validates it. Bed interaction then writes sleeping pose/position and zero velocity without clearing flag 7; server `LivingEntity.aiStep` glide validation/travel -> `Player.updatePlayerPose`; pose data update -> `Entity.onSyncedDataUpdated` -> `refreshDimensions` writes box dimensions and eye height. The exact ordinary writer/equipment chain is closed for F-14; other state writers remain open.
- Parent slices / dependencies / closure evidence: S1-LOCAL-TICK; S1-FLIGHT; S1-RIDING; S2-DIMENSIONS; S2-EYE-HEIGHT; S2-STATE-WRITERS; S3-GLIDE; S3-POST; S6-EQUIPMENT; S7-MOUNTS; F-06; F-14. The bed action is server-authoritative (`BedBlock#useWithoutItem` returns on the client); `ServerPlayer` sleep gates contain no grounded/fall-flying check. F-14's ordinary flag writer, packet caller and vanilla Elytra item/equipment dependency are source-closed. Other modifier/component inventory and pose writers stay open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source confirms a pose-priority delta when sleeping and fall-flying states coexist, the swimming-pose fit precheck succeeds, and the selected pose fits. A reachable vanilla path sets flag 7 from jump input when the player is airborne, not riding, not abilities-flying, outside water, without Levitation, and wearing a chest Elytra whose GLIDER/EQUIPPABLE components pass the no-break check. The server command handler reaches the same writer. Bed start has no ground/fall-flying gate and does not clear flag 7. This is a source-reachable state path, not a claim about frequency or runtime trajectory.
- Finding IDs or checked absence/replacement path: F-14

### Slice S2-DIMENSIONS: Dimensions, box resize and collision rejection timing

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `DATA_POSE` update -> `Entity.refreshDimensions`; pose-specific Player dimensions; player exclusion from server `fudgePositionAfterSizeChange`; position and box recomputation after sleeping/fall-flying priority update.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `Entity#onSyncedDataUpdated`/`refreshDimensions` lines 2934-2964; `LivingEntity#getDimensions` lines 3352-3358; `Player` pose table lines 136-147, `getDefaultDimensions` lines 1985-1986, and `LivingEntity.SLEEPING_DIMENSIONS` line 177. Hashes are recorded under S2-POSE.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `Entity#onSyncedDataUpdated`/`refreshDimensions` lines 2926-2957; `LivingEntity#getDimensions` lines 3343-3349; `Player` pose table lines 137-148, `getDefaultDimensions` lines 1927-1928, and `LivingEntity.SLEEPING_DIMENSIONS` line 176. Hashes are recorded under S2-POSE.
- State producers/writers -> consumers/readers: pose setter -> synced pose callback -> dimensions/eye-height fields and `reapplyPosition`; player bounding-box reads and collision/fluid queries consume the current pose dimensions. Player pose changes do not run the non-player position-fudge path.
- Parent slices / dependencies / closure evidence: S2-POSE; S2-EYE-HEIGHT; S4-COLLISION-QUERY; S4-SHAPES; F-14. Only the sleeping/fall-flying consequence is closed; all other player pose transitions, dynamic scale writes and shape/query consumers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): under F-14's preconditions, A refreshes to player fall-flying dimensions 0.6 x 0.6 with 0.4 eye height; B refreshes to sleeping dimensions 0.2 x 0.2 with 0.2 eye height. Both versions run the same refresh logic and do not fudge player position. Broader pose/dimension inventory remains in progress.
- Finding IDs or checked absence/replacement path: F-14; remaining pose transitions pending.


### Slice S2-EYE-HEIGHT: Eye height in fluid and collision checks

- Inventory ID(s): INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pose dimensions supply eye height; F-14's next pose update changes the stored eye-height field through `refreshDimensions` while the direct active fall-flying state may persist.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity.SLEEPING_DIMENSIONS` line 177 and Player pose table lines 136-147; `Entity#refreshDimensions` lines 2948-2964; source hashes recorded under S2-POSE.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity.SLEEPING_DIMENSIONS` line 176 and Player pose table lines 137-148; `Entity#refreshDimensions` lines 2941-2957; source hashes recorded under S2-POSE.
- State producers/writers -> consumers/readers: current pose -> stored eyeHeight via dimensions -> eye position/fluid checks and other eye-position readers. Fall-flying pose uses 0.4F; sleeping pose uses 0.2F in both versions.
- Parent slices / dependencies / closure evidence: S2-POSE; S2-DIMENSIONS; S3-WATER; S3-LAVA; S3-SWIM; S4-COLLISION-QUERY; F-14. Other direct eye-height consumers and all pose variants remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the bounded F-14 state produces different stored eye height in A and B as a consequence of the pose-priority difference. Eye-height use in water/lava queries and other movement consumers is not fully inventoried.
- Finding IDs or checked absence/replacement path: F-14; remaining consumer inventory pending.

### Slice S2-STATE-WRITERS: Movement flags, defaults, resets, timers and consumers

- Inventory ID(s): INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: paired direct writers for fall-flying shared flag (`startFallFlying`, `stopFallFlying`, server `updateFallFlying` validation), sleeping pose/position writer, sleeping-position sync data, delta movement reset and next Player pose update. This is a bounded producer/consumer trail, not the full state-writer inventory.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity#startSleeping` lines 3395-3410; `#stopFallFlying` and `#updateFallFlying` lines 2769-2771, 2825-2859; Player pose update lines 434-461; hashes recorded under S2-POSE.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity#startSleeping` lines 3386-3401; `#stopFallFlying` and `#updateFallFlying` lines 2753-2755, 2815-2849; Player pose update lines 449-475; hashes recorded under S2-POSE.
- State producers/writers -> consumers/readers: bed interaction writes sleeping pose, sleeping-position data, bed position, zero delta and impulse; fall-flying flag is unchanged there and is consumed by glide validation/travel and the pose selector. Pose refresh writes current dimensions/eye height. `canGlideUsing` reads synchronized equipment components and durability state.
- Parent slices / dependencies / closure evidence: S1-FLIGHT; S1-RIDING; S2-POSE; S2-DIMENSIONS; S2-EYE-HEIGHT; S3-GLIDE; S6-EQUIPMENT; S7-MOUNTS; F-14. Other state fields, defaults and reset/transition callers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the specific bed-start path leaves an already active fall-flying flag untouched; it remains until the server-side glide validator clears it or another state writer changes it. State combination reachability conditions and pose consequence are documented in F-14. Full writers/readers remain open.
- Finding IDs or checked absence/replacement path: F-14; broader state writer inventory pending.

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

- Inventory ID(s): INV-TICK, INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.aiStep` horizontal/vertical velocity cutoff runs before input, jump and travel. A uses independent component cutoffs; B uses a player-only squared horizontal-vector cutoff, preserves per-component horizontal cutoffs for non-player entities, then applies the same vertical cutoff. Also checked `getEffectiveGravity` and air-travel gravity/drag order: slow-falling only caps gravity while vertical velocity is nonpositive; travel applies the selected gravity before vertical drag. Other fluid/glide gravity and all gravity/drag producers remain open.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity#aiStep` lines 2708-2724; `#getEffectiveGravity` lines 2184-2187; `#travelInAir` lines 2202-2225. LivingEntity SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity#aiStep` lines 2686-2709; `#getEffectiveGravity` lines 2208-2211; `#travelInAir` lines 2224-2247. LivingEntity SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`.
- State producers/writers -> consumers/readers: prior delta movement -> player horizontal/vertical dead-zone cleanup -> `setDeltaMovement` -> input/jump/travel; vertical velocity sign and slow-falling effect -> effective gravity; gravity, ground friction, drag and discard-friction predicate -> travel velocity update. F-05 captures the player-specific horizontal threshold geometry and its reachable diagonal-vector consequence. Effect, attribute, fluid, collision and glide state producers remain open.
- Parent slices / dependencies / closure evidence: S1-LOCAL-TICK; S1-INPUT-SCALE; S3-AIR; S3-WATER; S3-LAVA; S3-GLIDE; S6-ATTRIBUTES; S6-EFFECTS; F-05; F-11. The selected gravity/air-travel arithmetic matches; F-05 is the inspected cutoff delta and F-11 affects a separate air-travel post-collision predicate. Full fluid, glide, modifier and collision closure remains open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): F-05 proves B can retain a diagonal low-speed horizontal vector which A zeros before input/travel. The cited slow-falling gravity and air-travel arithmetic match for equal inputs. The full gravity/drag inventory remains open.
- Finding IDs or checked absence/replacement path: F-05; checked absence of a delta in the cited effective-gravity and air-travel gravity/drag formulas.

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


- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: `Entity.tick` calls `updateSwimming`; `Entity.updateSwimming` retains swimming only while sprinting and in water without a passenger, otherwise starts it while sprinting and underwater with water at the entity block position; `Player.updateSwimming` clears swimming while abilities-based flying; `Player.isSwimming` rejects abilities-based flight and spectators before the shared predicate. Checked the shared-flag writer and the `Player` override/predicate. LocalPlayer sprint writers/gates feeding `isSprinting`, and swim travel/look-vector consumers, remain open.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `Entity#tick` line 455 calls `updateSwimming`; `Entity#updateSwimming` lines 1306-1314; `Entity#setSwimming` line 2370; `Player#updateSwimming` lines 1478-1485; `Player#isSwimming` lines 1818-1820. Hashes: Entity `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`; Player `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `Entity#tick` line 463 calls `updateSwimming`; `Entity#updateSwimming` lines 1336-1344; `Entity#setSwimming` line 2338; `Player#updateSwimming` lines 1456-1463; `Player#isSwimming` lines 1759-1761. Hashes: Entity `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`; Player `8fc187f33999db9dfc49251e95e92a17645a50ad16ca0f93f4948209f62036`.
- State producers/writers -> consumers/readers: sprint state + water/underwater state + passenger state -> `Entity.updateSwimming` -> shared flag 4; abilities-based flight and spectator state further gate `Player.isSwimming`; `Player.updateSwimming` forces flag 4 false while abilities-based flying. `LocalPlayer.aiStep` writes sprint state before inherited `LivingEntity.aiStep`/tick work; its paired sprint cancellation logic is still under S1-SPRINT inventory.
- Parent slices / dependencies / closure evidence: S1-LOCAL-TICK; S1-SPRINT-GATE; S1-SPRINT-TIMER; S2-POSE; S2-STATE-WRITERS; S3-WATER; S3-SWIM. The paired shared swimming updater and Player-specific gates match in the cited methods. The full sprint-state producer path, look-vector travel responses, and pose/collision dependencies remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): checked absence of a delta in the selected shared swimming flag update and Player-specific flight/spectator gates for equal input state. This closes only that predicate/writer sub-slice; sprint cancellation/start gates and swimming travel effects are not reconciled.
- Finding IDs or checked absence/replacement path: checked absence of a delta in the cited paired swimming predicates/writer; dependent sprint and travel inventories remain open.

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

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: post-travel `applyEffectsFromBlocks` and entity animation stage; `Entity.move` applies player edge back-off before collision resolution, updates collision/on-ground state, then calls `checkFallDamage`; the water-gated fall-distance writer feeds the next movement call's player edge-backoff predicate. B also guards non-ridden travel through `canSimulateMovement`; exact client/server gate equivalence and remaining post-travel state writers stay open.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity#aiStep` travel/post-travel lines 2779-2789; `Entity#move` edge-backoff/collision/check-fall order lines 647-682; `Entity#checkFallDamage` lines 1237-1252. Hashes: LivingEntity `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`; Entity `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity#aiStep` travel/post-travel lines 2763-2775; `Entity#move` edge-backoff/collision/check-fall order lines 655-703; `Entity#checkFallDamage` lines 1274-1292. Hashes: LivingEntity `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`; Entity `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`.
- State producers/writers -> consumers/readers: downward movement and water state -> `Entity.checkFallDamage` fall-distance update/reset -> next `Entity.move` -> `Player.maybeBackOffFromEdge`/`isAboveGround` -> horizontal movement reduction before collision. On-ground collision separately calls block `fallOn`; player movement callbacks such as `updateEntityMovementAfterFallOn` run during collision resolution and are not gated by this fall-distance callback. Remaining post-travel writers and state consumers remain open.
- Parent slices / dependencies / closure evidence: S3-WATER; S3-SWIM; S4-EDGE; S4-GROUND-SUPPORT; S5-FLUIDS; F-15. F-15 traces the in-scope state-writer-to-player-movement-reader dependency; damage/fall outcomes remain excluded under the campaign contract.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): F-15 records the reachable water accumulation change affecting sneak edge-backoff eligibility on a later non-grounded movement call. The rest of the selected post-travel order and fall-distance callback is inventoried; other post-travel state writes, fluid state and collision dependencies remain open.
- Finding IDs or checked absence/replacement path: F-15; other post-travel inventories pending.

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
- State producers/writers -> consumers/readers: movement vector and mover type plus flying/on-ground/fall-distance/max-step/surface state -> `isAboveGround` and per-axis/combined support probes -> possibly reduced horizontal movement vector -> `Entity.collide`. F-15 adds the preceding water-dependent fall-distance writer as a parent state dependency.
- Parent slices / dependencies / closure evidence: S3-POST; S4-COLLISION-QUERY; S4-SHAPES; S4-GROUND-SUPPORT; S5-BLOCK-SHAPES; F-15. Probe caller and AABB construction checked; collision shape/noCollision provider closure remains open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): exact paired player override and query inspected. B changes the query bounds and lower Y margin; source finding F-10 records the reachable guard and geometry. Underlying collision provider and all callers remain open.
- Finding IDs or checked absence/replacement path: F-10, F-15

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

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: paired `Entity#getBlockJumpFactor` chooses the current-position block factor unless it is `1.0`, then reads the block below that affects movement; `Entity#getBlockSpeedFactor` reads the current block, except water/bubble column, then falls back to below only when the current speed factor is exactly `1.0`. `LivingEntity#getBlockSpeedFactor` lerps from the inherited factor to `1.0F` by movement efficiency; `Player#getBlockSpeedFactor` bypasses block speed factor while abilities-flying or fall-flying. `Block` factor getters return stored friction/speed/jump values. Checked their consumers in jump power, on-ground travel friction and post-move horizontal velocity scaling. Exact-source scan of all 1.21.4/1.21.5 `net/minecraft` files found only the base Block getter overrides and seven built-in factor-bearing block registrations: Ice friction 0.98, Soul Sand speed 0.4, Slime Block friction 0.8, Packed Ice/Frosted Ice friction 0.98, Blue Ice friction 0.989, Honey Block speed 0.4/jump 0.5. Property defaults and these values match; contact-position providers remain open.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `Entity#getBlockJumpFactor`/`#getBlockSpeedFactor` lines 867-880 and `Entity#move` post-move factor application lines 707-708; `LivingEntity#getJumpPower` lines 2140-2142, `#getBlockSpeedFactor` lines 501-502 and on-ground travel friction lines 2202-2206; `Player#getBlockSpeedFactor` lines 1964-1965; `Block#getFriction`/`#getSpeedFactor`/`#getJumpFactor` lines 373-383; `BlockBehaviour.Properties` factor defaults/setters lines 961-963, 1072-1083; `Blocks` movement-factor registrations lines 1911, 1974, 3027, 3208, 4298, 5133, 5732. Hashes: Entity `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`; LivingEntity `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`; Player `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`; Block `857a7f02c86cbaff6b889032145677a0cc2255814dd983c0fae393cab0cb8a68`; BlockBehaviour `a549c5dcf7768f89f18519bdd2f985ef498d1bd4f6b329a30e0088e437819ddf`; Blocks `8e28c42de96dd9ebee370797cfb208f034eee2c38e3d00152ed6f9769390b238`.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `Entity#getBlockJumpFactor`/`#getBlockSpeedFactor` lines 915-928 and `Entity#move` post-move factor application lines 728-729; `LivingEntity#getJumpPower` lines 2164-2166, `#getBlockSpeedFactor` lines 473-474 and on-ground travel friction lines 2224-2228; `Player#getBlockSpeedFactor` lines 1905-1906; `Block#getFriction`/`#getSpeedFactor`/`#getJumpFactor` lines 418-428; `BlockBehaviour.Properties` factor defaults/setters lines 959-961, 1070-1081; `Blocks` movement-factor registrations lines 1960, 2034, 3103, 3285, 4375, 5210, 5817. Hashes: Entity `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`; LivingEntity `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`; Player `8fc187f33999db9dfc49251e95e92a17645a50ad16ca0f93f4948209f62036`; Block `d66c7efd4afb3e52f8d239a1b5fcd00a5c24874d75b61b5b16b794faf200f2c2`; BlockBehaviour `15418b0026919ef5ab97780a6ce8844677dc82d36cc4433a58f379b837d5cc50`; Blocks `026290c103c399d94ba4218317e3e627e7275836c5abe91fc4711443b7d2bec0`.
- State producers/writers -> consumers/readers: block friction/speed/jump values and entity/block positions -> `Entity` factor selection; movement-efficiency attribute -> `LivingEntity` interpolation; Player flight/fall-flying state -> factor bypass; selected factors -> jump power, travel friction, and post-move horizontal velocity scaling. Built-in defaults, assignments and getter providers are source-closed for this property family; `getBlockPosBelowThatAffectsMyMovement` producer/shape dependencies and contact-specific application conditions remain open.
- Parent slices / dependencies / closure evidence: S3-AIR; S3-GROUND; S3-JUMP-MATH; S4-COLLISION-QUERY; S5-BLOCK-SHAPES; S5-NEIGHBORS; S6-ATTRIBUTES. The cited shared and player-specific factor-selection formulas match; this does not close block value registration or neighboring/contact dependencies.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): no delta was found in the factor-selection/getter formulas, default values, or the seven built-in factor-bearing block registrations. Players reach on-ground friction during ground travel and the virtual Player factor override for horizontal movement; jump factor feeds the already-compared jump-power formula. Contact-position and application-condition dependencies remain open.
- Finding IDs or checked absence/replacement path: checked absence of a delta in cited factor selection/getter formulas; F-02 records the separately checked bubble-column contact precondition and velocity update.

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

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: paired `Attributes` registrations for movement speed, sneaking speed, gravity, jump strength, knockback resistance and explosion knockback resistance; LivingEntity and Player attribute builders; Player ability-derived walking-speed writer; wind-charge explosion knockback consumer.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `Attributes.java` lines 27-29, 44-49, 63-76; `LivingEntity#createLivingAttributes` lines 316-335; `Player#createAttributes` lines 229-242; Player ability load writer line 786; `ServerExplosion` lines 200-212. Source hashes: Attributes `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea`; LivingEntity `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`; Player `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`; ServerExplosion `d49f486abfa0d97a8da26f763c51c917c8b1f8064588962f21a3b113002c4f71`.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `Attributes.java` same lines/hash; `LivingEntity#createLivingAttributes` lines 305-324; `Player#createAttributes` lines 244-257; Player ability load writer line 783; `ServerExplosion` lines 200-212. Source hashes: Attributes `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea`; LivingEntity `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`; Player `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`; ServerExplosion `d49f486abfa0d97a8da26f763c51c917c8b1f8064588962f21a3b113002c4f71`.
- State producers/writers -> consumers/readers: registry definitions give paired default/range values; living/player builders include movement attributes; Player loading writes `MOVEMENT_SPEED` base from `abilities.getWalkingSpeed`; wind-charge explosion scales player impulse by `EXPLOSION_KNOCKBACK_RESISTANCE`. Runtime modifier sources, effects and equipment interactions remain open.
- Parent slices / dependencies / closure evidence: S1-INPUT-SCALE; S1-SPRINT-GATE; S1-FLIGHT; S3-GROUND; S3-AIR; S3-GLIDE; S7-PUSH; S7-PISTON-LAUNCH; D4. The cited registration and builder bodies match between A/B; full modifier producer/removal and travel-consumer inventory remains open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): paired movement attribute defaults and selected player ability/explosion consumers match in the inspected ranges. This does not close aggregation order, effect/enchantment modifiers, all attribute producers or all travel consumers.
- Finding IDs or checked absence/replacement path: checked absence of a delta in the cited attribute definition, player default builder and wind-charge resistance-consumer ranges; broader modifier inventory remains open.

### Slice S6-EFFECTS: Direct movement effects, formulas, conditions and timing

- Inventory ID(s): INV-MODIFIERS, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: selected direct readers for jump boost, slow falling, Levitation and Dolphins Grace in `LivingEntity`; selected registry definitions for Speed, Slowness and Jump Boost in `MobEffects`. Checked jump-power addition, nonpositive-vertical-velocity slow-falling gravity cap, Levitation vertical travel adjustment, water drag override, post-input fall-distance reset gates, and the Levitation glide rejection predicate. Effect application/removal, duration/tick timing, all other effect readers and full attribute aggregation are not yet checked.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity#getJumpBoostPower` lines 2144-2146; `#getEffectiveGravity` lines 2184-2187; `#travelInAir` lines 2202-2225; `#travelInFluid` lines 2227-2246; `#aiStep` fall-distance reset lines 2765-2777; `#canGlide` lines 2847-2858; `MobEffects` Speed/Slowness lines 15-28, Jump Boost lines 55-61, Levitation/Slow Falling/Dolphins Grace lines 96-109. SHA-256: LivingEntity `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`; MobEffects `35d97c1741ec1379b05f41b8452229870c87a75633d312cd0b20d7fd901b4d08`.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity#getJumpBoostPower` lines 2168-2170; `#getEffectiveGravity` lines 2208-2211; `#travelInAir` lines 2224-2247; `#travelInFluid` lines 2249-2268; `#aiStep` fall-distance reset lines 2751-2761; `#canGlide` lines 2837-2848; `MobEffects` Speed/Slowness lines 15-28, Jump Boost lines 55-61, Levitation/Slow Falling/Dolphins Grace lines 96-109. SHA-256: LivingEntity `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`; MobEffects `1511be2a9bfd2bd17112ef03b26d645e0cfaf315019b1d906d9d34c54d4ee245`.
- State producers/writers -> consumers/readers: active effect state is read by `LivingEntity` movement branches; Speed and Slowness register movement-speed attribute modifiers; Jump Boost registers safe-fall-distance modifier and `getJumpBoostPower` separately adds `0.1F * (amplifier + 1.0F)` to jump power. Slow Falling caps gravity at `Math.min(getGravity(), 0.01)` only while vertical velocity is nonpositive and also participates in the `aiStep` fall-distance reset; Levitation replaces the air-travel Y update and participates in the same reset, while preventing `canGlide`; Dolphins Grace sets water drag to `0.96F`. Effect producers, modifier attach/remove order, duration expiry and synchronization remain open.
- Parent slices / dependencies / closure evidence: S1-JUMP-GATE; S3-GROUND; S3-AIR; S3-GRAVITY-DRAG; S3-WATER; S3-GLIDE; S6-ATTRIBUTES; S6-EQUIPMENT; D4. Paired source-file hashes are recorded above and match each ready source manifest. The inspected predicates and floating-point expressions are identical; B renames the Java holders `MOVEMENT_SPEED`/`MOVEMENT_SLOWDOWN`/`JUMP` to `SPEED`/`SLOWNESS`/`JUMP_BOOST` while retaining the same registry IDs and modifier declarations.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): no delta is present in the selected direct movement formulas and registered Speed/Slowness/Jump Boost modifiers. This is a bounded read/registration comparison, not closure of effect coverage: active-effect lifecycle, all effect producers and consumers, and modifier aggregation dependencies remain open.
- Finding IDs or checked absence/replacement path: checked absence of a delta in the paired methods and registrations cited above; broader effects inventory pending.

### Slice S6-ENCHANTMENTS: Movement enchantments, conditions and resource data

- Inventory ID(s): INV-MODIFIERS, INV-STATE, INV-EXTERNAL, INV-RESOURCES
- Knockback-enchantment route: paired registration adds `1.0F` per Knockback level; `LivingEntity#getKnockback` adds this item effect to `ATTACK_KNOCKBACK`, and the accepted primary `Player#attack` path applies half the result through `LivingEntity#knockback`. A/B source ranges are `Enchantments.java` 667-680; `EnchantmentHelper.java` 184-187; `LivingEntity.java` 1441-1443 / 1469-1471; `Player.java` 1185-1193 / 1146-1154. These bounded registration, lookup and application bodies match. Client resources `data/minecraft/enchantment/knockback.json` (`812ef223af160fca60a16703e13145ec3e0420cf3e65e1349504ea9babb8f702`), `data/minecraft/tags/item/enchantable/sword.json` (`18c94bdd9c37c13d9a1de6b4d52df2ae2d195e3a264fd0b7bdef016f9a2035a2`), and `data/minecraft/tags/enchantment/exclusive_set/damage.json` (`b8f49a77123e2647d19e1d238f756eed1b6b7409bf5bae96b4e60de5d26b3fc8`) have matching A/B hashes.
- Additional direct impulse paths: Blast Protection adds `EXPLOSION_KNOCKBACK_RESISTANCE` by `0.15F` per level (`Enchantments.java` lines 227-243, A/B hashes cited below). Wind Burst is a main-hand Mace `POST_ATTACK` effect targeting/affecting the attacker (`Enchantments.java` lines 1158-1193): when the direct attacker is non-flying with fall distance at least `1.5`, its no-damage `ExplodeEffect` runs an explosion centered at that attacker (radius `3.5F`, level-based knockback multiplier). A/B `Player#attack` sweep routes call `doPostAttackEffects` after each swept target's `hurt` at A lines 1219-1222, regardless of the returned result, but B lines 1177-1182 call it only inside successful `hurtServer`. Since Wind Burst's explosion pushes eligible Players through the shared `ServerExplosion` -> `Entity.push` path, this creates confirmed finding F-16 when a swept target rejects damage. A/B `ExplodeEffect.java` hash `c62612e19828b0684973b6ffb420cc891dd78526b5f92f0e9d1a71413930e367`, `ServerExplosion.java` hash `d49f486abfa0d97a8da26f763c51c917c8b1f8064588962f21a3b113002c4f71`, and `SimpleExplosionDamageCalculator.java` hash `65728f68493bf507bba7db3b66c517db8bb559caef01db70667d17139e53681a` match; `Enchantments.java` entry `wind_burst`, `blast_protection`, and `enchantable/mace` data are also hash-identical (detailed evidence in F-16).
- Condition clarification: the grounded, non-flying, horizontal-speed-at-least-`1.0E-5F` predicate above is the separate periodic condition (`$$6`) for other Soul Speed effects. The movement modifier pair uses condition (`$$7`): not riding, not flying, and either (active enchantment plus Soul Speed block contact or airborne) or (inactive enchantment plus Soul Speed block contact). Thus the speed/efficiency modifiers can remain active while airborne.
- Location-change trigger evidence: `LivingEntity.baseTick` (A lines 386-394, 448-453; B lines 374-382, 391-430; source hashes cited below) calls `EnchantmentHelper.tickEffects` on the server before `super.baseTick` in both versions; after shared living-tick work, A gates its `lastPos` block-position comparison on `level() instanceof ServerLevel`, while B places the comparison inside an outer `isAlive() && level() instanceof ServerLevel` block. In both, a changed block position updates `lastPos` then calls `onChangedBlock`; paired `checkFallDamage` also invokes `onChangedBlock` on server landing with positive fall distance. These bounded server trigger paths show no movement-relevant change; tick-effect producer/consumer closure remains open.
- Paired client resource evidence: enchantment entries `depth_strider` (`a19a75083ed4229e1890333b5bfb71639e81c2fda5dbe80aee43251f05d5a934`), `frost_walker` (`2a3c9fd51dea59e196f4288df4cc27a83dc80cb4724898e6ea95bd806996d954`), `soul_speed` (`01a3752231e3bacf7d65627f3c7b48349446d35c527a58347e7f28456a247e07`), `swift_sneak` (`b5417e7c4e8705c897751ae6d0ed377030216af39c788519aaf00be8d3865956`), and `riptide` (`9d1bb93c765a78dcd0c31cb82e9b8d167919fae3d57dcd96b420e9e55e1fc08a`) match byte-for-byte in A/B client jars. Associated `soul_speed_blocks` (`8ba7eac6f7c74d25601ef4455b71e3947fdb8e51b8fafd55dddee48397b144b6`), `air` (`8291f3984418fa56cd1ca3f5132344de25cee407647b0f97d6008bc0f3d47d1b`), `enchantable/foot_armor` (`849645e1ac79d7b11ad1c06dd370ab00ee05e237ae47634caf829446e1f98d45`), `foot_armor` (`8d8cc28d2199e4121f5083dd74141ac506b7f1e36937806e65e55078558c0110`), `enchantable/leg_armor` (`eb588169cf0b0cf65673b607740792413be94efc19fa9c8ebc84f55626c0f468`), `leg_armor` (`6b757043346bbf76cf181d9241940277c2608b69b5b6e475e8a6079f6deaab6f`), `enchantable/trident` (`5cd0a665d2a509b7c56276975e35911e142c966766c03d16c00debc6fcdd8b0a`), and exclusive-set `boots` (`c83340128937b499d2e4becefbce42ca0134fbbf77d551ce3d1ca1adcfb5b5ef`) and `riptide` (`907bc30a1d7deb128dc8d423b4c887f252d05d9c64910f6f04b3b969b2d9f1dc` tags also match. This closes only the cited resource subset; broader item/component and enchantment resources remain in D2.
- Exact behavior boundary and enclosing guards/order checked: paired registrations for Depth Strider, Frost Walker, Soul Speed, Swift Sneak, and Riptide; `EnchantmentHelper.runLocationChangedEffects`, both `forEachModifier` overloads, and `getTridentSpinAttackStrength`; `Enchantment.runLocationChangedEffects`; Frost Walker's `ReplaceDisk.apply` and `FrostedIceBlock` tick/melt methods; `TridentItem.releaseUsing` through Riptide push, auto-spin, and grounded lift.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `Enchantments.java` lines 347-368 (Depth Strider), 372-420 (Frost Walker), 439-446 (Soul Speed movement predicate), 490-518 (Soul Speed attributes), 558-578 (Swift Sneak), 989-1004 (Riptide); `EnchantmentHelper.java` lines 224-238, 320-334, 360-364; `Enchantment.java` lines 407-443; `ReplaceDisk.java` lines 40-54; `FrostedIceBlock.java` lines 21-95; `LivingEntity.java` lines 345, 452, 529-531, 2599-2607; `TridentItem.java` lines 74-126. Source hashes: Enchantments `01bdf94faa8089e50a282ec0b50c2b9956ddfb4260ae37dd9ec23e460e9d1bd1`; EnchantmentHelper `5926f519897e629b369754a33fcd338140c57b930bb908bbd738ed430e38423c`; Enchantment `67394bfb374d6f5bcd989dc46aa7353379a5bcfb767fcf5e2950c7ec485482e4`; ReplaceDisk `662d593d7540ef2d53c60ef2ec8f8626aeb6ec95f0785e6ee8fbe55c292c8bfe`; FrostedIceBlock `e71acff645f25aa83e3aef2baae32a144b4d03f82641e7b72493405ef3afaf5f`; LivingEntity `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`; TridentItem `c4f1aae4fef4fd4b2d4b4ddb253b64824a8baf3d9f0a653f33d8dffe839ab132`.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; same paired registrations at Enchantments lines 347-368, 372-420, 439-446, 490-518, 558-578, 989-1004; `EnchantmentHelper.java` lines 224-238, 320-334, 360-364; `Enchantment.java` lines 407-443; `ReplaceDisk.java` lines 40-54; `FrostedIceBlock.java` lines 21-95; `LivingEntity.java` lines 334, 429, 505-507, 2609-2617; `TridentItem.java` lines 66-117. Source hashes: Enchantments `ee37699b95dc530fdd91dadef27d19a54bc7c4febe79bca40cc1ee438a626356`; EnchantmentHelper `b1c8ce1ef431d57488b4c6f991aa01e9d8372adaa077df8ff55a419d9b9448a0`; Enchantment `67394bfb374d6f5bcd989dc46aa7353379a5bcfb767fcf5e2950c7ec485482e4`; ReplaceDisk `662d593d7540ef2d53c60ef2ec8f8626aeb6ec95f0785e6ee8fbe55c292c8bfe`; FrostedIceBlock `e71acff645f25aa83e3aef2baae32a144b4d03f82641e7b72493405ef3afaf5f`; LivingEntity `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`; TridentItem `f22c71fe72980c256466eb12d68bc62a157c778186193d5379b37408da1423c7`.
- State producers/writers -> consumers/readers: Depth Strider adds `WATER_MOVEMENT_EFFICIENCY` by `0.33333334F` per level; Soul Speed's movement modifiers (`MOVEMENT_SPEED` `0.0405F` plus `0.0105F` per level, and `MOVEMENT_EFFICIENCY` +1) use condition `$$7`: not riding, not flying, and either (active enchantment plus Soul Speed block contact or airborne) or (inactive enchantment plus Soul Speed block contact). Its separate periodic predicate `$$6` for other effects requires grounded, non-flying, horizontal speed at least `1.0E-5F`, and Soul Speed block contact; it is not the movement-modifier gate. Swift Sneak adds `SNEAKING_SPEED` `0.15F` per level. Enchantment modifiers flow through item modifier enumeration into LivingEntity attribute rebuild; Soul Speed effects are invoked on location change. Frost Walker's on-ground, non-passenger condition runs a radius `(int)clamp(3 + level, 0, 16)` disk at y-1, replacing unobstructed water with frosted ice when air is above; this changes later support/collision. Riptide level effect gives spin strength; when released with strength >0 while in water/rain and durability permits, TridentItem normalizes the look vector, scales by strength, calls `Player.push`, starts 20-tick auto-spin, and moves grounded players upward by `1.1999999F`. These bounded registrations, dispatch methods, replacement/melt code, and Riptide impulse/lift arithmetic match. The B TridentItem has a changed ordinary-trident consumption path, but the inspected Riptide route's movement formulas and gates match.
- Parent slices / dependencies / closure evidence: S3-TRAVEL; S4-COLLISION; S5-BLOCK-SHAPES; S5-FLUIDS; S6-ATTRIBUTES; S6-EFFECTS; S6-EQUIPMENT; S7-PUSH; D2; D3. `ReplaceDisk` and `FrostedIceBlock` implementations and selected enchantment/tag resources match by source hash. The Frosted Ice inherited collision shape/factors and full tag/equipment/component inventory remain open. Effect lifecycle/aggregation, all movement attribute consumers, and Riptide start/use callers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): selected enchantment definitions/resources and the bounded shared effect/impulse formulas match; F-16 and F-17 are confirmed differences in the sweep-attack caller gate that decides whether those movement outputs run. This is not terminal: resource/tag/component coverage, equipment applicability, complete location-effect lifecycle, complete attribute aggregation/consumer inventory, inherited Frosted Ice shape/factors, and player Trident use caller order still require paired source evidence.
- Finding IDs or checked absence/replacement path: F-16 records the changed Wind Burst attacker impulse gate and F-17 records the separate direct sweep knockback to the target Player. The other cited selected registrations, Knockback-enchantment primary-hit route, Riptide impulse arithmetic, and Frost Walker effect implementations are unchanged in the bounded source comparisons; no whole-enchantment inventory conclusion.

### Slice S6-EQUIPMENT: Equipment/components, applicability and use slowdown

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Player#canGlide` ability guard -> `LivingEntity#canGlide` airborne/passenger/Levitation gates -> equipment-slot scan -> `canGlideUsing` GLIDER/EQUIPPABLE/breakage checks; ELYTRA item component producer compared in both releases.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `Player#canGlide` lines 1474-1475; `LivingEntity#canGlide` lines 2847-2858 and `#canGlideUsing` lines 3626-3633; `Items.ELYTRA` lines 971-986; `DataComponents.GLIDER`/`EQUIPPABLE` lines 147-154; `Unit.java` lines 5-8; `StreamCodec.unit` lines 45-59. Source hashes: Player `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`; LivingEntity `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`; Items `e872f8251d0b4f4a8b8ed1609dda56b57d7bc8ce35fc4477095334cbd6d3df0b`; DataComponents `47b74d16c5f2b995eee896a9aff3a3cb59fa9cd5daf88435a5a803db413fba48`; Unit `b517c2e24f359ede0537928ae7d7dd19865174867606c4c40ccc201cfebe19a0`; StreamCodec `cd24e9b59b953243df2d40004d34682aff862ef614ee9095630ce7c289b526c8`.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `Player#canGlide` lines 1452-1453; `LivingEntity#canGlide` lines 2837-2848 and `#canGlideUsing` lines 3622-3629; `Items.ELYTRA` lines 989-1004; `DataComponents.GLIDER`/`EQUIPPABLE` lines 168-174; `Unit.java` lines 7-11; `StreamCodec.unit` lines 46-60. Hashes: Player `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`; LivingEntity `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`; Items `d258c31e88d6e7208b7c196a7b7408172f7c54050e11442e54894a1d31f4f5d5`; DataComponents `a23368a7039ea5c0709d080c41387125e97685a3ced77622adbb10ccf2ef0ffe`; Unit `46859c2e45444410d407820f43108fef75785db938a01eae02e660a57de53db7`; StreamCodec `8f6864d4fa7f98ab103cb4c7aed58bd4844687c503631754607fb13f0ebc805c`.
- State producers/writers -> consumers/readers: item `GLIDER` and `EQUIPPABLE.slot` components plus durability state -> `canGlideUsing`; equipment slots are scanned by `LivingEntity#canGlide`; `Player#canGlide` additionally rejects abilities-based flight. Elytra writes both required components in both releases and uses the chest slot. `EQUIPPABLE` type registration matches. `GLIDER` changes from an inline `StreamCodec.unit(Unit.INSTANCE)` expression in A to `Unit.STREAM_CODEC` in B; B defines that field as `StreamCodec.unit(INSTANCE)`, and the paired `StreamCodec.unit` bodies match, so this alias change preserves the inspected Unit value and empty-payload validation behavior.
- Parent slices / dependencies / closure evidence: S1-FLIGHT; S2-POSE; S3-GLIDE; S6-ATTRIBUTES; S6-ENCHANTMENTS; D2 resource-backed defaults/tags; F-06; F-14. F-14's vanilla Elytra producer chain is closed; broader component definitions and other equipment effects remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the paired glide applicability predicates and Elytra registration are unchanged in the cited methods. F-14's ordinary writer and item/equipment path are now independently source-bound. Retained player fall flying also requires `abilities.flying == false`, airborne state, no passenger/Levitation, an equipped item with both components in its declared slot, and no impending breakage. Other equipment ticks, item/component data and movement equipment consumers remain open.
- Finding IDs or checked absence/replacement path: checked absence of a delta in the cited glide/equipment applicability path; full equipment inventory pending.

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
- Finding IDs or checked absence/replacement path: push/knockback formulas match in the paired methods, but F-17 identifies a changed Player sweep caller gate that controls whether the unchanged knockback formula reaches a target Player; trigger and caller closure remains pending.

### Slice S7-PISTON-LAUNCH: Piston displacement and launch items

- Inventory ID(s): INV-EXTERNAL, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: server-side `AbstractWindCharge#onHitEntity` -> `WindCharge#explode`/`Level.explode` -> `ServerExplosion` target impulse calculation -> `Entity#push(Vec3)`/`push(double,double,double)`; piston sources remain uninspected.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `AbstractWindCharge#onHitEntity` lines 73-88; `WindCharge` calculator and `#explode` lines 21-23, 54-70; `SimpleExplosionDamageCalculator#getKnockbackMultiplier` lines 45-48; `ServerExplosion` lines 186-215; `Entity#push` lines 1585-1592. Source hashes: AbstractWindCharge `758eb3865f6f7317ba0def6e1c46e80bc244a050435de6ca5a9ac33253bc0d04`; WindCharge `a1bb22b01dda69a5ab6f9856e5ebce2a297198694f4e7653766080eba32a5d23`; SimpleExplosionDamageCalculator `65728f68493bf507bba7db3b66c517db8bb559caef01db70667d17139e53681a`; ServerExplosion `d49f486abfa0d97a8da26f763c51c917c8b1f8064588962f21a3b113002c4f71`; Entity `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; same `AbstractWindCharge`, `WindCharge`, `SimpleExplosionDamageCalculator`, and `ServerExplosion` ranges/hashes as A; `Entity#push` lines 1614-1621; Entity SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`.
- State producers/writers -> consumers/readers: a server wind-charge hit invokes the explosion; eligible target direction/exposure and `getKnockbackMultiplier` produce a vector; for Player targets, the simple calculator returns zero when abilities-based flying is active, otherwise the server explosion calls `Entity#push`, which adds the vector to delta movement and marks `hasImpulse`. The explosion knockback-resistance attribute remains a modifier dependency. Damage outcomes and block destruction are excluded.
- Parent slices / dependencies / closure evidence: S6-ATTRIBUTES; S7-PUSH; S7-CORRECTIONS; S7-DEPENDENCIES; D2 `BLOCKS_WIND_CHARGE_EXPLOSIONS` tag entry (A/B hash `ef9816aea3e3beef7ed5c049c3d827955000272e810468e082d98cae0586da12`). WindCharge/AbstractWindCharge/ServerExplosion/Entity push bodies match; piston displacements, other launch items, caller/default closure, and resistance producers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the wind-charge direct player impulse route and F-16's bounded Wind Burst attacker impulse route are source-reachable; their shared explosion impulse arithmetic and player flying guard match. F-16 records the changed Player attack caller gate that conditionally reaches Wind Burst. Piston update/launch paths and other external movement inputs remain open.
- Finding IDs or checked absence/replacement path: checked absence of a delta in the bounded wind-charge and shared explosion impulse formulas; F-16 records the Wind Burst player-impulse caller difference. Piston displacement and other launch sources remain open.

### Slice S7-MOUNTS: Mount/dismount transitions and externally supplied values

- Inventory ID(s): INV-EXTERNAL, INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `Entity#startRiding` admission and player pose reset, `#removeVehicle`/`#stopRiding` relationship writes, passenger `#rideTick` zero-velocity/tick/rider-position order, and `LocalPlayer#rideTick` controlled-boat input forwarding.
- A evidence: `ready/1.21.4/mojmap.sources.sha256`; `Entity#rideTick` lines 2034-2040, `#positionRider` lines 2042-2052, `#startRiding` lines 2082-2116, `#removeVehicle`/`#stopRiding` lines 2128-2138; `LocalPlayer#rideTick` lines 877-885. Source hashes: Entity `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`; LocalPlayer `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`.
- B evidence: `ready/1.21.5/mojmap.sources.sha256`; `Entity#rideTick` lines 2011-2017, `#positionRider` lines 2019-2029, `#startRiding` lines 2059-2093, `#removeVehicle`/`#stopRiding` lines 2105-2115; `LocalPlayer#rideTick` lines 901-909. Source hashes: Entity `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`; LocalPlayer `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`.
- State producers/writers -> consumers/readers: successful ride admission writes `vehicle`/passenger links and `Pose.STANDING`; passenger tick zeroes player velocity and places the passenger from vehicle attachment; local controlled-boat input forwards directional keys to vehicle code, which is outside player-only physics. Dismount clears the relationship; downstream player travel and externally supplied corrections/velocity remain to be traced.
- Parent slices / dependencies / closure evidence: S1-RIDING; S1-LOCAL-TICK; S3-DISPATCH; S7-CORRECTIONS; S7-PUSH; S7-DEPENDENCIES. Paired cited lifecycle and local input forwarding bodies match; downstream post-dismount and external writer closure remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): on the inspected common passenger route, both releases zero velocity, tick the passenger, and position it from the vehicle; the local controlled-boat input forwarder also matches. Vehicle control/physics are excluded; player mount admission/transition and passenger-position sources remain within scope. No delta is found in these bounded bodies, and this slice remains nonterminal pending callers and downstream movement state consumers.
- Finding IDs or checked absence/replacement path: checked absence of a delta in the cited passenger lifecycle and local boat-forwarding bodies; transition callers and post-dismount path remain open.


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
- D2: IN PROGRESS. Inspect matching client-jar resources and referenced defaults/tags/components omitted by source output; hash entries. Owner: discovery.
- D2 evidence checkpoint: source references to `BlockTags.CLIMBABLE`, `PORTALS`, `FENCES`, `WALLS`, `BLOCKS_WIND_CHARGE_EXPLOSIONS` and `FluidTags.WATER`/`LAVA` resolve to `data/minecraft/tags/{block,fluid}/...` entries whose A/B client-jar entry hashes match: climbable `d0e3e76d7457f3f3f3d7219fe218c7746e4b2e7626d5c388fcf193089069e365`, portals `fe9759678b07daacfebc3ce9e5aa616489cb432af36d14ce4cc1128cd3a410ef`, fences `214462701d11703b222317fb7d470caa40e017b5036b127db242986b92fb30cd`, walls `7124e8b44110efc945df32e09bf88dae84413d38558c8073e84e98532391ef97`, water `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004`, lava `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b`, and `blocks_wind_charge_explosions` `ef9816aea3e3beef7ed5c049c3d827955000272e810468e082d98cae0586da12`. `inside_step_sound_blocks.json` differs (A `c8af9023de1dea1faad8b0bf77b494bd2941bc96c5fcd9f40798a031a49ba7b8`; B `2d97011bfe26d35e5895027ce80f3372b79ced9138bc567bf8cb031520f8a596`), but the paired `Entity#getPrimaryStepSoundBlockPos` consumers at A lines 1131-1135/B 1168-1172 only select a step-sound block position; this resource delta is sound-only. Artifact identities: A client.jar SHA-256 `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e`; B `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`. Other tag/resource consumers and component/default closure remain open.
- D3: IN PROGRESS. Nether portal and powder-snow inside shapes, bubble-column contact/neighbor behavior, core block friction/speed/jump factor selection and built-in factor registrations, and selected movement tag resources are inspected; close remaining block/fluid callbacks, contact shapes and neighbor data. Owner: discovery.
- D4: IN PROGRESS. F-14's ordinary glide-state writer, vanilla Elytra components, equipment-slot/breakage gates and selected attribute defaults/consumers are traced; close remaining movement attributes, effects, enchantments, equipment tick/applicability and defaults. Owner: discovery.
- D5: IN PROGRESS. Bounded client correction, passenger lifecycle and wind-charge impulse routes are inspected; trace remaining direct velocity writers, piston launches, mount callers and external inputs on both sides. Owner: discovery.
- D6: OPEN. Coordinator must assign a different source-only reviewer for a blind audit of the completed full-pair inventory; the author cannot self-close this gate. Owner: coordinator.
- Current dependency: full exact-pair call graph, state writer/consumer chains, resource data, external movement inputs and independent full-pair review remain open; source readiness is resolved and is not a blocker.

## Finding index

Source-confirmed findings: [F-01 movement callback path](findings/F-01-movement-callback-path.md), [F-02 bubble-column upper contact](findings/F-02-bubble-column-above-contact.md), [F-03 keyboard vector rounding](findings/F-03-keyboard-vector-rounding.md), [F-04 sprint stop gates](findings/F-04-sprint-stop-gates.md), [F-05 player horizontal velocity cutoff](findings/F-05-player-horizontal-velocity-cutoff.md), [F-06 fall-flying climbable exit](findings/F-06-fall-flying-climbable-exit.md), [F-07 Nether portal inside shape](findings/F-07-nether-portal-inside-shape.md), [F-08 powder-snow inside shape](findings/F-08-powder-snow-inside-shape.md), [F-09 local-player input damping](findings/F-09-local-player-input-damping.md), [F-10 player edge-support query](findings/F-10-player-edge-support-query.md), [F-11 powder-snow wall-jump state](findings/F-11-powder-snow-wall-jump-state.md), [F-12 client correction pitch clamp](findings/F-12-player-correction-clamps-pitch.md), [F-13 airborne forward double-tap sprint](findings/F-13-airborne-double-tap-sprint.md), and [F-14 sleeping pose priority during retained fall-flying](findings/F-14-sleep-fall-flying-pose-priority.md). Findings identify confirmed source deltas; they do not close the remaining pair-wide inventory/dependency slices.

Additional source-confirmed finding: [F-15 water fall-distance edge-backoff eligibility](findings/F-15-water-fall-distance-edge-backoff.md). This new source delta does not close the remaining pair-wide inventory/dependency slices.

Additional source-confirmed finding: [F-16 Wind Burst sweep-hit impulse gate](findings/F-16-wind-burst-sweep-hit-gate.md). This new source delta does not close the remaining pair-wide inventory/dependency slices.

Additional source-confirmed finding: [F-17 sweep-target knockback hit gate](findings/F-17-sweep-knockback-hit-gate.md). This separate movement output from the same caller gate does not close the remaining pair-wide inventory/dependency slices.

## Incremental finding snapshot log

- Candidate S-01: F-09 controlled local-player input damping; immutable finding snapshot commit `95061345541a2b5a8c56e57a7fe71a6007e9d684`; finding path `findings/F-09-local-player-input-damping.md`; finding-file SHA-256 `3a1680c5e724a935cbc576705088f03e3a9d4c6a8e29e5375d4ddb8e93b1e328`.
- Exact publication identity: A 1.21.4 Mojmap source manifest SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`, artifact manifest SHA-256 `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`; B 1.21.5 Mojmap source manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`, artifact manifest SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`. Finding lists the cited LocalPlayer/LivingEntity/Entity file hashes and jar/mapping identities.
- Review status: pending coordinator-routed independent blind source review; no acceptance or implementation handoff is recorded. This candidate does not freeze the pair or close its coverage slices.
- F-14 original finding snapshot: author commit `4479daadc43e48caba0f313ce037331bcb6a41fb`; path `findings/F-14-sleep-fall-flying-pose-priority.md`; file SHA-256 `720e2c042ee79e5ba7869ab8649d8a6beec81ea0cba33848a980eeb423ee52d0`. Review record commit `581b5bd2cb44981c4889eaf05ea739134e332b87` requested changes only for the mistyped B LivingEntity digest; behavior/reachability was accepted by that review.
- F-14 corrected immutable snapshot: commit `6f0a567c47d57071e1e22c89efaeb74fbc531491`; same finding path; file SHA-256 `c64be4794ee553e3adf11e0b1bb3f57428e2fb9e32292df940526279d44ae14c`. Correct B LivingEntity.java SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`, matching B1 source manifest. Status: corrected and awaiting independent re-review; no acceptance or implementation handoff is recorded.
- F-14 latest immutable snapshot: commit `eaa873aa5402065fb7f0070d11521b4e054f7683`; same finding path; file SHA-256 `f95d6b53ebd3a43ad775d72998a7dac9e8a9a9fa9296b1d578b9abf99df7353d`. This follow-up adds exact A/B `Player#canGlide` source ranges for the ability-flight precondition; it supersedes the prior corrected snapshot for re-review. Review remains pending and no implementation handoff is recorded.
- F-14 review event R-14-02: review report commit `d6664c08d015b6d301b5498302d9f64f32ebec92`, path `workflows/source-campaign-2026-10-07/independent-review-F14-revisions-2026-10-08.md`. Revision 1 (`6f0a567c...`) and revision 2 (`eaa873aa...`) both received REQUEST CHANGES: the digest and later `Player#canGlide` citation were verified; the ordinary shared-flag writer/callers and a concrete vanilla GLIDER/EQUIPPABLE item path were still missing from the finding. This event supplements, and does not overwrite, the earlier review record `581b5bd2cb44981c4889eaf05ea739134e332b87`.
- F-14 latest immutable snapshot: commit `9d2e4728d5a0d9f72a18a36a33e387f3cf0b14bb`; same finding path; Git blob `a404b95634d07d54f44e60bd14d1c5109473f3c8`; raw file SHA-256 `68275d18a15b845657bf8abc5dab198ecb20d8d9a004faff651abebaaf85296c`. It adds paired `Player.tryToStartFallFlying`/`startFallFlying`, local jump-input call, server `START_FALL_FLYING` handler, and vanilla Elytra GLIDER/chest-EQUIPPABLE plus `canGlideUsing` evidence. Those F-14-specific dependencies are closed; this snapshot awaits a fresh independent review. Pair status remains partial and no implementation handoff is recorded.
- F-05 digest correction snapshot: prior finding commit `a157cd1d904b2f695a287dfefc0abef92df3e615`, Git blob `f6e01f817a4cfbf564172de03e950a47f4e30137`, raw file SHA-256 `a168f58f98cb0c8cbb23156c956a689cbcf701b2d17d1655346e336e988844a2`; its B source digest contained a typo. Corrected finding-only snapshot commit `aee00bcb97151f2ec0d01bfe2c3035ae6953e9c8`, Git blob `b1897f6bb4f6196a8e8ee1607297a1419883299f`, raw file SHA-256 `4a14cf6645f7fe9897c45d3daeda9f271a3a4beccc4047ce294afd34ab398df6`; the B digest now matches `ready/1.21.5/mojmap.sources.sha256`. This correction changes evidence metadata only; it preserves the source comparison and awaits independent review.

- F-15 immutable snapshot: finding-only commit `6a3bc525e62a35c2e09a75a584c2df19d8a342a3`; path `findings/F-15-water-fall-distance-edge-backoff.md`; Git blob `4e22e705fef6d7bbf77810c076a83a869d4ca02e`; raw file SHA-256 `c2b539f226d92ec98d98597bd75d5bdac7ff0992133d782f3ea9481bcd586d53`. It traces the paired water-gated `fallDistance` writer through `Player.maybeBackOffFromEdge` before collision. Snapshot awaits independent review; pair remains partial.
- F-16 immutable snapshot: finding-only commit `37f159d9da0e8677566b9789567b46c1037da07a`; path `findings/F-16-wind-burst-sweep-hit-gate.md`; Git blob `3d10b5838f26b9b4a68705e4e8013604542a7c0f`; raw file SHA-256 `e77a110fe357b5d6e3050344b8f1fa6f77828ff3e4cb6ff6a6f1a653dc3b257d`. It traces the paired sweep attack damage-acceptance gate through Wind Burst's attacker-targeted explosion to the direct player push. Snapshot awaits independent review; pair remains partial.
- F-17 original snapshot: finding-only commit `279e299dd0d279f2ab3fbab81e452babce4b7f37`; path `findings/F-17-sweep-knockback-hit-gate.md`; Git blob `738c1610b7ace372455c4960f41a8ff7b01a2186`; raw file SHA-256 `fe2dc181291196ccf366878a93d03902d445f962ee2cc8bf2a99121f6340f51b`. This initial snapshot used an invalid slice identifier. Corrected immutable snapshot commit `2cea4f90188d31290dbb336e923095e9fb98ea49`; same path; Git blob `63afaada944d78beeb713473ad5900922d75f293`; raw file SHA-256 `9e39633e9b832cee5169e07cd1270d80adfc960e8e3f53c813e61da1a06b3f38`; slice attribution is now S7-PUSH. The corrected snapshot awaits independent review; pair remains partial.

## Resume checkpoint

- Latest bounded source work: S3-GRAVITY-DRAG records the paired slow-falling/air-travel gravity formulas and F-05 cutoff chain; F-05 now has a digest-corrected finding-only snapshot awaiting independent review. S6-EFFECTS records paired direct-reader formulas and selected Speed/Slowness/Jump Boost registrations; S3-SWIM records the paired shared swimming-state writer and Player flight/spectator gates; S5-BLOCK-FACTORS records shared/player-specific factor selection and built-in factor registrations. S6-ENCHANTMENTS now records selected matching enchantment definitions/resources, Frost Walker's shared block replacement path, Soul Speed conditions, Riptide player impulse, Blast Protection resistance and the changed sweep hit gate: F-16 tracks Wind Burst impulse to the attacker, F-17 tracks direct knockback to the target Player. These slices remain open because effect lifecycle/modifier aggregation, sweep and primary attack dependencies, full resource coverage, inherited Frosted Ice shape/factors, sprint producer gates/swim travel and contact-position dependencies remain open. Exact-source comparisons currently identify F-01 through F-17; F-14 snapshot `9d2e4728...` awaits fresh independent review after R-14-02 requested changes on its two predecessors; F-16 and F-17 await independent review. S2-POSE records the retained fall-flying/sleeping pose-priority difference and next-update dimension consequence; S6-ATTRIBUTES and S6-EQUIPMENT have bounded default/consumer and glide/Elytra comparisons; S7-MOUNTS has a bounded passenger lifecycle comparison; S7-PISTON-LAUNCH traces the unchanged wind-charge impulse route. D2 has a partial paired resource-tag inventory. S2-DIMENSIONS, S2-EYE-HEIGHT and S2-STATE-WRITERS remain in-progress because their full inventories and consumer closure are broader than F-14. S1-SPRINT-GATE/TIMER/JUMP-GATE/SPRINT-JUMP/AUTOJUMP/FLIGHT/RIDING, S3-WATER/LAVA and S7-PUSH/CORRECTIONS remain in-progress because dependency/caller closure is open. F-09 has a candidate snapshot pending independent review; no snapshot has been accepted and no pair freeze has been accepted.
- New source finding F-15 records the water-gated fall-distance writer feeding later sneak edge-backoff eligibility. It awaits independent review and does not close S3-POST/S4-EDGE dependencies.
- Next: close input sampling/scale dependencies and remaining local pre-travel state writers; continue ordered travel, collision, modifier, resource and external-player inventories.
- Outstanding dependencies and owners: D1 call-graph and producer/consumer inventory (discovery); D2 resource/tag/default inspection (discovery); D3 collision/block/fluid callback and shape inventory (discovery); D4 attributes/effects/enchantments/equipment and GLIDER/EQUIPPABLE data (discovery); D5 client correction/mount/push external paths (discovery); D6 independent full-pair source reviewer assignment and audit (coordinator).
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

- Coverage counts: 3 compared-no-difference; 1 terminal finding slice; 0 not-applicable; 0 blocked; 9 pending; 33 in-progress. Seventeen source-confirmed findings have been recorded; slices remain open until their full inventories and dependencies close.
- Required inventories: all remain open; bounded source work is underway across tick order, sprint/jump/flight gates and timers, riding input state, auto-jump, input math, travel formulas, pose/dimension state, collision callbacks, block/fluid contact and partial external correction/push paths. Inventory-level closure is not claimed.
- Open dependencies: D1-D6; D0 resolved.
- Gaps: full movement call graph, branch dependencies, state writers/consumers, collision/shape provider inventory, registry/tag/resource data, modifiers/equipment and external player movement inputs.
- Evidence/hash/correspondence audit: exact source/artifact readiness verified; finding hashes and all bounded slice evidence still require final audit at source freeze.
- Blind freeze: pending.
- Implementation reconciliation: pending.
- Independent audit: pending.
- Completion checker: the owner-published checker at workflow commit fba28fa154d29572263ea3f2c44cf1dc23134329 accepted the prior active run structure; not rerun for this partial handoff. Schema status is not source proof.
- Runtime validation: not performed; separate workflow and not authorized.







