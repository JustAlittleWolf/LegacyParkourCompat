# Discovery: 1.16.5 to 1.17.1

- Status: active
- Scope: direct client-player movement; older A = 1.16.5; newer B = 1.17.1.
- Repository revision and start date: 002137b227676caea77f6832b9f4c8d0b6200bff; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: expected official Mojang names (mojmap) on both sides; verify from exact readiness JSON before acceptance.
- Source preparation owner / command / log / readiness marker: serialized source-owner run; exact endpoint readiness records and success excerpts verified in the artifact manifest; full Gradle stdout was not persisted.
- Toolchain/decompiler/remapper versions and options: Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0, ASM 9.10.1, Mapping IO 0.9.1, Gson 2.14.0, Tiny Remapper 0.14.1; command options recorded below.
- Discovery author(s): source-only worker for this exact pair.
- Independent reviewer: pending assignment; must differ from discovery authors.
- Scope declaration: no implementation, wiki, or release-note mechanics evidence. No runtime implementation or validation.
- Exclusions: health/food state production, regeneration, saturation, exhaustion, attack/damage resolution, non-player movement, and vehicle physics. Predicates may read vanilla values without emulating their producer systems. Direct player velocity, impulse, and knockback application remains in scope when reachable, even when combat can trigger it; the combat cause and damage calculation remain excluded. Modern-only blocks/features receive no invented A-era behavior.

## Artifact manifest

Both endpoints passed the owner's exact-ID Mojmap readiness protocol. I independently checked marker status/version/mapping/versionMetadataId, success excerpts, source/artifact manifest SHA-256 values, diagnostics SHA-256 values, source-file counts, and eleven foundational source files on each side against the listed source manifests. The output roots exist. No source trees or artifact manifests were modified.

Source paths below are relative to this manifest: canonical artifact root is ../../../../build/movement-campaign-2026-10-07/; readiness/artifact files are under ready/<version>/ and caches are under artifacts/.

### A — 1.16.5

- Requested/resolved/version metadata ID: 1.16.5 / 1.16.5 / 1.16.5; readiness status ready; namespace and CLI mode mojmap.
- Source root: ../../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/; 3,523 source files, 19,260,356 bytes.
- Readiness marker SHA-256 e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee; provenance JSON SHA-256 b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d.
- Source manifest mojmap.sources.sha256 SHA-256 9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b; artifact manifest artifacts.sha256 SHA-256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c; 38 artifact entries.
- Original client jar SHA-256 00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263; version.json SHA-256 3eeeab7b3165cc5263dc26ff8fe114fdb718b75a0244efead9d19635d090ba72.
- Official Mojang client mappings for metadata 1.16.5: client_mappings.txt SHA-256 7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c; publisher SHA-1 374c6b789574afbdc901371207155661e0509e17; URL https://piston-data.mojang.com/v1/objects/374c6b789574afbdc901371207155661e0509e17/client.txt. Remapped client-mojmap.jar SHA-256 7c1a9f30983fa3c777d00116bdf3f3cef97c1c1aaf8294027052f5337fcde940.
- Success excerpt SHA-256 0974f4cdada4d0549cb730901064681d11a0f2cfbf1fc61783993b9dc4488b07; it confirms exact requested ID, mojmap output and BUILD SUCCESSFUL; full Gradle stdout was not persisted by owner.
- Movement diagnostics SHA-256 676ea98fca6e2b73e0db8214057397af8112b0b9d37b72ad7d9b1025bd77ec50; owner reviewed 21 anchors. It lists the relevant entry methods, not full-scope body closure; each slice is independently checked below.

### B — 1.17.1

- Requested/resolved/version metadata ID: 1.17.1 / 1.17.1 / 1.17.1; readiness status ready; namespace and CLI mode mojmap.
- Source root: ../../../../build/movement-campaign-2026-10-07/ready/1.17.1/mojmap/; 4,142 source files, 21,457,224 bytes.
- Readiness marker SHA-256 c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b; provenance JSON SHA-256 de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333.
- Source manifest mojmap.sources.sha256 SHA-256 93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b; artifact manifest artifacts.sha256 SHA-256 e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa; 41 artifact entries.
- Original client jar SHA-256 a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d; version.json SHA-256 b6125f5a4410c3a71c1cf177dbdbf8c9e409bde8e435fb094bedd5ad6ce07902.
- Official Mojang client mappings for metadata 1.17.1: client_mappings.txt SHA-256 2b28ded68f8602aaf2f35ef92dd10ee41ba2cf9723e29570155d60e1723848e9; publisher SHA-1 e4d540e0cba05a6097e885dffdf363e621f87d3f; URL https://piston-data.mojang.com/v1/objects/e4d540e0cba05a6097e885dffdf363e621f87d3f/client.txt. Remapped client-mojmap.jar SHA-256 2a2be036174902e447865498741b8c59fa2e090d352d786a8507dccb7c23008c.
- Success excerpt SHA-256 3f00b445492efcb2a1c748c722a161719945d0a3646d403552f44ae3c16a516e; it confirms exact requested ID, mojmap output and BUILD SUCCESSFUL; full Gradle stdout was not persisted by owner.
- Movement diagnostics SHA-256 e98f7769de9ed18eb77f5967a20643d5b67c48f0fb3914437b8bf3c8565d5dbe; owner reviewed 21 anchors. It lists the relevant entry methods, not full-scope body closure; each slice is independently checked below.

The `feather-r1-2026-10-07` derived-artifact revision applies to six early Feather runs (1.8.9–1.13.2), not this exact Mojmap pair. A/B readiness, source manifests, source trees, Mojang mapping inputs and mapped-JAR hashes above were verified against the pair's original published records; no revised Feather snapshot is used as evidence here.

### Shared preparation details

- Owner command options: gradlew.bat decompileMinecraft --versions=1.15.2,1.16.5,1.17.1,1.18.2 --mappings=mojmap --decompiler-heap=4G --output-root=<campaign staging root> --cache-directory=<campaign artifacts root>. One serialized source-owner run produced both exact endpoints and neighboring requested sources.
- Toolchain: Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0, ASM 9.10.1, Mapping IO 0.9.1, Gson 2.14.0, Tiny Remapper 0.14.1.
- Raw success stdout was not persisted; only owner-generated success excerpts are available. The ready markers, manifests and diagnostics were hash-verified.

### Foundational source hashes

SHA-256; each value was recomputed from the published tree and matched its version source manifest. These files support initial correspondence only; additional source/resource hashes are added as slices are closed.

