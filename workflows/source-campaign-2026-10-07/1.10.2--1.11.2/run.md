# Discovery: 1.10.2 to 1.11.2

- Status: active
- Scope: source-only comparison of reachable client-player movement for exact A=1.10.2 and B=1.11.2. Endpoint differences do not establish a first changed release.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff`; campaign date 2026-10-07; report branch `feat/source-discovery-movement-source-1-10-2-1-11-2`.
- Evidence integrity status: worker reverified both immutable Feather r1 derived-artifact snapshots, both source manifests and every listed Java source file, both original artifact manifests and all raw inputs, readiness markers, diagnostics, and the shared verification-log hash. The original mapped jars are unavailable; revised snapshot hashes differ, so artifact identity and metadata-only change are unproven. Independent ops verification remains pending; findings are provisional and the pair remains unfrozen.
- Selected naming namespace, CLI mode per side and alignment evidence: `ornithe-feather` / `--mappings=feather` for both. Exact metadata IDs match requested IDs; both use release-specific Feather Gen2 build 2 mappings. Both source trees have 1,845 / 1,921 files and verified manifests. This establishes aligned naming convention, not member correspondence by itself.
- Source preparation owner / command / log / readiness marker: shared source owner published the trees; worker did not decompile or write shared source/cache. Both provenance files record `.\gradlew.bat decompileMinecraft --versions=1.10.2,1.11.2,1.12.2 --mappings=feather --decompiler-heap=4G --output-root=... --cache-directory=...` (batch includes 1.12.2). Successful logs are `build/movement-campaign-2026-10-07/ready/<version>/ornithe-feather.success.log`; markers are `.../ready/<version>/ornithe-feather.ready.json`. Requested/resolved IDs are exact.
- Toolchain/decompiler/remapper versions and options: Java 25.0.3+9-LTS; Gradle 9.7.1; Vineflower 1.12.0; ASM 9.10.1; mapping-io 0.9.1; Gson 2.14.0; Tiny Remapper 0.14.1; decompiler heap 4G.
- Discovery author(s): Codex source worker, 2026-10-07.
- Independent reviewer (must differ from discovery authors): not assigned / pending.

## Artifact manifest

All paths below are repository-relative; generated sources and artifacts remain outside this tracked report. Publisher/client jar hashes are separately recorded as supplied in the owner's SHA256 manifest. At initial publication the worker verified readiness/provenance, source and artifact manifest hashes, all 3,766 source files, all 74 artifact entries and source counts. A later reproducibility rerun replaced the derived mapped jars; the original mapped artifacts are unavailable. The current immutable replacement snapshots and all unchanged source/raw inputs were independently rehashed by this worker as described below. Published source trees and shared cache remained read-only.

| Side | Release / source root | Client jar SHA-256 | Namespace / CLI mode | Mapping coordinate; mapping file SHA-256 | Remapped client jar SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Diagnostics SHA-256 |
|---|---|---|---|---|---|---|---|---|
| A | 1.10.2; `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/` | `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9` | `ornithe-feather`; `feather` | `feather-gen2-1.10.2+build.2`; `yarn/feather-gen2-1.10.2+build.2.tiny`, `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1` | `client-ornithe-feather.jar`, `ab7aa536f52e94c4c099999bc731020c11a1b29e2979a566cedcc2ffb00e701d` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116` | `3a8622864d424ff3ddd4d55febaf453d4201c8f1e05638c758616cea5443ab9e` |
| B | 1.11.2; `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather/` | `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2` | `ornithe-feather`; `feather` | `feather-gen2-1.11.2+build.2`; `yarn/feather-gen2-1.11.2+build.2.tiny`, `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` | `client-ornithe-feather.jar`, `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f` | `8bb3b1169a21958a6570fc304a0a95d9c51cc3a2101869bcbd453c3588925cdc` |

The mapped-client hashes in the table are the original values retained in the unchanged artifact manifests; the original mapped jars are unavailable. The replacement snapshot hashes are recorded and freshly verified below. The full mapped-jar entry is not needed for cited Java-source evidence in this partial report. Mapping jar hashes: A `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`; B `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`. Exact shared-source readiness and diagnostics were verified on resume; diagnostics show required movement classes and no reported relevant decompiler failure. This is not proof that every body in either tree is decompiled correctly.

Revised derived-artifact snapshots (revision `feather-r1-2026-10-07`): A `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.10.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`; B `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`. Worker verification on 2026-10-07 matched each snapshot to both `artifact.sha256` and `revision.json`; matched each revision's source-manifest/original-artifact-manifest hashes to the unchanged ready records; rehashed all 1,845 / 1,921 source files and all 36 raw artifact entries per side; and matched the verification log hash `33b732892a03ffac60026663f7266c20b637330db6d480b4260267861bebd40c`. Original derived JARs are marked unavailable by the owner. Snapshots therefore do not prove identity with the originals or that changes are metadata-only. Independent ops verification is still pending. Cite revision `feather-r1-2026-10-07` in any accepted finding snapshot that relies on these regenerated derivations.

Cited source hash inventory (SHA-256; relative roots are the source roots in the table):

