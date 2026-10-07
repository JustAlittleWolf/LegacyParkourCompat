# Discovery: 1.19.4 to 1.20.1

- Status: active
- Scope: direct client-player movement; older A = 1.19.4; newer B = 1.20.1.
- Repository revision and start date: Base main 002137b227676caea77f6832b9f4c8d0b6200bff; task branch feat/source-discovery-movement-source-1-19-4-1-20-1; started 2026-10-07 Europe/Vienna.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap / mojmap on both sides, as required by the source-campaign roster for pairs from 1.14.4 onward; endpoint artifact alignment and exact mapping builds remain unverified pending ready markers.
- Source preparation owner / command / log / readiness marker: campaign source owner is sole writer. A is published and verified as 1.19.4/mojmap; B exact 1.20.1/mojmap is requested but not yet published. The source worker will not invoke the shared decompiler.
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

- Exact release/source root/client jar SHA-256: pending 1.20.1/mojmap ready and provenance JSON.
- CLI mode/mapping coordinate/build/path/hash/mapped jar SHA-256: Mojmap requested; exact values pending source-owner publication.
- Cited source/resource paths and SHA-256 hashes: none accepted yet.
- Required external data and provenance: pending dependency inventory.

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

- INV-TICK input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=pending source pair; evidence=pending.
- INV-STATE movement state writers/readers: pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, support position, timers and direct predicates: status=pending; slice_ids=pending source pair; evidence=pending.
- INV-COLLISION player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=pending source pair; evidence=pending.
- INV-WORLD-MOVEMENT block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=pending source pair; evidence=pending.
- INV-MODIFIERS movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=pending source pair; evidence=pending.
- INV-EXTERNAL player-only externally supplied movement inputs and client consumers: corrections, pushes, pistons, mounts/dismounts and launch inputs: status=pending; slice_ids=pending source pair; evidence=pending.
- INV-EXCLUSIONS scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=pending; movement predicates may read vanilla values without emulating their producer systems.

The in-scope audit will preserve exact operation order, casts, float/double boundaries, literal suffixes and historical quirks. Modern-only blocks/features will not acquire old behavior. Other-entity code is in scope only as necessary to explain a direct player movement effect.

## Coverage ledger

No member-level comparison slices exist yet because neither required exact ready manifest has been published. The source request is open under dependency D0. Directory presence or previous-report claims will not be treated as readiness or coverage.

## Dependency queue and blockers

- D0 — source owner; originating scope: pair provenance. A 1.19.4/mojmap ready/provenance pair and all cited source/artifact/diagnostic manifest hashes are verified. Publish the remaining exact 1.20.1/mojmap ready JSON plus provenance/source/artifact SHA-256 manifests, requested/resolved IDs, successful decompiler record, exact mapping and method-body diagnostics. These records are required to trust and cite the B source. Next action: consume B publication read-only, verify exact ID/namespace/hashes and relevant body diagnostics. This is an active external dependency, not a mapping blocker.
- D1 — after D0; originating slices: all inventories. Identify any relevant damaged/missing method body from the diagnostic inventory; request exact-version bytecode/source assistance for the specific member and side.
- D2 — after INV-WORLD-MOVEMENT and INV-MODIFIERS inventories; inspect matching client-jar resource/tag/default entries reached by movement consumers, hash cited uncompressed entries, and identify synchronized/datapack inputs.
- Open dependencies: D0, D1, D2.

## Finding index

No source-confirmed findings. This is not a no-difference conclusion. The 1.19.4-to-1.20.6 prior report is only a navigation aid; its later endpoint does not establish behavior at 1.20.1. All candidates must be rechecked against the exact source pair and complete dependencies.

## Resume checkpoint

- Last completed slice: read current project/workflow/campaign rules; created the dedicated task branch and initial provenance checkpoint; read the updated workflow hardening commits; corrected the status checker’s top-level parser.
- Next bounded slice: verify 1.20.1/mojmap ready and provenance JSON, all referenced hashes and relevant movement-body diagnostics; then use the verified A anchors to resolve paired Stage 1 input/tick class/member correspondence and call order.
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

- Coverage counts by status: 0 pair-comparison slices created; 7 required inventory maps pending; 3 dependencies open. A-side navigation anchors and hashes have been recorded but do not count as paired coverage.
- Required inventory status and evidence: all pending exact source publication; none has member-level evidence yet.
- Open dependencies: D0 source owner; D1 worker method-diagnostic follow-up; D2 worker jar-resource/data audit.
- Unresolved gaps and limits: exact endpoint sources/provenance/diagnostics; all navigation inventories, member correspondences, resources, findings and dependency closure; independent audit.
- Evidence/hash/correspondence audit: no source evidence accepted. Require exact IDs, namespace, manifest hashes and relevant method body diagnostics before comparison.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed; explicitly not authorized by campaign coordinator.
