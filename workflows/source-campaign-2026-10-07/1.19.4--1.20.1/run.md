# Discovery: 1.19.4 to 1.20.1

- Status: active
- Scope: direct client-player movement; older A = 1.19.4; newer B = 1.20.1.
- Repository revision and start date: Base main 002137b227676caea77f6832b9f4c8d0b6200bff; task branch feat/source-discovery-movement-source-1-19-4-1-20-1; started 2026-10-07 Europe/Vienna.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap / mojmap on both sides, as required by the source-campaign roster for pairs from 1.14.4 onward. Both exact release IDs, official metadata IDs, namespaces, source/artifact/diagnostic manifest hashes and every source/artifact entry have now been verified.
- Source preparation owner / command / log / readiness marker: campaign source owner is sole writer. A 1.19.4/mojmap and B 1.20.1/mojmap are published and verified. Exact per-side commands, readiness/provenance records and successful logs are recorded below. This worker did not invoke the shared decompiler.
- Toolchain/decompiler/remapper versions and options: pending exact ready/provenance JSON.
- Discovery author(s): /root (delegated source-only worker).
- Independent reviewer (must differ from discovery authors): pending assignment.

## Artifact manifest

The shared protocol root is build/movement-campaign-2026-10-07/. Published Mojmap ready/provenance pairs include 1.14.4, 1.15.2, 1.16.5, 1.17.1, 1.18.2, 1.19.2, 1.19.3 and 1.19.4. A 1.19.4 was fully verified below. No 1.20.1 ready/provenance marker is published yet; the 1.20.1/mojmap folder alone does not establish readiness. Published earlier Feather sources and native 26.2 are unrelated to this exact pair and are not used as comparison evidence.

### A — 1.19.4

- Exact requested/resolved release and official metadata ID: 1.19.4 / 1.19.4, verified in mojmap.ready.json and the successful Gradle log.
- Source root, relative to this run manifest: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/.
- Client jar: ../../../build/movement-campaign-2026-10-07/artifacts/1.19.4/client.jar; SHA-256 0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec; publisher SHA-1 958928a560c9167687bea0cefeb7375da1e552a8.
- CLI mode/namespace: mojmap. Mapping: official Mojang client mappings; mapping file ../../../build/movement-campaign-2026-10-07/artifacts/1.19.4/client_mappings.txt; SHA-256 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606; publisher SHA-1 f14771b764f943c154d3a6fcb47694477e328148. Cached version metadata ../../../build/movement-campaign-2026-10-07/artifacts/1.19.4/version.json; SHA-256 31c80f5545445fe8e09aae83cda8983988517b75a4ea9e9f359f8a2a17850c75; official mapping download identity recorded there.
- Remapped jar: ../../../build/movement-campaign-2026-10-07/artifacts/1.19.4/client-mojmap.jar; SHA-256 efbf38c89b396faae60cfe3bdb91671cb8d1ec3ccf2e27d3ffab4d261acd016d.
- Source manifest ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap.sources.sha256; manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3; all 4,740 entries individually verified against the source tree. Artifact manifest ../../../build/movement-campaign-2026-10-07/ready/1.19.4/artifacts.sha256; SHA-256 e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72; all 69 entries individually verified. Readiness marker ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap.ready.json; SHA-256 59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0. Provenance record ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap.provenance.json; SHA-256 0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34. Movement diagnostics ../../../build/movement-campaign-2026-10-07/ready/1.19.4/movement-diagnostics.txt; SHA-256 61b7a69e1804684d0fc4414d5482d7233de5a7bfe64a19fb5a92571a7a69bcef. Diagnostics verify metadata ID and required classes; movement anchors include LocalPlayer.aiStep, LivingEntity.jumpFromGround/travel/aiStep, Player.aiStep/jumpFromGround/travel and Entity.move/moveRelative; bodies still require slice-level inspection.
- Exact source-owner batch command recorded in provenance: .\gradlew.bat decompileMinecraft --versions=1.19.2,1.19.3,1.19.4 --mappings=mojmap --decompiler-heap=4G --output-root=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\mojmap-1.19.2-to-1.19.4-45d0418942a144298b19fb4ac74ff06a --cache-directory=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts. Successful full log: ../../../build/movement-campaign-2026-10-07/staging/mojmap-1.19.2-to-1.19.4-45d0418942a144298b19fb4ac74ff06a/gradle.full.log; log confirms requested/resolved 1.19.4 and BUILD SUCCESSFUL.
- Toolchain: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; ASM 9.10.1; Gson 2.14.0. Exact run provenance also records 4G decompiler heap.
- Source manifest-relative paths and hashes for the initial pair navigation: LocalPlayer.java 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58; KeyboardInput.java a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0; Input.java b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb; Player.java 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2; Abilities.java a4f952ada7bc3b21406feaf13c171bfa22d36b01e265e3b987612f28b5609edc; LivingEntity.java c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165; Entity.java 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b; AABB.java 594239bb50298d34533fc8efc113f4c4b5312a41d3dc1713cc85130d5020ee5e; VoxelShape.java 99f8b6e44e6c249b251d98a99e38158ebcb459733b26cc98bae15d51d3b87417; BlockBehaviour.java 517aa4bd9875e9f7b3f5fba472a84e82afd1e11e35d3c83dc2677d8bbeb4997d; Blocks.java a4f2df87c2cae5a3827a818f39f7ad123ee5b18905ead9947efe654421c30ff5; FlowingFluid.java d84ceb3c0b83da25fb68599e16cc2298106e745a9ef0c9f32f616d097a8ec56c; FluidState.java 0d6d1d24deb58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c; Attributes.java 28e439335af9ccd2017ff29c24c645e85da1c8193ad07ce20f0f37ce053ff662; MobEffects.java 9266fef24206a32b67e6977580377e8fb92d860932b2eb909756e387e7df943c; Enchantments.java 35fa4d1f9da93971fa70bf6ec2f9218a8825ff343038b51143ae1404867f38e3; EnchantmentHelper.java c98b9e538aae7fea33a125560400f346745bac993e859ffd3912da58f3cc1128; ClientPacketListener.java bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715.

