# Discovery: 1.19.3 to 1.19.4

- Status: active
- Scope: client player movement; older A = 1.19.3; newer B = 1.19.4. Health/food state production, attack/damage resolution, non-player movement and vehicle physics are excluded. Direct player-motion response, including velocity/impulse/knockback application and resulting player state writes, remains in scope; direct vanilla-state movement predicates may read excluded-system values.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff`; task branch `feat/source-discovery-movement-source-1-19-3-1-19-4`; started 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap / Mojmap; both exact readiness records and source/artifact hashes verified. Member correspondence and comparison are pending.
- Source preparation owner / command / log / readiness marker: shared source owner. A `1.19.3` and B `1.19.4` Mojmap publications are validated below; both came from the same successful exact batch command and full log recorded at relative paths. This worker is read-only on shared generated sources and will not run decompilation.
- Toolchain/decompiler/remapper versions and options: verified Java 25.0.3+9-LTS; Gradle 9.7.1; Vineflower 1.12.0; TinyRemapper 0.14.1; ASM 9.10.1; Gson 2.14.0; Mapping IO 0.9.1; decompiler heap 4G.
- Discovery author(s): source-only worker for exact pair 1.19.3 to 1.19.4.
- Independent reviewer (must differ from discovery authors): not yet assigned.

## Artifact manifest

Shared artifact root, relative to the repository: `build/movement-campaign-2026-10-07/`. A: requested/resolved `1.19.3`, namespace/CLI `mojmap`; validated marker `ready/1.19.3/mojmap.ready.json` (`status=ready`, version metadata ID `1.19.3`, 4,618 source files, 22,430,993 source bytes). Source root `ready/1.19.3/mojmap/`; source manifest `ready/1.19.3/mojmap.sources.sha256`, SHA-256 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; all 4,618 listed source-file hashes checked, with zero missing or mismatched files. Artifact manifest `ready/1.19.3/artifacts.sha256`, SHA-256 `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`; relevant `client.jar` SHA-256 `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6`, `client-mojmap.jar` `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`, and `client_mappings.txt` `f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b`; these five relevant artifact entries exist and match their manifest hashes. Readiness diagnostics `ready/1.19.3/movement-diagnostics.txt`, SHA-256 `95e8189acb6b384b21a584f28d5e8d23922996ea9f446e17e54734ccba840e60`; official metadata and required Entity/LivingEntity/Player/LocalPlayer source presence verified; diagnostics enumerate 21 movement method/caller locations. Provenance `ready/1.19.3/mojmap.provenance.json` gives exact batch command for 1.19.2, 1.19.3, 1.19.4; normalized command: `gradlew.bat decompileMinecraft --versions=1.19.2,1.19.3,1.19.4 --mappings=mojmap --decompiler-heap=4G`, with shared staging output and cache under this relative artifact root. Successful full log at `staging/mojmap-1.19.2-to-1.19.4-45d0418942a144298b19fb4ac74ff06a/gradle.full.log`; log reports requested/resolved 1.19.3 and BUILD SUCCESSFUL. Logged remapper access warnings are for GUI option classes; no player movement body damage is reported. Toolchain: Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0, TinyRemapper 0.14.1, ASM 9.10.1, Gson 2.14.0, Mapping IO 0.9.1; 4G decompiler heap. B: requested/resolved `1.19.4`, namespace/CLI `mojmap`; validated marker `ready/1.19.4/mojmap.ready.json` (`status=ready`, version metadata ID `1.19.4`, 4,740 source files, 23,021,785 source bytes). Source root `ready/1.19.4/mojmap/`; source manifest `ready/1.19.4/mojmap.sources.sha256`, SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`; all 4,740 listed source-file hashes checked, with zero missing or mismatched files. Artifact manifest `ready/1.19.4/artifacts.sha256`, SHA-256 `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72`; relevant `client.jar` SHA-256 `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`, `client-mojmap.jar` `efbf38c89b396faae60cfe3bdb91671cb8d1ec3ccf2e27d3ffab4d261acd016d`, `client_mappings.txt` `5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606`, and `version.json` `31c80f5545445fe8e09aae83cda8983988517b75a4ea9e9f359f8a2a17850c75`; shared `version_manifest_v2.json` SHA-256 `845dfb7f8b28ce06bf0752b597ef2d4d65df973e4b59d8d6ff429ad2d3adde04`. All five entries exist and match the artifact manifest. Readiness diagnostics `ready/1.19.4/movement-diagnostics.txt`, SHA-256 `61b7a69e1804684d0fc4414d5482d7233de5a7bfe64a19fb5a92571a7a69bcef`; official metadata and required Entity/LivingEntity/Player/LocalPlayer source presence verified; diagnostics enumerate 22 movement method/caller locations. The same successful batch log reports requested/resolved 1.19.4 and `BUILD SUCCESSFUL`, with no reported player-movement body damage. Exact A/B source pair is verified; bounded source comparison is underway and recorded in the coverage ledger. Readiness JSON and source paths below are relative to this manifest through `../../../build/movement-campaign-2026-10-07/`. Do not commit generated sources, jars, raw diffs, caches or logs.

- Campaign artifact-revision applicability: revision `feather-r1-2026-10-07` covers only revised derived Feather jars for 1.8.9, 1.9.4, 1.10.2, 1.11.2, 1.12.2 and 1.13.2 (`workflows/source-campaign-2026-10-07/ARTIFACT-REVISION-2026-10-07.md`). This 1.19.3/1.19.4 pair is Mojmap and uses its verified original mapped jars plus the separately hash-verified JOML dependency; none of the three findings relies on a revised Feather derivation.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending; freeze only after source-only coverage is completed and audited for provenance.
- Evidence inventory and finding IDs included at freeze: source-only findings F-01 through F-04 are drafted; additional coverage remains open.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze: confirmed; no mod implementation or wiki-audit outputs opened and no wiki browsing performed.
- Source/mapping hashes covered by freeze: A/B source manifests and all listed Java source hashes were verified; freeze is pending. Manifest hashes: A `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; B `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`.

## Correspondence and call order

- Exact pair: A `1.19.3` and B `1.19.4`, both Mojmap, exact ready markers, metadata IDs, artifact/source manifests, and all listed source-file hashes verified. Namespace correspondence is source-root Mojmap; paired methods named below have same logical class/member role.
- Reachable ordinary local path established for F-01: `LocalPlayer#aiStep` sets sprint state and calls superclass tick -> `Player#aiStep` -> `LivingEntity#aiStep` -> `LivingEntity#travel` -> relative movement helper -> ground/air speed selection -> `moveRelative`. A air selection reads `flyingSpeed`; B invokes `Player#getFlyingSpeed`.
- Reachable auto-jump path established for F-02: `LocalPlayer#move` -> `updateAutoJump` after actual horizontal movement -> normalized forward probe and collision/shape query -> accepted gap writes `autoJumpTime=1` -> local tick consumes the timer. Full collision provider closure remains open.
- Input sampling, all sprint start/stop branches, mounted control, jump and flight branches, remaining local post-travel paths and their state edges are not fully indexed. Two confirmed deltas do not establish complete call-graph coverage.

