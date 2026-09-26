# Discovery: 1.8.9 to 1.9.4

- Status: active (paired source comparison awaiting 1.8.9 provenance)
- Scope: client player movement; older A = 1.8.9; newer B = 1.9.4
- Repository revision and start date: `c133c29`; 2026-09-26
- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather for B, explicit `feather`; resolved Feather build `1.9.4+build.2`. A is owned by the 1.8.9 chat; do not treat its existing output as evidence until its provenance is established. If it is Feather, this is the aligned namespace; otherwise stop and resolve a valid alignment before comparing.
- Source preparation command and log: `.\gradlew.bat --no-daemon decompileMinecraft --versions=1.9.4 --mappings=feather`; successful completion excerpt: [source-preparation.log](source-preparation.log). Console emitted `Decompiling Minecraft 1.9.4 (requested '1.9.4')`, `Applying ornithe feather (1.9.4+build.2)`, `Decompiling with Vineflower into .../1.9.4/ornithe-feather`, and `Finished 1.9.4 using ornithe-feather`. Gradle 9.7.1 wrapper; decompiler JVM 25.0.3+9-LTS; buildSrc declares Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1. Task's default decompiler options are in `buildSrc/src/main/java/legacyparkourcompat/minecraft/MinecraftDecompileEngine.java`. No failed decompiler task or required-class check was reported. Output also contained remapper invalid-access warnings, which it said were fixed for 1 class and 32 members; movement impact remains to be checked if those classes enter a slice.

## Artifact manifest

### A — 1.8.9

- Requested/resolved release: 1.8.9 / awaiting provenance from owning chat.
- Source root, client jar identity/hash, CLI mode, Feather mapping coordinate/hash, mapped jar hash, and successful log: pending owner verification. Existing shared output is navigation-only until then; do not regenerate it in this chat.

### B — 1.9.4