### B — 1.20.1

- Exact requested/resolved release and official metadata ID: 1.20.1 / 1.20.1, verified in mojmap.ready.json, provenance JSON and the successful full Gradle log.
- Source root, relative to this run manifest: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/.
- Client jar: ../../../build/movement-campaign-2026-10-07/artifacts/1.20.1/client.jar; SHA-256 56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f; publisher SHA-1 0c3ec587af28e5a785c0b4a7b8a30f9a8f78f838.
- CLI mode/namespace: mojmap. Mapping: official Mojang client mappings; mapping file ../../../build/movement-campaign-2026-10-07/artifacts/1.20.1/client_mappings.txt; SHA-256 67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d; publisher SHA-1 6c48521eed01fe2e8ecdadbd5ae348415f3c47da. Cached version metadata ../../../build/movement-campaign-2026-10-07/artifacts/1.20.1/version.json; SHA-256 990c243ea1c716b6617839d1db1d2f2f378e8666c1a9a742b136e45d6c3039ea; official mapping download identity recorded there.
- Remapped jar: ../../../build/movement-campaign-2026-10-07/artifacts/1.20.1/client-mojmap.jar; SHA-256 221d543b9f80ca42faaaa8e3f7cebc4fe1879b8f7ba954bb6ca62f8250be8b77.
- Source manifest ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap.sources.sha256; manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465; all 4,786 entries individually verified against the source tree. Artifact manifest ../../../build/movement-campaign-2026-10-07/ready/1.20.1/artifacts.sha256; SHA-256 5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2; all 69 entries individually verified. Readiness marker ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap.ready.json; SHA-256 f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed. Provenance record ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap.provenance.json; SHA-256 5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa. Movement diagnostics ../../../build/movement-campaign-2026-10-07/ready/1.20.1/movement-diagnostics.txt; SHA-256 3eecf5b2c48cb99795874a36a8eb83cfc07866bc909a51c997dbfaac8083f4fd. Diagnostics verify metadata ID and required classes; movement anchors include LocalPlayer.aiStep, LivingEntity.jumpFromGround/travel/aiStep, Player.aiStep/jumpFromGround/travel and Entity.move/moveRelative; bodies still require slice-level inspection.
- Exact source-owner batch command: .\gradlew.bat decompileMinecraft --versions=1.20.1,1.20.2,1.20.4,1.20.6 --mappings=mojmap --decompiler-heap=4G --output-root=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\mojmap-1.20.1-to-1.20.6-3e08bcae66a145b296f7f85b12623999 --cache-directory=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts. Successful full log: ../../../build/movement-campaign-2026-10-07/staging/mojmap-1.20.1-to-1.20.6-3e08bcae66a145b296f7f85b12623999/gradle.full.log; log confirms requested/resolved 1.20.1 and BUILD SUCCESSFUL.
- Toolchain: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; ASM 9.10.1; Gson 2.14.0; 4G decompiler heap.
- Source manifest-relative paths and hashes for pair navigation: LocalPlayer.java 69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2; KeyboardInput.java a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0; Input.java b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb; Player.java 873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac; Abilities.java a4f952ada7bc3b21406feaf13c171bfa22d36b01e265e3b987612f28b5609edc; LivingEntity.java decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f; Entity.java 94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759; AABB.java 594239bb50298d34533fc8efc113f4c4b5312a41d3dc1713cc85130d5020ee5e; VoxelShape.java 99f8b6e44e6c249b251d98a99e38158ebcb459733b26cc98bae15d51d3b87417; BlockBehaviour.java 5f37987191165379c222c412949a0c80a904bf78a453035b1f9d86c6454510fa; Blocks.java 1e7095d1fbfdc8198191f40037997feeca2ec993cc54dd53f2636375cae66b51; FlowingFluid.java ba1dff6ccb28b62760c74d66832d8fdbe7a1cdc37b9c5a0ff476bd200215c904; FluidState.java 0d6d1d24deb58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c; Attributes.java 28e439335af9ccd2017ff29c24c645e85da1c8193ad07ce20f0f37ce053ff662; MobEffects.java 56416cd0ef30da2b92b6fcd37dd0722dc8d18da62b93484726d9e736857a6cdd; Enchantments.java 35fa4d1f9da93971fa70bf6ec2f9218a8825ff343038b51143ae1404867f38e3; EnchantmentHelper.java c98b9e538aae7fea33a125560400f346745bac993e859ffd3912da58f3cc1128; ClientPacketListener.java 8b21ccd4106abb0698f6872250e665ae3f0fc942c8953b9ddd45976660ed624b.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: not frozen.
- Evidence inventory and finding IDs included at freeze: none; exact source pair unavailable.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed. The permitted prior 1.19.4-to-1.20.6 source report was read only for candidate navigation; its findings are not evidence for this run.
- Source/mapping hashes covered by freeze: none.