## Required source inventories

- `INV-TICK` input sampling, complete player tick order and travel branches: status=pending; slice_ids=I-TICK-INPUT-SAMPLE, I-TICK-EDGE-NUDGE, I-TICK-UNSTUCK-POSITION, I-TICK-LOCAL-TRAVEL-GATE, I-TICK-SPRINT-ELIGIBILITY, I-TICK-TRAVEL-FLUIDS, I-TICK-TRAVEL-FALL-FLYING, I-TICK-FLIGHT-SPEED, I-TICK-SWIM-HEAD-PROBE, I-TICK-AIR-SPEED, I-TICK-PLAYER-AISTEP-REMAINDER, I-TICK-LIVING-AISTEP-DISPATCH, I-TICK-LIVING-AISTEP-DAMAGE, I-TICK-AUTO-JUMP, I-TICK-MOUNT-START, I-TICK-PORTAL-PROGRESS, I-TICK-REMAINDER; evidence=paired exact member slices below; full tick and branch inventory remains open.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=I-TICK-AIR-SPEED, I-TICK-SPRINT-ELIGIBILITY, I-TICK-PLAYER-AISTEP-REMAINDER, I-STATE-REMAINDER; evidence=air-speed writer/reader chain is traced; full state inventory remains open.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=I-TICK-AUTO-JUMP, I-TICK-EDGE-NUDGE, I-COLLISION-ENTITY-MOVE, I-COLLISION-QUERY-ASSEMBLY, I-COLLISION-REMAINDER; evidence=auto-jump probe math is traced to collision query; providers and registrations remain open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, registrations and resource inventory: status=pending; slice=I-WORLD; no terminal inventory evidence yet.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and applications/removals/conditions: status=pending; slice=I-MODIFIER; no terminal inventory evidence yet.
- `INV-EXTERNAL` player-only external inputs and client consumers: status=pending; slice=I-EXTERNAL; no terminal inventory evidence yet.
- `INV-EXCLUSIONS` direct movement reads versus excluded health/food/damage producers and non-player movement: status=pending; slice_ids=I-EXCLUSIONS, I-TICK-LIVING-AISTEP-DAMAGE; evidence=campaign scope is recorded, but direct-read audit remains open.

## Coverage ledger

Every row below is a bounded source unit, not a stage-level completion claim. The seven campaign inventories remain incomplete.

### Slice I-TICK-AIR-SPEED: stored sprint air speed versus current sprint getter

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: ordinary player airborne relative-movement speed selection outside passenger, fall-flying and abilities-flight branches; local sprint state before superclass travel and A post-super field write.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java#aiStep()V` lines 652–819, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`; `.../world/entity/player/Player.java#aiStep()V` lines 496–517, SHA-256 `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`; `.../world/entity/LivingEntity.java#getFrictionInfluencedSpeed()F` / `handleRelativeFrictionAndCalculateMovement(Vec3)` lines 2201–2248, SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java#aiStep()V` lines 645–800, SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`; `.../world/entity/player/Player.java#aiStep()V` lines 499–510 and `getFlyingSpeed()F` lines 2112–2118, SHA-256 `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`; `.../world/entity/LivingEntity.java` corresponding methods lines 2152–2203, SHA-256 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`.
- State producers/writers -> consumers/readers: LocalPlayer sprint writes -> Player aiStep -> A post-super `flyingSpeed` writer / B Player getter -> LivingEntity air branch -> relative movement. Grounded friction bypasses this air-speed source.
- Parent slices / dependencies / closure evidence: exact source-pair readiness and Player/LivingEntity call chain checked; broader tick and movement-modifier inventories remain separate open slices.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source-confirmed reachable delta under the stated ordinary airborne preconditions; coefficient differs by one float ULP and A has one-tick read timing.
- Finding IDs or checked absence/replacement path: F-01; B getter replaces A stored value for the air consumer.

### Slice I-TICK-AUTO-JUMP: inverse-square-root input to auto-jump probe

- Inventory ID(s): INV-TICK, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: updateAutoJump direction normalization and probe distance after LocalPlayer move; unchanged canAutoJump gate.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java#move(MoverType,Vec3)` lines 900–905 and `#updateAutoJump(float,float)Z` lines 911–1007, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`; `ready/1.19.3/mojmap/net/minecraft/util/Mth.java#fastInvSqrt(F)F` lines 495–501, SHA-256 `f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java#move(MoverType,Vec3)` lines 880–885 and `#updateAutoJump(float,float)Z` lines 891–987, SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`; `ready/1.19.4/mojmap/net/minecraft/util/Mth.java#invSqrt(F)F` lines 387–389, SHA-256 `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797`; JOML artifact and method bytecode hash are recorded in F-02.
- State producers/writers -> consumers/readers: moved horizontal position -> auto-jump probe input -> inverse-square-root factor -> probe endpoints/distance -> collision query -> gap/height gates -> `autoJumpTime=1` -> next local tick jump path.
- Parent slices / dependencies / closure evidence: I-TICK-AUTO-JUMP -> I-COLLISION-REMAINDER for shape providers and collision query behavior; exact algorithm delta is independently bounded, while final probe outcome remains open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source-confirmed algorithm change when the unchanged auto-jump gates pass; no particular gap outcome is claimed.
- Finding IDs or checked absence/replacement path: F-02; B `Mth.invSqrt`/JOML call replaces A fast inverse-square-root call.

