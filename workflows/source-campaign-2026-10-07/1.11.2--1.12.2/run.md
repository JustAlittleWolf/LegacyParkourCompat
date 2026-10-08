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

Shared source root `build/movement-campaign-2026-10-07/ready/` is physically in the primary checkout and read-only to this worker. Fresh read-only verification confirmed both exact readiness records, all listed source-file hashes and all raw-input artifact hashes. The original derived mapped JARs were replaced during a reproducibility rerun and are unavailable; the source owner published immutable revision `feather-r1-2026-10-07` snapshots. Their hashes and verification records were rechecked by this worker, and independent ops audit passed the revised Feather bundles. The new snapshots do not prove identity with the unavailable originals or that the changes are metadata-only; preserve that limitation and do not rewrite markers or waive original-hash mismatches.

### A — 1.11.2

- Requested/resolved: `1.11.2` / `1.11.2`; readiness `1.11.2/ornithe-feather.ready.json` confirms exact `versionId` and `versionMetadataId`.
- Source root: `1.11.2/ornithe-feather`.
- Original client SHA-256: `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`.
- CLI mode/mapping: `feather`; `net.ornithemc:feather-gen2:1.11.2+build.2`.
- Mapping merged jar SHA-256: `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`; Tiny mapping SHA-256: `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`.
- Original remapped client SHA-256 (unavailable; recorded in the unchanged original artifact manifest): `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`.
- Revised mapped-artifact snapshot `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar` SHA-256: `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`; revision record and checksum file agree; snapshot is read-only.
- Source manifest SHA-256: `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1,921/1,921 files present and matching.
- Artifact manifest SHA-256: `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f` (unchanged original record; its derived-JAR entry names the unavailable original hash). Revised snapshot provenance is recorded under `feather-r1-2026-10-07`; independent ops audit passed.
- Movement diagnostics SHA-256: `8bb3b1169a21958a6570fc304a0a95d9c51cc3a2101869bcbd453c3588925cdc`; exact release success and required sources confirmed; jump and relative-movement anchors listed.

### B — 1.12.2

- Requested/resolved: `1.12.2` / `1.12.2`; readiness `1.12.2/ornithe-feather.ready.json` confirms exact `versionId` and `versionMetadataId`.
- Source root: `1.12.2/ornithe-feather`.
- Original client SHA-256: `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`.
- CLI mode/mapping: `feather`; `net.ornithemc:feather-gen2:1.12.2+build.2`.
- Mapping merged jar SHA-256: `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`; Tiny mapping SHA-256: `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`.
- Original remapped client SHA-256 (unavailable; recorded in the unchanged original artifact manifest): `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b`.
- Revised mapped-artifact snapshot `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar` SHA-256: `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`; revision record and checksum file agree; snapshot is read-only.
- Source manifest SHA-256: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 2,050/2,050 files present and matching.
- Artifact manifest SHA-256: `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` (unchanged original record; its derived-JAR entry names the unavailable original hash). Revised snapshot provenance is recorded under `feather-r1-2026-10-07`; independent ops audit passed.
- Movement diagnostics SHA-256: `1ae1a796ac7650bf218af02eb602e1b7f46df2950e57b14263c65d3b58dc71b3`; exact release success and required sources confirmed; jump and relative-movement anchors listed.

### Integrity update

- Fresh read-only verification at 2026-10-07 15:28 UTC confirmed the ready markers resolve exact IDs `1.11.2` and `1.12.2`, namespace `ornithe-feather`; the source-manifest hashes match the ready markers and all 1,921/2,050 listed source files match their entries.
- Rechecked 36/37 raw-input artifact entries against the original manifests; every entry matched. For each version, the revised read-only JAR snapshot hash matches both `artifact.sha256` and `revision.json`; the referenced verification-log hash also matches. Revision ID: `feather-r1-2026-10-07`.
- The original derived mapped JARs are unavailable. The revised snapshots are new derived artifacts and do not prove identity with those originals or that the hash change is metadata-only. Ready markers and original manifests remain unchanged.
- Independent ops audit passed the revised Feather bundles on 2026-10-07; this worker separately verified both exact pair endpoints as detailed above. `DEP-ARTIFACT-INTEGRITY` is closed for source-provenance use of revision `feather-r1-2026-10-07`. The original derived JARs remain unavailable and equivalence is unproven. Do not rewrite ready markers or waive original-hash mismatches.

### Artifact evidence identities

- Evidence artifact `EA-FEATHER-R1-1.11.2`: revision `feather-r1-2026-10-07`; immutable snapshot `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`; checksum sidecar `.../artifact.sha256` records that artifact hash (sidecar SHA-256 `40131c4f05a8229d384ee7cb3680ec2c45eb66b7a10a25544dd7c9b71c7cc3eb`); evidence manifest `.../revision.json`, SHA-256 `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`; original artifact manifest `build/movement-campaign-2026-10-07/ready/1.11.2/artifacts.sha256`, SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`; original derived JAR unavailable, expected SHA-256 `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`; revision records `sourceTreeIdentical=true` against source manifest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`, `rawInputsIdentical=true` against the original raw-input manifest, and no input differences; revised-to-original JAR equivalence unverified. Provenance limitation: a new derived JAR snapshot does not establish identity with the unavailable original or prove a metadata-only change. The worker verified the cited source files, snapshot bytes, sidecar and revision record; independent ops audit passed.
- Evidence artifact `EA-FEATHER-R1-1.12.2`: revision `feather-r1-2026-10-07`; immutable snapshot `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`; checksum sidecar `.../artifact.sha256` records that artifact hash (sidecar SHA-256 `162170b94be0fa6393a515ea3beed10210b2c86a006366cf9d351eba136bae2d`); evidence manifest `.../revision.json`, SHA-256 `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`; original artifact manifest `build/movement-campaign-2026-10-07/ready/1.12.2/artifacts.sha256`, SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`; original derived JAR unavailable, expected SHA-256 `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b`; revision records `sourceTreeIdentical=true` against source manifest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, `rawInputsIdentical=true` against the original raw-input manifest, and no input differences; revised-to-original JAR equivalence unverified. Provenance limitation: a new derived JAR snapshot does not establish identity with the unavailable original or prove a metadata-only change. The worker verified the cited source files, snapshot bytes, sidecar and revision record; independent ops audit passed.

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
- `INV-STATE` movement state writers/readers: status=pending; slice_ids=S1.2,S1.3,S1.4,S1.5,S1.6,S2.1,S2.2,S2.2a,S2.3,S2.3a,S2.4,S2.5,S2.6,S3.3,S3.4,S3.5,S3.6,S3.7,S4.3,S4.4,S4.4a,S7.1,S7.2,S7.3,S7.4; evidence=pair manifests verified; full producer/consumer inventory open
- `INV-COLLISION` player collision/query, shapes, callbacks, registrations and neighbor dependencies: status=pending; slice_ids=S1.4,S2.2,S2.3,S4.1,S4.2,S4.3,S4.4,S4.4a,S4.5,S4.6,S5.1,S5.2,S5.3,S5.4,S5.5; evidence=pair manifests verified; full shape/provider inventory open
- `INV-WORLD-MOVEMENT` block/fluid properties, subclasses, registries, data/tags and defaults: status=pending; slice_ids=S3.1,S3.5,S4.4,S4.4a,S4.6,S5.1,S5.2,S5.3,S5.4,S5.5,S6.3,S6.5,S6.6; evidence=pair manifests verified; resource inventory open
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and applications/removals/conditions: status=pending; slice_ids=S1.3,S1.4,S1.5,S2.1,S2.3a,S2.4,S2.5,S3.2,S3.4,S3.5,S3.7,S6.1,S6.2,S6.3,S6.4,S6.5,S6.6; evidence=pair manifests verified; modifier/data closure open
- `INV-EXTERNAL` player-only external inputs and client consumers: status=pending; slice_ids=S1.6,S1.7,S2.2a,S2.3a,S7.1,S7.2,S7.3,S7.4; evidence=pair manifests verified; external-writer inventory open
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
- B evidence: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::tick()V` lines 165-178, `serverTickAi()V` lines 631-642, `mobTick()V` lines 649-812, SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java::mobTick()V` lines 413-436, SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java::tick()V` lines 1619-1658 and `mobTick()V` lines 1781-1855, SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`
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
- B evidence: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 696-721 and `move(MoverType,DDD)V` lines 846-851, `autoJump(F,F)V` lines 857-957, SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java::mobTick()V` lines 1782-1838 and `jump()V`/`jumpInWater()V`/`jumpInLava()V` lines 1398-1419, SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; `PlayerEntity.jump()V` lines 1375-1383, SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`
- State producers/writers -> consumers/readers: local input/current jump and previous-jump edge feed flight/elytra and ride timers in S1.5/S1.6; auto-jump writes `ticksToNextAutojump`, consumed on the next local tick; `LivingEntity.jumpingCooldown` guards ground jump; jump writers modify velocity and may read jump-strength attributes/effects in S3.4/S6.2. Auto-jump reads dimensions, collision shapes and Jump Boost; those producers are inventoried in S2/S4/S6.
- Parent slices / dependencies / closure evidence: S1.1 input; S1.2 tick order; S1.5 flight edge; S1.6 mounted jump; S3.4 jump strength/impulse; S4.2/S4.5 auto-jump collision queries; S6.2 Jump Boost. Exact A/B autoJump, living jump resolver, `jump`, water/lava jumps and player jump method texts compare identical; provider and shape results remain separately open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): The direct input edge handling, cooldown values/order, ground/water/lava dispatch, player sprint-jump override and auto-jump probe formulas are text-identical for reachable local players. This disposition covers these methods only; the values returned by collision shapes, movement attributes and Jump Boost are checked in their linked slices.
- Finding IDs or checked absence/replacement path: none assigned

