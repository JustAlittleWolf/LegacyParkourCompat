# Discovery: 1.19.4 to 1.20.6

- Status: active
- Scope: client player movement; older A = 1.19.4; newer B = 1.20.6
- Repository revision and start date: source baseline `c133c29`; worktree branch `feat/movement-discovery-1-20`; 2026-09-26
- Selected naming namespace, CLI mode per side and alignment evidence: B uses Mojang official mappings (`mojmap`); A-side source and successful provenance are pending from the adjacent earlier-major operator. No paired source comparison or alignment conclusion yet.
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
- A-side exact release, source root, command/log, jar/mapping/remapped-jar identities, and hashes: pending successful provenance from the 1.19.4 owner. Do not regenerate A.

### B-side navigation inventory and source hashes

Paths are relative to `../../../../decompiled_minecraft/1.20.6/mojmap/`. SHA-256 values below are for the cited decompiled `.java` source files. This is an initial path inventory; method-pair correspondence and behavioral conclusions await A provenance.

- Stage 1 input/tick: `net/minecraft/client/player/LocalPlayer.java` `6B429DFA6E0681251EC985DDA1627F808652A7BBE5B70DC85C8FA0FE0ED46FFA`; `KeyboardInput.java` `A8064906872955A3520398AB5B2A326552D424F41887D1294AA6A038E2623FF0`; `Input.java` `B302FFBC45C5F900EA18A4D4AF2DF6FA0454EA7CB7744A0D249061E5FCB97FBB`.
- Stage 2 player state: `net/minecraft/world/entity/player/Player.java` `785D93CCC94E1F912E545B2B0C355EDEB352B44EE8E83A69364DAEC35266DBE2`; `Abilities.java` `A4F952ADA7BC3B21406FEAF13C171BFA22D36B01E265E3B987612F28B5609EDC`; `LivingEntity.java` `C66EC8DC3B1856E490E5834A46185589030D9FBC411E3E2CE64C73203CD753B2`.
- Stage 3 living integration: `LivingEntity.java` (same hash above), `net/minecraft/world/entity/ai/attributes/Attributes.java` `7E30D87C7A58B14D0052D2F9F7319D997B49AE7D025579CD762D28E845B2E82E`.
- Stage 4 movement/collision: `net/minecraft/world/entity/Entity.java` `71CD6B9F6C002684154DCE11D3745E8714D82F13C8E1B3AA56743930131C18F3`; `net/minecraft/world/phys/AABB.java` `13AB54EF7B11ABBE41465580EB5CDDB05A4BAD3178995D4609C252D70B96CAD9`; `net/minecraft/world/phys/shapes/VoxelShape.java` `106EB81EE1B2B0F7AA22C225524D4ADA28CB28906FFFFC9F7E790E4FFEFBE9ED`.
- Stage 5 block/fluid inventory: `net/minecraft/world/level/block/Blocks.java` `684579BBCBE48B1F0984090A4CA044C0B2CEE6D08C2DC1E449E2259389C5B129`; `net/minecraft/world/level/block/state/BlockBehaviour.java` (hash to record when cited); `net/minecraft/world/level/material/FlowingFluid.java` `0F44B1AF25533DD8A1FBFEC13B279739341DB0C563568337A2825D3E46A1A5BB`; `FluidState.java` `B52E2B1E889A510B5D80C5EEF09CD4F5B63448F15DBB131F02030497340E7C1C`. Movement-relevant block implementation filenames present include BedBlock, BubbleColumnBlock, FrostedIceBlock, HoneyBlock, IceBlock, LadderBlock, PowderSnowBlock, SlimeBlock, SoulSandBlock and WebBlock; no behavior conclusion recorded.
- Stage 6 effects/enchantments/attributes: `net/minecraft/world/effect/MobEffects.java` `9B24DEE8A23A4B8730FDFA7EA9CBD560C9370599CC282FAC1C0755F5894E7645`; `net/minecraft/world/item/enchantment/Enchantments.java` `26EEAFDF667FA57B711FAB3112AA9636F20A9A38CADFBFF42740E66A14C38381`; `EnchantmentHelper.java` `E0B4C410E0AA9499D67B38F57B34467628E882BE960DCE6958D4BC8EF717A6B6`; `Attributes.java` (same hash above). Resource entries and their hashes remain to be inventoried before this stage can close.
- Stage 7 external influences: `LocalPlayer.java` and `Entity.java` above are starting seeds; packet consumers, correction/push paths and unresolved state writers remain to be inventoried.

