# Discovery: 1.19.4 to 1.20.1

- Status: active
- Scope: direct client-player movement; older A = 1.19.4; newer B = 1.20.1.
- Repository revision and start date: Base main 002137b227676caea77f6832b9f4c8d0b6200bff; task branch feat/source-discovery-movement-source-1-19-4-1-20-1; started 2026-10-07 Europe/Vienna.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap / mojmap on both sides, as required by the source-campaign roster for pairs from 1.14.4 onward. Both exact release IDs, official metadata IDs, namespaces, source/artifact/diagnostic manifest hashes and every source/artifact entry have now been verified.
- Source preparation owner / command / log / readiness marker: campaign source owner is sole writer. A 1.19.4/mojmap and B 1.20.1/mojmap are published and verified. Exact per-side commands, readiness/provenance records and successful logs are recorded below. This worker did not invoke the shared decompiler.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; ASM 9.10.1; Gson 2.14.0; 4G decompiler heap on both sides.
- Discovery author(s): /root (delegated source-only worker).
- Independent reviewer (must differ from discovery authors): pending assignment.

## Artifact manifest

The shared protocol root is build/movement-campaign-2026-10-07/. Published Mojmap ready/provenance pairs include both exact endpoints 1.19.4 and 1.20.1; both readiness markers and provenance records are fully verified below. Published earlier Feather sources and native 26.2 are unrelated to this exact pair and are not used as comparison evidence.

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
- Evidence inventory and finding IDs included at freeze: pending freeze; current working ledger has S1-01, S1-02, S1-03, S1-04, S1-05, S1-PORTAL, S3-01.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed. The permitted prior 1.19.4-to-1.20.6 source report was read only for candidate navigation; its findings are not evidence for this run.
- Source/mapping hashes covered by freeze: pending freeze; paired readiness and full manifest hashes are recorded in Artifact manifest.

## Correspondence and call order

Paired correspondence is partially mapped for the local input and ground-jump path. Continue resolving each logical method, caller chain, state edge and dependency independently in the two verified Mojmap trees.

Once sources are ready, inventory the complete reachable local player tick chain: input sampling, local tick and superclass tick; pre-travel predicates/state writes; every reachable travel dispatch and branch; collision/move calls; post-travel callbacks/state writes. Link each relevant movement-state writer to its readers and record exact per-side source ranges and hashes.

## Required source inventories

These rows are mandatory pair-wide maps. Exact endpoint readiness, manifests and method-body diagnostics pass; each map remains incomplete until its reachable members and dependencies have terminal evidence.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-01,S1-02,S1-03,S1-04,S1-05,S1-06,S1-PORTAL,S3-01,S3-02,S3-03,S3-04; evidence=paired raw-input and pre-travel slices S1-01/S1-02 terminal, S3-01 finding F-01; remainder open.
- `INV-STATE` movement state writers/readers: pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, support position, timers and direct predicates: status=pending; slice_ids=S1-02,S1-03,S1-04,S1-05,S1-PORTAL,S2-01,S2-02,S2-03,S4-01,S7-01; evidence=F-01 jump-velocity writer/reader path recorded; remaining state inventory open.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4-01,S4-02,S5-01,S5-02; evidence=Entity support-block selection and its movement-factor consumer route recorded in S4-01/F-02; remaining shape/query paths open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S3-03,S5-01,S5-02,S5-03; evidence=block jump/speed-factor read sites linked in S4-01/F-02; block/fluid registrations, resources and tags remain open.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S1-02,S1-03,S2-02,S3-01,S6-01,S6-02; evidence=F-01 reads Jump Boost amplifier; broader writer/application chains open.
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