### Slice S1.5: Flight toggles and local flying input/speed gates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: When `canFly`, spectator mode enables flight and syncs abilities; otherwise a rising jump edge, excluding auto-jump, starts a seven-tick double-press timer or toggles `flying`, then syncs and clears the timer. While flying and camera-controlled, sneak divides horizontal inputs by `0.3F` and subtracts three times fly speed from vertical velocity; jump adds the same amount. Landing clears flying for non-spectators and syncs. The local elytra start-flying edge appears in this tick region; fall-flying state consumers and glide physics remain in S3.6.
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
- Exact behavior boundary and enclosing guards/order checked: `PlayerEntity` initializes a fresh `PlayerAbilities` object; defaults are false flags, `canModifyWorld=true`, fly speed `0.05F`, walk speed `0.1F`; NBT read/write persists flags and speeds with the same key/presence guards. Clientbound ability packets update flying/canFly and both speeds; serverbound client reports flying/allowFlying and both speeds; server accepts flying only when `canFly`. GameMode updates flight flags. Local spectator scroll changes fly speed by `0.005F` per wheel step clamped to `[0,0.2F]`. Ability update methods and packet classes are byte-identical across this pair.
- A evidence: `1.11.2/ornithe-feather/net/minecraft/entity/living/player/PlayerAbilities.java` lines 5-59, SHA-256 `b0350ca7819f995e4ede358a9d71270cc7a073a9d8817ce218d363cca7ea0cb0`; `PlayerEntity.java` ability field line 125 and NBT read/write lines 742/770, SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`; `LocalClientPlayerEntity.java::syncAbilities()V` lines 318-320, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; `ClientPlayNetworkHandler::handlePlayerAbilities(PlayerAbilitiesS2CPacket)V` lines 1362-1370, SHA-256 `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175`; `ServerPlayNetworkHandler::handlePlayerAbilities(PlayerAbilitiesC2SPacket)V` lines 1073-1075, SHA-256 `947be17cf3d7217e7c8e563d4dd312cd133de1dc06bcd82b4431c9e5e74bc0bf`; `ServerPlayerEntity::syncAbilities()V` lines 898-902, SHA-256 `c2187f10b589bbfb47bef2a1b573781fecad55a13fb80c4c5dc11260d9f12197`; `PlayerAbilitiesS2CPacket.java` lines 9-115, SHA-256 `808d7f80bfc07684566e6a1dcf53b14a68677de599344f9db50bd6af84a85588`; `PlayerAbilitiesC2SPacket.java` lines 9-107, SHA-256 `e0bab6badb7937127365c8834efaa92f61034fae88717f95972e13bfb90bdad1`; `GameMode.java` lines 29-46, SHA-256 `7e9a820a61edd88046b92083a7094ff442a598212271d9e91227bcd9b3e5bdc8`; `Minecraft.java` spectator wheel path lines 1738-1742, SHA-256 `3ca93038adf4cea512c21ae86d653376e963396640a02eb50cb42a67302f6236`
- B evidence: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerAbilities.java` lines 5-59, SHA-256 `b0350ca7819f995e4ede358a9d71270cc7a073a9d8817ce218d363cca7ea0cb0`; `PlayerEntity.java` ability field line 124 and NBT read/write lines 721/757, SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; `LocalClientPlayerEntity.java::syncAbilities()V` lines 324-326, SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; `ClientPlayNetworkHandler::handlePlayerAbilities(PlayerAbilitiesS2CPacket)V` lines 1410-1418, SHA-256 `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c`; `ServerPlayNetworkHandler::handlePlayerAbilities(PlayerAbilitiesC2SPacket)V` lines 1119-1121, SHA-256 `77bf65b2c48ff952713942e183af1cd5fb243ad4f8fd2e53aa1b97272e7acd7c`; `ServerPlayerEntity::syncAbilities()V` lines 1014-1018, SHA-256 `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9`; `PlayerAbilitiesS2CPacket.java` lines 9-115, SHA-256 `808d7f80bfc07684566e6a1dcf53b14a68677de599344f9db50bd6af84a85588`; `PlayerAbilitiesC2SPacket.java` lines 9-107, SHA-256 `e0bab6badb7937127365c8834efaa92f61034fae88717f95972e13bfb90bdad1`; `GameMode.java` lines 29-46, SHA-256 `7e9a820a61edd88046b92083a7094ff442a598212271d9e91227bcd9b3e5bdc8`; `Minecraft.java` spectator wheel path lines 1796-1800, SHA-256 `8af833a5d03a6f6516a7eff0ab463a96bfec64aac31fc80656f37d193b1c1310`
- State producers/writers -> consumers/readers: new `PlayerEntity` creates abilities; NBT restore and clientbound packets write them; local flight toggles and spectator wheel mutate fields; `syncAbilities` sends state; server network checks, game mode selection and server player synchronization produce authoritative updates. Local `mobTick`, `PlayerEntity.travel`, and `LivingEntity.moveRelative` consume flight flags/speeds. S1.5 and S3.1-S3.6 contain the direct movement consumers.
- Parent slices / dependencies / closure evidence: S1.5 local flight toggles; S1.7 client packet state writes; S3.1/S3.2 flight travel; S6 movement speed/attribute consumers; S7.3 packet authority. Writer scan for `canFly`, `flying`, fly/walk speed and NBT paths found matching client/server handlers, `GameMode`, local spectator speed control, player defaults and packet serialization in both trees.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Player ability defaults, persistence guards, local fly-speed adjustment, client/server packet fields and GameMode flag transitions match. `PlayerAbilities.java` and both ability packet classes are byte-identical. No pair-specific ability source change was found; downstream movement formulas remain covered independently.
- Finding IDs or checked absence/replacement path: none assigned

