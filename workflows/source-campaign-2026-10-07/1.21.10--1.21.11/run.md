# Discovery: 1.21.10 to 1.21.11

- Run status: active
- Scope: fresh source-only direct client-player movement comparison; older A = 1.21.10; newer B = 1.21.11. Do not read old/current mod implementation or wiki-audit output, browse Minecraft Wiki/MCPK, or use release notes. Exclude health/food-state production, attack/damage resolution, non-player movement and vehicle physics; direct player-motion, velocity, impulse and knockback response triggered by native movement events remains in scope. No Java implementation or runtime validation before the required handoff.
- Repository revision and start date: comparison branch created from `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07. Branch `feat/source-discovery-movement-source-1-21-10-1-21-11`.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap on both sides; exact pair markers confirm matching `mojmap` namespace and exact release IDs 1.21.10 and 1.21.11.
- Source preparation owner / command / log / readiness marker: shared source-preparation owner; exact command is in both provenance records. One serialized batch decompiled 1.21.8, 1.21.10 and 1.21.11 using Mojmap, 4G heap, Java 25.0.3+9-LTS, Gradle 9.7.1. Pair logs: `build/movement-campaign-2026-10-07/staging/mojmap-1.21.8-to-1.21.11-9a8c76419cd14660b88f9fc90225e127/gradle.full.log` (SHA-256 `2be0eaf67f24c0f1cfa585da9072572d1ac16b0c79c9864c67e5fb8c1bcd2c91`). Readiness markers and provenance hashes are recorded below. This worker did not run decompilation.
- Toolchain/decompiler/remapper versions and options: Java `25.0.3+9-LTS`; Gradle `9.7.1`; Vineflower `1.12.0`; Tiny Remapper `0.14.1`; Mapping IO `0.9.1`; Gson `2.14.0`; ASM `9.10.1`; `--mappings=mojmap --decompiler-heap=4G`.
- Discovery author(s): this worker.
- Independent reviewer (must differ from discovery authors): pending assignment.

## Artifact manifest

Both exact markers, provenance files, source manifests, artifact manifests, diagnostics and every file listed in the source/artifact manifests were verified read-only. Requested IDs, metadata IDs, resolved IDs and marker IDs all match exactly. Both sides are Mojmap in the same official-name mapping family. The artifact manifest paths are relative to the provenance-recorded batch cache, not the campaign `artifacts/` root.

- A 1.21.10 source root: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap`; 6,386 Java files; 28,999,290 bytes; source manifest `mojmap.sources.sha256`, SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; source hashes/count/bytes all match. Artifact manifest `artifacts.sha256`, SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; all 257 listed cache artifacts match. Readiness marker SHA-256 `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0`; provenance SHA-256 `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c`; diagnostics SHA-256 `5642b893edbb01bc1dd57386e013c61572c20f75fd25542877bace1f1f457eb0`. Original client jar SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`; official `client_mappings.txt` SHA-256 `2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39`; derived `client-mojmap.jar` SHA-256 `0885181c5e4c2f21dd2f95591dd095fa0176807edbe239ef77218e861f0751da`.
- B 1.21.11 source root: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap`; 6,622 Java files; 29,464,971 bytes; source manifest `mojmap.sources.sha256`, SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; source hashes/count/bytes all match. Artifact manifest `artifacts.sha256`, SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; all 257 listed cache artifacts match. Readiness marker SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; provenance SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; diagnostics SHA-256 `a97183dfdbeb5000aac0c66aaed9bb85aa2f2655594e5e4dece254a7c46a13aa`. Original client jar SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; official `client_mappings.txt` SHA-256 `517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd`; derived `client-mojmap.jar` SHA-256 `7055b6a734a8f9f0d80229c2438ba12d10952a57c3795f32b8611020b34eb89a`.

The provenance batch command is `decompileMinecraft --versions=1.21.8,1.21.10,1.21.11 --mappings=mojmap --decompiler-heap=4G` with isolated `staging/mojmap-1.21.8-to-1.21.11-9a8c76419cd14660b88f9fc90225e127/output` and `.../cache`; successful exit 0 is recorded in each `.success.log`. The shared full log reports remapper access warnings for `OptionInstance$ValueSet` from `VideoSettingsScreen`/`OnlineOptionsScreen`, and Gradle deprecation warnings; no exception, Vineflower error, or error loading a selected movement class was found. Movement diagnostics mark exact required source identities and list nine methods per side; each body still requires direct inspection before its slice can close. The Java source saver excludes jar resources; source-derived resource claims must cite the original client jar and entry hash. The separate Feather derived-artifact revision `feather-r1-2026-10-07` does not apply to this Mojmap pair.