### Slice S1-02: LocalPlayer pre-travel input transforms

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer#aiStep input preparation through super.aiStep, isolating portal helper guard as S1-PORTAL; full method comparison normalizes only level and onGround API accessors.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java #aiStep lines 645-795, SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58; Player#aiStep lines 499-517, SHA-256 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java #aiStep lines 648-801, SHA-256 69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2; Player#aiStep lines 500-518, SHA-256 873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac.
- State producers/writers -> consumers/readers: input key state -> LocalPlayer slowdown and use-item scaling, jump timer, sprint/flight/fluid/riding gates and vertical input -> Player.aiStep -> LivingEntity.aiStep.
- Parent slices / dependencies / closure evidence: S1-01 raw input; S1-PORTAL screen guard; S3-01/S3-02 travel consumers; modifier and exclusion inputs remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): after level and onGround accessor normalization, the LocalPlayer.aiStep bodies match except for the separately bounded portal-helper guard. Sneak slowdown, input tick, use-item multiplier, jump timer, sprint/flight/fluid/riding branches and super call order match. This closes pre-travel processing only.
- Finding IDs or checked absence/replacement path: no pre-travel delta; F-01 is downstream jump-power arithmetic.

### Slice S1-03: Local sprint-start predicates

- Inventory ID(s): INV-TICK, INV-MODIFIERS, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer#canStartSprinting, #vehicleCanSprint, #hasEnoughImpulseToStartSprinting and LocalPlayer#aiStep call site.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java #aiStep lines 653-686 and #canStartSprinting lines 1022-1039, SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java #aiStep lines 659-692 and #canStartSprinting lines 1014-1031, SHA-256 69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2.
- State producers/writers -> consumers/readers: forward impulse/underwater state, sprinting/use-item/blindness/fall-flying, food predicate and passenger control -> sprint attempt -> travel speed.
- Parent slices / dependencies / closure evidence: S1-01, S1-02, S3 travel; modifier and exclusion inventories remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): bounded predicate bodies compare equal after onGround API normalization, including the 0.8 threshold and underwater switch to Input#hasForwardImpulse. Food/effect producers and travel consumer remain open.
- Finding IDs or checked absence/replacement path: no LocalPlayer predicate delta in this slice.

### Slice S1-04: Auto-jump movement probe

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer#move, #updateAutoJump, #isHorizontalCollisionMinor, #canAutoJump and #isMoving, including call after super.move.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java #move lines 880-885, #updateAutoJump lines 891-987, #canAutoJump lines 1007-1015, SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java #move lines 872-877, #updateAutoJump lines 883-979, #canAutoJump lines 999-1007, SHA-256 69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2.
- State producers/writers -> consumers/readers: Entity.move displacement -> float X/Z probe and collision-shape iterator -> autoJumpTime=1 -> next LocalPlayer#aiStep jump state.
- Parent slices / dependencies / closure evidence: S3-01, S4-01/S4-02 and S5-01, including providers, tags and neighboring collision inputs.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): checked method bodies match after level/onGround accessor refactor, including constants and arithmetic order. Shape providers, block jump factors and neighborhood inputs remain open.
- Finding IDs or checked absence/replacement path: no local auto-jump algorithm delta observed; dependency slices remain open.

### Slice S1-05: Client tick and movement packet emission

- Inventory ID(s): INV-TICK, INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer#tick, #sendPosition and #sendIsSprintingIfNeeded, including controlled-camera guard, movement/rotation thresholds, packet branch order and sent-state writes.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java #tick lines 187-206, #sendPosition lines 218-268, SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java #tick lines 189-208, #sendPosition lines 220-270, SHA-256 69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2.
- State producers/writers -> consumers/readers: local position, angles, onGround, sprint/sneak and input -> serverbound packet fields; inbound corrections/velocity writes are in S7-01.
- Parent slices / dependencies / closure evidence: S1-06 superclass tick; S7-01 inbound corrections; S7-02 pushes/pistons.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): bodies match after onGround accessor normalization; thresholds, packet order and sent-state updates match. Packet consumers and external movement writers remain open.
- Finding IDs or checked absence/replacement path: no emission delta in this bounded slice.