## Correspondence and call order

Pending exact pair publication. Resolve each logical class/member descriptor, inheritance/caller chain, rename/split/replacement evidence and dependencies independently in both Mojmap trees. No guessed names or copied member correspondence from prior reports will be accepted.

Once sources are ready, inventory the complete reachable local player tick chain: input sampling, local tick and superclass tick; pre-travel predicates/state writes; every reachable travel dispatch and branch; collision/move calls; post-travel callbacks/state writes. Link each relevant movement-state writer to its readers and record exact per-side source ranges and hashes.

## Required source inventories

These inventory rows are mandatory pair-wide maps. Their behavior slices cannot be created or dispositioned until both exact source trees pass ready-manifest and method-body-diagnostic checks.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-01,S1-02,S1-03,S1-04,S1-05,S1-06,S1-PORTAL,S3-01,S3-02,S3-03,S3-04; evidence=source pair verified; S1-01 terminal and remaining tick slices open.
- `INV-STATE` movement state writers/readers: pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, support position, timers and direct predicates: status=pending; slice_ids=S1-02,S1-03,S1-04,S1-05,S1-PORTAL,S2-01,S2-02,S2-03,S4-01,S7-01; evidence=source pair verified; member writer/reader inventory open.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4-01,S4-02,S5-01,S5-02; evidence=source pair verified; full inventory open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S3-03,S5-01,S5-02,S5-03; evidence=source pair verified; registration/resource inventory open.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S1-02,S1-03,S2-02,S3-01,S6-01,S6-02; evidence=source pair verified; chains open.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers: corrections, pushes, pistons, mounts/dismounts and launch inputs: status=pending; slice_ids=S1-PORTAL,S7-01,S7-02,S7-03,S7-04; evidence=source pair verified; packet/writer inventory open.
- `INV-EXCLUSIONS` scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; slice_ids=SCOPE-01; evidence=source pair verified; movement predicates may read vanilla values without emulating their producer systems.