### Slice I-TICK-MOUNT-START: sprint eligibility while passenger and dismount carry-over

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: passenger sprint-start gates, vehicle capability inheritance, sprint modifier write, vehicle-link removal and next grounded local movement consumer.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java#aiStep()V` lines 693–719 and `hasEnoughFoodToStartSprinting()Z` lines 1047–1050, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`; `ready/1.19.3/mojmap/net/minecraft/world/entity/Entity.java#removeVehicle()V` lines 1784–1790, SHA-256 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`; `ready/1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java#setSprinting(Z)V` lines 1964–1974, SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java#aiStep()V` lines 685–701, `canStartSprinting()Z` / `vehicleCanSprint(Entity)Z` lines 1022–1034, SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`; `ready/1.19.4/mojmap/net/minecraft/world/entity/Entity.java#canSprint()Z` lines 3163–3165 and `removeVehicle()V` lines 1798–1804, SHA-256 `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`; `ready/1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java#setSprinting(Z)V` lines 1895–1905, SHA-256 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`. Override inventory: B full source search yields Entity default, Player and new Camel; existing older rideables inherit the false default.
- Normal shift-dismount route: A `LocalPlayer#tick()V` lines 188–197 sends shift input; `ServerGamePacketListenerImpl#handlePlayerInput(...)V` lines 365–368 and `Player#rideTick()V` lines 473–477 update/check it; `ServerPlayer#stopRiding()V` lines 957–963 calls `connection.dismount`; server `#dismount(...)V` lines 976–978 emits a position correction with the dismount flag; `ClientPacketListener#handleMovePlayer(...)V` lines 592–597 calls `removeVehicle`. A source hashes: Player `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`, ServerPlayer `7df71c12ff99bec35b7c6b0e68fa24e5a5aa7293c6dd8f3342d433936ad35c7c`, ServerGamePacketListenerImpl `81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b`, ClientPacketListener `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`; LocalPlayer source hash above. B sends the same shift input; listener lines 367–370 and `Player#rideTick()` lines 476–480 process it. B `ServerPlayer` has no `stopRiding()` override and uses `Entity#stopRiding()`; `ServerEntity#sendChanges()` lines 80–90 broadcasts `ClientboundSetPassengersPacket`, then teleports a removed ServerPlayer; `ClientPacketListener#handleSetEntityPassengersPacket()` lines 961–974 ejects previous passengers and applies the received list. B source hashes: Player `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`, ServerPlayer `5eeea9be89db11000daa95cada0a4120c5eda1014aae9e868fba0bee8de05606`, ServerGamePacketListenerImpl `1e1b8dc23031bd5dfc4a3dd5453ec1e6ad3211a534a810927c8fa725fc271dc9`, ServerEntity `a597bbd4b7639ac0ce91dcc6ea53af4ae734d872beea1cc215a9fbfb9ed67347`, ClientPacketListener `bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715`; LocalPlayer source hash above.
- State producers/writers -> consumers/readers: A passenger and input gate -> sprint flag / speed modifier -> dismount without clearing either -> next local tick -> grounded movement speed. B vehicle capability gate prevents this start path for existing rideables.
- Parent slices / dependencies / closure evidence: passenger gate, B vehicle inheritance, sprint modifier, and the normal shift-input dismount packet route are checked. The claim is conditioned on client dismount application before a subsequent eligible grounded local tick; packet arrival relative to that tick is not established. Other dismount triggers and external-input consumers remain in I-EXTERNAL; vehicle motion is excluded.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source-confirmed under the stated existing-vehicle, input, food, dismount and grounded-tick conditions; no vehicle motion claim.
- Finding IDs or checked absence/replacement path: F-03; B `Entity#canSprint()Z` default false replaces A's unconditional passenger eligibility; newer Camel behavior is modern-only.
### Slice I-TICK-INPUT-SAMPLE: keyboard movement and jump input sampling

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `KeyboardInput#tick(boolean,float)V` reads four direction keys, calculates forward/left impulses, samples jump/sneak keys, and applies the supplied movement scale when enabled.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/client/player/KeyboardInput.java#tick(boolean,float)V`, lines 21–33, SHA-256 `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/client/player/KeyboardInput.java#tick(boolean,float)V`, lines 21–33, SHA-256 `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`; entire paired source file hash is identical.
- State producers/writers -> consumers/readers: key bindings and caller-supplied scaling flag/value -> Input movement impulses, jumping and shift state -> LocalPlayer tick predicates/travel input.
- Parent slices / dependencies / closure evidence: bounded to the input sampling method; caller-provided scale semantics and the later tick consumers remain in I-TICK-REMAINDER.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): exact source file hashes match and the bounded method is byte-for-byte identical; this does not close caller-side key option configuration or full tick order.
- Finding IDs or checked absence/replacement path: checked absence of a difference in the paired method body; caller-side behavior remains open.
### Slice I-TICK-PORTAL-PROGRESS: confusion-gated local portal overlay

- Inventory ID(s): INV-TICK, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer confusion-duration branch updates local `portalTime`; trace that value to direct consumers to classify movement applicability.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java#handleNetherPortalClient()V`, lines 829–868, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`; `getDuration()I` in `ready/1.19.3/mojmap/net/minecraft/world/effect/MobEffectInstance.java`, lines 135–137, SHA-256 `151cc45017d1d77dd841cb4a6b7f7e659cbe4ead631e701fe440f9479879eb00`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java#handleNetherPortalClient()V`, lines 810–850, SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`; `endsWithin(I)Z` / `isInfiniteDuration()Z` in `ready/1.19.4/mojmap/net/minecraft/world/effect/MobEffectInstance.java`, lines 132–138, SHA-256 `72ea45b4c7fceba5b11d4101f1e5ec9d76b3fc978a456607923e2bf0ad3b22c7`.
- State producers/writers -> consumers/readers: confusion effect duration -> LocalPlayer local portal progress fields -> GUI and GameRenderer portal overlays. Full-source references for the client float fields are confined to LocalPlayer, Gui and GameRenderer; they are not inputs to player position/velocity movement.
- Parent slices / dependencies / closure evidence: direct field consumers checked: A `Gui.java` line 177 and `GameRenderer.java` lines 942, 1108; B `Gui.java` line 170 and `GameRenderer.java` lines 960, 1132. This is client display state, not the Entity portal transition/cooldown path.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): positive finite durations preserve the `duration > 60` boundary; an infinite duration differs because B `endsWithin(60)` excludes infinity and A `getDuration()` returns `-1`. The difference changes only local portal overlay progress/camera rendering, so it is outside player movement scope.
- Finding IDs or checked absence/replacement path: not a movement finding; no movement consumer exists for LocalPlayer portalTime fields in either source tree.
### Slice I-TICK-UNSTUCK-POSITION: local suffocation escape direction and impulse