| Role and relative path | A 1.16.5 | B 1.17.1 |
| --- | --- | --- |
| Local player — net/minecraft/client/player/LocalPlayer.java | 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b | c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812 |
| Keyboard input — net/minecraft/client/player/KeyboardInput.java | 746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7 | ea41065c909e53f1a2cc29ecdb6a9a8f9265d2801cd8b95b996e182c318ecd69 |
| Input state — net/minecraft/client/player/Input.java | 367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201 | 367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201 |
| Player — net/minecraft/world/entity/player/Player.java | d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960 | 724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481 |
| Living entity — net/minecraft/world/entity/LivingEntity.java | b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 | 33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f |
| Entity — net/minecraft/world/entity/Entity.java | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 | ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de |
| Block — net/minecraft/world/level/block/Block.java | 2838aa4fb4052b3bd78d77b3d232a6321d03bd2dfd52275ffacfe6915a89e5b6 | 01fa40798c7a4af538c29601a6ad52c82f3c6364f74d00e955a0a41a0a0d88b4 |
| Block registry — net/minecraft/world/level/block/Blocks.java | 3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12 | 87d72a113a3f8937a6a585ef917a4fd29cc5b335c00a858f6800e38f3c1bf7a8 |
| Fluids — net/minecraft/world/level/material/FlowingFluid.java | a85a5cd6625b1b4a047bd693399e1f78c2978ae207af927d4abfac3b34229552 | 93fac0aa0b44dcd42a9e05783eede16e6e1e72cbc7cbf00d34f6e5a55b67f4c6 |
| Box — net/minecraft/world/phys/AABB.java | 514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878 | dd143ccd01d0d05e50619910bceb98a0cec0ca526e161e9812ae1e0db6eb7b7e |
| Voxel shape — net/minecraft/world/phys/shapes/VoxelShape.java | 6747647a5b94e69747340d432932b77ce5ad701c8f06e141e15b3dd68f8c3bde | 6b53eb54933afacda1934c9f1e3ec3584294aa3dec09e5fbf4eeaa52b82d76c4 |
| Input options — net/minecraft/client/Options.java | 431ed10af0ee5d083a007a18208c6b117541c4cd4e4d37ad7ddd1a591de61190 | 30b91d23563e5b6ffd903e1f6e0dccc39352eb13dbe5e1aaa6d3a0e36118706e |
| Key state — net/minecraft/client/KeyMapping.java | d07149699a69aec6a6d010ad3a8f4cc1aa6865bf5faa2f6a1f07d3055a08df6f | 7f8f506bebfb8e25e41c81c1e59841a7f0e282aaa194767d760870dec49f9166 |
| Toggle key mapping — net/minecraft/client/ToggleKeyMapping.java | 62a498fd0aa92f301b97dda87c8f58f1940afcdd3a45b2177b64577485127508 | 62a498fd0aa92f301b97dda87c8f58f1940afcdd3a45b2177b64577485127508 |
| Input construction — net/minecraft/client/multiplayer/ClientPacketListener.java | a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e | a59ba067bb0b9cdf026159a87534fd456ed766ec287cfd23e24356f84f078a5a |
| Client tick iteration — net/minecraft/client/multiplayer/ClientLevel.java | 96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93 | fd2b3ebade86250cf1ab05f6086dd48f11b6bcd3a01e67c8a03f5c34f553fbc2 |
| Entity tick error wrapper — net/minecraft/world/level/Level.java | e9a23488863fd43a7d8975efc23f4c7cf4af874554bab5b76eaa2c79f028db80 | 730d3cc1f1c1c7054c102bb32d635d8f5a3657fff8073df863f0848ed1f9a0f8 |
| Chunk query — net/minecraft/world/level/LevelReader.java | d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a | 4d041cdbc702d532c5b9da800c34bd1c23f8d3b554ecbdd89decbb765a6b451a |
| Block coordinates — net/minecraft/core/BlockPos.java | 23f670f8b3ebdb4a7bbf810dfa637f1217795ce445bef614c35833e02b62717e | 1ce157feb785f1c536d5aeae5fc362885d2b5e1095ce419cf7afb0c945671f9f |
| Integer coordinate construction — net/minecraft/core/Vec3i.java | 2c04952e6a5cecbc641e69ea6376af91a13ebc4d8d6f427fc5392b50a5619fc2 | 500d65e0156a633edbedfa74263b43a858012b8af1afbadf7c3b7ed7dc464649 |
| Section coordinates — net/minecraft/core/SectionPos.java | 47145252c75695a6fee19388024f041e9c56d3c1fb449d79c4e00e348a2419ea | d17e6d4ff5e7573dde74fddaeb2733a39ecac9c9ef4621a10bf868cc0513f52a |
| Client game tick — net/minecraft/client/Minecraft.java | 3fc48eb93d2dc001496c11b66071db939cdaa3f1ff7c4e5b2b391e25a715facc | d474768e60049d657cfe4bf34f6a7c2690f82afbf692bdea41c465fa560e7b72 |
## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending.
- Evidence inventory and finding IDs included at freeze: none yet.
- Confirmation that old mod implementation/code and wiki-audit results were not opened before freeze (prior discovery reports may be used as navigation): yes.
- Source/mapping hashes covered by freeze: pending.

## Correspondence and call order

No exact correspondence is accepted before source verification. Resolve class, inheritance, member signatures, callers, state read/write edges, call order, and rename/split/replacement evidence on both sides. Cover the complete reachable player tick through pre-travel, travel branches and post-travel. Also resolve pose/dimension writers, shape providers/registrations/neighbors, fluid/block inputs, modifiers, and external player movement writers.

## Required source inventories

Each inventory maps to bounded slices; no broad stage-level checked claims. Evidence remains pending.

- `INV-TICK` full input/player tick/pre-travel/travel/post-travel chain: status=pending; slice_ids=S1-01..S1-06,S3-01..S3-09; evidence=pending
- `INV-STATE` direct player movement state writers/readers (pose, dimensions, position, velocity, box, flags, support, timers): status=pending; slice_ids=S2-01..S2-03,S3-03..S3-09,S4-01,S4-04..S4-05,S7-01,S7-03; evidence=pending
- `INV-COLLISION` player queries, shape providers, registrations, callbacks, and neighbor dependencies: status=pending; slice_ids=S2-01,S4-01..S4-05,S5-02..S5-03,S7-03; evidence=pending
- `INV-WORLD-MOVEMENT` block/fluid properties, subclasses, registrations, tags and resource defaults: status=pending; slice_ids=S3-02,S3-06..S3-07,S5-01..S5-05,S6-03; evidence=pending
- `INV-MODIFIERS` movement effects/attributes/enchantments/equipment and application/removal/conditions: status=pending; slice_ids=S3-05,S6-01..S6-03; evidence=pending
- `INV-EXTERNAL` player-only external inputs and consumers (corrections, pushes, pistons, mounts): status=pending; slice_ids=S1-06,S5-04,S7-01..S7-03; evidence=pending
- `INV-EXCLUSIONS` health/regen/hunger/food/saturation/exhaustion/damage-combat/non-player audit, with direct vanilla-state reads documented: status=pending; slice_ids=S1-03,S2-03,S6-01..S6-03,S7-02; evidence=pending

## Coverage ledger

Each entry is a bounded behavior slice, not an entire class/stage/travel method. Terminal status requires paired source ranges/hashes, producer-consumer links, closed dependencies and a concrete reachability rationale. Preserve enclosing guards and order when splitting methods.