### Slice S2.2: Pose selection, dimensions, eye height, resize collision gate and all pose/dimension writers

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `PlayerEntity.updatePlayerPose()` runs once in player tick after item cooldown update; priority is fall-flying `0.6 x 0.6`, sleeping `0.2 x 0.2`, sneaking `0.6 x 1.65`, then standing `0.6 x 1.8`. It creates a candidate box from current minimum coordinates and calls `setSize` only if `World.getCollisions(candidate)` is false. The clearance query scans the candidate region expanded by one block, delegates to `BlockState.addCollisions`, then to each block's collision provider and intersection helper. All direct player `setSize` sites found in the player packages: pose update, reset, death, sleep and wake; sleep is split into S2.2a.
- A evidence: `1.11.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` tick call line 249, `updatePlayerPose()` lines 295-319, `getEyeHeight()` lines 1759-1769, `resetPos()` lines 402-407, `die(DamageSource)` lines 499-525, `wakeUp()` lines 1265-1287, file SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`; `Entity.java` default dimensions lines 176-177 and `setSize()` lines 271-288, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563ea7ba308603335bbe05d49440`; `PlayerInventory.java::addItem(int,ItemStack)` lines 274-304 (the `ItemStack.setSize` calls alter stack counts), SHA-256 `cf956f7410c6911d2e978d2072a7d53590e4800c398687afecc6f771e74e19d6`; `LocalClientPlayerEntity::isSneaking()` lines 608-611, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; `World.java::getCollisions(Box)` lines 1058-1060 and collision helper lines 960-1011, SHA-256 `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58`.
- B evidence: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` tick call line 245, `updatePlayerPose()` lines 291-315, `getEyeHeight()` lines 1756-1766, `resetPos()` lines 398-403, `die(DamageSource)` lines 510-536, `wakeUp()` lines 1259-1281, file SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; `Entity.java` default dimensions lines 178-179 and `setSize()` lines 274-291, SHA-256 `80f091bf32166c88cf8b8bd31caf72d84fa16224410733c7d2a0f00563f294a0a`; `PlayerInventory.java::addItem(int,ItemStack)` lines 292-335 (the `ItemStack.setSize` calls alter stack counts), SHA-256 `ceb4be07c53e7be4957d33f710a43404915569fe81c0ec12976cd5fb4f6f14cd`; `LocalClientPlayerEntity::isSneaking()` lines 625-628, SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; `World.java::getCollisions(Box)` lines 1062-1064 and collision helper lines 964-1015, SHA-256 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`.
- Collision dispatch evidence: A `World.getCollisions(Entity,Box,boolean,List)` lines 960-1011 -> `StateDefinition.State.addCollisions(...)` line 374 -> `Block.addCollisions(...)` lines 353-361 -> `BlockState.getCollisionShape(...)` / `Block.getCollisionShape(...)` lines 368-369. B corresponding methods are `World.java` lines 964-1015 -> `StateDefinition.State.addCollisions(...)` line 375 -> `Block.addCollisions(...)` lines 372-380 -> `BlockState.getCollisionShape(...)` / `Block.getCollisionShape(...)` lines 387-388. A/B dispatch bodies match; `StateDefinition.java` hashes A `95704c944742efb2ec9cba7eb73399a330195556e669451131ba542e6a9fac65`, B `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37`; `Block.java` hashes A `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`, B `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`. The complete shape/collision-provider body census and the A-to-B block ID correspondence are recorded below; state properties/defaults and neighboring-state dependencies remain open. Farmland's `getShape()` returns the same `0..0.9375` box on both sides (A `FarmlandBlock.java` hash `09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8`; B `d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98`).
- Complete collision-provider body census: scanned A's 230 and B's 235 Java files below `net/minecraft/block/`. All 117 paired declarations across the same 76 relative files for `getShape`, `getCollisionShape` and `addCollisions` have identical non-whitespace method bodies. Exact A/B body ranges, relative to each version's `ornithe-feather/net/minecraft/block/` source root:
  - `AbstractPressurePlateBlock.java`: `getCollisionShape` A/B 45-47; `getShape` A/B 33-36.
  - `AbstractRailBlock.java`: `getCollisionShape` A/B 41-43; `getShape` A/B 51-54.
  - `AirBlock.java`: `getCollisionShape` A 24-26 / B 25-27.
  - `AnvilBlock.java`: `getShape` A 88-91 / B 92-95.
  - `BannerBlock.java`: `getCollisionShape` A/B 39-41; `getShape` A/B 116-118; second `getShape` A 177-189 / B 182-194.
  - `BedBlock.java`: `getShape` A 134-136 / B 174-176.
  - `BeetrootsBlock.java`: `getShape` A/B 63-65.
  - `Block.java`: `addCollisions` A 353-355 / B 372-374; `getCollisionShape` A 368-370 / B 387-389; `getShape` A 289-291 / B 307-309.
  - `BrewingStandBlock.java`: `addCollisions` A/B 70-73; `getShape` A/B 76-78.
  - `ButtonBlock.java`: `getCollisionShape` A/B 48-50; `getShape` A 114-132 / B 117-135.
  - `CactusBlock.java`: `getCollisionShape` A/B 55-57.
  - `CakeBlock.java`: `getShape` A/B 40-42.
  - `CarpetBlock.java`: `getShape` A 31-33 / B 30-32.
  - `CarrotsBlock.java`: `getShape` A/B 33-35.
  - `CauldronBlock.java`: `addCollisions` A/B 47-53; `getShape` A/B 56-58.
  - `ChestBlock.java`: `getShape` A/B 68-78.
  - `ChorusPlantBlock.java`: `addCollisions` A/B 66-97; `getShape` A/B 53-63.
  - `CobwebBlock.java`: `getCollisionShape` A 39-41 / B 40-42.
  - `CocoaBlock.java`: `getShape` A/B 77-90.
  - `DaylightDetectorBlock.java`: `getShape` A/B 42-44.
  - `DeadBushBlock.java`: `getShape` A/B 27-29.
  - `DiodeBlock.java`: `getShape` A/B 25-27.
  - `DoorBlock.java`: `getShape` A/B 51-67.
  - `DoublePlantBlock.java`: `getShape` A/B 42-44.
  - `DragonEggBlock.java`: `getShape` A/B 25-27.
  - `EnchantingTableBlock.java`: `getShape` A/B 32-34.
  - `EndGatewayBlock.java`: `getCollisionShape` A/B 38-40.
  - `EndPortalBlock.java`: `addCollisions` A/B 44-45; `getShape` A/B 34-36.
  - `EndPortalFrameBlock.java`: `addCollisions` A/B 50-55; `getShape` A/B 45-47.
  - `EndRodBlock.java`: `getShape` A/B 40-50.
  - `EnderChestBlock.java`: `getShape` A/B 37-39.
  - `FarmlandBlock.java`: `getShape` A 30-32 / B 31-33.
  - `FenceBlock.java`: `addCollisions` A/B 59-80; `getShape` A/B 83-86.
  - `FenceGateBlock.java`: `getCollisionShape` A/B 81-87; `getShape` A/B 36-43.
  - `FireBlock.java`: `getCollisionShape` A/B 98-100.
  - `FlowerBlock.java`: `getShape` A 33-35 / B 32-34.
  - `FlowerPotBlock.java`: `getShape` A/B 47-49.
  - `GrassPathBlock.java`: `getShape` A/B 52-54.
  - `HopperBlock.java`: `addCollisions` A/B 56-62; `getShape` A/B 51-53.
  - `LadderBlock.java`: `getShape` A/B 30-42.
  - `LeverBlock.java`: `getCollisionShape` A/B 40-42; `getShape` A 113-131 / B 111-129.
  - `LilyPadBlock.java`: `addCollisions` A/B 23-27; `getShape` A/B 38-40.
  - `LiquidBlock.java`: `getCollisionShape` A/B 41-43; `getShape` A/B 35-37.
  - `MovingBlock.java`: `addCollisions` A/B 135-140; `getCollisionShape` A/B 129-132; `getShape` A/B 143-146.
  - `MushroomPlantBlock.java`: `getShape` A/B 20-22.
  - `NetherWartBlock.java`: `getShape` A/B 34-36.
  - `PaneBlock.java`: `addCollisions` A/B 54-75; `getShape` A/B 82-85.
  - `PistonBaseBlock.java`: `addCollisions` A/B 80-82; `getShape` A/B 52-72.
  - `PistonHeadBlock.java`: `addCollisions` A/B 71-74; `getShape` A/B 52-68.
  - `PlantBlock.java`: `getCollisionShape` A 70-72 / B 71-73; `getShape` A 64-66 / B 65-67.
  - `PortalBlock.java`: `getCollisionShape` A/B 76-78; `getShape` A/B 40-50.
  - `PotatoesBlock.java`: `getShape` A/B 45-47.
  - `RedstoneWireBlock.java`: `getCollisionShape` A/B 130-132; `getShape` A/B 69-71.
  - `SaplingBlock.java`: `getShape` A 38-40 / B 37-39.
  - `ShulkerBoxBlock.java`: `getShape` A 217-220 / B 221-224.
  - `SignBlock.java`: `getCollisionShape` A/B 34-36; `getShape` A/B 28-30.
  - `SkullBlock.java`: `getShape` A 81-95 / B 82-96.
  - `SlabBlock.java`: `getShape` A/B 39-45.
  - `SnowLayerBlock.java`: `getCollisionShape` A 61-66 / B 66-71; `getShape` A/B 45-47.
  - `SoulSandBlock.java`: `getCollisionShape` A/B 24-26.
  - `StairsBlock.java`: `addCollisions` A/B 66-74.
  - `StemBlock.java`: `getShape` A/B 43-45.
  - `StructureVoidBlock.java`: `getCollisionShape` A 27-29 / B 28-30; `getShape` A 32-34 / B 33-35.
  - `SugarCaneBlock.java`: `getCollisionShape` A/B 101-103; `getShape` A/B 30-32.
  - `TallPlantBlock.java`: `getShape` A/B 33-35.
  - `TorchBlock.java`: `getCollisionShape` A/B 57-59; `getShape` A 40-53 / B 40-53.
  - `TrapdoorBlock.java`: `getShape` A/B 40-64.
  - `TripwireBlock.java`: `getCollisionShape` A/B 64-66; `getShape` A/B 50-52.
  - `TripwireHookBlock.java`: `getCollisionShape` A/B 56-58; `getShape` A 40-52 / B 40-52.
  - `VineBlock.java`: `getCollisionShape` A/B 46-48; `getShape` A 51-81 / B 51-81.
  - `WallBlock.java`: `addCollisions` A 92-98 / B 90-96; `getCollisionShape` A 102-105 / B 100-103; `getShape` A 86-89 / B 84-87.
  - `WallSignBlock.java`: `getShape` A/B 24-36.
  - `WheatBlock.java`: `getShape` A/B 40-42.
  - `entity/MovingBlockEntity.java`: `addCollisions` A/B 318-346; first `getShape` A/B 96-98; second `getShape` A/B 100-105.
  - `entity/ShulkerBoxBlockEntity.java`: first `getShape` A/B 88-90; second `getShape` A/B 92-99.
  - `state/StateDefinition.java`: `addCollisions` A 374-376 / B 375-377; `getCollisionShape` A 369-371 / B 370-372; `getShape` A 379-381 / B 380-382.