- Inventory ID(s): INV-TICK, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: no-physics guard callsites in LocalPlayer#aiStep and the helper that selects a free adjacent cell and writes a 0.1 horizontal velocity nudge.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java#moveTowardsClosestSpace(DD)V`, lines 413–440, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`; `BlockPos#BlockPos(DDD)V` lines 46–48 in `ready/1.19.3/mojmap/net/minecraft/core/BlockPos.java`, SHA-256 `19fd76058d98a5bc8f0dfd6aab03524c4941c0407660812d46a22f3cc05fc54a`, delegates to `Vec3i#Vec3i(DDD)V` lines 39–41, SHA-256 `e48be042611e39e69be4d6858644209cd00dbaa8e389f7a3f92809b51c642d32`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java#moveTowardsClosestSpace(DD)V`, lines 412–439, SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`; `BlockPos#containing(DDD)` lines 74–76 in `ready/1.19.4/mojmap/net/minecraft/core/BlockPos.java`, SHA-256 `6f7d806a3a3ca3204db248a7d0dba4908685224dc5a3350e2eb51f69f97d9715`, calls `Mth.floor` for each coordinate.
- State producers/writers -> consumers/readers: LocalPlayer unstuck call coordinates and current Y -> BlockPos floor quantization -> suffocating-block query for center/neighbors -> closest clear direction -> horizontal delta movement nudge.
- Parent slices / dependencies / closure evidence: local method body and coordinate conversion are checked; block suffocation provider inventory remains I-COLLISION-REMAINDER.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): A and B preserve suffocation check order, cardinal candidate order, strict tie handling and impulse values. The only relevant call-site change is `new BlockPos(double,...)` to `BlockPos.containing`, and both floor each coordinate using the same `Mth.floor`.
- Finding IDs or checked absence/replacement path: checked absence of difference in the unstuck coordinate/selection/impulse slice; shape providers remain open.
### Slice I-TICK-FLIGHT-SPEED: abilities-flight speed preservation during travel

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Player#travel(Vec3)V` ability-flight branch speed source, superclass travel, vertical velocity scaling, field restoration, fall-distance reset and shared-flag update.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/world/entity/player/Player.java#travel(Vec3)V`, lines 1448–1478, SHA-256 `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`; stores prior `flyingSpeed`, assigns ability speed times current sprint multiplier, calls superclass travel, and restores the field.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/world/entity/player/Player.java#travel(Vec3)V`, lines 1440–1467 and `getFlyingSpeed()F` lines 2112–2118, SHA-256 `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`; the field assignment is removed and the getter returns ability flying speed times the same sprint multiplier when flying and not passenger.
- State producers/writers -> consumers/readers: abilities flying speed and current sprint flag -> A temporary field / B virtual getter -> LivingEntity air movement helper -> relative movement; both methods then preserve pre-travel vertical velocity times `0.6F`, reset fall distance and clear shared flag 7 in the same sequence.
- Parent slices / dependencies / closure evidence: B getter and A/B Player travel branch connected to the same superclass consumer; F-01 separately covers the ordinary non-abilities airborne getter coefficient/timing change.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): when `abilities.flying && !isPassenger()`, both feed the same current ability speed multiplied by 2 for sprint or 1 otherwise before the same superclass travel operation. The code relocation from temporary field to virtual getter preserves this flight branch; no trajectory claim.
- Finding IDs or checked absence/replacement path: checked absence of a difference in this abilities-flight speed branch; ordinary airborne speed difference is F-01.
### Slice I-TICK-SWIM-HEAD-PROBE: fluid lookup above a swimming player

- Inventory ID(s): INV-TICK, INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: `Player#travel(Vec3)V` swimming and non-passenger guard, look/jump conditions, block fluid-state probe at Y + 1.0 - 0.1, and vertical delta adjustment.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/world/entity/player/Player.java#travel(Vec3)V`, lines 1453–1460, SHA-256 `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`; query cell uses `new BlockPos(getX(), getY() + 1.0 - 0.1, getZ())`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/world/entity/player/Player.java#travel(Vec3)V`, lines 1445–1452, SHA-256 `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`; query cell uses `BlockPos.containing` with the same coordinate expressions.
- State producers/writers -> consumers/readers: swimming/passenger/look/jump state -> integer block-cell probe -> fluid-state empty predicate -> vertical velocity adjustment using current look Y and delta Y.
- Parent slices / dependencies / closure evidence: A double constructor delegates to `Vec3i(double,...)`, which calls `Mth.floor` on all coordinates; B `BlockPos.containing(double,...)` calls the same floor function. Full fluid providers and swimming travel remain I-WORLD and I-TICK-REMAINDER.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both paths quantize the same three coordinate expressions with the same floor function; the surrounding branch guards, constants and velocity operation are unchanged.
- Finding IDs or checked absence/replacement path: checked absence of difference in the above-player fluid probe and vertical adjustment; fluid provider inventory remains open.
### Slice I-TICK-LOCAL-TRAVEL-GATE: LocalPlayer reachability of LivingEntity travel body

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: outer travel body gate changed from `isEffectiveAi() || isControlledByLocalInstance()` to `isControlledByLocalInstance()`; resolve the guard for ordinary LocalPlayer with no controlling passenger.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java#travel(Vec3)V`, lines 2052–2054, 2150 and 2184, SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`; `LocalPlayer#isEffectiveAi()Z`, lines 493–496, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`, returns true.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java#travel(Vec3)V`, lines 1983–1985, 2081 and 2115, SHA-256 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`; `LocalPlayer#isEffectiveAi()Z`, lines 486–489, SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`, returns true, and B `Entity#isControlledByLocalInstance()Z`, lines 2795–2798, falls back to `this.isEffectiveAi()` when no controlling passenger exists.
- State producers/writers -> consumers/readers: local client-player type and its unchanged true `isEffectiveAi` override -> travel dispatch predicate -> complete LivingEntity travel body. A guard is true through its first term; B guard is true through inherited fallback.
- Parent slices / dependencies / closure evidence: LocalPlayer override and B inherited predicate traced; this slice is limited to ordinary LocalPlayer without an entity controlling passenger. Other entity and unusual player-passenger dispatch remains under broader tick inventory and non-player movement exclusion.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the ordinary local player with no controlling passenger, both outer travel guards are true and dispatch the same body. The other changed expressions in this method supply a fly-into-wall damage source to the unchanged damage call (excluded damage resolution) and update entity animation state (rendering); neither changes player position/velocity math at these sites. The changed OR term does not alter local-player reachability under this precondition.
- Finding IDs or checked absence/replacement path: checked absence of a difference in ordinary LocalPlayer travel dispatch; other receiver types are outside this player-scoped slice.
### Slice I-TICK-SPRINT-ELIGIBILITY: sprint initiation while fall-flying

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer's direct key-sprint branch for a non-passenger player already fall-flying, when the sprint key is down and all existing impulse, food, water, item-use and blindness conditions permit sprinting.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java#aiStep()V`, lines 709–717, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`; the direct key branch has no `isFallFlying` guard and calls `setSprinting(true)` when its listed predicates pass.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java#aiStep()V`, lines 696–698 and `#canStartSprinting()Z`, lines 1022–1030, SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`; the shared eligibility helper adds `!this.isFallFlying()` and blocks that call while gliding.
- State producers/writers -> consumers/readers: direct sprint key, input impulse, food/ability and item/effect predicates -> A sprint-state write versus B helper rejection; subsequent non-fall-flying player travel can consume sprint state through movement-speed selection.
- Parent slices / dependencies / closure evidence: exact paired LocalPlayer#aiStep and B helper checked; F-01 separately records the ordinary airborne sprint-speed consumer. This slice is limited to the non-passenger key-sprint route; mounted start eligibility is recorded in F-03.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): for a non-passenger player who is fall-flying, holds sprint, is not already sprinting, and meets the remaining direct key-branch predicates, A can set sprinting while B rejects initiation solely because of the new fall-flying gate. The glide equations themselves do not use this sprint flag in the compared travel branch; no immediate glide-speed or trajectory consequence is asserted.
- Finding IDs or checked absence/replacement path: F-04; source-confirmed sprint eligibility delta under the stated preconditions.

