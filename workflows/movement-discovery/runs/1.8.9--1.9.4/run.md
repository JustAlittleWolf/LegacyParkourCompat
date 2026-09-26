# Discovery: 1.8.9 to 1.9.4

- Status: active (paired source audit underway)
- Scope: client player movement; older A = 1.8.9; newer B = 1.9.4
- Repository revision and start date: `c133c29`; 2026-09-26
- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather for B, explicit `feather`; resolved Feather build `1.9.4+build.2`. A is owned by the 1.8.9 chat; do not treat its existing output as evidence until its provenance is established. If it is Feather, this is the aligned namespace; otherwise stop and resolve a valid alignment before comparing.
- Source preparation command and log: `.\gradlew.bat --no-daemon decompileMinecraft --versions=1.9.4 --mappings=feather`; successful completion excerpt: [source-preparation.log](source-preparation.log). Console emitted `Decompiling Minecraft 1.9.4 (requested '1.9.4')`, `Applying ornithe feather (1.9.4+build.2)`, `Decompiling with Vineflower into .../1.9.4/ornithe-feather`, and `Finished 1.9.4 using ornithe-feather`. Gradle 9.7.1 wrapper; decompiler JVM 25.0.3+9-LTS; buildSrc declares Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1. Task's default decompiler options are in `buildSrc/src/main/java/legacyparkourcompat/minecraft/MinecraftDecompileEngine.java`. No failed decompiler task or required-class check was reported. Output also contained remapper invalid-access warnings, which it said were fixed for 1 class and 32 members; movement impact remains to be checked if those classes enter a slice.

## Artifact manifest

### A — 1.8.9

- Requested/resolved release: 1.8.9 / 1.8.9, confirmed in the owning chat's successful explicit decompilation log.
- Source root: `../../../../decompiled_minecraft/1.8.9/ornithe-feather` (resolved directory exists under the shared root); 1,612 Java files. In this task I verified the 1.8.9 Feather source files are present and recorded hashes for every file cited below. The source is owner-produced and was not regenerated here.
- CLI mode and naming namespace: explicit `feather`; matching Ornithe Feather family on both endpoints. Owner log selects `1.8.9+build.2`.
- Client jar identity from the owner's verified version metadata: version 1.8.9, 5,256,245 bytes, publisher SHA-1 `e80d9b3bf5085002218d4be59e668bac718abbc6`. Client jar SHA-256, exact metadata file hash, Feather mapping artifact hash/path, remapped jar hash and original log file path are still being recorded by the owner; these fields remain open until received.
- Successful source preparation: the owner reports command `gradlew.bat decompileMinecraft --rerun-tasks --versions=1.8.9 --mappings=feather`, exact resolver output `Applying ornithe feather (1.8.9+build.2)`, output to the exact source root above, and `Finished 1.8.9 using ornithe-feather`. The parent coordinator verified the successful log. The concise excerpt is in [source-preparation.log](source-preparation.log); the original raw log remains in the owning chat's worktree.

### B — 1.9.4