- Wall neighbor-connection follow-up (bounded under S2.2; does not close the slice): exact readiness records and source manifests were rehashed before inspection: A `ornithe-feather.ready.json` SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`, artifact manifest `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`, source manifest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; B corresponding hashes `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`, `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`, `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`. Both ready records identify 1.11.2/1.12.2 and `ornithe-feather`; every source hash below matched its exact source-manifest entry.
  - A evidence: `WallBlock.java::shouldConnectTo(WorldView,BlockPos)` lines 148-158, `resolveVirtualProperties(BlockState,WorldView,BlockPos)` 188-195, `isCube(BlockState)` 134-136, `getShape` 86-89, `addCollisions` 92-98, `getCollisionShape` 102-105; whole-file SHA-256 `17da6d881365e4dce5134f88cb9a9e5273d1fa1734812d13410dc6924a3acbfd`. `PlayerEntity.updatePlayerPose()` lines 295-319, `trySleep` size write line 1228 and `wakeUp` size write line 1266; whole-file hash `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`. `World.getCollisions(Box)` lines 1058-1060 / private collision collector 960-1011, whole-file hash `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58`.
  - B evidence: `WallBlock.java::shouldConnectTo(WorldView,BlockPos,Direction)` lines 146-155, `resolveVirtualProperties(BlockState,WorldView,BlockPos)` 190-197, `getFaceShape(WorldView,BlockState,BlockPos,Direction)` 205-207, and matching shape-provider bodies at lines 84-103; whole-file SHA-256 `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50`. New `FaceShape` enum SHA-256 `03d6b642d5182e5fae5ccfb2fd7d6a97c39fc0553221ae279d8a7b5e70da01f9`; `StateDefinition.java` delegates state face queries to the block at lines 415-416, whole-file hash `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37`. `PlayerEntity.updatePlayerPose()` lines 291-315, `trySleep` size write line 1222 and `wakeUp` size write line 1260; whole-file hash `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`. `World.getCollisions(Box)` lines 1062-1064 / private collector 964-1015, whole-file hash `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`.
  - Source difference: A's `resolveVirtualProperties` asks whether the east neighbor should connect using only material solidity plus `isCube`; `WallBlock.isCube` returns false, so an east-adjacent wall leaves the current wall at the center-pole shape. B asks that neighbor's west face and accepts `FaceShape.MIDDLE_POLE_THICK`; B `WallBlock.getFaceShape` returns that face shape on every horizontal side, so the east flag is true and the reciprocal west flag on the adjacent wall is also true. The property defaults are all false in both constructors, but this method overwrites the four horizontal flags from neighbors before the outline or collision box is selected. `Direction.EAST.getIdHorizontal()` is 3 in both releases, selecting index 8: the current B wall's box is x `[0.25,1.0]`, z `[0.25,0.75]`, y `[0,1.5]`; A's center pole is x/z `[0.25,0.75]`, y `[0,1.5]`. The adjacent B wall reciprocally extends west, making the pair's x collision intervals `[0.25,1.0]` and `[1.0,1.75]`; A has center intervals `[0.25,0.75]` and `[1.25,1.75]`. The same `COBBLESTONE_WALL` block is registered in both releases.
  - Player route and response check: `PlayerEntity.tick()` calls `updatePlayerPose()` in both versions; its dimension-change query is `World.getCollisions(candidateBox)`, whose collector dispatches with `forceShape=false` to the block-state `addCollisions` and therefore resolves the wall's virtual properties. The active player width is 0.6 for fall-flying, sneaking and standing. The only 0.2-wide sleeping transition writes dimensions directly in `trySleep`/`wakeUp`, not through `updatePlayerPose`'s collision-gated resize. For the live 0.6-wide player AABB, the 0.5-wide gap between the two A center poles is narrower than the player; any query box intersecting one of the added east/west arms also intersects at least one center pole, with identical y/z bounds. Thus the candidate collision query has no different player result for this exact mutual wall pair. `Entity.move` uses the same world collision collector for player movement, and its swept player box is at least 0.6 wide, so the same interval-coverage argument prevents a changed player collision response. This is a source-backed discarded candidate, not a finding; smaller non-player collision behavior is outside campaign scope.
- Registration and state identity evidence: A `Block.java` registration block lines 728-1321 has 236 indexed entries. B lines 747-1358 has those same 236 index/key pairs and 18 B-only entries at IDs 235-252 (glazed terracotta, concrete and concrete powder), which could not occur in an A-era map. The sole shared registration constructor change is ID 159: A `new ColoredBlock(Material.STONE)` versus B `new StainedHardenedClayBlock()` (A lines 1103-1107; B lines 1122-1126). B's class extends `ColoredBlock`, calls `super(Material.STONE)` and only overrides `getMapColor`; both constructors use the same inherited `COLOR` property/default and inherited block shape path. Registration source hashes: `Block.java` A `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`, B `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`; `Blocks.java` A `69e47950c2d9761deff0ce1e1c46fefb2472bf61cb3d6c155fcb15457d03e64d`, B `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e`; `ColoredBlock.java` A `c6d07db17d2ca94837358d9fef909853c55b6fd380e60bb03510c65bf0d4eb0e`, B `6edb1087c63d2d2547cc0bda6726a4407661c474b4df013cc73a456bbb7c64e0`; B `StainedHardenedClayBlock.java` `9aa86b43c1db88734bfe16f9ff15c3ead7c647cd0e6d89b81c0111a3cba8ae08`. Block-state property/default and neighboring-state dependencies remain open outside this bounded registry correspondence.
- State producers/writers -> consumers/readers: sleeping/sneaking/fall-flying flags select pose dimensions; `updatePlayerPose` collision clearance gates the new shape; shared `Entity.setSize` writes width, height and bounding shape, and may move a server entity horizontally when widening after its first tick. `getEyeHeight` consumes pose flags and current height. Respawn reset and death write the same size/position/velocity values on both releases; sleep writes are compared in S2.2a.
- Parent slices / dependencies / closure evidence: S2.2a owns the sleep eligibility difference. S2.3a owns fall-flight state entry; `DEP-COLLISION-SHAPES` remains open for state properties/defaults, neighboring-state dependencies and the broader collision slices even though this query's provider method bodies and old-block ID correspondence are compared. S1.7 remains open for broader packet authority outside the direct pose comparison.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): `updatePlayerPose`, `getEyeHeight`, `resetPos`, `die`, `wakeUp`, `Entity.setSize`, `getShape`/`setShape`, local-player `isSneaking`, the query dispatch and all 117 paired shape/collision-provider method bodies are unchanged for corresponding old blocks. The only changed PlayerEntity size transition found so far is the sleep gate in S2.2a. The complete player-package writer scan finds geometry writes only in `PlayerEntity`; `PlayerInventory` calls `ItemStack.setSize` to change stack counts, not entity bounds. Keep this slice open for state-property/default and neighboring-state dependencies.
- Finding IDs or checked absence/replacement path: F-SLEEP-SAFETY-TRANSITION is bounded in S2.2a; no other pose-size difference found in the compared methods.

### Slice S2.2a: Bed sleep eligibility and player movement-state transition

- Inventory ID(s): INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: server-side `PlayerEntity.trySleep(BlockPos)` safety query and its gate over player dismount, dimensions, position, sleeping state and velocity; bed interaction entry; server sleep result packet/teleport handoff; client packet consumer. The broad pose/dimension/eye-height writer inventory remains S2.2.
- A evidence: `1.11.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java::trySleep(BlockPos)` lines 1194-1249, SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`; `Entity.java::setSize(FF)V` lines 271-288, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`; `PlayerEntity.java::wakeUp(ZZZ)V` lines 1265-1287, same PlayerEntity hash; `BedBlock` call at line 64, SHA-256 `84f56aab19adae4b953a27040e5f431f65c8923d6bd169995ac5a5f629a024e1`; `ServerPlayerEntity::trySleep(BlockPos)` starts line 540, SHA-256 `c2187f10b589bbfb47bef2a1b573781fecad55a13fb80c4c5dc11260d9f12197`; client `handlePlayerSleep` lines 766-769, SHA-256 `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175`.
- B evidence: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java::trySleep(BlockPos)` lines 1185-1243, SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; `Entity.java::setSize(FF)V` lines 274-291, SHA-256 `80f091bf32166c88cf8b8bd31caf72d84fa16224410733c7d2a0f00563f294a0a`; `PlayerEntity.java::wakeUp(ZZZ)V` lines 1259-1281, same PlayerEntity hash; `BedBlock` call at line 86, SHA-256 `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369`; `ServerPlayerEntity::trySleep(BlockPos)` starts line 600, SHA-256 `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9`; client `handlePlayerSleep` lines 775-778, SHA-256 `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c`; `MonsterEntity::isAngryAt(PlayerEntity)` lines 162-164, SHA-256 `e96f44cf14e80d3154fdd3a8e6827000bf0f13a545ee095c1ebedac7993e41b6`; `ZombiePigmanEntity::isAngryAt(PlayerEntity)` lines 199-201, SHA-256 `aba397ad24f519dbdfcdd594a1e9ecd8ad26ef36ea030b54b7f2be35d06f9701`.
- State producers/writers -> consumers/readers: the A/B server safety query result gates the base player sleep transition; on success both set the sleeping dimensions, bed position, sleeping flag and zero velocity, then `ServerPlayerEntity` teleports and sends the sleep packet. The client handler replays `trySleep`; `wakeUp` restores ordinary player dimensions in both versions.
- Parent slices / dependencies / closure evidence: The finding-specific bed interaction, sleep predicate, player state writes and sleep-packet consumer are traced here. S2.2 retains the broader pose/dimension/eye-height inventory; S1.7 retains the full packet authority and movement-correction audit. The discovery author and independent ops both verified revision `feather-r1-2026-10-07`; the original-derived-JAR equivalence limitation remains explicit. Snapshot r2 is recorded as submitted; a replacement snapshot carrying the ops confirmation will receive fresh blind review.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): under the same server-side conditions—living, awake player, natural dimension, non-sunny time, bed in range, and only a non-angry zombie pigman inside the query box—A returns `NOT_SAFE` because any monster blocks sleep; B filters for monsters angry at this player, and the pigman override returns `isAngry()`, so B proceeds to the player movement-state writes. This source-differential finding is bounded to sleep eligibility; it does not close S2.2 or S1.7.
- Finding IDs or checked absence/replacement path: F-SLEEP-SAFETY-TRANSITION; paired source evidence and hash detail in `findings/F-SLEEP-SAFETY-TRANSITION.md`

