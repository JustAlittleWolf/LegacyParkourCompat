# Discovery: 1.20.2 to 1.20.4

- Status: active
- Scope: direct client player movement and the complete reachable vanilla movement call graph. Older A = 1.20.2; newer B = 1.20.4. This is a source-only discovery track; runtime Java implementation is not authorized here.
- Scope exclusions: health/food state production (including regeneration, hunger, saturation and exhaustion), attack/damage calculations and combat state, and non-player/vehicle physics. Direct local-player position, velocity, impulse and knockback response from native triggers remains in scope even when combat can trigger it. Vanilla values may be read as movement inputs; excluded producer systems are not emulated. Modern-only features remain modern-only.
- Evidence constraints: exact-version decompiled sources and bytecode only. No Minecraft Wiki/MCPK browsing, release-note mechanics, old implementation, old patch classes, or wiki-audit outputs before freeze. Prior discovery reports are allowed only as navigation; none were used for this checkpoint.
- Repository revision and start date: source baseline `002137b227676caea77f6832b9f4c8d0b6200bff` (`main` at worktree creation), 2026-10-07. Workflow-hardening commits cherry-picked: `40c34f5` and `2422192`.
- Worktree / branch: `C:/Users/Wolfi/.codex/visualizations/2026/10/07/01a116cd-4e53-7832-8dbd-7854d8fc7bc2/source-campaign-worktree`; `feat/source-discovery-movement-source-1-20-2-1-20-4`. This replacement checkout was created from the preserved branch tip `1dee4b8` after the formerly attached Codex worktree became unavailable; the primary `main` checkout remains untouched.
- Selected naming namespace, CLI mode per side and alignment evidence: `mojmap` / official Mojang names on both sides; both exact readiness records and mapping artifacts were checked and hashes match the source preparation manifests.
- Source preparation owner / command / log / readiness marker: source owner only. Exact 1.20.2 and 1.20.4 Mojmap readiness records are published and validated under `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07`. No decompile command was run by this worker.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; TinyRemapper 0.14.1; Mapping-IO 0.9.1; Gson 2.14.0; ASM 9.10.1; decompiler heap 4G.
- Discovery author(s): this task's source worker.
- Independent reviewer (must differ from discovery authors): not yet assigned.

## Artifact manifest