### Slice I-TICK-TRAVEL-FLUIDS: water and lava travel branches

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: paired `LivingEntity#travel(Vec3)V` water and lava branches, including fluid guards, depth-strider and dolphin's-grace scaling, relative movement, collision/climb handling, gravity and fluid-adjusted velocity writes; excludes fluid/block property production.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java#travel(Vec3)V`, lines 2062–2115, SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java#travel(Vec3)V`, lines 1993–2046, SHA-256 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`.
- State producers/writers -> consumers/readers: current fluid/ground/sprint/effect/enchantment state -> matching branch arithmetic -> relative movement and delta-velocity writes; environmental value producers remain outside this bounded comparison.
- Parent slices / dependencies / closure evidence: full method diff showed these two branch bodies preserve the same guards, expressions, order and writes; block/fluid values and registrations remain open in I-WORLD.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the same direct movement predicates and environmental values, the paired water and lava branch bodies are textually identical apart from source line offsets. This does not close fluid providers, movement modifiers or other travel branches.
- Finding IDs or checked absence/replacement path: checked absence of a difference in the water/lava travel branch bodies; environmental producers remain open.

### Slice I-TICK-TRAVEL-FALL-FLYING: gliding movement equations and writes

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: paired `LivingEntity#travel(Vec3)V` fall-flying branch equations, look-angle response, lift and drag calculations, movement call, grounded transition and delta-velocity writes; damage resolution and animation are excluded.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java#travel(Vec3)V`, lines 2117–2182, SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java#travel(Vec3)V`, lines 2048–2113, SHA-256 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`.
- State producers/writers -> consumers/readers: current velocity, look angle, pitch, ground/collision and level state -> gliding equations -> movement and delta-velocity writes; wall-impact damage call uses a changed damage-source API and is outside this slice.
- Parent slices / dependencies / closure evidence: full method diff checked; A/B gliding equations and direct movement writes preserve operation order, while the fly-into-wall damage call and final animation call are outside the bounded movement equations. External velocity inputs and equipment/modifiers remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): under the same fall-flying and movement-state inputs, the paired gliding movement calculations and direct motion writes match. This does not assert equal damage resolution, equipment effects or environmental values.
- Finding IDs or checked absence/replacement path: checked absence of a difference in the bounded gliding movement equations; damage-source API change is excluded from movement arithmetic.
### Slice I-TICK-EDGE-NUDGE: player edge-sneaking backoff height

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Player#maybeBackOffFromEdge guard, X then Z then diagonal reduction loops, and step-height value used to lower no-collision probe boxes.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/world/entity/player/Player.java#maybeBackOffFromEdge(Vec3,MoverType)`, lines 1050–1094, SHA-256 `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`; A `Entity.maxUpStep` is the public float field in `.../world/entity/Entity.java`, line 180, SHA-256 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/world/entity/player/Player.java#maybeBackOffFromEdge(Vec3,MoverType)`, lines 1041–1085, SHA-256 `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`; B `Entity#maxUpStep()F`, lines 3167–3169, returns the same private float field in `.../world/entity/Entity.java`, SHA-256 `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`.
- State producers/writers -> consumers/readers: Entity step-height field/setter -> player edge-backoff vertical probe offset -> collision query -> adjusted movement vector.
- Parent slices / dependencies / closure evidence: Player guard/order and B getter returning the inherited field are checked; no-collision providers and shape results remain in I-COLLISION-REMAINDER.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the only Player body change is replacing three direct reads of `maxUpStep` with `maxUpStep()`; B getter returns that field unchanged. Loop order, 0.05 increments, comparisons and vector write remain the same.
- Finding IDs or checked absence/replacement path: checked absence of a difference in the step-height/backoff value path; collision-query providers remain open.
### Slice I-TICK-PLAYER-AISTEP-REMAINDER: jump-trigger timer and post-super player updates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Player#aiStep jumpTriggerTime decrement and post-super setSpeed/ground bob/entity-touch/shoulder updates; paired LocalPlayer double-tap writer/reader remains linked.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/world/entity/player/Player.java#aiStep()V`, lines 497–499 and 519–558, SHA-256 `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`; `LocalPlayer#aiStep()V` jump-trigger writes lines 740–746, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/world/entity/player/Player.java#aiStep()V`, lines 500–502 and 517–555, SHA-256 `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`; `LocalPlayer#aiStep()V` paired jump-trigger writes lines 721–727, SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`.
- State producers/writers -> consumers/readers: LocalPlayer double-tap jump timer writes -> Player#aiStep decrement -> next LocalPlayer double-tap gate; post-super `setSpeed` reads movement-speed attribute and remaining statements retain their order. A's `flyingSpeed` reset/increment is covered by F-01.
- Parent slices / dependencies / closure evidence: exact paired Player method diff contains no other behavioral change after removing A's F-01 flyingSpeed write; local timer references were searched in both LocalPlayer/Player source pairs.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the jump timer decrement, post-super speed assignment and remaining player updates match in operation and guards. Health/food regeneration code earlier in the method is outside this behavior slice and remains excluded.
- Finding IDs or checked absence/replacement path: checked absence of another difference in the bounded Player#aiStep remainder; F-01 remains the sole movement-relevant delta found in this method comparison.

### Slice I-TICK-LIVING-AISTEP-DISPATCH: controlling-passenger travel dispatch

- Inventory ID(s): INV-TICK, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: LivingEntity#aiStep travel call selection after input friction and fall-flying update.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java#aiStep()V`, lines 2594–2599, SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`; A always calls `travel(new Vec3(xxa, yya, zza))`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java#aiStep()V`, lines 2545–2551, SHA-256 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`; when `getControllingPassenger()!=null && isAlive()` it calls `travelRidden`, otherwise `travel` with the same input vector.
- State producers/writers -> consumers/readers: controlling-passenger and alive state -> travel dispatcher; ordinary local Player with no controlling passenger still takes `travel`, while the new passenger-controlled path is vehicle physics.
- Parent slices / dependencies / closure evidence: ordinary local-player travel guard is separately bounded in I-TICK-LOCAL-TRAVEL-GATE; B-only route is explicitly excluded vehicle movement.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): no travel dispatch difference for the ordinary local player without a controlling passenger. If a passenger controls a Player-as-vehicle, B takes a vehicle-physics route, which the campaign excludes; no behavior for that route is asserted here.
- Finding IDs or checked absence/replacement path: scoped ordinary-player path is unchanged; B-only controlling-passenger vehicle route is an explicit exclusion.

### Slice I-TICK-LIVING-AISTEP-DAMAGE: freeze and drowning damage updates

