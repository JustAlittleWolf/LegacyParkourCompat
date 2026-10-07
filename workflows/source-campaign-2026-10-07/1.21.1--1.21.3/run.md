# Discovery: 1.21.1 to 1.21.3

- Run status: active
- Scope: source-only client player movement; older A = 1.21.1; newer B = 1.21.3. This is one content-update boundary using the exact latest-hotfix representatives.
- Repository revision and start date: started at `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`), 2026-10-07 Europe/Vienna; task branch `feat/source-discovery-movement-source-1-21-1-1-21-3`.
- Selected naming namespace: Mojmap on both endpoints; readiness IDs/metadata resolve exactly to 1.21.1 and 1.21.3.
- Source owner worktree: C:\Users\Wolfi\.codex\worktrees\3e2d\LegacyParkourCompat. Command from provenance: gradlew.bat decompileMinecraft --versions=1.21.1,1.21.3,1.21.4,1.21.5 --mappings=mojmap --decompiler-heap=4G. Success log path: ../../../build/movement-campaign-2026-10-07/staging/mojmap-1.21.1-to-1.21.5-cd5a99cb1024417c9d370097c886a131/gradle.full.log.
- Toolchain: Gradle 9.7.1, Java 25.0.3+9-LTS, Vineflower 1.12.0, mapping-io 0.9.1, tiny-remapper 0.14.1, ASM 9.10.1, Gson 2.14.0; heap 4G.
- Discovery author(s): Codex source worker for this branch.
- Independent reviewer (must differ from discovery authors): pending coordinator assignment.
- Scope constraints: no wiki/MCPK use; no wiki-audit output or old mod implementation/code inspected; no runtime Java implementation. Health/food state production, attack/damage resolution, non-player movement and vehicle physics are excluded. Direct player velocity/impulse/knockback application remains in scope when native triggers cause it; combat cause and damage calculations are not emulated. Direct vanilla-state reads by movement predicates may be recorded without emulating their producer systems. Modern-only blocks do not acquire old behavior.
- Runtime validation: not performed and not authorized. Tests, game/TAS/Gym/server/Docker launches and Gradle builds have not been run.

## Artifact manifest

### A — 1.21.1

- Requested/resolved release and metadata IDs: 1.21.1 / 1.21.1; ready, Mojmap; 5,363 files.
- Source root: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap.
- Client 499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99; mappings 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec; Mojmap jar 6b36d2ccc99e7eeb98e942b6dc093388d66eb6ede06b64238f35a0e26b092abe; source manifest 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48; artifact manifest 09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486; diagnostics 44c184cd1385e5b23991b2698ff0ae9f1e62a9491de8dcac27e5a3f9af88cd4f. Digests independently matched marker.
- Resource/tag comparison open; no absence claim.

### B — 1.21.3

- Requested/resolved release and metadata IDs: 1.21.3 / 1.21.3; ready, Mojmap; 5,655 files.
- Source root: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap.
- Client 25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b; mappings e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41; Mojmap jar 9e5d42c42acaf0076ee92b41b13aa7440667967266ca67ecd50b08e5e4ba8024; source manifest  d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce; artifact manifest 0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf; diagnostics 065c0644d14a4ba2001713ba9a72470b414f01ab327aec772924709f3118f08f. Digests independently matched marker.
- Resource/tag comparison open; no absence claim.

## Blind-discovery freeze

- Status: pending (full-pair freeze only after every source slice closes and independent audit passes)
- Freeze commit/checkpoint and timestamp: pending
- Evidence inventory and finding IDs included at freeze: not frozen
- Confirmation: no old mod implementation, prior source reports, wiki-audit outputs or wikis were consulted.
- Source/mapping hashes covered by freeze: pending

## Correspondence and call order

Partial correspondence: LocalPlayer.tick -> inherited player tick -> LivingEntity.aiStep -> travel -> Entity.move. A checks final bounding box after move; B checks recorded path after travel for server or locally controlled instances. Full graph remains open.

## Required source inventories