### Slice S3-01: Ground-jump upward velocity with Jump Boost

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: LivingEntity#getJumpPower, #getJumpBoostPower, #jumpFromGround, jump caller in #aiStep, reached through Player#jumpFromGround.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java #getJumpPower/#getJumpBoostPower lines 1947-1953, #jumpFromGround lines 1955-1965, #aiStep jump caller lines 2512-2537, SHA-256 c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165; Player#jumpFromGround lines 1429-1437, SHA-256 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java #getJumpPower/#getJumpBoostPower lines 1969-1975, #jumpFromGround lines 1977-1986, #aiStep jump caller lines 2531-2556, SHA-256 decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f; Player#jumpFromGround lines 1435-1443, SHA-256 873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac.
- State producers/writers -> consumers/readers: MobEffects.JUMP amplifier -> getJumpBoostPower -> ground-jump branch in LivingEntity#aiStep -> virtual Player#jumpFromGround -> LivingEntity#jumpFromGround writes deltaMovement.y; LocalPlayer#aiStep supplies jump input through the superclass chain.
- Parent slices / dependencies / closure evidence: S1-01/S1-02 input/call chain; effect application; S5-01 block-jump-factor source.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): A computes a float base power and double Jump Boost power, adds them as double into local $$0, then assigns velocity. B folds the boost into float-returning getJumpPower(); 0.42F * getBlockJumpFactor() + getJumpBoostPower() is rounded as float before the setter widens it to double. Player#jumpFromGround delegates to this superclass method in both versions. Under a reachable ground jump with Jump Boost, the upward delta can differ by float rounding; no trajectory is claimed.
- Finding IDs or checked absence/replacement path: F-01; first changed release unknown within this pair.



### Slice S4-01: Ground support position feeds player jump and speed factors

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Entity#move ground-state write, B Entity#checkSupportingBlock and CollisionGetter#findSupportingBlock, Entity#getBlockPosBelowThatAffectsMyMovement, #getBlockJumpFactor and #getBlockSpeedFactor.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/world/entity/Entity.java, #move lines 585-599, #getOnPos lines 720-742, #getBlockPosBelowThatAffectsMyMovement/#getBlockJumpFactor/#getBlockSpeedFactor lines 744-762; SHA-256 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/world/entity/Entity.java, #move lines 629-643, #setOnGroundWithKnownMovement/#checkSupportingBlock lines 553-585, #getOnPos/#getBlockPosBelowThatAffectsMyMovement lines 786-815, #getBlockJumpFactor/#getBlockSpeedFactor lines 817-831; SHA-256 94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759. CollisionGetter#findSupportingBlock lines 95-110, SHA-256 8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130.
- State producers/writers -> consumers/readers: Entity.move collision result -> onGround and B mainSupportingBlockPos via support-AABB query -> getBlockPosBelowThatAffectsMyMovement -> getBlockJumpFactor/getBlockSpeedFactor -> LivingEntity jump power, ground friction and post-move horizontal velocity damping.
- Parent slices / dependencies / closure evidence: S1-04 auto-jump; S3-01 jump-power consumer; S4-02 BlockCollisions, shape and support-query details; S5-01 block factors and existing block/provider registrations; D2 resource/tag input audit.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): A computes the movement-factor position from player position.x/z and boundingBox.minY - 0.5000001. B stores a support block selected under the feet, then getBlockPosBelowThatAffectsMyMovement calls getOnPos(0.500001F), which uses stored support X/Z when available. checkSupportingBlock is reached after grounded movement; CollisionGetter scans block collisions under a thin feet AABB and chooses the candidate nearest the entity position. If that selected block differs from A's center-column block and its jump or speed factor is non-default/different, B supplies a different input to reachable player movement math. Concrete terrain/factor outcomes remain open under S4-02/S5-01.
- Finding IDs or checked absence/replacement path: F-02, source-confirmed changed support-position input path; exact affected blocks and trajectories not claimed.


