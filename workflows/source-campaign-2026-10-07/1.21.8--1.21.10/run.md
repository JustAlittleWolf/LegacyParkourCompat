# Discovery: 1.21.8 to 1.21.10

- Run status: active
- Scope: direct client-player movement and direct player-facing velocity/impulse/knockback response; older A = `1.21.8`; newer B = `1.21.10`.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`), 2026-10-07.
- Work branch: `feat/source-discovery-movement-source-1-21-8-1-21-10`.
- Selected naming namespace: `mojmap` on both sides; exact releases and same-family alignment verified against both readiness records.
- Source preparation owner / command / log / readiness marker: exclusive shared source owner; this worker ran no decompiler. Exact command, success log, markers and hashes are recorded below.
- Toolchain/decompiler/remapper versions and options: recorded below from both provenance records; common run used 4G decompiler heap.
- Discovery author(s): Codex source-only worker for this pair.
- Independent reviewer: pending coordinator assignment; must differ from discovery author.

## Artifact manifest

Both exact endpoints have validated readiness markers with `status=ready`, `versionId` and version metadata IDs matching the requested releases, and `mapping=mojmap`. The common source run was `.\gradlew.bat decompileMinecraft --versions=1.21.8,1.21.10,1.21.11 --mappings=mojmap --decompiler-heap=4G` with output/cache under `staging/mojmap-1.21.8-to-1.21.11-9a8c76419cd14660b88f9fc90225e127/`. The successful full log is `staging/mojmap-1.21.8-to-1.21.11-9a8c76419cd14660b88f9fc90225e127/gradle.full.log`; each per-version success log says `BUILD SUCCESSFUL; exit code 0` and the exact metadata ID was verified. Readiness JSONs and provenance are `ready/1.21.8/mojmap.ready.json`, `ready/1.21.8/mojmap.provenance.json`, `ready/1.21.10/mojmap.ready.json`, and `ready/1.21.10/mojmap.provenance.json`.

Full manifest verification completed before comparison: all 6,105 A source files and 6,386 B source files match their listed SHA-256 values; all 257 staged jar/mapping/library artifacts match `ready/1.21.8/artifacts.sha256`. The source-manifest hashes, shared artifact-manifest hash, and movement-diagnostic hashes match the values in both readiness records. No mismatches. Each cited source member must still be referenced by its exact file hash below; matching names alone are not evidence.

- A / 1.21.8: source root `ready/1.21.8/mojmap`; client jar SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`; Mojang client mappings SHA-256 `dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca`; remapped `client-mojmap.jar` SHA-256 `f6f0339631f43b38ce5fe7511603cde17aa4c804ad627d933835f228003adb85`; `version.json` SHA-256 `3bf5cdd444dfa89199fe4f8a92b6c79f4e4fa8977ff52029c3acd99378506fad`, metadata ID `1.21.8`; source manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` (6,105 files); shared artifact manifest SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` (257 entries); movement diagnostics SHA-256 `c954da48013372c7b423bf47597b447d32607b2c39b00e3cf353b1d5fcc95806`.
- B / 1.21.10: source root `ready/1.21.10/mojmap`; client jar SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`; Mojang client mappings SHA-256 `2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39`; remapped `client-mojmap.jar` SHA-256 `0885181c5e4c2f21dd2f95591dd095fa0176807edbe239ef77218e861f0751da`; `version.json` SHA-256 `15faa7deb7cd39479db123ad4664e2fb83e05d4729b7e4a8ff813107376e349a`, metadata ID `1.21.10`; source manifest SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` (6,386 files); shared artifact manifest SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` (257 entries); movement diagnostics SHA-256 `5642b893edbb01bc1dd57386e013c61572c20f75fd25542877bace1f1f457eb0`.
- Mapping alignment: both use explicit Mojmap; each version’s own published `client_mappings.txt` was used, never a cross-version mapping. Tool versions from provenance: Gradle 9.7.1, Java 25.0.3+9-LTS, Vineflower 1.12.0, TinyRemapper 0.14.1, Mapping-IO 0.9.1, ASM 9.10.1 and Gson 2.14.0.
- Both movement diagnostics confirm exact-ID success and list these source bodies for focused review: `Entity.move`, `Entity.moveRelative`, `LivingEntity.jumpFromGround`, `LivingEntity.travel`, `LivingEntity.aiStep`, `Player.aiStep`, `Player.travel`, `LocalPlayer.aiStep`, and `LocalPlayer.move`. This nine-body diagnostic list is a useful seed, not an exhaustive coverage proof. Resource entry names/hashes and other cited source hashes are added with their bounded slices.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: not frozen; initial schema/coverage checkpoint is commit `e7f6ab7`, amended to this schema in a later checkpoint.
- Evidence inventory and finding IDs included at freeze: no freeze yet; source comparison is underway, with three unconfirmed candidates logged in the resume checkpoint and no accepted snapshots.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened: confirmed. Repository/workflow docs and exact vanilla source trees were read after source readiness verification. No mod implementation/source diff, Minecraft Wiki, MCPK, or wiki-audit output was opened.
- Source/mapping hashes covered by freeze: none yet.

## Correspondence and call order

The exact pair is published and validated; indexing is partial, not a complete caller graph. The current call-chain seed is `ClientPacketListener` -> `LocalPlayer.tick` -> inherited `Player.tick` / `LivingEntity.tick` -> virtual `LocalPlayer.aiStep` -> `Player.aiStep` -> `LivingEntity.aiStep` / travel; `LocalPlayer` installs `KeyboardInput`, whose key mappings populate the player input. `ClientLevel.tickEntities` ticks non-passenger entities, with passenger players going through `rideTick`; in B, block-entity ticking moved to the adjacent `Minecraft.tick` call immediately after `level.tickEntities`, preserving order relative to player entity ticking. See source hashes and ranges to be added as member slices. Full per-branch correspondence, writers/readers, collision path and resource/dependency graph remain open. Do not infer correspondence from Mojmap names alone.

## Required source inventories

Each inventory links to bounded slices below. The exact pair and full source manifests are verified; bounded inventory work has begun, but none of the seven inventories is complete.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1.1,S1.2,S1.3,S1.4,S3.1,S3.2,S3.3,S3.4; evidence=partial work only: validated paired tick/input sources opened; exact caller graph and all branches remain incomplete.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S2.1,S2.2,S4.1,S4.3; evidence=partial work only: selected direct predicates and incoming player state writers compared; full writer/consumer walk remains open.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4.1,S4.2,S4.3,S5.1; evidence=source pair available; registrations and shape inventory not yet walked.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S5.1,S5.2,S5.3; evidence=source pair available; client-jar resource/data inventory not yet walked.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S6.1,S6.2; evidence=source pair available; resource/data closure not yet walked.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S7.1,S7.2; evidence=partial work only: player motion/correction packet consumers inspected in both endpoints; remaining impulse, push, piston and transition paths are unindexed.
- `INV-EXCLUSIONS` explicit scope audit for health/food state production, attack/damage resolution, non-player movement and vehicle physics. Direct player-motion response remains in scope even when combat can trigger it: status=pending; evidence=after source inventory, enumerate direct vanilla-state reads retained in movement predicates without emulating their producer systems.

## Coverage ledger

The following are pending scope atoms for source-led member indexing. Each must be split further whenever source methods/dependencies exceed a bounded behavior. Pending entries do not assert that methods are unchanged or establish final coverage.

### Slice S1.1: Local player tick and pre-travel/travel/post-travel call order

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: input sampling through local/super tick, pre-travel, travel dispatch/branches and post-travel; exact boundaries still being split into method slices.
- A evidence: `LocalPlayer.java` SHA-256 `53F2A71A886B9C71853AFE36F2DF857F80A9BF1CD6EC4CA8604CCD7E238D89EE`; `ClientLevel.java` SHA-256 `18219267F00CAE67E19F9243A15D7193111F8182021C57CC057728FC4E103F0D`; `LocalPlayer.tick` (197), `LocalPlayer.aiStep` (683), `ClientLevel.tickEntities` (273), `tickNonPassenger` (294), `tickPassenger` (306) opened.
- B evidence: `LocalPlayer.java` SHA-256 `9C1DF00E2F8379B2C19577A3691FEE28071D8925210BE3D3DF928B5352E367E1`; `ClientLevel.java` SHA-256 `2A4BF7BAC40707BF0D7D2FEAA1F6564F5AFF7AAE1B923CF94270165425DC8EE3`; corresponding `LocalPlayer` methods at 195 and 687, `tickEntities` (321), `tickNonPassenger` (338), `tickPassenger` (350) opened.
- State producers/writers -> consumers/readers: pending full-tick state graph.
- Parent slices / dependencies / closure evidence: source readiness D1; correspondence and full caller graph.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): virtual local-player tick chain and client entity scheduler partially indexed; no movement equivalence or terminal disposition claimed. Split into exact method slices and finish all travel/post-travel branches before closure.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S1.2: Input sampling, yaw-to-motion conversion and input scaling

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: player-reachable input sampling/conversion, normalization, sneak/item-use scaling and producer timing; keyboard collision path is partially indexed.
- A evidence: `KeyMapping.MAP` declaration at 16, `set` 48-53 and `resetMapping` 77-83 in `KeyMapping.java` SHA-256 `D5475012D2849E7547E23D1584D5B099661A2CB1E60DAEBE6EC19CB28B779111`; `KeyboardHandler.keyPress` event dispatch 460,493-496 in SHA-256 `EA1D04D02829D0AFA75D376C9478D092971337FF14F49CF6E4A240572991DC74`; `Options` movement mappings 484-490 and per-mapping persistence 1251-1257 in SHA-256 `24B6E50A9376761ADFC935113DA78B58D11A88376097F9A6A5947B520CD3A3C3`; `KeyBindsScreen.keyPressed` 70-85 in SHA-256 `37FB7AF767757BC73759BDAC2CD0E7577BED98D18D2566E172D16C381B5CF909` accepts an occupied key and rebuilds the mapping; `KeyboardInput.tick` 23-36 in SHA-256 `D5CB0E93DF7F66755172D74E028225012EE33C6E4F0D510A1D8E25BA5497C0A3`; local input is constructed as `KeyboardInput` by `ClientPacketListener` at 516 (same listener hash recorded in S7.1). For use-item input, `Options.keyUse` is an ordinary mapping at 494, and `ControlsScreen.options` at 14-15 exposes crouch/sprint toggles but no use toggle; hashes: `Options.java` above, `ControlsScreen.java` `1C718BA8CBD5A28FC4709AB58E52C8731F82B209F7E649D9E7EA567F4A2D107B`. `Minecraft.handleKeybinds` lines 1911-1942 uses `keyUse.isDown()` to continue or release item use; `Minecraft.java` SHA-256 `8508BCB2BBE4D48B3628BF4C87F87F719D07B0F60E7811A8BC6322513E55F973`.
- B evidence: `KeyMapping.MAP` declaration at 21, `set` 33-35, `forAllKeyMappings` 37-44, `resetMapping` 78-84 and `registerMapping` 182-184 in `KeyMapping.java` SHA-256 `6AA1388D6348684DF6E85E3227B186839B4528FAE59A5595436538D7101858A9`; `KeyboardHandler.keyPress` event dispatch 502,525,556-559 in SHA-256 `6CC5209BCF738DB31761E3B76F3B9A2C3CF5D7BEEA53B08B3FE719B0B86B0AB1`; `Options` movement mappings 520-526 and per-mapping persistence 1321-1327 in SHA-256 `791DF2C2FD5B37C6E8C3D7775EF7797A20A5839DB00445B383CBC37662E7CEC8`; `KeyBindsScreen.keyPressed` 72-87 in SHA-256 `EE97164EAC9A048A2D159B95CED1B1F998F6090B48BF7F99AB3753D75A5EDB42` likewise accepts an occupied key and rebuilds; identical `KeyboardInput.tick` 23-36; local input is constructed as `KeyboardInput` by `ClientPacketListener` at 502 (same listener hash recorded in S7.1). For use-item input, `Options.toggleUse` at 492-494 (default false), `keyUse` as `ToggleKeyMapping` at 530, and serialization at 1267 are in `Options.java` SHA-256 `791DF2C2FD5B37C6E8C3D7775EF7797A20A5839DB00445B383CBC37662E7CEC8`; `ControlsScreen.options` at 14-17 exposes the toggle in `ControlsScreen.java` SHA-256 `E3531BB471440179FF38D46A776CE76F3D9915E09055F1EEC90A9E5E14CED4B6`; `ToggleKeyMapping.setDown`, lines 27-35, in SHA-256 `C580F39E7D4202C7C29B11E5E2132D447BEFA36D7A5ADA8E898739A90F744CD7` toggles on press and leaves the state set on release when configured to toggle; `Minecraft.handleKeybinds` lines 1935-1970 in `Minecraft.java` SHA-256 `AE782D427FF3F2B0A15BD58FE447BC3A07B216FAED243A7618420F816516BE50` continues or releases item use from that state.
- State producers/writers -> consumers/readers: input/key/options producers -> movement input fields -> pre-travel/travel consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S1.1; source readiness D1.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A maps one physical key to one movement mapping (`Map<Key,KeyMapping>`); B maps it to and fans events out to every mapping (`Map<Key,List<KeyMapping>>`). Both `KeyBindsScreen` implementations permit assigning an occupied key; movement mappings are individually persisted; the local player's `KeyboardInput` reads `keyUp`/`keyDown` and computes zero when those booleans match. Concrete example: assign the same key to forward and backward, start with both released, then press it. In B both mappings become down and the forward-minus-backward impulse is zero; in A only the single map winner is set, so the impulse is nonzero in one direction. The identity of A's winning mapping depends on `ALL` iteration order and is not asserted. A second candidate is B's new optional toggle-use mode: when enabled, the mouse-use mapping stays down after release, `Minecraft.handleKeybinds` therefore continues item use, and the same player's `LocalPlayer.modifyInput` applies its 0.2 use-item multiplier. Default false matches A's held mapping. Finish the input-to-travel reachability and bounded evidence before terminal disposition.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S1.6: Local movement-input slowdown and diagonal normalization

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.applyInput` writes local forward/sideways input; `modifyInput` applies base, use-item and slow-movement scaling, then square-movement normalization.
- A evidence: `LocalPlayer.isMovingSlowly`, `applyInput`, `modifyInput`, `modifyInputSpeedForSquareMovement`, and `distanceToUnitSquare`, lines 603-660, in `LocalPlayer.java` SHA-256 `53F2A71A886B9C71853AFE36F2DF857F80A9BF1CD6EC4CA8604CCD7E238D89EE`.
- B evidence: corresponding methods, lines 607-664, in `LocalPlayer.java` SHA-256 `9C1DF00E2F8379B2C19577A3691FEE28071D8925210BE3D3DF928B5352E367E1`.
- State producers/writers -> consumers/readers: input vector + item-use/crouch/crawl/sneak-speed attribute -> `xxa`/`zza` and jumping fields -> inherited living travel input handling.
- Parent slices / dependencies / closure evidence: S1.1, S1.2; endpoint source manifests verified; paired method bodies are text-identical in this bounded behavior.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): all five local transformation methods and the camera-control branch match in the inspected source; coefficients, operation order, guards and writes are unchanged. Broader input event mapping remains a separate open candidate in S1.2.
- Finding IDs or checked absence/replacement path: none; exact paired methods compared.