### Slice S1-01: Input producers and sampling

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: verified pair paths from Options key defaults, ClientPacketListener input construction, KeyMapping state read/write, KeyboardInput.tick(boolean), Input state/vector accessors, LocalPlayer.isMovingSlowly(), and the initial LocalPlayer.aiStep() sample/slow gate. The enclosing crouch-state writer differs by direct field versus getAbilities() and remains queued to S2-01.
- A evidence: net/minecraft/client/Options.java#keyUp/keyLeft/keyDown/keyRight/keyJump/keyShift lines 109-114; ClientPacketListener.java input construction lines 392 and 1106; KeyMapping.java#set(InputConstants.Key,boolean) lines 41-46, isDown() 84-86, setDown(boolean) 159-161; KeyboardInput.java#tick(boolean) 13-26; Input.java#tick/getMoveVector/hasForwardImpulse lines 15-24; LocalPlayer.java#isMovingSlowly() 604-606 and #aiStep() 627-641. SHA-256 values are in the artifact source table below.
- B evidence: net/minecraft/client/Options.java#keyUp/keyLeft/keyDown/keyRight/keyJump/keyShift lines 127-132; ClientPacketListener.java input construction lines 376 and 961; KeyMapping.java#set(InputConstants.Key,boolean) lines 48-53, isDown() 91-93, setDown(boolean) 166-168; KeyboardInput.java#tick(boolean) 14-27; Input.java#tick/getMoveVector/hasForwardImpulse lines 15-24; LocalPlayer.java#isMovingSlowly() 608-610 and #aiStep() 649-663. SHA-256 values are in the artifact source table below.
- State producers/writers -> consumers/readers: client key events -> KeyMapping.set/setDown -> KeyboardInput reads Options keyUp/down/left/right/jump/shift -> writes Input booleans and float impulses -> LocalPlayer.aiStep passes isMovingSlowly to input.tick -> downstream player tick copies impulses into xxa/zza and LivingEntity.aiStep constructs the travel input vector (close exact transfer range in S1-02).
- Parent slices / dependencies / closure evidence: S1-02 must close the input-state transfer to travel; S2-01 must resolve the crouching writer's getAbilities() accessor dependency. B MOVING_SLOW_FACTOR is a declaration-only unused constant; whole-tree search found no read. Continue inventory of the keyboard event path if changed dependencies appear.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The paired KeyboardInput.tick expressions retain identical key sampling, impulse ternaries, conditional scaling literal, cast and order. Input.java is byte-identical by SHA-256. The B MOVING_SLOW_FACTOR declaration is unused. No standalone input difference is established; terminal no-difference disposition awaits state-writer/dependency closure.
- Finding IDs or checked absence/replacement path: none; B constant use checked absent by whole-source-tree search.
### Slice S1-02: Local-player tick and superclass order

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: traced the unpaused Minecraft game-tick call to ClientLevel.tickEntities, its player tick dispatch and exception wrapper, the LocalPlayer chunk guard, then LocalPlayer/Player/LivingEntity/Entity superclass order. In the movement path, LivingEntity.aiStep calls the LocalPlayer serverAiStep override for the local player (isEffectiveAi returns true); that override calls Player.serverAiStep before copying the sampled Input impulses and jumping bit when this player is the controlled camera. LivingEntity then processes jump state and calls travel with the xxa/yya/zza vector. This does not close travel branches or post-travel callbacks, assigned to S1-03..S1-06 and S3.
- A evidence: net/minecraft/client/Minecraft.java#tick call to level.tickEntities() line 1446; ClientLevel.java#tickEntities lines 152-179 and #tickNonPassenger lines 181-202; Level.java#guardEntityTick lines 523-532; LocalPlayer.java#tick lines 184-202, #serverAiStep lines 609-620, #isEffectiveAi lines 493-496, #aiStep lines 627-793; Player.java#tick lines 207-277; LivingEntity.java#tick lines 2124-2247, #aiStep lines 2368-2472; Entity.java#tick lines 354-360. Exact hashes are in the artifact source table above.
- B evidence: net/minecraft/client/Minecraft.java#tick call to level.tickEntities() line 1642; ClientLevel.java#tickEntities lines 161-171 and #tickNonPassenger lines 173-183; Level.java#guardEntityTick lines 462-471; LocalPlayer.java#tick lines 191-209, #serverAiStep lines 613-624, #isEffectiveAi lines 498-501, #aiStep lines 649-815; Player.java#tick lines 220-291; LivingEntity.java#tick lines 2218-2334, #aiStep lines 2455-2578; Entity.java#tick lines 391-393. Exact hashes are in the artifact source table above.
- State producers/writers -> consumers/readers: unpaused Minecraft tick -> ClientLevel entity eligibility -> LocalPlayer.tick chunk gate -> superclass ticking -> LocalPlayer.aiStep input and state processing -> LivingEntity.aiStep cutoff/jump handling -> LocalPlayer.serverAiStep writes xxa/zza/jumping only for controlled camera -> LivingEntity.travel(new Vec3(xxa,yya,zza)). Riding tick and packet branches are inventoried in S1-06; travel and post-travel consumers remain open.
- Parent slices / dependencies / closure evidence: S1-03..S1-06 and S3 close the rest of the aiStep/travel path; S1-06 closes passenger scheduling and the B tickingEntities membership producer. S2-01 closes pose/ability accessors consumed by LocalPlayer.aiStep. S4/S5 close movement queries/callbacks reachable from these methods.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A and B call order through the local travel dispatch is aligned. The LocalPlayer chunk predicate is equivalent at the block-to-section level: A constructs BlockPos from x/z, whose Vec3i double constructor floors coordinates; B reads blockPosition coordinates maintained by Entity.setPosRaw using Mth.floor. Both LevelReader overloads reduce the same integer coordinates with >> 4. A ClientLevel.tickNonPassenger gates the Player branch on entity.inChunk || entity.isSpectator; B iterates tickingEntities and invokes tickNonPassenger for each nonremoved, nonpassenger member, with no corresponding local guard. This is a reachable tick-eligibility change candidate; close the producer and normal LocalPlayer membership preconditions in S1-06 before assigning its final movement disposition. The old-position snapshot helper and its A direct-field equivalent match for position and rotation fields. No terminal result yet.
- Finding IDs or checked absence/replacement path: candidate delta CD-S1-02-01: ClientLevel player tick eligibility changed from the A inChunk/spectator condition to B tickingEntities membership. Its effect for LocalPlayer remains open pending S1-06 membership and riding closure.

### Slice S1-03: Sprint gates, timers, and writers