Both exact Mojmap source pairs and full manifests are verified. S1-01 and S1-02 are terminal, S3-01 records a source-confirmed finding, and portal/collision/external traces remain open.

## Dependency queue and blockers

- D0 — source owner: resolved. Both exact release IDs, Mojmap namespaces, ready/provenance JSON hashes, source/artifact/diagnostic marker hashes and every source/artifact entry match their manifests; both exact release logs end successfully.
- D1 — after D0; originating slices: all inventories. Identify any relevant damaged/missing method body from the diagnostic inventory; request exact-version bytecode/source assistance for the specific member and side.
- D2 — after INV-WORLD-MOVEMENT and INV-MODIFIERS inventories; inspect matching client-jar resource/tag/default entries reached by movement consumers, hash cited uncompressed entries, and identify synchronized/datapack inputs.
- Open dependencies: D1, D2.

## Finding index

- F-01: Jump Boost is summed into ground-jump power at float precision (source-confirmed; see findings/F-01-jump-boost-rounding.md).
- F-02: Movement factors use the tracked supporting block in 1.20.1 (source-confirmed input-path change; see findings/F-02-supporting-block-factor-position.md).

This is not a complete or no-difference conclusion. Prior reports are navigation only; evidence here comes from the exact 1.19.4/1.20.1 Mojmap pair.

## Resume checkpoint

- Last completed slices: verified both exact endpoints; closed S1-01/S1-02; recorded F-01 jump precision and F-02 support-position source findings.
- Next bounded slice: trace the LocalPlayer superclass tick chain and close S1-03 sprint predicate dependencies, then continue LivingEntity travel and collision source comparisons.
- Outstanding dependencies and owners: D2 resource/tag audit after block/fluid consumer inventory; reviewer assignment and all open movement slices.
- Current assumptions requiring verification: portal-screen/cooldown effects, sprint modifier inputs, remaining travel/collision members, resources, data and tags; independent review remains unassigned.

## Implementation reconciliation

This is a source-only assignment. Existing/old mod implementation has not been inspected. No reconciliation is authorized or appropriate before blind discovery is frozen; the parent may route a separate integrator after freeze.

- Reconciliation status: pending
- Repository revision inspected: not inspected for implementation.
- Finding -> implementation disposition/evidence: deferred to integrator after freeze.
- Existing implementation without a frozen source finding: not inspected; deferred to integrator.
- Coverage gaps routed back to discovery slices: LocalPlayer portal state (S1-PORTAL), sprint/modifier dependencies (S1-03), auto-jump shape inputs (S1-04), packet consumers (S1-05), and collision/support/world data (S4/S5/S7) remain open.

## Independent source audit

- Reviewer: pending; must differ from discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: pending independent reviewer; exact source pair is published and verified.
- Concrete missed-slice routes (or none found): pending source review.
- Misses routed to slice/finding IDs and owners: pending source review.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 2 compared-no-difference slices (S1-01, S1-02); 2 findings slices (S3-01, S4-01); 4 in-progress slices (S1-PORTAL, S1-03, S1-04, S1-05); remaining planned slices not yet created; 7 required inventory maps incomplete; 1 resource/tag dependency and independent audit remain open.
- Required inventory status and evidence: all seven maps remain incomplete; exact-pair member evidence is recorded for bounded S1-01/S1-02/S1-03/S1-04/S1-05/S3-01/S4-01 slices.
- Open dependencies: D2 worker jar-resource/data audit after relevant consumers are inventoried; independent source audit and remaining slice closure.
- Unresolved gaps and limits: remaining navigation inventories, member correspondences, resources, findings and dependency closure; independent audit.
- Evidence/hash/correspondence audit: exact endpoint IDs, Mojmap namespaces, ready/provenance/source/artifact/diagnostic hashes, complete manifests and source range hashes verified; remaining member maps are open.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed; explicitly not authorized by campaign coordinator.
