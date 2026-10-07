# Discovery: 1.20.2 to 1.20.4

- Status: active
- Scope: direct client player movement and the complete reachable vanilla movement call graph. Older A = 1.20.2; newer B = 1.20.4. This is a source-only discovery track; runtime Java implementation is not authorized here.
- Scope exclusions: health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations (including indirect sprint-gate effects), and non-player movement. Vanilla values may be read as movement inputs; excluded producer systems are not emulated. Modern-only features remain modern-only.
- Evidence constraints: exact-version decompiled sources and bytecode only. No Minecraft Wiki/MCPK browsing, release-note mechanics, old implementation, old patch classes, or wiki-audit outputs before freeze. Prior discovery reports are allowed only as navigation; none were used for this checkpoint.
- Repository revision and start date: source baseline `002137b227676caea77f6832b9f4c8d0b6200bff` (`main` at worktree creation), 2026-10-07. Workflow-hardening commits cherry-picked: `40c34f5` and `2422192`.
- Worktree / branch: `C:/Users/Wolfi/.codex/worktrees/movement-source-1-20-2-1-20-4/LegacyParkourCompat`; `feat/source-discovery-movement-source-1-20-2-1-20-4`.
- Selected naming namespace, CLI mode per side and alignment evidence: intended `mojmap` / official Mojang names on both sides; alignment remains unverified until both readiness records and mappings are checked.
- Source preparation owner / command / log / readiness marker: source owner only. Requested exact 1.20.2 and 1.20.4 Mojmap readiness records under `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07`; neither marker has been published at this checkpoint. No decompile command run by this worker.
- Toolchain/decompiler/remapper versions and options: pending validated readiness records.
- Discovery author(s): this task's source worker.
- Independent reviewer (must differ from discovery authors): not yet assigned.

## Artifact manifest