## Correspondence and call order

B-side seeds resolved from the filename inventory (Mojmap names): local player `LocalPlayer extends AbstractClientPlayer`; input implementation `KeyboardInput extends Input`; player state `Player`; movement superclass `LivingEntity`; base entity/collision owner `Entity`; box/shape types `AABB` / `VoxelShape`. Verified B entry methods include `LocalPlayer.tick()` (line 190), `LocalPlayer.aiStep()` (648), `LivingEntity.jumpFromGround()` (2069), `LivingEntity.travel(Vec3)` (2104), `LivingEntity.handleRelativeFrictionAndCalculateMovement(Vec3,float)` (2269), `LivingEntity.tick()` (2336), `LivingEntity.aiStep()` (2591), `Entity.tick()` (414), and `Entity.moveRelative(float,Vec3)` (1311). The B-side ordered call chain and state read/write details are pending bounded method inspection. No A-to-B pairing has been asserted.

## Coverage ledger

- Stage 1 / local input and tick ordering: in-progress; B seed paths inventoried; A evidence pending; B call/state chain inspection pending.
- Stage 2 / player-specific state and gates: in-progress; B seed paths inventoried; A evidence pending; inheritance/default/update closure pending.
- Stage 3 / living movement integration: in-progress; B seed paths and travel/jump members inventoried; A evidence pending; bounded member comparison pending.
- Stage 4 / entity movement and collision: in-progress; B seed paths inventoried; A evidence pending; movement and query dependencies pending.
- Stage 5 / blocks and fluids: in-progress; B seed implementation filenames inventoried; A evidence pending; registrations, overrides, shapes, callbacks and resources pending.
- Stage 6 / effects, enchantments, attributes and equipment: in-progress; B seed paths inventoried; A evidence pending; resource/data dependencies and consumer/application closure pending.
- Stage 7 / external influences and dependency closure: in-progress; B seed paths inventoried; A evidence pending; packet and external-value paths pending.

## Dependency queue and blockers

- A source provenance: exact 1.19.4 source output, successful log and artifact hashes from the adjacent owner; required before pairing, alignment confirmation and behavioral conclusions. Do not regenerate A.
- B resource integrity: inspect the original `client.jar` entries for movement-relevant tags and data; hash each entry as it enters a cited dependency closure.
- B ordered member index: inspect each stage's bounded methods, callers, state writers, registrations and resource dependencies before making terminal coverage claims.

## Finding index

No findings yet. No candidate differences have been evaluated.

## Resume checkpoint

- Last completed slice: exact B (1.20.6) source generation and initial filename/hash inventory.
- Next step: inspect B stage 1 call order and state flow, inventory relevant B resources, then wait for the 1.19.4 owner's successful artifact manifest before any pair comparison.
- Outstanding dependencies: A provenance; B resource hashes; B method/caller/dependency closure.
- Assumption requiring verification: the versions can be compared in aligned official Mojang naming after confirming the A-side mapping source is Mojmap; no alignment is claimed yet.

## Source audit closure

- Coverage counts: 7 in-progress; 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked.
- Unresolved gaps: A provenance and all paired comparisons; B resource closure and ordered method/member index. This checkpoint is partial.
- Evidence/hash/correspondence audit: B artifacts and listed source hashes recorded; persistent task console log was not configured; no A evidence; no pair correspondence asserted.
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