- Inventory ID(s): INV-TICK, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: LivingEntity#aiStep powder-snow freezing damage amount/tag check and drowning damage-source call.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java#aiStep()V`, lines 2602–2629, SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`; freeze damage amount depends on `FREEZE_HURTS_EXTRA_TYPES`, with drowning call using `DamageSource.DROWN`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java#aiStep()V`, lines 2567–2580, SHA-256 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`; freezing call uses fixed 1.0F and a registry-backed damage source; drowning call uses a registry-backed source.
- State producers/writers -> consumers/readers: environment/effect state -> health damage calls; no direct player velocity, impulse, position or collision-state write is made at these changed callsites.
- Parent slices / dependencies / closure evidence: direct damage calls are identified in the complete LivingEntity#aiStep diff; health/damage production and resolution are explicitly excluded by campaign scope.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): source confirms freeze damage formula/source and drowning-source changes, but these are excluded damage/health behavior and do not themselves write movement state. No movement finding is raised from these calls.
- Finding IDs or checked absence/replacement path: explicit out-of-scope disposition; no direct player-motion response is present in the changed callsites.

### Slice I-TICK-REMAINDER: remaining local tick, input, jump, flight and post-travel paths

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: full input sampling; sprint and jump timers; sprint stop branches; flight toggle; pose and item-use gates; all travel dispatch branches; remaining post-travel updates.
- A evidence: paired LocalPlayer, KeyboardInput, Player and LivingEntity roots found; not all reachable methods and callers compared.
- B evidence: corresponding roots found; not all reachable methods and callers compared.
- State producers/writers -> consumers/readers: input, jump/sprint flags and timers -> travel/control predicates; full edge inventory open.
- Parent slices / dependencies / closure evidence: includes I-TICK-AIR-SPEED and I-TICK-AUTO-JUMP; new dependencies remain to be enumerated.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): unfinished required coverage.
- Finding IDs or checked absence/replacement path: F-01 and F-02 cover only their named behaviors.

### Slice I-STATE-REMAINDER: pose, dimensions and remaining movement state

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: constructors/defaults, pose transitions, dimensions, eye height, position/velocity/box, collision/ground/fluid flags, support state and timer writers/readers.
- A evidence: Entity, LivingEntity and Player source anchors available; complete paired writer/reader map not yet built.
- B evidence: corresponding source anchors available; complete paired writer/reader map not yet built.
- State producers/writers -> consumers/readers: pending full graph.
- Parent slices / dependencies / closure evidence: I-TICK-AIR-SPEED is closed only for its named sprint air-speed flow.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): unfinished required coverage.
- Finding IDs or checked absence/replacement path: none beyond the narrowly scoped F-01 path.

### Slice I-COLLISION-ENTITY-MOVE: entity bounding-box movement resolution body

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Entity#move(MoverType,Vec3)V` bounding-box displacement selection, collision-axis resolution, step candidates, position and movement-state writes, under identical collision-query results.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/world/entity/Entity.java#move(MoverType,Vec3)V`, lines 547–679, source SHA-256 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`; extracted member body SHA-256 `01e1932a0d9285fe9ba388a98761d19f4e6691f54e1b272aac38af5977bfb7ed`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/world/entity/Entity.java#move(MoverType,Vec3)V`, lines 549–681, source SHA-256 `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`; extracted member body SHA-256 `01e1932a0d9285fe9ba388a98761d19f4e6691f54e1b272aac38af5977bfb7ed`, identical to A.
- State producers/writers -> consumers/readers: incoming movement vector, position and box -> level collision-query results -> identical axis/step selection -> position, box, on-ground/horizontal-collision flags and delta-velocity clipping.
- Parent slices / dependencies / closure evidence: exact 133-line member bodies compare identical; collision query implementation, shapes, providers, contexts and state registrations remain in I-COLLISION-REMAINDER.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the movement-resolution body is byte-for-byte identical between A and B. This is limited to the resolver given the same collision-query results; no equivalence claim is made for providers or query results.
- Finding IDs or checked absence/replacement path: checked absence of difference in Entity movement-resolution method; provider and query paths remain open.
### Slice I-COLLISION-QUERY-ASSEMBLY: entity and block shape query wiring

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `CollisionGetter#getCollisions` combines entity collision shapes before block collision iteration; `getBlockCollisions` creates the iterator; `EntityGetter#getEntityCollisions` applies its bounding-box epsilon/filter and creates shapes from entity bounding boxes.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/world/level/CollisionGetter.java`, lines 67–75, SHA-256 `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c`; `EntityGetter#getEntityCollisions(Entity,AABB)` lines 57–76, SHA-256 `f7fa5641d3287d5084f4450e1c09cd4d5816c5cff5412c6f07b14779bb60215f`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/world/level/CollisionGetter.java`, lines 67–75, SHA-256 `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c`; `EntityGetter#getEntityCollisions(Entity,AABB)` lines 57–76, SHA-256 `f7fa5641d3287d5084f4450e1c09cd4d5816c5cff5412c6f07b14779bb60215f`.
- State producers/writers -> consumers/readers: movement query AABB and caller entity -> `canCollideWith` / entity-list query -> entity bounding-box VoxelShapes -> block-collision iterator -> Entity#move resolver.
- Parent slices / dependencies / closure evidence: the three paired method bodies are identical; BlockCollisions iteration and block-state shape providers, entity-list query implementation and collision predicates remain in I-COLLISION-REMAINDER.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): query assembly order, epsilon, predicate construction and bounding-box shape construction are unchanged. This does not establish equivalent entity lists or block shapes because their producers/providers remain open.
- Finding IDs or checked absence/replacement path: checked absence of a difference in these query-wiring member bodies; BlockCollisions and provider dependencies remain open.
### Slice I-COLLISION-REMAINDER: movement queries, shapes, callbacks and support providers

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: full entity movement axes/step/support/edge paths; query context; reachable block/entity shapes; callback order; registration and neighbor dependencies.
- A evidence: Entity, AABB, VoxelShape and block/fluid sources are available; complete providers and registrations not yet enumerated.
- B evidence: corresponding sources are available; complete providers and registrations not yet enumerated.
- State producers/writers -> consumers/readers: dimensions/pose/position/support -> query bounds/context -> chosen movement and callbacks; full map open.
- Parent slices / dependencies / closure evidence: I-TICK-AUTO-JUMP probes this query surface; closure remains open.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): unfinished required coverage.
- Finding IDs or checked absence/replacement path: F-02 records a probe-input algorithm change only.

### Slice I-WORLD: block/fluid movement properties, registrations and resource data

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: movement-relevant shape/callback overrides, friction/speed/jump factors, fluid flow and contact, registrations, tags, neighbors and data defaults.
- A evidence: exact client jar and source artifacts are hash-verified; member and resource inventory not yet performed.
- B evidence: exact client jar and source artifacts are hash-verified; member and resource inventory not yet performed.
- State producers/writers -> consumers/readers: registrations/properties/tags/neighbors -> player travel and collision/fluid consumers; pending enumeration.
- Parent slices / dependencies / closure evidence: I-COLLISION-REMAINDER and I-TICK-REMAINDER.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): unfinished required coverage; no modern-only applicability claims made.
- Finding IDs or checked absence/replacement path: none.