### Slice S2.3: Swimming/crawling pose transitions and their movement gates

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: A/B `PlayerEntity.updatePlayerPose()` checks fall-flying, sleeping and sneaking before the standing fallback; it has no swimming/crawling pose branch. The bounded fall-flying state entry is recorded in S2.3a. Water travel and glide movement math are covered by S3.x slices.
- A evidence: `1.11.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java::updatePlayerPose()V` lines 295-319, SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`; `LivingEntity.java` full entity-tree identifier scan found no swimming/crawling state methods or flags, file SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`.
- B evidence: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java::updatePlayerPose()V` lines 291-315, SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; `LivingEntity.java` full entity-tree identifier scan found no swimming/crawling state methods or flags, file SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`.
- State producers/writers -> consumers/readers: no player swimming/crawling state producer or consumer is present in either entity tree. Fall-flying state writers/readers are recorded in S2.3a; water travel remains in S3.5/S3.6.
- Parent slices / dependencies / closure evidence: absent swim/crawl pose state is confirmed by the exact player pose branches and identifier scan. S2.3a records fall-flying state entry; S2.2 owns pose dimensions and collision clearance; S3.5/S3.6 own water/glide movement.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): these exact releases have no swimming or crawling player pose/state transitions to emulate. Their movement still has separate water-travel coverage; no feature behavior is inferred from a later release.
- Finding IDs or checked absence/replacement path: none assigned