- Requested/resolved release: 1.9.4 / 1.9.4 (explicit version printed by the successful task).
- Source root: `../../../../decompiled_minecraft/1.9.4/ornithe-feather` (through the worktree junction to the shared root).
- Client jar: `../../../../build/minecraft-decompile-cache/1.9.4/client.jar`, 8,736,083 bytes, SHA-256 `23E90103A1CA2AC71100004C6D5846DE09F85695F579843EF8DA41571E60C908`; Mojang object ID/SHA-1 `4a61c873be90bb1196d68dac7b29870408c56969`.
- CLI mode and naming namespace: explicit `feather`; Ornithe Feather.
- Mapping coordinate/build: `net.ornithemc:feather-gen2:1.9.4+build.2`; resolved merged-v2 artifact `feather-gen2-1.9.4+build.2-mergedv2.jar`, SHA-256 `49A38D0ADFBDA1749E519C29844116E9F22E895CB505633261B7F587268F4125`; cached at `../../../../build/minecraft-decompile-cache/yarn/feather-gen2-1.9.4+build.2-mergedv2.jar`. Extracted Tiny mapping `feather-gen2-1.9.4+build.2.tiny`, SHA-256 `9E21708D4BC32A43AC404735EA3238465889797110204A0375BCB069A3798027`.
- Remapped jar: `../../../../build/minecraft-decompile-cache/1.9.4/client-ornithe-feather.jar`, 5,996,888 bytes, SHA-256 `BF1AC5827656840275F45C62D26D7B0671575A8131DD0FD9C0268F733C5B0A96`.
- Source inventory: output directory exists; 1,819 Java files. Hash each cited file when its paired slice is completed; do not regenerate this output after findings depend on it.
- Version metadata: `../../../../build/minecraft-decompile-cache/1.9.4/version.json`, SHA-256 `4B21379203F0D87DF8CC2AB4DD3C3689F139772F1CDB37B40688252547E2D5BE`.
- Relevant initial source hashes: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` `8AAF711948B7602C2E6C015A37E36ED06073D39727D999D80480B4910B704F5D`; `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` `68F6E640808D8B31C1A99D1470075606A42290600FD45435AA86C15EB8DB5EF0`; `net/minecraft/client/entity/living/player/KeyboardInput.java` `7BE11425906BE051C83E275F359816546E4677B16D212156380E8D2E9258654A`; `net/minecraft/client/entity/living/player/Input.java` `206DEA2FF5977599596C907C54F36300C4E72D07D6778D82E8850F57BB0FB009`; `net/minecraft/entity/living/player/PlayerEntity.java` `D658A0D95452D12BB7E347BFD802240EEECAF7F938E10DCD43E2640434387F85`; `net/minecraft/entity/living/LivingEntity.java` `BBB7703F18FD5DA05C4E4A43A77EA644B388E63C01D34166D308EA52054BE4E5`; `net/minecraft/entity/Entity.java` `BCD7FA2206BF8D7271102F6DA7FE2771F96DBE22EC79DAB9F2101A3E29C17EF0`; `net/minecraft/block/Block.java` `E62ECE80C6A7E7121346F65F8FDFD9B148C29441A27DE9F568BBA9AFE1D84FE6`.
- Resources cited: none yet. The Java source saver excludes jar resources; inspect the original client jar when resource-backed movement dependencies are reached.

## Correspondence and call order

No full A-to-B movement correspondence has been asserted. Build it from both provenanced sources in source-navigation order. B-side preliminary index (all paths are under `decompiled_minecraft/1.9.4/ornithe-feather/`; cited B source hashes are above):

- Local input: `net.minecraft.client.entity.living.player.KeyboardInput extends Input`; `tick()` clears two floats, samples forward/back/left/right key states in that order, updates `jumping` and `sneaking`, then scales sideways/forward by `(float)(value * 0.3)` when sneaking. It reads `GameOptions` keybindings and writes input booleans/magnitudes.
- Local player inheritance: `LocalClientPlayerEntity extends ClientPlayerEntity extends PlayerEntity extends LivingEntity extends Entity`. `LocalClientPlayerEntity.tick()` checks the chunk, calls `super.tick()`, then sends movement/vehicle packets. This is the network/reporting wrapper around the superclass tick chain.
- Input-to-living chain: `LocalClientPlayerEntity.mobTick()` saves prior jump/sneak/forward state, calls `input.tick()`, applies item-use input scaling, evaluates sprint/flight/elytra/mount gates, then calls `super.mobTick()`. `PlayerEntity.mobTick()` calls `super.mobTick()` before setting movement attributes and speed fields. `LivingEntity.mobTick()` invokes `serverTickAi()` when its local-control predicate is true, handles jump and cooldown, scales sideways/forward input and calls `moveRelative(sidewaysSpeed, forwardSpeed)`, then pushes colliding entities. `LocalClientPlayerEntity.serverTickAi()` copies input magnitudes/jump into `sidewaysSpeed`, `forwardSpeed`, and `jumping` when the player is the camera. `LocalClientPlayerEntity.isSneaking()` reads input sneaking unless sleeping. Exact method/caller correspondence and predicate timing are pending pair verification.
- State observed in this preliminary B index: input booleans and magnitudes; camera/local-control gates; yaw/pitch easing; sprint/double-tap/jump cooldown timers; flight ability/speed; on-ground/horizontal-collision flags; velocity; hunger, blindness, item-use and elytra equipment. These are navigation leads only, not findings.
- Living movement anchors: `LivingEntity.jump()` sets Y velocity from `getJumpStrength()`, adds Jump Boost, then applies sprint impulse to X/Z; water and lava jump helpers add `0.04F`. `LivingEntity.moveRelative(float,float)` reads local-control/riding/fluid/fall-flying/climbing/ground state and branches through Elytra, ground/air, lava, and water movement. It calls `Entity.updateVelocity(float,float,float)` and `Entity.move(double,double,double)`; ground friction reads the supporting block's `slipperiness`; water acceleration reads Depth Strider through `EnchantmentHelper`; Levitation changes vertical velocity. `PlayerEntity.jump()` wraps the superclass and stats/fatigue; `PlayerEntity.moveRelative(float,float)` wraps it for flight abilities and then movement stats; `PlayerEntity.getSpeed()` reads the movement-speed attribute. State writes include velocity, fall distance, flying flag, and walk animation. These are source-navigation anchors only; bounded paired slices still need A.
- Next B dependencies by source-navigation stage: resolve the above movement helpers and their exact control flow; `Entity.move`/box queries and collision callbacks; registered block/fluid movement properties and overrides; status effects/attributes/equipment and original-jar data; then local-player packet handlers for externally supplied velocity/position. No B-only slice is considered compared.

## Coverage ledger

Preliminary B navigation above is not a comparison. Paired stage 1 has three source-confirmed findings; the remaining local tick-order dependency is still being resolved.

- `1.1` keyboard movement magnitudes and sneak scaling — Status: compared-no-difference. A `KeyboardInput.tick()` lines 15-36 and B `KeyboardInput.tick()` lines 15-48; hashes and paths in MD-02. The movement float increments/decrements and `0.3` sneak scaling match. The newly added direction booleans are a separate pending slice `1.4`.
- `1.2` sprint timeout — Status: findings; see [MD-01](findings/MD-01-sprint-timeout.md). Other sprint-start/stop predicates and timer lifetime interactions remain open in player gates.
- `1.3` flight plus sneaking input scale — Status: findings; see [MD-02](findings/MD-02-flight-sneak-input-rescaling.md). Item-use slowdown interaction is described there and must be checked against the full player item-use slice.
- `1.4` directional booleans and boat/riding input — Status: findings; see [MD-04](findings/MD-04-boat-input-to-rider-movement.md). The input-to-rider path and A/B client-authoritative control consumers are paired there.
- `1.5` local tick order, jump edges, flight toggles, elytra start, and riding gates — Status: pending. Pair the exact Local/Player/Living tick order and resolve the new Elytra path without assigning historical behavior to absent equipment.
- `2.1` player-specific state and gates — Status: pending. Paired movement fields, initialization and reset writers remain to be inventoried.
- `3.1` sprint modifier and ground speed consumer — Status: compared-no-difference. Both `LivingEntity.setSprinting(boolean)` methods apply the same `0.3F`, operation-2 movement-speed modifier; both `PlayerEntity.getSpeed()` methods read the movement-speed attribute. Paths, lines, and hashes are in MD-01. No difference is claimed beyond this dependency closure.
- `3.2.1` ordinary ground/air acceleration, climbing and gravity — Status: pending. Compare the full branch after separately closing the chunk fallback, Levitation and Elytra dependencies.
- `3.2.2` client unloaded-chunk vertical fallback — Status: findings; see [MD-03](findings/MD-03-negative-zero-chunk-lookup.md). The A cast and B floor paths diverge only at a negative coordinate just below zero, and the adjacent chunk load states must differ for the movement branch to diverge.
- `3.2.3` water/lava movement and Depth Strider — Status: pending. Compare all fluid branches and verify enchantment application/registration and equipment conditions.
- `3.2.4` Levitation velocity integration — Status: pending. B has a new consumer in `LivingEntity.moveRelative`; check both versions' effect registrations and server-supplied effect state before disposition.
- `3.2.5` Elytra movement — Status: pending. B has a new fall-flying branch; trace the state gate, item registration and local start packet. Classify as modern-only if its absence in A is established.
- `4.1` entity movement and collision — Status: pending. A and B movement/collision pairs not yet inspected.
- `5.1` blocks and fluids that produce movement inputs — Status: pending. A and B registrations, overrides and shapes not yet inspected.
- `6.1` effects, enchantments, attributes and equipment — Status: pending. Original-jar data/resources and effect application paths not yet inventoried.
- `7.1` external influences and dependency closure — Status: pending. Packet corrections, push, and launch items remain unexamined.
- `7.2` boat input and rider position — Status: findings; see [MD-04](findings/MD-04-boat-input-to-rider-movement.md). This covers the local player's boat-control path only; remaining mount behavior is open.

## Dependency queue and blockers

- `PAIR-A-MANIFEST`: obtain the 1.8.9 owner's remaining client SHA-256, metadata SHA-256, mapping artifact path/hash, remapped jar SHA-256, tool versions/options and original successful log path. Family/build alignment and source-root existence are established, so the paired source audit can proceed while these provenance fields are completed. Do not regenerate the shared source here.
- `BOAT-INPUT`: resolved by [MD-04](findings/MD-04-boat-input-to-rider-movement.md); other mount/dismount and launch-item dependencies remain under `7.1`.
- `GROUND-POSITION`: check whether the A/B support-friction queries at `getShape().minY` use the same block at all player-reachable coordinates after A's `floor(minY) - 1` and B's pooled `floor(minY - 1.0)` conversions.
- `EFFECTS-LEVITATION`: verify B's effect registration and client path; A absence and server-provided effect applicability.
- `ELYTRA`: trace B `START_FALL_FLYING`, synchronized state and movement source; verify no A counterpart before marking modern-only.
- `PAIR-B-LOG`: the successful 1.9.4 invocation output is captured as a concise excerpt in `source-preparation.log`; no complete raw console log was persisted. The source tree and mapped-jar hash establish the produced artifacts, and the task reported success.

## Finding index

- `MD-01` — Local sprint timeout is removed (source-confirmed).
- `MD-02` — Sneaking input is restored during creative flight (source-confirmed).
- `MD-03` — Unloaded-chunk fallback selects a different chunk near zero (source-confirmed).
- `MD-04` — Boat controls change the local rider's movement (source-confirmed; player interaction with another entity).

## Resume checkpoint

- Last completed slice: stage 1.4 boat input/rider path and stage 7.2 player-on-boat movement; MD-04 recorded. Earlier source work also recorded MD-01–MD-03.
- Next slice: finish `1.5` tick/input ordering, then stage 2 player state. Resume stage 3 in navigation order with `3.2.1` and queued position/effect/Elytra dependencies.
- Outstanding dependencies: remaining hashes/log path in `PAIR-A-MANIFEST`; `GROUND-POSITION`; `EFFECTS-LEVITATION`; `ELYTRA`; further source-driven dependencies from stages 2-7.
- Current assumptions requiring verification: whether any of B's new Elytra gates interact with player state beyond their modern-only applicability; remaining rider/vehicle transitions beyond the covered boat-input path.

## Source audit closure

- Coverage counts by status: pending 10; in-progress 0; compared-no-difference 2; findings 5; not-applicable 0; blocked 0. Counts are slice rows, not stages; MD-04 resolves both stage 1.4 and stage 7.2.
- Unresolved gaps and limits: remaining stage 1 dependencies, stage 2 and most of stages 3-7 are open; A client/mapping hash manifest fields are pending the owner.
- Evidence/hash/correspondence audit: hashes for each source cited in findings MD-01–MD-04 are recorded in those files; 1.9.4 client/mapping/remapped jar hashes are recorded above; remaining A provenance hashes will be filled from the owning chat.
- Runtime validation: not performed (separate workflow).