### Slice I-MODIFIER: movement attributes, effects, enchantments and equipment

- Inventory ID(s): INV-MODIFIERS, INV-TICK, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: speed/jump/gravity attribute aggregation and direct movement effects, enchantment/item conditions, equipment and item-use modifiers.
- A evidence: source roots and jars available; producer-to-consumer and resource chains not yet compared.
- B evidence: source roots and jars available; producer-to-consumer and resource chains not yet compared.
- State producers/writers -> consumers/readers: server/default values, effects/items/enchantments -> player movement predicates and arithmetic consumers; pending.
- Parent slices / dependencies / closure evidence: I-TICK-REMAINDER and I-WORLD.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): unfinished required coverage. Food/health producer systems remain excluded even where their values gate movement.
- Finding IDs or checked absence/replacement path: none.

### Slice I-EXTERNAL-PACKET-VELOCITY: incoming player velocity packet consumer

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: generic set-entity-motion packet lookup and scaling into the entity velocity setter, followed through Entity#lerpMotion.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java#handleSetEntityMotion(ClientboundSetEntityMotionPacket)V`, lines 486–493, SHA-256 `'+$ha+'`; `ready/1.19.3/mojmap/net/minecraft/world/entity/Entity.java#lerpMotion(DDD)V`, lines 1919–1921, SHA-256 `'+$ea+'`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java#handleSetEntityMotion(ClientboundSetEntityMotionPacket)V`, lines 499–505, SHA-256 `'+$hb+'`; `ready/1.19.4/mojmap/net/minecraft/world/entity/Entity.java#lerpMotion(DDD)V`, lines 1936–1938, SHA-256 `'+$eb+'`.
- State producers/writers -> consumers/readers: external packet integer components -> divide by `8000.0` -> generic entity lookup -> virtual `lerpMotion` -> `setDeltaMovement`; the same route applies when the looked-up ID is the local player.
- Parent slices / dependencies / closure evidence: exact packet-consumer and inherited velocity-setter bodies checked on both sides; direct player knockback method, push paths, piston effects and remaining external transitions are separate open inventory.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both paired method bodies retain the same lookup, divisor, call order and direct velocity write; member line offsets and surrounding classes changed, but this bounded player-facing path is unchanged.
- Finding IDs or checked absence/replacement path: checked absence of a difference in the packet-to-velocity member path; other external sources remain open.
### Slice I-EXTERNAL-PLAYER-KNOCKBACK: inherited player knockback response method

- Inventory ID(s): INV-EXTERNAL, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity#knockback(double,double,double)V` response arithmetic and direct velocity/impulse state writes when the receiver is a Player; attack/damage cause and resistance-attribute production are outside this slice.
- A evidence: `ready/1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java#knockback(DDD)V`, lines 1404–1414, SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`.
- B evidence: `ready/1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java#knockback(DDD)V`, lines 1386–1396, SHA-256 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`.
- State producers/writers -> consumers/readers: supplied strength/direction and `KNOCKBACK_RESISTANCE` attribute -> strength scaling -> `hasImpulse=true` and delta movement update using normalized horizontal direction, half prior horizontal velocity and grounded vertical clamp. Player inherits this method; the attribute producer chain remains in I-MODIFIER.
- Parent slices / dependencies / closure evidence: bounded to the inherited response method; exact body math and writes checked. Resistance modifier sources, packet application timing and remaining external sources remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): paired method body is identical in operation order, casts, constants and state writes for any LivingEntity receiver including Player; this does not claim the surrounding damage system is movement scope or that its producer is unchanged.
- Finding IDs or checked absence/replacement path: checked absence of a difference in the inherited player knockback-response body; attribute and external producer inventories remain open.
### Slice I-EXTERNAL: corrections, pushes, pistons, mounts and launch inputs

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: client consumers of player corrections, incoming velocity, pushes, piston displacement, mount/dismount transitions and launch items.
- A evidence: exact packet and entity source roots available; complete consumers not yet compared.
- B evidence: exact packet and entity source roots available; complete consumers not yet compared.
- State producers/writers -> consumers/readers: server/external inputs -> local position/velocity/mount state -> next local movement; pending closure.
- Parent slices / dependencies / closure evidence: I-TICK-MOUNT-START, I-STATE-REMAINDER.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): unfinished required coverage; non-player movement and damage/combat producers excluded.
- Finding IDs or checked absence/replacement path: none.

### Slice I-EXCLUSIONS: direct movement reads and excluded producer systems

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: health/food state production, attack/damage resolution, non-player movement and vehicle physics excluded; direct player-motion response and direct movement-state reads remain in scope.
- A evidence: scope fixed by campaign; sprint's direct food-state consumer is visible in LocalPlayer source, but all direct reads have not been enumerated.
- B evidence: scope fixed by campaign; sprint's direct food-state consumer is visible in LocalPlayer source, but all direct reads have not been enumerated.
- State producers/writers -> consumers/readers: document only direct vanilla-state movement predicates, not excluded producer behavior; full inventory pending.
- Parent slices / dependencies / closure evidence: I-TICK-REMAINDER and I-MODIFIER.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): unfinished scope audit; no excluded producer system is a movement finding candidate.
- Finding IDs or checked absence/replacement path: none.

## Dependency queue and blockers

- D-1: I-TICK-MOUNT-START -> I-EXTERNAL. The ordinary shift-input dismount path is source-traced on both sides (A flagged position correction; B passenger-list packet followed by player correction). Still open: other server/world dismount triggers and packet-arrival versus next-local-tick ordering, which source alone does not settle. Owner: source worker.
- D-2: I-TICK-AUTO-JUMP -> I-COLLISION-REMAINDER. Enumerate the collision providers, shape inputs and query consequences; the finding currently establishes only changed probe arithmetic. Owner: source worker.
- D-3: close remaining movement tick/state call graph and world/modifier/external inventories. Owner: source worker.
- No external source blocker. The work is incomplete because required source inventories remain open.

## Finding index

- F-01: sprint air speed reads current sprint state in B and has a one-ULP coefficient change; source-confirmed for stated preconditions.
- F-02: auto-jump probe uses a different inverse-square-root implementation; source-confirmed algorithm difference, with collision outcome not inferred.
- F-03: A mounted sprint can carry into one bounded post-dismount movement tick when the sprint key is released, the trigger timer is zero, and the stated movement/stop guards hold.
- F-04: A can initiate sprint while fall-flying under the stated key-sprint predicates; B adds a fall-flying rejection guard.

## Finding snapshot log