- Requested/resolved release: 1.9.4 / 1.9.4 (explicit version printed by the successful task).
- Source root: `../../../../decompiled_minecraft/1.9.4/ornithe-feather` (through the worktree junction to the shared root).
- Client jar: `build/minecraft-decompile-cache/1.9.4/client.jar`, 8,736,083 bytes, SHA-256 `23E90103A1CA2AC71100004C6D5846DE09F85695F579843EF8DA41571E60C908`; Mojang object ID/SHA-1 `4a61c873be90bb1196d68dac7b29870408c56969`.
- CLI mode and naming namespace: explicit `feather`; Ornithe Feather.
- Mapping coordinate/build: `net.ornithemc:feather-gen2:1.9.4+build.2`; resolved merged-v2 artifact `feather-gen2-1.9.4+build.2-mergedv2.jar`, SHA-256 `49A38D0ADFBDA1749E519C29844116E9F22E895CB505633261B7F587268F4125`; cached at `build/minecraft-decompile-cache/yarn/feather-gen2-1.9.4+build.2-mergedv2.jar`. Extracted Tiny mapping `feather-gen2-1.9.4+build.2.tiny`, SHA-256 `9E21708D4BC32A43AC404735EA3238465889797110204A0375BCB069A3798027`.
- Remapped jar: `build/minecraft-decompile-cache/1.9.4/client-ornithe-feather.jar`, 5,996,888 bytes, SHA-256 `BF1AC5827656840275F45C62D26D7B0671575A8131DD0FD9C0268F733C5B0A96`.
- Source inventory: output directory exists; 1,819 Java files. Hash each cited file when its paired slice is completed; do not regenerate this output after findings depend on it.
- Version metadata: `build/minecraft-decompile-cache/1.9.4/version.json`, SHA-256 `4B21379203F0D87DF8CC2AB4DD3C3689F139772F1CDB37B40688252547E2D5BE`.
- Relevant initial source hashes: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` `8AAF711948B7602C2E6C015A37E36ED06073D39727D999D80480B4910B704F5D`; `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` `68F6E640808D8B31C1A99D1470075606A42290600FD45435AA86C15EB8DB5EF0`; `net/minecraft/client/entity/living/player/KeyboardInput.java` `7BE11425906BE051C83E275F359816546E4677B16D212156380E8D2E9258654A`; `net/minecraft/client/entity/living/player/Input.java` `206DEA2FF5977599596C907C54F36300C4E72D07D6778D82E8850F57BB0FB009`; `net/minecraft/entity/living/player/PlayerEntity.java` `D658A0D95452D12BB7E347BFD802240EEECAF7F938E10DCD43E2640434387F85`; `net/minecraft/entity/living/LivingEntity.java` `BBB7703F18FD5DA05C4E4A43A77EA644B388E63C01D34166D308EA52054BE4E5`; `net/minecraft/entity/Entity.java` `BCD7FA2206BF8D7271102F6DA7FE2771F96DBE22EC79DAB9F2101A3E29C17EF0`; `net/minecraft/block/Block.java` `E62ECE80C6A7E7121346F65F8FDFD9B148C29441A27DE9F568BBA9AFE1D84FE6`.
- Resources cited: none yet. The Java source saver excludes jar resources; inspect the original client jar when resource-backed movement dependencies are reached.

## Correspondence and call order

No A-to-B class/member correspondence has been asserted. Build it from both provenanced sources in source-navigation order. B-side preliminary index (all paths are under `decompiled_minecraft/1.9.4/ornithe-feather/`; cited B source hashes are above):

- Local input: `net.minecraft.client.entity.living.player.KeyboardInput extends Input`; `tick()` clears two floats, samples forward/back/left/right key states in that order, updates `jumping` and `sneaking`, then scales sideways/forward by `(float)(value * 0.3)` when sneaking. It reads `GameOptions` keybindings and writes input booleans/magnitudes.
- Local player inheritance: `LocalClientPlayerEntity extends ClientPlayerEntity extends PlayerEntity extends LivingEntity extends Entity`. `LocalClientPlayerEntity.tick()` checks the chunk, calls `super.tick()`, then sends movement/vehicle packets. This is the network/reporting wrapper around the superclass tick chain.
- Input-to-living chain: `LocalClientPlayerEntity.mobTick()` saves prior jump/sneak/forward state, calls `input.tick()`, applies item-use input scaling, evaluates sprint/flight/elytra/mount gates, then calls `super.mobTick()`. `PlayerEntity.mobTick()` calls `super.mobTick()` before setting movement attributes and speed fields. `LivingEntity.mobTick()` invokes `serverTickAi()` when its local-control predicate is true, handles jump and cooldown, scales sideways/forward input and calls `moveRelative(sidewaysSpeed, forwardSpeed)`, then pushes colliding entities. `LocalClientPlayerEntity.serverTickAi()` copies input magnitudes/jump into `sidewaysSpeed`, `forwardSpeed`, and `jumping` when the player is the camera. `LocalClientPlayerEntity.isSneaking()` reads input sneaking unless sleeping. Exact method/caller correspondence and predicate timing are pending pair verification.
- State observed in this preliminary B index: input booleans and magnitudes; camera/local-control gates; yaw/pitch easing; sprint/double-tap/jump cooldown timers; flight ability/speed; on-ground/horizontal-collision flags; velocity; hunger, blindness, item-use and elytra equipment. These are navigation leads only, not findings.
- Next B dependencies by source-navigation stage: resolve `LivingEntity` travel/collision methods and helpers; `Entity.move`/box queries; registered block/fluid movement properties and overrides; status effects/attributes/equipment and original-jar data; then local-player packet handlers for externally supplied velocity/position. No B-only slice is considered compared.

## Coverage ledger

All rows remain pending until both exact source artifacts are provenanced and their dependencies can be compared. Preliminary B navigation above is not a comparison.

- Input and tick ordering — pending; A and B paired evidence not yet inspected.
- Player-specific state and gates — pending; A and B paired evidence not yet inspected.
- Living movement integration — pending; A and B paired evidence not yet inspected.
- Entity movement and collision — pending; A and B paired evidence not yet inspected.
- Blocks and fluids that produce movement inputs — pending; A and B paired evidence not yet inspected.
- Effects, enchantments, attributes and equipment — pending; A and B paired evidence and jar resources not yet inspected.
- External influences and dependency closure — pending; A and B paired evidence not yet inspected.

## Dependency queue and blockers

- `PAIR-A`: obtain the owning chat's verified 1.8.9 source provenance, successful log, mapping family/build, client/mapped jar identities and hashes. It affects namespace alignment and every paired finding. Then verify the shared output hashes before use. Do not regenerate it here.
- `PAIR-B-LOG`: the successful 1.9.4 invocation output is captured as a concise excerpt in `source-preparation.log`; no complete raw console log was persisted. The source tree and mapped-jar hash establish the produced artifacts, and the task reported success.

## Finding index

None yet.

## Resume checkpoint

- Last completed slice: none; 1.9.4 Feather source preparation and initial provenance inventory.
- Next action: wait for the 1.8.9 owner, verify source provenance and shared source hashes, then establish exact correspondence and begin stage 1 with no more than three method pairs per slice.
- Outstanding dependencies: `PAIR-A`; decide Feather alignment only after owner evidence. No behavior candidates have been proposed.
- Current assumptions requiring verification: the 1.8.9 owner may have Feather available, but this is not established. No movement conclusions can be made from the 1.9.4-only output.

## Source audit closure

- Coverage counts by status: pending 7; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0.
- Unresolved gaps and limits: all paired movement slices remain unexamined until the 1.8.9 provenance is available.
- Evidence/hash/correspondence audit: 1.9.4 client, mapping artifacts, remapped jar, metadata, and initial navigation source hashes recorded above; findings not yet written.
- Runtime validation: not performed (separate workflow).