- Inventory ID(s): INV-TICK; INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: compared LocalPlayer.aiStep sprint timer increment/decrement, item-use/shift resets, auto-start delay, held-key start, swimming/water/ground/collision stop gates, and all local sprint state writes. Compared the forward impulse helper and LocalPlayer/Entity sprint setters/readers. Direct hunger and blindness values are recorded only as predicates; their producer systems remain excluded.
- A evidence: net/minecraft/client/player/LocalPlayer.java#aiStep lines 628-703, #hasEnoughImpulseToStartSprinting lines 989-992, #setSprinting lines 450-453; net/minecraft/world/entity/Entity.java#isSprinting/setSprinting lines 1833-1839; net/minecraft/world/entity/player/Player.java#getSpeed lines 1433-1435. Hashes are in the artifact source table above.
- B evidence: net/minecraft/client/player/LocalPlayer.java#aiStep lines 650-725, #hasEnoughImpulseToStartSprinting lines 1019-1022, #setSprinting lines 455-458; net/minecraft/world/entity/Entity.java#isSprinting/setSprinting lines 1964-1970; net/minecraft/world/entity/player/Player.java#getSpeed lines 1455-1457. Hashes are in the artifact source table above.
- State producers/writers -> consumers/readers: sampled Input.forwardImpulse and hasForwardImpulse, ground/underwater and water/swimming state, shift and sprint key state, item use, food level > 6 or mayfly ability, and blindness effect feed the same ordered start/stop predicates. LocalPlayer.setSprinting resets sprintTime and delegates the shared sprint flag write; Player speed is read from MOVEMENT_SPEED. Movement modifier installation/use is linked to S6; input and ability producers are linked to S1-01/S2-01.
- Parent slices / dependencies / closure evidence: S1-01 closes input sampling and key state; S2-01 closes the ability accessor used for mayfly; S3 closes the travel branches that consume the sprint flag; S6 closes movement-speed modifier application and effect/attribute dependencies. S4 closes ground, collision, water and swimming predicates. Food/health/exhaustion production remains excluded.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): For a client-controlled player on the reachable aiStep path, the A/B sprint conditions, `0.8` forward-impulse threshold, seven-tick trigger, guard order, setter order, and shared-flag bit are identical. The only source-form change in this bounded gate is direct `abilities.mayfly` field access to `getAbilities().mayfly`, whose identity/equivalence is pending S2-01. The sprint flag affects later travel speed, so final no-difference disposition also waits on S3/S6. No independent sprint-gate delta is established yet.
- Finding IDs or checked absence/replacement path: none; paired gate expressions and Entity sprint shared-flag accessors match, subject to S2-01 and S3/S6 closure.

### Slice S1-04: Jump state, cooldown, and auto-jump

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: compared LocalPlayer auto-jump timer sample/decrement/forced-jump write, Player jump-trigger cooldown decrement, LivingEntity noJumpDelay decrement and jump branch order, and jumpFromGround vertical/horizontal velocity writes. The predicate requires the normal client-player aiStep path; jumpFromGround is reached when jumping is set, fluid handling is enabled, the ground or shallow-fluid branch allows it, and noJumpDelay is zero. Auto-jump collision search and block jump-factor dependencies remain linked to S2/S4/S5.
- A evidence: net/minecraft/client/player/LocalPlayer.java#aiStep autoJumpTime lines 649-654, jump/flight preconditions lines 713-730, #updateAutoJump lines 874-972, #canAutoJump/#isMoving lines 974-987; net/minecraft/world/entity/player/Player.java#aiStep lines 483-506; LivingEntity.java#getJumpPower lines 1878-1880, #jumpFromGround lines 1882-1896, and #aiStep lines 2368-2472. Hashes are in the artifact source table above.
- B evidence: net/minecraft/client/player/LocalPlayer.java#aiStep autoJumpTime lines 671-676, jump/flight preconditions lines 735-752, #updateAutoJump lines 904-1002, #canAutoJump/#isMoving lines 1004-1017; net/minecraft/world/entity/player/Player.java#aiStep lines 489-512; LivingEntity.java#getJumpPower/#getJumpBoostPower lines 1967-1973, #jumpFromGround lines 1975-1985, and #aiStep lines 2455-2578. Hashes are in the artifact source table above.
- State producers/writers -> consumers/readers: LocalPlayer auto-jump collision/contact code sets autoJumpTime=1; the next LocalPlayer.aiStep decrements a positive timer and forces input.jumping before Player/LivingEntity.aiStep. Player.aiStep decrements jumpTriggerTime before the superclass call; LocalPlayer flight-toggle logic reads/resets it in its own earlier portion. LivingEntity.aiStep decrements noJumpDelay, resolves ground/water/lava jumping, invokes jumpFromGround or jumpInLiquid, then later travel consumes the resulting velocity. S1-05 owns flight-toggle behavior; S2/S4/S5 own its pose, fluid, collision and block-jump-factor predicates.
- Parent slices / dependencies / closure evidence: S1-01 closes sampled jump input; S1-02 closes the local-player/superclass tick dispatch; S1-05 closes jump-trigger interaction with flight; S3 closes resulting velocity through travel; S4/S5 close `isAffectedByFluids`, ground support, collision and block jump factor; S6 closes Jump Boost effect identity/amplifier provenance; S7-01 closes direct-field versus rotation-accessor inputs in the auto-jump and sprint-jump branches.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Auto-jump timer and Player jumpTriggerTime/noJumpDelay decrement order match across endpoints. A/B `jumpFromGround` differ in numeric type and addition order when Jump Boost is active: A starts from float `getJumpPower()` and adds `0.1F * (amplifier + 1)` into that float before widening it through `setDeltaMovement`; B widens the float base and adds a separately float-evaluated boost through a double `d`, then writes that double. With block jump factor 1 and Jump Boost I, A computes float-rounded `0.42F + 0.1F` (0.5199999809265137 after widening), whereas B computes the double sum of the two float values (0.5199999883770943). This is an exact reachable vertical-velocity delta under the jump branch above. Block jump factor/effect-state closure remains open; retain the candidate finding until those dependencies and the travel consumer are closed.
- Finding IDs or checked absence/replacement path: candidate delta CD-S1-04-01 — Jump Boost vertical impulse is assembled with float addition in A and double addition in B; evidence/ranges above. Exact movement consequence is established; endpoint effect/block-factor producer closure remains S5/S6.

### Slice S1-05: Flight, abilities, and flight toggle

- Inventory ID(s): INV-TICK; INV-STATE
- Exact behavior boundary and enclosing guards/order checked: compared the LocalPlayer.aiStep ability-toggle, Elytra-start, fall-flying snapshot, water descent, camera-flight vertical input and post-super grounded flight-disable regions. The saved `bl` is the prior sampled jump state; `bl4` records an auto-jump timer override, and the toggle branch checks the rising edge before Player.aiStep decrements jumpTriggerTime in the superclass call. Field/accessor and Elytra item predicate equivalence remain dependencies.
- A evidence: net/minecraft/client/player/LocalPlayer.java#aiStep lines 705-793, including abilities toggle lines 705-730, fall-flight/water handling lines 732-743, camera-flight velocity writer lines 745-757 and grounded post-super reset at method tail; net/minecraft/world/entity/player/Player.java#aiStep lines 483-506 for the later jumpTriggerTime decrement. Hashes are in the artifact source table above.
- B evidence: net/minecraft/client/player/LocalPlayer.java#aiStep lines 727-815, including abilities toggle lines 727-752, fall-flight/water handling lines 754-765, camera-flight velocity writer lines 767-779 and grounded post-super reset at method tail; net/minecraft/world/entity/player/Player.java#aiStep lines 489-512 for the later jumpTriggerTime decrement. Hashes are in the artifact source table above.
- State producers/writers -> consumers/readers: Input jumping/shift plus prior jump edge, autoJumpTime, mayfly/flying ability state, game mode always-flying state, passenger/climbable/fluid predicates and chest Elytra state select flight transitions. LocalPlayer writes ability flying and vertical delta movement, calls Elytra start-flight state/packet path, snapshots fall-flying, and clears ordinary flight after superclass travel when grounded. Player/Entity travel later consumes flying and sprint state. Remote ability synchronization and Elytra/effect producers are linked to S6/S7.
- Parent slices / dependencies / closure evidence: S1-01/S1-04 close sampled jump and auto-jump semantics; S2-01 closes direct abilities field versus getter; S1-06 closes passenger/riding and packet transitions; S3 closes Player.travel/fall-flying movement consumption; S4/S5 close water, climbable and collision predicates; S6/S7 close Elytra, ability and external server-state writers.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The ability toggle, Elytra-start, water descent, camera-flight vertical input and post-super grounded-disable order are structurally aligned. B's `getAbilities()` calls replace A's direct `abilities` field reads/writes, and B's `ItemStack.is(Items.ELYTRA)` replaces A's `getItem() == Items.ELYTRA`; exact behavior equivalence has not yet been closed. The writes are movement-reachable when mayfly/flight, jump-edge, fluid or Elytra preconditions hold. No formula delta was observed in this bounded pass; do not mark terminal until the listed state/item/travel dependencies are resolved.
- Finding IDs or checked absence/replacement path: no independent flight delta established in this pass; ability, item and travel dependencies remain open.