- Snapshot `F-03@a517ce65aabe15bdb170afc16e181cc7ccbe80e3`: immutable finding-only commit `a517ce65aabe15bdb170afc16e181cc7ccbe80e3`; file `findings/F-03-mounted-sprint-carryover.md`, SHA-256 `087322c71b2f0b468a49f9bf46b21983e8ab18ddc825caa242e991d6a3005bc5`. This original snapshot is retained and superseded for review after the reviewer requested proof that B cannot restart sprint before movement.
- Exact source identity for F-03: A `1.19.3` Mojmap source manifest SHA-256 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; B `1.19.4` Mojmap source manifest SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`. Cited A/B LocalPlayer files SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` / `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`; corresponding LivingEntity files `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` / `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`. The finding also cites exact server and packet-consumer source-file hashes inline.
- Exact mapped artifact identity for F-03: A artifact manifest SHA-256 `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`, `client-mojmap.jar` SHA-256 `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`; B artifact manifest SHA-256 `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72`, `client-mojmap.jar` SHA-256 `efbf38c89b396faae60cfe3bdb91671cb8d1ec3ccf2e27d3ffab4d261acd016d`.
- Interim revised snapshot `F-03@78e645e485854d95cf76d9b8a682bfe95400204f`: file `findings/F-03-mounted-sprint-carryover.md`, SHA-256 `27e3a80c5943e9d887482fa142d123562cd33f368c1f01d65837a0ccd27a6e43`; it narrowed the consequence per review. This immutable checkpoint was superseded after the normal dismount-route citations were restored to the finding.
- Final revised snapshot `F-03@128e17515ac2a111b557f3f7b56625dd54b9d06a`: immutable finding-only commit `128e17515ac2a111b557f3f7b56625dd54b9d06a`; file `findings/F-03-mounted-sprint-carryover.md`, SHA-256 `f9057b0a7e7e5b0364865413f33fe3ef320b799c3e0004ca8e6598e66886d1f2`. It narrows the consequence to the first grounded post-dismount movement tick when sprint key is released, `sprintTriggerTime == 0`, forward input and food are sufficient, and stop conditions are false. Under those conditions B sets the double-tap timer without starting sprint before movement. Normal shift-dismount source route is included. Reviewer `01a116ce-c937-7613-a49b-716e99582357`; status submitted for blind source review, acceptance pending.
- Snapshot `F-04@df40695a6319a70dd28cf81448e39fce9f5675ff`: immutable finding-only commit `df40695a6319a70dd28cf81448e39fce9f5675ff`; file `findings/F-04-sprint-during-fall-flying.md`, SHA-256 `60029df6f5bd84c0c0a91d67d23ea9f36d93f4af2618f9889fb1946eabaa3d2d`.
- Exact source identity: A `1.19.3` Mojmap source manifest SHA-256 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; B `1.19.4` Mojmap source manifest SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`. Cited A/B `LocalPlayer.java` SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` / `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`; cited A/B `LivingEntity.java` SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` / `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`.
- Exact mapped artifact identity: A `artifacts.sha256` manifest SHA-256 `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`, `client-mojmap.jar` SHA-256 `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`; B `artifacts.sha256` manifest SHA-256 `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72`, `client-mojmap.jar` SHA-256 `efbf38c89b396faae60cfe3bdb91671cb8d1ec3ccf2e27d3ffab4d261acd016d`.
- Bounded player path / guards: reachable non-passenger `LocalPlayer#aiStep` direct key-sprint route while fall-flying; A's food, impulse, water, item-use, blindness and not-already-sprinting gates permit the write, while B `canStartSprinting` adds `!isFallFlying`; paired inherited setter applies the same movement-speed modifier. This establishes sprint-state eligibility only, not a glide trajectory difference.
- Version boundary: source-confirmed A/B difference, first changed release unknown within (1.19.3, 1.19.4]. Reviewer: independent source reviewer `01a116ce-c937-7613-a49b-716e99582357`. Status: submitted for blind source review; acceptance pending. The snapshot does not close any other pair coverage or freeze the pair.
## Resume checkpoint

- Last completed slices: I-TICK-PLAYER-AISTEP-REMAINDER, I-TICK-LIVING-AISTEP-DISPATCH, I-TICK-LIVING-AISTEP-DAMAGE, the normal shift-dismount route within I-TICK-MOUNT-START, I-TICK-TRAVEL-FLUIDS and I-TICK-TRAVEL-FALL-FLYING; findings F-01 through F-04 have immutable snapshots or drafts; report remains active and partial.
- Next bounded slice: continue D-1 by inventorying remaining server/world dismount triggers and checking the scheduling boundary between client packet application and the next local tick, without asserting runtime arrival timing. Then continue `I-TICK-REMAINDER` from `KeyboardInput#tick`, `LocalPlayer#aiStep`, `Player#aiStep` and remaining `LivingEntity#travel` branches.
- Outstanding dependencies and owners: D-1 through D-3, source worker.
- Current assumptions requiring verification: complete behavior correspondence for all reachable travel branches; dismount packet routes; collision-provider/dependency inventory; block/fluid registrations and resources; modifiers/equipment; external player-facing inputs; direct excluded-state reads; remaining direct player knockback/impulse consumer and resistance-modifier inventory.

## Implementation reconciliation

- Reconciliation status: pending; source-only freeze has not occurred.
- Repository revision inspected: no implementation files inspected; blind-discovery constraint preserved.
- Finding -> implementation disposition/evidence: pending coordinator reassignment after source-only freeze.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: D-1 through D-3 remain source-only work.

## Independent source audit

- Reviewer: independent source reviewer `01a116ce-c937-7613-a49b-716e99582357`; revised F-03 and F-04 snapshot reviews pending. The original F-03 review requested a narrower post-dismount claim because B can restart sprint when its key remains held or the trigger timer is positive. Full-pair reviewer must differ from discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none.
- Concrete missed-slice routes (or `none found`): pending independent review.
- Misses routed to slice/finding IDs and owners: pending independent review.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: findings 4; in-progress 0; pending 7; compared-no-difference 13; not-applicable 3; blocked 0. Explicit pending count: 7. This is a partial checkpoint; full source-only coverage, freeze and independent audit are not claimed.
- Required inventory status and evidence: all seven required inventories remain pending overall; named sprint-air-speed, sprint-eligibility, auto-jump arithmetic and mounted sprint carry-over slices are closed as findings, while the full INV-TICK, INV-STATE and INV-COLLISION inventories and all remaining inventories remain open.
- Open dependencies: D-1, D-2, D-3.
- Unresolved gaps and limits: full tick and state inventory; additional dismount packet/timing routes; collision providers; block/fluid and data registrations; modifiers/equipment; external inputs; exclusions direct-read audit; independent reviewer.
- Evidence/hash/correspondence audit: readiness JSONs, source manifests, every listed source hash, required artifacts and diagnostics verified for both exact sources. Member-level hashes and ranges recorded for F-01 through F-04; revised F-03 and F-04 immutable finding snapshots are awaiting independent acceptance. Canonical completion checker recognizes active reports; its result is a structure/status check, not source evidence.
- Blind freeze: pending.
- Implementation reconciliation: pending.
- Independent audit: pending.
- Runtime validation: NOT AUTHORIZED; not performed. No tests/builds, clients, TAS, Gym, server or Docker launched.