- `net/minecraft/entity/Entity.java`: A `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`; B `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`.
- `net/minecraft/entity/living/LivingEntity.java`: A `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`; B `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`.
- `net/minecraft/entity/living/player/PlayerEntity.java`: A `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`; B `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: A `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`; B `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`.
- `net/minecraft/client/entity/living/player/Input.java`: both `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` (identical source content).
- `net/minecraft/client/entity/living/player/KeyboardInput.java`: both `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`.
- `net/minecraft/world/World.java`: A `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`; B `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58`.
- `net/minecraft/block/entity/MovingBlockEntity.java`: A `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1`; B `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e`.
- `net/minecraft/block/Block.java`: A `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`; B `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`.
- `net/minecraft/block/RedstoneOreBlock.java`: A `0691a8df1e7ceac85ca0ec9298706b225ce5669541757d15333f2c4019a1de82`; B `5d200f042a07ad928f1f38b9d7464a1a019f53ca7e63b9f23036dc71f8939ecd`.
- `net/minecraft/block/SlimeBlock.java`: both `38e77cdaaf3681fe3a2357ebc0dce7a86d658429463bc1e06e6e1167fe627c8a`.
- `net/minecraft/block/MagmaBlock.java`: both `6e382a4fc307391c6776dc2ff010aec1da16b50ebc50e015b6009d82c7b76b1a`.
- `net/minecraft/block/StairsBlock.java`: A `ee143aced2a6902563cddbae77e78d1d1a512244f278987684ebe5bcacba37cb`; B `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627`.
- `net/minecraft/entity/EntityFilter.java`: A `91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813`; B `49f2c3cbeea0bb9421fdfaad49a7742394f69cd73981a9cdd51e1badcf30c629`.
- `net/minecraft/world/World.java`: A `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`; B `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58`.
- `net/minecraft/client/entity/living/player/RemoteClientPlayerEntity.java`: both `e7e248b439f7356695b1bf196d08e997bb19b24c23b206763c772663ee7086a1`.
- `net/minecraft/util/math/Box.java`: A `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155`; B `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a`.
- `net/minecraft/entity/EntityFilter.java` and `net/minecraft/util/math/Box.java` hashes are recorded above; broader query/provider and call-site closure remains open. `net/minecraft/item/ItemStack.java`: A `a41054235eac09212065f7e20d4206aa7d83cdd159cebbc77eb8b3d1e43ab842`; B `dc929fcc42e94dacb1f2d2a32572c00612c4f9b39d4cb551634fd3dd290467d8`. `net/minecraft/entity/living/player/PlayerInventory.java`: A `eeb156c483ad1c3cb52b58a197f7321817b8c52c2cf35f4995a5758dc7871842`; B `cf956f7410c6911d2e978d2072a7d53590e4800c398687afeca6f771e74e19d6`. `net/minecraft/item/ElytraItem.java`: A `20db6fd5b7438c4957c19565e593d79497a4712d9ce71fd5af0a9a4a61ad8cf2`; B `16e02a70181c063c0643b7dfdbe17516ad46cd38053a2d88cc3fb97400652c7c`. `net/minecraft/block/state/StateDefinition.java`: A `00fea8cdf8a0cabf1af21e7e7ff47f071bd87a16f91697a3b31efce6c78bfda4`; B `95704c944742efb2ec9cba7eb73399a330195556e669451131ba542e6a9fac65`.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending; this report is not frozen.
- Evidence inventory and finding IDs included at freeze: pending.
- Confirmation old mod implementation/code and wiki-audit results were not opened before freeze: confirmed. Prior source-discovery reports were used only as navigation leads. No wiki/MCPK was opened.
- Source/mapping hashes covered by freeze: pending.

## Correspondence and call order

Verified initial correspondence: `Input.tick` / `Input.getMovement` and `KeyboardInput.tick` are identical by file hash. `LocalClientPlayerEntity.mobTick` is the direct local movement entry: it samples input, updates local player pre-travel flags, then calls `LivingEntity.mobTick`; `LivingEntity.mobTick` performs the locally controlled `serverTickAi`, jump cooldown/input handling, `flyingTick`, `moveRelative`, and post-travel entity pushing. `PlayerEntity.serverTickAi` sets `headYaw = yaw` before local travel. `LivingEntity.moveRelative` resolves to `Entity.move`; A's `move(double,double,double)` corresponds to B's `move(MoverType,double,double,double)`. B's piston caller supplies `MoverType.PISTON`; ordinary LivingEntity travel supplies `MoverType.SELF`. This is only the inspected path prefix, not a complete tick graph.

Other correspondence requiring further walk: remote/client corrections and packet application; riding and dismount; abilities and item-use state; all travel branch helpers; fluids and environmental callbacks; world collision query/shape production; state writers, registries, attributes/effects/equipment, and resource data. `LocalClientPlayerEntity.getRotationVector(float)` exists only in B; local `serverTickAi` writes `headYaw=yaw` before the confirmed locally controlled fall-flying travel call, but other callers and complete call ordering remain open.

## Required source inventories

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-input,S1-local-order,S3-travel,S3-post; evidence=`Input.java` lines 15-19 (both), `LocalClientPlayerEntity.mobTick` A 620-790 / B 632-802, `LivingEntity.mobTick` A 1681-1752 / B 1737-1808; only input method identity and partial ordering have been checked.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S2-resize,S2-eye-height,S2-state-writers,S3-jump,S4-collision-flags; evidence=`Entity.java` A 264-274 / B 271-287, and move bodies A 446-700 / B 459-724; only resize logic and part of collision are reviewed.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4-sneak-probe,S4-move-order,S4-step,S4-callbacks,S5-shapes; evidence=`Entity.java` A 468-512 / B 519-562 for the sneak probe and `World.getCollisions` A line 903 / B lines 960-1013; full providers/registrations not inventoried.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S5-blocks,S5-fluids,S5-resources; no source/resource inventory yet. Relevant `MovingBlockEntity` A 91-183 / B 116-285 identified.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S6-attributes,S6-effects,S6-enchantments,S6-equipment; `LivingEntity` has `flyingTick` A 1754-1773 / B 1810-1830, with Elytra read at A 1757 / B 1813. Dependencies/data remain open.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S7-piston,S7-pushability,S7-corrections,S7-mounts; direct piston caller is `MovingBlockEntity` B 116-160 and 210-242; A caller is 91-159. Corrections and mount graph remain open.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=complete for scope declaration only; evidence=campaign contract and the `LocalClientPlayerEntity`/`LivingEntity` tick boundary above. Movement predicates that read food/sprint state remain in scope; this report does not emulate food, health, exhaustion, or damage producers. Non-player movement is not researched. Revisit if call-chain review finds an excluded producer mixed into a movement operation.

## Coverage ledger

### Slice S1-input: keyboard sampling and vector accessor identity

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: full `Input.tick`, `Input.getMovement`, and `KeyboardInput.tick`; called from local player's `mobTick` after the movement-update gates and before inherited living travel.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/net/minecraft/client/entity/living/player/Input.java::Input#tick/getMovement`, lines 15-19, SHA-256 `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e`; `KeyboardInput.java::KeyboardInput#tick`, lines 13-50, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/Input.java::Input#tick/getMovement`, lines 15-19, SHA-256 `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e`; `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java::KeyboardInput#tick`, lines 13-50, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; both are byte-identical to A.
- State producers/writers -> consumers/readers: key bindings -> forward/sideways/jump/sneak flags -> `LocalClientPlayerEntity.mobTick`, `LivingEntity.moveRelative`, and edge-probe predicate.
- Parent slices / dependencies / closure evidence: input producer only; consumer/caller graph is partial. No equivalence claim outside these input bodies.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): exact paired source files are byte-identical; method behavior is identical for these input sampling/accessor bodies.
- Finding IDs or checked absence/replacement path: no finding; local caller identified, remaining downstream calls stay in S1-local-order/S3-travel.