### Slice S2.3a: Fall-flying activation, server revalidation and state flag

- Inventory ID(s): INV-STATE, INV-EXTERNAL, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: local player START_FALL_FLYING request; server packet handler guards and set/clear transition; fall-flying flag consumer, flight tick eligibility/durability check and NBT persistence. Glide velocity math and pose collision-provider closure are excluded.
- A evidence: `1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 742-749, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; `ServerPlayNetworkHandler.java::handlePlayerMovementAction(...)` lines 799-854, SHA-256 `947be17cf3d7217e7c8e563d4dd312cd133de1dc06bcd82b4431c9e5e74bc0bf`; `ServerPlayerEntity.java::setFlying()`/`clearFlying()` lines 1067-1074, SHA-256 `c2187f10b589bbfb47bef2a1b573781fecad55a13fb80c4c5dc11260d9f12197`; `LivingEntity.java::isFallFlying()` lines 2145-2147 and `flyingTick()` lines 1810-1829, SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`; `PlayerMovementActionC2SPacket.java` SHA-256 `4ee656c36ee827ade80a70c6f5052deff17aa8feb96ca6508341e590d615c341`; `ElytraItem.java::canFly(ItemStack)Z` lines 29-31, SHA-256 `16e02a70181c063c0643b7dfdbe17516ad46cd38053a2d88cc3fb97400652c7c`.
- B evidence: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick()V` lines 764-771, SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; `ServerPlayNetworkHandler.java::handlePlayerMovementAction(...)` lines 835-890, SHA-256 `77bf65b2c48ff952713942e183af1cd5fb243ad4f8fd2e53aa1b97272e7acd7c`; `ServerPlayerEntity.java::setFlying()`/`clearFlying()` lines 1188-1195, SHA-256 `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9`; `LivingEntity.java::isFallFlying()` lines 2184-2186 and `flyingTick()` lines 1854-1873, SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; `PlayerMovementActionC2SPacket.java` SHA-256 `4ee656c36ee827ade80a70c6f5052deff17aa8feb96ca6508341e590d615c341`; `ElytraItem.java::canFly(ItemStack)Z` lines 29-31, SHA-256 `16e02a70181c063c0643b7dfdbe17516ad46cd38053a2d88cc3fb97400652c7c`.
- State producers/writers -> consumers/readers: client sends the common enum action; handler calls the matching `ServerPlayerEntity` transition; both transition methods write flag 7; `LivingEntity.isFallFlying()` reads flag 7; `flyingTick()` consumes it and damages Elytra every 20 eligible flight ticks server-side; NBT stores/restores the flag. Full-tree `setFlag(7)` scan found corresponding writes in PlayerEntity, LivingEntity and ServerPlayerEntity for each version.
- Parent slices / dependencies / closure evidence: bounded activation and flag path compared; `DEP-RELATIVE-MOVE` remains open for PlayerEntity movement integration and glide math; `DEP-COLLISION-SHAPES` and `DEP-MODIFIER-DATA` remain open for pose clearance and Elytra/item closure; S1.7 packet-authority inventory remains open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): within the stated activation and server-validation preconditions, packet action, server acceptance gates, flag transitions, flag reader, durability cadence and persistence match. This establishes no pair-specific difference for this state path only; it does not infer equal glide movement.
- Finding IDs or checked absence/replacement path: none; paired methods and flag writers were directly compared.

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

### Slice S4.4a: Farmland conversion relocation in the landed-player callback

- Inventory ID(s): INV-COLLISION, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: server-side player landing on farmland with positive fall distance and entity volume above 0.512; `Entity.move` reaches `checkFallDamage`, which invokes `FarmlandBlock.onFallenOn` and `setDirt`
- A evidence: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `onFallenOn` lines 59-69 and `setDirt` lines 71-79, SHA-256 `09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8`; player route and position writer `Entity.java` SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563ea7ba308603335bbe05d49440`; entity query `World.java` SHA-256 `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58`; inherited full dirt shape `Block.java` SHA-256 `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`
- B evidence: `1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `onFallenOn` lines 60-70 and `setDirt` lines 72-80, SHA-256 `d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98`; teleport/set-position route `Entity.java` SHA-256 `80f091bf32166c88cf8b8bd31caf72d84fa16224410733c7d2a0f00563f294a0`; entity query and tick route `World.java` SHA-256 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`; inherited full dirt shape `Block.java` SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`
- State producers/writers -> consumers/readers: A relocates query results through `Entity.setPosition`; B computes the target from the entity AABB and calls `Entity.teleport`, which resets position history, sets position/angles and calls `World.tickEntity(entity,false)`.
- Parent slices / dependencies / closure evidence: bounded sub-slice of S4.4/S5.2; full collision-shape, callback and world/block inventories remain open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): changed player behavior in the landed-player callback. For standing player dimensions 0.6x0.6x1.8 and fall distance greater than 1.5, the server/random/volume guards pass; A places the player at block top y+1.0 while B adds the farmland-shape offset plus 0.001 and places the player at y+1.001, invoking B's teleport position-history and world-tick path. Scope is limited to this landed-player callback; other `setDirt` callers were not compared.
- Finding IDs or checked absence/replacement path: `F-FARMLAND-PLAYER-RELOCATION`; source finding snapshot is submitted for independent blind review.

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
- `DEP-COLLISION-SHAPES`: open; the paired shape/collision-provider method census, old-block ID correspondence and the WallBlock neighbor-derived box/query subcase are now checked; broader player-reachable state defaults, other neighbor providers, callbacks and collision/movement slices remain open; owner discovery author.
- `DEP-MODIFIER-DATA`: open; trace movement modifiers/equipment to application, resources and consumers; owner discovery author.
- `DEP-EXTERNAL-WRITERS`: open; enumerate player-only velocity/position packet, push, piston, launch and mount paths; owner discovery author.
- `DEP-OLD-CANDIDATES`: open re-verification only: prior report glide look-vector/yaw, bed landing rebound, server dismount query geometry; none accepted as current finding.
- `DEP-DIAGNOSTICS`: readiness diagnostic hashes verified; inspect every cited method body; raw Gradle logs not retained; request exact mapped-bytecode/source assistance through commentary if a body is damaged or ambiguous.
- `DEP-ARTIFACT-INTEGRITY`: closed for current source-provenance use; discovery author verified both `feather-r1-2026-10-07` snapshots, source trees and raw inputs, and independent ops audit passed the revised Feather bundle. Original derived mapped JARs remain unavailable; revised snapshots do not prove identity with them. Do not rewrite markers or waive mismatches.
- `DEP-RELATIVE-MOVE` (origin S1.2, owner discovery author): resolve all player-path writers/defaults of B-only `LivingEntity.verticalSpeed`, compare A 2D versus B 3D `moveRelative`/`Entity.updateVelocity` argument order and float operation order, then classify any zero-valued vertical write. Current source scan finds the declaration and only `MobEntity.setVerticalSpeed` writer; player inheritance path is not a `MobEntity`.
- Open dependencies: DEP-TICK-CALLGRAPH, DEP-COLLISION-SHAPES, DEP-MODIFIER-DATA, DEP-EXTERNAL-WRITERS, DEP-OLD-CANDIDATES, DEP-DIAGNOSTICS, DEP-RELATIVE-MOVE

## Finding index

`F-SLEEP-SAFETY-TRANSITION`: source-differential finding with artifact revision freshly verified by the discovery author and independent ops; fresh blind finding review is pending. It records the observed server-side bed safety predicate change and resulting sleep dimensions/position/velocity writes when the only nearby monster is a non-angry zombie pigman; see S2.2a and revision `feather-r1-2026-10-07`. Do not freeze or accept it until the reviewer accepts the exact replacement snapshot. Prior pair claims remain navigation candidates only under `DEP-OLD-CANDIDATES`. Source-only declaration: no implementation, wiki or wiki-audit evidence opened.

`F-FARMLAND-PLAYER-RELOCATION`: source-differential finding for the guarded server landed-player callback when farmland converts to dirt. A relocates query results with `setPosition` to y+1.0; B uses the top farmland shape offset plus 0.001 with `teleport`, reaching additional position-history and world-tick handling. Exact 1.11.2/1.12.2 Feather evidence artifact IDs, revision hashes and source hashes are recorded in the finding. Independent blind review pending. Scope is only this callback path; all other inventories and pair closure remain open.

## Resume checkpoint

- Last completed bounded slices: S2.2a, S2.3, S2.3a and S4.4a; S1.7 and S2.2 remain in-progress; S1.2a and S1.2b have bounded not-applicable dispositions.
- Next bounded slice and exact files/members/body ranges to open: continue S2.2 by closing remaining player-reachable state/default and neighbor-provider dependencies around `World.getCollisions(Box)`; the WallBlock connection subcase is dispositioned with no reachable player response for its 0.6-wide active hitbox. Then continue S1.7 packet-to-entity dispatch and remaining local movement-state writers. Keep `LivingEntity.travel` glide movement under S3.6 and `PlayerEntity.moveRelative` under S3.2/`DEP-RELATIVE-MOVE`.
- Outstanding dependencies and owners: listed above; discovery author owns source inventory; artifact provenance is independently verified; blind finding reviewer and full-pair reviewer assignment pending coordinator.
- Current assumptions requiring verification: all prior findings and no-difference claims remain unaccepted; every cited body still requires direct review despite ready tree hashes.

## Finding snapshots (not pair freeze)

Append-only source-review history. An accepted finding snapshot would release only that finding to a separate implementation task; it would not close this pair.

### Snapshot event F-SLEEP-SAFETY-TRANSITION-r1

- Finding ID(s): F-SLEEP-SAFETY-TRANSITION
- Source finding author(s): Codex source worker
- Status: invalidated
- Immutable snapshot commit: `eb997d6a4a533dcac36b2991be47df5282d94f8a`
- Finding file path and SHA-256: `workflows/source-campaign-2026-10-07/1.11.2--1.12.2/findings/F-SLEEP-SAFETY-TRANSITION.md`; `dcf23b56cbb4fbe3440fee24bc59a7e2535bf0d222d8091d2cb8ccf2bb409ccb`
- Exact A/B artifact-manifest identities/hashes: A `1.11.2/ornithe-feather/artifacts.sha256`, `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`; B `1.12.2/ornithe-feather/artifacts.sha256`, `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`; the revised Feather snapshot had not yet been supplied in this event.
- Cited source/resource hashes: A PlayerEntity `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`; B PlayerEntity was recorded incorrectly as `e4e0fdbe07a7d0a0ae4a70cbb6739a2d7c9d045a4a12b409895c220b5d91fe1e`.
- Verified implementation boundary/evidence, or unresolved boundary reason: sleep eligibility predicate and resulting player dimension/position/velocity writes; the B PlayerEntity source hash typo made the evidence record inaccurate.
- Finding-specific closed dependency IDs/evidence: sleep eligibility and direct state-write route inspected; artifact provenance was unresolved.
- Independent blind source reviewer and decision date: early independent review relayed a revision request; reviewer identity and date not supplied.
- Review basis / requested source-only revisions: recompute the exact B PlayerEntity.java hash, correct it, preserve this event as invalidated history, and submit a new snapshot for fresh review.
- Pair run status and commit at handoff: active at `eb997d6a4a533dcac36b2991be47df5282d94f8a`.
- Pair complete: no
- Implementation handoff: blocked; invalidated hash evidence and unresolved artifact provenance.
- Replaces/supersedes snapshot ID and reason, if applicable: replaced by `F-SLEEP-SAFETY-TRANSITION-r2` because the B-side source-file hash was incorrect.

### Snapshot event F-SLEEP-SAFETY-TRANSITION-r2

- Finding ID(s): F-SLEEP-SAFETY-TRANSITION
- Source finding author(s): Codex source worker
- Status: submitted
- Immutable snapshot commit: `77192ea412d8af36bdf2b4fe66c4db48459e45d6`
- Finding file path and SHA-256: `workflows/source-campaign-2026-10-07/1.11.2--1.12.2/findings/F-SLEEP-SAFETY-TRANSITION.md`; `ea3b84e4da7733a41832fce9af55c2dc15cec9e62a41f969f6d96f8be7bceb3b`
- Exact A/B artifact-manifest identities/hashes: A `1.11.2/ornithe-feather/artifacts.sha256`, `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`; B `1.12.2/ornithe-feather/artifacts.sha256`, `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`; both source manifests match their ready records; revised artifact revision `feather-r1-2026-10-07`, A snapshot `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`, B snapshot `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`.
- Cited source/resource hashes: A PlayerEntity `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`; B PlayerEntity `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; B MonsterEntity `e96f44cf14e80d3154fdd3a8e6827000bf0f13a545ee095c1ebedac7993e41b6`; B ZombiePigmanEntity `aba397ad24f519dbdfcdd594a1e9ecd8ad26ef36ea030b54b7f2be35d06f9701`; see finding for remaining paired source hashes and line ranges.
- Verified implementation boundary/evidence, or unresolved boundary reason: under the stated server-side bed conditions, only a non-angry zombie pigman no longer blocks sleep; B consequently reaches the player size, bed-position, sleeping-state and velocity writes. Full packet-authority and broader pose inventories remain outside this finding and open in S1.7/S2.2.
- Finding-specific closed dependency IDs/evidence: direct BedBlock-to-server sleep check, anger predicate and override, player state writes, server sleep packet emission, client sleep-packet consumer, source/raw input manifests, immutable revision checksums and independent ops audit were inspected and verified. Broad S1.7 packet-authority closure remains outside the bounded finding.
- Independent blind source reviewer and decision date: fresh source-only review pending; prior reviewer identity/date await coordinator relay.
- Review basis / requested source-only revisions: prior review found a B-side PlayerEntity hash typo; recomputation against the exact ready source gives the corrected hash above. Revised artifact snapshots, source trees and raw inputs were freshly checked by the discovery author; ops confirmation is pending.
- Pair run status and commit at handoff: active at `77192ea412d8af36bdf2b4fe66c4db48459e45d6`.
- Pair complete: no
- Implementation handoff: blocked; independent ops confirmation and fresh blind finding review remain pending.
- Replaces/supersedes snapshot ID and reason, if applicable: replaces `F-SLEEP-SAFETY-TRANSITION-r1`; the B PlayerEntity hash was corrected and the revision provenance was added.