Each inventory maps to bounded source slices and remains pending until its full producer/consumer chain closes.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S-INPUT-AXES,S-LOCAL-TICK,S-BLOCK-CONTACT,S-ENTITY-MOVE,S-TRAVEL; evidence=paired source ranges in coverage ledger.
- `INV-STATE` movement state writers/readers: status=pending; slice_ids=S-BLOCK-CONTACT,S-ENTITY-MOVE,S-TRAVEL; evidence=paired source ranges in coverage ledger.
- `INV-COLLISION` player collision/query, shapes, providers, callbacks and neighbors: status=pending; slice_ids=S-BLOCK-CONTACT,S-ENTITY-MOVE; evidence=paired source ranges in coverage ledger.
- `INV-WORLD-MOVEMENT` block/fluid properties, subclasses, registries, data/tags and resources: status=pending; slice_ids=S-BLOCK-CONTACT; evidence=WebBlock and BlockBehaviour ranges in coverage ledger.
- `INV-MODIFIERS` attributes, effects, enchantments and equipment applications: status=pending; slice_ids=S-TRAVEL; evidence=travel ranges in coverage ledger.
- `INV-EXTERNAL` player corrections, pushes, pistons, mounts and launch effects: status=pending; slice_ids=S-LOCAL-TICK,S-ENTITY-MOVE,S-EXTERNAL; evidence=outer tick and move ranges in coverage ledger.
- `INV-EXCLUSIONS` explicit excluded-system audit: status=pending; evidence=scope rules recorded above; full source audit open.

## Coverage ledger

### Slice S-INPUT-AXES: keyboard directional impulse

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: key sampling, directional axis calculation and slow-movement scaling only.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap/net/minecraft/client/player/KeyboardInput.java tick/calculateImpulse lines 5-35, SHA-256 a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0; Input.java lines 5-24, SHA-256 b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/client/player/KeyboardInput.java lines 6-39, SHA-256 2a0994e8a6a2fdf9e76480227216db4a4252734fca8ca0aedd25dde77be75ba6; ClientInput.java lines 6-24, SHA-256 6bc3c2362657c0d5ce889eb1dddb203ddcb185a729d97cf577b6db0fe22abee5.
- State producers/writers -> consumers/readers: key mappings -> keyboard input record -> directional axes -> LocalPlayer.aiStep.
- Parent slices / dependencies / closure evidence: S-LOCAL-TICK; wider input consumers and packet paths remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): narrow directional normalization and slow scaling appear equivalent; B adds sprint to its input record, so whole-input behavior is not claimed.
- Finding IDs or checked absence/replacement path: no finding for this narrow axis calculation.

### Slice S-LOCAL-TICK: local player outer tick

- Inventory ID(s): INV-TICK, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.tick entry through superclass tick and outgoing packet/ambient calls.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java tick lines 191-210, SHA-256 c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java tick lines 189-212, SHA-256 fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0.
- State producers/writers -> consumers/readers: A current-chunk availability guards superclass tick and sends; B has no such guard. World-load/caller path remains open.
- Parent slices / dependencies / closure evidence: S-TRAVEL; exact method bodies and order are paired, but ordinary-client reachability of the guard condition is not closed.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): source behavior differs if the player's current chunk is unavailable; do not generalize beyond the exact condition until reachability is established.
- Finding IDs or checked absence/replacement path: candidate only; no source-confirmed finding.

### Slice S-BLOCK-CONTACT: post-travel swept block effects

- Inventory ID(s): INV-TICK, INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: A per-move current-box callback; B post-travel recorded-path callback; one existing movement-relevant callback and shape provider.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap/net/minecraft/world/entity/Entity.java move lines 598-727 and checkInsideBlocks lines 1001-1031, SHA-256 b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850; WebBlock.entityInside lines 26-33, SHA-256 ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414; Blocks.java registers COBWEB at lines 765-770, SHA-256 538ac50c164484ca4c2367a2a6c07cf2faedcac0c4d5117d8817d36c2d1bde6d.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/world/entity/Entity.java move lines 619-701, applyEffectsFromBlocks lines 736-771, checkInsideBlocks lines 1038-1078, SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; LivingEntity.aiStep lines 2755-2779, SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52; WebBlock body unchanged; Blocks.java registers COBWEB at lines 701-706, SHA-256 28a66da36ce98800226ec6d8e5fb4904fc7b137322a31f6d7ec822450374e62a; BlockBehaviour default inside shape lines 353-355, SHA-256 4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133.
- State producers/writers -> consumers/readers: movement path cells -> shape predicate -> entityInside -> WebBlock.makeStuckInBlock writes stuckSpeedMultiplier -> next Entity.move reads/scales/resets it (A lines 611-615; B lines 633-637).
- Parent slices / dependencies / closure evidence: S-TRAVEL and S-ENTITY-MOVE; other block callback/provider/resource inventory remains open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): if player movement intersects an inside-block shape but ends with no final-box overlap, A final-box scan does not call entityInside while B path scan can. Existing WebBlock writes a movement multiplier, so movement changes under that geometry/velocity precondition.
- Finding IDs or checked absence/replacement path: F-BLOCK-CONTACT-TRAVERSE.