### Slice S1-local-order: local pre-travel state and inherited travel dispatch

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalClientPlayerEntity.mobTick` input/pre-travel segment and `LivingEntity.mobTick` through `moveRelative`; not the complete bodies.
- A evidence: `LocalClientPlayerEntity.java::mobTick`, lines 620-790, SHA-256 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`; `LivingEntity.java::mobTick`, lines 1681-1752, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`.
- B evidence: corresponding `LocalClientPlayerEntity.java::mobTick`, lines 632-802, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; `LivingEntity.java::mobTick`, lines 1737-1808, SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`.
- State producers/writers -> consumers/readers: local input and sprint/flight/riding flags -> inherited jump gate, `flyingTick`, `moveRelative`, movement call; inherited serverTickAi writes headYaw before locally controlled travel.
- Parent slices / dependencies / closure evidence: S1-input; open S2/S3/S6/S7 dependencies and all callers.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): paired methods are broadly similar; audited hunks include one changed null-to-empty Elytra guard and a movement signature change. Complete operation-level comparison and helper dependencies are not closed.
- Finding IDs or checked absence/replacement path: candidate API/null guard is not a finding pending empty-stack and Elytra trace; movement signature is traced to S4/S7.

### Slice S3-jump-impulse: base living jump math

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: protected `LivingEntity.jump` impulse assignments, excluding `PlayerEntity.jump` exhaustion/fatigue changes.
- A evidence: `LivingEntity.java::jump`, lines 1305-1318, SHA-256 above.
- B evidence: `LivingEntity.java::jump`, lines 1361-1374, SHA-256 above.
- State producers/writers -> consumers/readers: jump gate/cooldown and jump effect -> velocityY, sprint horizontal velocity, dirty velocity flag -> `Entity.move`.
- Parent slices / dependencies / closure evidence: S1-local-order; Jump Boost/equipment and full caller predicates remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): inspected formulas and operation order match: base `0.42F`, Jump Boost `(amplifier+1)*0.1F`, sprint impulse based on yaw with `0.2F`; upstream and effect-data closure is not claimed.
- Finding IDs or checked absence/replacement path: no finding for this bounded impulse body; PlayerEntity's additional cost path is excluded and separately inventoried.

### Slice S4-sneak-probe: grounded-player horizontal edge restraint

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Entity.move` after cobweb scaling, before the movement collision list; grounded + sneaking + player guard, x then z then combined x/z probes, 0.05 trimming loop.
- A evidence: `Entity.java::move(double,double,double)`, lines 446-512, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.
- B evidence: `Entity.java::move(MoverType,double,double,double)`, lines 459-562, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`.
- State producers/writers -> consumers/readers: local sneak/ground flags and stepHeight -> probe box -> world block/entity collision query -> retained requested x/z -> position/collision flags.
- Parent slices / dependencies / closure evidence: S1-local-order; local player inheritance is LocalClientPlayerEntity -> ClientPlayerEntity -> PlayerEntity -> LivingEntity. Both `LivingEntity(World)` constructors set `stepHeight=0.6F`; a full exact-source `rg stepHeight` inventory finds no LocalClientPlayerEntity/PlayerEntity writer. Other assignments are remote-player, server-player, or non-player constructors. D-STEPHEIGHT is resolved for the local client player; `World.getCollisions`/shape-producing block path remains open. See F-01.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): confirmed changed probe depth from fixed `-1.0` to `-this.stepHeight` for the same SELF/PLAYER eligible grounded sneaking player path. Consequence depends on support/collision shapes; no trajectory tested.
- Finding IDs or checked absence/replacement path: F-01.

### Slice S4-piston-cap: external piston displacement