### Snapshot event F-SLEEP-SAFETY-TRANSITION-r2-superseded

- Finding ID(s): F-SLEEP-SAFETY-TRANSITION
- Source finding author(s): Codex source worker
- Status: superseded
- Immutable snapshot commit: `77192ea412d8af36bdf2b4fe66c4db48459e45d6`
- Finding file path and SHA-256: `workflows/source-campaign-2026-10-07/1.11.2--1.12.2/findings/F-SLEEP-SAFETY-TRANSITION.md`; `ea3b84e4da7733a41832fce9af55c2dc15cec9e62a41f969f6d96f8be7bceb3b`
- Exact A/B artifact-manifest identities/hashes: A `1.11.2/ornithe-feather/artifacts.sha256`, `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`; B `1.12.2/ornithe-feather/artifacts.sha256`, `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`; revision `feather-r1-2026-10-07` was cited, but independent ops confirmation was not yet recorded in that finding snapshot.
- Cited source/resource hashes: corrected A/B source evidence as in r2; finding file SHA recorded above.
- Verified implementation boundary/evidence, or unresolved boundary reason: corrected B-side PlayerEntity source hash and source-worker artifact verification were present; ops audit result and revision record file hashes were not yet included in the finding text.
- Finding-specific closed dependency IDs/evidence: direct sleep route inspected; independent ops confirmation was pending at r2.
- Independent blind source reviewer and decision date: fresh review pending.
- Review basis / requested source-only revisions: superseded to capture the later independent ops pass and exact revision.json hashes in the immutable finding evidence.
- Pair run status and commit at handoff: active at `77192ea412d8af36bdf2b4fe66c4db48459e45d6`.
- Pair complete: no
- Implementation handoff: blocked; r2 lacked the subsequent ops confirmation.
- Replaces/supersedes snapshot ID and reason, if applicable: replaced by `F-SLEEP-SAFETY-TRANSITION-r3` after independent ops passed the revision and the finding citation was expanded.

