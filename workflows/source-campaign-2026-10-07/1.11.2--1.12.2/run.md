# Discovery: 1.11.2 to 1.12.2

- Status: active
- Scope: direct client player movement; older A = 1.11.2; newer B = 1.12.2
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07
- Naming namespace: Ornithe Feather on both exact releases; release-specific mapping artifacts; same naming convention verified
- Source preparation owner / command / log / readiness marker: shared source owner (individual name not supplied); exact `--versions=1.11.2 --mappings=feather` and `--versions=1.12.2 --mappings=feather` runs; raw Gradle logs not retained; readiness marker at `build/movement-campaign-2026-10-07/ready/<version>/ornithe-feather.ready.json`
- Toolchain/decompiler/remapper versions/options: Java 25; Gradle 9.7.1; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; generic signatures, ASCII strings, synthetic removal, four-space indentation, available processor threads (minimum 1), Java runtime excluded, allowed prefixes `net/minecraft` and `com/mojang`
- Discovery author(s): Codex source worker in managed worktree
- Independent reviewer: pending coordinator assignment; must differ from discovery author
- Source-only declaration: no old mod implementation/code, no wiki or wiki-audit output; no tests/builds/game/TAS/Gym/server/Docker; runtime validation not authorized

## Artifact manifest

Shared source root `build/movement-campaign-2026-10-07/ready/` is physically in the primary checkout and read-only to this worker. Both exact readiness records, complete listed-source hashes and complete artifact hashes were verified.

### A — 1.11.2