## Blind-discovery freeze

- This status is the full-pair freeze. Individual finding snapshots are tracked separately and do not change it.
- Status: pending
- Freeze commit/checkpoint and timestamp: pending source coverage and independent audit.
- Evidence inventory and finding IDs included at freeze: pending; no source finding is confirmed yet.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither has been opened. No wiki browsing or wiki/MCPK/release-note evidence used.
- Source/mapping hashes covered by freeze: pending exact readiness manifests.

## Correspondence and call order

Exact source roots and diagnostic anchors are now verified; member correspondence and call order remain in progress. Initial movement diagnostics identify `Entity.move`/`moveRelative`, `LivingEntity.jumpFromGround`/`travel`/`aiStep`, `Player.aiStep`/`travel`, and `LocalPlayer.aiStep`/`move` on both sides. Resolve exact descriptors, callers, overrides and source anchors before using these method names as correspondence. Required end-to-end sequence: local input sampling → local tick/superclass tick → pre-travel state/input preparation → travel dispatch and every reachable branch → move/collision/support callbacks → post-travel state updates. Record exact call sequence and state read/write edges for input history/yaw; pose, dimensions and eye height; position, velocity and box; collision/ground/fluid/support flags; movement attributes; sprint/jump timers; and equipment/effect state. Follow every changed or influential helper, writer, consumer, registration and resource.

## Required source inventories

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-*,S3-*; evidence=exact pair sources unavailable.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S1-*,S2-*,S3-*; evidence=exact pair sources unavailable.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4-*,S5-contact-shapes,S5-landing-bounce,S5-neighbors,S5-climbables; evidence=exact pair sources unavailable.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S5-*,S6-data-resources; evidence=exact pair sources unavailable.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S3-attributes,S6-*; evidence=exact pair sources unavailable.
- `INV-EXTERNAL` player-only externally supplied movement inputs and direct player velocity/impulse/knockback application, plus in-scope player-facing transitions; exclude non-player and vehicle physics: status=pending; slice_ids=S7-*; evidence=exact pair sources unavailable.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=delegated scope set; direct vanilla-state consumers remain covered by owning movement slices.

## Coverage ledger

The entries below are provisional behavior buckets from the required navigation stages. They remain pending and must be split into source-bounded member/body ranges wherever a bucket spans multiple methods, writers, callers, providers, registrations or resources. These labels are not a completed inventory or evidence of equivalence.
### Slice S1-input-sampling: keyboard-controller sampling, input object lifetime and previous/current input capture

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S1-local-tick: local player tick, superclass tick, travel dispatch and pre/post-travel ordering

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S1-yaw-relative: yaw-to-motion conversion, diagonal normalization, relative acceleration and input scaling

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S1-sneak-use: sneak and item-use input scaling, edge-sneak path and timing

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S1-sprint: sprint start/stop gates, timers, transitions, sprint-jump interaction

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S1-jump: jump input, cooldown, jump delay, auto-jump and jump state capture

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S1-flight-ride: flight toggle/input, unstuck behavior and riding gates affecting player

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S2-pose: pose selection, swimming/crawling transitions and stored pose state

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S2-dimensions: pose/entity dimensions, resize timing, eye height when used by movement/fluid queries

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S2-player-gates: player superclass movement gates, blindness/item-use/abilities consumers and field defaults/resets

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S2-flight-state: abilities, flight speed, flying/walking state and stored air speed

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-travel-dispatch: living travel branch selection and dispatch conditions

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-ground-air: ground/air acceleration, friction, drag and gravity with exact FP order

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-cutoffs: negligible velocity thresholds, comparisons, normalization and post-travel cleanup

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-jump-impulse: jump power, sprint jump impulse, yaw trigonometry and velocity writes

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-climb: climbing detection, clamps, movement and exit velocity behavior

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-water: water acceleration, drag, gravity, swimming and fluid-height interactions

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-lava: lava travel acceleration, drag, gravity and collision outcomes

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-glide: gliding travel and directly consumed movement attributes/state

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-attributes: movement speed, jump/gravity/step-related attribute consumers, aggregation/order/defaults