- Shared evidence root: `build/movement-campaign-2026-10-07/`; readiness files and source/artifact manifests were read and hash-checked.
- A (1.20.2): requested/resolved/metadata IDs all `1.20.2`; status `ready`; CLI `mojmap`; source root `ready/1.20.2/mojmap`; 4,904 Java files. Original client jar `artifacts/1.20.2/client.jar`, SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`, Mojang metadata SHA-1 `82d1974e75fc984c5ed4b038e764e50958ac61a0`. Official client mappings `artifacts/1.20.2/client_mappings.txt`, SHA-256 `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd`, publisher SHA-1 `5c292ff7d3161977041116698e295083fd5ec8f5`. Mojmap jar `artifacts/1.20.2/client-mojmap.jar`, SHA-256 `3dce01cbbb74e367414c811440ca6e80bc2ec18284386720ce413f9aec0ba339`. `ready/1.20.2/mojmap.sources.sha256` SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; `ready/1.20.2/artifacts.sha256` SHA-256 `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54`; `ready/1.20.2/movement-diagnostics.txt` SHA-256 `a60a6ea2a91cda32df8a7b837d5344ae54c8370058cc9ab3ae163b30e8fa6b79`.
- B (1.20.4): requested/resolved/metadata IDs all `1.20.4`; status `ready`; CLI `mojmap`; source root `ready/1.20.4/mojmap`; 5,048 Java files. Original client jar `artifacts/1.20.4/client.jar`, SHA-256 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`, Mojang metadata SHA-1 `fd19469fed4a4b4c15b2d5133985f0e3e7816a8a`. Official client mappings `artifacts/1.20.4/client_mappings.txt`, SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`, publisher SHA-1 `be76ecc174ea25580bdc9bf335481a5192d9f3b7`. Mojmap jar `artifacts/1.20.4/client-mojmap.jar`, SHA-256 `d20c183016505cceecd33d508973e7ca6f2ee8cbd860f316592f4a09a96bc339`. `ready/1.20.4/mojmap.sources.sha256` SHA-256 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; `ready/1.20.4/artifacts.sha256` SHA-256 `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948`; `ready/1.20.4/movement-diagnostics.txt` SHA-256 `8909a0341c2141f476160c19a05cbf2ef39e28bee7357dd50e3693e4a3174879`.
- The two readiness JSON files' `status`, exact requested/resolved/metadata release IDs, namespace, output roots, client hashes, source-manifest hashes, artifact-manifest hashes, and diagnostics hashes were checked. Manifest and diagnostics actual SHA-256 values match the JSON. The client jar, mapping file, and remapped jar hashes match artifact-manifest entries on both sides. Cited source files are checked against their source manifest entries.
- Artifact revision check (2026-10-07): `feather-r1-2026-10-07` revises derived Feather mapped jars only for 1.8.9, 1.9.4, 1.10.2, 1.11.2, 1.12.2 and 1.13.2. It does not apply to this 1.20.2/1.20.4 Mojmap pair. Re-read readiness and manifests and freshly re-hashed both source/artifact manifests, diagnostics, all cited core and piston/slime/honey/bubble Java files, and the original client jars, mapping files and Mojmap jars; the exact recorded hashes still match. The revised snapshots and the missing-original-jar limitation are not evidence inputs to this pair.
- Preparation command (shared source owner): `.\gradlew.bat decompileMinecraft --versions=1.20.1,1.20.2,1.20.4,1.20.6 --mappings=mojmap --decompiler-heap=4G --output-root=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\mojmap-1.20.1-to-1.20.6-3e08bcae66a145b296f7f85b12623999 --cache-directory=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts`.
- Success log: `staging/mojmap-1.20.1-to-1.20.6-3e08bcae66a145b296f7f85b12623999/gradle.full.log`; exact requested/resolved release IDs were logged. Readiness diagnostics contain 22 movement anchors per side and confirm required Entity/LivingEntity/Player/LocalPlayer classes. Filtered log review found only Gradle deprecation notices, no Vineflower error, exception, failed download or missing-library warning.
- Toolchain from provenance: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; TinyRemapper 0.14.1; Mapping-IO 0.9.1; Gson 2.14.0; ASM 9.10.1; heap 4G. Mapping alignment is the official Mojang-name namespace on both sides using each release's own mapping artifact.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending
- Evidence inventory and finding IDs included at freeze: none yet
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither wiki was browsed; no old implementation, old patch class, or wiki-audit output was opened.
- Source/mapping hashes covered by freeze: pending blind freeze; both source and mapping manifest hashes are validated in the Artifact manifest.

## Correspondence and call order

- Verified same-name/same-descriptor core correspondence: `net.minecraft.client.player.LocalPlayer#aiStep()V`, `#serverAiStep()V`, `#move(MoverType, Vec3)V`, `#updateAutoJump(FF)V`, and its local-player sprint/auto-jump helpers; `net.minecraft.client.player.Input#tick(ZF)V`, `#getMoveVector()Vec2`, `#hasForwardImpulse()Z`; `KeyboardInput#tick(ZF)V`; `net.minecraft.world.entity.player.Player#aiStep()V`, `#jumpFromGround()V`, `#travel(Vec3)V`, `#rideTick()V`; `net.minecraft.world.entity.LivingEntity#jumpFromGround()V`, `#travel(Vec3)V`, `#travelRidden(Player, Vec3)V`, `#tick()V`, `#aiStep()V`; `net.minecraft.world.entity.Entity#move(MoverType, Vec3)V`, `#moveRelative(float, Vec3)V`, `#updateInWaterStateAndDoFluidPushing()Z`, and relevant movement state writers. Official Mojmap class/member names and source method bodies were checked directly; descriptor/name equality alone is not the evidence.
- Exact endpoint source roots: `ready/1.20.2/mojmap/` and `ready/1.20.4/mojmap/`, relative to the shared evidence root. Both roots pass readiness and manifest verification. The four required movement classes are present and body diagnostics point to the corresponding local player, player, living entity, and entity methods.
- Partial input/tick sequence verified from sources: login/respawn packet handlers assign `new KeyboardInput(options)` to the local player; `LocalPlayer.aiStep` samples input, applies slow-input and item-use scaling, handles sprint/jump/flying/vehicle input, then calls `Player.aiStep`; `LivingEntity.aiStep` reaches `LocalPlayer.serverAiStep`, which copies input impulses and jumping into inherited movement fields for this locally controlled player, then damping/jump/travel and post-travel steps run. Exact caller order from the client/network and level tick, plus its new loading-screen path, remains under investigation.
- Pair comparison of core methods: `LocalPlayer.java` is byte-identical across A/B (SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` on both sides); `Input.java` (`b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb`) and `KeyboardInput.java` (`a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`) are also byte-identical. The inspected `LivingEntity` jump, travel, tick, and aiStep bodies and `Entity.move` / `moveRelative` bodies compare byte-for-byte after extraction despite shifted line numbers. `Player.aiStep`, `Player.jumpFromGround`, and `Player.getDimensions` also compare byte-for-byte. `Player.travel` and `Player.rideTick` have removed post-movement statistic callbacks in B; this candidate is separately dispositioned under the explicit exclusion slice below because it changes statistics/food exhaustion only, not movement state.
- Movement math must still be followed through external state/data producers, collision providers, fluids, attributes/effects/equipment and resources. The comparisons above are bounded evidence only, not closure for any full stage.

## Required source inventories

Each inventory maps to bounded slice IDs below. All are pending until each member-level slice and dependency is inspected on both exact sides.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1.1-S1.7,S3.1-S3.9; evidence=validated source roots; member-level body comparison remains open.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S1.2-S1.6,S2.1-S2.4,S3.1-S3.9,S4.1-S4.8; evidence=validated source roots; writer/consumer inventory remains open.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S2.2,S4.1-S4.8,S5.2,S5.7; evidence=validated source roots; exact providers, registrations and calls remain open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S3.2,S3.5-S3.7,S4.5-S4.8,S5.1-S5.7; evidence=validated source roots; provider and resource manifests remain open.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S2.3,S3.2,S3.4,S3.6,S3.8-S3.9,S6.1-S6.5; evidence=validated source roots; consumers through registrations/data remain open.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S1.7,S5.5,S7.1-S7.5; evidence=validated source roots; exact packet/callback/player state writers remain open.
- `INV-EXCLUSIONS` explicit scope audit for health/food state production, attack/damage calculations, combat state and non-player/vehicle physics, while retaining direct player impulse/knockback responses: status=pending; slice_ids=S1.3,S2.4,S3.4,S7.2; evidence=validated source roots; direct consumer bounds and explicit exclusion audit remain open.

## Coverage ledger

Every row below is a bounded behavior planning slice, not a claim of inspected methods or pair correspondence. Refine rows into exact method/body ranges, state writer/consumer edges and newly found dependencies. A pending row is not evidence of similarity.

### Slice S1.1: client input sample and local tick/superclass/travel order

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Input assignment through local aiStep/serverAiStep and LivingEntity.aiStep, plus outer Minecraft.tick -> ClientLevel.tickEntities -> Entity.tick -> LivingEntity.tick/aiStep order, network listener tick, receiving-level screen gate, and 1.20.4 tick-rate manager inputs/consumers.
- A evidence: ready/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#serverAiStep()V lines 610-621 and #aiStep()V lines 646-805 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5; ready/1.20.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java::ClientPacketListener#handleLogin(ClientboundLoginPacket) lines 358-419, #handleRespawn(ClientboundRespawnPacket) lines 1022-1099 and #tick()V lines 2239-2253 SHA-256 0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264; ready/1.20.2/mojmap/net/minecraft/client/Minecraft.java::Minecraft#tick()V lines 1744-1861 SHA-256 c8d5717ded08d6491b1c49c7aa388c081db883059b6efeea82e09df0868f57ea; ready/1.20.2/mojmap/net/minecraft/client/multiplayer/ClientLevel.java::ClientLevel#tick(BooleanSupplier)V lines 206-216 and #tickEntities()V lines 244-254 SHA-256 6204df69a17b375e46f4405fe67a106def336c819e589ec0794c04920a31c5d7; ready/1.20.2/mojmap/net/minecraft/client/gui/screens/ReceivingLevelScreen.java lines 41-74 SHA-256 d3a2f973a72ed927c4fe8a62c95980d00aae3db8ddd6b591783293ae5f776c1b.
- B evidence: ready/1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#serverAiStep()V lines 610-621 and #aiStep()V lines 646-805 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5; ready/1.20.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java::ClientPacketListener#handleLogin(ClientboundLoginPacket) lines 371-432, #handleRespawn(ClientboundRespawnPacket) lines 1052-1129, #tick()V lines 2306-2323, #handleTickingState(ClientboundTickingStatePacket) lines 529-537, #handleTickingStep(ClientboundTickingStepPacket) lines 540-545 and #startWaitingForNewLevel(LocalPlayer,ClientLevel) lines 1353-1355 SHA-256 ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f; ready/1.20.4/mojmap/net/minecraft/client/Minecraft.java::Minecraft#tick()V lines 1791-1917 SHA-256 4c672a48906a76408bfbf77fda303d6cead7b22c6e4632aed7a5d8182e057b0a; ready/1.20.4/mojmap/net/minecraft/client/multiplayer/ClientLevel.java::ClientLevel#tick(BooleanSupplier)V lines 209-222 and #tickEntities()V lines 250-260 SHA-256 2bc6147b758ba25fea89e1c7c8e28000cf323f1616b01b62af461cbce230e308; ready/1.20.4/mojmap/net/minecraft/client/multiplayer/LevelLoadStatusManager.java::tick()V and #loadingPacketsReceived()V lines 19-40 SHA-256 2d871f1c73e8391e3fe049552702210566d49857d7d02d20b781f3148d75dbec; ready/1.20.4/mojmap/net/minecraft/client/gui/screens/ReceivingLevelScreen.java lines 42-56 SHA-256 ca5d060de1b0068ce44ddbe51c3de672d3dd1695073d7615caf8765c79cad320; ready/1.20.4/mojmap/net/minecraft/world/TickRateManager.java::TickRateManager#tick()V and #isEntityFrozen(Entity)Z lines 56-65 SHA-256 ba5b2cddb1850e2e510867b4642004a8b7b6b39c31afc48ef806f7988981eebb; ready/1.20.4/mojmap/net/minecraft/world/entity/Entity.java::Entity#countPlayerPassengers()I lines 2914-2916 SHA-256 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9; these paths are present in the 1.20.4 source manifest.
- State producers/writers -> consumers/readers: Login/respawn creates KeyboardInput; keyboard options -> LocalPlayer.aiStep input sampling/scaling -> LocalPlayer.serverAiStep xxa/zza/jumping -> LivingEntity.aiStep damping, jump branch and travel dispatch. Minecraft.tick continues non-pausing level/entity ticks with ReceivingLevelScreen open, while keybind handling is screen-gated. In B, incoming ticking-state/step packets -> TickRateManager state -> ClientLevel.tick/tickEntities; frozen non-player entities are skipped, but Player is exempt and Entity.countPlayerPassengers exempts vehicles carrying a player.
- Parent slices / dependencies / closure evidence: DEP-04, S1.2-S1.7, S3.1-S3.9, S4/S5 collision and world-input slices. DEP-03 outer caller/screen/tick control is source-closed; travel state and environment dependencies remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The A/B player input and movement methods remain byte-identical. The receiving screen differs in how it waits for chunk readiness, but is non-pausing in both versions; Minecraft.tick still invokes ClientLevel.tickEntities while it is open, although keybind processing is skipped. B adds server-controlled tick-rate freezing: direct LocalPlayer ticks are not frozen, and vehicles with player passengers are exempt, while other entities can be frozen. This is a modern world-control feature with no historical player movement delta; do not emulate it for 1.20.2 maps. Its possible effect through non-player world state stays outside the declared movement scope.
- Finding IDs or checked absence/replacement path: none confirmed for the screen/tick caller path; A/B player input and movement bodies match, and the B-only tick-rate path is modern-only/excluded as described above.
### Slice S1.2: directional input conversion, yaw-relative vector math, diagonal normalization and input slowdown

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Keyboard producer, impulse construction, vector access/threshold, crouch/crawl slowdown, Swift Sneak bonus and active-use scale in the local player input path.
- A evidence: ready/1.20.2/mojmap/net/minecraft/client/player/Input.java::Input#tick(boolean,float) lines 15-16, #getMoveVector() lines 18-20 and #hasForwardImpulse() lines 22-24 SHA-256 b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb; ready/1.20.2/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean,float) lines 21-34 SHA-256 a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0; ready/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#isMovingSlowly()Z lines 605-607 and #aiStep()V lines 655-669 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5; ready/1.20.2/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java::getSneakingSpeedBonus(LivingEntity)F lines 193-195 SHA-256 c98b9e538aae7fea33a125560400f346745bac993e859ffd3912da58f3cc1128; ready/1.20.2/mojmap/net/minecraft/world/item/enchantment/Enchantments.java::SWIFT_SNEAK lines 27-32 SHA-256 35fa4d1f9da93971fa70bf6ec2f9218a8825ff343038b51143ae1404867f38e3; ready/1.20.2/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java::getEnchantmentLevel(Enchantment,LivingEntity)I lines 175-191 SHA-256 c98b9e538aae7fea33a125560400f346745bac993e859ffd3912da58f3cc1128; ready/1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java::isUsingItem()Z and #startUsingItem(InteractionHand)V lines 2888-2957 SHA-256 5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828; ready/1.20.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java::handleSetEntityData(ClientboundSetEntityDataPacket)V lines 490-496 SHA-256 0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264.
- B evidence: ready/1.20.4/mojmap/net/minecraft/client/player/Input.java::Input#tick(boolean,float) lines 15-16, #getMoveVector() lines 18-20 and #hasForwardImpulse() lines 22-24 SHA-256 b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb; ready/1.20.4/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean,float) lines 21-34 SHA-256 a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0; ready/1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#isMovingSlowly()Z lines 605-607 and #aiStep()V lines 655-669 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5; ready/1.20.4/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java::getSneakingSpeedBonus(LivingEntity)F lines 193-195 SHA-256 c98b9e538aae7fea33a125560400f346745bac993e859ffd3912da58f3cc1128; ready/1.20.4/mojmap/net/minecraft/world/item/enchantment/Enchantments.java::SWIFT_SNEAK lines 27-32 SHA-256 35fa4d1f9da93971fa70bf6ec2f9218a8825ff343038b51143ae1404867f38e3; ready/1.20.4/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java::getEnchantmentLevel(Enchantment,LivingEntity)I lines 175-191 SHA-256 c98b9e538aae7fea33a125560400f346745bac993e859ffd3912da58f3cc1128; ready/1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java::isUsingItem()Z and #startUsingItem(InteractionHand)V lines 2890-2959 SHA-256 f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d; ready/1.20.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java::handleSetEntityData(ClientboundSetEntityDataPacket)V lines 503-509 SHA-256 ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f.
- State producers/writers -> consumers/readers: Keyboard options -> KeyboardInput.tick -> Input impulses -> LocalPlayer slow/use scaling -> LocalPlayer.serverAiStep movement fields; Swift Sneak level -> EnchantmentHelper traverses the enchantment-defined equipment slots, takes the maximum item level, and applies 0.15F per level before the LocalPlayer clamp. Server-side LivingEntity.startUsingItem writes synchronized flags; ClientboundSetEntityDataPacket applies them through ClientPacketListener and LivingEntity.onSyncedDataUpdated supplies client use state consumed by the same local input gate.
- Parent slices / dependencies / closure evidence: S1.1, S6.3 (broader enchantment/equipment resolution), S6.4 (broader item-use/equipment movement state), S2.2 (pose/crouch conditions), S3/S4 movement consumers. DEP-04 is resolved for this direct local-input modifier chain; broader modifier coverage remains open under S6.3/S6.4.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Input.java, KeyboardInput.java, full LocalPlayer.java, EnchantmentHelper.java and Enchantments.java are byte-identical across A/B. The crouch bonus formula and input scaling are unchanged. Swift Sneak is registered for leggings in both sources; helper traversal and item-level max selection match. Active use is read from synchronized LivingEntity flags, with the same server writer and client entity-data consumer. Broader equipment and movement-modifier consumers remain open, so this slice stays in progress.
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
- Exact behavior boundary and enclosing guards/order checked: Entity movement-state defaults, shared Entity initialization and position/velocity writers, Player spawn initialization, and LocalPlayer resetPos placement/velocity reset; subclass state producers and packet writers remain open.
- A evidence: ready/1.20.2/mojmap/net/minecraft/world/entity/Entity.java movement fields lines 154-176, #Entity(EntityType,Level)V lines 243-262, #setPos(DDD)V lines 384-387, #getDeltaMovement()Vec3/#setDeltaMovement(Vec3)V lines 3160-3174 and #setPosRaw(DDD)V lines 3228-3244 SHA-256 d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8d8c5d23b01b; ready/1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java movement fields `jumping`, `xxa/yya/zza`, `noJumpDelay`, fall-flying/use state lines 185-232 and #LivingEntity(EntityType,Level)V lines 234-247 SHA-256 5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828; ready/1.20.2/mojmap/net/minecraft/world/entity/player/Player.java::Player(Level,BlockPos,float,GameProfile)V lines 181-189 SHA-256 25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c; ready/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java::resetPos()V lines 627-643 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5.
- B evidence: ready/1.20.4/mojmap/net/minecraft/world/entity/Entity.java movement fields lines 155-177, #Entity(EntityType,Level)V lines 244-263, #setPos(DDD)V lines 385-388, #getDeltaMovement()Vec3/#setDeltaMovement(Vec3)V lines 3166-3180 and #setPosRaw(DDD)V lines 3234-3250 SHA-256 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9; ready/1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java movement fields `jumping`, `xxa/yya/zza`, `noJumpDelay`, fall-flying/use state lines 184-231 and #LivingEntity(EntityType,Level)V lines 233-246 SHA-256 f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d; ready/1.20.4/mojmap/net/minecraft/world/entity/player/Player.java::Player(Level,BlockPos,float,GameProfile)V lines 176-184 SHA-256 218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd; ready/1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java::resetPos()V lines 627-643 SHA-256 bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5.
- State producers/writers -> consumers/readers: Entity constructor/defaults -> bounding box, pose, position and velocity readers; Player constructor spawn position/rotation -> initial local player state; LocalPlayer.resetPos -> standing pose, collision-tested vertical placement, zero velocity and pitch reset. Direct server corrections and later class-specific movement state remain separate writer dependencies.
- Parent slices / dependencies / closure evidence: S1.1 login/respawn creation, S2.2 pose/dimensions, S3.1 movement state consumers, S7.1 corrections, S7.2 pushes, S7.4 mount transitions, S6.1 player attributes; DEP-01 resolved.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The inspected A/B Entity and LivingEntity movement defaults/constructors, Entity position/velocity setters and Player constructor have the same behavior; full LocalPlayer.resetPos is byte-identical. Brace-bounded body hashes match for Entity constructor (`682f5259b07f0a5bcd7e470fd039b87cfbc1e6a9410ae9eda9f9501dc3e41899`), setPos (`b3786beb2f409b38ef2c692fdd6fcc65afbbd7f925b3550a64b895d608313b0b`), setDeltaMovement (`64a7ca0ab043bb5fae6fd46dbc430a9f15103c37c3aef38aa9f6d385a3dbeca2`), setPosRaw (`7279ff60589371b4c7e1d10944936aa35784d297435e6a0c4c98d55e2123bc70`), LivingEntity constructor (`4a5058bbab8c7b6e1270e8dc44bc9f56929fbe18da98ae5526809bcd05f918bb`), Player constructor (`29faac1e031386aece37b4f8ff3b8d6024d35e7cb7b0a3791886c51feb62a97a`) and LocalPlayer.resetPos (`c07ba015ccbe1672352681a21c479ca119a55f1e5f4931e152369c8db4a09541`). The local reset also restores health/death-time fields, which are excluded. For login/respawn, ClientPacketListener creates the local player and assigns input before this initialization chain runs. External correction/push writers and remaining class-specific state transitions are still open.
- Finding IDs or checked absence/replacement path: none confirmed in the bounded defaults/reset/writer ranges inspected.

### Slice S2.2: pose choice, dimensions, eye height and resize/collision timing

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Player pose priority and fit fallback, pose/dimension table, eye-height selection, and the shared pose-change dimension refresh/collision timing path.
- A evidence: ready/1.20.2/mojmap/net/minecraft/world/entity/player/Player.java::updatePlayerPose()V and #canPlayerFitWithinBlocksAndEntitiesWhen(Pose)Z lines 373-405, #getStandingEyeHeight(Pose,EntityDimensions)F lines 1907-1918, #getDimensions(Pose)EntityDimensions lines 2017-2020, and POSES table lines 122-139 SHA-256 25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c; ready/1.20.2/mojmap/net/minecraft/world/entity/Entity.java::setPose(Pose)V lines 352-354, #onSyncedDataUpdated(EntityDataAccessor)V lines 2706-2710, #refreshDimensions()V lines 2720-2755 and #getEyeHeight(Pose,EntityDimensions)F lines 2773-2775 SHA-256 d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8d8c5d23b01b.
- B evidence: ready/1.20.4/mojmap/net/minecraft/world/entity/player/Player.java::updatePlayerPose()V and #canPlayerFitWithinBlocksAndEntitiesWhen(Pose)Z lines 373-405, #getStandingEyeHeight(Pose,EntityDimensions)F lines 1833-1844, #getDimensions(Pose)EntityDimensions lines 1943-1946, and POSES table lines 121-138 SHA-256 218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd; ready/1.20.4/mojmap/net/minecraft/world/entity/Entity.java::setPose(Pose)V lines 353-355, #onSyncedDataUpdated(EntityDataAccessor)V lines 2708-2712, #refreshDimensions()V lines 2722-2757 and #getEyeHeight(Pose,EntityDimensions)F lines 2777-2779 SHA-256 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9.
- State producers/writers -> consumers/readers: Player.aiStep pose predicates (flight/sleep/swim/spin/sneak) -> can-fit queries for swimming, desired pose, crouch fallback and swim fallback -> synchronized DATA_POSE -> Entity.onSyncedDataUpdated -> refreshDimensions updates EntityDimensions, eyeHeight, bounding box and position cache. Player pose table and eye-height switch are equal; collision-query implementation/provider dependencies are tracked in S4/S5.
- Parent slices / dependencies / closure evidence: S1.1/S1.4/S1.5 (player input, jump/fall-flying), S2.1 defaults/reset, S4.1-S4.8 collision queries, S5.2/S5.7 shape providers and S6.4 equipment gates; DEP-01 resolved.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The player pose priority/fallback, fit box deflation, pose dimensions table and player eye-height values match in A/B. Brace-bounded body SHA-256 fingerprints match for Player.updatePlayerPose (`1874d3b4fe473a84fd95328271d08579ff80179835eab6957cfc25cfe7247cc2`), can-fit query (`62ac4e2bbf754c5668843b5d547fad0c2f0908e707ef525a63023f160b4c3220`), eye-height switch (`77933313220a9f488ed693b3b27d5d21e421f5db47b8a8b91c8c5500dfb09683`), dimensions lookup (`80d7809ce5cc5e72e23ae4c4ef4f397bd623879d87a1a52e74e3c5a25f6106ad`), Entity.onSyncedDataUpdated (`d2be3afdbd031facde741b118eb9091ab94e924feff69636c903e3215210e826`) and Entity.refreshDimensions (`5a1e863d4a72d75ea3e47733398a2e47acd8a23e4029c854802fd4007c7e7e80`). The common pose-change handler recalculates dimensions/eye height and reapplies position in both versions. Its free-position expansion is guarded to server-side non-Player entities, so it does not alter the local player branch. Exact collision query providers and pose predicate producers remain dependencies.
- Finding IDs or checked absence/replacement path: none confirmed in the inspected player pose/dimension path.

### Slice S2.3: swimming/crawling and active-use movement state

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S2.4: player edge sneaking and player-specific movement gates

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
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
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.3: air acceleration, velocity cutoff/threshold and air-control math

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.4: jump power, sprint-jump impulse and velocity thresholds

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.5: climbing travel and vertical/horizontal clamps

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.6: water travel, fluid gravity/drag, swimming and water-jump gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.7: lava travel, fluid gravity/drag and fluid transition gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.8: fall-flying/gliding travel and look/velocity transforms

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.9: gravity, drag, levitation/slow-fall and post-travel updates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.1: bounding-box movement entry, position update and query timing

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.2: axis collision order, clipping and velocity cancellation/restitution

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.3: step-up candidate generation, selection and tie-breaking

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.4: edge restraint/support probing and stored on-ground state

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.5: collision-shape lookup, context, AABB/voxel operations and candidate ordering

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.6: block collision callbacks, inside-block effects and movement callbacks

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.7: fluid contact/current checks during movement and collision

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.8: collision/support shape context from pose, neighboring blocks and repeated queries

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.1: movement-relevant block/fluid registrations and base defaults

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.2: all registered shape overrides and collision/support shapes

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.3: ground friction, speed/jump factors and support block selection

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.4: special contacts and movement callbacks (bounce, slow/contact, launch)

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.5: climbables, bubble columns, pistons and neighboring-state movement effects

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: bounded climbable tag/reader, ladder/vine/bed/piston shape and support/update methods, block movement-property readers and vanilla registrations in exact client jars. Direct state production and unrelated block-tick changes remain vanilla; collision query and full registry/state closure remain open.
- A evidence: `ready/1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java`::`onClimbable` 1511-1527, `trapdoorUsableAsLadder` 1529-1538; `net/minecraft/world/entity/Entity.java`::`getBlockPosBelowThatAffectsMyMovement` 790-794, `getBlockJumpFactor` 819-823, `getBlockSpeedFactor` 825-833; `net/minecraft/world/level/block/Block.java`::`stepOn` 340-342, `fallOn` 373-376, `updateEntityAfterFallOn` 377-380, `getFriction` 385-388, `getSpeedFactor` 389-392, `getJumpFactor` 393-396 SHA-256 `0c2df3d958078929c4cc2985e0d6a6a766cbbbb02066e6c9ac471981aa858656`; `net/minecraft/world/level/block/state/BlockBehaviour.java`::`BlockStateBase.getCollisionShape(BlockGetter,BlockPos,CollisionContext)` 615-617, `BlockStateBase.entityInside` 693-695, `Properties.copy` 949-976 SHA-256 `a6d88726ce4642fc0a61f46a01347bb0b74887b61ef55af7e7c76723789a0b57`; `net/minecraft/world/level/block/Blocks.java` movement-property registrations SHA-256 `75e8e77adab4a07b02ef95619be83a004f5f79810cf3f07157a713d714881253`; `LadderBlock.java`::`getShape` 36-48, `canSurvive` 56-59, `updateShape` 62-72, `getStateForPlacement` 76-99 SHA-256 `67c9784a299c0acda09e76bb9e497ac84fa9a1a431d5af8cb88e44c996b279df`; `VineBlock.java`::`getShape` 84-86, `canSurvive` 94-96, `updateShape` 165-172, `getStateForPlacement` 294-310 SHA-256 `0db839fef2d958d842f87cf5d015f76768fd90b1ef6aa118ea33ecaed321f79d`; `BedBlock.java`::`fallOn` 126-128, `updateEntityAfterFallOn` 131-137, `bounceUp` 139-145, `updateShape` 148-156, `getShape` 190-202 SHA-256 `aa5fad8b3d4e77af5064b49f2670fa47b259a82e8dbbb80023111439f784b6f4`; `piston/PistonBaseBlock.java`::`getShape` 61-81, `getStateForPlacement` 107-109 SHA-256 `adb5c06fa4e8337bd099f21a7ff40de46c8d784b06931d840eacbd4036e050b3`; `piston/PistonHeadBlock.java`::`getShape` 90-92, `updateShape` 123-127, `canSurvive` 130-133 SHA-256 `85a667cf8fb199115ef844325ad14cf80d615649984c790efc60008cc9e49e54`. `artifacts/1.20.2/client.jar` SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`; `data/minecraft/tags/blocks/climbable.json` SHA-256 `d0e3e76d7457f3f3f3d7219fe218c7746e4b2e7626d5c388fcf193089069e365`; `data/minecraft/tags/blocks/beds.json` SHA-256 `aeab40ccdf3fa735e4229bad0a60b604e0930bdfd368037d6346ab300fa8b73c`.
- B evidence: `ready/1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java`::`onClimbable` 1508-1524, `trapdoorUsableAsLadder` 1526-1535; `net/minecraft/world/entity/Entity.java`::`getBlockPosBelowThatAffectsMyMovement` 792-795, `getBlockJumpFactor` 820-824, `getBlockSpeedFactor` 826-834; `net/minecraft/world/level/block/Block.java`::`stepOn` 347-349, `fallOn` 380-383, `updateEntityAfterFallOn` 384-387, `getFriction` 392-395, `getSpeedFactor` 396-399, `getJumpFactor` 400-403 SHA-256 `9a777d44c9c6b55677132fb5a56d46c9aa322e42faf956749ef80443817416c4`; `net/minecraft/world/level/block/state/BlockBehaviour.java`::`BlockStateBase.getCollisionShape(BlockGetter,BlockPos,CollisionContext)` 657-660, `BlockStateBase.entityInside` 740-742, `Properties.ofLegacyCopy` 1015-1043 SHA-256 `6284a7d08a6d0d475cd4fe8aea62b35153ae8fbc0bce5f51788389f679233b54`; new `Properties.ofFullCopy` 1001-1012 copies `jumpFactor` and other full property fields; `net/minecraft/world/level/block/Blocks.java` movement-property registrations SHA-256 `5392c4349317e55c2989865be38c17b94e6e0543e7017e6a2a64d79217e83643`; `LadderBlock.java`::`getShape` 43-55, `canSurvive` 63-66, `updateShape` 69-79, `getStateForPlacement` 83-106 SHA-256 `e7c9138b8dd1be383cf242d47b07741f8c16899c447cad30e04e9b4ff64a379d`; `VineBlock.java`::`getShape` 91-93, `canSurvive` 101-103, `updateShape` 172-179, `getStateForPlacement` 301-317 SHA-256 `2290ad3bbf16006e89deb946a885780bbbb63c800c8ad32d6b4cc9f541f976e1`; `BedBlock.java`::`fallOn` 136-138, `updateEntityAfterFallOn` 141-147, `bounceUp` 149-155, `updateShape` 158-166, `getShape` 200-212 SHA-256 `145bbdb8f2466971c49b4bbf240d5b41879ba1e170d1b12505e5be5e5d99c490`; `piston/PistonBaseBlock.java`::`getShape` 72-92, `getStateForPlacement` 118-120 SHA-256 `90f35c735e5ad359c2ad2f12bfa85e169b6b8605ea911b3e2777f9088fe6d410`; `piston/PistonHeadBlock.java`::`getShape` 97-99, `updateShape` 130-134, `canSurvive` 137-140 SHA-256 `e5c67e3fd5bf39ebee90e9a52aa2f311517071deae585e2ea48449813981778b`. `artifacts/1.20.4/client.jar` SHA-256 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`; both tag resources have the same hashes and contents as A.
- State producers/writers -> consumers/readers: `LivingEntity.onClimbable` reads feet-state `BlockTags.CLIMBABLE` membership unless spectator; otherwise an open trapdoor is climbable only when the block below is a ladder with matching facing. The vanilla `climbable` tag lists ladder, vine, scaffolding, both weeping-vine states, both twisting-vine states and both cave-vine states on both sides. Ladder/vine shapes, placement/support/update methods, bed bounce/shape/support and piston base/head shape/support methods have matching brace-bounded A/B hashes. Bed landing invokes `bounceUp` unless bounce is suppressed; this negates downward velocity by `0.66F * (1.0 for LivingEntity, 0.8 otherwise)`. Entity reads block jump/speed factors; LivingEntity jump power applies `0.42F * blockJumpFactor + jumpBoost`; ground friction reads the block below that affects movement. The full movement-property registrations have equal A/B values: slime friction `0.8F`; soul-sand speed `0.4F`; honey speed `0.4F`, jump `0.5F`; ice-related friction remains `0.98F`/`0.989F`. B's `ofFullCopy` call sites copy copper blocks/variants; the only registered non-default jump factor in the checked built-in list is honey, directly constructed with `.of()` properties rather than copied. Existing property-copy registrations map to B's `ofLegacyCopy`. B's deprecated base `randomTick` no longer forwards to deprecated `tick`; this block-state update difference remains vanilla and is not treated as a player movement mechanic.
- Brace-bounded signature-plus-body hashes match A/B: Entity `getBlockPosBelowThatAffectsMyMovement` `ceb25f334613dd9c18439e81c69af7f806d9c7d86d5f3f5ee9530a684b5e5b60`, `getBlockJumpFactor` `39cc0591022930859907ebb028bf6d0fbbd01fc57b77c87400f0a07f0725879f`, `getBlockSpeedFactor` `82f474c34708fd0abf6847c7d432916084acf677b8116f93fe7c90f5dff3a19a`; LivingEntity `onClimbable` `8e2acf28b29a688188c556d11e26ea424d5c22f014a99b06cbe3456339ce8784`, `trapdoorUsableAsLadder` `ed28feebc50388d8d8e6bfc0be5053dc14aa71d0ac488ef69cbf77f46d264c65`, `getBlockSpeedFactor` `233c4f223e2885468df6514f515df9132d2110511f6842091c99a78cf655efbb`; Block defaults `stepOn` `37f478b01f3cc9c9fe77ff12d6baa5de0056ed8ca14f0345fb3f82cb3266769c`, `fallOn` `ac60648f262285ec560f76b719a87b4dab6dc1725b781fffbdf144103c82fef9`, `updateEntityAfterFallOn` `a964153f0d01d11403877c960c66b165a2bc301f0ec0fbc4b4d7737d48ada1f6`, `getFriction` `ce5d67d53edacf6bbe7d54caf1202c57e65df072bec2d187fe39292b485145fe`, `getSpeedFactor` `64eb82e66b5341a37432531a8b6fbab349146d8406ada8eb8fb856321c22a6c9`, `getJumpFactor` `6601cac73e2893d9c67a157e0c1f3ae4a27c3e1050b77bc811b4eff8f7f62180`; LadderBlock `getShape` `890c2f33220ac7c2d5d0e0496a3ac1766c9bddae21ad7fa8bb7056c0135af871`, `canSurvive` `7c35820e9cf8ead08293f6e203af8e1f025a8b055898d9cebf49466fdac43ace`, `updateShape` `65ffd1eda5059ca52e630e339a07f4b432f0489dd5fd43ef076ee2efceecbcf2`, `getStateForPlacement` `a2f081a4e3546428df34ce159f5196edddd16f690611ce7954d5f9028ff9170d`; VineBlock `getShape` `81471116588bfeb10c51342e1b63e44efc25ebd5e53d64161c1cad6926381b31`, `canSurvive` `c463524764a6005d41be7f4b88152b254c3d2256c65f09fb0b775e5a7d1a1cf1`, `updateShape` `e8b31742cbebf80f47033269975d480843e9ee9c9efc3e251e8c39bd6349b04e`, `getStateForPlacement` `2f22ec06e6d1f2a192dbd006927aa352a532dd88f187d482c00c470c30202e34`; BedBlock `updateEntityAfterFallOn` `09f8b5c58b29f1d6f784dde97bdf36b8b13a468422b0120551b0ce4621f3e84b`, `bounceUp` `e9c39f79022dde77ff3cbcfd5c30e132a8afef82323922a982f6b0969b144302`, `updateShape` `17c3397e5bae2de2d93b05356d393c16f1289a30577c8c5278789cf6a3dd72cc`, `getShape` `32ac52ec6558f0d911d133b71b1fc6a495085edbcd0f1aef84d0e3fb911e2a2e`; PistonBaseBlock `getShape` `18adf494ea9d336611daac6222592dcad0430315fb12e1bf87e2778269b1c9be`; PistonHeadBlock `getShape` `4f80e223a340b4fdcd66f2d12435bd9d796ca745050371e583c99afb6e5a9017`, `updateShape` `bb275e7614ff773d0494be948d2f6661b539327adac64b914e49dfdcd3bbdd24`, `canSurvive` `db5abeb337356f9380a3e578e830333d7655979486de4c8d8e7630adb1eb9116`.
- Parent slices / dependencies / closure evidence: DEP-01 resolved; S2.1/S2.2 position, shape and state consumers; S3.1/S3.5 friction/jump/climb consumers; S4 collision/support queries; S5.1-S5.4 and S5.6-S5.7 world providers; S7.3 piston movement; DEP-02 remaining client-jar tags and server/datapack-supplied tag overrides; exact collision query and full block registry/state dependencies remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The inspected ladder, vine, bed and piston shape/support/bounce paths, climbable default tag values, and block-factor consumers match A/B for the compared methods/data. B's full-property-copy change does not alter a checked shared-era block's movement factor: old property-copy call sites use the legacy-equivalent path, and the checked non-default jump factor is declared directly on honey. The deprecated random-tick bridge change affects block-state updates and stays vanilla; no movement mechanic is inferred from it. Continue registration/tag/data and collision-provider closure before terminal disposition.
- Finding IDs or checked absence/replacement path: no in-scope difference confirmed in these bounded paths; property-copy consequences for all registrations and server/datapack tag overrides remain open.

### Slice S5.6: water/lava fluid properties, heights and flow vectors

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.7: historical partial-block shape/support inventory

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.1: movement attribute defaults, aggregation, operations and application/removal

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.2: movement status effect registrations, formulas, timers and consumers

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.3: movement enchantment registrations, level formulas, predicates and consumers

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.4: Elytra, equipment slots, active item use and movement components

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.5: resource-backed tags, registry data, server-synchronized movement inputs

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S7.1: incoming player velocity and position corrections

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Incoming server position/rotation correction for the local player; entity motion and teleport packet handlers and their locality guards.
- A evidence: ready/1.20.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java::handleSetEntityMotion(ClientboundSetEntityMotionPacket)V lines 481-487, #handleTeleportEntity(ClientboundTeleportEntityPacket)V lines 499-514 and #handleMovePlayer(ClientboundPlayerPositionPacket)V lines 565-636 SHA-256 0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264; ready/1.20.2/mojmap/net/minecraft/world/entity/Entity.java::lerpMotion(DDD)V SHA-256 d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8d8c5d23b01b.
- B evidence: ready/1.20.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java::handleSetEntityMotion(ClientboundSetEntityMotionPacket)V lines 494-500, #handleTeleportEntity(ClientboundTeleportEntityPacket)V lines 512-527 and #handleMovePlayer(ClientboundPlayerPositionPacket)V lines 597-668 SHA-256 ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f; ready/1.20.4/mojmap/net/minecraft/world/entity/Entity.java::lerpMotion(DDD)V SHA-256 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9.
- State producers/writers -> consumers/readers: ClientboundPlayerPositionPacket relative-axis flags + player position/current velocity -> local Player.setPos/setDeltaMovement and rotation writers -> teleport acknowledgement/move packet; ClientboundSetEntityMotionPacket scaled velocity -> Entity.lerpMotion; non-local ClientboundTeleportEntityPacket -> Entity.lerpTo under !isControlledByLocalInstance. The three ClientPacketListener method body hashes match A/B: motion `d1a6134d43bb5372b488c6bd3b09c61610c123dba73e5ac0485600242b97c36f`, teleport `71f8d97620952d09e831898abca7f72bffc6ecab0c5014b97045f13f52c0560f`, local correction `91fa144eb3579e5020c25f2cea8cb116d948bc277866653217f3e9d80aa6ae27`; Entity.lerpMotion hash `736512db7b13f21ac10c97470ddeb9bbd6282a95655725073ce8b4811e1f8601` matches.
- Parent slices / dependencies / closure evidence: S2.1 position/velocity state, S3 movement consumers, S7.2 knockback/push, S7.4 mounted correction behavior; DEP-01 resolved.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Incoming local-player correction preserves relative-axis velocity components and zeros absolute-axis components before setting position/velocity; rotation follows its own relative flags. Entity motion and teleport handler bodies also match. Teleport packets do not directly move an entity controlled by the local instance. Packet writers on the server and other external movement paths remain open.
- Finding IDs or checked absence/replacement path: none confirmed in the compared correction handlers.

### Slice S7.2: player knockback, push and explosion movement writers

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Player-reachable entity pushes, LivingEntity knockback writer and client explosion impulse application; distinguish entity-collision movement from excluded damage/combat consequences and explosion world effects.
- A evidence: ready/1.20.2/mojmap/net/minecraft/world/entity/Entity.java::push(Entity)V lines 1397-1426 and #push(DDD)V lines 1428-1431 SHA-256 d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8d8c5d23b01b; ready/1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java::knockback(DDD)V lines 1445-1453 and #push(Entity)V lines 1978-1982 SHA-256 5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828; ready/1.20.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java::handleExplosion(ClientboundExplodePacket)V lines 1102-1107 SHA-256 0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264.
- B evidence: ready/1.20.4/mojmap/net/minecraft/world/entity/Entity.java::push(Entity)V lines 1398-1427 and #push(DDD)V lines 1429-1432 SHA-256 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9; ready/1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java::knockback(DDD)V lines 1442-1450 and #push(Entity)V lines 1975-1979 SHA-256 f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d; ready/1.20.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java::handleExplosion(ClientboundExplodePacket)V lines 1132-1149 SHA-256 ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f.
- State producers/writers -> consumers/readers: collision push callbacks -> Entity.push -> player delta movement; LivingEntity.knockback -> player velocity writer (damage/combat cause/state calculations excluded, direct player response remains in scope); ClientboundExplodePacket -> finalizeExplosion -> local-player deltaMovement + packet knockback. Brace-bounded body hashes match A/B for Entity.push(Entity) `f21db61f124b2eddd6b0643d7fa207a979cf3959e4639c54161bdc601d4ea113`, Entity.push(DDD) `85f53d8458e6b485be40dbfb87f4f5e971bb00c5381f0b4e57531e330179eb99`, LivingEntity.knockback `e8d9dc28403b878fce0ba07bf41368ad4ecdf5cb76c7728b4ed0e2c8edffb86f`, and LivingEntity.push(Entity) `96c85874f5236d61d488643f011302441335e79accf4c23c6febbed3c7829401`.
- Parent slices / dependencies / closure evidence: S3.1 velocity consumer, S4 player collision/push callbacks, S5 explosion block/shape effects, S7.1 external velocity handlers, S7.3 piston/launch movement, and INV-EXCLUSIONS direct-player-impulse vs damage/combat-state boundary; DEP-01 resolved.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Player-reachable push and knockback method bodies match and these direct player velocity responses are in scope; only damage/combat state production and cause calculations are excluded. The packet explosion impulse added to local-player velocity is the same expression and order in both versions, after `finalizeExplosion(true)`. B supplies additional block-interaction, particle and sound constructor inputs to Explosion; their world/collision effects remain for the S4/S5 provider audit.
- Finding IDs or checked absence/replacement path: no in-scope direct-impulse difference confirmed; explosion world effects and remaining push producers are open.

### Slice S7.3: piston displacement and launch-item movement paths

- Inventory ID(s): INV-EXTERNAL, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Piston moving-block entity overlap, push-reaction and slime-block branches, per-axis collision displacement, source-piston retraction correction; direct block callbacks that alter entity movement for slime, honey and bubble columns. Damage, sound, particles, achievements, world/block-state production and non-player/vehicle physics are excluded here.
- A evidence: `ready/1.20.2/mojmap/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java`::`moveCollidedEntities` 108-178, `moveEntityByPiston` 180-184, `tick` 275-309, `getCollisionShape` 332-361 SHA-256 `ecbe61885f9d5ecb3229427fd4082e8324b50894da83d65e2d2a4b5f0db2d4d0`; `net/minecraft/world/level/block/piston/PistonMath.java`::`getMovementArea` 7-26 SHA-256 `562a5c309be43c60cfc42e70b23c6775055aad9b277c82b4337c55306eaaef19`; `net/minecraft/world/level/block/SlimeBlock.java`::`fallOn` 18-24, `updateEntityAfterFallOn` 27-33, `bounceUp` 35-41, `stepOn` 44-52 SHA-256 `f5ce06a58cb3e46e6f3c900bdf521e2984765846dc55ca701812194d09d65f54`; `net/minecraft/world/level/block/HoneyBlock.java`::`getCollisionShape` 38-40, `fallOn` 43-52, `entityInside` 55-63, `isSlidingDown` 65-82, `doSlideMovement` 90-100 SHA-256 `6e5a2780f8d17f3e606fdc0404201e6f58cff2df52c5cf44f61333e13d8995ce`; `net/minecraft/world/level/block/BubbleColumnBlock.java`::`entityInside` 42-69 SHA-256 `d50f3502727c7f0d42bbb1216c6f7683f032b88bac46c4ac33d081323272843e`; `net/minecraft/world/entity/Entity.java`::`onAboveBubbleCol` 2282-2293, `onInsideBubbleColumn` 2294-2306 SHA-256 `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8d8c5d23b01b`.
- B evidence: `ready/1.20.4/mojmap/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` same member ranges and SHA-256 `ecbe61885f9d5ecb3229427fd4082e8324b50894da83d65e2d2a4b5f0db2d4d0`; `net/minecraft/world/level/block/piston/PistonMath.java`::`getMovementArea` 7-26 SHA-256 `562a5c309be43c60cfc42e70b23c6775055aad9b277c82b4337c55306eaaef19`; `net/minecraft/world/level/block/SlimeBlock.java`::`fallOn` 26-32, `updateEntityAfterFallOn` 35-41, `bounceUp` 43-49, `stepOn` 52-60 SHA-256 `62c1a543343cce23a47ff35ce51a4512637eda02c775db30bdefbe04d2673d46`; `net/minecraft/world/level/block/HoneyBlock.java`::`getCollisionShape` 45-47, `fallOn` 50-59, `entityInside` 62-70, `isSlidingDown` 72-89, `doSlideMovement` 97-107 SHA-256 `77706436b221a2f0320dedb8852e458388131483e5e69669869a523a5703fbca`; `net/minecraft/world/level/block/BubbleColumnBlock.java`::`entityInside` 49-76 SHA-256 `f2a1d54d5590a0546c1ea7640e012086c922c1682c68a82b0e20a9d7bcae19af`; `net/minecraft/world/entity/Entity.java`::`onAboveBubbleCol` 2283-2294, `onInsideBubbleColumn` 2295-2307 SHA-256 `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`.
- State producers/writers -> consumers/readers: `PistonMovingBlockEntity.tick` calls collision movement during partial extension/retraction; overlapping entities with non-IGNORE push reaction enter displacement. `PistonMath.getMovementArea` expands collision bounds; piston movement calls `Entity.move(MoverType.PISTON, ...)`, with a source-piston retraction correction where guarded. For moved slime blocks, the axis impulse writes delta movement for entities other than `ServerPlayer`; a local client player is not a `ServerPlayer`, provided its client path reaches this callback. Slime landing writes upward velocity (1.0 multiplier for LivingEntity, 0.8 otherwise) unless bounce is suppressed; `stepOn` applies horizontal factor `0.4 + abs(dy)*0.2` only below `abs(dy) < 0.1` and when not stepping carefully. Honey sliding requires airborne position below the block top, downward velocity below -0.08 and sufficient horizontal edge displacement; it clamps downward velocity to -0.05 (scaling horizontal components when dy < -0.13) and resets fall distance. Bubble-column `entityInside` selects the above/inside callback based on the block above; Entity callbacks adjust vertical velocity and the inside callback resets fall distance.
- Brace-bounded signature-plus-body hashes match A/B for PistonMovingBlockEntity `moveCollidedEntities` `02f9019a617eb4fb44a0bd37654b4d7c7261fa605e7a4162025814d011d038b4`, `moveEntityByPiston` `b2fdbe58b202b4a54bbecd8958b72e8f978e8d6a29987d56683c42cca112f6da`, `tick` `e15025943af479896ef337f35d7daa7655ba08f66d6236c0503c1426914b8bc5`, `getCollisionShape` `dcc749a88a6e40f08e6f3e443d6925a1c7c175170b528037423b4f3066cff9bc`; PistonMath `getMovementArea` `c1821337109c7bf2b550dcab68dbaa226f3e2fa7d1a3cdb4b13c742055af37fa`; SlimeBlock `fallOn` `8ffe983564b807ad1de35d52bac1d3e1117961f2e1e352be9beb25f9cf45eb3e`, `updateEntityAfterFallOn` `09f8b5c58b29f1d6f784dde97bdf36b8b13a468422b0120551b0ce4621f3e84b`, `bounceUp` `4f5de734cdb7fb5644febee9b64f02fc6283576781241078e2e4e09304fdae44`, `stepOn` `8f5f3494ca849b0cfc2ed5938ab7fbf4f2d7ad1ee870900715fa886e796acda3`; HoneyBlock `getCollisionShape` `6271868e67145489853621b56ce2ccbdfb875e8f25303f27f0304f7b8793e054`, `fallOn` `f39c2a8b5a14146b6121b40a092c243f0d3fb99961b039e15492bc5bf2b674fc`, `entityInside` `a6ed70220d53b4410eb126287fa4cdbbdcd5bbc52c0b5a645895c0a73a8dd37e`, `isSlidingDown` `52313c5bc52ccbd99c9a04ada22ed1c7a6bce1dfbe3fa43b78861a225b56ea81`, `doSlideMovement` `898d775e794731d0b52ec0c9f8660a5188f9281436e9c48c9b670c6a8aa10ff5`; BubbleColumnBlock `entityInside` `0b39c5f96ada11b777802f9f22b975ed8efb480443dd010837531ee9d943c77a`; Entity `onAboveBubbleCol` `035640b450b7c74af3f0d939df84f1988b8a454bc927cfe0a6a03709e084b842` and `onInsideBubbleColumn` `126f386dbf1d583bceea83c5784f2e4ccc796e91411f39e6eff4dd0839e20be5`.
- Parent slices / dependencies / closure evidence: DEP-01 resolved; S4/S5 collision and shape consequences, S5.5 neighboring-state/block-update and registration inputs, S2 state readers, and INV-EXTERNAL cross-stage writer/caller closure remain open. Resolve movement tags/registry/default inputs under DEP-02 when corresponding inventories are established.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Compared method signatures and bodies match on A/B for the inspected piston, slime, honey, bubble and Entity callback paths. These blocks can write direct player velocity or position through shared Entity movement methods; they remain in scope. The server-only `ServerPlayer` exception to piston slime-axis impulse does not exclude a local-player client instance. This evidence closes only these bounded writer paths; it does not close collision queries, piston movement-state/tag providers, block update ordering or all launch/neighboring-state items.
- Finding IDs or checked absence/replacement path: no in-scope A/B difference confirmed in these bounded writers; no pair-wide equivalence claim.

### Slice S7.4: mount, rider and dismount transitions affecting player movement

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Clientbound passenger attachment/detachment consumer, force-start rider transition, local-player mount/dismount overrides, passenger tick/position writer, and shift-key dismount input path. Vehicle movement simulation, independent vehicle physics, and other riders' motion remain excluded; only local-player state writes and its direct rider-placement callback are included.
- A evidence: `ready/1.20.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`::`handleSetEntityPassengersPacket` 923-950 SHA-256 `0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264`; `net/minecraft/client/player/LocalPlayer.java`::`tick` 188-197, `startRiding` 158-169, `removeVehicle` 172-175, `rideTick` 846-853 SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`; `net/minecraft/world/entity/Entity.java`::`rideTick` 1827-1833, `positionRider(Entity)` 1835-1839, `positionRider(Entity,MoveFunction)` 1841-1844, `startRiding(Entity,boolean)` 1873-1903, `removeVehicle` 1915-1921, `stopRiding` 1923-1925, `addPassenger` 1927-1946, `removePassenger` 1948-1961 SHA-256 `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8d8c5d23b01b`; `net/minecraft/world/entity/LivingEntity.java`::`stopRiding` 2730-2736 and `rideTick` 2739-2744 SHA-256 `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828`; `net/minecraft/world/entity/player/Player.java`::`wantsToStopRiding` 306-308 and `rideTick` 482-495 SHA-256 `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c`; `net/minecraft/server/network/ServerGamePacketListenerImpl.java`::`handlePlayerInput` 351-354 SHA-256 `8451076ce3a5a4c10e1983d5c576d01ca8bae5e3541211ea9c517e8a73a63644`.
- B evidence: `ready/1.20.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`::`handleSetEntityPassengersPacket` 953-980 SHA-256 `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f`; `net/minecraft/client/player/LocalPlayer.java` same method ranges and SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`; `net/minecraft/world/entity/Entity.java`::`rideTick` 1828-1834, `positionRider(Entity)` 1836-1840, `positionRider(Entity,MoveFunction)` 1842-1845, `startRiding(Entity,boolean)` 1874-1904, `removeVehicle` 1916-1922, `stopRiding` 1924-1926, `addPassenger` 1928-1947, `removePassenger` 1949-1962 SHA-256 `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`; `net/minecraft/world/entity/LivingEntity.java`::`stopRiding` 2732-2738 and `rideTick` 2741-2746 SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; `net/minecraft/world/entity/player/Player.java`::`wantsToStopRiding` 306-308 and `rideTick` 487-496 SHA-256 `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`; `net/minecraft/server/network/ServerGamePacketListenerImpl.java`::`handlePlayerInput` 351-354 SHA-256 `7e2026bc5dc571031a52fa6be38a71cee20e7ccfbb2e77aec48ea4bc7af11527`.
- State producers/writers -> consumers/readers: LocalPlayer sends `ServerboundPlayerInputPacket` with `shiftKeyDown` while passenger; the server packet listener writes it through `setPlayerInput`, Player's identical `wantsToStopRiding` reads that state, and server-side Player.rideTick stops riding only when not client-side and still passenger, then clears shift. The client consumer receives `ClientboundSetPassengersPacket`, snapshots whether the local player was already an indirect passenger, ejects the current passenger list, then force-starts each passenger; on a newly mounted boat it also aligns local-player body/head yaw to boat yaw. Entity.startRiding sets standing pose, vehicle reference and vehicle passenger list. Passenger Entity.rideTick zeroes passenger delta movement, ticks the passenger, and invokes vehicle.positionRider; the shared default rider writer calls `setPos` with the vehicle's passenger position plus rider offset. LocalPlayer.removeVehicle also clears `handsBusy`. Player.rideTick in A snapshots position and calls `checkRidingStatistics`; B removes only that post-ride statistics callback. This is the previously scoped-out statistic/food-exhaustion callback in S-EXCL-01 and has no player movement-state write.
- Additional player-rider callback evidence: A `ready/1.20.2/mojmap/net/minecraft/world/entity/vehicle/Boat.java` SHA-256 `8d01cdf2502e17ffd03f85c4add4b87316baaeea609f40a3d6f66d0a3c110686`, `positionRider(Entity,MoveFunction)` 700-709 calls the shared position writer then always applies boat `deltaRotation` to rider yaw/head yaw and clamps it; B `ready/1.20.4/mojmap/net/minecraft/world/entity/vehicle/Boat.java` SHA-256 `f0296d2198d1d0d1f65162126197249789c5d22f081841af454af3a074024109`, 665-676 adds a `!EntityTypeTags.CAN_TURN_IN_BOATS` guard around those rotation writes. Brace-bounded method hashes are A `0bc9c83192a16e8424d68c2f209182e9733ecd97223ba491d2c958ed8362a15d`, B `9c1761bd74fe38c0e76ad5f93ee702b7a100080179a1adea19233e82c7b89976`. The shared positional writer remains first and unchanged. A/B entity-source inventory found 52 methods across `positionRider`, `getPassengerAttachmentPoint` and `getPassengerRidingPosition` on each side; body hashes match for 51, with only this Boat rotation guard differing. `Boat.getPassengerAttachmentPoint` is identical A/B, as are the inspected position/attachment overrides for Pig, Camel, AbstractHorse, Llama, SkeletonHorse, Strider and AbstractMinecart (the per-method hashes follow below). B declares the tag at `ready/1.20.4/mojmap/net/minecraft/tags/EntityTypeTags.java` line 27, file SHA-256 `3805f34f55ae616145b2c10b4f6e6c78223096248f4a45779f698cc9f8482d97`; the exact source roots do not expose the default tag membership, so whether a Player rider passes the new guard remains unresolved under this source-only evidence boundary.
- Paired brace-bounded callback hashes from the exact source files (each file hash checked against its side's source manifest): `AbstractHorse.positionRider` 1.20.2 980-984 / 1.20.4 977-981 = `93c14e35cf49d8258c03c9327455120e08551eff855bc1d81367936996fbb324`; attachment methods are `AbstractMinecart.getPassengerAttachmentPoint` 154-156 / 146-148 = `a7d5a0515822cfa6ae26a4969ac23ad0223d85a25dcc4155689e2d442966d22c`, `AbstractHorse.getPassengerAttachmentPoint` 1143-1144 / 1140-1141 = `6f37f4d8e85a3408d7b09c5479ae41ae01c38d3c4f1c321c6284c5eae1c009e5`, `Camel.getPassengerAttachmentPoint` 462-477 both = `fa7783f2da8bf9c937fb0a4f5273fd54ebef67296ff130a69b94c1fca1d24be2`, `Llama.getPassengerAttachmentPoint` 487-488 both = `706de5d7cec4c1209edda5d309b844d740d5cbfc70b888ea23189944f25a0063`, `Pig.getPassengerAttachmentPoint` 275-276 both = `8e9762d182200969899e51e11601672036cb16e920732df79a1318ece77917e7`, `SkeletonHorse.getPassengerAttachmentPoint` 106-107 / 116-117 = `5938eaa8e7d3c4634ed13248c5440da303817f2e235b869221c50f716c6b52c2`, `Strider.getPassengerAttachmentPoint` 192-196 both = `7c5410d08dcea91ffa82dd5802fc635178f25afef3e86ea738d9f7c224a0fa06`, and `Boat.getPassengerAttachmentPoint` 153-168 / 148-163 = `ba17cd3bc58ef4f4fefe01b2e15a073d21cb060394c4f3dc973a308c64d71d59`. These exact overrides have no A/B body delta; the Boat rotational guard remains the only difference in this bounded callback inventory.
- Direct dismount placement follows `LivingEntity.stopRiding` (same A/B body hash above) to `dismountVehicle` (A 1984-1995 / B 1981-1992, same body hash `97a1f8c17a786db75fead46ff2fe0e92da500c8d63744d30745f4903d3c18514`). When the vehicle remains present and is not in a portal, that helper requests `getDismountLocationForPassenger`; otherwise it uses the rider's current x/z and the higher y. It sends the selected coordinates to `Entity.dismountTo` (A 2676-2677 / B 2678-2679, same body hash `bde697e19cd5e6578502c3dd89a275548bc174c7f9bc1f82a0844fd795614ab1`), which delegates to `teleportTo` (A 2680-2684 / B 2682-2686, same body hash `751ea5a4de7e03a9c8bffb7ba1de2847308c1f0547a468c65dedb995a3b946d4`); that final writer only moves the entity when its level is a ServerLevel. The paired dismount-position override inventory is body-identical: `Entity` 2955-2956 / 2961-2962 = `e4e9484844f2ee49ad939a346f6d2beb55db61f08d49128bab2ff1b7e5ee0fb1`, `Pig` 182-208 both = `872dc3345e85c44a54b5b4e3d38e403aa750283b60dc7371932e6935a3d4e1f2`, `AbstractHorse` 1102-1115 / 1099-1112 = `d97ec91505d522226f30d8745413277c483c4c1efa6b1c108054402b99491b47`, `Boat` 713-741 / 680-708 = `417131cf3c6c42a46a64bc02d83955ee67a2e8beade4d3614ab288d32c5a9bfc`, `AbstractMinecart` 160-207 / 152-199 = `8b9e6292ff5f37ddd1899a337c1ca1986d06a0fd4e64b9adb35b222d037a971e`, and `Strider` 213-252 both = `7c54aa5fc6fc739e70fe7c9d940693b8ae63bd49195d1094a61890d6c497d62d`. Dismount candidate positions therefore show no A/B source delta in these inspected paths.
- Brace-bounded signature-plus-body hashes match A/B for Entity `rideTick` `710a3f9829d5af247362d0fb87a1b87556ac1f78651ff0a73f3a6a833ca7d14e`, `positionRider(Entity)` `d0fefb458ef0202819d488ecd2d2042a40f0e8433372c8910a78576b85ee27e3`, `positionRider(Entity,MoveFunction)` `7666eef4ef88ab2fa79a42747152e221c4d3a0e708881571938ee6273585a87f`, `startRiding(Entity)` `67c9cbdd644da4c5f0ff4d83a84a7a5410f19e1f6d4894c109de4c5eec44258e`, `startRiding(Entity,boolean)` `68798ff770ab5ed9469f44c6381aa517ca144f494d7eedf486e6a7c881057913`, `removeVehicle` `3d58d69b2a20fd69bb153742edd0a7cdc1493bc4c348839fcf1252873fecc13b`, `stopRiding` `c8255697fa3d261a4e1e3309cff4767ec2cf233d684ac58acdd8846108e995ca`, `addPassenger` `2494485e3c003534902e487e204c4c97cd6f09e8ddf5f929c0f16d732bdba4ee`, `removePassenger` `ac443a7e48ed778bab4d2a1f7a03f289868c49a5da6d18ef62e3b1ccc9547cd8`, `getPassengerRidingPosition` `0dfb4296f8571de4d71f2e6178f13b191c7376a79909f81ac40fb9645d3cbff2`, `getPassengerAttachmentPoint` `e0b6e0adec0a2bc4b8e4d91fb31a886c0b4fd1c20e3cb6c50a2c9520b6ab3fa5`; LivingEntity `stopRiding` `c48b9ead7aa7f60b304e3817b39844f96f69fa27ceac442adca54348a590c157`, `rideTick` `0a93dcb6f5fd375b48f2f799838b1d402fcf3fc46487d6275e79fa073b28c2ad`; LocalPlayer `startRiding` `a24a239705390e7818f211df4f6caec1e057ae0f03f18edd1eac423205b60fe6`, `removeVehicle` `c83a6340747660d416ca006cfd232d42275e4de5623642c8fc46e5c7fd3a09d3`, `rideTick` `973a3eab79d11c406ceea4df218c3f99038b5c0ad7854d82865fefeba6d1a2ee`; ClientPacketListener `handleSetEntityPassengersPacket` `3db92e21b347f1b7dd368031ffebc72755cb94757591a41adbc172d68e3ecf5a`; ServerGamePacketListenerImpl `handlePlayerInput` `f9573d7c5caf67e592628dbcc45262d67bab985a1ec2f9498afb8bac57aeb344`; Player `wantsToStopRiding` `083dd388416aa9b8aa75a026d1d9a8a605590de40389261456a0740982e408a8`. Player.rideTick differs only by the `checkRidingStatistics` sequence, dispositioned under S-EXCL-01.
- Parent slices / dependencies / closure evidence: S1.1/S1.2 input/tick packet path; S2.1 pose/position/velocity state; S3.1 passenger travel and S-EXCL-01; S7.1 client/server corrections; S7.3 external piston movement; DEP-02 for default membership of `minecraft:can_turn_in_boats`; remaining vehicle-specific player attachment/position callbacks, client-controlled vehicle update and passenger order paths remain open. Non-player and vehicle physics remain out of scope.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The shared mount state transition, client passenger consumer, local-player mount/dismount wrappers, default rider position writer and shift-input stop predicate match A/B for inspected bodies. Boat mount yaw alignment also matches. A/B's Player.rideTick difference is limited to the excluded post-movement statistic/exhaustion chain; it does not write player movement state. Boat's override adds a type-tag guard around rider rotations after the shared positional write; the direct player position remains unchanged, while Player membership in the tag is unresolved because its default data is not present in the allowed source trees. Keep this callback as an open data dependency; do not yet claim either player impact or absence. Direct rider placement overrides and remaining callers are also open.
- Finding IDs or checked absence/replacement path: candidate `F-S7.4-BOAT-RIDER-ROTATION`; confirmation awaits `CAN_TURN_IN_BOATS` default-membership evidence under DEP-02.

### Slice S7.5: cross-stage writer/caller/callback/dependency closure

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; validated exact source trees are available.
- A evidence: pending exact 1.20.2 owner/member/body ranges and SHA-256 comparison.
- B evidence: pending exact 1.20.4 owner/member/body ranges and SHA-256 comparison.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

## Dependency queue and blockers

- DEP-01; source provenance for exact 1.20.2 and 1.20.4 Mojmap roots; resolved. Both readiness JSON records, exact IDs/namespace, source and artifact manifest hashes, movement diagnostics and relevant source/artifact file hashes have been verified. This is the source prerequisite only, not movement coverage.
- DEP-02; originating slices S5.1-S6.5 and S7.4; movement tags/defaults/registry values referenced by inspected source must be resolved where the permitted evidence inputs provide them. S7.4 specifically needs exact 1.20.4 default membership for `minecraft:can_turn_in_boats` to determine whether the Boat rider-rotation guard changes Player state. The current source-only roots contain the tag declaration but not its default membership; do not substitute a non-source input under this assignment. Owner: source worker.
- DEP-03; originating slice S1.1; outer tick/caller order, `ClientPacketListener` tick, and the level-load manager/screen path are closed at source level. Both versions tick the level while the non-pausing receiving screen is open; keybind processing is gated by `screen == null`, but player entity ticking continues. In 1.20.4, the new tick-rate manager skips frozen non-player entities while exempting players and vehicles carrying player passengers. This is a modern world tick-rate feature, not a direct player movement delta; non-player movement remains excluded. Evidence is recorded under S1.1; owner: source worker.
- DEP-04; originating slices S1.2/S1.3/S6.3/S6.4; resolved for direct local-input scaling: `isMovingSlowly`, crouch scaling, Swift Sneak slot registration/helper traversal, active-use flag writer and entity-data consumer are identical on A/B. Broader movement modifier, attribute, enchantment and equipment coverage remains open under S6.3/S6.4; owner: source worker.
- Open dependencies: DEP-02 and the method/data dependencies listed on open coverage slices.

## Finding index

- Candidate snapshot `F-S7.4-BOAT-RIDER-ROTATION` was committed as `ec6ed582016a93f4a53ef5d2febd1db7f89d3ceb`; file SHA-256 `288ee68b6764075f11e09bb6dc0f4c2b2d49b683b90699374eff5a8f2a1a73ed`. It is recorded for independent review and is not yet a confirmed finding because Player membership in `minecraft:can_turn_in_boats` is unresolved. No findings confirmed yet; zero confirmed findings does not imply equivalence.
- Discarded candidates: none yet.

## Resume checkpoint

- Last completed slices: source-pair readiness/hashes validated; initial bounded comparisons of local input and core travel/jump/movement methods completed; the outer client tick and level-load-screen path are source-closed. S2.1/S2.2, S3.1, S5.5, S7.1/S7.2/S7.3/S7.4 remain in progress. S7.4 has a 52-method A/B inventory for `positionRider`, `getPassengerAttachmentPoint` and `getPassengerRidingPosition` (one body delta: Boat rotation guard), plus 13 dismount helper/callback methods compared with no body deltas. Candidate `F-S7.4-BOAT-RIDER-ROTATION` is preserved at commit `ec6ed582016a93f4a53ef5d2febd1db7f89d3ceb`, file SHA-256 `288ee68b6764075f11e09bb6dc0f4c2b2d49b683b90699374eff5a8f2a1a73ed`; independent review is pending.
- Next bounded slice and exact files/members/body ranges to open: resolve only the player membership precondition for `F-S7.4-BOAT-RIDER-ROTATION`: inspect an allowed exact 1.20.4 source input for default `minecraft:can_turn_in_boats` membership and determine whether `minecraft:player` is present. Existing B declaration evidence is `ready/1.20.4/mojmap/net/minecraft/tags/EntityTypeTags.java` line 27, SHA-256 `3805f34f55ae616145b2c10b4f6e6c78223096248f4a45779f698cc9f8482d97`; B callback is `ready/1.20.4/mojmap/net/minecraft/world/entity/vehicle/Boat.java`::`positionRider` 665-676, file SHA-256 `f0296d2198d1d0d1f65162126197249789c5d22f081841af454af3a074024109`, method SHA-256 `9c1761bd74fe38c0e76ad5f93ee702b7a100080179a1adea19233e82c7b89976`; A callback is `ready/1.20.2/mojmap/net/minecraft/world/entity/vehicle/Boat.java`::`positionRider` 700-709, file SHA-256 `8d01cdf2502e17ffd03f85c4add4b87316baaeea609f40a3d6f66d0a3c110686`, method SHA-256 `0bc9c83192a16e8424d68c2f209182e9733ecd97223ba491d2c958ed8362a15d`. The current permitted Java source roots do not include default tag values, so DEP-02 stays open unless an allowed exact source input supplies them. After this dependency is dispositioned, continue remaining S5.5, S2 and campaign inventory work; do not treat this pair as complete.
- Outstanding dependencies and owners: DEP-02; source worker. Independent review of `F-S7.4-BOAT-RIDER-ROTATION` is pending; reviewer not yet assigned.
- Current assumptions requiring verification: Player's default membership in `minecraft:can_turn_in_boats` is unknown from supplied Java sources; if Player is absent, Boat rider yaw/head-yaw rotation remains reachable, while if present, B skips those writes. Other prior assumptions remain: tick-rate freezing is a modern-only world-control path and does not freeze the local player; `Player.travel`/ride callback removals remain limited to excluded statistic/food state; piston slime impulse branch reachability for local-player client tick remains to be reconciled with lifecycle/collision caller coverage.

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

- Coverage counts by status: pending 30; in-progress 15; compared-no-difference 0; findings 0; not-applicable 1; blocked 0.
- Required inventory status and evidence: INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT, INV-MODIFIERS, INV-EXTERNAL, and INV-EXCLUSIONS all pending; readiness is established but their inventories are not yet closed.
- Open dependencies: DEP-02 and the open slice dependencies listed above
- Unresolved gaps and limits: exact source publication is validated; most source inventory remains open and no whole-run equivalence claim has been made.
- Evidence/hash/correspondence audit: core input/travel source hashes and manifests checked; remaining cited providers/resources will be hashed at inspection.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).

This source-only report is not frozen. Keep implementation and runtime statuses separate. Runtime validation is not authorized in this task.