- Requested/resolved: `1.11.2` / `1.11.2`; readiness `1.11.2/ornithe-feather.ready.json` confirms exact `versionId` and `versionMetadataId`.
- Source root: `1.11.2/ornithe-feather`.
- Original client SHA-256: `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`.
- CLI mode/mapping: `feather`; `net.ornithemc:feather-gen2:1.11.2+build.2`.
- Mapping merged jar SHA-256: `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`; Tiny mapping SHA-256: `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`.
- Remapped client SHA-256: `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`.
- Source manifest SHA-256: `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1,921/1,921 files present and matching.
- Artifact manifest SHA-256: `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`; 37/37 artifacts present and matching.
- Movement diagnostics SHA-256: `8bb3b1169a21958a6570fc304a0a95d9c51cc3a2101869bcbd453c3588925cdc`; exact release success and required sources confirmed; jump and relative-movement anchors listed.

### B — 1.12.2

- Requested/resolved: `1.12.2` / `1.12.2`; readiness `1.12.2/ornithe-feather.ready.json` confirms exact `versionId` and `versionMetadataId`.
- Source root: `1.12.2/ornithe-feather`.
- Original client SHA-256: `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`.
- CLI mode/mapping: `feather`; `net.ornithemc:feather-gen2:1.12.2+build.2`.
- Mapping merged jar SHA-256: `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`; Tiny mapping SHA-256: `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`.
- Remapped client SHA-256: `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b`.
- Source manifest SHA-256: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 2,050/2,050 files present and matching.
- Artifact manifest SHA-256: `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`; 38/38 artifacts present and matching.
- Movement diagnostics SHA-256: `1ae1a796ac7650bf218af02eb602e1b7f46df2950e57b14263c65d3b58dc71b3`; exact release success and required sources confirmed; jump and relative-movement anchors listed.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending
- Evidence inventory and finding IDs included at freeze: pending
- Blind source confirmation: prior discovery reports read as navigation; no old mod implementation/code or wiki-audit output opened; no wiki browsing
- Source/mapping hashes covered by freeze: pair readiness, source manifests, artifact manifests and cited source/resource hashes

## Correspondence and call order

Initial correspondence: A/B `LocalClientPlayerEntity -> ClientPlayerEntity -> PlayerEntity -> LivingEntity -> Entity` class roles resolve to same Feather names; member correspondence is checked per slice. `Minecraft` installs `KeyboardInput`, the only `Input` subclass in either tree. `LivingEntity.tick()` dispatches `this.mobTick()` (A line 1643; B line 1681). Dynamic path: local-player `mobTick()` -> `PlayerEntity.mobTick()` -> `LivingEntity.mobTick()`; the locally controlled branch invokes `serverTickAi()` before jump and relative movement (A lines 1776-1804; B 1820-1848). The local override copies input axes and jumping into movement fields. Travel branches, collision, shape/data, post-travel and external-writer correspondence remain open.

## Required source inventories

- `INV-TICK` input, player tick/call graph, pre-travel, travel branches and post-travel: status=pending; slice_ids=S1.1,S1.2,S1.3,S1.4,S1.5,S1.6,S1.7,S3.1,S3.2,S3.3,S3.4,S3.5,S3.6; evidence=pair manifests verified; bounded source slices remain open
- `INV-STATE` movement state writers/readers: status=pending; slice_ids=S1.2,S1.3,S1.4,S1.5,S1.6,S2.1,S2.2,S2.3,S2.4,S2.5,S2.6,S3.3,S3.4,S3.5,S3.6,S3.7,S4.3,S4.4,S7.1,S7.2,S7.3,S7.4; evidence=pair manifests verified; full producer/consumer inventory open
- `INV-COLLISION` player collision/query, shapes, callbacks, registrations and neighbor dependencies: status=pending; slice_ids=S1.4,S2.2,S2.3,S4.1,S4.2,S4.3,S4.4,S4.5,S4.6,S5.1,S5.2,S5.3,S5.4,S5.5; evidence=pair manifests verified; full shape/provider inventory open
- `INV-WORLD-MOVEMENT` block/fluid properties, subclasses, registries, data/tags and defaults: status=pending; slice_ids=S3.1,S3.5,S4.4,S4.6,S5.1,S5.2,S5.3,S5.4,S5.5,S6.3,S6.5,S6.6; evidence=pair manifests verified; resource inventory open
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and applications/removals/conditions: status=pending; slice_ids=S1.3,S1.4,S1.5,S2.1,S2.4,S2.5,S3.2,S3.4,S3.5,S3.7,S6.1,S6.2,S6.3,S6.4,S6.5,S6.6; evidence=pair manifests verified; modifier/data closure open
- `INV-EXTERNAL` player-only external inputs and client consumers: status=pending; slice_ids=S1.6,S1.7,S7.1,S7.2,S7.3,S7.4; evidence=pair manifests verified; external-writer inventory open
- `INV-EXCLUSIONS` health, regeneration, hunger, food, saturation, exhaustion, damage/combat and non-player movement: status=complete; evidence=movement predicates read food level only at LocalClientPlayerEntity.mobTick A lines 696-704 / B 718-726 (source hashes under S1.2); excluded health/food simulation appears in PlayerEntity.mobTick A lines 422-430 / B 418-426 (source hashes under S1.2); direct reads remain vanilla state, producers are excluded

## Coverage ledger

### Slice S1.1: keyboard movement input sampling and default bindings

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: key state to directional flags, signed axes, jump/sneak flags and sneak scaling; movement key defaults; no diagonal normalization in this sampler
- A evidence: `1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/Input.java::Input#tick()V` lines 15-16 and `getMovement()Lnet/minecraft/util/math/Vec2f;` lines 18-20, SHA-256 `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50`; `KeyboardInput.java::KeyboardInput#tick()V` lines 13-50, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156a871f2a7d64cc64f`; `GameOptions.java` lines 109-115 SHA-256 `3a28b6a6540a134fb24e2d3517465020ee8a2d095db5a754b96176abc0ae2ec1`; `Minecraft.java` lines 1894-1927 SHA-256 `3ca93038adf4cea512c21ae86d653376e963396640a02eb50cb42a67302f6236`
- B evidence: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/Input.java::Input#tick()V` lines 15-16 and `getMovement()Lnet/minecraft/util/math/Vec2f;` lines 18-20, SHA-256 `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50`; `KeyboardInput.java::KeyboardInput#tick()V` lines 13-50, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156a871f2a7d64cc64f`; `GameOptions.java` lines 117-123 SHA-256 `301be9703fdbd4169ef614ca41d2f7cb8508ebc76f89ec032c911609e5f43848`; `Minecraft.java` lines 1953-1991 SHA-256 `8af833a5d03a6f6516a7eff0ab463a96bfec64aac31fc80656f37d193b1c1310`
- State producers/writers -> consumers/readers: key bindings write axes and flags through `KeyboardInput.tick`; local player tick and `serverTickAi` consume them; only `KeyboardInput` extends `Input` in each tree
- Parent slices / dependencies / closure evidence: downstream consumers S1.2-S1.6 and S3.2-S3.4 remain separately open; this no-difference claim is limited to the sampler/provider/defaults
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for identical pressed keys and sneaking state, both releases write the same signed digital axes and flags; sneak scaling preserves `(float)(axis * 0.3)`. All default movement key IDs/codes match. `Input.java` and `KeyboardInput.java` are byte-identical; both clients install the same provider.
- Finding IDs or checked absence/replacement path: none; exact members present and byte-identical