### Slice S1-06: Riding and local movement transitions

- Inventory ID(s): INV-TICK; INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: compared ClientLevel root/passenger scheduling, Player-specific passenger branches, Entity/LivingEntity.rideTick dispatch, LocalPlayer passenger packets, charged jump-riding input, and local tick membership. The server-controlled vehicle's physics are outside the player movement scope; player input, player tick scheduling, passenger placement and player-side transitions remain in scope.
- A evidence: net/minecraft/client/multiplayer/ClientLevel.java#tickEntities lines 152-179, #tickNonPassenger lines 181-202 and #tickPassenger lines 204-223; LocalPlayer.java#tick passenger packet branch lines 187-196 and #aiStep riding-jump state lines 761-787; #sendRidingJump lines 362-367; Entity.java#rideTick lines 1577-1583; LivingEntity.java#rideTick lines 2577-2582. Hashes are in the artifact source table above.
- B evidence: net/minecraft/client/multiplayer/ClientLevel.java#tickEntities lines 161-171, #tickNonPassenger lines 173-183 and #tickPassenger lines 185-195; LocalPlayer.java#tick passenger packet branch lines 194-202 and #aiStep riding-jump state lines 783-809; #sendRidingJump lines 367-372; Player.java#isAlwaysTicking lines 2048-2050; Entity.java#rideTick lines 1689-1695; LivingEntity.java#rideTick lines 2689-2694. Hashes are in the artifact source table above.
- State producers/writers -> consumers/readers: vehicle passenger relation and server updates feed ClientLevel passenger recursion; LocalPlayer's sampled input and jump edge feed xxa/zza/jump/shift packet values and the PlayerRideableJumping charge sequence; LocalPlayer.rideTick dynamically invokes LocalPlayer.tick, which runs the superclass player movement path only when its local chunk gate passes; `sendRidingJump` emits START_RIDING_JUMP with floor(scale*100). Server packet consumers and server-originated player/vehicle corrections remain open under S7. Non-player vehicle movement is excluded.
- Parent slices / dependencies / closure evidence: S1-02 closes top-level entity/tick-list membership; S1-01/S1-04/S1-05 close input, jump and flight edges; S3 closes player passenger travel; S7-01/S7-03 close serverbound input/jump and correction writers. A ClientLevel's `inChunk` writer/update path and B's entity-section ticking callbacks are required to resolve player membership edge cases.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): For a valid Player passenger whose vehicle is ticking, A enters the Player branch even when the passenger is not in an entity-ticking chunk, but increments tickCount and calls rideTick only if `inChunk`; it then updates chunk registration and recurses only while inChunk. B enters the Player branch unconditionally after vehicle/removal guards, increments tickCount and calls rideTick regardless of tickingEntities membership; entity-section move callbacks replace A's explicit updateChunkPos call. A Player with `inChunk=false` and a still-valid passenger relation therefore reaches rideTick in B but not A on that pass. Resolve whether LocalPlayer can satisfy that state with a loaded chunk and close the resulting player-side behavior before terminal disposition. The ride-jump charge formula/order and START_RIDING_JUMP payload expression match; ordinary passenger packet call order is aligned apart from rotation accessors pending S7-01.
- Finding IDs or checked absence/replacement path: candidate delta CD-S1-06-01 — Player passenger rideTick admission changes from A's inChunk gate to unconditional Player handling in B; concrete reachable effect pending membership/correction closure in S7. The charged jump formula/payload is pair-matched but server consumer closure remains open.

### Slice S2-01: Pose, dimensions, and resize collision query

- Inventory ID(s): INV-STATE; INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: compared Player.updatePlayerPose selection/fallback order, Entity.setPose and DATA_POSE resize dispatch, canEnterPose collision query, Player pose dimensions, and Entity.refreshDimensions box/position mutation. The tick writer is Player.tick.updatePlayerPose after the superclass player tick. `stopSleeping` also writes standing pose after moving to a bed exit position and is a concrete pose-resize caller.
- A evidence: net/minecraft/world/entity/player/Player.java#tick updatePlayerPose call line 276; #updatePlayerPose lines 350-378; #getDimensions lines 1990-1992 and pose table lines 112-121. net/minecraft/world/entity/Entity.java#setPose lines 311-313; #onSyncedDataUpdated lines 2301-2303; #canEnterPose lines 1640-1642; #refreshDimensions lines 2306-2334. net/minecraft/world/entity/LivingEntity.java#stopSleeping lines 3032-3052 and SLEEPING_DIMENSIONS line 126. Hashes are in the artifact source table above.
- B evidence: net/minecraft/world/entity/player/Player.java#tick updatePlayerPose call line 290; #updatePlayerPose lines 364-392; #getDimensions lines 1966-1968 and pose table lines 124-133. net/minecraft/world/entity/Entity.java#setPose lines 340-342; #onSyncedDataUpdated lines 2470-2472; #canEnterPose lines 1759-1761; #refreshDimensions lines 2475-2497. net/minecraft/world/entity/LivingEntity.java#stopSleeping lines 3154-3174 and SLEEPING_DIMENSIONS line 152. Hashes are in the artifact source table above.
- State producers/writers -> consumers/readers: Player.updatePlayerPose selects fall-flying/sleeping/swimming/spin/crouching/standing and collision fallbacks, then setPose writes DATA_POSE; the entity-data callback calls refreshDimensions, which updates dimensions, eye height and the bounding box and can move the entity. stopSleeping first positions the player at the bed exit, then sets STANDING. `canEnterPose` queries noCollision using getBoundingBoxForPose(pose). LocalPlayer.aiStep's crouching writer is an earlier state producer; movement collision and eye-height consumers are linked to S2-02/S4.
- Parent slices / dependencies / closure evidence: S1-01/S1-02 close crouching/input and pose update timing; S2-02 closes eye height and pose consumers; S4 closes noCollision/getBoundingBoxForPose shape behavior; S5 closes pose-specific movement properties; S7-01 closes synchronized pose/position writes and the stopSleeping event path. The client and server resize branches must both be reconciled.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Player.updatePlayerPose selection and canEnterPose expressions match apart from B's direct ability-field read becoming `getAbilities().flying` in LocalPlayer.aiStep. The resize algorithm changes materially. A preserves the old box's minimum corner when the new width is not smaller and, on the server, calls `move(SELF, new Vec3(oldWidth-newWidth, 0, oldWidth-newWidth))` when width grows, the entity is past firstTick, and the level is server-side. B first calls reapplyPosition to build the box around the unchanged entity position, then runs its free-position adjustment only for nonplayers; it does not move a Player. A reachable wake-from-sleep path has 0.2-wide sleeping dimensions and 0.6-wide standing dimensions; stopSleeping moves to the bed exit, writes STANDING (dispatching refreshDimensions), then writes the captured bed-exit position again. Thus the A self-move is followed by an explicit setPos restoring the captured coordinates, while B performs no self-move and also writes that position. The remaining candidate is the extra A move's collision/state callback path and transient box state, not a claimed final coordinate offset; resolve whether its flags/callbacks persist through the later setPos in S4/S7 before terminal disposition.
- Finding IDs or checked absence/replacement path: candidate delta CD-S2-01-01 — A performs an extra server-side Player self-move during pose-driven width growth; waking from sleep reaches the exact width transition, then resets position. Its surviving collision/flag/callback effect is open pending S4/S7 closure.