- Inventory ID(s): INV-EXTERNAL, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` piston-specific per-world-time axis accumulation and cutoff before ordinary collision resolution; piston caller's entity displacement. A `Entity.move` has no mover type or cap; A moving block caller directly passes displacement.
- A evidence: `Entity.java::move(double,double,double)`, lines 446-514, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`; `MovingBlockEntity.java::moveEntities`, lines 91-159, SHA-256 `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1`.
- B evidence: `Entity.java::move(MoverType,double,double,double)`, lines 459-500, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`; `MovingBlockEntity.java::moveEntities(float)`, lines 116-160 and 210-242, SHA-256 `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e`.
- State producers/writers -> consumers/readers: moving piston progress -> MoverType.PISTON displacement -> per-entity per-axis accumulated movement keyed by world time -> box/collision/position/flags. B clamps cumulative same-axis movement to +/-0.51, returns for incremental movement <=1.0E-5F.
- Parent slices / dependencies / closure evidence: S4-sneak-probe; exact tick order/progress and piston collision shapes are open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): confirmed B-only cap on a reachable piston displacement path. Repeated same-axis piston movement during one world time is capped; exact player consequence depends on piston geometry/progress and other callers. No runtime claim.
- Finding IDs or checked absence/replacement path: F-02.

### Slice S4-piston-edge-bypass: mover-type guard on sneak restraint

- Inventory ID(s): INV-EXTERNAL, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: eligibility guard before the grounded-player sneak edge probes in `Entity.move`; compare A's untyped method with B's new `MoverType` predicate and B's piston caller.
- A evidence: `Entity.java::move(double,double,double)`, lines 468-472, source hash as S4-sneak-probe. The guard is `onGround && isSneaking() && this instanceof PlayerEntity`; every call enters the same restraint.
- B evidence: `Entity.java::move(MoverType,double,double,double)`, lines 519-523, source hash as S4-sneak-probe. The guard additionally requires mover type SELF or PLAYER. `MovingBlockEntity.java::moveEntities(float)`, lines 159 and 241, supplies PISTON.
- State producers/writers -> consumers/readers: sneak/ground state + displacement producer/mover type -> edge-probe selection -> player position.
- Parent slices / dependencies / closure evidence: S4-sneak-probe and S4-piston-cap; exact piston/collision progress remains open under D-PISTON.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B skips the sneak-edge restraint for a grounded sneaking player receiving piston movement because its caller passes PISTON. A has no mover category and applies the restraint to the same player state. Piston pushing is an external movement route; actual positional outcome depends on geometry and remains a source-predicted consequence.
- Finding IDs or checked absence/replacement path: F-03.

### Slice S4-step-callbacks: post-move onSteppedOn dispatch

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: A caches the pre-movement grounded/sneaking/player condition in `bl`; B checks current `onGround`, `isSneaking`, and player type after collision resolution. Audited every `onSteppedOn` override under `net/minecraft/block` and its direct movement effects.
- A evidence: `Entity.java::move`, lines 468 and 664-694, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`; `Block.java::onSteppedOn`, line 517-518, SHA-256 `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`; `RedstoneOreBlock.java::onSteppedOn/interact`, lines 42-69, SHA-256 `0691a8df1e7ceac85ca0ec9298706b225ce5669541757d15333f2c4019a1de82`; `SlimeBlock.java::onSteppedOn`, lines 46-54, SHA-256 `38e77cdaaf3681fe3a2357ebc0dce7a86d658429463bc1e06e6e1167fe627c8a`; `MagmaBlock.java::onSteppedOn`, lines 34-40, SHA-256 `6e382a4fc307391c6776dc2ff010aec1da16b50ebc50e015b6009d82c7b76b1a`; `StairsBlock.java::onSteppedOn`, lines 220-222, SHA-256 `ee143aced2a6902563cddbae77e78d1d1a512244f278987684ebe5bcacba37cb`.
- B evidence: `Entity.java::move`, lines 519 and 727-759, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`; `Block.java::onSteppedOn`, line 514-515, SHA-256 `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`; `RedstoneOreBlock.java::onSteppedOn/interact`, lines 41-59, SHA-256 `5d200f042a07ad928f1f38b9d7464a1a019f53ca7e63b9f23036dc71f8939ecd`; `SlimeBlock.java::onSteppedOn`, lines 46-54, SHA-256 same as A; `MagmaBlock.java::onSteppedOn`, lines 34-40, SHA-256 same as A; `StairsBlock.java::onSteppedOn`, lines 221-223, SHA-256 `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627`.
- State producers/writers -> consumers/readers: pre-move ground flag -> A cached `bl`; collision result -> B updated `onGround`; `makesSteps` and callback dispatch -> subclass effects. Slime's horizontal velocity write is itself gated on `!entity.isSneaking`; Magma only applies fire damage (excluded); RedstoneOre lights itself and emits particles; Stairs delegates to its base block; base callback is empty.
- Parent slices / dependencies / closure evidence: S4-sneak-probe; source search enumerated each `onSteppedOn` override under both block trees. This closes movement effects of these callbacks for the sneaking-player landing case; it does not close all block callbacks, shapes, registrations, or resource dependencies.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the dispatch guard differs when a previously airborne sneaking player lands, but every audited callback reachable through that dispatch has no movement write for a sneaking player. Slime's damping is explicitly suppressed while sneaking; Magma damage is excluded; RedstoneOre's callback changes light state/particles only; Stairs delegates. Thus this bounded callback difference produces no confirmed in-scope movement delta.
- Finding IDs or checked absence/replacement path: no movement finding; F-01/F-03 describe separate movement-path changes. Full `onEntityCollision`, `beforeCollision`, and `onFallenOn` inventory remains open.

### Slice S4-box-axis-resolution: axis clipping and step candidate selection

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Box.intersectX/Y/Z` and strict `intersects` inequalities; `Entity.move` vertical then X then Z resolution, step-up's two candidate paths, horizontal squared-distance comparison, and `>=` fallback. The comparison is conditional on identical input collision lists and initial boxes; those producers remain a separate open slice.
- A evidence: `Box.java::intersectX/Y/Z/intersects`, lines 143-209, SHA-256 `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155`; `Entity.java::move`, lines 514-626, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.
- B evidence: `Box.java::intersectX/Y/Z/intersects`, lines 185-250, SHA-256 `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a`; `Entity.java::move`, lines 564-689, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`.
- State producers/writers -> consumers/readers: `World.getCollisions` ordered Box list + current entity shape + requested deltas -> Box axis clipping -> vertical/X/Z box moves -> step-up alternatives -> selected box/delta -> position and collision flags.
- Parent slices / dependencies / closure evidence: S4-sneak-probe; `World.getCollisions` traversal is partially inventoried in S4-world-query; every reachable block/entity shape provider, override, registration and support dependency remains open under D-COLLISION.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the inspected Box inequalities, clipping expressions, axis order, squared horizontal comparison, and fallback boundary match. B skips a zero-delta shape translation; with the same box this is a no-op. Conclusion is scoped to matching collision-list inputs and does not close collision-list generation.
- Finding IDs or checked absence/replacement path: no finding for this bounded algorithmic slice; provider differences remain open.

### Slice S7-pushability: living player collision eligibility

- Inventory ID(s): INV-EXTERNAL, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.isPushable` and entity-filter predicate consuming it; no non-player movement consequence is asserted.
- A evidence: `LivingEntity.java::isPushable`, lines 1887-1889, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`; `EntityFilter.java::canBePushedBy`, lines 56-70, SHA-256 `91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813`; `RemoteClientPlayerEntity#mobTick`, lines 105-112, SHA-256 `e7e248b439f7356695b1bf196d08e997bb19b24c23b206763c772663ee7086a1`; `Entity#push`, lines 1085-1107, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`; `World#getCollisions` entity-loop lines 945-964, SHA-256 `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`.
- B evidence: `LivingEntity.java::isPushable`, lines 1950-1952, SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`; `EntityFilter.java::canBePushedBy`, lines 50-64, SHA-256 `49f2c3cbeea0bb9421fdfaad49a7742394f69cd73981a9cdd51e1badcf30c629`; `RemoteClientPlayerEntity#mobTick`, lines 105-112, SHA-256 same as A; `Entity#push`, lines 1153-1175, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`; `World#getCollisions` entity-loop lines 1013-1036, SHA-256 `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58`.
- State producers/writers -> consumers/readers: player climbing state and alive/removal state -> `LivingEntity.isPushable` -> `EntityFilter.canBePushedBy` candidate admission -> `RemoteClientPlayerEntity.pushAwayCollidingEntities` -> `pushAway` -> local player's `Entity.push` -> velocity via `addVelocity`.
- Parent slices / dependencies / closure evidence: S1-local-order; client-side caller and receiver push path are traced for remote-player collision. Broader vehicle/entity push routes remain in D-EXTERNAL.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B changes the predicate from `!removed` to `isAlive() && !isClimbing()`. With collision rule allowing interaction, a non-spectator local player who is climbing and intersects a remote player is included by A's push candidate filter but rejected by B's; A's remote-player tick then applies `Entity.push` to the local player while B omits it. Conditions in `Entity.push` (different vehicle, neither noClip, horizontal separation at least `0.01F`, target has no passengers) further gate the velocity write. Death-related exclusion is not used for this finding.
- Finding IDs or checked absence/replacement path: F-04.