### Slice S1.2: Local player tick ordering, previous/current input and flags, superclass/tick/travel order

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalClientPlayerEntity.tick()` only runs under `world.isChunkLoaded(BlockPos(x, 0, z))`, then calls its superclass before mount/ordinary movement packet handling. Dynamic tick path is `LivingEntity.tick()` -> virtual `mobTick()` -> local-player pre-travel logic -> `PlayerEntity.mobTick()` -> `LivingEntity.mobTick()`; the latter updates prior velocity/lerp state, locally controlled AI/input fields, jump, travel dispatch and push in that order. Local player `serverTickAi()` copies sampled sideways/forward/jump values when `isCamera()`. The prior jump/sneak/forward booleans are captured before `input.tick()`; auto-jump then can overwrite current jump before superclass jump processing. B-only tutorial and inventory-close branches are separately bounded in S1.2a/S1.2b. B changes the final relative-movement call to pass `verticalSpeed`; its player-specific effect remains open under S3.2/DEP-RELATIVE-MOVE.
- A evidence: `1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::tick()V` lines 159-172, `serverTickAi()V` lines 614-625, `mobTick()V` lines 632-790, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; `1.11.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java::mobTick()V` lines 417-440, SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`; `1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java::tick()V` lines 1581-1620 and `mobTick()V` lines 1737-1815, SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`
- B evidence: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::tick()V` lines 165-178, `serverTickAi()V` lines 631-642, `mobTick()V` lines 649-812, SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java::mobTick()V` lines 413-436, SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a2d7c9d045a4a12b409895c220b5d91fe1e`; `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java::tick()V` lines 1619-1658 and `mobTick()V` lines 1781-1855, SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`
- State producers/writers -> consumers/readers: `Input.tick()` writes current direction/jump/sneak flags; local `mobTick()` captures prior flags and may scale/override current axes/jump; `serverTickAi()` transfers current axes/jump into `LivingEntity.sidewaysSpeed`, `forwardSpeed`, and `jumping`; `LivingEntity.mobTick()` consumes these for jump then travel and updates the corresponding cooldown and velocity fields. `PlayerEntity.mobTick()` decrements the double-jump timer and calls its superclass before setting air speed. Food/hunger production is excluded; direct food reads in the local sprint gate remain in S1.3.
- Parent slices / dependencies / closure evidence: S1.1 input producer; S1.2a/S1.2b isolate B-only callbacks with no movement-state writes; S1.3-S1.7 and S2-S7 trace each consumer/provider. `DEP-RELATIVE-MOVE` (S3.2) must establish player `verticalSpeed` writers/default and the exact 3D `updateVelocity` consequences before this call-order row closes.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): The compared local tick/superclass order and captured/current input timing match around the two separately tracked B-only callbacks. `ClientPlayerEntity` adds no `tick()` or `mobTick()` override in either tree; `PlayerEntity.tick()` calls `LivingEntity.tick()`, whose virtual `mobTick()` dispatches to the local player; `PlayerEntity.mobTick()` calls `LivingEntity.mobTick()` after its player timer/inventory updates. The different `moveRelative` argument list is an invocation/signature change, not a tick-order change, and its player effect remains routed to S3.2/DEP-RELATIVE-MOVE.
- Finding IDs or checked absence/replacement path: no confirmed finding yet; B 3D relative-movement dispatch routed to S3.2/DEP-RELATIVE-MOVE

### Slice S1.2a: tutorial callback consuming sampled input

- Inventory ID(s): INV-TICK, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: B-only callback after keyboard sampling; follow every override and all reads/writes of its input parameter
- A evidence: `1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 632-790, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` (checked absence: no tutorial callback in full method)
- B evidence: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 694-700 SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; `Tutorial.java::onPlayerInput(Input)V` lines 25-29 SHA-256 `c5281a3f8000275b1e2387f97e88d6e0af409e3cedbff8c1668a775d3a780945`; default `TutorialStep.onPlayerInput(Input)V` lines 18-19 SHA-256 `3150f62d0b96c83fecb231dabdecd0c618f6970c8f8ff093f27547ecb0b866fa`; sole override `MovementTutorialStep.onPlayerInput(Input)V` lines 105-109 SHA-256 `44f8769ebc81ffe6f368b085987e7c8879ef5db86388741310979bc94a80de17`
- State producers/writers -> consumers/readers: keyboard writes `Input`; tutorial callback reads direction/jump flags and writes tutorial-only `moved`, consumed/reset by tutorial tick's movement counter
- Parent slices / dependencies / closure evidence: S1.1, S1.2; full tutorial package search found no other input override; no player movement-state write
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): the callback is reachable each B local-player mob tick but only advances tutorial progress; it does not mutate `Input` or player movement state
- Finding IDs or checked absence/replacement path: no movement finding; B-only tutorial route fully traced

### Slice S1.2b: portal-triggered inventory UI closure

- Inventory ID(s): INV-TICK, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: B portal-time branch closes a non-pausing inventory menu; trace the player method and direct menu-close writes
- A evidence: `1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 632-790, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` (checked absence: no inventory-screen close branch)
- B evidence: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 655-663 and `closeMenu()V`/`doCloseMenu()V` lines 281-290, SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`
- State producers/writers -> consumers/readers: screen selects close call; helper writes cursor/menu/screen state and sends a menu packet; it writes no position, velocity, pose, input, movement flags or timers
- Parent slices / dependencies / closure evidence: S1.2; A/B close-helper bodies inspected
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): the B-only behavior changes inventory UI lifecycle on portal entry and has no player movement-state read/write path
- Finding IDs or checked absence/replacement path: no movement finding; B-only branch and close-helper path fully traced