### Slice S1.3: Sprint/jump gates, timers and impulses before travel

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: sprint start/stop, jump input/cooldown/state and reachable pre-travel impulse ordering; boundaries pending.
- A evidence: `LocalPlayer.java` SHA-256 `53F2A71A886B9C71853AFE36F2DF857F80A9BF1CD6EC4CA8604CCD7E238D89EE`; `aiStep` at 683 has the fixed seven-tick assignment at 727; sprint stop gates at 837-852 and `canStartSprinting` at 1065-1075 opened. `Options.java` SHA-256 `24B6E50A9376761ADFC935113DA78B58D11A88376097F9A6A5947B520CD3A3C3` has no `sprintWindow` option/accessor/serialization member. `Entity.isUnderWater` is at 1394-1396 in `Entity.java` SHA-256 `C403E6176D27B5BFD6AAA0DEA3735FFA4FCF80DBAE58766661DD84453B7E9704`; `LivingEntity.travelInFluid` (2301-2351) reads `isSprinting()` to choose water drag in `LivingEntity.java` SHA-256 `609F0197A0B4551AB42279E452C11CDD256135B1D467C2A95950B0E9FD7DDEF8`.
- B evidence: `LocalPlayer.java` SHA-256 `9C1DF00E2F8379B2C19577A3691FEE28071D8925210BE3D3DF928B5352E367E1`; `aiStep` at 687 reads `options.sprintWindow` at 731; new shared predicate at 1055-1060, start/stop gates at 841-848, `canStartSprinting` at 1062-1069. `Options.java` SHA-256 `791DF2C2FD5B37C6E8C3D7775EF7797A20A5839DB00445B383CBC37662E7CEC8` defines range 0..10/default 7 at 496-505 and serializes at 1268. `Entity.isInShallowWater` (1454-1456) is in `Entity.java` SHA-256 `8361DBB86FE6C975D21F69D008377B6F191669BE751150C842517E9D0346FA18`; `LivingEntity.travelInFluid` (2331-2381) reads `isSprinting()` to choose water drag in `LivingEntity.java` SHA-256 `B8B49D60769203F7BD5AFE4A1BFFCDCDBEC30BE28960324CDC43A2DF85A6EB66`.
- State producers/writers -> consumers/readers: sprint/jump input and timer writers -> gates, velocity writers and travel consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S1.1, S1.2; movement predicates may read vanilla food/hunger state, whose producer systems remain excluded (S8.1).
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A rejects sprint start/continuation in water when not underwater; B's `isSprintingPossible(flying)` bypasses that shallow-water restriction when `abilities.flying` is true. In the shared water-travel path, sprinting selects `0.9F` drag instead of the configured water slowdown, so this predicate difference reaches player motion; the option difference changes second-tap eligibility timing when configured away from 7. The default remains 7. Food is only read by the direct gate; food producer systems remain excluded. All jump/pre-travel impulse writers and the rest of the tick branches still need tracing before disposition.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S1.4: Auto-jump, flight toggles, unstuck and riding gates