### Slice S1-autojump: post-move auto-jump decision body

- Inventory ID(s): INV-TICK, INV-COLLISION, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: local `move` override records x/z, delegates movement, then calls `autoJump` with realized x/z displacement; exact `autoJump` body and sprint setter checked. Its collision-list producers and speed/effect inputs are not closed here.
- A evidence: `LocalClientPlayerEntity.java::move`, lines 812-817, and `autoJump`, lines 823-923, SHA-256 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`; `setSprinting`, lines 417-420, same file hash.
- B evidence: `LocalClientPlayerEntity.java::move`, lines 824-829, and `autoJump`, lines 835-935, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; `setSprinting`, lines 427-430, same file hash.
- State producers/writers -> consumers/readers: movement result and `onGround` -> auto-jump gates; current input vector, speed, yaw/rotation and jump-boost state -> candidate direction/height; `World.getCollisions` output -> obstacle height -> `ticksToNextAutojump`.
- Parent slices / dependencies / closure evidence: S1-local-order and S4-world-query; A's `move(double,double,double)` and B's `move(MoverType,double,double,double)` forward their received movement args to the superclass, then both compute realized x/z delta and invoke autoJump. The 101-line autoJump method body is text-identical. The `isSneaking`, A lines 596-599 and B lines 608-611, differs syntactically (`input != null ? input.sneaking : false` vs `input != null && input.sneaking`) but yields the same boolean for null and non-null input.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for identical local inputs, movement result, rotation/speed/effect values, and collision-list contents, the decision math, query construction, iteration order and cooldown write match exactly. This conditional result does not certify collision candidate lists or upstream movement outcomes.
- Finding IDs or checked absence/replacement path: no finding in the bounded local post-move auto-jump decision; provider, modifier, and call-order dependencies remain open.

### Slice S2-elytra-empty-slot: empty chest-slot representation at local flight gates

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: the local player's Elytra start-flying check in `LocalClientPlayerEntity.mobTick` and the chest-slot gate in `LivingEntity.flyingTick`; only the empty-chest-slot case is compared, not all equipment or flight behavior.
- A evidence: `LocalClientPlayerEntity.java::mobTick`, lines 731-734, SHA-256 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`, returns through `itemStack != null` before reading the chest item. `PlayerEntity.java::getEquipment`, lines 1683-1690, SHA-256 `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`, returns the armor array slot; `PlayerInventory.java` lines 23-25 shows the array allocation whose empty elements are null. `LivingEntity.java::flyingTick`, lines 1754-1773, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`, likewise checks non-null before Elytra item/capability checks. `ItemStack.java::getItem`, lines 113-115, SHA-256 `a41054235eac09212065f7e20d4206aa7d83cdd159cebbc77eb8b3d1e43ab842`, returns the stored item.
- B evidence: `LocalClientPlayerEntity.java::mobTick`, lines 743-746, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`, drops the null guard. `PlayerEntity.java::getEquipment`, lines 1678-1685, SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`, returns a `DefaultedList` slot; `PlayerInventory.java` lines 25-27 initializes armor with `ItemStack.EMPTY`, hashes recorded above. `LivingEntity.java::flyingTick`, lines 1810-1829, SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`, drops its null guard. `ItemStack.EMPTY` is constructed with a null item (`ItemStack.java` line 48), and `ItemStack.getItem`, lines 134-136, returns the air item when the stack is empty; B ItemStack hash recorded above.
- State producers/writers -> consumers/readers: empty armor-slot initialization/access -> local `mobTick` start-flight predicate and `LivingEntity.flyingTick` continuing-flight predicate -> Elytra item/canFly tests.
- Parent slices / dependencies / closure evidence: S1-local-order; this closes the null-to-empty question for the empty chest slot at these two local flight gates only. Broader item-use, equipment modifiers, and flight call-order dependencies remain open under D-MODIFIERS/D-TRAVEL.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): A's empty slot is null and short-circuits before `getItem`; B's empty slot is `ItemStack.EMPTY`, whose `getItem()` resolves to air, so its Elytra item comparison is false. Both the local start-flying gate and ongoing `flyingTick` gate therefore reject an empty chest slot. This establishes no movement difference for this bounded empty-slot case only.
- Finding IDs or checked absence/replacement path: no finding; non-empty Elytra checks and other equipment readers remain outside this slice.