### Slice S1.3: Sprint start/stop, timers, conditions and writes reachable from client input

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Local double-tap sprint requires grounded, prior captured sneak false and forward below `0.8F`, current forward at least `0.8F`, not already sprinting, food level above 6 or flight ability, not using an item, and no Blindness; held sprint key uses the same forward/resource/item/effect gates without the grounded/double-tap branch. Stop gate is current forward below `0.8F`, horizontal collision, or loss of the same food/flight allowance. Per-tick `doubleTapSprintTime` decrement, item-use reset, double-tap value 7, sprint flag and timer reset are included.
- A evidence: `1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 632-635, 678-681, 695-724 and `setSprinting(Z)V` lines 427-430, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; `LivingEntity.java::setSprinting(Z)V` lines 1256-1267, SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`; `Entity.java::isSprinting()Z`/`setSprinting(Z)V` lines 1751-1759, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`
- B evidence: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 649-652, 700-703, 719-745 and `setSprinting(Z)V` lines 444-447, SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; `LivingEntity.java::setSprinting(Z)V` lines 1295-1306, SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; `Entity.java::isSprinting()Z`/`setSprinting(Z)V` lines 1777-1785, SHA-256 `80f091bf32166c88cf8b8bd31caf72d84fa16224410733c7d2a0f00563f294a0a`
- State producers/writers -> consumers/readers: local gates read captured/current `Input`, ground/horizontal collision flags, item-use state, Blindness and hunger manager food level; `LocalClientPlayerEntity.setSprinting` delegates to `LivingEntity.setSprinting` for movement-speed modifier maintenance and `Entity.setSprinting` for flag 3, then resets `sprintTimer`. Sprint state is consumed by travel speed, air speed, and outgoing sprint action; modifier/effect/equipment sources remain in S2/S3/S6. Health/hunger producer systems are excluded; direct vanilla food-level read remains only as this movement predicate input.
- Parent slices / dependencies / closure evidence: S1.1 input; S1.2 local tick ordering; S2.5 Blindness/food predicate; S3.2 travel and speed consumers; S6.1 speed attribute; S7.3 external sprint flag updates. A/B gate order, thresholds, timer writes, setter chain and flags compare identically.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): The exact local predicates, short-circuit order, thresholds, values and state writes match between these releases. The base entity uses the same sprint flag bit; the living setter maintains the same movement-speed modifier, and the local override resets the same sprint timer. Predicates' upstream values and downstream travel effects remain separately inventoried.
- Finding IDs or checked absence/replacement path: none assigned

### Slice S1.4: Jump input, jump state/cooldown, auto-jump probes and movement dispatch

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `ticksToNextAutojump` decrements before travel and can set current input jump; the living jump resolver prioritizes water, then lava, then grounded jump when cooldown is zero, resets cooldown when not jumping, and sets ten ticks after ground jump. Auto-jump is called after local movement resolution; it requires the option enabled, cooldown zero, grounded, not sneaking/riding and nonzero sampled input, then probes support/headroom and forward collision geometry and writes a one-tick auto-jump delay. Player jump override adds sprint impulse after superclass jump. Exact bodies are identical on A/B; shape and jump-strength providers are separate inventories.
- A evidence: `1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 674-697 and `move(MoverType,DDD)V` lines 824-829, `autoJump(F,F)V` lines 835-935, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; `1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java::mobTick()V` lines 1738-1794 and `jump()V`/`jumpInWater()V`/`jumpInLava()V` lines 1361-1382, SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`; `PlayerEntity.jump()V` lines 1376-1384, SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`
- B evidence: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 696-721 and `move(MoverType,DDD)V` lines 846-851, `autoJump(F,F)V` lines 857-957, SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java::mobTick()V` lines 1782-1838 and `jump()V`/`jumpInWater()V`/`jumpInLava()V` lines 1398-1419, SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; `PlayerEntity.jump()V` lines 1375-1383, SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a2d7c9d045a4a12b409895c220b5d91fe1e`
- State producers/writers -> consumers/readers: local input/current jump and previous-jump edge feed flight/elytra and ride timers in S1.5/S1.6; auto-jump writes `ticksToNextAutojump`, consumed on the next local tick; `LivingEntity.jumpingCooldown` guards ground jump; jump writers modify velocity and may read jump-strength attributes/effects in S3.4/S6.2. Auto-jump reads dimensions, collision shapes and Jump Boost; those producers are inventoried in S2/S4/S6.
- Parent slices / dependencies / closure evidence: S1.1 input; S1.2 tick order; S1.5 flight edge; S1.6 mounted jump; S3.4 jump strength/impulse; S4.2/S4.5 auto-jump collision queries; S6.2 Jump Boost. Exact A/B autoJump, living jump resolver, `jump`, water/lava jumps and player jump method texts compare identical; provider and shape results remain separately open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): The direct input edge handling, cooldown values/order, ground/water/lava dispatch, player sprint-jump override and auto-jump probe formulas are text-identical for reachable local players. This disposition covers these methods only; the values returned by collision shapes, movement attributes and Jump Boost are checked in their linked slices.
- Finding IDs or checked absence/replacement path: none assigned