### Slice S2-02: Eye height and movement poses

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: compared Entity's generic pose/dimension eye-height formula, LivingEntity's sleeping special case, and Player's pose-specific eye heights. Entity.refreshDimensions writes the selected value after updating pose dimensions; values are identical across both endpoint bodies.
- A evidence: net/minecraft/world/entity/Entity.java#getEyeHeight(Pose,EntityDimensions)/getEyeHeight lines 2372-2382; LivingEntity.java#getEyeHeight/getStandingEyeHeight lines 3067-3072; Player.java#getStandingEyeHeight lines 1818-1829. Source hashes are in the artifact source table above.
- B evidence: net/minecraft/world/entity/Entity.java#getEyeHeight(Pose,EntityDimensions)/getEyeHeight lines 2536-2546; LivingEntity.java#getEyeHeight/getStandingEyeHeight lines 3188-3194; Player.java#getStandingEyeHeight lines 1838-1849. Source hashes are in the artifact source table above.
- State producers/writers -> consumers/readers: pose selection -> getDimensions(pose) -> Entity.refreshDimensions writes eyeHeight; sleeping yields 0.2F, swimming/fall-flying/spin-attack yields 0.4F, crouching yields 1.27F, and standing/default yields 1.62F. Player's superclass default is dimensions.height * 0.85F. getEyeY and eye-in-fluid/block-state queries consume this field; their movement consequences are linked to S4/S5.
- Parent slices / dependencies / closure evidence: S2-01 closes pose/dimension producers and refresh timing; S4/S5 close eye-height consumers in fluid, block and movement predicates. Player-specific and generic formula bodies are identical, including float literals and multiplication order.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): No difference is present in the paired eye-height formulas or Player pose values. The B LivingEntity override is additionally declared `final`, which changes subclass overrideability but not the vanilla Player result; no mod/subclass behavior is in scope. Keep this slice in-progress until eye-height movement consumers and S2-01 dimension selection are closed.
- Finding IDs or checked absence/replacement path: none in the bounded formulas; checked absence pending consumer closure in S4/S5.

### Slice S2-03: Item use, edge sneak, and stored movement state

- Inventory ID(s): INV-STATE; INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: compared the LocalPlayer active-item input scaling and its nonpassenger guard, sprint-trigger reset, shared shift flag read/write, and the local state transfer of sampled impulses to LivingEntity movement fields. Item, hunger, and damage systems are not modeled; only the active-use predicate's effect on player input is in scope.
- A evidence: net/minecraft/client/player/LocalPlayer.java#aiStep lines 643-647 and #serverAiStep lines 609-620; net/minecraft/world/entity/Entity.java#setShiftKeyDown/isShiftKeyDown lines 1805-1811; net/minecraft/world/entity/LivingEntity.java#isUsingItem lines 2706-2708, #setLivingEntityFlag lines 2749-2758, and #stopUsingItem lines 2869-2876. Hashes are in the artifact source table above.
- B evidence: net/minecraft/client/player/LocalPlayer.java#aiStep lines 665-669 and #serverAiStep lines 613-624; net/minecraft/world/entity/Entity.java#setShiftKeyDown/isShiftKeyDown lines 1936-1942; net/minecraft/world/entity/LivingEntity.java#isUsingItem lines 2824-2826 and #setLivingEntityFlag lines 2871-2880. Hashes are in the artifact source table above.
- State producers/writers -> consumers/readers: sampled shift state is stored in Input and the Entity shared shift flag; active-item use is read by LocalPlayer.aiStep. For `isUsingItem && !isPassenger`, the same ordered writes scale leftImpulse and forwardImpulse by 0.2F and reset sprintTriggerTime. Later LocalPlayer.serverAiStep transfers those fields to xxa/zza and jumping for the controlled camera, then LivingEntity.travel consumes the resulting movement vector. The source item-use producer and hunger/food outcomes remain excluded; riding input is S1-06.
- Parent slices / dependencies / closure evidence: S1-01 closes key sampling; S1-02 closes controlled-camera transfer and travel ordering; S1-06 closes passenger scheduling; S3 closes downstream travel consumption; S7 closes synchronized/server-originated shift and active-use state writers. S4/S5 close movement predicates that read crouch/pose state.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The paired active-use scaling statements, 0.2F literal, operand order, passenger guard, and sprint-trigger reset match. Entity shift state uses the same shared-flag bit in both endpoints. B's LivingEntity `isUsingItem` reads the same DATA_LIVING_ENTITY_FLAGS bit used by the A getter; remaining item-use and external-state writers and downstream movement consumers are open. There is no separate item-use movement delta established in this bounded comparison.
- Finding IDs or checked absence/replacement path: none for the paired input scaling/shared shift-bit expressions; producer/consumer closure remains S1/S3/S7.

### Slice S3-01: Travel dispatch and pre-branch state