### Slice S4-world-query: block and entity collision-list construction

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: ordinary `World.getCollisions(entity, shape)` used by entity movement; A's public query body versus B's public list wrapper/private block-query helper, followed by entity candidate collection. Border-only boolean query is not closed here.
- A evidence: `World.java::getCollisions(Entity,Box)`, lines 903-965, SHA-256 `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`; `Block.java::addCollisions`, lines 342-353, SHA-256 `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`; `StateDefinition.java::addCollisions`, lines 369-371, SHA-256 `00fea8cdf8a0cabf1af21e7e7ff47f071bd87a16f91697a3b31efce6c78bfda4`.
- B evidence: `World.java::getCollisions(Entity,Box)` and private helper, lines 960-1036, SHA-256 `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58`; `Block.java::addCollisions`, lines 353-364, SHA-256 `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`; `StateDefinition.java::addCollisions`, lines 374-376, SHA-256 `95704c944742efb2ec9cba7eb73399a330195556e669451131ba542e6a9fac65`.
- State producers/writers -> consumers/readers: requested/current entity box -> padded block-coordinate scan and border/chunk state selection -> block-state `addCollisions` -> entity candidate query (`shape.expand(0.25)`) and both entities' collision shapes -> ordered list consumed by `Entity.move` clipping/step candidates.
- Parent slices / dependencies / closure evidence: S4-box-axis-resolution; B's ordinary wrapper passes `enforceWorldBorder=false` and `collisions=list`, and passes `forceShape=false`. The World scan has a different y-boundary inclusion rule and new boolean-query short-circuit; candidate effects are not resolved until every reachable provider/override and shape bound is audited.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the entity-candidate loop, shared-vehicle guard, shape intersection tests, and append order match in the inspected bodies. The world-border state selection is algebraically equivalent for this ordinary path. The block scan and `addCollisions` API changed; A excludes both vertical end rows at x/z side columns while B excludes only the upper row, and B adds a `forceShape` parameter. Source search enumerates paired `addCollisions` overrides in `Block`, `BrewingStandBlock`, `CauldronBlock`, `ChorusPlantBlock`, `EndPortalBlock`, `EndPortalFrameBlock`, `FenceBlock`, `HopperBlock`, `LilyPadBlock`, `PaneBlock`, `PistonBaseBlock`, `PistonHeadBlock`, and `StairsBlock`; B additionally has `MovingBlock`, `MovingBlockEntity`, and `WallBlock`. Bodies, shape bounds and registration/reachability of these providers are not yet paired. Their movement consequence is unresolved pending that coverage.
- Finding IDs or checked absence/replacement path: no finding yet; `Block.addCollisions` override/provider inventory and state/resource reachability remain open under D-COLLISION/D-BLOCK-DATA.

### Slice S5-wall-collision-shapes: existing cobblestone wall provider

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: registered cobblestone wall collision shape selected by direction-connection bits and neighboring wall/fence context; ordinary entity/player collision query only, with B `forceShape=false`.
- A evidence: `WallBlock.java::getCollisionShape`, lines 91-94; `shouldConnectTo`, lines 137-147; `resolveVirtualProperties`, lines 177-184; `getShapeIndex`, lines 96-115; 16 `COLLISION_SHAPES` entries, lines 46-63; SHA-256 `5629ed19c8617a9a0d34bbb4e27ec15ef72d276621d4a006711282ea48f8174e`. `Block.java` registers numeric ID 139 as `cobblestone_wall` at line 1055, SHA-256 `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`; base `addCollisions` lines 342-353 delegates to the state's collision shape.
- B evidence: `WallBlock.java::addCollisions`, lines 92-98, `getCollisionShape`, lines 102-105, `shouldConnectTo`, lines 148-158; `resolveVirtualProperties`, lines 188-195, `getShapeIndex`, lines 107-126; 16 `COLLISION_SHAPES` entries, lines 48-65; SHA-256 `17da6d881365e4dce5134f88cb9a9e5273d1fa1734812d13410dc6924a3acbfd`. `Block.java` registers numeric ID 139 as `cobblestone_wall` at line 1060, SHA-256 `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`; `World.getCollisions` passes `forceShape=false` through the state dispatch in S4-world-query.
- State producers/writers -> consumers/readers: neighbor-derived connection properties -> identical shape-index bit mapping -> identical collision-box table (all heights use `withMaxY(1.5)`) -> ordinary world collision list -> entity movement clipping/edge probes.
- Parent slices / dependencies / closure evidence: S4-world-query; both exact sources register the same existing block and use matching neighbor-connect, virtual-state-resolution and shape-index bodies. This is conditional on identical neighbor `isCube`/material inputs. B's new `addCollisions` override resolves virtual properties on the normal `forceShape=false` path, then adds the same indexed collision box that A returns through the base delegation.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the registered wall's neighbor-connect predicates, state resolution, shape selection and 1.5-block collision height match for ordinary player collision queries given the same neighbor state/material results. The changed `forceShape=true` branch is outside this movement query. Other collision providers and block/data inventory remain open.
- Finding IDs or checked absence/replacement path: no finding for this existing wall provider; general block registration/resource and other provider coverage remains under D-BLOCK-DATA/D-COLLISION.