- Inventory ID(s): INV-TICK, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: player-local control branches and state transitions affecting movement; boundaries pending.
- A evidence: validated `1.21.8` Mojmap source; this control slice has not been indexed yet.
- B evidence: validated `1.21.10` Mojmap source; this control slice has not been indexed yet.
- State producers/writers -> consumers/readers: control inputs/abilities/riding transitions -> movement flags and velocity/position consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S1.1; S7.2; source readiness D1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S1.5: Portal cooldown ticking while the client waits for a replacement level

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.aiStep` suppresses portal transition effects and cooldown decrement under the active level-receive/loading screen; compare screen creation path and cooldown body.
- A evidence: `LocalPlayer.aiStep`, lines 688-690, in `LocalPlayer.java` SHA-256 `53F2A71A886B9C71853AFE36F2DF857F80A9BF1CD6EC4CA8604CCD7E238D89EE`; `ClientPacketListener.startWaitingForNewLevel`, lines 1533-1536, in `ClientPacketListener.java` SHA-256 `A66556678A93C63C0CEB8953F25261B82BF3BB1971048FBC15B177C6DFFEA8CD` creates `ReceivingLevelScreen`; `Entity.processPortalCooldown`, lines 553-556, in `Entity.java` SHA-256 `C403E6176D27B5BFD6AAA0DEA3735FFA4FCF80DBAE58766661DD84453B7E9704` decrements the timer.
- B evidence: `LocalPlayer.aiStep`, lines 692-694, in `LocalPlayer.java` SHA-256 `9C1DF00E2F8379B2C19577A3691FEE28071D8925210BE3D3DF928B5352E367E1`; `ClientPacketListener.startWaitingForNewLevel`, lines 1538-1549, in `ClientPacketListener.java` SHA-256 `1BB341EE18704D882BB68BF917190BE1045649026A99545057118B5CB9BFB348` creates or updates `LevelLoadingScreen`; `Entity.processPortalCooldown`, lines 560-564, in `Entity.java` SHA-256 `8361DBB86FE6C975D21F69D008377B6F191669BE751150C842517E9D0346FA18` has the same decrement body.
- State producers/writers -> consumers/readers: loading-screen lifecycle -> LocalPlayer cooldown-tick guard -> portal cooldown timer -> portal-entry eligibility.
- Parent slices / dependencies / closure evidence: S1.1 call order; S7.2 transition scope; exact paired loading-screen constructors and cooldown bodies.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the guarded legacy `ReceivingLevelScreen` is created by the corresponding `startWaitingForNewLevel` path in A; B uses `LevelLoadingScreen` for that same path and may update an already-visible instance. Both guards suppress this cooldown decrement during the respective level-wait screen, and the timer decrement body is identical. The guard also wraps visual portal-effect bookkeeping, which is not player movement. No pair-wide conclusion is implied.
- Finding IDs or checked absence/replacement path: none; exact screen replacement path checked.

### Slice S2.1: Pose, dimensions and eye height affecting movement/query timing

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: pose selection, resize timing, dimensions and eye-height consumers relevant to collision/fluid/player movement; boundaries pending.
- A evidence: pending source indexing for this pose and dimension boundary.
- B evidence: pending source indexing for this pose and dimension boundary.
- State producers/writers -> consumers/readers: pose/dimension writers -> bounding box, collision and fluid query consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S4.1; resource/pose definitions if reachable; source readiness D1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S2.2: Movement state writers, direct gates, item use and edge sneaking

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: initialization/update/reset of movement flags/timers, item-use slowdown, edge-sneak logic and direct movement predicates; exclude health/food producer simulation.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: all discovered writers -> readers in local tick/travel/collision; exact edges pending.
- Parent slices / dependencies / closure evidence: S1.1-S1.4, S2.1, S8.1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; writer inventory required before any closure.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S3.1: Ground and air acceleration, friction and speed input

- Inventory ID(s): INV-TICK, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: bounded ground/air travel branches and their direct movement-speed/friction dependencies; exact member slices pending.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: input, on-ground state, movement attributes and block factors -> acceleration/velocity consumers; exact edges pending.
- Parent slices / dependencies / closure evidence: S1.1-S1.3, S4.2-S4.3, S5.2, S6.1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; preserve expression order, casts and cutoffs in member-level comparison.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S3.2: Gravity, drag, negligible velocity thresholds and post-travel updates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: travel math and ensuing state updates affecting player velocity/position; split exact methods after inventory.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: velocity/gravity/drag writers -> threshold checks, collision movement and post-travel consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S1.1, S3.1, S4.2-S4.3.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S3.3: Jump, sprint-jump and climbing movement

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: jump power/impulse and climbing branch/clamps, including ordering against collisions and travel updates; boundaries pending.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: jump/sprint inputs, effect/attribute/block state -> velocity writers and travel consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S1.3, S3.1, S4.3, S5.2, S6.1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S3.4: Water/lava, swimming, gliding and other fluid travel branches

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: branch predicates, fluid travel math, swimming/gliding and post-branch state; split by bounded source member.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: fluid contact/height/flow, equipment/effects/attributes -> travel velocity and flags; exact edges pending.
- Parent slices / dependencies / closure evidence: S2.1, S3.1-S3.2, S4.3, S5.3, S6.1-S6.2.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S4.1: Bounding-box resize and collision-query timing

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: player box updates and query timing across pose/tick movement, including repeated queries within a tick; boundaries pending.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: pose/dimensions/position -> AABB and world-query consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S2.1-S2.2, S4.2-S4.3.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S4.2: Axis collision resolution and step candidates/tie-breaking

- Inventory ID(s): INV-COLLISION, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: player movement box clipping, axis order, step-up candidates, candidate selection/tie breaks and enclosing conditions; split exact methods after inventory.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: desired movement and shapes -> clipped displacement, collision flags and resulting velocity; exact edges pending.
- Parent slices / dependencies / closure evidence: S4.1, S4.3, S5.1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S4.3: Support, on-ground flags, velocity response, fluids and callbacks

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-TICK, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: support/ground lookup, collision-flag writers, velocity cancellation/restitution, fluid push and callback order; split exact methods as needed.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: collision and support result writers -> next travel gates; callbacks/fluids -> player velocity/flags; exact edges pending.
- Parent slices / dependencies / closure evidence: S2.1, S3.1-S3.4, S4.1-S4.2, S5.1-S5.3.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S5.1: Historical block shape providers, registrations and neighbor dependencies

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: blocks present in A and B whose player collision/support shapes or shape contexts can change movement; enumerate providers, registration and neighboring-state dependencies; block states remain vanilla.
- A evidence: pending validated `1.21.8` Mojmap source/resources; no source range/hash yet.
- B evidence: pending validated `1.21.10` Mojmap source/resources; no source range/hash yet.
- State producers/writers -> consumers/readers: state/context/neighbors -> shape provider -> player world collision/support query; exact edges pending.
- Parent slices / dependencies / closure evidence: S4.1-S4.3; registry, resource, tags and subclasses inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; modern-only blocks do not receive historical behavior.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S5.2: Block friction, speed/jump factors and movement callbacks

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-TICK, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: registered/overridden movement factors and player-reachable callbacks (ice/soul sand/slime/bed/web/honey/snow/climbables/pistons and newly discovered entries); split by provider/callback.
- A evidence: pending validated `1.21.8` Mojmap source/resources; no source range/hash yet.
- B evidence: pending validated `1.21.10` Mojmap source/resources; no source range/hash yet.
- State producers/writers -> consumers/readers: block/provider registry and contact state -> acceleration, jump, collision or player callback consumers; exact edges pending.
- Parent slices / dependencies / closure evidence: S3.1, S3.3, S4.3, S5.1; client-jar resource entries and referenced data.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; inventory is not limited to previously known block names.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S5.3: Fluid contact, height, flow vectors and bubble-column movement

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: fluid state/contact/height/flow and player push/travel paths, including relevant registry/data dependencies; split exact helpers.
- A evidence: pending validated `1.21.8` Mojmap source/resources; no source range/hash yet.
- B evidence: pending validated `1.21.10` Mojmap source/resources; no source range/hash yet.
- State producers/writers -> consumers/readers: fluid blocks/state/data -> contact, flow, height/push -> player velocity/travel consumers; exact edges pending.
- Parent slices / dependencies / closure evidence: S3.4, S4.3; source and jar resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S6.1: Movement attribute/effect consumers, aggregation and application

- Inventory ID(s): INV-MODIFIERS, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: movement-speed/jump/gravity/other movement attributes and Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness consumers; trace operations, order, defaults, modifiers and application/removal.
- A evidence: pending validated `1.21.8` Mojmap source/resources; no source range/hash yet.
- B evidence: pending validated `1.21.10` Mojmap source/resources; no source range/hash yet.
- State producers/writers -> consumers/readers: effect/attribute registration/application -> aggregation -> travel/jump/player predicates; exact edges pending.
- Parent slices / dependencies / closure evidence: S3.1-S3.4; source registrations, resource definitions/tags and external synchronization boundary.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; presence/absence and data-driven definitions need evidence.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S6.2: Enchantments, equipment and movement item components

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide, Elytra, use-item slowdown, relevant equipment slots/components and other registry-discovered movement entries; trace formulas, conditions, tags and server boundaries.
- A evidence: pending validated `1.21.8` Mojmap source/resources; no source range/hash yet.
- B evidence: pending validated `1.21.10` Mojmap source/resources; no source range/hash yet.
- State producers/writers -> consumers/readers: equipment/effect/enchantment registration and application -> player movement consumers; exact edges pending.
- Parent slices / dependencies / closure evidence: S3.1-S3.4, S5.2-S5.3; jar resources, tags and synchronized external inputs.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; server-side Frost Walker/world mutations are not inferred from client movement source.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S7.1: Player velocity/position corrections and incoming movement inputs

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: client consumers and timing for server-supplied corrections/velocity; retain protocol values as external input rather than emulated vanilla producer rules.
- A evidence: `ClientPacketListener.java` SHA-256 `A66556678A93C63C0CEB8953F25261B82BF3BB1971048FBC15B177C6DFFEA8CD`; `handleSetEntityMotion` (598), `handleMovePlayer` (769) and `setValuesFromPositionPacket` (780) inspected.
- B evidence: `ClientPacketListener.java` SHA-256 `1BB341EE18704D882BB68BF917190BE1045649026A99545057118B5CB9BFB348`; corresponding methods at 592, 764 and 775 inspected.
- State producers/writers -> consumers/readers: inbound packet/correction consumer -> player position/velocity/flags -> next tick readers; exact members pending.
- Parent slices / dependencies / closure evidence: S1.1-S1.4, S2.2; networking consumers and state writers.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): incoming motion is passed to `lerpMotion` from the packet vector in A and a value record in B; position correction helper bodies match in the inspected movement assignments, while packet-threading APIs changed. Verify all surrounding relative-rotation/correction and velocity call sites; no terminal disposition claimed.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S7.2: Direct player velocity, impulse and knockback response plus player-facing transitions

- Inventory ID(s): INV-EXTERNAL, INV-COLLISION, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: direct player velocity/impulse/knockback response to native triggers and player-facing transition consumers; exclude the trigger's combat/damage cause and all non-player/vehicle physics.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: external event/client handler -> player position/velocity/state consumers; exact ownership boundary pending.
- Parent slices / dependencies / closure evidence: S1.4, S4.2-S4.3, S7.1; changed callback/event paths.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; inspect other-entity code only to explain a direct player effect.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S8.1: Excluded producer systems and direct vanilla-state reads

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: do not emulate health/food state production, attack/damage resolution or combat cause, non-player movement or vehicle physics; identify direct vanilla-state reads in reachable player movement predicates. Direct player velocity/impulse/knockback application remains in scope without tracing combat resolution.
- A evidence: pending validated `1.21.8` Mojmap movement-predicate source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap movement-predicate source; no source range yet.
- State producers/writers -> consumers/readers: excluded health/food/combat producer systems -> only record a directly reachable vanilla-state read -> player movement gate; direct player velocity writers -> player movement consumers remain in scope.
- Parent slices / dependencies / closure evidence: S1.3, S2.2, S6.1, S7.1-S7.2; full gate and impulse inventory required.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): scope audit not complete; direct vanilla-state consumer reads may be documented without emulating excluded producers.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.
## Dependency queue and blockers

- Open dependencies:
  - D1 / source owner / resolved: both exact Mojmap source trees and readiness records validated; source/artifact manifests and every listed file hash matched, IDs/namespace/toolchain/diagnostics checked (see Artifact manifest).
  - D2 / discovery worker / continue relevant-body diagnostics and source comparison, enumerate matching client-jar resources/tags/defaults, and classify vanilla defaults vs server-synchronized vs external inputs.
  - D3 / coordinator / assign independent source auditor before closure.

## Finding index

No findings have been confirmed or independently accepted. Source-only candidate queue (all unconfirmed; no implementation access): (1) B's duplicate-key fan-out can change simultaneous player movement inputs when a physical key is assigned to multiple movement mappings; (2) B adds optional toggle-use mode, which can prolong held item-use state and movement slowdown (default off, matching A); (3) B's sprint double-tap window is configurable, defaulting to A's fixed 7 ticks; (4) B's sprint predicate bypasses shallow-water restriction while flying, affecting start/continuation in that precondition. Exact callsites and downstream water drag are noted in S1.3; final movement input reachability and independent review remain outstanding. Zero confirmed is not equivalence.

## Finding snapshots (not pair freeze)

No finding snapshots have been submitted or accepted. Pair-wide discovery remains active. Any later accepted immutable finding snapshot will be recorded here without implying pair completion.

## Resume checkpoint

- Last completed slice: none; bounded source comparison is underway, with S1.1, S1.2, S1.3 and S7.1 in-progress. Exact endpoint source files are hash-verified. No slice has a terminal disposition.
- Next bounded slice: finish key event/rebinding call path and exact line-bounded evidence for S1.2; trace sprint predicate downstream movement consumers and close S1.3 dependencies; then continue the local tick/travel branch inventory.
- Outstanding dependencies and owners: D2 discovery worker; D3 coordinator.
- Current assumptions requiring verification: pair readiness/provenance and all source/artifact hashes are verified; exact method-body correspondence, call edges, and every movement-reachable state producer/consumer remain to be indexed.

## Implementation reconciliation

Do only after blind-discovery freeze and through the designated integrator; this source-only worker will not inspect or reconcile mod implementation.

- Reconciliation status: pending
- Repository revision inspected: not inspected in this source-only track.
- Finding -> implementation disposition/evidence: deferred to integrator after freeze.
- Existing implementation without a frozen source finding: deferred to integrator.
- Coverage gaps routed back to discovery slices: none yet; route after source freeze/reconciliation.

## Independent source audit

Independent reviewer not yet assigned; no source audit performed.

- Reviewer: pending coordinator assignment
- Status: pending
- Inventories and call-chain ranges re-walked: none
- Concrete missed-slice routes (or `none found`): pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: pending 17; in-progress 4; compared-no-difference 2; findings 0; not-applicable 0; blocked 0. Planned broad atoms still need splitting into member-bounded slices; no terminal disposition is claimed.
- Required inventory status and evidence: INV-TICK pending (partial sources indexed); INV-STATE pending (partial sources indexed); INV-COLLISION pending; INV-WORLD-MOVEMENT pending; INV-MODIFIERS pending; INV-EXTERNAL pending (partial packet consumers indexed); INV-EXCLUSIONS pending. No source inventories complete.
- Open dependencies: D2, D3
- Unresolved gaps and limits: source pair and manifests are verified, but broad client movement/input, state, collision/shape, resource/data, modifier, external impulse, and exclusion slices remain open. Member-level correspondence is partial; no terminal disposition or finding snapshot has been accepted. This active report claims no pair completion.
- Evidence/hash/correspondence audit: hashes and selected source bodies are recorded above; remaining cited-member, resource/data, and call-graph evidence still must be added. No claims of equivalence.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).