### Snapshot event F-SLEEP-SAFETY-TRANSITION-r3

- Finding ID(s): F-SLEEP-SAFETY-TRANSITION
- Source finding author(s): Codex source worker
- Status: submitted
- Immutable snapshot commit: `cdc8db327b9d99fb6344c1f98e2f12f8774dc6a1`
- Finding file path and SHA-256: `workflows/source-campaign-2026-10-07/1.11.2--1.12.2/findings/F-SLEEP-SAFETY-TRANSITION.md`; `d98ec64359ac2cc8857383a9f6ddffdcf3c3b003c8752f9b85bfb290f470b797`
- Exact A/B artifact-manifest identities/hashes: A original artifact manifest `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`, source manifest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; B original artifact manifest `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`, source manifest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`. Revised artifact revision `feather-r1-2026-10-07`: A immutable JAR hash `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`, revision.json hash `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`; B immutable JAR hash `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`, revision.json hash `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`.
- Cited source/resource hashes: A PlayerEntity `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`; B PlayerEntity `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; B MonsterEntity `e96f44cf14e80d3154fdd3a8e6827000bf0f13a545ee095c1ebedac7993e41b6`; B ZombiePigmanEntity `aba397ad24f519dbdfcdd594a1e9ecd8ad26ef36ea030b54b7f2be35d06f9701`; remaining cited source hashes and exact ranges are in the finding file.
- Verified implementation boundary/evidence, or unresolved boundary reason: under the stated server-side bed condition, A rejects sleep because any monster in the box blocks it; B permits it when the only monster is a non-angry zombie pigman, reaching the player dimensions, bed position, sleeping flag and velocity writes. Full pair packet-authority and pose-provider closure remain open outside this bounded finding.
- Finding-specific closed dependency IDs/evidence: the bed interaction, server eligibility predicate, anger default/override, player writes and client/server sleep packet path are traced; `DEP-ARTIFACT-INTEGRITY` is closed for this pair by the discovery author's fresh source/raw/snapshot verification and independent ops pass. Original mapped JAR equivalence remains unproven.
- Independent blind source reviewer and decision date: fresh acceptance review pending; the prior reviewer requested and received the corrected source hash, but identity/date are not yet supplied.
- Review basis / requested source-only revisions: replacement snapshot records the corrected B PlayerEntity hash, exact revision paths/hashes and `revision.json` hashes, unchanged original source/raw manifest hashes, independent ops pass, and the explicit unavailable-original limitation.
- Pair run status and commit at handoff: active at `cdc8db327b9d99fb6344c1f98e2f12f8774dc6a1`.
- Pair complete: no
- Implementation handoff: blocked pending independent blind acceptance of this exact snapshot.
- Replaces/supersedes snapshot ID and reason, if applicable: supersedes `F-SLEEP-SAFETY-TRANSITION-r2`; r2 preceded ops confirmation and did not record revision.json hashes. The earlier invalidated r1 remains in history.

<!-- Append subsequent events; preserve prior records and commits. -->

### Snapshot event F-FARMLAND-PLAYER-RELOCATION-r1

- Finding ID(s): F-FARMLAND-PLAYER-RELOCATION
- Source finding author(s): Codex source worker
- Status: submitted
- Immutable snapshot commit: `6b0a9c7bbbe7822b558d95ed6435db2516f50eba`
- Finding file path and SHA-256: `workflows/source-campaign-2026-10-07/1.11.2--1.12.2/findings/F-FARMLAND-PLAYER-RELOCATION.md`; `6221cafeb9c42d30e839176322f0f20870a28d21a6b34edb341cf67368e23e84`
- Exact A/B evidence artifact records and revised artifact hashes: `EA-FEATHER-R1-1.11.2`, `EA-FEATHER-R1-1.12.2`; A JAR `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`, revision.json `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`; B JAR `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`, revision.json `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`. Original source/artifact manifest hashes and equivalence limitations are preserved in the finding.
- Cited source/resource hashes: exact `FarmlandBlock`, `Entity`, `World` and `Block` A/B hashes are in the finding and bounded slice S4.4a.
- Verified implementation boundary/evidence, or unresolved boundary reason: source confirms the landed-player server callback path and the different position writers; B additionally resets position history and calls `World.tickEntity(entity,false)`. Other `setDirt` callers and full parent inventories remain outside this snapshot.
- Finding-specific closed dependency IDs/evidence: `INV-COLLISION`, `INV-STATE`, and `INV-WORLD-MOVEMENT` evidence for this bounded path only; broad inventory closure remains pending.
- Independent blind source reviewer and decision date: reviewer assignment and review pending.
- Review basis / requested source-only revisions: independently re-walk exact A/B source bodies, guards, `Entity.teleport` side effects and cited artifact identity before accepting or requesting a correction.
- Pair run status and commit at handoff: active at `6b0a9c7bbbe7822b558d95ed6435db2516f50eba`.
- Pair complete: no
- Implementation handoff: awaiting independent blind acceptance of this exact snapshot.
- Replaces/supersedes snapshot ID and reason, if applicable: none; first snapshot for this finding.

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

- Coverage counts by status: 32 pending; 2 in-progress; 8 compared-no-difference; 3 not-applicable; 2 findings; 0 blocked (47 slices total).
- Required inventory status/evidence: INV-EXCLUSIONS complete; all other inventories pending with mapped slice IDs.
- Open dependencies: DEP-TICK-CALLGRAPH, DEP-COLLISION-SHAPES, DEP-MODIFIER-DATA, DEP-EXTERNAL-WRITERS, DEP-OLD-CANDIDATES, DEP-DIAGNOSTICS, DEP-RELATIVE-MOVE
- Unresolved gaps: stages beyond bounded keyboard input and completed UI/tutor dispositions; S2.2 shape providers, glide movement, exact state producers/consumers, registries/resources, external writers and historical candidates remain open.
- Evidence/hash/correspondence audit: source manifests, every source file, all raw-input entries and both revised artifact snapshots freshly verified under `feather-r1-2026-10-07`; independent ops audit passed; original derived mapped JARs unavailable; equivalence unproven; remaining source evidence pending.
- Blind freeze: pending
- Implementation reconciliation: pending and deferred
- Independent audit: pending reviewer assignment
- Runtime validation: not performed (separate workflow; not authorized).

Run folder contents are limited to `run.md` and optional `findings/*.md`; the completion checker validates schema/status only and cannot establish source truth.