The in-scope audit will preserve exact operation order, casts, float/double boundaries, literal suffixes and historical quirks. Modern-only blocks/features will not acquire old behavior. Other-entity code is in scope only as necessary to explain a direct player movement effect.

## Coverage ledger

### Slice S1-01: Keyboard key-to-impulse conversion and forward-impulse predicate

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: full KeyboardInput.tick conversion and conditional slowdown; Input.tick, getMoveVector and hasForwardImpulse including the strict epsilon comparison. The conclusion does not include LocalPlayer input multipliers or the resulting travel.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/client/player/KeyboardInput.java::net.minecraft.client.player.KeyboardInput#tick(boolean,float), lines 21-36, SHA-256 a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0; ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/client/player/Input.java::net.minecraft.client.player.Input#tick(boolean,float), lines 15-16, #getMoveVector(), lines 18-20, #hasForwardImpulse(), lines 22-24, SHA-256 b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb; caller assignments in ClientPacketListener#handleLogin, lines 427-428, and #handleRespawn, lines 1120-1121; LocalPlayer#serverAiStep, lines 609-617; LivingEntity#aiStep, lines 2546-2550.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/client/player/KeyboardInput.java::net.minecraft.client.player.KeyboardInput#tick(boolean,float), lines 21-36, SHA-256 a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0; ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/client/player/Input.java::net.minecraft.client.player.Input#tick(boolean,float), lines 15-16, #getMoveVector(), lines 18-20, #hasForwardImpulse(), lines 22-24, SHA-256 b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb; caller assignments in ClientPacketListener#handleLogin, lines 427-428, and #handleRespawn, lines 1131-1132; LocalPlayer#serverAiStep, lines 612-620; LivingEntity#aiStep, lines 2564-2572.
- State producers/writers -> consumers/readers: Options directional/jump/shift key states -> KeyboardInput.tick writes up/down/left/right, leftImpulse/forwardImpulse, jumping and shiftKeyDown -> LocalPlayer.aiStep calls Input.tick -> LocalPlayer.serverAiStep copies impulses to xxa/zza and jumping to the living jump state -> LivingEntity.aiStep forms the input Vec3. Later local slowdown, sprint, diagonal movement and travel consumers remain separate slices.
- Parent slices / dependencies / closure evidence: S1-02 LocalPlayer input scaling; S1-03 sprint gates; S3-02 living movement-vector consumer. Per-side LocalPlayer and LivingEntity file hashes are in the artifact manifest.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both complete Input.java and KeyboardInput.java files have identical SHA-256 hashes in the verified manifests. Opposite directional keys map to 0.0F; otherwise impulses are 1.0F/-1.0F; the optional slowdown multiplies both impulses; getMoveVector returns (leftImpulse, forwardImpulse); hasForwardImpulse retains the strict comparison forwardImpulse > 1.0E-5F. This closes only raw key conversion and these helpers, not the reachable player's later transformations.
- Finding IDs or checked absence/replacement path: no difference in the complete checked Input.java and KeyboardInput.java source files (identical per-side hashes); no trajectory claim.

### Slice S1-PORTAL: Local portal-screen and cooldown state