### Slice S1.5: Flight toggles and local flying input/speed gates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: When `canFly`, spectator mode enables flight and syncs abilities; otherwise a rising jump edge, excluding auto-jump, starts a seven-tick double-press timer or toggles `flying`, then syncs and clears the timer. While flying and camera-controlled, sneak divides horizontal inputs by `0.3F` and subtracts three times fly speed from vertical velocity; jump adds the same amount. Landing clears flying for non-spectators and syncs. Elytra start-flying edge handling and fall-flying state are bounded in S1.4/S3.6.
- A evidence: `1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 725-793 and `isCamera()Z` lines 627-629, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; ability sync invoked via `syncAbilities()V` at line 318 (body/packet writer deferred to S2.1/S7.3).
- B evidence: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 747-815 and `isCamera()Z` lines 644-646, SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; ability sync invoked via `syncAbilities()V` at line 324 (body/packet writer deferred to S2.1/S7.3).
- State producers/writers -> consumers/readers: input jump edge and `ticksToNextAutojump` gate local ability toggles; `PlayerAbilities.canFly`, `flying` and fly speed drive local input/velocity updates; `syncAbilities` sends changed state outward. `LivingEntity.mobTick` and travel consume `abilities.flying`, `speedInAir` and velocity; the source/default/update paths for abilities and packet consumers remain open.
- Parent slices / dependencies / closure evidence: S1.1 input; S1.2 tick/auto-jump timing; S1.4 jump edge; S2.1 ability defaults, updates and resets; S3.1/S3.2/S3.6 travel and flight; S7.3 ability packet boundary. Exact local branches and operation order compare identically; upstream ability provenance and downstream branches are separately open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Both versions use the same spectator, jump-edge, camera, sneak, vertical-speed, landing and sync guards, with the same timer value and arithmetic order. This is a local toggle/input disposition; ability defaults, server updates and fall-flying math have separate coverage rows.
- Finding IDs or checked absence/replacement path: none assigned

### Slice S1.6: Riding input gates and player-specific mounted movement hooks

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Rideable-mob jump charge timers update only under `isRidingRideableMob()`; a held/released jump edge sends floored riding progress as jump strength and starts the riding-jump action; non-rideable path clears `horseJumpSize`. Per-tick rideable predicate/progress helper and `startRidingJump` packet body were read. `rideTick()` passes the four directional boat-input booleans to `BoatEntity.setInput` and sets the player rowing flag if any is true. Boat's independent movement is out of scope; player-side input transfer is included.
- A evidence: `1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 762-797, `startRidingJump()V` lines 327-334, `isRidingRideableMob()Z` lines 516-519, `getRidingJumpProgress()F` lines 521-523, `rideTick()V` lines 798-806, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; `JumpingMount.setJumpStrength(I)V` line 4, SHA-256 `cfa239c4ff1bce667c2850090cafbb4e74ae877a4c2aa4baefc235b2d8950efc`; `BoatEntity.setInput(ZZZZ)V` lines 699-704, SHA-256 `0156b9cf2d028f53f2bc4c2ab6610d602e656e6f1dd33525b316b86588057f4d`
- B evidence: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 784-819, `startRidingJump()V` lines 333-340, `isRidingRideableMob()Z` lines 533-536, `getRidingJumpProgress()F` lines 538-540, `rideTick()V` lines 820-828, SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; `JumpingMount.setJumpStrength(I)V` line 4, SHA-256 `cfa239c4ff1bce667c2850090cafbb4e74ae877a4c2aa4baefc235b2d8950efc`; `BoatEntity.setInput(ZZZZ)V` lines 731-736, SHA-256 `9fb612de08de935a71f299410dc201abd7e9b522f60b0c9e9f982d3e06f59747`
- State producers/writers -> consumers/readers: input edges/timers write mount jump strength and send a player action packet; `JumpingMount` receives the strength; boat directional booleans are stored by `BoatEntity.setInput`, while `rowing` is a local player status. Packet consumption and dismount/position reconciliation remain in S7.2/S7.3; boat's own movement simulation is excluded as non-player movement.
- Parent slices / dependencies / closure evidence: S1.1 input; S1.2 tick order; S7.2 mount/dismount transition; S7.3 client packet/authority boundary. Local player mount guards, timer math, helper methods, boat input transfer and `JumpingMount` interface match on A/B. Mount-side player position/packet consumers remain separately open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): For rideable-mob mounts and boats, the player-side jump charge/reset transitions and four-direction input transfer are text-identical, including timer ordering and float-to-int conversion. This does not disposition mount movement simulation or server-authoritative player position updates.
- Finding IDs or checked absence/replacement path: none assigned