- Inventory ID(s): INV-TICK; INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.travel(Vec3)` outer `isEffectiveAi() || isControlledByLocalInstance()` gate; gravity base and slow-falling prelude; one `blockPosition()` fluid lookup; ordered water, lava, fall-flying, then ordinary ground/air dispatch. `Player.travel(Vec3)` wrapper checked through swimming and player-flight overrides, `super.travel`, and movement-statistics update.
- A evidence: `net/minecraft/world/entity/LivingEntity.java#travel` lines 1914-2046; `net/minecraft/world/entity/player/Player.java#travel` lines 1387-1417. LivingEntity and Player source hashes are in the foundational source table above.
- B evidence: `net/minecraft/world/entity/LivingEntity.java#travel` lines 2003-2139; `net/minecraft/world/entity/player/Player.java#travel` lines 1409-1439. LivingEntity and Player source hashes are in the foundational source table above.
- State producers/writers -> consumers/readers: `isEffectiveAi` / `isControlledByLocalInstance`, slow-falling effect and current vertical delta select the shared prelude; the fluid state and predicates select the first two branches; the fall-flying flag selects the third; otherwise ordinary movement runs. Player swimming and flying flags select the Player wrapper branches. The wrapper bodies match exactly; LocalPlayer's effective-AI reachability and preceding input/tick transfer are traced in S1-02. Branch-specific movement state, block/fluid inputs, modifiers, and effects are closed in S3-02..S3-08 and S4/S5/S6/S7.
- Parent slices / dependencies / closure evidence: S1-02 establishes the LocalPlayer call chain and effective-AI path; S1-03 and S1-05 establish sprint/flight state inputs; S2-02 covers eye-height consumers; S3-02..S3-08 cover branch bodies; S4/S5/S6/S7 close collision, predicate, modifier, and external-writer dependencies. Those branch and state dependencies remain open, but do not change the bounded dispatch comparison.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Both versions use the same outer guard and pre-branch gravity/slow-falling order, read fluid state at the same position, and test branches in the same order. The paired `Player.travel` overrides are statement-for-statement identical, including swimming correction, flying-speed save/set/restore, `super.travel`, fall-distance/shared-flag writes, and movement-statistics update. No dispatch or pre-branch delta was established. Differences within the selected fall-flying and ordinary branches, including the newer discard-friction check and unloaded-chunk Y threshold, are routed to their branch-specific slices; this status does not resolve those branch outcomes.
- Finding IDs or checked absence/replacement path: checked absence for dispatch/pre-branch behavior and Player wrapper; branch-specific paths pending S3-02..S3-08.

### Slice S3-02: Ground acceleration and friction

- Inventory ID(s): INV-TICK; INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: ordinary branch of `LivingEntity.travel` after water/lava/fall-flying dispatch; movement-support block selection, block friction read, ground/air friction coefficient, `handleRelativeFrictionAndCalculateMovement`, and the `moveRelative` input-vector transform. The post-move climbable/jump velocity adjustment in the helper is included. The later levitation, unloaded-client-chunk, gravity, and discard-friction result selection is recorded only where it consumes the helper result; its dedicated vertical-cutoff behavior routes to S3-04.
- A evidence: `net/minecraft/world/entity/LivingEntity.java#travel` lines 2023-2041 and `#handleRelativeFrictionAndCalculateMovement` lines 2062-2074; `net/minecraft/world/entity/Entity.java#moveRelative/#getInputVector` lines 1075-1090. Source hashes for LivingEntity and Entity are in the foundational source table above.
- B evidence: `net/minecraft/world/entity/LivingEntity.java#travel` lines 2112-2133 and `#handleRelativeFrictionAndCalculateMovement` lines 2155-2169; `net/minecraft/world/entity/Entity.java#moveRelative/#getInputVector` lines 1176-1191. Source hashes for LivingEntity and Entity are in the foundational source table above. `PowderSnowBlock.java` SHA-256 `4069b3ef70e2d0ce146bbff79be54b2be99b7c7b32818c1eb45f11fd66f99aa3`; the matching source-manifest entry was checked. The B friction writers are `LongJumpMidJump.java` SHA-256 `36f42f3f7b9a5ecc4f3145a07f0c14eb710af6bcc28031cb99520313255ce473` and `LongJumpToRandomPos.java` SHA-256 `4ec55d5d7915c32bf1a649b119a0e325acb2abc1d1a356890bee093c42287fd2`, both matching the B source manifest.
- State producers/writers -> consumers/readers: the selected support block supplies `t`; `onGround` selects `getSpeed() * (0.21600002F / (t*t*t))` versus `flyingSpeed` inside `getFrictionInfluencedSpeed`; `moveRelative` normalizes only when input length squared exceeds 1, rotates by yaw, then adds the vector to stored delta movement. B's `getYRot()` accessor directly returns the same `yRot` field and is the only declaration in the B entity hierarchy inspected. The helper moves with the accumulated delta and may set y to `0.2` when collision or jumping coincides with its climbability predicate. B's powder-snow branch requires powder snow at the feet and `canEntityWalkOnPowderSnow`; that predicate accepts tagged mobs or LivingEntities wearing leather boots. A has no `PowderSnowBlock.java` and no `Blocks.POWDER_SNOW` registration, so this B-only route cannot be assigned invented 1.16.5 behavior; S4/S6 must close registration/support applicability. B's `shouldDiscardFriction()` branch bypasses x/z and y friction multipliers only when its field is true; the field defaults false and its only source writers found are the two Mob long-jump behaviors above. Vanilla Player reachability and any external player writer remain for S7 closure.
- Parent slices / dependencies / closure evidence: S1-01/S1-02 establish input/yaw and tick reachability; S3-01 closes dispatch; S4 closes support/collision and block availability; S5 closes climbable/jump predicates; S6 closes speed/modifier/equipment inputs; S7 closes external writers. S3-04 owns unloaded-chunk vertical fallback and resulting vertical velocity.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the support lookup, friction coefficient, acceleration formula, `getInputVector` normalization/rotation math and delta addition match. B's yaw accessor is a direct getter of the same field. A's helper adjusts vertical velocity to `0.2` for `(horizontalCollision || jumping) && onClimbable()`. B extends that predicate with powder snow when `PowderSnowBlock.canEntityWalkOnPowderSnow(this)`; this is a source-confirmed B-only route whose player/map applicability is not closed. B also adds a discard-friction bypass, but the only vanilla writers found are Mob long-jump behaviors, so no vanilla Player effect is established. The ordinary branch's downstream vertical selection is not closed here.
- Finding IDs or checked absence/replacement path: candidate `CD-S3-02-01` — B-only powder-snow climb/jump velocity route, pending S4/S5/S6 closure and not an A-era feature; no historical A behavior inferred. The discard-friction branch has no vanilla Player writer found; external-writer closure is pending S7. No delta found in the shared acceleration math.

### Slice S3-03: Air acceleration and stored air speed

- Inventory ID(s): INV-TICK; INV-STATE
- Exact behavior boundary and enclosing guards/order checked: player `flyingSpeed` reset and sprint increment in `Player.aiStep`, movement-speed attribute copied to `LivingEntity.speed`, and `getFrictionInfluencedSpeed` selection between ground attribute speed and stored air speed. The temporary abilities-flight override is checked in S3-01's identical `Player.travel` wrapper.
- A evidence: `net/minecraft/world/entity/player/Player.java#aiStep` lines 498-506; `net/minecraft/world/entity/LivingEntity.java#getFrictionInfluencedSpeed/#getSpeed/#setSpeed` lines 2106-2116. Source hashes for Player and LivingEntity are in the foundational source table above.
- B evidence: `net/minecraft/world/entity/player/Player.java#aiStep` lines 504-512; `net/minecraft/world/entity/LivingEntity.java#getFrictionInfluencedSpeed/#getSpeed/#setSpeed` lines 2200-2210. Source hashes for Player and LivingEntity are in the foundational source table above.
- State producers/writers -> consumers/readers: after `super.aiStep()`, Player resets `flyingSpeed` to `0.02F`, adds the same float literal `0.005999999865889549` while sprinting, and stores `getAttributeValue(Attributes.MOVEMENT_SPEED)` as `speed`. The ordinary travel helper selects `speed * (0.21600002F / (friction cubed))` on ground and `flyingSpeed` in air. Player's ability-flight travel wrapper temporarily sets `flyingSpeed` to the ability value times the sprint multiplier and restores the saved value, as recorded in S3-01. Attribute/modifier producers and sprint predicates remain S6/S1 dependencies.
- Parent slices / dependencies / closure evidence: S1-02 establishes Player.aiStep ordering; S1-03 closes sprint state; S3-01 establishes the flight wrapper and dispatch; S3-02 records the consumer helper; S6 closes movement-speed modifiers and defaults. Player's post-super bob calculation changes from `sqrt(getHorizontalDistanceSqr(...))` to a float-cast `horizontalDistance()` expression in B; that is an animation/update path routed to S3-09 and is outside this stored-air-speed comparison.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): For Player, both versions reset, sprint-adjust, and restore the same stored air-speed field in the same order; the ordinary movement helper uses the same ground/air selection and friction-influenced speed formula. No air-acceleration or stored-air-speed delta was established. The movement-speed value's attribute sources and the sprint predicate are still open in their dedicated slices.
- Finding IDs or checked absence/replacement path: checked absence for the paired air-speed assignment and consumer formula; Player post-super bob formula is routed to S3-09.