- Inventory ID(s): INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.aiStep caller guard and LocalPlayer.handleNetherPortalClient updates, including processPortalCooldown; client portal-contact consumer and tick reachability still need closure.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep(), lines 645-654, and #handleNetherPortalClient(), lines 810-851, SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep(), lines 648-657, and #handleNetherPortalClient(), lines 816-844, SHA-256 69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2.
- State producers/writers -> consumers/readers: A calls the client helper every AI step; B skips while ReceivingLevelScreen is active. A helper writes portalTime/oPortalTime; B writes spinningEffectIntensity/oSpinningEffectIntensity; both call Entity.processPortalCooldown. Entity.handleInsidePortal reads cooldown and sets portal-contact state. Actual dimension change and server position are supplied through the server/entity path; direct client position/velocity effect is not yet closed.
- Parent slices / dependencies / closure evidence: S1-06 tick/screen reachability; S4-01 portal block contact; S7-01 respawn and position correction; compare Entity.handleInsidePortal and client/server movement boundary.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): code differs while the level-reception screen is active. The helper writes portal presentation/cooldown state and does not directly set local position or velocity, but the relevant caller execution and portal-contact/position dependency chain remains open.
- Finding IDs or checked absence/replacement path: candidate only pending dependency closure; not a confirmed player movement finding.

Both exact Mojmap source pairs and their full manifests have been verified. The first bounded player-input slice is closed below; the portal-screen state slice remains in progress. and their full manifests have been verified. The first bounded player-input slice is closed below; the portal-screen state slice remains in progress.

## Dependency queue and blockers

- D0 — source owner: resolved. Both exact release IDs, Mojmap namespaces, ready/provenance JSON hashes, source/artifact/diagnostic marker hashes and every source/artifact entry match their manifests; both exact release logs end successfully.
- D1 — after D0; originating slices: all inventories. Identify any relevant damaged/missing method body from the diagnostic inventory; request exact-version bytecode/source assistance for the specific member and side.
- D2 — after INV-WORLD-MOVEMENT and INV-MODIFIERS inventories; inspect matching client-jar resource/tag/default entries reached by movement consumers, hash cited uncompressed entries, and identify synchronized/datapack inputs.
- Open dependencies: D1, D2.

## Finding index

No source-confirmed findings. This is not a no-difference conclusion. The 1.19.4-to-1.20.6 prior report is only a navigation aid; its later endpoint does not establish behavior at 1.20.1. All candidates must be rechecked against the exact source pair and complete dependencies.

## Resume checkpoint

- Last completed slice: read current project/workflow/campaign rules; created the dedicated task branch and initial provenance checkpoint; read the updated workflow hardening commits; corrected the status checker’s top-level parser.
- Next bounded slice: continue paired Stage 1 input/tick body comparison and record exact method-range evidence, then resolve its changed callers and state consumers.
- Outstanding dependencies and owners: D0 source owner; D1 and D2 worker follow-ups after pair readiness and inventories.
- Current assumptions requiring verification: both exact releases successfully resolve to themselves; both Mojmap outputs are valid and use aligned release-specific mappings; relevant methods decompile without damage.

## Implementation reconciliation

This is a source-only assignment. Existing/old mod implementation has not been inspected. No reconciliation is authorized or appropriate before blind discovery is frozen; the parent may route a separate integrator after freeze.

- Reconciliation status: pending
- Repository revision inspected: not inspected for implementation.
- Finding -> implementation disposition/evidence: deferred to integrator after freeze.
- Existing implementation without a frozen source finding: not inspected; deferred to integrator.
- Coverage gaps routed back to discovery slices: none yet; source pair is pending.

## Independent source audit

- Reviewer: pending; must differ from discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none; source pair is not published.
- Concrete missed-slice routes (or none found): pending source review.
- Misses routed to slice/finding IDs and owners: pending source review.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 0 pair-comparison slices created; 7 required inventory maps pending; 2 dependencies open. A-side navigation anchors and hashes have been recorded but do not count as paired coverage.
- Required inventory status and evidence: all pending exact source publication; none has member-level evidence yet.
- Open dependencies: D0 source owner; D1 worker method-diagnostic follow-up; D2 worker jar-resource/data audit.
- Unresolved gaps and limits: exact endpoint sources/provenance/diagnostics; all navigation inventories, member correspondences, resources, findings and dependency closure; independent audit.
- Evidence/hash/correspondence audit: no source evidence accepted. Require exact IDs, namespace, manifest hashes and relevant method body diagnostics before comparison.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed; explicitly not authorized by campaign coordinator.