### Slice S-ENTITY-MOVE: collision result to position/support state

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: no-physics, piston limit, stuck multiplier, edge backoff, collision result, position-write guard, collision flags and immediate fall check.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap/net/minecraft/world/entity/Entity.java move lines 598-649, SHA-256 b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/world/entity/Entity.java move lines 619-680, SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9.
- State producers/writers -> consumers/readers: requested vector -> piston/edge transforms -> collision result -> conditional setPos; deltas -> collision flags -> support/fall checks; stuck multiplier read/reset.
- Parent slices / dependencies / closure evidence: S-BLOCK-CONTACT; collision implementation, support query, epsilon consequences and callers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B adds a second position-write condition at requested-length-squared minus actual-length-squared below 1.0E-7; near-zero consequences are unresolved.
- Finding IDs or checked absence/replacement path: pending closure.

### Slice S-TRAVEL: travel branch formulas

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: method correspondence and dispatch only; travel formulas are not closed.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java aiStep lines 2586-2681 and travel lines 2091-2217, SHA-256 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java aiStep lines 2690-2779 and travel lines 2178-2314, SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52.
- State producers/writers -> consumers/readers: input, attributes, fluids, effects, equipment, collision and external velocity feed travel; outputs feed later tick/callbacks.
- Parent slices / dependencies / closure evidence: S-LOCAL-TICK, S-INPUT-AXES, S-BLOCK-CONTACT; formula and operation-order audit required for each branch.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B splits travel into helpers and changes fall-flying call placement; no branch equivalence is claimed.
- Finding IDs or checked absence/replacement path: pending branch audit.

### Slice S-EXTERNAL: corrections and externally supplied movement

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: none closed.
- A evidence: LocalPlayer.tick lines 191-210 and Entity.move piston path; packet handlers and impulse sources remain open.
- B evidence: LocalPlayer.tick lines 189-212 and Entity.move lines 619-680; packet handlers and impulse sources remain open.
- State producers/writers -> consumers/readers: server corrections, impulses, mounts and piston sources -> player movement state; edges not mapped.
- Parent slices / dependencies / closure evidence: S-LOCAL-TICK, S-ENTITY-MOVE; packet/world caller inventory required.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): no absence or equivalence claim.
- Finding IDs or checked absence/replacement path: none.
## Dependency queue and blockers

- Open dependencies: METHOD-INVENTORY, RESOURCE-CHAIN, SOURCE-AUDIT.
- METHOD-INVENTORY: compare LivingEntity.travel branch-by-branch; close Entity.move support/epsilon dependencies and external player inputs.
- RESOURCE-CHAIN: client jar hashes verified; inspect relevant entries before resource/tag claims.
- SOURCE-AUDIT: independent reviewer not yet assigned.
- DEP-CHECKER resolved by canonical fix fba28fa154d29572263ea3f2c44cf1dc23134329, cherry-picked as 4223d9c; static schema gate only.

## Finding index

- F-BLOCK-CONTACT-TRAVERSE — findings/F-BLOCK-CONTACT-TRAVERSE.md.

## Resume checkpoint

- Last completed slices: input axes, local tick guard, block-contact path and initial move-writer comparison.
- Next slice: travel branch math, Entity.move support/epsilon dependencies, external inputs and resource chain.
- Outstanding dependencies: METHOD-INVENTORY, RESOURCE-CHAIN, independent SOURCE-AUDIT.
- Runtime validation not performed.

## Implementation reconciliation

Must remain pending until blind-discovery freeze. Runtime Java implementation has not been inspected and will not be changed by this source-only task.

- Reconciliation status: pending
- Repository revision inspected: not inspected
- Finding -> implementation disposition/evidence: pending freeze
- Existing implementation without a frozen source finding: pending freeze
- Coverage gaps routed back to discovery slices: pending freeze

## Independent source audit

An independent reviewer has not yet been assigned and no audit has occurred.

- Reviewer: pending coordinator assignment; must differ from discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none
- Concrete missed-slice routes (or `none found`): pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Finding snapshots (not pair freeze)

No finding snapshot submitted; independent source-finding review is required before implementation handoff.

## Source audit closure

- Coverage counts: 1 findings, 1 compared-no-difference, 3 in-progress, 1 pending.
- Inventory status: INV-TICK partial; INV-STATE partial; INV-COLLISION partial; INV-WORLD-MOVEMENT partial; INV-MODIFIERS partial; INV-EXTERNAL pending; INV-EXCLUSIONS partial.
- Open dependencies: METHOD-INVENTORY, RESOURCE-CHAIN, SOURCE-AUDIT.
- Full-pair blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed.
