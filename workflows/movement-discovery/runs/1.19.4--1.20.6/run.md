# Discovery: 1.19.4 to 1.20.6

- Status: active
- Scope: client player movement; older A = 1.19.4; newer B = 1.20.6
- Repository revision and start date: source baseline `c133c29`; worktree branch `feat/movement-discovery-1-20`; 2026-09-26
- Selected naming namespace, CLI mode per side and alignment evidence: A and B both use release-specific Mojang official mappings (`mojmap`). The A owner manifest identifies exact 1.19.4 Mojmap artifacts, and this task independently verified the 1.19.4 client, mappings, remapped jar, version metadata and cited source hashes against those identities. Release-specific mappings are paired in the same official namespace.
- Source preparation command and log: `gradlew.bat decompileMinecraft --versions=1.20.6 --mappings=mojmap`; successful `BUILD SUCCESSFUL` output captured by this task session, but no persistent Gradle console log was configured. Re-run is not permitted after output generation without invalidating B evidence.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; JDK 25.0.3+9-LTS fork (`-Xmx4G`, task default); Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; ASM 9.10.1; Fabric Loom 1.17.21. Exact mapping mode `mojmap`; no additional decompiler options.

## Artifact manifest

### B: 1.20.6

- Exact requested/resolved release: 1.20.6 / 1.20.6 (successful task line: `Decompiling Minecraft 1.20.6 (requested '1.20.6')`).
- Source root: `../../../../decompiled_minecraft/1.20.6/mojmap/` from this manifest directory; shared ignored root junction resolves to repository-level `decompiled_minecraft/`.
- Command: `gradlew.bat decompileMinecraft --versions=1.20.6 --mappings=mojmap`.
- Client jar: local cache path `../../../../build/minecraft-decompile-cache/1.20.6/client.jar`; SHA-256 `02DFD345AC1AD55692D5DBC8486AC7E4FEA72CD54AC494A79CD48963048E56B2`; Mojang version metadata client object `05b6f1c6b46a29d6ea82b4e0d42190e42402030f`.
- Mapping: official Mojang client mappings; cache path `../../../../build/minecraft-decompile-cache/1.20.6/client_mappings.txt`; Mojang object `de46c8f33d7826eb83e8ef0e9f80dc1f08cb9498`; local SHA-256 `27F4EF3A9362E9874E2C33EDAC981B6F485AD97F92C4E35B497525B646AAE745`.
- Remapped jar: `../../../../build/minecraft-decompile-cache/1.20.6/client-mojmap.jar`; SHA-256 `0A82FBD3C9EB3570573AC9CE7C1A2357F5A12D7C4501F63D62BA0B8E5D060532`.
- Version metadata: `../../../../build/minecraft-decompile-cache/1.20.6/version.json`; SHA-256 `6797A5A0B1C1F1C5E4F4B2B5EB0AA266BC1E350126A8AA71082E2C3808622597`.
- Task completed successfully. Non-fatal remapper access warnings affected five classes and zero members; Vineflower reported four repeated-processing notices. Relevant movement methods still require slice-level inspection for decompiler integrity.
- A-side exact request/resolution: 1.19.4 / 1.19.4, independently verified against the adjacent owner manifest and successful completion record. Command: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.19.4 --mappings=mojmap`; same JDK, Vineflower, remapper and Mapping IO versions as B. The command succeeded; no persistent raw log file was retained. Do not regenerate A.
- A source root: `../../../../decompiled_minecraft/1.19.4/mojmap/` via verified shared-root junction. Original client jar SHA-256 `0E79CF7F07C107E9A1FE22ED703E472372B44149E64114944F0811ABBD25F3EC` (publisher SHA-1 `958928a560c9167687bea0cefeb7375da1e552a8`); version metadata SHA-256 `DABA0C50674934924B15007863ADE2C0A8697F370781CD15E7F5AD950176CAB6`; official mapping artifact SHA-256 `5EF270B938F89CFC77371E0DEEE9F9044C41D9A373FA11DCB1FD1923272C4606` (publisher SHA-1 `f14771b764f943c154d3a6fcb47694477e328148`); remapped jar SHA-256 `3E40F8B67DC696F4E08F2F7AFD4D4C2E263B2A86221D13141953C7FDAA80375D`. All four cache hashes independently match the A owner manifest.

### B-side navigation inventory and source hashes

Paths are relative to `../../../../decompiled_minecraft/1.20.6/mojmap/`. SHA-256 values below are for the cited decompiled `.java` source files. This is an initial path inventory; paired member findings are recorded below and per slice.

- Stage 1 input/tick: `net/minecraft/client/player/LocalPlayer.java` `6B429DFA6E0681251EC985DDA1627F808652A7BBE5B70DC85C8FA0FE0ED46FFA`; `KeyboardInput.java` `A8064906872955A3520398AB5B2A326552D424F41887D1294AA6A038E2623FF0`; `Input.java` `B302FFBC45C5F900EA18A4D4AF2DF6FA0454EA7CB7744A0D249061E5FCB97FBB`.
- Stage 2 player state: `net/minecraft/world/entity/player/Player.java` `785D93CCC94E1F912E545B2B0C355EDEB352B44EE8E83A69364DAEC35266DBE2`; `Abilities.java` `A4F952ADA7BC3B21406FEAF13C171BFA22D36B01E265E3B987612F28B5609EDC`; `LivingEntity.java` `C66EC8DC3B1856E490E5834A46185589030D9FBC411E3E2CE64C73203CD753B2`.
- Stage 3 living integration: `LivingEntity.java` (same hash above), `net/minecraft/world/entity/ai/attributes/Attributes.java` `7E30D87C7A58B14D0052D2F9F7319D997B49AE7D025579CD762D28E845B2E82E`.
- Stage 4 movement/collision: `net/minecraft/world/entity/Entity.java` `71CD6B9F6C002684154DCE11D3745E8714D82F13C8E1B3AA56743930131C18F3`; `net/minecraft/world/phys/AABB.java` `13AB54EF7B11ABBE41465580EB5CDDB05A4BAD3178995D4609C252D70B96CAD9`; `net/minecraft/world/phys/shapes/VoxelShape.java` `106EB81EE1B2B0F7AA22C225524D4ADA28CB28906FFFFC9F7E790E4FFEFBE9ED`.
- Stage 5 block/fluid inventory: `net/minecraft/world/level/block/Blocks.java` `684579BBCBE48B1F0984090A4CA044C0B2CEE6D08C2DC1E449E2259389C5B129`; `net/minecraft/world/level/block/state/BlockBehaviour.java` (hash to record when cited); `net/minecraft/world/level/material/FlowingFluid.java` `0F44B1AF25533DD8A1FBFEC13B279739341DB0C563568337A2825D3E46A1A5BB`; `FluidState.java` `B52E2B1E889A510B5D80C5EEF09CD4F5B63448F15DBB131F02030497340E7C1C`. Movement-relevant block implementation filenames present include BedBlock, BubbleColumnBlock, FrostedIceBlock, HoneyBlock, IceBlock, LadderBlock, PowderSnowBlock, SlimeBlock, SoulSandBlock and WebBlock; no behavior conclusion recorded.
- Stage 6 effects/enchantments/attributes: `net/minecraft/world/effect/MobEffects.java` `9B24DEE8A23A4B8730FDFA7EA9CBD560C9370599CC282FAC1C0755F5894E7645`; `net/minecraft/world/item/enchantment/Enchantments.java` `26EEAFDF667FA57B711FAB3112AA9636F20A9A38CADFBFF42740E66A14C38381`; `EnchantmentHelper.java` `E0B4C410E0AA9499D67B38F57B34467628E882BE960DCE6958D4BC8EF717A6B6`; `Attributes.java` (same hash above). Resource entries and their hashes remain to be inventoried before this stage can close.
- Stage 7 external influences: `LocalPlayer.java` and `Entity.java` above are starting seeds; packet consumers, correction/push paths and unresolved state writers remain to be inventoried.

## Correspondence and call order

A/B correspondence seeds resolved from the filename inventories (Mojmap names): local player `LocalPlayer extends AbstractClientPlayer`; input implementation `KeyboardInput extends Input`; player state `Player`; movement superclass `LivingEntity`; base entity/collision owner `Entity`; box/shape types `AABB` / `VoxelShape`. Verified B entry methods include `LocalPlayer.tick()` (line 190), `LocalPlayer.aiStep()` (648), `LivingEntity.jumpFromGround()` (2069), `LivingEntity.travel(Vec3)` (2104), `LivingEntity.handleRelativeFrictionAndCalculateMovement(Vec3,float)` (2269), `LivingEntity.tick()` (2336), `LivingEntity.aiStep()` (2591), `Entity.tick()` (414), and `Entity.moveRelative(float,Vec3)` (1311). B-side tick order begins `LocalPlayer.tick()` -> `super.tick()` -> `LivingEntity.tick()`/`aiStep()`; `LocalPlayer.aiStep()` samples `Input`, applies client gates and calls `super.aiStep()`, which reaches living movement. Exact member ordering and state reads/writes continue to be added per bounded slice. A-to-B Mojmap namespace alignment is verified. `LocalPlayer`, `KeyboardInput`, `Input`, `Player`, `LivingEntity`, `Entity`, `AABB`, `VoxelShape`, `FlowingFluid`, `MobEffects`, `Enchantments`, `EnchantmentHelper`, `Blocks` and `ClientPacketListener` correspondence is confirmed from matching owner roles, inheritance/call paths and verified source hashes; member correspondence is established per slice.

## Coverage ledger

- Stage 1 / local input and tick ordering: findings; slices: `S1-01` keyboard input tick compared-no-difference; `S1-02` input vector and forward predicate compared-no-difference; `S1-03` LocalPlayer tick field-access refactor compared-no-difference after getter verification; `S1-04` creative flight toggle findings in `findings/MC1194-1206-01.md` for the added ground-jump call on creative flight activation. `KeyboardInput.tick`, `Input.getMoveVector` and `Input.hasForwardImpulse` compared-no-difference; `LocalPlayer.tick` differences at `level.hasChunkAt`/`level().hasChunkAt` and `onGround`/`onGround()` resolve to the same fields through B getters. Passenger crouch slowdown remains queued for scope/dependency disposition.
- Stage 2 / player-specific state and gates: in-progress; A/B player and entity anchors hash-verified; pose-fit refactor and passenger crouch state require bounded dependency tracing.
- Stage 3 / living movement integration: in-progress; bounded travel/jump slices `S3-01` gravity attribute source, `S3-02` fall-distance reset lifetime, and `S3-03` jump-power rounding have findings; remaining fluid, friction, acceleration, climb and effect closure is pending.
- Stage 4 / entity movement and collision: in-progress; A/B entity/collision anchors hash-verified; movement and query comparisons pending.
- Stage 5 / blocks and fluids: in-progress; B seed inventory and selected B resource entries recorded; A source/resource comparison and registration/override closure pending.
- Stage 6 / effects, enchantments, attributes and equipment: in-progress; A/B code anchors hash-verified; paired consumers and data/resource closure pending.
- Stage 7 / external influences and dependency closure: in-progress; A/B player, entity and packet-listener anchors hash-verified; packet and external-value comparisons pending.

## Dependency queue and blockers

- A source provenance verified against the adjacent owner manifest; no raw persistent console log exists, so successful completion facts are recorded in that manifest and this file. Do not regenerate A.
- B resource integrity: inspect the original `client.jar` entries for movement-relevant tags and data; hash each entry as it enters a cited dependency closure.
- B ordered member index: continue bounded methods, callers, state writers, registrations and resource dependencies before making terminal coverage claims.

## Finding index

`MC1194-1206-01` — Flight activation adds a ground-jump impulse (source-confirmed).
`MC1194-1206-02` — Living travel uses the synced gravity attribute (source-confirmed; default value unchanged).
`MC1194-1206-03` — Slow Falling and Levitation reset fall distance across a broader set of states (source-confirmed).
`MC1194-1206-04` — Jump Boost power sum is rounded to float before velocity assignment (source-confirmed).

## Resume checkpoint

- Last completed slice: stage 3 first bounded travel/jump comparison; three source-confirmed findings added.
- Next step: disposition stage-1 passenger-only input scaling; close stage 2 pose, dimensions and ability gates; continue stage 3 dependencies in order.
- Outstanding dependencies: passenger crouch slowdown scope; pose-fit/query dependency; gravity/jump attribute values and effects; resource closure; remaining navigation slices.
- Namespace alignment: verified Mojmap-to-Mojmap for exact 1.19.4 and 1.20.6 artifacts; names alone are not treated as correspondence proof.

## Source audit closure

- Coverage counts: stage 1 includes findings and compared-no-difference sub-slices; stages 2-7 in-progress; 0 blocked.
- Unresolved gaps: stages 1-7 are not closed; resource, method and dependency closures remain. This checkpoint is partial.
- Evidence/hash/correspondence audit: A/B artifacts and relevant source hashes verified; both console logs lack persistent raw files; stage-1 member references are in the finding.
- Runtime validation: not performed (separate workflow).

### B-side jar resource inventory (initial)

Entries are from the original cached 1.20.6 `client.jar` (`../../../../build/minecraft-decompile-cache/1.20.6/client.jar`), not the JavaDirectorySaver output. Hashes are SHA-256 of each ZIP entry's uncompressed bytes. These are navigation leads only; inspect their contents and references before using them in a completed coverage row.

- `data/minecraft/tags/blocks/climbable.json` — `D0E3E76D7457F3F3F3D7219FE218C7746E4B2E7626D5C388FCF193089069E365`
- `data/minecraft/tags/blocks/ice.json` — `85098BDEB333FB4B630565E8679BFAAE143D78DEFA4DBF0398280A9F4D863DB7`
- `data/minecraft/tags/blocks/soul_speed_blocks.json` — `8BA7EAC6F7C74D25601EF4455B71E3947FDB8E51B8FAFD55DDDEE48397B144B6`
- `data/minecraft/tags/blocks/snow.json` — `24D5E3B9042595E734D911CAF4D1D59F8D27C865743FD7562FA72D32F0B5ACD0`
- `data/minecraft/tags/fluids/lava.json` — `71F50FB9092D78260BC7434731FC5FD426A44E5284A6AD084EC71CB725630C6B`
- `data/minecraft/tags/fluids/water.json` — `DCFA69A748D03DBF788D8F7B0E5EB6C8DE6355A527FCAF74B9210FE4BE2A3004`
- `data/minecraft/tags/items/freeze_immune_wearables.json` — `8DF0FA68F14A7F9705DF39043A43C0B07ABF3C30642608EC47C52F78A3EE0C3D`

The successful decompiler output and cache are kept untracked/ignored. Gradle did not persist a console log file in this run; the task tool output recorded the requested/resolved version, artifact downloads, mapping mode, warnings and successful completion. This is a provenance-record limitation to resolve before final closure if a durable log becomes necessary; no B evidence is currently invalidated by rerunning before any paired findings, but no rerun is planned now.
### Stage 1 evidence index (paired)

- `S1-01` Keyboard input sampling: A and B `net/minecraft/client/player/KeyboardInput.java`, `KeyboardInput.tick(boolean,float)` lines 21-36; exact same source hash `A8064906872955A3520398AB5B2A326552D424F41887D1294AA6A038E2623FF0`. Both read up/down/left/right/jump/shift key states, derive impulses with the same `calculateImpulse` comparisons and apply the same slowdown multiplier in the same order. Status: compared-no-difference for this bounded member.
- `S1-02` Input representation: A and B `net/minecraft/client/player/Input.java`, `getMoveVector()` and `hasForwardImpulse()` lines 18-24; exact same source hash `B302FFBC45C5F900EA18A4D4AF2DF6FA0454EA7CB7744A0D249061E5FCB97FBB`. Both return `new Vec2(leftImpulse, forwardImpulse)` and compare `forwardImpulse > 1.0E-5F`; `Input.tick` is empty on both. Status: compared-no-difference for these members.
- `S1-03` Local-player send tick: A `LocalPlayer.tick()` lines 187-207, source SHA-256 `8E7DA18F42D09FBB994F522C2B0E65FCB2BB83CABB21024360299D44D9674C58`; B lines 190-210, source SHA-256 `6B429DFA6E0681251EC985DDA1627F808652A7BBE5B70DC85C8FA0FE0ED46FFA`. Guard changed from direct `level` field access to `level()`, and ground flag arguments changed from field to `onGround()`. B `Entity.level()` returns the `level` field (line 3393); `Entity.onGround()` returns the `onGround` field (line 593). A exposed those same fields to LocalPlayer. These substitutions preserve the read values; packet selection and call order in the inspected method are the same. Status: compared-no-difference for these access-path changes.
- `S1-04` Creative-flight transition: finding `findings/MC1194-1206-01.md`; LocalPlayer.aiStep branch plus player/living jump call chain. Status: findings.
- Passenger crouch slowdown candidate: `LocalPlayer.aiStep` now excludes passengers when setting the local `crouching` flag before `Input.tick(isMovingSlowly(), factor)`. This can alter input scaling forwarded to a ridden entity; determine whether any player movement state changes independently of the vehicle, and disposition against the explicit non-player-entity scope before closing S1.

A-side hash cross-checks against the adjacent source manifest: `LocalPlayer.java` `8E7DA18F42D09FBB994F522C2B0E65FCB2BB83CABB21024360299D44D9674C58`; `KeyboardInput.java` `A8064906872955A3520398AB5B2A326552D424F41887D1294AA6A038E2623FF0`; `Input.java` `B302FFBC45C5F900EA18A4D4AF2DF6FA0454EA7CB7744A0D249061E5FCB97FBB`; `Entity.java` `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B`; `LivingEntity.java` `C8D91AF61F87AAA1666DE79696D7CD9D4A8212D05F873BDAF28D7BB2926BF165`; `Player.java` `5E4436AFCCB361156F8184E7A5CFD91D5B12DCFE8F5937B4AC23DD3EDDA737A2`. The B `ClientPacketListener.java` source hash is `E121E998EC25211AAECF91FFFD0EA699CEBD47EDDEA9134EFD3F2FFBEF8EB7CD`; A counterpart hash is `BCC74E52A32D20A3E993EBE4E08FFFE8CACA7481FAD8F326DACDC93796B2B715`.
### Stage 3 evidence ledger (active)

- `S3-01` Travel gravity source and dependent fluid gravity gate: finding `MC1194-1206-02`. B's default player gravity equals A's literal `0.08`; synced attribute modifiers remain an explicit external-input dependency.
- `S3-02` Fall-distance reset timing/guards: finding `MC1194-1206-03`; position/velocity effects are not asserted.
- `S3-03` Ground-jump power operation order: finding `MC1194-1206-04`; effect application and attribute defaults are linked dependencies.
- Remaining stage-3 slices: ground/air acceleration and friction, water/lava, climbing, levitation/fall flying, post-travel updates and all callers/dependencies.`n