### Slice S1.7: Incoming client correction/velocity packet consumers that directly write local player movement state

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `handleEntityVelocity` looks up the packet entity and, when present, supplies three `/8000.0` components to `lerpVelocity`; if the ID is the local player, the velocity interpolation path is player-reachable. `handlePlayerMove` resolves relative X/Y/Z and yaw/pitch arguments, zeroes each non-relative velocity component, calls `updatePositionAndAngles`, then acknowledges and sends an absolute position. `handleExplosion` adds packet impulse components to local player velocity after block damage processing (damage simulation excluded). The three bounded handler bodies compare identical; exhaustive packet/state writer inventory remains open under S7.3.
- A evidence: `1.11.2/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java::handleEntityVelocity(EntityVelocityS2CPacket)V` lines 451-457, `handlePlayerMove(PlayerMoveS2CPacket)V` lines 565-614, `handleExplosion(ExplosionS2CPacket)V` lines 919-928, `handleEntityTeleport(EntityTeleportS2CPacket)V` lines 495-514, `handlePlayerRespawn(PlayerRespawnS2CPacket)V` lines 896-915, `handleEntityPassengers(EntityPassengersS2CPacket)V` lines 824-844, SHA-256 `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175`; `Entity.lerpVelocity(DDD)V` lines 1703-1706, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`
- B evidence: `1.12.2/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java::handleEntityVelocity(EntityVelocityS2CPacket)V` lines 464-470, `handlePlayerMove(PlayerMoveS2CPacket)V` lines 578-627, `handleExplosion(ExplosionS2CPacket)V` lines 928-937, `handleEntityTeleport(EntityTeleportS2CPacket)V` lines 508-527, `handlePlayerRespawn(PlayerRespawnS2CPacket)V` lines 905-924, `handleEntityPassengers(EntityPassengersS2CPacket)V` lines 833-853, SHA-256 `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c`; `Entity.lerpVelocity(DDD)V` lines 1729-1732, SHA-256 `80f091bf32166c88cf8b8bd31caf72d84fa16224410733c7d2a0f00563f294a0a`
- State producers/writers -> consumers/readers: network packet dispatch writes relative/local position and velocity; `Entity.lerpVelocity` feeds later velocity interpolation/travel; `handlePlayerMove` resets selected velocity axes and relocates the player; explosion packet components add directly to velocity. Teleport, respawn, ability and mount packet routes still require exhaustive classification.
- Parent slices / dependencies / closure evidence: S1.2 player tick consumes resulting position/velocity; S3.3 velocity/drag/cutoffs; S7.1 external velocity sources; S7.2 mount transitions; S7.3 packet authority/reconciliation. These three handler bodies are text-identical A/B; other client packet writers are not yet closed.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The compared `handleEntityVelocity`, `handlePlayerMove`, `handleExplosion`, `handleEntityTeleport`, `handleEntityPassengers`, and `Entity.lerpVelocity` bodies are text-identical. Respawn method behavior is also identical except the B download-screen constructor now receives the handler. No movement difference is established in these routes. The slice remains open until the complete incoming packet writer scan identifies and dispositions all local player position/velocity writes and interpolation callers.
- Finding IDs or checked absence/replacement path: none assigned

### Slice S2.1: Movement-relevant ability defaults/updates and player initialization/reset timing

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Movement-relevant ability defaults/updates and player initialization/reset timing; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S2.2: Pose selection, dimensions, eye height, resize collision gate and all pose/dimension writers

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Pose selection, dimensions, eye height, resize collision gate and all pose/dimension writers; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S2.3: Swimming/crawling/fall-flying pose transitions and their movement gates

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Swimming/crawling/fall-flying pose transitions and their movement gates; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S2.4: Active item use and direct movement slowdown state

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Active item use and direct movement slowdown state; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S2.5: Sprint eligibility consumers, including blindness and mounted/item gates; hunger/food systems themselves are excluded

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Sprint eligibility consumers, including blindness and mounted/item gates; hunger/food systems themselves are excluded; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S2.6: Edge sneaking, stored air speed and other local movement-state fields not covered above

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Edge sneaking, stored air speed and other local movement-state fields not covered above; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S3.1: Travel dispatch and branch guards; ground, air, water, lava, climb and fall-flying paths

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Travel dispatch and branch guards; ground, air, water, lava, climb and fall-flying paths; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S3.2: Relative input acceleration, normalization, friction and sprint multipliers

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Relative input acceleration, normalization, friction and sprint multipliers; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S3.3: Gravity, drag, negligible-velocity cutoffs, clamps and post-travel velocity/flag updates

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Gravity, drag, negligible-velocity cutoffs, clamps and post-travel velocity/flag updates; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S3.4: Ground jump impulse, sprint-jump impulse, jump strength and jump providers

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Ground jump impulse, sprint-jump impulse, jump strength and jump providers; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S3.5: Climbing, levitation, slow-falling and other movement-effect consumers

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Climbing, levitation, slow-falling and other movement-effect consumers; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S3.6: Fall-flying math, vectors, trigonometry, branch thresholds and state updates

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Fall-flying math, vectors, trigonometry, branch thresholds and state updates; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S3.7: Movement attributes/helpers called by travel and direct player-path modifiers

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Movement attributes/helpers called by travel and direct player-path modifiers; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S4.1: Player-reachable `Entity.move` call order, bounding-box/position updates and requested/resolved deltas

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Player-reachable `Entity.move` call order, bounding-box/position updates and requested/resolved deltas; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S4.2: Axis resolution order, collision candidate iteration/tie-breaking and step-up alternatives

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Axis resolution order, collision candidate iteration/tie-breaking and step-up alternatives; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S4.3: Edge probes, support lookup, grounding and collision-flag writes

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Edge probes, support lookup, grounding and collision-flag writes; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S4.4: Velocity cancellation/restitution, fall-distance changes and collision callbacks

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Velocity cancellation/restitution, fall-distance changes and collision callbacks; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S4.5: Shape/AABB calculations and context-sensitive query timing on the player path

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Shape/AABB calculations and context-sensitive query timing on the player path; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S4.6: Fluid contact/push checks and repeated movement within a tick

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Fluid contact/push checks and repeated movement within a tick; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S5.1: Registration/default friction, speed and jump factors for blocks reachable in supported historical maps

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Registration/default friction, speed and jump factors for blocks reachable in supported historical maps; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S5.2: Landing/bounce, ice/soul-sand slowdown, web and other contact-factor classes and callbacks

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Landing/bounce, ice/soul-sand slowdown, web and other contact-factor classes and callbacks; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S5.3: Climbable and fluid block/state callbacks, flow-vector and fluid-height inputs

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Climbable and fluid block/state callbacks, flow-vector and fluid-height inputs; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S5.4: Collision shapes/support behavior for historical partial blocks, including state and neighbor dependencies

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Collision shapes/support behavior for historical partial blocks, including state and neighbor dependencies; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S5.5: Exhaustive movement-relevant subclass override and registration inventory; modern-only entries classified without inventing old behavior

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Exhaustive movement-relevant subclass override and registration inventory; modern-only entries classified without inventing old behavior; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S6.1: Movement attribute definitions, aggregation/operation ordering, defaults and player consumers

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Movement attribute definitions, aggregation/operation ordering, defaults and player consumers; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S6.2: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace and Blindness registration/application/removal and direct movement consumers

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace and Blindness registration/application/removal and direct movement consumers; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S6.3: Depth Strider, Frost Walker and all registered movement-relevant enchantment formulas, conditions and consumers

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Depth Strider, Frost Walker and all registered movement-relevant enchantment formulas, conditions and consumers; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S6.4: Equipment slots, item-use movement effects, relevant defaults and applicability predicates

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Equipment slots, item-use movement effects, relevant defaults and applicability predicates; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S6.5: Tag/data/resource dependencies and synchronized/server-supplied movement values with provenance

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Tag/data/resource dependencies and synchronized/server-supplied movement values with provenance; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S6.6: Explicit completeness check for all movement-state consumers and data-driven definitions found from registry scans

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Explicit completeness check for all movement-state consumers and data-driven definitions found from registry scans; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S7.1: Player knockback, explosions, piston displacement, launch-item and other external velocity writers

- Inventory ID(s): INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Player knockback, explosions, piston displacement, launch-item and other external velocity writers; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S7.2: Mount/dismount transitions and player-specific mounted state/position writes

- Inventory ID(s): INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Mount/dismount transitions and player-specific mounted state/position writes; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S7.3: Client packet/authority boundaries and reconciliation of externally supplied velocity/position

- Inventory ID(s): INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Client packet/authority boundaries and reconciliation of externally supplied velocity/position; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S7.4: Final scan of reachable player movement-state writers, callbacks, overrides, registries and newly discovered dependencies

- Inventory ID(s): INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Final scan of reachable player movement-state writers, callbacks, overrides, registries and newly discovered dependencies; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned

### Slice S7.5: Cross-mechanic interactions and final dependency closure/revisit of affected unchanged callers

- Inventory ID(s): INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Cross-mechanic interactions and final dependency closure/revisit of affected unchanged callers; exact local guards and enclosing call order recorded per bounded behavior
- A evidence: pending exact A member/body range and SHA-256 from verified source root
- B evidence: pending exact B member/body range and SHA-256 from verified source root
- State producers/writers -> consumers/readers: pending exact source writer/consumer closure
- Parent slices / dependencies / closure evidence: parent/dependency links pending source inventory
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence inferred from prior reports
- Finding IDs or checked absence/replacement path: none assigned
## Dependency queue and blockers

- `SRC-PAIR`: resolved; exact readiness records, release IDs/namespaces and all source/artifact manifest hashes verified.
- `DEP-TICK-CALLGRAPH`: open; finish local pre-travel, each travel branch and post-travel; owner discovery author.
- `DEP-COLLISION-SHAPES`: open; enumerate player-reachable shape/callback providers, registrations and neighbor dependencies; owner discovery author.
- `DEP-MODIFIER-DATA`: open; trace movement modifiers/equipment to application, resources and consumers; owner discovery author.
- `DEP-EXTERNAL-WRITERS`: open; enumerate player-only velocity/position packet, push, piston, launch and mount paths; owner discovery author.
- `DEP-OLD-CANDIDATES`: open re-verification only: prior report glide look-vector/yaw, bed landing rebound, server dismount query geometry; none accepted as current finding.
- `DEP-DIAGNOSTICS`: readiness diagnostic hashes verified; inspect every cited method body; raw Gradle logs not retained; request exact mapped-bytecode/source assistance through commentary if a body is damaged or ambiguous.
- `DEP-RELATIVE-MOVE` (origin S1.2, owner discovery author): resolve all player-path writers/defaults of B-only `LivingEntity.verticalSpeed`, compare A 2D versus B 3D `moveRelative`/`Entity.updateVelocity` argument order and float operation order, then classify any zero-valued vertical write. Current source scan finds the declaration and only `MobEntity.setVerticalSpeed` writer; player inheritance path is not a `MobEntity`.
- Open dependencies: DEP-TICK-CALLGRAPH, DEP-COLLISION-SHAPES, DEP-MODIFIER-DATA, DEP-EXTERNAL-WRITERS, DEP-OLD-CANDIDATES, DEP-DIAGNOSTICS, DEP-RELATIVE-MOVE

## Finding index

No findings in the fresh campaign yet. Prior pair claims remain navigation candidates only under `DEP-OLD-CANDIDATES`. Source-only declaration: no implementation, wiki or wiki-audit evidence opened.

## Resume checkpoint

- Last completed slice: S1.6; S1.2a and S1.2b have bounded not-applicable dispositions.
- Next bounded slice and exact files/members/body ranges to open: finish S1.7 by checking A/B `handleEntityTeleport`, respawn, passenger/dismount and `handlePlayerAbilities` packet consumers plus `Entity.lerpVelocity`; then continue S2.1 ability defaults and packet updates.
- Outstanding dependencies and owners: listed above; discovery author owns source inventory; shared source owner owns generation; reviewer assignment pending coordinator.
- Current assumptions requiring verification: all prior findings and no-difference claims remain unaccepted; every cited body still requires direct review despite ready tree hashes.

## Implementation reconciliation

- Reconciliation status: pending (source-only worker; explicit parent role change required)
- Repository revision inspected: none; mod implementation/code not opened
- Finding -> implementation disposition/evidence: pending after source freeze and role change
- Existing implementation without a frozen source finding: not inspected
- Coverage gaps routed back to discovery slices: none yet; source discovery active

## Independent source audit

- Reviewer: pending coordinator assignment; must differ from discovery author
- Status: pending
- Inventories and call-chain ranges re-walked: none yet
- Concrete missed-slice routes (or `none found`): pending independent audit
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: 35 pending; 1 in-progress; 6 compared-no-difference; 2 not-applicable; 0 findings; 0 blocked.
- Required inventory status/evidence: INV-EXCLUSIONS complete; all other inventories pending with mapped slice IDs.
- Open dependencies: DEP-TICK-CALLGRAPH, DEP-COLLISION-SHAPES, DEP-MODIFIER-DATA, DEP-EXTERNAL-WRITERS, DEP-OLD-CANDIDATES, DEP-DIAGNOSTICS, DEP-RELATIVE-MOVE
- Unresolved gaps: all stages beyond bounded keyboard input and UI/tutor dispositions; exact methods, state producers/consumers, shapes/registries/resources, external writers and historical candidates remain open.
- Evidence/hash/correspondence audit: pair source/artifact manifests fully verified; S1.1 source hashes recorded; remaining source evidence pending.
- Blind freeze: pending
- Implementation reconciliation: pending and deferred
- Independent audit: pending reviewer assignment
- Runtime validation: not performed (separate workflow; not authorized).

Run folder contents are limited to `run.md` and optional `findings/*.md`; the completion checker validates schema/status only and cannot establish source truth.