- Shared evidence root: `build/movement-campaign-2026-10-07/`; readiness files and source/artifact manifests were read and hash-checked.
- A (1.20.2): requested/resolved/metadata IDs all `1.20.2`; status `ready`; CLI `mojmap`; source root `ready/1.20.2/mojmap`; 4,904 Java files. Original client jar `artifacts/1.20.2/client.jar`, SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`, Mojang metadata SHA-1 `82d1974e75fc984c5ed4b038e764e50958ac61a0`. Official client mappings `artifacts/1.20.2/client_mappings.txt`, SHA-256 `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd`, publisher SHA-1 `5c292ff7d3161977041116698e295083fd5ec8f5`. Mojmap jar `artifacts/1.20.2/client-mojmap.jar`, SHA-256 `3dce01cbbb74e367414c811440ca6e80bc2ec18284386720ce413f9aec0ba339`. `ready/1.20.2/mojmap.sources.sha256` SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; `ready/1.20.2/artifacts.sha256` SHA-256 `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54`; `ready/1.20.2/movement-diagnostics.txt` SHA-256 `a60a6ea2a91cda32df8a7b837d5344ae54c8370058cc9ab3ae163b30e8fa6b79`.
- B (1.20.4): requested/resolved/metadata IDs all `1.20.4`; status `ready`; CLI `mojmap`; source root `ready/1.20.4/mojmap`; 5,048 Java files. Original client jar `artifacts/1.20.4/client.jar`, SHA-256 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`, Mojang metadata SHA-1 `fd19469fed4a4b4c15b2d5133985f0e3e7816a8a`. Official client mappings `artifacts/1.20.4/client_mappings.txt`, SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`, publisher SHA-1 `be76ecc174ea25580bdc9bf335481a5192d9f3b7`. Mojmap jar `artifacts/1.20.4/client-mojmap.jar`, SHA-256 `d20c183016505cceecd33d508973e7ca6f2ee8cbd860f316592f4a09a96bc339`. `ready/1.20.4/mojmap.sources.sha256` SHA-256 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; `ready/1.20.4/artifacts.sha256` SHA-256 `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948`; `ready/1.20.4/movement-diagnostics.txt` SHA-256 `8909a0341c2141f476160c19a05cbf2ef39e28bee7357dd50e3693e4a3174879`.
- The two readiness JSON files' `status`, exact requested/resolved/metadata release IDs, namespace, output roots, client hashes, source-manifest hashes, artifact-manifest hashes, and diagnostics hashes were checked. Manifest and diagnostics actual SHA-256 values match the JSON. The client jar, mapping file, and remapped jar hashes match artifact-manifest entries on both sides. Cited source files are checked against their source manifest entries.
- Preparation command (shared source owner): `.\gradlew.bat decompileMinecraft --versions=1.20.1,1.20.2,1.20.4,1.20.6 --mappings=mojmap --decompiler-heap=4G --output-root=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\mojmap-1.20.1-to-1.20.6-3e08bcae66a145b296f7f85b12623999 --cache-directory=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts`.
- Success log: `staging/mojmap-1.20.1-to-1.20.6-3e08bcae66a145b296f7f85b12623999/gradle.full.log`; exact requested/resolved release IDs were logged. Readiness diagnostics contain 22 movement anchors per side and confirm required Entity/LivingEntity/Player/LocalPlayer classes. Filtered log review found only Gradle deprecation notices, no Vineflower error, exception, failed download or missing-library warning.
- Toolchain from provenance: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; TinyRemapper 0.14.1; Mapping-IO 0.9.1; Gson 2.14.0; ASM 9.10.1; heap 4G. Mapping alignment is the official Mojang-name namespace on both sides using each release's own mapping artifact.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending
- Evidence inventory and finding IDs included at freeze: none yet
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither wiki was browsed; no old implementation, old patch class, or wiki-audit output was opened.
- Source/mapping hashes covered by freeze: pending source publication and comparison.

## Correspondence and call order

- Verified same-name/same-descriptor core correspondence: `net.minecraft.client.player.LocalPlayer#aiStep()V`, `#serverAiStep()V`, `#move(MoverType, Vec3)V`, `#updateAutoJump(FF)V`, and its local-player sprint/auto-jump helpers; `net.minecraft.client.player.Input#tick(ZF)V`, `#getMoveVector()Vec2`, `#hasForwardImpulse()Z`; `KeyboardInput#tick(ZF)V`; `net.minecraft.world.entity.player.Player#aiStep()V`, `#jumpFromGround()V`, `#travel(Vec3)V`, `#rideTick()V`; `net.minecraft.world.entity.LivingEntity#jumpFromGround()V`, `#travel(Vec3)V`, `#travelRidden(Player, Vec3)V`, `#tick()V`, `#aiStep()V`; `net.minecraft.world.entity.Entity#move(MoverType, Vec3)V`, `#moveRelative(float, Vec3)V`, `#updateInWaterStateAndDoFluidPushing()Z`, and relevant movement state writers. Official Mojmap class/member names and source method bodies were checked directly; descriptor/name equality alone is not the evidence.
- Exact endpoint source roots: `ready/1.20.2/mojmap/` and `ready/1.20.4/mojmap/`, relative to the shared evidence root. Both roots pass readiness and manifest verification. The four required movement classes are present and body diagnostics point to the corresponding local player, player, living entity, and entity methods.
- Partial input/tick sequence verified from sources: login/respawn packet handlers assign `new KeyboardInput(options)` to the local player; `LocalPlayer.aiStep` samples input, applies slow-input and item-use scaling, handles sprint/jump/flying/vehicle input, then calls `Player.aiStep`; `LivingEntity.aiStep` reaches `LocalPlayer.serverAiStep`, which copies input impulses and jumping into inherited movement fields for this locally controlled player, then damping/jump/travel and post-travel steps run. Exact caller order from the client/network and level tick, plus its new loading-screen path, remains under investigation.
- Pair comparison of core methods: `LocalPlayer.java` is byte-identical across A/B (SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` on both sides); `Input.java` (`b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb`) and `KeyboardInput.java` (`a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`) are also byte-identical. The inspected `LivingEntity` jump, travel, tick, and aiStep bodies and `Entity.move` / `moveRelative` bodies compare byte-for-byte after extraction despite shifted line numbers. `Player.aiStep`, `Player.jumpFromGround`, and `Player.getDimensions` also compare byte-for-byte. `Player.travel` and `Player.rideTick` have removed post-movement statistic callbacks in B; this candidate is separately dispositioned under the explicit exclusion slice below because it changes statistics/food exhaustion only, not movement state.
- Movement math must still be followed through external state/data producers, collision providers, fluids, attributes/effects/equipment and resources. The comparisons above are bounded evidence only, not closure for any full stage.

## Required source inventories

Each inventory maps to bounded slice IDs below. All are pending until each member-level slice and dependency is inspected on both exact sides.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1.1-S1.7,S3.1-S3.9; evidence=pending validated exact sources and body ranges.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S1.2-S1.6,S2.1-S2.4,S3.1-S3.9,S4.1-S4.8; evidence=pending writer/consumer inventory.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S2.2,S4.1-S4.8,S5.2,S5.7; evidence=pending exact providers, registrations and calls.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S3.2,S3.5-S3.7,S4.5-S4.8,S5.1-S5.7; evidence=pending source/resource manifests.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S2.3,S3.2,S3.4,S3.6,S3.8-S3.9,S6.1-S6.5; evidence=pending consumers through registrations/data.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S1.7,S5.5,S7.1-S7.5; evidence=pending exact packet/callback/player state writers.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; slice_ids=S1.3,S2.4,S3.4,S7.2; evidence=pending direct consumer bounds and explicit exclusion audit.

## Coverage ledger

Every row below is a bounded behavior planning slice, not a claim of inspected methods or pair correspondence. Refine rows into exact method/body ranges, state writer/consumer edges and newly found dependencies. A pending row is not evidence of similarity.

### Slice S1.1: client input sample and local tick/superclass/travel order

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Input and local tick sequence from input assignment through local aiStep/serverAiStep into LivingEntity.aiStep; full client/network tick caller order and loading-screen gate are still open.
- A evidence: ready/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#serverAiStep()V lines 610-621 and #aiStep()V lines 646-805 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5; ready/1.20.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java::ClientPacketListener#handleLogin(ClientboundLoginPacket) lines 358-419 and #handleRespawn(ClientboundRespawnPacket) lines 1022-1099 SHA-256 0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264; ready/1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#aiStep()V lines 2509-2633 SHA-256 5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828.
- B evidence: ready/1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#serverAiStep()V lines 610-621 and #aiStep()V lines 646-805 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5; ready/1.20.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java::ClientPacketListener#handleLogin(ClientboundLoginPacket) lines 371-432 and #handleRespawn(ClientboundRespawnPacket) lines 1052-1129 SHA-256 ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f; ready/1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#aiStep()V lines 2511-2635 SHA-256 f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d.
- State producers/writers -> consumers/readers: LocalPlayer.input/KeyboardInput output -> LocalPlayer.serverAiStep xxa/zza/jumping -> LivingEntity.aiStep movement damping, jump branch and travel dispatch; Level/client/network tick entry still awaiting caller closure.
- Parent slices / dependencies / closure evidence: DEP-03, DEP-04, S1.2-S1.7, S3.1-S3.9; caller ordering and screen/tick behavior remain dependencies.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Login/respawn input assignment and the LocalPlayer/LivingEntity per-player code were compared; the screens differ and the complete outer tick/event order has not yet been closed.
- Finding IDs or checked absence/replacement path: none confirmed; paired source bodies are identical for the movement portion except the separately scoped out-of-scope callback where noted.
### Slice S1.2: directional input conversion, yaw-relative vector math, diagonal normalization and input slowdown

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Keyboard producer, impulse construction, vector access/threshold, sneaking scale and active-use scale in the local player input path.
- A evidence: ready/1.20.2/mojmap/net/minecraft/client/player/Input.java::Input#tick(boolean,float) lines 15-16, #getMoveVector() lines 18-20 and #hasForwardImpulse() lines 22-24 SHA-256 b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb; ready/1.20.2/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean,float) lines 21-34 SHA-256 a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0; ready/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()V lines 655-669 and #serverAiStep()V lines 610-621 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5.
- B evidence: ready/1.20.4/mojmap/net/minecraft/client/player/Input.java::Input#tick(boolean,float) lines 15-16, #getMoveVector() lines 18-20 and #hasForwardImpulse() lines 22-24 SHA-256 b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb; ready/1.20.4/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean,float) lines 21-34 SHA-256 a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0; ready/1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()V lines 655-669 and #serverAiStep()V lines 610-621 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5.
- State producers/writers -> consumers/readers: Keyboard options -> KeyboardInput.tick -> Input impulses -> LocalPlayer slow/use scaling -> LocalPlayer.serverAiStep movement fields; LocalPlayer movement path consumes impulse threshold/vector.
- Parent slices / dependencies / closure evidence: S1.1, S6.3 (sneak speed bonus), S6.4 (active item-use state), S2.2 (pose/crouch conditions), S3/S4 movement consumers.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Input.java, KeyboardInput.java and the full LocalPlayer.java are byte-identical; actual modifiers and consumer dependencies remain to be closed.
- Finding IDs or checked absence/replacement path: none confirmed; paired source bodies are identical for the movement portion except the separately scoped out-of-scope callback where noted.
### Slice S1.3: sprint start/stop predicates, timers and stored sprint state

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: Local sprint start/stop timers and direct gates in aiStep and its local helper methods; vanilla food level is read only as an excluded-system input.
- A evidence: ready/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()V lines 686-715 and #canStartSprinting()Z lines 1013-1022, #vehicleCanSprint(Entity)Z lines 1023-1026, #hasEnoughImpulseToStartSprinting()Z lines 1027-1031, #hasEnoughFoodToStartSprinting()Z lines 1032-1035 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5.
- B evidence: ready/1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()V lines 686-715 and #canStartSprinting()Z lines 1013-1022, #vehicleCanSprint(Entity)Z lines 1023-1026, #hasEnoughImpulseToStartSprinting()Z lines 1027-1031, #hasEnoughFoodToStartSprinting()Z lines 1032-1035 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5.
- State producers/writers -> consumers/readers: LocalPlayer sprint predicates/timers read input, vanilla food state, passenger/on-ground, fluid and collision flags; calls setSprinting and mutates sprintTriggerTime.
- Parent slices / dependencies / closure evidence: S1.1, S1.2, S2.1, S3.6, S4.2/S4.4 and INV-EXCLUSIONS direct-read audit.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Local sprint source and helper bodies are identical. Food/hunger producers are excluded; collision/fluid/environment inputs remain dependencies.
- Finding IDs or checked absence/replacement path: none confirmed; paired source bodies are identical for the movement portion except the separately scoped out-of-scope callback where noted.
### Slice S1.4: jump request, cooldown, jump timing and jump input gates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Local jump input edge/cooldown, vehicle jump branch, flight/elytra start gates and LivingEntity jump branch to the exact player jump override.
- A evidence: ready/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()V lines 717-742 and 772-799 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5; ready/1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#aiStep()V lines 2560-2585 and #jumpFromGround()V lines 2011-2020 SHA-256 5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828; ready/1.20.2/mojmap/net/minecraft/world/entity/player/Player.java::Player#jumpFromGround()V lines 1439-1447 SHA-256 25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c.
- B evidence: ready/1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()V lines 717-742 and 772-799 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5; ready/1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#aiStep()V lines 2562-2587 and #jumpFromGround()V lines 2008-2017 SHA-256 f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d; ready/1.20.4/mojmap/net/minecraft/world/entity/player/Player.java::Player#jumpFromGround()V lines 1440-1448 SHA-256 218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd.
- State producers/writers -> consumers/readers: Input.jumping -> LocalPlayer jump trigger/flight/vehicle predicates -> LivingEntity jump branch -> LivingEntity/Player jumpFromGround state and impulse writer.
- Parent slices / dependencies / closure evidence: S1.1, S1.5, S1.7, S3.4, S3.6, S3.8, S4.7, S6.1/S6.2/S6.4; movement attributes/effects and fluid thresholds remain dependencies.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Paired code bodies match, but jump power, effects, fluid gates and rideable jump consequences are still pending.
- Finding IDs or checked absence/replacement path: none confirmed; paired source bodies are identical for the movement portion except the separately scoped out-of-scope callback where noted.
### Slice S1.5: flight toggles, vertical input, ability state and flight-speed selection

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Local flight toggle and forced-flying gates, local vertical flight input and Elytra start predicate in LocalPlayer.aiStep.
- A evidence: ready/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()V lines 718-742 and 757-770 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5.
- B evidence: ready/1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()V lines 718-742 and 757-770 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5.
- State producers/writers -> consumers/readers: Ability flags and options/state gate LocalPlayer ability writer; vertical input adds to velocity; Elytra/equipment state gates fall-flying transition.
- Parent slices / dependencies / closure evidence: S1.1, S3.8, S4.1, S6.4 and INV-MODIFIERS; ability defaults/synchronization and Elytra applicability are pending.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): LocalPlayer source bodies are identical; the ability/equipment and fall-flying dependencies are not yet audited.
- Finding IDs or checked absence/replacement path: none confirmed; paired source bodies are identical for the movement portion except the separately scoped out-of-scope callback where noted.
### Slice S1.6: auto-jump and input/tick unstuck probes

- Inventory ID(s): INV-TICK, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Auto-jump trigger and candidate path in LocalPlayer move/updateAutoJump and helper predicates, including their exact order and guard.
- A evidence: ready/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#move(MoverType, Vec3)V lines 871-877, #isAutoJumpEnabled()Z lines 878-881, #updateAutoJump(float,float)V lines 882-980, #isHorizontalCollisionMinor(Vec3)Z lines 981-997, #canAutoJump()Z lines 998-1007 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5.
- B evidence: ready/1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#move(MoverType, Vec3)V lines 871-877, #isAutoJumpEnabled()Z lines 878-881, #updateAutoJump(float,float)V lines 882-980, #isHorizontalCollisionMinor(Vec3)Z lines 981-997, #canAutoJump()Z lines 998-1007 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5.
- State producers/writers -> consumers/readers: Player local input/auto-jump state -> movement result/collision flags -> candidate probe and jump request; shape/collision providers and bounding-box movement are downstream.
- Parent slices / dependencies / closure evidence: S1.1, S2.2, S4.1-S4.5, S5.2/S5.7; exact collision and shape dependency closure pending.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): All cited LocalPlayer bodies are identical. Auto-jump outcome depends on collision/query and shape providers not yet audited.
- Finding IDs or checked absence/replacement path: none confirmed; paired source bodies are identical for the movement portion except the separately scoped out-of-scope callback where noted.
### Slice S1.7: riding gates and rider input handoff relevant to player movement

- Inventory ID(s): INV-TICK, INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Local jumpable-vehicle charging/release path and player rideTick caller boundary; separate statistics callback removal is outside this movement slice.
- A evidence: ready/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()V lines 772-799 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5; ready/1.20.2/mojmap/net/minecraft/world/entity/player/Player.java::Player#rideTick()V lines 482-495 SHA-256 25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c; ready/1.20.2/mojmap/net/minecraft/world/entity/Entity.java::Entity#rideTick()V lines 1827-1833 SHA-256 d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b.
- B evidence: ready/1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()V lines 772-799 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5; ready/1.20.4/mojmap/net/minecraft/world/entity/player/Player.java::Player#rideTick()V lines 487-496 SHA-256 218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd; ready/1.20.4/mojmap/net/minecraft/world/entity/Entity.java::Entity#rideTick()V lines 1828-1834 SHA-256 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9.
- State producers/writers -> consumers/readers: Input jump edge/hold -> jumpRidingTicks/jumpRidingScale -> PlayerRideableJumping callback and riding jump packet; vehicle/mount supplied movement remains external.
- Parent slices / dependencies / closure evidence: S1.1, S3.1, S7.4, INV-EXTERNAL and S-EXCL-01; mounted input/ride transitions are not yet closed.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): LocalPlayer vehicle input is unchanged. Player.rideTick has a removed stat callback only; mount transitions/callbacks still require review.
- Finding IDs or checked absence/replacement path: none confirmed; paired source bodies are identical for the movement portion except the separately scoped out-of-scope callback where noted.
### Slice S2.1: player state defaults, initialization, reset and movement-relevant writers

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S2.2: pose choice, dimensions, eye height and resize/collision timing

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S2.3: swimming/crawling and active-use movement state

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S2.4: player edge sneaking and player-specific movement gates

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.1: travel dispatch and branch predicates before motion

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Player travel dispatch for swimming ascent, flight velocity handling, and LivingEntity travel delegation; ignore only separately recorded statistic/exhaustion callback effects.
- A evidence: ready/1.20.2/mojmap/net/minecraft/world/entity/player/Player.java::Player#travel(Vec3)V lines 1450-1477 SHA-256 25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c; ready/1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#travel(Vec3)V lines 2038-2169 SHA-256 5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828.
- B evidence: ready/1.20.4/mojmap/net/minecraft/world/entity/player/Player.java::Player#travel(Vec3)V lines 1451-1473 SHA-256 218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd; ready/1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#travel(Vec3)V lines 2035-2166 SHA-256 f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d.
- State producers/writers -> consumers/readers: LivingEntity.aiStep selected normal or ridden travel; Player.travel adds swim-up and creative-flight vertical behavior then delegates; ordinary movement math resides in LivingEntity.travel.
- Parent slices / dependencies / closure evidence: S1.4/S1.5/S1.7, S3.2-S3.9, S4.1-S4.8, S6.1-S6.4; state/attribute/fluid and entity-move dependencies remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Player.travel movement branches match; only its later statistics callback is removed in B and is handled by S-EXCL-01. The called LivingEntity.travel body matches exactly; its dependencies remain open.
- Finding IDs or checked absence/replacement path: none confirmed; paired source bodies are identical for the movement portion except the separately scoped out-of-scope callback where noted.
### Slice S-EXCL-01: post-movement statistics and food-exhaustion callbacks removed from player travel/riding

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: post-travel `Player.checkMovementStatistics` and `Player.checkRidingStatistics` calls after movement, including all method effects and the player travel/ride call sites.
- A evidence: ready/1.20.2/mojmap/net/minecraft/world/entity/player/Player.java::Player#travel(Vec3)V lines 1450-1477 and #checkMovementStatistics(DDD)V lines 1497-1545; #rideTick()V lines 482-495 and #checkRidingStatistics(DDD)V lines 1547-1565; SHA-256 25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c.
- B evidence: ready/1.20.4/mojmap/net/minecraft/world/entity/player/Player.java::Player#travel(Vec3)V lines 1451-1473 and #rideTick()V lines 487-496; neither method calls a statistics helper and `checkMovementStatistics` / `checkRidingStatistics` are absent from Player.java; SHA-256 218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd.
- State producers/writers -> consumers/readers: old post-move position deltas -> `awardStat`; `checkMovementStatistics` also calls `causeFoodExhaustion` for swimming, water walking, and sprint/walk distance. No position/velocity/pose/collision movement state is written by these helpers. Food exhaustion is explicitly excluded.
- Parent slices / dependencies / closure evidence: separate from S3.1 movement dispatch and S1.7/S7.4 vehicle movement; retained only as an explicit excluded-source difference.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): Source difference confirmed, but the removed calls update statistics and excluded food/exhaustion state only; they do not alter player movement state. Do not emulate these systems or treat the indirect hunger/sprint-gate consequence as a movement finding.
- Finding IDs or checked absence/replacement path: none; B source shows direct return after travel/ride movement and the old helper names have no definition or call in the complete B Player.java source.

### Slice S3.2: ground acceleration, support friction and speed-factor selection

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.3: air acceleration, velocity cutoff/threshold and air-control math

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.4: jump power, sprint-jump impulse and velocity thresholds

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.5: climbing travel and vertical/horizontal clamps

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.6: water travel, fluid gravity/drag, swimming and water-jump gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.7: lava travel, fluid gravity/drag and fluid transition gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.8: fall-flying/gliding travel and look/velocity transforms

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.9: gravity, drag, levitation/slow-fall and post-travel updates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.1: bounding-box movement entry, position update and query timing

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.2: axis collision order, clipping and velocity cancellation/restitution

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.3: step-up candidate generation, selection and tie-breaking

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.4: edge restraint/support probing and stored on-ground state

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.5: collision-shape lookup, context, AABB/voxel operations and candidate ordering

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.6: block collision callbacks, inside-block effects and movement callbacks

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.7: fluid contact/current checks during movement and collision

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.8: collision/support shape context from pose, neighboring blocks and repeated queries

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.1: movement-relevant block/fluid registrations and base defaults

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.2: all registered shape overrides and collision/support shapes

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.3: ground friction, speed/jump factors and support block selection

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.4: special contacts and movement callbacks (bounce, slow/contact, launch)

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.5: climbables, bubble columns, pistons and neighboring-state movement effects

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.6: water/lava fluid properties, heights and flow vectors

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.7: historical partial-block shape/support inventory

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.1: movement attribute defaults, aggregation, operations and application/removal

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.2: movement status effect registrations, formulas, timers and consumers

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.3: movement enchantment registrations, level formulas, predicates and consumers

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.4: Elytra, equipment slots, active item use and movement components

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.5: resource-backed tags, registry data, server-synchronized movement inputs

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S7.1: incoming player velocity and position corrections

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S7.2: player knockback, push and explosion movement writers

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S7.3: piston displacement and launch-item movement paths

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S7.4: mount, rider and dismount transitions affecting player movement

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S7.5: cross-stage writer/caller/callback/dependency closure

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

## Dependency queue and blockers

- DEP-01; source provenance for exact 1.20.2 and 1.20.4 Mojmap roots; resolved. Both readiness JSON records, exact IDs/namespace, source and artifact manifest hashes, movement diagnostics and relevant source/artifact file hashes have been verified. This is the source prerequisite only, not movement coverage.
- DEP-02; originating slices S5.1-S6.5; inspect original client-jar resources and referenced movement tags/defaults/registry values. Resolve after source and registration inventories establish the needed entries; owner: source worker.
- DEP-03; originating slice S1.1; outer tick/caller order, `ClientPacketListener` tick, new level-load manager/screen path and relationship to player input/movement are not yet closed. Exact A/B sources exist; next action: inspect bounded callers and compare screen/input gates; owner: source worker.
- DEP-04; originating slices S1.2/S1.3/S6.3/S6.4; close `isMovingSlowly`, Swift Sneak and active-item-use state producers/consumers; explain unchanged local scaling after modifier/equipment source review; owner: source worker.
- Open dependencies: DEP-02, DEP-03, DEP-04 and the method/data dependencies listed on open coverage slices.

## Finding index

- No findings confirmed yet. Zero findings does not imply equivalence.
- Discarded candidates: none yet.

## Resume checkpoint

- Last completed slice: source-pair readiness/hashes validated; initial bounded comparisons of local input and core travel/jump/movement methods completed, with remaining dependencies recorded.
- Next bounded slice and exact files/members/body ranges to open: complete S1.1 by tracing `Minecraft.tick` -> client connection/packet listener tick -> `ClientLevel.tickEntities` -> `LocalPlayer` tick/aiStep order; inspect `ClientPacketListener.tick`, `LevelLoadStatusManager.tick`, and A/B `ReceivingLevelScreen` caller gates. Then close S1.2 through attributes/equipment, before continuing stages 2-7.
- Outstanding dependencies and owners: DEP-02/03/04; source worker. Independent reviewer not assigned yet.
- Current assumptions requiring verification: source hashes remain stable while read-only; exact caller order is the same except for the observed level-load screen and tick-rate manager additions; `Player.travel`/ride callback removals remain limited to excluded statistic/food state.

## Implementation reconciliation

Complete only after blind-discovery freeze; not authorized for this source-only assignment unless the parent explicitly changes the role.

- Reconciliation status: pending
- Repository revision inspected: not applicable yet
- Finding -> implementation disposition/evidence: deferred until source-only freeze and explicit role change
- Existing implementation without a frozen source finding: deferred
- Coverage gaps routed back to discovery slices: pending source audit

## Independent source audit

- Reviewer: not yet assigned; must differ from discovery author
- Status: pending
- Inventories and call-chain ranges re-walked: none
- Concrete missed-slice routes (or `none found`): pending review
- Misses routed to slice/finding IDs and owners: none identified yet
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: pending 39; in-progress 8; compared-no-difference 0; findings 0; not-applicable 1; blocked 0.
- Required inventory status and evidence: INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT, INV-MODIFIERS, INV-EXTERNAL, and INV-EXCLUSIONS all pending; readiness is established but their inventories are not yet closed.
- Open dependencies: DEP-02, DEP-03, DEP-04 and the open slice dependencies listed above
- Unresolved gaps and limits: exact source publication is validated; most source inventory remains open and no whole-run equivalence claim has been made.
- Evidence/hash/correspondence audit: core input/travel source hashes and manifests checked; remaining cited providers/resources will be hashed at inspection.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).

This source-only report is not frozen. Keep implementation and runtime statuses separate. Runtime validation is not authorized in this task.