### Slice S2-sleep-size-cycle: direct player sleep and wake box updates

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: player `trySleep` shrinks to 0.2F then positions the player at the selected bed coordinates on both loaded and unloaded chunk branches; `wakeUp` restores 0.6F dimensions and later positions the player at a wake location. This does not cover `updatePlayerPose` or other size writers.
- A evidence: `PlayerEntity.java::trySleep`, lines 1213-1248, and `wakeUp`, lines 1269-1291, SHA-256 `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`; `Entity.java::setPosition`, lines 282-289, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`, reconstructs a centered box from current width/height and x/y/z.
- B evidence: `PlayerEntity.java::trySleep`, lines 1228-1248, and `wakeUp`, lines 1265-1287, SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`; `Entity.java::setPosition`, lines 295-302, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`, has the same centered-box reconstruction.
- State producers/writers -> consumers/readers: sleep state/bed facing -> `setSize` -> explicit `setPosition` at bed; wake call -> `setSize` -> explicit wake-position selection and `setPosition` -> later movement queries.
- Parent slices / dependencies / closure evidence: S2-resize; A's direct sleep shrink and B's recentering shrink can make an intermediate box differ, but both `trySleep` branches immediately reset it with `setPosition`. Wake uses width growth, not B's new shrink branch; on the bed branch, both versions position the player afterward. No intervening collision or movement query consumes the intermediate box in these inspected call sequences.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the final box after the direct sleep/wake methods is rebuilt from the same final x/y/z and dimensions in both endpoints. This closes only those explicit method call sequences, not pose-transition callers or all player resize paths.
- Finding IDs or checked absence/replacement path: no finding for direct trySleep/wake size sequencing; `updatePlayerPose` remains open under S2-resize.

### Slice S2-eye-height: player eye-height selection

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: full `PlayerEntity.getEyeHeight` branch order and returned float for sleep, sneak/height, fall-flying/height, and default states.
- A evidence: `PlayerEntity.java::getEyeHeight`, lines 1759-1770, SHA-256 `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`.
- B evidence: `PlayerEntity.java::getEyeHeight`, lines 1759-1770, SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`.
- State producers/writers -> consumers/readers: sleeping/sneaking/fall-flying flags and height -> ordered eye-height selection -> movement-adjacent fluid/view-origin consumers.
- Parent slices / dependencies / closure evidence: S2-resize; upstream flag/dimension writers and all eye-height consumers remain open in INV-STATE/INV-TICK.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both complete method bodies match: default `1.62F`, sleeping `0.2F`, sneaking or `height == 1.65F` subtracts `0.08F`, and fall-flying or `height == 0.6F` returns `0.4F`, in the same branch order.
- Finding IDs or checked absence/replacement path: no finding for the bounded getter; no whole-state equivalence is claimed.