### Slice S3-04: Gravity, drag, and velocity thresholds

- Inventory ID(s): INV-TICK; INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S3-05: Jump and sprint-jump power

- Inventory ID(s): INV-TICK; INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S3-06: Climbable travel

- Inventory ID(s): INV-TICK; INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S3-07: Water/lava travel and swimming

- Inventory ID(s): INV-TICK; INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S3-08: Fall-flying travel

- Inventory ID(s): INV-TICK; INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S3-09: Post-travel updates

- Inventory ID(s): INV-TICK; INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S4-01: Move entry, collision timing, and axis clipping

- Inventory ID(s): INV-TICK; INV-STATE; INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S4-02: Step candidates, selection, and tie-breaking

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S4-03: Collision iteration and shape algorithms

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S4-04: Support, grounding, and edge probes

- Inventory ID(s): INV-COLLISION; INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S4-05: Player collision callbacks and response

- Inventory ID(s): INV-COLLISION; INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S5-01: Movement-property providers and registrations

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S5-02: A-era collision-shape providers

- Inventory ID(s): INV-COLLISION; INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S5-03: Contact, landing, slowdown, and climb blocks

- Inventory ID(s): INV-COLLISION; INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S5-04: Fluid flow, height, and current

- Inventory ID(s): INV-WORLD-MOVEMENT; INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S5-05: Block/state additions and removals

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S6-01: Movement-effect and attribute consumers

- Inventory ID(s): INV-MODIFIERS; INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S6-02: Movement enchantments and equipment

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S6-03: Defaults, tags, and modifier resources

- Inventory ID(s): INV-MODIFIERS; INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S7-01: Packet-driven player position/velocity state

- Inventory ID(s): INV-EXTERNAL; INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S7-02: External player velocity writers

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

### Slice S7-03: Remaining writers and cross-stage closure

- Inventory ID(s): INV-STATE; INV-COLLISION; INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: pending source inspection.
- A evidence: pending source readiness; no range accepted.
- B evidence: pending source readiness; no range accepted.
- State producers/writers -> consumers/readers: pending paired inventory.
- Parent slices / dependencies / closure evidence: pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no source conclusion.
- Finding IDs or checked absence/replacement path: pending.

## Dependency queue and blockers

- SRC-A: exact 1.16.5 publication, readiness JSON, artifact/source manifests, diagnostics. Owner: source owner; next: verify hashes and IDs.
- SRC-B: exact 1.17.1 publication, readiness JSON, artifact/source manifests, diagnostics. Owner: source owner; next: verify hashes and IDs.
- RESOURCE-INVENTORY: exact client-jar resources and referenced/server-supplied data. Owner: this run after provenance verification.
- DECOMPILER-DIAGNOSTICS: inspect each relevant method body; request precise bytecode/source assistance via commentary if damaged.
- Open dependencies: SRC-A, SRC-B, RESOURCE-INVENTORY, DECOMPILER-DIAGNOSTICS

## Finding index

No findings accepted. Earlier 1.16.5--1.17.1 reports are candidate/navigation context only, not evidence for this run.

## Resume checkpoint

- Last completed slices: S3-01 travel dispatch/pre-branch comparison and S3-03 air-speed comparison; no dispatch or stored-air-speed delta found. Source pair verified; S1-01..S1-06, S2-01..S2-03, and S3-02 remain in-progress pending their listed dependencies.
- Next bounded slice: continue S3-02 closure for powder-snow support applicability and external friction writers, then S3-04 gravity, drag, and velocity thresholds; S3-09 owns the Player post-super bob formula change. Retain S1/S2 slices as in-progress until input/pose, tick-membership, ability, speed, fluid/collision, modifier, item, mount, resize and external-writer dependencies close.
- Outstanding dependencies and owners: source-owner publication is complete; source closure remains with this run, including S1-06 entity-tick membership/passenger scheduling and the remaining movement/resource inventories.
- Assumptions requiring verification: no unresolved source-root or namespace assumptions; verify every newly selected source file against its manifest as slices are opened.

## Implementation reconciliation

- Reconciliation status: pending; deferred until blind-discovery freeze and owned by separate integrator.
- Repository revision inspected: none; implementation not inspected.
- Finding -> implementation disposition/evidence: pending.
- Existing implementation without a frozen source finding: pending integrator review.
- Coverage gaps routed back to discovery slices: pending.

## Independent source audit

- Reviewer: pending assignment; must differ from discovery authors.
- Status: pending
- Inventories and call-chain ranges re-walked: pending.
- Concrete missed-slice routes (or `none found`): pending.
- Misses routed to slice/finding IDs and owners: pending.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 22 pending, 10 in-progress, 2 compared-no-difference, 0 findings, 0 not-applicable, 0 blocked.
- Required inventory status and evidence: all seven pending; evidence pending.
- Open dependencies: S1-01 input/state-writer closure; S1-02 entity tick-list membership and passenger scheduling; S1-03 abilities, travel consumers, movement-speed modifiers and predicates; S1-04 block jump factor and Jump Boost effect provenance; S1-05 abilities, Elytra/item state and travel/external writers; S1-06 chunk membership, passenger and server packet/correction closure; S2-01 collision-box and synchronized pose/position closure; S2-02 eye-height consumers; S2-03 item-use/shift state writers and travel consumers; S3-02 powder-snow support applicability and discard-friction player reachability; RESOURCE-INVENTORY and movement-diagnostic scope closure.
- Unresolved gaps and limits: S1-01..S1-06/S2-01..S2-03 and S3-02 dependency closure, and the remaining 32 source slices (22 pending, 10 in-progress) are open.
- Evidence/hash/correspondence audit: not started.
- Blind freeze: pending.
- Implementation reconciliation: pending.
- Independent audit: pending.
- Runtime validation: not performed; tests and runtime launches are not authorized.

Active checkpoint only; this is neither a partial handoff nor a completion claim. Continue after the exact sources are published.