- Inventory ID(s): INV-TICK, INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-move-core: entity move dispatch, axis ordering, position and velocity updates

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-collision-query: collision candidate acquisition, shape context/query timing, pose/box dependence

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-step: step-up eligibility, candidate paths, height comparisons and tie-breaking

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-edge-support: edge probes, support lookup, grounding and support-block state

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-velocity-collision: axis cancellation/restitution, collision flags and fall/ground callbacks

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-shape-math: AABB/voxel shape intersections, clipping, epsilon and iteration behavior

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-callbacks: block/entity movement callbacks reachable from player move and their order

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-block-registry: block registrations/default friction, speed/jump factors and shape-provider changes

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-landing-bounce: slime/bed landing, bounce, support and callback behavior

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-slow-surface: soul sand, ice and other speed/friction surface providers

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-contact-shapes: web/honey/powder snow and historical partial-block collision/support shapes

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-climbables: ladders, vines and other climbable registrations, callbacks and neighbor dependence

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-fluids: water/lava/bubble columns, flow vectors, fluid heights and push timing

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-neighbors: neighboring/state-dependent collision and movement providers, including piston displacement path

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-modern-only: blocks/states absent in A: verify registration/absence and record modern-only applicability; no historical behavior invented

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S6-effects: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness consumers and formulas

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S6-enchantments: Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide and other registered movement-affecting entries

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S6-equipment-items: Elytra, item-use slowdown, equipment slots/components and player movement predicates

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S6-data-resources: client jar data, tags, effect/enchantment definitions, registry defaults and referenced values

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S6-attribute-lifecycle: attribute modifier application/removal, stacking, timers and equipment/effect lifecycle

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S7-external-velocity: client consumers and writers for incoming velocity/position corrections and player knockback/push

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S7-environment-displacement: explosions, pistons, launch items, mount/dismount transitions insofar as they write player movement state

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S7-state-writer-closure: exhaustive remaining reachable player movement-state writers, callbacks, registrations and dependencies discovered from prior stages

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice X-scope-exclusions: health, regeneration, hunger/food/saturation/exhaustion/damage/combat emulation and non-player physics

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

## Dependency queue and blockers

- Open dependencies: `D-METHOD-BODY-REVIEW` and all method/resource dependencies discovered during the seven-stage walk.

## Finding index

No source findings confirmed yet; comparison has not started. This is not a no-difference conclusion.

## Resume checkpoint

- Last completed slice: source-gate preparation and 44 provisional stage behavior buckets; no exact-pair source slice completed.
- Next bounded slice and exact files/members/body ranges to open: start `S1-input-sampling` with `net/minecraft/client/player/KeyboardInput.java` and `LocalPlayer.java`; resolve full input sampling → local tick → superclass tick/travel sequence, exact descriptors, callsites and body ranges on both sides.
- Outstanding dependencies and owners: `D-METHOD-BODY-REVIEW` (discovery worker); newly discovered producer/consumer, shape, registration and data dependencies will be added with exact owners/actions.
- Current assumptions requiring verification: all listed ready/source/artifact hashes were verified. Remaining assumptions: exact member correspondence, operation/callback order, every reachable player state writer and producer/consumer dependency, relevant jar resource entries, and source-level movement semantics.

## Finding snapshots (not pair freeze)

- No finding snapshots submitted or accepted yet. An accepted source-confirmed finding snapshot may be handed off independently while this full-pair run remains active; snapshot acceptance does not change pair coverage or full-pair freeze status.

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: not inspected; blind source-only constraint.
- Finding -> implementation disposition/evidence: deferred until after blind-discovery freeze and assigned to an implementation reconciler.
- Existing implementation without a frozen source finding: not inspected; deferred.
- Coverage gaps routed back to discovery slices: pending source audit.

## Independent source audit

- Reviewer: pending assignment; reviewer must not be a discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: pending.
- Concrete missed-slice routes (or `none found`): pending.
- Misses routed to slice/finding IDs and owners: pending.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 44 pending; 0 in-progress; 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked. Pair source provenance is verified; behavior review has not yet closed any slice.
- Required inventory status and evidence: all seven inventories pending method-bounded traversal, producer/consumer linkage and full closure. Source roots and artifact hashes are verified above.
- Open dependencies: `D-METHOD-BODY-REVIEW` and all method/resource dependencies discovered during the seven-stage walk.
- Unresolved gaps and limits: pair provenance is verified, but all movement behavior remains unexamined. Keep active while comparison proceeds; at handoff, any open source slice requires partial status.
- Evidence/hash/correspondence audit: ready JSON, provenance, source/artifact manifests and diagnostics hashes are verified; full source-file and cache-artifact inventories match the markers. Per-slice source ranges and hashes are pending direct inspection.
- Full-pair blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Accepted finding snapshots: none. Full-pair freeze: pending. Implementation reconciliation: pending. Independent audit: pending. Runtime validation: not performed; not authorized.

The completion checker validates schema/status only and cannot establish source truth. The independent source audit remains mandatory. Keep this folder limited to run.md and optional findings/*.md.