### Slice S2-resize: entity box adjustment when dimensions change

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `Entity.setSize`; B adds early return for width shrink, re-centering the new box on current x/z; A reconstructs from prior minX/minZ. Direct `trySleep` and `wakeUp` call orders are closed separately in S2-sleep-size-cycle; `updatePlayerPose` collision-check/setSize order and dimension restoration remain open.
- A evidence: `Entity.java::setSize`, lines 264-274, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.
- B evidence: `Entity.java::setSize`, lines 271-287, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`.
- State producers/writers -> consumers/readers: pose/sleep dimensions -> bounding box -> position and collision queries; width/height defaults and eye height not yet closed.
- Parent slices / dependencies / closure evidence: S4-move-order; PlayerEntity sleep/pose calls require full guard and order comparison.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): source difference identified; direct sleep/wake callers overwrite the transient resized box through `setPosition`, but `updatePlayerPose` and remaining player dimension paths still need exact call-order and collision checks before classifying the broader size change.
- Finding IDs or checked absence/replacement path: candidate only; not indexed as finding.

### Slice S3-travel-and-world-dependencies: all remaining tick movement

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: all branches and data outside S1-input, S1-local-order, and S3-jump; includes water/lava/fall-flying, attributes/effects, post-travel, shape callbacks and source resources.
- A evidence: `LivingEntity.java` travel-related bodies include `moveRelative` lines 1332 onward, `flyingTick` 1754-1773, `mobTick` 1681-1752; SHA-256 above.
- B evidence: corresponding `moveRelative` lines 1388 onward, `flyingTick` 1810-1830, `mobTick` 1737-1808; SHA-256 above.
- State producers/writers -> consumers/readers: open; fluid, block, modifiers, attribute/effect/equipment, input and synchronized state edges have not been fully enumerated.
- Parent slices / dependencies / closure evidence: all first-stage tick slices; no dependency closure yet.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): endpoint pair is comparable but broad tick and data-driven coverage is not complete.
- Finding IDs or checked absence/replacement path: open; do not infer parity from bounded slices already checked.

## Dependency queue and blockers

- D-SOURCE-DIAGNOSTICS: retain and recheck body-level diagnostics for every cited movement method; owner markers and global hashes match, but the full method-body-by-method diagnostics audit is not recorded in this report. Owner/source paths above; open.
- D-ARTIFACT-INTEGRITY: source owner published immutable revision `feather-r1-2026-10-07`. Worker reverified each snapshot against `artifact.sha256`/`revision.json`, original manifest and source-manifest identities, every source file, every raw artifact entry and the full verification-log hash. Original derived mapped JARs are unavailable and the new hashes differ; identity/metadata-only change is not established. Independent ops verification remains pending. Do not rewrite markers/manifests or regenerate. Keep the pair unfrozen until ops verifies the revision and the remaining evidence limitation is dispositioned.
- D-STEPHEIGHT: resolved for the local client-player path: both `LivingEntity(World)` constructors initialize `0.6F`, and the local-player inheritance chain has no later writer. Remote/server player overrides are separate execution paths. Evidence recorded in S4-sneak-probe/F-01.
- D-COLLISION: inspect both `World.getCollisions` overloads, entity filtering, Box axis intersection, every reachable player collision shape provider/callback/neighbor dependency and registrations; needed by F-01/F-03/F-04 and all movement.
- D-PISTON: fully compare `MovingBlockEntity` A/B progress, selection, shape and tick call order; tie movement cap to the exact reachable player path; needed by F-02.
- D-PUSHABILITY: resolved for the client player-to-player climbing path in S7/F-04 using `EntityFilter.canBePushedBy` -> `RemoteClientPlayerEntity.pushAwayCollidingEntities` -> `Entity.push`. Other external push/collision producers remain open under D-EXTERNAL.
- D-POSE: trace player dimension/pose/eye-height constructors, sleep and pose transition writers, complete `trySleep`, and all collision readers; needed by S2-resize.
- D-TRAVEL: complete travel branch and exact operation order, including fluid/flight/climb, velocity cutoffs, jump/effect/item gates and post-travel state.
- D-BLOCK-DATA: inventory block/fluid providers, registrations, neighbors and resources (tags/data defaults and jar entries with hashes); modern-only applicability disposition per added block.
- D-MODIFIERS: trace movement attributes, effects, enchantments, equipment slots/applicability and application/removal timing; identify server-synchronized values and client-only boundary.
- D-EXTERNAL: trace corrections/teleports, velocity writes, mounts, shulkers and all player-facing movement producers; separate player movement from non-player simulation.
- D-REVIEWER: coordinator to assign an independent reviewer who did not author this report.
- D-CHECKER: resolved by applying canonical workflow checker fix `8fa4ab0`; report was checked against the local checker and accepted as structurally valid partial. This is schema validation only.
- Open dependencies: D-ARTIFACT-INTEGRITY, D-SOURCE-DIAGNOSTICS, D-COLLISION, D-PISTON, D-POSE, D-TRAVEL, D-BLOCK-DATA, D-MODIFIERS, D-EXTERNAL, D-REVIEWER.

## Finding index

- [F-01](findings/F-01-sneak-edge-probe-depth.md): grounded sneaking player edge restraint probes a different vertical depth; source-confirmed, consequence conditional on shapes/support.
- [F-02](findings/F-02-piston-movement-cap.md): B applies per-world-time axis movement cap to piston displacement; source-confirmed, no runtime validation.
- [F-03](findings/F-03-piston-sneak-edge-bypass.md): piston-driven movement bypasses sneak edge restraint in B; source-confirmed, consequence conditional on geometry.
- [F-04](findings/F-04-climbing-player-push-eligibility.md): climbing players are excluded from B's push recipient filter; source-confirmed, scoped to the traced local-player path.
- Discarded candidate: `LocalClientPlayerEntity.getRotationVector(float)` is added in B and reads yaw instead of inherited LivingEntity headYaw; at confirmed locally controlled travel, `PlayerEntity.serverTickAi` sets headYaw=yaw before travel. This bounds that travel use only; other callers remain open and this candidate is not globally discarded.
- The empty chest-slot null-to-empty change is closed for the local Elytra start/continue gates in S2-elytra-empty-slot; other item-use and equipment paths remain open.

## Resume checkpoint

- Last completed slice: exact source readiness verification; S1-input; bounded S3-jump; F-01/F-02; S4-callbacks; S4-box-axis-resolution; S7-pushability/F-04; bounded S2-elytra-empty-slot; partial S1-local-order; partial S4-world-query; bounded S2-sleep-size-cycle; S1-autojump; S5-wall-collision-shapes; S2-eye-height.
- Next bounded slice and exact files/members/body ranges to open: `D-COLLISION` (`World.getCollisions`, both block-query overloads, relevant `BlockState.addCollisions`, every reachable shape provider), then `D-PISTON` (`MovingBlockEntity` complete progress/tick paths A 91-183 and B 116-285). Continue all open queue items in navigation order.
- Outstanding dependencies and owners: shared source owner is read-only publisher; source worker owns this run and findings; coordinator must assign independent reviewer.
- Current assumptions requiring verification: line ranges cited above remain stable under the source hashes; current mapped artifact integrity needs canonical owner repair/reverification; complete method correspondence, branch coverage, data resources and external-player call paths remain open.

## Implementation reconciliation

Complete only after blind-discovery freeze. No mod implementation was opened.

- Reconciliation status: pending
- Repository revision inspected: none
- Finding -> implementation disposition/evidence: pending
- Existing implementation without a frozen source finding: pending
- Coverage gaps routed back to discovery slices: pending

## Independent source audit

- Reviewer: not assigned
- Status: pending
- Inventories and call-chain ranges re-walked: none
- Concrete missed-slice routes (or `none found`): pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: 4 findings, 9 compared-no-difference, 3 in-progress, 1 pending (bounded rows only; broad inventory remains open).
- Required inventory status and evidence: only `INV-EXCLUSIONS` declaration complete; all movement inventories pending, with partial anchors above.
- Open dependencies: D-ARTIFACT-INTEGRITY, D-SOURCE-DIAGNOSTICS, D-COLLISION, D-PISTON, D-POSE, D-TRAVEL, D-BLOCK-DATA, D-MODIFIERS, D-EXTERNAL, D-REVIEWER.
- Unresolved gaps and limits: complete tick graph, body-level diagnostic review, collision providers/resources, exact entity-player collision paths, modifiers, external writers, source-only freeze and independent audit remain open. Source comparison only; no gameplay behavior observed.
- Evidence/hash/correspondence audit: on resume, both revised snapshots, ready/source/artifact manifest identities, all 3,766 listed Java-source hashes, all 72 raw artifact entries, diagnostics and the shared verification-log hash were reverified. Original mapped JARs remain unavailable and snapshot identity is unproven; independent ops verification is pending. Cited Java source hashes are recorded. Recheck line ranges at freeze; broad correspondence remains incomplete.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).
